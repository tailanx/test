package com.yidejia.app.mall.net.homepage;

import java.io.IOException;

import org.apache.http.conn.ConnectTimeoutException;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.yidejia.app.mall.net.HttpAddressParam;
import com.yidejia.app.mall.net.HttpGetConn;
import com.yidejia.app.mall.util.Md5;
/**
 * 获取首页
 * @author long bin
 *
 */
public class GetHomePage {
	private String[] keys = new String[6];
	private String[] values = new String[6];
	private String TAG = "GetFavoriteList";
	private Context context;
	
	public GetHomePage(Context context){
		this.context = context;
	}
	
	private void setKeysAndValues(){
		keys[0] = "api";
		String api = "home.mobile.page";
		values[0] = api;
		keys[1] = "is_web";
		values[1] = "n";
		keys[2] = "key";
//		values[2] = "fw_test";
		values[2] = "fw_mobile";
		keys[3] = "format";
		values[3] = "array";
		keys[4] = "ts";
		long time = System.currentTimeMillis();
		String ts = String.valueOf(time/1000);
		values[4] = ts;
		
		keys[5] = "sign";
		StringBuffer strTemp = new StringBuffer();
//		strTemp.append("ChunTianfw_mobile123456");
		strTemp.append("ChunTianfw_mobile@SDF!TD#DF#*CB$GER@");
		strTemp.append(api);
		strTemp.append(ts);
		Md5 md = new Md5();
		String result = md.getMD5Str(strTemp.toString());
		md = null;
        strTemp = null;
		values[5] = result;
	}
	
	private String getHttpAddress(){
		StringBuffer result = new StringBuffer();
//		result.append("http://192.168.1.254:802/?");
		setKeysAndValues();
		result.append(HttpAddressParam.getHttpAddress(keys, values));
		Log.i(TAG, result.toString());
		return result.toString();
	}
	
	private String result = "";
	public String getHomePageJsonString()throws IOException{
		HttpGetConn conn = new HttpGetConn(getHttpAddress());
		result = conn.getJsonResult();
		return result;
	}
//	public String getFavoriteListJson(){
//		AsyncHttpClient client = new AsyncHttpClient();
//		client.setTimeout(5000);
//		client.get(getHttpAddress(), new AsyncHttpResponseHandler(){
//
//			@Override
//			public void onSuccess(String content) {
//				// TODO Auto-generated method stub
//				super.onSuccess(content);
//				Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
//				Log.i(TAG, "1111"+content);
//				bar.dismiss();
//				result = content;
//			}
//
//			@Override
//			public void onFailure(Throwable error, String content) {
//				// TODO Auto-generated method stub
//				super.onFailure(error, content);
//				Log.i(TAG, "2222"+content);
//				if (error.getCause() instanceof ConnectTimeoutException) {
//					System.out.println("Connection timeout !");
//					Log.i(TAG, "Connection timeout ");
//				}
//				bar.dismiss();
//			}
//
//			@Override
//			public void onStart() {
//				// TODO Auto-generated method stub
//				super.onStart();
//				bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//				bar.setMessage("正在查询");
//				bar.show();
//			}
//			
//			private ProgressDialog bar = new ProgressDialog(context);
//		});
//		return result;
//	}
//	
	
}
