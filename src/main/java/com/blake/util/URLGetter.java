
package com.blake.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLGetter
{
    private int failureCount = 0;

    private final Connection con = null;

    public String getURL(String url)
    {
        if(failureCount > 10)
        {
            try {
            	Thread.sleep(5 * 1000); 
            } catch(InterruptedException e) { 
            	
            	e.printStackTrace();
            };
            failureCount = 0;
        }
        
        return getURLWithoutLogin(url);
    }
    
    private String getURLWithoutLogin(String url){    
        
    	URL requestedURL = null;
		try {
			requestedURL = new URL(url);
		} catch (MalformedURLException e1) {

			e1.printStackTrace();
		}
    	try {
    		
            long startTime = System.currentTimeMillis();
            
            HttpURLConnection conn = (HttpURLConnection) requestedURL.openConnection();
            conn.setUseCaches(false);
            
            String ContentType="";
            try{
            	
                ContentType=conn.getHeaderFields().get("Content-Type").toString().toLowerCase();
                
            }catch(Exception e){
            	
            }
            String CharsetType="";
            //确定编码格式
            if(ContentType.indexOf("gb2312")>=0) {
            	
                CharsetType="gb2312";
            } else if(ContentType.indexOf("gbk")>=0) {
            	
                CharsetType="gbk";
            } else if(ContentType.indexOf("utf-8")>=0 || ContentType.indexOf("utf8") >= 0) {
            	
                CharsetType="utf-8";
            } else if(ContentType.indexOf("iso-8859-1") >= 0) {
            	
                CharsetType="iso-8859-1";
            }
            
            conn.connect();
            
            BufferedInputStream remoteBIS = new BufferedInputStream(conn.getInputStream());
            ByteArrayOutputStream baos = new ByteArrayOutputStream(10240);
            byte[] buf = new byte[1024];
            int bytesRead = 0;
            while(bytesRead >= 0)
            {
                baos.write(buf, 0, bytesRead);
                bytesRead = remoteBIS.read(buf);
            }

            byte[] content = baos.toByteArray();
            long timeTaken = System.currentTimeMillis() - startTime;
            if(timeTaken < 10){ 
                timeTaken = 10;
            }

            int bytesPerSec = (int) ((double) content.length / ((double)timeTaken / 1000.0));
            if(content.length < conn.getContentLength())
            {
                failureCount++;
                return null;
            }
            
            //如果编码不在文件头，则通过网页源代码获得
            if(CharsetType.equals("")){
                String tempSrc=new String(content);
                String find="<meta [\\s\\S]*?charset=[\\s\\S]*?/>";
                Pattern p = Pattern.compile(find);
                Matcher matcher = p.matcher(tempSrc);
                if(matcher.find()) {
                    ContentType=matcher.group().toLowerCase();
                    //确定编码格式
                    if(ContentType.indexOf("gb2312")>=0){
                        CharsetType="gb2312";
                    }else if(ContentType.indexOf("gbk")>=0){
                        CharsetType="gbk";
                    }else if(ContentType.indexOf("utf-8")>=0||ContentType.indexOf("utf8")>=0){
                        CharsetType="utf-8";
                    }else if(ContentType.indexOf("iso-8859-1")>=0){
                        CharsetType="iso-8859-1";
                    }
                }
            }
            String contentStr;
            if(CharsetType.equals("")){
                contentStr=new String(content);
            }else{
                contentStr=new String(content,CharsetType);
            }
            return contentStr;
        } catch(FileNotFoundException fnfe) {
	    
        	return null;
        }
        catch(IOException ioe) {
        	
            return null;
        }
    }
}
