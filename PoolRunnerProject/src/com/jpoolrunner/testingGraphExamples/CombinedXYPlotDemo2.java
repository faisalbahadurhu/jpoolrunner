package com.jpoolrunner.testingGraphExamples;
/* ------------------------
 * CombinedXYPlotDemo2.java
 * ------------------------
 * (C) Copyright 2002-2009, by Object Refinery Limited.
 *
 */



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CombinedRangeXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.date.MonthConstants;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A demonstration application showing how to create a combined chart.  A
 * bar chart is displayed on the left, and a line chart on the right.
 */
public class CombinedXYPlotDemo2 extends ApplicationFrame {

    /**
     * Constructs a new demonstration application.
     *
     * @param title  the frame title.
     */
	TimeSeries series2;
    public CombinedXYPlotDemo2(String title) {

        super(title);
        JFreeChart chart = createCombinedChart();
        ChartPanel panel = new ChartPanel(chart, true, true, true, true, true);
        panel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(panel);

    }

    /**
     * Creates a combined XYPlot chart.
     *
     * @return the combined chart.
     */
   JFreeChart createCombinedChart() {

        // create subplot 1...
        IntervalXYDataset data1 = createDataset1();
        XYItemRenderer renderer1 = new XYBarRenderer(0.20);
        renderer1.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
                StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
                new SimpleDateFormat("d-MMM-yyyy"),
                new DecimalFormat("0,000.0")));
        XYPlot subplot1 = new XYPlot(data1, new DateAxis("Date"), null,
                renderer1);

        // create subplot 2...
        series2 = new TimeSeries("Series 2",Second.class);

        XYDataset data2 = new TimeSeriesCollection(series2);
        XYItemRenderer renderer2 = new StandardXYItemRenderer();
        renderer2.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
                StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
                new SimpleDateFormat("d-MMM-yyyy"),
                new DecimalFormat("0,000.0")));
        XYPlot subplot2 = new XYPlot(data2, new DateAxis("Date"), null,
                renderer2);

        // create a parent plot...
        NumberAxis sharedAxis = new NumberAxis("Value");
        sharedAxis.setTickMarkInsideLength(3.0f);
        CombinedRangeXYPlot plot = new CombinedRangeXYPlot(sharedAxis);

        // add the subplots...
        plot.add(subplot1, 1);
        plot.add(subplot2, 1);

        // return a new chart containing the overlaid plot...
        JFreeChart chart = new JFreeChart("Combined (Range) XY Plot",
                JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        ChartUtilities.applyCurrentTheme(chart);
        return chart;
    }

    /**
     * Creates a sample dataset.
     *
     * @return Series 1.
     */
    private static IntervalXYDataset createDataset1() {

        // create dataset 1...
        TimeSeries series1 = new TimeSeries("Series 1");
        series1.add(new Day(1, MonthConstants.MARCH, 2002), 12353.3);
        series1.add(new Day(2, MonthConstants.MARCH, 2002), 13734.4);
        series1.add(new Day(3, MonthConstants.MARCH, 2002), 14525.3);
        series1.add(new Day(4, MonthConstants.MARCH, 2002), 13984.3);
        series1.add(new Day(5, MonthConstants.MARCH, 2002), 12999.4);
        series1.add(new Day(6, MonthConstants.MARCH, 2002), 14274.3);
        series1.add(new Day(7, MonthConstants.MARCH, 2002), 15943.5);
        series1.add(new Day(8, MonthConstants.MARCH, 2002), 14845.3);
        series1.add(new Day(9, MonthConstants.MARCH, 2002), 14645.4);
        series1.add(new Day(10, MonthConstants.MARCH, 2002), 16234.6);
        series1.add(new Day(11, MonthConstants.MARCH, 2002), 17232.3);
        series1.add(new Day(12, MonthConstants.MARCH, 2002), 14232.2);
        series1.add(new Day(13, MonthConstants.MARCH, 2002), 13102.2);
        series1.add(new Day(14, MonthConstants.MARCH, 2002), 14230.2);
        series1.add(new Day(15, MonthConstants.MARCH, 2002), 11235.2);

        TimeSeriesCollection collection = new TimeSeriesCollection(series1);
        return collection;

    }

    
    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     *
     * @return A panel.
     */
    public  JPanel createDemoPanel() {
        JFreeChart chart = createCombinedChart();
        return new ChartPanel(chart);
    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(String[] args) {

        CombinedXYPlotDemo2 demo = new CombinedXYPlotDemo2(
                "JFreeChart: CombinedXYPlotDemo2.java");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }
    class DataGenerator extends Timer implements ActionListener {
		/**
		 * Constructor.
		 *
		 * @param interval the interval (in milliseconds)
		 */
		DataGenerator(int interval) {
			super(interval, null);
			addActionListener(this);
		}
		/**
		 * Adds a new free/total memory reading to the dataset.
		 *
		 * @param event the action event.
		 */
		public void actionPerformed(ActionEvent event) {
			long f = Runtime.getRuntime().freeMemory();
			long t = Runtime.getRuntime().totalMemory();
		series2.add(new Second(), Math.random()*1000);
		}
	}

}