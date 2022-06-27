
package com.jpoolrunner.clientside;
import java.io.*;

//import bulk.BulkStrategies;
import javax.swing.*;
import javax.swing.tree.*;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.util.LinkedList;
public class ConnectToServer {
	Socket socket;
	LinkedList list;
	ObjectInputStream inStream=null;
	ObjectOutputStream outStream=null;
	String ip;
	//JLabel infoLabel;
	JTree tree;
	boolean connected=false;
	//PoolTuningStrategy strategies[];

	public ConnectToServer(String ip,JTree tree){
		this.ip=ip;
		this.tree=tree;
		//this.infoLabel=infoLabel;
		list=new LinkedList();
	}
	public boolean connect(){
		
		try {
			
		//	javax.swing.SwingUtilities.invokeLater(new Runnable() {
		//		public void run(){
					/*SplashScreen sc=new SplashScreen();
					Thread t=new Thread(sc);
					t.start();*/

		//		}
		//	});

		//	try{Thread.sleep(2000);}catch(Exception e){}
		/*	simulationGUIPanel.remove(scenariosPane);
			JPanel tempPanel=new JPanel(new FlowLayout());
			tempPanel.setPreferredSize(new Dimension(100,100));
			JLabel label=new JLabel("Connecting......Please wait!");
			tempPanel.add(label);
			simulationGUIPanel.add(tempPanel,BorderLayout.CENTER);
			simulationGUIPanel.revalidate();
			simulationGUIPanel.repaint();*/
			
			
		    socket = new Socket(ip, 2004);
		    System.out.println("Client starting");
		  //  this.outStream=new 	ObjectOutputStream(socket.getOutputStream());
		    this.outStream=new ObjectOutputStream(new BufferedOutputStream( socket.getOutputStream()));
		    outStream.flush();
		    
		 //   this.inStream=new 	ObjectInputStream(socket.getInputStream());
		    this.inStream=new ObjectInputStream(new BufferedInputStream( socket.getInputStream()));
		    
		  //  this.infoLabel.setText("Connection Sucessful");
		    
		    String name;
		    DefaultMutableTreeNode root=null;;
		    DefaultTreeModel model;	  
		   // while("END NAMES...".compareTo( (name=(String)this.inStream.readObject())) != 0 ){
		    while( ! "END NAMES...".equals( (name=(String)this.inStream.readObject())) ){  
		   // System.out.println(name);
		    //	name=name.replaceAll("\\s+","");
		    
		    	// name=(String)this.inStream.readObject();
		    	  root=(DefaultMutableTreeNode) this.tree.getModel().getRoot();
		    	 root.add(new DefaultMutableTreeNode(name));
		    	 model = (DefaultTreeModel) (tree.getModel()); 
		    	   model.reload();
		    //	 System.out.println("#"+name);
		    	// list.add(name);
		    	 list.addLast(name);
		    	 
		    }//while(name.equals("END NAMES..."));
		 //   sc.setVisible(false);

		    JOptionPane.showMessageDialog(null, "Connected to Server\n Names of loaded strategies recieved from Server");
		    if ((root.getChildCount())>0){
		    	this.connected=true;
		    	tree.setToolTipText("select a strategy");
		    	
		    }
		}catch(EOFException e){
			JOptionPane.showMessageDialog(null,"EOFException");
			System.out.println("I am inside Connector EOFException"); 
			connected=false;
			//System.out.println("PoolRunner Client side Disconnected");
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Server is not Running");

			System.out.println("I am inside Connector IOException");
	        System.out.println(e);
	        connected=false;
	    }
		catch(ClassNotFoundException e){
			JOptionPane.showMessageDialog(null,"ClassNotFoundException");

			System.out.println("I am inside Connector EOFException"); 
			connected=false;
			//System.out.println("PoolRunner Client side Disconnected");
		}
		finally{
			return(connected);
		}
	
	}
	public LinkedList getStrategyNames(){
		
		return this.list;
	} 
	public ObjectInputStream getObjectInputStream(){
		return this.inStream;
		
	}
	public ObjectOutputStream getObjectOutputStream(){
		
		return this.outStream;
	}
	public void disconnect(){
		try{
		this.inStream.close();
		this.outStream.close();
		this.socket.close();
		GCRunner gcRunner=new GCRunner();
		gcRunner.setPriority(Thread.MAX_PRIORITY);
		gcRunner.start();
		
	}catch(IOException e){
		JOptionPane.showMessageDialog(null,"Problem in closing streams or socket");

	}
		catch(Exception e){
			JOptionPane.showMessageDialog(null,"Problem in closing streams or socket");
			e.printStackTrace();

		}	
	}
}
