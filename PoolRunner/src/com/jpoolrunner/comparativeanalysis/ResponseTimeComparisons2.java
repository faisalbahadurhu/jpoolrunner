package com.jpoolrunner.comparativeanalysis;


import java.awt.BorderLayout;
import java.awt.BorderLayout;
import java.util.Comparator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.IntervalBarRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import org.jfree.ui.VerticalAlignment;

import com.jpoolrunner.clientside.FontSetting;
import com.jpoolrunner.clientside.JobPercentiles;
import com.jpoolrunner.diskIO.DataLoader;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

public class ResponseTimeComparisons2 extends JPanel {
	DefaultCategoryDataset dataset = new DefaultCategoryDataset();

	/**
	 * Create the panel.
	 */
	public ResponseTimeComparisons2() {

	}
	private String getSpace(int count){// to make category names unique because job 1 i for tps 1 and taps 2 so isert space in second name ie " "+1
	String space="";
	for(int i=1;i<=count;i++){
		space+=" ";}
	
	return space;
	}

	public ResponseTimeComparisons2(Vector<String> tpsAndTestNames, int width,int height,String[] workLoadForBins) {
		
	//	Vector responseResults=new Vector(tpsAndTestNames.size());// it is a vector of vectors
		Vector<String> subcategoryNames=new Vector<String>(3);
		Vector<Vector> vectorOfjobPercentilesVector=new Vector<Vector>(10);
		  Vector<String> bins=new Vector<String>(3);//hold ranges ie bins

		for(int i=0;i<tpsAndTestNames.size();i++){
			Vector<JobPercentiles> jobPercentilesVector=new Vector<JobPercentiles>(10);

			DataLoader dataloader=new DataLoader(tpsAndTestNames.elementAt(i));// path is in form of e.g. NFBOS2/Test1
			String strategyNameNTestNumber=tpsAndTestNames.elementAt(i);
			String[] split = strategyNameNTestNumber.split("/");
			String strategyName=split[0];
			subcategoryNames.add(strategyName);
			//////////////////
			for(int n=0;n<workLoadForBins.length;n++){
				//JobPercentiles
				String s1=dataloader.loadStringData("R"+workLoadForBins[n]);//i.e. R100 for example
				String s1Split[]=s1.split("\\s+");// it is is form of 100 100-119 119-125 e.g ie (name of job) (50%ile) & (90%ile)
				bins.add(s1Split[1]);bins.add(s1Split[2]); // for ranges ie y axis values in graph
				String s2=dataloader.loadStringData("W"+workLoadForBins[n]);//i.e. W100 for example
				String s2Split[]=s2.split("\\s+");// it is is form of 100 100-119 119-125 e.g ie (name of job) (50%ile) & (90%ile)
			//	public JobPercentiles(String strategyname,String jobName,String fiftyPercentileRT, String nintyPercentileRT, String fiftyPercentileWt, String nintyPercentileWt) {
				jobPercentilesVector.add(new JobPercentiles(strategyName,workLoadForBins[n],s1Split[1],s1Split[2],s2Split[1],s2Split[2]));//0 index contains ame of the job and not needed here as it is in workLoadForBins[i]
			//	JOptionPane.showMessageDialog(null, s1Split[1]+" "+s1Split[2]+"...."+s2Split[1]+" "+s2Split[2]);						
			}
			vectorOfjobPercentilesVector.add(jobPercentilesVector);
		}
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		//now sort bins String data
		Collections.sort(bins, new Comparator<String>() {
	        public int compare(String o1, String o2) {
	            return extractInt(o1) - extractInt(o2);
	        }

	        int extractInt(String s) {
	            String num = s.replaceAll("\\D", "");
	            // return 0 if no digits found
	            return num.isEmpty() ? 0 : Integer.parseInt(num);
	        }
	    });
	   //Bin data may contain duplicate so store it in a set which automatically removes duplicates
	    Set<String> set = new LinkedHashSet<String>();
	    set.addAll(bins);
	    //remove bin data now
	    bins.removeAllElements();
	    //store unique sorted data into bin again
	    bins.addAll(set);
	    
	    
	    int size=(vectorOfjobPercentilesVector.size()*workLoadForBins.length);
		String[] categoryKeys=new String[size] ;//= {"1kb-File","2kb-File"};
		int count=0;
		 Number s1[]= new Number[size];
		 Number e1[]= new Number[size];
		 Number s2[]= new Number[size];
	     Number e2[]= new Number[size];
	     String test="";
	//     Vector<DefaultIntervalCategoryDataset> datasetVector=new Vector<DefaultIntervalCategoryDataset>(5);
	    Iterator it=vectorOfjobPercentilesVector.iterator();
	    int space=0;//
	    int sapce1,sapce2,sapce3,sapce4,sapce5,sapce6,sapce7,sapce8;
	    sapce1=sapce2=sapce3=sapce4=sapce5=sapce6=sapce7=sapce8=0;
    	while(it.hasNext()){
    		Vector jobPercentilesVector=(Vector) it.next();//it is jobPercentilesVector
    	    Iterator it2=jobPercentilesVector.iterator();
    	    while(it2.hasNext()){
    	    	JobPercentiles hgg=(JobPercentiles)it2.next();
    	    	String spaces=getSpace(space);
    	    	switch (Integer.parseInt(hgg.getJobName())){
    	    /*	case 1:categoryKeys[count]=getSpace(sapce1++)+"Low";	break;
				case 2:categoryKeys[count]=getSpace(sapce2++)+"High";	break;
				case 3:categoryKeys[count]=getSpace(sapce3++)+"V.High";	break;
				case 100:categoryKeys[count]=getSpace(sapce4++)+"1kb";	break;
				case 200:categoryKeys[count]=getSpace(sapce5++)+"10kb";	break;
				case 300:categoryKeys[count]=getSpace(sapce6++)+"100kb";	break;
				case 400:categoryKeys[count]=getSpace(sapce7++)+"1000kb";	break;
				case 2000:categoryKeys[count]=getSpace(sapce8++)+"2000kb";	break;
				case 1000:categoryKeys[count]=getSpace(sapce8++)+"2000kb";	break;*/
    	    	
    	    	case 1:categoryKeys[count]=getSpace(sapce1++)+"1";	break;
				case 2:categoryKeys[count]=getSpace(sapce2++)+"2";	break;
				case 3:categoryKeys[count]=getSpace(sapce3++)+"3";	break;
				case 100:categoryKeys[count]=getSpace(sapce4++)+"4";	break;
				case 200:categoryKeys[count]=getSpace(sapce5++)+"5";	break;
				case 300:categoryKeys[count]=getSpace(sapce6++)+"6";	break;
				case 400:categoryKeys[count]=getSpace(sapce7++)+"7";	break;
				case 2000:categoryKeys[count]=getSpace(sapce8++)+"8";	break;
				case 1000:categoryKeys[count]=getSpace(sapce8++)+"7";	break;
    	    	
    		 	};
    		 //	categoryKeys[count]=spaces+hgg.getJobName();// job name ie 100 200 etc 
    		 	s1[count]=0;// bin first range is kept to 0
    		 	s2[count]=0;
    		 	e1[count]=1+bins.indexOf(hgg.getFiftyPercentileRT());// this method will search string in vector and return the index where the string is stored 1 is added because vector starts at 0
    		 	test+="e1["+count+"]="+e1[count]+"\t";
    		 	e2[count]=1+bins.indexOf(hgg.getNintyPercentileRT());// this method will search string in vector and return the index where the string is stored
    		 	test+="e2["+count+"]="+e2[count]+"\t";
    		 	count++;
    		 	space++;
    		 	spaces=null;
    		 	}
    		
    	    	
    	    }
    	        		
    	Number startZ[][] = {s1 ,s2};   
        Number endZ[][] = { e1,e2}; 
    	String[] seriesKeys = {"50th%","90th%"};
   //   String[]  categoryKeys = seriesKeys;
		DefaultIntervalCategoryDataset dataset= new DefaultIntervalCategoryDataset(seriesKeys,categoryKeys,startZ, endZ);
		FontSetting fontSetting=new FontSetting(); 

		CategoryAxis domainAxis = new CategoryAxis("");
		domainAxis.setTickLabelFont(fontSetting.getTickFont(7));
		domainAxis.setLabelFont(fontSetting.getLabelFont());
		String[] ranges=new String[bins.size()+1];// +1 for 0
		ranges[0]="0";//+1 for 0 is solved here
		for(int i=1;i<=bins.size();i++){
			ranges[i]="["+bins.elementAt(i-1)+"] "+i;
		}
		NumberAxis rangeAxis = new SymbolAxis("Bins",ranges);
		rangeAxis.setTickLabelFont(fontSetting.getTickFont(9));
		rangeAxis.setLabelFont(fontSetting.getLabelFont());
		rangeAxis.setAutoRange(false);
		rangeAxis.setRange(0, ranges.length);
	//	JOptionPane.showMessageDialog(null, ranges);
	//	JOptionPane.showMessageDialog(null, test);


		//  rangeAxis.setNumberFormatOverride(new DecimalFormat("0.00%"));
		IntervalBarRenderer renderer = new IntervalBarRenderer();
	//	DefaultIntervalCategoryDataset ds=(DefaultIntervalCategoryDataset)datasetVector.elementAt(0);
		CategoryPlot plot = new CategoryPlot(dataset, domainAxis, rangeAxis,renderer);			
	//	ValueAxis rageAxis = plot.getRangeAxis();
		JFreeChart chart = new JFreeChart(plot);
		String subTitle="";
		for(int i=0;i<subcategoryNames.size();i++){
			subTitle+=subcategoryNames.elementAt(i);
		//	if(i==(subcategoryNames.size()-1)) subTitle+=subcategoryNames.elementAt(i);//dont add space on last item
		//	else subTitle+=subcategoryNames.elementAt(i)+"                ";
		}
		chart.addSubtitle(new TextTitle(subTitle,
				fontSetting.getLabelFont(), Color.black,
        	    RectangleEdge.BOTTOM, HorizontalAlignment.CENTER,
        	    VerticalAlignment.BOTTOM, RectangleInsets.ZERO_INSETS));
		chart.setBackgroundPaint(Color.WHITE);
		plot.setBackgroundPaint(new Color(0xb8cfe5));
		plot.setRangeGridlinePaint(Color.WHITE); 
		plot.setDomainGridlinePaint(Color.WHITE);
		
		chart.setTitle(fontSetting.getTextTitle("Response Time Percentiles"));
		LegendTitle legend = chart.getLegend();
        legend.setItemFont(fontSetting.getTickFont());

		plot.setDomainGridlinesVisible(true);
		plot.setRangePannable(true);
	//	ChartUtilities.applyCurrentTheme(chart);
		ChartPanel panel = new ChartPanel(chart);
		if(bins.size()>=12) height=300;
		else if(bins.size()>9) height=250;
		 panel.setPreferredSize(new Dimension(width,height));
	   
		this.add(panel,BorderLayout.CENTER);
		/*ChartPanel chartPanel = new ChartPanel(chart, false);
		chartPanel.setPreferredSize(new Dimension(width,height));
		this.add(chartPanel,BorderLayout.CENTER);*/

		}
}
