package com.jpoolrunner.serverside;
//shared b/w PoolSizeSetter and ResponseSender
public class Toggle {

	/**
	 * @param args
	 */
boolean toggle=true;

	public synchronized boolean isToggle() {
		return toggle;
	}

	public synchronized void setToggle(boolean toggle) {
		this.toggle = toggle;
	}

}
