package com.jpoolrunner.testingGraphExamples;

import java.awt.Font;
import javax.swing.JFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.*;
import org.jfree.data.xy.DefaultXYDataset;

public class SymbolAxisDemo2 {
   public static void main(String[] args) {
      DefaultXYDataset dataset = new DefaultXYDataset();
      dataset.addSeries("1kb",new double[][]{{1, 2, 3}, {2, 4, 5}});      
      dataset.addSeries("2kb",new double[][]{{4, 5, 6}, {5, 6, 7}});      
      dataset.addSeries("10kb",new double[][]{{5, 3, 5}, {5, 3, 8}});      
      ValueAxis xAxis = new NumberAxis("x");
      ValueAxis yAxis = new SymbolAxis("Bins", new String[]{"A 1", "B 2","C 3", "D 4", "E 5", "F 6"," G 7"," H 8"});
     // yAxis.setTickUnit(new NumberTickUnit(10));
    //  plot.setRangeAxis(rangeAxis);
   //   yAxis.setRange(1, 8);
   //   yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
      
      XYItemRenderer renderer = new XYLineAndShapeRenderer();
      XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
      JFreeChart chart = new JFreeChart("Symbol Axis Demo", new Font("Tahoma", 0, 18), plot, true);
      JFrame frame = new JFrame("XY Plot Demo");
      frame.setContentPane(new ChartPanel(chart));
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setVisible(true);
   }
}