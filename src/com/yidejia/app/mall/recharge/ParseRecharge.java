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

	public ParseRecharge() {
		unicode = new UnicodeToString();
	}
	
	public String getNeedPayUrl(String handset, int amount) {
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
	
}
