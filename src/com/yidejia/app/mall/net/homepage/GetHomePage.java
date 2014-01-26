package com.yidejia.app.mall.net.homepage;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.BaseProduct;
import com.yidejia.app.mall.model.MainProduct;
import com.yidejia.app.mall.net.HttpGetConn;
import com.yidejia.app.mall.net.ImageUrl;
import com.yidejia.app.mall.util.UnicodeToString;
/**
 * ��ȡ��ҳ
 * @author long bin
 *
 */
public class GetHomePage {
	
	private ArrayList<BaseProduct> bannerArray; //轮播商品 （个数不固定，<=6）
	private ArrayList<MainProduct> hotSellArray; //热卖商品 （个数暂定3个）
	private ArrayList<MainProduct> acymerArray;    //美容护肤  （个数暂定3个）
	private ArrayList<MainProduct> inerbtyArray;    //内调养护  （个数暂定6个）
	private ArrayList<String> ggTitle;//公告
	private String TAG = GetHomePage.class.getName();
	private UnicodeToString unicode;
	
	public GetHomePage(){
		bannerArray = new ArrayList<BaseProduct>();
		hotSellArray = new ArrayList<MainProduct>();
		acymerArray = new ArrayList<MainProduct>();
		inerbtyArray = new ArrayList<MainProduct>();
		ggTitle = new ArrayList<String>();
		unicode = new UnicodeToString();
	}
	
	private String result = "";
	public String getHomePageJsonString()throws IOException, TimeOutEx{
//		HttpGetConn conn = new HttpGetConn(getHttpAddress());
		String url = new JNICallBack().getHttp4GetHome();
		Log.e("system.out", url);
		HttpGetConn conn = new HttpGetConn(url, true);
		result = conn.getJsonResult();
		return result;
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
	 * @return 热卖商品hotSellArray:包含MainProduct对象的ArrayList类型
	 */
	public ArrayList<MainProduct> getHotSellArray(){
		
		return hotSellArray;
	}
	/**
	 * 
	 * @return acymerArray 美容护肤商品列表:包含MainProduct对象的ArrayList类型
	 */
	public ArrayList<MainProduct> getAcymerArray(){
		return acymerArray;
	}
	/**
	 * 
	 * @return inerbtyArray 内调养护商品列表:包含MainProduct对象的ArrayList类型
	 */
	public ArrayList<MainProduct> getInerbtyArray(){
		return inerbtyArray;
	}
	/**
	 * 
	 * @return 商城公告
	 */
	public ArrayList<String> getGGTitle(){
		return ggTitle;
	}
	
	/**
	 * 解析首页所有数据
	 * @param httpResultString
	 * @return
	 */
	public boolean analysisGetHomeJson(String httpResultString){
		JSONObject httpResultObject;
		if(null == httpResultString || "".equals(httpResultString)) return false;
		try {
			httpResultObject = new JSONObject(httpResultString);
			int code = httpResultObject.optInt("code");
			if(code == 1){
				String responseString = httpResultObject.optString("response");
				JSONObject itemObject = new JSONObject(responseString);
				String bannerString = itemObject.optString("lb");	//轮播
				String hotsellString = itemObject.optString("rm");	//热卖
				String acymerString = itemObject.optString("mrhf");	//妍诗美
				String inerbtyString = itemObject.optString("ntyh");	//妍膳美
				String ggString = itemObject.optString("gg");	//公告
				
				String djdzmString = itemObject.optString("djdzm");	//大家都在买
				String sjk = itemObject.optString("sjk");	//随心逛
				
//				Log.e(TAG, ggString);
				analysisGGJson(ggString);
				analysisBannerJson(bannerString);
				analysisJson(hotsellString, 0);
				analysisJson(acymerString, 1);
				analysisJson(inerbtyString, 2);
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
	
	private void analysisJson(String jsonString, int index){
		JSONArray hotsellJsonArray = null;
		try {
			hotsellJsonArray = new JSONArray(jsonString);
			int length = hotsellJsonArray.length();
			JSONObject itemObject = null;
			MainProduct baseProduct;
			for (int i = 0; i < length; i++) {
				baseProduct = new MainProduct();
				try {
					itemObject = hotsellJsonArray.getJSONObject(i);
					String goodsId;
					try {
						goodsId = itemObject.optString("goods_id");
						baseProduct.setUId(goodsId);
					} catch (Exception e) {
						e.printStackTrace();
					}
					String imgUrl = "";
					if(i==0 && index != 2)
						try {
							imgUrl = ImageUrl.IMAGEURL + itemObject.optString("img_name");
						} catch (Exception e) {
							e.printStackTrace();
						}
					else
						try {
							imgUrl = ImageUrl.IMAGEURL + itemObject.optString("img_name");
						} catch (Exception e) {
							e.printStackTrace();
						}
					baseProduct.setImgUrl(imgUrl);
					String title = "";
					try {
						title = itemObject.optString("name");
					} catch (Exception e) {
						e.printStackTrace();
					}
					baseProduct.setTitle(unicode.revert(title));
					Log.i(TAG, title);
					String price = "";
					try {
						price = itemObject.optString("price");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					baseProduct.setPrice(price);
//			mainArray.add(baseProduct);
					switch (index) {
					case 0:
						hotSellArray.add(i, baseProduct);
						Log.i(TAG, hotSellArray.get(i).getTitle());
						break;
					case 1:
						acymerArray.add(i, baseProduct);
						break;
					case 2:
						inerbtyArray.add(i, baseProduct);
						break;
					default:
						break;
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			if(index == 0){
				for (int i = 0; i < hotSellArray.size(); i++) {
					Log.i(TAG, hotSellArray.get(i).getTitle());
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void analysisGGJson(String ggString){
		try {
			JSONArray ggJsonArray = new JSONArray(ggString);
			int length = ggJsonArray.length();
			
			JSONObject jsonObject;
			for (int i = 0; i < length; i++) {
				try {
					jsonObject = ggJsonArray.getJSONObject(i);
					try {
						ggTitle.add(jsonObject.optString("title"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}
