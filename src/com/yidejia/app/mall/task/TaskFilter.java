package com.yidejia.app.mall.task;

import java.io.IOException;
import java.util.ArrayList;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.ctrl.FilterViewCtrl;
import com.yidejia.app.mall.model.Brand;
import com.yidejia.app.mall.model.Function;
import com.yidejia.app.mall.model.PriceLevel;
import com.yidejia.app.mall.net.search.BrandDataUtil;
import com.yidejia.app.mall.net.search.EffectDataUtil;
import com.yidejia.app.mall.net.search.PriceDataUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;

public class TaskFilter {
	
	private Activity context ;
	private View view;
	private ArrayList<PriceLevel> pricesLevels;
	private ArrayList<Brand> brands;
	private ArrayList<Function> effects;
	
	private Task task;
	
	public TaskFilter(Activity context, View view){
		this.context = context;
		this.view = view;
	}
	
	public void getFilter(){
		closeTask();
		task = new Task();
		task.execute();
	}
	
	
	public void closeTask(){
		if(task != null && task.getStatus().RUNNING == AsyncTask.Status.RUNNING){
			task.cancel(true);
		}
	}
	
	private class Task extends AsyncTask<Void, Void, Boolean>{
		private ProgressDialog bar;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			bar = new ProgressDialog(context);
			bar.setCancelable(true);
			bar.setMessage(context.getResources().getString(R.string.loading));
			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			bar.show();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			bar.dismiss();
			if(result) {
				FilterViewCtrl ctrl = new FilterViewCtrl(context);
				ctrl.setBrands(brands);
				ctrl.setPrices(pricesLevels);
				ctrl.setFuns(effects);
				ctrl.getView(view);
			}
		}
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return false;
		}
	}
	
	
	
	
}
