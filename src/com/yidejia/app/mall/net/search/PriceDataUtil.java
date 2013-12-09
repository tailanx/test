package com.yidejia.app.mall.net.search;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.PriceLevel;
import com.yidejia.app.mall.net.HttpAddressParam;
import com.yidejia.app.mall.net.HttpGetConn;
import com.yidejia.app.mall.util.Md5;

public class PriceDataUtil {
	private String TAG = PriceDataUtil.class.getName();
	private ArrayList<PriceLevel> pricesArray;
	
	public PriceDataUtil(){
		pricesArray = new ArrayList<PriceLevel>();
	}
	
	public String getHttpResponseString() throws IOException, TimeOutEx{
//		HttpGetConn httpGetConn = new HttpGetConn(getHttpAddress());
		HttpGetConn httpGetConn = new HttpGetConn(new JNICallBack().getHttp4GetPrice(), true);
		return httpGetConn.getJsonResult();
	}
	
	
	public boolean analysis(String httpresp){
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
		return false;
	}
	
	private void analysisJson(String response) throws JSONException{
		JSONObject responseArray = new JSONObject(response);
		int length = responseArray.length();
//		JSONObject jsonObject;
		PriceLevel price;
		JSONObject itemArray;
		for (int i = 0; i < length; i++) {
			price = new PriceLevel();
			String itemString = responseArray.getString("" + (i+1));
			itemArray = new JSONObject(itemString);
			price.setPriceId(itemArray.getString("id"));
			price.setMinPrice(itemArray.getString("min"));
			price.setMaxPrice(itemArray.getString("max"));
			pricesArray.add(price);
		}
	}
	
	public ArrayList<PriceLevel> getPriceLevels(){
		return pricesArray;
	}
}
