package com.yidejia.app.mall.net.address;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpAddressParam;
import com.yidejia.app.mall.net.HttpPostConn;
import com.yidejia.app.mall.util.Md5;
import com.yidejia.app.mall.util.UnicodeToString;
/**
 * ���棨�½����£��ռ�����Ϣ
 * @author long bin
 *
 */
public class SaveUserAddress {
	private String[] keys = new String[14];
	private String[] values = new String[14];
	private String TAG = "SaveUserAddress";
	private Context context;
	private Map<String, String> httpAddressMap = new ConcurrentHashMap<String, String>();
	private UnicodeToString unicode;
	
	public SaveUserAddress(){
		unicode = new UnicodeToString();
	}
	
	public SaveUserAddress(Context context){
		this();
		this.context = context;
	}
	
	
	private void setKeysAndValues(String customer_id, String customer_name, String handset, String province,
			String city, String district, String address, String recipient_id, String token){
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
		keys[9] = "token";
		values[9] = token;
		
		keys[10] = "key";
		values[10] = "fw_mobile";
//		values[9] = "fw_test";
		keys[11] = "format";
		values[11] = "array";
		keys[12] = "ts";
		long time = System.currentTimeMillis();
		String ts = String.valueOf(time/1000);
		values[12] = ts;
		
		keys[13] = "sign";
		StringBuffer strTemp = new StringBuffer();
		strTemp.append("ChunTianfw_mobile123456");
//		strTemp.append("ChunTianfw_mobile@SDF!TD#DF#*CB$GER@");
		strTemp.append(api);
		strTemp.append(ts);
		Md5 md = new Md5();
		String result = md.getMD5Str(strTemp.toString());
		md = null;
        strTemp = null;
		values[13] = result;
	}
	
	private String getHttpAddress(String customer_id, String customer_name, String handset, String province,
			String city, String district, String address, String recipient_id, String token){
		StringBuffer result = new StringBuffer();
//		result.append("http://192.168.1.254:802/?");
		setKeysAndValues(customer_id, customer_name, handset, province,
				 city, district, address, recipient_id, token);
		result.append(HttpAddressParam.getHttpAddress(keys, values));
		Log.i(TAG, result.toString());
		for (int j = 0; j < keys.length; j++) {
			httpAddressMap.put(keys[j], values[j]);
		}
		return result.toString();
	}
	
	private String result = "";
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
				 city, district, address, recipient_id, "");
		HttpPostConn conn = new HttpPostConn(keys, values);
		result = conn.getJsonResult();
		return result;
	}
	/** 
	 * jni ��ʽ�ύ��ַ
	 * @param customer_id
	 * @param customer_name
	 * @param handset
	 * @param province
	 * @param city
	 * @param district
	 * @param address
	 * @param recipient_id
	 * @param token
	 * @return
	 * @throws IOException
	 * @throws TimeOutEx 
	 */
	public String saveAddress(String customer_id, String customer_name,
			String handset, String province, String city, String district,
			String address, String recipient_id, String token)
			throws IOException, TimeOutEx {
		HttpPostConn conn = new HttpPostConn(new JNICallBack().getHttp4SaveAddress(
				customer_id, new String(customer_name.getBytes("UTF-8"),
						"UTF-8"), handset,
				new String(province.getBytes("UTF-8"), "UTF-8"), new String(
						city.getBytes("UTF-8"), "UTF-8"),
				new String(district.getBytes("UTF-8"), "UTF-8"),	new String(address.getBytes("UTF-8"),"UTF-8"), recipient_id, token));
		result = conn.getHttpResponse();
//		String param = getHttpAddress(customer_id, customer_name, handset, province,
//				 city, district, address, recipient_id, token);
//		HttpPostConn conn = new HttpPostConn(param);
//		result = conn.getHttpResponse();
//		Log.e(TAG, result);
		return result;
	}

	private String isSuccessString = "";
	private String recipient_id = "";
	/**
	 * 解析添加或修改地址数据
	 * @param resultJson http返回的数据
	 * @return
	 */
	public boolean analysicSaveJson(String resultJson) {
		
		if ("".equals(resultJson))
			return false;
		
//		boolean isSaveSuccess = false;
		JSONObject httpResultObject;
		try {
			httpResultObject = new JSONObject(resultJson);
			int code = httpResultObject.getInt("code");
			if (code == 1){
				String response = httpResultObject.getString("response");
				JSONObject responseJsonObject = new JSONObject(response);
				String temp = responseJsonObject.getString("@p_recipient_id");
//				recipient_id = Integer.parseInt(temp);
				recipient_id = temp;
				isSuccessString = responseJsonObject.getString("@p_result");
				if(context.getResources().getString(R.string.success_del).equals(unicode.revert(isSuccessString))){
//					isSaveSuccess = true;
					return true;
				}
				else {
//					isSaveSuccess = false;
					return false;
				}
			} else {
				return false;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "save address analysis json jsonexception error");
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "save address analysis json exception error");
			e.printStackTrace();
			return false;
		}
	}

	public String getRecipient_id() {
		return recipient_id;
	}

	public String getIsSuccessString() {
		return isSuccessString;
	}
	
}
