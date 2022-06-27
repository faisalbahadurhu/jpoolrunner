package com.jpoolrunner.testingGraphExamples;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendRenderingOrder;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;

/**
 * A simple demonstration application showing how to create a stacked 3D bar chart
 * using data from a {@link CategoryDataset}.
 *
 */
   
import org.jfree.chart.ChartFactory;   
import org.jfree.chart.ChartPanel;   
import org.jfree.chart.JFreeChart;   
import org.jfree.chart.labels.ItemLabelAnchor;   
import org.jfree.chart.labels.ItemLabelPosition;   
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;   
import org.jfree.chart.plot.CategoryPlot;   
import org.jfree.chart.plot.PlotOrientation;   
import org.jfree.chart.renderer.category.BarRenderer;   
import org.jfree.data.category.CategoryDataset;   
import org.jfree.data.general.DatasetUtilities;   
import org.jfree.ui.ApplicationFrame;   
import org.jfree.ui.RefineryUtilities;   
import org.jfree.ui.TextAnchor;   
   
/**  
 * A simple demonstration application showing how to create a stacked 3D bar   
 * chart using data from a {@link CategoryDataset}.  
 */   
public class StackedBarChart3DDemo extends ApplicationFrame {   
   
    /**  
     * Creates a new demo.  
     *  
     * @param title  the frame title.  
     */   
    public StackedBarChart3DDemo(String title) {   
        super(title);   
        CategoryDataset dataset = createDataset();   
        JFreeChart chart = createChart(dataset);   
        ChartPanel chartPanel = new ChartPanel(chart);   
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));   
        setContentPane(chartPanel);   
    }   
   
    /**  
     * Creates and returns a {@link CategoryDataset} for the demo chart.  
     *  
     * @return a sample dataset.  
     */   
    public CategoryDataset createDataset() {   
   
        double[][] data = new double[][]   
            {{10.0, 4.0, 15.0, 14.0},   
             {-5.0, -7.0, 14.0, -3.0},   
             {6.0, 17.0, -12.0, 7.0},   
             {7.0, 15.0, 11.0, 0.0},   
             {-8.0, -6.0, 10.0, -9.0},   
             {9.0, 8.0, 0.0, 6.0},   
             {-10.0, 9.0, 7.0, 7.0},   
             {11.0, 13.0, 9.0, 9.0},   
             {-3.0, 7.0, 11.0, -10.0}};   
   
        return DatasetUtilities.createCategoryDataset("Series ", "Category ", data);   
   
    }   
   
    /**  
     * Creates a chart.  
     *   
     * @param dataset  the dataset.  
     *   
     * @return The chart.  
     */   
    private JFreeChart createChart(CategoryDataset dataset) {   
   
        JFreeChart chart = ChartFactory.createStackedBarChart3D(   
            "Stacked Bar Chart 3D Demo",  // chart title   
            "Category",                   // domain axis label   
            "Value",                      // range axis label   
            dataset,                      // data   
            PlotOrientation.HORIZONTAL,   // the plot orientation   
            true,                         // include legend   
            true,                         // tooltips   
            false                         // urls   
        );   
        CategoryPlot plot = (CategoryPlot) chart.getPlot();   
        BarRenderer renderer = (BarRenderer) plot.getRenderer();   
        renderer.setDrawBarOutline(false);   
        renderer.setItemLabelGenerator(   
            new StandardCategoryItemLabelGenerator()   
        );   
        renderer.setItemLabelsVisible(true);   
        renderer.setPositiveItemLabelPosition(   
            new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER)   
        );   
        renderer.setNegativeItemLabelPosition(   
            new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER)   
        );   
        return chart;   
           
    }   
       
    /**  
     * Starting point for the demonstration application.  
     *  
     * @param args  ignored.  
     */   
    public static void main(String[] args) {   
   
        StackedBarChart3DDemo demo = new StackedBarChart3DDemo("Stacked Bar Chart 3D Demo");   
        demo.pack();   
        RefineryUtilities.centerFrameOnScreen(demo);   
        demo.setVisible(true);   
   
    }   
   
}   