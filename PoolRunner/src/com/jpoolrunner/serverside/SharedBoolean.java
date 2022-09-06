package com.jpoolrunner.serverside;

public class SharedBoolean {
	boolean stop;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
public SharedBoolean(boolean stop){this.stop=stop;}
public boolean getStopValue(){return this.stop;}
public void setStopValue(boolean stop){ this.stop=stop;}

}
