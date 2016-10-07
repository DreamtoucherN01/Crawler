package com.blake.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class SpiderUtils {
	
	private static HttpClientBuilder builder = HttpClients.custom();
	private static CloseableHttpClient client = builder.build();
	
	public static String downLoad(String url) {
		
		HttpGet request = new HttpGet(url);
		String ret = "";
		try {
			
			CloseableHttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			ret = EntityUtils.toString(entity);
			
		} catch (ClientProtocolException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return ret;
	}

}
