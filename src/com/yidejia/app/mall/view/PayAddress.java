package com.yidejia.app.mall.view;

import java.util.ArrayList;

import android.content.Intent;
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
import com.yidejia.app.mall.util.Consts;

public class PayAddress extends SherlockActivity {
	private AddressDataManage dataManage ;
	private MyApplication myApplication;
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
		dataManage = new AddressDataManage(PayAddress.this);
		myApplication = (MyApplication) getApplication();
		mAddresses = dataManage.getAddressesArray(myApplication.getUserId(), fromIndex, acount);
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

}
