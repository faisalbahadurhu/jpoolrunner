package com.jpoolrunner.comparativeanalysis;

import java.util.Iterator;
import java.util.Vector;

import com.jpoolrunner.clientside.JobPercentiles;

public class JobData {
	String JobName;
	String JobNameAsCategoryKey;
	Vector strategyNameAndpercentiles=new Vector(2);
	public JobData(String name){JobName=name;
	switch (Integer.parseInt(name)){
	case 1:JobNameAsCategoryKey="Low";	break;
	case 2:JobNameAsCategoryKey="High";	break;
	case 3:JobNameAsCategoryKey="V.High";	break;
	case 100:JobNameAsCategoryKey="1kb";	break;
	case 200:JobNameAsCategoryKey="10kb";	break;
	case 300:JobNameAsCategoryKey="100kb";	break;
	case 400:JobNameAsCategoryKey="1000kb";	break;
	case 500:JobNameAsCategoryKey="1000kb";	break;
	case 600:JobNameAsCategoryKey="1000kb";	break;
	case 700:JobNameAsCategoryKey="1000kb";	break;
	case 800:JobNameAsCategoryKey="1000kb";	break;
	case 900:JobNameAsCategoryKey="1000kb";	break;
	case 2000:JobNameAsCategoryKey="2000kb";	break;
	case 1000:JobNameAsCategoryKey="1200kb";	break;
	
	
 	};
	
	}//
	
	public String getJobNameAsCategoryKey() {
		return JobNameAsCategoryKey;
	}

	public void setData(String strategy,String fiftyPerRT,String nintyPerRT,String fiftyPerWT,String nintyPerWT){
		
		
		StrategyPercentiles sp=new StrategyPercentiles( strategy, fiftyPerRT, nintyPerRT, fiftyPerWT, nintyPerWT);
		strategyNameAndpercentiles.add(sp);
		
	}
	public String getJobName() {
		return JobName;
	}
	public Vector getStrategyNameAndpercentiles() {
		return strategyNameAndpercentiles;
	}
	
	public String getPercentile(String strategyName,String per){
		
		String percentile="";
		
		Iterator iter=strategyNameAndpercentiles.iterator();
		while(iter.hasNext()){
			StrategyPercentiles sp=(StrategyPercentiles)iter.next();
			if(strategyName.equals(sp.getStrategy())){
			if(per.equals("fifty")){	percentile=sp.getFiftyPerRT();break;}
			else if(per.equals("ninty")) {percentile=sp.getNintyPerRT();break;}
			else if(per.equals("fiftyWt")) {percentile=sp.getFiftyPerWT();break;}
			else if(per.equals("nintyWt")) {percentile=sp.getNintyPerWT();break;}
			
			}
	}
		
	return percentile;
	}
	
	public String getAllData(){
		String s="";
		s+="JobName="+JobName+"\t";
		
		Iterator iter=strategyNameAndpercentiles.iterator();
		while(iter.hasNext()){
			StrategyPercentiles sp=(StrategyPercentiles) iter.next();
			s+=sp.getAll();
					
		}
		
		return s;
		
	}
	
	

}
