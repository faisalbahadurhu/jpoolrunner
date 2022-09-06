/*package com.jpoolrunner.clientside;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

public class DynamicWorkLoadSpecificationPanel extends JPanel //	implements	ActionListener,ListSelectionListener
 {
	// Instance attributes used in this example
	private	JPanel		topPanel;
	private	Vector		listData;
	private JTextField oneKbtextField, tenKbtextField, hundredKbtextField;
	private JSlider oneKbslider,tenKbslider,hundredKbslider;
	private JCheckBox oneKbCheckBox,tenKbcheckBox,hundredKbcheckBox;
	private Vector<JSlider> sliderVector;// this vector is used to keep JSlider references so that in event handling of each JSlider we can iterate through it and extract all Jsslider references and add values of enabled Jslider to total variable and then set the maximum value of slider(in which event handling is performed) to 100- totalvalue
	private Vector<JTextField> textFieldVector;// this vector is used to keep JTextfield references so that in event handling of each JSlider we can iterate through it and extract all Jsslider references and add values of enabled Jslider to total variable and then set the maximum value of slider(in which event handling is performed) to 100- totalvalue

	
	
	// Constructor of main frame
	public DynamicWorkLoadSpecificationPanel()
	{
		this.setLayout(new BorderLayout());

		// Create a panel to hold all other components
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(3, 1, 0, 0));
		
		mainPanel.add(topPanel,BorderLayout.CENTER);

		JScrollPane pane=new JScrollPane(mainPanel);
		//pane.
		//getContentPane().add( topPanel );
   //  JScrollPane pane=new JScrollPane(topPanel); 
	//	add( pane,BorderLayout.CENTER );
		add( pane,BorderLayout.CENTER );
		sliderVector=new Vector<JSlider>(5);//it contains 5 JSlider Objects we will iterate over this vector and if a slider is enable then its value is taken in event handling of each slider

		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.WHITE, 1, true));
		panel.setPreferredSize(new Dimension(3, 10));
		topPanel.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(50,50));
		panel.add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new BorderLayout());
		
		 oneKbCheckBox = new JCheckBox();
		 oneKbCheckBox.setEnabled(false);
		oneKbCheckBox.addItemListener(new ItemListener() {
		      public void itemStateChanged(ItemEvent e) {
		         // System.out.println("Checked? " + check.isSelected());
		    	  if(oneKbCheckBox.isSelected()){
		    		  oneKbtextField.setEnabled(true);
		    		 // oneKbtextField.setText((oneKbslider.getValue())*10+"");
		    		  oneKbtextField.setText("");
		    		  oneKbslider.setValue(0);
		    		  oneKbslider.setEnabled(true);
		    		  
		    	  }
		    	  else if(!oneKbCheckBox.isSelected()){
		    		  oneKbtextField.setEnabled(false);
		    		  oneKbtextField.setText("");

		    		  oneKbslider.setEnabled(false);
		    	  }
		        }
		      });
		
		panel_1.add(oneKbCheckBox,BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		panel_5.setPreferredSize(new Dimension(100,100));
		panel_2.add(panel_5, BorderLayout.CENTER);
		
		JLabel lblFileSize = new JLabel("Low CPU-Bound:   Access Frequency (%) :");
		panel_5.add(lblFileSize);
		
		oneKbtextField = new JTextField("");
		oneKbtextField.setEditable(false);
		oneKbtextField.setEnabled(false);
		oneKbtextField.setToolTipText("Must Press Enter when u done!");
		oneKbtextField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				try {
					oneKbslider.setValue(Integer.parseInt(oneKbtextField.getText()));
					Iterator iterator=textFieldVector.iterator();
		            int total=0;
		         	  while(iterator.hasNext()){
		         	 JTextField field=(JTextField) iterator.next();
		         	 if(!field.equals(e.getSource())){// ie if extracted slider is not this one ie oneKbslider ..because we dont wanna add value of this slider
		         		 if(field.isEnabled()){
		         			total+=Integer.parseInt(field.getText()); 
		         		 }
		         	 }
		        }
		         	  if((Integer.parseInt(oneKbtextField.getText())+total)>100){
		         		 oneKbtextField.setText("0");
		         		oneKbslider.setValue(0);
		         	  }
				}catch(NumberFormatException nfe){
		        	JOptionPane.showMessageDialog(null, "Enter an integer value");
		        }
			}
		});
		oneKbtextField.addFocusListener(new FocusListener() {
		      public void focusGained(FocusEvent e) {
		    	 // oneKbtextField.setToolTipText("Must Press Enter when u done!"); 
		    	  JComponent component = (JComponent)e.getSource();

	                MouseEvent phantom = new MouseEvent(
	                    component,
	                    MouseEvent.MOUSE_MOVED,
	                    System.currentTimeMillis(),
	                    0,
	                    10,
	                    10,
	                    0,
	                    false);

	                ToolTipManager.sharedInstance().mouseMoved(phantom);
		      }
		      public void focusLost(FocusEvent e) {
		    	  oneKbtextField.setText(oneKbslider.getValue()+"");
		      }

		});
		
		panel_5.add(oneKbtextField);
		oneKbtextField.setColumns(3);
		
		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(80,50));
		panel.add(panel_3, BorderLayout.EAST);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		 oneKbslider = new JSlider(SwingConstants.HORIZONTAL,0,10,0);
		 oneKbslider.setEnabled(false);
		// oneKbslider.setValue(50);
		 oneKbslider.addChangeListener(new ChangeListener(){
			 public void stateChanged(ChangeEvent e) {
			        JSlider source = (JSlider)e.getSource();
			       
			        	//now iterate in slider vector 
			        	Iterator iterator=sliderVector.iterator();
			            int total=0;
			         	  while(iterator.hasNext()){
			         	 JSlider slider=(JSlider) iterator.next();
			         	 if(!slider.equals(source)){// ie if extracted slider is not this one ie oneKbslider ..because we dont wanna add value of this slider
			         		 if(slider.isEnabled()){
			         			total+=slider.getValue(); 
			         		 }
			         	 }
			         		  
			         	  }
			         	  source.setMaximum(10-total);
			         	 int value=source.getValue()*10;
				        	oneKbtextField.setText(value+"");
			    }
		 });
		panel_3.add(oneKbslider, BorderLayout.CENTER);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(Color.WHITE));
		topPanel.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_7 = new JPanel();
		panel_7.setPreferredSize(new Dimension(50,50));
		panel_6.add(panel_7, BorderLayout.WEST);
		panel_7.setLayout(new BorderLayout(0, 0));
		
	    tenKbcheckBox = new JCheckBox("");
	    tenKbcheckBox.setEnabled(false);
		tenKbcheckBox.addItemListener(new ItemListener() {
		      public void itemStateChanged(ItemEvent e) {
		         // System.out.println("Checked? " + check.isSelected());
		    	  if(tenKbcheckBox.isSelected()){
		    		  tenKbtextField.setEnabled(true);
		    		  //tenKbtextField.setText((tenKbslider.getValue())*10+"");
		    		  tenKbtextField.setText("");
		    		  tenKbslider.setValue(0);
		    		  tenKbslider.setEnabled(true);
		    		  
		    	  }
		    	  else if(!tenKbcheckBox.isSelected()){
		    		  tenKbtextField.setEnabled(false);
		    		  tenKbtextField.setText("");
		    		  tenKbslider.setEnabled(false);
		    	  }
		        }
		      });
		
		panel_7.add(tenKbcheckBox, BorderLayout.CENTER);
		
		JPanel panel_8 = new JPanel();
		panel_8.setPreferredSize(new Dimension(80,50));

		panel_6.add(panel_8, BorderLayout.EAST);
		panel_8.setLayout(new BorderLayout(0, 0));
		
		 tenKbslider = new JSlider(SwingConstants.HORIZONTAL,0,10,0);
		 tenKbslider.setEnabled(false);
		// tenKbslider.setValue(30);
		 tenKbslider.addChangeListener(new ChangeListener(){
			 public void stateChanged(ChangeEvent e) {
			        JSlider source = (JSlider)e.getSource();
			       
			    	Iterator iterator=sliderVector.iterator();
		            int total=0;
		         	  while(iterator.hasNext()){
		         	 JSlider slider=(JSlider) iterator.next();
		         	 if(!slider.equals(source)){// ie if extracted slider is not this one ie oneKbslider ..because we dont wanna add value of this slider
		         		 if(slider.isEnabled()){
		         			total+=slider.getValue(); 
		         		 }
		         	 }
		         		  
		         	  }
		         	  source.setMaximum(10-total);
		         	 int value=source.getValue()*10;
			        	tenKbtextField.setText(value+"");
			           
			        
			    }
		 });
		panel_8.add(tenKbslider, BorderLayout.CENTER);
		
		JPanel panel_9 = new JPanel();
		panel_9.setLayout(new BorderLayout(0, 0));
		panel_6.add(panel_9, BorderLayout.CENTER);
		
		JPanel panel_11 = new JPanel();
		panel_9.add(panel_11, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("High CPU-Bound:  Access Frequency (%) :");
		panel_11.add(lblNewLabel);
		
		tenKbtextField = new JTextField("");
		tenKbtextField.setEditable(false);
		tenKbtextField.setEnabled(false);
		tenKbtextField.setToolTipText("Must Press Enter when u done!");
		tenKbtextField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				try {
					tenKbslider.setValue(Integer.parseInt(tenKbtextField.getText()));
					Iterator iterator=textFieldVector.iterator();
		            int total=0;
		         	  while(iterator.hasNext()){
		         	 JTextField field=(JTextField) iterator.next();
		         	 if(!field.equals(e.getSource())){// ie if extracted slider is not this one ie oneKbslider ..because we dont wanna add value of this slider
		         		 if(field.isEnabled()){
		         			total+=Integer.parseInt(field.getText()); 
		         		 }
		         	 }
		        }
		         	  if((Integer.parseInt(tenKbtextField.getText())+total)>100){
		         		 tenKbtextField.setText("0");
		         		tenKbslider.setValue(0);
		         	  }
				}
		          catch(NumberFormatException nfe){
		        	JOptionPane.showMessageDialog(null, "Enter an integer value");
		        }
			}
		});
		tenKbtextField.addFocusListener(new FocusListener() {
		      public void focusGained(FocusEvent e) {
		    	//  tenKbtextField.setToolTipText("Must Press Enter when u done!"); 
		    	  JComponent component = (JComponent)e.getSource();

	                MouseEvent phantom = new MouseEvent(
	                    component,
	                    MouseEvent.MOUSE_MOVED,
	                    System.currentTimeMillis(),
	                    0,
	                    10,
	                    10,
	                    0,
	                    false);

	                ToolTipManager.sharedInstance().mouseMoved(phantom);
		      }
		      public void focusLost(FocusEvent e) {
		    	  tenKbtextField.setText(tenKbslider.getValue()+"");
		      }

		});
		panel_11.add(tenKbtextField);
		tenKbtextField.setColumns(3);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(Color.WHITE));
		topPanel.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_10 = new JPanel();
		panel_10.setPreferredSize(new Dimension(50,50));

		panel_4.add(panel_10, BorderLayout.WEST);
		panel_10.setLayout(new BorderLayout(0, 0));
		
		hundredKbcheckBox = new JCheckBox("");
		hundredKbcheckBox.setEnabled(false);
		hundredKbcheckBox.addItemListener(new ItemListener() {
		      public void itemStateChanged(ItemEvent e) {
		         // System.out.println("Checked? " + check.isSelected());
		    	  if(hundredKbcheckBox.isSelected()){
		    		  hundredKbtextField.setEnabled(true);
		    		 // hundredKbtextField.setText((hundredKbslider.getValue())*10+"");
		    		  hundredKbtextField.setText("");
		    		  hundredKbslider.setValue(0);
		    		  hundredKbslider.setEnabled(true);
		    		  
		    	  }
		    	  else if(!hundredKbcheckBox.isSelected()){
		    		  hundredKbtextField.setEnabled(false);
		    		  hundredKbtextField.setText("");
		    		  hundredKbslider.setEnabled(false);
		    	  }
		        }
		      });
		panel_10.add(hundredKbcheckBox, BorderLayout.CENTER);
		
		JPanel panel_12 = new JPanel();
		panel_12.setPreferredSize(new Dimension(80,50));
		panel_4.add(panel_12, BorderLayout.EAST);
		panel_12.setLayout(new BorderLayout(0, 0));
		
		 hundredKbslider = new JSlider(SwingConstants.HORIZONTAL,0,10,0);
		// hundredKbslider.setValue(20);
		 hundredKbslider.setEnabled(false);
		 hundredKbslider.addChangeListener(new ChangeListener(){
			 public void stateChanged(ChangeEvent e) {
			        JSlider source = (JSlider)e.getSource();
			        
			        Iterator iterator=sliderVector.iterator();
		            int total=0;
		         	  while(iterator.hasNext()){
		         	 JSlider slider=(JSlider) iterator.next();
		         	 if(!slider.equals(source)){// ie if extracted slider is not this one ie oneKbslider ..because we dont wanna add value of this slider
		         		 if(slider.isEnabled()){
		         			total+=slider.getValue(); 
		         		 }
		         	 }
		         		  
		         	  }
		         	  source.setMaximum(10-total);
		         	 int value=source.getValue()*10;
			        	hundredKbtextField.setText(value+"");
			           
			        
			    }
		 });
		 
		panel_12.add(hundredKbslider, BorderLayout.CENTER);
		
		JPanel panel_13 = new JPanel();
		panel_4.add(panel_13, BorderLayout.CENTER);
		
		JLabel lblNewLabel_1 = new JLabel("V.High CPU-Bound:  Access Frequency (%) :");
		panel_13.add(lblNewLabel_1);
		
		hundredKbtextField = new JTextField("");
		hundredKbtextField.setEditable(false);
		hundredKbtextField.setToolTipText("Must Press Enter when u done!");
		hundredKbtextField.setEnabled(false);
		hundredKbtextField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				try {
					hundredKbslider.setValue(Integer.parseInt(hundredKbtextField.getText()));	
					Iterator iterator=textFieldVector.iterator();
		            int total=0;
		         	  while(iterator.hasNext()){
		         	 JTextField field=(JTextField) iterator.next();
		         	 if(!field.equals(e.getSource())){// ie if extracted slider is not this one ie oneKbslider ..because we dont wanna add value of this slider
		         		 if(field.isEnabled()){
		         			total+=Integer.parseInt(field.getText()); 
		         		 }
		         	 }
		        }
		         	  if((Integer.parseInt(hundredKbtextField.getText())+total)>100){
		         		 hundredKbtextField.setText("0");
		         		hundredKbslider.setValue(0);
		         	  }
		        }  catch(NumberFormatException nfe){
		        	JOptionPane.showMessageDialog(null, "Enter an integer value");
		        }
			}
		});
		hundredKbtextField.addFocusListener(new FocusListener() {
		      public void focusGained(FocusEvent e) {
		    	//  hundredKbtextField.setToolTipText("Must Press Enter when u done!"); 
		    	  JComponent component = (JComponent)e.getSource();

	                MouseEvent phantom = new MouseEvent(
	                    component,
	                    MouseEvent.MOUSE_MOVED,
	                    System.currentTimeMillis(),
	                    0,
	                    10,
	                    10,
	                    0,
	                    false);

	                ToolTipManager.sharedInstance().mouseMoved(phantom);
		      }
		      public void focusLost(FocusEvent e) {
		    	  hundredKbtextField.setText(hundredKbslider.getValue()+"");

		      }

		});
		panel_13.add(hundredKbtextField);
		hundredKbtextField.setColumns(3);
//			private JSlider oneKbslider,tenKbslider,hundredKbslider,thousandKbslider,twoThousandKbslider;
			sliderOff(oneKbslider);
			sliderOff(tenKbslider);
			sliderOff(hundredKbslider);
		
		sliderVector.add(oneKbslider);
		sliderVector.add(tenKbslider);
		sliderVector.add(hundredKbslider);
		textFieldVector=new Vector<JTextField>(5);
		textFieldVector.add(oneKbtextField);//, , , , ;
		textFieldVector.add(tenKbtextField);
		textFieldVector.add(hundredKbtextField);

		// Create the data model for this example
		

		
	}
	//it will store values of textfields in a vector and return them values are files kb information
	public Vector getTextFieldValues(){
		Vector<Integer> v=new Vector<Integer>(3);
		
	//private JTextField oneKbtextField, tenKbtextField, hundredKbtextField, thousandKbtextField, twoThousandKbtextField;
		
		Integer a,b,c;//=oneKbtextField.getText();
		a=b=c=0;
		if(!oneKbtextField.getText().equals("")) a=Integer.parseInt(oneKbtextField.getText());
		if(!tenKbtextField.getText().equals("")) b=Integer.parseInt(tenKbtextField.getText());
		if(!hundredKbtextField.getText().equals("")) c=Integer.parseInt(hundredKbtextField.getText());
		
		v.add(a);
		v.add(b);
		v.add(c);
		return v;
	}
private void sliderOff(JSlider js){
}


	// Main entry point for this example
	public static void main( String args[] )
	{
		// Create an instance of the test application
		DynamicWorkLoadSpecificationPanel mainFrame	= new DynamicWorkLoadSpecificationPanel();
		//mainFrame.setVisible( true );
		JFrame frame= new JFrame();
		frame.getContentPane().add(mainFrame,BorderLayout.CENTER);
		frame.setSize(500, 200);
		frame.setVisible(true);
	}
}
*/


package com.jpoolrunner.clientside;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

public class DynamicWorkLoadSpecificationPanel extends JPanel //	implements	ActionListener,ListSelectionListener
 {
	// Instance attributes used in this example
	private	JPanel		topPanel;
	private	Vector		listData;
	private JTextField oneKbtextField, tenKbtextField, hundredKbtextField;
	private JSlider oneKbslider,tenKbslider,hundredKbslider;
	private JCheckBox oneKbCheckBox,tenKbcheckBox,hundredKbcheckBox;
	private Vector<JSlider> sliderVector;// this vector is used to keep JSlider references so that in event handling of each JSlider we can iterate through it and extract all Jsslider references and add values of enabled Jslider to total variable and then set the maximum value of slider(in which event handling is performed) to 100- totalvalue
	private Vector<JTextField> textFieldVector;// this vector is used to keep JTextfield references so that in event handling of each JSlider we can iterate through it and extract all Jsslider references and add values of enabled Jslider to total variable and then set the maximum value of slider(in which event handling is performed) to 100- totalvalue

	
	
	// Constructor of main frame
	public DynamicWorkLoadSpecificationPanel()
	{
		this.setLayout(new BorderLayout());

		// Create a panel to hold all other components
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(3, 1, 0, 0));
		
		mainPanel.add(topPanel,BorderLayout.CENTER);

		JScrollPane pane=new JScrollPane(mainPanel);
		//pane.
		//getContentPane().add( topPanel );
   //  JScrollPane pane=new JScrollPane(topPanel); 
	//	add( pane,BorderLayout.CENTER );
		add( pane,BorderLayout.CENTER );
		sliderVector=new Vector<JSlider>(5);//it contains 5 JSlider Objects we will iterate over this vector and if a slider is enable then its value is taken in event handling of each slider

		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.WHITE, 1, true));
		panel.setPreferredSize(new Dimension(3, 10));
		topPanel.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(50,50));
		panel.add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new BorderLayout());
		
		 oneKbCheckBox = new JCheckBox();
		 oneKbCheckBox.setEnabled(false);
		oneKbCheckBox.addItemListener(new ItemListener() {
		      public void itemStateChanged(ItemEvent e) {
		         // System.out.println("Checked? " + check.isSelected());
		    	  if(oneKbCheckBox.isSelected()){
		    		  oneKbtextField.setEnabled(true);
		    		 // oneKbtextField.setText((oneKbslider.getValue())*10+"");
		    		  oneKbtextField.setText("");
		    		  oneKbslider.setValue(0);
		    		  oneKbslider.setEnabled(true);
		    		  
		    	  }
		    	  else if(!oneKbCheckBox.isSelected()){
		    		  oneKbtextField.setEnabled(false);
		    		  oneKbtextField.setText("");

		    		  oneKbslider.setEnabled(false);
		    	  }
		        }
		      });
		
		panel_1.add(oneKbCheckBox,BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		panel_5.setPreferredSize(new Dimension(100,100));
		panel_2.add(panel_5, BorderLayout.CENTER);
		
		JLabel lblFileSize = new JLabel("Low CPU-Bound:   Access Frequency (%) :");
		panel_5.add(lblFileSize);
		
		oneKbtextField = new JTextField("");
		oneKbtextField.setEditable(false);
		oneKbtextField.setEnabled(false);
		oneKbtextField.setToolTipText("Must Press Enter when u done!");
		oneKbtextField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				try {
					oneKbslider.setValue(Integer.parseInt(oneKbtextField.getText()));
					Iterator iterator=textFieldVector.iterator();
		            int total=0;
		         	  while(iterator.hasNext()){
		         	 JTextField field=(JTextField) iterator.next();
		         	 if(!field.equals(e.getSource())){// ie if extracted slider is not this one ie oneKbslider ..because we dont wanna add value of this slider
		         		 if(field.isEnabled()){
		         			total+=Integer.parseInt(field.getText()); 
		         		 }
		         	 }
		        }
		         	  if((Integer.parseInt(oneKbtextField.getText())+total)>100){
		         		 oneKbtextField.setText("0");
		         		oneKbslider.setValue(0);
		         	  }
				}catch(NumberFormatException nfe){
		        	JOptionPane.showMessageDialog(null, "Enter an integer value");
		        }
			}
		});
		oneKbtextField.addFocusListener(new FocusListener() {
		      public void focusGained(FocusEvent e) {
		    	 // oneKbtextField.setToolTipText("Must Press Enter when u done!"); 
		    	  JComponent component = (JComponent)e.getSource();

	                MouseEvent phantom = new MouseEvent(
	                    component,
	                    MouseEvent.MOUSE_MOVED,
	                    System.currentTimeMillis(),
	                    0,
	                    10,
	                    10,
	                    0,
	                    false);

	                ToolTipManager.sharedInstance().mouseMoved(phantom);
		      }
		      public void focusLost(FocusEvent e) {
		    	  oneKbtextField.setText(oneKbslider.getValue()+"");
		      }

		});
		
		panel_5.add(oneKbtextField);
		oneKbtextField.setColumns(3);
		
		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(80,50));
		panel.add(panel_3, BorderLayout.EAST);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		 oneKbslider = new JSlider(SwingConstants.HORIZONTAL,0,10,0);
		 oneKbslider.setEnabled(false);
		// oneKbslider.setValue(50);
		 oneKbslider.addChangeListener(new ChangeListener(){
			 public void stateChanged(ChangeEvent e) {
			        JSlider source = (JSlider)e.getSource();
			       
			        	//now iterate in slider vector 
			        	Iterator iterator=sliderVector.iterator();
			            int total=0;
			         	  while(iterator.hasNext()){
			         	 JSlider slider=(JSlider) iterator.next();
			         	 if(!slider.equals(source)){// ie if extracted slider is not this one ie oneKbslider ..because we dont wanna add value of this slider
			         		 if(slider.isEnabled()){
			         			total+=slider.getValue(); 
			         		 }
			         	 }
			         		  
			         	  }
			         	  source.setMaximum(10-total);
			         	 int value=source.getValue()*10;
				        	oneKbtextField.setText(value+"");
			    }
		 });
		panel_3.add(oneKbslider, BorderLayout.CENTER);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(Color.WHITE));
		topPanel.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_7 = new JPanel();
		panel_7.setPreferredSize(new Dimension(50,50));
		panel_6.add(panel_7, BorderLayout.WEST);
		panel_7.setLayout(new BorderLayout(0, 0));
		
	    tenKbcheckBox = new JCheckBox("");
	    tenKbcheckBox.setEnabled(false);
		tenKbcheckBox.addItemListener(new ItemListener() {
		      public void itemStateChanged(ItemEvent e) {
		         // System.out.println("Checked? " + check.isSelected());
		    	  if(tenKbcheckBox.isSelected()){
		    		  tenKbtextField.setEnabled(true);
		    		  //tenKbtextField.setText((tenKbslider.getValue())*10+"");
		    		  tenKbtextField.setText("");
		    		  tenKbslider.setValue(0);
		    		  tenKbslider.setEnabled(true);
		    		  
		    	  }
		    	  else if(!tenKbcheckBox.isSelected()){
		    		  tenKbtextField.setEnabled(false);
		    		  tenKbtextField.setText("");
		    		  tenKbslider.setEnabled(false);
		    	  }
		        }
		      });
		
		panel_7.add(tenKbcheckBox, BorderLayout.CENTER);
		
		JPanel panel_8 = new JPanel();
		panel_8.setPreferredSize(new Dimension(80,50));

		panel_6.add(panel_8, BorderLayout.EAST);
		panel_8.setLayout(new BorderLayout(0, 0));
		
		 tenKbslider = new JSlider(SwingConstants.HORIZONTAL,0,10,0);
		 tenKbslider.setEnabled(false);
		// tenKbslider.setValue(30);
		 tenKbslider.addChangeListener(new ChangeListener(){
			 public void stateChanged(ChangeEvent e) {
			        JSlider source = (JSlider)e.getSource();
			       
			    	Iterator iterator=sliderVector.iterator();
		            int total=0;
		         	  while(iterator.hasNext()){
		         	 JSlider slider=(JSlider) iterator.next();
		         	 if(!slider.equals(source)){// ie if extracted slider is not this one ie oneKbslider ..because we dont wanna add value of this slider
		         		 if(slider.isEnabled()){
		         			total+=slider.getValue(); 
		         		 }
		         	 }
		         		  
		         	  }
		         	  source.setMaximum(10-total);
		         	 int value=source.getValue()*10;
			        	tenKbtextField.setText(value+"");
			           
			        
			    }
		 });
		panel_8.add(tenKbslider, BorderLayout.CENTER);
		
		JPanel panel_9 = new JPanel();
		panel_9.setLayout(new BorderLayout(0, 0));
		panel_6.add(panel_9, BorderLayout.CENTER);
		
		JPanel panel_11 = new JPanel();
		panel_9.add(panel_11, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("High CPU-Bound:  Access Frequency (%) :");
		panel_11.add(lblNewLabel);
		
		tenKbtextField = new JTextField("");
		tenKbtextField.setEditable(false);
		tenKbtextField.setEnabled(false);
		tenKbtextField.setToolTipText("Must Press Enter when u done!");
		tenKbtextField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				try {
					tenKbslider.setValue(Integer.parseInt(tenKbtextField.getText()));
					Iterator iterator=textFieldVector.iterator();
		            int total=0;
		         	  while(iterator.hasNext()){
		         	 JTextField field=(JTextField) iterator.next();
		         	 if(!field.equals(e.getSource())){// ie if extracted slider is not this one ie oneKbslider ..because we dont wanna add value of this slider
		         		 if(field.isEnabled()){
		         			total+=Integer.parseInt(field.getText()); 
		         		 }
		         	 }
		        }
		         	  if((Integer.parseInt(tenKbtextField.getText())+total)>100){
		         		 tenKbtextField.setText("0");
		         		tenKbslider.setValue(0);
		         	  }
				}
		          catch(NumberFormatException nfe){
		        	JOptionPane.showMessageDialog(null, "Enter an integer value");
		        }
			}
		});
		tenKbtextField.addFocusListener(new FocusListener() {
		      public void focusGained(FocusEvent e) {
		    	//  tenKbtextField.setToolTipText("Must Press Enter when u done!"); 
		    	  JComponent component = (JComponent)e.getSource();

	                MouseEvent phantom = new MouseEvent(
	                    component,
	                    MouseEvent.MOUSE_MOVED,
	                    System.currentTimeMillis(),
	                    0,
	                    10,
	                    10,
	                    0,
	                    false);

	                ToolTipManager.sharedInstance().mouseMoved(phantom);
		      }
		      public void focusLost(FocusEvent e) {
		    	  tenKbtextField.setText(tenKbslider.getValue()+"");
		      }

		});
		panel_11.add(tenKbtextField);
		tenKbtextField.setColumns(3);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(Color.WHITE));
		topPanel.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_10 = new JPanel();
		panel_10.setPreferredSize(new Dimension(50,50));

		panel_4.add(panel_10, BorderLayout.WEST);
		panel_10.setLayout(new BorderLayout(0, 0));
		
		hundredKbcheckBox = new JCheckBox("");
		hundredKbcheckBox.setEnabled(false);
		hundredKbcheckBox.addItemListener(new ItemListener() {
		      public void itemStateChanged(ItemEvent e) {
		         // System.out.println("Checked? " + check.isSelected());
		    	  if(hundredKbcheckBox.isSelected()){
		    		  hundredKbtextField.setEnabled(true);
		    		 // hundredKbtextField.setText((hundredKbslider.getValue())*10+"");
		    		  hundredKbtextField.setText("");
		    		  hundredKbslider.setValue(0);
		    		  hundredKbslider.setEnabled(true);
		    		  
		    	  }
		    	  else if(!hundredKbcheckBox.isSelected()){
		    		  hundredKbtextField.setEnabled(false);
		    		  hundredKbtextField.setText("");
		    		  hundredKbslider.setEnabled(false);
		    	  }
		        }
		      });
	//	panel_10.add(hundredKbcheckBox, BorderLayout.CENTER);
		
		JPanel panel_12 = new JPanel();
		panel_12.setPreferredSize(new Dimension(80,50));
		panel_4.add(panel_12, BorderLayout.EAST);
		panel_12.setLayout(new BorderLayout(0, 0));
		
		 hundredKbslider = new JSlider(SwingConstants.HORIZONTAL,0,10,0);
		// hundredKbslider.setValue(20);
		 hundredKbslider.setEnabled(false);
		 hundredKbslider.addChangeListener(new ChangeListener(){
			 public void stateChanged(ChangeEvent e) {
			        JSlider source = (JSlider)e.getSource();
			        
			        Iterator iterator=sliderVector.iterator();
		            int total=0;
		         	  while(iterator.hasNext()){
		         	 JSlider slider=(JSlider) iterator.next();
		         	 if(!slider.equals(source)){// ie if extracted slider is not this one ie oneKbslider ..because we dont wanna add value of this slider
		         		 if(slider.isEnabled()){
		         			total+=slider.getValue(); 
		         		 }
		         	 }
		         		  
		         	  }
		         	  source.setMaximum(10-total);
		         	 int value=source.getValue()*10;
			        	hundredKbtextField.setText(value+"");
			           
			        
			    }
		 });
		 
//		panel_12.add(hundredKbslider, BorderLayout.CENTER);
		
	/*	JPanel panel_13 = new JPanel();
		panel_4.add(panel_13, BorderLayout.CENTER);
		
		JLabel lblNewLabel_1 = new JLabel("V.High CPU-Bound:  Access Frequency (%) :");
		panel_13.add(lblNewLabel_1);
		
		hundredKbtextField = new JTextField("");
		hundredKbtextField.setEditable(false);
		hundredKbtextField.setToolTipText("Must Press Enter when u done!");
		hundredKbtextField.setEnabled(false);
		hundredKbtextField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				try {
					hundredKbslider.setValue(Integer.parseInt(hundredKbtextField.getText()));	
					Iterator iterator=textFieldVector.iterator();
		            int total=0;
		         	  while(iterator.hasNext()){
		         	 JTextField field=(JTextField) iterator.next();
		         	 if(!field.equals(e.getSource())){// ie if extracted slider is not this one ie oneKbslider ..because we dont wanna add value of this slider
		         		 if(field.isEnabled()){
		         			total+=Integer.parseInt(field.getText()); 
		         		 }
		         	 }
		        }
		         	  if((Integer.parseInt(hundredKbtextField.getText())+total)>100){
		         		 hundredKbtextField.setText("0");
		         		hundredKbslider.setValue(0);
		         	  }
		        }  catch(NumberFormatException nfe){
		        	JOptionPane.showMessageDialog(null, "Enter an integer value");
		        }
			}
		});
		hundredKbtextField.addFocusListener(new FocusListener() {
		      public void focusGained(FocusEvent e) {
		    	//  hundredKbtextField.setToolTipText("Must Press Enter when u done!"); 
		    	  JComponent component = (JComponent)e.getSource();

	                MouseEvent phantom = new MouseEvent(
	                    component,
	                    MouseEvent.MOUSE_MOVED,
	                    System.currentTimeMillis(),
	                    0,
	                    10,
	                    10,
	                    0,
	                    false);

	                ToolTipManager.sharedInstance().mouseMoved(phantom);
		      }
		      public void focusLost(FocusEvent e) {
		    	  hundredKbtextField.setText(hundredKbslider.getValue()+"");

		      }

		});
		panel_13.add(hundredKbtextField);
		hundredKbtextField.setColumns(3);*/
			sliderOff(oneKbslider);
			sliderOff(tenKbslider);
			sliderOff(hundredKbslider);
		
		sliderVector.add(oneKbslider);
		sliderVector.add(tenKbslider);
	//	sliderVector.add(hundredKbslider);
		textFieldVector=new Vector<JTextField>(5);
		textFieldVector.add(oneKbtextField);//, , , , ;
		textFieldVector.add(tenKbtextField);
		textFieldVector.add(hundredKbtextField);

		// Create the data model for this example
		

		
	}
	//it will store values of textfields in a vector and return them values are files kb information
	public Vector getTextFieldValues(){
		Vector<Integer> v=new Vector<Integer>(3);
		
	//private JTextField oneKbtextField, tenKbtextField, hundredKbtextField, thousandKbtextField, twoThousandKbtextField;
		
		Integer a,b,c;//=oneKbtextField.getText();
		a=b=c=0;
		if(!oneKbtextField.getText().equals("")) a=Integer.parseInt(oneKbtextField.getText());
		if(!tenKbtextField.getText().equals("")) b=Integer.parseInt(tenKbtextField.getText());
	//	if(!hundredKbtextField.getText().equals("")) c=Integer.parseInt(hundredKbtextField.getText());
		
		v.add(a);
		v.add(b);
		v.add(c);
		return v;
	}
private void sliderOff(JSlider js){
}


	// Main entry point for this example
	public static void main( String args[] )
	{
		// Create an instance of the test application
		DynamicWorkLoadSpecificationPanel mainFrame	= new DynamicWorkLoadSpecificationPanel();
		//mainFrame.setVisible( true );
		JFrame frame= new JFrame();
		frame.getContentPane().add(mainFrame,BorderLayout.CENTER);
		frame.setSize(500, 200);
		frame.setVisible(true);
	}
}