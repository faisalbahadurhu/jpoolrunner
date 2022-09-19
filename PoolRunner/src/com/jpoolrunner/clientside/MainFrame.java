package com.jpoolrunner.clientside;

//if(defined) return;    uncomment this line to avoid having multiple pane of same type so that 1 cant do multiple simulation of same strategy in a single time anyhow i commented it because i m testing it and i think no problem is there in it so keep multiple panes filhal
import java.io.*;
import java.awt.*;
import java.net.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.util.*;
import java.util.regex.Pattern;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import org.apache.commons.validator.routines.InetAddressValidator;
import org.jfree.chart.JFreeChart;

import com.jpoolrunner.diskIO.DataLoader;
import com.jpoolrunner.diskIO.DataSaver;
import com.jpoolrunner.displaytestedscenariosonly.TpsOnDisk;
import com.jpoolrunner.tpsandtestnamesloader.FolderAndFileNames;
import com.jpoolrunner.tpsandtestnamesloader.ResultPath;
import com.jpoolrunner.tpsandtestnamesloader.TpsAndTestsNameLoader;
import com.jpoolrunner.tpsandtestnamesloader.TpsNameAndTestNamesPanel;
import com.jpoolrunner.warmup.Launcher;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class MainFrame extends JFrame implements ActionListener{
	//Scenario realScenario;
	BooleanChecker sendnotSendingProtocol;//only at the time of request sending it will be false 
	Vector scenarios;
	static boolean first=true;
	Vector tpsOnDiskVector;
	Vector differentScenarios;//for comparison 
	JButton loadTestButton;
	//DefineScenario ds;
	boolean exitWithoutStart=true;//no need of it any how leave it as it is
	ConnectToServer connectToServer;
	JPanel contentPane;
	JMenuBar jMenuBar1 = new JMenuBar();
	JMenu jMenuFile = new JMenu();
	JMenuItem loadTestGraphmenu = new JMenuItem("Open Test Report");
	//JMenu testMenu = new JMenu("Just Testing");
	JMenuItem deleteReport = new JMenuItem("Delete Test Report");


	JMenuItem frequency = new JMenuItem("Request Frequency");
	JMenu jMenuServer = new JMenu();
	JMenuItem jMenuServerConfiguration = new JMenuItem();
	JMenuItem jMenuFileExit = new JMenuItem();
	// JMenuItem jMenuSaveFile = new JMenuItem();
	JMenu jMenuHelp = new JMenu();
	JMenuItem jMenuHelpAbout = new JMenuItem();
	JToolBar jToolBar = new JToolBar();
	JButton jButton1 = new JButton();
	JButton jButton2 = new JButton();
	JButton jButton3 = new JButton();
	ImageIcon image1;
	ImageIcon image2;
	ImageIcon image3;
	BorderLayout borderLayout1 = new BorderLayout();
	// JPanel centeredPanel = new JPanel();
	TitledBorder titledBorder1;
	BorderLayout borderLayout2 = new BorderLayout();
	JPanel settingPanel = new JPanel();
	TitledBorder titledBorder2;
	JTabbedPane jTabbedPane1 = new JTabbedPane();
	JPanel jPanel1 = new JPanel();
	JPanel scenario = new JPanel();
	JPanel tuningStrategies = new JPanel();
	BorderLayout borderLayout3 = new BorderLayout();
	JPanel simulationGUIPanel = new JPanel();
/*	final Pattern PATTERN = Pattern.compile(
	        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
	public  boolean validate(final String ip) {
	    return PATTERN.matcher(ip).matches();
	}*/

	public JPanel getSimulationGUIPanel() {
		return simulationGUIPanel;
	}
	
	private final JPanel strategyConnectPanel = new JPanel();
	private final JPanel strategyTreePanel = new JPanel();
	private JTextField txtLocalhost;
	JTree tree;
	////////////////////////////JTabbedPane scenariosPane;
	ClosableTabbedPane scenariosPane;
	DefaultMutableTreeNode root;// = new DefaultMutableTreeNode("Root");
	private final JButton btnDisconnect = new JButton("Disconnect");
	JButton btnConnect;
	private String newScenarioname="";//when a new scenario is created this string store its name so that if new scenario is not started and any other scenario is closed from pane then "notstarting protocol is not send at the server side"
	private final JPanel panel_1 = new JPanel();
	//ResultPath menuResultPath=new ResultPath();
	//private final JLabel label = new JLabel("");
	private void  deleteDataFolderAndAllSubfoldersIfExist(){
		try{
			File pathZ = Paths.get(DataSaver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			String fullPath=uri+"data";// it will return   file:/C:/data/folderName
			URL pathURL=new URL(fullPath);
			Path newPath=Paths.get(pathURL.toURI());
			deleteAll(newPath);
		}
		catch(Exception e){
			//	JOptionPane.showMessageDialog(null, e);
		}
	}
	

	private void deleteAll(Path dir){
		try {
			Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(Path file,
						BasicFileAttributes attrs) throws IOException {

					//   System.out.println("Deleting file: " + file);
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir,
						IOException exc) throws IOException {

					//    System.out.println("Deleting dir: " + dir);
					if (exc == null) {
						Files.delete(dir);
						return FileVisitResult.CONTINUE;
					} else {
						throw exc;
					}
				}

			});
		} catch (IOException e) {
			//	JOptionPane.showMessageDialog(null, e);

		}

	}
	//Construct the frame
	public MainFrame() {
		addWindowListener(new java.awt.event.WindowAdapter() {//delete data folder when user close Jframe
			public void windowClosing(java.awt.event.WindowEvent e) {
				//deleteDataFolderAndAllSubfoldersIfExist();
				System.exit(0);
			}
		});
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			initilize();
			//deleteDataFolderAndAllSubfoldersIfExist();//it is possible that data folder is not deleted when user was quitted last so ckeck if it exists then delete it
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void actionPerformed(ActionEvent e){
		/*
	   jMenuSaveFile.add(frequency);jMenuSaveFile.add(response);
    jMenuSaveFile.add(wait);jMenuSaveFile.add(poolSize);jMenuSaveFile.add(throughput);

		 */
		if(scenariosPane.getTabCount()>0){
			int i=scenariosPane.getSelectedIndex();
			String TabPaneName=scenariosPane.getTabTitleAt(i);
			///////////
			Iterator theData=scenarios.iterator();
			Scenario selectedScenario=null;
			while(theData.hasNext()){
				Scenario scenario=(Scenario) theData.next();
				if (scenario.getStrategyName().equals(TabPaneName)){
					selectedScenario=scenario;
					break;
				}
			}
			/*
 	   	public JFreeChart getFrequencyChart(){return requestFrequencyPanel.getChart(); }
	public JFreeChart getThroughputChart(){return throughputPanel.getChart(); }
	public JFreeChart getResponseChart(){return responsePanel.getChart(); }
	public JFreeChart getWaitChart(){return waitPanel.getChart(); }
	public JFreeChart getPoolSizeChart(){return poolSizePanel.getChart(); }
	public JFreeChart getAcceptanceLatencyChart(){return acceptanceLatencyPanel.getChart(); }

			 */
			/*		JFreeChart chart=null;
			if(e.getSource()==frequency){
			//	chart= selectedScenario.getFrequencyChart();
			}
			else if(e.getSource()==response){chart= selectedScenario.getResponseChart();}
			else if(e.getSource()==wait){chart= selectedScenario.getWaitChart();}
			else if(e.getSource()==poolSize){chart= selectedScenario.getPoolSizeChart();}
			else if(e.getSource()==throughput){chart= selectedScenario.getThroughputChart();}
			else if(e.getSource()==acceptanceLatency){chart= selectedScenario.getAcceptanceLatencyChart();}
			 */
			//	GraphSaver graphSaver=new GraphSaver(chart);

			//	graphSaver.saveGraph();

		}
	}
	public boolean howManyTabs(){
		if(scenariosPane.getTabCount()>1)return true; 
		else return false;
	}
	//Component initialization
	private void initilize() throws Exception  {
		sendnotSendingProtocol=new BooleanChecker();
		this.scenarios=new Vector(5,5);//
		this.tpsOnDiskVector=new Vector(5,5);//
		this.differentScenarios=new Vector(5,5);
		btnDisconnect.setEnabled(false);
		image1 = new ImageIcon(getClass().getResource("/images/openFile.png"));
		image2 = new ImageIcon(getClass().getResource("/images/closeFile.png"));
		image3 = new ImageIcon(getClass().getResource("/images/help.png"));
		contentPane = (JPanel) this.getContentPane();
		titledBorder1 = new TitledBorder("");
		titledBorder2 = new TitledBorder("");
		contentPane.setLayout(borderLayout1);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screenSize);
		this.setTitle("PoolRunner");
		jMenuFile.setText("File");
		jMenuServer.setText("Server");
		jMenuServerConfiguration.setText("Configurations");
		jMenuFileExit.setText("Exit");
		jMenuFileExit.addActionListener(new MainFrame_jMenuFileExit_ActionAdapter(this));
		jMenuHelp.setText("Help");
		jMenuHelpAbout.setText("About");
		jMenuHelpAbout.addActionListener(new MainFrame_jMenuHelpAbout_ActionAdapter(this));
		jButton1.setIcon(image1);
		jButton1.setToolTipText("Open File");
		jButton2.setIcon(image2);
		jButton2.setToolTipText("Close File");
		jButton3.setIcon(image3);
		jButton3.setToolTipText("Help");
		// centeredPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		//  centeredPanel.setLayout(borderLayout2);
		// centeredPanel.setPreferredSize(new Dimension(300, 10));
		settingPanel.setBorder(titledBorder2);
		settingPanel.setDebugGraphicsOptions(0);
		settingPanel.setPreferredSize(new Dimension(300, 10));
		settingPanel.setLayout(borderLayout3);
		contentPane.setBackground(SystemColor.control);
		jTabbedPane1.setPreferredSize(new Dimension(110, 68));
		simulationGUIPanel.setBorder(BorderFactory.createEtchedBorder());
		jToolBar.add(jButton1);
		jToolBar.add(jButton2);
		jToolBar.add(jButton3);
		contentPane.add(simulationGUIPanel, BorderLayout.CENTER);
		simulationGUIPanel.setLayout(new BorderLayout(0, 0));

		//////////////////// scenariosPane = new JTabbedPane(JTabbedPane.TOP);
		scenariosPane= new ClosableTabbedPane(){
			public boolean tabAboutToClose(int tabIndex) {
				String tab = scenariosPane.getTabTitleAt(tabIndex);
				int sizeZ=scenarios.size();//JOptionPane.showMessageDialog(null, "size="+sizeZ);
				Scenario tempScenario=null;
				for(int i=0 ;i<sizeZ;i++){
					tempScenario=(Scenario)scenarios.elementAt(i);
					//	((Scenario)scenarios.elementAt(i)).
					if(tempScenario.getStrategyName().equals(tab)){

						break;
					}

				}
				int choice=0;

				//if (start=on N stop=off N save=off) do u wan2 quit without runnaing any simulation
				if(tempScenario!=null && tempScenario.getStrategyName().equals(tab)){
					if(tempScenario.isStartButtonEnable() && !tempScenario.isStopButtonEnable() && !tempScenario.isSaveButtonEnable()){
						choice = JOptionPane.showConfirmDialog(null, 
								"Do you want to quit without running any simulation ?", 
								"Confirmation Dialog", 
								JOptionPane.INFORMATION_MESSAGE);	
					}//else if(start=off N stop=on N save=off) u cnt quit between simulation choice=1
					else if(!tempScenario.isStartButtonEnable() && tempScenario.isStopButtonEnable() && !tempScenario.isSaveButtonEnable() ||(sendnotSendingProtocol.isSend()==false)){//sendnotSendingProtocol is ture when all responses have been plotted 
						JOptionPane.showMessageDialog(null, "You cant exit in between simulation");
						choice=1;


					}//else if (start=off N stop=off N save=on) Do u wan 2 quit W/O saving Results of simulation
					else if(!tempScenario.isStartButtonEnable() && !tempScenario.isStopButtonEnable() && tempScenario.isSaveButtonEnable()){
						choice = JOptionPane.showConfirmDialog(null, 
								"Do you want to quit without saving simulation result ?", 
								"Confirmation Dialog", 
								JOptionPane.INFORMATION_MESSAGE);	
					}//
					else if(!tempScenario.isStartButtonEnable() && !tempScenario.isStopButtonEnable() && !tempScenario.isSaveButtonEnable()){
						choice = JOptionPane.showConfirmDialog(null, 
								"Do you want to proceed ?", 
								"Confirmation Dialog", 
								JOptionPane.INFORMATION_MESSAGE);	
					}//
				}

				/*	if((sendnotSendingProtocol.isSend()==true)){// ie start button is not clicked coz upon clicking start it would be false

					choice = JOptionPane.showConfirmDialog(null, 
							"Do you want to proceed ?", 
									"Confirmation Dialog", 
									JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "You cant exit in between simulation");

					choice=1;
				}*/
				int choice2=choice;
				if(choice2==JOptionPane.YES_OPTION){

				//	if(scenario.gets){}
					////////
					if((tempScenario!=null)&&((!tempScenario.isWarmupButtonEnabled()&& tempScenario.isStartButtonEnabled()&& !tempScenario.isStopButtonEnabled()&&!tempScenario.isSaveButtonEnabled())||
							(newScenarioname.equals(tab) && exitWithoutStart && (loadTestButton.isEnabled()==false) && (sendnotSendingProtocol.isSend()==true) ))){
						try{//      sendnotSendingProtocol is kept false when start button is clicked so that during request sending the notsending protocol is not sent but when ThroughputPanel stops it make it true again but at that time the stop signal bheja ja chuka hota hay or server side pe threads stop ho chukay hoty hain 
							ObjectOutputStream out=connectToServer.getObjectOutputStream();
							out.writeObject("notstarting");
							out.flush();

						}catch(Exception e1){
							System.out.println("Exception in MainFrame when sending notstarting signal");
							e1.printStackTrace();
						}	
					}
					if(loadTestButton.isEnabled()==false && newScenarioname.equals(tab))loadTestButton.setEnabled(true);

					////////
					//	removePanelpermenently(tab);
					removePanel();
					int size=scenarios.size();
					for(int i=0 ;i<size;i++){
						Scenario s=(Scenario)scenarios.elementAt(i);
						//	((Scenario)scenarios.elementAt(i)).
						if(s.getStrategyName().equals(tab)){
							s=null;
							scenarios.removeElementAt(i);
							//JOptionPane.showMessageDialog(null, "removed n scenarios size="+scenarios.size());
							break;
						}

					}
					//no need of following code coz not adding scenarios in differentScenarios
					int size2=differentScenarios.size();
					for(int i=0 ;i<size2;i++){
						Scenario s=(Scenario)differentScenarios.elementAt(i);
						if(s.getStrategyName().equals(tab)){
							s=null;
							differentScenarios.removeElementAt(i);
							//JOptionPane.showMessageDialog(null, "removed");

							break;
						}

					}

					for(int i=0 ;i<tpsOnDiskVector.size();i++){
						TpsOnDisk s=(TpsOnDisk)tpsOnDiskVector.elementAt(i);
						if(s.getStrategyName().equals(tab)){
							s=null;
							tpsOnDiskVector.removeElementAt(i);
							//JOptionPane.showMessageDialog(null, "tpsOnDiskVector size="+tpsOnDiskVector.size());

							break;
						}

					}


					//JOptionPane.showMessageDialog(null, "size="+scenarios.size());



				}
				/* if (choice==0) return false;
            else return choice == 0;*/
				GCRunner gcRunner=new GCRunner();
				gcRunner.setPriority(Thread.MAX_PRIORITY);
				gcRunner.start();
				return choice == 0;
				// if returned false tab
				// closing will be canceled
			}
		};;
		// on selecting a tabbed pane its scenario summary is displayed 
		scenariosPane.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if(scenariosPane.getTabCount()>0){
					int i=scenariosPane.getSelectedIndex();
					String TabPaneName=null;
					try{
						TabPaneName=scenariosPane.getTabTitleAt(i);
					}catch(ArrayIndexOutOfBoundsException aioobe){
						System.out.println("scenariosPane.getTabTitleAt() method ArrayIndex Exception has handeled");
					}
					///////////
					Iterator theData=scenarios.iterator();
					boolean check=false;
					while(theData.hasNext()){
						Scenario scenario=(Scenario) theData.next();
						if (scenario.getStrategyName().equals(TabPaneName)){
							JPanel panel=scenario.getPanel();
							removePanel();                		  
							addPanel(panel);
							check=true;
							// JOptionPane.showMessageDialog(null, "message");
							break;
						}
					}

					if(check==false && tpsOnDiskVector.size()>0){// ie TpsOnDisk panel is clicked instead of 
						Iterator theData2=tpsOnDiskVector.iterator();
						while(theData2.hasNext()){
							TpsOnDisk tps=(TpsOnDisk) theData2.next();
							if (tps.getStrategyName().equals(TabPaneName)){
								//	JPanel panel=scenario.getPanel();
								removePanel();                		  
								addPanel(tps.getDynamicPanel());
								// JOptionPane.showMessageDialog(null, "message");
								break;
							}
						}
					}
					///////////

				}


			}
		});
		simulationGUIPanel.add(scenariosPane, BorderLayout.CENTER);
		//  contentPane.add(centeredPanel, BorderLayout.WEST);
		contentPane.add(settingPanel, BorderLayout.WEST);
		//  centeredPanel.add(settingPanel, BorderLayout.WEST);

		settingPanel.add(jTabbedPane1, BorderLayout.CENTER);

		// tuningStrategies.putClientProperty("JComponent.sizeVariant", "mini");
		//  jTabbedPane1.putClientProperty("JComponent.sizeVariant", "small");
		jTabbedPane1.add(tuningStrategies, "Configurations");
		Dimension dim=tuningStrategies.getPreferredSize();
		Dimension tuningStrategiesDimension=new Dimension(dim.width,dim.height/3);
		strategyConnectPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		strategyConnectPanel.setBounds(0, 0, 281, 64);
		strategyConnectPanel.setPreferredSize(tuningStrategiesDimension);
		strategyTreePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		strategyTreePanel.setBounds(0, 67, 281, 203);
		strategyTreePanel.setPreferredSize(tuningStrategiesDimension);
		tuningStrategies.setLayout(null);
		tuningStrategies.add(strategyConnectPanel);
		strategyConnectPanel.setLayout(null);
		JLabel lblServerIp = new JLabel("Server IP");
		lblServerIp.setHorizontalAlignment(SwingConstants.CENTER);
		lblServerIp.setBounds(0, 11, 69, 14);
		strategyConnectPanel.add(lblServerIp);
		txtLocalhost = new JTextField();
		txtLocalhost.setText("localhost");
		txtLocalhost.setMinimumSize(new Dimension(8, 7));
		txtLocalhost.setPreferredSize(new Dimension(7, 7));
		txtLocalhost.setBounds(79, 7, 164, 23);
		strategyConnectPanel.add(txtLocalhost);
		txtLocalhost.setColumns(10);
		
		txtLocalhost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InetAddressValidator validate=new InetAddressValidator();

				String server=txtLocalhost.getText().replaceAll("\\s+","");
				if((server).length()!=0)	{//st.replaceAll("\\s+","") removes all whitespaces and non-visible characters (e.g., tab, \n).
					if(server.equalsIgnoreCase("localhost")|| validate.isValid(server)) {
					//	JOptionPane.showMessageDialog(null, "Illegal IPIV format ");
					//	return;
					
					connectToServer=new ConnectToServer(server,tree);
					boolean connected=connectToServer.connect();
					if(connected){
						loadTestButton.setEnabled(true);
						btnDisconnect.setEnabled(true);
						btnConnect.setEnabled(false);
					}
				}
					else JOptionPane.showMessageDialog(null, "Illegal IPIV format ");
					}
			}
		});
		
		btnConnect = new JButton("Connect");
		btnConnect.setBorder(null);
		btnConnect.setBackground(SystemColor.activeCaption);
		btnConnect.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				InetAddressValidator validate=new InetAddressValidator();
			//	vdtr.isValid(inetAddress)
				String server=txtLocalhost.getText().replaceAll("\\s+","");
				if((server).length()!=0)	{//st.replaceAll("\\s+","") removes all whitespaces and non-visible characters (e.g., tab, \n).
					if(server.equalsIgnoreCase("localhost")|| validate.isValid(server)) {
						//JOptionPane.showMessageDialog(null, "Illegal IPIV format ");
					//	return;
					
					connectToServer=new ConnectToServer(server,tree);
						boolean connected=connectToServer.connect();
					if(connected){
						loadTestButton.setEnabled(true);
						btnDisconnect.setEnabled(true);
						btnConnect.setEnabled(false);
					}
				}
					else JOptionPane.showMessageDialog(null, "Illegal IPIV format ");
	
				}
			}
		});
		btnConnect.setBounds(32, 36, 89, 23);
		strategyConnectPanel.add(btnConnect);
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDisconnect.setEnabled(false);
				btnConnect.setEnabled(true);
				connectToServer.disconnect();	
				//tree.removeAll();
				root.removeAllChildren();
				DefaultTreeModel  model = (DefaultTreeModel) (tree.getModel()); 
				model.reload();
				//tree.validate();

			}
		});
		btnDisconnect.setBounds(142, 36, 101, 23);

		strategyConnectPanel.add(btnDisconnect);
		tuningStrategies.add(strategyTreePanel);

		root = new DefaultMutableTreeNode("TPS");

		// tree.setBounds(56, 34, 72, 88);
		tree = new JTree(root);

		JScrollPane pane=new JScrollPane(tree);
		pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		strategyTreePanel.setLayout(new BorderLayout(0, 0));
		JLabel lbl=new JLabel("Thread Pool Tuning strategies");
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbl.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		strategyTreePanel.add(lbl,BorderLayout.NORTH);
		//label.setBounds(145, 2, 143, 166);

		//  strategyTreePanel.add(label);
		strategyTreePanel.add(pane,BorderLayout.CENTER);
		//	pane.setColumnHeaderView(tree);
		tree.setShowsRootHandles(true);
		tree.setBackground(SystemColor.activeCaption);

		loadTestButton = new JButton("Prepare Test Plan");
		loadTestButton.setEnabled(false);
		loadTestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedStrategy;
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)
						tree.getLastSelectedPathComponent();

				if (node == null || node.isRoot()){//if no node is selected or if Root is selected ...root is not a strategy it is just string Strategies which have children strategy names
					JOptionPane.showMessageDialog(null, "No Strategy is selected");
				}
				else {
					selectedStrategy=node.getUserObject().toString();
					boolean scenarioExists=false;//ie how many tab panes are there false means no tabs

					if(scenariosPane.getTabCount()>0){
						JOptionPane.showMessageDialog(null, "Strategy in the testing pane utilizes system resources\n New Strategy must use full system resources\nClose all existing panes first");
						return;
					}
					TpsAndTestsNameLoader tpsAndTestNamesloder=new TpsAndTestsNameLoader();
					FolderAndFileNames[] folderAndFileNames=tpsAndTestNamesloder.getTpsAndTestNames();
					//	DefineScenarioNew3 myPanel=new DefineScenarioNew3(getStrategynames(),folderAndFileNames);
					if(folderAndFileNames!=null) scenarioExists=true;
					else scenarioExists=false;
					//	myPanel.sethowmanyScenarios(scenarioExists);
					BooleanChecker check=new BooleanChecker();
					check.setSend(false);
					DataLoader dataLoader=new DataLoader();// just use to check if strategy exists on disk and if exists then how many tests are there so that we can give a new number to a new test
					int numberOfTests=0;
					String tps=selectedStrategy.replaceAll("\\s+","");

					//	numberOfTests=dataLoader.countNumberOfTestsInTPS(tps);
					//	JOptionPane.showMessageDialog(null, "TPS tests="+numberOfTests);


					if(dataLoader.tspFolderExistsOrNot(tps)){// check whether TPS already exists on disk i.e. any previous test has been run before

						numberOfTests=dataLoader.countNumberOfTestsInTPS(tps);
						//	JOptionPane.showMessageDialog(null, "TPS Exists with tests="+numberOfTests);

					}


					numberOfTests++;
					DefineScenarioNew3 myPanel=new DefineScenarioNew3(getStrategynames(),folderAndFileNames,tps);
					myPanel.sethowmanyScenarios(scenarioExists);


					//	myPanel.setSelectedTPS(tps);//The name of selected TPS to test it is w/o test#
					tps+="/Test"+numberOfTests;


					DefineScenarioDialog defineScenarioDialog=new DefineScenarioDialog(myPanel,check,tps,MainFrame.this);

					//	DefineScenarioDialog defineScenarioDialog=new DefineScenarioDialog(myPanel,check,selectedStrategy,MainFrame.this);

					defineScenarioDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

					//defineScenarioDialog.setAlwaysOnTop(true);
					defineScenarioDialog.pack();
					defineScenarioDialog.setModal(true);
					defineScenarioDialog.setVisible(true);



					if (check.isSend()==true){//(result == JOptionPane.OK_OPTION){
						Scenario scenario=new Scenario(MainFrame.this,connectToServer.getObjectOutputStream(),connectToServer.getObjectInputStream(),selectedStrategy);
						scenario.setTestNumber(numberOfTests);
						scenario.setButton(loadTestButton);// the loadtest button is enabled in ThroughputPanel when all responses plotted
						scenario.setStopWatchForThroughputPanel();//StopWatch is stopped in throughputPanel when all responses plotted succesfully
						scenario.setSaveButtonForThroughputPanel();
						scenario.setBooleanChecker(sendnotSendingProtocol);
						loadTestButton.setEnabled(false);
						try{
							ObjectOutputStream out=connectToServer.getObjectOutputStream();
							out.writeObject(selectedStrategy);
							out.flush();


						}catch(Exception e1){
							System.out.println("Exception in MainFrame when sending name of strategy");
							e1.printStackTrace();
						}	//loadTestGraphmenu
						//scenario.setLoadTestGraphmenu(loadTestGraphmenu);
						if(myPanel.getusePreviousScenario()==false){

							scenario.setUsePreviousScenario(myPanel.getusePreviousScenario());
							try{
								scenario.setStaticLoadValues(myPanel.getStaticLoadValues());
								scenario.setDynamicLoadValues(myPanel.getDynamicLoadValues());
								scenario.setStaticLoadPercentage(myPanel.getStaticRequestQuantity());
								scenario.setDynamicLoadPercentage(myPanel.getdynamicRequestQuantity());
								scenario.setWorkloadPattern(myPanel.getWorkloadPattern());// ie poisson or Guassian etc
								scenario.setLambdaForPoisson(myPanel.getLambdaForPoisson());
								scenario.setnForUniform(myPanel.getnForUniform());
								scenario.setMeanForGuassian(myPanel.getMeanForGuassian());
								scenario.setStandardDeviationForGuassin(myPanel.getStandardDeviationForGuassin());
								scenario.setConstantDistibutor(myPanel.getConstantDist());
								scenario.setStartForManualDistribution(myPanel.getStartForManualDistribution());
								scenario.setEndForManualDistribution(myPanel.getEndForManualDistribution());
								scenario.setScaleForPareto(myPanel.getScalePareto());
								scenario.setShapeForPareto(myPanel.getShapePareto());
								//scenario.setDecreaseForManualDistribution(myPanel.getDecreaseForManualDistribution());
								scenario.prepareWorkLoad();// it will prepare 100 jobs in a vector with specified loads
								scenario.setWorkloadForBins();//This will prepare a Vector Varible in Response and Wait Panels.
								scenario.prepareLoadPattern();//Now initialize FrequencyDistributorInterface to Poisson or Guasssian or whatever is selected
								scenario.setRequestSenderLoadAttributes();//


							}catch(Exception ex){
								ex.printStackTrace();
							}

						}
						else{ 
							DataLoader dataloader=new DataLoader(myPanel.getTestNumberAndTpsNamePath());// path is in form of e.g. NFBOS2/Test1

							Vector<Integer> requestFrequencies=dataloader.loadIntegerData("FrequencySeries");//load request frequencies
							String load=dataloader.loadStringData("RealLoad");//it will read Realload file that contains data in form of tokens of strings I200C15 must split this string by spaces as i have put space between each token when saving in DataSaver class method of saveRealLoad()
							String[] realLoad=load.split("\\s+");//tokens have been splited up by spaces read comment of above statement
							String workLoadForBins1=dataloader.loadStringData("WorkLoadForBins");
							String[] workLoadForBins=workLoadForBins1.split("\\s+");//tokens have been splited up by spaces read comment of above statement
							
							Vector<Integer> staticAndDynamicLoadValues=dataloader.loadIntegerData("staticAndDynamicLoad");// saved as int oneKb,int tenKb,int hundredKb,int thousandKb,int twoThousandKb,int tenMilliSecond,int fiftyMilliSecond,int hundredMilliSecond,int staticLoadPercentage,int dynamicLoadPercentage
							String workLoadPatternValue=dataloader.loadStringData("WorkloadPattern");//name of the distribution ie poisson uniform guassian heavy tailed etc
							scenario.setPreviousScenarioName(myPanel.getTestNumberAndTpsNamePath());
							scenario.setScenarioFromPreviousTest(requestFrequencies,realLoad,staticAndDynamicLoadValues,workLoadPatternValue);
							scenario.setRequestSenderFrequencyAndLoadAttributes();
							scenario.setWorkloadForBins(workLoadForBins);
							scenario.disableStopButton();//disable STOP button because its simulation would be stopped itself upon emptying requestFrequenciesClone Vector in RequestSender
							dataloader=null;
							requestFrequencies=null;
							load=null;
							realLoad=null;
							staticAndDynamicLoadValues=null;
							workLoadPatternValue=null;

							GCRunner gcRunner=new GCRunner();
							gcRunner.setPriority(Thread.MAX_PRIORITY);
							gcRunner.start();

						}

						defineScenarioDialog=null;
						myPanel=null;	
						scenario.prepareResponseNWaitPanelgraphics();
						scenario.preparePanel();//prepare summary panel for scenario
						scenario.prepareTabPanel(scenariosPane);
						scenarios.addElement(scenario);//add this defined scenario in the vector called scenarios
						//	differentScenarios.addElement(scenario);
						removePanel();//remove panel if it exists ....summary panel only
						addPanel(scenario.getPanel());///add summary panel

						addTabPanel(scenario.getTabPanel(),scenario.getStrategyName());//add Graphs panel 
						//addTabPanel(scenario.getTabPanel(),tps);//add Graphs panel 

						newScenarioname=scenario.getStrategyName();
						try{
							ObjectInputStream in=connectToServer.getObjectInputStream();
							String initialThreadPoolSize=(String)in.readObject();
							int initialPoolSize=Integer.parseInt(initialThreadPoolSize);
							scenario.setInitialThreadPoolSize(initialPoolSize);
						/*	if(first==true){
							MainFrame.this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
							new Launcher(in,MainFrame.this).start();//This thread will wait for server message that server has performed JIT optimizations and then it will restore the cursor 
							first=false;
							}*/


						}catch(Exception e1){
							System.out.println("Exception in MainFrame when sending name of strategy");
							e1.printStackTrace();
						}
					}
				}
				// JOptionPane.showMessageDialog(null, scenarios.size());
			}
		}//else
				);
		strategyTreePanel.add(loadTestButton,BorderLayout.SOUTH);
		panel_1.setBounds(0, 269, 281, 245);
		jMenuFile.add(jMenuFileExit);
		frequency.addActionListener(this);
		jMenuFile.add(loadTestGraphmenu);
		jMenuFile.add(deleteReport);

		loadTestGraphmenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TpsAndTestsNameLoader tpsAndTestNamesloder=new TpsAndTestsNameLoader();
				FolderAndFileNames[] folderAndFileNames=tpsAndTestNamesloder.getTpsAndTestNames();
				if(folderAndFileNames!=null) {
					//8
					JPanel tpsFolderPanel=new JPanel(new GridLayout(folderAndFileNames.length,1,5,5));
					ButtonGroup buttonGroup=new ButtonGroup();
					ResultPath testNumberAndTpsNamePath=new ResultPath();
					testNumberAndTpsNamePath.setPath(folderAndFileNames[0].getTpsName()+"/"+(folderAndFileNames[0].getTests().elementAt(0)));//it is a default Resultpath so that if user clicks ok w/o checking any radiobutton then resultpath still have some value so that exception can not be generated on run time
					TpsNameAndTestNamesPanel panelGenerator=new TpsNameAndTestNamesPanel(testNumberAndTpsNamePath,buttonGroup);
					for(int d=0;d<folderAndFileNames.length;d++){
						String tpsName=folderAndFileNames[d].getTpsName();
						Vector<String> testNames=folderAndFileNames[d].getTests();

						JPanel pnl=panelGenerator.getPanel(tpsName, testNames,d);
						tpsFolderPanel.add(pnl);
					}	

					JPanel innerPanel = new JPanel();
					innerPanel.setLayout(new BorderLayout());
					innerPanel.setPreferredSize(new Dimension(300,100));

					JScrollPane scrollPane=new JScrollPane(tpsFolderPanel);
					scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
					scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


					//	scrollPane.setBounds(5, 5, 100, 100);
					innerPanel.add(scrollPane, BorderLayout.CENTER);
					int result = JOptionPane.showConfirmDialog(null, innerPanel,"Load Test Reports", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);

					if(result==JOptionPane.OK_OPTION){
						boolean check=false;
						int totalTabs = scenariosPane.getTabCount();
						//  boolean defined=false;
						for(int i = 0; i < totalTabs; i++)//detect whether a pane is already displyed
						{
							String name = scenariosPane.getTabTitleAt(i);
							if(name.equals(testNumberAndTpsNamePath.getPath())) {
								JOptionPane.showMessageDialog(null, "Test is already opened");
								check=true;
								break;
							}
						}//
						if(check==true) return;

						DataLoader dataloader=new DataLoader(testNumberAndTpsNamePath.getPath());// path is in form of e.g. NFBOS2/Test1
						Vector<Integer> requestFrequencies=dataloader.loadIntegerData("FrequencySeries");//load request frequencies
						//Vector<Double>  responseSeries=dataloader.loadDoubleDatas("ResponseSeries");
						//Vector<Double>  waitPanelSeries=dataloader.loadDoubleDatas("WaitPanelSeries");
						Vector<Double>  throughputPanelSeries=dataloader.loadDoubleDatas("ThroughputPanelSeries");
						Vector<Double>  poolSizePanelSeries=dataloader.loadDoubleDatas("PoolSizePanelSeries");
						Vector<Double> resultStatistics=dataloader.loadDoubleDatas("ResultStatistics"); //saved as double totalThroughput,double avgThroughput,double fiftyPercentileRT,double nintyPercentileRT,double nintyFivePercentileRT,double avgRT,double avgWaitTime,double avgPoolSize){
						Vector<Integer> staticAndDynamicLoadValues=dataloader.loadIntegerData("staticAndDynamicLoad");// saved as int oneKb,int tenKb,int hundredKb,int thousandKb,int twoThousandKb,int tenMilliSecond,int fiftyMilliSecond,int hundredMilliSecond,int staticLoadPercentage,int dynamicLoadPercentage
						String workLoadPatternValue=dataloader.loadStringData("WorkloadPattern");//name of the distribution ie poisson uniform guassian heavy tailed etc
						String workLoadForBins1=dataloader.loadStringData("WorkLoadForBins");
						String[] workLoadForBins=workLoadForBins1.split("\\s+");//tokens have been splited up by spaces
						Vector<JobPercentiles> jobPercentilesVector=new Vector<JobPercentiles>(5);
						for(int i=0;i<workLoadForBins.length;i++){
							//JobPercentiles
							String s1=dataloader.loadStringData("R"+workLoadForBins[i]);//i.e. R100 for example
							String s1Split[]=s1.split("\\s+");// it is is form of 100 100-119 119-125 e.g ie name of job 50%ile 90%ile
							String s2=dataloader.loadStringData("W"+workLoadForBins[i]);//i.e. W100 for example
							String s2Split[]=s2.split("\\s+");//100-119 119-125 e.g
						//	public JobPercentiles(String jobName,String fiftyPercentileRT, String nintyPercentileRT, String fiftyPercentileWt, String nintyPercentileWt) {
							jobPercentilesVector.add(new JobPercentiles(workLoadForBins[i],s1Split[1],s1Split[2],s2Split[1],s2Split[2]));//0 index contains ame of the job and not needed here as it is in workLoadForBins[i]
						//	JOptionPane.showMessageDialog(null, s1Split[1]+" "+s1Split[2]+"...."+s2Split[1]+" "+s2Split[2]);						
						}
						Vector<String> previousTestNames=new Vector<String>();
						String path2=testNumberAndTpsNamePath.getPath();
						while(path2!=""){
							String previousTest=dataloader.loadPreviousTestName(path2);
							if(previousTest!=""){ 
								previousTestNames.add(previousTest);
							}
							path2=previousTest;

						}
						try{
						//TpsOnDisk tpsOnDisk=new TpsOnDisk(testNumberAndTpsNamePath.getPath(),requestFrequencies,responseSeries,waitPanelSeries,throughputPanelSeries,poolSizePanelSeries,resultStatistics,staticAndDynamicLoadValues,workLoadPatternValue);
						TpsOnDisk tpsOnDisk=new TpsOnDisk(MainFrame.this,testNumberAndTpsNamePath.getPath(),requestFrequencies,throughputPanelSeries,poolSizePanelSeries,resultStatistics,staticAndDynamicLoadValues,workLoadPatternValue,previousTestNames,jobPercentilesVector,workLoadForBins);

						addTabPanel(tpsOnDisk,testNumberAndTpsNamePath.getPath());//full panel at the centre with all graphs etc
						removePanel();//summarayPanel
						addPanel(tpsOnDisk.getDynamicPanel());//small panel at the left side
						tpsOnDiskVector.add(tpsOnDisk);
						}catch(Exception ex){System.out.println(""+ex.toString());}
						requestFrequencies=null;
						//responseSeries=null;
						//waitPanelSeries=null;
						throughputPanelSeries=null;
						poolSizePanelSeries=null;
						resultStatistics=null;
						GCRunner gcRunner=new GCRunner();
						gcRunner.setPriority(Thread.MAX_PRIORITY);
						gcRunner.start();

					}

				}
				else{ 
					JOptionPane.showMessageDialog(null, "There is no Test to load");
				}


			}
		});
		
		
		 deleteReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TpsAndTestsNameLoader tpsAndTestNamesloder=new TpsAndTestsNameLoader();
				FolderAndFileNames[] folderAndFileNames=tpsAndTestNamesloder.getTpsAndTestNames();
				if(folderAndFileNames!=null) {
					//8
					JPanel tpsFolderPanel=new JPanel(new GridLayout(folderAndFileNames.length,1,5,5));
					ButtonGroup buttonGroup=new ButtonGroup();
					ResultPath testNumberAndTpsNamePath=new ResultPath();
					testNumberAndTpsNamePath.setPath(folderAndFileNames[0].getTpsName()+"/"+(folderAndFileNames[0].getTests().elementAt(0)));//it is a default Resultpath so that if user clicks ok w/o checking any radiobutton then resultpath still have some value so that exception can not be generated on run time
					TpsNameAndTestNamesPanel panelGenerator=new TpsNameAndTestNamesPanel(testNumberAndTpsNamePath,buttonGroup);
					for(int d=0;d<folderAndFileNames.length;d++){
						String tpsName=folderAndFileNames[d].getTpsName();
						Vector<String> testNames=folderAndFileNames[d].getTests();

						JPanel pnl=panelGenerator.getPanel(tpsName, testNames,d);
						tpsFolderPanel.add(pnl);
					}	

					JPanel innerPanel = new JPanel();
					innerPanel.setLayout(new BorderLayout());
					innerPanel.setPreferredSize(new Dimension(300,100));

					JScrollPane scrollPane=new JScrollPane(tpsFolderPanel);
					scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
					scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


					//	scrollPane.setBounds(5, 5, 100, 100);
					innerPanel.add(scrollPane, BorderLayout.CENTER);
					int result = JOptionPane.showConfirmDialog(null, innerPanel,"Delete Test Reports", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);

					if(result==JOptionPane.OK_OPTION){
						DataLoader dataloader=new DataLoader(testNumberAndTpsNamePath.getPath());// path is in form of e.g. NFBOS2/Test1
						dataloader.deleteFolderwithFiles();
						JOptionPane.showMessageDialog(null, "Deleted");
					}
											
						}
				else JOptionPane.showMessageDialog(null, "There is no Test to Delete");

						}
						});
						
		 
		jMenuFile.setMargin(new Insets(0,5,0,5));
		jMenuServer.add(jMenuServerConfiguration);
		jMenuServer.setMargin(new Insets(0,5,0,5));
		jMenuHelp.add(jMenuHelpAbout);
		jMenuHelp.setMargin(new Insets(0,5,0,5));
		// jMenuBar1.setMargin();
		jMenuBar1.add(jMenuFile);

		jMenuBar1.add(jMenuServer);
		jMenuBar1.add(jMenuHelp);
		this.setJMenuBar(jMenuBar1);
		contentPane.add(jToolBar, BorderLayout.NORTH);
	}
	private boolean dataFolderExist(){
		boolean check=false;
		try{
			File pathZ = Paths.get(MainFrame.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();//it will return   C:\t\PoolRunnerProject.jar
			File parent=pathZ.getParentFile();//it will return C:\t
			URI uri=parent.toURI();// it will return   file:/C:/t/
			String fullPath=uri+"data";
			URL pathURL=new URL(fullPath);
			File destDir = new File(pathURL.toURI());// must do it otherwise not work
			
			if(destDir.exists()) check=true;
			

		}catch(Exception e){}
		return check;

	}
	private void removePanelpermenently(String name){
		for(Component component : tuningStrategies.getComponents()) {
			// if (component.getName().equals(name) ){
			if (component instanceof Scenario.DynamicPanel ){
				component=(Scenario.DynamicPanel)component;
				if(((Scenario.DynamicPanel) component).getName2().equals(name)){
					tuningStrategies.remove(component);
					tuningStrategies.revalidate();
					tuningStrategies.repaint();
					break;

				}}
			//else

		}}
	private void removePanel(){
		for(Component component : tuningStrategies.getComponents()) {
			if (component instanceof Scenario.DynamicPanel || component instanceof TpsOnDisk.DynamicPanel ){
				tuningStrategies.remove(component);
				tuningStrategies.revalidate();
				tuningStrategies.repaint();
				break;
			}
		}}
	private Vector getStrategynames(){
		Vector v=new Vector(scenarios.size());
		Iterator theData=scenarios.iterator();
		while(theData.hasNext()){
			Scenario scenario=(Scenario) theData.next();
			v.addElement(scenario.getStrategyName());
		}
		return v;
	}
	public void addTabPanel(JPanel panel,String tabName){
		///////////////////////  scenariosPane.add(panel,tabName);
		JScrollPane scrollPane=new JScrollPane(panel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		scenariosPane.addTab(tabName,scrollPane);
		scenariosPane.revalidate();
		scenariosPane.repaint();

		int totalTabs = scenariosPane.getTabCount();
		for(int i = 0; i < totalTabs; i++)//detect whether a pane is already displyed
		{
			String name = scenariosPane.getTabTitleAt(i);
			if(name.equals(tabName)) {
				scenariosPane.setSelectedIndex(i);
				break;
			}
			//other stuff
		}
	}
	public ClosableTabbedPane getScenarioPane(){
		
		return this.scenariosPane;
	}

	private void addPanel(JPanel panel){
		tuningStrategies.add(panel);
		tuningStrategies.revalidate();
		tuningStrategies.repaint();  
	}
	/*	private void displayStringData(String[] data){////just used 4 testing and no use further

	String s="";
	for(int i=0;i<data.length;i++){

		s+=data[i]+"\n";
	}
	JPanel innerPanel = new JPanel();
	innerPanel.setLayout(new BorderLayout());
	innerPanel.setPreferredSize(new Dimension(500,500));

	JTextArea jta=new JTextArea(50,50);
	jta.append(s);
	//jta.append(testing);

	JScrollPane pane=new JScrollPane(jta);
	pane.setBounds(5, 5, 100, 100);
	innerPanel.add(pane, BorderLayout.CENTER);
	int result = JOptionPane.showConfirmDialog(null, innerPanel," JOB DATA", JOptionPane.OK_CANCEL_OPTION);


}
private void displayDoubleData(Vector<Double> v){////just used 4 testing and no use further
	Iterator it=v.iterator();
	String s="";
	while(it.hasNext()){
			double n=(double)it.next();
		//int n=(int)it.next();
		s+=n+"\n";
	}
	JPanel innerPanel = new JPanel();
	innerPanel.setLayout(new BorderLayout());
	innerPanel.setPreferredSize(new Dimension(500,500));

	JTextArea jta=new JTextArea(50,50);
	jta.append(s);
	//jta.append(testing);

	JScrollPane pane=new JScrollPane(jta);
	pane.setBounds(5, 5, 100, 100);
	innerPanel.add(pane, BorderLayout.CENTER);
	int result = JOptionPane.showConfirmDialog(null, innerPanel," JOB DATA", JOptionPane.OK_CANCEL_OPTION);


}
private void displayIntData(Vector<Integer> v){//just used 4 testing and no use further
	Iterator it=v.iterator();
	String s="";
	while(it.hasNext()){
		//	double n=(double)it.next();
		int n=(int)it.next();
		s+=n+"\n";
	}
	JPanel innerPanel = new JPanel();
	innerPanel.setLayout(new BorderLayout());
	innerPanel.setPreferredSize(new Dimension(500,500));

	JTextArea jta=new JTextArea(50,50);
	jta.append(s);
	//jta.append(testing);

	JScrollPane pane=new JScrollPane(jta);
	pane.setBounds(5, 5, 100, 100);
	innerPanel.add(pane, BorderLayout.CENTER);
	int result = JOptionPane.showConfirmDialog(null, innerPanel," JOB DATA", JOptionPane.OK_CANCEL_OPTION);


}*/
	//File | Exit action performed
	public void jMenuFileExit_actionPerformed(ActionEvent e) {
		System.exit(0);
	}
	//Help | About action performed
	public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
		MainFrame_AboutBox dlg = new MainFrame_AboutBox(this);
		Dimension dlgSize = dlg.getPreferredSize();
		Dimension frmSize = getSize();
		Point loc = getLocation();
		dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
		dlg.setModal(true);
		dlg.pack();
		dlg.show();
	}
	//Overridden so we can exit when window is closed
	@Override
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			jMenuFileExit_actionPerformed(null);
		}
	}
}
class MainFrame_jMenuFileExit_ActionAdapter implements ActionListener {
	MainFrame adaptee;

	MainFrame_jMenuFileExit_ActionAdapter(MainFrame adaptee) {
		this.adaptee = adaptee;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.jMenuFileExit_actionPerformed(e);
	}
}

class MainFrame_jMenuHelpAbout_ActionAdapter implements ActionListener {
	MainFrame adaptee;

	MainFrame_jMenuHelpAbout_ActionAdapter(MainFrame adaptee) {
		this.adaptee = adaptee;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.jMenuHelpAbout_actionPerformed(e);
	}
}