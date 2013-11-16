package com.yidejia.app.mall.datamanage;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.yidejia.app.mall.MainFragmentActivity;
import com.yidejia.app.mall.model.Cart;
/**
 * 浏览历史模块
 * <p>
 *  添加到浏览历史{@link #addHistory( Cart)}
 * <p>
 *  删除单个浏览历史商品{@link #delHistory( String)}
 * <p>
 *  获取浏览历史列表{@link #getHistoryArray()}
 * <p>
 *  清空浏览历史列表{@link #cleanHistory()}
 *  <p>
 *  获取浏览历史内商品总数{@link #getCartAmount()}
 * @author long bin
 *
 */
public class BrowseHistoryDataManage {
	
	private ArrayList<Cart> historyArray; 
	private SharedPreferences sp;
	
	private String TAG = BrowseHistoryDataManage.class.getName();
	
	public BrowseHistoryDataManage(){
		sp = MainFragmentActivity.MAINACTIVITY.getSharedPreferences("BrowseHistory", Activity.MODE_APPEND );
		historyArray = new ArrayList<Cart>();
	}
	
	/**
	 * 添加到浏览历史 ,如果该商品已存在浏览历史，则追加商品数量
	 * @param details 商品详情数据
	 * @return boolean 返回"true" : 成功 "false" : 失败
	 */
	public boolean addHistory(Cart details) {
		boolean flag = false;
		String id = details.getUId();
		boolean isExsist = checkIsExsist(id);
		if(!isExsist){//如果浏览历史不存在改商品
			checkAndDel();//检查是否有20个商品，并删除最早加入的商品
		}
		Editor editor = sp.edit();
		try {
			JSONStringer stringer = new JSONStringer(); 
			String cartValues = stringer.object()//.key("goods_id").value(details.getUId())
					//.key("amount").value(details.getAmount())
					.key("name").value(details.getProductText())
					.key("price").value(details.getPrice())
					.key("img_url").value(details.getImgUrl()).endObject().toString();
			Log.i(TAG, cartValues);
			editor.putString(id, cartValues);
			editor.commit();
			flag = true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "addHistory Json err");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "addHistory err");
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 删除单个浏览历史商品
	 * @param uId 产品Id
	 * @return boolean 返回"true" : 成功 "false" : 失败
	 */
	public boolean delHistory(String uId){
		boolean flag = false;
		try {
			Editor editor = sp.edit();
			editor.remove(uId);
			editor.commit();
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "delHistory err");
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 获取浏览历史列表
	 * @return cartsArray 浏览历史产品列表
	 */
	public ArrayList<Cart> getHistoryArray(){
		ArrayList<Cart> cartsArray = new ArrayList<Cart>();
		try {
			Map<String, ?>spMap = sp.getAll();
			if(!spMap.isEmpty()){
				Cart cart = null;
				for (Entry<String, ?> entry : spMap.entrySet()) {
					String key = entry.getKey();
					cart = new Cart();
					cart.setUId(key);
					String details = (String) entry.getValue();
//					String amount = value.split(":#")[0];
//					String details = value.split(":#")[1];
					try {
						cart.setSalledAmmount(1);
						JSONObject jsonObject = new JSONObject(details);
						cart.setImgUrl(jsonObject.getString("img_url"));
						cart.setPrice(Float.parseFloat(jsonObject.getString("price")));
						cart.setProductText(jsonObject.getString("name"));
					} catch (NumberFormatException  e) {
						// TODO: handle exception
						Log.e(TAG, "get cart array NumberFormatException ");
						e.printStackTrace();
					} catch (Exception e) {
						// TODO: handle exception
						Log.e(TAG, "get cart array json err ");
						e.printStackTrace();
					}
					cartsArray.add(cart);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "get cart array err ");
			e.printStackTrace();
		}
		int length = cartsArray.size();
		for (int i = 0; i < length; i++) {
			historyArray.add(cartsArray.get(length - i - 1));
		}
		return historyArray;
	}
	/**
	 * 清空浏览历史
	 * @return boolean 成功与否 。返回"true" : 成功 "false" : 失败
	 */
	public boolean cleanHistory(){
		try {
			Editor editor = sp.edit();
			editor.clear();
			editor.commit();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
	
	/**
	 * 获取浏览历史内商品的数量
	 * @return
	 */
	public int getCartAmount(){
		int count = 0;
		try {
			Map<String, ?>spMap = sp.getAll();
			count = spMap.size();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "get cart type count err");
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 检查uid的商品是否存在浏览历史中,在的话删除该商品
	 * @param uid
	 * @return true 在，false 不在
	 */
	private boolean checkIsExsist(String uid){
		boolean issuccess = false;
		issuccess = sp.contains(uid);
		delHistory(uid);
		return issuccess;
	}
	
	/**
	 * 检查浏览历史是否小于20个，超过就删除最早加入的数据
	 * @return
	 */
	private boolean checkAndDel(){
		int count = getCartAmount();
		if(count < 20)return true;
		ArrayList<Cart> carts = getHistoryArray();
		count = carts.size();
		for (int i = 19; i < count; i++) {//从最早加入的20个开始删除
			String id = carts.get(i).getUId();
			delHistory(id);
		}
		return false;
	}
	
}
