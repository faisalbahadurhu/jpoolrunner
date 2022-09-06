package com.jpoolrunner.testingGraphExamples;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A simple demonstration application showing how to create a dynamic bar
 * chart.
 */
public class BarChartDemo10 extends ApplicationFrame {

    /**
     * Creates a new demo instance.
     *
     * @param title  the frame title.
     */
    public BarChartDemo10(String title) {
        super(title);
        JPanel chartPanel = createDemoPanel();
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);
    }

    /**
     * Returns a sample dataset.
     *
     * @return The dataset.
     */
    private static DefaultCategoryDataset createDataset() {

        // row keys...
        String series1 = "First";
        String series2 = "Second";
        String series3 = "Third";

        // column keys...
        String category1 = "Category 1";
        String category2 = "Category 2";
        String category3 = "Category 3";
        String category4 = "Category 4";
        String category5 = "Category 5";

        // create the dataset...
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(31.0, series1, category1);
        dataset.addValue(44.0, series1, category2);
        dataset.addValue(33.0, series1, category3);
        dataset.addValue(45.0, series1, category4);
        dataset.addValue(35.0, series1, category5);

        dataset.addValue(45.0, series2, category1);
        dataset.addValue(37.0, series2, category2);
        dataset.addValue(46.0, series2, category3);
        dataset.addValue(38.0, series2, category4);
        dataset.addValue(44.0, series2, category5);

        dataset.addValue(34.0, series3, category1);
        dataset.addValue(43.0, series3, category2);
        dataset.addValue(32.0, series3, category3);
        dataset.addValue(43.0, series3, category4);
        dataset.addValue(36.0, series3, category5);

        return dataset;

    }

    /**
     * Creates a sample chart.
     *
     * @param dataset  the dataset.
     *
     * @return The chart.
     */
    private static JFreeChart createChart(CategoryDataset dataset) {

        // create the chart...
        JFreeChart chart = ChartFactory.createBarChart(
            "Bar Chart Demo 10",         // chart title
            "Category",               // domain axis label
            "Value",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

        // get a reference to the plot for further customisation...
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setDomainGridlinesVisible(true);

        // set the range axis to display integers only...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);

        // set up gradient paints for series...
        GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue,
                0.0f, 0.0f, new Color(0, 0, 64));
        GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green,
                0.0f, 0.0f, new Color(0, 64, 0));
        GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red,
                0.0f, 0.0f, new Color(64, 0, 0));
        renderer.setSeriesPaint(0, gp0);
        renderer.setSeriesPaint(1, gp1);
        renderer.setSeriesPaint(2, gp2);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(
                Math.PI / 6.0));

        return chart;

    }

    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     *
     * @return A panel.
     */
    public static JPanel createDemoPanel() {
        DefaultCategoryDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        Animator animator = new Animator(dataset);
        animator.start();
        return new ChartPanel(chart);
    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(String[] args) {
        BarChartDemo10 demo = new BarChartDemo10(
                "JFreeChart: BarChartDemo10.java");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

}

/**
 * The animator.
 */
class Animator extends Timer implements ActionListener {

    /** The plot. */
    private DefaultCategoryDataset dataset;

    /**
     * Constructor.
     *
     * @param dataset  the dataset.
     */
    Animator(DefaultCategoryDataset dataset) {
        super(20, null);
        this.dataset = dataset;
        addActionListener(this);
    }

    /**
     * Modifies the starting angle.
     *
     * @param event  the action event.
     */
    public void actionPerformed(ActionEvent event) {
        int r = (int) (Math.random() * this.dataset.getRowCount());
        Comparable rowKey = this.dataset.getRowKey(r);
        int c = (int) (Math.random() * this.dataset.getColumnCount());
        Comparable columnKey = this.dataset.getColumnKey(c);
        int change = (Math.random() - 0.5 < 0 ? -5 : 5);
        this.dataset.setValue(Math.max(0,
                this.dataset.getValue(r, c).doubleValue() + change), rowKey,
                columnKey);
    }

}