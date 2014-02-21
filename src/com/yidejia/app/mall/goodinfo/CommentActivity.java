package com.yidejia.app.mall.goodinfo;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.opens.asyncokhttpclient.AsyncHttpResponse;
import com.opens.asyncokhttpclient.AsyncOkHttpClient;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.UserComment;
import com.yidejia.app.mall.util.CommentUtil;
import com.yidejia.app.mall.util.DPIUtil;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;

public class CommentActivity extends BaseActivity  {
	private String goodsId;
	private LinearLayout linearLayout;
	private PullToRefreshScrollView mPullToRefreshScrollView;
	
//	private View view;
//	private CommentUtil util;
	private RelativeLayout refresh_view;
	private ImageView refreshBtn;
	
	private int fromIndex = 0;
	private int amount = 10;
	private boolean isFirstIn = true;
	
//	private ArrayList<UserComment> comments;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//获取存储的参数
		Bundle args = getIntent().getExtras();
		goodsId = args.getString("goodsId");
		
		setActionbarConfig();
		setTitle(R.string.produce_comment);
		
		if(TextUtils.isEmpty(goodsId)) return;
		
		setContentView(R.layout.item_goods_emulate);
		init();
	}
	
	

	private void init(){
		try {
			
			mPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.item_goods_scrollView);
			linearLayout = (LinearLayout) findViewById(R.id.item_goods_scrollView_linearlayout1);
			mPullToRefreshScrollView.setOnRefreshListener(listener);
			
			refresh_view = (RelativeLayout) findViewById(R.id.refresh_view);
			refreshBtn = (ImageView) findViewById(R.id.refresh_data_btn);
			
			refreshBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					fromIndex = 0;
					setupShow();
				}
			});
			
//			util = new CommentUtil(this, linearLayout);
//			util.setRefreshView(mPullToRefreshScrollView, refresh_view);
			setupShow();//
			
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
//		if(util != null) util.closeTask();
	}

	private void setupShow(){
		String url = new JNICallBack().getHttp4GetComment("goods_id=" + goodsId, fromIndex + "", amount + "", "", "commentDate+desc", "%2A");
		
		HttpClientUtil httpClientUtil = new HttpClientUtil(this);
		if(isFirstIn)httpClientUtil.setIsShowLoading(true);
		else httpClientUtil.setIsShowLoading(false);
		httpClientUtil.setPullToRefreshView(mPullToRefreshScrollView);
		httpClientUtil.getHttpResp(url, new IHttpResp(){

			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
				if(null == getResources()) return;
				ParseGoodsJson parseGoodsJson = new ParseGoodsJson();
				boolean isSuccess = parseGoodsJson.parseCommentJson(content);
				if(isSuccess) {
					ArrayList<UserComment> comments = parseGoodsJson.getComments();
					showItemView(comments);
				} else {
					if (parseGoodsJson.getIsNoMore()) {
						Toast.makeText(CommentActivity.this,
								getResources().getString(R.string.nomore),
								Toast.LENGTH_SHORT).show();
						fromIndex -= amount;
					}
				}
				
				
				String label = getResources().getString(R.string.update_time) + DateUtils.formatDateTime(
						CommentActivity.this,
						System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME
							| DateUtils.FORMAT_SHOW_DATE
							| DateUtils.FORMAT_ABBREV_ALL);
				if(null != mPullToRefreshScrollView)
				mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			}

			@Override
			public void onError() {
				super.onError();
				fromIndex -= amount;
				if(fromIndex < 0) {
					fromIndex = 0;
				}
			}
			
		});
		
		/*
		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.get(url, new AsyncHttpResponse(){

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				if(!isFirstIn) mPullToRefreshScrollView.onRefreshComplete();
				isFirstIn = false;
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				Log.e("info", content);
				ParseGoodsJson parseGoodsJson = new ParseGoodsJson();
				boolean isSuccess = parseGoodsJson.parseCommentJson(content);
				if(isSuccess) {
					ArrayList<UserComment> comments = parseGoodsJson.getComments();
					showItemView(comments);
				} else {
					if (parseGoodsJson.getIsNoMore()) {
						Toast.makeText(CommentActivity.this,
								getResources().getString(R.string.nomore),
								Toast.LENGTH_SHORT).show();
						fromIndex -= amount;
					}
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onError(error, content);
				fromIndex -= amount;
				if(fromIndex < 0) {
					fromIndex = 0;
				}
			}
			
		});*/
	}
	
	/**
	 * 显示评论
	 * @param comments
	 */
	private void showItemView(ArrayList<UserComment> comments) {
		if(null == comments) return;
		int length = comments.size();
		
		for (int i = 0; i < length; i++) {
			try {
				UserComment userComment = comments.get(i);
				View view = LayoutInflater.from(this).inflate(
						R.layout.item_goods_emulate_item, null);
				TextView userName = (TextView) view.findViewById(R.id.user_name);
				TextView userLevel = (TextView) view.findViewById(R.id.user_level);
				TextView userContent = (TextView) view.findViewById(R.id.emulate_text);
				TextView commentTime = (TextView) view.findViewById(R.id.emulate_user_time);
				LinearLayout starLayout = (LinearLayout) view.findViewById(R.id.emulate_grade_star);
				userName.setText(userComment.getUserName());
				userLevel.setText(userComment.getVipLevel());
				userContent.setText(userComment.getUserCommentText());
//				 userGrade.setText(userComment.getRate()+"");
				int rate = userComment.getRate();
				setCommStar(rate, starLayout);
				commentTime.setText(userComment.getCommentTime());
				linearLayout.addView(view);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 设置评论的星级数
	 * @param rate
	 * @param starLayout
	 */
	private void setCommStar(int rate, LinearLayout starLayout){
		int px = (int)DPIUtil.dpToPixel(20, this);
		LinearLayout.LayoutParams lp_base = new LinearLayout.LayoutParams(px,
				px);
		
		lp_base.gravity = Gravity.CENTER;
		for (int i = 0; i < rate; i++) {
			ImageView starImageView = new ImageView(this);
			starImageView.setLayoutParams(lp_base);
			starImageView.setPadding(5, 0, 5, 0);
			starImageView.setImageResource(R.drawable.score1);
			starLayout.addView(starImageView);
		}
	}
	
	private OnRefreshListener<ScrollView> listener = new OnRefreshListener<ScrollView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
			
			isFirstIn = false;
			fromIndex += amount;
			setupShow();
		}
		
	};
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "商品评论页面");
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "商品评论页面");
	}
}