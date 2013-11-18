package com.yidejia.app.mall.net.search;

import java.io.IOException;





import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.SearchItem;
import com.yidejia.app.mall.net.HttpGetConn;
import com.yidejia.app.mall.net.ImageUrl;
import com.yidejia.app.mall.util.UnicodeToString;
/**
 * ��ȡ��������б�
 * @author long bin
 *
 */
public class SearchDataUtil {
	private String TAG = SearchDataUtil.class.getName();
	
	private UnicodeToString unicode;
	private ArrayList<SearchItem> searchArray;
	
	private boolean isNoMore = false;//判断是否还有更多数据,true为没有更多了
	private boolean isHasResult = false;//判断是否有搜索结果
	
	public SearchDataUtil(){
		unicode = new UnicodeToString();
		searchArray = new ArrayList<SearchItem>();
	}
	
	
	
	private String result = "";
	/**
	 * 
	 * @param name ������Ʒ����ƣ�֧��ģ�����
	 * @param fun ��Ч
	 * @param brand Ʒ��
	 * @param price �۸�
	 * @param order1 ����
	 * @param offset1 ��ʼ��
	 * @param limit1 ������
	 * @return
	 * @throws IOException
	 */
	public String getHttpResponseString(String name, String fun, String brand, String price, String order1, String offset1, String limit1)throws IOException{
//		HttpGetConn conn = new HttpGetConn(getHttpAddress(name, fun, brand, price, order1, offset1, limit1));
		String url = new JNICallBack().getHttp4GetSearch(name, fun, brand, price, order1, offset1, limit1);
		Log.e(TAG, "before conn"+url);
		HttpGetConn conn = new HttpGetConn(url, true);
		result = conn.getJsonResult();
		return result;
	}

	public boolean analysis(String httpresp){
		try {
			JSONObject jsonObject = new JSONObject(httpresp);
			int code = jsonObject.getInt("code");
			if (code == 1) {
				isHasResult = true;
				String responseString = jsonObject.getString("response");
				analysisJson(responseString);
				return true;
			} else if (code == -1) {
				// ����ʧ��
				isNoMore = true;
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "task getlist other ex");
			e.printStackTrace();
			return false;
		}
	}
	
	private void analysisJson(String jsonString) throws JSONException{
		JSONObject dataObject = new JSONObject(jsonString);
		String dataString = dataObject.getString("data");
		JSONArray responseArray = new JSONArray(dataString);
		SearchItem searchItem;
		JSONObject responseObject;
		int length = responseArray.length();
		for (int i = 0; i < length; i++) {
			searchItem = new SearchItem();
			responseObject = responseArray.getJSONObject(i);
			String temp = responseObject.getString("goods_id");
			searchItem.setUId(temp);
			Log.i(TAG, "goods_id:"+temp);
			temp = responseObject.getString("price");
			searchItem.setPrice(temp);
			Log.i(TAG, "price:"+temp);
			temp = responseObject.getString("goods_name");
			searchItem.setName(unicode.revert(temp));
			Log.i(TAG, "goods_name:"+temp);
			temp = responseObject.getString("sells");
			searchItem.setSelledAmount(temp);
			Log.i(TAG, "sells:"+temp);
			temp = responseObject.getString("remarks");
			searchItem.setCommentAmount(temp);
			Log.i(TAG, "remarks:"+temp);
			temp = ImageUrl.IMAGEURL + responseObject.getString("imgname") + "!100";
			searchItem.setImgUrl(temp);
			Log.i(TAG, "imageUrl:"+temp);
			
			searchItem.setTheCode(responseObject.getString("the_code"));
			Log.i(TAG, "thecode:"+responseObject.getString("the_code"));
			searchItem.setModule(unicode.revert(responseObject.getString("module")));
			Log.i(TAG, "module:"+unicode.revert(responseObject.getString("module")));
			searchItem.setBrand(responseObject.getString("brand"));
			Log.i(TAG, "brand:"+responseObject.getString("brand"));
			searchItem.setSpec(responseObject.getString("spec"));
			Log.i(TAG, "spec:"+responseObject.getString("spec"));
			searchItem.setDesc(unicode.revert(responseObject.getString("desc")));
			Log.i(TAG, "desc:"+responseObject.getString("desc"));
			searchArray.add(searchItem);
		}
	}
	
	public ArrayList<SearchItem> getSearchResults(){
		return searchArray;
	}
	
	public boolean getIsNomore(){
		return isNoMore;
	}
	
	public boolean getIsHasRst(){
		return isHasResult;
	}
}
