package com.jpoolrunner.clientside;
import java.io.*;
import javax.swing.*;

import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;

import com.jpoolrunner.job.Task;

import java.awt.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;
public class RecieverX extends Thread {
	ObjectInputStream inStream=null;
	Producer producer;
	private ConcurrentLinkedQueue queue;
	NonblockingCounter throughput;
	ThroughputPanel throughputPanel;// reference to stop it
	ResponsePanel responsePanel;
	WaitPanel waitPanel;
	PoolSizePanel poolSizePanel;
	AcceptanceLatencyPanel acceptanceLatencyPanel;
	JLabel responserecievedValue;
	PoolSizeGetter poolSizer;
	BooleanChecker sleepTime;
//	String s="";
	//public TesterReciever(ObjectInputStream inStream,NonblockingCounter throughput) {
	public RecieverX(JLabel responserecievedValue, ObjectInputStream inStream,Producer producer,NonblockingCounter throughput,ThroughputPanel throughputPanel,ResponsePanel responsePanel,PoolSizePanel poolSizePanel,AcceptanceLatencyPanel acceptanceLatencyPanel, WaitPanel waitPanel, PoolSizeGetter poolSizer, BooleanChecker sleepTime) {
		this.responserecievedValue=responserecievedValue;
		this.inStream=inStream;		// TODO Auto-generated constructor stub
		queue = new ConcurrentLinkedQueue();
		this.producer=producer;
		this.throughput=throughput;
		this.throughputPanel=throughputPanel;
		this.responsePanel=responsePanel;
		this.poolSizePanel=poolSizePanel;
		this.acceptanceLatencyPanel=acceptanceLatencyPanel;
		this.waitPanel=waitPanel;
		this.poolSizer=poolSizer;
		this.sleepTime=sleepTime;
		
		
	}
	@Override
	public void run() {
	
		try{
			
			do{	
				//Object ob=inStream.readObject();
				//if(!(ob instanceof String)){
			//IoBoundTask obj=(IoBoundTask)ob;
				Runnable obj=(Runnable)inStream.readObject();
			this.throughput.increment();
			this.responserecievedValue.setText(""+this.throughput.getValue());
			producer.produceMessages(obj);
		//	queue.add(obj);
			
			Task task=(Task)obj;//
			if(task.getPoolsize()>0){ this.poolSizer.setPoolSize(task.getPoolsize());}//at server side ResponseSender embedded poolsize in the sending object silently ie not embedded in all jobs 
		//	jta.append("Client recived back Task#"+c);
		//	IoBoundTask jobx=(IoBoundTask)queue.remove();
		//	jta.append("\nTotal TAT of job#"+c+"="+(jobx.getSendTime()-jobx.getReciveTime()));
			//jta.append("\nTotal Nano response time of job#"+c+"="+(jobx.getNanoEnQueueTime()-jobx.getNanoReciveTime()));
			//jta.append(""+(jobx.getNanoEnQueueTime()-jobx.getNanoReciveTime())+"\n");
		//	jta.append("Response time"+(jobx.getSendTime()-jobx.getReciveTime())+"\n");
			
			//	}
		//	Thread.sleep(200);
				}while(true);
			
			//this.inStream.close();
			//queue.addLast(obj);
		}
		catch(ClassCastException e){//ResponseSender sent "stop" signal from server side....RecieverX has ClassCastException because ResponseSender sent stop signal in form of String which cant be casted into IoBoundTask and stopped
			/////////////////////System.out.println("RecieverX has ClassCastException");
			this.throughputPanel.stopThroughputPanel();
			this.throughputPanel.changeChart();
			this.responsePanel.stopResponsePanel();
		//	JOptionPane.showMessageDialog(null, "Total Responses ="+responsePanel.getTotalResponsesinResponsePanelGraphs());

			this.poolSizePanel.stopPoolSizePanel();
		//	this.acceptanceLatencyPanel.stopAcceptanceLatencyPanel();
			this.waitPanel.stopWaitPanel();//no problem with stopResponsePanel
			this.sleepTime.setSend(false);//so that ResponsePanel and waitPanel timers run without sleeping to print results fatly
		//	JOptionPane.showMessageDialog(null, s);
		 //   printResponsePanelData();

		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "You have been disconnected and Exception has thrown in RecieverX");
			System.out.println("I m in RecieverX");
			e.printStackTrace();
			//this.interrupt();
		}
	}

/*private void printResponsePanelData(){
	String s="";
	XYSeries series1=responsePanel.getSeries();
	LinkedList<Double> doubleList = new LinkedList<Double>();

	 for(Object o: series1.getItems()) {
         XYDataItem xydi = (XYDataItem)o;
         doubleList.add(xydi.getY().doubleValue());
      //   s+="\n"+curY;
         
         
         }
	 Collections.sort(doubleList);
	 double n=(90.0/100.0)*(doubleList.size());
	 int position=(int)(Math.round(n)-1);
//	 return (doubleList.get(position));
	 JOptionPane.showMessageDialog(null,doubleList);
	
}*/

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
