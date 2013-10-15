package com.yidejia.app.mall.net.favorite;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.conn.ConnectTimeoutException;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpAddressParam;
import com.yidejia.app.mall.net.HttpGetConn;
import com.yidejia.app.mall.net.HttpPostConn;
import com.yidejia.app.mall.util.Md5;
/**
 * 保存（新建,更新）收藏
 * @author long bin
 *
 */
public class CheckExistsFavorite {
	private String[] keys = new String[7];
	private String[] values = new String[7];
	private String TAG = "CheckExistsFavorite";
	private Context context;
	private Map<String, String> httpAddressMap = new ConcurrentHashMap<String, String>();
	
	public CheckExistsFavorite(Context context){
		this.context = context;
	}
	
	private void setKeysAndValues(String userid, String goodsid){
		keys[0] = "api";
		String api = "product.favoliten.checkExists";
		values[0] = api;
		keys[1] = "userId";
		values[1] = userid;
		keys[2] = "goods_id";
		values[2] = goodsid;
		
		keys[3] = "key";
//		values[3] = "fw_test";
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
	public String httpResponse(String userid, String goodsid) throws IOException{
//		getHttpAddress(userid, goodsid);
//		HttpGetConn conn = new HttpGetConn(getHttpAddress(userid, goodsid));
//		HttpPostConn conn = new HttpPostConn(keys, values);
//		result = conn.getJsonResult();
		String param = JNICallBack.getHttp4CheckFav(userid, goodsid);
		Log.i(TAG, param.toString());
		HttpPostConn conn = new HttpPostConn(param);
		result = conn.getHttpResponse();
		Log.i(TAG, result.toString());
		return result;
	}
	
}
