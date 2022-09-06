package com.jpoolrunner.clientside;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.Dimension;
import java.util.Vector;


public class DefineScenarioDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
//	DefineScenarioNew myPanel;
	DefineScenarioNew3 myPanel;
	BooleanChecker check;

	//private DisplayPanel displayPanel;
	JButton okButton=new JButton("OK");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
		//	DefineScenarioDialog dialog = new DefineScenarioDialog();
		//	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		//	dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void callCheck(boolean b){
		check.setSend(b);
	}

	/**
	 * Create the dialog.
	 * @param mainFrame 
	 */
	//public DefineScenarioDialog(DefineScenarioNew defineScenario,BooleanChecker check) {
		public DefineScenarioDialog(DefineScenarioNew3 defineScenario,BooleanChecker check,String tpsName, MainFrame mainFrame) {
			super(mainFrame);
	
		this.setTitle("Test Plan for "+tpsName);
		myPanel=defineScenario;
		this.check=check;
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//	if()
				if((myPanel.getusePreviousScenario())||(myPanel.isStaticDone() && myPanel.isDynamicDone() && myPanel.isDistributionDone())) {// if all inputs are correct ie staticload dynamic load and frequency distribution
					
					callCheck(true);

					DefineScenarioDialog.this.setVisible(false);
					DefineScenarioDialog.this.dispatchEvent(new WindowEvent(
					DefineScenarioDialog.this, WindowEvent.WINDOW_CLOSING));
					}
					else {
						String s="";
						if(!myPanel.isStaticDone()) { s+="Plz keep Satic Load 100% "+"\n";}
						if(!myPanel.isDynamicDone()) { s+="Plz keep Dynamic Load 100% "+"\n";}
						if(!myPanel.isDistributionDone()) { s+="Plz set Request Frequency Distribution "+"\n";}
						
						JOptionPane.showMessageDialog(null,s);	
						
						}
			}
		});
		setBounds(100, 50, 0, 0);
		//setPreferredSize(new Dimension(900,600));

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(myPanel,BorderLayout.CENTER);
		
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						callCheck(false);
						DefineScenarioDialog.this.setVisible(false);
						DefineScenarioDialog.this.dispatchEvent(new WindowEvent(
						DefineScenarioDialog.this, WindowEvent.WINDOW_CLOSING));
						}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
