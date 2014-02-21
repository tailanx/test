package com.yidejia.app.mall.address;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.baidu.mobstat.StatService;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.DefinalDate;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;

public class AddressActivity extends BaseActivity {

	// private String TAG = AddressActivity.class.getName();// log
	private AddressAdapter adapter;
	private ListView listView;
	private ArrayList<ModelAddresses> mAddresses;
	private MyApplication myApplication;
	private String userId;
	private PullToRefreshListView pullToRefreshListView;
//	private String TAG = "AddressActivity";
	private int requestCode = -1;

	private ProgressDialog bar;
//	private boolean isNomore = false;
	private boolean isFirstIn = true;
	// private String message;// 发生错误时候的报错信息
	private HttpClientUtil httpClientUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置头部
		setActionbarConfig();
		setTitle(getResources().getString(R.string.manage_address));
		TextView rightButton = (TextView) findViewById(R.id.ab_common_tv_right);
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
				startActivityForResult(intent2, Consts.AddressRequestCode);//
			}
		});

		Intent intent = getIntent();
		if(null != intent){
			requestCode = intent.getIntExtra("requestCode", -1);
		}
		
		setContentView(R.layout.address_management);

		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.address_item_main_refresh_scrollview111);

		pullToRefreshListView.setOnRefreshListener(listener2);

		listView = pullToRefreshListView.getRefreshableView();

		myApplication = MyApplication.getInstance();
		userId = myApplication.getUserId();
		mAddresses = new ArrayList<ModelAddresses>();
		adapter = new AddressAdapter(AddressActivity.this, mAddresses);
		
		adapter.setRequestCode(requestCode);
		
		listView.setAdapter(adapter);

		
		httpClientUtil = new HttpClientUtil(this);
		// load data
		getAddressData();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mAddresses.clear();
		mAddresses = null;
		adapter = null;
	}

	private OnRefreshListener2<ListView> listener2 = new OnRefreshListener2<ListView>() {

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			fromIndex = 0;
			isFirstIn = false;
			getAddressData();
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			fromIndex += acount;
			isFirstIn = false;
			getAddressData();
		}
	};

	private int fromIndex = 0;
	private int acount = 10;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			if (requestCode == Consts.AddressRequestCode
					&& resultCode == Consts.AddressResponseCode) {
				try {

					fromIndex = 0;

					pullToRefreshListView.setRefreshing();

				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(AddressActivity.this,
							getResources().getString(R.string.wrong_input),
							Toast.LENGTH_SHORT).show();
				}

			} else if (requestCode == DefinalDate.requestcode
					&& resultCode == DefinalDate.responcode1) {

			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(AddressActivity.this,
					getResources().getString(R.string.bad_network),
					Toast.LENGTH_SHORT).show();

		}
	}

	private void getAddressData() {
		String url = new JNICallBack().getHttp4GetAddress("customer_id%3D"
				+ userId + "+and+valid_flag%3D%27y%27", fromIndex + "", acount
				+ "", "", "", "");
		
		httpClientUtil.setShowErrMessage(true);
		if(isFirstIn) {
			bar = new ProgressDialog(this);
			httpClientUtil.setProgressDialog(bar);
			httpClientUtil.setIsShowLoading(true);
		} else {
			httpClientUtil.setIsShowLoading(false);
			httpClientUtil.setPullToRefreshView(pullToRefreshListView);
		}
		
		httpClientUtil.getHttpResp(url, new IHttpResp() {
			
			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
				ParseAddressJson parseAddressJson = new ParseAddressJson();
				boolean issuccess = parseAddressJson
						.parseAddressListJson(content);
				if (issuccess) {
					ArrayList<ModelAddresses> modelAddresses = parseAddressJson
							.getAddresses();
					if (null != modelAddresses && !modelAddresses.isEmpty()
							&& fromIndex == 0) {
						mAddresses.clear();
					}

					mAddresses.addAll(modelAddresses);
					adapter.notifyDataSetChanged();
					
					String label = getResources().getString(R.string.update_time)
							+ DateUtils.formatDateTime(AddressActivity.this, System.currentTimeMillis(),
									DateUtils.FORMAT_ABBREV_ALL
											| DateUtils.FORMAT_SHOW_DATE
											| DateUtils.FORMAT_SHOW_TIME);
					pullToRefreshListView.getLoadingLayoutProxy()
							.setLastUpdatedLabel(label);
				} else {
					Toast.makeText(AddressActivity.this, getResources().getString(R.string.nomore), Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onError() {
				super.onError();
				fromIndex -= acount;
			}
			
		});
		
	}


	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "地址管理页面");
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "地址管理页面");
	}
}
