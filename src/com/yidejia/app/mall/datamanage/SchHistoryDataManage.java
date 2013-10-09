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
 * ������ʷģ��
 * <p>
 *  ��ӵ�������ʷ{@link #addHistory(String)}
 * <p>
 *  ��ȡ������ʷ�����б�{@link #getHistorysArray()}
 * <p>
 *  ���������ʷ{@link #cleanHistory()}
 * <p>
 *  ��ȡ������ʷ��������{@link #getHistoryAmount()}
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
		myApplication = new MyApplication();
//		schHstryAmount = myApplication.getSchHstryAmount();
		schHstryAmount = getHistoryAmount();
		Log.i(TAG, "amount" + schHstryAmount);
	}
	
	/**
	 * ��ӵ�������ʷ
	 * @param details ������¼�ַ���
	 * @return boolean ����"true" : �ɹ� "false" : ʧ��
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
	 * ��ȡ������ʷ�����б�
	 * @return historyArray ������ʷ�����б�
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
	 * ���������ʷ
	 * @return boolean �ɹ���� ������"true" : �ɹ� "false" : ʧ��
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
	 * ��ȡ������ʷ��������
	 * @return amount ������ʷ��������
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
