package com.yidejia.app.mall.net.search;

import java.io.IOException;

import android.util.Log;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpAddressParam;
import com.yidejia.app.mall.net.HttpGetConn;
import com.yidejia.app.mall.util.Md5;

public class EffectDataUtil {
	private String[] keys = new String[9];
	private String[] values = new String[9];
	private String TAG = EffectDataUtil.class.getName();
	
	private void setKeysAndValues(String where, String offset, String limit, String fields){
		keys[0] = "api";
		String api = "product.effect.getList";
		values[0] = api;
		keys[1] = "where";
		values[1] = where;
	
		keys[2] = "option%5Boffset%5D";
		values[2] = offset;
		keys[3] = "option%5Blimit%5D";
		values[3] = limit;
		keys[4] = "fields";
		values[4] = fields;
		
		keys[5] = "key";
//		values[5] = "fw_test";
		values[5] = "fw_mobile";
		keys[6] = "format";
		values[6] = "array";
		
		keys[7] = "ts";
		long time = System.currentTimeMillis();
		String ts = String.valueOf(time/1000);
		values[7] = ts;
		
		keys[8] = "sign";
		StringBuffer strTemp = new StringBuffer();
//		strTemp.append("ChunTianfw_mobile123456");
		strTemp.append("ChunTianfw_mobile@SDF!TD#DF#*CB$GER@");
		strTemp.append(api);
		strTemp.append(ts);
		Md5 md = new Md5();
		String result = md.getMD5Str(strTemp.toString());
		md = null;
        strTemp = null;
		values[8] = result;
	}
	
	private String getHttpAddress(String where, String offset, String limit, String fields){
		StringBuffer result = new StringBuffer();
//		result.append("http://192.168.1.254:802/?");
		setKeysAndValues(where, offset, limit, fields);
		result.append(HttpAddressParam.getHttpAddress(keys, values));
		Log.i(TAG, result.toString());
		return result.toString();
	}
	
	public String getHttpResponseString(String where, String offset, String limit, String fields) throws IOException{
		HttpGetConn httpGetConn = new HttpGetConn(getHttpAddress(where, offset, limit, fields));
//		HttpGetConn httpGetConn = new HttpGetConn(JNICallBack.getHttp4GetEffect(where, offset, limit, group, order, fields), true);
		return httpGetConn.getJsonResult();
	}
	
	public String getHttpResponseString(String where, String offset, String limit, String group, String order, String fields) throws IOException{
//		HttpGetConn httpGetConn = new HttpGetConn(getHttpAddress(where, offset, limit, fields));
		HttpGetConn httpGetConn = new HttpGetConn(JNICallBack.getHttp4GetEffect(where, offset, limit, group, order, fields), true);
		return httpGetConn.getJsonResult();
	}
}
