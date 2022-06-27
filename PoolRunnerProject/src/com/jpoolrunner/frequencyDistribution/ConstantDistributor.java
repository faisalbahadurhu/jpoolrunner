package com.jpoolrunner.frequencyDistribution;

public class ConstantDistributor implements FrequencyDistributorInterface {
int constant=0;

	public ConstantDistributor(int constant) {
	this.constant = constant;
}

	@Override
	public int getRandomNumber() {
		// TODO Auto-generated method stub
		return constant;
	}

	@Override
	public void increaseLoad(int n) {
		// TODO Auto-generated method stub
		this.constant*=n;
	}
 
}
