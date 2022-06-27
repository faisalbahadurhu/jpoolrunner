package com.jpoolrunner.clientside;
import java.util.concurrent.*;
import javax.swing.*;

import org.jfree.data.xy.XYSeries;

import com.jpoolrunner.job.Task;
public class Consumer extends JPanel implements MessageListener {

	  BlockingQueue< Runnable > queue = new LinkedBlockingQueue< Runnable >();
	 	  // This implements the MessageListener interface:
	  @Override
	  public void newMessage(Runnable message ) {
	    try {
	      queue.put( message );
	      
	    } catch (InterruptedException e) {
	        // won't happen.
	    }
	  }

	    // Execute in another thread:       
	    protected void handleMessages() throws InterruptedException {
	        while ( true ) {
	        	Runnable newMessage = queue.take();

	            // handle the new message.
	        }
	    }
	  public BlockingQueue< Runnable > getQueue(){
		 return this.queue;
	  }
	    
}