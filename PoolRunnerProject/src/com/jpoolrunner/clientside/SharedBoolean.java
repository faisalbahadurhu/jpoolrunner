package com.jpoolrunner.clientside;

public class SharedBoolean {
	boolean stop;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
public SharedBoolean(boolean stop){this.stop=stop;}
public synchronized boolean getStopValue(){return this.stop;}
public synchronized void setStopValue(boolean stop){ this.stop=stop;}

}
