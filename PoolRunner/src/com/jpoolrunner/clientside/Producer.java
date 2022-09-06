package com.jpoolrunner.clientside;
import java.util.Collection;
import java.util.ArrayList;
public class Producer {
	  Collection<MessageListener> listeners = new ArrayList<MessageListener>();


	  // Allow interested parties to register for new messages
	  public void addListener( MessageListener listener ) {
	    this.listeners.add( listener );
	  }

	  public void removeListener( Object listener ) {
	    this.listeners.remove( listener );
	  }

	  protected void produceMessages(Runnable msg) {
		//  Runnable msg = new byte[10];

	    // Create message and put into msg

	    // Tell all registered listeners about the new message:
	    for ( MessageListener l : this.listeners ) {
	      l.newMessage( msg );
	    }

	  }
	}