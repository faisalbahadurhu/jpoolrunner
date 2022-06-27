package com.jpoolrunner.testingGraphExamples;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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

public class IntervalBarChartTest0 extends ApplicationFrame {
	public IntervalBarChartTest0(String title) {
		super(title);
		JPanel chartPanel = createDemoPanel();
		chartPanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(chartPanel);
	}
	private static DefaultIntervalCategoryDataset createDataset1() {

		Number[] series1_Start = new Number[] {25,50};
		Number[] series1_End = new Number[] {40,75};
		Number[][] starts = new Number[][]{series1_Start};
		Number[][] ends = new Number[][] {series1_End};
		String[]  categoryKeys = {"50th%ile", "90th%ile"};
		String[] seriesKeys = {"Series1"};
		DefaultIntervalCategoryDataset dataset= new DefaultIntervalCategoryDataset(seriesKeys,categoryKeys,starts, ends);
	
		return dataset;
	}
	
	private static JFreeChart createChart(DefaultIntervalCategoryDataset dataset) {

		CategoryAxis domainAxis = new CategoryAxis("Percentiles");

		NumberAxis rangeAxis = new NumberAxis("ms1");
		IntervalBarRenderer renderer = new IntervalBarRenderer();
		rendererSettings(renderer);
		CategoryPlot plot = new CategoryPlot(dataset, domainAxis, rangeAxis,renderer);
	//	plot.getRangeAxis().setVisible(false);
		JFreeChart chart = new JFreeChart("Percentiles", plot);
		ChartUtilities.applyCurrentTheme(chart);
		return chart;
	}
	/*renderer.setBaseItemLabelGenerator(
		    new StandardCategoryItemLabelGenerator(
		        "{0} {1} {2} {3}", NumberFormat.getInstance()));
		renderer.setBaseItemLabelsVisible(true);*/
	private static void rendererSettings(IntervalBarRenderer renderer){
		renderer.setItemLabelFont(new Font("Verdana",Font.PLAIN,8));
		//renderer.setBaseItemLabelGenerator(new IntervalCategoryItemLabelGenerator());
	//	renderer.setBaseItemLabelGenerator(new IntervalCategoryItemLabelGenerator("{3}-{2} ", NumberFormat.getNumberInstance()));

	/*	renderer.setBaseItemLabelGenerator( new CategoryItemLabelGenerator(){

		    @Override
		    public String generateRowLabel(CategoryDataset dataset, int row) {
		        return "Your Row Text  " + row;
		    }

		    @Override
		    public String generateColumnLabel(CategoryDataset dataset, int column) {
		        return "Your Column Text  " + column;
		    }

		    @Override
		    public String generateLabel(CategoryDataset dataset, int row, int column) {
		        return ""+dataset.getValue(row, column);
		        
		    }


		});*/
		renderer.setBaseItemLabelGenerator( new IntervalCategoryItemLabelGenerator("{3}-{2} ", NumberFormat.getNumberInstance()){
			protected Object[] createItemArray(CategoryDataset dataset,
					int row, int column) {
				Object[] result = new Object[5];
				result[0] = dataset.getRowKey(row).toString();
				result[1] = dataset.getColumnKey(column).toString();
				Number value = dataset.getValue(row, column);
				value=value.intValue()-20;
			//	JOptionPane.showMessageDialog(null, value);
				if (getNumberFormat() != null) {
					result[2] = getNumberFormat().format(value);
				}
				else if (getDateFormat() != null) {
					result[2] = getDateFormat().format(value);
				}

				if (dataset instanceof IntervalCategoryDataset) {
					IntervalCategoryDataset icd = (IntervalCategoryDataset) dataset;
					Number start = icd.getStartValue(row, column);
					Number end = icd.getEndValue(row, column);
				//	end=end.intValue()+20;
					if (getNumberFormat() != null) {
						result[3] = getNumberFormat().format(start);
						result[4] = getNumberFormat().format(end);
					}
					else if (getDateFormat() != null) {
						result[3] = getDateFormat().format(start);
						result[4] = getDateFormat().format(end);
					}
				}
				return result;
			}

		});	
		
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.INSIDE6, TextAnchor.BOTTOM_CENTER));
		renderer.setItemMargin(0.1);
		renderer.setBaseItemLabelsVisible(true);	
	}
	public static JPanel createDemoPanel() {
		JFreeChart chart = createChart(createDataset1());
		return new ChartPanel(chart);
	}

	public static void main(String[] args) {
		JFrame demo = new IntervalBarChartTest0(
				"Testing");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}

}
/*
 protected Object[] createItemArray(CategoryDataset dataset,
                                       int row, int column) {
        Object[] result = new Object[5];
        result[0] = dataset.getRowKey(row).toString();
        result[1] = dataset.getColumnKey(column).toString();
       Number value = dataset.getValue(row, column);
        if (getNumberFormat() != null) {
           result[2] = getNumberFormat().format(value);
       }
       else if (getDateFormat() != null) {
            result[2] = getDateFormat().format(value);
        }

        if (dataset instanceof IntervalCategoryDataset) {
            IntervalCategoryDataset icd = (IntervalCategoryDataset) dataset;
            Number start = icd.getStartValue(row, column);
            Number end = icd.getEndValue(row, column);
            if (getNumberFormat() != null) {
                result[3] = getNumberFormat().format(start);
                result[4] = getNumberFormat().format(end);
            }
            else if (getDateFormat() != null) {
                result[3] = getDateFormat().format(start);
                result[4] = getDateFormat().format(end);
            }
        }
        return result;
    }
 */