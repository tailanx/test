package com.yidejia.app.mall.net.skin;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.SkinAnswer;
import com.yidejia.app.mall.net.HttpPostConn;

public class Answer {
	
	private SkinAnswer answer;
	private String message;
	
	/**
	 * 获取皮肤测试答案的http 返回数据
	 * @param handset 用户手机
	 * @param qq 用户qq号
	 * @param name 用户名称
	 * @param gender 性别
	 * @param birthday 生日
	 * @param improve_type 最想解决的皮肤问题
	 * @param skin_type 皮肤特性
	 * @param want_type 需求类型
	 * @param brand 使用的品牌
	 * @param channel 购买途径
	 * @param cpsid 员工cps id
	 * @param ip ip地址
	 * @return
	 */
	public String getHttpResp(String handset, String qq, String name, String gender, String birthday, String improve_type,
			String skin_type, String want_type, String brand, String channel, String cpsid, String ip){
		String url = new JNICallBack().getHttp4SkinAnswer(handset, qq, name, gender, birthday, improve_type, skin_type, want_type, brand, channel, cpsid, ip);
		HttpPostConn conn = new HttpPostConn(url);
		String result ="";
		try {
			result = conn.getHttpResponse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean analysis(String httpresp){
		try {
			JSONObject jsonObject = new JSONObject(httpresp);
			int code = jsonObject.getInt("code");
			if(code == 1){
				answer = new SkinAnswer();
				String resp = jsonObject.getString("response");
				JSONObject respJsonObject = new JSONObject(resp);
				answer.setDesc(respJsonObject.getString("desc"));
				answer.setSuggest(respJsonObject.getString("suggest"));
				return true;
			} else if(code == -1) {
				message = jsonObject.getString("response");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	public SkinAnswer getAnswer() {
		return answer;
	}

	public String getMessage() {
		return message;
	}
	
}
