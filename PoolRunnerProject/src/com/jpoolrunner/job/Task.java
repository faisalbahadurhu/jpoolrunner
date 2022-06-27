package com.jpoolrunner.job;

import java.util.concurrent.TimeUnit;

public class Task implements java.io.Serializable{

 protected long delay;//its I/O intensity level or CPU-Intensiveness level ie how much this job will sleep or keeps processor busy
 protected long responseTime;//the time from initiating to completion of a job ie wait time+execution time+management overhead etc
 protected long sendTime=0;
 protected long reciveTime;
 protected long totalWait;
 protected long reciveNanoTime;
 protected long enQueueNanoTime;
 protected long dequeueNanoTime;
 protected long dequeueTime;

 String leavingTime;
 private int poolsize=0;
  

public long getDequeueTime() {
	return dequeueTime;
}
public void setDequeueTime(long dequeueTime) {
	this.dequeueTime = dequeueTime;
}
public long getDequeueNanoTime() {
	return dequeueNanoTime;
}
public void setDequeueNanoTime(long dequeueNanoTime) {
	this.dequeueNanoTime = dequeueNanoTime;
}
protected long enQueueTime;
 int id;
 String entryTime;
//private long a;
//private long b;
 
  public Task(long delay ) {this.delay=delay;}
  public Task( ) {}
 
  public long getEnQueueTime(){return this.enQueueTime;}

  public void setNanoEnQueueTime(long n){this.enQueueNanoTime=n;}
  public long getNanoEnQueueTime(){return this.enQueueNanoTime;}
  public void setSendTime(long n){
	  this.sendTime=n;
	  }
  public long getSendTime(){return this.sendTime;}
  public void setNanoReciveTime(long n){this.reciveNanoTime=n;}
  public long getNanoReciveTime(){return this.reciveNanoTime;}
  public double getTotalTATWait(){
  //	long waitTime1=getDequeueNanoTime()-this.getNanoEnQueueTime();
  	long waitTime1=getDequeueNanoTime()-this.getNanoReciveTime();
  	//return((double)waitTime1/1000000);//1 millisec =10,00000 nanosec so devide by 1000000 to get milliseconds
  	return((double)TimeUnit.NANOSECONDS.toMillis(waitTime1));
  //	return((double)waitTime1);
  	
  }
 public double getAcceptanceLatency(){
	//	long acceptancelatency=getNanoEnQueueTime()-getNanoReciveTime();//.getSendTime()-jobx.getReciveTime();
	//	return ((double)acceptancelatency/1000000);//1 millisec =10,00000 nanosec so devide by 1000000 to get milliseconds
	 return 0.0;
 }
 public double getResponseTimeOfStaticReq(){
		long serviceTime=getSendTime()-this.getDequeueNanoTime();	 
      double responseTime=(((double)TimeUnit.NANOSECONDS.toMillis(serviceTime))+getTotalTATWait());
      
	//	long serviceTime=getSendTime()-this.getNanoReciveTime();
	//	double responseTime=((double)TimeUnit.NANOSECONDS.toMillis(serviceTime));
      //   return(responseTime);

      
      return (getDelay()+getTotalTATWait());
		 
 }
 public double getResponseTime(){
		long serviceTime=getSendTime()-this.getDequeueNanoTime();	 
		double responseTime=(((double)TimeUnit.NANOSECONDS.toMillis(serviceTime))+getTotalTATWait());
   
	//	long serviceTime=getSendTime()-this.getNanoReciveTime();
	//	double responseTime=((double)TimeUnit.NANOSECONDS.toMillis(serviceTime));
   //   return(responseTime);

   
   return (getDelay()+getTotalTATWait());
		 
}
 public double getResponseTimeOfDynamicReq(){
		long serviceTime=getSendTime()-this.getDequeueNanoTime();	 
   double responseTime=(((double)TimeUnit.NANOSECONDS.toMillis(serviceTime))+getTotalTATWait());
   
	//	long serviceTime=getSendTime()-this.getNanoReciveTime();
	//	double responseTime=((double)TimeUnit.NANOSECONDS.toMillis(serviceTime));
   //   return(responseTime);

   return(responseTime);
		 
}
 
 public void setId(int id){this.id=id;}
 public int getId(){return this.id;}
 public void setPoolsize(int poolSize){this.poolsize=poolSize;}
 public int getPoolsize(){return this.poolsize;}
 public long getDelay(){return this.delay;}
 public void setDelay(long delay){this.delay=delay;}
}