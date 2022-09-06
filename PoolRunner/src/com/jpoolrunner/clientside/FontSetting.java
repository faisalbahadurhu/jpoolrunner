package com.jpoolrunner.clientside;
import java.awt.Font;

import org.jfree.chart.title.TextTitle;
public class FontSetting {
	   int titleFontSize=13;//15
	   int smalltitleFontSize=10;
	   int tickFontSize=10;
	   int labelFontSize=10;
	  final String tickFontType="Verdana";
	  final String titleFontType="Verdana";
	  final String labelFontType="Verdana";
	 // Font tickFont= new Font ("Verdana", Font.PLAIN , 8);
      
	public TextTitle getTextTitle(String title){
		TextTitle my_Chart_title=new TextTitle(title, new Font (titleFontType, Font.BOLD ,titleFontSize));
		return my_Chart_title;
	}
	
	public TextTitle getSmallTextTitle(String title){
		TextTitle my_Chart_title=new TextTitle(title, new Font (titleFontType, Font.BOLD ,smalltitleFontSize));
		return my_Chart_title;
	}
  public Font getTickFont(){
	  Font tickFont= new Font (tickFontType, Font.PLAIN , tickFontSize);
	  return tickFont;
  }
  public Font getTickFont(int fontSize){
	  Font tickFont= new Font (tickFontType, Font.PLAIN , fontSize);
	  return tickFont;
  }
  public Font getSmallTickFont(){
	  Font tickFont= new Font (tickFontType, Font.PLAIN , tickFontSize-3);
	  return tickFont;
  }
  public Font getLabelFont(){
	  Font labelFont= new Font (labelFontType, Font.BOLD , labelFontSize);
	  return labelFont;
  }
  public Font getLabelFont(int fontSize){
	  Font labelFont= new Font (labelFontType, Font.BOLD , fontSize);
	  return labelFont;
  }
  public Font getSmallFontForTPSName(){
	  Font labelFont= new Font (labelFontType, Font.PLAIN , 12);
	  return labelFont;
  }
 public FontSetting(){}
 public  FontSetting(int titleFontSize ,int tickFontSize,int labelFontSize){
	  this.titleFontSize=titleFontSize;
	  this.tickFontSize=tickFontSize;
	  this.labelFontSize=labelFontSize;
	  
  }
}
