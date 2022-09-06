package com.jpoolrunner.serverside;

public class ResponseSenderCounter {
	int count;
	public ResponseSenderCounter(){
		this.count=0;
	} 
	public void increment(){
		this.count++;
	}
	public int getCounter(){
		return this.count;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
