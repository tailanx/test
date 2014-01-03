package com.yidejia.app.mall.net.address;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpPostConn;

public class SetDefAddr {
	
	private String TAG = SetDefAddr.class.getName();
	
	public String getHttpResponse(String cid, String aid, String token) throws IOException, TimeOutEx{
		HttpPostConn conn = new HttpPostConn(new JNICallBack().getHttp4SetDefAddr(cid, aid, token));
		return conn.getHttpResponse();
	}
	
	public boolean analysicSetDefJson(String resultJson) {
		if ("".equals(resultJson) || resultJson == null)
			return false;
		
		JSONObject httpResultObject;
		try {
			httpResultObject = new JSONObject(resultJson);
			int code = httpResultObject.optInt("code");
			if (code == 1)
				return true;
			else return false;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "delete address analysis json jsonexception error");
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "delete address analysis json exception error");
			return false;
		}
	}
}
