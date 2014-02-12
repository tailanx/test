package com.yidejia.app.mall;

import com.yidejia.app.mall.util.SharedPreferencesUtil;
import com.yidejia.app.mall.widget.YLViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.FrameLayout.LayoutParams;

public class GuideActivity extends Activity{

	private YLViewPager ylViewPager;
	private int currentId = 0;
	
	private int[] images = { R.drawable.boot_page1, R.drawable.boot_page2,
			R.drawable.boot_page3, R.drawable.boot_page4,
			R.drawable.boot_page5, R.drawable.boot_page6 };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.layout_first_item_in_main_listview);
		
		((RelativeLayout) findViewById(R.id.layout_title_text)).setVisibility(View.GONE);
		
		ylViewPager = (YLViewPager) findViewById(R.id.image_slide_page);
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		ylViewPager.setLayoutParams(layoutParams);
		ylViewPager.setAdapter(new SlideAdapter());
		ylViewPager.setOnPageChangeListener(new ImagePageChangeListener());
		ylViewPager.setCurrentItem(currentId);
		ylViewPager.setOffscreenPageLimit(2);
		
	}
	
	private class SlideAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return images.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}
		
		public void destroyItem(ViewGroup container, int position, Object object) {
			// ((ViewPager) view).removeView(mImagePageViewList.get(arg1));
			((ViewPager) container).removeView((View) object);
		}
		
		public Object instantiateItem(ViewGroup view, int position) {
			View imageLayout = getLayoutInflater().inflate(R.layout.item_pager_image,
					view, false);
			final ImageView imageView = (ImageView) imageLayout
					.findViewById(R.id.image);
			imageView.setBackgroundResource(R.drawable.white);
			imageView.setImageResource(images[position]);
			
			imageView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					imageClick();
				}
			});
			
			((ViewPager) view).addView(imageLayout);
			return imageLayout;
		}
		
	}
	
	private void imageClick(){
		if(currentId == images.length - 1){
			SharedPreferencesUtil spUtil = new SharedPreferencesUtil(this);
			spUtil.saveData("appFirstStart", "isFirstStart", false);
			Intent intent = new Intent(this, HomeMallActivity.class);
			intent.putExtra("isFirstIn", true);
			startActivity(intent);
			this.finish();
		} else {
			currentId = (currentId + 1) % images.length;
			ylViewPager.setCurrentItem(currentId);
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
			currentId = index % images.length;
		}

	}
	
}
