package com.jpoolrunner.serverside;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ServiceLoader;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.jpoolrunner.TPSInterface.TPSInterface;


public class TPS_Service {
	JTextArea jta;
	public TPS_Service(JTextArea jta){this.jta=jta;}

	public TPSInterface serviceLoader(String name){
		TPSInterface TPS=null;//=new Vector<TPSInterface>();
		try{ 
			URLClassLoader urlloaders=getURLClassLoader();
			for (TPSInterface foo : ServiceLoader.load(TPSInterface.class,urlloaders)) {
				if(name.equalsIgnoreCase(foo.getName())) {TPS=foo;break;}
			}

		}catch(Exception e){
			jta.append("\nException in TPSLoader method getTPS()");
			jta.append("\n"+e);
			e.printStackTrace();
		}
		return TPS;


	}
	private URLClassLoader getURLClassLoader(){
		URLClassLoader urlloaders=null;

		try{ 
			URL urls[];
			String path=ThreadPoolingServerSystem.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();// it will return /c:/t/Project.jar   if this calss resides in project.jar ie ur application jar
			File pathZ = Paths.get(ThreadPoolingServerSystem.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File b=pathZ.getParentFile();//it will return C:\t
			URI v=b.toURI();// it will return   file:/C:/t/
			String fullPath=v+"strategies";// it will return   file:/C:/t/strategies

			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work
			String files1;
			File folder = new File(pathURL.toURI());//
			File[] listOfFiles = folder.listFiles(); 
			//  names=new String[listOfFiles.length-1] ;// -1 is discarded for  PoolRunner.jar this jar file contains TPSInterface interface plus IoBoundTask and ParentJob
			urls=new URL[listOfFiles.length-1];//-1 is discarded for  PoolRunner.jar this jar file contains TPSInterface interface plus IoBoundTask and ParentJob
			int counter=0;
			for (int i = 0; i < listOfFiles.length; i++) // listOfFiles.length-1 so that PoolRunner.jar can be discarded and only strategies can be added 
			{

				if (listOfFiles[i].isFile()) 
				{
					files1 = listOfFiles[i].getName();
					if ((files1.endsWith(".jar") || files1.endsWith(".JAR"))&&(!files1.equals("PoolRunnerLib.jar")))// && (!files1.equals("jcommon-1.0.21.jar")) && (!files1.equals("jfreechart-1.0.17.jar")) && (!files1.equals("RUN.bat")))//because all these 4 files reside inside folder that also contains a jar file of project ie PoolRunner or project.jar and then contains user added strategies
					{

						urls[counter++]=new URL(fullPath+"/"+files1);
					}
				}
			}

			urlloaders = new URLClassLoader(urls);
		}catch(Exception e){
			jta.append("\ni m inside TPSLoader method getURLClassLoader()");
			jta.append("\n"+e);
			e.printStackTrace();
		}
		return urlloaders;
	}
	public Vector<String> getTPSNames()throws ClassNotFoundException, MalformedURLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException{
		Vector<String> names=new Vector<String>();
		try{ 
			URLClassLoader urlloaders=getURLClassLoader();
			for (TPSInterface foo : ServiceLoader.load(TPSInterface.class,urlloaders)) {
				String name=foo.getName();
			//	name=name.replaceAll("\\s+","");//no need of it as i have used it in TpsInterface getName() method//removes all white spaces and non-visible characters (e.g., tab, \n)

				names.add(name);
			}

		}catch(Exception e){
			jta.append("\ni m inside TPSLoader method getTPSNames()");
			jta.append("\n"+e);
			e.printStackTrace();
		}
		return names;

	}	
}


