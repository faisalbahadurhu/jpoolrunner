package com.jpoolrunner.warmup;
// not in use
import java.awt.Cursor;
import java.io.ObjectInputStream;

import com.jpoolrunner.clientside.MainFrame;

public class Launcher extends Thread {
	ObjectInputStream is;
	MainFrame mainframe;
	public Launcher(ObjectInputStream is,MainFrame mainframe){this.is=is;this.mainframe=mainframe;}
	public void run(){
		try{		
		String optimiztionDone=(String)is.readObject();
		}catch(Exception ex){}
		mainframe.setCursor(Cursor.getDefaultCursor());
		
	}

}
