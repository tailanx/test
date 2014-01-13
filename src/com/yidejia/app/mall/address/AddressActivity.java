package com.yidejia.app.mall.address;

import java.io.IOException;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.net.address.GetUserAddressList;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.DefinalDate;
import com.yidejia.app.mall.view.CstmPayActivity;
import com.yidejia.app.mall.widget.YLProgressDialog;

public class AddressActivity extends SherlockActivity {

	private String TAG = AddressActivity.class.getName();// log
	private AddressAdapter adapter;
	private ListView listView;
	private ArrayList<ModelAddresses> mAddresses;
	private MyApplication myApplication;
	private String userId;
	private PullToRefreshListView pullToRefreshListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionbar();
		setContentView(R.layout.address_management);

		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.address_item_main_refresh_scrollview111);

		pullToRefreshListView.setOnRefreshListener(listener2);

		String label = getResources().getString(R.string.update_time)
				+ DateUtils.formatDateTime(this, System.currentTimeMillis(),
						DateUtils.FORMAT_ABBREV_ALL
								| DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_SHOW_TIME);
		pullToRefreshListView.getLoadingLayoutProxy()
				.setLastUpdatedLabel(label);

		listView = pullToRefreshListView.getRefreshableView();

		myApplication = (MyApplication) getApplication();
		userId = myApplication.getUserId();
		mAddresses = new ArrayList<ModelAddresses>();
		adapter = new AddressAdapter(AddressActivity.this, mAddresses);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i("info", "   OnItemClickListener");
				ModelAddresses addresses = mAddresses.get(position - 1);
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
			fromIndex = 0;
			closeTask();
			task = new Task();
			task.execute();
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			fromIndex += acount;
			closeTask();
			task = new Task();
			task.execute();
		}
	};

	private int fromIndex = 0;
	private int acount = 10;

	private void setActionbar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_common);
		TextView leftButton = (TextView) findViewById(R.id.actionbar_left);
//		TextView list = (TextView) findViewById(R.id.search_with_list);
		leftButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				AddressActivity.this.finish();
			}
		});
		TextView rightButton = (TextView) findViewById(R.id.actionbar_right);
		rightButton.setVisibility(View.VISIBLE);
		rightButton.setText(getResources().getString(R.string.new_address));
		rightButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent2 = new Intent(AddressActivity.this,
						EditNewAddressActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("editaddress", null);
				intent2.putExtras(bundle);
				startActivityForResult(intent2, DefinalDate.requestcode);// 发送Intent,并设置请求码

			}
		});

		TextView titleTextView = (TextView) findViewById(R.id.actionbar_title);
		titleTextView
				.setText(getResources().getString(R.string.manage_address));
		Log.e(TAG, titleTextView.getText().toString());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			if (requestCode == DefinalDate.requestcode
					&& resultCode == DefinalDate.responcode) {
				try {
					Log.i(TAG, TAG + "onResume");

					fromIndex = 0;

					pullToRefreshListView.setRefreshing();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(AddressActivity.this,
							getResources().getString(R.string.wrong_input),
							Toast.LENGTH_SHORT).show();
				}

			} else if (requestCode == DefinalDate.requestcode
					&& resultCode == DefinalDate.responcode1) {

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
	private ArrayList<ModelAddresses> addresses;
	private boolean isNomore = false;
	private boolean isFirstIn = true;

	private class Task extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				String httpresp = getUserAddressList
						.getAddressHttpresp("customer_id%3D" + userId
								+ "+and+valid_flag%3D%27y%27", fromIndex + "",
								acount + "");
				boolean issuccess = getUserAddressList.analysis(httpresp);
				// if(addresses != null && !addresses.isEmpty()){
				// addresses.clear();
				// }
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
				// bar.setCancelable(true);
				// bar.setMessage(getResources().getString(R.string.loading));
				// bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				// bar.show();
				bar = (ProgressDialog) new YLProgressDialog(
						AddressActivity.this).createLoadingDialog(
						AddressActivity.this, null);
				bar.setOnCancelListener(new DialogInterface.OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						// TODO Auto-generated method stub
						cancel(true);
					}
				});
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
			if (!result) {
				if (fromIndex != 0) {
					fromIndex -= acount;
				}
			} else {
				if (!mAddresses.isEmpty() && isNomore) {
					Toast.makeText(AddressActivity.this,
							getResources().getString(R.string.nomore),
							Toast.LENGTH_LONG).show();
					isNomore = false;
					return;
				}
				if (mAddresses != null && !mAddresses.isEmpty()
						&& fromIndex == 0) {
					mAddresses.clear();
				}
				Log.e(TAG, "addresses size " + addresses.size());
				mAddresses.addAll(addresses);
				Log.e(TAG, "maddresses size " + mAddresses.size());
				adapter.notifyDataSetChanged();
			}
		}

	}

	private void closeTask() {
		if (task != null
				&& task.getStatus().RUNNING == AsyncTask.Status.RUNNING) {
			task.cancel(true);
		}
	}

}
