package com.jpoolrunner.testingGraphExamples;
import java.awt.BasicStroke;
import org.apache.commons.math3.distribution.PoissonDistribution;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.util.RelativeDateFormat;
import org.jfree.data.time.Millisecond;

import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;

public class MemoryUsageDemo extends JPanel {
	private TimeSeries rand1;
	private TimeSeries rand2;
	//PoissonDistribution poisson;
	Random random;
	CategoryAxis xAxis;

	public MemoryUsageDemo(int maxAge) {
		super(new BorderLayout());
		/*	long seed = System.currentTimeMillis();
		int lambda = 100;
		poisson= new PoissonDistribution(lambda);
		poisson.reseedRandomGenerator(seed);*/
		// create two series that automatically discard data more than 30
		//seconds old...
		long seed = System.currentTimeMillis();
		random= new Random(seed);
		this.rand1 = new TimeSeries("Random1", Second.class);
		this.rand1.setMaximumItemAge(maxAge);
		this.rand2 = new TimeSeries("Random1", Second.class);
		this.rand2.setMaximumItemAge(maxAge);
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(this.rand1);
		dataset.addSeries(this.rand2);

		JFreeChart chart =	ChartFactory.createTimeSeriesChart("Random numbers", "Time", "Random#", dataset);
		chart.removeLegend();
		XYPlot plotX;
		 plotX      = chart.getXYPlot();
		 DateAxis axis2      = (DateAxis) plotX.getDomainAxis();
		 RelativeDateFormat  format    = new RelativeDateFormat();
		 format.setHourSuffix(":");
		 format.setMinuteSuffix(":");
		 format.setSecondSuffix("");
		 format.setSecondFormatter(new DecimalFormat("0"));
		 long startTime = System.currentTimeMillis();
		  axis2.setDateFormatOverride(format);
		  format.setBaseMillis(startTime);
		chart.setBackgroundPaint(Color.white);
		ValueAxis xaxis = chart.getXYPlot().getDomainAxis();

		DateAxis axis  = (DateAxis)xaxis;
	//	axis.setDateFormatOverride(new SimpleDateFormat("hh:mm:ss"));
		final XYPlot plot2 = (XYPlot)chart.getPlot();
		NumberAxis range2 = (NumberAxis) plot2.getRangeAxis();
		range2.setRange(100,500);
		range2.setLabel("AAAA");
		range2.setTickUnit(new NumberTickUnit(50));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(4, 4, 4, 4),
				BorderFactory.createLineBorder(Color.black)));
		add(chartPanel);
	}

	private void addTotalObservation(double y) {
		this.rand1.add(new Second(), y);
	}

	private void addFreeObservation(double y) {
		this.rand2.add(new Second(), y);
	}
	/**
	 * The data generator.
	 */
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
		 * Adds a new rand2/rand1 memory reading to the dataset.
		 *
		 * @param event the action event.
		 */

		public void actionPerformed(ActionEvent event) {
			//	long f =(long) poisson.sample();
			//	long t = (long)  poisson.sample();
			rand1.add(new Second(), random.nextInt(400));
			rand2.add(new Second(), random.nextInt(400));
			//xAxis.setLabel("");

		}
	}
	/**
	 * Entry point for the sample application.
	 *
	 * @param args ignored.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Random Number Generator");
		MemoryUsageDemo panel = new MemoryUsageDemo(3000);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setBounds(200, 120, 600, 280);
		frame.setVisible(true);
		panel.new DataGenerator(1000).start();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}