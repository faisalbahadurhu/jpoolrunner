package com.jpoolrunner.warmuptest;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class CpuBoundJob  implements Runnable {
	 private long serviceTime;
	 private long execTime;
	 
public CpuBoundJob(long serviceTime) {
		   this.serviceTime=serviceTime;
		  }
public CpuBoundJob() { }
	@Override
public void run() {
		long t1=System.nanoTime();
		long x = 0;
		 for (int i = 0; i <1500000*10 ; i++)//1500000*1(about 10ms)...1500000*5(about 50ms)...1500000*10(about 100ms)
		 x ^= ThreadLocalRandom.current().nextLong();
	      if (x == 0)
	    	  
	        throw new IllegalStateException();
		long t2=System.nanoTime();
		execTime=t2-t1;

		
	}
public long getTime(){
	return TimeUnit.NANOSECONDS.toMillis(execTime);
}


}
