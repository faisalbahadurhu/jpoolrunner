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
public class SimpleHistogramDemo2 extends JFrame{

    private SimpleHistogramDataset dataset;

    public SimpleHistogramDemo2(String title) {
        super(title);

        // create the dataset with appropriate bins and some initial data
        this.dataset = new SimpleHistogramDataset("Key");
        dataset.addBin(new SimpleHistogramBin(10, 15, true, false));
        dataset.addBin(new SimpleHistogramBin(15, 20, true, false));
        dataset.addBin(new SimpleHistogramBin(20, 25, true, false));
        dataset.addBin(new SimpleHistogramBin(25, 30, true, false));
        try{

        dataset.addObservations(new double[] {10,12,13,17,16,18,22,25,24,23,21});
        }catch(java.lang.RuntimeException rte){
            System.out.println("some value Out of bin");
        }

        JFreeChart chart = ChartFactory.createHistogram("", 
                "Covers Passed", "Count", dataset, PlotOrientation.VERTICAL, 
                false, false, false);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainZeroBaselineVisible(false);
        plot.getDomainAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
     //   plot.getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        plot.getRangeAxis().setVisible(true);
        
        ChartPanel panel = new ChartPanel(chart);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel);
            }

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