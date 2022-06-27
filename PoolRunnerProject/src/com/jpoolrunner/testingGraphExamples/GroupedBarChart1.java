package com.jpoolrunner.testingGraphExamples;

/* --------------------
* GroupedBarChart.java
* --------------------
* (C) Copyright 2007, by Object Refinery Limited.
*
*/


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.SubCategoryAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardCategorySeriesLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.data.KeyToGroupMap;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
* A grouped stacked bar chart.
*/
public class GroupedBarChart1 extends ApplicationFrame {

   
    /**
     * Creates a new demo.
     *
     * @param title  the frame title.
     */
    public GroupedBarChart1(String title) {
        super(title);
        final CategoryDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);
    }

    /**
     * Creates a sample dataset.
     *
     * @return A sample dataset.
     */
    private static CategoryDataset createDataset() {
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        result.addValue(0, "Waiting", "Task 1");
        result.addValue(0, "Started", "Task 1");
        result.addValue(1, "Started", "Task 2");
        result.addValue(2, "Started", "Task 3");
        result.addValue(2, "Waiting", "Task 4");
        result.addValue(1, "Completed", "Task 1");
       
        result.addValue(1, "Completed", "Task 2");
        return result;
    }
   
    /**
     * Creates a sample chart.
     *
     * @param dataset  the dataset for the chart.
     *
     * @return A sample chart.
     */
    private static JFreeChart createChart(CategoryDataset dataset) {

        JFreeChart chart = ChartFactory.createStackedBarChart(
            "Grouped Bar Chart",  // chart title
            null,                  // domain axis label
            "Value",                     // range axis label
            dataset,                     // data
            PlotOrientation.HORIZONTAL,    // the plot orientation
            true,                        // legend
            true,                        // tooltips
            false                        // urls
        );
       
         CategoryPlot plot = (CategoryPlot) chart.getPlot();

        plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        plot.setBackgroundPaint(Color.WHITE);
        ValueAxis yAxis = new SymbolAxis("Domain Axis 1",new String[]{"Week 1","Week 2","Week 3","Week 4"});
      plot.setRangeAxis(yAxis);
      yAxis.setUpperMargin(0.0);
      plot.setBackgroundPaint(Color.WHITE);
      StackedBarRenderer renderer = new StackedBarRenderer();
      renderer.setSeriesPaint(0, Color.RED);
      renderer.setSeriesPaint(1, Color.YELLOW);
      renderer.setSeriesPaint(2, Color.GREEN);
        plot.setRenderer(renderer);

        return chart;
       
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
        GroupedBarChart1 demo = new GroupedBarChart1("Test Run Execution");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
}