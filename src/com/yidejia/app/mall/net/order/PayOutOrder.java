package com.yidejia.app.mall.net.order;

import java.io.IOException;

import android.util.Log;

import com.yidejia.app.mall.net.HttpAddressParam;
import com.yidejia.app.mall.net.HttpGetConn;
import com.yidejia.app.mall.util.Md5;

public class PayOutOrder {
	private String[] keys = new String[7];
	private String[] values = new String[7];
	private String TAG = PayOutOrder.class.getName();
	
	/**
	 * 
	 * 只有返回 “success付款成功” 才算提交订单成功。
	 * @param customer_id 客户id
	 * @param code 订单号
	 */
	private void setKeysAndValues(String customer_id, String code){
		keys[0] = "api";
		String api = "ucenter.order.payout";
		values[0] = api;
		keys[1] = "customer_id";
		values[1] = customer_id;
		keys[2] = "code";
		values[2] = code;
		
		
		keys[3] = "key";
//		values[3] = "fw_test";
		values[3] = "fw_mobile";
		keys[4] = "format";
		values[4] = "array";
		keys[5] = "ts";
		long time = System.currentTimeMillis();
		String ts = String.valueOf(time/1000);
		values[5] = ts;
		
		keys[6] = "sign";
		StringBuffer strTemp = new StringBuffer();
//		strTemp.append("ChunTianfw_mobile123456");
		strTemp.append("ChunTianfw_mobile@SDF!TD#DF#*CB$GER@");
		strTemp.append(api);
		strTemp.append(ts);
		Md5 md = new Md5();
		String result = md.getMD5Str(strTemp.toString());
		md = null;
        strTemp = null;
		values[6] = result;
	}
	
	private String getHttpAddress(String customer_id, String code){
		StringBuffer result = new StringBuffer();
		result.append("http://192.168.1.254:802/?");
		setKeysAndValues(customer_id, code);
		result.append(HttpAddressParam.getHttpAddress(keys, values));
		Log.i(TAG, result.toString());
		return result.toString();
	}
	
	private String result = "";
	/**
	 * 
	 * 只有返回 “success付款成功” 才算提交订单成功。
	 * @param customer_id 客户id
	 * @param code 订单号
	 */
	public String getHttpResponseString(String customer_id, String code)throws IOException{
		HttpGetConn conn = new HttpGetConn(getHttpAddress(customer_id, code));
		result = conn.getJsonResult();
		return result;
	}
}
