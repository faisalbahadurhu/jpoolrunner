package com.jpoolrunner.job;

import java.util.concurrent.TimeUnit;

public class IoBoundTask extends Task implements Runnable {

 
  public IoBoundTask(long delay) {
  //  this.delay=delay;
    super(delay);
  }
  @Override
public void run() {
	  
 //  setDequeueNanoTime(TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis()));
	  
  setDequeueNanoTime(System.nanoTime());

   try{
	   //Faisal
	  // System.out.println("Faisal i am running");
    
Thread.currentThread().sleep(this.delay);


        }catch(InterruptedException e){
        	System.out.println("Faisal I m called from IOBoundJob Class");
        	//throw new InterruptedException();
        }

 //  setSendTime(TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis()));
   setSendTime(System.nanoTime());
    
     }
  public IoBoundTask() {
	 
	  }
  

}