package com.jpoolrunner.displaytestedscenariosonly;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.jpoolrunner.clientside.CompareGraphWindow;
import com.jpoolrunner.clientside.JobPercentiles;
import com.jpoolrunner.clientside.MainFrame;
import com.jpoolrunner.comparativeanalysis.ResponseTimeComparisons;
import com.jpoolrunner.comparativeanalysis.ThroughputSeriesComparisons;
import com.jpoolrunner.comparativeanalysis.ThroughputStatisticsComparison;
import com.jpoolrunner.comparativeanalysis.WaitTimeComparisons;
public class TpsOnDisk extends JPanel{

	private boolean compareButtonClicked=false;//i.e. compare button 
	private Vector<Double> responseSeries;
	private Vector<Double> waitPanelSeries;
	private Vector<Double> throughputPanelSeries;
	private Vector<Double> poolSizePanelSeries;
	private Vector<JobPercentiles> jobPercentilesVector;
	private String[] workLoadForBins;
	JComboCheckBox compareCheckBox;
	CompareGraphWindow cgWindow;
	
	private String strategyName;// it is in form NFbos2/Test1 e.g.
	DynamicPanel dynamicPanel;
	public String getStrategyName() {
		return strategyName;
	}
	private Vector<Integer> requestFrequencies;
	private Vector<Double> frequencies=new Vector<Double>();
	private Vector<Integer> staticAndDynamicLoadValues;
	private String workLoadPatternValue;
	private Vector<Double>  resultStatistics;
	int oneKb, tenKb, hundredKb,thousandKb,twoThousandKb,tenMilliSecond,fiftyMilliSecond,hundredMilliSecond,staticLoadPercentage,dynamicLoadPercentage;
	private Vector<String> previousTestNames;
	private MainFrame mainFrame;

	public TpsOnDisk(MainFrame mainFrame, String strategyName, Vector<Integer> requestFrequencies, Vector<Double> throughputPanelSeries, Vector<Double> poolSizePanelSeries, Vector<Double> resultStatistics, Vector<Integer> staticAndDynamicLoadValues, String workLoadPatternValue, Vector<String> previousTestNames,Vector<JobPercentiles> jobPercentilesVector,String[] workLoadForBins){
		
		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		this.setBackground(Color.BLUE);
		this.requestFrequencies=requestFrequencies;
		this.resultStatistics=resultStatistics;
		Iterator it=requestFrequencies.iterator();
		while(it.hasNext()){
			int value=(int)it.next();
			frequencies.add((double)value);
			
			
		}
	
	
		
		this.mainFrame=mainFrame;
		//Vector<Integer> staticAndDynamicLoadValues, String workLoadPatternValue
		this.staticAndDynamicLoadValues=staticAndDynamicLoadValues;// saved as int oneKb,int tenKb,int hundredKb,int thousandKb,int twoThousandKb,int tenMilliSecond,int fiftyMilliSecond,int hundredMilliSecond,int staticLoadPercentage,int dynamicLoadPercentage
		this.workLoadPatternValue=workLoadPatternValue;	//name of the distribution ie poisson uniform guassian heavy tailed etc	
		this.strategyName=strategyName;
		
		this.throughputPanelSeries=throughputPanelSeries;
		this.poolSizePanelSeries=poolSizePanelSeries;	
		this.previousTestNames=previousTestNames;
		this.jobPercentilesVector=jobPercentilesVector;
		this.workLoadForBins=workLoadForBins;
		oneKb=staticAndDynamicLoadValues.elementAt(0);
		tenKb=staticAndDynamicLoadValues.elementAt(1);
		hundredKb=staticAndDynamicLoadValues.elementAt(2);
		thousandKb=staticAndDynamicLoadValues.elementAt(3);
		twoThousandKb=staticAndDynamicLoadValues.elementAt(4);
		tenMilliSecond=staticAndDynamicLoadValues.elementAt(5);
		fiftyMilliSecond=staticAndDynamicLoadValues.elementAt(6);
		hundredMilliSecond=staticAndDynamicLoadValues.elementAt(7);
		staticLoadPercentage=staticAndDynamicLoadValues.elementAt(8);
		dynamicLoadPercentage=staticAndDynamicLoadValues.elementAt(9);
		preparePanel();
		dynamicPanel=new DynamicPanel();
		cgWindow=new CompareGraphWindow(this.mainFrame,"Comparative Analysis");
		
	}
	public DynamicPanel getDynamicPanel() {
		return dynamicPanel;
	}
	private  void preparePanel(){	

		setLayout(new BorderLayout(0, 0));
		
		//northPanel.add(new JButton("sdc"));
		JPanel quadrantPanel=new JPanel(new GridLayout(3,2));

		///////
		JTabbedPane requestFrequenciesPane = new JTabbedPane();
		PanelFromDisk frequencyPanel=new PanelFromDisk(strategyName,frequencies,"Load on Server","Time(s)","Number of Requests");
		requestFrequenciesPane.add(frequencyPanel,"Request-Frequencies");
	//	requestFrequenciesPane.add(responseStatisticPanelFromDisk,"Statistics");
		
		JTabbedPane responseTabbedPane = new JTabbedPane();
		ResponseStatisticPanelFromDisk responseStatisticPanelFromDisk=new ResponseStatisticPanelFromDisk(strategyName,jobPercentilesVector);
	//	PanelFromDisk responsePanel=new PanelFromDisk(strategyName,responseSeries,"Response Time","Response Time(ms)","Responses");
	//	throughputTabbedPane1.add(responsePanel,"Response Time Graph");
		
		JScrollPane scrollerForRTGraph=new JScrollPane(responseStatisticPanelFromDisk);
		scrollerForRTGraph.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollerForRTGraph.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(500,205));
		 panel.add(scrollerForRTGraph,BorderLayout.CENTER);	
		
		responseTabbedPane.add(panel,"Response Time Statistics");
		
		JTabbedPane waitTabbedPanel = new JTabbedPane();
		WaitStaticticsPanelFromDisk WaitStaticticsPanelFromDisk=new WaitStaticticsPanelFromDisk(strategyName,jobPercentilesVector);
	//	PanelFromDisk responsePanel2=new PanelFromDisk(strategyName,waitPanelSeries,"Wait Time","Wait Time(ms)","Responses");
	//	throughputTabbedPanel2.add(responsePanel2,"Wait Time Graph");
		
		JScrollPane scrollerForRTGraph2=new JScrollPane(WaitStaticticsPanelFromDisk);
		scrollerForRTGraph2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollerForRTGraph2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				
		JPanel panel2 = new JPanel(new BorderLayout());
		panel2.setPreferredSize(new Dimension(500,205));
		 panel2.add(scrollerForRTGraph2,BorderLayout.CENTER);	
		 waitTabbedPanel.add(panel2,"Wait Time Statistics");

		 
		JTabbedPane throughputTabbedPane = new JTabbedPane();
		ThroughputStaticticsPanelFromDisk throughputStaticticsPanelFromDisk=new ThroughputStaticticsPanelFromDisk(resultStatistics,strategyName);
		PanelFromDisk responsePanel3=new PanelFromDisk(strategyName,throughputPanelSeries,"Throughput/Sec","Time(s)","Number of Responses");
		throughputTabbedPane.add(responsePanel3,"Troughput Graph");
		throughputTabbedPane.add(throughputStaticticsPanelFromDisk,"Statistics");
		
		JTabbedPane poolSizeTabbedPane = new JTabbedPane();
		PoolSizeStaticticsPanelFromDisk poolSizeStaticticsPanelFromDisk=new PoolSizeStaticticsPanelFromDisk(resultStatistics,strategyName);
		PanelFromDisk responsePanel4=new PanelFromDisk(strategyName,poolSizePanelSeries,"Pool Size","Time(s)","Number of thread in pool");
		poolSizeTabbedPane.add(responsePanel4,"Pool Size Graph");
		poolSizeTabbedPane.add(poolSizeStaticticsPanelFromDisk,"Statistics");
	
		quadrantPanel.add(requestFrequenciesPane);
		quadrantPanel.add(responseTabbedPane);
		quadrantPanel.add(waitTabbedPanel);
		quadrantPanel.add(throughputTabbedPane);
		quadrantPanel.add(poolSizeTabbedPane);

		JPanel innerPanel=new JPanel(new BorderLayout());
		innerPanel.add(quadrantPanel,BorderLayout.CENTER);
		
		innerPanel.setBackground(Color.ORANGE);
		add(innerPanel, BorderLayout.CENTER);
		
	}
	public class DynamicPanel extends JPanel{
		public String getName2(){
			return strategyName;
		}
		public DynamicPanel(){
			//this.setBackground(Color.GREEN);
			this.setBounds(0, 271, 281, 340);
			this.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			prepareDynmicPanel();

		}
	
		public void prepareDynmicPanel(){
			Font labelFont= new Font ("Verdana", Font.BOLD , 12);
			this.setLayout(new BorderLayout());
			JPanel panel1=new JPanel(new GridLayout(2,1));
			JLabel strategyName=new  JLabel(""+getStrategyName());
			strategyName.setFont(labelFont);
			strategyName.setHorizontalAlignment(SwingConstants.CENTER);
			panel1.add(strategyName);
			String distributionNValue="";
			JLabel workLoadNDistribution=new JLabel(workLoadPatternValue+" Distribution ");
			workLoadNDistribution.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel1.add(workLoadNDistribution);
			//add(new JLabel(" Scenario: "+getStrategyName()),BorderLayout.NORTH); 
			add(panel1,BorderLayout.NORTH);
			JTabbedPane jTabbedPane1 = new JTabbedPane();

			JPanel staticInfoPanel=new JPanel(new BorderLayout());
			JTable staticInfoTable=prepareAndGetStaticTable();
			// JScrollPane scrollPane = new JScrollPane(staticInfoTable);
			staticInfoTable.setFillsViewportHeight(true);
			staticInfoPanel.add(staticInfoTable.getTableHeader(), BorderLayout.PAGE_START);
			staticInfoPanel.add(staticInfoTable, BorderLayout.CENTER);

			JPanel dynamicInfoPanel=new JPanel(new BorderLayout());
			JTable dynamicInfoTable=prepareAndGetDynamicTable();
			dynamicInfoTable.setFillsViewportHeight(true);
			dynamicInfoPanel.add(dynamicInfoTable.getTableHeader(), BorderLayout.PAGE_START);
			dynamicInfoPanel.add(dynamicInfoTable, BorderLayout.CENTER);

			jTabbedPane1.add(staticInfoPanel,"Static WorkLoad "+staticLoadPercentage+" %");
			jTabbedPane1.add(dynamicInfoPanel,"Dynamic WorkLoad "+dynamicLoadPercentage+" %");
			add(jTabbedPane1,BorderLayout.CENTER);


			JPanel comparePanel=new JPanel();
			comparePanel.setLayout(new GridLayout(1,2));

			comparePanel.setPreferredSize(new Dimension(2,40));
			//compare.setSize(new Dimension(2,5));
			//compare.setBounds(0, 36, 89, 23);
			JPanel comparePanelFor1Buttton=new JPanel(new FlowLayout());
			JButton compare=new JButton("COMPARE");
			Vector namesinCheckBox=new Vector();
		
			for(int i=0;i<previousTestNames.size();i++){
				JCheckBox jcb=new JCheckBox(previousTestNames.elementAt(i),false);
				jcb.setToolTipText(previousTestNames.elementAt(i));
				namesinCheckBox.add(jcb);
	
				
			}
			 compareCheckBox=new JComboCheckBox(namesinCheckBox);
			comparePanelFor1Buttton.add(compare);
		
	//	final	CompareGraphWindow dialog= new CompareGraphWindow(mainFrame,"Comparative Analysis",tpsNTestNames,workLoadForBins);
			
			

			compare.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Vector tpsNTestNames=compareCheckBox.getSelectedStrategyNames();// in form of NFBOS/Test2 e.g.
					
					if(tpsNTestNames.size()==0) {// 1 atrategy name is already added so if did not check a strategy then no selection is made
						JOptionPane.showMessageDialog(null, "There s no selection ");
						return;
					}
					tpsNTestNames.add(getName2());//name of this strategy Who's tps is on display it is in the form NFBOS/Test2
					if(compareButtonClicked==false){// it is false when clicked 1st time
						cgWindow.prepareGUI(tpsNTestNames, workLoadForBins);
						compareButtonClicked=true;//
						}
					else{
						cgWindow.makeGUIVisible();
					}
					//cgWindow.prepareGUI(tpsNTestNames, workLoadForBins);

				
				}});

			comparePanel.add(compareCheckBox);
			comparePanel.add(comparePanelFor1Buttton);
			if(previousTestNames.size()>0){
				//JComboCheckBox
				add(comparePanel,BorderLayout.SOUTH);	
			}
		}
		private JTable prepareAndGetStaticTable(){

			String[] columnNames = {"File Size","Access Frequency(%)"};
			/*
	 int oneKb,tenKb,hundredKb,thousandKb,twoThousandKb;//static load
		Vector<Integer> staticLoadValues;//All above values are extracted from this vector.U can use this vector also instead of above variables 
		int tenMilliSecond,fiftyMilliSecond,hundredMilliSecond;//dynamic load

			 */

			Object[][] data = {
					{"1 KB",new Integer(oneKb)},
					{"10 KB",new Integer(tenKb)},
					{"100 KB",new Integer(hundredKb)},
					{"1000 KB",new Integer(thousandKb)},
					{"2000 KB",new Integer(twoThousandKb)},

			};

			JTable table = new JTable(data, columnNames);
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(SwingConstants.CENTER );
			table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
			table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );


			//JScrollPane tableScrollPane = new JScrollPane(table);
			return table;
		}
		private JTable prepareAndGetDynamicTable(){

			String[] columnNames = {"Processing Time","Access Frequency(%)"};

			/*
	 int oneKb,tenKb,hundredKb,thousandKb,twoThousandKb;//static load
		Vector<Integer> staticLoadValues;//All above values are extracted from this vector.U can use this vector also instead of above variables 
		int tenMilliSecond,fiftyMilliSecond,hundredMilliSecond;//dynamic load

			 */

			Object[][] data = {
					{"15 ms",new Integer(tenMilliSecond)},
					{"50 ms",new Integer(fiftyMilliSecond)},
					{"100 ms",new Integer(hundredMilliSecond)},

			};

			JTable table = new JTable(data, columnNames);
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
			table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
			table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
			//JScrollPane tableScrollPane = new JScrollPane(table);
			return table;


		}

	}

}
class JComboCheckBox extends JComboBox
{
	Vector v=new Vector();
	public Vector getSelectedStrategyNames(){
		return this.v;
	}
	public JComboCheckBox() {
		init();
	}

	public JComboCheckBox(JCheckBox[] items) {
		super(items);
		init();
	}

	public JComboCheckBox(Vector items) {
		super(items);
		init();
	}

	public JComboCheckBox(ComboBoxModel aModel) {
		super(aModel);
		init();
	}

	private void init() {
		setRenderer(new ComboBoxRenderer());
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				itemSelected();
			}
		});
	}

	private void itemSelected() {
		//   Main.text.setText(" ");
		if (getSelectedItem() instanceof JCheckBox) {
			JCheckBox jcb = (JCheckBox)getSelectedItem();
			jcb.setSelected(!jcb.isSelected()); 
			if(jcb.isSelected()) v.addElement(jcb.getActionCommand());//Main.text.append(t);
			else v.removeElement(jcb.getActionCommand());


		}
	}
	class ComboBoxRenderer implements ListCellRenderer {
		private JLabel label;

		public ComboBoxRenderer() {
			setOpaque(true);
		}

		public Component getListCellRendererComponent(JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {
			if (value instanceof Component) {
				Component c = (Component)value;
				if (isSelected) {
					c.setBackground(Color.WHITE);// c.setBackground(list.getSelectionBackground()); 
					c.setForeground(Color.BLACK);//c.setForeground(list.getSelectionForeground());
				} else {
					c.setBackground(Color.WHITE);//c.setBackground(list.getBackground());
					c.setForeground(Color.BLACK);// c.setForeground(list.getForeground());
				}

				return c;
			} else {
				if (label ==null) {
					label = new JLabel(value.toString());
					label.setBackground(Color.WHITE);// no lines was here
					label.setBackground(Color.BLACK);//no line was here
				}
				else {
					label.setText(value.toString());
				}

				return label;
			}
		}
	}
}
