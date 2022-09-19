package com.jpoolrunner.clientside;
import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.IntervalCategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.IntervalBarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;

import com.jpoolrunner.clientside.ResponsePanel.HistogramGraph;
import com.jpoolrunner.clientside.WaitPanel.HistogramGraph2;
import com.jpoolrunner.diskIO.DataSaver;
import com.jpoolrunner.frequencyDistribution.ConstantDistributor;
import com.jpoolrunner.frequencyDistribution.FrequencyDistributorInterface;
import com.jpoolrunner.frequencyDistribution.GuassianDistributor;
import com.jpoolrunner.frequencyDistribution.ManualDistributor;
import com.jpoolrunner.frequencyDistribution.ParetoDistributor;
import com.jpoolrunner.frequencyDistribution.PoissonDistributor;
import com.jpoolrunner.frequencyDistribution.UniformDistributor;
import com.jpoolrunner.job.CpuBoundTask;
import com.jpoolrunner.job.IoBoundTask;
import com.jpoolrunner.job.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.jfree.data.xy.XYSeries;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class Scenario {
	ObjectOutputStream outStream;
	ObjectInputStream inStream=null;
    JProgressBar progressBar = new JProgressBar(0,100);
    
   // SplashWindow splash=null;//new SplashWindow();

	 class WarmupReceiver extends Thread{
		
		public void run(){
		try{
			do{// it will quit when server send it "stop" signal
				Runnable obj=(Runnable)inStream.readObject();
				
			}while(true);
			
			
			
		}catch(ClassCastException e){
		//	System.out.println("ClassCastException in  WarmupReceiver");
		}
	 	catch(Exception e){
		//	System.out.println("Exception in  WarmupReceiver");

	 	}
	//	JOptionPane.showMessageDialog(null, "Warmup completed and WarmupReceiver terminating ");
		mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

		mainFrame.getScenarioPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		warm=false;

		start.setEnabled(true);

		}
	}
 class WarmupRequestSender extends Thread{
	//ObjectOutputStream outStream;
	
	public void run(){
	try{
		int barvalue=20;
		for(int i=1;i<=5;i++){
	         progressBar.setValue(barvalue);
	        // progressBar.updat
	         barvalue+=20;
	       //  progressBar.updateUI();

		for(int j=1;j<=100;j++){
			if(i==5) outStream.writeObject((Task)(new CpuBoundTask(1)));
			else outStream.writeObject((Task)(new IoBoundTask(100)));
			outStream.reset();
			outStream.flush();
		}
		Thread.sleep(900);
		}
		outStream.writeObject("stop");
		outStream.reset();
		outStream.flush();
			
		}catch(Exception e){
			System.out.println("Exception in  WarmupRequestSender");

		}
	}}
 class SplashWindow extends JWindow
 {
     public SplashWindow()
     {
         super(mainFrame);
      final   JLabel l = new JLabel(" Warm up "+getStrategyName()+"...Plz wait!");
      l.setBackground(Color.WHITE);
      l.setForeground(Color.blue);
      l.setBorder(BorderFactory.createRaisedBevelBorder());
      JPanel panel= (JPanel) getContentPane();
      panel.setBorder(BorderFactory.createRaisedBevelBorder());

      panel.setLayout(new BorderLayout());
        panel.add(l, BorderLayout.CENTER);
      //   progressBar.setValue(0);
     //   UIManager.put("ProgressBar.background", Color.ORANGE);
        UIManager.put("ProgressBar.foreground", Color.BLUE);
        progressBar.setForeground(Color.blue);
        UIManager.put("ProgressBar.selectionBackground", Color.RED);
        UIManager.put("ProgressBar.selectionForeground", Color.GREEN);
         progressBar.setStringPainted(true);
         

        panel.add(progressBar, BorderLayout.SOUTH);// 
         pack();
         Dimension screenSize =
           Toolkit.getDefaultToolkit().getScreenSize();
         Dimension labelSize = l.getPreferredSize();
         setLocation(screenSize.width/2 - (labelSize.width/2),
                     screenSize.height/2 - (labelSize.height/2));
        // UIManager.put(ProgressBar., value)
         addMouseListener(new MouseAdapter()
             {
                 public void mousePressed(MouseEvent e)
                 {
                     setVisible(false);
                     dispose();
                 }
             });
         final Runnable closerRunner = new Runnable()
             {
                 public void run()
                 {
                     setVisible(false);
                     dispose();
                 }
             };
         Runnable waitRunner = new Runnable()
             {
                 public void run()
                 {
                     try
                         {
                    	 while(warm==true){
                             Thread.sleep(100);

                    	 }
                    	 
                    	 SwingUtilities.invokeAndWait(closerRunner);
                         }
                     catch(Exception e)
                         {
                             e.printStackTrace();
                             // can catch InvocationTargetException
                             // can catch InterruptedException
                         }
                 }
             };
         setVisible(true);
         Thread splashThread = new Thread(waitRunner, "SplashThread");
         splashThread.start();
     }
 }
	boolean warm=true;
	final MainFrame mainFrame;
	
	RequestfrequencyPanel requestFrequencyPanel;
	Frequencyholder frequencyholder;
	Vector throughputs;
	NonblockingCounter nonBlockingCounter;// incremented by RecieverX on reciving responses and plotted by ThroughputPanel
	NonblockingCounter tracerForCompareButton;// All panels increment it only once b4 stopping and since there are 4 Graphics panels so when its value is four then it means that all panels have been stopped and compare button should be enabled now
	Producer producer;
	ThroughputPanel throughputPanel;
	ResponsePanel responsePanel;
	WaitPanel waitPanel;
	PoolSizePanel poolSizePanel;
	AcceptanceLatencyPanel acceptanceLatencyPanel;
	RecieverX reciever;
	SharedBoolean sharedBoolean;//this boolean would be true only in ResponsePanel when ResponsePanel stops it on drawing all readoObjects by ReciverX and then ThroughputPanel etc would be stopped 
	private	String strategyName;
	private	boolean usePreviousScenario=false;
	private Scenario previousScenario=null;
	private boolean fixedIntensity=true;//these are default values of Dialog visible to user
	private	String fixIntensityValue="0.25";
	private	String lowerIntensity="0.25",upperIntensity="2.0";
	private	boolean manualLoad=true;
	private	boolean simulationTimeFix=true;//ie user stop it or not ie user will run simulation for the specified time
	private	String simulationTime="5";//5 minutes by default
	private  DynamicPanel panel;
	private TabPanel tabPanel;
	JButton warmup,start,stop,save;
	public boolean isSaveButtonEnabled() {
		return save.isEnabled();
	}
	public boolean isStartButtonEnabled() {
		return start.isEnabled();
	}
	public boolean isStopButtonEnabled() {
		return stop.isEnabled();
	}
	public boolean isWarmupButtonEnabled() {
		return warmup.isEnabled();
	}
	boolean stopDisable=false;
	boolean startButtonEnable,stopButtonEnable,saveButtonEnable;
	Vector workloadForBins=new Vector(3);
	public boolean isStartButtonEnable() {
		return startButtonEnable;
	}
	public void setStartButtonEnable(boolean startButtonEnable) {
		this.startButtonEnable = startButtonEnable;
	}
	public boolean isStopButtonEnable() {
		return stopButtonEnable;
	}
	public void setStopButtonEnable(boolean stopButtonEnable) {
		this.stopButtonEnable = stopButtonEnable;
	}
	public boolean isSaveButtonEnable() {
		return saveButtonEnable;
	}
	public void setSaveButtonEnable(boolean saveButtonEnable) {
		this.saveButtonEnable = saveButtonEnable;
	}
	StopWatch stopWatch;
	JButton compare;// enabled from ThroughputPanel when all panels are stop and at value of Atomic vriable becomes 4 because there are 4 panels
	JComboCheckBox compareCheckBox;// enabled from ThroughputPanel when all panels are stop and at value of Atomic vriable becomes 4 because there are 4 panels
	Vector namesinCheckBox=new Vector();// it will contain names of prvious strategies that has been simulated so far this vector is embedded in compareCheckBox 
	JButton loadTestButton;//enabled from ThroughputPanel
	BooleanChecker sendnotSendingProtocol;//for start button it checks whether it is clicked or not: only at the time of request sending it will be false 
	BooleanChecker sleepTime;// it is used in responsePanel and WaitPanel it is true 
	JLabel responserecievedValue=new JLabel("");
	JLabel reqSentvalue=new JLabel("");
	JLabel loadValue=new JLabel("10 req/sec");
	PoolSizeGetter poolSizer;//this is a shared variable between RecieverX and PoolSizePanel only.
	int oneKb,tenKb,hundredKb,thousandKb,twoThousandKb;//static load
	int tenMilliSecond,fiftyMilliSecond,hundredMilliSecond;//dynamic load
	Vector<Integer> staticLoadValues;//All above values are extracted from this vector.U can use this vector also instead of above variables 

	Vector<String> loadOntheServer=new Vector<String>();//it contains io and cpu bound jobs intensity with first letter that indicate whether it is cpu bound or Io Bound //initially it stores 100 jobs based on input dialog box and shuffle it

	Vector<Integer> requestFrequencies=new Vector<Integer>(1000);//it will store request frequencies
	Vector<String> realLoad=new Vector<String>();// uses in case of previuos scenario ture this vector will be filled at the time of job xtraction in LoadGenerationEngine and later utilizes as a previous strtegy load
	FrequencyDistributorInterface randomNumberDistributor;// this is 

	JButton responsePanelStatisticsButton=new JButton("Display Statistics");
	JButton buttonWTStatisticsGraph=new JButton("Display");

	JButton buttonRTStatisticsTable=new JButton("Display");
	JPanel responsePanelStatisticsNorthPanel;
	JPanel responseStatisticsPanel=new JPanel(new BorderLayout());
	JPanel responseStatisticsCenterPanel;

	JButton poolSizePanelStatisticsButton=new JButton("Display Statistics");
	JPanel poolSizeStatisticsPanel=new JPanel(new BorderLayout());
	JPanel buttonPoolSizePanel;
	JPanel poolSizeAvgStatisticsPanel;

	JButton throughputPanelStatisticsButton=new JButton("Display Statistics");;
	JPanel throughputStatisticsPanel=new JPanel(new BorderLayout());
	JPanel buttonthroughputPanel;
	JPanel throughputCenterStatisticsPanel;

	JButton waitPanelStatisticsButton=new JButton("Display Statistics");
	JPanel buttonwaitPanel;
	JPanel waitStatisticsPanel=new JPanel(new BorderLayout());
	JPanel waitAvgStatisticsPanel;
	boolean scenarioSavedOrNot=false;
	boolean startButtonActiveOrNot=true;
	boolean stopButtonActiveOrNot=false;
	
	public boolean isStartButtonActiveOrNot() {
		return startButtonActiveOrNot;
	}
	public boolean isStopButtonActiveOrNot() {
		return stopButtonActiveOrNot;
	}
	public boolean isScenarioSavedOrNot() {
		return scenarioSavedOrNot;
	}
	int staticLoadPercentage,dynamicLoadPercentage;
	String workloadPattern;//ie Poisson Or Guassian or Uniform or Heavy tail etc whichever is selected through radiobutton this string is set to that Distribution
	double lambdaForPoisson,meanForGuassian,standardDeviationForGuassin;
	int startForManualDistribution,endForManualDistribution,decreaseForManualDistribution;
	private int initialThreadPoolSize;

	public int getStartForManualDistribution() {
		return startForManualDistribution;
	}
	public void setStartForManualDistribution(int startForManualDistribution) {
		this.startForManualDistribution = startForManualDistribution;
	}
	public int getEndForManualDistribution() {
		return endForManualDistribution;
	}
	public void setEndForManualDistribution(int endForManualDistribution) {
		this.endForManualDistribution = endForManualDistribution;
	}
	public int getDecreaseForManualDistribution() {
		return decreaseForManualDistribution;
	}
	public void setInitialThreadPoolSize(int poolSize){//Since this is the scenario of a particular strategy when this strategy is initialized at the server side then it will send the initial pool size and it is then set in the scenario
		this.initialThreadPoolSize=poolSize;
		this.poolSizePanel.setInitialThreadPoolSize(poolSize);
	}
	public void setDecreaseForManualDistribution(int decreaseForManualDistribution) {
		this.decreaseForManualDistribution = decreaseForManualDistribution;
	}
	public JFreeChart getFrequencyChart(){return requestFrequencyPanel.getChart(); }
	public JFreeChart getThroughputChart(){return throughputPanel.getChart(); }
	public JFreeChart getResponseChart(){return responsePanel.getChart(); }
	public JFreeChart getWaitChart(){return waitPanel.getChart(); }
	public JFreeChart getPoolSizeChart(){return poolSizePanel.getChart(); }
	public JFreeChart getAcceptanceLatencyChart(){return acceptanceLatencyPanel.getChart(); }



	int nForUniform;
	int constantDistibutor;
	private double scalePareto;
	private double shapePareto;
	public double getLambdaForPoisson() {
		return lambdaForPoisson;
	}
	public double getMeanForGuassian() {
		return meanForGuassian;
	}
	public double getStandardDeviationForGuassin() {
		return standardDeviationForGuassin;
	}
	public int getnForUniform() {
		return nForUniform;
	}
	public int getOneKb() {
		return oneKb;
	}
	public void setOneKb(int oneKb) {
		this.oneKb = oneKb;
	}
	public int getTenKb() {
		return tenKb;
	}
	public void setTenKb(int tenKb) {
		this.tenKb = tenKb;
	}
	public int getHundredKb() {
		return hundredKb;
	}
	public void setHundredKb(int hundredKb) {
		this.hundredKb = hundredKb;
	}
	public int getThousandKb() {
		return thousandKb;
	}
	public void setThousandKb(int thousandKb) {
		this.thousandKb = thousandKb;
	}
	public int getTwoThousandKb() {
		return twoThousandKb;
	}
	public void setTwoThousandKb(int twoThousandKb) {
		this.twoThousandKb = twoThousandKb;
	}
	public int getTenMilliSecond() {
		return tenMilliSecond;
	}
	public void setTenMilliSecond(int tenMilliSecond) {
		this.tenMilliSecond = tenMilliSecond;
	}
	public int getFiftyMilliSecond() {
		return fiftyMilliSecond;
	}
	public void setFiftyMilliSecond(int fiftyMilliSecond) {
		this.fiftyMilliSecond = fiftyMilliSecond;
	}
	public int getHundredMilliSecond() {
		return hundredMilliSecond;
	}
	public void setHundredMilliSecond(int hundredMilliSecond) {
		this.hundredMilliSecond = hundredMilliSecond;
	}
	public Vector<String> getLoadOntheServer() {
		return loadOntheServer;
	}


	public XYSeries getResposePanelSeries(){
		return responsePanel.getSeries();
	}
	public XYSeries getWaitPanelSeries(){
		return waitPanel.getSeries();
	}
	public XYSeries getPoolSizePanelSeries(){
		return poolSizePanel.getSeries();
	}
	public XYSeries getThroughputPanelSeries(){
		return throughputPanel.getThroughputPanelSeries();
	}
	public int getConstantDistibutor() {
		return constantDistibutor;
	}

	public void setConstantDistibutor(int constantDistibutor) {
		this.constantDistibutor = constantDistibutor;
	}

	public void setRequestSenderLoadAttributes(){

		this.requestFrequencyPanel.requestSender.setRequestSenderLoadAttributes(randomNumberDistributor,requestFrequencies,loadOntheServer,realLoad,this.usePreviousScenario);
	}

	//	public void setRequestSenderFrequencyAndLoadAttributes(Vector<Integer> requestFrequencies,Vector<Task> realLoad){
	void setRequestSenderFrequencyAndLoadAttributes(){
		this.requestFrequencyPanel.requestSender.setRequestSenderFrequencyAndLoadAttributes(this.requestFrequencies, this.realLoad,this.usePreviousScenario,this.stopWatch);
	}
	public Vector<Integer> getRequestFrequencies() {
		return requestFrequencies;
	}

	public Vector<String> getRealLoad() {
		return realLoad;
	}

	public void setLambdaForPoisson(double lambdaForPoisson) {
		this.lambdaForPoisson = lambdaForPoisson;
	}
	public void setMeanForGuassian(double meanForGuassian) {
		this.meanForGuassian = meanForGuassian;
	}

	public void setStandardDeviationForGuassin(double standardDeviationForGuassin) {
		this.standardDeviationForGuassin = standardDeviationForGuassin;
	}
	public void setnForUniform(int nForUniform) {
		this.nForUniform = nForUniform;
	}
	public void setScaleForPareto(double scale){
		this.scalePareto=scale;
	}
	public void setShapeForPareto(double shape){
		this.shapePareto=shape;
	}

	public void setWorkloadPattern(String workloadPattern) {
		this.workloadPattern = workloadPattern;
	}
	public String getWorkloadPattern() {
		return this.workloadPattern ;
	}
	public void prepareResponseNWaitPanelgraphics(){
		this.responsePanel.preparegraphics();
		this.waitPanel.preparegraphics();
	}
	public void setWorkloadForBins(){
		this.responsePanel.setWorkloadForBins(workloadForBins);
		this.waitPanel.setWorkloadForBins(workloadForBins);
	}
	public void setWorkloadForBins(String[] workloadForBins){
		//Collections.addAll(this.workloadForBins,workloadForBins);
		for(int i=0;i<workloadForBins.length;i++){
			this.workloadForBins.add(new Integer(Integer.parseInt(workloadForBins[i])));
		}
		this.responsePanel.setWorkloadForBins(this.workloadForBins);
		this.waitPanel.setWorkloadForBins(this.workloadForBins);
		//Collections.addAll(this.realLoad, realLoad);/
	}
	public void prepareWorkLoad(){
		//following 5 loops will populate loadOntheServer with static load
		//	JOptionPane.showMessageDialog(null,oneKb+tenKb+hundredKb+thousandKb+twoThousandKb);
		//	JOptionPane.showMessageDialog(null,tenMilliSecond+fiftyMilliSecond+hundredMilliSecond);
		final int oneKbSleep=100,tenKbSleep=200,hundredKbSleep=300,thousandKbSleep=500,twoThousandKbSleep=1000,tenMilliSecondRun=1,fiftyMilliSecondRun=2,hundredMilliSecondRun=3;
		for(int i=0;i<((int)(((float)oneKb/100)*this.staticLoadPercentage));i++)
			loadOntheServer.add("I"+oneKbSleep);//ie 250 milliseconds
		for(int i=0;i<((int)(((float)tenKb/100)*this.staticLoadPercentage));i++)
			loadOntheServer.add("I"+tenKbSleep);//ie 5000 milliseconds
		for(int i=0;i<((int)(((float)hundredKb/100)*this.staticLoadPercentage));i++)
			loadOntheServer.add("I"+hundredKbSleep);//ie 750 milliseconds
		for(int i=0;i<((int)(((float)thousandKb/100)*this.staticLoadPercentage));i++)
			loadOntheServer.add("I"+thousandKbSleep);//ie 1000 milliseconds
		for(int i=0;i<((int)(((float)twoThousandKb/100)*this.staticLoadPercentage));i++)
			//	loadOntheServer.add("I"+1250);//ie 1250 milliseconds
			loadOntheServer.add("I"+twoThousandKbSleep);//ie 1250 milliseconds


		//following 3 loops will populate loadOntheServer with dynamic load
		for(int i=0;i<((int)(((float)tenMilliSecond/100)*this.dynamicLoadPercentage));i++)
			loadOntheServer.add("C"+tenMilliSecondRun);//see CpuBoundTask.java code to clarify it
		for(int i=0;i<((int)(((float)fiftyMilliSecond/100)*this.dynamicLoadPercentage));i++)
			loadOntheServer.add("C"+fiftyMilliSecondRun);//see CpuBoundTask.java code to clarify it
		for(int i=0;i<((int)(((float)hundredMilliSecond/100)*this.dynamicLoadPercentage));i++)
			loadOntheServer.add("C"+hundredMilliSecondRun);//see CpuBoundTask.java code to clarify it
		if(oneKb>0)workloadForBins.add(new Integer(oneKbSleep));//100ms	
		if(tenKb>0)workloadForBins.add(new Integer(tenKbSleep));//200ms	
		if(hundredKb>0)workloadForBins.add(new Integer(hundredKbSleep));//300ms	
		if(thousandKb>0)workloadForBins.add(new Integer(thousandKbSleep));	//400ms
		if(twoThousandKb>0)workloadForBins.add(new Integer(twoThousandKbSleep));	//2000ms
		if(tenMilliSecond>0)workloadForBins.add(new Integer(tenMilliSecondRun));	//1
		if(fiftyMilliSecond>0)workloadForBins.add(new Integer(fiftyMilliSecondRun));	//2
		if(hundredMilliSecond>0)workloadForBins.add(new Integer(hundredMilliSecondRun));	//3

		
		Collections.shuffle(loadOntheServer);

		/*	Vector clone=(Vector)loadOntheServer.clone();
		Iterator iterator= loadOntheServer.iterator();
		//JOptionPane.showMessageDialog(null,"vector size="+loadOntheServer.size());
		String s="";int count=0;
		while(iterator.hasNext()){
			String jobIntensity= (String)iterator.next();
			count++;
			s+=count+" "+jobIntensity+",";
			//System.out.println(job.getDelay());
		}
		//JOptionPane.showMessageDialog(null,s);
		//Collections.shuffle(loadOntheServer);

			Iterator iterator2= loadOntheServer.iterator();
	String s2="";int count2=0;
	while(iterator2.hasNext()){
		Task job= (Task)iterator2.next();
		count2++;
		s2+=count2+" "+job.getDelay()+",";
		//System.out.println(job.getDelay());
	}
	JOptionPane.showMessageDialog(null,s2);*/


	}
	public Scenario getThisScenario(){
		return this;
	}
	public void prepareLoadPattern(){//generate request frequencies by this method
		/*
	 int nForUniform;
	double lambdaForPoisson,meanForGuassian,standardDeviationForGuassin;

		 */
		if(workloadPattern.equalsIgnoreCase("Poisson")){
			PoissonDistributor poissonDistributor=new PoissonDistributor(lambdaForPoisson);
			randomNumberDistributor=poissonDistributor;

		}
		else if(workloadPattern.equalsIgnoreCase("Guassian")){
			GuassianDistributor guassianDistributor=new GuassianDistributor(meanForGuassian,standardDeviationForGuassin);
			randomNumberDistributor=guassianDistributor;
		}
		else if(workloadPattern.equalsIgnoreCase("Uniform")){
			UniformDistributor uniformDistributor=new UniformDistributor(nForUniform);
			randomNumberDistributor=uniformDistributor;
		}
		else if(workloadPattern.equalsIgnoreCase("Heavy")){
			ParetoDistributor paretoDistributor=new ParetoDistributor(scalePareto,shapePareto);
			randomNumberDistributor=paretoDistributor;
			//its variable not defined yet
		}
		else if(workloadPattern.equalsIgnoreCase("Constant")){
			//its variable not defined yet
			ConstantDistributor constantDistributorObject=new ConstantDistributor(this.constantDistibutor);
			randomNumberDistributor=constantDistributorObject;
		}//	int startForManualDistribution,endForManualDistribution,decreaseForManualDistribution;

		else if(workloadPattern.equalsIgnoreCase("Manual")){
			ManualDistributor manualDistributor=new ManualDistributor(startForManualDistribution,endForManualDistribution,decreaseForManualDistribution);
			randomNumberDistributor=manualDistributor;
		}



	}


	public void setStaticLoadPercentage(int staticLoadPercentage) {
		this.staticLoadPercentage = staticLoadPercentage;
		//	JOptionPane.showMessageDialog(null,"Static%="+this.staticLoadPercentage);

	}
	public void setDynamicLoadPercentage(int dynamicLoadPercentage) {
		this.dynamicLoadPercentage = dynamicLoadPercentage;
		//	JOptionPane.showMessageDialog(null,"Dynamic%="+this.dynamicLoadPercentage);

	}
	public Vector<Integer> getStaticLoadValues() {
		return staticLoadValues;
	}
	public void setStaticLoadValues(Vector<Integer> staticLoadValues) {
		this.staticLoadValues = staticLoadValues;
		if(this.staticLoadValues!=null){
			oneKb=this.staticLoadValues.get(0);
			tenKb=this.staticLoadValues.get(1);
			hundredKb=this.staticLoadValues.get(2);
			thousandKb=this.staticLoadValues.get(3);
			twoThousandKb=this.staticLoadValues.get(4);
			//JOptionPane.showMessageDialog(null,oneKb+tenKb+hundredKb+thousandKb+twoThousandKb);
		}
	}
	public Vector<Integer> getDynamicLoadValues() {
		return dynamicLoadValues;
	}
	public void setDynamicLoadValues(Vector<Integer> dynamicLoadValues) {
		this.dynamicLoadValues = dynamicLoadValues;
		if(dynamicLoadValues!=null){
			tenMilliSecond=this.dynamicLoadValues.get(0);
			fiftyMilliSecond=this.dynamicLoadValues.get(1);
			hundredMilliSecond=this.dynamicLoadValues.get(2);
			//JOptionPane.showMessageDialog(null,tenMilliSecond+fiftyMilliSecond+hundredMilliSecond);

		}
	}
	Vector<Integer> dynamicLoadValues;//All above values are extracted from this vector.U can use this vector also instead of above variables
	private String previousScenarioName="";
	private int numberOfTests;
	public void setButton(JButton loadTestButton){this.loadTestButton=loadTestButton;
	throughputPanel.setButton(this.loadTestButton);
	
	}
	public void setBooleanChecker(BooleanChecker sendnotSendingProtocol){
		this.sendnotSendingProtocol=sendnotSendingProtocol;
		throughputPanel.setBooleanChecker(this.sendnotSendingProtocol);
	}

	public Scenario(final MainFrame mainFrame, ObjectOutputStream outStream,ObjectInputStream inStream, String selectedStrategy){
		this.mainFrame=mainFrame;
		startButtonEnable=true;
		stopButtonEnable=false;
		saveButtonEnable=false;

	//	tpsMenu=new JMenu(selectedStrategy);		
		responsePanelStatisticsButton.setEnabled(false);
		buttonWTStatisticsGraph.setEnabled(false);
		buttonRTStatisticsTable.setEnabled(false);
		poolSizePanelStatisticsButton.setEnabled(false);
		throughputPanelStatisticsButton.setEnabled(false);
		waitPanelStatisticsButton.setEnabled(false);
		this.strategyName=selectedStrategy.replaceAll("\\s+","");
		oneKb=tenKb=hundredKb=thousandKb=twoThousandKb=tenMilliSecond=fiftyMilliSecond=hundredMilliSecond=0;

		poolSizer=new PoolSizeGetter();
		compare=new JButton("COMPARE");
		compare.setEnabled(false);
		namesinCheckBox.add(new JCheckBox("Debugging", false));
		compareCheckBox=new JComboCheckBox(namesinCheckBox);
		compareCheckBox.setEnabled(false);
		this.sharedBoolean=new SharedBoolean(false);//shared between ResponsePanel Throughput and other statistics panels and only ResponsePanel would make it true when stop ...so all other statistics panels stop themelves
		this.outStream=outStream;
		this.inStream=inStream;
		frequencyholder=new Frequencyholder();
		throughputs=new Vector(20);// used to store throughput per seconds.
		/* if(this.manualLoad)System.out.println("Manual load is set");
 else System.out.println("Auto load is set");*/
		this.requestFrequencyPanel=new  RequestfrequencyPanel(this.reqSentvalue,this.outStream,this.inStream,frequencyholder,throughputs,this.manualLoad,loadValue);
		nonBlockingCounter=new NonblockingCounter();
		tracerForCompareButton=new NonblockingCounter();
		producer=new Producer();																		                                         /*,,,*/

		throughputPanel=new ThroughputPanel(nonBlockingCounter,tracerForCompareButton,this.sharedBoolean,this.compareCheckBox,this.compare,throughputs,this.strategyName,responsePanelStatisticsButton,poolSizePanelStatisticsButton,throughputPanelStatisticsButton,waitPanelStatisticsButton,this,buttonRTStatisticsTable,buttonWTStatisticsGraph);
		//
		sleepTime=new BooleanChecker();

		responsePanel=new ResponsePanel(nonBlockingCounter,tracerForCompareButton,this.sharedBoolean,this.strategyName,sleepTime);
		waitPanel=new WaitPanel(nonBlockingCounter,tracerForCompareButton,this.sharedBoolean,this.strategyName,sleepTime);
		poolSizePanel=new PoolSizePanel(tracerForCompareButton,this.sharedBoolean,poolSizer,this.strategyName);
		acceptanceLatencyPanel=new AcceptanceLatencyPanel(nonBlockingCounter,tracerForCompareButton,this.sharedBoolean,this.strategyName);

		producer.addListener(responsePanel);
		//	producer.addListener(throughputPanel);
		//	producer.addListener(poolSizePanel);
		//	producer.addListener(acceptanceLatencyPanel);
		producer.addListener(waitPanel);
		reciever=new RecieverX(responserecievedValue,this.inStream,this.producer,this.nonBlockingCounter,this.throughputPanel,this.responsePanel,this.poolSizePanel,this.acceptanceLatencyPanel,this.waitPanel,poolSizer,sleepTime);
		panel=new DynamicPanel();
		tabPanel=new TabPanel();
		ButtonHandler btnHandl=new ButtonHandler();
		responsePanelStatisticsButton.addActionListener(btnHandl);
		poolSizePanelStatisticsButton.addActionListener(btnHandl);
		throughputPanelStatisticsButton.addActionListener(btnHandl);
		waitPanelStatisticsButton.addActionListener(btnHandl);

		start=new JButton("Start");
		start.addActionListener(btnHandl);
		setDim(start);
		stop=new JButton("Stop");
		stop.setEnabled(false);
		stop.addActionListener(btnHandl);
		setDim(stop);
		save=new JButton("Save");
		warmup=new JButton("Warmup");
		warmup.addActionListener(btnHandl);

		start.setEnabled(false);
		save.setEnabled(false);

		save.addActionListener(btnHandl);
		setDim(save);
		stopWatch=new StopWatch(start);
	}
	public JPanel getPanel(){
		//JOptionPane.showMessageDialog(null,this.strategyName);
		return this.panel;
	}
	public JPanel getTabPanel(){
		return this.tabPanel;
	}
	public void setDim(JButton b){
		b.setPreferredSize(new Dimension(76,35));
	}
	private void updateVector(){//No use of it anymore

		//v.add(new JCheckBox("Brussels", false));
		namesinCheckBox.add(new JCheckBox(this.previousScenario.getStrategyName(),false));//first insert name of previous scenario
		Scenario scenariosInLoop=this.previousScenario.getScenario();//now loop recursively inside each scenario and get names
		while(scenariosInLoop!=null){
			//	Scenario sc=this.previousScenario.getScenario();
			namesinCheckBox.add(new JCheckBox(scenariosInLoop.getStrategyName(),false));
			scenariosInLoop=scenariosInLoop.getScenario();


		}

	}
	public Scenario getScenario(){
		return this.previousScenario;
	}
	public void setScenarioFromPreviousTest(Vector<Integer> requestFrequencies, String[] realLoad, Vector<Integer> staticAndDynamicLoadValues,
			String workLoadPatternValue){

		this.usePreviousScenario=true;
		this.requestFrequencies=requestFrequencies;
		Collections.addAll(this.realLoad, realLoad);//add values of Array of Strings into vector
		// saved as int oneKb,int tenKb,int hundredKb,int thousandKb,int twoThousandKb,int tenMilliSecond,int fiftyMilliSecond,int hundredMilliSecond,int staticLoadPercentage,int dynamicLoadPercentage
		oneKb=staticAndDynamicLoadValues.elementAt(0);
		tenKb=staticAndDynamicLoadValues.elementAt(1);
		hundredKb=staticAndDynamicLoadValues.elementAt(2);
		thousandKb=staticAndDynamicLoadValues.elementAt(3);
		twoThousandKb=staticAndDynamicLoadValues.elementAt(4);
		tenMilliSecond=staticAndDynamicLoadValues.elementAt(5);
		fiftyMilliSecond=staticAndDynamicLoadValues.elementAt(6);
		hundredMilliSecond=staticAndDynamicLoadValues.elementAt(7);
		staticLoadPercentage=staticAndDynamicLoadValues.elementAt(8);
		dynamicLoadPercentage=staticAndDynamicLoadValues.elementAt(9);
		workloadPattern=workLoadPatternValue;


	}
/*	public void setScenario(Scenario sc){//No use of it anymore
		this.previousScenario=sc;
		this.usePreviousScenario=true;
		updateVector();//for compare check box that contains names of previous scenarios simulated so far
		this.fixedIntensity=this.previousScenario.getfixedIntensity();
		this.fixIntensityValue=this.previousScenario.getfixIntensityValue();
		this.lowerIntensity=this.previousScenario.getlowerIntensity();
		this.upperIntensity=this.previousScenario.getupperIntensity();
		this.realLoad=this.previousScenario.getRealLoad();
		this.requestFrequencies=this.previousScenario.getRequestFrequencies();


		oneKb=this.previousScenario.getOneKb();
		tenKb=this.previousScenario.getTenKb();
		hundredKb=this.previousScenario.getHundredKb();
		thousandKb=this.previousScenario.getThousandKb();
		twoThousandKb=this.previousScenario.getTwoThousandKb();
		tenMilliSecond=this.previousScenario.getTenMilliSecond();
		fiftyMilliSecond=this.previousScenario.getFiftyMilliSecond();
		hundredMilliSecond=this.previousScenario.getHundredMilliSecond();
		this.workloadPattern=this.previousScenario.getWorkloadPattern();
		setLambdaForPoisson(this.previousScenario.getLambdaForPoisson());
		setnForUniform(this.previousScenario.getnForUniform());
		setMeanForGuassian(this.previousScenario.getMeanForGuassian());
		setStandardDeviationForGuassin(this.previousScenario.getStandardDeviationForGuassin());
		setConstantDistibutor(this.previousScenario.getConstantDistibutor());

		setRequestSenderArguments();
		////
		this.manualLoad=this.previousScenario.getManualLoad();
		this.simulationTime=this.previousScenario.getsimulationTime();
		this.simulationTimeFix=this.previousScenario.getsimulationTimeFix();
	}*/

	public String getStrategyName(){
		return this.strategyName;
	}
	public void setUsePreviousScenario(boolean b){
		this.usePreviousScenario=b;

	}
	public boolean getUsePreviousScenario(){
		return this.usePreviousScenario;
	}
	public void setfixedIntensity(boolean t){
		this.fixedIntensity=t;
	}
	public boolean getfixedIntensity(){
		return this.fixedIntensity;
	}
	public void setRequestSenderArguments(){//No use of it anymore
		if (getfixedIntensity()){
			this.requestFrequencyPanel.setFixedIntensityTrueOrFalse(true);
			this.requestFrequencyPanel.setFixedIntensityValue(getfixIntensityValue());
		}
		else  {
			this.requestFrequencyPanel.setFixedIntensityTrueOrFalse(false);
			this.requestFrequencyPanel.setIntensityRange(getlowerIntensity(),getupperIntensity());
		}
	}
	public void setfixIntensityValue(String s){
		this.fixIntensityValue=s;
	}
	public String getfixIntensityValue(){
		return this.fixIntensityValue;
	}
	public void setlowerIntensity(String s){
		this.lowerIntensity=s;
	}
	public String getlowerIntensity(){
		return lowerIntensity;
	}
	public void setupperIntensity(String s){
		this.upperIntensity=s;
	}
	public String getupperIntensity(){
		return upperIntensity;
	}
	public void setManualLoad(boolean t){
		this.manualLoad=t;
		this.requestFrequencyPanel.setManualLoad(this.manualLoad);
	}
	public boolean getManualLoad(){
		return manualLoad;		
	}
	public void setsimulationTimeFix(boolean t){
		this.simulationTimeFix=t;
	}
	public boolean getsimulationTimeFix(){
		return simulationTimeFix;
	}
	public void setsimulationTime(String s){
		this.simulationTime=s;
	}
	public String getsimulationTime(){
		return simulationTime;
	}
	public void preparePanel(){
		this.panel.preparepanel();
	}
	public void disableStopButton(){

		stopDisable=true;
	}
	public class DynamicPanel extends JPanel{
		public String getName2(){
			return strategyName;
		}
		public DynamicPanel(){
			//this.setBackground(Color.GREEN);
			this.setBounds(0, 271, 281, 340);
			this.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		}
		private String getDistributionAndValue(){
			String distributionValue="";

			if(workloadPattern.equalsIgnoreCase("Poisson"))
				distributionValue="Avg Req/Sec="+lambdaForPoisson;
			else if(workloadPattern.equalsIgnoreCase("Guassian"))
				distributionValue="Avg Req/Sec="+meanForGuassian+"Standard Dev="+standardDeviationForGuassin;

			else if(workloadPattern.equalsIgnoreCase("Uniform"))
				distributionValue="Avg Req/Sec="+nForUniform;
			else if(workloadPattern.equalsIgnoreCase("Heavy"))
				distributionValue="Heavy";
			else if(workloadPattern.equalsIgnoreCase("Constant"))
				distributionValue="Constant="+constantDistibutor;

			return distributionValue; 
		}
		public void preparepanel(){
			Font labelFont= new Font ("Verdana", Font.BOLD , 12);
			this.setLayout(new BorderLayout());
			JPanel panel1=new JPanel(new GridLayout(2,1));
			JLabel strategyName=new  JLabel(""+getStrategyName()+"/Test"+numberOfTests);
			strategyName.setFont(labelFont);
			strategyName.setHorizontalAlignment(SwingConstants.CENTER);
			panel1.add(strategyName);
			String distributionNValue=getDistributionAndValue();
			JLabel workLoadNDistribution=new JLabel(workloadPattern+" Distribution "+distributionNValue);
			workLoadNDistribution.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel1.add(workLoadNDistribution);
			//add(new JLabel(" Scenario: "+getStrategyName()),BorderLayout.NORTH); 
			add(panel1,BorderLayout.NORTH);
			JTabbedPane jTabbedPane1 = new JTabbedPane();

			JPanel staticInfoPanel=new JPanel(new BorderLayout());
			JTable staticInfoTable=prepareAndGetStaticTable();
			// JScrollPane scrollPane = new JScrollPane(staticInfoTable);
			staticInfoTable.setFillsViewportHeight(true);
			staticInfoPanel.add(staticInfoTable.getTableHeader(), BorderLayout.PAGE_START);
			staticInfoPanel.add(staticInfoTable, BorderLayout.CENTER);

			JPanel dynamicInfoPanel=new JPanel(new BorderLayout());
			JTable dynamicInfoTable=prepareAndGetDynamicTable();
			dynamicInfoTable.setFillsViewportHeight(true);
			dynamicInfoPanel.add(dynamicInfoTable.getTableHeader(), BorderLayout.PAGE_START);
			dynamicInfoPanel.add(dynamicInfoTable, BorderLayout.CENTER);

			jTabbedPane1.add(staticInfoPanel,"Static WorkLoad "+staticLoadPercentage+" %");
			jTabbedPane1.add(dynamicInfoPanel,"Dynamic WorkLoad "+dynamicLoadPercentage+" %");
			add(jTabbedPane1,BorderLayout.CENTER);


		/*	JPanel comparePanel=new JPanel();
			comparePanel.setLayout(new GridLayout(1,2));

			comparePanel.setPreferredSize(new Dimension(2,40));
			//compare.setSize(new Dimension(2,5));
			//compare.setBounds(0, 36, 89, 23);
			JPanel comparePanelFor1Buttton=new JPanel(new FlowLayout());
			comparePanelFor1Buttton.add(compare);

			comparePanel.add(compareCheckBox);
			comparePanel.add(comparePanelFor1Buttton);




			if(usePreviousScenario==true){
				//JComboCheckBox
				add(comparePanel,BorderLayout.SOUTH);	
			}*/
		}
	}
	private JPanel getThroughputStatistics(){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		JPanel panel=new JPanel(new BorderLayout());
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		//dataset.addValue(throughputPanel.getTotalThroughput(), " ", "Throughput");
		dataset.addValue(throughputPanel.getAvgThroughput(), " ", "Throughput Per Second ");
		
		
		JFreeChart chart = ChartFactory.createBarChart(
				"Average Throughput", // chart title
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
        chart.setTitle(fontSetting.getTextTitle("Average Throughput:"+getStrategyName()));
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
		panel.add(chartPanel,BorderLayout.CENTER);
		return panel;
	/*	DefaultTableModel model = new DefaultTableModel();
		JTable table = new JTable(model);
		model.addColumn("Total Throughput");
		model.addColumn("Avg Throughput");


		double totalTP=throughputPanel.getTotalThroughput();
		double avgTP=throughputPanel.getAvgThroughput();


		model.addRow(new Object[] { totalTP,avgTP });

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER );
		//table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		//table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );


		//JScrollPane tableScrollPane = new JScrollPane(table);
		return table;*/
	}
	private JPanel getWaitStatistics(){
	
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		JPanel panel=new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(500, 205));
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		DefaultTableModel model = new DefaultTableModel();
		JTable table = new JTable(model);
		model.addColumn("Tasks");
		model.addColumn("50th Percentile Bin(ms) ");
		model.addColumn("90th Percentile Bin(ms)");
		//model.addColumn("95th Percentile(ms)");
		//model.addColumn("Avg Response Time(ms)");
		Vector graphs=waitPanel.getWaitTimeGraphs();
		Iterator it=graphs.iterator();
    	while(it.hasNext()){
    		 HistogramGraph2 hgg=(HistogramGraph2)it.next();
    		 int D=hgg.getLowerBound();
    		// System.out.println(" "+D+"\n");
    		 String job="";
 		 	switch (D){
 		 		case 1:job+="Low";	break;
				case 2:job+="High";	break;
				case 3:job+="V.High";break;
				case 100:job+="1kb";break;
				case 200:job+="10kb";break;
				case 300:job+="100kb";break;
				case 400:job+="1000kb";break;
				case 500:job+="1000kb";break;
				case 600:job+="1000kb";break;
				case 700:job+="1000kb";break;
				case 800:job+="1000kb";break;
				case 900:job+="1000kb";break;
				case 1000:job+="1200kb";break;
				case 2000:job+="2000kb";break;
					};
    		 model.addRow(new Object[] {job,hgg.getNthPercentileBin(50.0),hgg.getNthPercentileBin(90.0)});
    	}
    	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    	centerRenderer.setHorizontalAlignment(SwingConstants.CENTER );
    	//table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
    	//table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
    	JScrollPane scroll = new JScrollPane(table);
    	scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
    	panel.add(scroll, BorderLayout.CENTER);
		
		return panel;
	}


	private JPanel getPoolSizeStatistics(){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		JPanel panel=new JPanel(new BorderLayout());
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		dataset.addValue(poolSizePanel.getAveragePoolSize(), " ", "Pool Size Per Second");
		
		
		JFreeChart chart = ChartFactory.createBarChart(
				"Pool-Size Statistics", // chart title
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
        chart.setTitle(fontSetting.getTextTitle("Average Pool Size:"+getStrategyName()));
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
		panel.add(chartPanel,BorderLayout.CENTER);
		return panel;
	/*	DefaultTableModel model = new DefaultTableModel();
		JTable table = new JTable(model);
		model.addColumn("Avg Pool Size");


		double totalPs=poolSizePanel.getAveragePoolSize();


		model.addRow(new Object[] { totalPs});

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER );
		//table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		//table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );


		//JScrollPane tableScrollPane = new JScrollPane(table);
		return table;*/
	}

	/*
	 String string = "004-034556";
String[] parts = string.split("-");
String part1 = parts[0]; // 004
String part2 = parts[1]; // 034556
	 */


	
	 private JPanel getStatisticsCatagoryGraph(Vector graphs,String check){
			//In order to understand this plz See IntervalBarChartDemo1.java in Graphs package
			Iterator it=graphs.iterator();
			  Vector<String> bins=new Vector<String>(3);//hold ranges ie bins

			while(it.hasNext()){
				if(check.equals("R")){// R means called for ResponseTime
    		 	HistogramGraph hgg=(HistogramGraph)it.next();
    		 	bins.add(hgg.getNthPercentileBin(50.0)); 
    		 	bins.add(hgg.getNthPercentileBin(90.0)); 
				}
				else{
					HistogramGraph2 hgg=(HistogramGraph2)it.next();
	    		 	bins.add(hgg.getNthPercentileBin(50.0)); 
	    		 	bins.add(hgg.getNthPercentileBin(90.0)); 
					
				}
				
				}
			//now sort bins String data
			Collections.sort(bins, new Comparator<String>() {
		        public int compare(String o1, String o2) {
		            return extractInt(o1) - extractInt(o2);
		        }

		        int extractInt(String s) {
		            String num = s.replaceAll("\\D", "");
		            // return 0 if no digits found
		            return num.isEmpty() ? 0 : Integer.parseInt(num);
		        }
		    });
		   //Bin data may contain duplicate so store it in a set which automatically removes duplicates
		    Set<String> set = new LinkedHashSet<String>();
		    set.addAll(bins);
		    //remove bin data now
		    bins.removeAllElements();
		    //store unique sorted data into bin again
		    bins.addAll(set);
		    
		    
		    int size=graphs.size();//how many types of jobs there 1kb 2kb etc
			String[] categoryKeys=new String[size] ;//= {"1kb-File","2kb-File"};
			int count=0;
			 Number s1[]= new Number[size];
			 Number e1[]= new Number[size];
			 Number s2[]= new Number[size];
		     Number e2[]= new Number[size];
		     String test="";
		//     Vector<DefaultIntervalCategoryDataset> datasetVector=new Vector<DefaultIntervalCategoryDataset>(5);
		     it=null;
		     it=graphs.iterator();
	    	while(it.hasNext()){
	    		if(check.equals("R")){// R means called for ResponseTime
	    		 	HistogramGraph hgg=(HistogramGraph)it.next();
	    		 	
	    		 	switch (hgg.getLowerBound()){
					case 1:categoryKeys[count]=""+"Low";	break;
					case 2:categoryKeys[count]=""+"High";	break;
					case 3:categoryKeys[count]=""+"V.High";	break;
					case 100:categoryKeys[count]=""+"1kb";	break;
					case 200:categoryKeys[count]=""+"10kb";	break;
					case 300:categoryKeys[count]=""+"100kb";	break;
					case 400:categoryKeys[count]=""+"1000kb";	break;
					case 500:categoryKeys[count]=""+"1000kb";	break;
					case 600:categoryKeys[count]=""+"1000kb";	break;
					case 700:categoryKeys[count]=""+"1000kb";	break;
					case 800:categoryKeys[count]=""+"1000kb";	break;
					case 900:categoryKeys[count]=""+"1000kb";	break;
					case 1000:categoryKeys[count]=""+"1200kb";	break;
					case 2000:categoryKeys[count]=""+"2000kb";	break;
						
	    		 	};
	    		 	
	    		 	
	    		 	//categoryKeys[count]=""+hgg.getLowerBound();// job name ie 100 200 etc 
	    		 	s1[count]=0;// bin first range is kept to 0
	    		 	s2[count]=0;
	    		 	e1[count]=1+bins.indexOf(hgg.getNthPercentileBin(50.0));// this method will search string in vector and return the index where the string is stored 1 is added because vector starts at 0
	    		 	test+="e1["+count+"]="+e1[count]+"\t";
	    		 	e2[count]=1+bins.indexOf(hgg.getNthPercentileBin(90.0));// this method will search string in vector and return the index where the string is stored
	    		 	test+="e2["+count+"]="+e2[count]+"\t";
	    		 	count++;
	    		 	}
	    		else{// and this is for wait times
	    			HistogramGraph2 hgg=(HistogramGraph2)it.next();
	    			switch (hgg.getLowerBound()){
	    			case 1:categoryKeys[count]=""+"Low";	break;
					case 2:categoryKeys[count]=""+"High";	break;
					case 3:categoryKeys[count]=""+"V.High";	break;
					case 100:categoryKeys[count]=""+"1kb";	break;
					case 200:categoryKeys[count]=""+"10kb";	break;
					case 300:categoryKeys[count]=""+"100kb";	break;
					case 400:categoryKeys[count]=""+"1000kb";	break;
					case 500:categoryKeys[count]=""+"1000kb";	break;
					case 600:categoryKeys[count]=""+"1000kb";	break;
					case 700:categoryKeys[count]=""+"1000kb";	break;
					case 800:categoryKeys[count]=""+"1000kb";	break;
					case 900:categoryKeys[count]=""+"1000kb";	break;
					case 1000:categoryKeys[count]=""+"1200kb";	break;
					case 2000:categoryKeys[count]=""+"2000kb";	break;
						
	    		 	};
	    		 	//categoryKeys[count]=""+hgg.getLowerBound();// job name ie 100 200 etc 
	    		 	s1[count]=0;// bin first range is kept to 0
	    		 	s2[count]=0;
	    		 	e1[count]=1+bins.indexOf(hgg.getNthPercentileBin(50.0));// this method will search string in vector and return the index where the string is stored 1 is added because vector starts at 0
	    		 	test+="e1["+count+"]="+e1[count]+"\t";
	    		 	e2[count]=1+bins.indexOf(hgg.getNthPercentileBin(90.0));// this method will search string in vector and return the index where the string is stored
	    		 	test+="e2["+count+"]="+e2[count]+"\t";
	    		 	count++;
	    			
	    		}
	    		}
	    	
	    	Number startZ[][] = {s1 ,s2};   
	        Number endZ[][] = { e1,e2}; 
	    	String[] seriesKeys = {"50th%","90th%"};
	   //   String[]  categoryKeys = seriesKeys;
			DefaultIntervalCategoryDataset dataset= new DefaultIntervalCategoryDataset(seriesKeys,categoryKeys,startZ, endZ);
			FontSetting fontSetting=new FontSetting(); 

			CategoryAxis domainAxis = new CategoryAxis("Workload");
	      //  domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

			domainAxis.setTickLabelFont(fontSetting.getTickFont(9));
			domainAxis.setLabelFont(fontSetting.getLabelFont());
			String[] ranges=new String[bins.size()+1];// +1 for 0
			ranges[0]="0";//+1 for 0 is solved here
			for(int i=1;i<=bins.size();i++){
				ranges[i]="["+bins.elementAt(i-1)+"] "+i;
			}
			NumberAxis rangeAxis = new SymbolAxis("Bins(ms)",ranges);
			rangeAxis.setTickLabelFont(fontSetting.getTickFont());
			rangeAxis.setLabelFont(fontSetting.getLabelFont());
			rangeAxis.setAutoRange(false);
			rangeAxis.setRange(0, ranges.length);
		//	JOptionPane.showMessageDialog(null, ranges);
		//	JOptionPane.showMessageDialog(null, test);


			//  rangeAxis.setNumberFormatOverride(new DecimalFormat("0.00%"));
			IntervalBarRenderer renderer = new IntervalBarRenderer();
		//	DefaultIntervalCategoryDataset ds=(DefaultIntervalCategoryDataset)datasetVector.elementAt(0);
			CategoryPlot plot = new CategoryPlot(dataset, domainAxis, rangeAxis,renderer);			
		//	ValueAxis rageAxis = plot.getRangeAxis();
			JFreeChart chart = new JFreeChart(plot);
			chart.setBackgroundPaint(Color.WHITE);
			plot.setBackgroundPaint(new Color(0xb8cfe5));
			plot.setRangeGridlinePaint(Color.WHITE); 
			plot.setDomainGridlinePaint(Color.WHITE);
		     String chartTitle="";

			if(check.equals("R"))// R means called for ResponseTime
    			chartTitle+="Response Time Percentiles:"+getStrategyName();
			else chartTitle+="Wait Time Percentiles:"+getStrategyName();
			chart.setTitle(fontSetting.getTextTitle(chartTitle));
			LegendTitle legend = chart.getLegend();
	        legend.setItemFont(fontSetting.getTickFont());

			plot.setDomainGridlinesVisible(true);
			plot.setRangePannable(true);
		//	ChartUtilities.applyCurrentTheme(chart);
			ChartPanel panel = new ChartPanel(chart);
			int height=205;
			if(bins.size()>=12) height=300;
			else if(bins.size()>9) height=250;
			 panel.setPreferredSize(new Dimension(500,height));
		   
		/*	JPanel testPanel=new JPanel();//
			testPanel.setPreferredSize(new Dimension(500,205));
			
			JScrollPane scroll = new JScrollPane(panel);
	    	scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			testPanel.add(scroll,BorderLayout.CENTER);*/
			
			return panel;
		}
	private static IntervalBarRenderer rendererSettings(){
		IntervalBarRenderer renderer = new IntervalBarRenderer();
		renderer.setItemLabelFont(new Font("Verdana",Font.PLAIN,8));
		renderer.setToolTipGenerator(new StandardCategoryToolTipGenerator());
	//	renderer.setBaseItemLabelGenerator(new IntervalCategoryItemLabelGenerator("{3}-{2} ", NumberFormat.getNumberInstance()));
		renderer.setBaseItemLabelGenerator( new IntervalCategoryItemLabelGenerator("{3}-{2} ", NumberFormat.getNumberInstance()){
			protected Object[] createItemArray(CategoryDataset dataset,
					int row, int column) {
				Object[] result = new Object[5];
				result[0] = dataset.getRowKey(row).toString();
				result[1] = dataset.getColumnKey(column).toString();
				Number value = dataset.getValue(row, column);
				value=value.intValue()-20;
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
					end=end.intValue()-20;
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
		renderer.setBaseItemLabelsVisible(true);	
		renderer.setItemMargin(0.1);

		return renderer;
		
	}
	private JPanel getResponseTimeStatisticsTable(){
	
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		JPanel panel=new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(500, 205));
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		DefaultTableModel model = new DefaultTableModel();
		JTable table = new JTable(model);
		model.addColumn("Tasks");
		model.addColumn("50th Percentile Bin(ms)");
		model.addColumn("90th Percentile Bin(ms)");
		
		Vector graphs=responsePanel.getResposeTimeGraphs();
		Iterator it=graphs.iterator();
    	while(it.hasNext()){
    		 	HistogramGraph hgg=(HistogramGraph)it.next();
    		 	
    		 	String job="";
    		 	switch (hgg.getLowerBound()){
    			case 1:job+="Low";	break;
				case 2:job+="High";	break;
				case 3:job+="V.High";	break;
				case 100:job+="1kb";	break;
				case 200:job+="10kb";	break;
				case 300:job+="100kb";	break;
				case 400:job+="1000kb";	break;
				case 500:job+="1000kb";	break;
				case 600:job+="1000kb";	break;
				case 700:job+="1000kb";	break;
				case 800:job+="1000kb";	break;
				case 900:job+="1000kb";	break;
				case 1000:job+="1200kb";	break;
				case 2000:job+="2000kb";	break;// this is for future use
			 	};
    		 	///////////
    		 model.addRow(new Object[] { job ,hgg.getNthPercentileBin(50.0),hgg.getNthPercentileBin(90.0)});
    	}
    	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    	centerRenderer.setHorizontalAlignment(SwingConstants.CENTER );
    	//table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
    	//table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
    	JScrollPane scroll = new JScrollPane(table);
    	scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
    	panel.add(scroll, BorderLayout.CENTER);
		
		return panel;
	}
	private JTable prepareAndGetStaticTable(){

		String[] columnNames = {"File Size=","Access Frequency(%)"};
		/*
 int oneKb,tenKb,hundredKb,thousandKb,twoThousandKb;//static load
	Vector<Integer> staticLoadValues;//All above values are extracted from this vector.U can use this vector also instead of above variables 
	int tenMilliSecond,fiftyMilliSecond,hundredMilliSecond;//dynamic load

		 */

		Object[][] data = {
				{"1 KB",new Integer(oneKb)},
				{"10 KB",new Integer(tenKb)},
				{"100 KB",new Integer(hundredKb)},
				{"1000 KB",new Integer(thousandKb)},
				{"1200 KB",new Integer(twoThousandKb)},

		};

		JTable table = new JTable(data, columnNames);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER );
		table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );


		//JScrollPane tableScrollPane = new JScrollPane(table);
		return table;
	}
	private JTable prepareAndGetDynamicTable(){

		String[] columnNames = {"Processing Time","Access Frequency(%)"};

		/*
 int oneKb,tenKb,hundredKb,thousandKb,twoThousandKb;//static load
	Vector<Integer> staticLoadValues;//All above values are extracted from this vector.U can use this vector also instead of above variables 
	int tenMilliSecond,fiftyMilliSecond,hundredMilliSecond;//dynamic load

		 */

		Object[][] data = {
				{"15 ms",new Integer(tenMilliSecond)},
				{"50 ms",new Integer(fiftyMilliSecond)},
				{"100 ms",new Integer(hundredMilliSecond)},

		};

		JTable table = new JTable(data, columnNames);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
		table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		//JScrollPane tableScrollPane = new JScrollPane(table);
		return table;


	}

	public void prepareTabPanel(Container c){
		this.tabPanel.preparePanel(c);
	}
	public class TabPanel extends JPanel{

		//Container container;
		public TabPanel(){		
			this.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			this.setBackground(Color.BLUE);
		}
		private  void preparePanel(Container c){	

			setLayout(new BorderLayout(0, 0));
			JPanel centerPanel=new JPanel(new BorderLayout());

			JPanel northPanel = new JPanel(new GridLayout(1,2,5,5));
			northPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));


			northPanel.setPreferredSize(new Dimension(100,205));
			//northPanel.setBackground(Color.BLACK);
			//requestFrequencyPanel.putClientProperty("JComponent.sizeVariant", "mini");
			JPanel p=new  JPanel(new BorderLayout());
			p.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			p.add(requestFrequencyPanel,BorderLayout.CENTER);
			//northPanel.add(requestFrequencyPanel);//,BorderLayout.CENTER);
			northPanel.add(p);//,BorderLayout.CENTER);

			JPanel panelX=new JPanel(new BorderLayout());
			SliderPanel sliderPanel=new SliderPanel(1,3,1);
			if(usePreviousScenario==true)sliderPanel.setSliderDisable();
			panelX.add(sliderPanel,BorderLayout.WEST);
			JPanel buttonPanel=new JPanel(new GridLayout(5,1,5,5));
		//	buttonPanel.add(new JLabel("     "));
			buttonPanel.add(warmup);
			buttonPanel.add(start);
			buttonPanel.add(stop);
			buttonPanel.add(save);
			buttonPanel.add(new JLabel("     "));
			JPanel panelY=new JPanel(new BorderLayout());

			JPanel statisticPanel=new JPanel(new BorderLayout());
			statisticPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			JLabel info=new JLabel("Testing Information");
			info.setHorizontalAlignment(SwingConstants.CENTER);

			JPanel gridPanel=new JPanel(new GridLayout(3,2));
			JLabel elapsedtime=new JLabel("Elapsed Time:");elapsedtime.setHorizontalAlignment(SwingConstants.CENTER);
		//	JLabel loadInfo=new JLabel("Current Load");loadInfo.setHorizontalAlignment(SwingConstants.CENTER);

			JLabel reqSent=new JLabel("Requests sent:");
			reqSent.setHorizontalAlignment(SwingConstants.CENTER);
			JLabel responserecieved=new JLabel("Response recieved:");
			responserecieved.setHorizontalAlignment(SwingConstants.CENTER);

			gridPanel.add(elapsedtime);gridPanel.add(stopWatch);
		//	gridPanel.add(loadInfo);gridPanel.add(loadValue);
			gridPanel.add(reqSent);gridPanel.add(reqSentvalue);
			gridPanel.add(responserecieved);gridPanel.add(responserecievedValue);

			statisticPanel.add(info,BorderLayout.NORTH);
			statisticPanel.add(gridPanel,BorderLayout.CENTER); 

			panelY.add(statisticPanel,BorderLayout.CENTER);
			panelY.add(buttonPanel,BorderLayout.WEST);
			panelX.add(panelY,BorderLayout.CENTER);




			northPanel.add(panelX);
			//northPanel.add(new JButton("sdc"));
			centerPanel.add(northPanel, BorderLayout.NORTH);

			JPanel innerPanel = new JPanel(new BorderLayout());
			JPanel quadrantPanel=new JPanel(new GridLayout(2,2));

			///////
			JTabbedPane throughputTabbedPane1 = new JTabbedPane();
			throughputStatisticsPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			buttonthroughputPanel=new JPanel(new FlowLayout());
			buttonthroughputPanel.add(	throughputPanelStatisticsButton);
			throughputStatisticsPanel.add(buttonthroughputPanel,BorderLayout.NORTH);
			//throughputStatisticsPanel.remove(buttonthroughputPanel);
			throughputCenterStatisticsPanel=new JPanel(new BorderLayout());
			throughputStatisticsPanel.add(throughputCenterStatisticsPanel,BorderLayout.CENTER);
			throughputTabbedPane1.add(throughputPanel,"Throughput Graph");
			throughputTabbedPane1.add(throughputStatisticsPanel,"Throughput Statistics");

			JTabbedPane responseTabbedPane1 = new JTabbedPane();
			responsePanelStatisticsNorthPanel=new JPanel(new FlowLayout());
			responsePanelStatisticsNorthPanel.add(	responsePanelStatisticsButton);

			
			responseStatisticsPanel.add(responsePanelStatisticsNorthPanel,BorderLayout.NORTH);
			responseStatisticsPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			responseStatisticsCenterPanel=new JPanel(new BorderLayout());
			responseStatisticsPanel.add(responseStatisticsCenterPanel,BorderLayout.CENTER);
			
		//	JPanel kPanel=new JPanel();
		/*	JScrollPane scrollerForRTGraph=new JScrollPane(responsePanel);
			scrollerForRTGraph.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scrollerForRTGraph.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);*/

			
			responseTabbedPane1.add(responsePanel,"Response Time(RT) Histogram");
		//	responseTabbedPane1.add(scrollerForRTGraph,"Graph");

			responseTabbedPane1.add(responseStatisticsPanel,"RT Statistics Table");
			
			final JPanel pq=new JPanel(new FlowLayout());
		//	buttonRTStatisticsTable
			pq.add(buttonRTStatisticsTable);
			//button.setEnabled(false);// it is kept false because i did not complete the grph processing yet. Graph is functional 4 only 3 jobs 1kb 10kb and 100kb and no work is done on other graphs
			responseTabbedPane1.add(pq,"RT Statistics Graph");
			buttonRTStatisticsTable.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				pq.removeAll();
				pq.setLayout(new BorderLayout());
				JScrollPane scrollerForRTGraph=new JScrollPane(getStatisticsCatagoryGraph(responsePanel.getResposeTimeGraphs(),"R"));//R means ResponstTime
				scrollerForRTGraph.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				scrollerForRTGraph.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				JPanel panel = new JPanel(new BorderLayout());
				 panel.setPreferredSize(new Dimension(500,205));
				 panel.add(scrollerForRTGraph,BorderLayout.CENTER);
				
				pq.add(panel, BorderLayout.CENTER);
				pq.revalidate();
				pq.repaint();
				
				}
			});
			//responseTabbedPane1.add(getResponseTimeStatisticsCatagoryGraph(),"Statistics Graph");

			
			//JScrollPane scrollerForRTGraph=new JScrollPane(getResponseTimeStatisticsCatagoryGraph());
			
			//quadrantPanel.add(responsePanel);
			JTabbedPane poolSizeTabbedPane1 = new JTabbedPane();
			poolSizeStatisticsPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			buttonPoolSizePanel=new JPanel(new FlowLayout());
			buttonPoolSizePanel.add(	poolSizePanelStatisticsButton);
			poolSizeStatisticsPanel.add(buttonPoolSizePanel,BorderLayout.NORTH);
			poolSizeAvgStatisticsPanel=new JPanel(new BorderLayout());
			poolSizeStatisticsPanel.add(poolSizeAvgStatisticsPanel,BorderLayout.CENTER);
			poolSizeTabbedPane1.add(poolSizePanel,"Pool Size Graph");
			poolSizeTabbedPane1.add(poolSizeStatisticsPanel,"Pool Size Statistics");

			
			JTabbedPane waitTabbedPane1 = new JTabbedPane();
			waitStatisticsPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			buttonwaitPanel=new JPanel(new FlowLayout());
			buttonwaitPanel.add(	waitPanelStatisticsButton);
			waitStatisticsPanel.add(buttonwaitPanel,BorderLayout.NORTH);
			waitAvgStatisticsPanel=new JPanel(new BorderLayout());
			waitStatisticsPanel.add(waitAvgStatisticsPanel,BorderLayout.CENTER);
			waitTabbedPane1.add(waitPanel,"Wait-Time(WT) Histogram");
			waitTabbedPane1.add(waitStatisticsPanel,"WT Statistics");
			
			final JPanel pq2=new JPanel(new FlowLayout());
			pq2.add(buttonWTStatisticsGraph);
			//button.setEnabled(false);// it is kept false because i did not complete the grph processing yet. Graph is functional 4 only 3 jobs 1kb 10kb and 100kb and no work is done on other graphs
			waitTabbedPane1.add(pq2,"WT Statistics Graph");
			buttonWTStatisticsGraph.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				pq2.removeAll();
				pq2.setLayout(new BorderLayout());
				JScrollPane scrollerForRTGraph=new JScrollPane(getStatisticsCatagoryGraph(waitPanel.getWaitTimeGraphs(),"W"));
				scrollerForRTGraph.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				scrollerForRTGraph.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				
				JPanel panel = new JPanel(new BorderLayout());
				 panel.setPreferredSize(new Dimension(500,205));
				 panel.add(scrollerForRTGraph,BorderLayout.CENTER);
				
				pq2.add(panel, BorderLayout.CENTER);
				
			//	pq2.add(scrollerForRTGraph, BorderLayout.CENTER);
				pq2.revalidate();
				pq2.repaint();
				
				}
			});
			
			
					
			
			quadrantPanel.add(throughputTabbedPane1);
			quadrantPanel.add(responseTabbedPane1);
			quadrantPanel.add(waitTabbedPane1);
			quadrantPanel.add(poolSizeTabbedPane1);
		//	quadrantPanel.add(acceptanceLatencyPanel);
			innerPanel.add(quadrantPanel,BorderLayout.CENTER);
			
			innerPanel.setBackground(Color.ORANGE);
			centerPanel.add(innerPanel, BorderLayout.CENTER);
			add(centerPanel, BorderLayout.CENTER);
			
		}
	}
	private class ButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
		//	if(e.getActionCommand().equals("Resume")) requestFrequencyPanel.restartSendingRequest();
			/*
			 public boolean isStartButtonEnable() {
		return startButtonEnable;
	}
	public void setStartButtonEnable(boolean startButtonEnable) {
		this.startButtonEnable = startButtonEnable;
	}
	public boolean isStopButtonEnable() {
		return stopButtonEnable;
	}
	public void setStopButtonEnable(boolean stopButtonEnable) {
		this.stopButtonEnable = stopButtonEnable;
	}
	public boolean isSaveButtonEnable() {
		return saveButtonEnable;
	}
	public void setSaveButtonEnable(boolean saveButtonEnable) {
		this.saveButtonEnable = saveButtonEnable;
	}
			 */
			if(e.getSource()==start){
				setStartButtonEnable(false);
				setStopButtonEnable(true);
				setSaveButtonEnable(false);
				
				
				if(!mainFrame.howManyTabs()){// it will return true if more than 1 tabs exists in MainFrame window
				//boolean startButtonActiveOrNot=true;
				//boolean stopButtonActiveOrNot=false;
				startButtonActiveOrNot=false;

				////
				try{
					sendnotSendingProtocol.setSend(false);
					outStream.writeObject("starting");// it is a signal to StrategyNameCommuncator Thread on the server side that i m going to send the Jobs ...when this signal is recieved by StrategyNameCommuncator then StrategyNameCommuncator wil start Reciever and ResponseSender Threads
					outStream.flush();
				}catch(Exception ex){
				//	System.out.println("Exception in Mainframe in start button sending a protocol of starting");
					JOptionPane.showMessageDialog(null, "Server is not Running/n Exception in Mainframe in start button sending a protocol of starting");
					System.exit(0);
				}
				/*
				 * After sending protocl we PoolRunner should wait for a while so that Server can take start its Reciever and ResponseSender Threads any how it is not mandatory to wait
				 */
				try{
					Thread.sleep(250);//just wait for a while so that sever's StrategyNameCommuncator starts  its corresponding threads for communication
				}catch(InterruptedException ex){
					JOptionPane.showMessageDialog(null, "Exception in Mainframe in start button in sleeping");

				}
				try{
					requestFrequencyPanel.startSendingRequest();
					requestFrequencyPanel.dataGenerator.start();

					reciever.start();
					throughputPanel.startThroughputPanel();//start its data generator which is a Timer
					responsePanel.startResponsePanel();//start its data generator which is a Thread
					waitPanel.startWaitPanel();
					poolSizePanel.startPoolSizePanel();
					//	acceptanceLatencyPanel.startAcceptanceLatencyPanel();
				}catch(Exception ex){JOptionPane.showMessageDialog(null,"Pakra gaya");}
				start.setEnabled(false);
				if(stopDisable==true) stop.setEnabled(false);// ie if usepreviousstrategy is true then dnt enable stop button because it will then generate certain events on click that is already i m doing in when all requests in requestsFrequncyClone are finished in LoadGenerationEngine class
				else {
					//stop.setEnabled(true);
					StopButtonController stopButtonController=new StopButtonController(stop);// this thread will enable Stop Button after 3 seconds so tht user dont immediately stop simulation 
					stopButtonController.start();

					}
				//testReciver.start();
				//	start.setVisible(false);
				////

			}
				else JOptionPane.showMessageDialog(null, "Close all other test windows so that Testing can utilize full system resources");
				
			}
			else if(e.getSource()==stop){
				//boolean startButtonActiveOrNot=true;
				//boolean stopButtonActiveOrNot=false;
				//
				/*	responsePanelStatisticsButton.setEnabled(true);
			poolSizePanelStatisticsButton.setEnabled(true);
			throughputPanelStatisticsButton.setEnabled(true);
			waitPanelStatisticsButton.setEnabled(true);*/
				setStopButtonEnable(false);//setSaveButtonEnable(false) would b called from throughputpanel

				stopButtonActiveOrNot=true;

				stop.setEnabled(false);
				requestFrequencyPanel.dataGenerator.stop();
				requestFrequencyPanel.changeChart();
				requestFrequencyPanel.stopSendingRequest();
				//following panels would be stopped by RecieverX when ResponseSender from server side will send stop signal and a ClaasCastException in ReciverX will stop these panels
				//	responsePanel.stopResponsePanel();
				//	throughputPanel.stopThroughputPanel();
				//	poolSizePanel.stopPoolSizePanel();
				try{
					outStream.writeObject("stop");// it is a signal to StrategyNameCommuncator Thread on the server side that i m going to send the Jobs ...when this signal is recieved by StrategyNameCommuncator then StrategyNameCommuncator wil start Reciever and ResponseSender Threads
					outStream.flush();
					outStream.reset();
					GCRunner gcRunner=new GCRunner();
					gcRunner.setPriority(Thread.MAX_PRIORITY);
					gcRunner.start();
				}catch(Exception ex){
					//System.out.println("Exception in Mainframe in stop button sending a protocol of stop");
					JOptionPane.showMessageDialog(null, "Server is not Running/n Exception in Mainframe in stop button sending a protocol of stop");
					System.exit(0);
				}
				//throughputPanel.startThroughputPanel();
			}
			else if(e.getSource()==warmup){
				mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				mainFrame.getSimulationGUIPanel().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

				mainFrame.getScenarioPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				new SplashWindow();
				warmup.setEnabled(false);

				try{
					outStream.writeObject("warmup");// it is a signal to StrategyNameCommuncator Thread on the server side that i m going to send the Jobs ...when this signal is recieved by StrategyNameCommuncator then StrategyNameCommuncator wil start Reciever and ResponseSender Threads
					outStream.flush();
				}catch(Exception ex){
					//System.out.println("Exception in Mainframe in start button sending a protocol of starting");
					JOptionPane.showMessageDialog(null, "Server is not Running/n Exception in Mainframe in start button sending a protocol of starting");
					System.exit(0);
				}
				/*
				 * After sending protocl we PoolRunner should wait for a while so that Server can take start its Reciever and ResponseSender Threads any how it is not mandatory to wait
				 */
				try{
					Thread.sleep(250);//just wait for a while so that sever's StrategyNameCommuncator starts  its corresponding threads for communication
				}catch(InterruptedException ex){
					JOptionPane.showMessageDialog(null, "Exception in Mainframe in start button in sleeping");

				}
				new	WarmupRequestSender().start();
				new WarmupReceiver().start();
				
			}
			else if(e.getSource()==save){
				setSaveButtonEnable(false);				
				DataSaver datasaver=new DataSaver();
				if(datasaver.createDataFolderIfNotExists()){
					if(datasaver.createTPSFolderIfNotExists(getStrategyName())){
						int numberOfTests=(datasaver.countNumberOfTestsInTPS(getStrategyName()))+1;
						String testNumber="Test"+numberOfTests;
						boolean check=false;
						while(check==false){
							String testReportName=null;
						 testReportName=JOptionPane.showInputDialog(null, "Enter name of Test Report(no space or special chars)");
					if(testReportName==null || testReportName.length()==0 ) { 
						JOptionPane.showMessageDialog(null,"Illegal format! Not saved");	

						return;
						}
						testReportName=testReportName.replaceAll("[^A-Za-z0-9]", "");// remove all special characters including spaces
						
						if(testReportName.length()==0 ) 
							// for example if he entered only special chars
						{ 
							JOptionPane.showMessageDialog(null,"Illegal format! Not saved");	

							return;
							}

						if(datasaver.checkIfTestReportNameAlreadyExist(getStrategyName(),testReportName))
							JOptionPane.showMessageDialog(null,"Name Already Exist Try again!");	
						
						else {check=true;testNumber=testReportName;}
						
							}
						
					//	JOptionPane.showMessageDialog(null, "Bin size="+responsePanel.getWorkloadForBins().size());
						if(//(datasaver.saveDoubleSeries(getResposePanelSeries(),getStrategyName(),testNumber, "ResponseSeries")) &&
							//	(datasaver.saveDoubleSeries(getWaitPanelSeries(),getStrategyName(),testNumber, "WaitPanelSeries"))&&
								(datasaver.saveDoubleSeries(getThroughputPanelSeries(),getStrategyName(),testNumber, "ThroughputPanelSeries"))&&
								(datasaver.saveDoubleSeries(getPoolSizePanelSeries(),getStrategyName(),testNumber, "PoolSizePanelSeries")) &&
								(datasaver.saveWorkLoadForBins(getStrategyName(),testNumber,responsePanel.getWorkloadForBins()))&&//// i.e. 100 200 300 400 2000 1 5 10 etc static and dynamic workload that user will choose 4m dialog box i.e. types of jobs
								(datasaver.saveResponseOrWaitPanelGraphResults(getStrategyName(),testNumber,responsePanel.getResponseGraphsResult(),'R'))&&
								(datasaver.saveResponseOrWaitPanelGraphResults(getStrategyName(),testNumber,waitPanel.getWaitGraphsResult(),'W'))&&
								(datasaver.saveIntSeries(requestFrequencyPanel.getSeries(),getStrategyName(),testNumber, "FrequencySeries"))&&
								//(datasaver.saveResultStatistics(getStrategyName(),testNumber,throughputPanel.getTotalThroughput(),throughputPanel.getAvgThroughput(),responsePanel.get50Percentile(),responsePanel.get90Percentile(),responsePanel.get95Percentile(),responsePanel.getAverageRT(),waitPanel.getAverageWait(),poolSizePanel.getAveragePoolSize()))
								(datasaver.saveResultStatistics(getStrategyName(),testNumber,throughputPanel.getTotalThroughput(),throughputPanel.getAvgThroughput(),poolSizePanel.getAveragePoolSize()))&&

								(datasaver.saveRealLoad(getStrategyName(),testNumber,realLoad))&& 
								(datasaver.savePreviousTestName(getStrategyName(),testNumber,getPreviousScenarioName() ))&&
								(datasaver.staticAndDynamicLoad(getStrategyName(),testNumber,oneKb,tenKb, hundredKb, thousandKb, twoThousandKb, tenMilliSecond, fiftyMilliSecond, hundredMilliSecond,staticLoadPercentage,dynamicLoadPercentage))&&
								(datasaver.saveWorkloadPattern(getStrategyName(),testNumber,workloadPattern ))){
							
							JOptionPane.showMessageDialog(null, "Test results has been saved as "+getStrategyName()+"/"+testNumber);
							save.setEnabled(false);
							scenarioSavedOrNot=true;
							GCRunner gcRunner=new GCRunner();
							gcRunner.setPriority(Thread.MAX_PRIORITY);
							gcRunner.start();


						}
						else JOptionPane.showMessageDialog(null, getStrategyName()+" Folder creation failed!");

					}
					else JOptionPane.showMessageDialog(null, "Data Folder creation failed!");


				}
			}
			else if(e.getSource()==responsePanelStatisticsButton){
				JPanel responseStatisticsTable=getResponseTimeStatisticsTable();
				//responsePanelStatisticsNorthPanel
			//	responseStatisticsCenterPanel.remove(responseStatisticsCenterPanel);
			responseStatisticsPanel.remove(responsePanelStatisticsNorthPanel);

			responseStatisticsPanel.revalidate();
			responseStatisticsPanel.repaint();
			//	responseStatisticsCenterPanel.add(responseStatisticsTable.getTableHeader(), BorderLayout.PAGE_START);
			JScrollPane jp=new JScrollPane();
			jp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			jp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			
				responseStatisticsCenterPanel.add(responseStatisticsTable,BorderLayout.CENTER);
			//	responseStatisticsCenterPanel.add(jp,BorderLayout.CENTER);

				responseStatisticsCenterPanel.revalidate();
				responseStatisticsCenterPanel.repaint();

				responseStatisticsPanel.revalidate();
				responseStatisticsPanel.repaint();
			}
			else if(e.getSource()==poolSizePanelStatisticsButton){

				JPanel poolSizeStatisticsTable=getPoolSizeStatistics();
				poolSizeStatisticsPanel.remove(buttonPoolSizePanel);
				poolSizeStatisticsPanel.revalidate();
				poolSizeStatisticsPanel.repaint();

			//	poolSizeAvgStatisticsPanel.add(poolSizeStatisticsTable.getTableHeader(), BorderLayout.PAGE_START);
				poolSizeAvgStatisticsPanel.add(poolSizeStatisticsTable,BorderLayout.CENTER);
				poolSizeAvgStatisticsPanel.revalidate();
				poolSizeAvgStatisticsPanel.repaint();

				poolSizeStatisticsPanel.revalidate();
				poolSizeStatisticsPanel.repaint();
			}
			else if(e.getSource()==throughputPanelStatisticsButton){
				JPanel responseStatisticsTable=getThroughputStatistics();
				throughputStatisticsPanel.remove(buttonthroughputPanel);
				throughputStatisticsPanel.revalidate();
				throughputStatisticsPanel.repaint();

			//	throughputCenterStatisticsPanel.add(responseStatisticsTable.getTableHeader(), BorderLayout.PAGE_START);
				throughputCenterStatisticsPanel.add(responseStatisticsTable,BorderLayout.CENTER);
				throughputCenterStatisticsPanel.revalidate();
				throughputCenterStatisticsPanel.repaint();

			}
			else if(e.getSource()==waitPanelStatisticsButton){
				
				JPanel waitStatisticsTable=getWaitStatistics();
				waitStatisticsPanel.remove(buttonwaitPanel);
				waitStatisticsPanel.revalidate();
				waitStatisticsPanel.repaint();

				//waitAvgStatisticsPanel.add(waitStatisticsTable.getTableHeader(), BorderLayout.PAGE_START);
				waitAvgStatisticsPanel.add(waitStatisticsTable,BorderLayout.CENTER);
				waitAvgStatisticsPanel.revalidate();
				waitAvgStatisticsPanel.repaint();


			}

			stopWatch.actionPerformedMe(e);
		}
	}
	
	/* it is a testing code
	private void checkStringFile(){
		String s="";
		try
		{
			File pathZ = Paths.get(DataSaver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			String fullPath=uri+"data/"+getStrategyName()+"/Test1/realLoad";//String fullPath=uri+"data/"+getStrategyName()+"/Test1/realLoad";// String fullPath=uri+"data/"+getStrategyName()+"/Test1/FrequencySeries
			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work
			BufferedReader br = new BufferedReader(new FileReader(destDir));
			String sCurrentLine = null;

			while ((sCurrentLine = br.readLine()) != null)
				s+=sCurrentLine;
				sCurrentLine = null;
			br.close();



		}
		catch (Exception e){
			e.printStackTrace();
		}

		String[] splitStr = s.split("\\s+");
		s=null;
		String s2="";
		for(int i=0;i<splitStr.length;i++){
			s2+=splitStr[i]+"\n";
		}
		//JPanel scrollingPanel = new JPanel();
		//scrollingPanel.setLayout(new BorderLayout());

		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new BorderLayout());
		innerPanel.setPreferredSize(new Dimension(500,500));

		JTextArea jta=new JTextArea(50,50);
		jta.append(s2);
		//jta.append(testing);

		JScrollPane pane=new JScrollPane(jta);
		pane.setBounds(5, 5, 100, 100);
		innerPanel.add(pane, BorderLayout.CENTER);
		int result = JOptionPane.showConfirmDialog(null, innerPanel," JOB DATA", JOptionPane.OK_CANCEL_OPTION);
	} */
	/* it is a testing code
	private void checkFiles(){

	//	Vector<Double> v=new Vector<Double>();
		Vector<Integer> v=new Vector<Integer>();
		try{

			File pathZ = Paths.get(DataSaver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			String fullPath=uri+"data/"+getStrategyName()+"/Test1/staticAndDynamicLoad";// String fullPath=uri+"data/"+getStrategyName()+"/Test1/FrequencySeries
			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work
			FileInputStream  f= new FileInputStream( destDir );
			FileChannel ch = f.getChannel( );
			MappedByteBuffer mb = ch.map( FileChannel.MapMode.READ_ONLY,0L, ch.size( ) );
			//DoubleBuffer dBuf = mb.asDoubleBuffer();
			IntBuffer dBuf = mb.asIntBuffer();

			int pos = dBuf.position();
			//Set position to zero
			dBuf.position(0);
			int counter=0;
			while(dBuf.hasRemaining()){
				v.add(dBuf.get());
			}
			f.close();
			ch.close();



		}
		catch(Exception ex){ex.printStackTrace();}
		Iterator it=v.iterator();
		String s="";
		while(it.hasNext()){
			//	double n=(double)it.next();
			int n=(int)it.next();
			s+=n+"\n";
		}
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new BorderLayout());
		innerPanel.setPreferredSize(new Dimension(500,500));

		JTextArea jta=new JTextArea(50,50);
		jta.append(s);
		//jta.append(testing);

		JScrollPane pane=new JScrollPane(jta);
		pane.setBounds(5, 5, 100, 100);
		innerPanel.add(pane, BorderLayout.CENTER);
		int result = JOptionPane.showConfirmDialog(null, innerPanel," JOB DATA", JOptionPane.OK_CANCEL_OPTION);

	}*/
	private Component[] getComponents(Component container) {
		ArrayList<Component> list = null;

		try {
			list = new ArrayList<Component>(Arrays.asList(
					((Container) container).getComponents()));
			for (int index = 0; index < list.size(); index++) {
				for (Component currentComponent : getComponents(list.get(index))) {
					list.add(currentComponent);
				}
			}
		} catch (ClassCastException e) {
			list = new ArrayList<Component>();
		}

		return list.toArray(new Component[list.size()]);
	}
	private class SliderPanel extends JPanel implements ActionListener{
		JSlider slider;
		JButton change;

		SliderPanel(int min,int max,int factor){
			super(new BorderLayout());
			this.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			putClientProperty("JComponent.sizeVariant", "mini");
			slider= new JSlider(SwingConstants.VERTICAL,min,max,factor);
			change= new JButton("\u2713");
			change.setPreferredSize(new Dimension(37,20));		//change.putClientProperty("JComponent.sizeVariant", "mini");
			change.addActionListener(this);
			slider.setMajorTickSpacing(1);
			slider.setMinorTickSpacing(1);
			slider.setPaintTicks(true);
			//slider.setPaintLabels(true);
			//SwingUtilities.updateComponentTreeUI(this);
			add(slider,BorderLayout.CENTER);
			add(change,BorderLayout.SOUTH);
			//change.putClientProperty("JComponent.sizeVariant", "mini");
		}
		public void setSliderDisable(){
			this.slider.setEnabled(false);
			this.change.setEnabled(false);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==change){
				int frequency=slider.getValue();
				//	requestFrequencyPanel.setFrequency(frequency);
				randomNumberDistributor.increaseLoad(frequency);
				loadValue.setText(frequency+" req/sec");
			}

		}
	}
	public void getInputStream(){}
	public void setStopWatchForThroughputPanel() {
		// TODO Auto-generated method stub
		throughputPanel.setStopWatch(stopWatch);
	}
	public void setSaveButtonForThroughputPanel() {
		// TODO Auto-generated method stub
		throughputPanel.setSaveButton(save);
		
	}
	public void setPreviousScenarioName(String testNumberAndTpsNamePath) {
		// TODO Auto-generated method stub
		this.previousScenarioName=testNumberAndTpsNamePath;
	}
	public String getPreviousScenarioName() {
		// TODO Auto-generated method stub
		return this.previousScenarioName;
	}
	public void setTestNumber(int numberOfTests) {
		// TODO Auto-generated method stub
	this.numberOfTests=numberOfTests;	
	}
}
class JComboCheckBox extends JComboBox
{
	Vector v=new Vector();
	public Vector getSelectedStrategyNames(){
		return this.v;
	}
	public JComboCheckBox() {
		init();
	}

	public JComboCheckBox(JCheckBox[] items) {
		super(items);
		init();
	}

	public JComboCheckBox(Vector items) {
		super(items);
		init();
	}

	public JComboCheckBox(ComboBoxModel aModel) {
		super(aModel);
		init();
	}

	private void init() {
		setRenderer(new ComboBoxRenderer());
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				itemSelected();
			}
		});
	}

	private void itemSelected() {
		//   Main.text.setText(" ");
		if (getSelectedItem() instanceof JCheckBox) {
			JCheckBox jcb = (JCheckBox)getSelectedItem();
			jcb.setSelected(!jcb.isSelected()); 
			if(jcb.isSelected()) v.addElement(jcb.getActionCommand());//Main.text.append(t);
			else v.removeElement(jcb.getActionCommand());


		}
	}
	
	
	class ComboBoxRenderer implements ListCellRenderer {
		private JLabel label;

		public ComboBoxRenderer() {
			setOpaque(true);
		}

		public Component getListCellRendererComponent(JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {
			if (value instanceof Component) {
				Component c = (Component)value;
				if (isSelected) {
					c.setBackground(Color.WHITE);// c.setBackground(list.getSelectionBackground()); 
					c.setForeground(Color.BLACK);//c.setForeground(list.getSelectionForeground());
				} else {
					c.setBackground(Color.WHITE);//c.setBackground(list.getBackground());
					c.setForeground(Color.BLACK);// c.setForeground(list.getForeground());
				}

				return c;
			} else {
				if (label ==null) {
					label = new JLabel(value.toString());
					label.setBackground(Color.WHITE);// no lines was here
					label.setBackground(Color.BLACK);//no line was here
				}
				else {
					label.setText(value.toString());
				}

				return label;
			}
		}
	}
}

/*

 */
