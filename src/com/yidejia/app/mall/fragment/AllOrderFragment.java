package com.yidejia.app.mall.fragment;

import java.util.ArrayList;

import org.apache.http.HttpStatus;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.baidu.mobstat.StatService;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.opens.asyncokhttpclient.AsyncHttpResponse;
import com.opens.asyncokhttpclient.AsyncOkHttpClient;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.Order;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.order.ParseOrder;
import com.yidejia.app.mall.util.AllOrderUtil;
import com.yidejia.app.mall.util.OrdersUtil;
import com.yidejia.app.mall.widget.YLProgressDialog;

public class AllOrderFragment extends SherlockFragment {

	private int orderTimeType;
	private int orderType;
	private String TAG = AllOrderFragment.class.getName();
	private PullToRefreshScrollView mPullToRefreshScrollView;// 刷新
	private LinearLayout relativeLayout;
	private MyApplication myApplication;


	// 通过单例模式，构建对象
	/**
	 * 跳转到fragment
	 * 
	 * @param orderType
	 *            0-4分别表示全部订单，待付款订单，待发货订单，已发货订单，已完成订单
	 * @param orderTimeType
	 *            0-2 分别表示近一周，近一月，近一年
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
	
	private int fromIndex = 0;
	private int amount = 10;

	private boolean isFirstOpen = true;// 是否为第一次进入该页面
	private ArrayList<Order> orders;
	private OrdersUtil allOrderUtil;
	private ProgressDialog bar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 获取存储的参数
		Bundle args = getArguments();
		orderTimeType = args != null ? args.getInt("orderTimeType") : 0;
		orderType = args != null ? args.getInt("orderType") : 0;
		myApplication = MyApplication.getInstance();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.all_order_item_main, null);// 获取视图对象
		mPullToRefreshScrollView = (PullToRefreshScrollView) view
				.findViewById(R.id.all_order_item_main_refresh_scrollview11);
		relativeLayout = (LinearLayout) view
				.findViewById(R.id.all_order_item_main_scrollView_linearlayout1);// 获取布局

		mPullToRefreshScrollView.setOnRefreshListener(listener);
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (!ConnectionDetector.isConnectingToInternet(getSherlockActivity())) {
			Toast.makeText(
					getSherlockActivity(),
					getSherlockActivity().getResources().getString(
							R.string.no_network), Toast.LENGTH_LONG).show();
			return;
		}
		fromIndex = 0;
		getOrderData();
	}
	
	
	/**
	 * 获取订单数据
	 */
	private void getOrderData() {
		AllOrderUtil util = new AllOrderUtil();
		
		String url = new JNICallBack().getHttp4GetOrder(myApplication.getUserId(), "",
		String.valueOf(util.getOrderTime(orderTimeType)),
		util.getOrderTpye(orderType), fromIndex + "", amount
				+ "", myApplication.getToken());
		
		AsyncOkHttpClient client = new AsyncOkHttpClient();
		
		client.get(url, new AsyncHttpResponse(){

			@Override
			public void onStart() {
				super.onStart();
				showBar();
			}

			@Override
			public void onFinish() {
				super.onFinish();
				dismissBar();
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				if(statusCode == HttpStatus.SC_OK){
					ParseOrder parseOrder = new ParseOrder(getSherlockActivity());
					boolean isSuccess = parseOrder.parseGetOrders(content);
					if(isSuccess) {
						orders = parseOrder.getOrders();
						dealOrder();
					} else {
						Toast.makeText(getSherlockActivity(),
								getResources().getString(R.string.nomore),
								Toast.LENGTH_LONG).show();
					}
					String label = getResources().getString(R.string.update_time)
							+ DateUtils.formatDateTime(getSherlockActivity(),
									System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_ALL
											| DateUtils.FORMAT_SHOW_DATE
											| DateUtils.FORMAT_SHOW_DATE);
					mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
							label);
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onError(error, content);
				fromIndex -= amount;
				if(fromIndex < 0) fromIndex = 0;
				dismissBar();
			}
			
		});
	}
	
	private void dismissBar() {
		if (isFirstOpen) {
			bar.dismiss();
			isFirstOpen = false;
		} else {
			mPullToRefreshScrollView.onRefreshComplete();
		}
	}
	
	private void showBar() {
		if (isFirstOpen) {
			bar = (ProgressDialog) new YLProgressDialog(
					getSherlockActivity()).createLoadingDialog(
					getSherlockActivity(), null);
			bar.setOnCancelListener(new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
//					cancel(true);
//					client.getOkHttpClient().getConnectionPool().
				}
			});
		}
	}
	
	/**下拉刷新监听**/
	private OnRefreshListener2<ScrollView> listener = new OnRefreshListener2<ScrollView>() {
		// 下拉刷新
		@Override
		public void onPullDownToRefresh(
				PullToRefreshBase<ScrollView> refreshView) {
			if (!ConnectionDetector
					.isConnectingToInternet(getSherlockActivity())) {
				Toast.makeText(
						getSherlockActivity(),
						getSherlockActivity().getResources().getString(
								R.string.no_network), Toast.LENGTH_LONG).show();
				return;
			}
			fromIndex = 0;// 刷新的index要改为0
			getOrderData();
		}

		// 上拉加载
		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
			if (!ConnectionDetector
					.isConnectingToInternet(getSherlockActivity())) {
				Toast.makeText(
						getSherlockActivity(),
						getSherlockActivity().getResources().getString(
								R.string.no_network), Toast.LENGTH_LONG).show();
				return;
			}
			
			fromIndex += amount;
			getOrderData();
		}
	};

	private void dealOrder() {
		if (orders != null && !orders.isEmpty()) {
			if (fromIndex == 0)
				relativeLayout.removeAllViews();// 清空数据
			Log.e(TAG, orderType + "type and length" + orders.size());
			allOrderUtil = new OrdersUtil(getSherlockActivity(),
					relativeLayout);
			allOrderUtil.viewCtrl(orders, orderType);
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPause(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		StatService.onResume(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		
	}

}