package com.yidejia.app.mall.search;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.yidejia.app.mall.model.Brand;
import com.yidejia.app.mall.model.Function;
import com.yidejia.app.mall.model.PriceLevel;
import com.yidejia.app.mall.model.SearchItem;
import com.yidejia.app.mall.net.ImageUrl;
import com.yidejia.app.mall.util.UnicodeToString;

/**
 * 解析搜索的json数据
 * 
 * @author LongBin
 * 
 */
public class ParseSearchJson {

	private UnicodeToString unicode;
	private ArrayList<Brand> brandsArray; // 品牌
	private ArrayList<Function> functionsArray; // 功效
	private ArrayList<PriceLevel> pricesArray; // 价格区间
	private ArrayList<SearchItem> searchArray; // 搜索结果
	private String TAG = ParseSearchJson.class.getName();

	public ParseSearchJson() {
		unicode = new UnicodeToString();
	}

	/**
	 * 解析品牌数据
	 * 
	 * @param httpresp
	 * @return 成功与否
	 */
	public boolean parseBrandJson(String httpresp) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(httpresp);
			int code = jsonObject.optInt("code");
			if (code == 1) {
				String response = jsonObject.optString("response");
				brandJson(response);
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 返回品牌信息
	 * 
	 * @return
	 */
	public ArrayList<Brand> getBrands() {
		return brandsArray;
	}

	/**
	 * 解析具体品牌数据
	 * 
	 * @param response
	 * @throws JSONException
	 */
	private void brandJson(String response) throws JSONException {
		brandsArray = new ArrayList<Brand>();
		JSONArray responseArray = new JSONArray(response);
		int length = responseArray.length();
		Brand brand;
		JSONArray itemArray;
		for (int i = 0; i < length; i++) {
			brand = new Brand();
			itemArray = responseArray.getJSONArray(i);
			int count = itemArray.length();
			for (int j = 0; j < count; j++) {
				if (j == 0)
					brand.setBrandName(itemArray.optString(j));
				if (j < count - 1)
					brand.setDesc(unicode.revert(itemArray.optString(j + 1)));
			}
			brandsArray.add(brand);
		}
	}

	/**
	 * 解析服务器返回的功效数据
	 * 
	 * @param httpresp
	 * @return 返回成功与否
	 */
	public boolean parseFunJson(String httpresp) {
		boolean issuccess = false;
		if (!"".equals(httpresp)) {
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(httpresp);
				int code = jsonObject.optInt("code");
				if (code == 1) {
					String response = jsonObject.optString("response");
					funJson(response);
					return true;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return issuccess;
	}

	/**
	 * 解析具体功效数据
	 * 
	 * @param response
	 */
	private void funJson(String response) {
		functionsArray = new ArrayList<Function>();
		JSONArray responseArray;
		try {
			responseArray = new JSONArray(response);
			int length = responseArray.length();
			JSONObject jsonObject;
			Function function;

			for (int i = 0; i < length; i++) {
				function = new Function();
				jsonObject = responseArray.getJSONObject(i);
				function.setFunId(jsonObject.optString("id"));
				function.setFunName(unicode.revert(jsonObject.optString("name")));
				function.setDesc(unicode.revert(jsonObject.optString("desc")));
				function.setImgUrl(ImageUrl.IMAGEURL + jsonObject.optString("imgname"));
				functionsArray.add(function);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取功效的列表
	 * 
	 * @return
	 */
	public ArrayList<Function> getFunctions() {
		return functionsArray;
	}

	/**
	 * 解析服务器返回价格区间数据
	 * 
	 * @param httpresp
	 * @return
	 */
	public boolean parsePriceJson(String httpresp) {
		if (!"".equals(httpresp)) {
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(httpresp);
				int code = jsonObject.optInt("code");
				if (code == 1) {
					String response = jsonObject.optString("response");
					priceJson(response);
					return true;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 解析具体价格区间数据
	 * 
	 * @param response
	 * @throws JSONException
	 */
	private void priceJson(String response) throws JSONException {
		pricesArray = new ArrayList<PriceLevel>();
		JSONObject responseArray = new JSONObject(response);
		int length = responseArray.length();
		PriceLevel price;
		JSONObject itemArray;
		for (int i = 0; i < length; i++) {
			price = new PriceLevel();
			String itemString = responseArray.optString("" + (i + 1));
			itemArray = new JSONObject(itemString);
			price.setPriceId(itemArray.optString("id"));
			price.setMinPrice(itemArray.optString("min"));
			price.setMaxPrice(itemArray.optString("max"));
			pricesArray.add(price);
		}
	}

	/**
	 * 返回价格区间
	 * 
	 * @return
	 */
	public ArrayList<PriceLevel> getPriceLevels() {
		return pricesArray;
	}

	/**
	 * 解析服务器返回的搜索结果数据
	 * 
	 * @param httpresp
	 * @return
	 */
	public boolean parseSearchJson(String httpresp) {
		try {
			JSONObject jsonObject = new JSONObject(httpresp);
			int code = jsonObject.optInt("code");
			if (code == 1) {
				String responseString = jsonObject.optString("response");
				searchJson(responseString);
				return true;
			} else if (code == -1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			Log.e(TAG, "task getlist other ex");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 解析具体搜索结果数据
	 * 
	 * @param jsonString
	 * @throws JSONException
	 */
	private void searchJson(String jsonString) throws JSONException {
		searchArray = new ArrayList<SearchItem>();
		JSONObject dataObject = new JSONObject(jsonString);
		String dataString = dataObject.optString("data");
		JSONArray responseArray = new JSONArray(dataString);
		SearchItem searchItem;
		JSONObject responseObject;
		int length = responseArray.length();
		for (int i = 0; i < length; i++) {
			searchItem = new SearchItem();
			responseObject = responseArray.getJSONObject(i);
			String temp = responseObject.optString("goods_id");
			searchItem.setUId(temp);
			temp = responseObject.optString("price");
			searchItem.setPrice(temp);
			temp = responseObject.optString("goods_name");
			searchItem.setName(unicode.revert(temp));
			temp = responseObject.optString("sells");
			searchItem.setSelledAmount(temp);
			temp = responseObject.optString("remarks");
			searchItem.setCommentAmount(temp);
			temp = ImageUrl.IMAGEURL + responseObject.optString("imgname")
					;
			searchItem.setImgUrl(temp);

			searchItem.setTheCode(responseObject.optString("the_code"));
			searchItem.setModule(unicode.revert(responseObject
					.optString("module")));
			searchItem.setBrand(responseObject.optString("brand"));
			searchItem.setSpec(responseObject.optString("spec"));
			searchItem
					.setDesc(unicode.revert(responseObject.optString("desc")));
			searchArray.add(searchItem);
		}
	}

	/**
	 * 获取搜索结果数据
	 * 
	 * @return
	 */
	public ArrayList<SearchItem> getSearchResults() {
		return searchArray;
	}
}
