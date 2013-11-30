package com.yidejia.app.mall.net.favorite;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.SearchItem;
import com.yidejia.app.mall.net.HttpAddressParam;
import com.yidejia.app.mall.net.HttpGetConn;
import com.yidejia.app.mall.net.ImageUrl;
import com.yidejia.app.mall.util.Md5;
import com.yidejia.app.mall.util.UnicodeToString;
/**
 * ��ȡ�ղ��б�
 * @author long bin
 *
 */
public class GetFavoriteList {
	
	private String TAG = GetFavoriteList.class.getName();
	private UnicodeToString unicode;
	private boolean isNoMore = false;//判断是否还有更多数据,true为没有更多了
	private ArrayList<SearchItem> favoriteArray;
	
	public GetFavoriteList(){
		unicode = new UnicodeToString();
		favoriteArray = new ArrayList<SearchItem>();
	}
	
	
	
	private String result = "";

	public String getFavoritesListJsonString(String where, String offset,
			String limit, String group, String order, String fields)
			throws IOException, TimeOutEx {
		// HttpGetConn conn = new HttpGetConn(getHttpAddress(where, offset,
		// limit, group, order, fields));
		HttpGetConn conn = new HttpGetConn(new JNICallBack().getHttp4GetFav(where,
				offset, limit, group, order, fields), true);
		result = conn.getJsonResult();
		return result;
	}
	
	public String getHttpResp(String userid, String offset,
			String limit)
					throws IOException, TimeOutEx {
		// HttpGetConn conn = new HttpGetConn(getHttpAddress(where, offset,
		// limit, group, order, fields));
		
		return getFavoritesListJsonString("userid="+userid, offset, limit, "", "created+desc", "%2A");
	}
	
	/**
	 * 解析所有收藏数据
	 * @param httpResultString
	 * @return
	 */
	public boolean analysisGetListJson(String httpResultString){
		JSONObject httpResultObject;
		favoriteArray.clear();
		isNoMore = false;
		try {
			httpResultObject = new JSONObject(httpResultString);
			int code = httpResultObject.getInt("code");
			Log.i(TAG, "code"+code);
			if(code == 1){
				String responseString = httpResultObject.getString("response");
				JSONArray responseArray = new JSONArray(responseString);
				int length = responseArray.length();
				JSONObject infoItem ;
				SearchItem searchItem;
				for (int i = 0; i < length; i++) {
					searchItem = new SearchItem();
					infoItem = responseArray.getJSONObject(i);
					String favoriteId = infoItem.getString("id");
					searchItem.setFavoriteId(favoriteId);
					String name = infoItem.getString("goods_name");
					searchItem.setName(unicode.revert(name));
					String goodsId = infoItem.getString("goods_id");
					searchItem.setUId(goodsId);
					String priceString = infoItem.getString("price");
//					float price = Float.parseFloat(priceString);
					searchItem.setPrice(priceString);
					String brief = infoItem.getString("desc");
					searchItem.setBrief(unicode.revert(brief));
					String sells = infoItem.getString("sells");
					searchItem.setSelledAmount(sells);
					String remarks = infoItem.getString("remarks");
					searchItem.setCommentAmount(remarks);
					String imageName = infoItem.getString("imgname");
//					ImageUrl imageUrlClass = new ImageUrl();
					String imageUrl = ImageUrl.IMAGEURL + imageName + "!100";//imageUrlClass.getImageUrl();
					searchItem.setImgUrl(imageUrl);
					
//					String maddress = infoItem.getString("address");
//					searchItem.setAddress(unicode.revert(maddress));
//					String isDefault = infoItem.getString("is_default");
//					boolean isDef = isDefault.equals("y") ? true : false;
//					searchItem.setDefaultAddress(isDef);
					favoriteArray.add(searchItem);
//					Log.e(TAG, "???");
				}
				return true;
			} else if(code == -1){
				isNoMore = true;
				return true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "get favorite list json error");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "get favorite list json other error");
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<SearchItem> getFavList(){
		return favoriteArray;
	}
	
	public boolean getIsNoMore(){
		return isNoMore;
	}
	
}
