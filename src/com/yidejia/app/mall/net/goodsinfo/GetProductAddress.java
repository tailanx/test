package com.yidejia.app.mall.net.goodsinfo;

import java.io.IOException;




import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.BaseProduct;
import com.yidejia.app.mall.model.MainProduct;
import com.yidejia.app.mall.model.ProductBaseInfo;
import com.yidejia.app.mall.net.HttpGetConn;
import com.yidejia.app.mall.net.ImageUrl;
import com.yidejia.app.mall.util.UnicodeToString;
/**
 * ��ȡ��Ʒ��Ϣ
 * @author long bin
 *
 */
public class GetProductAddress {
	
	private String TAG = GetProductAddress.class.getName();
	
	private UnicodeToString unicode;
	
	private ProductBaseInfo productBaseInfo;
	private ArrayList<BaseProduct> bannerArray;			//轮播商品
	private ArrayList<MainProduct> recommendArray;    ///推荐搭配 
	
	public GetProductAddress(){
		unicode = new UnicodeToString();
		productBaseInfo = new ProductBaseInfo();
		bannerArray = new ArrayList<BaseProduct>();
		recommendArray = new ArrayList<MainProduct>();
	}
	
	private String result = "";
	public String getProductJsonString(String id)throws IOException{
//		HttpGetConn conn = new HttpGetConn(getHttpAddress(id));
		HttpGetConn conn = new HttpGetConn(new JNICallBack().getHttp4GetGoods(id), true);
		result = conn.getJsonResult();
		return result;
	}

	/**
	 * 解析http返回数据
	 * @param httpresp
	 * @return
	 */
	public boolean analysis(String httpresp){
		boolean issuccess = false;
		try {
			JSONObject jsonObject = new JSONObject(httpresp);
			int code = jsonObject.getInt("code");
			String resp = jsonObject.getString("response");
			if(code == 1){
				analysisProductJson(resp);
				issuccess = true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return issuccess;
	}
	
	/**
	 * 解析商品的json数据
	 * @param jsonString
	 * @throws JSONException
	 */
	private boolean analysisProductJson(String jsonString){
		try {
			JSONObject responseObject = new JSONObject(jsonString);
			String uid = responseObject.getString("goods_id");
			productBaseInfo.setUId(uid);
			String goodName = responseObject.getString("goods_name");
			productBaseInfo.setName(unicode.revert(goodName));
			String price = responseObject.getString("price");
			productBaseInfo.setPrice(price);
			String sells = responseObject.getString("sells");
			productBaseInfo.setSalledAmount(sells);
			String remarks = responseObject.getString("remarks");
			productBaseInfo.setCommentAmount(remarks);
			String brands = responseObject.getString("brand");
			productBaseInfo.setBrands(brands);
			String theCode = responseObject.getString("the_code");
			productBaseInfo.setProductNumber(theCode);
			String sepc = responseObject.getString("sepc");
			productBaseInfo.setProductSpecifications(sepc);
			String info = responseObject.getString("info");
			productBaseInfo.setProductDetailUrl(info);
			String shaidan = responseObject.getString("shaidan");
			productBaseInfo.setShowListAmount(shaidan);
			String pics = responseObject.getString("pics");
			analysisPicJson(pics, bannerArray);
			productBaseInfo.setImgUrl(ImageUrl.IMAGEURL+imageUrlString + "!100");
			productBaseInfo.setBannerArray(bannerArray);
			String collects = responseObject.getString("collects");
			analysisRecmJson(collects, recommendArray);
			productBaseInfo.setRecommendArray(recommendArray);
			return true;
		} catch (JSONException e) {
			// TODO: handle exception
		}
		return false;
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
				String goodsId = itemObject.getString("goods_id");
				baseProduct.setUId(goodsId);
				String imgUrlTemp = itemObject.getString("imgname");
				String imgUrl = ImageUrl.IMAGEURL + imgUrlTemp + "!150";
				baseProduct.setImgUrl(imgUrl);
				baseProduct.setPrice(itemObject.getString("price"));
				baseProduct.setTitle(itemObject.getString("goods_name"));
				picList.add(baseProduct);
				Log.i(TAG, picList.getClass().getName());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "analysis RecmJson json err");
			e.printStackTrace();
		}
	}
	
	private String imageUrlString = "";
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
			imageUrlString = responseArray.getString("imgname");
			Log.i(TAG, imageUrlString);
			BaseProduct baseProduct;
			int length = responseArray.length();
			for (int i = 0; i < length - 1; i++) {
				baseProduct = new BaseProduct();
				String itemsString = responseArray.getString(String.valueOf(i));
				JSONObject itemObject = new JSONObject(itemsString);
				String id = itemObject.getString("goods_id");
				baseProduct.setUId(id);
				String imgUrlTemp = ImageUrl.IMAGEURL + itemObject.getString("imgname") + "!200";
				baseProduct.setImgUrl(imgUrlTemp);
				picList.add(baseProduct);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "analysis Pic json err");
			e.printStackTrace();
		}
		return imageUrlString;
	}
	
	public ArrayList<BaseProduct> getBannerProducts(){
		return bannerArray;
	}
	
	public ArrayList<MainProduct> getRecommentProducts(){
		return recommendArray;
	}
	
	public ProductBaseInfo getProductBaseInfo(){
		return productBaseInfo;
	}
}
