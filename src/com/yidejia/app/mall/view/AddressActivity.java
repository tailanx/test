package com.yidejia.app.mall.view;

import java.io.IOException;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.AddressDataManage;
import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.net.address.GetUserAddressList;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.DefinalDate;

public class AddressActivity extends SherlockActivity {


	private String TAG = AddressActivity.class.getName();// log
	private AddressAdapter adapter;
//	private AddressDataManage addressDataManage;
	private ListView listView;
	private ArrayList<Addresses> mAddresses;
	private MyApplication myApplication;
	private String userId;
	private PullToRefreshListView pullToRefreshListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setActionbar();
		setContentView(R.layout.address_management);
		
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.address_item_main_refresh_scrollview111);
		
		pullToRefreshListView.setOnRefreshListener(listener2);
		
		String label = getResources().getString(R.string.update_time)+DateUtils.formatDateTime(this, System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_ALL|DateUtils.FORMAT_SHOW_DATE|DateUtils.FORMAT_SHOW_TIME);
		pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		
		
		listView = pullToRefreshListView.getRefreshableView();
//		layout = (LinearLayout) findViewById(R.id.address_management_relative2);
//		addressDataManage = new AddressDataManage(AddressActivity.this);
		
		myApplication = (MyApplication) getApplication();
		userId = myApplication.getUserId();
		mAddresses = new ArrayList<Addresses>();
//		mAddresses = addressDataManage.getAddressesArray(
//				userId, fromIndex, acount);
		adapter = new AddressAdapter(AddressActivity.this, mAddresses);
		listView.setAdapter(adapter);
//		Utility.setListViewHeightBasedOnChildren(listView);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i("info", "   OnItemClickListener");
				Addresses addresses = mAddresses.get(position - 1);
				Intent intent = new Intent(AddressActivity.this,
						CstmPayActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("addresses1", addresses);
				intent.putExtras(bundle);
				AddressActivity.this.setResult(Consts.AddressResponseCode,
						intent);
				AddressActivity.this.finish();
			}
		});
		
		bar = new ProgressDialog(this);
		getUserAddressList = new GetUserAddressList();
		
		closeTask();
		task = new Task();
		task.execute();
	}
	
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		closeTask();
	}


	private OnRefreshListener2<ListView> listener2 = new OnRefreshListener2<ListView>() {

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			// TODO Auto-generated method stub
			fromIndex = 0;
			closeTask();
			task = new Task();
			task.execute();
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			// TODO Auto-generated method stub
			fromIndex += acount;
			closeTask();
			task = new Task();
			task.execute();
		}
	};
	

	private int fromIndex = 0;
	private int acount = 10;
	/*private OnRefreshListener<ListView> listener = new OnRefreshListener<ListView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			// TODO Auto-generated method stub
			String label = getResources().getString(R.string.update_time)+DateUtils.formatDateTime(AddressActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME|DateUtils.FORMAT_SHOW_DATE|DateUtils.FORMAT_ABBREV_ALL);
			pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			fromIndex = 0;
			AddressDataManage dataManage = new AddressDataManage(AddressActivity.this);
			ArrayList<Addresses> addressesList = dataManage.getAddressesArray(myApplication.getUserId(), fromIndex, acount);
			pullToRefreshListView.onRefreshComplete();
			if(addressesList.isEmpty()){
				if(mAddresses.isEmpty()){
					Toast.makeText(AddressActivity.this, getResources().getString(R.string.load_addresses), Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(AddressActivity.this, getResources().getString(R.string.nomore), Toast.LENGTH_SHORT).show();
				}
//				Toast.makeText(AddressActivity.this, t, duration).show();
			}else{
				mAddresses.clear();
				mAddresses.addAll(addressesList);
				adapter.notifyDataSetChanged();
			}
//			setupShow();
		}
	};*/

	private void setActionbar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		// getSupportActionBar().setLogo(R.drawable.back);
		getSupportActionBar().setIcon(R.drawable.back1);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_common);
		// startActionMode(new
		// AnActionModeOfEpicProportions(ComposeActivity.this));
		ImageView leftButton = (ImageView) findViewById(R.id.actionbar_left);
		leftButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				AddressActivity.this.finish();
			}
		});
		Button rightButton = (Button) findViewById(R.id.actionbar_right);
		rightButton.setText(getResources().getString(R.string.new_address));
		rightButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent(AddressActivity.this,
						EditNewAddressActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("editaddress", null);
				intent2.putExtras(bundle);
				startActivityForResult(intent2, DefinalDate.requestcode);// 发送Intent,并设置请求码
				// AddressActivity.this.finish();
			}
		});

		TextView titleTextView = (TextView) findViewById(R.id.actionbar_title);
		titleTextView
				.setText(getResources().getString(R.string.manage_address));
		Log.e(TAG, titleTextView.getText().toString());
	}

//	public void updateView(ArrayList<Addresses> musics) {
//		adapter.changeData(musics);
//	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			if (requestCode == DefinalDate.requestcode
					&& resultCode == DefinalDate.responcode) {
				try {
					Bundle bundle = data.getExtras();
					Addresses addresses = (Addresses) bundle
							.getSerializable("newaddress");
					// Log.i(TAG, TAG+"onResume");
					/*mAddresses.add(addresses);
					adapter.notifyDataSetChanged();
					*/
					pullToRefreshListView.setRefreshing();
					pullToRefreshListView.onRefreshComplete();
					
					
					// adapter.mAddresses.clear();
					// AddressDataManage addressDataManage1= new
					// AddressDataManage(AddressActivity.this);
					// adapter.mAddresses =
					// addressDataManage1.getAddressesArray(myApplication.getUserId(),
					// fromIndex, acount);
//										Utility.setListViewHeightBasedOnChildren(listView);
//					listView.setAdapter(adapter);
					// new AddressUtil(AddressActivity.this,
					// layout).addAddresses(data);
					// layout.invalidate();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(AddressActivity.this,
							getResources().getString(R.string.wrong_input),
							Toast.LENGTH_SHORT).show();
				}

			} else if (requestCode == DefinalDate.requestcode
					&& resultCode == DefinalDate.responcode1) {
//				listView.removeAllViews();
//				ArrayList<Addresses> addresses =  mAddresses = addressDataManage.getAddressesArray(
//						myApplication.getUserId(), fromIndex, acount);
//				adapter.changeData(addresses);
//				adapter.notifyDataSetChanged();
//				new AddressUtil(AddressActivity.this, layout)
//						.updateAddresses(data);
//				layout.invalidate();
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(AddressActivity.this,
					getResources().getString(R.string.bad_network),
					Toast.LENGTH_SHORT).show();

		}
	}
	
	
	private ProgressDialog bar;
	private Task task;
	private GetUserAddressList getUserAddressList;
	private ArrayList<Addresses> addresses;
	private boolean isNomore = false;
	private boolean isFirstIn = true;
	
	private class Task extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				String httpresp = getUserAddressList.getAddressHttpresp("customer_id%3D"+userId+"+and+valid_flag%3D%27y%27", fromIndex + "", acount + "");
				boolean issuccess = getUserAddressList.analysis(httpresp);
//				if(addresses != null && !addresses.isEmpty()){
//					addresses.clear();
//				}
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
			if (isFirstIn) {
				bar.setCancelable(false);
				bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				bar.show();
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (isFirstIn) {
				bar.dismiss();
				isFirstIn = false;
			} else {
				pullToRefreshListView.onRefreshComplete();
			}
			if(!result) {
				if(fromIndex != 0) {
					fromIndex -= acount;
				}
			} else {
				if(!mAddresses.isEmpty() && isNomore) {
					Toast.makeText(AddressActivity.this, getResources().getString(R.string.nomore), Toast.LENGTH_LONG).show();
					isNomore = false;
					return;
				}
				if(mAddresses != null && !mAddresses.isEmpty() && fromIndex == 0){
					mAddresses.clear();
				}
				Log.e(TAG, "addresses size " + addresses.size());
				mAddresses.addAll(addresses);
				Log.e(TAG, "maddresses size " + mAddresses.size());
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
