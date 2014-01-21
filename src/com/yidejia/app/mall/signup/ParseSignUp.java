package com.yidejia.app.mall.signup;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 解析签到数据
 * @author LongBin
 *
 */
public class ParseSignUp {

	private int signCount = 0;	//获取总签到次数
	private String msg = "";	//返回错误数据信息
	
	/**
	 * 解析服务器返回数据获取总签到次数
	 * @param content 解析服务器返回数据
	 * @return
	 */
	public boolean parseSignCount(String content) {
		try {
			JSONObject jsonObject = new JSONObject(content);
			int code = jsonObject.optInt("code");
			if (1 == code) {
				signCount = jsonObject.optInt("response");
			} else {
				msg = jsonObject.optString("msg");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 *  
	 * @return 获取总签到次数
	 */
	public int getSignCount() {
		return signCount;
	}
	
	/**
	 * 
	 * @return 出错时的信息
	 */
	public String getMsg(){
		return msg;
	}
}
