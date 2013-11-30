package com.yidejia.app.mall.datamanage;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.ctrl.RetViewCtrl;
import com.yidejia.app.mall.model.RetOrderInfo;
import com.yidejia.app.mall.net.order.GetReturnOrder;
import com.yidejia.app.mall.view.ExchangeActivity;
import com.yidejia.app.mall.view.ReturnActivity;
import com.yidejia.app.mall.widget.YLProgressDialog;

public class TaskGetRetList {
	
	private Activity activity;
	private LinearLayout allOrderLayout;//全部退换货的layout
	private PullToRefreshScrollView mPullToRefreshScrollView;
	
	private RetViewCtrl ctrl;
	
	private Task task;
	
	private boolean isSuccess = false;
	
	private ArrayList<RetOrderInfo> retOrderInfos;
	private ArrayList<RetOrderInfo> retOrderInfoTemps;
	
	private String userId;
	private String token;
	private boolean isFirstIn = true;
	private int fromIndex = 0;
	private int amount = 10;
	private boolean isClean = false;
	
	public TaskGetRetList(Activity activity, PullToRefreshScrollView mPullToRefreshScrollView){
		this.activity = activity;
		this.mPullToRefreshScrollView = mPullToRefreshScrollView;
		findIds();
		isFirstIn = true;
		retOrderInfos = new ArrayList<RetOrderInfo>();
	}
	
	public boolean getRetOrderList(String userId, String token, int fromIndex, int amount){
		this.userId = userId;
		this.token = token;
		this.fromIndex = fromIndex;
		this.amount = amount;
		if(fromIndex == 0) {
			isClean = true;
		}
		if(task != null && task.getStatus().RUNNING == AsyncTask.Status.RUNNING){
			task.cancel(true);
		}
		
		task = new Task();
		task.execute();
		
		return isSuccess;
	}
	
	private void findIds(){
		allOrderLayout = (LinearLayout) activity.findViewById(R.id.all_order_item_main_scrollView_linearlayout1);
	}
	
	
	private void add2view(final RetOrderInfo info){
		ctrl = new RetViewCtrl(activity,info);
		View view = ctrl.getRetView();
		ctrl.setText(info.getStatus(), info.getOrderCode(), info.getTheDate());
		view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(activity, ReturnActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("info", info);
				intent.putExtras(bundle);
				activity.startActivity(intent);
			}
		});
		allOrderLayout.addView(view);
	}
	
	private class Task extends AsyncTask<Void, Void, Boolean>{

		private ProgressDialog bar;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (isFirstIn) {
//				bar = new ProgressDialog(activity);
//				bar.setMessage(activity.getResources().getString(R.string.loading));
//				bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//				bar.show();
				bar = (ProgressDialog) new YLProgressDialog(activity)
						.createLoadingDialog(activity, null);
				bar.setOnCancelListener(new DialogInterface.OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						// TODO Auto-generated method stub
						closeTask();
					}
				});
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result){
//				Toast.makeText(activity, "提交成功!", Toast.LENGTH_LONG).show();
				String label = activity.getResources().getString(R.string.update_time)
						+ DateUtils.formatDateTime(activity,
								System.currentTimeMillis(),
								DateUtils.FORMAT_ABBREV_ALL
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_SHOW_TIME);
				mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				
				isSuccess = true;
				if(isClean) {
					allOrderLayout.removeAllViews();
					isClean = false;
				}
				retOrderInfos.addAll(retOrderInfoTemps);
				int length = retOrderInfoTemps.size();
				for (int i = 0; i < length; i++) {
					add2view(retOrderInfoTemps.get(i));
				}
			}else {
				if(!retOrderInfos.isEmpty() && retOrderInfos != null){
					Toast.makeText(activity, activity.getResources().getString(R.string.nomore), Toast.LENGTH_LONG).show();
				} else {
//					Toast.makeText(activity, "亲，您并无", Toast.LENGTH_LONG).show();
				}
				isSuccess = false;
			}
			if(isFirstIn){
				bar.dismiss();
				isFirstIn = false;
			} else{
				mPullToRefreshScrollView.onRefreshComplete();
			}
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			GetReturnOrder getReturnOrder = new GetReturnOrder();
			String httpResp = "";
			try {
				httpResp = getReturnOrder.getHttpResp(userId, fromIndex + "", amount + "", token);
				try {
					if(getReturnOrder.analysisReturn(httpResp)){
//						retOrderInfos = getReturnOrder.getRetOrderInfos();
						retOrderInfoTemps = getReturnOrder.getRetOrderInfos();
						return true;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			return false;
		}
		
	}
	
	public void closeTask(){
		if(task != null && task.getStatus().RUNNING == AsyncTask.Status.RUNNING){
			task.cancel(true);
		}
	}
}
