package com.jpoolrunner.testingGraphExamples;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.IntervalCategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.IntervalBarRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;

public class IntervalBarChartDemo2 extends ApplicationFrame {
	public IntervalBarChartDemo2(String title) {
		super(title);
		JPanel chartPanel = createDemoPanel();
		chartPanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(chartPanel);
	}
	private static DefaultIntervalCategoryDataset createDataset1() {
		
		Number[] starts_S1 = new Number[] {150,100};
		Number[] ends_S1 = new Number[] {200,150};
		
		Number[] starts_S2 = new Number[] {250,200};
		Number[] ends_S2 = new Number[] {300,250};
		
		Number[][] starts = new Number[][]{starts_S1, starts_S2};
	/*	Number[][] starts= new Number[2][1];
		starts[0]=starts_S1;
		starts[1]=starts_S2;*/
		
	//	Number[][] starts11 = new Number[][] {starts_S11, starts_S22};
		Number[][] ends = new Number[][] {ends_S1,ends_S2};
	//	Number[][] ends11 = new Number[][] {ends_S11, ends_S22};
		
//		"CO\u2082"
	//	String ninty="P\u2082";
	//	String fifty="<html>P<sub>50</sub></html>";
		String[]  categoryKeys = {"90th%ile", "50th%ile"};//90th percentile and 50 percentile
	

		String[] seriesKeys = {"1kb-File","2kb-File"};
		DefaultIntervalCategoryDataset dataset
		= new DefaultIntervalCategoryDataset(seriesKeys,categoryKeys,starts, ends);
		
		
		return dataset;
	}
private static DefaultIntervalCategoryDataset createDataset2() {
		
		Number[] starts_S1 = new Number[] {170,230};
		Number[] ends_S1 = new Number[] {240,260};
		Number[] starts_S2 = new Number[] {350,400};
		Number[] ends_S2 = new Number[] {400,450};
		
		
		Number[][] starts = new Number[][]{starts_S1,starts_S2};
		/*	Number[][] starts= new Number[2][1];
			starts[0]=starts_S1;
			starts[1]=starts_S2;*/
			
		//	Number[][] starts11 = new Number[][] {starts_S11, starts_S22};
			Number[][] ends = new Number[][] {ends_S1,ends_S2};
//		"CO\u2082"
	//	String ninty="P\u2082";
	//	String fifty="<html>P<sub>50</sub></html>";
		String[]  categoryKeys = {"90th%ile", "50th%ile"};//90th percentile and 50 percentile
	

		String[] seriesKeys = {"3kb-File","4kb-File"};
		DefaultIntervalCategoryDataset dataset
		= new DefaultIntervalCategoryDataset(starts, ends);
		dataset.setSeriesKeys(seriesKeys);
		dataset.setCategoryKeys(categoryKeys);
		
		return dataset;
	}
	private static JFreeChart createChart(DefaultIntervalCategoryDataset dataset) {

		CategoryAxis domainAxis = new CategoryAxis("Percentiles");
		domainAxis.setLowerMargin(0);
		domainAxis.setUpperMargin(0);
		 domainAxis.setCategoryMargin(0);
		NumberAxis rangeAxis = new NumberAxis("ms");
		//  rangeAxis.setNumberFormatOverride(new DecimalFormat("0.00%"));
		IntervalBarRenderer renderer = new IntervalBarRenderer();
	//	renderer.setBaseItemLabelFont(new Font("Verdana",Font.PLAIN,4));
		renderer.setItemLabelFont(new Font("Verdana",Font.PLAIN,4));

		renderer.setBaseItemLabelGenerator(new IntervalCategoryItemLabelGenerator(){
			/*public Number getStartValue(int series, int category){
				return dataset.getStartValue(series, category);
			}*/
		/*	 @Override
			    public String generateLabel(CategoryDataset dataset, int row, int column) {
			        return "" + row + "," + column;
			    }*/
			
		});
		
	//	 ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER, TextAnchor.CENTER, 0.0); 
	  //      renderer.setPositiveItemLabelPosition(position);
	    //    position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE3, TextAnchor.CENTER, TextAnchor.CENTER, 0.0);
		// renderer.setPositiveItemLabelPositionFallback(position);
	        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
	        		ItemLabelAnchor.INSIDE6, TextAnchor.BOTTOM_CENTER));
	     //   renderer.setNegativeItemLabelPositionFallback(position);
		renderer.setBaseItemLabelsVisible(true);
	//	renderer.setItemMargin(0.1);
	/*	renderer.setBaseItemLabelGenerator( new CategoryItemLabelGenerator(){

		    @Override
		    public String generateRowLabel(CategoryDataset dataset, int row) {
		        return " " + row;
		    }

		    @Override
		    public String generateColumnLabel(CategoryDataset dataset, int column) {
		        return " " + column;
		    }

		    @Override
		    public String generateLabel(CategoryDataset dataset, int row, int column) {
		        return "" + row + "," + column;
		    }
		});*/
		CategoryPlot plot = new CategoryPlot(dataset, domainAxis, rangeAxis,
				renderer);
		
///////////////////
		
	      
////////////////////////////
		//ValueAxis rageAxis = plot.getRangeAxis();
		//rageAxis.setRange(new Range(100,300));
		JFreeChart chart = new JFreeChart("IntervalBarChartDemo1", plot);
		
		  final CategoryPlot plot2 = chart.getCategoryPlot();
		  CategoryAxis axis = plot2.getDomainAxis();
		/*  axis.setLowerMargin(0);
		  axis.setUpperMargin(0);
		  axis.setCategoryMargin(0);*/
		
		  final CategoryDataset dataset2 = createDataset2();
	      //  dataset2.
	        plot2.setDataset(1, dataset2);
	        plot2.mapDatasetToRangeAxis(1, 1);

	      
	        final ValueAxis axis2 = new NumberAxis("Secondary");
	        plot2.setRangeAxis(1, axis2);
	        IntervalBarRenderer renderer2 = new IntervalBarRenderer();
		//	renderer2.setItemMargin(.1);
	                plot.setRenderer(1, renderer2);

		ChartUtilities.applyCurrentTheme(chart);
		return chart;

	}
	public static JPanel createDemoPanel() {
		JFreeChart chart = createChart(createDataset1());
		return new ChartPanel(chart);
	}

	public static void main(String[] args) {
		JFrame demo = new IntervalBarChartDemo2(
				"Testing");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}

}