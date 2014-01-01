package com.yidejia.app.mall.main;

import java.io.IOException;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.MainFragmentActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.SearchActivity;
import com.yidejia.app.mall.SearchResultActivity;
import com.yidejia.app.mall.adapter.SearchListAdapter;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.model.Function;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.search.EffectDataUtil;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.widget.YLProgressDialog;

public class HomeSearchActivity extends SherlockFragmentActivity implements
		OnClickListener {
	private LayoutInflater inflater;
	private View view;
	private MyApplication myApplication;

	private ListView searchListView;
	private RelativeLayout search_item_refresh_view;
	private ImageView refresh_data_btn;
	private SearchListAdapter searchListAdapter;

	private ArrayList<Function> functions;
	private int search;
	private int defaultInt = -1;
	private String TAG = "SearchFragment";
	private FrameLayout frameLayout;

	private Task task;
	private InnerReceiver receiver;

	private void closeTask() {
		if (task != null
				&& task.getStatus().RUNNING == AsyncTask.Status.RUNNING) {
			task.cancel(true);
		}
	}

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		receiver = new InnerReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Consts.UPDATE_CHANGE);
		registerReceiver(receiver, filter);
		
		Log.e(TAG, "search onCreate");
		
		if(null != arg0) {
			functions = (ArrayList<Function>) arg0.getSerializable("funs");
		}
		
		cartsDataManage = new CartsDataManage();
		myApplication = (MyApplication) getApplication();
		setContentView(R.layout.activity_main_fragment_layout);
		frameLayout = (FrameLayout) findViewById(R.id.main_fragment);
		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.activity_search_item_list, null);// 加载fragment的内容
		frameLayout.addView(view);
		searchListView = (ListView) view.findViewById(R.id.search_result_list);// 初始化

		search_item_refresh_view = (RelativeLayout) view
				.findViewById(R.id.search_item_refresh_view);
		refresh_data_btn = (ImageView) view.findViewById(R.id.refresh_data_btn);
		res = getResources();
		initNavView();
		setActionBarConfig();

		if (!ConnectionDetector.isConnectingToInternet(HomeSearchActivity.this)) {
			searchListView.setVisibility(View.GONE);
			search_item_refresh_view.setVisibility(View.VISIBLE);
		} else {
			if ((null == functions) || (functions.isEmpty())) {
				functions = new ArrayList<Function>();
				Log.e(TAG, "search on create get task");
				closeTask();
				task = new Task();
				task.execute();
			}
		}

		if (null != bar) {
			bar.setOnCancelListener(new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
					closeTask();
					if (functions.isEmpty()) {
						searchListView.setVisibility(View.GONE);
						search_item_refresh_view.setVisibility(View.VISIBLE);
					}
				}
			});
		}
		if (null != refresh_data_btn) {
			refresh_data_btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					closeTask();
					task = new Task();
					task.execute();
				}
			});
		}
	}

	private ImageView searchText;

	/**
	 * 头部
	 */
	private void setActionBarConfig() {
		down_search_textview.setTextColor(res.getColor(R.color.white));
		downSearchLayout.setBackgroundResource(R.drawable.down_hover1);
		// down_search_imageView.setPressed(true);
		down_search_imageView.setImageResource(R.drawable.down_search_hover);
		// down_search_TextView.setTextColor(Color.WHITE);
		getSupportActionBar().setCustomView(R.layout.actionbar_search);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);

		searchText = (ImageView) findViewById(R.id.search_bar_edittext);
		searchText.setOnClickListener(go2SearchListener2);
		
	}

	private RelativeLayout downHomeLayout;
	private RelativeLayout downGuangLayout;
	private RelativeLayout downSearchLayout;
	private RelativeLayout downShoppingLayout;
	private RelativeLayout downMyLayout;
	private ImageView down_home_imageView;// 首页按钮图片
	private ImageView down_guang_imageView;// 逛按钮图片
	private ImageView down_search_imageView;// 搜索按钮图片
	private ImageView down_shopping_imageView; // 购物车按钮图片
	private ImageView down_my_imageView; // 我的商城按钮图片
	private CartsDataManage cartsDataManage;
	private TextView down_home_textview;
	private TextView down_guang_textview;
	private TextView down_search_textview;
	private TextView down_shopping_textview;
	private TextView down_my_textview;
	private Resources res;
	private int number;
	private Button cartImage;
	/**
	 * 初始化底部导航栏
	 */
	private void initNavView() {
		// 改变底部首页背景，有按下去的效果的背景
		number = cartsDataManage.getCartAmount();
		cartImage = (Button) findViewById(R.id.down_shopping_cart);
		if (number == 0) {
			cartImage.setVisibility(View.GONE);
		} else {
			cartImage.setText(number + "");
		}
		downGuangLayout = (RelativeLayout) findViewById(R.id.down_guang_layout);
		downHomeLayout = (RelativeLayout) findViewById(R.id.down_home_layout);
		down_home_imageView = (ImageView) findViewById(R.id.down_home_icon);
		down_search_imageView = (ImageView) findViewById(R.id.down_search_icon);
		down_home_textview = (TextView) findViewById(R.id.down_home_text);
		down_search_textview = (TextView) findViewById(R.id.down_search_text);

		downGuangLayout = (RelativeLayout) findViewById(R.id.down_guang_layout);
		downSearchLayout = (RelativeLayout) findViewById(R.id.down_search_layout);
		downShoppingLayout = (RelativeLayout) findViewById(R.id.down_shopping_layout);
		downMyLayout = (RelativeLayout) findViewById(R.id.down_my_layout);

		downHomeLayout.setOnClickListener(this);
//		downSearchLayout.setOnClickListener(this);
		downShoppingLayout.setOnClickListener(this);
		downMyLayout.setOnClickListener(this);

		down_home_textview.setTextColor(this.getResources().getColor(
				R.color.white_white));
		downHomeLayout.setBackgroundResource(R.drawable.downbg);
		down_home_imageView.setImageResource(R.drawable.home_normal);

		down_search_textview.setTextColor(res.getColor(R.color.white));
		downSearchLayout.setBackgroundResource(R.drawable.down_hover1);
		down_search_imageView.setImageResource(R.drawable.down_search_hover);
		downGuangLayout.setVisibility(ViewGroup.GONE);
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.e(TAG, "TestFragment-----onStart");
//		if(functions == null || functions.isEmpty()){
//			closeTask();
//			task = new Task();
//			task.execute();
//		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		closeTask();
		unregisterReceiver(receiver);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.down_home_layout:
			intent.setClass(HomeSearchActivity.this, HomeMallActivity.class);
			break;
		case R.id.down_shopping_layout:
			intent.setClass(HomeSearchActivity.this, HomeCarActivity.class);
			break;
		case R.id.down_my_layout:
			if (myApplication.getIsLogin())
				intent.setClass(HomeSearchActivity.this, HomeMyMaActivity.class);
			else
				intent.setClass(HomeSearchActivity.this, HomeLogActivity.class);
			break;
		}
		HomeSearchActivity.this.startActivity(intent);
		overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
	}

	private ProgressDialog bar;

	private class Task extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// bar.show();
			bar = (ProgressDialog) new YLProgressDialog(HomeSearchActivity.this)
					.createLoadingDialog(HomeSearchActivity.this, null);
			bar.setOnCancelListener(new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
					cancel(true);
				}
			});
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			EffectDataUtil util = new EffectDataUtil();
			try {
				String httpresp;
				try {
					httpresp = util.getHttpResponseString();
					boolean issuccess = util.analysis(httpresp);
					functions = util.getFunctions();
					return issuccess;
				} catch (TimeOutEx e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					isTimeout = true;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result) {
				searchListView.setVisibility(View.VISIBLE);
				search_item_refresh_view.setVisibility(View.GONE);
				searchListAdapter = new SearchListAdapter(
						HomeSearchActivity.this, functions);
				searchListView.setAdapter(searchListAdapter);
				searchListView
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								// TODO Auto-generated method stub
								// getSherlockActivity().getSupportActionBar().setCustomView(R.layout.actionbar_common);
								listener(arg2);
							}
						});
			} else {
				if (functions.isEmpty()) {
					searchListView.setVisibility(View.GONE);
					search_item_refresh_view.setVisibility(View.VISIBLE);
				}
				if (isTimeout) {
					Toast.makeText(
							HomeSearchActivity.this,
							HomeSearchActivity.this.getResources().getString(
									R.string.time_out), Toast.LENGTH_SHORT)
							.show();
					isTimeout = false;
				}
			}
			bar.dismiss();
		}

	}

	private boolean isTimeout = false;

	/**
	 * 搜索项的监听函数
	 * 
	 * @param position
	 */
	private void listener(int position) {
		Intent intent = new Intent(HomeSearchActivity.this,
				SearchResultActivity.class);
		Bundle bundle = new Bundle();
		// bundle.putString("fun", arg0.getItemAtPosition(arg2).toString());
		if (!functions.isEmpty()) {
			if (position == 0) {
				bundle.putString("fun", "");
				bundle.putString("title",
						getResources().getString(R.string.filter_all));
			} else {
				bundle.putString("fun", functions.get(position - 1).getFunId());
				bundle.putString("title", functions.get(position - 1)
						.getFunName());
			}
		}
		bundle.putString("name", "");
		bundle.putString("price", "");
		bundle.putString("brand", "");
		intent.putExtras(bundle);
		startActivity(intent);

	}

	/**
	 * 头部的跳转
	 */
	private OnClickListener go2SearchListener2 = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(HomeSearchActivity.this,
					SearchActivity.class);
			startActivity(intent);
		}
	};
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("funs", functions);
	};

	// 双击返回键退出程序
	private long exitTime = 0;

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.exit),
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				// ((MyApplication)getApplication()).setUserId("");
				// ((MyApplication)getApplication()).setToken("");
				finish();
				// System.exit(0);
			}
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	private class InnerReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (Consts.UPDATE_CHANGE.equals(action)) {
				number = cartsDataManage.getCartAmount();
				cartImage.setVisibility(View.VISIBLE);
				cartImage.setText(number + "");
			}
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		StatService.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		StatService.onResume(this);
	}

}
