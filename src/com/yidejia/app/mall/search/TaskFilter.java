package com.yidejia.app.mall.search;

import java.io.IOException;
import java.util.ArrayList;

import com.opens.asyncokhttpclient.AsyncHttpResponse;
import com.opens.asyncokhttpclient.AsyncOkHttpClient;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.Brand;
import com.yidejia.app.mall.model.Function;
import com.yidejia.app.mall.model.PriceLevel;
import com.yidejia.app.mall.net.search.BrandDataUtil;
import com.yidejia.app.mall.net.search.EffectDataUtil;
import com.yidejia.app.mall.net.search.PriceDataUtil;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class TaskFilter {
	
	private Activity context ;
	private View view;
	private ArrayList<PriceLevel> pricesLevels;
	private ArrayList<Brand> brands;
	private ArrayList<Function> effects;
	
//	private Task task;
	private FilterViewCtrl ctrl;
	
	public TaskFilter(Activity context, View view){
		this.context = context;
		this.view = view;
		pricesLevels = new ArrayList<PriceLevel>();
		brands = new ArrayList<Brand>();
		effects = new ArrayList<Function>();
		ctrl = new FilterViewCtrl(context);
		showFilterView();
	}
	
	public void getFilter(){
//		closeTask();
//		task = new Task();
//		task.execute();
		JNICallBack jniCallBack = new JNICallBack();
		String urlPrice = jniCallBack.getHttp4GetPrice();
		String urlBrand = jniCallBack.getHttp4GetBrand();
		String urlEffect = jniCallBack.getHttp4GetEffect("flag%3D%27y%27", "0", "20", "", "", "%2A");
		AsyncOkHttpClient client = new AsyncOkHttpClient();
//		client.get(urlBrand, new AsyncHttpResponse(){
//
//			@Override
//			public void onSuccess(int statusCode, String content) {
//				super.onSuccess(statusCode, content);
//				ParseSearchJson parseSearchJson = new ParseSearchJson();
//				parseSearchJson.parseBrandJson(content);
//				brands = parseSearchJson.getBrands();
//				ctrl.update();
//			}
//			
//		});
		client.get(urlPrice, new AsyncHttpResponse(){
			
			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				ParseSearchJson parseSearchJson = new ParseSearchJson();
				parseSearchJson.parsePriceJson(content);
				pricesLevels = parseSearchJson.getPriceLevels();
				ctrl.update();
			}
			
		});
//		client.get(urlEffect, new AsyncHttpResponse(){
//			
//			@Override
//			public void onSuccess(int statusCode, String content) {
//				super.onSuccess(statusCode, content);
//				ParseSearchJson parseSearchJson = new ParseSearchJson();
//				parseSearchJson.parseFunJson(content);
//				effects = parseSearchJson.getFunctions();
//				ctrl.update();
//			}
//			
//		});
	}
	
	private void showFilterView() {
		ctrl.setBrands(brands);
		ctrl.setPrices(pricesLevels);
		ctrl.setFuns(effects);
		ctrl.getView(view);
	}
	
//	public void closeTask(){
//		if(task != null && task.getStatus().RUNNING == AsyncTask.Status.RUNNING){
//			task.cancel(true);
//		}
//	}
	
	private boolean isTimeOut = false;
	
	private class Task extends AsyncTask<Void, Void, Boolean>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
//			bar = new ProgressDialog(context);
//			bar.setCancelable(true);
//			bar.setMessage(context.getResources().getString(R.string.loading));
//			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			bar.show();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if(result) {
				showFilterView();
				
			} else {
				if(isTimeOut){
					isTimeOut = false;
					Toast.makeText(context, context.getResources().getString(R.string.time_out), Toast.LENGTH_SHORT).show();
				}
			}
				
		}
		@Override
		protected Boolean doInBackground(Void... params) {
			PriceDataUtil priceDataUtil = new PriceDataUtil();
			BrandDataUtil brandDataUtil = new BrandDataUtil();
			EffectDataUtil effectDataUtil = new EffectDataUtil();
			boolean priceSuccess = false;
			boolean brandSuccess = false;
			boolean effectSuccess = false;
			try {
				String priceHttpresp = priceDataUtil.getHttpResponseString();
				priceSuccess = priceDataUtil.analysis(priceHttpresp);
				pricesLevels = priceDataUtil.getPriceLevels();
				
				String brandHttpresp = brandDataUtil.getHttpResponseString();
				brandSuccess = brandDataUtil.analysis(brandHttpresp);
				brands = brandDataUtil.getBrands();
				
				String effectHttpresp = effectDataUtil.getHttpResponseString();
				effectSuccess = effectDataUtil.analysis(effectHttpresp);
				effects = effectDataUtil.getFunctions();
				return priceSuccess && brandSuccess && effectSuccess;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TimeOutEx e) {
				e.printStackTrace();
				isTimeOut = true;
			}
			
			return false;
		}
	}
	
	
	
	
}
