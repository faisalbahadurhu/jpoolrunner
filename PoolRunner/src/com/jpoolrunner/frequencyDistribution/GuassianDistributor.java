package com.jpoolrunner.frequencyDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;


public class GuassianDistributor implements FrequencyDistributorInterface {
double mean , std;//std ie standard deviation is square root of variance
long seed;
NormalDistribution guassian;//NormalDistribution(double mean, double sd)
public GuassianDistributor(double mean, double std) {
	
	this.mean = mean;
	this.std = std;
	seed = System.currentTimeMillis();
	guassian=new NormalDistribution(this.mean,this.std);
	guassian.reseedRandomGenerator(seed);
		
    
}
	@Override
public synchronized int getRandomNumber() {
		// TODO Auto-generated method stub
		return((int) getGuassian());
	}
	
	public double getGuassian(){
		
		return(guassian.sample());
	}

@Override
public synchronized void increaseLoad(int n) {
	// TODO Auto-generated method stub
	
	this.guassian=null;
	this.guassian=new NormalDistribution(this.mean*n,this.std);
	guassian.reseedRandomGenerator(seed);
}
}
