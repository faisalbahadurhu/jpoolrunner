package com.jpoolrunner.serverside;

import java.io.EOFException;
import java.net.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jpoolrunner.TPSInterface.TPSInterface;
import com.jpoolrunner.warmup.WarmUp;

//import PoolTuningStrategies.TPSInterface;

public class ServiceNameCommunicator extends Thread {
	private BlockingQueue<Runnable> tempQueue=null;
	private BlockingQueue<Runnable> responseQueue=null;


	ObjectInputStream inStream=null;
	ObjectOutputStream outStream=null;
	Socket socket;//used for closing
	ServerSocket serverSocket;
//	TPSInterface strategies[];
	TPSInterface strategy=null;
	String selectedStrategy;
	Reciever reciever=null;
	Consumer recieverHelper;
	ResponseSender responseSender=null;
	RecieverCounter recieverCounter;
	RecieverHelperCounter recieverHelperCounter;
	ResponseSenderCounter responseSenderCounter;
	boolean clientdisconnect=false;
	//ConnectionListener listener;
	
	//static volatile boolean  stop=false;
	SharedBoolean sharedBoolean;// it is used to stop threads Reciever will make it true on ClassCastException ie on reciving stop signal
	SharedBoolean sharedBoolean2;// it is used by ReciverHelper that i have no jobs in Queue it is empty so ResponseSender should stop now
	JTextArea textArea;
	private TPS_Service TPS_Service;
	private boolean warmup;
	private String tpsName;
	JTextField textFieldRequest;
	JTextField textFieldResponse;
	JProgressBar progressBar;
	public ServiceNameCommunicator(ObjectInputStream inStream, ObjectOutputStream outStream, Socket socket,
			ServerSocket serverSocket, BlockingQueue<Runnable> responseQueue, TPS_Service TPS_Service,
			JTextArea textArea,boolean warmup,String tpsName,JTextField textFieldRequest, JTextField textFieldResponse, JProgressBar progressBar) {
		// TODO Auto-generated constructor stub
		this.textFieldRequest=textFieldRequest;
		this.textFieldResponse=textFieldResponse;
		this.progressBar=progressBar;
		this.warmup=warmup;
		this.tpsName=tpsName;// in case after warmup it is needed to initialize the tps again
		this.inStream=inStream;
		this.outStream=outStream;
		this.socket=socket;
		this.serverSocket=serverSocket;
		this.responseQueue=responseQueue;
		this.responseQueue.clear();//Removes all of the elements from this collection (optional operation). The collection will be empty after this method returns.
		this.TPS_Service=TPS_Service;
		this.sharedBoolean=new SharedBoolean(false);
		this.sharedBoolean2=new SharedBoolean(false);
		this.textArea=textArea;
		tempQueue=new LinkedBlockingQueue<Runnable>();
		this.recieverCounter=new RecieverCounter();
		this.recieverHelperCounter=new RecieverHelperCounter();
		this.responseSenderCounter=new ResponseSenderCounter();
		reciever=new Reciever(this.inStream,this.outStream,this.sharedBoolean,tempQueue,this.recieverCounter,textArea,textFieldRequest,  textFieldResponse,  progressBar);
		//strategy is sent to Consumer through setstrategy method
		this.recieverHelper=new Consumer(this.tempQueue,this.sharedBoolean,this.sharedBoolean2,this.recieverCounter,this.recieverHelperCounter,textArea);
		//strategy is sent to ResponseSender through setstrategy method for shutdown call

	//	responseSender=new ResponseSender(this.outStream,this.inStream,this.socket,this.serverSocket,this.strategies, this.responseQueue,this.sharedBoolean,this.sharedBoolean2,this.recieverHelperCounter,this.responseSenderCounter,textArea);
		responseSender=new ResponseSender(this.outStream,this.inStream,this.socket,this.serverSocket,this.TPS_Service, this.responseQueue,this.sharedBoolean,this.sharedBoolean2,this.recieverHelperCounter,this.responseSenderCounter,textArea,textFieldRequest,  textFieldResponse,  progressBar);
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
			try{
				do{
					if(warmup==false){
		textArea.append("\n ServiceNameCommunicator has started and waiting for the name of TPS to test");
		
		
			selectedStrategy=(String)this.inStream.readObject();
			textArea.append("\nClient requested to test "+selectedStrategy);
			this.strategy=TPS_Service.serviceLoader(selectedStrategy);
			this.strategy.setQueue(responseQueue);
			this.strategy.initAllConsumers();

			int initialPoolSize=this.strategy.getWorkerThreadPoolSize();
			
			 outStream.writeObject(initialPoolSize+"");
		      outStream.flush();
					}
					
					else if(warmup==true){
						textArea.append("\nAfter warmup client will now test "+tpsName);
						this.strategy=TPS_Service.serviceLoader(tpsName);
						this.strategy.setQueue(responseQueue);
						this.strategy.initAllConsumers();

					//	int initialPoolSize=this.strategy.getWorkerThreadPoolSize();
						
					//	 outStream.writeObject(initialPoolSize+"");
					//      outStream.flush();	
						
					}
  
			/*
			 * Now client will press start button to start sending requests so when start button is preesed a protocol is send to this Thread called "starting" and when this thread will recieve this protocol it will start Reciever and ResponseSender
			 */
			textArea.append("\nServer is waiting for client to start sending Tasks/Requests");
			String protocol=(String) this.inStream.readObject();
			
			if(protocol.equals("starting")){
				textArea.append("\nClient pressed start button to start sending requests");
				this.recieverHelper.setStrategy(strategy);
				responseSender.setStrategy(strategy);
				this.reciever.start();
				this.recieverHelper.start();
				this.responseSender.setWarmup(false);
				this.responseSender.start();
				break;
			}
			
			else if(protocol.equals("warmup")){
				textArea.append("\nClient pressed warmup button to start sending requests");
				this.recieverHelper.setStrategy(strategy);
				responseSender.setStrategy(strategy);
				this.reciever.start();
				this.recieverHelper.start();
				this.responseSender.setWarmup(true);
				this.responseSender.start();
				break;
			}
			else if(protocol.equals("notstarting")){
				textArea.append("\n Client denied testing "+this.strategy.getName());
				
				//textArea.append("\n "+this.strategy.getName()+" is shutting down");
				this.strategy.shutDownPool();
				textArea.append("\n "+this.strategy.getName()+" has been shut down");
				this.strategy=null;
				warmup=false;//if he warmed up but did not start simulation and closed the pane....
				continue;
				}
			
		
		}while(true);
		}
		catch(ClassNotFoundException e){
			this.clientdisconnect=true;
			textArea.append("\nI am inside StrategyNameCommunicator ClassNotFoundException"); 
			textArea.append("\nPoolRunner Client side Disconnected");
		//	e.printStackTrace();

		}
		catch(EOFException e){
			this.clientdisconnect=true;
			textArea.append("\n From StrategyNameCommunicator the PoolRunner Client side Disconnected");
		//	e.printStackTrace();
		}
		catch(IOException e){//on SoCketException is also handled by this catch 
		//	e.printStackTrace();
			this.clientdisconnect=true;

			textArea.append("\nI am inside StrategyNameCommunicator IOException"); 
			textArea.append("\nPoolRunner Client side Disconnected");


		}
			catch(Exception e){//on SoCketException is also handled by this catch 
				

					textArea.append("\nI am inside StrategyNameCommunicator Exception"); 
				//	textArea.append("\nPoolRunner Client side Disconnected");


				}
			
			
			
		finally{
			if(clientdisconnect)
			{ 
				try{ 
				if(this.strategy!=null){
					this.strategy.shutDownPool(); this.strategy=null;
					}
				this.inStream.close();
			    this.outStream.close();
			    this.socket.close();
			    this.serverSocket.close();
			    
				new ConnectionListener(responseQueue,TPS_Service,textArea, textFieldRequest,  textFieldResponse,  progressBar).start();
			}
				catch(Exception e) {textArea.append("\nI am inside StrategyNameCommunicator finally clause"); }
			}
		}
	
		
	}

}
