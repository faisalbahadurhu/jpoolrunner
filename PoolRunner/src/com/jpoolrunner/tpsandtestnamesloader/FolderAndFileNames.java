package com.jpoolrunner.tpsandtestnamesloader;


import java.util.Vector;

public class FolderAndFileNames {
	String folderName;
	Vector<String> tests;
	public FolderAndFileNames(String folderName){
		this.folderName=folderName;
		tests=new Vector<String>();
	}
	public String getTpsName() {
		return folderName;
	}
	public void setTpsName(String folderName) {
		this.folderName = folderName;
	}
	public Vector<String> getTests() {
		return tests;
	}

	public void setTest(String testName){
		this.tests.add(testName);

	}

}
