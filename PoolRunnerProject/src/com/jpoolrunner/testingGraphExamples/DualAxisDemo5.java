package com.jpoolrunner.testingGraphExamples;


import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.title.TextTitle;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.VerticalAlignment;

/**
 * This demo shows how to create a dual axis bar chart.  A workaround is used because the
 * {@link BarRenderer} and {@link CategoryAxis} classes will overlap the bars for the two 
 * datasets - to get around this, an an additional series (containing 'null' values) is added 
 * to each dataset, and the getLegendItems() method in the plot is overridden.
 *
 */
public class DualAxisDemo5 extends ApplicationFrame {

    /**
     * Creates a new demo instance.
     *
     * @param title  the frame title.
     */
    public DualAxisDemo5(final String title) {
        super(title);
        final CategoryDataset dataset1 = createDataset1();
        final CategoryDataset dataset2 = createDataset2();
        final JFreeChart chart = createChart(dataset1, dataset2);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
    }

    // ****************************************************************************
    // * JFREECHART DEVELOPER GUIDE                                               *
    // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
    // * to purchase from Object Refinery Limited:                                *
    // *                                                                          *
    // * http://www.object-refinery.com/jfreechart/guide.html                     *
    // *                                                                          *
    // * Sales are used to provide funding for the JFreeChart project - please    * 
    // * support us so that we can continue developing free software.             *
    // ****************************************************************************
    
    /**
     * Creates a sample dataset.
     *
     * @return  The dataset.
     */
    private CategoryDataset createDataset1() {

        // row keys...
        final String series1 = "Series 1";
        final String series2 = "Dummy 1";

        // column keys...
        final String category1 = "Category 1";
        final String category2 = "Category 2";
        final String category3 = "Category 3";
        final String category4 = "Category 4";

        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(1.0, series1, category1);
        dataset.addValue(4.0, series1, category2);
        dataset.addValue(3.0, series1, category3);
        dataset.addValue(5.0, series1, category4);

        dataset.addValue(null, series2, category1);
        dataset.addValue(null, series2, category2);
        dataset.addValue(null, series2, category3);
        dataset.addValue(null, series2, category4);

        return dataset;

    }

    /**
     * Creates a sample dataset.
     *
     * @return  The dataset.
     */
    private CategoryDataset createDataset2() {

        // row keys...
        final String series1 = "Dummy 2";
        final String series2 = "Series 2";

        // column keys...
        final String category1 = "Category 1";
        final String category2 = "Category 2";
        final String category3 = "Category 3";
        final String category4 = "Category 4";

        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(null, series1, category1);
        dataset.addValue(null, series1, category2);
        dataset.addValue(null, series1, category3);
        dataset.addValue(null, series1, category4);

        dataset.addValue(75.0, series2, category1);
        dataset.addValue(87.0, series2, category2);
        dataset.addValue(96.0, series2, category3);
        dataset.addValue(68.0, series2, category4);

        return dataset;

    }

    /**
     * Creates a chart.
     * 
     * @param dataset1  the first dataset.
     * @param dataset2  the second dataset.
     * 
     * @return A chart.
     */
    private JFreeChart createChart(final CategoryDataset dataset1, final CategoryDataset dataset2) {

        final CategoryAxis domainAxis = new CategoryAxis("");
       // final NumberAxis rangeAxis = new NumberAxis("Value");
        final NumberAxis rangeAxis =new SymbolAxis("Bins", new String[]{"A 0", "B 1","C 2", "D 3", "E 4", "F 5",}); 
//
        final BarRenderer renderer1 = new BarRenderer();
        final CategoryPlot plot = new CategoryPlot(dataset1, domainAxis, rangeAxis, renderer1) {
            
            /**
             * Override the getLegendItems() method to handle special case.
             *
             * @return the legend items.
             */
            public LegendItemCollection getLegendItems() {

                final LegendItemCollection result = new LegendItemCollection();

                final CategoryDataset data = getDataset();
                if (data != null) {
                    final CategoryItemRenderer r = getRenderer();
                    if (r != null) {
                        final LegendItem item = r.getLegendItem(0, 0);
                        result.add(item);
                    }
                }

                // the JDK 1.2.2 compiler complained about the name of this
                // variable 
                final CategoryDataset dset2 = getDataset(1);
                if (dset2 != null) {
                    final CategoryItemRenderer renderer2 = getRenderer(1);
                    if (renderer2 != null) {
                        final LegendItem item = renderer2.getLegendItem(1, 1);
                        result.add(item);
                    }
                }
                return result;
            }
            
        };
        
        final JFreeChart chart = new JFreeChart("Dual Axis Bar Chart", plot);
        LegendTitle legendTitle = chart.getLegend();

        chart.addSubtitle(new TextTitle("sjdhkjshdf "+"      "+"fffffff",
        	    new Font("Dialog", Font.ITALIC, 14), Color.black,
        	    RectangleEdge.BOTTOM, HorizontalAlignment.CENTER,
        	    VerticalAlignment.BOTTOM, RectangleInsets.ZERO_INSETS));
        
        
      //  legend.setPosition(RectangleEdge.RIGHT);
        chart.setBackgroundPaint(Color.white);
//        chart.getLegend().setAnchor(Legend.SOUTH);
        plot.setBackgroundPaint(new Color(0xEE, 0xEE, 0xFF));
        plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        plot.setDataset(1, dataset2);
        plot.mapDatasetToRangeAxis(1, 1);
        final ValueAxis axis2 = new NumberAxis("Secondary");
        plot.setRangeAxis(1, axis2);
        plot.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_RIGHT);
        final BarRenderer renderer2 = new BarRenderer();
        plot.setRenderer(1, renderer2);
        
        return chart;
    }
    
    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(final String[] args) {

        final DualAxisDemo5 demo = new DualAxisDemo5("Dual Axis Demo 5");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }

}