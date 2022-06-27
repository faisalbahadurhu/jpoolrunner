package com.jpoolrunner.clientside;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.swing.border.EtchedBorder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.jpoolrunner.job.Task;

public class AcceptanceLatencyPanel extends Consumer {

	NonblockingCounter throughput;
	DataUpdater dataGenerator;
	XYDataset dataset;
	XYSeries series1 ;
	boolean stopThread=false;//to stop datagenerator thread
	NonblockingCounter nonBlockingCounter;
	NonblockingCounter tracerForCompareButton;// came from scenario .All panels increment it only once b4 stopping and since there are 4 Graphics panels so when its value is four then it means that all panels have been stopped and compare button should be enabled now

	SharedBoolean sharedBoolean;//this boolean would be true only in ResponsePanel when ResponsePanel stops it on drawing all readoObjects by ReciverX and then ThroughputPanel etc would be stopped 
	String strategyName;
public XYSeries getSeries(){// for comparison 
	return this.series1;
	}
JFreeChart chart;
public JFreeChart getChart(){return this.chart;}
	public AcceptanceLatencyPanel(NonblockingCounter nonBlockingCounter,NonblockingCounter tracerForCompareButton,SharedBoolean sharedBoolean, String strategyName) {
		this.tracerForCompareButton=tracerForCompareButton;
		this.strategyName=strategyName;
		this.sharedBoolean=sharedBoolean;
		this.nonBlockingCounter=nonBlockingCounter;
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		dataset = createDataset();
		chart = createChart(dataset);
		chart.removeLegend();
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500,205));//width height
		//chartPanel.setPreferredSize(new java.awt.Dimension(800,00));
		this.add(chartPanel,BorderLayout.CENTER);
		
		
		//this.setBackground(new Color(0xEEEEE));
		
		
		
		this.dataGenerator=new DataUpdater(getQueue());//get the Q from Consumer in which resposes reside
		//panel.startSendingRequest();

	}
	public void startAcceptanceLatencyPanel(){
		this.dataGenerator.start();

		
	}
	public void stopAcceptanceLatencyPanel(){
		this.stopThread=true;

		
	}
	public boolean getStopValue(){
		return this.stopThread;
	}
	private  XYDataset createDataset() {
		 series1 = new XYSeries(this.strategyName);

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);

		return dataset;
		}
	  private JFreeChart createChart(final XYDataset dataset) {
	        final JFreeChart result = ChartFactory.createXYLineChart(
	            null,
	            "Responses/Jobs",
	            "Latency(millisec)",
	            dataset,
	            PlotOrientation.VERTICAL,
	            true,
	            true,
	            false
	        );
	        
	        FontSetting fontSetting=new FontSetting(); 
          result.setTitle(fontSetting.getTextTitle("Acceptance Latency"));
	        final XYPlot plot = result.getXYPlot();
	        result.setBackgroundPaint(Color.WHITE);
	        plot.setBackgroundPaint(new Color(0xb8cfe5));
	        //  plot.setRangeGridlinesVisible(false);
			 //  plot.setRangeGridlinesVisible(false);

		        plot.setDomainGridlinesVisible(true);
		        plot.setRangeGridlinesVisible(true);


		        plot.setRangeGridlinePaint(Color.WHITE); 
		        plot.setDomainGridlinePaint(Color.WHITE);
	     
	                
	     /*   ValueAxis xaxis = plot.getDomainAxis();
	        DateAxis axis  = (DateAxis)xaxis;
	        axis.setDateFormatOverride(new SimpleDateFormat("mm:ss"));
	       
	        xaxis.setTickLabelFont(fontSetting.getTickFont());
	        xaxis.setLabelFont(fontSetting.getLabelFont());
	        xaxis.setAutoRange(true);
	     
	        //Domain axis would show data of 2minutes for a time(60000 means 60 sec 120000 means 120 sec 180000 means 180 sec and so on)
	        xaxis.setFixedAutoRange(60000*this.timeForGraphDataShow);  // for 5minutes..use 60000*5 seconds
	        xaxis.setVerticalTickLabels(true);
	     
	         final XYPlot plot2 = (XYPlot)result.getPlot();
	        NumberAxis range = (NumberAxis) plot2.getRangeAxis();
	        range.setTickLabelFont(fontSetting.getTickFont());
	        range.setLabelFont(fontSetting.getLabelFont());
	      //  range.setRange(10,50);
	        range.setAutoRange(true);
	     //   range.setTickUnit(new NumberTickUnit(50));
	        
	        
	      //  yaxis.setra
	       * */
	        XYLineAndShapeRenderer renderer
	    	= (XYLineAndShapeRenderer) plot.getRenderer();
	    	renderer.setShapesVisible(true);
	    	renderer.setShapesFilled(true);
	    	//following line ....change size of marker or data point
	    	 renderer.setSeriesPaint(0, Color.blue);
	    	    double size = 2.0;
	    	    double delta = size / 2.0;
	    	    Shape shape1 = new Rectangle2D.Double(-delta, -delta, size, size);
	    	    renderer.setSeriesShape(0, shape1);
	    	    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	    		//rangeAxis.setRange(10,100);
	    	   // rangeAxis.setRange(0.0,5.0);
	    	    rangeAxis.setTickLabelFont(fontSetting.getTickFont());
		        rangeAxis.setLabelFont(fontSetting.getLabelFont());
	    	    //rangeAxis.setTickUnit(new NumberTickUnit(0.25));
	    		rangeAxis.setAutoRange(true);
	    		//rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	    		ValueAxis xaxis = plot.getDomainAxis();
	    		 xaxis.setTickLabelFont(fontSetting.getTickFont());
	    		// xaxis.setLowerBound(1.0);
	    		 xaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	 	        xaxis.setLabelFont(fontSetting.getLabelFont());
	 	        xaxis.setAutoRange(true);
	       
	        return result;
	    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
		               // System.out.println("Consumed: "+ sharedQueue.take());
		            	/*
		            	IoBoundTask jobx=(IoBoundTask)sharedQueue.take();
		            	double acceptancelatency=(double)(jobx.getNanoEnQueueTime()-jobx.getNanoReciveTime());//.getSendTime()-jobx.getReciveTime();
		           
		            	series1.add(counter+=1,(double)acceptancelatency/1000000);//1 millisec =10,00000 nanosec so devide by 1000000 to get milliseconds
		            	
		            	
		            	if((stopThread) && (counter==nonBlockingCounter.getValue()))//stopThread signals from RecieverX and RecieverX increments  nonBlockingCounter on every readObject so when all readObjects have been plotted then stop this ResponsePanel
		            		{
		            		sharedBoolean.setStopValue(true);
		            		tracerForCompareButton.increment();

		            		System.out.println("AcceptanceLatencyPanel exiting");
		            		break;
		            		}*/
		            	Runnable jobx=(Runnable)sharedQueue.poll(3000L,TimeUnit.MILLISECONDS);//take method of BlockingQueue blocks until an object is available so i used poll which waits for specified time(3 sec) until an object is available and if not available in specified time then it return null object
		            	//jobx=(CpuBoundTask)jobx;
		            	
		            	
		            	
		            	if(jobx!=null){
		            		Task pj=(Task) jobx;
		            		//double acceptancelatency=(double)(pj.getNanoEnQueueTime()-pj.getNanoReciveTime());//.getSendTime()-jobx.getReciveTime();
		            	//	long acceptancelatency=(pj.getNanoEnQueueTime()-pj.getNanoReciveTime());//.getSendTime()-jobx.getReciveTime();

		            		series1.add(counter+=1,pj.getAcceptanceLatency());//1 millisec =10,00000 nanosec so devide by 1000000 to get milliseconds
		            		//series1.add(counter+=1,(double)acceptancelatency/1000000);//1 millisec =10,00000 nanosec so devide by 1000000 to get milliseconds

			            	}
		            	else if((stopThread) && sharedQueue.isEmpty())//stopThread signals from RecieverX and RecieverX increments  nonBlockingCounter on every readObject so when all readObjects have been plotted then stop this ResponsePanel
		            		{
		            		sharedBoolean.setStopValue(true);
		            		tracerForCompareButton.increment();

		            	//////////////////	System.out.println("AcceptanceLatencyPanel exiting");
		            		break;
		            		}
		            	
		            	Thread.sleep(15);
		            } catch (InterruptedException ex) {
		               // Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
		            }
		            catch(Exception e){System.out.println("Exception in AcceptanceLatencyPanel");}

		        }
		    }
	 
	
}
}

