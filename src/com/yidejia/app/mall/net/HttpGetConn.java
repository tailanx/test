package com.yidejia.app.mall.net;


import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import com.yidejia.app.mall.WelcomeActivity;

import android.util.Log;
import android.widget.Toast;

public class HttpGetConn {
	private String urlString;
	private String TAG = "HttpGetConn";
	private int TIME_OUT_DELAY = 10000;
//	private int count = 0;
//	public HttpGetConn(){
//		
//	}
	private String urlHost = "http://fw1.atido.net/?";
//	private String urlHost = "http://192.168.1.254:802/?";
	public HttpGetConn(String urlString){
		this.urlString = urlHost + urlString;
	}
	
	public String getJsonResult() throws IOException {
		String result = "";
//		if(!ConnectionDetector.isConnectingToInternet(WelcomeActivity.ACTIVITY) ){
//			return result;
//		}
		HttpGet httpRequst = new HttpGet(urlString);
		Log.i(TAG, urlString);
		try {
			HttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setIntParameter(
                    HttpConnectionParams.SO_TIMEOUT, TIME_OUT_DELAY); // ��ȡ��ʱ����
			httpClient.getParams().setIntParameter(
                    HttpConnectionParams.CONNECTION_TIMEOUT, TIME_OUT_DELAY);// ���ӳ�ʱ
			HttpResponse httpResponse = httpClient.execute(httpRequst);
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				result = EntityUtils.toString(httpResponse.getEntity());
				Log.i(TAG, "������:"+httpResponse.getStatusLine().getStatusCode());
				Log.i(TAG, "������Ϣ:"+result);
//				count = 0;
			} else if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_REQUEST_TIMEOUT ){
				Log.i(TAG, "���ӳ�ʱ��"+httpResponse.getStatusLine().getStatusCode());
//				Toast.makeText(, text, duration)
				return result;
			} else {//����ʧ��
				Log.i(TAG, "��������ʧ�ܣ�"+httpResponse.getStatusLine().getStatusCode());
				return result;
			}
		} catch(ClientProtocolException e){
			Log.i(TAG, "httpЭ�����" + e.getMessage().toString());
		} 
		
		return result;
	}
}
