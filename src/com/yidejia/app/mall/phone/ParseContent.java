package com.yidejia.app.mall.phone;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.order.Order;

/**
 * 用来解析获取的订单数据
 * 
 * @author Administrator
 * 
 */
public class ParseContent {
	public ArrayList<Order> getPhoneArrayList() {
		return phoneArrayList;
	}

	private ArrayList<Order> phoneArrayList;

	public ParseContent() {

	}

	public boolean parsePhone(String content) {
		boolean isSucess = false;
		phoneArrayList = new ArrayList<Order>();
		JSONObject object;
		int code;
		try {
			object = new JSONObject(content);
			code = object.optInt("code");
			if (code == 1) {
				String responseString = object.optString("response");
				isSucess = true;
				JSONArray jsonArray = new JSONArray(responseString);
				int length = jsonArray.length();
				JSONObject jsonObject;
				Order order;
				for (int i = 0; i < length; i++) {
					jsonObject = jsonArray.optJSONObject(i);
					order = new Order();
					order.setStatus(jsonObject.optString("pay_status"));
					order.setOrderCode(jsonObject.optString("recharge_code"));
					order.setId(jsonObject.optString("recharge_id"));
					order.setDate(jsonObject.optString("the_time"));
					order.setDetail(jsonObject.optString("goods_detail"));
					order.setNumber(jsonObject.optString("handset"));
					order.setImage(jsonObject.optString("img"));
					order.setPay_money(jsonObject.optString("pay_amount"));
					phoneArrayList.add(order);
				}

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return isSucess;
	}
}
