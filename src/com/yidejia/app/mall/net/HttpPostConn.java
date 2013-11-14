package com.yidejia.app.mall.net;


import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.yidejia.app.mall.jni.JNICallBack;

public class HttpPostConn {
	private String urlString = "http://fw1.atido.net/";
//	private String urlString = "http://192.168.1.254:802/";
	private String TAG = "HttpPostConn";
	private int TIME_OUT_DELAY = 10000;
	
	private String[] keys;
	private String[] values;
//	private int count = 0;
	
	
	public HttpPostConn(String[]keys, String[] values){
//		this.urlString = urlString;
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
//			httpClient.getParams().setIntParameter(
//                    HttpConnectionParams.SO_TIMEOUT, TIME_OUT_DELAY); // ��ȡ��ʱ����
//			httpClient.getParams().setIntParameter(
//                    HttpConnectionParams.CONNECTION_TIMEOUT, TIME_OUT_DELAY);// ���ӳ�ʱ
			HttpResponse httpResponse = httpClient.execute(httpRequst);
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				result = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
//				Log.i(TAG, result);
//				count = 0;
			} else if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_REQUEST_TIMEOUT ){
				Log.i(TAG, "time out");
				return result;
			} else {//����ʧ��
				Log.i(TAG, "time out");
				return result;
			}
		} catch(ClientProtocolException e){
			Log.i(TAG, "http协议出错 " + e.getMessage().toString());
		} 
		
		return result;
	}
	
	/**
	 * ------------------ jni 方式获取数据---------------------------
	 */
	
	private String param;//post param
	public HttpPostConn(String param){
		this.param = param;
	}
	/**
	 * jni post request 
	 * @return
	 */
	public String getHttpResponse() throws IOException{
		if(param == null || "".equals(param)){
			return null;
		}
		String result = "";
		try {
//			byte[] paramArrayOfByte = param.getBytes();
//			ByteArrayEntity arrayEntity = new ByteArrayEntity(paramArrayOfByte); 
//			arrayEntity.setContentType("application/octet-stream"); 
			
			String url = JNICallBack.HTTPURL;
			HttpPost httpPost = new HttpPost(url); 
			Log.i(TAG, url+"?"+param);
//			httpPost.setEntity(arrayEntity); 
			String utf8param = URLDecoder.decode(new String(param.getBytes("UTF-8")), "UTF-8");
			Log.e(TAG, utf8param);
			StringEntity stringEntity = new StringEntity(utf8param, HTTP.UTF_8);
			stringEntity.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
			httpPost.setEntity(stringEntity);
			
			HttpClient client = new DefaultHttpClient(); 
			
//			client.getParams().setIntParameter(
//					HttpConnectionParams.SO_TIMEOUT, TIME_OUT_DELAY); // ��ȡ��ʱ����
//			client.getParams().setIntParameter(
//					HttpConnectionParams.CONNECTION_TIMEOUT, TIME_OUT_DELAY);// ���ӳ�ʱ
			HttpResponse httpResponse = client.execute(httpPost);
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				result = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
				Log.i(TAG, result);
//				count = 0;
			} else if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_REQUEST_TIMEOUT ){
				Log.i(TAG, "time out");
				return result;
			} else {//����ʧ��
				Log.i(TAG, "return code:"+httpResponse.getStatusLine().getStatusCode());
				return result;
			}
		} catch (ClientProtocolException e) {
			// TODO: handle exception
		}
		
		return result;
	}
	
}
