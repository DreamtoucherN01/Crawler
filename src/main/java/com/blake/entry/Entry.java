/**
 * 
 */
/**
 * @author Jack
 *
 */
package com.blake.entry;

import com.blake.procedure.itemgetter.Item;
import com.blake.procedure.parser.JDParser;
import com.blake.util.Constants;
import com.blake.util.MemoryHolder;
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
		
		for(int i = 1; i <= 500; i++) {
			
			for(int j = 1; j <= 10; j++) {
				
				new Item().fetchItem(i, j);
				System.out.println(MemoryHolder.urlToBeDealed.size());
				
			}
		}

	}
}