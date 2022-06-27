package com.jpoolrunner.tpsandtestnamesloader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Vector;

import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;

public class TpsAndTestsNameLoader {
	public TpsAndTestsNameLoader(){}
	public FolderAndFileNames[] getTpsAndTestNames(){
		FolderAndFileNames[] folders=null;
		try{
			File pathZ = Paths.get(TpsAndTestsNameLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			String fullPath=uri+"data";// it will return   file:/C:/data

			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work	
			if(destDir.exists()){
				String[] names = destDir.list();
				folders=new FolderAndFileNames[names.length];
				for(int i=0;i<names.length;i++){
					folders[i]=new FolderAndFileNames(names[i]);
				}

				for(int i=0;i<folders.length;i++){
					String newPath=	fullPath+"/"+folders[i].getTpsName();
					URL pathURL2=new URL(newPath);
					File destDir2 = new File(pathURL2.toURI());// must do it otherwise not work	
					if(destDir2.exists()){						
						String[] testNames = destDir2.list();
						for(int j=0;j<testNames.length;j++){ 
							folders[i].setTest(testNames[j]);
						}
					}
				}
			}
			
		}catch(Exception e){}
		finally{return folders;}
		
	}
	
	

}
