package com.yidejia.app.mall.net;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;


public class ImageUrl {
	public static String IMAGEURL = "";
	private static String TAG = ImageUrl.class.getName();
	
	public String getImageUrl(){
		if(null != IMAGEURL && !"".equals(IMAGEURL)){
			return IMAGEURL;
		} else{
			Log.i(TAG, "is null?"+IMAGEURL);
			TaskImageUrl taskImageUrl = new TaskImageUrl();
			boolean state = false ;
			try {
				state = taskImageUrl.execute().get();
				if(state){
					return IMAGEURL;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "TaskGetList() InterruptedException");
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "TaskGetList() ExecutionException");
				e.printStackTrace();
			}
			
			return IMAGEURL;
		}
	}
	
	private class TaskImageUrl extends AsyncTask<Void, Void, Boolean>{
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			GetImageUrlPrefix getList = new GetImageUrlPrefix();
			try {
				String httpResultString = getList.getUrlJsonString();
				analysisJson(httpResultString);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "task getlist ioex");
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
//			if(result)
//				Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
		}
	}
	
//	private String urlString;
	/**
	 * 解析图片Url数据
	 * @param resultJson http返回的数据
	 * @return
	 */
	private boolean analysisJson(String resultJson) {
		if ("".equals(resultJson))
			return false;
		
		JSONObject httpResultObject;
		try {
			httpResultObject = new JSONObject(resultJson);
			int code = httpResultObject.getInt("code");
			if (code == 1){
				IMAGEURL = httpResultObject.getString("response");
//				JSONObject responseJsonObject = new JSONObject(response);
//				String temp = responseJsonObject.getString("@p_recipient_id");
//				favoriteId = Integer.parseInt(temp);
				return true;
			} else return false;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "image url analysis json jsonexception error");
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "image url analysis json exception error");
			e.printStackTrace();
			return false;
		}
	}
}
