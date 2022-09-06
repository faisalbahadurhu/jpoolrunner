package com.jpoolrunner.displaytestedscenariosonly;
import java.awt.BorderLayout;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;

import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.border.EtchedBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.jpoolrunner.clientside.FontSetting;

public class PanelFromDisk extends JPanel {
	XYSeriesCollection  dataset;
	XYSeries series1;	
	JFreeChart chart;
	private String strategyName;
	String title;
/*	private double getminimum(Vector vector){
		double minimum=999999999;
		Iterator it=vector.iterator();
		while(it.hasNext()){
			double value=(double)it.next();
			if(value<minimum) minimum=value;
		}
		return	minimum;
	}	
	private double getmaximum(Vector vector){
		double maximum=0.0;
		Iterator it=vector.iterator();
		while(it.hasNext()){
			double value=(double)it.next();
			if(value>maximum) maximum=value;
		}
		return	maximum;
	}	*/
	public PanelFromDisk( String strategyName,Vector<Double> vector, String title, String xAxis, String yAxis) {
		this.strategyName=strategyName;
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		series1 = new XYSeries(this.strategyName);
		setSeriesValuesFromVector(vector);
		this.title=title;
		dataset = new XYSeriesCollection();
		dataset.addSeries(series1);
		chart = createChart(dataset,title,xAxis,yAxis);
		chart.removeLegend();
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500,205));//width height
		//chartPanel.setPreferredSize(new java.awt.Dimension(800,00));
		this.add(chartPanel,BorderLayout.CENTER);
		}
	
	private void setSeriesValuesFromVector(Vector<Double> vector){
		Iterator it=vector.iterator();
		int counter=1;
		while(it.hasNext()){
			series1.add(counter++,(double)it.next());
		}
	}
	
	  private JFreeChart createChart(final XYDataset dataset,String title,String xAxix,String yAxis) {
	        final JFreeChart result = ChartFactory.createXYLineChart(
	            null,
	            xAxix,
	            yAxis,
	            dataset,
	            PlotOrientation.VERTICAL,
	            true,
	            true,
	            false
	        );
	        
	        FontSetting fontSetting=new FontSetting(); 
	        int lastIndex=strategyName.lastIndexOf('/');//extract TPS name from full path e.g. it would be if form NFBOS2/aaa but following statement will convert it into NFBOS2
			String tpsName=strategyName.substring(0, lastIndex);
          result.setTitle(fontSetting.getTextTitle(title+": "+tpsName));
	        final XYPlot plot = result.getXYPlot();
	        result.setBackgroundPaint(Color.WHITE);
	        plot.setBackgroundPaint(new Color(0xb8cfe5));	        
	       
	     //   plot.setRangeGridlinesVisible(false);
		//   plot.setDomainGridlinesVisible(false);

	        plot.setDomainGridlinesVisible(true);
	        plot.setRangeGridlinesVisible(true);


	        plot.setRangeGridlinePaint(Color.WHITE); 
	        plot.setDomainGridlinePaint(Color.WHITE);
	     
	      //Troughput/Sec
	    	//Load on Server   
	    
	       
	    	    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	    	    if(title.equals("Throughput/Sec")|| title.equals("Load on Server") ){
	    	    double minimum=series1.getMinY();
	    		double maximum=series1.getMaxY();//getMinY();
	     	   rangeAxis.setRange(minimum-1.0,maximum+1.0);
	    	    }
	    	    else rangeAxis.setAutoRange(true);
	    	    

	    	    rangeAxis.setTickLabelFont(fontSetting.getTickFont());
		        rangeAxis.setLabelFont(fontSetting.getLabelFont());
	    	   	ValueAxis xaxis = plot.getDomainAxis();
	    		 xaxis.setTickLabelFont(fontSetting.getTickFont());
	    		 xaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	 	        xaxis.setLabelFont(fontSetting.getLabelFont());
	 	        xaxis.setAutoRange(true);
	       
	        return result;
	    }
	  public double get90Percentile(){
			String s="";
			
			LinkedList<Double> doubleList = new LinkedList<Double>();
			XYSeries series1=this.dataset.getSeries(0);//since series1=null has been executed by the thread when stop signals came in, so we take the series from the dataset
			 for(Object o: series1.getItems()) {
		         XYDataItem xydi = (XYDataItem)o;
		         doubleList.add(xydi.getY().doubleValue());
		      //   s+="\n"+curY;
		         
		         
		         }
			 Collections.sort(doubleList);
			 double n=(90.0/100.0)*(doubleList.size());
			 int position=(int)(Math.round(n)-1);
			 return (doubleList.get(position));
		//	 JOptionPane.showMessageDialog(null,doubleList);
			
		}
	public double get95Percentile(){
		String s="";
		
		LinkedList<Double> doubleList = new LinkedList<Double>();
		XYSeries series1=this.dataset.getSeries(0);

		 for(Object o: series1.getItems()) {
	         XYDataItem xydi = (XYDataItem)o;
	         doubleList.add(xydi.getY().doubleValue());
	      //   s+="\n"+curY;
	         
	         
	         }
		 Collections.sort(doubleList);
		 double n=(95.0/100.0)*(doubleList.size());
		 int position=(int)(Math.round(n)-1);
		 return (doubleList.get(position));
//		 JOptionPane.showMessageDialog(null,doubleList);
		
	}
	public double get50Percentile(){
		String s="";
		
		LinkedList<Double> doubleList = new LinkedList<Double>();
		XYSeries series1=this.dataset.getSeries(0);

		 for(Object o: series1.getItems()) {
	         XYDataItem xydi = (XYDataItem)o;
	         doubleList.add(xydi.getY().doubleValue());
	      //   s+="\n"+curY;
	         
	         
	         }
		 Collections.sort(doubleList);
		 double n=(50.0/100.0)*(doubleList.size());
		 int position=(int)(Math.round(n)-1);
		 return (doubleList.get(position));
//		 JOptionPane.showMessageDialog(null,doubleList);
		
	}
	public double getAverageRT(){
		LinkedList<Double> doubleList = new LinkedList<Double>();
		XYSeries series1=this.dataset.getSeries(0);

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
	}
