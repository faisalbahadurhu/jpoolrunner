package com.jpoolrunner.testingGraphExamples;

/* -----------------------------
* StatisticalBarChartDemo1.java
* -----------------------------
* (C) Copyright 2005, by Object Refinery Limited.
*
*/

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.IntervalBarRenderer;
import org.jfree.chart.renderer.category.StatisticalBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
* A simple demonstration application showing how to create a "statistical" 
* bar chart using data from a {@link CategoryDataset}.
*/
public class StatisticalBarChartDemo1 extends ApplicationFrame {

    /**
     * Creates a new demo.
     *
     * @param title  the frame title.
     */
    public StatisticalBarChartDemo1(String title) {
        super(title);
        CategoryDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);
    }

    /**
     * Creates a sample dataset.
     * 
     * @return The dataset.
     */
    private static CategoryDataset createDataset() {
        
        DefaultStatisticalCategoryDataset dataset 
            = new DefaultStatisticalCategoryDataset();
        dataset.add(15.0, 2.4, "Row 1", "Column 1");
        dataset.add(15.0, 4.4, "Row 1", "Column 2");
        dataset.add(13.0, 2.1, "Row 1", "Column 3");
        dataset.add(7.0, 1.3, "Row 1", "Column 4");
        dataset.add(2.0, 2.4, "Row 2", "Column 1");
        dataset.add(18.0, 4.4, "Row 2", "Column 2");
        dataset.add(28.0, 2.1, "Row 2", "Column 3");
        dataset.add(17.0, 1.3, "Row 2", "Column 4");
        return dataset;
                
    }
    
    /**
     * Creates a sample chart.
     * 
     * @param dataset  a dataset.
     * 
     * @return The chart.
     */
    private static JFreeChart createChart(CategoryDataset dataset) {
        
        // create the chart...
        JFreeChart chart = ChartFactory.createLineChart(
            "Statistical Bar Chart Demo 1", // chart title
            "Type",                         // domain axis label
            "Value",                        // range axis label
            dataset,                        // data
            PlotOrientation.VERTICAL,       // orientation
            true,                           // include legend
            true,                           // tooltips
            false                           // urls
        );

        chart.setBackgroundPaint(Color.white);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);

        // customise the range axis...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);

        // customise the renderer...
        StatisticalBarRenderer renderer = new StatisticalBarRenderer();
        renderer.setErrorIndicatorPaint(Color.green);
        renderer.setSeriesOutlinePaint(0,Color.red);
        renderer.setSeriesOutlinePaint(1,Color.blue);
        renderer.setItemMargin(0.0);
        plot.setRenderer(0,renderer);
        
        //draw interval chart here
        IntervalBarRenderer renderer1 = new IntervalBarRenderer();
        IntervalCategoryDataset intervals = createIntervalDataset();
        plot.setRenderer(1,renderer1);
        plot.setDataset(1,intervals);
        // OPTIONAL CUSTOMISATION COMPLETED.
       return chart;
    }
    
    private static IntervalCategoryDataset createIntervalDataset() {
        final String[] categories = {"column 4","column 5","column 6"};
        final String[] seriesNames = {"series 1", "series 2"}; 
        double[][] starts = new double[][] {{5, 15, 4}, {3, 4, 5}};
        double[][] ends = new double[][] {{20, 25, 10}, {17, 18, 25}};
        DefaultIntervalCategoryDataset dataset 
            = new DefaultIntervalCategoryDataset(starts, ends);
        dataset.setSeriesKeys(seriesNames);
        dataset.setCategoryKeys(categories);
        return dataset;
    }
    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     * 
     * @return A panel.
     */
    public static JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        return new ChartPanel(chart);
    }
    
    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(String[] args) {
        StatisticalBarChartDemo1 demo = new StatisticalBarChartDemo1(
            "Statistical Bar Chart Demo"
        );
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

}