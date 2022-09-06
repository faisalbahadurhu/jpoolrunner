package com.jpoolrunner.comparativeanalysis;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;

public class TestDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	JTextArea textArea = new JTextArea();

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			TestDialog dialog = new TestDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public TestDialog(Vector clonedynamicStartvector) {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			
			contentPanel.add(textArea);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		String s="";
		
		Iterator itL1=clonedynamicStartvector.iterator();
    	int counter=0;
    	while(itL1.hasNext()){
    		Number start[]= (Number[])itL1.next();
    		for(int i=0;i<start.length;i++){
        		s+=start[i]+"\t";
    		}
    		s+="\n";
    		
    	}
    	/*	Iterator itL1=clonedynamicStartvector.iterator();
    	int counter=0;
    	while(itL1.hasNext()){
    		String st= (String)itL1.next();
    		s+=st+"\t";
    	
    	//	s+="\n";
    		
    	}*/
    	
    	textArea.append(s);
		
	}
	

}
