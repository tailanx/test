package com.yidejia.app.mall.net;

public class HttpAddressParam {
	
	
	public static String getHttpAddress(String[] keys, String[] values){
		StringBuffer address = new StringBuffer();
		int length = keys.length;
		for (int i = 0; i < length; i++) {
			address.append(keys[i]);
			address.append("=");
			address.append(values[i]);
			if(i != length - 1)
				address.append("&");
		}
		return address.toString();
	}
}
