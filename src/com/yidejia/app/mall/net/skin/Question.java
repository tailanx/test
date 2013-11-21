package com.yidejia.app.mall.net.skin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.Skin;
import com.yidejia.app.mall.model.SkinQOption;
import com.yidejia.app.mall.net.HttpGetConn;

public class Question {
	
	private String message = "";
	private ArrayList<Skin> skinQs;
	
	public Question(){
		skinQs = new ArrayList<Skin>();
	}
	
	public String getHttpResp(){
		String url = new JNICallBack().getHttp4SkinQuestion();
		HttpGetConn conn = new HttpGetConn(url, true);
		String result = null;
		try {
			result = conn.getJsonResult();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 解析http 返回的数据
	 * @param httpresp 待解析的数据
	 * @return 解析成功与否
	 */
	public boolean analysis(String httpresp){
		boolean issuccess = false;
		
		try {
			JSONObject httpJsonObject = new JSONObject(httpresp);
			int code = httpJsonObject.getInt("code");
			if(code == 1){
				String resp = httpJsonObject.getString("response");
				analysisResp(resp);
				issuccess = true;
			} else {
				issuccess = false;
				message = httpJsonObject.getString("response");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return issuccess;
	}
	
	private boolean analysisResp(String resp){
		Skin skin = null;
		try {
			JSONArray jsonArray = new JSONArray(resp);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject itemObject = jsonArray.getJSONObject(i);
				skin = new Skin();
				skin.setName(itemObject.getString("name"));
				skin.setQuestion(itemObject.getString("question"));
				skin.setNeed(itemObject.getBoolean("need"));
				try{
					String answer = itemObject.getString("answer");
					analysisAnswer(answer, skin);
				} catch(Exception e){
					e.printStackTrace();
				}
				skinQs.add(skin);
			}
			return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean analysisAnswer(String answer, Skin skin){
		SkinQOption option = null;
		ArrayList<SkinQOption> options = new ArrayList<SkinQOption>();
		try {
			JSONArray aJsonArray = new JSONArray(answer);
			for (int i = 0; i < aJsonArray.length(); i++) {
				String value = (String)aJsonArray.opt(i);
				option = new SkinQOption();
				option.setValue(value);
				options.add(option);
			}
			skin.setOptions(options);
			return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		try {
			JSONObject aJsonObject = new JSONObject(answer);
			@SuppressWarnings("unchecked")
			Iterator<String> iterator = aJsonObject.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				option = new SkinQOption();
				option.setKey(key);
				option.setValue(aJsonObject.getString(key));
				options.add(option);
			}
			skin.setOptions(options);
			return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	
	public String getMsg(){
		return message;
	}

	/**
	 * 获取皮肤测试的问题
	 * @return
	 */
	public ArrayList<Skin> getSkinQs() {
		return skinQs;
	}
	
}
