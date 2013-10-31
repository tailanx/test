package com.yidejia.app.mall.datamanage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.Express;
import com.yidejia.app.mall.model.FreePost;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.express.GetDistributionList;
import com.yidejia.app.mall.net.express.GetExpressList;
import com.yidejia.app.mall.net.express.GetFreeList;
import com.yidejia.app.mall.util.UnicodeToString;

import android.content.Context;

public class ExpressDataManage {
	
	private Context context;
	private UnicodeToString unicode;
	
	public ExpressDataManage(Context context){
		this.context = context;
		unicode = new UnicodeToString();
		expresses = new ArrayList<Express>();
		distributions = new ArrayList<Express>();
		freePosts = new ArrayList<FreePost>();
	}
	
	private String TAG = ExpressDataManage.class.getName();
//	private Express express;
	private ArrayList<Express> expresses;
	/**
	 * 快递费用
	 * @param province 
	 * @param isDefault y or n
	 * @return 
	 */
	public ArrayList<Express> getExpressesExpenses(String province, String isDefault){
		if(!ConnectionDetector.isConnectingToInternet(context)) {
//			Toast.makeText(context, "����δ���ӣ����������������״̬��", Toast.LENGTH_LONG).show();
			return expresses;
		}
		StringBuffer where = new StringBuffer();
		where.append("province%3D%27");
		where.append(province);
		where.append("%27+and+is_default%3D%27");
		where.append(isDefault);
		where.append("%27");
		TaskExpress taskExpress = new TaskExpress(where.toString(), "0", "20", "", "", "%2A");
		boolean state = false ;
		try {
			state = taskExpress.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "TaskGetList() InterruptedException");
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "TaskGetList() ExecutionException");
			e.printStackTrace();
		}
		if(!state){
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		}
		return expresses;
	}
	
	private class TaskExpress extends AsyncTask<Void, Void, Boolean>{
		private String where;
		private String offset;
		private String limit;
		private String group;
		private String order;
		private String fields;
		
		public TaskExpress(String where, String offset, String limit, String group, String order, String fields){
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
			GetExpressList getList = new GetExpressList(context);
			try {
				String httpResponseString = getList.getExpressListJsonString(where, offset, limit, group, order, fields);
				JSONObject jsonObject = new JSONObject(httpResponseString);
				int code = jsonObject.getInt("code");
				if(code == 1){
					String responseString = jsonObject.getString("response");
					analysisJson(responseString);
					return true;
				} else {
					return false;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "task getlist io ex");
				e.printStackTrace();
				return false;
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "task getlist other ex");
				e.printStackTrace();
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
	
	private void analysisJson(String responseString) throws JSONException{
		JSONArray responseArray = new JSONArray(responseString);
		JSONObject responseObject;
		Express express;
		int length = responseArray.length();
		for (int i = 0; i < length; i++) {
			express = new Express();
			responseObject = responseArray.getJSONObject(i);
			express.setEms(responseObject.getString("ems"));
			express.setExpress(responseObject.getString("express"));
			if(i == 0) Log.i(TAG, "pre_id:" + responseObject.getString("pre_id"));
			express.setPreId(responseObject.getString("pre_id"));
			if(i==0)Log.i(TAG, express.getPreId());
			express.setIsDefault("y".equals(responseObject.getString("is_default")));
			expresses.add(express);
		}
	}
	
	private ArrayList<Express> distributions;// 配送中心
	/**
	 * 配送中心
	 * @param id 根据地址id获取配送中心
	 * @param fromIndex 
	 * @param amount
	 * @return
	 */
	public ArrayList<Express> getDistributionsList(String id, String fromIndex, String amount){
		if(!ConnectionDetector.isConnectingToInternet(context)) {
//			Toast.makeText(context, "����δ���ӣ����������������״̬��", Toast.LENGTH_LONG).show();
			return distributions;
		}
		TaskDis taskDis = new TaskDis("id="+id, fromIndex, amount, "", "", "%2A");
		boolean state = false ;
		try {
			state = taskDis.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "TaskGetList() InterruptedException");
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "TaskGetList() ExecutionException");
			e.printStackTrace();
		}
		if(!state){
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		}
		return distributions;
	}
	
	private class TaskDis extends AsyncTask<Void, Void, Boolean>{
		private String where;
		private String offset;
		private String limit;
		private String group;
		private String order;
		private String fields;
		
		public TaskDis(String where, String offset, String limit, String group, String order, String fields){
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
			GetDistributionList getList = new GetDistributionList(context);
			try {
				String httpResponseString = getList.getListJsonString(where, offset, limit, group, order, fields);
				JSONObject jsonObject = new JSONObject(httpResponseString);
				int code = jsonObject.getInt("code");
				if(code == 1){
					String responseString = jsonObject.getString("response");
					analysisDisJson(responseString);
					return true;
				} else {
					return false;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "task dis getlist io ex");
				e.printStackTrace();
				return false;
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "task dis getlist other ex");
				e.printStackTrace();
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
	
	private void analysisDisJson(String responseString) throws JSONException{
		JSONArray responseArray = new JSONArray(responseString);
		JSONObject responseObject;
		Express express = new Express();
		int length = responseArray.length();
		for (int i = 0; i < length; i++) {
			responseObject = responseArray.getJSONObject(i);
			express.setDisName(unicode.revert(responseObject.getString("name")));
			express.setDisDesc(unicode.revert(responseObject.getString("desc")));
			if("y".equals(responseObject.get("flag")))
				distributions.add(express);
		}
	}
	private ArrayList<FreePost> freePosts;//免邮
	/**
	 * 免邮信息
	 * @param fromIndex 
	 * @param amount
	 * @return
	 */
	public ArrayList<FreePost> getFreePostList(String fromIndex, String amount){
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return freePosts;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());
		String dateStr = format.format(curDate);
		String str = "+startDate+%3C%3D+%27"+dateStr+"%27";
		TaskFree taskDis = new TaskFree(str, fromIndex, amount, "", "", "%2A");
		boolean state = false ;
		try {
			state = taskDis.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "TaskGetList() InterruptedException");
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "TaskGetList() ExecutionException");
			e.printStackTrace();
		}
		if(!state){
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		}
		return freePosts;
	}
	
	private class TaskFree extends AsyncTask<Void, Void, Boolean>{
		private String where;
		private String offset;
		private String limit;
		private String group;
		private String order;
		private String fields;
		
		public TaskFree(String where, String offset, String limit, String group, String order, String fields){
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
			GetFreeList getList = new GetFreeList(context);
			try {
				String httpResponseString = getList.getListJsonString(where, offset, limit, group, order, fields);
				JSONObject jsonObject = new JSONObject(httpResponseString);
				int code = jsonObject.getInt("code");
				if(code == 1){
					String responseString = jsonObject.getString("response");
					analysisFreeJson(responseString);
					return true;
				} else {
					return false;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "task dis getlist io ex");
				e.printStackTrace();
				return false;
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "task dis getlist other ex");
				e.printStackTrace();
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
	
	private void analysisFreeJson(String responseString) throws JSONException{
		JSONArray responseArray = new JSONArray(responseString);
		JSONObject responseObject;
		FreePost frees = new FreePost();
		int length = responseArray.length();
		for (int i = 0; i < length; i++) {
			responseObject = responseArray.getJSONObject(i);
			frees.setId(responseObject.getString("id"));
			frees.setName(unicode.revert(responseObject.getString("name")));
			frees.setStartDate(responseObject.getString("startdate"));
			frees.setEndDate(responseObject.getString("enddate"));
			frees.setMax(responseObject.getString("max"));
			frees.setDesc(unicode.revert(responseObject.getString("desc")));
			freePosts.add(frees);
		}
	}
}
