package com.jpoolrunner.testingGraphExamples;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Second;

/**
 * @see http://stackoverflow.com/a/21307289/230513
 */
public class DynamicTimeSeriesChart extends JPanel {

private static final long serialVersionUID = 5128935838291298041L;
private final DynamicTimeSeriesCollection dataset;
private final JFreeChart chart;

public DynamicTimeSeriesChart(final String title) {
	/*dataset = new DynamicTimeSeriesCollection(1, 3600, new Second());
	dataset.setTimeBase(new Second(0, 0, 0, 1, 1, 2016));*/
	
	int nMoments = 1000;
	dataset = new DynamicTimeSeriesCollection(1, nMoments, new Second());
	Calendar c = Calendar.getInstance();
	c.setTime(new Date(0));
	c.add(Calendar.SECOND, -nMoments+2);
	dataset.setTimeBase(new Second(c.getTime()));
   
    dataset.addSeries(new float[1], 0, title);
    
    chart = ChartFactory.createTimeSeriesChart(
        title, "Time", title, dataset, true, true, false);
    final XYPlot plot = chart.getXYPlot();
    DateAxis axis = (DateAxis) plot.getDomainAxis();
    axis.setFixedAutoRange(10000);
  //  axis.setAutoRange(true);
    axis.setDateFormatOverride(new SimpleDateFormat("mm:ss"));
    final ChartPanel chartPanel = new ChartPanel(chart);
    add(chartPanel);
  /*  for(int i=1;i<=1000;i++){
    	 float[] newData = new float[1];
    	    newData[0] = (float)1.1;
        //	dataset.addValue(0, i, (float) (1.1));
        	dataset.advanceTime();
        	 dataset.appendData(newData);
        }*/
}

public void update(float value) {
    float[] newData = new float[1];
    newData[0] = value;
    dataset.advanceTime();
    dataset.appendData(newData);
}

public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {

        @Override
        public void run() {
            JFrame frame = new JFrame("testing");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            final DynamicTimeSeriesChart chart
                = new DynamicTimeSeriesChart("Alternating data");
            frame.add(chart);
            frame.pack();
            Timer timer = new Timer(1000, new ActionListener() {
                private boolean b;

                @Override
                public void actionPerformed(ActionEvent e) {
                    chart.update(b ? 2 : 3);
                   // chart.update(2);
                    b = !b;
                }
            });
            frame.setVisible(true);
            timer.start();

        }
    });
}
}