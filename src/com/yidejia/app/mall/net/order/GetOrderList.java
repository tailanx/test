package com.yidejia.app.mall.net.order;

import java.io.IOException;

import org.apache.http.conn.ConnectTimeoutException;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpAddressParam;
import com.yidejia.app.mall.net.HttpGetConn;
import com.yidejia.app.mall.util.Md5;
/**
 * ��ȡ����
 * @author long bin
 *
 */
public class GetOrderList {
	private String[] keys = new String[11];
	private String[] values = new String[11];
	private String TAG = GetOrderList.class.getName();
	private Context context;
	
	public GetOrderList(Context context){
		this.context = context;
	}
	
	private void setKeysAndValues(String user_id, String code, String date, String status, String offset1, String limit1){
		keys[0] = "api";
		String api = "ucenter.order.getOrders";
		values[0] = api;
		keys[1] = "user_id";
		values[1] = user_id;
		keys[2] = "code";
		values[2] = code;
		keys[3] = "date";
		values[3] = date;
		keys[4] = "status";
		values[4] = status;
		keys[5] = "offset1";
		values[5] = offset1;
		keys[6] = "limit1";
		values[6] = limit1;
		keys[7] = "key";
//		values[7] = "fw_test";
		values[7] = "fw_mobile";
		keys[8] = "format";
		values[8] = "array";
		keys[9] = "ts";
		long time = System.currentTimeMillis();
		String ts = String.valueOf(time/1000);
		values[9] = ts;
		
		keys[10] = "sign";
		StringBuffer strTemp = new StringBuffer();
//		strTemp.append("ChunTianfw_mobile123456");
		strTemp.append("ChunTianfw_mobile@SDF!TD#DF#*CB$GER@");
		strTemp.append(api);
		strTemp.append(ts);
		Md5 md = new Md5();
		String result = md.getMD5Str(strTemp.toString());
		md = null;
        strTemp = null;
		values[10] = result;
	}
	
	private String getHttpAddress(String user_id, String code, String date, String status, String offset1, String limit1){
		StringBuffer result = new StringBuffer();
//		result.append("http://192.168.1.254:802/?");
		setKeysAndValues(user_id, code, date, status, offset1, limit1);//"", "0", "1", "", "", "%2A"
		result.append(HttpAddressParam.getHttpAddress(keys, values));
		Log.i(TAG, result.toString());
		return result.toString();
	}
	
	private String result = "";

	public String getListJsonString(String user_id, String code, String date,
			String status, String offset1, String limit1) throws IOException, TimeOutEx {
		HttpGetConn conn = new HttpGetConn(getHttpAddress(user_id, code, date,
				status, offset1, limit1));
		result = conn.getJsonResult();
		return result;
	}
	
	public String getHttpResponse(String user_id, String code, String date, String status
			, String offset1, String limit1, String token) throws IOException, TimeOutEx{
		HttpGetConn conn = new HttpGetConn(new JNICallBack().getHttp4GetOrder(user_id, code, date, status, offset1, limit1, token),true);
		return result = conn.getJsonResult();
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
//				bar.setMessage("���ڲ�ѯ");
//				bar.show();
//			}
//			
//			private ProgressDialog bar = new ProgressDialog(context);
//		});
//		return result;
//	}
//	
	
}
