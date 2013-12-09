package com.yidejia.app.mall.fragment;

import java.io.IOException;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.yidejia.app.mall.GoodsInfoActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.SearchResultActivity;
import com.yidejia.app.mall.adapter.SelledResultListAdapter;
import com.yidejia.app.mall.datamanage.SearchDataManage;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.initview.SRViewWithImage;
import com.yidejia.app.mall.model.SearchItem;
import com.yidejia.app.mall.net.search.SearchDataUtil;
import com.yidejia.app.mall.view.ImageLoaderUtil;
import com.yidejia.app.mall.widget.YLProgressDialog;

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
	private boolean isNoMore = false;//判断是否还有更多数据,true为没有更多了
	private ImageLoader imageloader;
	private ImageLoadingListener imageLoadingListener;
	private DisplayImageOptions options;
	
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
//		manage = new SearchDataManage(getSherlockActivity().getApplicationContext());
		Log.e(TAG, "before search");
		ImageLoaderUtil loaderUtil = new ImageLoaderUtil();
		imageloader =  loaderUtil.getImageLoader();
		imageLoadingListener = loaderUtil.getAnimateFirstListener();
		options = loaderUtil.getOptions();
//		try {
//			searchItemsArray = manage.getSearchArray(name, fun, brand, price, order, ""+fromIndex, ""+amount);
//			Log.e(TAG, "after search");
//			isHasResult = true;
//		} catch (NullSearchResultEx e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			isHasResult = false;
//		} catch (Exception e) {
//			// TODO: handle exception
//			isHasResult = false;
//		}
	}

	private LayoutInflater inflater;
	private ViewGroup container;
	private View view = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.inflater = inflater;
		this.container = container;
//		if(!isHasResult){
//			view = inflater.inflate(R.layout.search_empty, container, false);
//			Log.e(TAG, "search_empty");
//			Button guang_btn = (Button) view.findViewById(R.id.guang_button);
//			guang_btn.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					Intent intent = new Intent (getSherlockActivity(),SearchResultActivity.class);
//					Bundle bundle = new Bundle();
//					bundle.putString("title", "全部");
//					bundle.putString("name", "");
//					bundle.putString("price", "");
//					bundle.putString("brand", "");
//					bundle.putString("fun", "");
//					intent.putExtras(bundle);
//					getSherlockActivity().startActivity(intent);
//					getSherlockActivity().finish();
//				}
//			});
//			return view;
//		}
		if(isShowWithList){
			view = inflater.inflate(R.layout.activity_search_result_list_layout, container, false);
			mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
			mPullToRefreshListView.setOnRefreshListener(listviewRefreshListener);
			emptyLayout = (RelativeLayout) view.findViewById(R.id.search_empty);
			selledListView = mPullToRefreshListView.getRefreshableView();
//			mPullToRefreshListView.setRefreshing();
		}
		else {
			view = inflater.inflate(R.layout.activity_search_image_layout, container, false);
			emptyLayout = (RelativeLayout) view.findViewById(R.id.search_empty);
			mPullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.pull_refresh_scrollview);
			searchResultLayout = (LinearLayout) view.findViewById(R.id.search_result_layout);
			mPullToRefreshScrollView.setOnRefreshListener(scrollviewRefreshListener);
//			mPullToRefreshScrollView.setRefreshing();
		}
		return view;
	}
	
	private RelativeLayout emptyLayout;

	private void initWithListView(){
//		selledListView.setVisibility(View.VISIBLE);
		if (isFirstIn) {
			searchListAdapter = new SelledResultListAdapter(getActivity(),
					searchItemsArray,imageloader,imageLoadingListener,options);
			selledListView.setAdapter(searchListAdapter);
		} else {
			searchListAdapter.notifyDataSetChanged();
		}
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
			fromIndex += amount;
//			refreshView.setRefreshing();
//			try {
//				searchItemsArray = manage.getSearchArray(name, fun, brand, price, order, ""+fromIndex, ""+amount);
//				isHasResult = true;
//			} catch (NullSearchResultEx e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				isHasResult = false;
//				if(!isHasResult){
//					mPullToRefreshListView.onRefreshComplete();
//				}
//			}
			closeTask();
			task = new Task();
			task.execute();
//			mPullToRefreshListView.onRefreshComplete();
			Log.e(TAG, "on refreshcomplete");
//			searchListAdapter.notifyDataSetChanged();
		}
	};
	
	private OnRefreshListener<ScrollView> scrollviewRefreshListener = new OnRefreshListener<ScrollView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
			// TODO Auto-generated method stub
			fromIndex += amount;
			searchItemsArray.clear();
//			refreshView.setRefreshing();
//			try {
//				searchItemsArray = manage.getSearchArray(name, fun, brand, price, order, ""+fromIndex, ""+amount);
//				isHasResult = true;
//			} catch (NullSearchResultEx e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				isHasResult = false;
//			}
//			initWithImageView();
			closeTask();
			task = new Task();
			task.execute();
//			mPullToRefreshScrollView.onRefreshComplete();
		}
	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "TestFragment-----onActivityCreated");
		closeTask();
		task = new Task();
		task.execute();
		
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d(TAG, "TestFragment-----onStart");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stu
		super.onDestroy();
		closeTask();
		imageloader.destroy();
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		manage = null;
		searchItemsArray = null;
	}
	
	private void closeTask(){
		if(task != null && AsyncTask.Status.RUNNING == task.getStatus().RUNNING){
			task.cancel(true);
		}
	}
	
	private Task task;
	private SearchDataUtil util = new SearchDataUtil();
	private boolean isFirstIn = true;
	
	private class Task extends AsyncTask<Void, Void, Boolean>{
		private ProgressDialog bar;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (isFirstIn) {
//				bar = new ProgressDialog(getSherlockActivity());
//				bar.setCancelable(true);
//				bar.setMessage(getSherlockActivity().getResources().getString(R.string.loading));
//				bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//				bar.show();
				bar = (ProgressDialog) new YLProgressDialog(
						getSherlockActivity()).createLoadingDialog(
						getSherlockActivity(), null);
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
			if(result){
				if(isHasResult && isNoMore){
					Toast.makeText(getSherlockActivity(), getSherlockActivity().getResources().getString(R.string.nomore), Toast.LENGTH_SHORT).show();
					isNoMore = false;
					if (!isFirstIn) {
						if (isShowWithList)
							mPullToRefreshListView.onRefreshComplete();
						else
							mPullToRefreshScrollView.onRefreshComplete();
					}
					return;
				}
				if(!isHasResult) {
					if(isShowWithList){
//						view = inflater.inflate(R.layout.activity_search_result_list_layout, container, false);
//						mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
						mPullToRefreshListView.setVisibility(View.GONE);
						
						emptyLayout.setVisibility(View.VISIBLE);
					} else {
//						mPullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.pull_refresh_scrollview);
						mPullToRefreshScrollView.setVisibility(View.GONE);
//						emptyLayout = (RelativeLayout) view.findViewById(R.id.search_empty);
						emptyLayout.setVisibility(View.VISIBLE);
					}
					
					Button guang_button = (Button) emptyLayout.findViewById(R.id.guang_button);
					guang_button.setOnClickListener(new OnClickListener() {
						
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
					
					if(isFirstIn){
						bar.dismiss();
						isFirstIn = false;
					}
					return;
				
				}
				if(isShowWithList){
//					view = inflater.inflate(R.layout.activity_search_result_list_layout, container, false);
//					mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
//					selledListView = (ListView) view.findViewById(R.id.search_result_list);
//					if()
					
					initWithListView();
				} else {
//					view = inflater.inflate(R.layout.activity_search_image_layout, container, false);
					
//					mPullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.pull_refresh_scrollview);
//					mPullToRefreshScrollView.onRefreshComplete();
					initWithImageView();
				}
				
			}
			if(isFirstIn){
				bar.dismiss();
				isFirstIn = false;
			} else {
				String label = getResources().getString(R.string.update_time) + DateUtils.formatDateTime(
						getSherlockActivity().getApplicationContext(),
						System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
				if(isShowWithList){
					mPullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
					mPullToRefreshListView.onRefreshComplete();
				}
				else {
					mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
					mPullToRefreshScrollView.onRefreshComplete();
				}
			} 
			if (isTimeout) {
				Toast.makeText(
						getSherlockActivity(),
						getSherlockActivity().getResources()
								.getString(R.string.time_out),
						Toast.LENGTH_SHORT).show();
				isTimeout = false;
				if(bar.isShowing()) bar.dismiss();
			}
		}
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				String httpresp;
				try {
					httpresp = util.getHttpResponseString(name, fun, brand, price, order, fromIndex + "", amount + "");
					boolean isSuccess = util.analysis(httpresp);
					searchItemsArray = util.getSearchResults();
					isHasResult = util.getIsHasRst();
					isNoMore = util.getIsNomore();
					return isSuccess;
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
		
		
	}
	
	private boolean isTimeout = false;
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		imageloader.init(ImageLoaderConfiguration.createDefault(getSherlockActivity()));
		imageloader.stop();
	}
}