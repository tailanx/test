package com.yidejia.app.mall.favorite;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

import com.yidejia.app.mall.model.SearchItem;
import com.yidejia.app.mall.net.ImageUrl;
import com.yidejia.app.mall.util.UnicodeToString;

public class ParseFavJson {
	
	private String TAG = getClass().getName();
	private UnicodeToString unicode;
	private ArrayList<SearchItem> favoriteArray;
	
	public ParseFavJson(){
		unicode = new UnicodeToString();
	}
	
	/**
	 * 解析所有收藏数据
	 * @param httpResultString
	 * @return
	 */
	public boolean parseGetListJson(String httpResultString){
		JSONObject httpResultObject;
		try {
			httpResultObject = new JSONObject(httpResultString);
			int code = httpResultObject.optInt("code");
			Log.i(TAG, "code"+code);
			if(code == 1){
				favoriteArray = new ArrayList<SearchItem>();
				String responseString = httpResultObject.optString("response");
				JSONArray responseArray = new JSONArray(responseString);
				int length = responseArray.length();
				JSONObject infoItem ;
				SearchItem searchItem;
				for (int i = 0; i < length; i++) {
					searchItem = new SearchItem();
					infoItem = responseArray.optJSONObject(i);
					String favoriteId = infoItem.optString("id");
					searchItem.setFavoriteId(favoriteId);
					String name = infoItem.optString("goods_name");
					searchItem.setName(unicode.revert(name));
					String goodsId = infoItem.optString("goods_id");
					searchItem.setUId(goodsId);
					String priceString = infoItem.optString("price");
					searchItem.setPrice(priceString);
					String brief = infoItem.optString("desc");
					searchItem.setBrief(unicode.revert(brief));
					String sells = infoItem.optString("sells");
					searchItem.setSelledAmount(sells);
					String remarks = infoItem.optString("remarks");
					searchItem.setCommentAmount(remarks);
					String imageName = infoItem.optString("imgname");
					String imageUrl = ImageUrl.IMAGEURL + imageName + "!100";
					searchItem.setImgUrl(imageUrl);
					
					favoriteArray.add(searchItem);
				}
				return true;
			} else if(code == -1){
				return true;
			}
		} catch (JSONException e) {
			Log.e(TAG, "get favorite list json error");
			e.printStackTrace();
		} catch (Exception e) {
			Log.e(TAG, "get favorite list json other error");
			e.printStackTrace();
		}
		return false;
	}
	
	/**获取收藏列表**/
	public ArrayList<SearchItem> getFavList(){
		return favoriteArray;
	}
	
	/**
	 * 解析删除收藏数据
	 * @param resultJson http返回的数据
	 * @return
	 */
	public boolean parseDeleteJson(String resultJson) {
		if (TextUtils.isEmpty(resultJson))
			return false;

		JSONObject httpResultObject;
		try {
			httpResultObject = new JSONObject(resultJson);
			int code = httpResultObject.optInt("code");
			if (code == 1){
				return true;
			}
			else {
				return false;
			}
		} catch (JSONException e) {
			Log.e(TAG, "delete favorite analysis json jsonexception error");
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			Log.e(TAG, "delete favorite analysis json exception error");
			e.printStackTrace();
			return false;
		}
	}

}
