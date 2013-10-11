package com.yidejia.app.mall.net.favorite;

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
 * 保存（新建,更新）收藏
 * @author long bin
 *
 */
public class SaveFavorite {
	private String[] keys = new String[7];
	private String[] values = new String[7];
	private String TAG = "SaveFavorite";
	private Context context;
	private Map<String, String> httpAddressMap = new ConcurrentHashMap<String, String>();
	
	public SaveFavorite(Context context){
		this.context = context;
	}
	
	private void setKeysAndValues(String userid, String goodsid){
		keys[0] = "api";
		String api = "product.favoliten.save";
		values[0] = api;
		keys[1] = "userid";
		values[1] = userid;
		keys[2] = "goods_id";
		values[2] = goodsid;
		
		keys[3] = "key";
		values[3] = "fw_mobile";
		keys[4] = "format";
		values[4] = "array";
		keys[5] = "ts";
		long time = System.currentTimeMillis();
		String ts = String.valueOf(time/1000);
		values[5] = ts;
		
		keys[6] = "sign";
		StringBuffer strTemp = new StringBuffer();
//		strTemp.append("ChunTianfw_mobile123456");
		strTemp.append("ChunTianfw_mobile@SDF!TD#DF#*CB$GER@");
		strTemp.append(api);
		strTemp.append(ts);
		Md5 md = new Md5();
		String result = md.getMD5Str(strTemp.toString());
		md = null;
        strTemp = null;
		values[6] = result;
	}
	
	private String getHttpAddress(String userid, String goodsid){
		StringBuffer result = new StringBuffer();
//		result.append("http://192.168.1.254:802/?");
		setKeysAndValues(userid, goodsid);
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
	 * @param userid 用户id
	 * @param goodsid 收藏的商品id
	 * @return http response string
	 * @throws IOException
	 */
	public String saveFavorite(String userid, String goodsid) throws IOException{
		getHttpAddress(userid, goodsid);
		HttpPostConn conn = new HttpPostConn(keys, values);
		result = conn.getJsonResult();
		return result;
	}
	
//	public String saveFavoriteJson(){
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
//	
	
}
