package com.jpoolrunner.job;
import javax.swing.*;

import com.jpoolrunner.job.IoBoundTask;
public class abc extends Thread {
IoBoundTask job;
	public abc() {
		// TODO Auto-generated con
		job=new IoBoundTask(3000);
	}
	//  panel.setBounds(0, 271, 281, 368);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
new abc().start();
	}
@Override
public void run(){
	UIManager.LookAndFeelInfo[] lookAndFeelInfos = UIManager.getInstalledLookAndFeels();
    for (int i = 0; i < lookAndFeelInfos.length; i++) {
        UIManager.LookAndFeelInfo lookAndFeelInfo = lookAndFeelInfos[i];

        //
        // Get the name of the look and feel
        //
        String name = lookAndFeelInfo.getName();
        System.out.println("name = " + name);
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("processors="+processors);
        //
        // Get the implementation class for the look and feel
        //
        String className = lookAndFeelInfo.getClassName();
        System.out.println("className = " + className);
    }
	new Thread(job).start();
	System.out.println("I am abc");
	

	
}
}
