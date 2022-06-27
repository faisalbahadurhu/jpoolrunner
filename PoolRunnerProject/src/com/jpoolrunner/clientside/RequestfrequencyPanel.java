package com.jpoolrunner.clientside;
import java.awt.*;
/** This class is inialized in Scenario
 * 
 */
import java.text.*;
import java.util.Iterator;
import java.util.Vector;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.jfree.chart.axis.NumberTickUnit;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.axis.DateAxis;

//import abc.MemoryUsageDemo;
//import abc.MemoryUsageDemo.DataGenerator;
public class RequestfrequencyPanel extends JPanel{
	boolean isFixedIntensityTrue=true;
	String fixedIntensity;
	String lowerIntensity;
	String upperIntensity;
	int counter;

Frequencyholder frequencyholder;
double timeForGraphDataShow=05;// time to show data on the graph i kept it 5minutes...1 minute(60000*1)
LoadGenerationEngine requestSender;
//import java.net.ServerSocket;
ObjectOutputStream outStream;
ObjectInputStream inStream=null;
DataGenerator dataGenerator;
//TesterReciever testReciver;
private XYSeries frequencySeries;//the line graph to b plott
Vector throughputs;
boolean manualLoad;
JLabel loadValue;
JFreeChart chart;
public JFreeChart getChart(){return this.chart;}
//GraphSaver graphSaver;
@SuppressWarnings("deprecation")
public RequestfrequencyPanel(JLabel reqSentvalue, ObjectOutputStream outStream,ObjectInputStream inStream, Frequencyholder frequencyholder, Vector throughputs, boolean manualLoad, JLabel loadValue){
	
	setLayout(new BorderLayout());
	this.dataGenerator=new DataGenerator(1000);//call after every 1 second
	
	this.inStream=inStream;
	this.outStream=outStream;
//	this.testReciver=new TesterReciever(this.inStream);
	this.frequencyholder=frequencyholder;
	this.throughputs=throughputs;
	this.manualLoad=manualLoad;
	this.loadValue= loadValue;
	 frequencySeries = new XYSeries("Load/Requests per second");

	//frequencySeries = new TimeSeries("Load/Requests per second", Millisecond.class);
	//TimeSeriesCollection dataset = new TimeSeriesCollection();
	XYSeriesCollection dataset = new XYSeriesCollection();

	dataset.addSeries(frequencySeries);
	
	chart = createChart(dataset);
	
	chart.removeLegend();
	final ChartPanel chartPanel = new ChartPanel(chart);
	
	this.add(chartPanel,BorderLayout.CENTER);
	this.setBackground(new Color(0xEEEEE));
	requestSender= new LoadGenerationEngine(1000,frequencyholder,this.outStream,reqSentvalue,this.throughputs,this.manualLoad,this.loadValue,this.dataGenerator,this.chart,this.frequencySeries);//1000 ie send req after every 1 sec
	counter=1;

}
/*public void setFrequency(int freq){
	 this.frequencyholder.setFrequency(freq);
}*/
public void setManualLoad(boolean t){
	this.manualLoad=t;
	this.requestSender.setManualLoad(this.manualLoad);
	
}

public void startSendingRequest(){
	requestSender.startMe();
	
}
public void restartSendingRequest(){
	requestSender.restartMe();
}
public void stopSendingRequest(){
	requestSender.stopMe();
	
}
public void startRecivingRequest(){
	
	// new TesterReciever(inStream,this.throughput).start();
}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	/*	JFrame frame = new JFrame("Sending Frequency demo");
		frame.setBackground(new Color(0xEEEEE));
		RequestfrequencyPanel panel = new RequestfrequencyPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setSize(500, 400);
		//frame.setBounds(200, 120, 600, 280);
		frame.setVisible(true);
		//panel.new DataGenerator(500).start();
	//	panel.startSendingRequest();
		frame.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
		System.exit(0);
		}
		});
*/
	}
	class DataGenerator extends Timer implements ActionListener {
		/**
		* Constructor.
		*
		* @param interval the interval (in milliseconds)
		*/
		DataGenerator(int interval) {
		super(interval, null);
		this.setInitialDelay(1000);//start after 1 sec
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
			frequencySeries.add(counter,frequencyholder.getFrequency());	
			counter++;
			//frequencySeries.add(new Millisecond(), 4);
		}
}
	public XYSeries getSeries(){// for comparison 
		return this.frequencySeries;

		//return this.series1;
		}
	public void changeChart(){
		double minimum=frequencySeries.getMinY();
		double maximum=frequencySeries.getMaxY();//getMinY();
        final XYPlot plot = chart.getXYPlot();//domain is x and range is y
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
 	   rangeAxis.setRange(minimum-1.0,maximum+1.0);


		
	}
	  private JFreeChart createChart(final XYDataset dataset) {
	        final JFreeChart result = ChartFactory.createXYLineChart(
	            null,
	            "Time(sec)",
	            "Number Of Requests",
	            dataset,
	            PlotOrientation.VERTICAL,
	            true,
	            true,
	            false
	        );
	        FontSetting fontSetting=new FontSetting(); 
          //  TextTitle my_Chart_title=new TextTitle("Dynamic Request Frequencies", new Font ("Verdana", Font.BOLD , 15));
            result.setTitle(fontSetting.getTextTitle("Dynamic Request Frequencies"));
	        final XYPlot plot = result.getXYPlot();
	       result.setBackgroundPaint(Color.WHITE);
	        plot.setBackgroundPaint(new Color(0xb8cfe5));
	        plot.setDomainGridlinesVisible(false);
	        plot.setRangeGridlinesVisible(false);

	     /*   plot.setDomainGridlinesVisible(true);
	        plot.setRangeGridlinesVisible(true);
	        plot.setDomainGridlinePaint(Color.WHITE);
	        plot.setRangeGridlinePaint(Color.WHITE);	*/                
	        ValueAxis xaxis = plot.getDomainAxis();
	        
	      //  DateAxis axis  = (DateAxis)xaxis;
	    //    axis.setDateFormatOverride(new SimpleDateFormat("mm:ss"));
	       
	      //  xaxis.setDateFormatOverride(new SimpleDateFormat("HH:mm:ss"));
	        
	        
	        xaxis.setTickLabelFont(fontSetting.getTickFont());
	        xaxis.setLabelFont(fontSetting.getLabelFont());
	        xaxis.setAutoRange(true);
	        xaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	     
	        //Domain axis would show data of 2minutes for a time(60000 means 60 sec 120000 means 120 sec 180000 means 180 sec and so on)
	   //     xaxis.setFixedAutoRange(60000*this.timeForGraphDataShow);  // for 5minutes..use 60000*5 seconds
	      //  xaxis.setVerticalTickLabels(true);
	     
	         final XYPlot plot2 = (XYPlot)result.getPlot();
	        NumberAxis range = (NumberAxis) plot2.getRangeAxis();
	        range.setTickLabelFont(fontSetting.getTickFont());
	        range.setLabelFont(fontSetting.getLabelFont());
	      //  range.setRange(0.0,20);
	        range.setAutoRange(true);
    		range.setStandardTickUnits(NumberAxis.createIntegerTickUnits());


	      //  range.setTickUnit(new NumberTickUnit(10.0));
	        
	        
	      //  yaxis.setra
	       
	        return result;
	    }
	  public void setFixedIntensityTrueOrFalse(boolean b) {
			// TODO Auto-generated method stub
			this.isFixedIntensityTrue=b;
			this.requestSender.setFixedIntensityTrueOrFalse(this.isFixedIntensityTrue);
			
		}
	public void setFixedIntensityValue(String fixedIntensity) {
		// TODO Auto-generated method stub
		this.fixedIntensity=fixedIntensity;
		this.requestSender.setFixedIntensityValue(this.fixedIntensity);
		
	}
	public void setIntensityRange(String lowerIntensity,String upperIntensity) {
		// TODO Auto-generated method stub
		this.lowerIntensity=lowerIntensity;
		this.upperIntensity=upperIntensity;
		this.requestSender.setIntensityRange(this.lowerIntensity,this.upperIntensity);
		
	}

	
}
