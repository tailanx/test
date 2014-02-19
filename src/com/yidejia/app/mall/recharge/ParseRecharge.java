package com.yidejia.app.mall.recharge;

import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.util.UnicodeToString;

public class ParseRecharge {

	private UnicodeToString unicode;
	private String errMsg;
	private String cardid;
	private String cardname;
	private double inPrice;
	private String game_area;
	
	private String czOrderCode;	//充值的订单号
	private String tn;	//银联充值的流水号

	public ParseRecharge() {
		unicode = new UnicodeToString();
	}
	
	public String getNeedPayUrl(String handset, String amount) {
		return "http://u.yidejia.com/index.php?m=of&c=index&a=telquery&handset="
				+ handset + "&amount=" + amount;
	}

	public boolean parseNeedPay(String content) {
		try {
			JSONObject jsonObject = new JSONObject(content);
			String retcode = jsonObject.optString("retcode");
			if ("1".equals(retcode)) {
				cardid = jsonObject.optString("cardid");
				cardname = unicode.revert(jsonObject.optString("cardname"));
				inPrice = jsonObject.optDouble("inprice");
				game_area = unicode.revert(jsonObject.optString("game_area"));
				return true;
			} else {
				errMsg = unicode.revert(jsonObject.optString("err_msg"));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public String getCardid() {
		return cardid;
	}

	public String getCardname() {
		return cardname;
	}

	public double getInPrice() {
		return inPrice;
	}

	public String getGame_area() {
		return game_area;
	}
	
	/**解析充值获取订单号的数据**/
	public boolean parseCZOrder(String content){
		try {
			JSONObject httpObject = new JSONObject(content);
			int code = httpObject.optInt("code");
			if(1 == code){
				czOrderCode = httpObject.optString("response");
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**获取充值订单的订单号**/
	public String getCzOrderCode() {
		return czOrderCode;
	}
	
	public boolean parseCZUnion(String content){
		try {
			JSONObject httpObject = new JSONObject(content);
			int code = httpObject.optInt("code");
			if(1 == code){
				tn = httpObject.optString("response");
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**获取银联充值的流水号**/
	public String getTn() {
		return tn;
	}
}
