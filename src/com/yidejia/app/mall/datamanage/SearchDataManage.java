package com.yidejia.app.mall.datamanage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



//import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.exception.NullSearchResultEx;
import com.yidejia.app.mall.model.SearchItem;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.ImageUrl;
import com.yidejia.app.mall.net.search.SearchDataUtil;
import com.yidejia.app.mall.util.UnicodeToString;

/**
 * 获取搜索商品数据
 * @author long bin
 *
 */
public class SearchDataManage {
	private ArrayList<SearchItem> searchArray;
	private Context context ;
	private String TAG = SearchDataManage.class.getName();
	private UnicodeToString unicode;
	
	private boolean isNoMore = false;//判断是否还有更多数据,true为没有更多了
	private boolean isHasResult = false;//判断是否有搜索结果
	
	public SearchDataManage(Context context){
		this.context = context;
		unicode = new UnicodeToString();
		searchArray = new ArrayList<SearchItem>();
	}
	
	/**
	 * 搜索商品
	 * @param name 名称，支持模糊查找
	 * @param fun 功效
	 * @param brand 品牌
	 * @param price 价格区间
	 * @param order 排序
	 * @param offset 开始项
	 * @param limit 结束项
	 * @return 搜索结构
	 * @throws NullSearchResultEx
	 */
	public ArrayList<SearchItem> getSearchArray(String name, String fun,
			String brand, String price, String order, String offset,
			String limit) throws NullSearchResultEx{
		if (!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return searchArray;
		}
		TaskSearch taskSearch = new TaskSearch(name, fun, brand, price, order, offset, limit);
		boolean state = false;
		try {
			Log.e(TAG, "before task");
			state = taskSearch.execute().get();
			Log.e(TAG, "after task");
			if(isHasResult && isNoMore){
				Toast.makeText(context, context.getResources().getString(R.string.nomore), Toast.LENGTH_SHORT).show();
				isNoMore = false;
				state = true;
			} else if(!isHasResult){
				throw new NullSearchResultEx(context.getResources().getString(R.string.no_search_result));
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "task search InterruptedException ");
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "task search ExecutionException ");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "task search Exception ");
			e.printStackTrace();
		} 
		if(!state){
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		} 
		return searchArray;
	}
	
	private class TaskSearch extends AsyncTask<Void, Void, Boolean>{
		
		private String name;
		private String offset;
		private String limit;
		private String brand;
		private String order;
		private String fun;
		private String price;
		
		public TaskSearch(String name, String fun, String brand, String price,String order, String offset, String limit){
			this.name = name;
			this.offset = offset;
			this.limit = limit;
			this.brand = brand;
			this.order = order;
			this.price = price;
			this.fun = fun;
		}
		
//		private ProgressDialog bar = new ProgressDialog(context);
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
////			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
////			bar.setMessage("���ڲ�ѯ");
////			bar.show();
//		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Log.e(TAG, "doing task");
			SearchDataUtil searchDataUtil = new SearchDataUtil();//context
			try {
				Log.e(TAG, "before http");
				String httpResponseString = searchDataUtil.getHttpResponseString(name, fun, brand, price, order, offset, limit);
				Log.e(TAG, httpResponseString);
				JSONObject jsonObject = new JSONObject(httpResponseString);
				int code = jsonObject.getInt("code");
				if(code == 1){
					isHasResult = true;
					String responseString = jsonObject.getString("response");
					analysisJson(responseString);
					return true;
				} else if (code == -1){
					//����ʧ��
					isNoMore = true;
					return true;
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
		}
		
//		@Override
//		protected void onPostExecute(Boolean result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
////			bar.dismiss();
//		}
	}
	
	
	/**
	 * �����������
	 * @param jsonString
	 * @throws JSONException
	 */
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
			temp = ImageUrl.IMAGEURL + responseObject.getString("imgname");
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
}
