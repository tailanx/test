package com.yidejia.app.mall.phone;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.order.Order;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;

public class PhoneOrderActivity extends BaseActivity {
	private PullToRefreshScrollView mPullToRefreshScrollView;
	private LinearLayout linearLayout;
	private int fromIndex = 0;
	private int amount = 10;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.all_order_item_main);
		setActionbarConfig();
		setTitle(getResources().getString(R.string.phone_order_title));
		mPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.all_order_item_main_refresh_scrollview11);
		linearLayout = (LinearLayout) findViewById(R.id.all_order_item_main_scrollView_linearlayout1);
		mPullToRefreshScrollView.setOnRefreshListener(listener);
		getOrderData();
	}

	private OnRefreshListener2<ScrollView> listener = new OnRefreshListener2<ScrollView>() {
		/**
		 * 下拉刷新
		 * 
		 * @param refreshView
		 */
		@Override
		public void onPullDownToRefresh(
				PullToRefreshBase<ScrollView> refreshView) {
			if (!ConnectionDetector
					.isConnectingToInternet(PhoneOrderActivity.this)) {
				Toast.makeText(
						PhoneOrderActivity.this,
						PhoneOrderActivity.this.getResources().getString(
								R.string.no_network), Toast.LENGTH_LONG).show();
				return;
			}
			fromIndex = 0;// 刷新的index要改为0
			getOrderData();
		}

		/**
		 * 上拉加载
		 * 
		 * @param refreshView
		 */

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
			if (!ConnectionDetector
					.isConnectingToInternet(PhoneOrderActivity.this)) {
				Toast.makeText(
						PhoneOrderActivity.this,
						PhoneOrderActivity.this.getResources().getString(
								R.string.no_network), Toast.LENGTH_LONG).show();
				return;
			}
			fromIndex += amount;
			getOrderData();
		}

	};

	/**
	 * 获取数据
	 */
	private void getOrderData() {
		String url = new JNICallBack().getHttp4GetCZOrder(MyApplication
				.getInstance().getUserId(), fromIndex + "", amount + "",
				MyApplication.getInstance().getToken());
		HttpClientUtil client = new HttpClientUtil();
		client.setPullToRefreshView(mPullToRefreshScrollView);
		client.getHttpResp(url, new IHttpResp() {

			@Override
			public void success(String content) {
				Log.e("info", content + "phoneOrder");
				ParseContent parseContent = new ParseContent();
				if (parseContent.parsePhone(content)) {
					ArrayList<Order> mArrayList = parseContent
							.getPhoneArrayList();
					Log.e("info", mArrayList.size() + "size");
					PhoneUtil util = new PhoneUtil(PhoneOrderActivity.this,
							linearLayout, mArrayList);
					util.addView();
				} else {
					Toast.makeText(PhoneOrderActivity.this,
							getResources().getString(R.string.nomore),
							Toast.LENGTH_LONG).show();
				}
				String label = getResources().getString(R.string.update_time)
						+ DateUtils.formatDateTime(PhoneOrderActivity.this,
								System.currentTimeMillis(),
								DateUtils.FORMAT_ABBREV_ALL
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_SHOW_TIME);
				mPullToRefreshScrollView.getLoadingLayoutProxy()
						.setLastUpdatedLabel(label);
			}
		});
	}
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "手机订单页面");
	}
	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "手机订单页面");
	}
}
