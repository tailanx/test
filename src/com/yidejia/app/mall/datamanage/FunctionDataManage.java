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

import com.yidejia.app.mall.model.Function;
import com.yidejia.app.mall.net.search.EffectDataUtil;
import com.yidejia.app.mall.util.UnicodeToString;

/**
 * ��ȡ��Ч�б�
 * @author long bin
 *
 */
public class FunctionDataManage {
	private ArrayList<Function> functionsArray;
	private Context context;
	private UnicodeToString unicode;
	private String TAG = FunctionDataManage.class.getName();
	
	public FunctionDataManage(Context context){
		this.context = context;
		unicode = new UnicodeToString();
		functionsArray = new ArrayList<Function>();
	}
	/**
	 * 
	 * @return functionsArray ��Ч�б�
	 */
	public ArrayList<Function> getFunArray(){
		TaskFun taskFun = new TaskFun("flag%3D%27y%27", "0", "20", "%2A");
		boolean state = false;
		try {
			state = taskFun.execute().get();
			if("".equals(httpResponseString)){
				Toast.makeText(context, "���ӳ�ʱ", Toast.LENGTH_SHORT).show();
				state = true;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "getfunarray interrupter ex");
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "getfunarray execution ex");
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "getfunarray  ex");
			e.printStackTrace();
		} 
		if(!state){
			Toast.makeText(context, "���粻����", Toast.LENGTH_SHORT).show();
		}
		return functionsArray;
	}
	
	private class TaskFun extends AsyncTask<Void, Void, Boolean>{
		private String where;
		private String offset;
		private String limit;
		private String fields;
		
		public TaskFun(String where, String offset, String limit, String fields){
			this.where = where;
			this.offset = offset;
			this.limit = limit;
			this.fields = fields;
		}
		
//		private ProgressDialog bar = new ProgressDialog(context);
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
			EffectDataUtil effectDataUtil = new EffectDataUtil();
			try {
				httpResponseString = effectDataUtil.getHttpResponseString(where, offset, limit, fields);
				if(!"".equals(httpResponseString)){
					JSONObject jsonObject = new JSONObject(httpResponseString);
					int code = jsonObject.getInt("code");
					if(code == 1){
						String response = jsonObject.getString("response");
						analysisJson(response);
						return true;
					}
				} else{
					httpResponseString = "���ӳ�ʱ";
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "task fun io ex");
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "task fun ex");
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
		JSONObject jsonObject;
		Function function;
		
		for (int i = 0; i < length; i++) {
			function = new Function();
			jsonObject = responseArray.getJSONObject(i);
			function.setFunId(jsonObject.getString("id"));
			function.setFunName(unicode.revert(jsonObject.getString("name")));
			function.setDesc(unicode.revert(jsonObject.getString("desc")));
			functionsArray.add(function);
		}
	}
	
	private String httpResponseString = "";
}
