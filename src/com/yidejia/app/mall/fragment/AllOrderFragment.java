package com.yidejia.app.mall.fragment;

import java.io.IOException;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.os.AsyncTask;
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
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.AnlsGetOrderData;
import com.yidejia.app.mall.model.Order;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.order.GetOrderList;
import com.yidejia.app.mall.util.AllOrderUtil;
import com.yidejia.app.mall.util.OrdersUtil;

public class AllOrderFragment extends SherlockFragment {

	private int orderTimeType;
	private int orderType;
	private String TAG = AllOrderFragment.class.getName();
	private PullToRefreshScrollView mPullToRefreshScrollView;// 刷新
	private LinearLayout relativeLayout;
	private MyApplication myApplication;
	private TaskGetOrder taskGetOrder;

	/**
	 * 实例化对象
	 * 
	 * @param view
	 */
	private void setupShow() {
		AllOrderUtil allOrderUtil = new AllOrderUtil(getSherlockActivity(),
				relativeLayout);
		allOrderUtil.loadView(orderTimeType, orderType, fromIndex, amount);
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
		myApplication = (MyApplication) getSherlockActivity().getApplication();
	}

	private int fromIndex = 0;
	private int amount = 10;
	private OnRefreshListener2<ScrollView> listener = new OnRefreshListener2<ScrollView>() {
		// 下拉刷新
		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
			if(!ConnectionDetector.isConnectingToInternet(getSherlockActivity())) {
				Toast.makeText(getSherlockActivity(), getSherlockActivity().getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
				return;
			}
			String label = getResources().getString(R.string.update_time)
					+ DateUtils.formatDateTime(getSherlockActivity(),
							System.currentTimeMillis(),
							DateUtils.FORMAT_ABBREV_ALL
									| DateUtils.FORMAT_SHOW_DATE
									| DateUtils.FORMAT_SHOW_TIME);
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			fromIndex = 0;//刷新的index要改为0
//			relativeLayout.removeAllViews();//清空数据
//			setupShow();
//			isFirstOpen = true;//刷新时改变状态用来显示bar
//			mPullToRefreshScrollView.onRefreshComplete();
			if(taskGetOrder != null && taskGetOrder.getStatus() == AsyncTask.Status.RUNNING){
				taskGetOrder.cancel(true);
			}
			taskGetOrder = new TaskGetOrder();
			taskGetOrder.execute();
		}

		// 上拉加载
		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
			// TODO Auto-generated method stub
			if(!ConnectionDetector.isConnectingToInternet(getSherlockActivity())) {
				Toast.makeText(getSherlockActivity(), getSherlockActivity().getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
				return;
			}
			String label = getResources().getString(R.string.update_time)
					+ DateUtils.formatDateTime(getSherlockActivity(),
							System.currentTimeMillis(),
							DateUtils.FORMAT_ABBREV_ALL
									| DateUtils.FORMAT_SHOW_DATE
									| DateUtils.FORMAT_SHOW_TIME);
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			fromIndex += amount;
//			setupShow();
//			mPullToRefreshScrollView.onRefreshComplete();
			if(taskGetOrder != null && taskGetOrder.getStatus() == AsyncTask.Status.RUNNING){
				taskGetOrder.cancel(true);
			}
			taskGetOrder = new TaskGetOrder();
			taskGetOrder.execute();
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
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
//		setupShow();
//		mPullToRefreshScrollView.setPullToRefreshEnabled(true);
//		mPullToRefreshScrollView.setRefreshing();
		if(!ConnectionDetector.isConnectingToInternet(getSherlockActivity())) {
			Toast.makeText(getSherlockActivity(), getSherlockActivity().getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return;
		}
		if(taskGetOrder != null && taskGetOrder.getStatus() == AsyncTask.Status.RUNNING){
			taskGetOrder.cancel(true);
		}
		taskGetOrder = new TaskGetOrder();
		taskGetOrder.execute();
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
		if(taskGetOrder != null && taskGetOrder.getStatus() == AsyncTask.Status.RUNNING){
			taskGetOrder.cancel(true);
		}
		if(allOrderUtil != null)
			allOrderUtil.cancelTask();
	}
	
	private boolean isFirstOpen = true;//是否为第一次进入该页面
	private ArrayList<Order> orders;
	private OrdersUtil allOrderUtil;
	
	private class TaskGetOrder extends AsyncTask<Void, Void, Boolean>{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(isFirstOpen){
				bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			bar.setMessage(context.getResources().getString(R.string.searching));
				bar.show();
			}
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			GetOrderList getList = new GetOrderList(getSherlockActivity());
			try {
				AllOrderUtil util = new AllOrderUtil();
				
//				String httpResultString = getList.getHttpResponse(userId,
//						code, the_day, status, offset, limit, token);
				String httpResultString = getList.getHttpResponse(myApplication.getUserId(), "",
						String.valueOf(util.getOrderTime(orderTimeType)),
						util.getOrderTpye(orderType), fromIndex + "", amount + "",
						myApplication.getToken());
				AnlsGetOrderData task = new AnlsGetOrderData(getSherlockActivity(), relativeLayout, orderType);
				orders = task.analysisGetListJson(httpResultString);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, "task getlist ioex");
				return false;
			} catch (NumberFormatException e) {
				// TODO: handle exception
				e.printStackTrace();
				// Toast.makeText(context, "�������", Toast.LENGTH_SHORT).show();
				return false;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				Log.e(TAG, "task getlist other ex");
				return false;
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			 if(result){
				 if(fromIndex == 0) relativeLayout.removeAllViews();//清空数据
				 allOrderUtil = new OrdersUtil(getSherlockActivity(), relativeLayout);
				 if(!orders.isEmpty()){
					 Log.e(TAG, orderType + "type and length" + orders.size());
					 allOrderUtil.viewCtrl(orders, orderType);
				 }
			 }
			// Toast.makeText(context, "�ɹ�", Toast.LENGTH_SHORT).show();
			if(isFirstOpen){
				bar.dismiss();
				isFirstOpen = false;
			} else {
				mPullToRefreshScrollView.onRefreshComplete();
			}
		}

		private ProgressDialog bar = new ProgressDialog(getSherlockActivity());
	}
//	}
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