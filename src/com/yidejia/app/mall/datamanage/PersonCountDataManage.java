package com.yidejia.app.mall.datamanage;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.widget.Toast;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.user.GetCount;

/**
 * 个人中心昵称下面的几个数据
 * @author long bin
 *
 */
public class PersonCountDataManage {
	
	private Context context;
	private Resources res;
	
	public PersonCountDataManage(Context context){
		this.context = context;
		res = context.getResources();
	}
	
	private String userid;
	private String token;
	/**
	 * 获取消息中心数据
	 * @param userid
	 * @param token
	 * @return
	 */
	public boolean getCountData(String userid, String token){
		this.userid = userid;
		this.token = token;
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, res.getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return false;
		}
		TaskCount taskCount = new TaskCount();
		boolean state = false;
		try {
			state = taskCount.execute().get();
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
	
	private class TaskCount extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
//			GetMessage getMessage = new GetMessage();
			GetCount getCount = new GetCount();
			try {
				String httpResponse = null;
				try {
					httpResponse = getCount.getHttpResponse(userid, token);
				} catch (TimeOutEx e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return false;
				}
				JSONObject httpObject;
				try {
					httpObject = new JSONObject(httpResponse);
					int code = httpObject.getInt("code");
					if(code == 1){
						String response = httpObject.getString("response");
//						analysis(response);
						JSONObject resObject = new JSONObject(response);
						scores = resObject.getString("scores");
						order = resObject.getString("order");
						favoliten = resObject.getString("favoliten");
//						Log.i("info", favoliten +"  fa");
						msg = resObject.getString("msg");
						return true;
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
	
	private String scores = "";//积分
	private String order = "";//订单
	private String favoliten = "";//收藏
	private String msg = "";//消息数
	
	public String getScores(){
		return scores;
	}
	
	public String getOrder(){
		return order;
	}
	
	public String getFavoliten(){
		return favoliten;
	}
	
	public String getMsg(){
		return msg;
	}
	
}
