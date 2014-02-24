/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.yidejia.app.mall.view;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.BaseProduct;
import com.yidejia.app.mall.photoview.PhotoView;
import com.yidejia.app.mall.util.Consts;

public class ViewPagerActivity extends BaseActivity {

	private HackyViewPager mViewPager;
	private static final String STATE_POSITION = "STATE_POSITION";
	private ArrayList<BaseProduct> sDrawables;
	private DisplayImageOptions options;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setActionbarConfig();
//		getSupportActionBar().setCustomView(R.layout.actionbar_back);
//		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//		getSupportActionBar().setDisplayShowCustomEnabled(true);
//		getSupportActionBar().setDisplayShowTitleEnabled(false);
//		getSupportActionBar().setDisplayShowHomeEnabled(false);
		

		options = MyApplication.getInstance().initGoodsImageOption();
		
		Bundle bundle = getIntent().getExtras();
		sDrawables = (ArrayList<BaseProduct>) getIntent().getSerializableExtra(
				Consts.IMAGES);
		if (sDrawables.isEmpty()) {
			sDrawables = new ArrayList<BaseProduct>();
		}
		int pagerPosition = 0;
		if(null != bundle)
			pagerPosition = bundle.getInt(Consts.IMAGE_POSITION, 0);

		if (savedInstanceState != null) {
			pagerPosition = savedInstanceState.getInt(STATE_POSITION);
		}

		setContentView(R.layout.activity_pic_view);
		
		ImageView ivBack = (ImageView) findViewById(R.id.iv_pic_back);
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ViewPagerActivity.this.finish();
			}
		});
		
		mViewPager = (HackyViewPager) findViewById(R.id.vp_hack);
		
//		RelativeLayout layout = new RelativeLayout(this);
//		layout.setBackgroundResource(R.color.main_bg);
//		
//		
//		RelativeLayout.LayoutParams lpPicView = new RelativeLayout.LayoutParams(
//				ViewGroup.LayoutParams.MATCH_PARENT,
//				ViewGroup.LayoutParams.MATCH_PARENT);
//		lpPicView.addRule(RelativeLayout., anchor);
//		lpPicView.addRule(RelativeLayout.BELOW, ivBack.getId());  
		
		
//		mViewPager = new HackyViewPager(this);
		
//		layout.addView(mViewPager, lpPicView);
		
		/*RelativeLayout.LayoutParams lpBack = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		lpBack.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		lpBack.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);

		ImageView ivBack = new ImageView(this);
		ivBack.setId(1);
		ivBack.setImageResource(R.drawable.pic_back);

		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ViewPagerActivity.this.finish();
			}
		});

		layout.addView(ivBack, lpBack);*/
		
//		setContentView(layout);
		
		mViewPager.setAdapter(new SamplePagerAdapter());
	}

	private class SamplePagerAdapter extends PagerAdapter {
		//
		// private static int[] sDrawables = { R.drawable.wallpaper,
		// R.drawable.a1, R.drawable.a2, R.drawable.a3,
		// R.drawable.wallpaper, R.drawable.wallpaper };

		@Override
		public int getCount() {
			if (sDrawables.isEmpty()) {
				sDrawables = new ArrayList<BaseProduct>();
				return 0;
			} else {
				return sDrawables.size();
			}
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			PhotoView photoView = new PhotoView(container.getContext());
			
			ImageLoader.getInstance().init(MyApplication.getInstance().initConfig());
			ImageLoader.getInstance().displayImage(
					sDrawables.get(position).getImgUrl(), photoView, options,
					MyApplication.getInstance().getImageLoadingListener());

			// Now just add PhotoView to ViewPager and return it
			container.addView(photoView, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);

			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPageStart(this, "图片放大和缩小页面");
	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "图片放大和缩小页面");
	}

}
