package com.yidejia.app.mall.widget;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.goodinfo.GoodsInfoActivity;
import com.yidejia.app.mall.model.BaseProduct;

/**
 * 滑动的控件
 * 
 * @author LiuYong
 * 
 */
public class BannerView {
	private ViewGroup mMainView;
	private ArrayList<BaseProduct> bannerArray;
	private Activity context;
	private YLViewPager mViewPager;
	private SlideImageLayout mSlideLayout;
	private ImageView mImageCircleViews[];
	private ViewGroup mImageCircleView = null;
	
	private int width;	//屏幕宽
	private int height;	//按比例的图片高
	private int length = 0;	//图片轮播的数量
	private int currentIndex = 0;	//轮播的当前位置
	private long DELAY = 10000;	//轮播图片轮播的毫秒数
	
	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageLoadingListener animateFirstListener;

	private Timer timer;
	private TimerTask timetask;
	private Handler handler;
	
	public BannerView(ArrayList<BaseProduct> baseProducts, Activity context) {
		this.bannerArray = baseProducts;
		this.context = context;
		animateFirstListener = MyApplication.getInstance().getImageLoadingListener();
		
		handler = new Handler() {

			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					setBannerImageShow();
					break;
				}
				super.handleMessage(msg);
			}

		};
	}

	/**
	 * 
	 * @return 首页轮播的控件
	 */
	@SuppressWarnings("deprecation")
	public ViewGroup getMainListFirstItem() {
		if(null != bannerArray) {
			length = bannerArray.size();
		}
		LayoutInflater inflater = LayoutInflater.from(context);
		mMainView = (ViewGroup) inflater.inflate(
				R.layout.layout_first_item_in_main_listview, null);
		if (length == 0)
			return mMainView;
		mViewPager = (YLViewPager) mMainView
				.findViewById(R.id.image_slide_page);
		
		width = context.getWindowManager().getDefaultDisplay().getWidth();
		height = (int)((width / 320f) * 160f);
		
		mSlideLayout = new SlideImageLayout(context, width);
		mSlideLayout.setCircleImageLayout(length);
		mImageCircleViews = new ImageView[length];
		mImageCircleView = (ViewGroup) mMainView
				.findViewById(R.id.layout_circle_images);
		for (int i = 0; i < length; i++) {

			mImageCircleViews[i] = mSlideLayout.getCircleImageLayout(i);
			mImageCircleView.addView(mSlideLayout.getLinearLayout(
					mImageCircleViews[i], 9, 9));
		}

		// 设置ViewPager
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
		mViewPager.setLayoutParams(layoutParams);
		mViewPager.setAdapter(new SlideImageAdapter(bannerArray));
		mViewPager.setOnPageChangeListener(new ImagePageChangeListener());
		mViewPager.setCurrentItem(0);
		mViewPager.setOffscreenPageLimit(2);
		currentIndex = 0;
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
			inflater = LayoutInflater.from(context);
			initDisplayImageOption();
		}

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
			View imageLayout = inflater.inflate(R.layout.item_pager_image,
					view, false);
			final ImageView imageView = (ImageView) imageLayout
					.findViewById(R.id.image);
			ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
			imageView.setLayoutParams(layoutParams);
			ImageLoader.getInstance().init(MyApplication.getInstance().initConfig());
			imageLoader.displayImage(bannerArray.get(position).getImgUrl(),
					imageView, options, animateFirstListener);
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

		public class ImageOnClickListener implements OnClickListener {
			private int index;

			public ImageOnClickListener(int index) {
				this.index = index;
			}

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,
						GoodsInfoActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("goodsId", bannerArray.get(index).getUId());
				intent.putExtras(bundle);
				context.startActivity(intent);
			}
		}
	}


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
		}

		@Override
		public void onPageSelected(int index) {
			mImageCircleViews[index % length]
					.setBackgroundResource(R.drawable.dot1);
			for (int i = 0; i < length; i++) {
				// mSlideTitle.setText(""+i);

				if (index % length != i) {
					mImageCircleViews[i % length]
							.setBackgroundResource(R.drawable.dot2);
				}
			}
			currentIndex = index % length;
		}

	}
	

	public void startTimer() {
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
		
		timer.schedule(timetask, DELAY, DELAY);
	}
	
	public void stopTimer() {

		if (timer != null) {
			timer.cancel();
			timer = null;
		}

		if (timetask != null) {
			timetask.cancel();
			timetask = null;
		}

	}
	

	
//	public void 

	private void setBannerImageShow() {
		int indexTemp = currentIndex + 1;
		if (length != 0)
			indexTemp = indexTemp % length;
		mViewPager.setCurrentItem(indexTemp);
		currentIndex = indexTemp;
	}

	private void initDisplayImageOption() {
		options = MyApplication.getInstance().initBannerImageOption();
	}

}
