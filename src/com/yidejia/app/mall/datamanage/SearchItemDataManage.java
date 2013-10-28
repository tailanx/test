package com.yidejia.app.mall.datamanage;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.yidejia.app.mall.model.SearchItem;
import com.yidejia.app.mall.net.ImageUrl;
import com.yidejia.app.mall.net.search.GetSearchList;
import com.yidejia.app.mall.util.UnicodeToString;

/**
 * 获取搜索商品数据
 * @author long bin
 *
 */
public class SearchItemDataManage {
	private ArrayList<SearchItem> searchArray;
	private Context context ;
	private String TAG = SearchItemDataManage.class.getName();
	private UnicodeToString unicode;
	
	public SearchItemDataManage(Context context){
		this.context = context;
		unicode = new UnicodeToString();
		searchArray = new ArrayList<SearchItem>();
	}
	
	/**
	 * 
	 * @param order 
	 * @param name
	 * @param brand
	 * @param fun
	 * @param price
	 * @return
	 */
	public ArrayList<SearchItem> getSearchArray(String order, String name, String brand, String fun, String price){
		return searchArray;
	}
	
	private class TaskSearch extends AsyncTask<Void, Void, Boolean>{
		
		private String where;
		private String offset;
		private String limit;
		private String group;
		private String order;
		private String fields;
		
		public TaskSearch(String where, String offset, String limit, String group, String order, String fields){
			this.where = where;
			this.offset = offset;
			this.limit = limit;
			this.group = group;
			this.order = order;
			this.fields = fields;
		}
		
		private ProgressDialog bar = new ProgressDialog(context);
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			bar.setMessage("正在查询");
			bar.show();
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			GetSearchList getList = new GetSearchList(context);
			try {
				String httpResponseString = getList.getSearchListJsonString(where, offset, limit, group, order, fields);
				JSONObject jsonObject = new JSONObject(httpResponseString);
				int code = jsonObject.getInt("code");
				if(code == 1){
					String responseString = jsonObject.getString("response");
					analysisJson(responseString);
					return true;
				} else if (code == -1){
					//搜索失败
				} else {
					return false;
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "task getlist other ex");
				e.printStackTrace();
				return false;
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "task getlist other ex");
				e.printStackTrace();
				return false;
			}
			return false;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			bar.dismiss();
		}
	}
	
	
	/**
	 * 解析搜索结果
	 * @param jsonString
	 * @throws JSONException
	 */
	private void analysisJson(String jsonString) throws JSONException{
		JSONArray responseArray = new JSONArray(jsonString);
		SearchItem searchItem = new SearchItem();
		JSONObject responseObject;
		int length = responseArray.length();
		for (int i = 0; i < length; i++) {
			responseObject = responseArray.getJSONObject(i);
			String temp = responseObject.getString("goods_id");
			searchItem.setUId(temp);
			temp = responseObject.getString("price");
			searchItem.setPrice(temp);
			temp = responseObject.getString("goods_name");
			searchItem.setName(unicode.revert(temp));
			temp = responseObject.getString("sells");
			searchItem.setSelledAmount(temp);
			temp = responseObject.getString("remarks");
			searchItem.setCommentAmount(temp);
			temp = ImageUrl.IMAGEURL + responseObject.getString("imgname");
			searchItem.setImgUrl(temp);
			searchArray.add(searchItem);
		}
	}
}