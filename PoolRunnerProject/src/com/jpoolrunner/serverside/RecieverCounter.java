package com.jpoolrunner.serverside;

public class RecieverCounter {
int count;
public RecieverCounter(){
	this.count=0;
} 
public synchronized void increment(){
	this.count++;
}
public synchronized int getCounter(){
	return this.count;
}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
