package com.yidejia.app.mall.order;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 解析退换货数据
 * @author LongBin
 *
 */
public class ParseReturnJson {
	
	private ArrayList<RetOrderInfo> retOrders;
	private String[] statusStrings = {"处理失败","正在处理", "退换成功"};

	/**
	 * 解析返回数据
	 * @param httpResp
	 * @return
	 * @throws JSONException 
	 */
	public boolean parseReturn(String httpResp){
		
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(httpResp);
			int code = jsonObject.optInt("code");
			if(code == 1){
				retOrders = new ArrayList<RetOrderInfo>();
				String msg = jsonObject.optString("response");
				JSONArray jArray = new JSONArray(msg);
				int length = jArray.length();
				for(int i = 0; i < length; i++){
					JSONObject itemObject = jArray.optJSONObject(i);
					RetOrderInfo info = new RetOrderInfo();
					info.setId(itemObject.optString("id"));
					info.setOrderCode(itemObject.optString("order_code"));
					info.setTheDate(itemObject.optString("the_date"));
					info.setContact(itemObject.optString("contact"));
					info.setContact_manner(itemObject.optString("contact_manner"));
					String statusInt = itemObject.optString("status");
					try {
						info.setStatus(statusStrings[Integer.parseInt(statusInt) + 1]);
					} catch (Exception e) {
						e.printStackTrace();
					}
					info.setCause(itemObject.optString("cause"));
					info.setDesc(itemObject.optString("desc"));
					retOrders.add(info);
				}
				return true;
			} else if(code == -1){
				return true;
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 获取退换货列表
	 * <p>
	 * {@link #analysisReturn(String)}必须在这个方法前执行
	 * @return
	 */
	public ArrayList<RetOrderInfo> getRetOrderInfos(){
		return retOrders;
	}
	
	
}
