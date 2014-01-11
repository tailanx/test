package com.yidejia.app.mall.goodinfo;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.util.CommentUtil;

public class CommentActivity extends BaseActivity  {
	private String goodsId;
	private LinearLayout linearLayout;
	private PullToRefreshScrollView mPullToRefreshScrollView;
	
	private View view;
	private CommentUtil util;
	private RelativeLayout refresh_view;
	private ImageView refreshBtn;
	
	private int fromIndex = 0;
	private int amount = 10;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//获取存储的参数
		Bundle args = getIntent().getExtras();
		goodsId = args.getString("goodsId");
//		goodsId = getIntent() != null ? getIntent().getStringExtra("goodsId") : "";
		
		setActionbarConfig();
		setTitle(R.string.produce_comment);
		
		setContentView(R.layout.item_goods_emulate);
		init();
		
		Toast.makeText(this, goodsId, Toast.LENGTH_LONG).show();
	}
	
	

	private void init(){
		try {
			
			mPullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.item_goods_scrollView);
			linearLayout = (LinearLayout) view.findViewById(R.id.item_goods_scrollView_linearlayout1);
			mPullToRefreshScrollView.setOnRefreshListener(listener);
			String label = getResources().getString(R.string.update_time) + DateUtils.formatDateTime(
					this,
					System.currentTimeMillis(),
					DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
			mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(label);;
			
			refresh_view = (RelativeLayout) view.findViewById(R.id.refresh_view);
			refreshBtn = (ImageView) view.findViewById(R.id.refresh_data_btn);
			
			refreshBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					fromIndex = 0;
					setupShow();
				}
			});
			
			util = new CommentUtil(this, linearLayout);
			util.setRefreshView(mPullToRefreshScrollView, refresh_view);
			setupShow();//
			
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(util != null) util.closeTask();
	}

	private void setupShow(){
		util.AllComment(goodsId, fromIndex, amount);//, dataManage , produceView
	}
	
	private OnRefreshListener<ScrollView> listener = new OnRefreshListener<ScrollView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
			String label = getResources().getString(R.string.update_time) + DateUtils.formatDateTime(
					CommentActivity.this,
					System.currentTimeMillis(),
					DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			fromIndex += amount;
			setupShow();
		}
		
	};
	
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
}