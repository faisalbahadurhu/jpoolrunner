package com.jpoolrunner.warmup;
// This class will warmup the code(I did for CpuBoundTask only) so that JIT optimizes the code at its best level
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import javax.swing.JTextArea;
public class WarmUp extends Thread {
int warmUpIterations=100;
private JTextArea textArea;
public WarmUp(JTextArea textArea){
	this.textArea=textArea;
}
//public void warupCode(){}
	
public void run(){
	for(int i=1;i<=warmUpIterations;i++){
		TaskRunner tr=new TaskRunner();
		tr.start();
		try{
		tr.join();
		}catch(InterruptedException ie){}
	}
	  textArea.append("\nJIT optimizations have been performed");

}
//main will not execute
/*public static void main(String[] args) {
	
	Vector buffer=new Vector();
	System.out.println("Warmup strted");
	for(int i=1;i<=100;i++){
		TaskRunner t=new TaskRunner();
		buffer.add(t);
		t.start();
	}
	try{
	Iterator it=buffer.iterator();
	while(it.hasNext()){
		TaskRunner t=(TaskRunner)it.next();
		t.join();
	}
	
	}catch(Exception e){}
	System.out.println("Warmup ended");
	
	buffer.removeAllElements();
	//////////////////////////
	int r=4;

	long t1=System.nanoTime();
	for(int i=1;i<=r;i++){
		TaskRunner t=new TaskRunner();
		buffer.add(t);
		t.start();
	}
	//long t3=System.nanoTime();

		try{
			Iterator it=buffer.iterator();
			while(it.hasNext()){
				TaskRunner t=(TaskRunner)it.next();
				t.join();
			}
			
			}catch(Exception e){}
	double sum=0;
		try{
			Iterator it=buffer.iterator();
			while(it.hasNext()){
				TaskRunner t=(TaskRunner)it.next();
				sum+=t.getTime();
			}}catch(Exception e){}
	long t2=System.nanoTime();	

	System.out.println("Original Time(ms)="+((double)(t2-t1)/1000000L));
	System.out.println("Avg task Time(ms)="+sum/r);

	}*/
}
