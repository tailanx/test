package com.yidejia.app.mall;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

public class VipBuyActivity extends SherlockActivity{
	
	private View view;
	private RelativeLayout emptyLayout;
	private PullToRefreshScrollView mPullToRefreshScrollView;
	private LinearLayout searchResultLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setActionBarConfig();
		view = getLayoutInflater().inflate(R.layout.activity_search_image_layout, null);
		setContentView(view);
		emptyLayout = (RelativeLayout) view.findViewById(R.id.search_empty);
		mPullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.pull_refresh_scrollview);
		searchResultLayout = (LinearLayout) view.findViewById(R.id.search_result_layout);
//		mPullToRefreshScrollView.setRefreshing(false);
		mPullToRefreshScrollView.setMode(Mode.DISABLED);
	}
	
	
	private void setActionBarConfig(){
		getSupportActionBar().setDisplayShowCustomEnabled(true);
//		getSupportActionBar().setCustomView(R.layout.actionbar_search_result);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		
		ImageView backImageView = (ImageView) findViewById(R.id.actionbar_left);
		backImageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				VipBuyActivity.this.finish();
			}
		});
		
		TextView titleTextView = (TextView) findViewById(R.id.actionbar_title);
		titleTextView.setText(R.string.main_vip_text);
		
		Button actionbar_right = (Button) findViewById(R.id.actionbar_right);
		actionbar_right.setVisibility(View.GONE);
	}

}
