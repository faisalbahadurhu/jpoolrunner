package com.jpoolrunner.clientside;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

public class StaticWorkLoadSpecificationPanel extends JPanel 
 {
	// Instance attributes used in this example
	private	JPanel		innerPanel;
	private	Vector		listData;
	private JTextField oneKbtextField, tenKbtextField, hundredKbtextField, thousandKbtextField, twoThousandKbtextField;
	private int oneKbValue,tenKbValue,hundredKbValue,thousandKbValue,twoThousandKbValue;
	private JSlider oneKbslider,tenKbslider,hundredKbslider,thousandKbslider,twoThousandKbslider;
	private JCheckBox oneKbCheckBox,tenKbcheckBox,hundredKbcheckBox,thousandKbcheckBox,twoThousandKbcheckBox;
	private Vector<JSlider> sliderVector;// this vector is used to keep JSlider references so that in event handling of each JSlider we can iterate through it and extract all Jsslider references and add values of enabled Jslider to total variable and then set the maximum value of slider(in which event handling is performed) to 100- totalvalue
	private Vector<JTextField> textFieldVector;// this vector is used to keep JTextfield references so that in event handling of each JSlider we can iterate through it and extract all Jsslider references and add values of enabled Jslider to total variable and then set the maximum value of slider(in which event handling is performed) to 100- totalvalue


	
	
	// Constructor of main frame
	public StaticWorkLoadSpecificationPanel()
	{
		this.setLayout(new BorderLayout());

		// Create a panel to hold all other components
		
		//JPanel mainPanel=new JPanel(new BorderLayout());
		
		JPanel scrollingPanel = new JPanel();
		scrollingPanel.setLayout(new BorderLayout());
		
		innerPanel = new JPanel();
		innerPanel.setLayout(new GridLayout(5, 1, 0, 0));
		
		scrollingPanel.add(innerPanel,BorderLayout.CENTER);

		JScrollPane pane=new JScrollPane(scrollingPanel);
		//JPanel
		//pane.
		//getContentPane().add( topPanel );
   //  JScrollPane pane=new JScrollPane(topPanel); 
	//	add( pane,BorderLayout.CENTER );
		add( pane,BorderLayout.CENTER );
		sliderVector=new Vector<JSlider>(5);//it contains 5 JSlider Objects we will iterate over this vector and if a slider is enable then its value is taken in event handling of each slider

		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.WHITE, 1, true));
		panel.setPreferredSize(new Dimension(3, 10));
		innerPanel.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(50,50));
		panel.add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new BorderLayout());
		
		 oneKbCheckBox = new JCheckBox();
		oneKbCheckBox.setSelected(true);
		oneKbCheckBox.addItemListener(new ItemListener() {
		      public void itemStateChanged(ItemEvent e) {
		         // System.out.println("Checked? " + check.isSelected());
		    	  if(oneKbCheckBox.isSelected()){
		    		  oneKbtextField.setEnabled(true);
		    		//  oneKbtextField.setText((oneKbslider.getValue())*10+"");// so that it can display 70 istead of 7
		    		  oneKbtextField.setText("");// 

		    		  oneKbslider.setEnabled(true);
		    		  oneKbslider.setValue(0);
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
		panel_5.setPreferredSize(new Dimension(150,100));
		panel_2.add(panel_5, BorderLayout.CENTER);
		JLabel lblFileSize = new JLabel("Simulating 1 KB file"+"\u2243"+"100ms): Access Frequency (%) :");

		//JLabel lblFileSize = new JLabel("File Size=1 KB: Simulation("+"\u2243"+"100ms): Access Frequency (%) :");
		panel_5.add(lblFileSize);
		
		oneKbtextField = new JTextField("30");
		oneKbtextField.setEditable(false);
		//oneKbtextField.setToolTipText("Must Press Enter when u done!");
		oneKbtextField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			/*	try {
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
		        }*/
			}
		});
	/*	oneKbtextField.addFocusListener(new FocusListener() {
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

		});*/
		
		panel_5.add(oneKbtextField);
		oneKbtextField.setColumns(3);
		
		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(80,50));
		panel.add(panel_3, BorderLayout.EAST);
		panel_3.setLayout(new BorderLayout(0, 0));
	//	putClientProperty("JComponent.sizeVariant", "mini");

		// oneKbslider = new JSlider(SwingConstants.HORIZONTAL,1,100,10);
		 oneKbslider = new JSlider(SwingConstants.HORIZONTAL,0,10,0);
		 oneKbslider.setValue(3);
		
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
			         	//  total*=10;
			         	  source.setMaximum(10-total);
			         	 int value=source.getValue()*10;
				        	oneKbtextField.setText(value+"");
				        //	oneKbValue=value;
				        	//	private int oneKbValue,tenKbValue,hundredKbValue,thousandKbValue,twoThousandKbValue;

				        	//oneKbValue=
				        //		staticRequest.setText(String.valueOf(slider.getValue()*10)+" %");

			    }
		 });
		panel_3.add(oneKbslider, BorderLayout.CENTER);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(Color.WHITE));
		innerPanel.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_7 = new JPanel();
		panel_7.setPreferredSize(new Dimension(50,50));
		panel_6.add(panel_7, BorderLayout.WEST);
		panel_7.setLayout(new BorderLayout(0, 0));
		
	    tenKbcheckBox = new JCheckBox("");
		tenKbcheckBox.setSelected(true);
		tenKbcheckBox.addItemListener(new ItemListener() {
		      public void itemStateChanged(ItemEvent e) {
		         // System.out.println("Checked? " + check.isSelected());
		    	  if(tenKbcheckBox.isSelected()){
		    		  tenKbtextField.setEnabled(true);
		    		//  tenKbtextField.setText((tenKbslider.getValue())*10+"");// so that it can display 30 istead of 3
		    		  tenKbtextField.setText("");// 

		    		  tenKbslider.setEnabled(true);
		    		  tenKbslider.setValue(0);
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
		 tenKbslider.setValue(5);
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
		         	//  total*=10;
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
	//	JLabel lblFileSize = new JLabel("Simulating 1 KB file"+"\u2243"+"100ms): Access Frequency (%) :");

		JLabel lblNewLabel = new JLabel("Simulating 10KB file"+"\u2243"+"200ms): Access Frequency (%) :");
		panel_11.add(lblNewLabel);
		
		tenKbtextField = new JTextField("50");
		tenKbtextField.setEditable(false);
		tenKbtextField.setToolTipText("Must Press Enter when u done!");
		tenKbtextField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			/*	try {
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
		        }*/
			}
		});
	/*	tenKbtextField.addFocusListener(new FocusListener() {
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

		});*/
		panel_11.add(tenKbtextField);
		tenKbtextField.setColumns(3);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(Color.WHITE));
		innerPanel.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_10 = new JPanel();
		panel_10.setPreferredSize(new Dimension(50,50));

		panel_4.add(panel_10, BorderLayout.WEST);
		panel_10.setLayout(new BorderLayout(0, 0));
		
		hundredKbcheckBox = new JCheckBox("");
		hundredKbcheckBox.setSelected(true);
		hundredKbcheckBox.addItemListener(new ItemListener() {
		      public void itemStateChanged(ItemEvent e) {
		         // System.out.println("Checked? " + check.isSelected());
		    	  if(hundredKbcheckBox.isSelected()){
		    		  hundredKbtextField.setEnabled(true);
		    		//  hundredKbtextField.setText((hundredKbslider.getValue())*10+"");

		    		  hundredKbtextField.setText("");
		    		  hundredKbslider.setEnabled(true);
		    		  hundredKbslider.setValue(0);
		    		  
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
		 hundredKbslider.setValue(2);
		 hundredKbslider.addChangeListener(new ChangeListener(){
			 public void stateChanged(ChangeEvent e) {
			        JSlider source = (JSlider)e.getSource();
			        
			        Iterator iterator=sliderVector.iterator();
		            int total=0;
		         	  while(iterator.hasNext()){
		         	 JSlider slider=(JSlider) iterator.next();
		         	 if(!slider.equals(source)){// ie if extracted slider is not this one ie oneKbslider .because we dont wanna add value of this slider
		         		 if(slider.isEnabled()){
		         			total+=slider.getValue(); 
		         		 }
		         	 }
		         		  
		         	  }
		         	// total*=10;
		         	  source.setMaximum(10-total);
		         	 int value=source.getValue()*10;
			        	hundredKbtextField.setText(value+"");
			           
			        
			    }
		 });
		 
		panel_12.add(hundredKbslider, BorderLayout.CENTER);
		
		JPanel panel_13 = new JPanel();
		panel_4.add(panel_13, BorderLayout.CENTER);
		
		JLabel lblNewLabel_1 = new JLabel("Simulating 100KB file"+"\u2243"+"300ms): Access Frequency (%) :");
		panel_13.add(lblNewLabel_1);
		
		hundredKbtextField = new JTextField("20");
		hundredKbtextField.setEditable(false);
		hundredKbtextField.setToolTipText("Must Press Enter when u done!");
		hundredKbtextField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			/*	try {
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
		        }*/
			}
		});
	/*	hundredKbtextField.addFocusListener(new FocusListener() {
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

		});*/
		panel_13.add(hundredKbtextField);
		hundredKbtextField.setColumns(3);
		
		JPanel panel_14 = new JPanel();
		panel_14.setBorder(new LineBorder(Color.WHITE));
		innerPanel.add(panel_14);
		panel_14.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_15 = new JPanel();
		panel_15.setPreferredSize(new Dimension(50,50));
		panel_14.add(panel_15, BorderLayout.WEST);
		panel_15.setLayout(new BorderLayout(0, 0));
		
		thousandKbcheckBox = new JCheckBox(" ");
		thousandKbcheckBox.addItemListener(new ItemListener() {
		      public void itemStateChanged(ItemEvent e) {
		         // System.out.println("Checked? " + check.isSelected());
		    	  if(thousandKbcheckBox.isSelected()){
		    		  thousandKbtextField.setEnabled(true);
		    		//  thousandKbtextField.setText((thousandKbslider.getValue())*10+"");

		    		  thousandKbtextField.setText("");
		    		  thousandKbslider.setEnabled(true);
		    		  thousandKbslider.setValue(0);
		    		  
		    	  }
		    	  else if(!thousandKbcheckBox.isSelected()){
		    		  thousandKbtextField.setEnabled(false);
		    		  thousandKbtextField.setText("");
		    		  thousandKbslider.setEnabled(false);
		    	  }
		        }
		      });
		panel_15.add(thousandKbcheckBox, BorderLayout.CENTER);
		
		JPanel panel_16 = new JPanel();
		panel_16.setPreferredSize(new Dimension(80,50));
		panel_14.add(panel_16, BorderLayout.EAST);
		panel_16.setLayout(new BorderLayout(0, 0));
		
		 thousandKbslider = new JSlider(SwingConstants.HORIZONTAL,0,10,0);
		 thousandKbslider.setValue(0);
		 thousandKbslider.setEnabled(false);
		 thousandKbslider.addChangeListener(new ChangeListener(){
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
		         	// total*=10;
		         	  source.setMaximum(10-total);
		         	 int value=source.getValue()*10;
			        	thousandKbtextField.setText(value+"");
			           
			        
			    }
		 });
		panel_16.add(thousandKbslider, BorderLayout.CENTER);
		
		JPanel panel_17 = new JPanel();
		panel_14.add(panel_17, BorderLayout.CENTER);
		
		JLabel lblNewLabel_2 = new JLabel("Simulating 1000KB file"+"\u2243"+"500ms): Access Frequency (%) :");
		panel_17.add(lblNewLabel_2);
		
		thousandKbtextField = new JTextField();
		thousandKbtextField.setEditable(false);
		thousandKbtextField.setToolTipText("Must Press Enter when u done!");
		thousandKbtextField.setToolTipText("Must Press Enter when u done!");
		thousandKbtextField.setEnabled(false);
		thousandKbtextField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			/*	try {
					thousandKbslider.setValue(Integer.parseInt(thousandKbtextField.getText()));		
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
		         	  if((Integer.parseInt(thousandKbtextField.getText())+total)>100){
		         		 thousandKbtextField.setText("0");
		         		thousandKbslider.setValue(0);
		         	  }
		        }  catch(NumberFormatException nfe){
		        	JOptionPane.showMessageDialog(null, "Enter an integer value");
		        }*/
			}
		});
		/*thousandKbtextField.addFocusListener(new FocusListener() {
		      public void focusGained(FocusEvent e) {
		    	//  thousandKbtextField.setToolTipText("Must Press Enter when u done!"); 
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
		    	  thousandKbtextField.setText(thousandKbslider.getValue()+"");
 
		      }

		});*/
		panel_17.add(thousandKbtextField);
		thousandKbtextField.setColumns(3);
		
		JPanel panel_18 = new JPanel();
		panel_18.setBorder(new LineBorder(Color.WHITE));
		innerPanel.add(panel_18);
		panel_18.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_19 = new JPanel();
		panel_19.setPreferredSize(new Dimension(50,50));

		panel_18.add(panel_19, BorderLayout.WEST);
		panel_19.setLayout(new BorderLayout(0, 0));
		
		twoThousandKbcheckBox = new JCheckBox();
		twoThousandKbcheckBox.addItemListener(new ItemListener() {
		      public void itemStateChanged(ItemEvent e) {
		         // System.out.println("Checked? " + check.isSelected());
		    	  if(twoThousandKbcheckBox.isSelected()){
		    		  twoThousandKbtextField.setEnabled(true);
		    		//  twoThousandKbtextField.setText((twoThousandKbslider.getValue())*10+"");
		    		  twoThousandKbtextField.setText("");

		    		  twoThousandKbslider.setEnabled(true);
		    		  twoThousandKbslider.setValue(0);
		    		  
		    	  }
		    	  else if(!twoThousandKbcheckBox.isSelected()){
		    		  twoThousandKbtextField.setEnabled(false);
		    		  twoThousandKbtextField.setText("");
		    		  twoThousandKbslider.setEnabled(false);
		    	  }
		        }
		      });
		panel_19.add(twoThousandKbcheckBox, BorderLayout.CENTER);
		
		JPanel panel_20 = new JPanel();
		panel_20.setPreferredSize(new Dimension(80,50));

		panel_18.add(panel_20, BorderLayout.EAST);
		panel_20.setLayout(new BorderLayout(0, 0));
		
		 twoThousandKbslider = new JSlider(SwingConstants.HORIZONTAL,0,10,0);
		 twoThousandKbslider.setValue(0);
		 twoThousandKbslider.setEnabled(false);
		 twoThousandKbslider.addChangeListener(new ChangeListener(){
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
		         	// total*=10;
		         	  source.setMaximum(10-total);
		         	 int value=source.getValue()*10;
			        	twoThousandKbtextField.setText(value+"");
			           
			        
			    }
		 });
		panel_20.add(twoThousandKbslider, BorderLayout.CENTER);
		
		sliderVector.add(oneKbslider);
		sliderVector.add(tenKbslider);
		sliderVector.add(hundredKbslider);
		sliderVector.add(thousandKbslider);
		sliderVector.add(twoThousandKbslider);
		
		JPanel panel_21 = new JPanel();
		panel_18.add(panel_21, BorderLayout.CENTER);
		//
		JLabel lblNewLabel_3 = new JLabel("Simulating 1200KB file"+"\u2243"+"1000ms):Access Frequency(%):");
		panel_21.add(lblNewLabel_3);
		
		twoThousandKbtextField = new JTextField();
		twoThousandKbtextField.setEditable(false);
		textFieldVector=new Vector<JTextField>(5);
		textFieldVector.add(oneKbtextField);//, , , , ;
		textFieldVector.add(tenKbtextField);
		textFieldVector.add(hundredKbtextField);
		textFieldVector.add(thousandKbtextField);
		textFieldVector.add(twoThousandKbtextField);
		twoThousandKbtextField.setToolTipText("Must Press Enter when u done!");
		twoThousandKbtextField.setEnabled(false);
		twoThousandKbtextField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			/*	try {
					twoThousandKbslider.setValue(Integer.parseInt(twoThousandKbtextField.getText()));
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
		         	  if((Integer.parseInt(twoThousandKbtextField.getText())+total)>100){
		         		 twoThousandKbtextField.setText("0");
		         		twoThousandKbslider.setValue(0);
		         	  }
		        }  catch(NumberFormatException nfe){
		        	JOptionPane.showMessageDialog(null, "Enter an integer value");
		        }*/
			}
		});
	/*	twoThousandKbtextField.addFocusListener(new FocusListener() {
		      public void focusGained(FocusEvent e) {
		    	 // twoThousandKbtextField.setToolTipText("Must Press Enter when u done!");
		    	  //twoThousandKbtextField.
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
		    	  twoThousandKbtextField.setText(twoThousandKbslider.getValue()+"");

		      }

		});*/
		panel_21.add(twoThousandKbtextField);
		twoThousandKbtextField.setColumns(3);

		// Create the data model for this example
		

		
	}
//it will store values of textfields in a vector and return them values are files kb information
public Vector getTextFieldValues(){
	Vector<Integer> v=new Vector<Integer>(5);
	
//private JTextField oneKbtextField, tenKbtextField, hundredKbtextField, thousandKbtextField, twoThousandKbtextField;
	
	Integer a,b,c,d,e;//=oneKbtextField.getText();
	a=b=c=d=e=0;
	if(!oneKbtextField.getText().equals("")) a=Integer.parseInt(oneKbtextField.getText());
	if(!tenKbtextField.getText().equals("")) b=Integer.parseInt(tenKbtextField.getText());
	if(!hundredKbtextField.getText().equals("")) c=Integer.parseInt(hundredKbtextField.getText());
	if(!thousandKbtextField.getText().equals("")) d=Integer.parseInt(thousandKbtextField.getText());
	if(!twoThousandKbtextField.getText().equals("")) e=Integer.parseInt(twoThousandKbtextField.getText());

	v.add(a);
	v.add(b);
	v.add(c);
	v.add(d);
	v.add(e);

	return v;
}
	


	// Main entry point for this example
	public static void main( String args[] )
	{
		// Create an instance of the test application
		StaticWorkLoadSpecificationPanel mainFrame	= new StaticWorkLoadSpecificationPanel();
		//mainFrame.setVisible( true );
		JFrame frame= new JFrame();
		frame.getContentPane().add(mainFrame,BorderLayout.CENTER);
		frame.setSize(500, 200);
		frame.setVisible(true);
	}
	/*
	 
	 private JTextField oneKbtextField, tenKbtextField, hundredKbtextField, thousandKbtextField, twoThousandKbtextField;
	private JSlider oneKbslider,tenKbslider,hundredKbslider,thousandKbslider,twoThousandKbslider;
	private JCheckBox oneKbCheckBox,tenKbcheckBox,hundredKbcheckBox,thousandKbcheckBox,twoThousandKbcheckBox;
	*/
	public void setDefaultcheckedInDefinescenarionDialogBox(){//it will set the default setting of workload of a Test plan 
		oneKbCheckBox.setSelected(true);
		tenKbcheckBox.setSelected(true);
		hundredKbcheckBox.setSelected(true);
		thousandKbcheckBox.setSelected(false);
		twoThousandKbcheckBox.setSelected(false);
		oneKbtextField.setText("30");
		tenKbtextField.setText("50");
		hundredKbtextField.setText("20");
		thousandKbtextField.setText("");
		twoThousandKbtextField.setText("");
	}
	

}