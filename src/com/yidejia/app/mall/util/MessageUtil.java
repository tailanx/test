package com.yidejia.app.mall.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class MessageUtil {

	private static final String CHARSET = "UTF-8";
	
	/** = */
	public static final String QSTRING_EQUAL = "=";

	/** & */
	public static final String QSTRING_SPLIT = "&";
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public static String assemblyParams(Map<String, String> map) {
		
		StringBuilder buff = new StringBuilder(64);
		String data = null;
		try {
			for (String key : map.keySet()) {
				buff.append(QSTRING_SPLIT).append(key).append(QSTRING_EQUAL).append(URLEncoder.encode(map.get(key), CHARSET));
			}
			data = buff.substring(1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		if (null == data) {
			return null;
		}
		return Base64.encodeBase64String(data.getBytes()).replaceAll("\r|\t|\n", "");
	}
	
	/**
	 * 
	 * @param message
	 * @return
	 */
	public static Map<String, String> resolveParams(String data) {
		
		String content = new String(Base64.decodeBase64(data));
		Map<String, String> map = new LinkedHashMap<String, String>();
		String[] params = content.split(QSTRING_SPLIT);
		try {
			for (String tmp : params) {
				String[] seq = tmp.split(QSTRING_EQUAL);
				if (seq.length == 2) {
					map.put(seq[0], URLDecoder.decode(seq[1], CHARSET));
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
}
