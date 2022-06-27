package com.jpoolrunner.comparativeanalysis;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import com.jpoolrunner.clientside.FontSetting;
import com.jpoolrunner.diskIO.DataLoader;

public class ThroughputStatisticsComparison extends JPanel {

	private Vector<Double> resultStatistics; 
	//saved as double totalThroughput,double avgThroughput,double fiftyPercentileRT,double nintyPercentileRT,double nintyFivePercentileRT,double avgRT,double avgWaitTime,double avgPoolSize){

	DefaultCategoryDataset dataset = new DefaultCategoryDataset();

	/**
	 * Create the panel.
	 */
	public ThroughputStatisticsComparison() {

	}

	public ThroughputStatisticsComparison(Vector<String> tpsAndTestNames, int width,int height) {
		
		Vector responseResults=new Vector(tpsAndTestNames.size());// it is a vector of vectors
		
		for(int i=0;i<tpsAndTestNames.size();i++){
			
			DataLoader dataloader=new DataLoader(tpsAndTestNames.elementAt(i));// path is in form of e.g. NFBOS2/Test1
			Vector<Double> resultStatistics=dataloader.loadDoubleDatas("ResultStatistics"); //saved as double totalThroughput,double avgThroughput,double fiftyPercentileRT,double nintyPercentileRT,double nintyFivePercentileRT,double avgRT,double avgWaitTime,double avgPoolSize){
			responseResults.add(resultStatistics);
			dataloader=null;		

		}
		
		for(int i=0;i<tpsAndTestNames.size();i++){
			
			Vector<Double> responseResult=	(Vector)responseResults.elementAt(i);// Extract a vector which contains Response Statistics of a Single test
			
		//	dataset.addValue(responseResult.get(0),tpsAndTestNames.elementAt(i) , "Total-Throughput");
			String s=tpsAndTestNames.elementAt(i);
			String spliter[]=s.split("/");//spliter[0]
		//	dataset.addValue(responseResult.get(1),tpsAndTestNames.elementAt(i) , "Average Throughput");
			dataset.addValue(responseResult.get(1),spliter[0] , "Average Throughput");
		
		}
		
		JFreeChart chart = ChartFactory.createBarChart(
				"Throughput Statistics", // chart title
				"Category", // domain axis label
				"Value", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips?
				false // URLs?
				);
		//chart.removeLegend();
		FontSetting fontSetting=new FontSetting(); 
        chart.setTitle(fontSetting.getTextTitle("Average Throughput Comparison"));
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
     //   final CategoryItemRenderer renderer = plot.getRenderer();
                
     /*   StackedBarRenderer renderer = new StackedBarRenderer(false);
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        plot.setRenderer(renderer);*/


        plot.setBackgroundPaint(new Color(0xb8cfe5));	        

        NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
        rangeAxis.setTickLabelFont(fontSetting.getTickFont());
        rangeAxis.setLabelFont(fontSetting.getLabelFont());
        
    	CategoryAxis xaxis = plot.getDomainAxis();
    	//xaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
		 xaxis.setTickLabelFont(fontSetting.getTickFont());
        xaxis.setLabelFont(fontSetting.getLabelFont());
		
		ChartPanel chartPanel = new ChartPanel(chart, false);
		chartPanel.setPreferredSize(new Dimension(width,height));
		this.add(chartPanel,BorderLayout.CENTER);

		}
}

