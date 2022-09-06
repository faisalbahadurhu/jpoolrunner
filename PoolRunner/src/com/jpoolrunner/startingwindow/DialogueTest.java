package com.jpoolrunner.startingwindow;

import java.awt.event.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.StringTokenizer;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.io.*;
import java.util.Enumeration;
import java.util.jar.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.jpoolrunner.TPSInterface.TPSInterface;
import com.jpoolrunner.clientside.PoolRunnerApplication;
import com.jpoolrunner.serverside.ServerGUI;
import com.jpoolrunner.serverside.ThreadPoolingServerSystem;

import java.net.*;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;
public class DialogueTest extends JDialog implements ActionListener {
	//ServerGUI serverGUI;
	JButton btnNewButton_1;
	public DialogueTest() {
		super();
		//setUndecorated( true );
		
		setLookNFeel();
		getRootPane().setBorder( BorderFactory.createLineBorder(Color.BLUE) );
		this.setModal(false);
		this.setAlwaysOnTop(true);
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setPreferredSize(new Dimension(500, 38));
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblNewLabel = new JLabel("PoolRunner                                                                                   ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		panel.add(lblNewLabel);
		
		JButton btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				dispose();
				//System.exit(0);
			}
		});
		btnExit.setVerticalAlignment(SwingConstants.BOTTOM);
		btnExit.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(btnExit);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
		tabbedPane.setPreferredSize(new Dimension(500, 300));
		panel_1.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel loadTestingPanel = new JPanel();
		tabbedPane.addTab("Load Testing", null, loadTestingPanel, null);
		loadTestingPanel.setLayout(null);
		
		JButton btnNewButton_2 = new JButton("Plan Load Testing");
		btnNewButton_2.addActionListener(this);
		btnNewButton_2.setBounds(48, 39, 164, 23);
		loadTestingPanel.add(btnNewButton_2);
		
		JPanel serverpanel = new JPanel();
		tabbedPane.addTab("Server", null, serverpanel, null);
		serverpanel.setLayout(new FlowLayout(FlowLayout.LEFT, 17, 5));
		
		JPanel serverInnerPanel = new JPanel();
		serverInnerPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	//	panel_2.setBackground(Color.BLACK);
	//	Dimension d=panel_2.getParent().getSize();
		//System.out.println(d.width+"  "+d.height);
		serverInnerPanel.setPreferredSize(new Dimension(200, 265));
		serverpanel.add(serverInnerPanel);
		serverInnerPanel.setLayout(null);
		
		 btnNewButton_1 = new JButton("Load New TPS");
		
		JButton btnNewButton = new JButton("Run Server");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				JButton b=(JButton )e.getSource();
				b.setFont(new Font("Tahoma", Font.BOLD, 12));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				JButton b=(JButton )e.getSource();
				b.setFont(new Font("Tahoma", Font.PLAIN, 13));
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton source = (JButton) e.getSource();
                source.setEnabled(false);
                btnNewButton_1.setEnabled(false);
               // serverGUI=new ServerGUI();
                new ServerGUI();
			}
		});
		btnNewButton.setBounds(10, 43, 156, 23);
		serverInnerPanel.add(btnNewButton);
		
		
		btnNewButton_1.setToolTipText("Extension Framework");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				JButton b=(JButton )e.getSource();
				b.setFont(new Font("Tahoma", Font.BOLD, 12));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				JButton b=(JButton )e.getSource();
				b.setFont(new Font("Tahoma", Font.PLAIN, 13));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				try{
					DialogueTest.this.setAlwaysOnTop(false);
					jFIleChooserCall();
					DialogueTest.this.setAlwaysOnTop(true);
					}catch(Exception ew){}
			}
		});
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					DialogueTest.this.setAlwaysOnTop(false);
					jFIleChooserCall();
					DialogueTest.this.setAlwaysOnTop(true);
					}catch(Exception ew){}

			}
		});
		btnNewButton_1.setBounds(10, 138, 156, 23);
		serverInnerPanel.add(btnNewButton_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(250, 230));
		serverpanel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JTextArea textArea = new JTextArea(1,1);
		panel_2.add(textArea, BorderLayout.CENTER);
				
		JPanel whatsNewPanel = new JPanel();
		tabbedPane.addTab("What's new", null, whatsNewPanel, null);
		// TODO Auto-generated constructor stub
		//this.setSize(1000, 1000);
		
		this.pack();
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - getWidth()/2, (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - getHeight()/2);
		this.setVisible(true);
		
	}
	private void jFIleChooserCall() throws Exception{
		JFileChooser fileChooser = new JFileChooser();
		//fileChooser.getPreferredSize();
		fileChooser.setPreferredSize(new Dimension(500, 300));
	     fileChooser.setDialogTitle("Choose a file");
	     fileChooser.setFileFilter(new FileFilter() {   
	    	 
	    	 
	         @Override
	         public String getDescription() {
	              return "jar files only (*.jar)";
	          }
	   
	         @Override
	         public boolean accept(File f) {
	              if (f.isDirectory()) {
	                  return true;
	              } else {
	                  return f.getName().toLowerCase().endsWith(".jar");
	              }
	          }
	      });
	     fileChooser.setVisible(true);
	     int ret = fileChooser.showDialog(null, "Open file");

	     if (ret == JFileChooser.APPROVE_OPTION) {
	       File sourceFileOrDir = fileChooser.getSelectedFile();
	       if (!(sourceFileOrDir.getName().endsWith(".jar"))) {
	    	    JOptionPane.showMessageDialog(null, "ur selection is Not a jar file");
	    	   // System.exit(0);
	       }
	       else{
      // File sourceFileOrDir = new File(args[0]);
	    	   String path=DialogueTest.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();// it will return /c:/t/Project.jar   if this calss resides in project.jar ie ur application jar
	    	   path=path.substring(0, path.lastIndexOf("/") + 1);// this will convert /c:/t/Project.jar into /c:/t/
	    	   path+="strategies";// and now the path is /c:/t/strategies 
	    	   path="file://"+path;// and now the path is file:///c:/t/strategies   but plz keep in mind that unix linux dont follow 3 slashes after file: they follow 2 so path="file:/"+path; is for linux unix. you can check the os with System.getProperty("os.name")
	    	//String path2="file:///c:/t/strategies/";
	    	   URL pathURL=new URL(path);
	    	   File destDirectory = new File(pathURL.toURI());// must do it otherwise not work
	/*    	   
    URL url=(ClassLoader.getSystemResource("Loadedstrategies"));
	URI uri=url.toURI();// String path=uri.
    File destDir = new File(uri);//Loadedstrategies
      */
       if (sourceFileOrDir.isFile()) {//.jar file to copy
    	   try{  copyJarFile(new JarFile(sourceFileOrDir), destDirectory);
    	   }catch(Exception e){
    		   JOptionPane.showMessageDialog(null, "Exception in copying");
    		   System.out.println(e);
    		   System.exit(0);
    	   }
    	 //  JOptionPane.showMessageDialog(null, "Strategy has been loaded successfully");
    	   
    	 //  System.out.println(path2+sourceFileOrDir.getName());
    	   
    	   /////////
    	   URLClassLoader   urlloaders = new URLClassLoader(new URL[]{new URL(path+"/"+sourceFileOrDir.getName())});
    	   TPSInterface strategies;
    	   boolean check=false;
    	   for (TPSInterface foo : ServiceLoader.load(TPSInterface.class,urlloaders)) {
    		   strategies=foo;  
    		//   JOptionPane.showMessageDialog(null,strategies.getName()+" is Your strategy");
        	   check=true;
        	   }
    	   if(check==false)
    		   {
    		   JOptionPane.showMessageDialog(null, "There is no implementation of TPSInterface in your JAR file:Your Strategy cant be loaded");
    		   urlloaders.close();
    		   URL pathX=new URL(path+"/"+sourceFileOrDir.getName());
    		   Files.delete(Paths.get(pathX.toURI()));
    			 		  
    		   }
    	   else if (check==true){
    		   JOptionPane.showMessageDialog(null, "Strategy has been loaded successfully");
    		 //  serverGUI.restartThreadPoolingServerSystem();
    	   }

    	   ////////
       }
	       }
   }
	     else {
	    	    JOptionPane.showMessageDialog(null, "No Files Selected");
	    	}	     
   
		
	}
	   public static void copyJarFile(JarFile jarFile, File destDir) throws IOException {
	       String fileName = jarFile.getName();
	       String fileNameLastPart = fileName.substring(fileName.lastIndexOf(File.separator));
	       File destFile = new File(destDir, fileNameLastPart);
	 
	       JarOutputStream jos = new JarOutputStream(new FileOutputStream(destFile));
	       Enumeration<JarEntry> entries = jarFile.entries();
	 
	       while (entries.hasMoreElements()) {
	           JarEntry entry = entries.nextElement();
	           InputStream is = jarFile.getInputStream(entry);
	 
	           //jos.putNextEntry(entry);
	           //create a new entry to avoid ZipException: invalid entry compressed size
	           jos.putNextEntry(new JarEntry(entry.getName()));
	           byte[] buffer = new byte[4096];
	           int bytesRead = 0;
	           while ((bytesRead = is.read(buffer)) != -1) {
	               jos.write(buffer, 0, bytesRead);
	           }
	           is.close();
	           jos.flush();
	           jos.closeEntry();
	       }
	       jos.close();
	       
	   }
	private void setLookNFeel(){
		try {			   //   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e1) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
			try {
			     //  UIManager.setLookAndFeel(UIManager.get);
			      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			    }
			    catch(Exception e) {
			      e.printStackTrace();
			    }
		
	}}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
new DialogueTest().setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		JButton source = (JButton) e.getSource();
        source.setEnabled(false);
		this.dispose();
		//setEditable(false);
		new PoolRunnerApplication();
	}
}
