package com.jpoolrunner.clientside;

public class Frequencyholder {
	private int frequency=1;
	public Frequencyholder(){}
	public synchronized void setFrequency(int f){this.frequency=f;}
	public synchronized int getFrequency(){return this.frequency;}
	public void restFrequency(){this.frequency=1;}

}
