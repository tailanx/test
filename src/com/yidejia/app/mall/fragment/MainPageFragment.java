package com.yidejia.app.mall.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.yidejia.app.mall.GoodsInfoActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.SearchActivity;
import com.yidejia.app.mall.SlideImageLayout;
import com.yidejia.app.mall.datamanage.MainPageDataManage;
import com.yidejia.app.mall.initview.HotSellView;
import com.yidejia.app.mall.model.BaseProduct;
import com.yidejia.app.mall.model.MainProduct;
import com.yidejia.app.mall.net.homepage.GetHomePage;
import com.yidejia.app.mall.view.AllOrderActivity;
import com.yidejia.app.mall.view.IntegeralActivity;
import com.yidejia.app.mall.view.LoginActivity;
import com.yidejia.app.mall.view.MyCollectActivity;
import com.yidejia.app.mall.view.PersonActivity;
import com.yidejia.app.mall.widget.YLViewPager;

public class MainPageFragment extends SherlockFragment {

	public static MainPageFragment newInstance(int title) {

		MainPageFragment fragment = new MainPageFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("main", title);
		fragment.setArguments(bundle);

		return fragment;
	} 

	private int main;
	private int defaultInt = -1;
	private String TAG = "MainPageFragment";
	private PullToRefreshScrollView mPullToRefreshScrollView;
	private TextView main_mall_notice_content;
	private MyApplication myApplication;
	private AlertDialog dialog;

	// private RelativeLayout myorderLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		main = (bundle != null) ? bundle.getInt("main") : defaultInt;
		getSherlockActivity().getSupportActionBar().setCustomView(R.layout.actionbar_main_home_title);
		// getSherlockActivity().getSupportActionBar().setCustomView(R.layout.actionbar_main_home_title);
	}

	private View view;
	private LayoutInflater inflater;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.activity_main_layout, container, false);
		myApplication = (MyApplication) getSherlockActivity().getApplication();
		dialog = new Builder(getSherlockActivity())
				.setTitle(getResources().getString(R.string.tips))
				.setIcon(R.drawable.ic_launcher)
				.setMessage(getResources().getString(R.string.waiting4open))
//				.setNegativeButton(
//						getSherlockActivity().getResources().getString(
//								R.string.cancel), null)
				.setPositiveButton(
						getSherlockActivity().getResources().getString(
								R.string.sure), null).create();
		view = inflater
				.inflate(R.layout.activity_main_layout, container, false);
		this.inflater = inflater;
		switch (main) {
		case 0:

			break;

		default:
			break;
		}

		return view;
	}

	
	/**
	 * activity启动时调用这个方法显示界面
	 * 
	 * @param view
	 * @param inflater
	 */
	private void createView(View view, LayoutInflater inflater) {
		getMainListFirstItem();
		FrameLayout layout = (FrameLayout) view.findViewById(R.id.layout);
		// mMainView = (ViewGroup) inflater.inflate(
		// R.layout.layout_first_item_in_main_listview, null);
		layout.addView(mMainView);
		RelativeLayout shorcutLayout = (RelativeLayout) view.findViewById(R.id.function_parent_layout);
		
		View child = inflater.inflate(R.layout.main_function, null);
		shorcutLayout.addView(child);

		mPullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.main_pull_refresh_scrollview);
		mPullToRefreshScrollView = (PullToRefreshScrollView) view
				.findViewById(R.id.main_pull_refresh_scrollview);
		mPullToRefreshScrollView.setScrollingWhileRefreshingEnabled(true);
		mPullToRefreshScrollView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
		mPullToRefreshScrollView
				.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
		mPullToRefreshScrollView.setVerticalScrollBarEnabled(false); // 禁用垂直滚动
		mPullToRefreshScrollView.setHorizontalScrollBarEnabled(false); // 禁用水平滚动
	
		String label = getResources().getString(R.string.update_time)
				+ DateUtils.formatDateTime(getSherlockActivity()
						.getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
		mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
				label);
		mPullToRefreshScrollView.setOnRefreshListener(listener);

		functionIntent(child);
		main_mall_notice_content = (TextView) view
				.findViewById(R.id.main_mall_notice_content);
	}

		
	private boolean isLogin() {
		if (!myApplication.getIsLogin()) {
			Toast.makeText(getSherlockActivity(),
					getResources().getString(R.string.please_login),
					Toast.LENGTH_LONG).show();
			Intent intent1 = new Intent(getSherlockActivity(),
					LoginActivity.class);
			startActivity(intent1);
			return true;
		}

		else {
			return false;
		}
	}

	private OnRefreshListener<ScrollView> listener = new OnRefreshListener<ScrollView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
			try {
				// TODO Auto-generated method stub
				String label = getResources().getString(R.string.update_time)
						+ DateUtils.formatDateTime(getSherlockActivity()
								.getApplicationContext(), System
								.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// mPullToRefreshScrollView.setRefreshing();
				MainPageDataManage manage = new MainPageDataManage(getSherlockActivity(), mPullToRefreshScrollView);
				
				manage.getMainPageData();
				bannerArray = manage.getBannerArray();
				acymerArray = manage.getAcymerArray();
				inerbtyArray = manage.getInerbtyArray();
				hotsellArray = manage.getHotSellArray();
				ggTitleArray = manage.getGGTitle();
				HotSellView hotSellView = new HotSellView(view,getSherlockActivity());
				hotSellView.initHotSellView(hotsellArray);
				hotSellView.initAcymerView(acymerArray);
				hotSellView.initInerbtyView(inerbtyArray);
				main_mall_notice_content.setText(ggTitleArray.get(0));


//				MainPageDataManage manage = new MainPageDataManage(
//						getSherlockActivity(), mPullToRefreshScrollView);
//
//				manage.getMainPageData();
//				bannerArray = manage.getBannerArray();
//				acymerArray = manage.getAcymerArray();
//				inerbtyArray = manage.getInerbtyArray();
//				hotsellArray = manage.getHotSellArray();
//				ggTitleArray = manage.getGGTitle();
//				HotSellView hotSellView = new HotSellView(view,
//						getSherlockActivity());
//				hotSellView.initHotSellView(hotsellArray);
//				hotSellView.initAcymerView(acymerArray);
//				hotSellView.initInerbtyView(inerbtyArray);
//				main_mall_notice_content.setText(ggTitleArray.get(0));
//				getMainListFirstItem();
				// mPullToRefreshScrollView.onRefreshComplete();
				closeTask();
				task = new Task();
				task.execute();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(getSherlockActivity(), getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
				Toast.makeText(getSherlockActivity(),
						getResources().getString(R.string.bad_network),
						Toast.LENGTH_SHORT).show();
			}
		}

		
	};
	
	private void closeTask(){
		if(task != null && task.getStatus().RUNNING == AsyncTask.Status.RUNNING){
			task.cancel(true);
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
			RelativeLayout hotsellLeft = (RelativeLayout)view.findViewById(R.id.main_hot_sell_left);
			hotsellLeft.setOnClickListener(new MainGoodsOnclick(hotsellArray.get(0).getUId()));
			
			hotsellLeft.setOnClickListener(new MainGoodsOnclick(hotsellArray
					.get(0).getUId()));
			// hot sell 右上
			RelativeLayout hotsellRightTop = (RelativeLayout)view.findViewById(R.id.main_hot_sell_right_top);
			hotsellRightTop.setOnClickListener(new MainGoodsOnclick(hotsellArray.get(1).getUId()));
		
			hotsellRightTop.setOnClickListener(new MainGoodsOnclick(
					hotsellArray.get(1).getUId()));
			// hot sell 右下
			RelativeLayout hotsellRightDown = (RelativeLayout)view.findViewById(R.id.main_hot_sell_right_down);
			hotsellRightDown.setOnClickListener(new MainGoodsOnclick(hotsellArray.get(2).getUId()));
		
			hotsellRightDown.setOnClickListener(new MainGoodsOnclick(
					hotsellArray.get(2).getUId()));

			// acymer 左边
			RelativeLayout acymeiLeft = (RelativeLayout)view.findViewById(R.id.main_acymei_left);
			acymeiLeft.setOnClickListener(new MainGoodsOnclick(acymerArray.get(0).getUId()));

			acymeiLeft.setOnClickListener(new MainGoodsOnclick(acymerArray.get(
					0).getUId()));
			// acymer 右上
			RelativeLayout acymeiRightTop = (RelativeLayout)view.findViewById(R.id.main_acymei_right_top);
			acymeiRightTop.setOnClickListener(new MainGoodsOnclick(acymerArray.get(1).getUId()));
	
			acymeiRightTop.setOnClickListener(new MainGoodsOnclick(acymerArray
					.get(1).getUId()));
			// acymer 右下
			RelativeLayout acymeiRightDown = (RelativeLayout)view.findViewById(R.id.main_acymei_right_down);
			acymeiRightDown.setOnClickListener(new MainGoodsOnclick(acymerArray.get(2).getUId()));
	
			acymeiRightDown.setOnClickListener(new MainGoodsOnclick(acymerArray
					.get(2).getUId()));

			// inerbty 左上
			RelativeLayout inerbtyTopLeft = (RelativeLayout)view.findViewById(R.id.main_inerbty_top_left);
			inerbtyTopLeft.setOnClickListener(new MainGoodsOnclick(inerbtyArray.get(0).getUId()));
		
			inerbtyTopLeft.setOnClickListener(new MainGoodsOnclick(inerbtyArray
					.get(0).getUId()));
			// inerbty 右上
			RelativeLayout inerbtyTopRight = (RelativeLayout)view.findViewById(R.id.main_inerbty_top_right);
			inerbtyTopRight.setOnClickListener(new MainGoodsOnclick(inerbtyArray.get(1).getUId()));
	
			inerbtyTopRight.setOnClickListener(new MainGoodsOnclick(
					inerbtyArray.get(1).getUId()));
			// inerbty 左中
			RelativeLayout nerbtyMidLeft = (RelativeLayout)view.findViewById(R.id.main_inerbty_mid_left);
			nerbtyMidLeft.setOnClickListener(new MainGoodsOnclick(inerbtyArray.get(2).getUId()));
	
			nerbtyMidLeft.setOnClickListener(new MainGoodsOnclick(inerbtyArray
					.get(2).getUId()));
			// inerbty 右中
			RelativeLayout inerbtyMidRight = (RelativeLayout)view.findViewById(R.id.main_inerbty_mid_right);
			inerbtyMidRight.setOnClickListener(new MainGoodsOnclick(inerbtyArray.get(3).getUId()));

			inerbtyMidRight.setOnClickListener(new MainGoodsOnclick(
					inerbtyArray.get(3).getUId()));
			// inerbty 左下
			RelativeLayout inerbtyDownLeft = (RelativeLayout)view.findViewById(R.id.main_inerbty_down_left);
			inerbtyDownLeft.setOnClickListener(new MainGoodsOnclick(inerbtyArray.get(4).getUId()));

			inerbtyDownLeft.setOnClickListener(new MainGoodsOnclick(
					inerbtyArray.get(4).getUId()));
			// inerbty 右下
			RelativeLayout inerbtyDownRight = (RelativeLayout)view.findViewById(R.id.main_inerbty_down_right);
			inerbtyDownRight.setOnClickListener(new MainGoodsOnclick(inerbtyArray.get(5).getUId()));

			inerbtyDownRight.setOnClickListener(new MainGoodsOnclick(
					inerbtyArray.get(5).getUId()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(getSherlockActivity(), getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
			Toast.makeText(getSherlockActivity(),
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
			Intent intentLeft = new Intent(getSherlockActivity(), GoodsInfoActivity.class);
	
			Bundle bundle = new Bundle();
			bundle.putString("goodsId", goodsId);
			intentLeft.putExtras(bundle);
			getSherlockActivity().startActivity(intentLeft);
		}

	}

	/**
	 * 快捷功能那块跳到相应界面
	 * 
	 * @param child
	 */
	private void functionIntent(View child) {
		RelativeLayout myOrder = (RelativeLayout) child.findViewById(R.id.function_my_order);//订单


		myOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intentOrder = new Intent(getSherlockActivity(),AllOrderActivity.class);
				if (!isLogin()) {
					
					getSherlockActivity().startActivity(intentOrder);
				}
			}
		});

		RelativeLayout myCollect = (RelativeLayout) child.findViewById(R.id.function_my_favorite);//收藏

		myCollect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intentOrder = new Intent(getSherlockActivity(),MyCollectActivity.class);
				if (!isLogin()) {
					
					getSherlockActivity().startActivity(intentOrder);
				}
			}

		});
		RelativeLayout myEvent = (RelativeLayout) child.findViewById(R.id.function_event);//活动馆
	
		myEvent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
//				Intent intentOrder = new Intent(getSherlockActivity(),MyCollectActivity.class);
//				getSherlockActivity().startActivity(intentOrder);
				
					dialog.show();
				
				// Intent intentOrder = new
				// Intent(getSherlockActivity(),MyCollectActivity.class);
				// getSherlockActivity().startActivity(intentOrder);
			}
		});
		RelativeLayout myMember = (RelativeLayout) child.findViewById(R.id.function_member);//会员购

		myMember.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
//				Intent intentOrder = new Intent(getSherlockActivity(),MyCollectActivity.class);
//				getSherlockActivity().startActivity(intentOrder);
				
					dialog.show();
				
				// Intent intentOrder = new
				// Intent(getSherlockActivity(),MyCollectActivity.class);
				// getSherlockActivity().startActivity(intentOrder);
			}
		});
		RelativeLayout myHistory = (RelativeLayout) child.findViewById(R.id.function_history);//浏览历史

		myHistory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intentOrder = new Intent(getSherlockActivity(), SearchActivity.class);
//				if (!isLogin()) {
				
					getSherlockActivity().startActivity(intentOrder);
//				}
			}
		});
		RelativeLayout myCoupon = (RelativeLayout) child.findViewById(R.id.function_coupon);//积分卡券
	
		myCoupon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intentOrder = new Intent(getSherlockActivity(),IntegeralActivity.class);
				if (!isLogin()) {
			
					getSherlockActivity().startActivity(intentOrder);
				}
			}
		});
		RelativeLayout mySkin = (RelativeLayout) child.findViewById(R.id.function_skin);//肌肤测试
	
		mySkin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
//				Intent intentOrder = new Intent(getSherlockActivity(),IntegeralActivity.class);
//				getSherlockActivity().startActivity(intentOrder);
					dialog.show();
					// Intent intentOrder = new
					// Intent(getSherlockActivity(),IntegeralActivity.class);
					// getSherlockActivity().startActivity(intentOrder);
			}
		});
		RelativeLayout myMessage = (RelativeLayout) child.findViewById(R.id.function_message);//消息中心

		myMessage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
//				Intent intentOrder = new Intent(getSherlockActivity(),PersonActivity.class);
//				getSherlockActivity().startActivity(intentOrder);
//				if (!isLogin()) {
//					Intent intentOrder = new Intent(getSherlockActivity(),
//							PersonActivity.class);
//					getSherlockActivity().startActivity(intentOrder);
//				}
				dialog.show();
			}
		});
	}

	private ArrayList<BaseProduct> bannerArray;
	private ArrayList<MainProduct> acymerArray;
	private ArrayList<MainProduct> inerbtyArray;
	private ArrayList<MainProduct> hotsellArray;
	private ArrayList<String> ggTitleArray;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		try {
			super.onActivityCreated(savedInstanceState);
			Log.d(TAG, "TestFragment-----onActivityCreated");
			// intentToView(view);
			MainPageDataManage manage = new MainPageDataManage(getSherlockActivity(), null);
			manage.getMainPageData();
			bannerArray = manage.getBannerArray();
			acymerArray = manage.getAcymerArray();
			inerbtyArray = manage.getInerbtyArray();
			hotsellArray = manage.getHotSellArray();
			ggTitleArray = manage.getGGTitle();

//			MainPageDataManage manage = new MainPageDataManage(
//					getSherlockActivity(), null);
//			manage.getMainPageData();
//			bannerArray = manage.getBannerArray();
//			acymerArray = manage.getAcymerArray();
//			inerbtyArray = manage.getInerbtyArray();
//			hotsellArray = manage.getHotSellArray();
//			ggTitleArray = manage.getGGTitle();

//			createView(view, inflater);
//			HotSellView hotSellView = new HotSellView(view,
//					getSherlockActivity());
//			hotSellView.initHotSellView(hotsellArray);
//			hotSellView.initAcymerView(acymerArray);
//			hotSellView.initInerbtyView(inerbtyArray);
//			intentToView(view);
//			main_mall_notice_content.setText(ggTitleArray.get(0));
			closeTask();
			task = new Task();
			task.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(getSherlockActivity(),
					getResources().getString(R.string.bad_network),
					Toast.LENGTH_SHORT).show();
		}
	}
	
	private boolean isFirstIn = true;
	private Task task;
	
	private class Task extends AsyncTask<Void, Void, Boolean>{

		private ProgressDialog bar;
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-gene|rated method stub
			GetHomePage getHomePage = new GetHomePage();
			String httpresp;
			try {
				httpresp = getHomePage.getHomePageJsonString();
				boolean issuccess = getHomePage.analysisGetHomeJson(httpresp);
				bannerArray = getHomePage.getBannerArray();
				acymerArray = getHomePage.getAcymerArray();
				inerbtyArray = getHomePage.getInerbtyArray();
				hotsellArray = getHomePage.getHotSellArray();
				ggTitleArray = getHomePage.getGGTitle();
				return issuccess;
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
				bar = new ProgressDialog(getSherlockActivity());
				bar.setCancelable(false);
				bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				bar.show();
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//			try{
			if(result){
				createView(view, inflater);
			HotSellView hotSellView = new HotSellView(view,getSherlockActivity());
		
				hotSellView.initHotSellView(hotsellArray);
				hotSellView.initAcymerView(acymerArray);
				hotSellView.initInerbtyView(inerbtyArray);
				intentToView(view);
				main_mall_notice_content.setText(ggTitleArray.get(0));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			Toast.makeText(getSherlockActivity(), getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
			} else{
				Toast.makeText(getSherlockActivity(),
						getResources().getString(R.string.bad_network),
						Toast.LENGTH_SHORT).show();


			}
			if(isFirstIn){
				bar.dismiss();
				isFirstIn = false;
			} else {
				mPullToRefreshScrollView.onRefreshComplete();
			}
		}
		
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d(TAG, "TestFragment-----onStart");
	}

	private ViewGroup mMainView = null;
	private YLViewPager mViewPager;
	private ViewGroup mImageCircleView = null;
	private SlideImageLayout mSlideLayout;
	private ImageView[] mImageCircleViews;
//	private static final int[] ids = { R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};
	// private static final int[] ids = { R.drawable.banner1,
	// R.drawable.banner2, R.drawable.banner3};
	private int length = 0;

	/**
	 * 
	 * @return 首页轮播的控件
	 */
	private ViewGroup getMainListFirstItem() {
		length = bannerArray.size();
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = (ViewGroup) inflater.inflate(
				R.layout.layout_first_item_in_main_listview, null);
		if(length == 0) return mMainView;
		mViewPager = (YLViewPager) mMainView.findViewById(R.id.image_slide_page);
		if (length == 0)
			return mMainView;
		mViewPager = (YLViewPager) mMainView
				.findViewById(R.id.image_slide_page);
		mSlideLayout = new SlideImageLayout(getActivity(),
				mMainView.getContext(), width);
		mSlideLayout.setCircleImageLayout(length);
		mImageCircleViews = new ImageView[length];
		mImageCircleView = (ViewGroup) mMainView
				.findViewById(R.id.layout_circle_images);
		// mSlideTitle = (TextView) mMainView.findViewById(R.id.tvSlideTitle);

		Log.i(TAG, "length--------------------------------"+bannerArray.size());
		Log.i(TAG,
				"length--------------------------------" + bannerArray.size());
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
		private LayoutInflater inflater;

		private ArrayList<BaseProduct> bannerArray;

		public SlideImageAdapter(ArrayList<BaseProduct> bannerArray) {
			this.bannerArray = bannerArray;
			inflater = getActivity().getLayoutInflater();
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
            View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
			final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
//			Toast.makeText(getSherlockActivity(), bannerArray.get(position).getImgUrl(), Toast.LENGTH_SHORT).show();
			imageLoader.displayImage(bannerArray.get(position).getImgUrl(), imageView, options,
					animateFirstListener);
//			View imageLayout = inflater.inflate(R.layout.item_pager_image,
//					view, false);
//			final ImageView imageView = (ImageView) imageLayout
//					.findViewById(R.id.image);
//			// Toast.makeText(getSherlockActivity(),
//			// bannerArray.get(position).getImgUrl(),
//			// Toast.LENGTH_SHORT).show();
//			imageLoader.displayImage(bannerArray.get(position).getImgUrl(),
//					imageView, options, animateFirstListener);
//			// imageView.setBackgroundResource(ids[position%length]);
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
//        		Toast.makeText(getActivity(), "我点击了第"+"["+pageIndex%length+"]几个", Toast.LENGTH_SHORT).show();
        		Intent intent = new Intent(getSherlockActivity(), GoodsInfoActivity.class);
				// Toast.makeText(getActivity(),
				// "我点击了第"+"["+pageIndex%length+"]几个",
				// Toast.LENGTH_SHORT).show();
				Bundle bundle = new Bundle();
				bundle.putString("goodsId", bannerArray.get(index).getUId());
				intent.putExtras(bundle);
				getSherlockActivity().startActivity(intent);
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
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	
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
            mImageCircleViews[index%length].setBackgroundResource(R.drawable.dot1);
			mImageCircleViews[index % length]
					.setBackgroundResource(R.drawable.dot1);
			for (int i = 0; i < length; i++) {
				// mSlideTitle.setText(""+i);

				if (index % length != i) {
                    mImageCircleViews[i%length].setBackgroundResource(R.drawable.dot2);
					mImageCircleViews[i % length]
							.setBackgroundResource(R.drawable.dot2);
				}
			}
		}

	}

	private int width;

    
    
	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	private void initDisplayImageOption() {
		options = new DisplayImageOptions.Builder()
//			.showStubImage(R.drawable.hot_sell_right_top_image)
//			.showImageOnFail(R.drawable.hot_sell_right_top_image)
//			.showImageForEmptyUri(R.drawable.hot_sell_right_top_image)
//			.cacheInMemory(true)
//			.cacheOnDisc(true)
//			.build();
			.showStubImage(R.drawable.banner_bg)
				.showImageOnFail(R.drawable.banner_bg)
				.showImageForEmptyUri(R.drawable.banner_bg).cacheInMemory(true)
				.cacheOnDisc(true).build();
	}

	static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
	


	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
	
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
	
}