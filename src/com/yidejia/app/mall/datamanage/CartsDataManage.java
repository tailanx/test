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

import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.model.Cart;
/**
 * 购物车模块
 * <p>
 *  添加到购物车{@link #addCart( Cart)}
 * <p>
 *  删除单个购物车商品{@link #delCart( String)}
 * <p>
 *  修改购物车商品数量{@link #mdfCartAmount( String, int)}
 * <p>
 *  获取购物车列表{@link #getCartsArray()}
 * <p>
 *  清空购物车列表{@link #cleanCart()}
 * <p>
 *  根据商品id获取该商品在购物车的数量{@link #getGoodsCount(String)}
 *  <p>
 *  获取购物车内商品总数{@link #getCartAmount()}
 * @author long bin
 *
 */
public class CartsDataManage {
	
//	private ArrayList<Cart> cartsArray; 
	private SharedPreferences sp;
	
	private String TAG = CartsDataManage.class.getName();
	
	public CartsDataManage(){
		try {
			sp = MyApplication.getInstance().getSharedPreferences("Cart", Activity.MODE_APPEND );
//			cartsArray = new ArrayList<Cart>();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加到购物车 ,如果该商品已存在购物车，则追加商品数量
	 * @param details 商品详情数据
	 * @return boolean 返回"true" : 成功 "false" : 失败
	 */
	public boolean addCart(Cart details) {
		boolean flag = false;
		String id = details.getUId();
		int amount = getGoodsCount(id);
		Log.i(TAG, "this goods "+ id +" has :"+amount);
		Editor editor = sp.edit();
		int addCartAmount = amount + details.getAmount();
		if(amount != 0){
			mdfCartAmount(id, addCartAmount);
		}
		try {
			JSONStringer stringer = new JSONStringer(); 
			String cartValues = addCartAmount + ":#"+stringer.object()//.key("goods_id").value(details.getUId())
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
			Log.e(TAG, "addCart Json err");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "addCart err");
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 删除单个购物车商品
	 * @param uId 产品Id
	 * @return boolean 返回"true" : 成功 "false" : 失败
	 */
	public boolean delCart(String uId){
		boolean flag = false;
		try {
			Editor editor = sp.edit();
			editor.remove(uId);
			editor.commit();
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "delCart err");
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 修改购物车单个产品购买数量
	 * @param uId 产品Id
	 * @param amount 购买数量
	 * @return boolean 返回"true" : 成功     "false" : 失败
	 */
	public boolean mdfCartAmount(String uId, int amount){
		boolean flag = false;
		try {
			Editor editor = sp.edit();
			String cartDetails = sp.getString(uId, "0:#");
			String cartJsonString = cartDetails.split(":#")[1];
			editor.putString(uId, amount+":#"+cartJsonString);
			editor.commit();
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "mdfCartAmount err");
			e.printStackTrace();
			
		}
		return flag;
	}
	/**
	 * 获取购物车列表
	 * @return cartsArray 购物车产品列表
	 */
	public ArrayList<Cart> getCartsArray(){
		ArrayList<Cart> cartsArray = new ArrayList<Cart>();
		try {
			Map<String, ?>spMap = sp.getAll();
			if(!spMap.isEmpty()){
				Cart cart = null;
				for (Entry<String, ?> entry : spMap.entrySet()) {
					String key = entry.getKey();
					cart = new Cart();
					cart.setUId(key);
					String value = (String) entry.getValue();
					String amount = value.split(":#")[0];
					String details = value.split(":#")[1];
					try {
						cart.setSalledAmmount(Integer.parseInt(amount));
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
		
		return cartsArray;
	}
	/**
	 * 清空购物车
	 * @return boolean 成功与否 。返回"true" : 成功 "false" : 失败
	 */
	public boolean cleanCart(){
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
	 * 根据商品id获取该商品在购物车的数量
	 * @param uId 商品id
	 * @return count 商品数量
	 */
	public int getGoodsCount(String uId){
		String cartDetails = sp.getString(uId, "0:#");
		String amountString = cartDetails.split(":#")[0];
		try {
			int amount = Integer.parseInt(amountString);
			return amount;
		} catch (NumberFormatException e) {
			// TODO: handle exception
			Log.e(TAG, "NumberFormatException");
		}
		return 0;
	}
	/**
	 * 获取购物车内商品总数
	 * @return amount 
	 */
	public int getCartAmount(){
		int amount = 0 ;
		try {
			Map<String, ?>spMap = sp.getAll();
			if(!spMap.isEmpty()){
				for (Entry<String, ?> entry : spMap.entrySet()) {
					String value = (String) entry.getValue();
					String amountString = value.split(":#")[0];
					try {
						amount = amount + Integer.parseInt(amountString);
					} catch (NumberFormatException  e) {
						// TODO: handle exception
						Log.e(TAG, "get cart array NumberFormatException ");
						e.printStackTrace();
					} catch (Exception e) {
						// TODO: handle exception
						Log.e(TAG, "get cart array json err ");
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "get cart array err ");
			e.printStackTrace();
		}
		Log.i(TAG, ""+amount);
		return amount;
	}
	
	/**
	 * 获取购物车内商品种类的数量
	 * @return
	 */
	public int getTypeCount(){
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
	
}
