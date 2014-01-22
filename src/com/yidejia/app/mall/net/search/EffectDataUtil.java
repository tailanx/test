package com.yidejia.app.mall.net.search;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.Function;
import com.yidejia.app.mall.net.HttpGetConn;
import com.yidejia.app.mall.util.UnicodeToString;

public class EffectDataUtil {
	private String TAG = EffectDataUtil.class.getName();
	private ArrayList<Function> functionsArray;
	private UnicodeToString unicode;
	
	public EffectDataUtil(){
		unicode = new UnicodeToString();
		functionsArray = new ArrayList<Function>();
	}
	
	public String getHttpResponseString() throws IOException, TimeOutEx{
//		HttpGetConn httpGetConn = new HttpGetConn(getHttpAddress(where, offset, limit, fields));
		HttpGetConn httpGetConn = new HttpGetConn(new JNICallBack().getHttp4GetEffect("flag%3D%27y%27", "0", "20", "", "", "%2A"), true);
		//"flag%3D%27y%27", "0", "20", "%2A"
		return httpGetConn.getJsonResult();
	}
	
	public boolean analysis(String httpresp){
		boolean issuccess = false;
		if(!"".equals(httpresp)){
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(httpresp);
				int code = jsonObject.getInt("code");
				if(code == 1){
					String response = jsonObject.getString("response");
					analysisJson(response);
					return true;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		return issuccess;
	}
	
	private void analysisJson(String response){
		JSONArray responseArray;
		try {
			responseArray = new JSONArray(response);
			int length = responseArray.length();
			JSONObject jsonObject;
			Function function;
			
			for (int i = 0; i < length; i++) {
				function = new Function();
				jsonObject = responseArray.getJSONObject(i);
				function.setFunId(jsonObject.getString("id"));
				function.setFunName(unicode.revert(jsonObject.getString("name")));
				function.setDesc(unicode.revert(jsonObject.getString("desc")));
				functionsArray.add(function);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Function> getFunctions(){
		return functionsArray;
	}
}
