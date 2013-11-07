package com.yidejia.app.mall.datamanage;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.net.order.DelOrder;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class TaskDelOrder {
	
	private Context context;
	private TaskDel taskDel;
	private String customer_id;
	private String orderCode;
	private String token;
	private String TAG = TaskDelOrder.class.getName();
	
	public TaskDelOrder(Context context){
		this.context = context;
	}
	
	/**
	 * 删除订单
	 * @param customer_id 用户id
	 * @param orderCode 订单号
	 * @param token 用户token
	 */
	public void delOrder(String customer_id, String orderCode, String token){
		this.customer_id = customer_id;
		this.orderCode = orderCode;
		this.token = token;
		
		taskDel = new TaskDel();
		taskDel.execute();
	}
	
	/**
	 * 
	 * 删除订单
	 *
	 */
	private class TaskDel extends AsyncTask<Void, Void, Boolean>{
		
		private ProgressDialog bar;

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return getAndAnalysisHttpResp();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			bar = new ProgressDialog(context);
			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			// bar.setMessage(context.getResources().getString(
			// R.string.searching));
			bar.show();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result){
				Toast.makeText(context, "删除订单成功！", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(context, "删除订单失败！", Toast.LENGTH_LONG).show();
			}
			bar.dismiss();
		}
		
	}
	
	
	private boolean getAndAnalysisHttpResp(){
		boolean isSuccess = false;
		DelOrder delOrder = new DelOrder();
		try {
			String httpResp = delOrder.getHttpResponse(customer_id, orderCode, token);
			return analysisData(httpResp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "del order get http error");
			e.printStackTrace();
		}
		return isSuccess;
	}
	
	/**
	 * 解析服务器返回数据
	 * @param httpResp 待解析的字符串
	 * @return 只有返回"success删除成功"才算删除成功
	 */
	private boolean analysisData(String httpResp){
		boolean isSuccess = false;
		try {
			JSONObject httpObject = new JSONObject(httpResp);
			int code = httpObject.getInt("code");
			String response = httpObject.getString("response");
			if(code == 1){
				JSONObject respObject = new JSONObject(response);
				String result = respObject.getString("");
				if("success删除成功".equals(result)){
					return true;
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return isSuccess;
	}
}
