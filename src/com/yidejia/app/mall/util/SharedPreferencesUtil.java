package com.yidejia.app.mall.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtil {
	private Activity activity;
	private SharedPreferences sp ;
	
	public SharedPreferencesUtil(Activity activity) {
		this.activity = activity;
	}
	
	public void saveData(String fileName, String key, String value){
		sp = activity.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}
	public void saveData(String fileName, String key, boolean value){
		sp = activity.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	public void saveData(String fileName, String key, int value){
		sp = activity.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	public void saveData(String fileName, String key, float value){
		sp = activity.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putFloat(key, value);
		editor.commit();
	}
	public void saveData(String fileName, String key, long value){
		sp = activity.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
	public String getData(String fileName, String key, String defValue){
		sp = activity.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		return sp.getString(key, defValue);
	}
	public boolean getData(String fileName, String key, boolean defValue){
		sp = activity.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		return sp.getBoolean(key, defValue);
	}
	public int getData(String fileName, String key, int defValue){
		sp = activity.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		return sp.getInt(key, defValue);
	}
	public float getData(String fileName, String key, float defValue){
		sp = activity.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		return sp.getFloat(key, defValue);
	}
	public long getData(String fileName, String key, long defValue){
		sp = activity.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		return sp.getLong(key, defValue);
	}
}
