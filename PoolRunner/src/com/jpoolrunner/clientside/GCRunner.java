package com.jpoolrunner.clientside;

public class GCRunner extends Thread {
public GCRunner(){}
public void run(){
	try{
	Thread.sleep(100);
	System.gc();
	Thread.sleep(500);
	System.gc();
	}catch(InterruptedException e){}
	
}

}
