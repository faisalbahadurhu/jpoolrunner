package com.jpoolrunner.clientside;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Vector;
import java.awt.event.*;
import java.util.concurrent.atomic.*;
import org.jfree.chart.title.TextTitle;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberTickUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import java.awt.*;
import javax.swing.*;
import org.jfree.chart.axis.DateTickUnit;
import javax.swing.JPanel;import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.plot.CategoryPlot;

import org.jfree.data.time.Millisecond;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


public class ThroughputPanel extends Consumer{

	double timeForGraphDataShow=5;// time to show data on the graph 10mints ...1 minute(60000*1)
	private XYSeries throughputSeries;//the line graph to b plott
	NonblockingCounter throughput;// it increments on reciving every response in RecieverX
	Vector throughputs;// it contains  throughputs of every second  used for comparison it is extracted in getThroughputPanelSeries() method in this calss plz see it //shared in this calss and FrequencyGenerator
	int count;
	StopWatch stopWatch;
	public Vector getThroughputs() {
		return throughputs;
	}
	JFreeChart chart;
	public JFreeChart getChart(){return this.chart;}
	NonblockingCounter tracerForCompareButton;// came from scenario .All panels increment it only once b4 stopping and since there are 4 Graphics panels so when its value is four then it means that all panels have been stopped and compare button should be enabled now
	int countTester=0;
	DataUpdater dataGenerator;
	XYDataset dataset;
	XYSeries series1 ;
	int counter;
	int olderThroughput=0;//this is throughput per sec
	boolean stop=false;
	SharedBoolean sharedBoolean;//this boolean would be true only in ResponsePanel when ResponsePanel stops it on drawing all readoObjects by ReciverX and then ThroughputPanel etc would be stopped
	//JButton compare=null;
	JComboCheckBox compare=null;
	JButton compareButton=null;
	JButton loadTestButton;//enabled in ThroughputPanel
	BooleanChecker sendnotSendingProtocol;//only at the time of request sending it will be false 
	String strategyName;
	JButton responseStatisticsButton;
	JButton buttonRTStatisticsTable;
	JButton poolSizeStatisticsButton;
	JButton throughputStatisticsButton;
	JButton waitStatisticsButton;
	JButton buttonWTStatisticsGraph;
	private JButton save;
	private Scenario scenario;
	public void setBooleanChecker(BooleanChecker sendnotSendingProtocol){
		this.sendnotSendingProtocol=sendnotSendingProtocol;

	}
	public void setButton(JButton loadTestButton){this.loadTestButton=loadTestButton;}

	public XYSeries getSeries(){// for comparison 
		return this.series1;
	}
	public void changeChart(){
		double minimum=throughputSeries.getMinY();
		double maximum=throughputSeries.getMaxY();//getMinY();
        final XYPlot plot = chart.getXYPlot();//domain is x and range is y
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
 	   rangeAxis.setRange(minimum-1.0,maximum+1.0);


		
	}
	public ThroughputPanel(NonblockingCounter throughput,NonblockingCounter tracerForCompareButton,SharedBoolean sharedBoolean,JComboCheckBox compare, JButton compareButton, Vector throughputs, String strategyName, JButton responsePanelStatisticsButton, JButton poolSizePanelStatisticsButton, JButton throughputPanelStatisticsButton, JButton waitPanelStatisticsButton, Scenario scenario, JButton buttonRTStatisticsTable, JButton buttonWTStatisticsGraph){
		this.responseStatisticsButton=responsePanelStatisticsButton;
		this.buttonWTStatisticsGraph=buttonWTStatisticsGraph;
		this.buttonRTStatisticsTable=buttonRTStatisticsTable;
		this.poolSizeStatisticsButton=poolSizePanelStatisticsButton;
		this.throughputStatisticsButton=throughputPanelStatisticsButton;
		this.waitStatisticsButton=waitPanelStatisticsButton;
		count=1;
		this.scenario=scenario;
		this.compare=compare;
		this.compareButton=compareButton;
		this.tracerForCompareButton=tracerForCompareButton;
		setPreferredSize(new Dimension(100,205));
		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		//super(new BorderLayout());
		this.throughput= throughput;
		this.sharedBoolean=sharedBoolean;
		this.throughputs=throughputs;
		this.strategyName=strategyName;
		counter=-5;

		//throughputSeries = new TimeSeries("Throughput per second", Second.class);// v.v. imp it was commented
		throughputSeries = new XYSeries("Throughput per second");
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(throughputSeries);// remov it
		/////
		//dataset = createDataset();// v.v.impr used when display total throughput after every 5 sec 
		chart = createChart(dataset);
		chart.removeLegend();
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500,205));//width height
		//chartPanel.setPreferredSize(new java.awt.Dimension(800,00));
		this.add(chartPanel,BorderLayout.CENTER);


		//this.setBackground(new Color(0xEEEEE));



		this.dataGenerator=new DataUpdater(1000);// draw after every 1 sec ,,,, v.v imp it was 50000draw after every 5 seconds
		//panel.startSendingRequest();

	}
	public XYSeries getThroughputPanelSeries(){

		XYSeries series ;
		series = new XYSeries(this.strategyName);
		Iterator it=throughputs.iterator();
		int count=1;
		while(it.hasNext()){
			series.add(count,(double)it.next());
			count++;
		}
		return series;
	}
	private  XYDataset createDataset() {
		series1 = new XYSeries("First");

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);

		return dataset;
	}
	public void startThroughputPanel(){
		this.dataGenerator.start();//run after every second


	}


	public void stopThroughputPanel(){
		this.stop=true;//this is not utilized yet
		//this.dataGenerator.stop();//run after every second


	}
	public boolean getStopValue(){
		return this.stop;
	}
	/*public void showJOptioPane(){
//	Vector v=getTotalJobs();
	Iterator it=throughputs.iterator();
	String s="";
	while(it.hasNext()){
		double n=(double)it.next();
		s+=n+"\n";
	}
	//JPanel scrollingPanel = new JPanel();
	//scrollingPanel.setLayout(new BorderLayout());

	JPanel innerPanel = new JPanel();
	innerPanel.setLayout(new BorderLayout());
	innerPanel.setPreferredSize(new Dimension(500,500));

	JTextArea jta=new JTextArea(50,50);
	jta.append(s);
	//jta.append(testing);

	JScrollPane pane=new JScrollPane(jta);
	pane.setBounds(5, 5, 100, 100);
	innerPanel.add(pane, BorderLayout.CENTER);
	int result = JOptionPane.showConfirmDialog(null, innerPanel," JOB DATA", JOptionPane.OK_CANCEL_OPTION);
}*/

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
			this.setInitialDelay(1500);//start after 1.5 sec so tht throughput.getvalue() returns 10 instead of 0 at the start
			addActionListener(this);
		}
		/**
		 * Adds a new free/total memory reading to the dataset.
		 *
		 * @param event the action event.
		 */
		@Override
		public void actionPerformed(ActionEvent event) {


			int tput=throughput.getValue();
		//	if(tput!=olderThroughput){//i.e simulation is stopped and thats y throughput.getValue() contains older values
				double outPut=tput-olderThroughput;


				throughputSeries.addOrUpdate(count,outPut);
				count++;

				synchronized(throughputs){
					throughputs.add(outPut);// add the plotting value to the vector so that FrequencyGenerator can analyze it
				}
				olderThroughput=tput;	
			//}
			if((stop==true) && (sharedBoolean.getStopValue())) {
				scenario.setSaveButtonEnable(true);
				scenario.setStopButtonEnable(false);
				sendnotSendingProtocol.setSend(true);
				tracerForCompareButton.increment();
				if(tracerForCompareButton.getValue()>=3){
					compare.setEnabled(true);
					compareButton.setEnabled(true);
					loadTestButton.setEnabled(true);
					enableAllStatisticsPanels();
					stopWatch.stopTheWatch();
					save.setEnabled(true);
				}
				this.stop();// ie if stop is true only when RecieverX will recieve stop signa from Responsesender on server side and a ClassCastException in Reciever will make this stop true and sharedBoolean is true only when ResponsePanel make it true when ResponsePanel plots all readObjects
				//showJOptioPane();



			}
		}
	}
	private void enableAllStatisticsPanels(){
		this.responseStatisticsButton.setEnabled(true);
		buttonRTStatisticsTable.setEnabled(true);
		buttonWTStatisticsGraph.setEnabled(true);
		this.poolSizeStatisticsButton.setEnabled(true);
		this.throughputStatisticsButton.setEnabled(true);
		this.waitStatisticsButton.setEnabled(true);
	}
	private JFreeChart createChart(final XYDataset dataset) {//createXYLineChart(
		final JFreeChart result = ChartFactory.createXYLineChart(

				null,
				"Time(sec)",
				"Throughput(Jobs per second)",
				dataset,
				PlotOrientation.VERTICAL,
				true,
				true,
				false
				);

		FontSetting fontSetting=new FontSetting(); 
		//  TextTitle my_Chart_title=new TextTitle("Dynamic Request Frequencies", new Font ("Verdana", Font.BOLD , 15));
		result.setTitle(fontSetting.getTextTitle("Throughput: "+strategyName));
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

		/*   plot.setDomainGridlinesVisible(true);
		   plot.setRangeGridlinesVisible(true);


		plot.setRangeGridlinePaint(Color.WHITE); 
		plot.setDomainGridlinePaint(Color.WHITE);*/
		///////////*

		ValueAxis xaxis = plot.getDomainAxis();


		xaxis.setTickLabelFont(fontSetting.getTickFont());
		xaxis.setLabelFont(fontSetting.getLabelFont());
		xaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		xaxis.setAutoRange(true);

		//Domain axis would show data of 2minutes for a time(60000 means 60 sec 120000 means 120 sec 180000 means 180 sec and so on)
		//   xaxis.setFixedAutoRange(60000*this.timeForGraphDataShow);  // for 5minutes..use 60000*5 seconds
		// xaxis.setVerticalTickLabels(true);

		final XYPlot plot2 = (XYPlot)result.getPlot();
		NumberAxis range = (NumberAxis) plot2.getRangeAxis();
		range.setTickLabelFont(fontSetting.getTickFont());
		range.setLabelFont(fontSetting.getLabelFont());
		//  range.setRange(10,50);
		range.setAutoRange(true);
		//   range.setTickUnit(new NumberTickUnit(50));


		//  yaxis.setra
		//  * */
		//v.v. imp  
		/*   XYLineAndShapeRenderer renderer
	    	= (XYLineAndShapeRenderer) plot.getRenderer();
	    	renderer.setShapesVisible(true);
	    	renderer.setShapesFilled(true);
	    	//following line ....change size of marker or data point
	    	 renderer.setSeriesPaint(0, Color.blue);
	    	    double size = 4.0;
	    	    double delta = size / 2.0;
	    	    Shape shape1 = new Rectangle2D.Double(-delta, -delta, size, size);
	    	    renderer.setSeriesShape(0, shape1);*/
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		//rangeAxis.setRange(10,100);
		//  rangeAxis.setRange(5.0,100);
		//  rangeAxis.setTickUnit(new NumberTickUnit(10));
		rangeAxis.setAutoRange(true);
		//rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());


		return result;
	}
	public double getTotalThroughput() {
		double sum=0.0;
		Iterator it=throughputs.iterator();
		//	String s="";
		while(it.hasNext()){
			double d=(double)it.next();
			//	s+=" "+d;
			sum+=d;

		}
		//	JOptionPane.showMessageDialog(null, ""+s);

		return sum;

	}
	public double getAvgThroughput() {//i.e. responses per second
		// TODO Auto-generated method stub
		//	JOptionPane.showMessageDialog(null, ""+throughputs.size());
		return (getTotalThroughput()/(throughputs.size()));

	}
	public void setStopWatch(StopWatch stopWatch) {
		// TODO Auto-generated method stub
		this.stopWatch=stopWatch;
		
	}
	public void setSaveButton(JButton save) {
		// TODO Auto-generated method stub
		this.save=save;
		
	}
}
