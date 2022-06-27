package com.jpoolrunner.job;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class CpuBoundTask extends Task implements Runnable {
	 public CpuBoundTask(long delay) {
		//delay specifies the milliseconds value
		 
		    super(delay);
		  }
	@Override
	public void run() {
		setDequeueNanoTime(System.nanoTime());

		long x = 0;
		 for (int i = 0; i <1500000*delay ; i++)////1500000*1(about 10ms)...1500000*5(about 50ms)...1500000*10(about 100ms)
		 x ^= ThreadLocalRandom.current().nextLong();
	      if (x == 0)
	    	  
	        throw new IllegalStateException();
	      setSendTime(System.nanoTime());
		//	execTime=t2-t1;
			//TimeUnit.NANOSECONDS.toMillis(execTime);
		/* BigInteger factValue = BigInteger.ONE;
	     
		    for ( int i = 2; i <= 800; i++){
		      factValue = factValue.multiply(BigInteger.valueOf(i));
		    }*/
		// setDequeueNanoTime(System.nanoTime());
		 //  setDequeueNanoTime(System.nanoTime());

		/// long timeToWait = this.delay;
		/// long timeToWaitNano=timeToWait*1000000L;
		// long startTime = System.currentTimeMillis();
		///	 long startTime = System.nanoTime();

		// while(startTime + timeToWait > System.currentTimeMillis());
		///	    while ((System.nanoTime() - startTime) < timeToWaitNano) {}
			/*
			  long timeToWait = this.delay;
		 long timeToWaitNano=timeToWait*1000000L;
		// long startTime = System.currentTimeMillis();
			 long startTime = System.nanoTime();

		// while(startTime + timeToWait > System.currentTimeMillis());
			    while ((System.nanoTime() - startTime) < timeToWaitNano) {}
			        
			 */
			//    setSendTime(System.nanoTime());

			    
			//    setSendTime(System.nanoTime());
			    

		// TODO Auto-generated method stub
		/*long sleepTime = this.delay*1000000L; // convert to nanoseconds
	    long startTime = System.nanoTime();
	    while ((System.nanoTime() - startTime) < sleepTime) {}*/
		
	}
	/*
	 * private  void spin(int milliseconds) {
	    long sleepTime = milliseconds*1000000L; // convert to nanoseconds
	    long startTime = System.nanoTime();
	    while ((System.nanoTime() - startTime) < sleepTime) {}
	}
	 */
	 public CpuBoundTask() { }

}

