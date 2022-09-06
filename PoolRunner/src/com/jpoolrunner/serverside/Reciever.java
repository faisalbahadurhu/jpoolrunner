/*
 * Reciever just recives Jobs from strea and put it in a List which is ConcurrentLinkedQueue<Runnable> tempQueue and its helper Thread ReciverHelper will take requests from list and submit that requests to a strategy
 */
package com.jpoolrunner.serverside;
//package Clientside;
/*
 * //Reciever will stop when "stop" signal is send from client upon clicking stop button and trying to cast of Sting from IOBoundJob in run method would give an ClassCastException and this Thread would b stopped and in Catch clause the shared variable is set to true to stop RespondSender Thraead too
 */

//import PoolTuningStrategies.PoolTuningStrategy;
//import java.nio.channels.
import java.io.*;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class Reciever extends Thread {
	//private ConcurrentLinkedQueue<Runnable> responseQueue=null;
	private BlockingQueue<Runnable> tempQueue=null;

	SharedBoolean sharedBoolean;
	ObjectInputStream inStream=null;
	ObjectOutputStream outStream=null;
	private LinkedList queue;
	boolean flag=false;
	RecieverCounter recieverCounter;
	JTextArea textArea;
	//RecieverHelper recieverHelper;
	JTextField textFieldRequest;
	JTextField textFieldResponse;
	JProgressBar progressBar;
	boolean firstRequest=true;
	
	
public Reciever(ObjectInputStream inStream,ObjectOutputStream outStream,SharedBoolean sharedBoolean, BlockingQueue<Runnable> tempQueue,RecieverCounter recieverCounter, JTextArea textArea, JTextField textFieldRequest, JTextField textFieldResponse, JProgressBar progressBar) {
	this.textFieldRequest=textFieldRequest;
	this.textFieldResponse=textFieldResponse;
	this.progressBar=progressBar;
		this.sharedBoolean=sharedBoolean;
		this.inStream=inStream;
		this.outStream=outStream;
		this.tempQueue=tempQueue;
		this.recieverCounter= recieverCounter;
		this.textArea=textArea;

		
	}
	@Override
	public void run(){
	//	textArea.append("\nReciever has started");
		//do{//
			try{
				do{	
				
					Runnable job=(Runnable)inStream.readObject();//Recieve will stop when "stop" signal is send from client upon clicking stop button and this cast of Sting from IOBoundJob would give an ClassCastException and this Thread would b stopped and in Catch clause the shared variable is set to true to stop RespondSender Thraead too
					if(this.firstRequest) {
					this.progressBar.setVisible(true);
					this.progressBar.setIndeterminate(true);
						firstRequest=false;
					}
					this.recieverCounter.increment();
					this.textFieldRequest.setText(recieverCounter.getCounter()+"");
					this.tempQueue.put(job);
				
				}while(true);
				
			}catch(ClassCastException e){//ie String "stop" is recieved instead of IoBound object
				flag=true;
				try{	this.tempQueue.put(new Poison());}catch(Exception cce){}// to stop RecieverHelper

			//	textArea.append("\nReciever noticed a Stop signal from client");
				
			}
			catch(EOFException e){textArea.append("\nClient has disconnected and Reciever has stopped due to EOFException in Reciever Thread");}
			catch(Exception e){
				textArea.append("\nClient has disconnected and Reciever has stopped due to an Exception");
				}
			finally{
				if (flag){
					//textArea.append("\nReciever recieved Jobs="+this.recieverCounter.getCounter());
					this.sharedBoolean.setStopValue(true);//signal to ResponseSender to stop
					
				}
				//textArea.append("\nReciever has stopped");
			}
		}
	}
