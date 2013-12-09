package com.yidejia.app.mall.datamanage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
//import java.util.Map;



import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.unionpay.mpay.views.u;
import com.yidejia.app.mall.MainFragmentActivity;
import com.yidejia.app.mall.main.HomeMallActivity;
import com.yidejia.app.mall.model.ProductBaseInfo;
/**
 * 浏览历史模块
 * <p>
 *  添加到浏览历史{@link #addHistory( ProductBaseInfo)}
 * <p>
 *  删除单个浏览历史商品{@link #delHistory( String)}
 * <p>
 *  获取浏览历史列表{@link #getHistoryArray()}
 * <p>
 *  清空浏览历史列表{@link #cleanHistory()}
 *  <p>
 *  获取浏览历史内商品总数{@link #getProductBaseInfoAmount()}
 * @author long bin
 *
 */
public class BrowseHistoryDataManage {
	
	private ArrayList<ProductBaseInfo> historyArray; 
	private SharedPreferences sp;
	private ArrayList<String> keysList;
	
	private String TAG = BrowseHistoryDataManage.class.getName();
	
	public BrowseHistoryDataManage(){
		sp = HomeMallActivity.MAINACTIVITY.getSharedPreferences("BrowseHistory", Activity.MODE_APPEND );
		historyArray = new ArrayList<ProductBaseInfo>();
		keysList = new ArrayList<String>();
	}
	
	/**
	 * 添加到浏览历史 ,如果该商品已存在浏览历史，则追加商品数量
	 * @param details 商品详情数据
	 * @return boolean 返回"true" : 成功 "false" : 失败
	 */
	public boolean addHistory(ProductBaseInfo details) {
		boolean flag = false;
		long time = System.currentTimeMillis();
		String id = details.getUId();
		boolean isExsist = checkIsExsist(id);
		if(!isExsist){//如果浏览历史不存在改商品
			checkAndDel();//检查是否有20个商品，并删除最早加入的商品
		}
		Editor editor = sp.edit();
		try {
			
			JSONStringer stringer = new JSONStringer(); 
			String ProductBaseInfoValues = stringer.object()//.key("goods_id").value(details.getUId())
					//.key("amount").value(details.getAmount())
					.key("id").value(id)
					.key("name").value(details.getName())
					.key("price").value(details.getPrice())
					.key("sell_count").value(details.getSalledAmmount())
					.key("comment_sum").value(details.getCommentAmount())
					.key("img_url").value(details.getImgUrl())
					.endObject().toString();
			Log.e(TAG, id+ProductBaseInfoValues);
			editor.putString(time+"", ProductBaseInfoValues);
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
			ArrayList<ProductBaseInfo> baseInfos = getHistoryArray();
			String key = "";
			int length = baseInfos.size();
			for (int i = 0; i < length; i++) {
				if(baseInfos.get(i).getUId().equals(uId)){
					key = keysList.get(i);
					break;
				}
			}
			Editor editor = sp.edit();
			editor.remove(key);
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
	 * @return productBaseInfosArray 浏览历史产品列表
	 */
	public ArrayList<ProductBaseInfo> getHistoryArray(){
		ArrayList<ProductBaseInfo> productBaseInfosArray = new ArrayList<ProductBaseInfo>();
		ArrayList<String> keysArray = new ArrayList<String>();
		try {
			Map<String, ?>spMap = sp.getAll();
			Object[] key =  spMap.keySet().toArray();    
			Arrays.sort(key); 
			ProductBaseInfo productBaseInfo = null;
			for (int i = 0; i < key.length; i++) {
				String details = (String) spMap.get(key[i]);
				keysArray.add((String)key[i]);
				productBaseInfo = new ProductBaseInfo();
				try {
					JSONObject jsonObject = new JSONObject(details);
					String id = jsonObject.getString("id");
					productBaseInfo.setUId(id);
					productBaseInfo.setSalledAmount(jsonObject.getString("sell_count"));
					productBaseInfo.setImgUrl(jsonObject.getString("img_url"));
					productBaseInfo.setPrice(Float.parseFloat(jsonObject.getString("price"))+"");
					productBaseInfo.setBrief(jsonObject.getString("name"));
					productBaseInfo.setCommentAmount(jsonObject.getString("comment_sum"));
//					Log.e(TAG, "ts:"+key+" and id"+id);
					productBaseInfosArray.add(productBaseInfo);
				} catch (NumberFormatException  e) {
					// TODO: handle exception
					Log.e(TAG, "get ProductBaseInfo array NumberFormatException ");
					e.printStackTrace();
				} catch (Exception e) {
					// TODO: handle exception
					Log.e(TAG, "get ProductBaseInfo array json err ");
					e.printStackTrace();
				}
			
			} 
			
			/*if(!spMap.isEmpty()){
				ProductBaseInfo ProductBaseInfo = null;
				for (Entry<String, ?> entry : spMap.entrySet()) {
					String key = entry.getKey();
					ProductBaseInfo = new ProductBaseInfo();
					ProductBaseInfo.setUId(key);
					String details = (String) entry.getValue();
//					String amount = value.split(":#")[0];
//					String details = value.split(":#")[1];
					Log.e(TAG, key);
					try {
						ProductBaseInfo.setSalledAmount(1+"");
						JSONObject jsonObject = new JSONObject(details);
						ProductBaseInfo.setImgUrl(jsonObject.getString("img_url"));
						ProductBaseInfo.setPrice(Float.parseFloat(jsonObject.getString("price"))+"");
						ProductBaseInfo.setBrief(jsonObject.getString("name"));
						ProductBaseInfo.setCommentAmount(jsonObject.getString("comment_sum"));
						ProductBaseInfo.setSalledAmount(jsonObject.getString("sall_count"));
//						Date date = new Date();
						long ts = jsonObject.getLong("ts");
						int length = dates.size();
						int i = length - 1;
						for(; i > 0; i--){
							if(ts > dates.get(i) && ts < dates.get(i - 1)){
								dates.add(i, ts);
								productBaseInfosArray.add(i, ProductBaseInfo);
								Log.e(TAG, "add position:"+i);
								break;
							}
						}
						Log.d(TAG, "position:"+i+" and length:"+length);
						if(i == 0 && length == dates.size()){
							dates.add(0, ts);
							Log.e(TAG, "add position:"+i);
							productBaseInfosArray.add(0, ProductBaseInfo);
						}
					} catch (NumberFormatException  e) {
						// TODO: handle exception
						Log.e(TAG, "get ProductBaseInfo array NumberFormatException ");
						e.printStackTrace();
					} catch (Exception e) {
						// TODO: handle exception
						Log.e(TAG, "get ProductBaseInfo array json err ");
						e.printStackTrace();
					}
				}
			}*/
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "get ProductBaseInfo array err ");
			e.printStackTrace();
		}
//		historyArray = (ArrayList<ProductBaseInfo>)productBaseInfosArray.clone();
		int length = productBaseInfosArray.size();
		for (int i = 0; i < length; i++) {
			keysList.add(keysArray.get(length - i - 1));
			historyArray.add(productBaseInfosArray.get(length - i - 1));
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
	public int getProductBaseInfoAmount(){
		int count = 0;
		try {
			Map<String, ?>spMap = sp.getAll();
			count = spMap.size();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "get ProductBaseInfo type count err");
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
		ArrayList<ProductBaseInfo> productBaseInfos = getHistoryArray();
		int length = productBaseInfos.size();
		for(int i = 0; i < length; i++){
			if(uid.equals(productBaseInfos.get(i).getUId())){
				issuccess = true;
				delHistory(uid);
				break;
			}
		}
		return issuccess;
	}
	
	/**
	 * 检查浏览历史是否小于20个，超过就删除最早加入的数据
	 * @return
	 */
	private boolean checkAndDel(){
		int count = getProductBaseInfoAmount();
		if(count < 20)return true;
		ArrayList<ProductBaseInfo> productBaseInfos = getHistoryArray();
		count = productBaseInfos.size();
		for (int i = 19; i < count; i++) {//从最早加入的20个开始删除
			String id = productBaseInfos.get(i).getUId();
			delHistory(id);
		}
		return false;
	}
	
}
