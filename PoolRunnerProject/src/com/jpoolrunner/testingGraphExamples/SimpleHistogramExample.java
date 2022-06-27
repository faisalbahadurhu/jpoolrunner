package com.jpoolrunner.testingGraphExamples;
import org.jfree.chart.*; 
import org.jfree.chart.plot.*; 
import org.jfree.data.statistics.SimpleHistogramBin; 
import org.jfree.data.statistics.SimpleHistogramDataset; 

public class SimpleHistogramExample 
{ 

public static void main(String[] args) 
{ 
ChartFrame f = new ChartFrame("Hello", getHistogram()); 
f.pack(); 
f.setVisible(true); 

} 

public static JFreeChart getHistogram() 
{ 
JFreeChart chart = null; 

chart = ChartFactory.createHistogram( 
"Histogram", 
"xAxisLabel", 
"yAxisLabel", 
getSimpleHistogramDataset(), 
PlotOrientation.VERTICAL, 
true /* legend */, 
false /* tooltips */, 
false /* urls */); 
return chart; 
} 

public static SimpleHistogramDataset getSimpleHistogramDataset() 
{ 
SimpleHistogramBin bin1 = new SimpleHistogramBin(95, 100, true, false);
SimpleHistogramBin bin2 = new SimpleHistogramBin(100, 104, true, false);
SimpleHistogramBin bin3 = new SimpleHistogramBin(104, 110, true, false);

SimpleHistogramDataset result = null; 

result = new SimpleHistogramDataset("SimpleHistogramDataset"); 
result.addBin(bin1); 
result.addBin(bin2); 
result.addBin(bin3); 
/*result.addObservation(99); 
result.addObservation(100); 
result.addObservation(103); 
result.addObservation(109); */
result.addObservation(100); 




result.setAdjustForBinSize(false);

/*result.addObservation(5); 
result.addObservation(18); 
result.addObservation(50); 
result.addObservation(55); 
result.addObservation(60); 
result.addObservation(88); 
result.addObservation(120); 
result.addObservation(199); 
result.addObservation(220); */

return result; 
} 

}