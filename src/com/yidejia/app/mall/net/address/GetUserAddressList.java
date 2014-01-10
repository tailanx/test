package com.yidejia.app.mall.net.address;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.yidejia.app.mall.address.ModelAddresses;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpAddressParam;
import com.yidejia.app.mall.net.HttpGetConn;
import com.yidejia.app.mall.util.Md5;
import com.yidejia.app.mall.util.UnicodeToString;
/**
 * 获取收件人列表
 * @author long bin
 *
 */
public class GetUserAddressList {
	private String[] keys = new String[11];
	private String[] values = new String[11];
	private String TAG = "GetUserAddressList";
	private Context context;
	
	private boolean isNoMore = false;//判断是否还有更多数据,true为没有更多了
	private ArrayList<ModelAddresses> addressesArray;
	private UnicodeToString unicode;
	
	public GetUserAddressList(Context context){
		this.context = context;
	}
	
	public GetUserAddressList(){
		unicode = new UnicodeToString();
		addressesArray = new ArrayList<ModelAddresses>();
	}
	
	private void setKeysAndValues(String where, String offset, String limit, String group, String order, String fields){
		keys[0] = "api";
		String api = "ucenter.address.getList";
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
	
	private String getHttpAddress(String where, String offset, String limit, String group, String order, String fields){
		StringBuffer result = new StringBuffer();
//		result.append("http://192.168.1.254:802/?");
		setKeysAndValues(where, offset, limit, group, order, fields);//"", "0", "1","","", "%2A"
		result.append(HttpAddressParam.getHttpAddress(keys, values));
		Log.i(TAG, result.toString());
		return result.toString();
	}
	
	private String result = "";

	public String getAddressListJsonString(String where, String offset,
			String limit, String group, String order, String fields)
			throws IOException, TimeOutEx {
		// HttpGetConn conn = new HttpGetConn(getHttpAddress(where, offset,
		// limit, group, order, fields));
		HttpGetConn conn = new HttpGetConn(
				new JNICallBack().getHttp4GetAddress(where, offset, limit, "",
						"", ""), true);
		result = conn.getJsonResult();
		return result;
	}
	
	public String getAddressHttpresp(String where, String offset, String limit)throws IOException, TimeOutEx{
//		HttpGetConn conn = new HttpGetConn(getHttpAddress(where, offset, limit, group, order, fields));
		HttpGetConn conn = new HttpGetConn(new JNICallBack().getHttp4GetAddress(
				where, offset, limit, "", "", ""), true);
		result = conn.getJsonResult();
		return result;
	}

	public boolean analysis(String httpresp){
		JSONObject httpResultObject;
		try {
			httpResultObject = new JSONObject(httpresp);
			int code = httpResultObject.getInt("code");
			Log.i(TAG, "code"+code);
			if(code == 1){
				String responseString = httpResultObject.optString("response");
				JSONArray responseArray = new JSONArray(responseString);
				int length = responseArray.length();
				JSONObject addressItem ;
				addressesArray.clear();
				isNoMore = false;
				for (int i = 0; i < length; i++) {
					ModelAddresses addresses = new ModelAddresses();
					addressItem = responseArray.optJSONObject(i);
//					if("n".equals(addressItem.getString("valid_flag"))) continue;
					String recipient_id = addressItem.optString("recipient_id");
					addresses.setAddressId(recipient_id);
					String name = addressItem.optString("customer_name");
					addresses.setName(unicode.revert(name));
					String handset = addressItem.optString("handset");
					addresses.setHandset(handset);
					String phone = addressItem.optString("telephone");
					addresses.setPhone(phone);
					String province = addressItem.optString("province");
					addresses.setProvince(unicode.revert(province));
					String city = addressItem.optString("city");
					addresses.setCity(unicode.revert(city));
					String area = addressItem.optString("district");
					addresses.setArea(unicode.revert(area));
					String maddress = addressItem.optString("address");
					addresses.setAddress(unicode.revert(maddress));
					String isDefault = addressItem.optString("is_default");
					boolean isDef = "y".equals(isDefault) ? true : false;
					addresses.setDefaultAddress(isDef);
					addressesArray.add(addresses);
				}
			} else if(code == -1){
				isNoMore = true;
			}
			return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<ModelAddresses> getAddresses(){
		return addressesArray;
	}
	
	public boolean getIsNoMore(){
		return isNoMore;
	}
}
