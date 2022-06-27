package com.jpoolrunner.testingGraphExamples;
import java.awt.Color;   
import java.awt.Dimension;   
import java.text.DecimalFormat;   
import javax.swing.JFrame;   
import javax.swing.JPanel;   
import org.jfree.chart.ChartPanel;   
import org.jfree.chart.JFreeChart;   
import org.jfree.chart.axis.CategoryAxis;   
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;   
import org.jfree.chart.renderer.category.IntervalBarRenderer;   
import org.jfree.data.category.DefaultIntervalCategoryDataset;   
import org.jfree.data.category.IntervalCategoryDataset;   
import org.jfree.ui.ApplicationFrame;   
import org.jfree.ui.RefineryUtilities;   
   
public class IntervalBarChartDemoX extends ApplicationFrame   
{   
   
    public IntervalBarChartDemoX(String s)   
    {   
        super(s);   
        JPanel jpanel = createDemoPanel();   
        jpanel.setPreferredSize(new Dimension(500, 270));   
        setContentPane(jpanel);   
    }   
   
    private static IntervalCategoryDataset createDataset()   
    {   
       Number s1[]={1, 1 ,3,4,5 ,1, 1 ,3,4,5};Number e1[]={ 2, 3,7,7,7, 2, 3,7,7,7 };
       Number s2[]={1,2,3,4,5,1,2,3,4,5};Number e2[]={ 2,6,8,8,7,2,6,8,8,7};
       
    	Number start[][] = {s1 ,s2};   
        Number end[][] = { e1,e2, };  
        String[]  categoryKeys = {"1kb", "2kb","3kb","4kb","5kb","1Akb", "2Akb","3Akb","4Akb","5Akb"};
		String[] seriesKeys = {"50th%","90th%"};
    DefaultIntervalCategoryDataset defaultintervalcategorydataset = new DefaultIntervalCategoryDataset(seriesKeys,categoryKeys,start, end);   
   
        return defaultintervalcategorydataset;   
    }   
   
    private static JFreeChart createChart(IntervalCategoryDataset intervalcategorydataset)   
    {   
        CategoryAxis categoryaxis = new CategoryAxis("Category");   
        NumberAxis numberaxis = new NumberAxis("Percentage");  
        ValueAxis yAxis = new SymbolAxis("Bins", new String[]{"0","One 1","Two 2","Three 3","Four 4","Five 5","Six 6","Seven 7","E 8","N 9"});

       // numberaxis.setNumberFormatOverride(new DecimalFormat("0.00%"));   
        IntervalBarRenderer intervalbarrenderer = new IntervalBarRenderer();   
        CategoryPlot categoryplot = new CategoryPlot(intervalcategorydataset, categoryaxis, yAxis, intervalbarrenderer);   
      //  ValueAxis yAxis = new SymbolAxis("Bins", new String[]{"One 1","Two 2","Three 3","Four 4","Five 5","Six 6","Seven 7","E 8","N 9"});
      //  categoryplot.setRangeAxis(yAxis);
        
        JFreeChart jfreechart = new JFreeChart(categoryplot);   
        jfreechart.setBackgroundPaint(Color.white);   
        categoryplot.setBackgroundPaint(Color.lightGray);   
        categoryplot.setDomainGridlinePaint(Color.white);   
        categoryplot.setDomainGridlinesVisible(true);   
        categoryplot.setRangeGridlinePaint(Color.white);   
        return jfreechart;   
    }   
   
    public static JPanel createDemoPanel()   
    {   
        JFreeChart jfreechart = createChart(createDataset());   
        return new ChartPanel(jfreechart);   
    }   
   
    public static void main(String args[])   
    {   
        IntervalBarChartDemoX intervalbarchartdemo1 = new IntervalBarChartDemoX("Interval Bar Chart Demo 1");   
        intervalbarchartdemo1.pack();   
        RefineryUtilities.centerFrameOnScreen(intervalbarchartdemo1);   
        intervalbarchartdemo1.setVisible(true);   
    }   
}