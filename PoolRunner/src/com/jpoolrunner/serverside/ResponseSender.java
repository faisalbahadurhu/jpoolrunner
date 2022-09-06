package com.jpoolrunner.serverside;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.lang.reflect.InvocationTargetException;
import java.util.ServiceLoader;

import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jpoolrunner.TPSInterface.TPSInterface;
import com.jpoolrunner.clientside.GCRunner;
import com.jpoolrunner.job.Task;
import com.jpoolrunner.startingwindow.DialogueTest;
public class ResponseSender extends Thread {
	BlockingQueue<Runnable> responseQueue=null;
	ObjectOutputStream outStream=null;
	SharedBoolean sharedBoolean;
	SharedBoolean sharedBoolean2;
	ObjectInputStream inStream;
	Socket socket;//just send it to StrategyNamesCommunicator 
	ServerSocket serverSocket;//just send it to StrategyNamesCommunicator
	TPSInterface strategy=null;

	//TPSInterface strategies[];
	boolean flag=false;
	RecieverHelperCounter recieverHelperCounter;
	ResponseSenderCounter responseSenderCounter;
//	PoolSizeSetter poolSizeSetterTimer;
	//Toggle toggler;
	JTextArea textArea;
	int forceStop=0;//
	private TPS_Service TPS_Service;
	//TestToggler testMe;
	boolean warmup=true;
	private JTextField textFieldRequest;
	private JTextField textFieldResponse;
	private JProgressBar progressBar;
	
	public boolean isWarmup() {
		return warmup;
	}
	public void setWarmup(boolean warmup) {
		this.warmup = warmup;
	}
	public ResponseSender(ObjectOutputStream outStream, ObjectInputStream inStream, Socket socket,
			ServerSocket serverSocket, TPS_Service TPS_Service, BlockingQueue<Runnable> responseQueue,
			SharedBoolean sharedBoolean, SharedBoolean sharedBoolean2, RecieverHelperCounter recieverHelperCounter,
			ResponseSenderCounter responseSenderCounter, JTextArea textArea, JTextField textFieldRequest, JTextField textFieldResponse, JProgressBar progressBar) {
		// TODO Auto-generated constructor stub
		this.textFieldRequest=textFieldRequest;
		this.textFieldResponse=textFieldResponse;
		this.progressBar=progressBar;
		this.outStream=outStream;
		this.inStream=inStream;
		this.socket=socket;
		this.serverSocket=serverSocket;
		this.TPS_Service=TPS_Service;
		this.responseQueue=responseQueue;
		this.sharedBoolean=sharedBoolean;
		this.sharedBoolean2=sharedBoolean2;
		this.recieverHelperCounter=recieverHelperCounter;
		this.responseSenderCounter=responseSenderCounter;
		this.textArea=textArea;
	}
	private void loadAgain(){
		  
	}
	private boolean checkStop(){
		if(this.sharedBoolean2.getStopValue()==true && this.recieverHelperCounter.getCounter()==this.responseSenderCounter.getCounter())
			return true;
		else return false;
	}
	public void setStrategy(TPSInterface strategy){
		this.strategy=strategy;
	}
	@Override
	public void run() {
	//	textArea.append("\nResponseSender has started");
	//	this.poolSizeSetterTimer.start();
		boolean firstEntry=true;
		Runnable r;
		try{
			do{
				if (checkStop())  {flag=true;	 break;}
				if(firstEntry){
					r=(Runnable)responseQueue.poll(4000L,TimeUnit.MILLISECONDS);
					firstEntry=false;
				}
				else  r=(Runnable)responseQueue.poll(2000L,TimeUnit.MILLISECONDS);
				if(r==null && (checkStop())){flag=true;break;}
				//Faisal
				if(r==null){System.out.println("r is null dear");}
				Task task=(Task)r;
					task.setPoolsize(strategy.getWorkerThreadPoolSize());
					r=(Runnable)task;
					outStream.writeObject(r);
					outStream.reset();
					outStream.flush();
					this.responseSenderCounter.increment();
					this.textFieldResponse.setText(responseSenderCounter.getCounter()+"");
		
			}while(true);
		   }
		catch(InterruptedException e){
			 textArea.append("\ni m in Consumer");
		 }
			catch(SocketException e){textArea.append("\nClient Disconnected SocketException in ResponseSender i.e. socket write error");}
			catch(Exception e){
				textArea.append("\nClient Disconnected Exception in ResponseSender");
				e.printStackTrace();
			  }
		finally{
		//	textArea.append("\nIn Finally clause ResponseSender has stopped ");
		
			if(flag){//ie stop signalis sent to the client
				try{
				this.progressBar.setIndeterminate(false);
					progressBar.setVisible(false);
					outStream.writeObject("stop");
					outStream.reset();
					outStream.flush();	
				}catch(Exception e){textArea.append("\nResponseSender Exception in finally clause's try catch");
				e.printStackTrace();}
			//	textArea.append("\nResponseSender has sent Jobs="+this.responseSenderCounter.getCounter());
				this.strategy.shutDownPool();//to stop all threads of strategy & all threads are in wait state because MyQueue is empty
				textArea.append("\n");
				textArea.append(this.strategy.getName()+" with pool size "+this.strategy.getWorkerThreadPoolSize()+" has shut down");
				String tpsName=this.strategy.getName();
			//	tpsName=tpsName.replaceAll("\\s+","");//removes all whitespaces and non-visible characters (e.g., tab, \n)

				this.strategy=null;
				 GCRunner gcRunner=new GCRunner();
		            gcRunner.setPriority(Thread.MAX_PRIORITY);
		            gcRunner.start();
				
				
				new ServiceNameCommunicator(this.inStream,this.outStream,this.socket,this.serverSocket,this.responseQueue,this.TPS_Service,textArea,warmup,tpsName,textFieldRequest,  textFieldResponse,  progressBar).start();

				
			}
		}
	}
}
