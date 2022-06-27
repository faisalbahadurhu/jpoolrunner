package com.jpoolrunner.clientside;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;

import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class PoolSizePanel extends Consumer{

	double timeForGraphDataShow=3;// time to show data on the graph ...1 minute(60000*1)
	private TimeSeries throughputSeries;//the line graph to b plott
//	NonblockingCounter throughput;
	DataUpdater dataGenerator;
	XYDataset dataset;
	XYSeries series1 ;
	int counter;
	String strategyName;
	boolean stop=false;
	SharedBoolean sharedBoolean;//this boolean would be true only in ResponsePanel when ResponsePanel stops it on drawing all readoObjects by ReciverX and then ThroughputPanel etc would be stopped 
	NonblockingCounter tracerForCompareButton;// came from scenario .All panels increment it only once b4 stopping and since there are 4 Graphics panels so when its value is four then it means that all panels have been stopped and compare button should be enabled now
	 PoolSizeGetter poolSizer;//it is updated in RecieverX and it is shared in only this class and RecieverX
	public XYSeries getSeries(){// for comparison 
		return this.series1;
		}
	JFreeChart chart;
	public JFreeChart getChart(){return this.chart;}
	public PoolSizePanel(NonblockingCounter tracerForCompareButton,SharedBoolean sharedBoolean, PoolSizeGetter poolSizer, String strategyName){
		this.tracerForCompareButton=tracerForCompareButton;
		this.sharedBoolean=sharedBoolean;
        this.poolSizer=poolSizer;
        this.strategyName=strategyName;
		setPreferredSize(new Dimension(100,205));
		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		//super(new BorderLayout());
		//this.throughput= throughput;
		counter=0;
		
		//throughputSeries = new TimeSeries("Throughput per second", Second.class);
	//	TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset = createDataset();
		//dataset.addSeries(throughputSeries);
		 chart = createChart(dataset);
		 chart = createChart(dataset);
		chart.removeLegend();
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500,205));//width height
		//chartPanel.setPreferredSize(new java.awt.Dimension(800,00));
		this.add(chartPanel,BorderLayout.CENTER);
		
		
		//this.setBackground(new Color(0xEEEEE));
		
		
		
		this.dataGenerator=new DataUpdater(1000);
		//panel.startSendingRequest();

	}
	public void setInitialThreadPoolSize(int size){
		this.series1.add(0,size);
	}
	private  XYDataset createDataset() {
		 series1 = new XYSeries(this.strategyName);

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);

		return dataset;
		}
	public void startPoolSizePanel(){
		this.dataGenerator.start();//run after every second

		
	}

	public void stopPoolSizePanel(){
		this.stop=true;
		//this.dataGenerator.stop();//run after every second

		
	}
	public double getAveragePoolSize(){
		LinkedList<Double> doubleList = new LinkedList<Double>();

		 for(Object o: series1.getItems()) {
	        XYDataItem xydi = (XYDataItem)o;
	        doubleList.add(xydi.getY().doubleValue());
	     //   s+="\n"+curY;
	        
	        
	        }
		double sum =0;
		for(double x:doubleList){
		sum=sum+x;
		}
		return(sum/(doubleList.size()));
	}	
	public boolean getStopValue(){
		return this.stop;
	}
		
		/*
		 * *
		 * @param args
		 
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			JFrame frame = new JFrame("Sending Frequency demo");
			frame.setBackground(new Color(0xEEEEE));
		//	RequestfrequencyPanel panel = new RequestfrequencyPanel();
			
		//	panel.new DataGenerator(1000).start();//run after every second
		//	panel.startSendingRequest();
			frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
			System.exit(0);
			}
			});

		}*/
		class DataUpdater extends Timer implements ActionListener {
			/**
			* Constructor.
			*
			* @param interval the interval (in milliseconds)
			*/
			DataUpdater(int interval) {
			super(interval, null);
			this.setInitialDelay(250);//start after 250 msec coz minimum sleep time of simulation is 250
			addActionListener(this);
			}
			/**
			* Adds a new free/total memory reading to the dataset.
			*
			* @param event the action event.
			*/
			@Override
			public void actionPerformed(ActionEvent event) {
			//long f = Runtime.getRuntime().freeMemory();
			//long t = Runtime.getRuntime().totalMemory();
			//addTotalObservation(t);
			//addFreeObservation(f);
				//throughputSeries.add(new Millisecond(),throughput.getValue());	
				//throughputSeries.add(new Second(),throughput.getValue());
				//IoBoundTask jobx=(IoBoundTask)getQueue().take();
				int size=0;
				int poolSize=poolSizer.getPoolSize();
				if(poolSize>0){
					size=poolSize;
					poolSizer.setPoolSize(0);
					series1.add(counter,size);

				}
				counter++;

				
				//series1.add(new Second(),size);	

			//	if(stop ) {
					if(stop && sharedBoolean.getStopValue()) {

					tracerForCompareButton.increment();
				////////////////	System.out.println("PoolSizePanel exiting");
					this.stop();// ie if stop is true only when RecieverX will recieve stop signa from Responsesender on server side and a ClassCastException in Reciever will make this stop true and sharedBoolean is true only when ResponsePanel make it true when ResponsePanel plots all readObjects
					

				}
			}
	}

		  private JFreeChart createChart(final XYDataset dataset) {
		        final JFreeChart result = ChartFactory.createXYLineChart(
		            null,
		            "Time(sec)",
		            "Size(Number of Threads)",
		            dataset,
		            PlotOrientation.VERTICAL,
		            true,
		            true,
		            false
		        );
		        
		        FontSetting fontSetting=new FontSetting(); 
	          //  TextTitle my_Chart_title=new TextTitle("Dynamic Request Frequencies", new Font ("Verdana", Font.BOLD , 15));
	            result.setTitle(fontSetting.getTextTitle("Pool Size: "+strategyName));


	            final XYPlot plot = result.getXYPlot();
		     //   CategoryPlot plotX = (CategoryPlot) result.getPlot();
		       // plot2.setBackgroundPaint(Color.lightGray);
		      //  plotX.setRangeGridlinePaint(Color.white);
		    //    LineAndShapeRenderer renderer = (LineAndShapeRenderer) plotX.getRenderer(); renderer.setShapesVisible(true);
		    //    renderer.setDrawOutlines(true); renderer.setUseFillPaint(true);
		       result.setBackgroundPaint(Color.WHITE);
		        plot.setBackgroundPaint(new Color(0xb8cfe5));
		          plot.setRangeGridlinesVisible(false);
				  plot.setDomainGridlinesVisible(false);

			    /*    plot.setDomainGridlinesVisible(true);
			        plot.setRangeGridlinesVisible(true);


			        plot.setRangeGridlinePaint(Color.WHITE); 
			        plot.setDomainGridlinePaint(Color.WHITE);*/
		     
		        ValueAxis xaxis = plot.getDomainAxis();
		        xaxis.setLabelFont(fontSetting.getLabelFont());
		        xaxis.setLabelFont(fontSetting.getLabelFont());
		        xaxis.setAutoRange(true);
		        xaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		     

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
		       */
		        XYLineAndShapeRenderer renderer
		    	= (XYLineAndShapeRenderer) plot.getRenderer();
		    	renderer.setShapesVisible(true);
		    	renderer.setShapesFilled(true);
		    	//following line ....change size of marker or data point
		    	 renderer.setSeriesPaint(0, Color.blue);
		    	    double size = 4.0;
		    	    double delta = size / 2.0;
		    	    Shape shape1 = new Rectangle2D.Double(-delta, -delta, size, size);
		    	    renderer.setSeriesShape(0, shape1);
		    	    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		    		//rangeAxis.setRange(10,100);
		    	  //  rangeAxis.setRange(2,50);
		    	  //  rangeAxis.setTickUnit(new NumberTickUnit(5));
		    	    rangeAxis.setTickLabelFont(fontSetting.getTickFont());
			        rangeAxis.setLabelFont(fontSetting.getLabelFont());
		    		rangeAxis.setAutoRange(true);
		    		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		    		
		       
		        return result;
		    }
	}