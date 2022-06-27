package com.jpoolrunner.clientside;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
// No need of this class now I am using DynamicListComponentNew instead of this class
public class DynamicListComponent extends JPanel 	implements	ActionListener,ListSelectionListener
 {
	// Instance attributes used in this example
	private	JPanel		topPanel;
	private	JList		listbox;
	private	Vector		listData;
	private	JButton		addButton;
	private	JButton		removeButton;
	private	JScrollPane scrollPane;
	private JLabel lblFileSize;
	private JTextField fileAndProcessingTextField;
	private JLabel lblProbabilityOfAccess;
	private JTextField probTextfield;
	private JPanel inputPanel;
	private JPanel addRemovePanel;

	
	public void setLabel(String label){
		this.lblFileSize.setText(label);
	}
	// Constructor of main frame
	public DynamicListComponent()
	{
		// Set the frame characteristics
	//	setTitle( "Advanced List Box Application" );
		//setSize( 400, 200 );
		//setBackground( Color.gray );

		// Create a panel to hold all other components
		topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout() );
		//getContentPane().add( topPanel );

		add( topPanel,BorderLayout.CENTER );

		// Create the data model for this example
		listData = new Vector();

		// Create a new listbox control
		listbox = new JList( listData );
		
		listbox.addListSelectionListener( this );
		listbox.setVisibleRowCount(3);

		// Add the listbox to a scrolling pane
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(listbox);
		topPanel.add( scrollPane, BorderLayout.CENTER );

		CreateDataEntryPanel();
	}


	public void CreateDataEntryPanel()
	{
		// Create a panel to hold all other components
		JPanel dataPanel = new JPanel();
		topPanel.add( dataPanel, BorderLayout.SOUTH );
		dataPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		inputPanel = new JPanel();
		dataPanel.add(inputPanel);
		
		addRemovePanel = new JPanel();
		dataPanel.add(addRemovePanel);
		addRemovePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// Create some function buttons
		addButton = new JButton( "Add" );
		addRemovePanel.add( addButton );
		addButton.addActionListener( this );
		
		lblFileSize = new JLabel("File Size(KB):");
		inputPanel.add(lblFileSize);
		
		fileAndProcessingTextField = new JTextField();
		fileAndProcessingTextField.setColumns(4);
		inputPanel.add(fileAndProcessingTextField);
		
		lblProbabilityOfAccess = new JLabel("Probability of Access(%) :");
		inputPanel.add(lblProbabilityOfAccess);
		
		probTextfield = new JTextField();
		inputPanel.add(probTextfield);
		probTextfield.setColumns(3);
		//textField.setColumns(10);

		removeButton = new JButton( "Delete" );
		addRemovePanel.add( removeButton );
		removeButton.addActionListener( this );
		/*
		 * 
		 JPanel dataPanel = new JPanel();
		dataPanel.setLayout( new BorderLayout() );
		topPanel.add( dataPanel, BorderLayout.SOUTH );

		// Create some function buttons
		addButton = new JButton( "Add" );
		dataPanel.add( addButton, BorderLayout.WEST );
		addButton.addActionListener( this );

		removeButton = new JButton( "Delete" );
		dataPanel.add( removeButton, BorderLayout.EAST );
		removeButton.addActionListener( this );

		// Create a text field for data entry and display
		dataField = new JTextField();
		dataPanel.add( dataField, BorderLayout.CENTER );*/
	}

	// Handler for list selection changes
 	public void valueChanged( ListSelectionEvent event )
 	{
 	/*	// See if this is a listbox selection and the
 		// event stream has settled
		if( event.getSource() == listbox
						&& !event.getValueIsAdjusting() )
		{
			// Get the current selection and place it in the
			// edit field
			String stringValue = (String)listbox.getSelectedValue();
			if( stringValue != null )
				dataField.setText( stringValue );
		}*/
 	}

	// Handler for button presses
	public void actionPerformed( ActionEvent event )
	{
		if( event.getSource() == addButton )
		{
			// Get the text field value
			//String stringValue = dataField.getText();
		//	dataField.setText( "" );
			
			String stringValue = fileAndProcessingTextField.getText();
			fileAndProcessingTextField.setText( "" );
			String stringValue2 = probTextfield.getText();
			probTextfield.setText( "" );
			// Add this item to the list and refresh
			if( stringValue != null && stringValue2 !=null )
			{
				listData.addElement( "File size= "+stringValue+" KB" +" Probability Of Access= "+ stringValue2+" %" );
				//listData.addElement( new JTextField(stringValue) );
				listbox.setListData( listData );
				scrollPane.revalidate();
				scrollPane.repaint();
			}
		}

		if( event.getSource() == removeButton )
		{
			// Get the current selection
			int selection = listbox.getSelectedIndex();
			if( selection >= 0 )
			{
				// Add this item to the list and refresh
				listData.removeElementAt( selection );
				listbox.setListData( listData );
				scrollPane.revalidate();
				scrollPane.repaint();

				// As a nice touch, select the next item
				if( selection >= listData.size() )
					selection = listData.size() - 1;
				listbox.setSelectedIndex( selection );
			}
		}
	}

	// Main entry point for this example
	public static void main( String args[] )
	{
		// Create an instance of the test application
		DynamicListComponent mainFrame	= new DynamicListComponent();
		//mainFrame.setVisible( true );
		JFrame frame= new JFrame();
		frame.getContentPane().add(mainFrame,BorderLayout.CENTER);
		frame.setSize(400, 200);
		frame.setVisible(true);
	}
}