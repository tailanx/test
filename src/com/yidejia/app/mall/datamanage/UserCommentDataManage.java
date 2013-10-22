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
import com.yidejia.app.mall.model.UserComment;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.commments.GetProductCommentList;
import com.yidejia.app.mall.util.UnicodeToString;

/**
 * 获取用户评论
 * @author long bin
 *
 */
public class UserCommentDataManage {
	private ArrayList<UserComment> userComments;
	private Context context;
	private UnicodeToString unicode;
	private String TAG = UserCommentDataManage.class.getName();
	
	private boolean isNoMore = false;//判断是否还有更多数据,true为没有更多了
	
	public UserCommentDataManage(Context context){
		this.context = context;
		unicode = new UnicodeToString();
		userComments = new ArrayList<UserComment>();
	}
	/**
	 * 倒叙获取客户评论列表
	 * @param id 客户Id 或者 商品id
	 * @param fromIndex 起始位置
	 * @param amount 个数
	 * @param isUser 是否为客户id还是商品id
	 * @return 时间倒叙的客户评论列表
	 */
	public ArrayList<UserComment> getUserCommentsArray(String id, int fromIndex, int amount, boolean isUser){
		String idTemp = isUser? "user_id=":"goods_id=";
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return userComments;
		}
		TaskGetList taskGetList = new TaskGetList(idTemp+id, String.valueOf(fromIndex), String.valueOf(amount), "", "", "%2A");
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
		} catch (Exception e){
			Log.e(TAG, "other ex");
			e.printStackTrace();
		}
		if(!state){
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		}
		return userComments;
	}
	
	/**
	 * 提交评论
	 * @param goodsid 产品Id
	 * @param userId 客户Id
	 * @param username 客户名称
	 * @param text 评论内容
	 * @param value 评分
	 * @param date 评论时间
	 * @return 成功与否
	 */
	public boolean commitComment(String goodsid, String userId, String username ,String text, int value, String date){
		boolean isSuccess = false;
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return isSuccess;
		}
		
		return isSuccess;
	}
	
	private class TaskGetList extends AsyncTask<Void, Void, Boolean>{
		String where;
		String offset;
		String limit;
		String group;
		String order;
		String fields;
		public TaskGetList(String where, String offset, String limit, String group, String order, String fields){
			this.where = where;
			this.offset = offset;
			this.limit = limit;
			this.group = group;
			this.order = order;
			this.fields = fields;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			bar.setMessage("正在查询");
//			bar.show();
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			GetProductCommentList getList = new GetProductCommentList(context);
			try {
				String httpResultString = getList.getCommentsListJsonString(where, offset, limit, group, order, fields);
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
//			bar.dismiss();
//			if(result)
//				Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
		}
//		private ProgressDialog bar = new ProgressDialog(context);
	}
	
	/**
	 * 解析所有地址数据
	 * @param httpResultString
	 * @return
	 */
	private ArrayList<UserComment> analysisGetListJson(String httpResultString){
		JSONObject httpResultObject;
		try {
			httpResultObject = new JSONObject(httpResultString);
			int code = httpResultObject.getInt("code");
			Log.i(TAG, "code"+code);
			if(code == 1){
				String responseString = httpResultObject.getString("response");
				JSONArray responseArray = new JSONArray(responseString);
				int length = responseArray.length();
				JSONObject commentItem ;
				UserComment comments;
				for (int i = 0; i < length; i++) {
					comments = new UserComment();
					commentItem = responseArray.getJSONObject(i);
					String id = commentItem.getString("id");
					comments.setId(id);
					String goodsId = commentItem.getString("goods_id");
					comments.setGoodsId(goodsId);
					String user_id = commentItem.getString("user_id");
					comments.setUserId(user_id);
					String name = commentItem.getString("user_name");
					comments.setUserName(unicode.revert(name));
					String title = commentItem.getString("title");
					comments.setTitle(unicode.revert(title));
					String experience = commentItem.getString("experience");
					comments.setUserCommentText(unicode.revert(experience));
					String commentDate = commentItem.getString("commentDate");
					comments.setCommentTime(commentDate);
					String level = commentItem.getString("customer_grade");
					comments.setVipLevel(unicode.revert(level));
//					String maddress = commentItem.getString("address");
//					comments.setAddress(unicode.revert(maddress));
//					String isDefault = commentItem.getString("is_default");
//					boolean isDef = isDefault.equals("y") ? true : false;
//					comments.setDefaultAddress(isDef);
					int rate = 5;
					comments.setRate(rate);
					userComments.add(comments);
				}
			} else if(code == -1){
				isNoMore = true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userComments;
	}
	
	private class TaskSave extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			return null;
		}
		
	}
}