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

import com.yidejia.app.mall.model.UserComment;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.commments.GetProductCommentList;
import com.yidejia.app.mall.util.UnicodeToString;

/**
 * ��ȡ�û�����
 * @author long bin
 *
 */
public class UserCommentDataManage {
	private ArrayList<UserComment> userComments;
	private Context context;
	private UnicodeToString unicode;
	private String TAG = UserCommentDataManage.class.getName();
	
	private boolean isNoMore = false;//�ж��Ƿ��и�������,trueΪû�и�����
	
	public UserCommentDataManage(Context context){
		this.context = context;
		unicode = new UnicodeToString();
		userComments = new ArrayList<UserComment>();
	}
	/**
	 * �����ȡ�ͻ������б�
	 * @param id �ͻ�Id ���� ��Ʒid
	 * @param fromIndex ��ʼλ��
	 * @param amount ����
	 * @param isUser �Ƿ�Ϊ�ͻ�id������Ʒid
	 * @return ʱ�䵹��Ŀͻ������б�
	 */
	public ArrayList<UserComment> getUserCommentsArray(String id, int fromIndex, int amount, boolean isUser){
		String idTemp = isUser? "user_id=":"goods_id=";
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, "����δ���ӣ�����������������״̬��", Toast.LENGTH_LONG).show();
			return userComments;
		}
		TaskGetList taskGetList = new TaskGetList(idTemp+id, String.valueOf(fromIndex), String.valueOf(amount), "", "", "%2A");
		boolean state = false ;
		try {
			state = taskGetList.execute().get();
			if(isNoMore){
				Toast.makeText(context, "û�и�����!", Toast.LENGTH_SHORT).show();
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
			Toast.makeText(context, "���粻������", Toast.LENGTH_SHORT).show();
		}
		return userComments;
	}
	
	/**
	 * �ύ����
	 * @param goodsid ��ƷId
	 * @param userId �ͻ�Id
	 * @param username �ͻ�����
	 * @param text ��������
	 * @param value ����
	 * @param date ����ʱ��
	 * @return �ɹ����
	 */
	public boolean commitComment(String goodsid, String userId, String username ,String text, int value, String date){
		boolean isSuccess = false;
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, "����δ���ӣ�����������������״̬��", Toast.LENGTH_LONG).show();
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
//			bar.setMessage("���ڲ�ѯ");
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
//				Toast.makeText(context, "�ɹ�", Toast.LENGTH_SHORT).show();
		}
//		private ProgressDialog bar = new ProgressDialog(context);
	}
	
	/**
	 * �������е�ַ����
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
