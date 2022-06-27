package com.jpoolrunner.testingGraphExamples;

//Example from http://www.crionics.com/products/opensource/faq/swing_ex/SwingExamples.html
/* (swing1.1beta3) */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
* @version 1.0 11/09/98
*/

class RowHeaderRenderer extends JLabel implements ListCellRenderer {

RowHeaderRenderer(JTable table) {
 JTableHeader header = table.getTableHeader();
 setOpaque(true);
 setBorder(UIManager.getBorder("TableHeader.cellBorder"));
 setHorizontalAlignment(CENTER);
 setForeground(header.getForeground());
 setBackground(header.getBackground());
 setFont(header.getFont());
}

public Component getListCellRendererComponent(JList list, Object value,
   int index, boolean isSelected, boolean cellHasFocus) {
 setText((value == null) ? "" : value.toString());
 return this;
}
}

public class RowHeaderExample extends JFrame {

public RowHeaderExample() {
 super("Row Header Example");
 setSize(300, 150);

 ListModel lm = new AbstractListModel() {
   String headers[] = { "a", "b", "c", "d", "e", "f", "g", "h", "i" };

   public int getSize() {
     return headers.length;
   }

   public Object getElementAt(int index) {
     return headers[index];
   }
 };

 DefaultTableModel dm = new DefaultTableModel(lm.getSize(),2);
 
 JTable table = new JTable(dm);
 table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

 JList rowHeader = new JList(lm);
 rowHeader.setFixedCellWidth(50);

 rowHeader.setFixedCellHeight(table.getRowHeight()
     );
 //                           + table.getIntercellSpacing().height);
 dm.addRow(new Object[] { 2,2 });

 //rowHeader.setCellRenderer(new RowHeaderRenderer(table));
 //////////////////////////////////////////////////////////////////////////////
 DefaultTableModel model = new DefaultTableModel();
	JTable table2 = new JTable(model);
	model.addColumn("");

	model.addColumn("50th Percentile(ms)");
	model.addColumn("90th Percentile(ms)");
	model.addColumn("95th Percentile(ms)");
	model.addColumn("Average Response Time(ms)");

	

	model.addRow(new Object[] { "<html><font color=\"red\"><b>hello!</b></font></html>",1,2,3, 4 });

	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	centerRenderer.setHorizontalAlignment(SwingConstants.CENTER );
	table2.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
	table2.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
	
   // Component c = centerRenderer.getTableCellRendererComponent(table2,0, 0); 

	
	 
	 JScrollPane scroll = new JScrollPane(table2);

// JScrollPane scroll = new JScrollPane(table);
 //scroll.setRowHeaderView(rowHeader);
 getContentPane().add(scroll, BorderLayout.CENTER);
}

public static void main(String[] args) {
 RowHeaderExample frame = new RowHeaderExample();
 frame.addWindowListener(new WindowAdapter() {
   public void windowClosing(WindowEvent e) {
     System.exit(0);
   }
 });
 frame.setVisible(true);
}
}
