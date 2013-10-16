package com.yidejia.app.mall.datamanage;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.user.GetCount;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * ���������ǳ�����ļ�������
 * @author long bin
 *
 */
public class PersonCountDataManage {
	
	private Context context;
	
	public PersonCountDataManage(Context context){
		this.context = context;
	}
	
	private String userid;
	private String token;
	/**
	 * ��ȡ��Ϣ��������
	 * @param userid
	 * @param token
	 * @return
	 */
	public boolean getCountData(String userid, String token){
		this.userid = userid;
		this.token = token;
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, "����δ���ӣ�����������������״̬��", Toast.LENGTH_LONG).show();
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
				String httpResponse = getCount.getHttpResponse(userid, token);
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
	
	private String scores = "";//����
	private String order = "";//����
	private String favoliten = "";//�ղ�
	private String msg = "";//��Ϣ��
	
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
