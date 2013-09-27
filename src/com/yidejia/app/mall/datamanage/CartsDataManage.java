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
 * ���ﳵģ��
 * <p>
 *  ��ӵ����ﳵ{@link #addCart( Cart)}
 * <p>
 *  ɾ���������ﳵ��Ʒ{@link #delCart( String)}
 * <p>
 *  �޸Ĺ��ﳵ��Ʒ����{@link #mdfCartAmount( String, int)}
 * <p>
 *  ��ȡ���ﳵ�б�{@link #getCartsArray()}
 * <p>
 *  ��չ��ﳵ�б�{@link #cleanCart()}
 * <p>
 *  ������Ʒid��ȡ����Ʒ�ڹ��ﳵ������{@link #getGoodsCount(String)}
 *  <p>
 *  ��ȡ���ﳵ����Ʒ����{@link #getCartAmount()}
 * @author long bin
 *
 */
public class CartsDataManage {
	
	private ArrayList<Cart> cartsArray; 
	private SharedPreferences sp;
	
	private String TAG = CartsDataManage.class.getName();
	
	public CartsDataManage(){
		sp = MainFragmentActivity.MAINACTIVITY.getSharedPreferences("Cart", Activity.MODE_APPEND );
		cartsArray = new ArrayList<Cart>();
	}
	
	/**
	 * ��ӵ����ﳵ ,�������Ʒ�Ѵ��ڹ��ﳵ����׷����Ʒ����
	 * @param details ��Ʒ��������
	 * @return boolean ����"true" : �ɹ� "false" : ʧ��
	 */
	public boolean addCart(Cart details) {
		boolean flag = false;
		String id = details.getUId();
		int amount = getGoodsCount(id);
		Editor editor = sp.edit();
		if(amount != 0){
			mdfCartAmount(id, amount + details.getAmount());
		}
		try {
			JSONStringer stringer = new JSONStringer(); 
			String cartValues = details.getAmount()+":#"+stringer.object()//.key("goods_id").value(details.getUId())
					//.key("amount").value(details.getAmount())
					.key("name").value(details.getProductText())
					.key("price").value(details.getPrice())
					.key("img_url").value(details.getImgUrl()).endObject().toString();
			editor.putString(id, cartValues);
			editor.commit();
			flag = true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "delCart Json err");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "addCart err");
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * ɾ���������ﳵ��Ʒ
	 * @param uId ��ƷId
	 * @return boolean ����"true" : �ɹ� "false" : ʧ��
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
	 * �޸Ĺ��ﳵ������Ʒ��������
	 * @param uId ��ƷId
	 * @param amount ��������
	 * @return boolean ����"true" : �ɹ�     "false" : ʧ��
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
	 * ��ȡ���ﳵ�б�
	 * @return cartsArray ���ﳵ��Ʒ�б�
	 */
	public ArrayList<Cart> getCartsArray(){
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
	 * ��չ��ﳵ
	 * @return boolean �ɹ���� ������"true" : �ɹ� "false" : ʧ��
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
	 * ������Ʒid��ȡ����Ʒ�ڹ��ﳵ������
	 * @param uId ��Ʒid
	 * @return count ��Ʒ����
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
	 * ��ȡ���ﳵ����Ʒ����
	 * @return amount ��Ʒ����
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
		return amount;
	}
	
}
