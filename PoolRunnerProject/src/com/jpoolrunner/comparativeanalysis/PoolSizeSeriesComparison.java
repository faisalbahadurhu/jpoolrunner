package com.jpoolrunner.comparativeanalysis;



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
import javax.swing.SwingUtilities;
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
import com.jpoolrunner.diskIO.DataLoader;

public class PoolSizeSeriesComparison extends JPanel {
	XYSeriesCollection  dataset;
		
	JFreeChart chart;
	final ChartPanel chartPanel;
	int width,height;	
	private XYSeries setSeriesValuesFromVector(Vector<Double> vector,String name){
		XYSeries series= new XYSeries(name);
		Iterator it=vector.iterator();
		int counter=1;
		while(it.hasNext()){
			series.add(counter++,(double)it.next());
		}
		
		return series;
	}
	public void makeNull(){
		try{
    		SwingUtilities.invokeLater(new Runnable() {//it is used to tackle exceptions that are generated often see bookmarks JfreeChartException
    			public void run() {
		dataset.removeAllSeries();
		dataset=null;
		chart=null;
		chartPanel.removeAll();
		chart=null;
		remove(chartPanel);
	}});}catch(Exception e){
		//JOptionPane.showMessageDialog(null, "Exception in RespnsePanel");
		//System.err.print(e);
	}
	}
	public PoolSizeSeriesComparison( Vector<String> tpsNTestNames, int width,int height) {
		this.width=width;
		 this.height=height;
		
	//String[] tpsNames=new String[tpsNTestNames.size()];	
//	Vector vectorOfVectors=new Vector(tpsNTestNames.size());
//	Vector vectorOfSeries=new Vector(tpsNTestNames.size());
		 dataset = new XYSeriesCollection();
		 
		 
	
	for(int i=0;i<tpsNTestNames.size();i++){
		String s=tpsNTestNames.elementAt(i);
		String spliter[]=s.split("/");
		//tpsNames[i]=spliter[0];	
		DataLoader dataloader=new DataLoader(tpsNTestNames.elementAt(i));// path is in form of e.g. NFBOS2/Test1
		Vector<Double>  throughputPanelSeries=dataloader.loadDoubleDatas("PoolSizePanelSeries");
	//	vectorOfVectors.add(throughputPanelSeries);
		final XYSeries series=setSeriesValuesFromVector(throughputPanelSeries,spliter[0]);	
		try{
    		SwingUtilities.invokeLater(new Runnable() {//it is used to tackle exceptions that are generated often see bookmarks JfreeChartException
    			public void run() {
    			//	dataset.removeAllSeries();
    				dataset.addSeries(series);
	
    			}
    		});
    	
		
    	}catch(Exception e){
    		//JOptionPane.showMessageDialog(null, "Exception in RespnsePanel");
    		//System.err.print(e);
    	}
		dataloader=null;
		
	
	}	
	
 chart = createChart(dataset,"Pool Size Comparison","Time(sec)","Nuber of Threads");
	//chart.removeLegend();
 chartPanel = new ChartPanel(chart);
	chartPanel.setPreferredSize(new Dimension(this.width,this.height));//width height
	//chartPanel.setPreferredSize(new java.awt.Dimension(800,00));
	this.add(chartPanel,BorderLayout.CENTER);

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
          result.setTitle(fontSetting.getTextTitle(title));
	        final XYPlot plot = result.getXYPlot();
	        result.setBackgroundPaint(Color.WHITE);
	        plot.setBackgroundPaint(new Color(0xb8cfe5));	        
	       
	     //   plot.setRangeGridlinesVisible(false);
		//   plot.setDomainGridlinesVisible(false);

	        plot.setDomainGridlinesVisible(true);
	        plot.setRangeGridlinesVisible(true);


	        plot.setRangeGridlinePaint(Color.WHITE); 
	        plot.setDomainGridlinePaint(Color.WHITE);
	     
	                
	    
	       
	    	    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	    	rangeAxis.setAutoRange(true);

	    	    rangeAxis.setTickLabelFont(fontSetting.getTickFont());
		        rangeAxis.setLabelFont(fontSetting.getLabelFont());
	    	   	ValueAxis xaxis = plot.getDomainAxis();
	    		 xaxis.setTickLabelFont(fontSetting.getTickFont());
	    		 xaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	 	        xaxis.setLabelFont(fontSetting.getLabelFont());
	 	        xaxis.setAutoRange(true);
	       
	        return result;
	    }
}
