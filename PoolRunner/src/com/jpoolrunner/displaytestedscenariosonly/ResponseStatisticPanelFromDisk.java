package com.jpoolrunner.displaytestedscenariosonly;

import java.awt.BorderLayout;
import java.util.Comparator;

import java.awt.Color;
import java.awt.Dimension;
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
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.jfree.ui.TextAnchor;

import com.jpoolrunner.clientside.FontSetting;
import com.jpoolrunner.clientside.JobPercentiles;

public class ResponseStatisticPanelFromDisk extends JPanel {

	private Vector<JobPercentiles> jobPercentilesVector;
	//	public JobPercentiles(String jobName,String fiftyPercentileRT, String nintyPercentileRT, String fiftyPercentileWt, String nintyPercentileWt) {


	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	String strategyName;


	/**
	 * Create the panel.
	 */
	public ResponseStatisticPanelFromDisk() {

	} 

	public ResponseStatisticPanelFromDisk(String strategyName, Vector<JobPercentiles> jobPercentilesVector) {
		// TODO Auto-generated constructor stub
		this.jobPercentilesVector=jobPercentilesVector;
		this.strategyName=strategyName;

		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	//	Vector graphs=responsePanel.getResposeTimeGraphs();
		Iterator it=jobPercentilesVector.iterator();
		  Vector<String> bins=new Vector<String>(3);//hold ranges ie bins

		while(it.hasNext()){
			JobPercentiles hgg=(JobPercentiles)it.next();
		 	bins.add(hgg.getFiftyPercentileRT()); 
		 	bins.add(hgg.getNintyPercentileRT()); 
		}
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
	    
	    
	    int size=jobPercentilesVector.size();//how many types of jobs there 1kb 2kb etc
		String[] categoryKeys=new String[size] ;//= {"1kb-File","2kb-File"};
		int count=0;
		 Number s1[]= new Number[size];
		 Number e1[]= new Number[size];
		 Number s2[]= new Number[size];
	     Number e2[]= new Number[size];
	     String test="";
	//     Vector<DefaultIntervalCategoryDataset> datasetVector=new Vector<DefaultIntervalCategoryDataset>(5);
	     it=null;
	     it=jobPercentilesVector.iterator();
    	while(it.hasNext()){
    		JobPercentiles hgg=(JobPercentiles)it.next();
    		switch (Integer.parseInt(hgg.getJobName())){
    		case 1:categoryKeys[count]=""+"Low";	break;
			case 2:categoryKeys[count]=""+"High";	break;
			case 3:categoryKeys[count]=""+"V.High";	break;
			case 100:categoryKeys[count]=""+"1kb";	break;
			case 200:categoryKeys[count]=""+"10kb";	break;
			case 300:categoryKeys[count]=""+"100kb";	break;
			case 400:categoryKeys[count]=""+"1000kb";	break;
			case 2000:categoryKeys[count]=""+"2000kb";	break;
			case 1000:categoryKeys[count]=""+"1000kb";	break;// this is for future use
				
		 	};
    		 	//categoryKeys[count]=""+hgg.getJobName();// job name ie 100 200 etc 
    		 	s1[count]=0;// bin first range is kept to 0
    		 	s2[count]=0;
    		 	e1[count]=1+bins.indexOf(hgg.getFiftyPercentileRT());// this method will search string in vector and return the index where the string is stored 1 is added because vector starts at 0
    		 	test+="e1["+count+"]="+e1[count]+"\t";
    		 	e2[count]=1+bins.indexOf(hgg.getNintyPercentileRT());// this method will search string in vector and return the index where the string is stored
    		 	test+="e2["+count+"]="+e2[count]+"\t";
    		 	count++;
    		 	}
    	
    	Number startZ[][] = {s1 ,s2};   
        Number endZ[][] = { e1,e2}; 
    	String[] seriesKeys = {"50th%","90th%"};
   //   String[]  categoryKeys = seriesKeys;
		DefaultIntervalCategoryDataset dataset= new DefaultIntervalCategoryDataset(seriesKeys,categoryKeys,startZ, endZ);
		FontSetting fontSetting=new FontSetting(); 

		CategoryAxis domainAxis = new CategoryAxis("Workload");
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
	//	JOptionPane.showMessageDialog(null, ranges);
	//	JOptionPane.showMessageDialog(null, test);


		//  rangeAxis.setNumberFormatOverride(new DecimalFormat("0.00%"));
		IntervalBarRenderer renderer = new IntervalBarRenderer();
	//	DefaultIntervalCategoryDataset ds=(DefaultIntervalCategoryDataset)datasetVector.elementAt(0);
		CategoryPlot plot = new CategoryPlot(dataset, domainAxis, rangeAxis,renderer);			
	//	ValueAxis rageAxis = plot.getRangeAxis();
		JFreeChart chart = new JFreeChart(plot);
		chart.setBackgroundPaint(Color.WHITE);
		plot.setBackgroundPaint(new Color(0xb8cfe5));
		plot.setRangeGridlinePaint(Color.WHITE); 
		plot.setDomainGridlinePaint(Color.WHITE);
		int lastIndex=strategyName.lastIndexOf('/');//extract TPS name from full path e.g. it would be if form NFBOS2/aaa but following statement will convert it into NFBOS2
		String tpsName=strategyName.substring(0, lastIndex);
		
		chart.setTitle(fontSetting.getTextTitle("Response Time Percentiles: "+tpsName));
		LegendTitle legend = chart.getLegend();
        legend.setItemFont(fontSetting.getTickFont());

		plot.setDomainGridlinesVisible(true);
		plot.setRangePannable(true);
	//	ChartUtilities.applyCurrentTheme(chart);
		ChartPanel panel = new ChartPanel(chart);
		int height=205;
		if(bins.size()>=12) height=300;
		else if(bins.size()>9) height=250;
		 panel.setPreferredSize(new Dimension(500,height));
		// panel.setPreferredSize(new Dimension(500,205));
	   
		this.add(panel,BorderLayout.CENTER);

		}
}
