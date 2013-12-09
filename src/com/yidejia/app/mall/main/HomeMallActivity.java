package com.yidejia.app.mall.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.Inflater;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.yidejia.app.mall.ActiveGoActivity;
import com.yidejia.app.mall.GoodsInfoActivity;
import com.yidejia.app.mall.MainFragmentActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.SearchActivity;
import com.yidejia.app.mall.SlideImageLayout;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.initview.HotSellView;
import com.yidejia.app.mall.model.BaseProduct;
import com.yidejia.app.mall.model.MainProduct;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.homepage.GetHomePage;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.view.AllOrderActivity;
import com.yidejia.app.mall.view.GoCartActivity;
import com.yidejia.app.mall.view.HistoryActivity;
import com.yidejia.app.mall.view.IntegeralActivity;
import com.yidejia.app.mall.view.LoginActivity;
import com.yidejia.app.mall.view.MyCollectActivity;
import com.yidejia.app.mall.view.SkinHomeActivity;
import com.yidejia.app.mall.widget.YLProgressDialog;
import com.yidejia.app.mall.widget.YLViewPager;

public class HomeMallActivity extends SherlockFragmentActivity implements
		OnClickListener {

	private MyApplication myApplication;
	private FrameLayout frameLayout;//填充activity的界面用的
	private LayoutInflater inflater;
	private View view;
	private ViewGroup mMainView = null;
	private YLViewPager mViewPager;
	private ViewGroup mImageCircleView = null;
	private SlideImageLayout mSlideLayout;
	private ImageView[] mImageCircleViews;
	public static Activity MAINACTIVITY;
	private ArrayList<BaseProduct> bannerArray = new ArrayList<BaseProduct>();
	private ArrayList<MainProduct> acymerArray = new ArrayList<MainProduct>();
	private ArrayList<MainProduct> inerbtyArray = new ArrayList<MainProduct>();
	private ArrayList<MainProduct> hotsellArray = new ArrayList<MainProduct>();
	private ArrayList<String> ggTitleArray = new ArrayList<String>();
	private static final String TAG = "MainActivity";
	private InnerReceiver receiver;
	private RelativeLayout reLayout;
	private int number1;
	private Bottom bottom;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//注册一个广播
		receiver = new InnerReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Consts.UPDATE_CHANGE);
		filter.addAction(Consts.BROAD_UPDATE_CHANGE);
		filter.addAction(Consts.DELETE_CART);
		registerReceiver(receiver, filter);
		
		MAINACTIVITY = this;
		cartsDataManage = new CartsDataManage();
		setContentView(R.layout.activity_main_fragment_layout);
		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.activity_main_layout, null);
		//设置头部
		setActionBarConfig();
		
//		number1 = cartsDataManage.getCartAmount();
//		reLayout = (RelativeLayout) findViewById(R.id.down_parent_layout);
//		bottom = new Bottom();
//		bottom.getBottm(HomeMallActivity.this,reLayout,number1);
		
		//设置底部
		initNavView();
		myApplication = (MyApplication) getApplication();
		frameLayout = (FrameLayout) findViewById(R.id.main_fragment);
		frameLayout.addView(view);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	/**
	 * 头部
	 */
	private void setActionBarConfig() {
		getSupportActionBar().setCustomView(R.layout.actionbar_main_home_title);

		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);

		ImageView searchEditText = (ImageView) findViewById(R.id.main_home_title_search);

		searchEditText.setOnClickListener(go2SearchListener2);

		onActivityCreated();
	}

	private void onActivityCreated() {
		bar = new ProgressDialog(HomeMallActivity.this,
				R.style.StyleProgressDialog);

		bar.setCancelable(true);
		// bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// bar.setMessage(getSherlockActivity().getResources().getString(R.string.loading));
		bar.setOnCancelListener(new DialogInterface.OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				isFirstIn = false;
				closeTask();
			}
		});
		createView(view, inflater);
		if (!ConnectionDetector.isConnectingToInternet(this)) {
			Toast.makeText(this, getResources().getString(R.string.no_network),
					Toast.LENGTH_LONG).show();
			isFirstIn = false;
			return;
		}
		closeTask();
		task = new Task();
		task.execute();
	}

	private void closeTask() {
		if (task != null
				&& task.getStatus().RUNNING == AsyncTask.Status.RUNNING) {
			task.cancel(true);
		}
	}

	/**
	 * 头部的点击事件
	 */
	private OnClickListener go2SearchListener2 = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(HomeMallActivity.this,
					SearchActivity.class);
			startActivity(intent);
		}
	};
	private boolean isFirstIn = true;
	private Task task;
	private ProgressDialog bar;
	private boolean isTimeOut = false;

	private RelativeLayout downHomeLayout;
	private RelativeLayout downGuangLayout;
	private RelativeLayout downSearchLayout;
	private RelativeLayout downShoppingLayout;
	private RelativeLayout downMyLayout;
	private ImageView down_home_imageView;// 首页按钮图片
	private ImageView down_guang_imageView;// 逛按钮图片
	private ImageView down_search_imageView;// 搜索按钮图片
	private ImageView down_shopping_imageView; // 购物车按钮图片
	private ImageView down_my_imageView; // 我的商城按钮图片
	private CartsDataManage cartsDataManage;
	private TextView down_home_textview;
	private TextView down_guang_textview;
	private TextView down_search_textview;
	private TextView down_shopping_textview;
	private TextView down_my_textview;
	private Resources res;
	private int number;
	private Button cartImage;// 购物车上的按钮

	/**
	 * 初始化底部导航栏
	 */
	private void initNavView() {
		// 改变底部首页背景，有按下去的效果的背景
		downHomeLayout = (RelativeLayout) findViewById(R.id.down_home_layout);
		down_home_imageView = (ImageView) findViewById(R.id.down_home_icon);

		res = getResources();
		cartsDataManage = new CartsDataManage();
		number = cartsDataManage.getCartAmount();
		cartImage = (Button) findViewById(R.id.down_shopping_cart);
		if (number == 0) {
			cartImage.setVisibility(View.GONE);
		} else {
			cartImage.setText(number + "");
		}
		downGuangLayout = (RelativeLayout) findViewById(R.id.down_guang_layout);
		downSearchLayout = (RelativeLayout) findViewById(R.id.down_search_layout);
		downShoppingLayout = (RelativeLayout) findViewById(R.id.down_shopping_layout);
		downMyLayout = (RelativeLayout) findViewById(R.id.down_my_layout);

		downGuangLayout.setOnClickListener(this);
		downSearchLayout.setOnClickListener(this);
		downShoppingLayout.setOnClickListener(this);
		downMyLayout.setOnClickListener(this);

		downGuangLayout.setVisibility(ViewGroup.GONE);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.down_search_layout:
			intent.setClass(HomeMallActivity.this, HomeSearchActivity.class);
			break;
		case R.id.down_shopping_layout:
			intent.setClass(HomeMallActivity.this, HomeCarActivity.class);
			break;
		case R.id.down_my_layout:
			if (myApplication.getIsLogin())
				intent.setClass(HomeMallActivity.this, HomeMyMaActivity.class);
			else
				intent.setClass(HomeMallActivity.this, HomeLogActivity.class);
			break;
		}
		HomeMallActivity.this.startActivity(intent);

	}

	private class Task extends AsyncTask<Void, Void, Boolean> {

		ProgressDialog bar2;

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-gene|rated method stub
			GetHomePage getHomePage = new GetHomePage();
			String httpresp;
			try {
				try {
					httpresp = getHomePage.getHomePageJsonString();
					boolean issuccess = getHomePage
							.analysisGetHomeJson(httpresp);
					bannerArray = getHomePage.getBannerArray();
					acymerArray = getHomePage.getAcymerArray();
					inerbtyArray = getHomePage.getInerbtyArray();
					hotsellArray = getHomePage.getHotSellArray();
					ggTitleArray = getHomePage.getGGTitle();
					return issuccess;
				} catch (TimeOutEx e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					isTimeOut = true;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (isFirstIn) {
				// bar.show();
				bar2 = (ProgressDialog) new YLProgressDialog(
						HomeMallActivity.this).createLoadingDialog(
						HomeMallActivity.this, null);
				// bar2.show();
				bar2.setOnCancelListener(new DialogInterface.OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						// TODO Auto-generated method stub
						isFirstIn = false;
						closeTask();
					}
				});
			}

			if (!bannerArray.isEmpty())
				bannerArray.clear();
			if (!inerbtyArray.isEmpty())
				inerbtyArray.clear();
			if (!hotsellArray.isEmpty())
				hotsellArray.clear();
			if (!acymerArray.isEmpty())
				acymerArray.clear();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// try{

			if (result) {
				try {

					bannerIndex = 0;
					layout.removeAllViews();
					getMainListFirstItem();
					layout.addView(mMainView);

					HotSellView hotSellView = new HotSellView(view,
							HomeMallActivity.this);
					hotSellView.initHotSellView(hotsellArray);
					hotSellView.initAcymerView(acymerArray);
					hotSellView.initInerbtyView(inerbtyArray);

					try {
						intentToView(view);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					main_mall_notice_content.setText(ggTitleArray.get(0));
				} catch (Exception e) {
					// TODO: handle exception
				}
				// } catch (Exception e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// Toast.makeText(getSherlockActivity(),
				// getResources().getString(R.string.bad_network),
				// Toast.LENGTH_SHORT).show();

			} else {
				if (isTimeOut) {
					Toast.makeText(HomeMallActivity.this,
							getResources().getString(R.string.time_out),
							Toast.LENGTH_SHORT).show();
					isTimeOut = false;

				} else
					Toast.makeText(HomeMallActivity.this,
							getResources().getString(R.string.bad_network),
							Toast.LENGTH_SHORT).show();
			}

			if (isFirstIn) {
				// bar.dismiss();
				bar2.dismiss();
				if (result)
					timer.schedule(timetask, DELAY, DELAY);
				isFirstIn = false;
			} else {

				mPullToRefreshScrollView.onRefreshComplete();
				String label = getResources().getString(R.string.update_time)
						+ DateUtils.formatDateTime(
								HomeMallActivity.this.getApplicationContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
				mPullToRefreshScrollView.getLoadingLayoutProxy()
						.setLastUpdatedLabel(label);
			}
		}

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d(TAG, "TestFragment-----onStart");
//		number = (new CartsDataManage()).getCartAmount();
//		bottom.getBottm(this, reLayout, number);
		startTimer();
		// timer.schedule(timetask, DELAY, DELAY);
	}

	private void startTimer() {
		if (timer == null) {
			timer = new Timer();
		}

		if (timetask == null) {
			timetask = new TimerTask() {

				public void run() {
					Message message = new Message();
					message.what = 1;
					handler.sendMessage(message);
				}

			};
		}
	}

	private OnRefreshListener<ScrollView> listener = new OnRefreshListener<ScrollView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
			// TODO Auto-generated method stub
			try {
				// String label = getResources().getString(R.string.update_time)
				// + DateUtils.formatDateTime(getSherlockActivity()
				// .getApplicationContext(), System
				// .currentTimeMillis(),
				// DateUtils.FORMAT_SHOW_TIME
				// | DateUtils.FORMAT_SHOW_DATE
				// | DateUtils.FORMAT_ABBREV_ALL);
				// refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// mPullToRefreshScrollView.setRefreshing();
				if (!ConnectionDetector
						.isConnectingToInternet(HomeMallActivity.this)) {
					Toast.makeText(HomeMallActivity.this,
							getResources().getString(R.string.no_network),
							Toast.LENGTH_LONG).show();
					mPullToRefreshScrollView.onRefreshComplete();
					return;
				}
				closeTask();
				task = new Task();
				task.execute();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(HomeMallActivity.this,
						getResources().getString(R.string.bad_network),
						Toast.LENGTH_SHORT).show();
				mPullToRefreshScrollView.onRefreshComplete();
			}
		}

	};

	/**
	 * activity启动时调用这个方法显示界面
	 * 
	 * @param view
	 * @param inflater
	 */
	private void createView(View view, LayoutInflater inflater) {

		RelativeLayout shorcutLayout = (RelativeLayout) view
				.findViewById(R.id.function_parent_layout);

		View child = inflater.inflate(R.layout.main_function, null);
		shorcutLayout.addView(child);
		functionIntent(child);

		mPullToRefreshScrollView = (PullToRefreshScrollView) view
				.findViewById(R.id.main_pull_refresh_scrollview);
		mPullToRefreshScrollView.setScrollingWhileRefreshingEnabled(true);
		mPullToRefreshScrollView
				.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
		mPullToRefreshScrollView.setVerticalScrollBarEnabled(false); // 禁用垂直滚动
		mPullToRefreshScrollView.setHorizontalScrollBarEnabled(false); // 禁用水平滚动

		String label = getResources().getString(R.string.update_time)
				+ DateUtils.formatDateTime(
						HomeMallActivity.this.getApplicationContext(),
						System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
								| DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
		mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
				label);
		mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
				label);
		mPullToRefreshScrollView.setOnRefreshListener(listener);

		main_mall_notice_content = (TextView) view
				.findViewById(R.id.main_mall_notice_content);

		layout = (FrameLayout) view.findViewById(R.id.layout);
		// mMainView = (ViewGroup) inflater.inflate(
		// R.layout.layout_first_item_in_main_listview, null);

	}

	// private int bannerIndex = 0;
	private FrameLayout layout;

	/**
	 * 
	 * @return 首页轮播的控件
	 */
	private ViewGroup getMainListFirstItem() {
		length = bannerArray.size();
		LayoutInflater inflater = HomeMallActivity.this.getLayoutInflater();
		mMainView = (ViewGroup) inflater.inflate(
				R.layout.layout_first_item_in_main_listview, null);
		if (length == 0)
			return mMainView;
		mViewPager = (YLViewPager) mMainView
				.findViewById(R.id.image_slide_page);
		// if (length == 0)
		// return mMainView;
		// mViewPager = (YLViewPager) mMainView
		// .findViewById(R.id.image_slide_page);
		mSlideLayout = new SlideImageLayout(HomeMallActivity.this,
				mMainView.getContext(), width);
		mSlideLayout.setCircleImageLayout(length);
		mImageCircleViews = new ImageView[length];
		mImageCircleView = (ViewGroup) mMainView
				.findViewById(R.id.layout_circle_images);

		Log.i(TAG,
				"length--------------------------------" + bannerArray.size());
		Log.i(TAG,
				"length--------------------------------" + bannerArray.size());
		// Timer timer = new Timer();
		// timer.schedule(new TimerTask() {
		//
		// @Override
		// public void run() {
		// int i=0;
		//
		// i++;
		// // TODO Auto-generated method stub
		// Log.i("voucher",i+"");
		// }
		//
		// }, 3000);
		for (int i = 0; i < length; i++) {

			// mainImageData.getBitmaps().get(i)
			// mImagePageViewList.add(mSlideLayout
			// .getSlideImageLayout((Bitmap)topAdImage.get(i)));//mainImageData.getBitmaps().get(i)
			mImageCircleViews[i] = mSlideLayout.getCircleImageLayout(i);
			mImageCircleView.addView(mSlideLayout.getLinearLayout(
					mImageCircleViews[i], 9, 9));
		}

		// 设置ViewPager
		mViewPager.setAdapter(new SlideImageAdapter(bannerArray));
		mViewPager.setOnPageChangeListener(new ImagePageChangeListener());
		mViewPager.setCurrentItem(0);
		mViewPager.setOffscreenPageLimit(2);
		return mMainView;
	}

	/**
	 * 滑动图片数据适配器
	 */
	private class SlideImageAdapter extends PagerAdapter {
		// int i =0;
		private LayoutInflater inflater;

		private ArrayList<BaseProduct> bannerArray;

		public SlideImageAdapter(ArrayList<BaseProduct> bannerArray) {
			this.bannerArray = bannerArray;
			inflater = HomeMallActivity.this.getLayoutInflater();
			initDisplayImageOption();
		}

		// private int height = (int)((float)width / 320) * 160;

		// public SlideImageAdapter(){
		// }
		public int getCount() {
			return length;// 1000;
		}

		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		public void destroyItem(ViewGroup container, int position, Object object) {
			// ((ViewPager) view).removeView(mImagePageViewList.get(arg1));
			((ViewPager) container).removeView((View) object);
		}

		public Object instantiateItem(ViewGroup view, int position) {
			// ((ViewPager) view).addView(mImagePageViewList.get(position));
			View imageLayout = inflater.inflate(R.layout.item_pager_image,
					view, false);
			final ImageView imageView = (ImageView) imageLayout
					.findViewById(R.id.image);
			// Toast.makeText(getSherlockActivity(),
			// bannerArray.get(position).getImgUrl(),
			// Toast.LENGTH_SHORT).show();
			imageLoader.init(ImageLoaderConfiguration.createDefault(HomeMallActivity.this));
			imageLoader.displayImage(bannerArray.get(position).getImgUrl(),
					imageView, options, animateFirstListener);
			// imageView.setBackgroundResource(ids[position%length]);
			imageView.setOnClickListener(new ImageOnClickListener(position));
			((ViewPager) view).addView(imageLayout, 0);
			return imageLayout;
		}

		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		public Parcelable saveState() {
			return null;
		}

		public void startUpdate(View arg0) {
		}

		public void finishUpdate(View arg0) {
		}

		// public void setPageIndex(int index){
		// pageIndex = index;
		// }

		public class ImageOnClickListener implements OnClickListener {
			private int index;

			public ImageOnClickListener(int index) {
				this.index = index;
			}

			@Override
			public void onClick(View v) {
				// Toast.makeText(getActivity(),
				// "我点击了第"+"["+pageIndex%length+"]几个",
				// Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(HomeMallActivity.this,
						GoodsInfoActivity.class);
				// Toast.makeText(getActivity(),
				// "我点击了第"+"["+pageIndex%length+"]几个",
				// Toast.LENGTH_SHORT).show();
				Bundle bundle = new Bundle();
				bundle.putString("goodsId", bannerArray.get(index).getUId());
				intent.putExtras(bundle);
				HomeMallActivity.this.startActivity(intent);
			}
		}
	}

	// private int pageIndex = 0;

	/**
	 * 滑动页面更改事件监听器
	 */
	private class ImagePageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {

			// mViewPager.setCurrentItem(pageIndex);
			// }
		}

		@Override
		public void onPageSelected(int index) {
			// mSlideLayout.setPageIndex(index);
			// SlideImageAdapter slideImageAdapter = new SlideImageAdapter();
			// slideImageAdapter.setPageIndex(index);
			// pageIndex = index;
			int length = mImageCircleViews.length;
			mImageCircleViews[index % length]
					.setBackgroundResource(R.drawable.dot1);
			mImageCircleViews[index % length]
					.setBackgroundResource(R.drawable.dot1);
			for (int i = 0; i < length; i++) {
				// mSlideTitle.setText(""+i);

				if (index % length != i) {
					mImageCircleViews[i % length]
							.setBackgroundResource(R.drawable.dot2);
					mImageCircleViews[i % length]
							.setBackgroundResource(R.drawable.dot2);
				}
			}
		}

	}

	private int width;
	private int length = 0;

	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private TextView main_mall_notice_content;

	private void initDisplayImageOption() {
		options = new DisplayImageOptions.Builder()
				// .showStubImage(R.drawable.hot_sell_right_top_image)
				// .showImageOnFail(R.drawable.hot_sell_right_top_image)
				// .showImageForEmptyUri(R.drawable.hot_sell_right_top_image)
				// .cacheInMemory(true)
				// .cacheOnDisc(true)
				// .build();
				.showStubImage(R.drawable.banner_bg)
				.showImageOnFail(R.drawable.banner_bg)
				.showImageForEmptyUri(R.drawable.banner_bg).cacheInMemory(true)
				.cacheOnDisc(true).build();
	}

	static final List<String> displayedImages = Collections
			.synchronizedList(new LinkedList<String>());

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {

			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

	/**
	 * 获取首页商品hotsell,acymer,inerbty布局的控件和跳转
	 * 
	 * @param view
	 */
	private void intentToView(View view) {
		try {
			// hot sell 左边
			RelativeLayout hotsellLeft = (RelativeLayout) view
					.findViewById(R.id.main_hot_sell_left);
			hotsellLeft.setOnClickListener(new MainGoodsOnclick(hotsellArray
					.get(0).getUId()));

			hotsellLeft.setOnClickListener(new MainGoodsOnclick(hotsellArray
					.get(0).getUId()));
			// hot sell 右上
			RelativeLayout hotsellRightTop = (RelativeLayout) view
					.findViewById(R.id.main_hot_sell_right_top);
			hotsellRightTop.setOnClickListener(new MainGoodsOnclick(
					hotsellArray.get(1).getUId()));

			hotsellRightTop.setOnClickListener(new MainGoodsOnclick(
					hotsellArray.get(1).getUId()));
			// hot sell 右下
			RelativeLayout hotsellRightDown = (RelativeLayout) view
					.findViewById(R.id.main_hot_sell_right_down);
			hotsellRightDown.setOnClickListener(new MainGoodsOnclick(
					hotsellArray.get(2).getUId()));

			hotsellRightDown.setOnClickListener(new MainGoodsOnclick(
					hotsellArray.get(2).getUId()));

			// acymer 左边
			RelativeLayout acymeiLeft = (RelativeLayout) view
					.findViewById(R.id.main_acymei_left);
			acymeiLeft.setOnClickListener(new MainGoodsOnclick(acymerArray.get(
					0).getUId()));

			acymeiLeft.setOnClickListener(new MainGoodsOnclick(acymerArray.get(
					0).getUId()));
			// acymer 右上
			RelativeLayout acymeiRightTop = (RelativeLayout) view
					.findViewById(R.id.main_acymei_right_top);
			acymeiRightTop.setOnClickListener(new MainGoodsOnclick(acymerArray
					.get(1).getUId()));

			acymeiRightTop.setOnClickListener(new MainGoodsOnclick(acymerArray
					.get(1).getUId()));
			// acymer 右下
			RelativeLayout acymeiRightDown = (RelativeLayout) view
					.findViewById(R.id.main_acymei_right_down);
			acymeiRightDown.setOnClickListener(new MainGoodsOnclick(acymerArray
					.get(2).getUId()));

			acymeiRightDown.setOnClickListener(new MainGoodsOnclick(acymerArray
					.get(2).getUId()));

			// inerbty 左上
			RelativeLayout inerbtyTopLeft = (RelativeLayout) view
					.findViewById(R.id.main_inerbty_top_left);
			inerbtyTopLeft.setOnClickListener(new MainGoodsOnclick(inerbtyArray
					.get(0).getUId()));

			inerbtyTopLeft.setOnClickListener(new MainGoodsOnclick(inerbtyArray
					.get(0).getUId()));
			// inerbty 右上
			RelativeLayout inerbtyTopRight = (RelativeLayout) view
					.findViewById(R.id.main_inerbty_top_right);
			inerbtyTopRight.setOnClickListener(new MainGoodsOnclick(
					inerbtyArray.get(1).getUId()));

			inerbtyTopRight.setOnClickListener(new MainGoodsOnclick(
					inerbtyArray.get(1).getUId()));
			// inerbty 左中
			RelativeLayout nerbtyMidLeft = (RelativeLayout) view
					.findViewById(R.id.main_inerbty_mid_left);
			nerbtyMidLeft.setOnClickListener(new MainGoodsOnclick(inerbtyArray
					.get(2).getUId()));

			nerbtyMidLeft.setOnClickListener(new MainGoodsOnclick(inerbtyArray
					.get(2).getUId()));
			// inerbty 右中
			RelativeLayout inerbtyMidRight = (RelativeLayout) view
					.findViewById(R.id.main_inerbty_mid_right);
			inerbtyMidRight.setOnClickListener(new MainGoodsOnclick(
					inerbtyArray.get(3).getUId()));

			inerbtyMidRight.setOnClickListener(new MainGoodsOnclick(
					inerbtyArray.get(3).getUId()));
			// inerbty 左下
			RelativeLayout inerbtyDownLeft = (RelativeLayout) view
					.findViewById(R.id.main_inerbty_down_left);
			inerbtyDownLeft.setOnClickListener(new MainGoodsOnclick(
					inerbtyArray.get(4).getUId()));

			inerbtyDownLeft.setOnClickListener(new MainGoodsOnclick(
					inerbtyArray.get(4).getUId()));
			// inerbty 右下
			RelativeLayout inerbtyDownRight = (RelativeLayout) view
					.findViewById(R.id.main_inerbty_down_right);
			inerbtyDownRight.setOnClickListener(new MainGoodsOnclick(
					inerbtyArray.get(5).getUId()));

			inerbtyDownRight.setOnClickListener(new MainGoodsOnclick(
					inerbtyArray.get(5).getUId()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(HomeMallActivity.this,
					getResources().getString(R.string.bad_network),
					Toast.LENGTH_SHORT).show();
			Toast.makeText(HomeMallActivity.this,
					getResources().getString(R.string.bad_network),
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 首页hotsell,acymer,inerbty商品点击事件
	 * 
	 * @author long bin
	 * 
	 */
	private class MainGoodsOnclick implements OnClickListener {

		private String goodsId;

		public MainGoodsOnclick(String goodsId) {
			this.goodsId = goodsId;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intentLeft = new Intent(HomeMallActivity.this,
					GoodsInfoActivity.class);

			Bundle bundle = new Bundle();
			bundle.putString("goodsId", goodsId);
			intentLeft.putExtras(bundle);
			HomeMallActivity.this.startActivity(intentLeft);
		}

	}

	Timer timer = new Timer();;
	TimerTask timetask = new TimerTask() {

		public void run() {
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}

	};

	Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				setBannerImageShow();
				break;
			}
			super.handleMessage(msg);
		}

	};

	private int bannerIndex = 0;

	private void setBannerImageShow() {
		int indexTemp = bannerIndex + 1;
		if (length != 0)
			indexTemp = indexTemp % length;
		mViewPager.setCurrentItem(indexTemp);
		bannerIndex = indexTemp;
	}

	private long DELAY = 5000;
	private PullToRefreshScrollView mPullToRefreshScrollView;

	private boolean isLogin() {
		if (!myApplication.getIsLogin()) {
			Toast.makeText(HomeMallActivity.this,
					getResources().getString(R.string.please_login),
					Toast.LENGTH_LONG).show();
			Intent intent1 = new Intent(HomeMallActivity.this,
					LoginActivity.class);
			startActivity(intent1);
			return true;
		}

		else {
			return false;
		}
	}

	private AlertDialog dialog;

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
				Intent intentOrder = new Intent(HomeMallActivity.this,
						AllOrderActivity.class);
				if (!isLogin()) {

					HomeMallActivity.this.startActivity(intentOrder);
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
				if (!isLogin()) {

					HomeMallActivity.this.startActivity(intentOrder);
				}
			}

		});
		RelativeLayout myEvent = (RelativeLayout) child
				.findViewById(R.id.function_event);// 活动馆

		myEvent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Intent intentOrder = new
				// Intent(getSherlockActivity(),MyCollectActivity.class);
				// getSherlockActivity().startActivity(intentOrder);
				Intent intent = new Intent(HomeMallActivity.this,
						ActiveGoActivity.class);
				HomeMallActivity.this.startActivity(intent);
			}
		});
		RelativeLayout myMember = (RelativeLayout) child
				.findViewById(R.id.function_member);// 会员购

		myMember.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Intent intentOrder = new
				// Intent(getSherlockActivity(),MyCollectActivity.class);
				// getSherlockActivity().startActivity(intentOrder);

				dialog.show();

				// Intent intentOrder = new
				// Intent(getSherlockActivity(),MyCollectActivity.class);
				// getSherlockActivity().startActivity(intentOrder);
			}
		});
		RelativeLayout myHistory = (RelativeLayout) child
				.findViewById(R.id.function_history);// 浏览历史

		myHistory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intentOrder = new Intent(HomeMallActivity.this,
						HistoryActivity.class);
				// if (!isLogin()) {

				HomeMallActivity.this.startActivity(intentOrder);
				// }
			}
		});
		RelativeLayout myCoupon = (RelativeLayout) child
				.findViewById(R.id.function_coupon);// 积分卡券

		myCoupon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intentOrder = new Intent(HomeMallActivity.this,
						IntegeralActivity.class);
				if (!isLogin()) {

					HomeMallActivity.this.startActivity(intentOrder);
				}
			}
		});
		RelativeLayout mySkin = (RelativeLayout) child
				.findViewById(R.id.function_skin);// 肌肤测试

		mySkin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intentOrder = new Intent(HomeMallActivity.this,
						SkinHomeActivity.class);
				HomeMallActivity.this.startActivity(intentOrder);
				// dialog.show();
				// Intent intentOrder = new
				// Intent(getSherlockActivity(),IntegeralActivity.class);
				// getSherlockActivity().startActivity(intentOrder);
			}
		});
		RelativeLayout myMessage = (RelativeLayout) child
				.findViewById(R.id.function_message);// 消息中心

		myMessage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Intent intentOrder = new
				// Intent(getSherlockActivity(),PersonActivity.class);
				// getSherlockActivity().startActivity(intentOrder);
				// if (!isLogin()) {
				// Intent intentOrder = new Intent(getSherlockActivity(),
				// PersonActivity.class);
				// getSherlockActivity().startActivity(intentOrder);
				// }
				dialog.show();
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		imageLoader.stop();
		unregisterReceiver(receiver);
	}

	// 双击返回键退出程序
	private long exitTime = 0;

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.exit),
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				// ((MyApplication)getApplication()).setUserId("");
				// ((MyApplication)getApplication()).setToken("");
				finish();
				// System.exit(0);
			}
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	private class InnerReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (Consts.UPDATE_CHANGE.equals(action)) {
				number = cartsDataManage.getCartAmount();
				cartImage.setVisibility(View.VISIBLE);
				cartImage.setText(number + "");
			}if(Consts.BROAD_UPDATE_CHANGE.equals(action)){
				cartImage.setVisibility(View.GONE);
			}if(Consts.DELETE_CART.equals(action)){
				number = cartsDataManage.getCartAmount();
				if(number == 0)
					cartImage.setVisibility(View.GONE);
				else{
					cartImage.setText(number+"");
				}
				}
		}
	}
}