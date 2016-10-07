/**
 * 
 */
/**
 * @author Jack
 *
 */
package com.blake.entry;

import com.blake.procedure.parser.JDParser;
import com.blake.util.Constants;
import com.blake.util.SpiderUtils;

public class Entry{
	
	/**
	 * 
	 * @param args
	 * 
	 * step 1 : fetch item number and item category, store number in the memory and category in the database
	 * 
	 * step 2
	 */
	public static void main(String args[]) {
		
		String content = SpiderUtils.downLoad(Constants.testUrl.replaceAll("[?]", "1523682"));
		
		JDParser jdParser = new JDParser(content);
		System.out.println(jdParser.getItemId());
	}
}