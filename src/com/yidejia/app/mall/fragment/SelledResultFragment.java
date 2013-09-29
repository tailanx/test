package com.yidejia.app.mall.fragment;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockFragment;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yidejia.app.mall.GoodsInfoActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.adapter.SelledResultListAdapter;
import com.yidejia.app.mall.datamanage.SearchDataManage;
import com.yidejia.app.mall.initview.SRViewWithImage;
import com.yidejia.app.mall.model.SearchItem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SelledResultFragment extends SherlockFragment {
	
	public static SelledResultFragment newInstance(Bundle searchBundle, boolean title){
		
		SelledResultFragment fragment = new SelledResultFragment();
		Bundle bundle = new Bundle();
		bundle.putBoolean("isShowWithList", title);
		bundle.putBundle("bundle", searchBundle);
		fragment.setArguments(bundle);
		
		return fragment;
	}
	
	private boolean isShowWithList;
	private boolean defaultInt = true;
	private String TAG = "SelledResultFragment";
	
	private ListView selledListView;
	private LinearLayout searchResultLayout;
	private PullToRefreshListView mPullToRefreshListView;
	
	private ArrayList<SearchItem> searchItemsArray;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		isShowWithList = (bundle != null)? bundle.getBoolean("isShowWithList"):defaultInt;
		Bundle searchBundle = bundle.getBundle("bundle");
		String name = searchBundle.getString("name");
		String fun = searchBundle.getString("fun");
		String price = searchBundle.getString("price");
		String brand = searchBundle.getString("brand");
		String order = searchBundle.getString("order");
//		getSherlockActivity().getSupportActionBar().setCustomView(R.layout.actionbar_search);
		SearchDataManage manage = new SearchDataManage(getSherlockActivity());
		searchItemsArray = manage.getSearchArray(name, fun, brand, price, order, "0", "10");
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = null;
		
		if(isShowWithList){
			view = inflater.inflate(R.layout.activity_search_result_list_layout, container, false);
			mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
//			selledListView = (ListView) view.findViewById(R.id.search_result_list);
			selledListView = mPullToRefreshListView.getRefreshableView();
			initWithListView(view);
		} else {
			view = inflater.inflate(R.layout.activity_search_image_layout, container, false);
			searchResultLayout = (LinearLayout) view.findViewById(R.id.search_result_layout);
			initWithImageView(view);
		}
		return view;
	}

	private void initWithListView(View view){
//		selledListView.setVisibility(View.VISIBLE);
		SelledResultListAdapter searchListAdapter = new SelledResultListAdapter(getActivity(), searchItemsArray);
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
		
	}
	
	private void initWithImageView(View view){
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
}
