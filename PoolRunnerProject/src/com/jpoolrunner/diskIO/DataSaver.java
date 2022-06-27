package com.jpoolrunner.diskIO;

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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;

import com.jpoolrunner.serverside.ThreadPoolingServerSystem;

public class DataSaver {
	public DataSaver(){}
	public boolean createDataFolderIfNotExists(){//it creates the folder c:\t\data
		boolean check=true;

		try{
			File pathZ = Paths.get(DataSaver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			String fullPath=uri+"data";// it will return   file:/C:/data/folderName

			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work

			if(destDir.exists() && destDir.isDirectory()){
				check=true;	
			}
			else { 

				if (destDir.mkdir()) {
					System.out.println("directories created successfully");
					check= true;
				} else {
					System.out.println("directories not created");
					check= false;
				}

			}
		}
		catch(Exception e){}
		finally{
			return check;
		}
	}
	public boolean createTPSFolderIfNotExists(String TPSName){//it ceates the folder named by strategy name or TPS
		boolean check=true;
		try{
			File pathZ = Paths.get(DataSaver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			String fullPath=uri+"data/"+TPSName;// it will return   file:/C:/data/folderName

			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work
			if(destDir.exists() && destDir.isDirectory()){
				check=true;	
			}
			else{
				if (destDir.mkdir()) {
					System.out.println(TPSName+" Folder created successfully");
					check= true;
				} else {
					System.out.println(TPSName+" Folder not created");
					check= false;
				}
			}
		}
		catch(Exception e){}
		finally{
			return check;
		}
	}
	public int countNumberOfTestsInTPS(String TPSName){//it counts number of tests in a particular strategy/TPS so that new test can be given a unique name
		int length=0;
		try{
			File pathZ = Paths.get(DataSaver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			String fullPath=uri+"data/"+TPSName;// it will return   file:/C:/data/folderName

			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work
			String[] names = destDir.list();
			/*	for(String name : names)  
				System.out.println(name+" ");*/
			return length=names.length;
		}
		catch(Exception e){}
		finally{
			return length;
		}
	}

	public boolean saveDoubleSeries(XYSeries series, String TPSName,String testNumber,String filename){//it saves the series of Throughput panel responsepanel waitpanel,poolsize panel
		LinkedList<Double> doubleList=getDataFromSeries(series);
		boolean check=true;
		try{
			File pathZ = Paths.get(DataSaver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			String fullPath=uri+"data/"+TPSName+"/"+testNumber;// it will return   file:/C:/data/folderName
			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work
			if(!destDir.exists()){
				destDir.mkdir();
			}

			URL pathURL2=new URL(fullPath+"/"+filename);
			File dataFile = new File(pathURL2.toURI());
			if( dataFile.createNewFile()){
				FileChannel fileChannel = new RandomAccessFile(dataFile, "rw").getChannel();

				ByteBuffer buf = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 8 * doubleList.size());
				for (double i : doubleList) {
					buf.putDouble(i);
				}
				fileChannel.close();
				check=true;
			}
			else{
				System.out.println(filename+" craetion failed");
				check=false;

			}
		}
		catch(Exception e){}
		finally{
			return check;
		}


	}
	public boolean saveIntSeries(XYSeries series, String TPSName,String testNumber,String filename){//it saves frequencies ie resquestfrequecypanel series
		LinkedList<Integer> intList=getIntDataFromSeries(series);
		boolean check=true;
		try{
			File pathZ = Paths.get(DataSaver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			String fullPath=uri+"data/"+TPSName+"/"+testNumber;// it will return   file:/C:/data/folderName
			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work
			if(!destDir.exists()){
				destDir.mkdir();
			}

			URL pathURL2=new URL(fullPath+"/"+filename);
			File dataFile = new File(pathURL2.toURI());
			if( dataFile.createNewFile()){
				FileChannel fileChannel = new RandomAccessFile(dataFile, "rw").getChannel();

				ByteBuffer buf = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 4 * intList.size());
				for (int i : intList) {
					buf.putInt(i);
				}
				fileChannel.close();
				check=true;
			}
			else{
				System.out.println(filename+" craetion failed");
				check=false;

			}
		}
		catch(Exception e){}
		finally{
			return check;
		}
	}
	//public boolean saveResultStatistics( String TPSName,String testNumber, double totalThroughput,double avgThroughput,double fiftyPercentileRT,double nintyPercentileRT,double nintyFivePercentileRT,double avgRT,double avgWaitTime,double avgPoolSize){
	public boolean saveResultStatistics( String TPSName,String testNumber, double totalThroughput,double avgThroughput,double avgPoolSize){

		boolean check=true;
		try{
			File pathZ = Paths.get(DataSaver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			String fullPath=uri+"data/"+TPSName+"/"+testNumber;// it will return   file:/C:/data/folderName
			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work
			if(!destDir.exists()){
				destDir.mkdir();
			}

			URL pathURL2=new URL(fullPath+"/ResultStatistics");
			File dataFile = new File(pathURL2.toURI());
			if( dataFile.createNewFile()){
				FileChannel fileChannel = new RandomAccessFile(dataFile, "rw").getChannel();

				ByteBuffer buf = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 8 * 8);//8*8 i.e. 8bytes is size of double and there are 8 doubles to store in the file
				buf.putDouble(totalThroughput);
				buf.putDouble(avgThroughput);
			/*	buf.putDouble(fiftyPercentileRT);
				buf.putDouble(nintyPercentileRT);
				buf.putDouble(nintyFivePercentileRT);
				buf.putDouble(avgRT);
				buf.putDouble(avgWaitTime);*/
				buf.putDouble(avgPoolSize);


				fileChannel.close();
				check=true;
			}
			else{
				System.out.println("ResultStatistics file craetion failed");
				check=false;

			}
		}
		catch(Exception e){}
		finally{
			return check;
		}

	}
	public boolean saveResponseOrWaitPanelGraphResults(String TPSName,String testNumber,Vector<Vector> percentilesEtc,char character){//  This is a vector of vectors and each subvector contains Strings i.e name of graph e.g. 100 and percentiles 50th 90th 95th etc these are the data that Responsepanel and waitpanel's tables display in statistics tabs also char character represents whether it is from responsepanel or waitpanel character would be inserted at the start of file name eg R100 means response graph of 100ms file
		boolean check=true;
		try{
			File pathZ = Paths.get(DataSaver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			String fullPath=uri+"data/"+TPSName+"/"+testNumber;// it will return   file:/C:/data/folderName
			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work
			if(!destDir.exists()){
				destDir.mkdir();
			}
			Iterator it=percentilesEtc.iterator();
			while(it.hasNext()){//iterate through the vector to obtain a vector that contains string data about ....read above comments on function call
				Vector<String> graphPercentilesEtc=(Vector)it.next();////  graphPercentilesEtc means 100(or 200 etc Workload for bin) and 50th 90th 95th percentiles which have been calculated in tables and percentilesEtc contain name of graph at its 1st index i.e. Responsepanel or waitpanel's graph name e.g. 100 200 or 1 5 etc
				URL pathURL2=new URL(fullPath+"/"+character+graphPercentilesEtc.elementAt(0));//first element contain name of graph e.g. 100 200 300 400 2000 1 5 10
				File dataFile = new File(pathURL2.toURI());
				if( dataFile.createNewFile()){
					FileWriter writer = new FileWriter(dataFile);
					BufferedWriter bufferedWriter = new BufferedWriter(writer, 8192);
					for (String record: graphPercentilesEtc) {
						writer.write(record);
						writer.write(" ");//put space so that we can split it easily
					}
					writer.flush();
					writer.close();
					bufferedWriter.close();
					check=true;
				}
				else{
					System.out.println(" file craetion failed in saveResponseOrWaitPanelGraphResults() method in Datasaver");
					check=false;

				}
			}
		}
		catch(Exception e){}
		finally{
			return check;
		}

	}
	public boolean saveWorkLoadForBins(String TPSName,String testNumber,Vector temp){// i.e. 100 200 300 400 2000 1 5 10 etc static and dynamic workload that user will choose 4m dialog box i.e. types of jobs 
		boolean check=true;
		Vector<String> load=new Vector(3);
		Iterator itr=temp.iterator();
		while(itr.hasNext()){
			String s="";
			s+=(int)itr.next();
			load.add(s);
		}
		try{
			File pathZ = Paths.get(DataSaver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			String fullPath=uri+"data/"+TPSName+"/"+testNumber;// it will return   file:/C:/data/folderName
			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work
			if(!destDir.exists()){
				destDir.mkdir();
			}

			URL pathURL2=new URL(fullPath+"/WorkLoadForBins");
			File dataFile = new File(pathURL2.toURI());
			if( dataFile.createNewFile()){
				FileWriter writer = new FileWriter(dataFile);
				BufferedWriter bufferedWriter = new BufferedWriter(writer, 8192);
				for (String record: load) {
					writer.write(record);
					writer.write(" ");//put space so that we can split it easily
				}
				writer.flush();
				writer.close();
				bufferedWriter.close();
				check=true;
			}
			else{
				System.out.println("WorkLoadForBins file craetion failed");
				check=false;

			}
		}
		catch(Exception e){}
		finally{
			return check;
		}

	}
	public boolean saveRealLoad(String TPSName,String testNumber,Vector<String> load){// it saves the workload ie I200 or c15 ie all the jobs sent to the server wheter I/o or CPU bound with their corresponding intensities ie 200ms or 400ms or 15ms eg
		boolean check=true;
		try{
			File pathZ = Paths.get(DataSaver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			String fullPath=uri+"data/"+TPSName+"/"+testNumber;// it will return   file:/C:/data/folderName
			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work
			if(!destDir.exists()){
				destDir.mkdir();
			}

			URL pathURL2=new URL(fullPath+"/RealLoad");
			File dataFile = new File(pathURL2.toURI());
			if( dataFile.createNewFile()){
				FileWriter writer = new FileWriter(dataFile);
				BufferedWriter bufferedWriter = new BufferedWriter(writer, 8192);
				for (String record: load) {
					writer.write(record);
					writer.write(" ");//put space so that we can split it easily
				}
				writer.flush();
				writer.close();
				bufferedWriter.close();
				check=true;
			}
			else{
				System.out.println("ResultStatistics file craetion failed");
				check=false;

			}
		}
		catch(Exception e){}
		finally{
			return check;
		}

	}
	public boolean saveWorkloadPattern(String TPSName,String testNumber,String workloadPattern ){//name of the distribution ie poisson uniform guassian heavy tailed etc
		boolean check=true;
		try{
			File pathZ = Paths.get(DataSaver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			String fullPath=uri+"data/"+TPSName+"/"+testNumber;// it will return   file:/C:/data/folderName
			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work
			if(!destDir.exists()){
				destDir.mkdir();
			}

			URL pathURL2=new URL(fullPath+"/WorkloadPattern");
			File dataFile = new File(pathURL2.toURI());
			if( dataFile.createNewFile()){
				FileWriter writer = new FileWriter(dataFile);
				writer.write(workloadPattern);
				writer.flush();
				writer.close();
				check=true;
			}
			else{
				System.out.println("ResultStatistics file craetion failed");
				check=false;

			}
		}
		catch(Exception e){}
		finally{
			return check;
		}

	}

	public boolean savePreviousTestName(String TPSName,String testNumber,String previousTestName ){//name of the distribution ie poisson uniform guassian heavy tailed etc
		boolean check=true;
		try{
			File pathZ = Paths.get(DataSaver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			String fullPath=uri+"data/"+TPSName+"/"+testNumber;// it will return   file:/C:/data/folderName
			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work
			if(!destDir.exists()){
				destDir.mkdir();
			}

			URL pathURL2=new URL(fullPath+"/PreviousTestName");
			File dataFile = new File(pathURL2.toURI());
			if( dataFile.createNewFile()){
				FileWriter writer = new FileWriter(dataFile);
				writer.write(previousTestName);
				writer.flush();
				writer.close();
				check=true;
			}
			else{
				System.out.println("ResultStatistics file craetion failed");
				check=false;

			}
		}
		catch(Exception e){}
		finally{
			return check;
		}

	}





	public boolean staticAndDynamicLoad(String TPSName,String testNumber,int oneKb,int tenKb,int hundredKb,int thousandKb,int twoThousandKb,int tenMilliSecond,int fiftyMilliSecond,int hundredMilliSecond,int staticLoadPercentage,int dynamicLoadPercentage){
		boolean check=true;
		try{
			File pathZ = Paths.get(DataSaver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			String fullPath=uri+"data/"+TPSName+"/"+testNumber;// it will return   file:/C:/data/folderName
			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work
			if(!destDir.exists()){
				destDir.mkdir();
			}

			URL pathURL2=new URL(fullPath+"/staticAndDynamicLoad");
			File dataFile = new File(pathURL2.toURI());
			if( dataFile.createNewFile()){
				FileChannel fileChannel = new RandomAccessFile(dataFile, "rw").getChannel();

				ByteBuffer buf = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 4 * 10);//4*10 i.e. 4bytes is size of int and there are 10 int to store in the file
				buf.putInt(oneKb);//static load
				buf.putInt(tenKb);//static load
				buf.putInt(hundredKb);//static load
				buf.putInt(thousandKb);//static load
				buf.putInt(twoThousandKb);//static load
				buf.putInt(tenMilliSecond);//dynamic load
				buf.putInt(fiftyMilliSecond);//dynamic load
				buf.putInt(hundredMilliSecond);//dynamic load
				buf.putInt(staticLoadPercentage);//static load percentage 
				buf.putInt(dynamicLoadPercentage);//dynamic load percentage 

				fileChannel.close();
				check=true;
			}
			else{
				System.out.println("ResultStatistics file craetion failed");
				check=false;

			}
		}
		catch(Exception e){}
		finally{
			return check;
		}
	}
	private LinkedList<Double> getDataFromSeries(XYSeries series)// the series of all panels responepanel waitpanel throughputpanel poolsizepanel is sent here to extract their double values 
	{
		LinkedList<Double> doubleList = new LinkedList<Double>();

		for(Object o: series.getItems()) {
			XYDataItem xydi = (XYDataItem)o;
			doubleList.add(xydi.getY().doubleValue());
		}
		return doubleList;
	}
	private LinkedList<Integer> getIntDataFromSeries(XYSeries series)// the series of frequency panel is sent here to extract their double values 

	{
		LinkedList<Integer> doubleList = new LinkedList<Integer>();

		for(Object o: series.getItems()) {
			XYDataItem xydi = (XYDataItem)o;
			doubleList.add(xydi.getY().intValue());
		}
		return doubleList;
	}
	public void displayFolderNames(){//not used but it is used to display names of strategies tested in the data folder
		try{
			File pathZ = Paths.get(DataSaver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			String fullPath=uri+"data";// it will return   file:/C:/data

			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work	
			String[] names = destDir.list();
			for(String name : names)  
				System.out.println(name+" ");


		}catch(Exception e){}
	}
	public boolean checkIfTestReportNameAlreadyExist(String tpsName,String testReportName){//not used but it is used to display names of strategies tested in the data folder
		boolean check=false;

		try{
			File pathZ = Paths.get(DataSaver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			//String fullPath=uri+"data/"+TPSName+"/"+testNumber;// it will return   file:/C:/data/folderName

			String fullPath=uri+"data/"+tpsName;// it will return   file:/C:/data

			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work	
			String[] names = destDir.list();
			for(String name : names) { 
				if(name.equalsIgnoreCase(testReportName)) {check=true; break;}
				
			}
			
			

		}catch(Exception e){}
		return check;
	}
	
}
