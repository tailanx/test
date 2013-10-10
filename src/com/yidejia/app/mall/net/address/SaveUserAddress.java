package com.yidejia.app.mall.net.address;

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
 * 保存（新建更新）收件人信息
 * @author long bin
 *
 */
public class SaveUserAddress {
	private String[] keys = new String[13];
	private String[] values = new String[13];
	private String TAG = "SaveUserAddress";
	private Context context;
	private Map<String, String> httpAddressMap = new ConcurrentHashMap<String, String>();
	
	public SaveUserAddress(Context context){
		this.context = context;
	}
	
	private void setKeysAndValues(String customer_id, String customer_name, String handset, String province,
			String city, String district, String address, String recipient_id){
		keys[0] = "api";
		String api = "ucenter.address.save";
		values[0] = api;
		keys[1] = "customer_id";
		values[1] = customer_id;
		keys[2] = "customer_name";
		values[2] = customer_name;
		keys[3] = "handset";
		values[3] = handset;
		keys[4] = "province";
		values[4] = province;
		keys[5] = "city";
		values[5] = city;
		keys[6] = "district";
		values[6] = district;
		keys[7] = "address";
		values[7] = address;
		keys[8] = "recipient_id";
		values[8] = recipient_id;
		
		keys[9] = "key";
		values[9] = "fw_mobile";
		keys[10] = "format";
		values[10] = "array";
		keys[11] = "ts";
		long time = System.currentTimeMillis();
		String ts = String.valueOf(time/1000);
		values[11] = ts;
		
		keys[12] = "sign";
		StringBuffer strTemp = new StringBuffer();
		strTemp.append("ChunTianfw_mobile123456");
		strTemp.append(api);
		strTemp.append(ts);
		Md5 md = new Md5();
		String result = md.getMD5Str(strTemp.toString());
		md = null;
        strTemp = null;
		values[12] = result;
	}
	
	private String getHttpAddress(String customer_id, String customer_name, String handset, String province,
			String city, String district, String address, String recipient_id){
		StringBuffer result = new StringBuffer();
//		result.append("http://192.168.1.254:802/?");
		setKeysAndValues(customer_id, customer_name, handset, province,
				 city, district, address, recipient_id);
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
	 * @param customer_id
	 * @param customer_name
	 * @param handset
	 * @param province
	 * @param city
	 * @param district
	 * @param address
	 * @param recipient_id
	 * @return
	 * @throws IOException
	 */
	public String saveAddress(String customer_id, String customer_name, String handset, String province,
			String city, String district, String address, String recipient_id) throws IOException{
		getHttpAddress(customer_id, customer_name, handset, province,
				 city, district, address, recipient_id);
		HttpPostConn conn = new HttpPostConn(urlString, keys, values);
		result = conn.getJsonResult();
		return result;
	}
//	public String saveAddressJson(){
//		AsyncHttpClient client = new AsyncHttpClient();
//		getHttpAddress();
//		RequestParams params = new RequestParams(httpAddressMap);
//		
//		client.setTimeout(5000);
//		client.post("http://192.168.1.254:802/", params, new AsyncHttpResponseHandler(){
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
	
	
}
