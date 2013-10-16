package com.yidejia.app.mall.datamanage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.yidejia.app.mall.model.PriceLevel;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.search.PriceDataUtil;

/**
 * 获取价格列表
 * @author long bin
 *
 */
public class PriceDataManage {
	private ArrayList<PriceLevel> pricesArray;
	private Context context;
	private String TAG = PriceDataManage.class.getName();
	
	public PriceDataManage(Context context){
		this.context = context;
		pricesArray = new ArrayList<PriceLevel>();
	}
	/**
	 * 
	 * @return pricesArray 价格列表
	 */
	public ArrayList<PriceLevel> getPriceArray(){
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, "网络未连接，请检查您的网络连接状态！", Toast.LENGTH_LONG).show();
			return pricesArray;
		}
		TaskPrice taskPrice = new TaskPrice();
		boolean state = false;
		try {
			state = taskPrice.execute().get();
			if("".equals(httpResponseString)){
				Toast.makeText(context, "连接超时", Toast.LENGTH_SHORT).show();
				state = true;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "get price array interrupter ex");
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "get price array execution ex");
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "get price array  ex");
			e.printStackTrace();
		} 
		if(!state){
			Toast.makeText(context, "网络不给力", Toast.LENGTH_SHORT).show();
		}
		return pricesArray;
	}
	
	private class TaskPrice extends AsyncTask<Void, Void, Boolean>{
		
//		private ProgressDialog bar = new ProgressDialog(context);
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
//			super.onPreExecute();
//			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			bar.setMessage("正在查询");
//			bar.show();
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			PriceDataUtil priceDataUtil = new PriceDataUtil();
			try {
				httpResponseString = priceDataUtil.getHttpResponseString();
				if(!"".equals(httpResponseString)){
					JSONObject jsonObject = new JSONObject(httpResponseString);
					int code = jsonObject.getInt("code");
					if(code == 1){
						String response = jsonObject.getString("response");
						analysisJson(response);
						return true;
					}
				} else{
					httpResponseString = "连接超时";
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "task price io ex");
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "task price ex");
				e.printStackTrace();
			}
			return false;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//			bar.dismiss();
		}
	}
	
	private void analysisJson(String response) throws JSONException{
		JSONObject responseArray = new JSONObject(response);
		int length = responseArray.length();
//		JSONObject jsonObject;
		PriceLevel price;
		JSONObject itemArray;
		for (int i = 0; i < length; i++) {
			price = new PriceLevel();
			String itemString = responseArray.getString("" + (i+1));
			itemArray = new JSONObject(itemString);
			price.setPriceId(itemArray.getString("id"));
			price.setMinPrice(itemArray.getString("min"));
			price.setMaxPrice(itemArray.getString("max"));
			pricesArray.add(price);
		}
	}
	
	private String httpResponseString = "";
}
