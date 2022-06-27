package com.jpoolrunner.serverside;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

// it activates after every 5 sec and set value of toogle to true whch is made false by ResponseSender after embedding poolsize in a job object so not all jobs contain poolsize but after 5 sec the current poolsize is inserted in a job object and send at client side Recieverx will take it and set pool size in a shared variable which is taken by PoolSizePanel
public class PoolSizeSetter extends Timer implements ActionListener{

Toggle toggler;
TestToggler testMe;
int c=0;
	public PoolSizeSetter(int interval,Toggle toggler, TestToggler testMe) {
		super(interval,null);
		this.toggler=toggler;
		this.testMe=testMe;
		addActionListener(this);
		this.setInitialDelay(5000);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//if(toggler.isToggle()==true) toggler.setToggle(false);//=false;
		//else 
		toggler.setToggle(true);
		c++;
		this.testMe.setString(c+"");
	}
	

}
