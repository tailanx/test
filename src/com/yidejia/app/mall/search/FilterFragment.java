package com.yidejia.app.mall.search;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opens.asyncokhttpclient.AsyncHttpResponse;
import com.opens.asyncokhttpclient.AsyncOkHttpClient;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.Brand;
import com.yidejia.app.mall.model.Function;
import com.yidejia.app.mall.model.PriceLevel;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;
import com.yidejia.app.mall.util.SharedPreferencesUtil;

@SuppressLint("UseSparseArrays")
public class FilterFragment extends Fragment {
	
	private ArrayList<PriceLevel> pricesLevels;
	private ArrayList<Brand> brands;
	private ArrayList<Function> effects;
	private View view;
	private FilterViewCtrl ctrl;
	
	private SharedPreferencesUtil spUtil;
	
	private HttpClientUtil client;
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		spUtil = new SharedPreferencesUtil(getActivity());
	}
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		System.gc();
		spUtil = null;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		try {
			view = inflater.inflate(R.layout.activity_filter, null);
//			((MyApplication)getActivity().getApplication()).setView(view);;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Log.e(FilterFragment.class.getName(), "===onStart");
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.e(FilterFragment.class.getName(), "===onActivityCreated");
		
		if(null == view){
//			view = ((MyApplication)getActivity().getApplication()).getView();
			return;
		}
		
		client = new HttpClientUtil(getActivity());
		
		pricesLevels = new ArrayList<PriceLevel>();
		brands = new ArrayList<Brand>();
		effects = new ArrayList<Function>();
		ctrl = new FilterViewCtrl(getActivity());
		showFilterView();
		
		loadCache();
		
		if(!MyApplication.getInstance().isFilterCreated()){
			getFilter();
			MyApplication.getInstance().setFilterCreated(true);
		}
	}
	
	private void showFilterView() {
		ctrl.setBrands(brands);
		ctrl.setPrices(pricesLevels);
		ctrl.setFuns(effects);
		ctrl.getView(view);
	}
	
	/**加载本地缓存数据**/
	private void loadCache(){
		if(null == spUtil) spUtil = new SharedPreferencesUtil(getActivity());
		String strBrand = spUtil.getData("filter", "brand", "");
		String strPrice = spUtil.getData("filter", "price", "");
		String strEffect = spUtil.getData("filter", "effect", "");
		
		ParseSearchJson parseSearchJson = new ParseSearchJson();
		if(parseSearchJson.parseBrandJson(strBrand)){
			brands = parseSearchJson.getBrands();
			ctrl.setBrands(brands);
		}
		if(parseSearchJson.parsePriceJson(strPrice)){
			pricesLevels = parseSearchJson.getPriceLevels();
			ctrl.setPrices(pricesLevels);
		}
		if(parseSearchJson.parseFunJson(strEffect)){
			effects = parseSearchJson.getFunctions();
			ctrl.setFuns(effects);
		}
		ctrl.update();
	}
	
	private void getFilter(){
//		closeTask();
//		task = new Task();
//		task.execute();
		JNICallBack jniCallBack = new JNICallBack();
		String urlPrice = jniCallBack.getHttp4GetPrice();
		String urlBrand = jniCallBack.getHttp4GetBrand();
		String urlEffect = jniCallBack.getHttp4GetEffect("flag%3D%27y%27", "0", "20", "", "", "%2A");
		
		client.getHttpResp(urlBrand, new IHttpResp(){

			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
				ParseSearchJson parseSearchJson = new ParseSearchJson();
				if (parseSearchJson.parseBrandJson(content)) {
					if(null != spUtil)
					spUtil.saveData("filter", "brand", content);
					
					brands = parseSearchJson.getBrands();
					ctrl.setBrands(brands);
					ctrl.update();
				}
			}
			
		});
		client.getHttpResp(urlPrice, new IHttpResp(){
			
			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
				ParseSearchJson parseSearchJson = new ParseSearchJson();
				if (parseSearchJson.parsePriceJson(content)) {
					if(null != spUtil)
					spUtil.saveData("filter", "price", content);
					
					pricesLevels = parseSearchJson.getPriceLevels();
					ctrl.setPrices(pricesLevels);
					ctrl.update();
				}
			}
			
		});
		client.getHttpResp(urlEffect, new IHttpResp(){
			
			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
				ParseSearchJson parseSearchJson = new ParseSearchJson();
				if (parseSearchJson.parseFunJson(content)) {
					if(null != spUtil)
					spUtil.saveData("filter", "effect", content);

					effects = parseSearchJson.getFunctions();
					ctrl.setFuns(effects);
					ctrl.update();
				}
			}
			
		});
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		if (null != brands) {
			brands.clear();
			brands = null;
		}
		if (null != pricesLevels) {
			pricesLevels.clear();
			pricesLevels = null;
		}
		if (null != effects) {
			effects.clear();
			effects = null;
		}
		if(null != ctrl.getAdapter()) ctrl.cleanAdapter();
		
		if(null != client)
		client.closeConn();
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e(FilterFragment.class.getName(), "===onDestroy");
//		if(taskFilter != null) taskFilter.closeTask();
		System.gc();
	}

	
}
