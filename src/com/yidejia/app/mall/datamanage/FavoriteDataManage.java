package com.yidejia.app.mall.datamanage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.SearchItem;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.ImageUrl;
import com.yidejia.app.mall.net.favorite.CheckExistsFavorite;
import com.yidejia.app.mall.net.favorite.DeleteFavorite;
import com.yidejia.app.mall.net.favorite.GetFavoriteList;
import com.yidejia.app.mall.net.favorite.SaveFavorite;
import com.yidejia.app.mall.util.UnicodeToString;

/**
 * 获取收藏列表  
 * <p>
 * 方法（函数）有{@link #getFavouriteArray()} , {@link #addFavourite(int userId, int productId)} ,{@link #deleteFavourite(int userId, int productId)}
 * <p>
 * @author long bin
 *
 */
public class FavoriteDataManage {
	private ArrayList<SearchItem> favoriteArray;
	private Context context ;
	private String TAG = FavoriteDataManage.class.getName();
	private UnicodeToString unicode;
	
	private boolean isNoMore = false;//判断是否还有更多数据,true为没有更多了
	/**
	 * {@link FavoriteDataManage}
	 * @param context
	 */
	public FavoriteDataManage(Context context){
		this.context = context;
		unicode = new UnicodeToString();
		favoriteArray = new ArrayList<SearchItem>();
	}
	
	/**
	 *获取客户收藏夹中商品
	 * @param userId 客户id
	 * @param fromIndex 第几个地址开始获取数据
	 * @param acount 获取个数
	 * @return 返回被收藏列表，具体请看搜索模块的{@link SearchItem}
	 */
	public ArrayList<SearchItem> getFavouriteArray(String userId, int fromIndex, int acount){
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return favoriteArray;
		}
		TaskGetList taskGetList = new TaskGetList("userid="+userId, String.valueOf(fromIndex), String.valueOf(acount), "", "", "%2A");
		boolean state = false ;
		try {
			state = taskGetList.execute().get();
			if(isNoMore){
				Toast.makeText(context, context.getResources().getString(R.string.nomore), Toast.LENGTH_SHORT).show();
				isNoMore = false;
				state = true;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			Log.e(TAG, "TaskGetList() InterruptedException");
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "TaskGetList() ExecutionException");
		}
		if(!state){
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		}
		return favoriteArray;
	}
	
	/**
	 * 添加收藏商品
	 * @param userId 客户id
	 * @param productId 商品id
	 * @param token
	 * @return 成功与否
	 */
	public boolean addFavourite(String userId, String productId, String token){
		boolean isSuccess = false;
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return false;
		}
		TaskSave taskSave = new TaskSave(userId, productId, token);
		try {
			isSuccess = taskSave.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "addAddress() InterruptedException");
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "addAddress() ExecutionException");
			e.printStackTrace();
		}
		
		if(!isSuccess){
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		} else {
			return true;
		}
		return isSuccess;
	}
	
	/**
	 * 删除收藏商品
	 * @param userId 客户id
	 * @param productId 商品id
	 * @param token
	 * @return 成功与否
	 */
	public boolean deleteFavourite(String userId, String productId, String token){
		boolean isSuccess = false;
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return isSuccess;
		}
		TaskDelete taskDelete = new TaskDelete(userId, productId, token);
		try {
			isSuccess = taskDelete.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "TaskDelete() InterruptedException");
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "TaskDelete() ExecutionException");
			e.printStackTrace();
		}
		
		if(!isSuccess){
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		}
		return isSuccess;
	}
	
	private class TaskDelete extends AsyncTask<Void, Void, Boolean>{
		private String userId;
		private String productId;
		private String token;
		public TaskDelete(String userId, String productId, String token){
			this.userId = userId;
			this.productId = productId;
			this.token = token;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			bar.setMessage("���ڲ�ѯ");
//			bar.show();
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			DeleteFavorite deleteFavorite = new DeleteFavorite(context);
			try {
				String httpResultString = deleteFavorite.deleteFavorite(userId, String.valueOf(productId), token);///String.valueOf(userId), 
				
				return analysicDeleteJson(httpResultString);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "task delete ioex");
				e.printStackTrace();
				return false;
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "task delete other ex");
				e.printStackTrace();
				return false;
			}
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//			bar.dismiss();
//			if(result)
//				Toast.makeText(context, "�ɹ�", Toast.LENGTH_SHORT).show();
		}
//		private ProgressDialog bar = new ProgressDialog(context);
	}
	
	/**
	 * 解析添加收藏数据
	 * @param resultJson http返回的数据
	 * @return
	 */
	private boolean analysicDeleteJson(String resultJson) {
		if ("".equals(resultJson))
			return false;

		JSONObject httpResultObject;
		try {
			httpResultObject = new JSONObject(resultJson);
			int code = httpResultObject.getInt("code");
			Log.i(TAG, "analysis delete json code"+code);
			if (code == 1){
				return true;
			}
			else {
				return false;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "delete favorite analysis json jsonexception error");
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "delete favorite analysis json exception error");
			e.printStackTrace();
			return false;
		}
	}
	
	private class TaskSave extends AsyncTask<Void, Void, Boolean>{
		private String userId;
		private String productId;
		private String token;
		public TaskSave(String userId, String productId, String token){
			this.userId = userId;
			this.productId = productId;
			this.token = token;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			bar.setMessage("���ڲ�ѯ");
//			bar.show();
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			SaveFavorite saveFavorite = new SaveFavorite(context);
			try {
				String httpResultString = saveFavorite.saveFavorite(String.valueOf(userId), String.valueOf(productId), token);
				
				return analysicSaveJson(httpResultString);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, "task save ioex");
				return false;
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "task save other ex");
				return false;
			}
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//			bar.dismiss();
//			if(result)
//				Toast.makeText(context, "�ɹ�", Toast.LENGTH_SHORT).show();
		}
		private ProgressDialog bar = new ProgressDialog(context);
	}
	
	private int favoriteId = 0;
	public int getFavoriteId(){
		return favoriteId;
	}
	/**
	 * ��������ղ����
	 * @param resultJson http���ص����
	 * @return
	 */
	private boolean analysicSaveJson(String resultJson) {
		if ("".equals(resultJson))
			return false;
		
		JSONObject httpResultObject;
		try {
			httpResultObject = new JSONObject(resultJson);
			int code = httpResultObject.getInt("code");
			Log.i(TAG, "save code :" + code);
			if (code == 1){
//				favoriteId = httpResultObject.getInt("response");
//				JSONObject responseJsonObject = new JSONObject(response);
//				String temp = responseJsonObject.getString("@p_recipient_id");
//				favoriteId = Integer.parseInt(temp);
				return true;
			} else return false;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "save address analysis json jsonexception error");
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "save address analysis json exception error");
			e.printStackTrace();
			return false;
		}
	}
	
	private class TaskGetList extends AsyncTask<Void, Void, Boolean>{
		private String where;
		private String offset;
		private String limit;
		private String group;
		private String order;
		private String fields;
		public TaskGetList(String where, String offset, String limit, String group, String order, String fields){
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
			bar.setMessage(context.getResources().getString(R.string.searching));
			bar.show();
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			GetFavoriteList getList = new GetFavoriteList(context);
			try {
				String httpResultString = getList.getFavoritesListJsonString(where, offset, limit, group, order, fields);
				analysisGetListJson(httpResultString);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, "task getlist ioex");
				return false;
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "task getlist other ex");
				return false;
			}
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			bar.dismiss();
//			if(result)
//				Toast.makeText(context, "�ɹ�", Toast.LENGTH_SHORT).show();
		}
	}
	/**
	 * 解析所有收藏数据
	 * @param httpResultString
	 * @return
	 */
	private ArrayList<SearchItem> analysisGetListJson(String httpResultString){
		JSONObject httpResultObject;
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
					String imageUrl = ImageUrl.IMAGEURL + imageName;//imageUrlClass.getImageUrl();
					searchItem.setImgUrl(imageUrl);
					
//					String maddress = infoItem.getString("address");
//					searchItem.setAddress(unicode.revert(maddress));
//					String isDefault = infoItem.getString("is_default");
//					boolean isDef = isDefault.equals("y") ? true : false;
//					searchItem.setDefaultAddress(isDef);
					favoriteArray.add(searchItem);
//					Log.e(TAG, "???");
				}
			} else if(code == -1){
				isNoMore = true;
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
		return favoriteArray;
	}
	/**
	 * 检查客户是否收藏了某商品
	 * @param userid
	 * @param goodsid
	 * @return true ? false
	 */
	public boolean checkExists(String userid, String goodsid, String token){
		boolean isExists = false;
		if(!ConnectionDetector.isConnectingToInternet(context)) {
//			Toast.makeText(context, "����δ���ӣ����������������״̬��", Toast.LENGTH_LONG).show();
			return isExists;
		}
		try {
			isExists = (new TaskCheck(userid, goodsid, token)).execute().get();
			Log.i(TAG, "is exists?"+ isExists);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "check favorite exists interrupted ex");
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "check favorite exists ExecutionException ex");
			e.printStackTrace();
		}
		return isExists;
	}
	
	private class TaskCheck extends AsyncTask<Void, Void, Boolean>{
		
		private String userid;
		private String goodsid;
		private String token;
		public TaskCheck(String userid, String goodsid, String token){
			this.userid = userid;
			this.goodsid = goodsid;
			this.token = token;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			CheckExistsFavorite check = new CheckExistsFavorite(context);
			try {
				String httpResponse = check.httpResponse(userid, goodsid, token);
				Log.i(TAG, httpResponse);
				JSONObject httpResponseoObject = new JSONObject(httpResponse);
				int code = httpResponseoObject.getInt("code");
				Log.i(TAG, "check exists code" + code);
				if(code == 1) return true;
				else {
					return false;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "check favorite exists task io ex");
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "check favorite exists task ex");
				e.printStackTrace();
			}
			return false;
		}
		
	}
}
