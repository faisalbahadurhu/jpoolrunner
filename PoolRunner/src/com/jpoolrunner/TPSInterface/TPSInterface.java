package com.jpoolrunner.TPSInterface;
import java.util.concurrent.BlockingQueue;
import javax.swing.JTextArea;
public abstract class TPSInterface {
protected String name;
private NonblockingCounter nbCounter=new NonblockingCounter();
private BlockingQueue< Runnable > responseQueue;// = new LinkedBlockingQueue< Runnable >();//it will collect the responses ie IoBoundTask to be deque by a timer to send back to the poolrunner client side

public TPSInterface(){}
public void setQueue(BlockingQueue< Runnable > responseQueue){this.responseQueue=responseQueue;}
public abstract void submitRequest(Runnable request);//ThreadPoolServer System will call it to submit requests ie when a job arrives it is submitted to strategy by this method so u can say that it is entering in ur system...ie in ur strategy
//public abstract void execute(Runnable request);//use this method to execute ur requests.....submitrequest() can call this method to execute the request
public abstract void initAllConsumers();
public abstract void shutDownPool();//to stop and kill all threads in the pool
public abstract int getWorkerThreadPoolSize();//It must return the size of dynamic thread pool which is tuned by u...This method is periodically called by ThreadPoolServerSystem's timer which is already embedded and running in the background..u dnt need to worry about it...just return ur Worker pool size from this method.
//public abstract int getRequestQueueSize();//this method should return the size of the Queue in which requests are waiting for their turn of execution...This method is periodically called by ThreadPoolServerSystem's timer which is already embedded and running in the background..u dnt need to worry about it...just return ur request queue size from this method.
public String getName(){
	//String name=foo.getName();
	//name=name.replaceAll("\\s+","");//remove spaces
	name=name.replaceAll("[^A-Za-z0-9]", "");// remove all special characters including spaces

	
	return this.name;
	}//Returns name of pooling strategy eg FBOS
public void setName(String name){this.name=name;}//set name of strategy e.g. FBOS
public int getCounter(){return this.nbCounter.getValue();}
public  void sendResponse(Runnable obj){//this method must be called by each worker at the end of completing its task e.g when ur Worker thread run the task e.g job.run() then after this statement ur thread will just call this method ie job.run();sendResponse(job); job is a runable object ie IoBound object or CpuBound object etc
	int n=nbCounter.increment();
	try {
		this.responseQueue.put(obj);//a timer thread will then dequeue it and send this job as a response back to the client through ObjectOutputStream
	//	System.out.println("QUEUE SIZE="+this.responseQueue.size());

		 	    } catch (InterruptedException e) {
	        // won't happen.
	    }
	}
}

