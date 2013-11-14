package com.yidejia.app.mall.fragment;

import android.os.Bundle;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.actionbarsherlock.app.SherlockFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.UserCommentDataManage;
import com.yidejia.app.mall.util.CommentUtil;

public class CommentFragment extends SherlockFragment  {
	private String goodsId;
	private String defaultHello = "";
	private View produceView;
	private LinearLayout linearLayout;
	private UserCommentDataManage dataManage;//评论数据业务
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
		View view = inflater.inflate(R.layout.item_goods_emulate, container, false);
		mPullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.item_goods_scrollView);
//		scrollView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
		linearLayout = (LinearLayout) view.findViewById(R.id.item_goods_scrollView_linearlayout1);
//		produceView = inflater.inflate(R.layout.item_goods_emulate_item, null);
//		dataManage = new UserCommentDataManage(getSherlockActivity());
		
		mPullToRefreshScrollView.setOnRefreshListener(listener);
		String label = getResources().getString(R.string.update_time)	+ DateUtils.formatDateTime(
				getSherlockActivity().getApplicationContext(),
				System.currentTimeMillis(),
				DateUtils.FORMAT_SHOW_TIME
					| DateUtils.FORMAT_SHOW_DATE
					| DateUtils.FORMAT_ABBREV_ALL);
		mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(label);;
		setupShow();//
		return view;
	}
	
	private int fromIndex = 0;
	private int amount = 10;
	private void setupShow(){
		new CommentUtil(getSherlockActivity(), linearLayout).AllComment(goodsId, fromIndex, amount);//, dataManage , produceView
	}
	
	private OnRefreshListener<ScrollView> listener = new OnRefreshListener<ScrollView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
			// TODO Auto-generated method stub
			String label = getResources().getString(R.string.update_time)	+ DateUtils.formatDateTime(
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