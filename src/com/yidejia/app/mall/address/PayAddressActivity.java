package com.yidejia.app.mall.address;

import java.io.IOException;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.net.address.GetUserAddressList;
import com.yidejia.app.mall.pay.CstmPayActivity;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.widget.YLProgressDialog;

public class PayAddressActivity extends SherlockActivity {
	private MyApplication myApplication;
	
	private String userId;
	private PayAddressAdapter adapter;
	private int fromIndex = 0;
	private int acount = 10;
	private ListView listview;
	private ArrayList<ModelAddresses> mAddresses;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_listview);
		setActionbar();
		listview = (ListView) findViewById(R.id.lv_common);
		myApplication = (MyApplication) getApplication();
		userId = myApplication.getUserId();
		mAddresses = new ArrayList<ModelAddresses>();
		adapter  = new PayAddressAdapter(PayAddressActivity.this, mAddresses);
		listview.setAdapter(adapter);
		//listview的点击事件
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
					ModelAddresses addresses = mAddresses.get(arg2);
					Intent intent = new Intent(PayAddressActivity.this, CstmPayActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("addresses1", addresses);
					intent.putExtras(bundle);
					PayAddressActivity.this.setResult(Consts.AddressResponseCode, intent);
					PayAddressActivity.this.finish();
			}
		});
		
		getUserAddressList = new GetUserAddressList();
		
		closeTask();
		task = new Task();
		task.execute();
	}
	/**
	 * UI控件的顶部
	 */
	private void setActionbar() {
		try {
			getSupportActionBar().setDisplayHomeAsUpEnabled(false);
			getSupportActionBar().setDisplayShowHomeEnabled(false);
			getSupportActionBar().setDisplayShowTitleEnabled(false);
			getSupportActionBar().setDisplayUseLogoEnabled(false);
			getSupportActionBar().setDisplayShowCustomEnabled(true);
			getSupportActionBar().setCustomView(R.layout.actionbar_common);
			TextView button = (TextView) findViewById(R.id.ab_common_back);
			TextView title = (TextView) findViewById(R.id.ab_common_title);
			title.setText("地址");
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					PayAddressActivity.this.finish();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(PayAddressActivity.this, getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT)
					.show();
		}
	}
	
	private ProgressDialog bar;
	private Task task;
	private GetUserAddressList getUserAddressList;
	private ArrayList<ModelAddresses> addresses;
	private boolean isNomore = false;
	
	private class Task extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				String httpresp = getUserAddressList.getAddressHttpresp("customer_id%3D"+userId+"+and+valid_flag%3D%27y%27", fromIndex + "", acount + "");
				boolean issuccess = getUserAddressList.analysis(httpresp);
				if(addresses != null && !addresses.isEmpty()){
					addresses.clear();
				}
				addresses = getUserAddressList.getAddresses();
				isNomore = getUserAddressList.getIsNoMore();
				return issuccess;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			bar = (ProgressDialog) new YLProgressDialog(PayAddressActivity.this)
					.createLoadingDialog(PayAddressActivity.this, null);
			bar.setOnCancelListener(new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					cancel(true);
				}
			});
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			bar.dismiss();
			if(!result) {
				if(fromIndex != 0) {
					fromIndex -= acount;
				}
			} else {
				if(!mAddresses.isEmpty() && isNomore) {
					Toast.makeText(PayAddressActivity.this, getResources().getString(R.string.nomore), Toast.LENGTH_LONG).show();
					return;
				}
				mAddresses.addAll(addresses);
				adapter.notifyDataSetChanged();
			}
		}
		
	}
	
	private void closeTask(){
		if(task != null && task.getStatus().RUNNING == AsyncTask.Status.RUNNING){
			task.cancel(true);
		}
	}

}
