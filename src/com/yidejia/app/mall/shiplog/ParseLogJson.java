package com.yidejia.app.mall.shiplog;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 解析快递信息数据
 * @author LongBin
 *
 */
public class ParseLogJson {
	
	private ArrayList<ShipLog> shipLogs;
	
	/**
	 * 解析物流信息数据列表
	 * @param httpResp
	 * @return 
	 */
	public boolean parseShipLog(String httpResp){
		try {
			JSONObject httpJsonObject = new JSONObject(httpResp);
			int code = httpJsonObject.optInt("code");
			if(code == 1){
				shipLogs = new ArrayList<ShipLog>();
				String respString = httpJsonObject.optString("response");
				JSONArray respArray = new JSONArray(respString);
				int length = respArray.length();
				ShipLog mLog = null;
				JSONObject itemObject = null;
				for (int i = 0; i < length; i++) {
					mLog = new ShipLog();
					itemObject = respArray.optJSONObject(i);
					mLog.setContext(itemObject.optString("context"));
					mLog.setTime(itemObject.optString("time"));
					shipLogs.add(mLog);
				}
				return true;
			}
		} catch (JSONException e) {
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
