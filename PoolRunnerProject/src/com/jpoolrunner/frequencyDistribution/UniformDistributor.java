package com.jpoolrunner.frequencyDistribution;

import java.util.Random;

public class UniformDistributor implements FrequencyDistributorInterface {
int n;
static Random random;


	public UniformDistributor(int n) {
	this.n = n;
	long seed = System.currentTimeMillis();
    random= new Random(seed);
}

	@Override
	public int getRandomNumber() {
		// TODO Auto-generated method stub
		return uniform(n);
	}
	
    public static int uniform(int n) {
      
        return (random.nextInt(n));
    }
  
	@Override
	public void increaseLoad(int n) {
		// TODO Auto-generated method stub
		this.n*=n;
	}
}
