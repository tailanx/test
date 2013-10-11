package com.yidejia.app.mall.net.search;

import java.io.IOException;

import android.util.Log;

import com.yidejia.app.mall.net.HttpAddressParam;
import com.yidejia.app.mall.net.HttpGetConn;
import com.yidejia.app.mall.util.Md5;

public class BrandDataUtil {
	private String[] keys = new String[6];
	private String[] values = new String[6];
	private String TAG = BrandDataUtil.class.getName();
	
	private void setKeysAndValues(){
		keys[0] = "api";
		String api = "product.mallgood.getBrands";
		values[0] = api;
		keys[1] = "desc";
		values[1] = "";
	
		
		keys[2] = "key";
//		values[2] = "fw_test";
		values[2] = "fw_mobile";
		keys[3] = "format";
		values[3] = "array";
		
		keys[4] = "ts";
		long time = System.currentTimeMillis();
		String ts = String.valueOf(time/1000);
		values[4] = ts;
		
		keys[5] = "sign";
		StringBuffer strTemp = new StringBuffer();
//		strTemp.append("ChunTianfw_mobile123456");
		strTemp.append("ChunTianfw_mobile@SDF!TD#DF#*CB$GER@");
		strTemp.append(api);
		strTemp.append(ts);
		Md5 md = new Md5();
		String result = md.getMD5Str(strTemp.toString());
		md = null;
        strTemp = null;
		values[5] = result;
	}
	
	private String getHttpAddress(){
		StringBuffer result = new StringBuffer();
//		result.append("http://192.168.1.254:802/?");
		setKeysAndValues();
		result.append(HttpAddressParam.getHttpAddress(keys, values));
		Log.i(TAG, result.toString());
		return result.toString();
	}
	
	public String getHttpResponseString() throws IOException{
		HttpGetConn httpGetConn = new HttpGetConn(getHttpAddress());
		return httpGetConn.getJsonResult();
	}
}
