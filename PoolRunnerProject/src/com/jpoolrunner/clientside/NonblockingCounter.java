package com.jpoolrunner.clientside;
import java.util.concurrent.atomic.*;
public class NonblockingCounter {
    private AtomicInteger value=new AtomicInteger(0);

    public int getValue() {
        return value.get();
    }

    public int increment() {
        int v;
        do {
            v = value.get();
        }
         while (!value.compareAndSet(v, v + 1));
        return v + 1;
    }
    public void resetCounter(){
    	this.value.set(0);
    }
}