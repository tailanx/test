package com.yidejia.app.mall.broadcast;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask.Status;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
//import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
//import com.baidu.mobstat.StatActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.yidejia.app.mall.ActiveGoActivity;
import com.yidejia.app.mall.HistoryActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.MyCollectActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.order.AllOrderActivity;
import com.yidejia.app.mall.phone.PhoneActivity;
import com.yidejia.app.mall.search.SearchActivity;
import com.yidejia.app.mall.skintest.SkinHomeActivity;
import com.yidejia.app.mall.task.MallTask;
import com.yidejia.app.mall.task.MallTask.Task;
import com.yidejia.app.mall.view.IntegeralActivity;
import com.yidejia.app.mall.view.LoginActivity;
import com.yidejia.app.mall.yirihui.YirihuiActivity;

public class MallAction {
	private SherlockFragmentActivity activity;
	private ProgressDialog bar;
	public static boolean isFirstIn = true;
	private View view;
	private LayoutInflater inflater;
	private MallTask mallTask;// 开启一个异步任务用来加载数据
//	private TextView main_mall_notice_content;// 公告
	private PullToRefreshScrollView mPullToRefreshScrollView;
	private FrameLayout frameLayout;
	private Task task;// 异步任务
	private MyApplication myApplication;

	public MallAction(SherlockFragmentActivity activity, FrameLayout layout) {
		this.activity = activity;
		myApplication = (MyApplication) activity.getApplication();
		this.frameLayout = layout;
		this.inflater = LayoutInflater.from(activity);
		view = inflater.inflate(R.layout.activity_main_layout, null);// 加载主界面
		frameLayout.addView(view);
	}

	/**
	 * 设置头部
	 */
	public void setActionBarConfig() {
		activity.getSupportActionBar().setCustomView(
				R.layout.actionbar_main_home_title);

		activity.getSupportActionBar().setDisplayShowCustomEnabled(true);
		activity.getSupportActionBar().setDisplayUseLogoEnabled(false);
		activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		activity.getSupportActionBar().setDisplayShowHomeEnabled(false);

		ImageView searchEditText = (ImageView) activity
				.findViewById(R.id.main_home_title_search);
		// 头部事件监听
		searchEditText.setOnClickListener(go2SearchListener2);

	}

	/**
	 * 头部的点击事件
	 */
	private OnClickListener go2SearchListener2 = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(activity, SearchActivity.class);
			activity.startActivity(intent);
		}
	};

	/**
	 * 创建界面的数据加载
	 */

	public void onActivityCreated() {

		bar = new ProgressDialog(activity, R.style.StyleProgressDialog);
		bar.setCancelable(true);
		bar.setOnCancelListener(new DialogInterface.OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				isFirstIn = false;
				closeTask(task);
			}
		});
		createView(view, inflater);
		if (!ConnectionDetector.isConnectingToInternet(activity)) {
			Toast.makeText(activity.getApplicationContext(),
					activity.getResources().getString(R.string.no_network),
					Toast.LENGTH_LONG).show();
			isFirstIn = false;
			return;
		}
		closeTask(task);
		mallTask = new MallTask(activity, view, frameLayout,mPullToRefreshScrollView);
		task = mallTask.getTask();
	}

	@SuppressWarnings("static-access")
	private void closeTask(Task task) {
		if (task != null && task.getStatus().RUNNING == Status.RUNNING) {
			task.cancel(true);
		}
	}

	/**
	 * activity启动时调用这个方法显示界面
	 * 
	 * @param view
	 * @param inflater
	 */
	private void createView(View view, LayoutInflater inflater) {

		RelativeLayout shorcutLayout = (RelativeLayout) view
				.findViewById(R.id.function_parent_layout);

		View child = inflater.inflate(R.layout.main_function, null);
		shorcutLayout.addView(child);
		functionIntent(child);

		mPullToRefreshScrollView = (PullToRefreshScrollView) view
				.findViewById(R.id.main_pull_refresh_scrollview);
		mPullToRefreshScrollView.setScrollingWhileRefreshingEnabled(true);
		mPullToRefreshScrollView
				.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
		mPullToRefreshScrollView.setVerticalScrollBarEnabled(false); // 禁用垂直滚动
		mPullToRefreshScrollView.setHorizontalScrollBarEnabled(false); // 禁用水平滚动

		String label = activity.getResources().getString(R.string.update_time)
				+ DateUtils.formatDateTime(activity,
						System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
								| DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
		mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
				label);
		mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
				label);
		mPullToRefreshScrollView.setOnRefreshListener(listener);

//		main_mall_notice_content = (TextView) view
//				.findViewById(R.id.main_mall_notice_content);
	}

	/**
	 * 快捷功能那块跳到相应界面
	 * 
	 * @param child
	 */
	private void functionIntent(View child) {
		RelativeLayout myOrder = (RelativeLayout) child
				.findViewById(R.id.function_my_order);// 订单

		myOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intentOrder1 = new Intent(activity,AllOrderActivity.class);
				if (!isLogin()) {

					activity.startActivity(intentOrder1);
				}
			}
		});

		RelativeLayout myCollect = (RelativeLayout) child
				.findViewById(R.id.function_my_favorite);// 收藏

		myCollect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intentOrder = new Intent(activity,
						MyCollectActivity.class);
				if (!isLogin()) {

					activity.startActivity(intentOrder);
				}
			}

		});
		RelativeLayout myEvent = (RelativeLayout) child
				.findViewById(R.id.function_event);// 活动馆

		myEvent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(activity, ActiveGoActivity.class);
				activity.startActivity(intent);
			}
		});
		RelativeLayout myMember = (RelativeLayout) child
				.findViewById(R.id.function_member);// 会员购

		myMember.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(activity, PhoneActivity.class);
				activity.startActivity(intent);
			}
		});
		RelativeLayout myHistory = (RelativeLayout) child
				.findViewById(R.id.function_history);// 浏览历史

		myHistory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intentOrder = new Intent(activity, HistoryActivity.class);
				activity.startActivity(intentOrder);
			}
		});
		RelativeLayout myCoupon = (RelativeLayout) child
				.findViewById(R.id.function_coupon);// 积分卡券

		myCoupon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(activity, YirihuiActivity.class);
				activity.startActivity(intent);

			}
		});
		RelativeLayout mySkin = (RelativeLayout) child
				.findViewById(R.id.function_skin);// 肌肤测试

		mySkin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intentOrder = new Intent(activity,
						IntegeralActivity.class);
				if (!isLogin()) {

					activity.startActivity(intentOrder);
				}

			}
		});
		RelativeLayout myMessage = (RelativeLayout) child
				.findViewById(R.id.function_message);// 消息中心

		myMessage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intentOrder = new Intent(activity,
						SkinHomeActivity.class);
				activity.startActivity(intentOrder);
			}
		});

	}

	private OnRefreshListener<ScrollView> listener = new OnRefreshListener<ScrollView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
			try {
				if (!ConnectionDetector.isConnectingToInternet(activity)) {
					Toast.makeText(
							activity,
							activity.getResources().getString(
									R.string.no_network), Toast.LENGTH_LONG)
							.show();
					mPullToRefreshScrollView.onRefreshComplete();
					return;
				}
				// closeTask();
				@SuppressWarnings("unused")
				MallTask mall = new MallTask(activity, view, frameLayout, mPullToRefreshScrollView);
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(
						activity,
						activity.getResources().getString(R.string.bad_network),
						Toast.LENGTH_SHORT).show();
				mPullToRefreshScrollView.onRefreshComplete();
			}
		}

	};

	private boolean isLogin() {
		if (!myApplication.getIsLogin()) {
			Toast.makeText(activity,
					activity.getResources().getString(R.string.please_login),
					Toast.LENGTH_LONG).show();
			Intent intent1 = new Intent(activity, LoginActivity.class);
			activity.startActivity(intent1);
			return true;
		}

		else {
			return false;
		}
	}

	public void onResume() {
		mallTask.onStart();
	}

	public void onPause() {
		mallTask.onPause();
	}
}
