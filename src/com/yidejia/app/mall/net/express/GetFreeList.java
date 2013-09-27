package com.yidejia.app.mall.net.express;

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
 * 获取配送中心
 * @author long bin
 *
 */
public class GetFreeList {
	private String[] keys = new String[11];
	private String[] values = new String[11];
	private String TAG = GetFreeList.class.getName();
	private Context context;
	
	public GetFreeList(Context context){
		this.context = context;
	}
	
	private void setKeysAndValues(String where, String offset, String limit, String group, String order, String fields){
		keys[0] = "api";
		String api = "ship.free.getList";
		values[0] = api;
		keys[1] = "where";
		values[1] = where;
		keys[2] = "option%5Boffset%5D";
		values[2] = offset;
		keys[3] = "option%5Blimit%5D";
		values[3] = limit;
		keys[4] = "option%5Bgroup%5D";
		values[4] = group;
		keys[5] = "option%5Border%5D";
		values[5] = order;
		keys[6] = "fields";
		values[6] = fields;
		keys[7] = "key";
		values[7] = "fw_mobile";
		keys[8] = "format";
		values[8] = "array";
		keys[9] = "ts";
		long time = System.currentTimeMillis();
		String ts = String.valueOf(time/1000);
		values[9] = ts;
		
		keys[10] = "sign";
		StringBuffer strTemp = new StringBuffer();
		strTemp.append("ChunTianfw_mobile123456");
		strTemp.append(api);
		strTemp.append(ts);
		Md5 md = new Md5();
		String result = md.getMD5Str(strTemp.toString());
		md = null;
        strTemp = null;
		values[10] = result;
	}
	
	private String getHttpAddress(String where, String offset, String limit, String group, String order, String fields){
		StringBuffer result = new StringBuffer();
		result.append("http://192.168.1.254:802/?");
		setKeysAndValues(where, offset, limit, group, order, fields);//"", "0", "1", "", "", "%2A"
		result.append(HttpAddressParam.getHttpAddress(keys, values));
		Log.i(TAG, result.toString());
		return result.toString();
	}
	
	private String result = "";
	public String getListJsonString(String where, String offset, String limit, String group, String order, String fields)throws IOException{
		HttpGetConn conn = new HttpGetConn(getHttpAddress(where, offset, limit, group, order, fields));
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
