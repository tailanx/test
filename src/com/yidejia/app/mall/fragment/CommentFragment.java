package com.yidejia.app.mall.fragment;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.util.CommentUtil;

public class CommentFragment extends SherlockFragment  {
	private String goodsId;
	private String defaultHello = "";
	private LinearLayout linearLayout;
	private PullToRefreshScrollView mPullToRefreshScrollView;
	
	public static CommentFragment newInstance(String goodsId){
		CommentFragment commentFragment = new CommentFragment();
		Bundle bundle = new Bundle();
		bundle.putString("goodsId", goodsId);
		commentFragment.setArguments(bundle);
		return commentFragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//获取存储的参数
		Bundle args = getArguments();
		goodsId = args!=null?args.getString("goodsId"):defaultHello;
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.item_goods_emulate, container, false);
		
//		produceView = inflater.inflate(R.layout.item_goods_emulate_item, null);
//		dataManage = new UserCommentDataManage(getSherlockActivity());
		
		Log.e(CommentFragment.class.getName(), "comment fragment on create view");
		
		
		return view;
	}
	
	private View view;
	private CommentUtil util;
	private RelativeLayout refresh_view;
	private ImageView refreshBtn;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		try {
		
		mPullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.item_goods_scrollView);
////		scrollView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
		linearLayout = (LinearLayout) view.findViewById(R.id.item_goods_scrollView_linearlayout1);
		mPullToRefreshScrollView.setOnRefreshListener(listener);
		String label = getResources().getString(R.string.update_time) + DateUtils.formatDateTime(
				getSherlockActivity().getApplicationContext(),
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
				// TODO Auto-generated method stub
				fromIndex = 0;
				setupShow();
			}
		});
		
		util = new CommentUtil(getSherlockActivity(), linearLayout);
		util.setRefreshView(mPullToRefreshScrollView, refresh_view);
		setupShow();//
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}



	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(util != null) util.closeTask();
	}

	private int fromIndex = 0;
	private int amount = 10;
	private void setupShow(){
		util.AllComment(goodsId, fromIndex, amount);//, dataManage , produceView
	}
	
	private OnRefreshListener<ScrollView> listener = new OnRefreshListener<ScrollView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
			// TODO Auto-generated method stub
			String label = getResources().getString(R.string.update_time) + DateUtils.formatDateTime(
					getSherlockActivity().getApplicationContext(),
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
}