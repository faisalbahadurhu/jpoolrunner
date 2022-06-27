
package com.jpoolrunner.testingGraphExamples;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.util.RelativeDateFormat;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Second;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


public class TestDynamicTimeSeries extends ApplicationFrame {

	private static final String TITLE = "Dynamic Series";

	private static final int COUNT = 2 * 60;

	private static final Random random = new Random();
	private Timer timer;

	public TestDynamicTimeSeries(final String title) {
		super(title);
		final DynamicTimeSeriesCollection dataset =
				new DynamicTimeSeriesCollection(1, COUNT, new Second());
		dataset.setTimeBase(new Second(0, 0, 0, 1, 1, 2016));
		dataset.addSeries(gaussianData(), 0, "Gaussian data");
		JFreeChart chart = createChart(dataset);

		DateAxis dateAxis      = (DateAxis) chart.getXYPlot().getDomainAxis();
		RelativeDateFormat  format    = new RelativeDateFormat();
		format.setHourSuffix(":");
		format.setMinuteSuffix(":");
		format.setSecondSuffix("");
		format.setSecondFormatter(new DecimalFormat("0"));
		long startTime = System.currentTimeMillis();
		dateAxis.setDateFormatOverride(format);
		format.setBaseMillis(startTime);

		this.add(new ChartPanel(chart), BorderLayout.CENTER);
		JPanel btnPanel = new JPanel(new FlowLayout());

		timer = new Timer(1000, new ActionListener() {

			float[] newData = new float[1];

			@Override
			public void actionPerformed(ActionEvent e) {
				newData[0] = randomValue();
				dataset.advanceTime();
				dataset.appendData(newData);
			}
		});
	}

	private float randomValue() {
		return (float) (random.nextGaussian() * 100 / 3);
	}

	private float[] gaussianData() {
		float[] a = new float[COUNT];
		for (int i = 0; i < a.length; i++) {
			a[i] = randomValue();
		}
		return a;
	}

	private JFreeChart createChart(final XYDataset dataset) {
		final JFreeChart result = ChartFactory.createTimeSeriesChart(
				TITLE, "hh:mm:ss", "Y", dataset, true, true, false);
		final XYPlot plot = result.getXYPlot();
		ValueAxis domain = plot.getDomainAxis();
		domain.setAutoRange(true);
		ValueAxis range = plot.getRangeAxis();
		range.setRange(-100, 100);
		return result;
	}

	public void start() {
		timer.start();
	}

	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				TestDynamicTimeSeries demo = new TestDynamicTimeSeries(TITLE);
				demo.pack();
				RefineryUtilities.centerFrameOnScreen(demo);
				demo.setVisible(true);
				demo.start();
			}
		});
	}
}