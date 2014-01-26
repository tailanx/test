package com.yidejia.app.mall.net.homepage;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

import com.yidejia.app.mall.model.BaseProduct;
import com.yidejia.app.mall.model.MainProduct;
import com.yidejia.app.mall.net.ImageUrl;
import com.yidejia.app.mall.util.UnicodeToString;
/**
 * 首页
 * @version 2.0 2014/01/24
 * @author long bin
 *
 */
public class GetHomePage {
	
	private ArrayList<BaseProduct> bannerArray; //轮播商品 （个数不固定，<=6）
	
	private ArrayList<MainProduct> sxgProducts;	//	随心逛商品
	private ArrayList<MainProduct> djdzmProducts;	//大家都在买商品
	
	private String TAG = GetHomePage.class.getName();
	private UnicodeToString unicode;
	
	public GetHomePage(){
		bannerArray = new ArrayList<BaseProduct>();
		unicode = new UnicodeToString();
	}
	
	/**
	 * 
	 * @return 轮播商品bannerArray ：包含BaseProduct对象的ArrayList类型
	 */
	public ArrayList<BaseProduct> getBannerArray(){
		return bannerArray;
	}
	/**
	 * 
	 * @return 大家都在买djdzm:包含MainProduct对象的ArrayList类型
	 */
	public ArrayList<MainProduct> getDjdzmArray(){
		return djdzmProducts;
	}
	/**
	 * 
	 * @return 随心逛 sxg商品列表:包含MainProduct对象的ArrayList类型
	 */
	public ArrayList<MainProduct> getSxgArray(){
		return sxgProducts;
	}
	
	/**
	 * 解析首页所有数据
	 * @param httpResultString
	 * @return
	 */
	public boolean parseGetHomeJson(String httpResultString){
		JSONObject httpResultObject;
		if(TextUtils.isEmpty(httpResultString)) return false;
		try {
			httpResultObject = new JSONObject(httpResultString);
			int code = httpResultObject.optInt("code");
			if(code == 1){
				String responseString = httpResultObject.optString("response");
				JSONObject itemObject = new JSONObject(responseString);
				String bannerString = itemObject.optString("lb");	//轮播
//				String hotsellString = itemObject.optString("rm");	//热卖
//				String acymerString = itemObject.optString("mrhf");	//妍诗美
//				String inerbtyString = itemObject.optString("ntyh");	//妍膳美
//				String ggString = itemObject.optString("gg");	//公告
				
				String djdzmString = itemObject.optString("djdzm");	//大家都在买
				String sxg = itemObject.optString("sxg");	//随心逛
				
//				Log.e(TAG, ggString);
//				analysisGGJson(ggString);
				analysisBannerJson(bannerString);
//				analysisJson(hotsellString, 0);
//				analysisJson(acymerString, 1);
//				analysisJson(inerbtyString, 2);
				
				parseSxg(sxg, 0);
				parseSxg(djdzmString, 1);
				return true;
			}
		} catch (JSONException e) {
			Log.e(TAG, "get home page json error");
			e.printStackTrace();
		} catch (Exception e) {
			Log.e(TAG, "get home page json other error");
			e.printStackTrace();
		}
		return false;
	}
	
	/**解析（0）随心逛或（1）大家都在买数据**/
	private void parseSxg(String content, int type){
		
		try {
			JSONArray sxgArray = new JSONArray(content);
			int length = sxgArray.length();
			
			MainProduct product = null;
			if(0 == type)
				sxgProducts = new ArrayList<MainProduct>();
			else if(1 == type)
				djdzmProducts = new ArrayList<MainProduct>();
			
			for(int i = 0; i < length; i++){
				JSONObject itemJsonObject = sxgArray.optJSONObject(i);
				String goodsId = itemJsonObject.optString("goods_id");
				String goodsUrl = itemJsonObject.optString("img_name");
				String goodsName = itemJsonObject.optString("goods_name");
				String price = itemJsonObject.optString("price");
				
				product = new MainProduct();
				product.setPrice(price);
				product.setUId(goodsId);
				product.setTitle(unicode.revert(goodsName));
				product.setImgUrl(ImageUrl.IMAGEURL + goodsUrl);
				
				if(type == 0)
					sxgProducts.add(product);
				else if(type == 1)
					djdzmProducts.add(product);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void analysisBannerJson(String bannerJsonString) throws JSONException{
		JSONArray bannerJsonArray = new JSONArray(bannerJsonString);
		int length = bannerJsonArray.length();
		JSONObject itemObject;
		BaseProduct baseProduct ;
		for (int i = 0; i < length; i++) {
			baseProduct = new BaseProduct();
			itemObject = bannerJsonArray.getJSONObject(i);
			String goodsId = itemObject.optString("goods_id");
			baseProduct.setUId(goodsId);
			String imgUrl = itemObject.optString("img_name");
			Log.i(TAG, imgUrl);
			baseProduct.setImgUrl(ImageUrl.IMAGEURL+imgUrl);
			bannerArray.add(baseProduct);
		}
	}
	
	
	
	
}
