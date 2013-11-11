package com.yidejia.app.mall.net.order;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.RetOrderInfo;
import com.yidejia.app.mall.net.HttpPostConn;

public class GetReturnOrder {
	
	public GetReturnOrder(){
		retOrders = new ArrayList<RetOrderInfo>();
	}
	
	public String getHttpResp(String user_id, String offset,
			String limit, String token) throws IOException {
		String url = JNICallBack.getHttp4GetReturnList(user_id, offset,
				limit, token);
		HttpPostConn conn = new HttpPostConn(url);
		return conn.getHttpResponse();
	}
	
	/**
	 * 解析返回数据
	 * @param httpResp
	 * @return
	 * @throws JSONException 
	 */
	public boolean analysisReturn(String httpResp) throws JSONException{
		
		JSONObject jsonObject = new JSONObject(httpResp);
		int code = jsonObject.getInt("code");
		if(code == 1){
			
			String msg = jsonObject.getString("response");
//			if("成功".equals(msg)) return true;
			JSONArray jArray = new JSONArray(msg);
			int length = jArray.length();
			for(int i = 0; i < length; i++){
				JSONObject itemObject = jArray.getJSONObject(i);
				RetOrderInfo info = new RetOrderInfo();
				info.setId(itemObject.getString("id"));
				info.setOrderCode(itemObject.getString("order_code"));
				info.setTheDate(itemObject.getString("the_date"));
				info.setContact(itemObject.getString("contact"));
				info.setContact_manner(itemObject.getString("contact_manner"));
				String statusInt = itemObject.getString("status");
				try {
					info.setStatus(statusStrings[Integer.parseInt(statusInt) + 1]);
				} catch (Exception e) {
					// TODO: handle exception
				}
				info.setCause(itemObject.getString("cause"));
				info.setDesc(itemObject.getString("desc"));
				retOrders.add(info);
			}
		}
		return false;
	}
	
	private ArrayList<RetOrderInfo> retOrders;
	/**
	 * 获取退换货列表
	 * <p>
	 * {@link #analysisReturn(String)}必须在这个方法前执行
	 * @return
	 */
	public ArrayList<RetOrderInfo> getRetOrderInfos(){
		return retOrders;
	}
	
	private String[] statusStrings = {"处理失败","正在处理", "退换成功"};
}
