package com.yidejia.app.mall.datamanage;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.ctrl.RetViewCtrl;
import com.yidejia.app.mall.model.RetOrderInfo;
import com.yidejia.app.mall.net.order.GetReturnOrder;
import com.yidejia.app.mall.view.ReturnActivity;

public class TaskGetRetList {
	
	private Activity activity;
	private LinearLayout allOrderLayout;//全部退换货的layout
	private PullToRefreshScrollView mPullToRefreshScrollView;
	
	private RetViewCtrl ctrl;
	
	private Task task;
	
	private boolean isSuccess = false;
	
	private ArrayList<RetOrderInfo> retOrderInfos;
	
	private String userId;
	private String token;
	private boolean isFirstIn = true;
	private int fromIndex = 0;
	private int amount = 10;
	
	public TaskGetRetList(Activity activity, PullToRefreshScrollView mPullToRefreshScrollView){
		this.activity = activity;
		this.mPullToRefreshScrollView = mPullToRefreshScrollView;
		findIds();
		isFirstIn = true;
	}
	
	public boolean getRetOrderList(String userId, String token, int fromIndex, int amount){
		this.userId = userId;
		this.token = token;
		this.fromIndex = fromIndex;
		this.amount = amount;
		if(fromIndex == 0) allOrderLayout.removeAllViews();
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
				bar = new ProgressDialog(activity);
				bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				bar.show();
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result){
//				Toast.makeText(activity, "提交成功!", Toast.LENGTH_LONG).show();
				isSuccess = true;
				int length = retOrderInfos.size();
				for (int i = 0; i < length; i++) {
					add2view(retOrderInfos.get(i));
				}
			}else {
//				Toast.makeText(activity, "提交失败!", Toast.LENGTH_LONG).show();
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
						retOrderInfos = getReturnOrder.getRetOrderInfos();
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
