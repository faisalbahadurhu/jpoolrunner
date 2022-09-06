package com.jpoolrunner.displaytestedscenariosonly;
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
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import com.jpoolrunner.clientside.FontSetting;

public class PoolSizeStaticticsPanelFromDisk extends JPanel{
	private Vector<Double> resultStatistics; 
	//saved as double totalThroughput,double avgThroughput,double avgPoolSize){

	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	 String strategyName;

	/**
	 * Create the panel.
	 */

	/**
	 * Create the panel.
	 * @param strategyName 
	 */
	public PoolSizeStaticticsPanelFromDisk(Vector<Double> resultStatistics, String strategyName) {

		// TODO Auto-generated constructor stub
				this.resultStatistics=resultStatistics;
				this.strategyName=  strategyName;
				setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				dataset.addValue(resultStatistics.get(2), " ", "Average");
				
				int lastIndex=strategyName.lastIndexOf('/');//extract TPS name from full path e.g. it would be if form NFBOS2/aaa but following statement will convert it into NFBOS2
				String tpsName=strategyName.substring(0, lastIndex);
				JFreeChart chart = ChartFactory.createBarChart(
						"Average Pool Size: "+tpsName, // chart title
						"Category", // domain axis label
						"Value", // range axis label
						dataset, // data
						PlotOrientation.VERTICAL, // orientation
						true, // include legend
						true, // tooltips?
						false // URLs?
						);
				chart.removeLegend();
				FontSetting fontSetting=new FontSetting(); 
		        chart.setTitle(fontSetting.getTextTitle("Average Pool Size: "+tpsName));
		        CategoryPlot plot = (CategoryPlot) chart.getPlot();
		       
		     //   final CategoryItemRenderer renderer = plot.getRenderer();
		                
		        StackedBarRenderer renderer = new StackedBarRenderer(false);
		        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		        renderer.setBaseItemLabelsVisible(true);
		        plot.setRenderer(renderer);


		        plot.setBackgroundPaint(new Color(0xb8cfe5));	        

		        NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
		        rangeAxis.setTickLabelFont(fontSetting.getTickFont());
		        rangeAxis.setLabelFont(fontSetting.getLabelFont());
		        
		    	CategoryAxis xaxis = plot.getDomainAxis();
		    	//xaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
				 xaxis.setTickLabelFont(fontSetting.getTickFont());
		        xaxis.setLabelFont(fontSetting.getLabelFont());
				
				ChartPanel chartPanel = new ChartPanel(chart, false);
				chartPanel.setPreferredSize(new Dimension(500, 205));
				this.add(chartPanel,BorderLayout.CENTER);

				}
		}

