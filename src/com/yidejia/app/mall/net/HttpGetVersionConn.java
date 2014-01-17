package com.yidejia.app.mall.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

public class HttpGetVersionConn {
	
	private String urlString;
	private String TAG = "HttpGetVersionConn";
	
	public HttpGetVersionConn(String urlString){
		this.urlString = urlString;
	}
	
	public String getVersionString(){
		String result = "";
		URL url = null;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			// TODO: handle exception
			Log.e(TAG,"MalformedURLException in get version");
		}
		if(url != null){
			HttpURLConnection conn = null;
			try {
				Log.e(TAG, "before open");
				conn = (HttpURLConnection) url.openConnection();
				conn.connect();
				Log.e(TAG, "open"+conn);
				//�õ���ȡ��������
				if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
					Log.e(TAG, "http ok");
				} else{
					Log.e(TAG, "http no"+conn.getResponseCode());
				}
				InputStreamReader in = new InputStreamReader(url.openStream());
//				InputStream is = conn.getInputStream();
//				Log.e(TAG, "is");
//				InputStreamReader in = new InputStreamReader(is,"GB2312");
				Log.e(TAG, "in");
				// �������bufferReader
				BufferedReader buffer = new BufferedReader(in);
				Log.e(TAG, "buffer");
				String input = null;
				while((input = buffer.readLine()) != null){
					result += input;
					Log.e(TAG, "result"+result);
				}
				in.close();
				conn.disconnect();
			} catch (IOException e) {
				// TODO: handle exception
				Log.e(TAG, "IOException in get version");
				if(null != conn) conn.disconnect();
			}
		} else {
			Log.e(TAG, "url == null");
		}
		return result;
	}
}
