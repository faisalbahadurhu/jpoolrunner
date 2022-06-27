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
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;   
import org.jfree.data.category.IntervalCategoryDataset;   
import org.jfree.ui.ApplicationFrame;   
import org.jfree.ui.RefineryUtilities; 

public class IntervalBarChartDemoX3 extends ApplicationFrame   
{   

    public IntervalBarChartDemoX3(String s)   
    {   
        super(s);   
        JPanel jpanel = createDemoPanel();   
        jpanel.setPreferredSize(new Dimension(500, 270));   
        setContentPane(jpanel);   
    }   

    private static IntervalCategoryDataset createDataset(int a, int b, int c, int d,String categoryName)   
    {   
       Number s1[]={a};Number e1[]={ b };
       Number s2[]={c};Number e2[]={ d};

        Number start[][];// = {s1 ,s2};   
        start=new Number[2][];
        start[0]=s1;
        start[1]=s2;
        Number end[][] = { e1,e2 };  
        String[]  categoryKeys = {categoryName};
        String[] seriesKeys = {"50th%","90th%"};
    DefaultIntervalCategoryDataset defaultintervalcategorydataset = new DefaultIntervalCategoryDataset(seriesKeys,categoryKeys,start, end);   

        return defaultintervalcategorydataset;   
    }   

    private static JFreeChart createChart(IntervalCategoryDataset intervalcategorydataset,final int n)   
    {   
        CategoryAxis categoryaxis = new CategoryAxis("Category");   
        NumberAxis numberaxis = new NumberAxis("Percentage");   
        IntervalBarRenderer intervalbarrenderer = new IntervalBarRenderer();   
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
        // now add another category
        IntervalCategoryDataset dataset2=createDataset(2,3,2,3,"2kb");
        categoryplot.setDataset(1, dataset2);
        categoryplot.mapDatasetToRangeAxis(1, 1);        
        final ValueAxis axis2 = new NumberAxis("ms2");
        categoryplot.setRangeAxis(1, axis2);
        IntervalBarRenderer renderer2 = new IntervalBarRenderer();
    //  rendererSettings(renderer2);
        categoryplot.setRenderer(1, renderer2);

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
        JFreeChart jfreechart = createChart(createDataset(1,2,3,4,"1kb"),2);   
        return new ChartPanel(jfreechart);   
    }   

    public static void main(String args[])   
    {   
        IntervalBarChartDemoX3 intervalbarchartdemo1 = new IntervalBarChartDemoX3("Interval Bar Chart Demo 1");   
        intervalbarchartdemo1.pack();   
        RefineryUtilities.centerFrameOnScreen(intervalbarchartdemo1);   
        intervalbarchartdemo1.setVisible(true);   
    }   
}