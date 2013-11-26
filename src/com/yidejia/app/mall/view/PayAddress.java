package com.yidejia.app.mall.view;

import java.io.IOException;
import java.util.ArrayList;

import android.app.ProgressDialog;
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
import com.yidejia.app.mall.adapter.PayAddressAdapter;
import com.yidejia.app.mall.datamanage.AddressDataManage;
import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.net.address.GetUserAddressList;
import com.yidejia.app.mall.util.Consts;

public class PayAddress extends SherlockActivity {
//	private AddressDataManage dataManage ;
	private MyApplication myApplication;
	
	private String userId;
	private PayAddressAdapter adapter;
	private int fromIndex = 0;
	private int acount = 10;
	private ListView listview;
	private ArrayList<Addresses> mAddresses;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_address);
		setActionbar();
		listview = (ListView) findViewById(R.id.pay_address);
//		dataManage = new AddressDataManage(PayAddress.this);
		myApplication = (MyApplication) getApplication();
		userId = myApplication.getUserId();
		mAddresses = new ArrayList<Addresses>();
//		mAddresses = dataManage.getAddressesArray(userId, fromIndex, acount);
		adapter  = new PayAddressAdapter(PayAddress.this, mAddresses);
		listview.setAdapter(adapter);
		//listview的点击事件
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
					Addresses addresses = mAddresses.get(arg2);
					Intent intent = new Intent(PayAddress.this, CstmPayActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("addresses1", addresses);
					intent.putExtras(bundle);
					PayAddress.this.setResult(Consts.AddressResponseCode, intent);
					PayAddress.this.finish();
			}
		});
		
		bar = new ProgressDialog(this);
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
			getSupportActionBar().setCustomView(R.layout.actionbar_compose);
			ImageView button = (ImageView) findViewById(R.id.compose_back);
			TextView title = (TextView) findViewById(R.id.compose_title);
			title.setText("地址");
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					PayAddress.this.finish();
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(PayAddress.this, getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT)
					.show();
		}
	}
	
	private ProgressDialog bar;
	private Task task;
	private GetUserAddressList getUserAddressList;
	private ArrayList<Addresses> addresses;
	private boolean isNomore = false;
	
	private class Task extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			bar.setCancelable(false);
			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			bar.show();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			bar.dismiss();
			if(!result) {
				if(fromIndex != 0) {
					fromIndex -= acount;
				}
			} else {
				if(!mAddresses.isEmpty() && isNomore) {
					Toast.makeText(PayAddress.this, getResources().getString(R.string.nomore), Toast.LENGTH_LONG).show();
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
