package com.yidejia.app.mall.net.voucher;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.conn.ConnectTimeoutException;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.yidejia.app.mall.net.HttpAddressParam;
import com.yidejia.app.mall.net.HttpPostConn;
import com.yidejia.app.mall.util.Md5;
/**
 * 积分卡券
 * @author long bin
 *
 */
public class Voucher {
	private String[] keys = new String[6];
	private String[] values = new String[6];
	private String TAG = "Voucher";
	private Context context;
	private Map<String, String> httpAddressMap = new ConcurrentHashMap<String, String>();
	
	public Voucher(Context context){
		this.context = context;
	}
	
	private void setKeysAndValues(String id){
		keys[0] = "api";
		String api = "user.info.get";
		values[0] = api;
		keys[1] = "id";
		values[1] = id;
		
		
		keys[2] = "key";
		values[2] = "fw_mobile";
		keys[3] = "format";
		values[3] = "array";
		keys[4] = "ts";
		long time = System.currentTimeMillis();
		String ts = String.valueOf(time/1000);
		values[4] = ts;
		
		keys[5] = "sign";
		StringBuffer strTemp = new StringBuffer();
		strTemp.append("ChunTianfw_mobile123456");
		strTemp.append(api);
		strTemp.append(ts);
		Md5 md = new Md5();
		String result = md.getMD5Str(strTemp.toString());
		md = null;
        strTemp = null;
		values[5] = result;
	}
	
	private String getHttpAddress(String id){
		StringBuffer result = new StringBuffer();
		result.append("http://192.168.1.254:802/?");
		setKeysAndValues(id);
		result.append(HttpAddressParam.getHttpAddress(keys, values));
		Log.i(TAG, result.toString());
		for (int j = 0; j < keys.length; j++) {
			httpAddressMap.put(keys[j], values[j]);
		}
		return result.toString();
	}
	
	private String result = "";
	private String urlString = "http://192.168.1.254:802/";
	/**
	 * 
	 * @param id
	 * @return http response string
	 * @throws IOException
	 */
	public String getResponse(String id) throws IOException{
		getHttpAddress(id);
		HttpPostConn conn = new HttpPostConn(urlString, keys, values);
		result = conn.getJsonResult();
		return result;
	}
//	public String getVoucherJson(){
//		AsyncHttpClient client = new AsyncHttpClient();
//		getHttpAddress();
////		RequestParams params = new RequestParams(httpAddressMap);
//		
//		client.setTimeout(5000);
//		client.post(getHttpAddress(), null, new AsyncHttpResponseHandler(){
//
//			@Override
//			public void onSuccess(String content) {
//				// TODO Auto-generated method stub
//				super.onSuccess(content);
//				Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
//				Log.i(TAG, "1111"+content);
//				bar.dismiss();
//				result  = content;
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
