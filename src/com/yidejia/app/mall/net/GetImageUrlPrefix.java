package com.yidejia.app.mall.net;

import java.io.IOException;

import android.util.Log;

import com.yidejia.app.mall.util.Md5;


public class GetImageUrlPrefix {
	private String[] keys = new String[5];
	private String[] values = new String[5];
	private String TAG = GetImageUrlPrefix.class.getName();
	
	private void setKeysAndValues(){
		keys[0] = "api";
		String api = "common.img.get";
		values[0] = api;
		
		keys[1] = "key";
		values[1] = "fw_mobile";
		keys[2] = "format";
		values[2] = "array";
		keys[3] = "ts";
		long time = System.currentTimeMillis();
		String ts = String.valueOf(time/1000);
		values[3] = ts;
		
		keys[4] = "sign";
		StringBuffer strTemp = new StringBuffer();
		strTemp.append("ChunTianfw_mobile123456");
		strTemp.append(api);
		strTemp.append(ts);
		Md5 md = new Md5();
		String result = md.getMD5Str(strTemp.toString());
		md = null;
        strTemp = null;
		values[4] = result;
	}
	
	private String getHttpAddress(){
		StringBuffer result = new StringBuffer();
		result.append("http://192.168.1.254:802/?");
		setKeysAndValues();
		result.append(HttpAddressParam.getHttpAddress(keys, values));
		Log.i(TAG, result.toString());
		return result.toString();
	}
	
	private String result = "";
	public String getUrlJsonString()throws IOException{
		HttpGetConn conn = new HttpGetConn(getHttpAddress());
		result = conn.getJsonResult();
		return result;
	}
}
