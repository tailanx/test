package com.yidejia.app.mall;

import java.util.ArrayList;

import org.apache.http.HttpStatus;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.opens.asyncokhttpclient.AsyncHttpResponse;
import com.opens.asyncokhttpclient.AsyncOkHttpClient;
import com.yidejia.app.mall.goodinfo.GoodsInfoActivity;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.log.LogService;
import com.yidejia.app.mall.model.BaseProduct;
import com.yidejia.app.mall.model.MainProduct;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.homepage.GetHomePage;
import com.yidejia.app.mall.order.AllOrderActivity;
import com.yidejia.app.mall.phone.PhoneActivity;
//import com.yidejia.app.mall.util.BottomChange;
import com.yidejia.app.mall.search.SearchActivity;
import com.yidejia.app.mall.skintest.SkinHomeActivity;
import com.yidejia.app.mall.view.IntegeralActivity;
import com.yidejia.app.mall.view.LoginActivity;
import com.yidejia.app.mall.widget.BannerView;
import com.yidejia.app.mall.yirihui.YirihuiActivity;

public class HomeMallActivity extends HomeBaseActivity {

	private FrameLayout frameLayout;// 填充activity的界面用的

	private View view;
	private FrameLayout bannerViewGroup;
	private BannerView bannerView;

	private PullToRefreshScrollView mPullToRefreshScrollView;
	private static int screenWidth;
	private static int screenHeight;

	// 设置大家都在买组
	private ArrayList<MainProduct> djdzmProducts;
	// 设置随心逛组
	private ArrayList<MainProduct> sxgProducts;

	// private boolean isFrist = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_fragment_layout);
		// 实例化组件
		frameLayout = (FrameLayout) findViewById(R.id.main_fragment);
		view = getLayoutInflater().inflate(R.layout.activity_main_layout, null);// 加载主界面
		frameLayout.addView(view);
		bannerViewGroup = (FrameLayout) view.findViewById(R.id.layout);

		initRefreshView();
		initFunView();

		setActionBar();
		setCurrentActivityId(0);

		getMainData();
		WindowManager manager = getWindowManager();
		Display display = manager.getDefaultDisplay();
		screenWidth = display.getHeight();
		screenHeight = (int) ((screenWidth / 320f) * 160f) - 250;
	}

	private void getMainData() {
		
		if(!ConnectionDetector.isConnectingToInternet(this)) return;
		
		String url = new JNICallBack().getHttp4GetHome();

		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.get(url, new AsyncHttpResponse() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				mPullToRefreshScrollView.onRefreshComplete();
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				if (HttpStatus.SC_OK == statusCode) {
					GetHomePage getHomePage = new GetHomePage();
					if (getHomePage.parseGetHomeJson(content)) {
						// 设置轮播的组
						ArrayList<BaseProduct> bannerProducts = getHomePage
								.getBannerArray();
						bannerView = new BannerView(bannerProducts,
								HomeMallActivity.this);
						bannerViewGroup.removeAllViews();
						bannerViewGroup.addView(bannerView
								.getMainListFirstItem());
						bannerView.startTimer();
						djdzmProducts = getHomePage.getDjdzmArray();
						setDjdzmView(djdzmProducts);
						sxgProducts = getHomePage.getSxgArray();
						setSxgView(sxgProducts);
					} else {
						Toast.makeText(HomeMallActivity.this,
								getString(R.string.bad_network),
								Toast.LENGTH_SHORT).show();
					}

					String label = getResources().getString(
							R.string.update_time)
							+ DateUtils.formatDateTime(HomeMallActivity.this,
									System.currentTimeMillis(),
									DateUtils.FORMAT_SHOW_TIME
											| DateUtils.FORMAT_SHOW_DATE
											| DateUtils.FORMAT_ABBREV_ALL);
					mPullToRefreshScrollView.getLoadingLayoutProxy()
							.setLastUpdatedLabel(label);
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onError(error, content);
				Toast.makeText(HomeMallActivity.this,
						getString(R.string.bad_network), Toast.LENGTH_SHORT)
						.show();
			}

		});
	}

	/**
	 * activity启动时调用这个方法显示界面
	 */
	private void initRefreshView() {
		mPullToRefreshScrollView = (PullToRefreshScrollView) view
				.findViewById(R.id.main_pull_refresh_scrollview);
		mPullToRefreshScrollView.setScrollingWhileRefreshingEnabled(true);
		mPullToRefreshScrollView
				.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
		mPullToRefreshScrollView.setVerticalScrollBarEnabled(false); // 禁用垂直滚动
		mPullToRefreshScrollView.setHorizontalScrollBarEnabled(false); // 禁用水平滚动

		mPullToRefreshScrollView.setOnRefreshListener(listener);
	}

	/** 初始化快捷功能区 **/
	private void initFunView() {
		RelativeLayout shorcutLayout = (RelativeLayout) view
				.findViewById(R.id.function_parent_layout);

		View child = getLayoutInflater().inflate(R.layout.main_function, null);
		shorcutLayout.addView(child);
		functionIntent(child);
	}

	/**
	 * 快捷功能那块跳到相应界面
	 * 
	 * @param child
	 */
	private void functionIntent(View child) {
		RelativeLayout myOrder = (RelativeLayout) child
				.findViewById(R.id.function_my_order);// 订单
		myOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intentOrder1 = new Intent(HomeMallActivity.this,
						AllOrderActivity.class);
				if (isLogin()) {
					HomeMallActivity.this.startActivity(intentOrder1);
				}
			}
		});

		RelativeLayout myCollect = (RelativeLayout) child
				.findViewById(R.id.function_my_favorite);// 收藏

		myCollect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intentOrder = new Intent(HomeMallActivity.this,
						MyCollectActivity.class);
				if (isLogin()) {
					HomeMallActivity.this.startActivity(intentOrder);
				}
			}

		});
		RelativeLayout myEvent = (RelativeLayout) child
				.findViewById(R.id.function_event);// 活动馆
		myEvent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(HomeMallActivity.this,
						ActiveGoActivity.class);
				startActivity(intent);
			}
		});
		RelativeLayout myMember = (RelativeLayout) child
				.findViewById(R.id.function_member);// 充值中心
		myMember.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(HomeMallActivity.this,
						PhoneActivity.class);
				HomeMallActivity.this.startActivity(intent);
			}
		});
		RelativeLayout myHistory = (RelativeLayout) child
				.findViewById(R.id.function_history);// 浏览历史
		myHistory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intentOrder = new Intent(HomeMallActivity.this,
						HistoryActivity.class);
				HomeMallActivity.this.startActivity(intentOrder);
			}
		});
		RelativeLayout myCoupon = (RelativeLayout) child
				.findViewById(R.id.function_coupon);// 伊日惠
		myCoupon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(HomeMallActivity.this,
						YirihuiActivity.class);
				HomeMallActivity.this.startActivity(intent);

			}
		});
		RelativeLayout mySkin = (RelativeLayout) child
				.findViewById(R.id.function_skin);// 积分卡券
		mySkin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intentOrder = new Intent(HomeMallActivity.this,
						IntegeralActivity.class);
				if (isLogin()) {
					HomeMallActivity.this.startActivity(intentOrder);
				}

			}
		});
		RelativeLayout myMessage = (RelativeLayout) child
				.findViewById(R.id.function_message);// 皮肤测试
		myMessage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intentOrder = new Intent(HomeMallActivity.this,
						SkinHomeActivity.class);
				HomeMallActivity.this.startActivity(intentOrder);
			}
		});

	}

	private OnRefreshListener<ScrollView> listener = new OnRefreshListener<ScrollView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
			try {
				if (!ConnectionDetector
						.isConnectingToInternet(HomeMallActivity.this)) {
					Toast.makeText(HomeMallActivity.this,
							getResources().getString(R.string.no_network),
							Toast.LENGTH_LONG).show();
					mPullToRefreshScrollView.onRefreshComplete();
					return;
				}
				getMainData();
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(HomeMallActivity.this,
						getResources().getString(R.string.bad_network),
						Toast.LENGTH_SHORT).show();
				mPullToRefreshScrollView.onRefreshComplete();
			}
		}

	};

	/**
	 * 设置头部
	 */
	private void setActionBar() {
		getSupportActionBar().setCustomView(R.layout.actionbar_main_home_title);

		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);

		ImageView searchEditText = (ImageView) findViewById(R.id.main_home_title_search);
		// 头部事件监听
		searchEditText.setOnClickListener(go2SearchListener2);

	}

	/**
	 * 头部的点击事件
	 */
	private OnClickListener go2SearchListener2 = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(HomeMallActivity.this,
					SearchActivity.class);
			startActivity(intent);
		}
	};

	/** 检查是否已登录 **/
	private boolean isLogin() {
		if (!MyApplication.getInstance().getIsLogin()) {
			Toast.makeText(this,
					getResources().getString(R.string.please_login),
					Toast.LENGTH_LONG).show();
			Intent intent1 = new Intent(HomeMallActivity.this,
					LoginActivity.class);
			startActivity(intent1);
			return false;
		}

		return true;
	}

	/** 设置大家都在买的组 **/
	private void setDjdzmView(ArrayList<MainProduct> products) {
		if (null == products)
			return;

		ImageView ivDjdzmLeft = (ImageView) findViewById(R.id.iv_djdzm_left);
		ImageView ivDjdzmRLeft = (ImageView) findViewById(R.id.iv_djdzm_left_right);
		ImageView ivDjdzmRRight = (ImageView) findViewById(R.id.iv_djdzm_right_right);
		ivDjdzmLeft.setOnClickListener(new ToucheOnclick(0, 0));
		ivDjdzmRLeft.setOnClickListener(new ToucheOnclick(0, 1));
		ivDjdzmRRight.setOnClickListener(new ToucheOnclick(0, 2));
		ImageLoader.getInstance()
				.init(MyApplication.getInstance().initConfig());

		int length = products.size();
		if (length > 0)
			imageload(products.get(0).getImgUrl(), ivDjdzmLeft);
		if (length > 1)
			imageload(products.get(1).getImgUrl(), ivDjdzmRLeft);
		if (length > 2)
			imageload(products.get(2).getImgUrl(), ivDjdzmRRight);

	}

	/** 设置随心逛的组 **/
	private void setSxgView(ArrayList<MainProduct> products) {
		if (null == products)
			return;

		ImageView ivSxgTop = (ImageView) findViewById(R.id.iv_sxg_top);
		ImageView ivSxgLeft = (ImageView) findViewById(R.id.iv_sxg_left);
		ImageView ivSxgRUp = (ImageView) findViewById(R.id.iv_sxg_right_up);
		ImageView ivSxgRDown = (ImageView) findViewById(R.id.iv_sxg_right_down);
		
		ivSxgTop.setOnClickListener(new ToucheOnclick(1, 0));
		ivSxgLeft.setOnClickListener(new ToucheOnclick(1, 1));
		ivSxgRUp.setOnClickListener(new ToucheOnclick(1, 2));
		ivSxgRDown.setOnClickListener(new ToucheOnclick(1, 3));

		int length = products.size();

		LayoutParams layoutParams = new LayoutParams(screenWidth, screenHeight);
		layoutParams.setMargins(5, 4, 5, 0);
		ivSxgTop.setLayoutParams(layoutParams);

		if (length > 0)
			Log.e("info", products.get(0).getImgUrl());
		imageload(products.get(0).getImgUrl(), ivSxgTop);

		if (length > 1)
			imageload(products.get(1).getImgUrl(), ivSxgLeft);
		if (length > 2)
			imageload(products.get(2).getImgUrl(), ivSxgRUp);
		if (length > 3)
			imageload(products.get(3).getImgUrl(), ivSxgRDown);

	}

	/** 显示图片到指定的view **/
	private void imageload(String url, ImageView iv) {
		ImageLoader.getInstance().displayImage(url, iv,
				MyApplication.getInstance().initGoodsImageOption(),
				MyApplication.getInstance().getImageLoadingListener());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// StatService.onPause(this);
		StatService.onPageEnd(this, "首页");
		if (null != bannerView || "".equals(bannerView)) {
			bannerView.stopTimer();
		}
	}

	@Override
	protected void onResume() {

		Intent intent = new Intent(HomeMallActivity.this, LogService.class);
		startService(intent);
		super.onResume();
		// StatService.onResume(this);
		StatService.onPageStart(this, "首页");
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (null != bannerView || "".equals(bannerView)) {
			bannerView.stopTimer();
		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		if (null != bannerView || "".equals(bannerView)) {
			bannerView.stopTimer();
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0) {
			finish();
		}
	}

	public class ToucheOnclick implements OnClickListener {
		private int type;// 大家都在买，或者是随心逛,o代表大家都在买，1代表随心逛
		private int index;// 点击的第几个

		public ToucheOnclick(int type, int index) {
			this.type = type;
			this.index = index;
		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(HomeMallActivity.this,
					GoodsInfoActivity.class);
			Bundle bundle = new Bundle();
			switch (type) {
			case 0:// 大家都在买
				bundle.putString("goodsId", djdzmProducts.get(index)
						.getUId());
				break;

			case 1:// 1代表随心逛
				bundle.putString("goodsId", sxgProducts.get(index).getUId());
				break;
			}
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}
	
	
}