package com.jpoolrunner.serverside;

public class TestToggler {
	String s="";
public synchronized void setString(String s){this.s+=" "+s;}
public synchronized String getString(){
	return s;
}

}
