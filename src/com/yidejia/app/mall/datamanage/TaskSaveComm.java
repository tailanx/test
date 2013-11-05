package com.yidejia.app.mall.datamanage;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.commments.SaveProductComment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

public class TaskSaveComm {

	private Activity activity;
	private TaskSave taskSave;

	public TaskSaveComm(Activity activity) {
		this.activity = activity;
	}

	/**
	 * 提交评论
	 * 
	 * @param goodsId
	 *            商品id
	 * @param userId
	 *            用户id
	 * @param nick
	 *            用户名称，昵称
	 * @param title
	 *            评论的标题，只能是不满意，一般般，还可以，满意，很满意这5种之一
	 * @param commentText
	 *            评论的内容
	 * @param commDate
	 *            评论时间
	 */
	public void saveComm(String goodsId, String userId, String nick,
			String title, String commentText, String commDate) {
		if (null == nick || "".equals(nick))
			nick = userId;
		if (!ConnectionDetector.isConnectingToInternet(activity)) {
			Toast.makeText(activity,
					activity.getResources().getString(R.string.no_network),
					Toast.LENGTH_LONG).show();
			return;
		}
		
		if(taskSave != null && taskSave.getStatus() == AsyncTask.Status.RUNNING){
			taskSave.cancel(true);
		}
		taskSave = new TaskSave(goodsId, userId, nick, title, commentText, commDate);
		taskSave.execute();
	}

	private class TaskSave extends AsyncTask<Void, Void, Boolean> {

		private ProgressDialog bar;
		private String goods_id, user_id, user_name, title, experience,
				commentDate;
		
		public TaskSave(String goods_id, String user_id, String user_name, String title, String experience, String commentDate){
			this.goods_id = goods_id;
			this.user_id = user_id;
			this.user_name = user_name;
			this.title = title;
			this.experience = experience;
			this.commentDate = commentDate;
			
			bar = new ProgressDialog(activity);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			// bar.setMessage(activity.getResources().getString(
			// R.string.searching));
			bar.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			SaveProductComment saveProductComment = new SaveProductComment(
					activity);
			try {
				String httpResp = saveProductComment.saveComment(goods_id, user_id,
						user_name, title, experience, commentDate);
				return analysisHttpResp(httpResp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result){
				bar.dismiss();
				Toast.makeText(activity, "提交评论成功！", Toast.LENGTH_LONG).show();
				activity.finish();
			}else {
				bar.dismiss();
				Toast.makeText(activity, "提交评论失败！", Toast.LENGTH_LONG).show();
			}
		}
		
	}
	
	private boolean analysisHttpResp(String httpResp){
		boolean isSaveSucceess = false;
		int code = -1;
		try {
			JSONObject httpObject = new JSONObject(httpResp);
			code = httpObject.getInt("code");
			if(code == 1) isSaveSucceess = true;
			else isSaveSucceess = false;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isSaveSucceess;
	}
}
