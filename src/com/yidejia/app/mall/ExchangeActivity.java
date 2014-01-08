package com.yidejia.app.mall;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.TaskGetRetList;

/**
 * 退换货
 * @author Administrator
 *
 */
public class ExchangeActivity extends SherlockFragmentActivity implements
		OnClickListener {

	private PullToRefreshScrollView mPullToRefreshScrollView;
	private TaskGetRetList task;

	private int fromIndex = 0;
	private int amount = 10;
	private String userId;
	private String token;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setActionbar();
		setContentView(R.layout.all_order_item_main);

		mPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.all_order_item_main_refresh_scrollview11);

		userId = ((MyApplication) getApplication()).getUserId();
		token = ((MyApplication) getApplication()).getToken();

		task = new TaskGetRetList(ExchangeActivity.this,
				mPullToRefreshScrollView);
		task.getRetOrderList(userId, token, fromIndex, amount);

		String label = getResources().getString(R.string.update_time)
				+ DateUtils.formatDateTime(ExchangeActivity.this,
						System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_ALL
								| DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_SHOW_TIME);
		mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
				label);
		mPullToRefreshScrollView.setOnRefreshListener(listener2);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (task != null) {
			task.closeTask();
		}
	}

	@Override
	public void onClick(View v) {

	}

	private void setActionbar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setIcon(R.drawable.back1);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_compose);
		ImageView button = (ImageView) findViewById(R.id.compose_back);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ExchangeActivity.this.finish();
			}
		});

		TextView titleTextView = (TextView) findViewById(R.id.compose_title);
		titleTextView.setText(getResources().getString(R.string.exchanged));
	}

	private OnRefreshListener2<ScrollView> listener2 = new OnRefreshListener2<ScrollView>() {

		@Override
		public void onPullDownToRefresh(
				PullToRefreshBase<ScrollView> refreshView) {
			fromIndex = 0;// 刷新的index要改为0
			task.getRetOrderList(userId, token, fromIndex, amount);
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
			fromIndex += amount;
			if (!task.getRetOrderList(userId, token, fromIndex, amount)) {
				fromIndex -= amount;
			}
		}
	};

}
