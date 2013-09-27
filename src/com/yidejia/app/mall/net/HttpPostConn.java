package com.yidejia.app.mall.net;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpPostConn {
	private String urlString;
	private String TAG = "HttpGetConn";
	private int TIME_OUT_DELAY = 10000;
	
	private String[] keys;
	private String[] values;
//	private int count = 0;
	
	
	public HttpPostConn(String urlString, String[]keys, String[] values){
		this.urlString = urlString;
		this.keys = keys;
		this.values = values;
	}
	
	public String getJsonResult() throws IOException {
		String result = "";
		HttpPost httpRequst = new HttpPost(urlString);
		List<NameValuePair>list = new ArrayList<NameValuePair>();
		for (int i = 0; i < keys.length; i++) {
			list.add(new BasicNameValuePair(keys[i], values[i]));
		}
		httpRequst.setEntity(new UrlEncodedFormEntity(list,HTTP.UTF_8));
//		Log.i(TAG, urlString);
		try {
			HttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setIntParameter(
                    HttpConnectionParams.SO_TIMEOUT, TIME_OUT_DELAY); // 读取超时设置
			httpClient.getParams().setIntParameter(
                    HttpConnectionParams.CONNECTION_TIMEOUT, TIME_OUT_DELAY);// 连接超时
			HttpResponse httpResponse = httpClient.execute(httpRequst);
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				result = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
//				Log.i(TAG, result);
//				count = 0;
			} else if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_REQUEST_TIMEOUT ){
				Log.i(TAG, "连接超时");
				return result;
			} else {//连接失败
				Log.i(TAG, "接收数据失败");
				return result;
			}
		} catch(ClientProtocolException e){
			Log.i(TAG, "http协议出错" + e.getMessage().toString());
		} 
		
		return result;
	}
}
