package com.jpoolrunner.frequencyDistribution;

import org.apache.commons.math3.distribution.ParetoDistribution;

public class ParetoDistributor implements FrequencyDistributorInterface {
	ParetoDistribution pareto;	//ParetoDistribution(double scale, double shape)
	double scale;//like a mean
	double shape;
	long seed;
public ParetoDistributor(double scale, double shape){
	this.scale=scale;
	this.shape=shape;
	seed = System.currentTimeMillis();
	pareto=new ParetoDistribution(this.scale,this.shape);
	pareto.reseedRandomGenerator(seed);
	
}
	@Override
	public synchronized int getRandomNumber() {
		// TODO Auto-generated method stub
		return getParetoRandom();
	}

	private int getParetoRandom() {
		// TODO Auto-generated method stub
		return ((int)pareto.sample());
	}
	@Override
	public synchronized void increaseLoad(int n) {
		// TODO Auto-generated method stub
		pareto=null;
		pareto=new ParetoDistribution(this.scale*n,this.shape);
		pareto.reseedRandomGenerator(seed);
		
	}

}
