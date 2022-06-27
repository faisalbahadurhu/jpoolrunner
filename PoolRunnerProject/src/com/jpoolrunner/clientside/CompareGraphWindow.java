package com.jpoolrunner.clientside;

import java.awt.BorderLayout;
import java.awt.Dialog;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import com.jpoolrunner.comparativeanalysis.PoolSizeSeriesComparison;
import com.jpoolrunner.comparativeanalysis.PoolSizeStatisticsComparison;
import com.jpoolrunner.comparativeanalysis.ResponseTimeComparisons;
import com.jpoolrunner.comparativeanalysis.ThroughputSeriesComparisons;
import com.jpoolrunner.comparativeanalysis.ThroughputStatisticsComparison;
import com.jpoolrunner.comparativeanalysis.WaitTimeComparisons;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.Dimension;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.Vector;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.border.LineBorder;

public class CompareGraphWindow extends JDialog{
	private Vector tpsNTestNames;
	private String[]workLoadForBins;
	int panelwidth=500;
	int panelheight=205;
	int JDialogWidth=1100;
	int JDialogHeight=600;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
		//	CompareGraphWindow dialog = new CompareGraphWindow();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	private void freeResources(){
	/*	throughputSeriesComparisonsPanel.makeNull();
		throughputSeriesComparisonsPanel=null;
		throughputGraphPanel = null;
		responseGraphpanel =null;
		waitGraphPanel = null;
		poolSizeGraphPanel =null;*/
		GCRunner gcRunner=new GCRunner();
        gcRunner.setPriority(Thread.MAX_PRIORITY);
        gcRunner.start();
	//	System.runFinalization();
	//	System.gc();
		
	}
	public void prepareGUI(Vector tpsNTestNames,String[] workLoadForBins){
		JPanel throughputGraphPanel = new JPanel();
		JPanel responseGraphpanel = new JPanel();
		JPanel waitGraphPanel = new JPanel();
		JPanel poolSizeGraphPanel = new JPanel();
	//	ThroughputSeriesComparisons  throughputSeriesComparisonsPanel;
		JPanel contentPanel = new JPanel();
		this.tpsNTestNames=tpsNTestNames;
		this.workLoadForBins=workLoadForBins;
		setBounds(150, 50, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		JScrollPane jsp=new JScrollPane(contentPanel);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		getContentPane().add(jsp, BorderLayout.CENTER);
		responseGraphpanel.setBorder(new LineBorder(SystemColor.activeCaption));
		responseGraphpanel.setLayout(new BorderLayout());
		contentPanel.setLayout(new GridLayout(2, 2, 5, 5));
		throughputGraphPanel.setBorder(new LineBorder(SystemColor.activeCaption));
		contentPanel.add(throughputGraphPanel);
		throughputGraphPanel.setLayout(new BorderLayout(0, 0));
		
		
		contentPanel.add(responseGraphpanel);
		
		
		waitGraphPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		contentPanel.add(waitGraphPanel);
		waitGraphPanel.setLayout(new BorderLayout(0, 0));
		
		
	
		poolSizeGraphPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPanel.add(poolSizeGraphPanel);
		poolSizeGraphPanel.setLayout(new BorderLayout(0, 0));
		
	
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
            //	CompareGraphWindow.this.removeAll();
            	CompareGraphWindow.this.setVisible(false);

           // 	freeResources();
            	
            	CompareGraphWindow.this.dispatchEvent(new WindowEvent(
            			CompareGraphWindow.this, WindowEvent.WINDOW_CLOSING));
            }
        });
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		pack();
		addResponseComparison(responseGraphpanel);					
		addWaitComparison(waitGraphPanel);
		addThroughputComparison(throughputGraphPanel);
		addPoolSizeComparison(poolSizeGraphPanel);
		setVisible(true);
	}
	public void makeGUIVisible(){
		setVisible(true);
	}
	public CompareGraphWindow(MainFrame mainFrame,String title){
		super(mainFrame,title);
		setPreferredSize(new Dimension(JDialogWidth, JDialogHeight));
		this.setModal(true);
		 this.addWindowListener(new WindowAdapter() {

	            @Override
	            public void windowClosing(WindowEvent e) {
	            //	removeAll();
	            	setVisible(false);

	            //	freeResources();

	            }
	        });


	}
/*	public CompareGraphWindow(MainFrame mainFrame,String title, Vector tpsNTestNames,String[] workLoadForBins) {// not utilized 
		super(mainFrame,title);
		this.tpsNTestNames=tpsNTestNames;
		this.workLoadForBins=workLoadForBins;
		//setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
	//	this.scenarios=scenarios;
		setPreferredSize(new Dimension(JDialogWidth, JDialogHeight));
		//setPreferredSize(new Dimension(600, 600));
		this.setModal(true);
		 this.addWindowListener(new WindowAdapter() {

	            @Override
	            public void windowClosing(WindowEvent e) {
	            //	freeResources();
	            //	removeAll();

	            	setVisible(false);
	            }
	        });

		setBounds(150, 50, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		JScrollPane jsp=new JScrollPane(contentPanel);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		getContentPane().add(jsp, BorderLayout.CENTER);
		responseGraphpanel.setBorder(new LineBorder(SystemColor.activeCaption));
		responseGraphpanel.setLayout(new BorderLayout(0, 0));
		contentPanel.setLayout(new GridLayout(2, 2, 5, 5));
		throughputGraphPanel.setBorder(new LineBorder(SystemColor.activeCaption));
		contentPanel.add(throughputGraphPanel);
		throughputGraphPanel.setLayout(new BorderLayout(0, 0));
		
		
		contentPanel.add(responseGraphpanel);
		
		
		waitGraphPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		contentPanel.add(waitGraphPanel);
		waitGraphPanel.setLayout(new BorderLayout(0, 0));
		
		
	
		poolSizeGraphPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPanel.add(poolSizeGraphPanel);
		poolSizeGraphPanel.setLayout(new BorderLayout(0, 0));
		
	
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
            	CompareGraphWindow.this.setVisible(false);
            //	CompareGraphWindow.this.removeAll();
            	CompareGraphWindow.this.dispatchEvent(new WindowEvent(
            			CompareGraphWindow.this, WindowEvent.WINDOW_CLOSING));
            }
        });
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		pack();
	}*/
	
	
	public void addResponseComparison(JPanel responseGraphpanel){
	 	ResponseTimeComparisons responseTimeComparisonPanelFiftyPercentile=new ResponseTimeComparisons(tpsNTestNames,panelwidth,panelheight,workLoadForBins,"fifty");
	 	ResponseTimeComparisons responseTimeComparisonPanelNintyPercentile=new ResponseTimeComparisons(tpsNTestNames,panelwidth,panelheight,workLoadForBins,"ninty");

	// 	JScrollPane scrollerForRTGraph=new JScrollPane(getStatisticsCatagoryGraph(responsePanel.getResposeTimeGraphs(),"R"));//R means ResponstTime
	
		
		JScrollPane scrollerForRTGraphFifty=new JScrollPane(responseTimeComparisonPanelFiftyPercentile);
		scrollerForRTGraphFifty.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollerForRTGraphFifty.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		JScrollPane scrollerForRTGraphNinty=new JScrollPane(responseTimeComparisonPanelNintyPercentile);
		scrollerForRTGraphNinty.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollerForRTGraphNinty.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		JTabbedPane responseTimeComparisonTabbedPane = new JTabbedPane();
		responseTimeComparisonTabbedPane.add(scrollerForRTGraphFifty," 50 Percentile Response Times");
		responseTimeComparisonTabbedPane.add(scrollerForRTGraphNinty,"90  Percentile Response Times");
		
		responseGraphpanel.add(responseTimeComparisonTabbedPane,BorderLayout.CENTER);
		responseGraphpanel.revalidate();
		responseGraphpanel.repaint();
		
	}
	public void addThroughputComparison(JPanel throughputGraphPanel){//
		ThroughputSeriesComparisons throughputSeriesComparisonsPanel=new ThroughputSeriesComparisons(tpsNTestNames,panelwidth,panelheight);
		ThroughputStatisticsComparison throughputStatisticsComparisonPanel=new ThroughputStatisticsComparison(tpsNTestNames,panelwidth,panelheight);
		JTabbedPane throughputTabbedPane = new JTabbedPane();
		throughputTabbedPane.add(throughputSeriesComparisonsPanel," Throughput Graph");
		throughputTabbedPane.add(throughputStatisticsComparisonPanel,"Throughput Statistics");
		throughputGraphPanel.add(throughputTabbedPane,BorderLayout.CENTER);
		throughputGraphPanel.revalidate();
		throughputGraphPanel.repaint();
		
	}
	public void addPoolSizeComparison(JPanel poolSizeGraphPanel){//
		PoolSizeSeriesComparison poolSizeSeriesComparisonPanel=new PoolSizeSeriesComparison(tpsNTestNames,panelwidth,panelheight);
		PoolSizeStatisticsComparison poolSizeStatisticsComparisonPanel=new PoolSizeStatisticsComparison(tpsNTestNames,panelwidth,panelheight);
		JTabbedPane poolSizeTabbedPane = new JTabbedPane();
		poolSizeTabbedPane.add(poolSizeSeriesComparisonPanel," Pool Size Graph");
		poolSizeTabbedPane.add(poolSizeStatisticsComparisonPanel,"Pool Size Statistics");
		poolSizeGraphPanel.add(poolSizeTabbedPane,BorderLayout.CENTER);
		poolSizeGraphPanel.revalidate();
		poolSizeGraphPanel.repaint();
		
	}
	
	
	
	public void addWaitComparison(JPanel waitGraphPanel){
		WaitTimeComparisons WaitTimeComparisonsPanelFifty=new WaitTimeComparisons(tpsNTestNames,panelwidth,panelheight,workLoadForBins,"fiftyWt");
		WaitTimeComparisons WaitTimeComparisonsPanelNinty=new WaitTimeComparisons(tpsNTestNames,panelwidth,panelheight,workLoadForBins,"nintyWt");
			
		JScrollPane scrollerForWTGraphFifty=new JScrollPane(WaitTimeComparisonsPanelFifty);
		scrollerForWTGraphFifty.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollerForWTGraphFifty.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		JScrollPane scrollerForWTGraphNinty=new JScrollPane(WaitTimeComparisonsPanelNinty);
		scrollerForWTGraphNinty.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollerForWTGraphNinty.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		JTabbedPane waitTimeComparisonTabbedPane = new JTabbedPane();
		waitTimeComparisonTabbedPane.add(scrollerForWTGraphFifty," 50 Percentile Wait Times");
		waitTimeComparisonTabbedPane.add(scrollerForWTGraphNinty,"90  Percentile Wait Times");
		
		waitGraphPanel.add(waitTimeComparisonTabbedPane,BorderLayout.CENTER);
		waitGraphPanel.revalidate();
		waitGraphPanel.repaint();
		
	}
	



}
