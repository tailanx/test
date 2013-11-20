package com.yidejia.app.mall.datamanage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.address.DeleteUserAddress;
import com.yidejia.app.mall.net.address.GetUserAddressList;
import com.yidejia.app.mall.net.address.SaveUserAddress;
import com.yidejia.app.mall.net.address.SetDefAddr;
import com.yidejia.app.mall.util.UnicodeToString;

public class AddressDataManage {
	
	private ArrayList<Addresses> addressesArray;
	private Context context;
	private String TAG = "AddressDataManage";
	
	private boolean isNoMore = false;//判断是否还有更多数据,true为没有更多了
	
//	private int defaultUserId = 68298;
//	private int fromIndex = 0;
//	private int acount = 10;
	private UnicodeToString unicode;
	
	public AddressDataManage(Context context){
		this.context = context;
		unicode = new UnicodeToString();
		addressesArray = new ArrayList<Addresses>();
	}
	
	/**添加收货地址
	 *@param userId 用户id
	 *@param name 用户名称
	 *@param province 省份
	 *@param city 城市
	 *@param area 地区
	 *@param address 详细地址
	 *@param phone 手机
	 *@param token token
	 *@param defaultAddress 是否为默认地址
	 *@return: 返回addressId;可能为""
	 */
	public String addAddress(String userId, String name, String province, String city, String area, String address, String phone, boolean defaultAddress, String token){
		String addressId = "";
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return addressId;
		}
		boolean isSuccess = false;
		TaskSave taskSave = new TaskSave(userId, name, province, city, area, address, phone, defaultAddress, token);
		try {
			isSuccess = taskSave.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "addAddress() InterruptedException");
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "addAddress() ExecutionException");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if(!isSuccess && !isSaveSuccess){
			Toast.makeText(context, isSuccessString, Toast.LENGTH_SHORT).show();
		} else if(!isSuccess){
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		} else {
			return recipient_id;
		}
		
		return addressId;
	}
	
	/**更新收货地址
	 *@param userId 用户id
	 *@param name 用户名称
	 *@param province 省份
	 *@param city 城市
	 *@param area 地区
	 *@param address 详细地址
	 *@param phone 手机
	 *@param token
	 *@param defaultAddress 是否为默认地址
	 *@param recipientId 需要更新地址的id
	 *@return: 是否更新成功
	 */
	public boolean updateAddress(String userId, String name, String province,
			String city, String area, String address, String phone, boolean defaultAddress, String recipientId,
			String token) {
	boolean isSuccess = false;
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return isSuccess;
		}
		this.recipient_id = recipientId;
		TaskSave taskSave = new TaskSave(userId, name, province, city, area, address, phone, defaultAddress, token);
		try {
			isSuccess = taskSave.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "updateAddress() InterruptedException");
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "updateAddress() ExecutionException");
		}
		
		if(!isSuccess && !isSaveSuccess){
			Toast.makeText(context, isSuccessString, Toast.LENGTH_SHORT).show();
		} else if(!isSuccess){
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		}
		
		return isSuccess;
	}
	
	/**删除收货地址
	 * @param userId 客户id
	 *@param addressId 地址id
	 *@param token
	 *@return: 是否删除成功
	 */
	public boolean deleteAddress(String userId, String addressId, String token){
		boolean isSuccess = false;
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return isSuccess;
		}
//		String resultJson = deleteAddressJson(userId, addressId);
		TaskDelete taskDelete = new TaskDelete(userId, addressId, token);
		try {
			isSuccess = taskDelete.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "TaskDelete() InterruptedException");
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "TaskDelete() ExecutionException");
			e.printStackTrace();
		}
		
		if(!isSuccess){
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		}
		
//		isSuccess = !isSuccess;
		return isSuccess;
	}
	/**
	 * 根据用户id获取所有地址
	 * @param userId 用户id
	 * @param fromIndex 第几个地址开始获取数据
	 * @param acount 获取个数
	 * @return
	 */
	public ArrayList<Addresses> getAddressesArray(String userId, int fromIndex, int acount){
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return addressesArray;
		}
		TaskGetList taskGetList = new TaskGetList("customer_id%3D"+userId+"+and+valid_flag%3D%27y%27", String.valueOf(fromIndex), String.valueOf(acount), "", "", "");
		boolean state = false ;
		try {
			state = taskGetList.execute().get();
			if(isNoMore){
				Toast.makeText(context, context.getResources().getString(R.string.nomore), Toast.LENGTH_SHORT).show();
				isNoMore = false;
				state = true;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			Log.e(TAG, "TaskGetList() InterruptedException");
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "TaskGetList() ExecutionException");
		}
		if(!state){
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		}
		
		return addressesArray;
	}
	
	
	/**
	 * 根据用户id获取默认地址
	 * @param userId 用户id
	 * @return
	 */
	public ArrayList<Addresses> getDefAddresses(String userId){
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return addressesArray;
		}
		TaskGetList taskGetList = new TaskGetList("customer_id%3D"+userId+"+and+is_default%3D%27y%27+and+valid_flag%3D%27y%27", String.valueOf(0), String.valueOf(10), "", "", "");
		boolean state = false ;
		try {
			state = taskGetList.execute().get();
			if(isNoMore){
				Toast.makeText(context, context.getResources().getString(R.string.nomore), Toast.LENGTH_SHORT).show();
				isNoMore = false;
				state = true;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			Log.e(TAG, "TaskGetList() InterruptedException");
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "TaskGetList() ExecutionException");
		}
		if(!state){
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		}
		
		return addressesArray;
	}
	/**
	 * 设置默认地址
	 * @param cid
	 * @param aid
	 * @param token
	 * @return
	 */
	public boolean setDefaultAddress(String cid, String aid, String token){
		boolean isSuccess = false;
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return isSuccess;
		}
//		String resultJson = deleteAddressJson(userId, addressId);
		TaskSetDef taskDelete = new TaskSetDef(cid, aid, token);
		try {
			isSuccess = taskDelete.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "TaskDelete() InterruptedException");
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "TaskDelete() ExecutionException");
			e.printStackTrace();
		}
		
		if(!isSuccess){
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		}
		
//		isSuccess = !isSuccess;
		return isSuccess;
	}
	
//	private String result = "";
	
	/**
	 * 解析所有地址数据
	 * @param httpResultString
	 * @return
	 */
	public ArrayList<Addresses> analysisGetListJson(String httpResultString){
		JSONObject httpResultObject;
		try {
			httpResultObject = new JSONObject(httpResultString);
			int code = httpResultObject.getInt("code");
			Log.i(TAG, "code"+code);
			if(code == 1){
				String responseString = httpResultObject.getString("response");
				JSONArray responseArray = new JSONArray(responseString);
				int length = responseArray.length();
				JSONObject addressItem ;
				for (int i = 0; i < length; i++) {
					Addresses addresses = new Addresses();
					addressItem = responseArray.getJSONObject(i);
//					if("n".equals(addressItem.getString("valid_flag"))) continue;
					String recipient_id = addressItem.getString("recipient_id");
					addresses.setAddressId(recipient_id);
					String name = addressItem.getString("customer_name");
					addresses.setName(unicode.revert(name));
					String handset = addressItem.getString("handset");
					addresses.setHandset(handset);
					String phone = addressItem.getString("telephone");
					addresses.setPhone(phone);
					String province = addressItem.getString("province");
					addresses.setProvince(unicode.revert(province));
					String city = addressItem.getString("city");
					addresses.setCity(unicode.revert(city));
					String area = addressItem.getString("district");
					addresses.setArea(unicode.revert(area));
					String maddress = addressItem.getString("address");
					addresses.setAddress(unicode.revert(maddress));
					String isDefault = addressItem.getString("is_default");
					boolean isDef = isDefault.equals("y") ? true : false;
					addresses.setDefaultAddress(isDef);
					addressesArray.add(addresses);
				}
			} else if(code == -1){
				isNoMore = true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return addressesArray;
	}
	
	
	private class TaskGetList extends AsyncTask<Void, Void, Boolean>{
		String where;
		String offset;
		String limit;
		String group;
		String order;
		String fields;
		public TaskGetList(String where, String offset, String limit, String group, String order, String fields){
			this.where = where;
			this.offset = offset;
			this.limit = limit;
			this.group = group;
			this.order = order;
			this.fields = fields;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			bar.setMessage(context.getResources().getString(R.string.searching));
//			bar.show();
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			GetUserAddressList getList = new GetUserAddressList(context);
			try {
				String httpResultString = getList.getAddressListJsonString(where, offset, limit, group, order, fields);
				analysisGetListJson(httpResultString);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "task getlist ioex");
				e.printStackTrace();
				return false;
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "task getlist other ex");
				e.printStackTrace();
				return false;
			}
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//			bar.dismiss();
//			if(result)
//				Toast.makeText(context, "�ɹ�", Toast.LENGTH_SHORT).show();
		}
//		private ProgressDialog bar = new ProgressDialog(context);
	}
	
	
	private class TaskDelete extends AsyncTask<Void, Void, Boolean>{
		String userId;
		String addressId;
		String token;
		public TaskDelete(String userId, String addressId, String token){
			this.userId = userId;
			this.addressId = addressId;
			this.token = token;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			bar.setMessage(context.getResources().getString(R.string.searching));
//			bar.show();
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			DeleteUserAddress deleteAddress = new DeleteUserAddress(context);
			try {
				String httpResultString = deleteAddress.deleteAddress(String.valueOf(userId), String.valueOf(addressId), token);
				
				return analysicDeleteJson(httpResultString);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, "task delete ioex");
				return false;
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "task delete other ex");
				return false;
			}
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//			bar.dismiss();
//			if(result)
//				Toast.makeText(context, "�ɹ�", Toast.LENGTH_SHORT).show();
		}
//		private ProgressDialog bar = new ProgressDialog(context);
	}
	
	private class TaskSave extends AsyncTask<Void, Void, Boolean>{
		private String userId;
		private String name;
		private String province;
		private String city;
		private String area;
		private String address;
		private String phone; 
		private String token;
		private boolean defaultAddress;
		
		public TaskSave(String userId, String name, String province, String city, String area, String address, String phone, boolean defaultAddress, String token){
			this.userId = userId;
			this.name = name;
			this.province = province;
			this.city = city;
			this.area = area;
			this.address = address;
			this.phone = phone;
			this.token = token;
			this.defaultAddress = defaultAddress;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			bar.setMessage(context.getResources().getString(R.string.searching));
//			bar.show();
			if(defaultAddress);
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			SaveUserAddress saveUserAddress = new SaveUserAddress(context);
			try {
				String httpResultString = saveUserAddress.saveAddress(String.valueOf(userId), name, phone, province, city, area, address, String.valueOf(recipient_id), token);
				
				return analysicSaveJson(httpResultString);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "task save ioex");
				e.printStackTrace();
				return false;
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "task save other ex");
				e.printStackTrace();
				return false;
			}
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//			bar.dismiss();
//			if(result)
//				Toast.makeText(context, "�ɹ�", Toast.LENGTH_SHORT).show();
		}
//		private ProgressDialog bar = new ProgressDialog(context);
	}
	
	private class TaskSetDef extends AsyncTask<Void, Void, Boolean>{
		private String cid;
		private String aid;
		private String token;
		public TaskSetDef(String cid, String aid, String token){
			this.cid = cid;
			this.aid = aid;
			this.token = token;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			bar.setMessage(context.getResources().getString(R.string.searching));
//			bar.show();
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
//			DeleteUserAddress deleteAddress = new DeleteUserAddress(context);
			SetDefAddr setDefAddr = new SetDefAddr();
			try {
				String httpResultString = setDefAddr.getHttpResponse(cid, aid, token);
				
				return analysicSetDefJson(httpResultString);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, "task delete ioex");
				return false;
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "task delete other ex");
				return false;
			}
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//			bar.dismiss();
//			if(result)
//				Toast.makeText(context, "�ɹ�", Toast.LENGTH_SHORT).show();
		}
//		private ProgressDialog bar = new ProgressDialog(context);
	}
	
	/**
	 * 解析删除数据
	 * @param resultJson http 返回的数据
	 * @return
	 */
	private boolean analysicDeleteJson(String resultJson) {
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
	
	private boolean analysicSetDefJson(String resultJson) {
		if ("".equals(resultJson) || resultJson == null)
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
	
	private String recipient_id = "0";
	/**
	 * 解析添加或修改地址数据
	 * @param resultJson http返回的数据
	 * @return
	 */
	private boolean analysicSaveJson(String resultJson) {
		if ("".equals(resultJson))
			return false;
		
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
					isSaveSuccess = true;
					return true;
				}
				else {
					isSaveSuccess = false;
					return false;
				}
			} else {
				return false;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "save address analysis json jsonexception error");
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "save address analysis json exception error");
			return false;
		}
	}
	
	private boolean isSaveSuccess = true;
	
	private String isSuccessString = "";
	public String getReason(){
		return isSuccessString;
	}
	/**
	 * 返回地址的id
	 */
	public String getAddressId(){
		return recipient_id;
	}
	
}
