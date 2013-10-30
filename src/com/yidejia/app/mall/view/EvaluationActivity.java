package com.yidejia.app.mall.view;

import com.actionbarsherlock.app.SherlockActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.TaskNoEva;
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

	private ScrollView mPullToRefreshScrollView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evaluation);
		myApplication = (MyApplication) getApplication();
		setActionbar();
		layout = (LinearLayout) findViewById(R.id.evaluation_scrollView_linearlayout1);
		mPullToRefreshScrollView = (ScrollView) findViewById(R.id.evaluation_scrollView);
//		mPullToRefreshScrollView.ScrollView(listener);
//		String label = getResources().getString(R.string.update_time)
//				+ DateUtils.formatDateTime(
//						EvaluationActivity.this.getApplicationContext(),
//						System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
//								| DateUtils.FORMAT_SHOW_DATE
//								| DateUtils.FORMAT_ABBREV_ALL);
//		ScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
//				label);
//		;
		// setupShow();
		// View person = getLayoutInflater().inflate(R.layout.evaluation_item,
		// null);
		// layout.addView(person);
		TaskNoEva taskNoEva = new TaskNoEva(EvaluationActivity.this, layout);
		taskNoEva.getWaitingComment(myApplication.getUserId(), true);
	}

	private int fromIndex = 0;
	private int amount = 10;
//	private OnRefreshListener<ScrollView> listener = new OnRefreshListener<ScrollView>() {
//
//		@Override
//		public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
//			// TODO Auto-generated method stub
//			String label = getResources().getString(R.string.update_time)
//					+ DateUtils.formatDateTime(
//							EvaluationActivity.this.getApplicationContext(),
//							System.currentTimeMillis(),
//							DateUtils.FORMAT_SHOW_TIME
//									| DateUtils.FORMAT_SHOW_DATE
//									| DateUtils.FORMAT_ABBREV_ALL);
//			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
//			// fromIndex += amount;
//			TaskNoEva taskNoEva = new TaskNoEva(EvaluationActivity.this, layout);
//			taskNoEva.getWaitingComment(myApplication.getUserId(), false);
//			mPullToRefreshScrollView.onRefreshComplete();
//		}
//	};

	private void setupShow() {
		new CommentUtil(EvaluationActivity.this, layout).AllCommentUserId(
				myApplication.getUserId(), fromIndex, amount);
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