package com.yidejia.app.mall.express;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import android.util.Log;

import com.yidejia.app.mall.model.Express;
import com.yidejia.app.mall.model.FreePost;
import com.yidejia.app.mall.util.UnicodeToString;

public class ParseExpressJson {
	
	private UnicodeToString unicode;
	private ArrayList<FreePost> freePosts;//免邮
	private ArrayList<Express> expresses;	//快递费用
	private ArrayList<Express> distributions;// 配送中心
	
	public ParseExpressJson() {
		unicode = new UnicodeToString();
	}

	/**
	 * 解析服务器返回的免邮信息数据
	 * @param content
	 * @return 成功与否
	 */
	public boolean parseFree(String content) {

		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(content);
			int code = jsonObject.optInt("code");
			if (code == 1) {
				String responseString = jsonObject.optString("response");
				analysisFreeJson(responseString);
				return true;
			} else {
				return false;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 解析具体免邮数据
	 * @param responseString
	 * @throws JSONException
	 */
	private void analysisFreeJson(String responseString) throws JSONException{
		JSONArray responseArray = new JSONArray(responseString);
		JSONObject responseObject;
		FreePost frees;
		freePosts = new ArrayList<FreePost>();
		int length = responseArray.length();
		for (int i = 0; i < length; i++) {
			frees = new FreePost();
			responseObject = responseArray.optJSONObject(i);
			frees.setId(responseObject.optString("id"));
			frees.setName(unicode.revert(responseObject.optString("name")));
			frees.setStartDate(responseObject.optString("startdate"));
			frees.setEndDate(responseObject.optString("enddate"));
			frees.setMax(responseObject.optString("max"));
			frees.setDesc(unicode.revert(responseObject.optString("desc")));
			freePosts.add(frees);
		}
	}
	
	/**
	 * 
	 * @return 返回免邮信息列表
	 */
	public ArrayList<FreePost> getFreePosts(){
		return freePosts;
	}
	
	/**
	 * 解析邮费信息
	 * @param content
	 * @return 成功与否
	 */
	public boolean parseExpress(String content){
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(content);
			int code = jsonObject.optInt("code");
			if(code == 1){
				String responseString = jsonObject.optString("response");
				analysisExpressJson(responseString);
				return true;
			} else {
				return false;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	private void analysisExpressJson(String responseString) throws JSONException{
		JSONArray responseArray = new JSONArray(responseString);
		expresses = new ArrayList<Express>();
		JSONObject responseObject;
		Express expressTemp;
		int length = responseArray.length();
		for (int i = 0; i < length; i++) {
			expressTemp = new Express();
			responseObject = responseArray.getJSONObject(i);
			expressTemp.setEms(responseObject.optString("ems"));
			expressTemp.setExpress(responseObject.optString("express"));
//			if(i == 0) Log.i(TAG, "pre_id:" + responseObject.optString("pre_id"));
			expressTemp.setPreId(responseObject.optString("pre_id"));
//			if(i==0)Log.i(TAG, express.getPreId());
			expressTemp.setIsDefault("y".equals(responseObject.optString("is_default")));
			expresses.add(expressTemp);
		}
	}
	/**
	 * 
	 * @return 返回具体邮费列表
	 */
	public ArrayList<Express> getExpresses() {
		return expresses;
	}
	
	/**
	 * 解析配送中心数据
	 * @param content
	 * @return 成功与否
	 */
	public boolean parseDist(String content) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(content);
			int code = jsonObject.optInt("code");
			if(code == 1){
				String responseString = jsonObject.optString("response");
				analysisDisJson(responseString);
				return true;
			} else {
				return false;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private void analysisDisJson(String responseString) throws JSONException{
		JSONArray responseArray = new JSONArray(responseString);
		JSONObject responseObject;
		Express express;
		distributions = new ArrayList<Express>();
		int length = responseArray.length();
		for (int i = 0; i < length; i++) {
			express = new Express();
			responseObject = responseArray.optJSONObject(i);
			express.setDisName(unicode.revert(responseObject.optString("name")));
			express.setDisDesc(unicode.revert(responseObject.optString("desc")));
			express.setPreId(responseObject.optString("id"));
			distributions.add(express);
		}
	}
	
	/**
	 * 
	 * @return 返回配送中心列表
	 */
	public ArrayList<Express> getDistributions() {
		return distributions;
	}
}
