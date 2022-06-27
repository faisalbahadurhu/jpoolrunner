package com.jpoolrunner.clientside;

public class PoolSizeGetter {
int poolSize=0;
	public synchronized int getPoolSize() {
	return poolSize;
}
public synchronized void setPoolSize(int poolSize) {
	this.poolSize = poolSize;
}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
