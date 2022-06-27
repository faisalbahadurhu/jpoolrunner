package com.jpoolrunner.clientside;

public class JobPercentiles {
private String jobName;// ie 1 2 3 100 200 300 etc
String nintyPercentileRT;
String fiftyPercentileRT;
String nintyPercentileWt;
String fiftyPercentileWt;
String strategyName;// it is used in comparative analysis only
	public String getStrategyName() {
	return strategyName;
}
public void setStrategyName(String strategyName) {
	this.strategyName = strategyName;
}
	public JobPercentiles() {
		// TODO Auto-generated constructor stub
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getNintyPercentileRT() {
		return nintyPercentileRT;
	}
	public void setNintyPercentileRT(String nintyPercentileRT) {
		this.nintyPercentileRT = nintyPercentileRT;
	}
	public String getFiftyPercentileRT() {
		return fiftyPercentileRT;
	}
	public void setFiftyPercentileRT(String fiftyPercentileRT) {
		this.fiftyPercentileRT = fiftyPercentileRT;
	}
	public String getNintyPercentileWt() {
		return nintyPercentileWt;
	}
	public void setNintyPercentileWt(String nintyPercentileWt) {
		this.nintyPercentileWt = nintyPercentileWt;
	}
	public String getFiftyPercentileWt() {
		return fiftyPercentileWt;
	}
	public void setFiftyPercentileWt(String fiftyPercentileWt) {
		this.fiftyPercentileWt = fiftyPercentileWt;
	}
	public JobPercentiles(String jobName,String fiftyPercentileRT, String nintyPercentileRT, String fiftyPercentileWt, String nintyPercentileWt) {
		super();
		this.jobName = jobName;
		this.nintyPercentileRT = nintyPercentileRT;
		this.fiftyPercentileRT = fiftyPercentileRT;
		this.nintyPercentileWt = nintyPercentileWt;
		this.fiftyPercentileWt = fiftyPercentileWt;
	}
	public JobPercentiles(String strategyName,String jobName,String fiftyPercentileRT, String nintyPercentileRT, String fiftyPercentileWt, String nintyPercentileWt) {
		super();
		this.strategyName=strategyName;
		this.jobName = jobName;
		this.nintyPercentileRT = nintyPercentileRT;
		this.fiftyPercentileRT = fiftyPercentileRT;
		this.nintyPercentileWt = nintyPercentileWt;
		this.fiftyPercentileWt = fiftyPercentileWt;
	}
	

}
