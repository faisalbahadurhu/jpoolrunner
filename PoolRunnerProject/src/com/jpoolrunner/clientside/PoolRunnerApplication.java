package com.jpoolrunner.clientside;



import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class PoolRunnerApplication {
  boolean packFrame = false;

  //Construct the application
  public PoolRunnerApplication() {
    MainFrame frame = new MainFrame();
    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame) {
      frame.pack();
    }
    else {
      frame.validate();
    }
    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    frame.setVisible(true);
  }
  //Main method
  public static void main(String[] args) {
	  try {			   //   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e1) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
			try {
			     //  UIManager.setLookAndFeel(UIManager.get);
			      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			    }
			    catch(Exception e) {
			      e.printStackTrace();
			    }
		}
	  
    
    new PoolRunnerApplication();
  }
}