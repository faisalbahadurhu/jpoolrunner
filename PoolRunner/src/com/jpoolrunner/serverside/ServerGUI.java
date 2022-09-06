package com.jpoolrunner.serverside;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.Component;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

public class ServerGUI {

	private JFrame frame;
	JTextArea textArea;
	private JTextField textFieldRequest;
	private JTextField textFieldResponse;
	JProgressBar progressBar;
	ThreadPoolingServerSystem threadPoolserverSystem;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGUI window = new ServerGUI();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServerGUI() {
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Simulated Server");
		frame.setBounds(100, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		 textArea = new JTextArea();
		// DefaultCaret caret=(DefaultCaret) textArea.getCaret();
		// caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		 textArea.setEditable(false);
		textArea.setForeground(Color.WHITE);
		textArea.setBackground(Color.BLACK);
		//textArea.setWrapStyleWord(true);
		//textArea.setLineWrap(true);
		JScrollPane jScrollPane1 = new JScrollPane(textArea);
		new SmartScroller(jScrollPane1);
		//frame.addWindowListener(new WindowListener(){});
		frame.addWindowListener(new WindowListener(){

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				// super.processWindowEvent(e);
				    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
				    	
				      System.exit(0);
				    }
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}});
		frame.getContentPane().add(jScrollPane1, BorderLayout.CENTER);
		JPanel panel= new JPanel();
		panel.setPreferredSize(new Dimension(100, 40));
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		
		 progressBar = new JProgressBar();
		progressBar.setVisible(false);
	/*	progressBar.setForeground(Color.blue);
		UIManager.put("ProgressBar.background", Color.BLUE);
		UIManager.put("ProgressBar.foreground", Color.BLUE);
		UIManager.put("ProgressBar.selectionBackground", Color.RED);
		UIManager.put("ProgressBar.selectionForeground", Color.GREEN);*/
		
	//	progressBar.setIndeterminate(true);
		panel.add(progressBar);
		
		JLabel lblTasksReceived = new JLabel("Requests Received");
		panel.add(lblTasksReceived);
		
		textFieldRequest = new JTextField();
		textFieldRequest.setEditable(false);
		panel.add(textFieldRequest);
		textFieldRequest.setColumns(10);
		
		JLabel lblTaskSent = new JLabel("Responses Sent");
		panel.add(lblTaskSent);
		
		textFieldResponse = new JTextField();
		textFieldResponse.setEditable(false);
		panel.add(textFieldResponse);
		textFieldResponse.setColumns(10);
		
		threadPoolserverSystem=new ThreadPoolingServerSystem(textArea,textFieldRequest,textFieldResponse,progressBar);
		
		frame.setVisible(true);
		threadPoolserverSystem.startListenerNow();
		
	}
/*	public void restartThreadPoolingServerSystem(){
	//	textArea.append("\nServer is restarting!...");
		this.threadPoolserverSystem.stopListening();
		this.threadPoolserverSystem=null;
		textArea.append("\nServer is restarting!...");
		threadPoolserverSystem=new ThreadPoolingServerSystem(textArea,textFieldRequest,textFieldResponse,progressBar);
		threadPoolserverSystem.startListenerNow();
		
	}*/
	
	public class SmartScroller implements AdjustmentListener
	{
		public final static int HORIZONTAL = 0;
		public final static int VERTICAL = 1;

		public final static int START = 0;
		public final static int END = 1;

		private int viewportPosition;

		private JScrollBar scrollBar;
		private boolean adjustScrollBar = true;

		private int previousValue = -1;
		private int previousMaximum = -1;

		/**
		 *  Convenience constructor.
		 *  Scroll direction is VERTICAL and viewport position is at the END.
		 *
		 *  @param scrollPane the scroll pane to monitor
		 */
		public SmartScroller(JScrollPane scrollPane)
		{
			this(scrollPane, VERTICAL, END);
		}

		/**
		 *  Convenience constructor.
		 *  Scroll direction is VERTICAL.
		 *
		 *  @param scrollPane the scroll pane to monitor
		 *  @param viewportPosition valid values are START and END
		 */
		public SmartScroller(JScrollPane scrollPane, int viewportPosition)
		{
			this(scrollPane, VERTICAL, viewportPosition);
		}

		/**
		 *  Specify how the SmartScroller will function.
		 *
		 *  @param scrollPane the scroll pane to monitor
		 *  @param scrollDirection indicates which JScrollBar to monitor.
		 *                         Valid values are HORIZONTAL and VERTICAL.
		 *  @param viewportPosition indicates where the viewport will normally be
		 *                          positioned as data is added.
		 *                          Valid values are START and END
		 */
		public SmartScroller(JScrollPane scrollPane, int scrollDirection, int viewportPosition)
		{
			if (scrollDirection != HORIZONTAL
			&&  scrollDirection != VERTICAL)
				throw new IllegalArgumentException("invalid scroll direction specified");

			if (viewportPosition != START
			&&  viewportPosition != END)
				throw new IllegalArgumentException("invalid viewport position specified");

			this.viewportPosition = viewportPosition;

			if (scrollDirection == HORIZONTAL)
				scrollBar = scrollPane.getHorizontalScrollBar();
			else
				scrollBar = scrollPane.getVerticalScrollBar();

			scrollBar.addAdjustmentListener( this );

			//  Turn off automatic scrolling for text components

			Component view = scrollPane.getViewport().getView();

			if (view instanceof JTextComponent)
			{
				JTextComponent textComponent = (JTextComponent)view;
				DefaultCaret caret = (DefaultCaret)textComponent.getCaret();
				caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
			}
		}

		@Override
		public void adjustmentValueChanged(final AdjustmentEvent e)
		{
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run()
				{
					checkScrollBar(e);
				}
			});
		}

		/*
		 *  Analyze every adjustment event to determine when the viewport
		 *  needs to be repositioned.
		 */
		private void checkScrollBar(AdjustmentEvent e)
		{
			//  The scroll bar listModel contains information needed to determine
			//  whether the viewport should be repositioned or not.

			JScrollBar scrollBar = (JScrollBar)e.getSource();
			BoundedRangeModel listModel = scrollBar.getModel();
			int value = listModel.getValue();
			int extent = listModel.getExtent();
			int maximum = listModel.getMaximum();

			boolean valueChanged = previousValue != value;
			boolean maximumChanged = previousMaximum != maximum;

			//  Check if the user has manually repositioned the scrollbar

			if (valueChanged && !maximumChanged)
			{
				if (viewportPosition == START)
					adjustScrollBar = value != 0;
				else
					adjustScrollBar = value + extent >= maximum;
			}

			//  Reset the "value" so we can reposition the viewport and
			//  distinguish between a user scroll and a program scroll.
			//  (ie. valueChanged will be false on a program scroll)

			if (adjustScrollBar && viewportPosition == END)
			{
				//  Scroll the viewport to the end.
				scrollBar.removeAdjustmentListener( this );
				value = maximum - extent;
				scrollBar.setValue( value );
				scrollBar.addAdjustmentListener( this );
			}

			if (adjustScrollBar && viewportPosition == START)
			{
				//  Keep the viewport at the same relative viewportPosition
				scrollBar.removeAdjustmentListener( this );
				value = value + maximum - previousMaximum;
				scrollBar.setValue( value );
				scrollBar.addAdjustmentListener( this );
			}

			previousValue = value;
			previousMaximum = maximum;
		}
	}


}
