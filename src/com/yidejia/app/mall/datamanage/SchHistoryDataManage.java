package com.yidejia.app.mall.datamanage;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;


import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.yidejia.app.mall.MainFragmentActivity;
import com.yidejia.app.mall.MyApplication;
/**
 * 搜索历史模块
 * <p>
 *  添加到搜索历史{@link #addHistory(String)}
 * <p>
 *  获取搜索历史数据列表{@link #getHistorysArray()}
 * <p>
 *  清空搜索历史{@link #cleanHistory()}
 * <p>
 *  获取搜索历史数据总数{@link #getHistoryAmount()}
 * @author long bin
 *
 */
public class SchHistoryDataManage {
	
	private ArrayList<String> historyArray; 
	private SharedPreferences sp;
	private MyApplication myApplication;
	private int schHstryAmount = 0;
	
	private String TAG = SchHistoryDataManage.class.getName();
	
	public SchHistoryDataManage(){
		sp = MainFragmentActivity.MAINACTIVITY.getSharedPreferences("History", Activity.MODE_APPEND );
		historyArray = new ArrayList<String>();
		myApplication = (MyApplication)MainFragmentActivity.MAINACTIVITY.getApplication();
//		myApplication = new MyApplication();
//		schHstryAmount = myApplication.getSchHstryAmount();
		schHstryAmount = getHistoryAmount();
		Log.i(TAG, "amount" + schHstryAmount);
	}
	
	/**
	 * 添加到搜索历史
	 * @param details 搜索记录字符串
	 * @return boolean 返回"true" : 成功 "false" : 失败
	 */
	public boolean addHistory(String details) {
		boolean flag = false;
		Log.i(TAG, "this history amount has :"+schHstryAmount);
		Editor editor = sp.edit();
		try {
			schHstryAmount++;
			editor.putString(""+schHstryAmount, details);
			editor.commit();
			myApplication.setSchHstryAmount(schHstryAmount);
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "addCart err");
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 获取搜索历史数据列表
	 * @return historyArray 搜索历史数据列表
	 */
	public ArrayList<String> getHistorysArray(){
		try {
//			ArrayList<String> historyArray = new ArrayList<String>();
			Map<String, ?>spMap = sp.getAll();
			if(!spMap.isEmpty()){
				for (Entry<String, ?> entry : spMap.entrySet()) {
//					String key = entry.getKey();
					String value = (String) entry.getValue();
					historyArray.add(value);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "get cart array err ");
			e.printStackTrace();
		}
		
		return historyArray;
	}
	/**
	 * 清空搜索历史
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
	 * 获取搜索历史数据总数
	 * @return amount 搜索历史数据总数
	 */
	public int getHistoryAmount(){
		int amount = 0 ;
		try {
			Map<String, ?>spMap = sp.getAll();
			if(!spMap.isEmpty()){
				amount = spMap.size();
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "get cart array err ");
			e.printStackTrace();
		}
		Log.i(TAG, ""+amount);
		return amount;
	}
	
}
