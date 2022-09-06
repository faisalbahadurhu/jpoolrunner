package com.jpoolrunner.clientside;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SplashScreen extends JWindow implements Runnable {

    static boolean isRegistered;
    private static JProgressBar progressBar = new JProgressBar();
    private static SplashScreen execute;
    private static int count;
    private static Timer timer1;

    public SplashScreen() {
    	//super();
    	Container container = getContentPane();
        container.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(new javax.swing.border.EtchedBorder());
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(10, 10, 348, 150);
        panel.setLayout(null);
        container.add(panel);

        JLabel label = new JLabel("Hello World!");
        label.setFont(new Font("Verdana", Font.BOLD, 14));
        label.setBounds(85, 25, 280, 30);
        panel.add(label);

        progressBar.setMaximum(50);
        progressBar.setBounds(55, 180, 250, 15);
    //    container.add(progressBar);
       // loadProgressBar();
        setSize(370, 215);
        setLocationRelativeTo(null);
       
    }

public void run(){
	 
  //   pack();
     setVisible(true);
}

    public static void main(String[] args) {
       // execute = new SplashScreen();
    }
};