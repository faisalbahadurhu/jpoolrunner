package com.jpoolrunner.serverside;
import java.io.*;
//import PoolTuningStrategies.PoolTuningStrategy;
import java.net.*;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jpoolrunner.warmup.WarmUp;
public class ConnectionListener extends Thread {
	private BlockingQueue<Runnable> responseQueue=null;

	ServerSocket serverSocket;
	Socket socket;
	ObjectInputStream inStream=null;
	ObjectOutputStream outStream=null;
	ServiceNameCommunicator strategyNameCommunicator;
	JTextArea textArea;
	private TPS_Service TPS_Service;
	JTextField textFieldRequest;
	JTextField textFieldResponse;
	JProgressBar progressBar;
		
	public ConnectionListener(BlockingQueue<Runnable> responseQueue, TPS_Service TPS_Service, JTextArea textArea, JTextField textFieldRequest, JTextField textFieldResponse, JProgressBar progressBar) {
	// TODO Auto-generated constructor stub
		this.textFieldRequest=textFieldRequest;
		this.textFieldResponse=textFieldResponse;
		this.progressBar=progressBar;
		this.responseQueue=responseQueue;
		this.responseQueue.clear();//Removes all of the elements from this collection (optional operation). The collection will be empty after this method returns.
		this.TPS_Service=TPS_Service;
		this.textArea=textArea;	
}
/*	public void closeConnection(){

		try {
	        socket.close();
	        serverSocket.close();
	        textArea.append("\nServer stopped listening!");
	    } catch (IOException e) { 	textArea.append("\n "+e.toString());    }
	}*/
	public void run(){
		try{
			Vector names=TPS_Service.getTPSNames();
			serverSocket=new ServerSocket(2004);
			textArea.append("\nServer is Running and waiting for client");
			socket=serverSocket.accept();
			textArea.append("\nClient connected from "+socket.getInetAddress());
			/* this.outStream=new ObjectOutputStream(new BufferedOutputStream( socket.getOutputStream()));
		    outStream.flush();
		    
		 //   this.inStream=new 	ObjectInputStream(socket.getInputStream());
		    this.inStream=new ObjectInputStream(new BufferedInputStream( socket.getInputStream()));
		    */
			this.outStream=new ObjectOutputStream(new BufferedOutputStream( socket.getOutputStream()));
			outStream.flush();
			this.inStream=new ObjectInputStream(new BufferedInputStream( socket.getInputStream()));
			
			
			/*
			 Following lines send names of all strategies names on server side to the client
			 
			 */
				Iterator it=names.iterator();
				while(it.hasNext()){
					outStream.writeObject((String)it.next());
				      outStream.flush();
				}
				
				/*for(int i=0;i<this.strategies.length;i++){
					String name=this.strategies[i].getName();
				      outStream.writeObject(name);
				      outStream.flush();
				    }*/
				    //end of strategies names
					outStream.writeObject("END NAMES...");
			      outStream.flush();
			      /*
			       Now wait for client
			       
			       */
			      textArea.append("\nServer has sent the strategy names to "+socket.getInetAddress());
					/*
					 * now initialize StrategyNameCommunicator Thread and run it;
					 */
			      
			  //    strategyNameCommunicator=new StrategyNameCommunicator(this.inStream,this.outStream,this.socket,this.serverSocket,this.responseQueue,this.strategies,textArea);
			      strategyNameCommunicator=new ServiceNameCommunicator(this.inStream,this.outStream,this.socket,this.serverSocket,this.responseQueue,this.TPS_Service,textArea,false," ", textFieldRequest,  textFieldResponse,  progressBar);

			      strategyNameCommunicator.start();
				
		}
		
		catch(EOFException e){
			textArea.append("\n From ConnectionListener the PoolRunner Client side Disconnected");
		}
		catch(IOException e){
			textArea.append("\n Listener is stopped! "); 			

		}
		catch(Exception e){
			textArea.append("\n Exception  inside ConnectionListener Exception"); 

		}
		
		
	}
}
