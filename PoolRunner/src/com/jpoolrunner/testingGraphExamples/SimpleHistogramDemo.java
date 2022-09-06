package com.jpoolrunner.testingGraphExamples;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.statistics.SimpleHistogramBin;
import org.jfree.data.statistics.SimpleHistogramDataset;

/**
* A histogram with dynamically added data.
*/
public class SimpleHistogramDemo extends JFrame implements ActionListener {
    
    private SimpleHistogramDataset dataset;
    
    public SimpleHistogramDemo(String title) {
        super(title);
        
        // create the dataset with appropriate bins and some initial data
        this.dataset = new SimpleHistogramDataset("Key");
      /*  dataset.addBin(new SimpleHistogramBin(10, 15, true, false));
        dataset.addBin(new SimpleHistogramBin(15, 20, true, false));
        dataset.addBin(new SimpleHistogramBin(20, 25, true, false));
        dataset.addBin(new SimpleHistogramBin(25, 30, true, false));*/
        SimpleHistogramBin s1=new SimpleHistogramBin(95, 101, true, false);
        SimpleHistogramBin s2=new SimpleHistogramBin(101, 107, true, false);
        SimpleHistogramBin s3=new SimpleHistogramBin(107, 113, true, false);
        dataset.addBin(s1);
        dataset.addBin(s2);
        dataset.addBin(s3);
        dataset.setAdjustForBinSize(false);
        try{
        	 dataset.addObservations(new double[] {95.0, 101.0, 100.0,98,98,99,99,99,99,99,99,99});
        	
     //   dataset.addObservations(new double[] {10,12,13,17,16,18,22,25,24,23,21});
        }catch(java.lang.RuntimeException rte){
        	System.out.println("some value Out of bin");
        }
        
        // create the chart with integer axis labels
        JFreeChart chart = ChartFactory.createHistogram("", 
                "Covers Passed", "Count", dataset, PlotOrientation.VERTICAL, 
                false, false, false);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainZeroBaselineVisible(false);
        plot.getDomainAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        plot.getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        // set up the UI
        ChartPanel panel = new ChartPanel(chart);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel);
        JButton button = new JButton("Add one more observation");
       button.addActionListener(this);
      getContentPane().add(button, BorderLayout.SOUTH);
 //     System.out.println("Total items="+(s1.getItemCount()+s2.getItemCount()+s3.getItemCount()));
      System.out.println("Total items="+(dataset.getItemCount(0)));

    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // add a random observation in the range 0 to 3
        this.dataset.addObservation((int) (Math.random()* 4));
     //   this.dataset.addObservation((int) 10);

    }

    /**
     * Starting point for the demo.
     * 
     * @param args  ignored 
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SimpleHistogramDemo demo = new SimpleHistogramDemo(
                        "JFreeChart: SimpleHistogramDemo");
                demo.pack();
                demo.setVisible(true);
            }
        });
    }
}