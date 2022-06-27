package com.jpoolrunner.frequencyDistribution;

public class ManualDistributor implements FrequencyDistributorInterface {
	private int start,end,decrease;
	private int counter=0;
	private boolean downWard=false;
	int arranged=1;
	//int[] array = {10,3,7,9,12,8,5,9,23,16,28,6,17,24,12,12,30,36,24,23,25};
	int[] array = {100,100,90,80,70,85,70,80,95,60,60,65,40,45,55,30,16,10,17,24,12,12,30,36,24,23,25};

	boolean arrayTurn=true;
	int arrayCounter=-1;
public ManualDistributor(int start,int end,int decrease){
	this.start=start;
	this.end=end;
	this.decrease=decrease;
	
}
	@Override
	public int getRandomNumber() {
		// TODO Auto-generated method stub
	if(arrayTurn){
		arrayCounter++;
		if(arrayCounter==array.length){arrayTurn=false;arrayCounter=-1; return array[0];}
		else return array[arrayCounter];
	}
	else{
		if(downWard==false){//ie end not reached
			counter++;
			arranged=start*counter;
			if(arranged==end) {
				downWard=true;
				counter=0;
				}
		}
		else{
			arranged-=decrease;
			if(arranged<=start){
				downWard=false;
				arranged=1;
				arrayTurn=true;
			}
		}
	}	
		return arranged;
	}
	@Override
	public void increaseLoad(int n) {
		// TODO Auto-generated method stub
		
	}

}
