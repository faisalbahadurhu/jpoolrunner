package com.jpoolrunner.diskIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Vector;


public class DataLoader {

	private String testNumberAndTpsFolderPath;

	public DataLoader(String testNumberAndTpsFolderPath) {
		// TODO Auto-generated constructor stub
		this.testNumberAndTpsFolderPath=testNumberAndTpsFolderPath;
	}
	public DataLoader(){}
	
	public void deleteFolderwithFiles(){
		try{

			File pathZ = Paths.get(DataLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			String fullPath=uri+"data/"+testNumberAndTpsFolderPath;//e.g. data/oraclethreadpool/rep1
			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work
			String[] names = destDir.list();//names of all files in rep1
			for(String name : names) { 
				
				String fullPath2=uri+"data/"+testNumberAndTpsFolderPath+"/"+name;
				URL pathURL2=new URL(fullPath2);
				File destDir2 = new File(pathURL2.toURI());// must do it otherwise not work
				destDir2.delete();//delete text file
				
			}
			
			destDir.delete();//delete folder e.g. rep1 which is empty now
			
			int lastIndex=fullPath.lastIndexOf('/');
			String sub=fullPath.substring(0, lastIndex);//it will convert file:/c/t2/data/oraclethreadpool/rep1 into file:/c/t2/data/oraclethreadpool 
			if(this.countNumberOfTests(sub)==0){// if there is no more tests in TPS
				URL pathURL3=new URL(sub);
				File destDir3 = new File(pathURL3.toURI());
				destDir3.delete();// if there is no more tests in TPS then delete the TPS folder also
			}
			
			String fullPath4=uri+"data";
			if(this.countNumberOfTests(fullPath4)==0){// if there is no more tests in data folder
			URL pathURL4=new URL(fullPath4);
			File destDir4 = new File(pathURL4.toURI());
			destDir4.delete();// if there is no more tests in data folder then delete the data folder also

			}
			
			
		System.out.println(sub);
		}catch(Exception ex){
			System.out.println("Error in deleting TPS");
		}
		
	}
	
	public  Vector<Integer> loadIntegerData(String fileName){
		Vector<Integer> requestFrequencies=new Vector<Integer>();

		try{

			File pathZ = Paths.get(DataLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			String fullPath=uri+"data/"+testNumberAndTpsFolderPath+"/"+fileName;// String fullPath=uri+"data/"+getStrategyName()+"/Test1/FrequencySeries
			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work
			FileInputStream  f= new FileInputStream( destDir );
			FileChannel ch = f.getChannel( );
			MappedByteBuffer mb = ch.map( FileChannel.MapMode.READ_ONLY,0L, ch.size( ) );
			//DoubleBuffer dBuf = mb.asDoubleBuffer();
			IntBuffer intBuf = mb.asIntBuffer();

			int pos = intBuf.position();
			//Set position to zero
			intBuf.position(0);
			int counter=0;
			while(intBuf.hasRemaining()){
				requestFrequencies.add(intBuf.get());
			}
			f.close();
			ch.close();
		//	intBuf.clear();
		//	mb.clear();
			
		}
		catch(Exception ex){
			System.out.println("Exception in loadRequestFrequencies() of DataLoader");
			ex.printStackTrace();
			}
		finally{
			return requestFrequencies;
		}
		
	}
	
	public  Vector<Double> loadDoubleDatas(String fileName){//use to load all panels data which are all doubles
		Vector<Double> data=new Vector<Double>();

		try{

			File pathZ = Paths.get(DataLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			String fullPath=uri+"data/"+testNumberAndTpsFolderPath+"/"+fileName;// String fullPath=uri+"data/"+getStrategyName()+"/Test1/FrequencySeries
			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work
			FileInputStream  f= new FileInputStream( destDir );
			FileChannel ch = f.getChannel( );
			MappedByteBuffer mb = ch.map( FileChannel.MapMode.READ_ONLY,0L, ch.size( ) );
			DoubleBuffer dBuf = mb.asDoubleBuffer();

			int pos = dBuf.position();
			dBuf.position(0);
			int counter=0;
			while(dBuf.hasRemaining()){
				data.add(dBuf.get());
			}
			f.close();
			ch.close();
			
		}
		catch(Exception ex){
			System.out.println("Exception in loadRequestFrequencies() of DataLoader");
			ex.printStackTrace();
			}
		finally{
			return data;
		}
		
	}
	public String loadStringData(String fileName){//Realload file that contains data in form of tokens I200 C15 etc
		String s="";
		try
		{
			File pathZ = Paths.get(DataLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			String fullPath=uri+"data/"+testNumberAndTpsFolderPath+"/"+fileName;//String fullPath=uri+"data/"+getStrategyName()+"/Test1/realLoad";// String fullPath=uri+"data/"+getStrategyName()+"/Test1/FrequencySeries
			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work
			FileReader fR=new FileReader(destDir);
			BufferedReader br = new BufferedReader(fR);
			String sCurrentLine = null;

			while ((sCurrentLine = br.readLine()) != null)
				s+=sCurrentLine;
				sCurrentLine = null;
			br.close();
			fR.close();
			
			
			

		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		finally{
			//String[] splitStr = s.split("\\s+");
			return s;
			}
	}
	public String loadPreviousTestName(String path){//Realload file that contains data in form of tokens I200 C15 etc
		String s="";
		try
		{
			File pathZ = Paths.get(DataLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			String fullPath=uri+"data/"+path+"/"+"PreviousTestName";//String fullPath=uri+"data/"+getStrategyName()+"/Test1/realLoad";// String fullPath=uri+"data/"+getStrategyName()+"/Test1/FrequencySeries
			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work
			FileReader fR=new FileReader(destDir);
			BufferedReader br = new BufferedReader(fR);
			String sCurrentLine = null;

			while ((sCurrentLine = br.readLine()) != null)
				s+=sCurrentLine;
				sCurrentLine = null;
			br.close();
			fR.close();
			
			
			

		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		finally{
			//String[] splitStr = s.split("\\s+");
			return s;
			}
	}
	public boolean tspFolderExistsOrNot(String TPSName){//it checks whether a TPS folder exists or not: to create JmenuItem
		boolean check=true;
		try{
			File pathZ = Paths.get(DataLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			String fullPath=uri+"data/"+TPSName;// it will return   file:/C:/data/folderName

			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work
			if(destDir.exists() && destDir.isDirectory()){
				check=true;	
			}
			else{
					check= false;
				
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
		catch(Exception e){
			System.out.println("No data exists in "+TPSName);
			//e.printStackTrace();
			
		}
		finally{
			return length;
		}
	}
	public int countNumberOfTests(String fullPath){//it counts number of tests in a particular strategy/TPS so that new test can be given a unique name
		int length=0;
		try{
		/*	File pathZ = Paths.get(DataSaver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			String fullPath=uri+"data/"+TPSName;// it will return   file:/C:/data/folderName*/

			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work
			String[] names = destDir.list();
			/*	for(String name : names)  
				System.out.println(name+" ");*/
			return length=names.length;
		}
		catch(Exception e){
			System.out.println("No data exists in "+fullPath);
			//e.printStackTrace();
			
		}
		finally{
			return length;
		}
	}
	
}
