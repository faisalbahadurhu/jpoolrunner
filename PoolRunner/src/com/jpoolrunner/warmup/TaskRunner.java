package com.jpoolrunner.warmup;

import com.jpoolrunner.job.CpuBoundTask;

public class TaskRunner extends Thread {
	CpuBoundTask job=new CpuBoundTask(1);
	public void run(){

	job.run();	
	}
	//public double getTime(){
		//return job.getTime();
	//}
}
