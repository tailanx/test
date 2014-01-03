package com.yidejia.app.mall.net.address;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpAddressParam;
import com.yidejia.app.mall.net.HttpPostConn;
import com.yidejia.app.mall.util.Md5;
/**
 * 删除收件人
 * @author long bin
 *
 */
public class DeleteUserAddress {
	private String[] keys = new String[7];
	private String[] values = new String[7];
	private String TAG = "DeleteUserAddress";
	private Context context;
	private Map<String, String> httpAddressMap = new ConcurrentHashMap<String, String>();
	
	public DeleteUserAddress(Context context){
		this.context = context;
	}
	/**
	 * 根据cid 和 aid 删除收件人地址
	 * @param cid 客户id
	 * @param aid 收件人id
	 */
	private void setKeysAndValues(String cid, String aid){
		keys[0] = "api";
		String api = "ucenter.address.delete";
		values[0] = api;
		keys[1] = "cid";
		values[1] = cid;
		keys[2] = "aid";
		values[2] = aid;
		
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
	/**
	 * 根据cid 和 aid 删除收件人地址
	 * @param cid 客户id
	 * @param aid 收件人id
	 */
	public String getHttpAddress(String cid, String aid){
		StringBuffer result = new StringBuffer();
//		result.append("http://192.168.1.254:802/?");
		setKeysAndValues(cid, aid);
		result.append(HttpAddressParam.getHttpAddress(keys, values));
		Log.i(TAG, result.toString());
		for (int j = 0; j < keys.length; j++) {
			httpAddressMap.put(keys[j], values[j]);
		}
		return result.toString();
	}
	
	public Map<String, String> getHttpAddressMap(){
		return httpAddressMap;
	}
	
	private String result = "";
	private String urlString = "http://192.168.1.254:802/";
	public String deleteAddress(String cid, String aid) throws IOException{
		getHttpAddress(cid, aid);
		HttpPostConn conn = new HttpPostConn(keys, values);
		result = conn.getJsonResult();
		return result;
	}
	/**
	 * jni方式删除地址
	 * @param cid
	 * @param aid
	 * @param token
	 * @return
	 * @throws IOException
	 * @throws TimeOutEx 
	 */
	public String deleteAddress(String cid, String aid, String token) throws IOException, TimeOutEx{
		HttpPostConn conn = new HttpPostConn(new JNICallBack().getHttp4DelAddress(cid, aid, token));
		return result = conn.getHttpResponse();
	}
	
	/**
	 * 解析删除数据
	 * @param resultJson http 返回的数据
	 * @return
	 */
	public boolean analysicDeleteJson(String resultJson) {
		if ("".equals(resultJson))
			return false;

		JSONObject httpResultObject;
		try {
			httpResultObject = new JSONObject(resultJson);
			int code = httpResultObject.getInt("code");
			if (code == 1)
				return true;
			else return false;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "delete address analysis json jsonexception error");
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "delete address analysis json exception error");
			return false;
		}
	}
	
}
