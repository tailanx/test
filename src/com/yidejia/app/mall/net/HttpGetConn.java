package com.yidejia.app.mall.net;


import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import com.yidejia.app.mall.exception.TimeOutEx;

import android.util.Log;

public class HttpGetConn {
	private String urlString;
	private String TAG = "HttpGetConn";
	private int TIME_OUT_DELAY = 1000 * 5;
//	private int count = 0;
//	public HttpGetConn(){
//		
//	}
	private String urlHost = "http://fw1.atido.net/?";
//	private String urlHost = "http://192.168.1.254:802/?";
	public HttpGetConn(String urlString){
		this.urlString = urlHost + urlString;
	}
	
	public String getJsonResult() throws IOException, TimeOutEx {
		String result = "";
//		if(!ConnectionDetector.isConnectingToInternet(WelcomeActivity.ACTIVITY) ){
//			return result;
//		}
		HttpGet httpRequst = new HttpGet();
		Log.i(TAG, urlString);
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpProtocolParams.setUseExpectContinue(httpClient.getParams(), false);
			httpRequst.setURI(new URI(urlString));
			httpClient.getParams().setIntParameter(
                    HttpConnectionParams.SO_TIMEOUT, TIME_OUT_DELAY); // 读取超时设置
			httpClient.getParams().setIntParameter(
                    HttpConnectionParams.CONNECTION_TIMEOUT, TIME_OUT_DELAY);// 连接超时
			HttpResponse httpResponse = null;
			boolean RetryConnection = true;
	        int retrycount = 0;
	      //retry request 5 times
			while (RetryConnection == true && retrycount < 5) {
				try {
					httpResponse = httpClient.execute(httpRequst);
					RetryConnection = false;
				} catch (IOException e) {
					System.out.println("caught http io ex");
					retrycount += 1;
				}
			}
			if(null == httpResponse){
				Log.i(TAG, "返回失败!");
				return result;
			}
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				Log.i(TAG, "200返回成功!");
				result = EntityUtils.toString(httpResponse.getEntity());
				Log.i(TAG, "返回码:"+httpResponse.getStatusLine().getStatusCode());
				Log.i(TAG, "返回信息:"+result);
//				count = 0;
			} else if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_REQUEST_TIMEOUT ){
				Log.i(TAG, "连接超时："+httpResponse.getStatusLine().getStatusCode());
//				Toast.makeText(, text, duration)
				return result;
			} else {//连接失败
				Log.i(TAG, "接收数据失败："+httpResponse.getStatusLine().getStatusCode());
				return result;
			}
		} catch(ClientProtocolException e){
			Log.i(TAG, "http协议出错" + e.getMessage().toString());
			
		} catch (Exception e) {
			// TODO: handle exception
			Log.i(TAG, "连接超时：");
			e.printStackTrace();
			throw new TimeOutEx("连接超时");
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		if(null == result || "".equals(result)) throw new TimeOutEx();
		return result;
	}
	/**
	 * jni方式获取数据， isTrue为不需要用到的参数
	 * @param urlString
	 * @param isTrue 
	 */
	public HttpGetConn(String urlString, boolean isTrue){
		this.urlString = urlString;
	}
	
	
}
