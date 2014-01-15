package com.yidejia.app.mall.express;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.model.Specials;
import com.yidejia.app.mall.net.ImageUrl;

public class ParseCredit {

	private String voucherNum; // 积分
	private ArrayList<Specials> freeProductArray; // 免费送商品
	private ArrayList<Specials> scoresProductArray; // 积分换购商品

	/**
	 * 解析用户可用积分数据
	 * 
	 * @param content
	 * @return
	 */
	public boolean parseCredit(String content) {
		JSONObject httpObject;
		try {
			httpObject = new JSONObject(content);
			int code = httpObject.optInt("code");
			if (code == 1) {
				String response = httpObject.optString("response");
				JSONObject resObject = new JSONObject(response);
				voucherNum = resObject.optString("can_use_score");
				return true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * @return 返回可用积分
	 */
	public String getCreditNum() {
		return voucherNum;
	}

	/**
	 * 解析积分换购商品数据
	 * 
	 * @param content
	 * @return
	 */
	public boolean parseVerify(String content) {
		JSONObject httpJsonObject;
		try {
			httpJsonObject = new JSONObject(content);
			int code = httpJsonObject.optInt("code");
			if (code == 1) {
				String response = httpJsonObject.optString("response");
				analysisActive(response);
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void analysisActive(String response) {
		JSONArray resArray;
		try {
			resArray = new JSONArray(response);
			scoresProductArray = new ArrayList<Specials>();
			freeProductArray = new ArrayList<Specials>();
			for (int i = 0; i < resArray.length(); i++) {
				JSONObject itemObject = resArray.optJSONObject(i);
				// String activeId = itemObject.optString("id");
				String goods_list = "";
				try {
					goods_list = itemObject.optString("good_list");
					JSONArray goodsArray = new JSONArray(goods_list);
					Specials specials;
					JSONObject listObject;
					for (int j = 0; j < goodsArray.length(); j++) {
						specials = new Specials();
						listObject = goodsArray.optJSONObject(j);
						if (null == listObject) {
							continue;
						}
						specials.setUId(listObject.optString("goods_id"));
						specials.setImgUrl(ImageUrl.IMAGEURL
								+ listObject.optString("imgname") + "!100");
						specials.setPrice(listObject.optString("price"));
						String score_price = listObject
								.optString("score_price");
						specials.setScores(score_price);
						specials.setBrief(listObject.optString("desc"));
						try {
							if (Float.parseFloat(score_price) > 0.01F) {
								scoresProductArray.add(specials);
							} else {
								freeProductArray.add(specials);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取免费送商品
	 */
	public ArrayList<Specials> getFreeGoods() {
		return freeProductArray;
	}

	/**
	 * 获取积分换购商品
	 */
	public ArrayList<Specials> getScoreGoods() {
		return scoresProductArray;
	}
}
