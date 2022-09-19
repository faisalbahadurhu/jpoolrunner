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

import javax.swing.JDialog;
import javax.swing.JOptionPane;
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

public class ResponseTimeComparisons extends JPanel {
	DefaultCategoryDataset dataset = new DefaultCategoryDataset();

	/**
	 * Create the panel.
	 */
	public ResponseTimeComparisons() {

	}
	
	public ResponseTimeComparisons(Vector<String> tpsAndTestNames, int width,int height,String[] workLoadForBins,String fiftyNinty) {
		
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
				if(fiftyNinty.equals("fifty"))
					bins.add(s1Split[1]);
				else if(fiftyNinty.equals("ninty"))	 bins.add(s1Split[2]); // for 90 percentile
				String s2=dataloader.loadStringData("W"+workLoadForBins[n]);//i.e. W100 for example
				String s2Split[]=s2.split("\\s+");// it is is form of 100 100-119 119-125 e.g ie (name of job) (50%ile) & (90%ile)
			//	public JobPercentiles(String strategyname,String jobName,String fiftyPercentileRT, String nintyPercentileRT, String fiftyPercentileWt, String nintyPercentileWt) {
				jobPercentilesVector.add(new JobPercentiles(strategyName,workLoadForBins[n],s1Split[1],s1Split[2],s2Split[1],s2Split[2]));//0 index contains ame of the job and not needed here as it is in workLoadForBins[i]
			//	JOptionPane.showMessageDialog(null, s1Split[1]+" "+s1Split[2]+"...."+s2Split[1]+" "+s2Split[2]);						
			}
			vectorOfjobPercentilesVector.add(jobPercentilesVector);
		}
		
		Vector temp=new Vector();
		
		Iterator it=vectorOfjobPercentilesVector.iterator();
    	while(it.hasNext()){
    		Vector jobPercentilesVector=(Vector) it.next();//it is jobPercentilesVector
    	    Iterator it2=jobPercentilesVector.iterator();
    	    while(it2.hasNext()){
    	    	JobPercentiles hgg=(JobPercentiles)it2.next();
    	    	temp.add(hgg);
    	    }}
    	
    	Vector vectorOfJobData=new Vector();
    	
    	for(int n=0;n<workLoadForBins.length;n++){
    		JobData job=new JobData(workLoadForBins[n]);
    		Iterator iter=temp.iterator();
    		while(iter.hasNext()){
    			JobPercentiles hgg=(JobPercentiles)iter.next();
    			
    			if(workLoadForBins[n].equals(hgg.getJobName())){
    				job.setData(hgg.getStrategyName(),hgg.getFiftyPercentileRT(),hgg.getNintyPercentileRT(),hgg.getFiftyPercentileWt(),hgg.getNintyPercentileWt());//(strategy, fiftyPerRT, nintyPerRT, fiftyPerWT, nintyPerWT);
    				
    			}
    		}
    		vectorOfJobData.add(job);
			iter=null;
    		
    		
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
	    
	    
	    int size=(workLoadForBins.length);
		String[] categoryKeys=new String[size] ;//= {"1kb-File","2kb-File"};
		int count=0;
		
		Vector<Number[]> dynamicStartvector=new Vector<Number[]>();
		Vector<Number[]> dynamicEndvector=new Vector<Number[]>();

		
		for(int i=0;i<subcategoryNames.size();i++){
			dynamicStartvector.add(new Number[size]);
			dynamicEndvector.add(new Number[size]);

		}
		
	     Vector clonedynamicStartvector=new Vector();
	    	
	    	Iterator itL=dynamicStartvector.iterator();
	    	while(itL.hasNext()){
	    		Number start[]= (Number[])itL.next();
	    		for(int i=0;i<start.length;i++){
	    			start[i]=0;
	    		}
	    		clonedynamicStartvector.add(start);
	    		
	    		
	    	}
	     
	     
	//     Vector<DefaultIntervalCategoryDataset> datasetVector=new Vector<DefaultIntervalCategoryDataset>(5);
	    Iterator itq=vectorOfJobData.iterator();
	   
	    
		
    	while(itq.hasNext()){
    		JobData job=(JobData) itq.next();//it is jobPercentilesVector
    		switch (Integer.parseInt(job.getJobName())){
    	  		
    	    	case 1:categoryKeys[count]="Low";	break;
				case 2:categoryKeys[count]="High";	break;
				case 3:categoryKeys[count]="V.High";	break;
				case 100:categoryKeys[count]="1kb";	break;
				case 200:categoryKeys[count]="10kb";	break;
				case 300:categoryKeys[count]="100kb";	break;
				case 400:categoryKeys[count]="1000kb";	break;
				case 500:categoryKeys[count]="1000kb";	break;
				case 600:categoryKeys[count]="1000kb";	break;
				case 700:categoryKeys[count]="1000kb";	break;
				case 800:categoryKeys[count]="1000kb";	break;
				case 900:categoryKeys[count]="1000kb";	break;
				case 2000:categoryKeys[count]="2000kb";	break;
				case 1000:categoryKeys[count]="1200kb";	break;
    	    	
    	  	
    		 	};
    		
    		 	count++;
    		
    		 	}

    	for(int i=0;i<categoryKeys.length;i++){
    		for(int j=0;j<vectorOfJobData.size();j++){
        		JobData job=(JobData) vectorOfJobData.elementAt(j);//it is jobPercentilesVector
        		if(job.getJobNameAsCategoryKey().equals(categoryKeys[i])){
        			for(int k=0;k<dynamicEndvector.size();k++){
        			//	Number end[]= (Number[])dynamicEndvector.elementAt(k);
        			// 	end[i]=1+bins.indexOf(job.getPercentile(subcategoryNames.elementAt(k), fiftyNinty));
        				//following line of code is same as above 2 commented lines
        				((Number[])dynamicEndvector.elementAt(k))[i]=1+bins.indexOf(job.getPercentile(subcategoryNames.elementAt(k), fiftyNinty));

        			}
        			break;
        		}
        		else continue;
        		
    		}
    	}
    	
    	
 
    	    
    	        		
    //	Number startZ[][] = {s1 ,s2}; 
    	Number startZ[][] = new Number[subcategoryNames.size()][];  
    	Iterator itL1=clonedynamicStartvector.iterator();
    	int counter=0;
    	while(itL1.hasNext()){
    		Number start[]= (Number[])itL1.next();
    		startZ[counter++]=start;
    		
    	}
    	
    	
     //   Number endZ[][] = { e1,e2};
        Number endZ[][] =new Number[subcategoryNames.size()][];  
        Iterator itL3=dynamicEndvector.iterator();
    	int counter2=0;
    	while(itL3.hasNext()){
    		Number end[]= (Number[])itL3.next();
    		endZ[counter2++]=end;
    	}
    	
   
    	String[] seriesKeys=new String[subcategoryNames.size()];
    	
    	for(int i=0;i<subcategoryNames.size();i++){
    		seriesKeys[i]=(String)subcategoryNames.elementAt(i);
    	}

    	DefaultIntervalCategoryDataset dataset= new DefaultIntervalCategoryDataset(seriesKeys,categoryKeys,startZ, endZ);
		FontSetting fontSetting=new FontSetting(); 

		CategoryAxis domainAxis = new CategoryAxis("");
		domainAxis.setTickLabelFont(fontSetting.getTickFont(9));
		domainAxis.setLabelFont(fontSetting.getLabelFont());
		String[] ranges=new String[bins.size()+1];// +1 for 0
		ranges[0]="0";//+1 for 0 is solved here
		for(int i=1;i<=bins.size();i++){
			ranges[i]="["+bins.elementAt(i-1)+"] "+i;
		}
		NumberAxis rangeAxis = new SymbolAxis("Bins(ms)",ranges);
		rangeAxis.setTickLabelFont(fontSetting.getTickFont());
		rangeAxis.setLabelFont(fontSetting.getLabelFont());
		rangeAxis.setAutoRange(false);
		rangeAxis.setRange(0, ranges.length);
	
		IntervalBarRenderer renderer = new IntervalBarRenderer();
		CategoryPlot plot = new CategoryPlot(dataset, domainAxis, rangeAxis,renderer);			
		JFreeChart chart = new JFreeChart(plot);
	
		
		chart.setBackgroundPaint(Color.WHITE);
		plot.setBackgroundPaint(new Color(0xb8cfe5));
		plot.setRangeGridlinePaint(Color.WHITE); 
		plot.setDomainGridlinePaint(Color.WHITE);
		
		String embed="";
		if(fiftyNinty.equals("fifty")) embed+="50th";
		else embed+="90th";
		
		chart.setTitle(fontSetting.getTextTitle(embed+"Percentile Response Time "));
		LegendTitle legend = chart.getLegend();
        legend.setItemFont(fontSetting.getTickFont());

		plot.setDomainGridlinesVisible(true);
		plot.setRangePannable(true);
	//	ChartUtilities.applyCurrentTheme(chart);
		ChartPanel panel = new ChartPanel(chart);
		if(bins.size()>=12) height=300;
		else if(bins.size()>9) height=250;
		 panel.setPreferredSize(new Dimension(width,height));
		 this.setLayout(new BorderLayout());
		 this.add(panel,BorderLayout.CENTER);
		/*ChartPanel chartPanel = new ChartPanel(chart, false);
		chartPanel.setPreferredSize(new Dimension(width,height));
		this.add(chartPanel,BorderLayout.CENTER);*/

		}
}
