package com.jpoolrunner.clientside;
//getStaticRequestQuantity()returns staticLoad %age and  getdynamicRequestQuantity()returns dynmicLoad %age
//	uncomment //	frequencyCenterPanel.add(rdbtnManualDistibution); for manual distribution option
//uncomment //panelCentre.add(panel_6); and 		//JPanel panelCentre=new JPanel(new GridLayout(2,1)); for Load Settings option


import javax.swing.*;
import javax.swing.event.*;

import com.jpoolrunner.tpsandtestnamesloader.FolderAndFileNames;
import com.jpoolrunner.tpsandtestnamesloader.ResultPath;
import com.jpoolrunner.tpsandtestnamesloader.TpsAndTestsNameLoader;
import com.jpoolrunner.tpsandtestnamesloader.TpsNameAndTestNamesPanel;

import java.util.*;
import java.awt.*;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
// This calss also uses StaticWorkLoadSpecificationPanel and DynamicWorkLoadSpecificationPanel classes;
public class DefineScenarioNew3 extends  JPanel{// JDialog{
	String a;
	JLabel staticRequest = new JLabel("100 %");
	JLabel dynamicRequest = new JLabel("0 %");
	JCheckBox chckbxSetTo = new JCheckBox("Set to Default");
	JSlider slider = new JSlider(SwingConstants.HORIZONTAL,0,10,10);
//	private JTextField textField_1;
	boolean howmanyScenarios=false;
	boolean previousScenario=false;
	boolean usePreviousScenario=false;
	public boolean isUsePreviousScenario() { 
		return usePreviousScenario;
	}



	public void setUsePreviousScenario(boolean usePreviousScenario) {
		this.usePreviousScenario = usePreviousScenario;
	}


	boolean fixedIntensity=true;//these are default values of Dialog visible to user
	String fixIntensityValue="0.25";
	String lowerIntensity="0.25",upperIntensity="2.0";
	boolean manualLoad=true;
	boolean simulationTimeFix=true;//ie user stop it or not ie user will run simulation for the specified time
	String simulationTime="5";//5 minutes by default
	JPanel centerPanel;
	JPanel prevScenariosPanel;
	JPanel panel_3;
	JPanel panel_5;
	JRadioButton previousRadio;
	JRadioButton newRadio;
	Vector previousStrategiesnames;
//	JComboBox comboBox1,comboBox2,comboBox3,strategiesComboBox;
	String previousStrategyName;
	StaticWorkLoadSpecificationPanel staticLoad;
	DynamicWorkLoadSpecificationPanel dynamicLoad;
	JButton doneButton, doneButton2;
	JButton editButton,editButton2;
	int oneKb,tenKb,hundredKb,thousandKb,twoThousandKb;//these values are initialized when user presses DONE doneButton button ;see DONE button event
	Vector<Integer> staticLoadValues;//All above values are extracted from this vector.U can use this vector also instead of above variables 
	int tenMilliSecond,fiftyMilliSecond,hundredMilliSecond;//these values are initialized when user presses DONE doneButton2 button ;see DONE button event
	Vector<Integer> dynamicLoadValues;//All above values are extracted from this vector.U can use this vector also instead of above variables
	int nForUniform,constantDist;
	JPanel tpsFolderPanel;//=new JPanel();
	ResultPath testNumberAndTpsNamePath;//stores the clicked test# and tpsname folder path: user clicked it as previous scenario

	public String getTestNumberAndTpsNamePath() {
		return testNumberAndTpsNamePath.getPath();
	}


	double lambdaForPoisson,meanForGuassian,standardDeviationForGuassin;
	double scalePareto,shapePareto;
	public double getScalePareto() {
		return scalePareto;
	}



	public double getShapePareto() {
		return shapePareto;
	}


	int startForManualDistribution,endForManualDistribution,decreaseForManualDistribution;
	public int getStartForManualDistribution() {
		return startForManualDistribution;
	}



	public int getEndForManualDistribution() {
		return endForManualDistribution;
	}



	public int getDecreaseForManualDistribution() {
		return decreaseForManualDistribution;
	}


	String workloadPattern="";//ie Poisson Or Guassian or Uniform or Heavy tail etc whichever is selected through radiobutton this string is set to that Distribution
	JPanel eastPanel;
	
	boolean staticDone=false;// used in Final ok button to check whether user has correctly given all inputs or not
	boolean dynamicDone=true;// used in Final ok button to check whether user has correctly given all inputs or not
	boolean distributionDone=false;// used in Final ok button to check whether user has correctly given all inputs or not
	private FolderAndFileNames[] folderAndFileNames;
	private String tps;//The name of selected TPS to test it is w/o test#

	public boolean isStaticDone() {
		return staticDone;
	}



	public boolean isDynamicDone() {
		return dynamicDone;
	}



	public boolean isDistributionDone() {
		return distributionDone;
	}


	
	//public int getOneKbValue(){
	//int oneKb,tenKb,hundredKb,thousandKb,twoThousandKb;//these values are initialized when user presses DONE button ;see DONE button event
	public int getConstantDist() {
		return constantDist;
	}

	public double getStandardDeviationForGuassin() {
		return standardDeviationForGuassin;
	}
	public double getMeanForGuassian() {
		return meanForGuassian;
	}
	public double getLambdaForPoisson() {
		return lambdaForPoisson;
	}
	public int getnForUniform() {
		return nForUniform;
	}
	public String getWorkloadPattern() {
		return workloadPattern;
	}

	public Vector<Integer> getStaticLoadValues() {
		return staticLoadValues;
	}
	
	public Vector<Integer> getDynamicLoadValues() {
		return dynamicLoadValues;
	}
	
	//return staticLoad.getOneKbtextField().get
	//}
	public void sethowmanyScenarios(boolean howmanyScenarios){this.howmanyScenarios=howmanyScenarios;}
	public boolean getusePreviousScenario(){
		return this.usePreviousScenario;
		}
	//public void setStrategynames(Vector v){this.previousStrategiesnames=v;}
	public boolean getfixedIntensity(){
		return fixedIntensity;
	}
	public String getPreviousStrategyName(){
		return previousStrategyName;
	}
	public String getfixIntensityValue(){
		return fixIntensityValue;
	}
	public String getlowerIntensity(){
		return lowerIntensity;
	}
	public String getupperIntensity(){
		return upperIntensity;
	}
	public boolean getManualLoad(){
		return manualLoad;		
	}
	public boolean getsimulationTimeFix(){
		return simulationTimeFix;
	}
	public String getsimulationTime(){
		return simulationTime;
	}
	public void disablePreviousScenarioRadioButton(){
		previousRadio.setEnabled(false);
		 newRadio.setSelected(true);
		
	}
	public DefineScenarioNew3(Vector v, FolderAndFileNames[] folderAndFileNames, String tps){
	//	super();
		this.folderAndFileNames=folderAndFileNames;
		this.previousStrategiesnames=v;
		//getContentPane().setLayout(new BorderLayout());
		this.setLayout(new BorderLayout());
		JPanel mainPanel = new JPanel();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width=((int)screenSize.getWidth())-200;
		mainPanel.setPreferredSize(new Dimension(width, 500));
		this.add(mainPanel, BorderLayout.CENTER);
		this.tps=tps;
		//getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel northPanel = new JPanel();
		northPanel.setBorder(UIManager.getBorder("TitledBorder.border"));
		northPanel.setPreferredSize(new Dimension(10, 120));
		mainPanel.add(northPanel, BorderLayout.NORTH);
       

	 previousRadio = new JRadioButton("Reuse Test Plan");
		
		previousRadio.addMouseListener(new MouseAdapter() {
			//@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				if(previousRadio.isSelected())
				

				if(howmanyScenarios){//ie if there exists scenarios and mouse is cliked in previous scenario radio button then new scenario raio button components must be disabled on clicking preevious scenario button 
					JOptionPane.showMessageDialog(null, "Must select a Test of tested TPS Systems ");
					//usePreviousScenario=true;// now i made it true in TpsNameAndTestNamesPanel class.
					for(Component component : getComponents(centerPanel)) 
				    component.setEnabled(false);
					
					for(Component component : getComponents(prevScenariosPanel)) 
					    if(component instanceof JRadioButton && !component.isEnabled())component.setEnabled(true);
				    
				}
				else {// if no previous scenarios exists then previous radio button should be disabled
					usePreviousScenario=false;//
					for(Component component : getComponents(prevScenariosPanel)) 
					    component.setEnabled(false);
					disablePreviousScenarioRadioButton();
					
					
				}
			}
		});
	
		
		previousRadio.setBorder(null);
		//previousRadio.addItemListener(handler);
		
		tpsFolderPanel=new JPanel();//(new GridLayout(folderAndFileNames.length,1,5,5));
		ButtonGroup buttonGroup=new ButtonGroup();
		testNumberAndTpsNamePath=new ResultPath();
		TpsNameAndTestNamesPanel panelGenerator=new TpsNameAndTestNamesPanel(testNumberAndTpsNamePath,buttonGroup,DefineScenarioNew3.this,tps);
		if(folderAndFileNames!=null){
			tpsFolderPanel.setLayout(new GridLayout(folderAndFileNames.length,1,5,5));	
			
		for(int d=0;d<folderAndFileNames.length;d++){
			String tpsName=folderAndFileNames[d].getTpsName();
			Vector<String> testNames=folderAndFileNames[d].getTests();
			
			JPanel pnl=panelGenerator.getPanel(tpsName, testNames,d);
			if (pnl.isVisible())tpsFolderPanel.add(pnl);// same TPS cnt be tested so i kept it invisible in TpsNameAndTestNamesPanel line 85 if Same tps 
			
			
			
		}}
		JScrollPane tpsFolderScroller=new JScrollPane(tpsFolderPanel);
		tpsFolderScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		tpsFolderScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	//	tpsFolderScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		//JPanel prevScenariosPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
		prevScenariosPanel=new JPanel(new BorderLayout());
		prevScenariosPanel.setBorder(null);

		//prevScenariosPanel.add(previousRadio);
		prevScenariosPanel.add(previousRadio,BorderLayout.WEST);

	
		northPanel.setLayout(new BorderLayout(0, 0));
		//prevScenariosPanel.add(strategiesComboBox);
		prevScenariosPanel.add(tpsFolderScroller,BorderLayout.CENTER);

		northPanel.add(prevScenariosPanel,BorderLayout.CENTER);
		
	 newRadio = new JRadioButton("New Test Plan\r\n");
		newRadio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				usePreviousScenario=false;
				for(Component component : getComponents(centerPanel)) {
				    component.setEnabled(true);
				}
				for(Component component : getComponents(tpsFolderPanel)) {
				    component.setEnabled(false);
				}
				
			}
		});
		
		
		newRadio.setBorder(null);
		newRadio.setSelected(true);
		//newRadio.addItemListener(handler);
		JPanel dummyPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
		dummyPanel.add(newRadio);
		northPanel.add(dummyPanel,BorderLayout.SOUTH);
		ButtonGroup group = new ButtonGroup();
		group.add(newRadio);
		group.add(previousRadio);
		
		//prevScenariosPanel.add(list);
		
		centerPanel = new JPanel();
		centerPanel.setPreferredSize(new Dimension(10, 600));
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));
	//	JPanel panelCentre=new JPanel(new GridLayout(2,1));
		staticLoad=new StaticWorkLoadSpecificationPanel();
		staticLoad.setPreferredSize(new Dimension(388, 150));
		dynamicLoad=new DynamicWorkLoadSpecificationPanel();
		dynamicLoad.setPreferredSize(new Dimension(388, 150));
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new TitledBorder(null, "Workload Charactorization", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		panel_7.setLayout(new BorderLayout(0, 0));
		centerPanel.add(panel_7,BorderLayout.NORTH);
		JPanel panel_12 = new JPanel();
		panel_12.setPreferredSize(new Dimension(10, 25));
		panel_7.add(panel_12,BorderLayout.NORTH);
		chckbxSetTo.setSelected(true);
		chckbxSetTo.setBorder(BorderFactory.createEtchedBorder());
		chckbxSetTo.setBorderPainted(true);
		//	JCheckBox chckbxSetTo = new JCheckBox("Set to Default");
			slider.setEnabled(false);
			//	slider.setMinorTickSpacing(10);
				panel_12.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				panel_12.add(chckbxSetTo);
				chckbxSetTo.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent e){
					//JCheckBox cbLog = (JCheckBox) e.getSource();
	        if (chckbxSetTo.isSelected()) {
	        	//JButton doneButton;
	        	//JButton editButton;
	        	staticDone=dynamicDone=true;
	        	doneButton.setEnabled(true);
	        	editButton.setEnabled(false);
	        	 slider.setValue(10) ;
	        	 staticRequest.setText("100 %");
	        	 dynamicRequest.setText("0 %");
	        	 slider.setEnabled(false);
	        	 setDefaultChecked(staticLoad);//disable all components of StaticWorkLoadSpecificationPanel
	        	 
	        } 
	        else 
	        	{
	        	staticDone=false;
	        	slider.setEnabled(true);
	        	setDefaultUnChecked(staticLoad);//enable all components of StaticWorkLoadSpecificationPanel

	        	}
				}});
				
				JLabel lblSaticRequests = new JLabel("Satic Requests :");
				panel_12.add(lblSaticRequests);
				
				//JLabel staticRequest = new JLabel("50 %");
				panel_12.add(staticRequest);
				
				
				slider.setValue(100);
				panel_12.add(slider);
				slider.addChangeListener(new sliderListener());
				
				JLabel lblDynamicRequests = new JLabel("Dynamic Requests :");
				panel_12.add(lblDynamicRequests);
				
	//	JLabel dynamicRequest = new JLabel("50 %");
				panel_12.add(dynamicRequest);
				
				JPanel panel_13 = new JPanel();
				panel_7.add(panel_13, BorderLayout.CENTER);
				panel_13.setLayout(new GridLayout(1, 2, 0, 0));
				
				
				//staticLoad;
				
	//	dynamicLoad.setLabel("Processing Time(ms):");
				JPanel panel_14 = new JPanel();
				panel_14.setBorder(new TitledBorder(null, "Static Workload Specification", TitledBorder.CENTER, TitledBorder.TOP, null, null));
				panel_14.setBackground(new Color(240, 240, 240));
				panel_13.add(panel_14);
				panel_14.setLayout(new BorderLayout(0, 0));
				JPanel buttonPanel1=new JPanel();
				buttonPanel1.setPreferredSize(new Dimension(10, 20));
				buttonPanel1.setBackground(new Color(240, 240, 240));
				
				panel_14.add(buttonPanel1,BorderLayout.SOUTH);
				buttonPanel1.setLayout(null);
				
				 doneButton = new JButton("DONE");
				 doneButton.addActionListener(new ActionListener() {
				 	public void actionPerformed(ActionEvent e) {
				 		staticLoadValues=(Vector)staticLoad.getTextFieldValues();
				 		//int oneKb,tenKb,hundredKb,thousandKb,twoThousandKb;
				 		oneKb=staticLoadValues.get(0);
				 		tenKb=staticLoadValues.get(1);
				 		hundredKb=staticLoadValues.get(2);
				 		thousandKb=staticLoadValues.get(3);
				 		twoThousandKb=staticLoadValues.get(4);
				 		if((oneKb+tenKb+hundredKb+thousandKb+twoThousandKb)!=100){
				 			//JOptionPane.showMessageDialog(null,"onekb="+oneKb+" 10kb="+tenKb+" 100kb="+hundredKb+" 1000kb="+thousandKb+" 2000kb="+twoThousandKb);
				 			JOptionPane.showMessageDialog(null,"Please keep your Static Load 100%");
				 		staticDone=false;
				 		}
				 		else{//disable components of StaticWorkloadspecicationPanel
				 			JOptionPane.showMessageDialog(null,"Static WorkloadLoad is prepared! ");
					 		staticDone=true;;

				 			Component[] components = getComponents(staticLoad);
				 	        for(Component com : components) {
				 	          //  if(com.isEnabled()) {
				 	            	if(!(com instanceof JTextField || com instanceof JLabel ) &&  !(com instanceof JScrollBar) && !(com instanceof JSlider))// so that labels textfields and scrollbar remain enable to increase visibiity
				 	                com.setEnabled(false);
				 	            	
				 	         //   } 
				 	        }
				 	        if(!chckbxSetTo.isSelected()){
				 	        editButton.setEnabled(true);
				 			doneButton.setEnabled(false);
				 	        }}
				 		
				 	}
				 });
				 doneButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
				 doneButton.setBounds(125, 1, 65, 18);
				 buttonPanel1.add(doneButton);
				 
				 editButton = new JButton("EDIT");
				 editButton.addActionListener(new ActionListener() {//First enable components of StaticWorkloadSpecificationPanel
				 	public void actionPerformed(ActionEvent e) {
				 		staticDone=false;
				 		Component[] components = getComponents(staticLoad);
				         for(Component com : components) {
				             if((!com.isEnabled())&& !( com instanceof JSlider)) {
				                com.setEnabled(true);
				              }
				         }
				         editButton.setEnabled(false);
				         doneButton.setEnabled(true);
				 	}
				 
				 });
				 editButton.setEnabled(false);
				 editButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
				 editButton.setBounds(215, 1, 65, 18);
				 buttonPanel1.add(editButton);
				 
				 panel_14.add(staticLoad,BorderLayout.CENTER);
				 
				 JPanel panel_15 = new JPanel();
				 panel_15.setBorder(new TitledBorder(null, "Dynamic Workload Specification", TitledBorder.CENTER, TitledBorder.TOP, null, null));
				 panel_15.setBackground(new Color(240, 240, 240));
				 panel_13.add(panel_15);
				 panel_15.setLayout(new BorderLayout(0, 0));
				 
				 JPanel buttonPanel2=new JPanel();
				 buttonPanel2.setPreferredSize(new Dimension(10, 20));
				 buttonPanel2.setBackground(new Color(240, 240, 240));
				 
				 panel_15.add(buttonPanel2,BorderLayout.SOUTH);
				 buttonPanel2.setLayout(null);
				 
				 doneButton2 = new JButton("DONE");
				 doneButton2.setEnabled(false);
				 doneButton2.addActionListener(new ActionListener() {
				 	public void actionPerformed(ActionEvent e) {
				 		dynamicLoadValues=(Vector)dynamicLoad.getTextFieldValues();
				 	//	int tenMilliSecond,fiftyMilliSecond,hundredMilliSecond;
				 		tenMilliSecond=dynamicLoadValues.get(0);
				 		fiftyMilliSecond=dynamicLoadValues.get(1);
				 		hundredMilliSecond=dynamicLoadValues.get(2);
				 		
				 		if((tenMilliSecond+fiftyMilliSecond+hundredMilliSecond)!=100){
				 			//JOptionPane.showMessageDialog(null,"onekb="+oneKb+" 10kb="+tenKb+" 100kb="+hundredKb+" 1000kb="+thousandKb+" 2000kb="+twoThousandKb);
				 			JOptionPane.showMessageDialog(null,"Please keep your Dynamic Load 100%");
				 		dynamicDone=false;
				 		}
				 		else{//disable components of StaticWorkloadspecicationPanel
				 			JOptionPane.showMessageDialog(null,"Dynamic WorkloadLoad is prepared! ");
				 		dynamicDone=true;

				 			Component[] components = getComponents(dynamicLoad);
				 	        for(Component com : components) {
				 	          //  if(com.isEnabled()) {
				 	            	if(!(com instanceof JTextField || com instanceof JLabel ) &&  !(com instanceof JScrollBar) && !(com instanceof JSlider))// so that labels textfields and scrollbar remain enable to increase visibiity
				 	                com.setEnabled(false);
				 	            	
				 	         //   } 
				 	        }
				 	      //  if(!chckbxSetTo.isSelected()){
				 	        editButton2.setEnabled(true);
				 			doneButton2.setEnabled(false);
				 	        }}
				 		
				 	
				 });
				 doneButton2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
				 doneButton2.setBounds(125, 1, 65, 18);
				 buttonPanel2.add(doneButton2);
				 
				 editButton2 = new JButton("EDIT");
				 editButton2.setEnabled(false);
				 editButton2.addActionListener(new ActionListener() {
				 	public void actionPerformed(ActionEvent e) {
				 		dynamicDone=false;

				 		Component[] components = getComponents(dynamicLoad);
				         for(Component com : components) {
				             if((!com.isEnabled())&& !( com instanceof JSlider)) {
				                com.setEnabled(true);
				              }
				         }
				         editButton2.setEnabled(false);
				         doneButton2.setEnabled(true);
				 	}
				 });
				 editButton2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
				 editButton2.setBounds(215, 1, 65, 18);
				 buttonPanel2.add(editButton2);
				 
				 panel_15.add(dynamicLoad,BorderLayout.CENTER);
				 setDefaultChecked(staticLoad);	//When this dialog displays first time its setDefult option is checked which means that All the components of StaticWorkloadSpecificationPanel should be disabled and initialized with their default values e.g. 1kb file is checked with 30% load etc
		
		ButtonGroup group33 = new ButtonGroup();
		JPanel IoIntensityPanel = new JPanel();
		IoIntensityPanel.setBorder(new TitledBorder(null, "Task's I/O Intensity Level", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		//centerPanel.add(IoIntensityPanel);
		IoIntensityPanel.setLayout(new GridLayout(2, 1, 0, 5));
		
		JPanel panel_1 = new JPanel();
		IoIntensityPanel.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		panel_1.add(panel_2);
		
		JRadioButton fixedRadioButton = new JRadioButton("Fixed for all Jobs    ");
		fixedRadioButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fixedIntensity=true;
				for(Component component : getComponents(panel_3)) {
				    component.setEnabled(true);
				}
				for(Component component : getComponents(panel_5)) {
				    component.setEnabled(false);
				}
				
			}
		});
		fixedRadioButton.setSelected(true);
		panel_2.add(fixedRadioButton);
		
		panel_3 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_3.getLayout();
		flowLayout_3.setAlignment(FlowLayout.LEFT);
		panel_1.add(panel_3);
		String intensity[]={"0.25sec","0.5sec","1.0sec","1.5sec","2.0sec"};
		String intensity2[]={"0.25sec","0.5sec","1.0sec","1.5sec"};
		String intensity3[]={"0.5sec","1.0sec","1.5sec","2.0sec"};
		JLabel lblIoIntensity = new JLabel("I/O Intensity");
		panel_3.add(lblIoIntensity);
		JPanel panel = new JPanel();
		IoIntensityPanel.add(panel);
		panel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_4.getLayout();
		panel.add(panel_4);
		
		JRadioButton randomRadioButton = new JRadioButton("Random for all jobs\r\n\r\n");
		randomRadioButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fixedIntensity=false;//lower and upper values are initialized by default.
				for(Component component : getComponents(panel_3)) {
				    component.setEnabled(false);
				}
				for(Component component : getComponents(panel_5)) {
				    component.setEnabled(true);
				}
				
			}
		});
		panel_4.add(randomRadioButton);
		ButtonGroup group2 = new ButtonGroup();
		group2.add(fixedRadioButton);
		group2.add(randomRadioButton);
		panel_5 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_5.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		panel.add(panel_5);
		JLabel lblIoIntensityBetween = new JLabel("I/O Intensity b/w");
		lblIoIntensityBetween.setEnabled(false);
		panel_5.add(lblIoIntensityBetween);
		JLabel lblAnd = new JLabel("AND");
		lblAnd.setEnabled(false);
		panel_5.add(lblAnd);	
		JPanel panel_16 = new JPanel();//It sets workload arrival pattern on the server i.e. Request frequency distribution
		panel_16.setPreferredSize(new Dimension(10, 50));
		panel_16.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Load Generation Pattern Distribution", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		JPanel panelCentre=new JPanel(new GridLayout(1,1));
		//JPanel panelCentre=new JPanel(new GridLayout(2,1));

		//panelCentre.setPreferredSize(new Dimension(500, 500));
		panelCentre.add(panel_16);
		
		//centerPanel.add(panel_16);
		panel_16.setLayout(new BorderLayout(0, 0));
		
		JPanel frequencyCenterPanel = new JPanel();
		panel_16.add(frequencyCenterPanel, BorderLayout.CENTER);
		frequencyCenterPanel.setLayout(new GridLayout(1, 5, 0, 0));
		//frequencyCenterPanel.setPreferredSize(new Dimension(200, 100));
		
		JRadioButton rdbtnPoissonDistribution = new JRadioButton("Poisson");
		rdbtnPoissonDistribution.setToolTipText("Poisson Distribution");
		rdbtnPoissonDistribution.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				String input=JOptionPane.showInputDialog("Enter Average Requests Per Second( "+"\u03BB"+")");
				lambdaForPoisson=Double.parseDouble(input);
				if(lambdaForPoisson>1000){ 
					JOptionPane.showMessageDialog(null,"You can not exceed more than 1000 Plz try again!");
					lambdaForPoisson=0;
					return;
					}
				if(lambdaForPoisson<=0){ 
					JOptionPane.showMessageDialog(null,"Number should be greater than 0. Plz try again!");
					lambdaForPoisson=0;
					return;
					}
				
				
				workloadPattern="Poisson";
				distributionDone=true;//
				
				
				//now following lines prepare a panel to be displayed on eastpanel at run time
				JPanel panel=new JPanel(new BorderLayout());
				JLabel label2=new JLabel("\u03BB"+"= "+lambdaForPoisson,JLabel.CENTER);
				label2.setFont(new Font(Font.SANS_SERIF, Font.HANGING_BASELINE, 12));
				panel.add(label2,BorderLayout.CENTER);
				JScrollPane jsp=new JScrollPane(panel);
				eastPanel.removeAll();
				eastPanel.setBorder(new TitledBorder(null, "Poisson Distribution", TitledBorder.LEFT, TitledBorder.TOP, null, null));
				eastPanel.add(jsp,BorderLayout.CENTER);
				eastPanel.revalidate();
				eastPanel.repaint();
				}catch(NumberFormatException nfe){
					JOptionPane.showMessageDialog(null,"Number Format Not Correct!");
					distributionDone=false;//

				}catch(NullPointerException npe){
					//JOptionPane.showMessageDialog(null,"You canceled entry so NullPointerException  Double.parseDouble(null) !");
					distributionDone=false;
				}
				
							
			}
		});
		frequencyCenterPanel.add(rdbtnPoissonDistribution);
		
		JRadioButton rdbtnGuassianDistribution = new JRadioButton("Guassian");
		rdbtnGuassianDistribution.setToolTipText("Guassian Distribution");
		rdbtnGuassianDistribution.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 int nForUniform;
				double lambdaForPoisson,meanForGuassian,standardDeviationForGuassin;
				 */
			//	JOptionPane.showMessageDialog(null,"Guassian");
				JPanel mainPanel=new JPanel(new GridLayout(2,1));
				
				JPanel meanPanel=new JPanel(new FlowLayout());
				JLabel meanLabel=new JLabel("Enter mean(Average requests Per Second):");
				JTextField meanField=new JTextField(5);
				meanPanel.add(meanLabel);
				meanPanel.add(Box.createHorizontalStrut(15));
				meanPanel.add(meanField);
				
				JPanel stdPanel=new JPanel(new FlowLayout());
				JLabel stdLabel=new JLabel("Enter standard deviation:");
				JTextField stdField=new JTextField(5);
				stdPanel.add(stdLabel);
				stdPanel.add(Box.createHorizontalStrut(15));
				stdPanel.add(stdField);
				
				mainPanel.add(meanPanel);
				mainPanel.add(stdPanel);
				int result = JOptionPane.showConfirmDialog(null, mainPanel, 
			               "Please Enter values", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
			         try{
			        	 meanForGuassian=Double.parseDouble(meanField.getText());
			        	 standardDeviationForGuassin=Double.parseDouble(stdField.getText());
			        	 if(meanForGuassian>1000){ 
								JOptionPane.showMessageDialog(null,"You can not exceed more than 1000 Plz try again!");
								meanForGuassian=0;
								return;
								}
							if(meanForGuassian<=0){ 
								JOptionPane.showMessageDialog(null,"Mean should be greater than 0. Plz try again!");
								meanForGuassian=0;
								return;
								}
							 if(standardDeviationForGuassin>1000){ 
									JOptionPane.showMessageDialog(null,"You can not exceed more than 1000 Plz try again!");
									standardDeviationForGuassin=0;
									return;
									}
								if(standardDeviationForGuassin<=0){ 
									JOptionPane.showMessageDialog(null,"standardDeviationForGuassin should be greater than 0. Plz try again!");
									standardDeviationForGuassin=0;
									return;
									}
							
			        	 
			        	 workloadPattern="Guassian";
			        	 distributionDone=true;//

			        	//now following lines prepare a panel to be displayed on eastpanel at run time
							JPanel panel=new JPanel(new GridLayout(2,1));
							JLabel label2=new JLabel("Mean ="+meanForGuassian,JLabel.CENTER);
							JLabel label3=new JLabel("S.D ="+standardDeviationForGuassin,JLabel.CENTER);

							label2.setFont(new Font(Font.SANS_SERIF, Font.HANGING_BASELINE, 12));
							label3.setFont(new Font(Font.SANS_SERIF, Font.HANGING_BASELINE, 12));

							panel.add(label2);
							panel.add(label3);
							JScrollPane jsp=new JScrollPane(panel);

							eastPanel.removeAll();
							eastPanel.setBorder(new TitledBorder(null, "Guassian Distribution", TitledBorder.LEFT, TitledBorder.TOP, null, null));
							eastPanel.add(jsp,BorderLayout.CENTER);
							eastPanel.revalidate();
							eastPanel.repaint();
			        	 
			        	 
			        	 }catch(NumberFormatException nfe){
								JOptionPane.showMessageDialog(null,"Numbers Format Not Correct!");
								meanForGuassian=standardDeviationForGuassin=0;
								distributionDone=false;//


			        	 			}
			      }

			}
		});
		frequencyCenterPanel.add(rdbtnGuassianDistribution);
		
		JRadioButton rdbtnUniformDistribution = new JRadioButton("Uniform");
		rdbtnUniformDistribution.setToolTipText("Uniform Distribution");
		rdbtnUniformDistribution.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String input=JOptionPane.showInputDialog("Enter a Positive integer for Average Requests Per Second");
					nForUniform=Integer.parseInt(input);
					boolean check=true;//ie user did not enter 0 or -ve number
					if (nForUniform <= 0) {
						JOptionPane.showMessageDialog(null,"Number should be greater than 0");
						nForUniform=0;
						check=false;
						distributionDone=false;//
						return;

						}
					if (nForUniform >1000) {
						JOptionPane.showMessageDialog(null,"Number should not exceed 1000");
						nForUniform=0;
						check=false;
						distributionDone=false;//
						return;

						}
					
					
					//now following lines prepare a panel to be displayed on eastpanel at run time
					if(check==true){
						workloadPattern="Uniform";
						distributionDone=true;//
						JPanel panel=new JPanel(new BorderLayout());
						JLabel label2=new JLabel("Positive Integer="+nForUniform,JLabel.CENTER);
						label2.setFont(new Font(Font.SANS_SERIF, Font.HANGING_BASELINE, 12));
						panel.add(label2,BorderLayout.CENTER);
						JScrollPane jsp=new JScrollPane(panel);

						eastPanel.removeAll();
						eastPanel.setBorder(new TitledBorder(null, "Uniform Distribution", TitledBorder.LEFT, TitledBorder.TOP, null, null));
						eastPanel.add(jsp,BorderLayout.CENTER);
						eastPanel.revalidate();
						eastPanel.repaint();
					}
					}catch(NumberFormatException nfe){
						JOptionPane.showMessageDialog(null,"Number Format Not Correct!");
						distributionDone=false;//
						}

			}
		});
		frequencyCenterPanel.add(rdbtnUniformDistribution);
		/////////
		JRadioButton rdbtnManualDistibution = new JRadioButton("Manual");
		rdbtnManualDistibution.setToolTipText("Manual Distribution");
		rdbtnManualDistibution.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel mainPanel=new JPanel(new GridLayout(3,1));
				
				JPanel startPanel=new JPanel(new FlowLayout());
				JLabel startLabel=new JLabel("Enter start Value:");
				JTextField startField=new JTextField(5);
				startPanel.add(startLabel);
				startPanel.add(Box.createHorizontalStrut(15));
				startPanel.add(startField);
				
				JPanel endPanel=new JPanel(new FlowLayout());
				JLabel endLabel=new JLabel("Enter End Value:");
				JTextField endField=new JTextField(5);
				endPanel.add(endLabel);
				endPanel.add(Box.createHorizontalStrut(15));
				endPanel.add(endField);
				
				JPanel decreasePanel=new JPanel(new FlowLayout());
				JLabel decreaseLabel=new JLabel("Enter decrease value:");
				JTextField decreaseField=new JTextField(5);
				decreasePanel.add(decreaseLabel);
				decreasePanel.add(Box.createHorizontalStrut(15));
				decreasePanel.add(decreaseField);
				
				mainPanel.add(startPanel);
				mainPanel.add(endPanel);
				mainPanel.add(decreasePanel);

				int result = JOptionPane.showConfirmDialog(null, mainPanel, 
			               "Enter values", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
			
					try{
						startForManualDistribution=Integer.parseInt(startField.getText());
						
						endForManualDistribution=Integer.parseInt(endField.getText());
						decreaseForManualDistribution=Integer.parseInt(decreaseField.getText());
			        	 workloadPattern="Manual";
			        	 distributionDone=true;//

			        	//now following lines prepare a panel to be displayed on eastpanel at run time
							JPanel panel=new JPanel(new GridLayout(3,1));
							JLabel label1=new JLabel("Start ="+startForManualDistribution,JLabel.CENTER);
							JLabel label2=new JLabel("End ="+endForManualDistribution,JLabel.CENTER);
							JLabel label3=new JLabel("Decrease ="+decreaseForManualDistribution,JLabel.CENTER);


							label1.setFont(new Font(Font.SANS_SERIF, Font.HANGING_BASELINE, 12));
							label2.setFont(new Font(Font.SANS_SERIF, Font.HANGING_BASELINE, 12));
							label3.setFont(new Font(Font.SANS_SERIF, Font.HANGING_BASELINE, 12));
							
							panel.add(label1);
							panel.add(label2);
							panel.add(label3);

							eastPanel.removeAll();
							eastPanel.setBorder(new TitledBorder(null, "Manual Distribution", TitledBorder.LEFT, TitledBorder.TOP, null, null));
							eastPanel.add(panel,BorderLayout.CENTER);
							eastPanel.revalidate();
							eastPanel.repaint();
			        	 
			        	 
			        	 }catch(NumberFormatException nfe){
								JOptionPane.showMessageDialog(null,"Numbers Format Not Correct!");
								startForManualDistribution=endForManualDistribution=decreaseForManualDistribution=0;
								distributionDone=false;//


			        	 			}

			}}
		});
	//	frequencyCenterPanel.add(rdbtnManualDistibution);
		///////
		
		JRadioButton rdbtnHeavyTail = new JRadioButton("Heavy-Tail");
		rdbtnHeavyTail.setToolTipText("Heavy Tail");
		rdbtnHeavyTail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 int nForUniform;
				double lambdaForPoisson,meanForGuassian,standardDeviationForGuassin;
				 */
				//do coding and checking and then following 1 line also check for setOkButtonEnable();
;

				workloadPattern="Heavy";
			//	JOptionPane.showMessageDialog(null,"Heavy Tail");//just for checking
				JPanel mainPanel=new JPanel(new GridLayout(2,1));
				
				JPanel meanPanel=new JPanel(new FlowLayout());
				JLabel meanLabel=new JLabel("Enter scale parameter for Pareto Distribution:");
				JTextField meanField=new JTextField(5);
				meanPanel.add(meanLabel);
				meanPanel.add(Box.createHorizontalStrut(15));
				meanPanel.add(meanField);
				
				JPanel stdPanel=new JPanel(new FlowLayout());
				JLabel stdLabel=new JLabel("Shape parameter=3");
			//	JTextField stdField=new JTextField(5);
				stdPanel.add(stdLabel);
				stdPanel.add(Box.createHorizontalStrut(15));
			//	stdPanel.add(stdField);
				
				mainPanel.add(meanPanel);
				mainPanel.add(stdPanel);
				int result = JOptionPane.showConfirmDialog(null, mainPanel, 
			               "Please Enter values", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
			         try{
			        	 scalePareto=Double.parseDouble(meanField.getText());
			        	 if(scalePareto>1000){ 
								JOptionPane.showMessageDialog(null,"You can not exceed more than 1000 Plz try again!");
								scalePareto=0;
								return;
								}
			        	 if(scalePareto<=0){ 
								JOptionPane.showMessageDialog(null,"Number should be greater than 0. Plz try again!");
								scalePareto=0;
								return;
								}
			        	// shapePareto=Double.parseDouble(stdField.getText());
			        	 shapePareto=3;
			        	 workloadPattern="Heavy";
			        	 distributionDone=true;//

			        	//now following lines prepare a panel to be displayed on eastpanel at run time
							JPanel panel=new JPanel(new GridLayout(2,1));
							JLabel label2=new JLabel("Scale ="+scalePareto,JLabel.CENTER);
							JLabel label3=new JLabel("Shape ="+shapePareto,JLabel.CENTER);

							label2.setFont(new Font(Font.SANS_SERIF, Font.HANGING_BASELINE, 12));
							label3.setFont(new Font(Font.SANS_SERIF, Font.HANGING_BASELINE, 12));

							panel.add(label2);
							panel.add(label3);
							JScrollPane jsp=new JScrollPane(panel);


							eastPanel.removeAll();
							eastPanel.setBorder(new TitledBorder(null, "Pareto Distribution", TitledBorder.LEFT, TitledBorder.TOP, null, null));
							eastPanel.add(jsp,BorderLayout.CENTER);
							eastPanel.revalidate();
							eastPanel.repaint();
			        	 
			        	 
			        	 }catch(NumberFormatException nfe){
								JOptionPane.showMessageDialog(null,"Numbers Format Not Correct!");
								scalePareto=shapePareto=0;
								distributionDone=false;//


			        	 			}
			      }

			}
		});
		group33.add(rdbtnPoissonDistribution);
		group33.add(rdbtnGuassianDistribution);
		group33.add(rdbtnUniformDistribution);
		group33.add(rdbtnManualDistibution);

		group33.add(rdbtnHeavyTail);
		
		frequencyCenterPanel.add(rdbtnHeavyTail);
		
		JRadioButton rdbtnConstantDistr = new JRadioButton("Constant");
		rdbtnConstantDistr.setToolTipText("Constant Distribution");
		rdbtnConstantDistr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String input=JOptionPane.showInputDialog("Enter Requests Per Second:");
					constantDist=Integer.parseInt(input);
					
					if (constantDist <= 0) {
						JOptionPane.showMessageDialog(null,"Number should be greater than 0");
						constantDist=0;
						//check=false;
						distributionDone=false;//
						return;

						}
					if (constantDist >1000) {
						JOptionPane.showMessageDialog(null,"Number should not exceed 1000");
						constantDist=0;
						//check=false;
						distributionDone=false;//
						return;

						}
					
					workloadPattern="Constant";
					distributionDone=true;//
					//now following lines prepare a panel to be displayed on eastpanel at run time
					JPanel panel=new JPanel(new BorderLayout());
					JLabel label2=new JLabel("Constant= "+constantDist,JLabel.CENTER);
					label2.setFont(new Font(Font.SANS_SERIF, Font.HANGING_BASELINE, 12));
					panel.add(label2,BorderLayout.CENTER);
					JScrollPane jsp=new JScrollPane(panel);

					eastPanel.removeAll();
					eastPanel.setBorder(new TitledBorder(null, "Constant Distribution", TitledBorder.LEFT, TitledBorder.TOP, null, null));
					eastPanel.add(jsp,BorderLayout.CENTER);
					eastPanel.revalidate();
					eastPanel.repaint();
					}catch(NumberFormatException nfe){
						JOptionPane.showMessageDialog(null,"Number Format Not Correct!");
						distributionDone=false;//
						}
				
			}
		});
		frequencyCenterPanel.add(rdbtnConstantDistr);
		
	    eastPanel = new JPanel();
	    eastPanel.setBorder(new TitledBorder(null, "Workload Pattern", TitledBorder.LEFT, TitledBorder.TOP, null, null));
	    eastPanel.setPreferredSize(new Dimension(150, 10));
	    eastPanel.setBackground(new Color(240,240,240));
	    panel_16.add(eastPanel, BorderLayout.EAST);
	    eastPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_6 = new JPanel();
		panel_6.setPreferredSize(new Dimension(10, 50));
		panel_6.setBorder(new TitledBorder(null, "Load Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		//panelCentre.add(panel_6);
		centerPanel.add(panelCentre,BorderLayout.CENTER);
		//centerPanel.add(panel_6);
		panel_6.setLayout(new GridLayout(1, 2, 0, 0));
		ButtonGroup group3 = new ButtonGroup();
		

		String time[]={"1 minute","2 minutes","3 minutes","4 minutes","5 minutes"};
		ButtonGroup group4 = new ButtonGroup();
		this.setVisible(true);
	}


private class PoissonSummary extends JPanel{
	
}
public Component[] getComponents(Component container) {
        ArrayList<Component> list = null;

        try {
            list = new ArrayList<Component>(Arrays.asList(
                  ((Container) container).getComponents()));
            for (int index = 0; index < list.size(); index++) {
            for (Component currentComponent : getComponents(list.get(index))) {
                list.add(currentComponent);
            }
            }
        } catch (ClassCastException e) {
            list = new ArrayList<Component>();
        }

        return list.toArray(new Component[list.size()]);
        }
    public void setpreviousScenario(boolean t){
    	this.previousScenario=t;
    }
	public static void main(String ss[]){
		TpsAndTestsNameLoader tpsAndTestNamesloder=new TpsAndTestsNameLoader();
		FolderAndFileNames[] folderAndFileNames=tpsAndTestNamesloder.getTpsAndTestNames();
		
		DefineScenarioNew3 myPanel=new DefineScenarioNew3(new Vector(),folderAndFileNames,"");
		JFrame frame =new JFrame();
		frame.getContentPane().add(myPanel);
		frame.pack();
		frame.show(true);
		             
		            
	}
	// returns %age of static requests 
	public int getStaticRequestQuantity(){
		String staticValue=removePercentSymbol(staticRequest.getText());
		return Integer.parseInt(staticValue);
	}
	// returns %age of dynamic requests 
	public int getdynamicRequestQuantity(){
		String dynamicValue=removePercentSymbol(dynamicRequest.getText());
		return Integer.parseInt(dynamicValue);

	}

	public int getOneKb() {
		return oneKb;
	}
	
	
	public int getTenMilliSecond() {
		return tenMilliSecond;
	}
	public int getFiftyMilliSecond() {
		return fiftyMilliSecond;
	}
	public int getHundredMilliSecond() {
		return hundredMilliSecond;
	}
	public int getTenKb() {
		return tenKb;
	}
	public int getHundredKb() {
		return hundredKb;
	}
	public int getThousandKb() {
		return thousandKb;
	}
	public int getTwoThousandKb() {
		return twoThousandKb;
	}
	public String removePercentSymbol(String str) {
	    if (str.length() > 0) {
	      str = str.substring(0, str.length()-2);
	    }
	    return str;
	}
public class sliderListener implements ChangeListener{
boolean checkStaticDisable=false;
boolean checkDynamicDisable=false;

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		JSlider slider=(JSlider)e.getSource();
		staticRequest.setText(String.valueOf(slider.getValue()*10)+" %");
		dynamicRequest.setText(String.valueOf(100-(slider.getValue()*10))+" %");
	String staticValue=removePercentSymbol(staticRequest.getText());
	String dynamicValue=removePercentSymbol(dynamicRequest.getText());
	if(Integer.parseInt(staticValue)==0){
		staticDone=true;
		disableComponents(staticLoad);
		doneButton.setEnabled(false);
		editButton.setEnabled(false);
		checkStaticDisable=true;
		//System.out.println("0");
	}
	else if(Integer.parseInt(staticValue)>0&& checkStaticDisable==true){
		staticDone=false;

		enableComponents(staticLoad);
		doneButton.setEnabled(true);
		checkStaticDisable=false;
		//System.out.println("0");
	}
	if(Integer.parseInt(dynamicValue)==0){
		dynamicDone=true;
		disableComponents(dynamicLoad);
		doneButton2.setEnabled(false);
		editButton2.setEnabled(false);
		checkDynamicDisable=true;
		//System.out.println("0");
	}
	else if(Integer.parseInt(dynamicValue)>0&& checkDynamicDisable==true){
		dynamicDone=false;

		enableComponents(dynamicLoad);
		doneButton2.setEnabled(true);
		checkDynamicDisable=false;
		//System.out.println("0");
	}
	}
	
	//public void stateChanged(changeEvent e){}
}
// extract all the components of StaticWorkloadSpecification or DynamicWorkloadSpecifition classes and disable them because there workload is 0%

private void disableComponents(Container container){
	Component[] components = getComponents(container);
    for(Component com : components) {
        if(com.isEnabled()) {
        	if(com instanceof JCheckBox){ ((JCheckBox) com).setSelected(false);}
            com.setEnabled(false);
        } 
    }
	
	
}
// extract all the components of StaticWorkloadSpecification or DynamicWorkloadSpecifition classes and enable them because there workload is > 0% now

private void enableComponents(Container container){
	// extract all the components of StaticWorkloadSpecification or DynamicWorkloadSpecifition classes and enable them because there workload is > 0% now
	Component[] components = getComponents(container);
    for(Component com : components) {
        if(!com.isEnabled()&& !( com instanceof JSlider)) {
            com.setEnabled(true);
        } 
    }
}	
 //user checkhd the set default option so disable compenents of StaticWorkloadSepecifaction Panel

    private void setDefaultChecked(Container container){
        staticLoad.setDefaultcheckedInDefinescenarionDialogBox();

    	Component[] components = getComponents(container);
        for(Component com : components) {
          //  if(com.isEnabled()) {
            	if(!(com instanceof JTextField || com instanceof JLabel ) &&  !(com instanceof JScrollBar))// so that labels textfields and scrollbar remain enable to increase visibiity
                com.setEnabled(false);
            	
         //   } 
        }
    	
    	
    }
    //user uncheckhd the set default option so enable compenents of StaticWorkloadSepecifaction Panel


    private void setDefaultUnChecked(Container container){
    	// extract all the components of StaticWorkloadSpecification or DynamicWorkloadSpecifition classes and enable them because there workload is > 0% now
    	Component[] components = getComponents(container);
        for(Component com : components) {
            if((!com.isEnabled())&& !( com instanceof JSlider)) {
               com.setEnabled(true);
                if(com instanceof JCheckBox) ((JCheckBox) com).setSelected(false);
                else if(com instanceof JTextField) ((JTextField) com).setText("0");
               // else if(com instanceof JSlider) ((JSlider) com).setValue(0);
            } 
        }
    	
}



	
}
