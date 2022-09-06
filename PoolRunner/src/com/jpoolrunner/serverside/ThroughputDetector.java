package com.jpoolrunner.serverside;
import com.jpoolrunner.TPSInterface.NonblockingCounter;

public class ThroughputDetector extends Thread {
	NonblockingCounter nbCounter;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
public ThroughputDetector(NonblockingCounter nbCounter){
	this.nbCounter=nbCounter;
}
@Override
public void run(){
	
	
}
}
