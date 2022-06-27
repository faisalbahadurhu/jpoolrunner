package com.jpoolrunner.clientside;
import java.awt.BorderLayout;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.*;


import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.statistics.SimpleHistogramBin;
import org.jfree.data.statistics.SimpleHistogramDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.VerticalAlignment;

import com.jpoolrunner.clientside.ResponsePanel.HistogramGraph;
import com.jpoolrunner.job.CpuBoundTask;
import com.jpoolrunner.job.IoBoundTask;
import com.jpoolrunner.job.Task;

public class ResponsePanel extends Consumer {
	double timeForGraphDataShow=3;// time to show data on the graph ...1 minute(60000*1)
	//private TimeSeries throughputSeries;//the line graph to b plotted
	//NonblockingCounter throughput;
	DataUpdater dataGenerator;
	boolean stopThread=false;//to stop datagenerator thread
	NonblockingCounter nonBlockingCounter;
	NonblockingCounter tracerForCompareButton;// came from scenario .All panels increment it only once b4 stopping and since there are 4 Graphics panels so when its value is four then it means that all panels have been stopped and compare button should be enabled now
	JFreeChart chart;
	BooleanChecker sleepTime;
	Vector workloadForBins;
	public Vector getWorkloadForBins() {
		
		return workloadForBins;
	}

	Vector graphs=new Vector(3);
	JPanel flowPanel;
	//int counter=0;

public JFreeChart getChart(){return this.chart;}
	/*public Vector getTotalJobs() {
	return totalJobs;
}*/
public Vector getResposeTimeGraphs(){ return this.graphs;}

	SharedBoolean sharedBoolean;//this boolean would be true only in ResponsePanel when ResponsePanel stops it on drawing all readoObjects by ReciverX and then ThroughputPanel etc would be stopped 
	private String strategyName;
public double get90Percentile(){
		
		 return 0;
	//	 JOptionPane.showMessageDialog(null,doubleList);
		
	}
public double get95Percentile(){
	
	 return 0;
//	 JOptionPane.showMessageDialog(null,doubleList);
	
}
public double get50Percentile(){
	
	 return 0;
//	 JOptionPane.showMessageDialog(null,doubleList);
	
}
public double getAverageRT(){
	
	return 0;
}
	public XYSeries getSeries(){// for comparison 
		return new XYSeries("");
			
		}
	public void setWorkloadForBins( Vector workloadForBins){
		this.workloadForBins=workloadForBins;
	}
	
	public ResponsePanel(NonblockingCounter nonBlockingCounter,NonblockingCounter tracerForCompareButton,SharedBoolean sharedBoolean, String strategyName, BooleanChecker sleepTime) {
		this.strategyName=strategyName;
		this.tracerForCompareButton=tracerForCompareButton;
		this.sharedBoolean=sharedBoolean;
		this.nonBlockingCounter=nonBlockingCounter;
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(500,205));
		
		 flowPanel=new JPanel(new FlowLayout());	
		JPanel centrePanel=new JPanel();
		centrePanel.setLayout(new BorderLayout());
		centrePanel.add(flowPanel, BorderLayout.CENTER);
		JScrollPane scrollerForRTGraph=new JScrollPane(centrePanel);
		scrollerForRTGraph.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollerForRTGraph.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.add(scrollerForRTGraph,BorderLayout.CENTER);

		this.dataGenerator=new DataUpdater(getQueue());
		this.sleepTime=sleepTime;

	}
	
	public void preparegraphics(){
		//chartPanel.setPreferredSize(new java.awt.Dimension(800,00));
				for(int i=0;i<this.workloadForBins.size();i++){
				//	JOptionPane.showMessageDialog(null, "Hi");
					int lowerBound=(int) this.workloadForBins.elementAt(i);
					String title="";
					switch(lowerBound){
					case 1:title+="Low CPU-Bound Jobs";break;
					case 2:title+="High CPU-Bound Jobs";break;
					case 3:title+="V.High CPU-Bound Jobs";break;//this is xcluded from DynamicWorkloadSpecificationpanel
					case 100: title+="1kb file";break;
					case 200:title+="10kb file";break;
					case 300:title+="100kb file";break;
					case 400:title+="1000kb file";break;
					case 1000:title+="1000kb file";break;
					case 2000:title+="2000kb file";break;
					}
					graphs.add(new HistogramGraph(title+" Response Times ",lowerBound)) ;
					flowPanel.add((HistogramGraph)graphs.elementAt(i));
				}
	}
 
	public void startResponsePanel(){
		this.dataGenerator.start();

		
	}
	public void stopResponsePanel(){
		this.stopThread=true;

		
	}
	public boolean getStopValue(){
		return this.stopThread;
	}
	public Vector getResponseGraphsResult(){
			Vector results=new Vector(3);
			Iterator it=graphs.iterator();
	    	while(it.hasNext()){
	    		 	HistogramGraph hgg=(HistogramGraph)it.next();
	    		 	Vector v=new Vector(5);
	    		 	v.add(""+hgg.getLowerBound());//the name of graph 
	    		 	v.add(hgg.getNthPercentileBin(50.0));
	    		 	v.add(hgg.getNthPercentileBin(90.0));
	    		 //	v.add(hgg.getNthPercentileBin(95.0));
	    		 	results.add(v);
	    	}
	    	return results;
	    }
	
		public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/*public int getTotalResponsesinResponsePanelGraphs(){
		int count=0;
		Iterator it=graphs.iterator();
    	while(it.hasNext()){
    		count+=((HistogramGraph)it.next()).getTotalItems();
    	}
    	return count;
	}*/

	 class DataUpdater extends Thread{//implements Runnable{

		    private final BlockingQueue sharedQueue;

		    public DataUpdater (BlockingQueue sharedQueue) {
		        this.sharedQueue = sharedQueue;
		    }
		   
		  
		    @Override
		    public void run() {
		        while(true){
		            try {
       	
		            	Runnable jobx=(Runnable)sharedQueue.poll(3000L,TimeUnit.MILLISECONDS);//take method of BlockingQueue blocks until an object is available so i used poll which waits for specified time(3 sec) until an object is available and if not available in specified time then it return null object
		           
		            	if(jobx!=null){
		            		//counter++;
		            		Task pj=(Task) jobx;
		            		Iterator it=graphs.iterator();
		            	while(it.hasNext()){
		            		HistogramGraph hgraph=(HistogramGraph)it.next();
		            		if(hgraph.getLowerBound()==(int)pj.getDelay()){
		            			double rt=0.0;
		            			if(pj instanceof IoBoundTask){ 
		            				rt=pj.getResponseTimeOfStaticReq();
		            			}
		            			else if(pj instanceof CpuBoundTask){
		            				rt=pj.getResponseTimeOfDynamicReq();
		            			}
		            		//	hgraph.addResponseinGraph(pj.getResponseTime());
		            			hgraph.addResponseinGraph(rt);
		            			break;
		            		}
		            		else continue;
		            		
		            	}
		            		}
		            	
		            	 if(stopThread)// && sharedQueue.isEmpty())//stopThread signals from RecieverX and RecieverX increments  nonBlockingCounter on every readObject so when all readObjects have been plotted then stop this ResponsePanel
	            		{
		            	try{
		            		            	
	            		sharedBoolean.setStopValue(true);
	            		tracerForCompareButton.increment();
	            	//	System.out.println("Total Responses in R.Panel="+counter);
		            	}catch(Exception e){
		            		//JOptionPane.showMessageDialog(null, "Exception in RespnsePanel");
		            		System.err.print(e);
		            	}
	            		break;
	            		}	
		           // if(sleepTime.isSend())	Thread.sleep(15);
		            } 
		            catch (InterruptedException ex) {
		            	System.out.println("Exception in ResponsePanel xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		            	ex.printStackTrace();
		            }
		            catch(Exception e){
		            	System.out.println("Exception in ResponsePanel xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		            	e.printStackTrace();
		            	}
		        }
		        
		    }
	 
	
}
	 class HistogramGraph extends JPanel{
		 private SimpleHistogramDataset dataset;
		 
		 public SimpleHistogramDataset getDataset() {
			return dataset;
		}
		public Vector getHistogramBins() {
			return histogramBins;
		}
		private Vector<SimpleHistogramBin> histogramBins=new Vector(6); 
		 private String title;
	//	 private String domainXisLabel="";
		 private int lowerBound;
		 private int upperBound=48;//upperBound specifies last bin e.g. for 100ms lower bound its upperBound would be 130
		 HistogramGraph(String title,int lowerBound){
			 this.title=title;
			 this.lowerBound=lowerBound;
			 this.dataset = new SimpleHistogramDataset("Key");
		     dataset.setAdjustForBinSize(false);//The adjustForBinSize flag controls whether or not the bin count is divided by the bin size (width) when returning the y-value for the dataset:

			if( this.lowerBound<=3){// i.e. if it is 1 or 2 or 3 [for dynamic contents]
				SimpleHistogramBin bin1,bin2,bin3;
				switch (this.lowerBound){
						case 1: bin1=new SimpleHistogramBin(10,30, true, false);
								bin2=new SimpleHistogramBin(30,50, true, false);
								bin3=new SimpleHistogramBin(50,70, true, false);
								histogramBins.add(bin1);histogramBins.add(bin2);histogramBins.add(bin3);
								dataset.addBin(bin1);dataset.addBin(bin2);dataset.addBin(bin3);
								break;						
						case 2:/*bin1=new SimpleHistogramBin(30,60, true, false);
								bin2=new SimpleHistogramBin(60,80, true, false);
								bin3=new SimpleHistogramBin(80,100, true, false); */
								bin1=new SimpleHistogramBin(70,90, true, false);
								bin2=new SimpleHistogramBin(90,110, true, false);
								bin3=new SimpleHistogramBin(110,130, true, false); 
								histogramBins.add(bin1);histogramBins.add(bin2);histogramBins.add(bin3);
								dataset.addBin(bin1);dataset.addBin(bin2);dataset.addBin(bin3);
								break;
						case 3: bin1=new SimpleHistogramBin(50,80, true, false);
								bin2=new SimpleHistogramBin(80,100, true, false);
								bin3=new SimpleHistogramBin(100,130, true, false); 
								histogramBins.add(bin1);histogramBins.add(bin2);histogramBins.add(bin3);
								dataset.addBin(bin1);dataset.addBin(bin2);dataset.addBin(bin3);
								break;
				}
				
			}
			else{
				String test="";
				test+=lowerBound-5+" "+lowerBound;
		     //first add a bin explicitly 
		//	 histogramBins.add(new SimpleHistogramBin(lowerBound-5,lowerBound+1, true, false));//e.g. lowerBound is 100ms then it would be 95 to 101
			 histogramBins.add(new SimpleHistogramBin(lowerBound-5,lowerBound));//e.g. lowerBound is 100ms then it would be 95 to 101

			 //	 domainXisLabel+=lowerBound-5+"  "+lowerBound+1;
			 dataset.addBin((SimpleHistogramBin)histogramBins.elementAt(0));
			 // now add 5 more bins
			 int binWidth=5;
			 int initial=1;
			 int lowerBound1=lowerBound+1;
			 int upperBound2=lowerBound+6;
			 for(int i=1;i<=8;i++){// create 5 more  bins dynamically and store in a vector
			//	 histogramBins.add(new SimpleHistogramBin(((lowerBound+1)+binWidth),((lowerBound+1)+binWidth+6), true, false));//e.g. lowerBound is 100ms then it would be 95 to 101
				 histogramBins.add(new SimpleHistogramBin((lowerBound1),(upperBound2)));//e.g. lowerBound is 100ms then it would be 95 to 101
				 test+="\n"+lowerBound1+" "+upperBound2;
				 //	 domainXisLabel+="  "+((lowerBound+1)+binWidth)+"  "+((lowerBound+1)+binWidth+6);
				 dataset.addBin((SimpleHistogramBin)histogramBins.elementAt(i));
				 initial=binWidth+2;//7
				 lowerBound1=upperBound2+1;//7
				 upperBound2+=6;
				// binWidth+=5;//
			 }
		//	 JOptionPane.showMessageDialog(null,test);
			}
			 setPreferredSize(new Dimension(200,150));
			 
			 JFreeChart chart = ChartFactory.createHistogram(title, 
		                "Response Time Buckets(ms)", "Frequency", dataset, PlotOrientation.VERTICAL, 
		                false, false, false);
		        FontSetting fontSetting=new FontSetting(12,10,10); 

			 chart.setTitle(fontSetting.getSmallTextTitle(title));
		        XYPlot plot = (XYPlot) chart.getPlot();
		        plot.setDomainZeroBaselineVisible(false);
		        plot.getDomainAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		        plot.getDomainAxis().setTickLabelFont(fontSetting.getTickFont());
		        plot.getDomainAxis().setLabelFont(fontSetting.getLabelFont());
		    //    plot.getDomainAxis().setVisible(false);
		   /*     chart.addSubtitle(new TextTitle(domainXisLabel,
						fontSetting.getLabelFont(), Color.black,
		        	    RectangleEdge.BOTTOM, HorizontalAlignment.CENTER,
		        	    VerticalAlignment.BOTTOM, RectangleInsets.ZERO_INSETS));*/
		        
		        plot.getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		        plot.getRangeAxis().setTickLabelFont(fontSetting.getTickFont());
		        plot.getRangeAxis().setLabelFont(fontSetting.getLabelFont());
		        plot.setBackgroundPaint(new Color(0xb8cfe5));
		        plot.setDomainGridlinesVisible(true);
		        plot.setRangeGridlinesVisible(true);
		        plot.setDomainGridlinePaint(Color.WHITE);
		        plot.setRangeGridlinePaint(Color.WHITE);

		        // set up the UI
		        ChartPanel panel = new ChartPanel(chart);
		    //    panel.setPreferredSize(new Dimension(200,150));
		        setLayout(new BorderLayout());
		        add(panel);
		 }
		public String getTitle() {
			return title;
		}
		public int getLowerBound() {
			return lowerBound;
		}
		public void addResponseinGraph(double responseTime){
			if(this.lowerBound<=3){// ie if it is 1 or 2 or 3 i.e. for dynamic contents.... and responsetime is exceding last bin range then put it in the last bin by force
			switch(this.lowerBound){
			case 1:
				if(responseTime>=70){//69
					this.dataset.addObservation(69);
				}
				else if(responseTime<10){//69
					this.dataset.addObservation(10);
				}
				else this.dataset.addObservation(responseTime);
				break;

			case 2:
				/*bin1=new SimpleHistogramBin(30,60, true, false);
				bin2=new SimpleHistogramBin(60,80, true, false);
				bin3=new SimpleHistogramBin(80,100, true, false); */
				if(responseTime>=130){//99
					this.dataset.addObservation(129);
				}
				else if(responseTime<70){//99
					this.dataset.addObservation(70);
				}
				else this.dataset.addObservation(responseTime); 
				break;
			case 3:
				if(responseTime>=130){//129
					this.dataset.addObservation(129);
				}
				else if(responseTime<50){//129
					this.dataset.addObservation(50);
				}
				else this.dataset.addObservation(responseTime); 
				break;
			}	
			}
			else{
			if(responseTime>=lowerBound-5 && responseTime<=this.lowerBound+upperBound)// e.g for 100ms it is 100+30=130 which is the upper bound of the last bin
			 this.dataset.addObservation(responseTime);
			else this.dataset.addObservation(this.lowerBound+upperBound-1);// don not drop the responses above upper bound but just add to the last bin.
			}
			}
		
		public int getTotalItems(){
			int count=0;
			Iterator it=histogramBins.iterator();
	    	while(it.hasNext()){
	    		SimpleHistogramBin bin=(SimpleHistogramBin)it.next();
	    		count+=bin.getItemCount();
	    	}
			
			//return this.dataset.getItemCount(0);// 
	    	return count;
		}	 
		/*
		1. Define any random number of response time buckets (e.g. 0ms – 100ms, 100ms – 200ms, 200ms – 400ms, 400ms – 800ms,800ms – 1200ms, …)
		2.Count number of responses and number of response each bucket (For a response time of 360ms, increment the counter for the 200ms – 400ms bucket)
		3.Estimate the n-th percentile by summing counter for buckets until the sum exceeds n percent of the total
		 */
		public String getNthPercentileBin(double n){
			double exceeds=(n/100)*getTotalItems();//n percent of the total
			
			double sum=0.0;
			boolean exceeded=false;
			String binName="";
			do{
			
			Iterator it=histogramBins.iterator();
	    	while(it.hasNext()){
	    		SimpleHistogramBin bin=(SimpleHistogramBin)it.next();
	    		sum+=(double)bin.getItemCount();
	    		if(sum>exceeds){
	    			exceeded=true;
	    			binName+=((int)bin.getLowerBound())+"-"+((int)bin.getUpperBound());
	    			break;
	    		}
	    	}
			}while(exceeded==false);
			
			return binName;
			
		}
		
	 }

	
}
