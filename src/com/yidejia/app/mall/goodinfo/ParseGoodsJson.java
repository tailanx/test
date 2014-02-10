package com.yidejia.app.mall.goodinfo;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

import com.yidejia.app.mall.model.BaseProduct;
import com.yidejia.app.mall.model.MainProduct;
import com.yidejia.app.mall.model.ProductBaseInfo;
import com.yidejia.app.mall.model.UserComment;
import com.yidejia.app.mall.net.ImageUrl;
import com.yidejia.app.mall.util.UnicodeToString;

/**
 * 解析服务器返回的商品信息数据,包括基本信息，评论
 * @author LongBin
 *
 */
public class ParseGoodsJson {

	private UnicodeToString unicode;
	private String TAG = ParseGoodsJson.class.getName();
	
	private ProductBaseInfo productBaseInfo;
	private ArrayList<BaseProduct> bannerArray;			//轮播商品
	private ArrayList<MainProduct> recommendArray;    ///推荐搭配 
	
	private ArrayList<UserComment> userComments;
	
	private String imageUrlString;
	private boolean isNoMore = false;	//判断是否没有更多数据了
	
	public ParseGoodsJson() {
		unicode = new UnicodeToString();
	}
	
	/**
	 * 解析http返回基本信息数据
	 * @param httpresp
	 * @return
	 */
	public boolean parseGoodsInfo(String httpresp){
		try {
			JSONObject jsonObject = new JSONObject(httpresp);
			int code = jsonObject.optInt("code");
			String resp = jsonObject.optString("response");
			if(code == 1){
				parseProductJson(resp);
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 返回商品基本信息数据
	 * @return
	 */
	public ProductBaseInfo getProductBaseInfo(){
		return productBaseInfo;
	}
	
	/**
	 * 解析商品基本信息的json数据
	 * @param jsonString
	 * @throws JSONException
	 */
	private boolean parseProductJson(String jsonString){
		try {
			
			productBaseInfo = new ProductBaseInfo();
			bannerArray = new ArrayList<BaseProduct>();
			recommendArray = new ArrayList<MainProduct>();
			
			JSONObject responseObject = new JSONObject(jsonString);
			
			productBaseInfo.setUId(responseObject.optString("goods_id"));
			productBaseInfo.setName(unicode.revert(responseObject.optString("goods_name")));
			productBaseInfo.setPrice(responseObject.optString("price"));
			productBaseInfo.setSalledAmount(responseObject.optString("sells"));
			productBaseInfo.setCommentAmount(responseObject.optString("remarks"));
			productBaseInfo.setBrands(responseObject.optString("brand"));
			productBaseInfo.setProductNumber(responseObject.optString("the_code"));
			productBaseInfo.setProductSpecifications(responseObject.optString("sepc"));
			productBaseInfo.setProductDetailUrl(responseObject.optString("info"));
			productBaseInfo.setShowListAmount(responseObject.optString("shaidan"));
			productBaseInfo.setShow_flag(responseObject.optString("show_flag").equals("y"));
			
			
			String pics = responseObject.optString("pics");
			analysisPicJson(pics, bannerArray);
			if(!TextUtils.isEmpty(imageUrlString) && !"null".equals(imageUrlString)){
				productBaseInfo.setImgUrl(ImageUrl.IMAGEURL+imageUrlString + "!100");
			}
			productBaseInfo.setBannerArray(bannerArray);
			
			String collects = responseObject.optString("collects");
			analysisRecmJson(collects, recommendArray);
			productBaseInfo.setRecommendArray(recommendArray);
			return true;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 解析轮播图片数据
	 * @param picString
	 * @param picList
	 * @return
	 */
	private String analysisPicJson(String picString, ArrayList<BaseProduct> picList) {
		JSONObject responseArray;
		try {
			responseArray = new JSONObject(picString);
			imageUrlString = responseArray.optString("imgname");
			BaseProduct baseProduct;
			int length = responseArray.length();
			for (int i = 0; i < length - 1; i++) {
				baseProduct = new BaseProduct();
				String itemsString = responseArray.optString(String.valueOf(i));
				JSONObject itemObject = new JSONObject(itemsString);
				String id = itemObject.optString("goods_id");
				baseProduct.setUId(id);
				String imgName = itemObject.optString("imgname");
				if (!TextUtils.isEmpty(imgName) && !"null".equals(imgName)) {
					String imgUrlTemp = ImageUrl.IMAGEURL
							+ imgName;//+ "!200"
					baseProduct.setImgUrl(imgUrlTemp);
					picList.add(baseProduct);
				}
			}
		} catch (JSONException e) {
			Log.e(TAG, "analysis Pic json err");
			e.printStackTrace();
		}
		return imageUrlString;
	}
	
	/**
	 * 解析商品推荐轮播图片
	 * @param picString
	 * @throws JSONException 
	 */
	private void analysisRecmJson(String picString, ArrayList<MainProduct> picList){
		JSONArray responseArray;
		try {
			responseArray = new JSONArray(picString);
			MainProduct baseProduct;
			JSONObject itemObject;
			int length = responseArray.length();
			for (int i = 0; i < length; i++) {
				baseProduct = new MainProduct();
				itemObject = responseArray.getJSONObject(i);
				String goodsId = itemObject.optString("goods_id");
				baseProduct.setUId(goodsId);
				String imgUrlTemp = itemObject.optString("imgname");
				if (!TextUtils.isEmpty(imgUrlTemp) && !"null".equals(imgUrlTemp)) {
					String imgUrl = ImageUrl.IMAGEURL + imgUrlTemp + "!150";
					baseProduct.setImgUrl(imgUrl);
					baseProduct.setPrice(itemObject.optString("price"));
					baseProduct.setTitle(itemObject.optString("goods_name"));
					picList.add(baseProduct);
				}
			}
		} catch (JSONException e) {
			Log.e(TAG, "analysis RecmJson json err");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 解析所有评论数据
	 * @param content 服务器返回数据
	 * @return 成功与否
	 */
	public boolean parseCommentJson(String content) {
			JSONObject httpResultObject;
			try {
				httpResultObject = new JSONObject(content);
				int code = httpResultObject.optInt("code");
				if(code == 1){
					userComments = new ArrayList<UserComment>();
					String responseString = httpResultObject.optString("response");
					JSONArray responseArray = new JSONArray(responseString);
					int length = responseArray.length();
					JSONObject commentItem ;
					UserComment comments;
					for (int i = 0; i < length; i++) {
						comments = new UserComment();
						commentItem = responseArray.optJSONObject(i);
						String id = commentItem.optString("id");
						comments.setId(id);
						String goodsId = commentItem.optString("goods_id");
						comments.setGoodsId(goodsId);
						String user_id = commentItem.optString("user_id");
						comments.setUserId(user_id);
						String name = commentItem.optString("user_name");
						if(TextUtils.isEmpty(name)) comments.setUserName(user_id);
						else comments.setUserName(unicode.revert(name));
						String title = commentItem.optString("title");
						comments.setTitle(unicode.revert(title));
						String experience = commentItem.optString("experience");
						comments.setUserCommentText(unicode.revert(experience));
						String commentDate = commentItem.optString("commentDate");
						comments.setCommentTime(commentDate);
						String level = commentItem.optString("customer_grade");
						comments.setVipLevel(unicode.revert(level));
//						String maddress = commentItem.getString("address");
//						comments.setAddress(unicode.revert(maddress));
//						String isDefault = commentItem.getString("is_default");
//						boolean isDef = isDefault.equals("y") ? true : false;
//						comments.setDefaultAddress(isDef);
						int rate = 5;
						comments.setRate(rate);
						userComments.add(comments);
					}
					isNoMore = false;
					return true;
				} else if(code == -1){
					isNoMore = true;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return false;
	}
	
	/**
	 * 获取评论列表
	 * @return
	 */
	public ArrayList<UserComment> getComments(){
		return userComments;
	}
	
	/**
	 * 检查是否没更多数据
	 * @return
	 */
	public boolean getIsNoMore(){
		return isNoMore;
	}
	
}
