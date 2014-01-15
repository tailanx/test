package com.yidejia.app.mall.order;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.util.UnicodeToString;

public class ParseOrder {

	private Context context;
	private String orderCode;
	private String resp_code;
	private String tn;
	private UnicodeToString unicode;

	public ParseOrder(Context context) {
		this.context = context;
		unicode = new UnicodeToString();
	}

	/**
	 * 解析提交订单数据
	 * @param content
	 * @return
	 */
	public boolean parseSaveOrder(String content) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(content);
			int code = jsonObject.optInt("code");
			if (code == 1) {
				String response = jsonObject.optString("response");
				JSONObject responseObject = new JSONObject(response);
				String result = responseObject.optString("@p_result");
				if (unicode.revert(result).equals(
						context.getResources().getString(
								R.string.success_save_order))) {
					orderCode = responseObject.optString("@p_order_code");
					resp_code = responseObject.optString("@p_resp_code");
					tn = responseObject.optString("@p_tn");
					return true;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * @return 提交订单成功返回的订单号
	 */
	public String getOrderCode() {
		return orderCode;
	}

	/**
	 * 
	 * @return 银联返回的订单状态
	 */
	public String getResp_code() {
		return resp_code;
	}

	/**
	 * 
	 * @return 银联返回的流水号
	 */
	public String getTn() {
		return tn;
	}
	
	
}
