package com.yidejia.app.mall.fragment;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.actionbarsherlock.app.SherlockFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.util.AllOrderUtil;

public class AllOrderFragment extends SherlockFragment {
	// private TextView titleTextView;//订单的状态
	// private TextView numberTextView;//订单的编号
	// private TextView sumPrice;//订单的总价格
	// private TextView countTextView;//订单的总数目
	// private LinearLayout mLayout;//外层的布局
	// private View view;
	// private OrderDataManage orderDataManage ;//用来获取订单数据

	private int orderTimeType;
	private int orderType;
	private String defaultHello = "default hello";
	private PullToRefreshScrollView mPullToRefreshScrollView;// 刷新
	private LinearLayout relativeLayout;

	/**
	 * 实例化对象
	 * 
	 * @param view
	 */
	private void setupShow() {
		AllOrderUtil allOrderUtil = new AllOrderUtil(getSherlockActivity(),
				relativeLayout);
		allOrderUtil.loadView(orderTimeType, orderType, fromIndex, amount);

		// mLayout = (LinearLayout)
		// view.findViewById(R.id.all_order_item_main_scrollView_linearlayout1);
		// orderDataManage = new OrderDataManage(getSherlockActivity());
		// titleTextView =
		// (TextView)view.findViewById(R.id.all_order_item_main_item_detail);
		// numberTextView =
		// (TextView)view.findViewById(R.id.all_order_item_main_item_number);
		// sumPrice =
		// (TextView)view.findViewById(R.id.all_order_item_main_sum_money_deatil);
		// countTextView =
		// (TextView)view.findViewById(R.id.all_order_item_main_item_textview7);
	}

	// 通过单例模式，构建对象
	/**
	 * 跳转到fragment
	 * @param orderType 0-4分别表示全部订单，待付款订单，待发货订单，已发货订单，已完成订单
	 * @param orderTimeType 0-2 分别表示近一周，近一月，近一年
	 * @return
	 */
	public static AllOrderFragment newInstance(int orderType, int orderTimeType) {
		AllOrderFragment waitFragment = new AllOrderFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("orderTimeType", orderTimeType);
		bundle.putInt("orderType", orderType);
		waitFragment.setArguments(bundle);
		return waitFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 获取存储的参数
		Bundle args = getArguments();
		orderTimeType = args != null ? args.getInt("orderTimeType") : 0;
		orderType = args != null ? args.getInt("orderType") : 0;
	}

	private int fromIndex = 0;
	private int amount = 10;
	private OnRefreshListener2<ScrollView> listener = new OnRefreshListener2<ScrollView>() {
		// 下拉刷新
		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
			String label = getResources().getString(R.string.update_time)
					+ DateUtils.formatDateTime(getSherlockActivity(),
							System.currentTimeMillis(),
							DateUtils.FORMAT_ABBREV_ALL
									| DateUtils.FORMAT_SHOW_DATE
									| DateUtils.FORMAT_SHOW_TIME);
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			fromIndex = 0;//刷新的index要改为0
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.all_order_item_main, null);// 获取视图对象
		mPullToRefreshScrollView = (PullToRefreshScrollView) view
				.findViewById(R.id.all_order_item_main_refresh_scrollview11);
		relativeLayout = (LinearLayout) view
				.findViewById(R.id.all_order_item_main_scrollView_linearlayout1);// 获取布局

		mPullToRefreshScrollView.setOnRefreshListener(listener);
		String label = getResources().getString(R.string.update_time)
				+ DateUtils.formatDateTime(getSherlockActivity(),
						System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_ALL
								| DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_SHOW_DATE);
		mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		// Log.i("info", allOrderUtil+"");
//		setupShow();//初始化
		// getData();

		// View produce = inflater.inflate(R.layout.all_order_item_produce,
		// null);//产品详细
		// View produce1 = inflater.inflate(R.layout.all_order_item_produce,
		// null);//产品详细
		//
		// relativeLayout.addView(produce);
		// relativeLayout.addView(produce1);
		//
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

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setupShow();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		setupShow();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	// /**
	// * 用来展示数据的
	// */
	// private void getData(){
	// ArrayList<Order> mList = orderDataManage.getOrderArray(514492+"", "", "",
	// "", 0+"", 10+"");
	// for(int i=0;i<mList.size();i++){
	// Order mOrder = mList.get(i);
	// titleTextView.setText(mOrder.getStatus());
	// numberTextView.setText(mOrder.getOrderCode());
	// mLayout.addView(view);
	// }
	// }
}