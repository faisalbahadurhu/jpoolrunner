package com.jpoolrunner.clientside;
import java.util.concurrent.atomic.*;

public class NonBlockingFrequencyHolder {
	
	    private AtomicInteger frequency=new AtomicInteger(1);
	   // static int n=0;

	    public int getValue() {
	        return frequency.get();
	    
	    }

	    public int increment() {
	        int v;
	        do {
	            v = frequency.get();
	        }
	         while (!frequency.compareAndSet(v, v + 1));
	        return v + 1;
	    }
	}