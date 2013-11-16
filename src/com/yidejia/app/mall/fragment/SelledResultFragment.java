package com.yidejia.app.mall.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.actionbarsherlock.app.SherlockFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.yidejia.app.mall.GoodsInfoActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.SearchResultActivity;
import com.yidejia.app.mall.adapter.SelledResultListAdapter;
import com.yidejia.app.mall.datamanage.SearchDataManage;
import com.yidejia.app.mall.exception.NullSearchResultEx;
import com.yidejia.app.mall.initview.SRViewWithImage;
import com.yidejia.app.mall.model.SearchItem;

public class SelledResultFragment extends SherlockFragment {
	
	public static SelledResultFragment newInstance(Bundle searchBundle, boolean title){
		
		SelledResultFragment fragment = new SelledResultFragment();
		Bundle bundle = new Bundle();
		bundle.putBoolean("isShowWithList", title);
		bundle.putBundle("bundle", searchBundle);
		fragment.setArguments(bundle);
		bundle = null;
		return fragment;
	}
	
	private boolean isShowWithList;
	private boolean defaultInt = true;
	private String TAG = "SelledResultFragment";
	
	private ListView selledListView;
	private SelledResultListAdapter searchListAdapter;
	private LinearLayout searchResultLayout;
	private PullToRefreshListView mPullToRefreshListView;
	private PullToRefreshScrollView mPullToRefreshScrollView;
	
	private SearchDataManage manage;
	private ArrayList<SearchItem> searchItemsArray;
	
	private int fromIndex = 0;//开始
	private int amount = 10;//个数
	private String name;//名称
	private String fun;//功效
	private String price;//价格区间
	private String brand;//品牌
	private String order;//排序
	
	private boolean isHasResult = true;//搜索是否有结果
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		isShowWithList = (bundle != null)? bundle.getBoolean("isShowWithList"):defaultInt;
		Bundle searchBundle = bundle.getBundle("bundle");
		name = searchBundle.getString("name");
		fun = searchBundle.getString("fun");
		price = searchBundle.getString("price");
		brand = searchBundle.getString("brand");
		order = searchBundle.getString("order");
//		getSherlockActivity().getSupportActionBar().setCustomView(R.layout.actionbar_search);
		Log.e(TAG, "new search");
		manage = new SearchDataManage(getSherlockActivity().getApplicationContext());
		Log.e(TAG, "before search");
		try {
			searchItemsArray = manage.getSearchArray(name, fun, brand, price, order, ""+fromIndex, ""+amount);
			Log.e(TAG, "after search");
			isHasResult = true;
		} catch (NullSearchResultEx e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isHasResult = false;
		} catch (Exception e) {
			// TODO: handle exception
			isHasResult = false;
		}
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = null;
		if(!isHasResult){
			view = inflater.inflate(R.layout.search_empty, container, false);
			Log.e(TAG, "search_empty");
			Button guang_btn = (Button) view.findViewById(R.id.guang_button);
			guang_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent (getSherlockActivity(),SearchResultActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("title", "全部");
					bundle.putString("name", "");
					bundle.putString("price", "");
					bundle.putString("brand", "");
					bundle.putString("fun", "");
					intent.putExtras(bundle);
					getSherlockActivity().startActivity(intent);
					getSherlockActivity().finish();
				}
			});
			return view;
		}
		if(isShowWithList){
			view = inflater.inflate(R.layout.activity_search_result_list_layout, container, false);
			mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
//			selledListView = (ListView) view.findViewById(R.id.search_result_list);
			selledListView = mPullToRefreshListView.getRefreshableView();
			mPullToRefreshListView.setOnRefreshListener(listviewRefreshListener);
			initWithListView();
		} else {
			view = inflater.inflate(R.layout.activity_search_image_layout, container, false);
			searchResultLayout = (LinearLayout) view.findViewById(R.id.search_result_layout);
			mPullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.pull_refresh_scrollview);
			mPullToRefreshScrollView.setOnRefreshListener(scrollviewRefreshListener);
//			mPullToRefreshScrollView.onRefreshComplete();
			initWithImageView();
		}
		return view;
	}

	private void initWithListView(){
//		selledListView.setVisibility(View.VISIBLE);
		searchListAdapter = new SelledResultListAdapter(getActivity(), searchItemsArray);
		selledListView.setAdapter(searchListAdapter);
		selledListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), GoodsInfoActivity.class);
				intent.putExtra("goodsId", searchItemsArray.get(arg2 - 1).getUId());
				startActivity(intent);
			}
		});
//		mPullToRefreshListView.onRefreshComplete();
	}
	
	private void initWithImageView(){
//		selledListView.setVisibility(View.GONE);
		int count = searchItemsArray.size();
		SRViewWithImage srViewWithImage = null;
		boolean isOdd = (count%2 == 0) ? false : true;
		for (int i = 0; i < count; i++) {
			View child = null;
			if(isOdd && i == count -1){
				child = getActivity().getLayoutInflater().inflate(R.layout.item_search_result_image_odd, null);
				srViewWithImage = new SRViewWithImage(getSherlockActivity(), child);
				srViewWithImage.initView(searchItemsArray.get(i));
			} else {
				child = getActivity().getLayoutInflater().inflate(R.layout.item_search_result_image_even, null);
				srViewWithImage = new SRViewWithImage(getSherlockActivity(), child);
				srViewWithImage.initView(searchItemsArray.get(i), searchItemsArray.get(i + 1));
				i++;
			}
			searchResultLayout.addView(child);
			
		}
	}
	
	private OnRefreshListener<ListView> listviewRefreshListener = new OnRefreshListener<ListView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			// TODO Auto-generated method stub
			String label = getResources().getString(R.string.update_time) + DateUtils.formatDateTime(
					getSherlockActivity().getApplicationContext(),
					System.currentTimeMillis(),
					DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			fromIndex += amount;
			try {
				searchItemsArray = manage.getSearchArray(name, fun, brand, price, order, ""+fromIndex, ""+amount);
				isHasResult = true;
			} catch (NullSearchResultEx e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				isHasResult = false;
				if(!isHasResult){
					mPullToRefreshListView.onRefreshComplete();
				}
			}
			mPullToRefreshListView.onRefreshComplete();
			Log.e(TAG, "on refreshcomplete");
//			searchListAdapter.notifyDataSetChanged();
		}
	};
	
	private OnRefreshListener<ScrollView> scrollviewRefreshListener = new OnRefreshListener<ScrollView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
			// TODO Auto-generated method stub
			String label = getResources().getString(R.string.update_time) + DateUtils.formatDateTime(
					getSherlockActivity().getApplicationContext(),
					System.currentTimeMillis(),
					DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			fromIndex += amount;
			searchItemsArray.clear();
			try {
				searchItemsArray = manage.getSearchArray(name, fun, brand, price, order, ""+fromIndex, ""+amount);
				isHasResult = true;
			} catch (NullSearchResultEx e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				isHasResult = false;
			}
			initWithImageView();
			mPullToRefreshScrollView.onRefreshComplete();
		}
	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "TestFragment-----onActivityCreated");
		
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d(TAG, "TestFragment-----onStart");
	}


	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		manage = null;
		searchItemsArray = null;
	}
	
	
}
