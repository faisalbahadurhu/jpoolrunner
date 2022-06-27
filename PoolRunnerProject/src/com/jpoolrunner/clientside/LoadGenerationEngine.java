/** This class is inialized in RequestFrequencyPanel
 * 
 */
package com.jpoolrunner.clientside;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;

import com.jpoolrunner.clientside.RequestfrequencyPanel.DataGenerator;
import com.jpoolrunner.frequencyDistribution.FrequencyDistributorInterface;
import com.jpoolrunner.job.CpuBoundTask;
import com.jpoolrunner.job.IoBoundTask;
import com.jpoolrunner.job.Task;

import java.io.*;
import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class LoadGenerationEngine extends Timer implements ActionListener{
	FrequencyDistributorInterface randomNumberDistributor;
	FrequencyGenerator frequencyGenerator;
	ObjectOutputStream outStream=null;
	Frequencyholder freqHolder;
	private boolean isFixedIntensityTrue;
	private String fixedIntensity;
	private String lowerIntensity;
	private String upperIntensity;
	Vector throughputs;
	long delay=500;//by default it is half second
	JLabel reqSentvalue;
	JLabel loadValue;
	long totalReqSent=0;
	int pass=0;//after how many times frequencyGenerator will be called i set after 10sec ie 
	boolean manualLoad;
	Vector<String> loadOntheServer;
	Vector<Integer> requestFrequencies;//=new Vector<Integer>(1000);//it will store request frequencies
	Vector<String> realLoad;//=new Vector<ParentJob>(1000);//this vector will be filled at the time of job xtraction in LoadGenerationEngine
	Vector<Integer> requestFrequenciesClone;//=new Vector<Integer>(1000);//it will store request frequencies
	Vector<String> realLoadClone;//=new Vector<ParentJob>(1000);//this vector will be filled at the time of job xtraction in LoadGenerationEngine
	
	boolean usePreviousScenario;
	Vector<String> tempVector;
	DataGenerator dataGenerator;
	private StopWatch stopWatch;
	JFreeChart chart; 
	XYSeries frequencySeries;
	
	public void setRequestSenderLoadAttributes(FrequencyDistributorInterface randomNumberDistributor,Vector<Integer> requestFrequencies,Vector<String> loadOntheServer,Vector<String> realLoad, boolean usePreviousScenario){
		this.loadOntheServer=loadOntheServer;
		tempVector=(Vector<String>)this.loadOntheServer.clone();
		this.requestFrequencies=requestFrequencies;
		this.realLoad=realLoad;
		this.randomNumberDistributor=randomNumberDistributor;
		this.usePreviousScenario=usePreviousScenario;
	
	}
	
	public void setRequestSenderFrequencyAndLoadAttributes(Vector<Integer> requestFrequencies,Vector<String> realLoad, boolean usePreviousScenario, StopWatch stopWatch){
		
		this.requestFrequencies=requestFrequencies;
		this.requestFrequenciesClone=(Vector)this.requestFrequencies.clone();
		this.totalReqSent=0;//coz this useprevious scenario is true
		this.realLoad=realLoad;
		this.realLoadClone=(Vector)this.realLoad.clone();
		this.usePreviousScenario=usePreviousScenario;
this.stopWatch=stopWatch;
	}

	//ObjectOutputStream outStream;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
public LoadGenerationEngine(int interval,Frequencyholder fh,ObjectOutputStream outStream, JLabel reqSentvalue, Vector throughputs, boolean manualLoad, JLabel loadValue, DataGenerator dataGenerator, JFreeChart chart, XYSeries frequencySeries){
	super(interval,null);
	this.freqHolder=fh;
	this.outStream=outStream;
	this.reqSentvalue= reqSentvalue;
	this.throughputs= throughputs;
	this.manualLoad=manualLoad;
	this.loadValue= loadValue;
	this.dataGenerator=dataGenerator;
	this.chart=chart;
	this.frequencySeries=frequencySeries;
	frequencyGenerator=new FrequencyGenerator( throughputs,this.freqHolder,this.reqSentvalue);
	addActionListener(this);
	
	
	//this.setInitialDelay(1000);//start this timer afer 1 second.
}
public LoadGenerationEngine(int interval){
	super(interval,null);
	addActionListener(this);

	
}
public void setManualLoad(boolean t){
	this.manualLoad=t;
	
}
@Override
public void actionPerformed(ActionEvent e){
	try {
		/*
	//////////////////////////also must uncomment this.pass++ at the end of this function

		if((this.manualLoad==false) && (this.pass==10)){
			this.frequencyGenerator.generate();
			this.pass=0;// ie after 10th pass or 10 sec this.frequencyGenerator.generate() would be called and then again set pass to 0 so that u can take 10 next passes and so on ie after every 10 sec this.frequencyGenerator.generate() is called
			loadValue.setText(freqHolder.getFrequency()+" req/sec");
			}
		
	for(int i=1;i<=freqHolder.getFrequency();i++){
		this.totalReqSent++;
		if(isFixedIntensityTrue){
		
			this.delay=(long)(Double.parseDouble(fixedIntensity)*1000); //eg it was .25 so .25*1000=250 ie 250ms similarly if 0.5 so .5*1000=500ms and so on
		}
		else{
			int a= 1+(int) (Math.random()*2);
			if(a==1) this.delay=(long)(Double.parseDouble(this.lowerIntensity)*1000);//eg it was .25 so .25*1000=250 ie 250ms similarly if 0.5 so .5*1000=500ms and so on
			else this.delay=(long)(Double.parseDouble(this.upperIntensity)*1000);//eg it was .25 so .25*1000=250 ie 250ms similarly if 0.5 so .5*1000=500ms and so on

		}		
		this.reqSentvalue.setText(""+this.totalReqSent);
		outStream.writeObject(new IoBoundTask(this.delay));
		outStream.flush();
		
	}
	/////////////////////////////////////also must uncomment this.pass++ at the end of this function if u want to restore previous code
*/
		////////////////////////////////////
		int randomNumber=0;
		if(this.usePreviousScenario==false){
		//	long t1=System.nanoTime();
			randomNumber=randomNumberDistributor.getRandomNumber();//it is frequency 
			if(randomNumber<0) randomNumber*=-1;//if generated random number is -ve convert it to +ve e.g. in Guassian distribution it may generate a -ve number
			this.freqHolder.setFrequency(randomNumber);
		//	requestFrequencies.add(randomNumber);//no use of it as it is taken 4m disk now
			for(int i=1;i<=randomNumber;i++){
				if(this.tempVector.size()==0)
					{
					tempVector=(Vector<String>)this.loadOntheServer.clone();
					//Collections.shuffle(tempVector);// it is shuffled only 1 time in Scenario class but here it can create performance problems so i dont shuffle it here

					}
				String intensityAndType=tempVector.firstElement();//e.g. I250 or C100
				tempVector.remove(0);
				String type=intensityAndType.substring(0, 1);//I or C is type now
				int intensity=Integer.parseInt(intensityAndType.substring(1)); //250 or 1000 or whatever
					
				IoBoundTask ioBound=null;
				CpuBoundTask cpuBound=null;
				Task task=null;
				
				
				if(type.equalsIgnoreCase("I")){ioBound=new IoBoundTask(intensity); task=(Task) ioBound;}
				else {cpuBound=new CpuBoundTask(intensity);task=(Task)cpuBound; }
				
				
				this.totalReqSent++;
				task.setId((int)totalReqSent);
				realLoad.add(intensityAndType);//for later use in previous strategy
				outStream.writeObject(task);
				outStream.reset();
				outStream.flush();
				this.reqSentvalue.setText(""+this.totalReqSent);
				ioBound=null;
				cpuBound=null;
			

			}

		}
		else {//ie use previous scenario is true so realLoad and requetFrequencies contains Load and frequencies 
			if(requestFrequenciesClone.size()==0){ // ie all the Load of previous strategy has been successfully sent
				this.stopMe();
				dataGenerator.stop();
				changeChartofRequestFrequencyPanel();
			
				try{
					outStream.writeObject("stop");// it is a signal to StrategyNameCommuncator Thread on the server side that i m going to send the Jobs ...when this signal is recieved by StrategyNameCommuncator then StrategyNameCommuncator wil start Reciever and ResponseSender Threads
					outStream.reset();
					outStream.flush();
					GCRunner gcRunner=new GCRunner();
					gcRunner.setPriority(Thread.MAX_PRIORITY);
					gcRunner.start();
				}catch(Exception ex){
				//	System.out.println("Exception in Mainframe in stop button sending a protocol of stop");
					//JOptionPane.showMessageDialog(null, "Server is not Running/n Exception in Mainframe in stop button sending a protocol of stop");
				//	System.exit(0);
				}
				
			}
			else {
				randomNumber=requestFrequenciesClone.firstElement();
				requestFrequenciesClone.remove(0);
				this.freqHolder.setFrequency(randomNumber);
				for(int i=1;i<=randomNumber;i++){
				String intensityAndType=(String)realLoadClone.firstElement();
				realLoadClone.remove(0);	
				String type=intensityAndType.substring(0, 1);//I or C is type now
				int intensity=Integer.parseInt(intensityAndType.substring(1)); //250 or 1000 or whatever

				IoBoundTask ioBound=null;
				CpuBoundTask cpuBound=null;
				Task task=null;
				this.totalReqSent++;

				if(type.equalsIgnoreCase("I")){ioBound=new IoBoundTask(intensity); task=(Task) ioBound;}
				else {cpuBound=new CpuBoundTask(intensity);task=(Task)cpuBound; }
				
				task.setId((int)totalReqSent);
				outStream.writeObject(task);
				outStream.reset();
				outStream.flush();
				this.reqSentvalue.setText(""+this.totalReqSent);
				ioBound=null;
				cpuBound=null;
				}
			
			}
			}
		
			
		/////////////////////////////////
	}catch(EOFException ee){
		System.out.println("I am inside LoadGenerationEngine EOFException"); 
		//System.out.println("PoolRunner Client side Disconnected");
	}
	catch (IOException ee) {
		JOptionPane.showMessageDialog(null, "You have been disconnected and IOException has thrown in RequestSende");
		this.stop();
    }
//	this.pass++;//ie after completing a pass it should be incremented
}

private void changeChartofRequestFrequencyPanel() {
	// TODO Auto-generated method stub
	double minimum=frequencySeries.getMinY();
	double maximum=frequencySeries.getMaxY();//getMinY();
    final XYPlot plot = chart.getXYPlot();//domain is x and range is y
    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	   rangeAxis.setRange(minimum-1.0,maximum+1.0);
}

public void startMe(){this.start();}
public void restartMe(){this.restart();}
public void stopMe(){this.stop();}
public void setFixedIntensityTrueOrFalse(boolean b) {
	// TODO Auto-generated method stub
	this.isFixedIntensityTrue=b;
	
}
public void setFixedIntensityValue(String fixedIntensity) {
// TODO Auto-generated method stub
this.fixedIntensity=fixedIntensity;

}
public void setIntensityRange(String lowerIntensity,String upperIntensity) {
// TODO Auto-generated method stub
this.lowerIntensity=lowerIntensity;
this.upperIntensity=upperIntensity;

}
}

