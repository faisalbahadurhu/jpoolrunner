package com.jpoolrunner.clientside;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

public class CompareGraphs extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CompareGraphs frame = new CompareGraphs();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CompareGraphs() {
		setBounds(100, 100, 450, 300);

	}

}
