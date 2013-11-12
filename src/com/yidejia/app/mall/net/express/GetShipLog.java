package com.yidejia.app.mall.net.express;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.ShipLog;
import com.yidejia.app.mall.net.HttpGetConn;

public class GetShipLog {
	
	private ArrayList<ShipLog> shipLogs;
	
	/**
	 * 根据快递单号获取物流信息
	 * @param code
	 * @return
	 * @throws IOException
	 */
	public String getHttpResp(String code) throws IOException{
		HttpGetConn conn = new HttpGetConn(JNICallBack.getHttp4GetShipLog(code),true);
		return conn.getJsonResult();
	}
	
	/**
	 * 解析物流信息数据列表
	 * @param httpResp
	 * @return 
	 */
	public boolean analysisHttpResp(String httpResp){
		shipLogs = new ArrayList<ShipLog>();
		try {
			JSONObject httpJsonObject = new JSONObject(httpResp);
			int code = httpJsonObject.getInt("code");
			if(code == 1){
				String respString = httpJsonObject.getString("response");
				JSONArray respArray = new JSONArray(respString);
				int length = respArray.length();
				ShipLog mLog = null;
				JSONObject itemObject = null;
				for (int i = 0; i < length; i++) {
					mLog = new ShipLog();
					itemObject = respArray.getJSONObject(i);
					mLog.setContext(itemObject.getString("context"));
					mLog.setTime(itemObject.getString("time"));
					shipLogs.add(mLog);
				}
				return true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 获取物流列表<p>
	 * {@link #analysisHttpResp(String)} 必须在{@link #getShipLogs()} 之前调用
	 * @return
	 */
	public ArrayList<ShipLog> getShipLogs(){
		return shipLogs;
	}
}
