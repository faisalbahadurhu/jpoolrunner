package com.jpoolrunner.warmuptest;

public class TaskRunner extends Thread {
	CpuBoundJob job;
	public void run(){

	job.run();	
	}
public TaskRunner(int n){
	job=new CpuBoundJob(n);
}
	
public double getTime(){
return job.getTime();
}
}
