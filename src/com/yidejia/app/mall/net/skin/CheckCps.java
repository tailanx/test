package com.yidejia.app.mall.net.skin;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpGetConn;

public class CheckCps {
	
	public String getHttpResp(String cpsid) throws TimeOutEx{
		HttpGetConn conn = new HttpGetConn(new JNICallBack().getHttp4CheckCps(cpsid), true);
		String httpresp = "";
		try {
			httpresp = conn.getJsonResult();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return httpresp;
	}
	
	/**
	 * 返回cps是否为正确的cps
	 * @param httpresp
	 * @return
	 */
	public boolean isCpsTrue(String httpresp){
		try {
			JSONObject jsonObject = new JSONObject(httpresp);
			int code = jsonObject.optInt("code");
			if(code == 1){
				return true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
}
