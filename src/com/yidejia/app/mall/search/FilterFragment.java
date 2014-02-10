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
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.Brand;
import com.yidejia.app.mall.model.Function;
import com.yidejia.app.mall.model.PriceLevel;

@SuppressLint("UseSparseArrays")
public class FilterFragment extends Fragment {
	
	private ArrayList<PriceLevel> pricesLevels;
	private ArrayList<Brand> brands;
	private ArrayList<Function> effects;
	private View view;
	private FilterViewCtrl ctrl;
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		System.gc();
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
		pricesLevels = new ArrayList<PriceLevel>();
		brands = new ArrayList<Brand>();
		effects = new ArrayList<Function>();
		ctrl = new FilterViewCtrl(getActivity());
		showFilterView();
		getFilter();
	}
	
	private void showFilterView() {
		ctrl.setBrands(brands);
		ctrl.setPrices(pricesLevels);
		ctrl.setFuns(effects);
		ctrl.getView(view);
	}
	
	private void getFilter(){
//		closeTask();
//		task = new Task();
//		task.execute();
		JNICallBack jniCallBack = new JNICallBack();
		String urlPrice = jniCallBack.getHttp4GetPrice();
		String urlBrand = jniCallBack.getHttp4GetBrand();
		String urlEffect = jniCallBack.getHttp4GetEffect("flag%3D%27y%27", "0", "20", "", "", "%2A");
		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.get(urlBrand, new AsyncHttpResponse(){

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				ParseSearchJson parseSearchJson = new ParseSearchJson();
				parseSearchJson.parseBrandJson(content);
				brands = parseSearchJson.getBrands();
				ctrl.setBrands(brands);
				ctrl.update();
			}
			
		});
		client.get(urlPrice, new AsyncHttpResponse(){
			
			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				ParseSearchJson parseSearchJson = new ParseSearchJson();
				parseSearchJson.parsePriceJson(content);
				pricesLevels = parseSearchJson.getPriceLevels();
				ctrl.setPrices(pricesLevels);
				ctrl.update();
			}
			
		});
		client.get(urlEffect, new AsyncHttpResponse(){
			
			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				ParseSearchJson parseSearchJson = new ParseSearchJson();
				parseSearchJson.parseFunJson(content);
				effects = parseSearchJson.getFunctions();
				ctrl.setFuns(effects);
				ctrl.update();
			}
			
		});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e(FilterFragment.class.getName(), "===onDestroy");
//		if(taskFilter != null) taskFilter.closeTask();
		System.gc();
	}

	
}
