package com.jpoolrunner.tpsandtestnamesloader;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ListCellRenderer;

import com.jpoolrunner.clientside.DefineScenarioNew3;

public class TpsNameAndTestNamesPanel extends JPanel {
	
	private ResultPath resultPath;
	private ButtonGroup buttonGroup;
	private TpsRadioButtonHandler handler;
	private Vector<JPanel> vectorOfPanels;
	private DefineScenarioNew3 defineScenarioNew3;
	private boolean DefineScenarioNew=false;// to check whether this class object is constructed from which constructor: 
	private String tps="";
	public TpsNameAndTestNamesPanel(ResultPath resultPath,ButtonGroup buttonGroup, DefineScenarioNew3 defineScenarioNew3, String tps){
		this.buttonGroup=buttonGroup;
		this.resultPath=resultPath;
		this.handler=new TpsRadioButtonHandler();
		this.tps=tps;//JOptionPane.showMessageDialog(null,this.tps);
		vectorOfPanels=new Vector<JPanel>();
		this.defineScenarioNew3=defineScenarioNew3;
		DefineScenarioNew=true;
		


	}
	public TpsNameAndTestNamesPanel(ResultPath resultPath,ButtonGroup buttonGroup){
		this.buttonGroup=buttonGroup;
		this.resultPath=resultPath;
		this.handler=new TpsRadioButtonHandler();
		vectorOfPanels=new Vector<JPanel>();
	//	this.defineScenarioNew3=defineScenarioNew3;
		DefineScenarioNew=false;		


	}
	public JPanel getPanel(String tpsName,Vector<String> testNames,int d){
		JPanel panel=new JPanel(new FlowLayout(FlowLayout.LEFT));
		ButtonGroup group1=new ButtonGroup();;
		JRadioButton tpsNameRadioButton=new JRadioButton(tpsName);
		
		if(DefineScenarioNew)tpsNameRadioButton.setEnabled(false);
		
	//	if(!DefineScenarioNew && d!=0) {tpsNameRadioButton.setEnabled(false);}//all other tpnname radio buttons are disable except 1st radiobutton
		if(!DefineScenarioNew && d==0) {tpsNameRadioButton.setSelected(true);}//1st radiobutton is also selected
		
		tpsNameRadioButton.addActionListener(handler);
		buttonGroup.add(tpsNameRadioButton);
		int i;String s;
		Vector radioButtonVector=new Vector();

		for(i=0;i<testNames.size();i++){
			JRadioButton radiobutton=new JRadioButton(testNames.elementAt(i));
			if(!DefineScenarioNew && d==0 && i==0)radiobutton.setSelected(true);
			radioButtonVector.add(radiobutton);
			group1.add(radiobutton);
		}
		JComboCheckBox compareCheckBox=new JComboCheckBox(radioButtonVector,tpsName);
		compareCheckBox.setEnabled(false);
		if(!DefineScenarioNew && d==0) compareCheckBox.setSelectedIndex(0);
		panel.add(tpsNameRadioButton);
		panel.add(compareCheckBox);
		panel.add(new JLabel("           "));
		vectorOfPanels.add(panel);
		if(tps.equals(tpsName)) panel.setVisible(false);// because same TPS cant be tested 
		return panel;
		
		
	}
	class TpsRadioButtonHandler implements ActionListener {
		public TpsRadioButtonHandler( ) {
			// TODO Auto-generated constructor stub
			}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JRadioButton jrb=(JRadioButton)e.getSource();
			for(int i=0;i<vectorOfPanels.size();i++){
				if(!vectorOfPanels.elementAt(i).equals(jrb.getParent())){
					for(Component component : getComponents(vectorOfPanels.elementAt(i)))
						if(component instanceof JComboCheckBox )component.setEnabled(false);	
				}
				else{
					for(Component component : getComponents(vectorOfPanels.elementAt(i)))
						if(component instanceof JComboCheckBox && ! component.isEnabled())component.setEnabled(true);	
				
				}
			}
			
		}
		private Component[] getComponents(Component container) {
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

	}
	class JComboCheckBox extends JComboBox
	{
		Vector v=new Vector();
		String tpsName;
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

		public JComboCheckBox(Vector items,String tpsName) {
			super(items);
			this.tpsName=tpsName;
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
			if (getSelectedItem() instanceof JRadioButton) {
				JRadioButton jcb = (JRadioButton)getSelectedItem();
				jcb.setSelected(true);
				resultPath.setPath(tpsName+"/"+jcb.getActionCommand());
				if(DefineScenarioNew) defineScenarioNew3.setUsePreviousScenario(true);
				//JOptionPane.showMessageDialog(null,"Path="+resultPath.getPath());
				if(tps!="") { //JOptionPane.showMessageDialog(null,tps);
					String tpsName[]=((String)resultPath.getPath()).split("/");
					if(tpsName[0].equals(tps))
						{JOptionPane.showMessageDialog(null,"Same TPS can not be tested");
					//jcb.setSelected(false);
				}
					}
				
				/*	jcb.setSelected(!jcb.isSelected()); 
				if(jcb.isSelected()) v.addElement(jcb.getActionCommand());//Main.text.append(t);
				else v.removeElement(jcb.getActionCommand());*/


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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
