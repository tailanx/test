package com.yidejia.app.mall.msg;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.model.MsgCenter;
import com.yidejia.app.mall.util.TimeUtil;
import com.yidejia.app.mall.util.UnicodeToString;

import android.util.Log;

/**
 * 解析消息中心数据
 * @author LongBin
 *
 */
public class ParseMsg {
	
	private MsgCenter msgCenter;	//消息
	private ArrayList<MsgCenter> msgs;	//消息中心列表
	private String errMsg;	//出错时返回的提示信息
	
	/**
	 * 解析服务器返回消息中心的信息
	 * @param content 服务器返回信息
	 * @return
	 */
	public boolean parseMsg(String content){
		try {
			JSONObject httpObject = new JSONObject(content);
			int code = httpObject.optInt("code");
			UnicodeToString unicode = new UnicodeToString();
			if(1 == code){
				msgs = new ArrayList<MsgCenter>();
				String strResp = httpObject.optString("response");
				JSONArray respArray = new JSONArray(strResp);
				int length = respArray.length();
				for (int i = 0; i < length; i++) {
					MsgCenter msgCenter = new MsgCenter();
					JSONObject respObject = respArray.optJSONObject(i);
					String id = respObject.optString("id");
					msgCenter.setMsgid(id);
					String subject = respObject.optString("subject");
					msgCenter.setSubject(unicode.revert(subject));
					String title = respObject.optString("title");
					msgCenter.setTitle(unicode.revert(title));
					String strTime = respObject.optString("created");
					try {
						long time = Long.parseLong(strTime);
						msgCenter.setTime(TimeUtil.convert(time));
					} catch (NumberFormatException e) {
					}
					msgs.add(msgCenter);
				}
				return true;
			} else if(-1 == code){
				return true;
			} else {
				errMsg = httpObject.optString("msg");
			}
		} catch (JSONException e) {
			Log.e(getClass().getName(), "解析消息中心数据出错：" + content);
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 解析服务器返回消息中心的信息
	 * @param content 服务器返回信息
	 * @return
	 */
	public boolean parseMsgDetails(String content){
		try {
			JSONObject httpObject = new JSONObject(content);
			int code = httpObject.optInt("code");
			UnicodeToString unicode = new UnicodeToString();
			if (1 == code) {
				String strResp = httpObject.optString("response");

				MsgCenter msgCenter = new MsgCenter();
				JSONObject respObject = new JSONObject(strResp);
				String id = respObject.optString("id");
				msgCenter.setMsgid(id);
				String subject = respObject.optString("subject");
				msgCenter.setSubject(unicode.revert(subject));
				String title = respObject.optString("title");
				msgCenter.setTitle(unicode.revert(title));
				String msgContent = respObject.optString("txt_content");
				msgCenter.setMsg(msgContent);
				return true;
			} else {
				errMsg = httpObject.optString("msg");
			}
		} catch (JSONException e) {
			Log.e(getClass().getName(), "解析消息中心数据出错：" + content);
			e.printStackTrace();
		}
		return false;
	}

	
	/**消息详情**/
	public MsgCenter getMsgCenter() {
		return msgCenter;
	}

	/**获取消息列表**/
	public ArrayList<MsgCenter> getMsgs() {
		return msgs;
	}
	
	public String getErrMsg(){
		return errMsg;
	}
	
}
