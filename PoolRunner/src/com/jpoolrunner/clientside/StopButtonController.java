package com.jpoolrunner.clientside;

import javax.swing.JButton;

public class StopButtonController extends Thread {
	JButton stopButton;
	
	public StopButtonController(JButton stopButton) {
		// TODO Auto-generated constructor stub
		this.stopButton=stopButton;
	}

	public void run(){
		try{
			Thread.sleep(3000);
			stopButton.setEnabled(true);
		}catch(InterruptedException e){}
	}
}
