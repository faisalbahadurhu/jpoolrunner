package com.jpoolrunner.clientside;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ThroughputDetector extends Timer implements ActionListener {
	NonblockingCounter throughput;

	public ThroughputDetector(int interval,NonblockingCounter throughput) {
		super(interval,null);
		this.throughput= throughput;
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	@Override
	public void actionPerformed(ActionEvent e){
		this.throughput.getValue();
		
	}

}
