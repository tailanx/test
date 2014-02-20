package com.yidejia.app.mall.shark;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.yidejia.app.mall.model.ProductBaseInfo;
import com.yidejia.app.mall.net.ImageUrl;

/**
 * 解析摇一摇数据
 * 
 * @author LongBin
 * 
 */
public class ParseShark {

	public int getCount() {
		return count;
	}

	private int theType;
	private String data;
	private int count;// 剩下的摇一摇的次数

	private ProductBaseInfo productBaseInfo;

	/**
	 * 解析摇一摇返回结果
	 * 
	 * @param content
	 * @return
	 */
	public boolean parseShark(String content) {
		try {
			JSONObject httpObject = new JSONObject(content);
			int code = httpObject.optInt("code");
			String strResp = httpObject.optString("response");
			if (code == 1) {
				JSONObject respObject = new JSONObject(strResp);
				theType = respObject.optInt("the_type");
				data = respObject.optString("data");
				count = respObject.optInt("times");
				switch (theType) {
				case 1:

					break;
				case 2:
					// 解析摇到商品的数据
					parseGoods(data);
					break;
				default:
					break;
				}
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 解析摇到商品的数据
	 * 
	 * @param data
	 */
	private void parseGoods(String strData) {
		try {
			Log.e("info", strData);
			JSONObject dataObject = new JSONObject(strData);

			productBaseInfo = new ProductBaseInfo();

			productBaseInfo.setUId(dataObject.optString("goods_id"));
			productBaseInfo.setName(dataObject.optString("goods_name"));
			productBaseInfo.setPrice(dataObject.optString("price"));
			productBaseInfo.setBrands(dataObject.optString("brand"));
			productBaseInfo.setSalledAmount(dataObject.optString("sells"));
			productBaseInfo.setCommentAmount(dataObject.optString("remarks"));
			productBaseInfo.setProductNumber(dataObject.optString("the_code"));
			productBaseInfo.setImgUrl(ImageUrl.IMAGEURL + dataObject.optString("imgname") + "!200");
			productBaseInfo.setProductSpecifications(dataObject
					.optString("sepc"));
			productBaseInfo.setShowListAmount(dataObject.optString("shaidan"));
			productBaseInfo.setShow_flag("y"
					.equals(dataObject.optString("is_valid")));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取返回数据类型, <br>
	 * the_type = 1 :什么也没摇到
	 * 
	 * <br>
	 * the_type = 2 :摇到了某一个产品
	 * 
	 * <br>
	 * the_type = 3: 礼品券
	 * 
	 * <br>
	 * the_type = 4 :现金券
	 * 
	 * @return
	 */
	public int getTheType() {
		return theType;
	}

	/** 摇到商品时返回的商品信息 **/
	public ProductBaseInfo getProductBaseInfo() {
		return productBaseInfo;
	}

	/** 什么都没摇到时提示信息 **/
	public String getData() {
		return data;
	}

}
