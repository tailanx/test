package com.yidejia.app.mall.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Http {

	private static final String METHOD_POST = "POST";
	private static final int CACHE_SIZE = 1024;
	private static final int CONNECT_TIMEOUT = 10000;
	private static final int READ_TIMEOUT = 30000;
	
	/**
	 * 发送到自己商户服务器
	 * @param url
	 * @param msg
	 * @param encoding
	 * @return
	 * @throws IOException 
	 */
	public static String post(String url, String content) {
		
		String result = null;

		OutputStream os = null;
		InputStream is = null;
		HttpURLConnection httpConn = null;

		try {
			// 建立连接
			httpConn = (HttpURLConnection) new URL(url).openConnection();
			httpConn.setDoOutput(true); // 使用URL连接输出
			httpConn.setDoInput(true); // 使用URL连接输入
			httpConn.setUseCaches(false); // 忽略缓存
			httpConn.setRequestMethod(METHOD_POST); // 设置URL请求方式
			httpConn.setConnectTimeout(CONNECT_TIMEOUT);
			httpConn.setReadTimeout(READ_TIMEOUT);
			httpConn.connect();
			
			// 写
			os = httpConn.getOutputStream();
			os.write(content.getBytes());
			os.flush();
			is = httpConn.getInputStream();
			byte[] cache = new byte[CACHE_SIZE];
			int count = 0;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        while((count = is.read(cache)) != -1) {
	        	baos.write(cache, 0, count);
	        }
	        baos.flush();
	        baos.close();
	        result = baos.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != os) {
						os.close();
				}
				if (null != is) {
					is.close();
				}
				if (null != httpConn) {
					httpConn.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
}
