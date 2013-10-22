package com.yidejia.app.mall.datamanage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.Brand;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.search.BrandDataUtil;
import com.yidejia.app.mall.util.UnicodeToString;

/**
 * 获取品牌列表
 * @author long bin
 *
 */
public class BrandDataManage {
	private ArrayList<Brand> brandsArray;
	private Context context;
	private UnicodeToString unicode;
	private String TAG = BrandDataManage.class.getName();
	
	public BrandDataManage(Context context){
		this.context = context;
		unicode = new UnicodeToString();
		brandsArray = new ArrayList<Brand>();
	}
	/**
	 * 
	 * @return brandsArray 品牌列表
	 */
	public ArrayList<Brand> getBrandArray(){
		if(!ConnectionDetector.isConnectingToInternet(context)) {
//			Toast.makeText(context, "����δ���ӣ����������������״̬��", Toast.LENGTH_LONG).show();
			return brandsArray;
		}
		TaskBrand taskBrand = new TaskBrand();
		boolean state = false;
		try {
			state = taskBrand.execute().get();
			if("".equals(httpResponseString)){
				Toast.makeText(context, context.getResources().getString(R.string.time_out), Toast.LENGTH_SHORT).show();
				state = true;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "get brand array interrupter ex");
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "get brand array execution ex");
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "get brand array  ex");
			e.printStackTrace();
		} 
		if(!state){
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		}
		return brandsArray;
	}
	
	private class TaskBrand extends AsyncTask<Void, Void, Boolean>{
		
//		private ProgressDialog bar = new ProgressDialog(context);
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
//			super.onPreExecute();
//			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			bar.setMessage("���ڲ�ѯ");
//			bar.show();
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			BrandDataUtil brandDataUtil = new BrandDataUtil();
			try {
				httpResponseString = brandDataUtil.getHttpResponseString();
				if(!"".equals(httpResponseString)){
					JSONObject jsonObject = new JSONObject(httpResponseString);
					int code = jsonObject.getInt("code");
					if(code == 1){
						String response = jsonObject.getString("response");
						analysisJson(response);
						return true;
					}
				} else{
					httpResponseString = context.getResources().getString(R.string.time_out);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "task brand io ex");
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "task brand ex");
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
		JSONArray responseArray = new JSONArray(response);
		int length = responseArray.length();
//		JSONObject jsonObject;
		Brand brand;
		JSONArray itemArray;
		for (int i = 0; i < length; i++) {
			brand = new Brand();
			itemArray = responseArray.getJSONArray(i);
			int count = itemArray.length();
			for (int j = 0; j < count; j++) {
				brand.setBrandName(itemArray.getString(j));
				if(j < count - 1)
					brand.setDesc(unicode.revert(itemArray.getString(j+1)));
			}
//			jsonObject = responseArray.getJSONObject(i);
			
//			brand.setFunId(jsonObject.getString("id"));
//			brand.setBrandName(unicode.revert(jsonObject.getString("name")));
//			brand.setDesc(unicode.revert(jsonObject.getString("desc")));
			brandsArray.add(brand);
		}
	}
	
	private String httpResponseString = "";
}
