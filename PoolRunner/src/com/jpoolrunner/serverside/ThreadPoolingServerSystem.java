package com.jpoolrunner.serverside;
import java.security.CodeSource;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jpoolrunner.warmup.WarmUp;

public class ThreadPoolingServerSystem {
	//	
	TPS_Service tpsLoader;
	private ConnectionListener listener=null;
//	
	private JTextArea textArea;
	//	ThreadPoolManager staticPool;
	JTextField textFieldRequest;
	JTextField textFieldResponse;
	JProgressBar progressBar;

	private BlockingQueue<Runnable> responseQueue;//it will collect the responses ie IoBoundTask to be deque by a timer to send back to the poolrunner client side

	public ThreadPoolingServerSystem(JTextArea textArea, JTextField textFieldRequest, JTextField textFieldResponse, JProgressBar progressBar) {
		// TODO Auto-generated constructor stub
		this.textFieldRequest=textFieldRequest;
		this.textFieldResponse=textFieldResponse;
		this.progressBar=progressBar;
		
		this.textArea=textArea;
		tpsLoader=new TPS_Service(this.textArea);
		responseQueue=new LinkedBlockingQueue<Runnable>();

	}
	/*public void stopListening(){
		//listener.closeConnection();
		listener.interrupt();
		listener=null;
		
	}*/

	public void startListenerNow(){
		try{
			Vector names=tpsLoader.getTPSNames();

			textArea.append("\nServer has following Thread Pool Systems");
			Iterator it=names.iterator();
			while(it.hasNext()){

				String name=(String)it.next();
				textArea.append("\n"+name);
			}
		//	textArea.append("\nEnd of names");
		}catch(Exception e){textArea.append("\n Exception in ThreadPoolingServerSystem method startListenerNow()");
		e.printStackTrace();
		}
		//listener=new ConnectionListener(responseQueue,strategies,textArea);//it will also initialize the Reciever in its constructor
		
	//	textArea.append("\nServer is doing warm-up i.e. performing JIT optimizations");
	//      WarmUp cpuBoundJobWarmup=new WarmUp(textArea);//warm-up code that allows the JIT optimization for CpuBoundTask.
	//	    cpuBoundJobWarmup.start();
		//textArea.append("\nnnnnnnnnnnnnnnnnnn");
		listener=new ConnectionListener(responseQueue,tpsLoader,textArea,textFieldRequest,textFieldResponse,progressBar);//it will also initialize the Reciever in its constructor 

		listener.start();//start thread to listen
	}
}

/*public static String getJarContainingFolder(Class aclass) throws Exception {
CodeSource codeSource = aclass.getProtectionDomain().getCodeSource();

File jarFile;

if (codeSource.getLocation() != null) {
  jarFile = new File(codeSource.getLocation().toURI());
}
else {
  String path = aclass.getResource(aclass.getSimpleName() + ".class").getPath();
  String jarFilePath = path.substring(path.indexOf(":") + 1, path.indexOf("!"));
  jarFilePath = URLDecoder.decode(jarFilePath, "UTF-8");
  jarFile = new File(jarFilePath);
}
return jarFile.getParentFile().getAbsolutePath();
}*/
