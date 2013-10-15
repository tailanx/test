package com.yidejia.app.mall.net.order;

import java.io.IOException;

import android.util.Log;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpAddressParam;
import com.yidejia.app.mall.net.HttpGetConn;
import com.yidejia.app.mall.net.HttpPostConn;
import com.yidejia.app.mall.util.Md5;
/**
 * ȡ������
 * @author long bin
 *
 */
public class CancelOrder {
	private String[] keys = new String[7];
	private String[] values = new String[7];
	private String TAG = CancelOrder.class.getName();
	
	/**
	 * 
	 * ֻ�з��� ��successȡ���ɹ��� �����ύ�����ɹ���
	 * @param customer_id �ͻ�id
	 * @param code ������
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
	 * ֻ�з��� ��successȡ���ɹ��� �����ύ�����ɹ���
	 * @param customer_id �ͻ�id
	 * @param code ������
	 */
	public String getHttpResponseString(String customer_id, String code, String token)throws IOException{
//		HttpGetConn conn = new HttpGetConn(getHttpAddress(customer_id, code));
//		result = conn.getJsonResult();
		HttpPostConn conn = new HttpPostConn(JNICallBack.getHttp4CancelOrder(customer_id, code, token));
		result = conn.getHttpResponse();
		return result;
	}
}
