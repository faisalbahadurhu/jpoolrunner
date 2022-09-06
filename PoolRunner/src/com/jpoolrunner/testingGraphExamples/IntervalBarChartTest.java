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
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
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
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.IntervalBarRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;

public class IntervalBarChartTest extends ApplicationFrame {
	public IntervalBarChartTest(String title) {
		super(title);
		JPanel chartPanel = createDemoPanel();
		chartPanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(chartPanel);
	}
	private static DefaultIntervalCategoryDataset createDataset1() {

		Number[] series1_Start = new Number[] {25,50};
		Number[] series1_End = new Number[] {50,75};
		Number[] dymmy1_Start = new Number[] {null,null};
		Number[] dummy1_End = new Number[] {null,null};
		Number[][] starts = new Number[][]{series1_Start,dymmy1_Start};
		Number[][] ends = new Number[][] {series1_End,dummy1_End};
		String[]  categoryKeys = {"50th%ile", "90th%ile"};
		String[] seriesKeys = {"Series1",""};
		DefaultIntervalCategoryDataset dataset= new DefaultIntervalCategoryDataset(seriesKeys,categoryKeys,starts, ends);
		return dataset;
	}
	private static DefaultIntervalCategoryDataset createDataset2() {
		Number[] dymmy1_Start = new Number[] {null,null};
		Number[] dummy1_End = new Number[] {null,null};
		Number[] series2_Start = new Number[] {100,150};
		Number[] series2_End = new Number[] {200,200};
		Number[] dymmy3_Start = new Number[] {null,null};
		Number[] dummy3_End = new Number[] {null,null};
		Number[][] starts = new Number[][]{dymmy1_Start,series2_Start,dymmy3_Start};
		Number[][] ends = new Number[][] {dummy1_End,series2_End, dummy3_End};
		String[]  categoryKeys = {"50th%ile", "90th%ile"};
		String[] seriesKeys = {"","Series2",""};
		DefaultIntervalCategoryDataset dataset= new DefaultIntervalCategoryDataset(starts, ends);
		dataset.setSeriesKeys(seriesKeys);
		dataset.setCategoryKeys(categoryKeys);

		return dataset;
	}
	private static DefaultIntervalCategoryDataset createDataset3() {
		Number[] dymmy1_Start = new Number[] {null,null};
		Number[] dummy1_End = new Number[] {null,null};
		Number[] series2_Start = new Number[] {150,150};
		Number[] series2_End = new Number[] {200,200};
		Number[] dymmy2_Start = new Number[] {null,null};
		Number[] dummy2_End = new Number[] {null,null};
		
		Number[][] starts = new Number[][]{dymmy1_Start,series2_Start,dymmy2_Start};
		Number[][] ends = new Number[][] {dummy1_End,series2_End,dummy2_End};
		String[]  categoryKeys = {"50th%ile", "90th%ile"};
		String[] seriesKeys = {"","Series3",""};
		DefaultIntervalCategoryDataset dataset= new DefaultIntervalCategoryDataset(starts, ends);
		dataset.setSeriesKeys(seriesKeys);
		dataset.setCategoryKeys(categoryKeys);

		return dataset;
	}
	private static DefaultIntervalCategoryDataset createDataset4() {
		Number[] dymmy1_Start = new Number[] {null,null};
		Number[] dummy1_End = new Number[] {null,null};
		Number[] series2_Start = new Number[] {50,50};
		Number[] series2_End = new Number[] {100,100};
		Number[][] starts = new Number[][]{dymmy1_Start,series2_Start};
		Number[][] ends = new Number[][] {dummy1_End,series2_End, };
		String[]  categoryKeys = {"50th%ile", "90th%ile"};
		String[] seriesKeys = {"","Series4"};
		DefaultIntervalCategoryDataset dataset= new DefaultIntervalCategoryDataset(starts, ends);
		dataset.setSeriesKeys(seriesKeys);
		dataset.setCategoryKeys(categoryKeys);

		return dataset;
	}
	private static JFreeChart createChart(CategoryDataset dataset1, CategoryDataset dataset2, CategoryDataset dataset3,CategoryDataset dataset4) {

		CategoryAxis domainAxis = new CategoryAxis("Percentiles");

		NumberAxis rangeAxis = new NumberAxis("ms1");
		IntervalBarRenderer renderer = new IntervalBarRenderer();
		rendererSettings(renderer);
		//CategoryPlot plot = new CategoryPlot(dataset1, domainAxis, rangeAxis,renderer);
final CategoryPlot plot = new CategoryPlot(dataset1, domainAxis, rangeAxis, renderer) {
            
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
                final CategoryDataset dset3 = getDataset(2);
                if (dset3 != null) {
                    final CategoryItemRenderer renderer2 = getRenderer(2);
                    if (renderer2 != null) {
                        final LegendItem item = renderer2.getLegendItem(2, 1);
                        result.add(item);
                    }
                }
                final CategoryDataset dset4 = getDataset(3);
                if (dset4 != null) {
                    final CategoryItemRenderer renderer2 = getRenderer(3);
                    if (renderer2 != null) {
                        final LegendItem item = renderer2.getLegendItem(3, 1);
                        result.add(item);
                    }
                }
                return result;
            }
            
        };
        
		///////////
		
		JFreeChart chart = new JFreeChart("Percentiles", plot);

	//	final CategoryPlot plot2 = chart.getCategoryPlot();		 
	//	CategoryAxis axis = plot2.getDomainAxis();		
	//	final CategoryDataset dataset2 = createDataset2();
		plot.setDataset(1, dataset2);
		plot.mapDatasetToRangeAxis(1, 1);        
		final ValueAxis axis2 = new NumberAxis("ms2");
		plot.setRangeAxis(1, axis2);
		IntervalBarRenderer renderer2 = new IntervalBarRenderer();
		rendererSettings(renderer2);
		plot.setRenderer(1, renderer2);
		
		plot.setDataset(2, dataset3);
		plot.mapDatasetToRangeAxis(2, 2);      
		final ValueAxis axis3 = new NumberAxis("ms3");
		plot.setRangeAxis(2, axis3);
		IntervalBarRenderer renderer3 = new IntervalBarRenderer();
		rendererSettings(renderer3);
		plot.setRenderer(2, renderer3);
		
		plot.setDataset(3, dataset4);
		plot.mapDatasetToRangeAxis(3, 3);      
		final ValueAxis axis4 = new NumberAxis("ms4");
		plot.setRangeAxis(3, axis4);
		IntervalBarRenderer renderer4 = new IntervalBarRenderer();
		rendererSettings(renderer4);
		plot.setRenderer(3, renderer4);
		
		//plot.getDomainAxis().setVisible(false);

		ChartUtilities.applyCurrentTheme(chart);
		return chart;

	}
	private static void rendererSettings(IntervalBarRenderer renderer){
	//	renderer.setItemLabelFont(new Font("Verdana",Font.PLAIN,2));
	//	renderer.setBaseItemLabelGenerator(new IntervalCategoryItemLabelGenerator());
	//	renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.INSIDE6, TextAnchor.BOTTOM_CENTER));
	//	renderer.setItemMargin(0.3);
	//	renderer.setBaseItemLabelsVisible(true);	
		
	}
	public static JPanel createDemoPanel() {
		JFreeChart chart = createChart(createDataset1(),createDataset2(),createDataset3(),createDataset4());
		return new ChartPanel(chart);
	}

	public static void main(String[] args) {
		JFrame demo = new IntervalBarChartTest(
				"Testing");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}

}