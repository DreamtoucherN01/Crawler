package com.blake.procedure.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
    
    
    public String getItemId(){
    	
        String itemId="";
        try{
        	
            itemId=doc.select("#pinfo .p-name a").first().attr("href");
            itemId=itemId.replaceAll("(//book.jd.com/)|(\\.html)", "");
            
        }catch(Exception e){
        	
        	e.printStackTrace();
        }
        return itemId;
    }
}
