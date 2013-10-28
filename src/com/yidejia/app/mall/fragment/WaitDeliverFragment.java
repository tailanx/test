package com.yidejia.app.mall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.actionbarsherlock.app.SherlockFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.util.WaitDeliverUtil;
import com.yidejia.app.mall.view.OrderDetailActivity;

public class WaitDeliverFragment extends SherlockFragment {
	private String hello;
	private String defaultHello = "default hello";
	private PullToRefreshScrollView mPullToRefreshScrollView;// 刷新
	private LinearLayout relativeLayout;
	private int fromIndex = 0;
	private int amount = 10;

	private void setupShow() {
		WaitDeliverUtil waitDeliverUtil = new WaitDeliverUtil(
				getSherlockActivity(), relativeLayout);
		waitDeliverUtil.loadView(fromIndex, amount);
	}

	// 通过单例模式，构建对象
	public static WaitDeliverFragment newInstance(String s) {
		WaitDeliverFragment waitFragment = new WaitDeliverFragment();
		Bundle bundle = new Bundle();
		bundle.putString("Hello", s);
		waitFragment.setArguments(bundle);
		return waitFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 获取存储的参数
		Bundle args = getArguments();
		hello = args != null ? args.getString("hello") : defaultHello;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.wait_deliver_item_main, null);// 获取视图对象
//		setupShow();
		mPullToRefreshScrollView = (PullToRefreshScrollView) view
				.findViewById(R.id.wait_deliver_order_item_main_refresh_scrollview11);
		relativeLayout = (LinearLayout) view
				.findViewById(R.id.wait_deliver_order_scrollView_linearlayout1);// 获取布局
//		setupShow();
		mPullToRefreshScrollView.setOnRefreshListener(listener);
		String label = getResources().getString(R.string.update_time)
				+ DateUtils.formatDateTime(getSherlockActivity(),
						System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_ALL
								| DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_SHOW_DATE);
		mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
				label);
		// View produce = inflater.inflate(R.layout.all_order_item_produce,
		// null);//产品详细
		// View produce1 = inflater.inflate(R.layout.all_order_item_produce,
		// null);//产品详细
		//
		// relativeLayout.addView(produce);
		// relativeLayout.addView(produce1);
		// produce1.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// Intent intent = new
		// Intent(getSherlockActivity(),OrderDetailActivity.class);
		//
		// startActivity(intent);
		// }
		// });
		// //添加监听
		// produce.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// Intent intent = new
		// Intent(getSherlockActivity(),OrderDetailActivity.class);
		//
		// startActivity(intent);
		// }
		// });
		return view;
	}

	private OnRefreshListener2<ScrollView> listener = new OnRefreshListener2<ScrollView>() {
		// 下拉刷新
		@Override
		public void onPullDownToRefresh(
				PullToRefreshBase<ScrollView> refreshView) {
			String label = getResources().getString(R.string.update_time)
					+ DateUtils.formatDateTime(getSherlockActivity(),
							System.currentTimeMillis(),
							DateUtils.FORMAT_ABBREV_ALL
									| DateUtils.FORMAT_SHOW_DATE
									| DateUtils.FORMAT_SHOW_TIME);
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			fromIndex = 0;
			relativeLayout.removeAllViews();//清空数据
			setupShow();
			mPullToRefreshScrollView.onRefreshComplete();

		}

		// 上拉加载
		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
			// TODO Auto-generated method stub
			String label = getResources().getString(R.string.update_time)
					+ DateUtils.formatDateTime(getSherlockActivity(),
							System.currentTimeMillis(),
							DateUtils.FORMAT_ABBREV_ALL
									| DateUtils.FORMAT_SHOW_DATE
									| DateUtils.FORMAT_SHOW_TIME);
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			fromIndex += amount;
			setupShow();
			mPullToRefreshScrollView.onRefreshComplete();

		}
	};
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setupShow();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}