package com.yidejia.app.mall.view;

import com.actionbarsherlock.app.SherlockActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.util.CommentUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class EvaluationActivity extends SherlockActivity {
	private LinearLayout layout;
	private MyApplication myApplication;
	public void doClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		// case R.id.evaluation_button://返回
		// intent.setClass(this, MyMallActivity.class);
		// break;
		case R.id.evaluation_item_evaluation:// 评价
			intent.setClass(this, PersonEvaluationActivity.class);
		}
		startActivity(intent);
		// 结束当前Activity；
		EvaluationActivity.this.finish();
	}
	private PullToRefreshScrollView mPullToRefreshScrollView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myApplication = (MyApplication) getApplication();
		setActionbar();
		setContentView(R.layout.evaluation);
		layout = (LinearLayout) findViewById(R.id.evaluation_scrollView_linearlayout1);
		mPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.item_goods_scrollView);
		mPullToRefreshScrollView.setOnRefreshListener(listener);
		String label = getResources().getString(R.string.update_time)	+ DateUtils.formatDateTime(
				EvaluationActivity.this.getApplicationContext(),
				System.currentTimeMillis(),
				DateUtils.FORMAT_SHOW_TIME
					| DateUtils.FORMAT_SHOW_DATE
					| DateUtils.FORMAT_ABBREV_ALL);
		mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(label);;
	setupShow();
	}
	private int fromIndex = 0;
	private int amount = 10;
	private OnRefreshListener<ScrollView> listener = new OnRefreshListener<ScrollView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
			// TODO Auto-generated method stub
			String label = getResources().getString(R.string.update_time)	+ DateUtils.formatDateTime(
					EvaluationActivity.this.getApplicationContext(),
					System.currentTimeMillis(),
					DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			fromIndex += amount;
		
			mPullToRefreshScrollView.onRefreshComplete();
		}
		
	};
	private void setupShow(){
		new CommentUtil(EvaluationActivity.this, layout).AllCommentUserId(myApplication.getUserId()
				, fromIndex, amount);
	}
	private void setActionbar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		// getSupportActionBar().setLogo(R.drawable.back);
		getSupportActionBar().setIcon(R.drawable.back);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_compose);
		// startActionMode(new
		// AnActionModeOfEpicProportions(ComposeActivity.this));
		ImageView button = (ImageView) findViewById(R.id.compose_back);// 返回
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(ComposeActivity.this, "button",
				// Toast.LENGTH_SHORT).show();
				EvaluationActivity.this.finish();
			}
		});

		TextView titleTextView = (TextView) findViewById(R.id.compose_title);
		titleTextView.setText(getResources().getString(R.string.evaluation));
	}
}
