package com.jpoolrunner.serverside;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import javax.swing.JTextArea;

import com.jpoolrunner.TPSInterface.TPSInterface;
import com.jpoolrunner.job.Task;
public class Consumer extends Thread {
	private BlockingQueue<Runnable> tempQueue=null;
	TPSInterface strategy=null;
	SharedBoolean sharedBoolean;
	SharedBoolean sharedBoolean2;
	boolean flag=false;
	RecieverCounter recieverCounter;
	RecieverHelperCounter recieverHelperCounter;
	JTextArea textArea;

	public Consumer(BlockingQueue<Runnable> tempQueue,SharedBoolean sharedBoolean,SharedBoolean sharedBoolean2,RecieverCounter recieverCounter,RecieverHelperCounter recieverHelperCounter, JTextArea textArea) {
		this.sharedBoolean=sharedBoolean;
		this.sharedBoolean2=sharedBoolean2;
		this.tempQueue = tempQueue;
		this.recieverCounter=recieverCounter;
		this.recieverHelperCounter=recieverHelperCounter;
		this.textArea=textArea;
		//this.strategy=strategy;
	}
	public void setStrategy(TPSInterface strategy){
		this.strategy=strategy;
	}
 public void run(){
		//textArea.append("\nRecieverHelper has started");

	 try{
	 do{
		 	Object jobx=(Object)tempQueue.take();
			if(jobx instanceof Poison ) {flag=true; break;}// flag to stop Response Sender
			else jobx=(Runnable) jobx;

				long timeNano=System.nanoTime();
				Task pj=(Task)jobx;
				pj.setNanoReciveTime(timeNano);
				Runnable r=(Runnable)pj;
				this.strategy.submitRequest(r);
				this.recieverHelperCounter.increment();
				
			}while(true);
	 }catch(InterruptedException e){
		 textArea.append("\ni m in Consumer");
	 }
	 catch(Exception e){
		 textArea.append("\ni m in Consumer");
	 }
	 finally{
			//textArea.append("\nRecieverHelper has stopped");
			if(flag) this.sharedBoolean2.setStopValue(true);//ie ResponSednder should stop 
		}
 }

}
