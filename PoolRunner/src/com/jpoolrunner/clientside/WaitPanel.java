package com.jpoolrunner.clientside;

import java.awt.BorderLayout;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.LinkedList;
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
import org.jfree.data.statistics.SimpleHistogramBin;
import org.jfree.data.statistics.SimpleHistogramDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.jpoolrunner.job.Task;

public class WaitPanel extends Consumer {
	double timeForGraphDataShow=3;// time to show data on the graph ...1 minute(60000*1)
	//private TimeSeries throughputSeries;//the line graph to b plott
	//NonblockingCounter throughput;
	DataUpdater dataGenerator;
	boolean stopThread=false;//to stop datagenerator thread
	NonblockingCounter nonBlockingCounter;
	NonblockingCounter tracerForCompareButton;// came from scenario .All panels increment it only once b4 stopping and since there are 4 Graphics panels so when its value is four then it means that all panels have been stopped and compare button should be enabled now
//Vector totalJobs=new Vector();
 JFreeChart chart;
 BooleanChecker sleepTime;
	Vector workloadForBins;
	Vector graphs=new Vector(3);
	JPanel flowPanel;
public JFreeChart getChart(){return this.chart;}
	/*public Vector getTotalJobs() {
	return totalJobs;
}*/
public Vector getWaitTimeGraphs(){ return this.graphs;}

	SharedBoolean sharedBoolean;//this boolean would be true only in ResponsePanel when ResponsePanel stops it on drawing all readoObjects by ReciverX and then ThroughputPanel etc would be stopped 
	private String strategyName;

	public XYSeries getSeries(){// for comparison 
		return new XYSeries("");

		//return this.series1;
		}
	public WaitPanel(NonblockingCounter nonBlockingCounter,NonblockingCounter tracerForCompareButton,SharedBoolean sharedBoolean, String strategyName, BooleanChecker sleepTime) {
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
	public void setWorkloadForBins( Vector workloadForBins){
		this.workloadForBins=workloadForBins;
	}
	public void preparegraphics(){
		//chartPanel.setPreferredSize(new java.awt.Dimension(800,00));
				for(int i=0;i<this.workloadForBins.size();i++){
				//	JOptionPane.showMessageDialog(null, "Hi g");
					int lowerBound=(int) this.workloadForBins.elementAt(i);
				//	JOptionPane.showMessageDialog(null, "Hi g lowerbound="+lowerBound);

					String title="";
					switch(lowerBound){
				/*	case 1:title+="Low CPU-Bound Jobs";break;
					case 2:title+="High CPU-Bound Jobs";break;
					case 3:title+="V.High CPU-Bound Jobs";break;
					case 100: title+="1kb file";break;
					case 200:title+="10kb file";break;
					case 300:title+="100kb file";break;
					case 400:title+="1000kb file";break;
					case 2000:title+="2000kb file";break;
					case 1000:title+="1000kb file";break;*/

					
					case 1:title+="Low CPU-Bound Jobs";break;
					case 2:title+="High CPU-Bound Jobs";break;
					case 3:title+="V.High CPU-Bound Jobs";break;//this is xcluded from DynamicWorkloadSpecificationpanel
					case 100: title+="1kb file";break;
					case 200:title+="10kb file";break;
					case 300:title+="100kb file";break;
					case 400:title+="1000kb file";break;
					case 500:title+="1000kb file";break;
					case 600:title+="1000kb file";break;
					case 700:title+="1000kb file";break;
					case 800:title+="1000kb file";break;
					case 900:title+="1000kb file";break;
					case 1000:title+="1200kb file";break;
					case 2000:title+="2000kb file";break;
					
					
					}
					graphs.add(new HistogramGraph2(title+" Wait Times ",lowerBound) ) ;
					flowPanel.add((HistogramGraph2)graphs.elementAt(i));
				}
	}
	public int getTotalResponsesinResponsePanelGraphs(){
		int count=0;
		Iterator it=graphs.iterator();
    	while(it.hasNext()){
    		count+=((HistogramGraph2)it.next()).getTotalItems();
    	}
    	return count;
	}

	public void startWaitPanel(){
		this.dataGenerator.start();

		
	}
	public void stopWaitPanel(){
		this.stopThread=true;

		
	}
	public boolean getStopValue(){
		return this.stopThread;
	}
	public Vector getWaitGraphsResult(){
		Vector results=new Vector(3);
		Iterator it=graphs.iterator();
    	while(it.hasNext()){
    		 	HistogramGraph2 hgg=(HistogramGraph2)it.next();
    		 	Vector v=new Vector(5);
    		 	v.add(""+hgg.getLowerBound());//the name of graph 100 200 300 400 500 1 5 10 etc
    		 	v.add(hgg.getNthPercentileBin(50.0));
    		 	v.add(hgg.getNthPercentileBin(90.0));
    		 //	v.add(hgg.getNthPercentileBin(95.0));
    		 	results.add(v);
    	}
    	return results;
    }

	 class DataUpdater extends Thread{//implements Runnable{

		    private final BlockingQueue sharedQueue;

		    public DataUpdater (BlockingQueue sharedQueue) {
		        this.sharedQueue = sharedQueue;
		    }
		  
		    @Override
		    public void run() {
		    	int counter=0;
		        while(true){
		            try {
		           
		            	
		            	Runnable jobx=(Runnable)sharedQueue.poll(3000L,TimeUnit.MILLISECONDS);//take method of BlockingQueue blocks until an object is available so i used poll which waits for specified time(3 sec) until an object is available and if not available in specified time then it return null object
		           
		            	if(jobx!=null){
		            		Task pj=(Task) jobx;
		            		Iterator it=graphs.iterator();
			            	while(it.hasNext()){
			            		HistogramGraph2 hgraph=(HistogramGraph2)it.next();
			            		if(hgraph.getLowerBound()==(int)pj.getDelay()){
			            			hgraph.addWaitsinGraph(pj.getTotalTATWait());
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
		            	
		            		}catch(Exception e){
			            		JOptionPane.showMessageDialog(null, "Exception in RespnsePanel");
			            	}
		            	
		            		break;
		            		}
		           
		            } 
		            catch (InterruptedException ex) {
		            	System.out.println("Exception in WaitPanel   xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		            	ex.printStackTrace();
		            }
		            catch(Exception e){
		            	System.out.println("Exception in WaitPanel    xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		            	e.printStackTrace();
		            	}
		        }
		        
		    }
	 
	
}
	 class HistogramGraph2 extends JPanel{
		 private SimpleHistogramDataset dataset;
		 public SimpleHistogramDataset getDataset() {
			return dataset;
		}
		public Vector getHistogramBins() {
			return histogramBins;
		}
		private Vector histogramBins=new Vector(6); 
		 private String title;
		 private int lowerBound;
		 private int upperBound=30;//upperBound specifies last bin e.g. for 100ms lower bound its upperBound would be 130
		 HistogramGraph2(String title,int lowerBound){
			 this.title=title;
			 this.lowerBound=lowerBound;
			 this.dataset = new SimpleHistogramDataset("Key");
		     dataset.setAdjustForBinSize(false);//The adjustForBinSize flag controls whether or not the bin count is divided by the bin size (width) when returning the y-value for the dataset:

		     //first add a bin explicitly 
			 histogramBins.add(new SimpleHistogramBin(-1,5, true, false));//e.g. -1 to 5
			 dataset.addBin((SimpleHistogramBin)histogramBins.elementAt(0));
			 
			 int binWidth=5;
			 
			 for(int i=1;i<=9;i++){// create 9 more  bins dynamically and store in a vector
				 histogramBins.add(new SimpleHistogramBin(binWidth,binWidth+5, true, false));//e.g. 5 10,10 15,15 20 and so on
				 dataset.addBin((SimpleHistogramBin)histogramBins.elementAt(i));
				 binWidth+=5;
			 }
			 setPreferredSize(new Dimension(200,150));
			 
			 JFreeChart chart = ChartFactory.createHistogram(title, 
		                "Wait-Time Buckets(ms)", "Frequency", dataset, PlotOrientation.VERTICAL, 
		                false, false, false);
			 FontSetting fontSetting=new FontSetting(12,10,10); 

			 chart.setTitle(fontSetting.getSmallTextTitle(title));
		        XYPlot plot = (XYPlot) chart.getPlot();
		        plot.setDomainZeroBaselineVisible(false);
		        plot.getDomainAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		        plot.getDomainAxis().setTickLabelFont(fontSetting.getTickFont());
		        plot.getDomainAxis().setLabelFont(fontSetting.getLabelFont());
		        
		        plot.getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		        plot.getRangeAxis().setTickLabelFont(fontSetting.getTickFont());
		        plot.getRangeAxis().setLabelFont(fontSetting.getLabelFont());
		        plot.setBackgroundPaint(new Color(0xb8cfe5));
		        plot.setDomainGridlinesVisible(true);
		        plot.setRangeGridlinesVisible(true);
		        plot.setDomainGridlinePaint(Color.WHITE);
		        plot.setRangeGridlinePaint(Color.WHITE);
			 
		        ChartPanel panel = new ChartPanel(chart);
		        setLayout(new BorderLayout());
		        add(panel);
		 }
		public String getTitle() {
			return title;
		}
		public int getLowerBound() {
			return lowerBound;
		}
		public void addWaitsinGraph(double waitTime){
			try{
			if(waitTime>=0 && waitTime< 50)// e.g for 100ms it is 100+30=130 which is the upper bound of the last bin
			 this.dataset.addObservation(waitTime);
			else if(waitTime>=50) this.dataset.addObservation(49.0);// don not drop the responses above upper bound but just add to the last bin.
			else this.dataset.addObservation(0);
			}catch(Exception e){System.out.println(" No Bin for "+waitTime);}
			
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
	    			if(bin.getLowerBound()<0) binName+=(0+"-"+((int)bin.getUpperBound()));// since we made the 1st bin -1 to 5 so that 0ms can be captured because addobservation dnt work for 0 to 5 for 0mswaits i.e. if i keep the bin 0 to 5 then 0 dont fall in this bin so i made it -1 to 5 so that 0 can be fall in
	    			else binName+=((int)bin.getLowerBound())+"-"+((int)bin.getUpperBound());
	    			
	    			break;
	    		}
	    	}
			}while(exceeded==false);
			
			return binName;
			
		}		
	 }
public double getTotalWait(){
			
				return 0;
			} 
public double getAverageWait(){
		
			return 0;
		}
	
}
