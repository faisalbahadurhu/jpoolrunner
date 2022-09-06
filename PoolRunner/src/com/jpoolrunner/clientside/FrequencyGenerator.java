/** This class 
 * 
 */
package com.jpoolrunner.clientside;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JLabel;
/**
 * @author Faisal Bahadur
 *This class is initialised in RequestSender
 */
public class FrequencyGenerator {
	int incrementBy1=2;//coz first time frequencyholder run 10 times and when first time this class generate() is called it should multiply by 2nd time ie by 2 and so on
	int delay;
	double previousAvgThroughput=0.0;
	JLabel reqSentvalue;
	Frequencyholder freqHolder;
	Vector throughputs;// used to store throughput per seconds.
	boolean saturation=false;// ie throughput is incresaing in every pass 
public FrequencyGenerator(Vector throughputs, Frequencyholder freqHolder, JLabel reqSentvalue) {
	
	
	this.throughputs=throughputs;
	this.freqHolder=freqHolder;
	this.reqSentvalue= reqSentvalue;
	
}
	public void generate() {
		// TODO Auto-generated method stub
	//	queue.
		Vector copyVector;
		synchronized(throughputs){
			copyVector=(Vector)throughputs.clone();
			throughputs.removeAllElements();
		   }
		///////////
		Iterator theData=copyVector.iterator();
	      double sum=0;
     	  while(theData.hasNext()){
     	 double outPut=(double) theData.next();
     //	System.out.println("outPut="+outPut);
     	 sum+=outPut;
     	  }
     	  double averageThroughput=sum/copyVector.size();
     	  if((saturation==false) && (averageThroughput>(previousAvgThroughput+3))){// //+3 so that v ignore measurements .5 to 4 but think about it n it should be 10 instead of 5
     		  this.freqHolder.setFrequency(10*(incrementBy1++));
     		  this.previousAvgThroughput=averageThroughput;
     		  
     	  }
     	  else if(this.freqHolder.getFrequency()>=20) {//ie saturatin point started and frequency holder should not less then 10
     		 saturation=true;
     		 this.freqHolder.setFrequency(this.freqHolder.getFrequency()-10);
     	  }
     	  else {//ie frequency again set to 10 now again start increasing frequency
     		 saturation=false;
     		previousAvgThroughput=0.0;
     		incrementBy1=2;
     	  }
		////////////
     	/*  System.out.println("sum="+sum);
     	 System.out.println("averageThroughput="+averageThroughput);
     	System.out.println("throughputs size="+throughputs.size());*/
		}
	}



/*public class FrequencyGenerator extends Consumer{
	int delay;
	Frequencyholder fh;
	JLabel reqSentvalue;
	FrequencyTimer timer;
	Vector throughputs;// used to store throughput per seconds.
public FrequencyGenerator(int delay, Frequencyholder fh, JLabel reqSentvalue,Vector throughputs) {
	super();
	this.delay = delay;
	this.fh = fh;
	this.reqSentvalue = reqSentvalue;
	
	this.throughputs=throughputs;
 timer=new FrequencyTimer(delay);
}
class FrequencyTimer extends Timer implements ActionListener{

	public FrequencyTimer(int delay) {
		
		super(delay, null);
		addActionListener(this);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	//	queue.
		Vector copyVector;
		synchronized(throughputs){
			copyVector=(Vector)throughputs.clone();
			throughputs.removeAllElements();
		   }
		///////////
		Iterator theData=copyVector.iterator();
	      double sum=0;
     	  while(theData.hasNext()){
     	 double outPut=(double) theData.next();
     	 sum+=outPut;
     	  }
     	  double averageThroughput=sum/copyVector.size();
		////////////
     	  System.out.println("sum="+sum);
     	 System.out.println("averageThroughput="+averageThroughput);
		}
	}
public void startMe(){this.timer.start();}
public void restartMe(){this.timer.restart();}
public void stopMe(){this.timer.stop();}	


}*/
