package com.yidejia.app.mall.evaluation;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.net.ImageUrl;
import com.yidejia.app.mall.net.goodsinfo.GetProductAddress;

public class ParseEvaJson {

	private ArrayList<Cart> waitCommGoods;

	/**解析待评论商品数据**/
	public boolean parseNoEvaJson(String content) {

		JSONObject httpObject;
		try {
			httpObject = new JSONObject(content);
			int httpCode = httpObject.optInt("code");
			if (httpCode == 1) {
				waitCommGoods = new ArrayList<Cart>();
				String resp = httpObject.optString("response");
				try {
					JSONArray respArray = new JSONArray(resp);
					int length = respArray.length();
					Cart mCart;
					JSONObject arrayObject;
					for (int i = 0; i < length; i++) {
						mCart = new Cart();
						arrayObject = respArray.optJSONObject(i);
						String goodsid = arrayObject.optString("goods_id");
						String price = arrayObject.optString("price");
						String quantity = arrayObject.optString("quantity");
						String evaluate_status = arrayObject
								.optString("evaluate_status");
						if ("y".equals(evaluate_status))
							continue;
						String dry_status = arrayObject.optString("dry_status");
						String imageUrl = ImageUrl.IMAGEURL
								+ arrayObject.optString("imgname") + "!100";
						String goodsname = "";
						GetProductAddress address = new GetProductAddress();
						try {
							String productString = address
									.getProductJsonString(goodsid);
							JSONObject productObject = new JSONObject(
									productString);
							int productHttpCode = productObject.optInt("code");
							if (productHttpCode == 1) {
								String productResp = productObject
										.optString("response");
								JSONObject productRespObject = new JSONObject(
										productResp);
								goodsname = productRespObject
										.optString("goods_name");
							}
						} catch (IOException e) {
							e.printStackTrace();
						} catch (JSONException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
						mCart.setImgUrl(imageUrl);
						mCart.setProductText(goodsname);
						mCart.setUId(goodsid);
						try {
							mCart.setSalledAmmount(Integer.parseInt(quantity));
							mCart.setPrice(Float.parseFloat(price));
						} catch (NumberFormatException e) {
							// mCart.setPrice(0.0f);
							continue;
						}
						waitCommGoods.add(mCart);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return true;
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		return false;
	}

	/**获取待评价商品**/
	public ArrayList<Cart> getWaitCommGoods() {
		return waitCommGoods;
	}
	
}
