package com.blake.procedure.itemgetter;

import com.blake.procedure.parser.JDParser;
import com.blake.util.Constants;
import com.blake.util.SpiderUtils;

public class Item {
	
	public void fetchItem(int i, int j) {
		
		String url = Constants.originalUrl.replaceAll("x", String.valueOf(i)).replaceAll("y", String.valueOf(j));
		String content = SpiderUtils.downLoad(url);
		JDParser jdParser = new JDParser(content);
		jdParser.getItemId();
	}

}
