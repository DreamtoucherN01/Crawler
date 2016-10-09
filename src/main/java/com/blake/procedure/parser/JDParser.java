package com.blake.procedure.parser;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.blake.util.MemoryHolder;

public class JDParser {

    private String usefulContent;    //有用的内容
    Document doc;
    
    public JDParser(String webContent){
    	
        //有用的内容即去除了js代码、css代码、注释
        usefulContent = webContent.replaceAll("(?i)((<script[\\s\\S]*?</script>)|(<style[\\s\\S]*?</style>)|(<!--[\\s\\S]*?-->))", "");
        doc = Jsoup.parse(usefulContent);
    }
    
    public String getItemCategory(){
    	
        String itemCategory="";
        try{
        	
            Elements as=doc.select(".breadcrumb > a");
            int aNum = as.size();
            for(int i = 1;i < aNum-2; i++){
            	
                Element a=as.get(i);
                itemCategory+=a.text().replaceAll(" ", "") +" ";
            }
            
        }catch(Exception e){
        	
        	e.printStackTrace();
        }
        return itemCategory;
    }
    
    
    public void getItemId() {
    	
        ListIterator<Element> it = null;
        try{
        	
            it = doc.select(".list-item").listIterator();
            while(it.hasNext()) {
            	
            	Element next = it.next();
            	String itemId = next.select(".p-comm a").attr("href");
            	if(itemId.contains("item")) {
                	
                	itemId = itemId.replaceAll("(//item.jd.com/)|(\\.html)", "");
                } else if(itemId.contains("book")) {
                	
                	itemId = itemId.replaceAll("(//book.jd.com/)|(\\.html)", "");
                } 
            	if(!StringUtils.isBlank(itemId)) {
                	
                	MemoryHolder.urlToBeDealed.add(itemId);
                }
            }
            
        } catch(Exception e) {
        	
        	e.printStackTrace();
        }
    }
}
