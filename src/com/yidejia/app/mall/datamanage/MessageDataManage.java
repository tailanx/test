package com.yidejia.app.mall.datamanage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.MsgCenter;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.user.ChangeRead;
import com.yidejia.app.mall.net.user.GetMessage;

public class MessageDataManage {
	
	private Context context;
	private ArrayList<MsgCenter> msgArrays;
	
	private boolean isNoMore = false;//判断是否还有更多数据,true为没有更多了
	
	public MessageDataManage(Context context){
		this.context = context;
		msgArrays = new ArrayList<MsgCenter>();
	}
	
	private String userid;
	private String token;
	private String offset;
	private String limit;
	/**
	 * 获取消息中心数据
	 * @param userid
	 * @param token
	 * @param offset
	 * @param limit
	 * @return
	 */
	public boolean getMsgData(String userid, String token, String offset, String limit){
		this.userid = userid;
		this.token = token;
		this.offset = offset;
		this.limit = limit;
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return false;
		}
		TaskMsg taskMsg = new TaskMsg();
		boolean state = false;
		try {
			state = taskMsg.execute().get();
			if(isNoMore){
				Toast.makeText(context, context.getResources().getString(R.string.nomore), Toast.LENGTH_SHORT).show();
				isNoMore = false;
				state = true;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return state;
	}
	
	private class TaskMsg extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			GetMessage getMessage = new GetMessage();
			try {
				String httpResponse = getMessage.getHttpResponse(userid, offset, limit, token);
				JSONObject httpObject;
				try {
					httpObject = new JSONObject(httpResponse);
					int code = httpObject.getInt("code");
					if(code == 1){
						String response = httpObject.getString("response");
						analysis(response);
						return true;
					} else if(code == -1){
						isNoMore = true;
						return false;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		
	}
	
	private void analysis(String response){
		try {
			JSONArray jsonArray = new JSONArray(response);
			JSONObject resObject;
			MsgCenter msgCenter;
			for (int i = 0; i < jsonArray.length(); i++) {
				resObject = jsonArray.getJSONObject(i);
				msgCenter = new MsgCenter();
				msgCenter.setMsgid(resObject.getString("id"));
				msgCenter.setUid(resObject.getString("uid"));
				msgCenter.setPid(resObject.getString("pid"));
				msgCenter.setIsread(resObject.getString("isread"));
				String msg = resObject.getString("msg");
				try {
					JSONObject msgObject = new JSONObject(msg);
					msgCenter.setMsg(msgObject.getString("msg"));
					msgCenter.setTitle(msgObject.getString("title"));
					msgCenter.setUrl(msgObject.getString("url"));
					msgCenter.setTime(msgObject.getString("starttime"));
				} catch (JSONException e) {
					// TODO: handle exception
				}
				msgArrays.add(msgCenter);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 改变是否为已读
	 * @param userId
	 * @param msgId
	 * @param token
	 * @return true:false
	 */
	public boolean changeReadState(String userId, String msgId, String token){
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return false;
		} 
		boolean state = false;
		try {
			state = new TaskChangeRead(userId, msgId, token).execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return state;
	}
	
	private class TaskChangeRead extends AsyncTask<Void, Void, Boolean>{

		private String userId;
		private String msgId;
		private String token;
		public TaskChangeRead(String userId, String msgId, String token){
			this.userId = userId;
			this.msgId = msgId;
			this.token = token;
		}
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ChangeRead changeRead = new ChangeRead();
			try {
				String httpResponse = changeRead.getHttpResponse(userId, msgId, token);
				try {
					JSONObject jsonObject = new JSONObject(httpResponse);
					int code = jsonObject.getInt("code");
					if(code == 1){
						boolean result = jsonObject.getBoolean("response");
						return result;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
	}
	public ArrayList<MsgCenter> getMsg(){
		return msgArrays;
	}

}