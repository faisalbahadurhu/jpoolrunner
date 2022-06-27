package com.jpoolrunner.frequencyDistribution;

import org.apache.commons.math3.distribution.PoissonDistribution;


public class PoissonDistributor implements FrequencyDistributorInterface {
double lambda=0.0;
long seed;
PoissonDistribution poisson;

public PoissonDistributor(double lambda) {
	
	seed = System.currentTimeMillis();
	this.lambda = lambda;
	poisson= new PoissonDistribution(lambda);
	poisson.reseedRandomGenerator(seed);
	
	    
	}
public  int getPoisson() {
	
		return((int) poisson.sample());
		  
	}
	

	@Override
public synchronized int getRandomNumber() {
		// TODO Auto-generated method stub
		return getPoisson();
	}
	@Override
	public synchronized void increaseLoad(int n) {
		// TODO Auto-generated method stub
		poisson=null;
		poisson= new PoissonDistribution(lambda*n);
		poisson.reseedRandomGenerator(seed);
	}
	
}
