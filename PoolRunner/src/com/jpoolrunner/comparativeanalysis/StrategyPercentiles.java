package com.jpoolrunner.comparativeanalysis;

public class StrategyPercentiles{
	String strategy, fiftyPerRT, nintyPerRT, fiftyPerWT, nintyPerWT;
	public StrategyPercentiles(String strategy,String fiftyPerRT,String nintyPerRT,String fiftyPerWT,String nintyPerWT){
		this.strategy=strategy;
		this.fiftyPerRT=fiftyPerRT;
		this.nintyPerRT=nintyPerRT;
		this.fiftyPerWT=fiftyPerWT;
		this.nintyPerWT=nintyPerWT;
			
	}
	public String getStrategy() {
		return strategy;
	}
	public String getFiftyPerRT() {
		return fiftyPerRT;
	}
	public String getNintyPerRT() {
		return nintyPerRT;
	}
	public String getFiftyPerWT() {
		return fiftyPerWT;
	}
	public String getNintyPerWT() {
		return nintyPerWT;
	}
	
	public String getAll(){
		String s="";
		s+="strategy-Name="+strategy+" fiftyPerRT= "+fiftyPerRT+" nintyPerRT= "+nintyPerRT+"\n";
		return s;
		
	}
}