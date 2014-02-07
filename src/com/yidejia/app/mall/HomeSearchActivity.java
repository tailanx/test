package com.yidejia.app.mall;

import java.util.ArrayList;

import org.apache.http.HttpStatus;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

import com.baidu.mobstat.StatService;
import com.opens.asyncokhttpclient.AsyncHttpResponse;
import com.opens.asyncokhttpclient.AsyncOkHttpClient;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.adapter.SearchListAdapter;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.Function;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.search.ParseSearchJson;
import com.yidejia.app.mall.search.SearchActivity;
import com.yidejia.app.mall.search.SearchResultActivity;

public class HomeSearchActivity extends HomeBaseActivity {
	private LayoutInflater inflater;
	private View view;

	private ListView searchListView;
	private RelativeLayout search_item_refresh_view;
	private ImageView refresh_data_btn;
	private SearchListAdapter searchListAdapter;

	private ArrayList<Function> functions;
	private String TAG = "SearchFragment";
	private FrameLayout frameLayout;


	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		Log.e(TAG, "search onCreate");

		if (null != arg0) {
			functions = (ArrayList<Function>) arg0.getSerializable("funs");
		}

		setCurrentActivityId(2);

		setContentView(R.layout.activity_main_fragment_layout);
		frameLayout = (FrameLayout) findViewById(R.id.main_fragment);
		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.activity_search_item_list, null);// 加载fragment的内容
		frameLayout.addView(view);
		searchListView = (ListView) view.findViewById(R.id.search_result_list);// 初始化

		search_item_refresh_view = (RelativeLayout) view
				.findViewById(R.id.search_item_refresh_view);
		refresh_data_btn = (ImageView) view.findViewById(R.id.refresh_data_btn);

		setActionBarConfig();


		if (null != bar) {
			bar.setOnCancelListener(new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
//					closeTask();
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
					getData();
				}
			});
		}
		if(null == functions || functions.isEmpty())
		getData();
	}

	private ImageView searchText;

	/**
	 * 头部
	 */
	private void setActionBarConfig() {
		getSupportActionBar().setCustomView(R.layout.actionbar_search);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);

		searchText = (ImageView) findViewById(R.id.search_bar_edittext);
		searchText.setOnClickListener(go2SearchListener2);
	}

	private void getData(){
		
		if (!ConnectionDetector.isConnectingToInternet(HomeSearchActivity.this)) {
			searchListView.setVisibility(View.GONE);
			search_item_refresh_view.setVisibility(View.VISIBLE);
			return;
		} 
		
		String url = new JNICallBack().getHttp4GetEffect("flag%3D%27y%27", "0", "20", "", "", "%2A");
		
		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.get(url, new AsyncHttpResponse(){

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				if(HttpStatus.SC_OK == statusCode){
					ParseSearchJson parseSearchJson = new ParseSearchJson();
					boolean isSuccess = parseSearchJson.parseFunJson(content);
					if(isSuccess){
						searchListView.setVisibility(View.VISIBLE);
						search_item_refresh_view.setVisibility(View.GONE);
						functions = parseSearchJson.getFunctions();
						searchListAdapter = new SearchListAdapter(
								HomeSearchActivity.this, functions);
						searchListView.setAdapter(searchListAdapter);
						searchListView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								listener(arg2);
							}
						});
					} else {
						//TODO
					}
				} else {
					//TODO
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onError(error, content);
			}
			
		});
		
	}

	private ProgressDialog bar;


	/**
	 * 搜索项的监听函数
	 * 
	 * @param position
	 */
	private void listener(int position) {
		Intent intent = new Intent(HomeSearchActivity.this,
				SearchResultActivity.class);
		Bundle bundle = new Bundle();
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

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPageEnd(this, getString(R.string.searchLabel));
	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, getString(R.string.searchLabel));
	}

}
