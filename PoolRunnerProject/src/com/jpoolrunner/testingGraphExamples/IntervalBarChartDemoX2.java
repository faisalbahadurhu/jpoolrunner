package com.jpoolrunner.testingGraphExamples;

import java.awt.Color;   
import java.awt.Dimension;   
import java.text.DecimalFormat;   
import javax.swing.JFrame;   
import javax.swing.JPanel;   
import org.jfree.chart.ChartPanel;   
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.CategoryAxis;   
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.IntervalBarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;   
import org.jfree.data.category.IntervalCategoryDataset;   
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RefineryUtilities;   
   
public class IntervalBarChartDemoX2 extends ApplicationFrame   
{   
   
    public IntervalBarChartDemoX2(String s)   
    {   
        super(s);   
        JPanel jpanel = createDemoPanel();   
        jpanel.setPreferredSize(new Dimension(600, 270));   
        setContentPane(jpanel);   
    }   
   
    private static IntervalCategoryDataset createDataset(int a, int b, int c, int d,String seriesName)   
    {   
    	DefaultIntervalCategoryDataset defaultintervalcategorydataset=null;
       
     //  Number s2[]={c};Number e2[]={ d};
    	if(seriesName.equals("1kb")){
     	   Number s1[]={a,b};Number e1[]={ c,d };
            Number s2[]={null,null};Number e2[]={null,null};
     	Number start[][] = {s1,s2};   
         Number end[][] = { e1,e2 };  
         String[]  categoryKeys = {"50th%","90th%"};
 		String[] seriesKeys = {seriesName,""};
 		
        
      defaultintervalcategorydataset = new DefaultIntervalCategoryDataset(seriesKeys,categoryKeys,start, end);   
        }
       
       else if(seriesName.equals("2kb")){
    	   Number s1[]={a,b};Number e1[]={ c,d };
           Number s2[]={null,null};Number e2[]={null,null};
           Number s3[]={null,null};Number e3[]={null,null};

    	Number start[][] = {s2,s1};   
        Number end[][] = { e2,e1 };  
        String[]  categoryKeys = {"50th%","90th%"};
		String[] seriesKeys = {"",seriesName};
		
       
     defaultintervalcategorydataset = new DefaultIntervalCategoryDataset(seriesKeys,categoryKeys,start, end);  
       }
       else  if(seriesName.equals("3kb")){
    	   Number s1[]={a,b};Number e1[]={ c,d };
           Number s2[]={null,null};Number e2[]={null,null};
           Number s3[]={null,null};Number e3[]={null,null};
    	Number start[][] = {s2,s1};   
        Number end[][] = {e2, e1};  
        String[]  categoryKeys = {"50th%","90th%"};
		String[] seriesKeys = {"",seriesName};
		
       
     defaultintervalcategorydataset = new DefaultIntervalCategoryDataset(seriesKeys,categoryKeys,start, end);  
       }
       else  if(seriesName.equals("4kb")){
    	   Number s1[]={a,b};Number e1[]={ c,d };
           Number s2[]={null,null};Number e2[]={null,null};
           Number s3[]={null,null};Number e3[]={null,null};
    	Number start[][] = {s2,s1,s3};   
        Number end[][] = { e2,e1,e3};  
        String[]  categoryKeys = {"50th%","90th%"};
		String[] seriesKeys = {"",seriesName,""};
		
       
     defaultintervalcategorydataset = new DefaultIntervalCategoryDataset(seriesKeys,categoryKeys,start, end);  
       }
       
        return defaultintervalcategorydataset;   
    }   
   
    private static JFreeChart createChart(IntervalCategoryDataset intervalcategorydataset,final int n)   
    {   
    //	double itemMargin=.5;
        CategoryAxis categoryaxis = new CategoryAxis("Category");   
        NumberAxis numberaxis = new NumberAxis("Percentage");   
        IntervalBarRenderer intervalbarrenderer = new IntervalBarRenderer();  
     //  intervalbarrenderer.setItemMargin(itemMargin);
        CategoryPlot categoryplot = new CategoryPlot(intervalcategorydataset, categoryaxis, numberaxis, intervalbarrenderer){
            
            /**
             * Override the getLegendItems() method to handle special case.
             *
             * @return the legend items.
             */
           public LegendItemCollection getLegendItems() {

                final LegendItemCollection result = new LegendItemCollection();
                
                for(int i=0;i<n;i++){
                	final CategoryDataset data = getDataset(i);
                    if (data != null) {
                        final CategoryItemRenderer r = getRenderer(i);
                        if (r != null) {
                        	if(i>1){
                        		final LegendItem item = r.getLegendItem(i, 1);
                                result.add(item);
                        	}
                        	else {
                        		final LegendItem item = r.getLegendItem(i, i);
                            result.add(item);
                            }
                            
                        }
                    }	
                	
                }    
                return result;
            }
            
        };  
        JFreeChart jfreechart = new JFreeChart(categoryplot); 
        Color trans = new Color(0,0,0,255);
        jfreechart.setBackgroundPaint(trans);
        categoryplot .setBackgroundPaint(trans);
        // now add another category
        IntervalCategoryDataset dataset2=createDataset(2,3,4,12,"2kb");
        categoryplot.setDataset(1, dataset2);
        categoryplot.mapDatasetToRangeAxis(1, 1);        
		final ValueAxis axis2 = new NumberAxis("ms2");
		categoryplot.setRangeAxis(1, axis2);
		IntervalBarRenderer renderer2 = new IntervalBarRenderer();
	//	renderer2.setItemMargin(itemMargin);
		categoryplot.setRenderer(1, renderer2);
		
		
		IntervalCategoryDataset dataset3=createDataset(1,0,3,1,"3kb");
        categoryplot.setDataset(2, dataset3);
        categoryplot.mapDatasetToRangeAxis(2, 2);        
		final ValueAxis axis3 = new NumberAxis("ms3");
		categoryplot.setRangeAxis(2, axis3);
		IntervalBarRenderer renderer3 = new IntervalBarRenderer();
	//	renderer2.setItemMargin(itemMargin);
		categoryplot.setRenderer(2, renderer3);
		
		IntervalCategoryDataset dataset4=createDataset(1,2,3,4,"4kb");
        categoryplot.setDataset(3, dataset4);
        categoryplot.mapDatasetToRangeAxis(3, 3);        
		final ValueAxis axis4 = new NumberAxis("ms4");
		categoryplot.setRangeAxis(3, axis4);
		IntervalBarRenderer renderer4 = new IntervalBarRenderer();
	//	renderer2.setItemMargin(itemMargin);
		categoryplot.setRenderer(3, renderer4);
		
		
		
        
         
        jfreechart.setBackgroundPaint(Color.white);   
        categoryplot.setBackgroundPaint(Color.lightGray);   
        categoryplot.setDomainGridlinePaint(Color.white);   
        categoryplot.setDomainGridlinesVisible(true);   
        categoryplot.setRangeGridlinePaint(Color.white);   
        return jfreechart;   
    }   
   
    public static JPanel createDemoPanel()   
    {   
        JFreeChart jfreechart = createChart(createDataset(1,2,3,4,"1kb"),4);   
        TextTitle legendText = new TextTitle("This is LEGEND: ");
        legendText.setPosition(RectangleEdge.LEFT);
        jfreechart.addSubtitle(legendText);
        return new ChartPanel(jfreechart);   
    }   
   
    public static void main(String args[])   
    {   
        IntervalBarChartDemoX2 intervalbarchartdemo1 = new IntervalBarChartDemoX2("Interval Bar Chart Demo 1");   
        intervalbarchartdemo1.pack();   
        RefineryUtilities.centerFrameOnScreen(intervalbarchartdemo1);   
        intervalbarchartdemo1.setVisible(true);   
    }   
}