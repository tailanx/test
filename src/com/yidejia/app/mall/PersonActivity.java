package com.yidejia.app.mall;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.util.MsgUtil;

/**
 * 前消息中心
 * @author Administrator
 *
 */
public class PersonActivity extends SherlockActivity {
	
	private int fromIndex = 0;
	private int amount = 5;
	PullToRefreshScrollView mPullToRefreshScrollView;
	LinearLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionbar();
		setContentView(R.layout.message_center);
		layout = (LinearLayout) findViewById(R.id.message_scrollView_linearlayout1);
	    mPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.message_scrollView);
		
	    mPullToRefreshScrollView.setOnRefreshListener(listener);
		String label = getResources().getString(R.string.update_time)	+ DateUtils.formatDateTime(
				PersonActivity.this.getApplicationContext(),
				System.currentTimeMillis(),
				DateUtils.FORMAT_SHOW_TIME
					| DateUtils.FORMAT_SHOW_DATE
					| DateUtils.FORMAT_ABBREV_ALL);
		mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(label);;
		new MsgUtil(PersonActivity.this, layout,fromIndex,amount).loadView();;
	}
	
	private void setupShow(){
		new MsgUtil(PersonActivity.this, layout,fromIndex,amount);
	}
	
	private OnRefreshListener<ScrollView> listener = new OnRefreshListener<ScrollView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
			String label = getResources().getString(R.string.update_time)	+ DateUtils.formatDateTime(
					PersonActivity.this.getApplicationContext(),
					System.currentTimeMillis(),
					DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			fromIndex += amount;
			setupShow();
			mPullToRefreshScrollView.onRefreshComplete();
		}
		
	};

	private void setActionbar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setIcon(R.drawable.back);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_compose);
		ImageView button = (ImageView) findViewById(R.id.compose_back);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PersonActivity.this.finish();
			}
		});

		TextView titleTextView = (TextView) findViewById(R.id.compose_title);
		titleTextView.setText(getResources().getString(R.string.person_message));
	}
}