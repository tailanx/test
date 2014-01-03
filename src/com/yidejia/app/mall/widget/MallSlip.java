package com.yidejia.app.mall.widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.yidejia.app.mall.GoodsInfoActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.SlideImageLayout;
import com.yidejia.app.mall.model.BaseProduct;

/**
 * 滑动的控件
 * 
 * @author Administrator
 * 
 */
public class MallSlip {
	private ViewGroup mMainView;
	private ArrayList<BaseProduct> bannerArray;
	private Context context;
	private YLViewPager mViewPager;
	private SlideImageLayout mSlideLayout;
	private ImageView mImageCircleViews[];
	private ViewGroup mImageCircleView = null;

	public MallSlip(ArrayList<BaseProduct> baseProducts, Context context) {
		this.bannerArray = baseProducts;
		this.context = context;
	}

	public ViewGroup getViewGroup() {
		return mMainView;
	}

	public YLViewPager getYlViewPager(){
			return mViewPager;
	}
	/**
	 * 
	 * @return 首页轮播的控件
	 */
	public ViewGroup getMainListFirstItem() {
		length = bannerArray.size();
		LayoutInflater inflater = LayoutInflater.from(context);
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
		mSlideLayout = new SlideImageLayout((Activity) context, context, width);
		mSlideLayout.setCircleImageLayout(length);
		mImageCircleViews = new ImageView[length];
		mImageCircleView = (ViewGroup) mMainView
				.findViewById(R.id.layout_circle_images);
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
			inflater = LayoutInflater.from(context);
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
			imageLoader.init(ImageLoaderConfiguration
					.createDefault(context));
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
				Intent intent = new Intent(context,
						GoodsInfoActivity.class);
				// Toast.makeText(getActivity(),
				// "我点击了第"+"["+pageIndex%length+"]几个",
				// Toast.LENGTH_SHORT).show();
				Bundle bundle = new Bundle();
				bundle.putString("goodsId", bannerArray.get(index).getUId());
				intent.putExtras(bundle);
				context.startActivity(intent);
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

}
