package com.yidejia.app.mall.address;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.util.UnicodeToString;


public class ParseAddressJson {

	private String TAG = getClass().getName();
	private ArrayList<ModelAddresses> addressesArray;
	private UnicodeToString unicode;
	
	public ParseAddressJson() {
		unicode = new UnicodeToString();
	}
	
	/**
	 * 解析服务器返回获取用户收货地址数据
	 * @param httpresp 服务器返回的数据
	 * @return 成功与否
	 */
	public boolean parseAddressListJson(String httpresp){
		JSONObject httpResultObject;
		try {
			httpResultObject = new JSONObject(httpresp);
			int code = httpResultObject.getInt("code");
			if(code == 1){
				
				addressesArray = new ArrayList<ModelAddresses>();
				
				String responseString = httpResultObject.optString("response");
				JSONArray responseArray = new JSONArray(responseString);
				int length = responseArray.length();
				JSONObject addressItem ;
				addressesArray.clear();
				ModelAddresses addresses = null;
				for (int i = 0; i < length; i++) {
					addresses = new ModelAddresses();
					addressItem = responseArray.optJSONObject(i);
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
				return false;
			}
			return true;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<ModelAddresses> getAddresses(){
		return addressesArray;
	}
}
