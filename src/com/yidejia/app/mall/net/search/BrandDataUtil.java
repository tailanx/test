package com.yidejia.app.mall.net.search;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.Brand;
import com.yidejia.app.mall.net.HttpAddressParam;
import com.yidejia.app.mall.net.HttpGetConn;
import com.yidejia.app.mall.util.Md5;
import com.yidejia.app.mall.util.UnicodeToString;

public class BrandDataUtil {

	private String TAG = BrandDataUtil.class.getName();
	private ArrayList<Brand> brandsArray;
	private UnicodeToString unicode;
	
	public BrandDataUtil(){
		unicode = new UnicodeToString();
		brandsArray = new ArrayList<Brand>();
	}

	public String getHttpResponseString() throws IOException {
		// HttpGetConn httpGetConn = new HttpGetConn(getHttpAddress());
		HttpGetConn httpGetConn = new HttpGetConn(
				new JNICallBack().getHttp4GetBrand(), true);
		return httpGetConn.getJsonResult();
	}

	public boolean analysis(String httpresp) {
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
		return false;
	}
	
	
	private void analysisJson(String response) throws JSONException{
		JSONArray responseArray = new JSONArray(response);
		int length = responseArray.length();
//		JSONObject jsonObject;
		Brand brand;
		JSONArray itemArray;
		for (int i = 0; i < length; i++) {
			brand = new Brand();
			itemArray = responseArray.getJSONArray(i);
			int count = itemArray.length();
			for (int j = 0; j < count; j++) {
				if(j == 0)
					brand.setBrandName(itemArray.getString(j));
				if(j < count - 1)
					brand.setDesc(unicode.revert(itemArray.getString(j+1)));
			}
			brandsArray.add(brand);
		}
	}
	
	public ArrayList<Brand> getBrands(){
		return brandsArray;
	}
}
