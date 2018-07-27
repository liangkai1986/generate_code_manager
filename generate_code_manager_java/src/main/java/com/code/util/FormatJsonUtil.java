package com.code.util;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import javax.swing.SwingUtilities;

public class FormatJsonUtil {
	public static void main(String[] args) {
		String jsonStr = "{\"id\":\"1\",\"name\":\"a1\",\"obj\":{\"id\":11,\"name\":\"a11\",\"array\":[{\"id\":111,\"name\":\"a111\"},{\"id\":112,\"name\":\"a112\"}]}}";
		String fotmatStr = FormatJsonUtil.formatJson(jsonStr);
//		fotmatStr = fotmatStr.replaceAll("\n", "<br/>");
//		fotmatStr = fotmatStr.replaceAll("\t", "    ");
		System.out.println(fotmatStr);
		getStringBounds(fotmatStr);
	} 
	
	/**
	 * 得到格式化json数据  退格用\t 换行用\r
	 */
	public static String formatJson(String jsonStr) {
		jsonStr = jsonStr.replaceAll("\t", "");
		jsonStr = jsonStr.replaceAll("\n", "");
		jsonStr = jsonStr.replaceAll(" ", "");
		int level = 0;
		StringBuffer jsonForMatStr = new StringBuffer();
		for(int i=0;i<jsonStr.length();i++){
			char c = jsonStr.charAt(i);
			if(level>0&&'\n'==jsonForMatStr.charAt(jsonForMatStr.length()-1)){
				jsonForMatStr.append(getLevelStr(level));
			}
			switch (c) {
			case '{': 
			case '[':
				jsonForMatStr.append(c+"\n");
				level++;
				break;
			case ',': 
				jsonForMatStr.append(c+"\n");
				break;
			case '}':
			case ']':
				jsonForMatStr.append("\n");
				level--;
				jsonForMatStr.append(getLevelStr(level));
				jsonForMatStr.append(c);
				break;
			default:
				jsonForMatStr.append(c);
				break;
			}
		}
		
		return jsonForMatStr.toString();

	}
	
	private static String getLevelStr(int level){
		StringBuffer levelStr = new StringBuffer();
		for(int levelI = 0;levelI<level ; levelI++){
			levelStr.append("\t");
		}
		return levelStr.toString();
	}
	
	@SuppressWarnings("deprecation")
	public static void getStringBounds( String text)
	 {
	  Font font = new Font(Font.SERIF,Font.BOLD,8);
	  //方法1--只能分别得到字符串的宽度和高度
	  FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(font);
	  int width = SwingUtilities.computeStringWidth(fm, text);
	  int height = fm.getHeight();
	  
	  System.out.println("方法1 width:" + width + " height:" + height);
	  
	  //方法2--能得到字符串的边界
	  FontRenderContext frc = new FontRenderContext(new AffineTransform(),true,true);
	  Rectangle rec = font.getStringBounds(text, frc).getBounds();
	  
	  System.out.println("方法2 width:" + rec.getWidth() + " height:" + rec.getHeight());
	  
	 }
}
