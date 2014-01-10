/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
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

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.R.drawable;
import com.yidejia.app.mall.R.id;
import com.yidejia.app.mall.R.layout;
import com.yidejia.app.mall.model.BaseProduct;
import com.yidejia.app.mall.util.Consts;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class ImagePagerActivity extends Activity {

	private static final String STATE_POSITION = "STATE_POSITION";

	DisplayImageOptions options;

	ViewPager pager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ac_image_pager);

		Bundle bundle = getIntent().getExtras();
		ArrayList<BaseProduct> imageUrls = (ArrayList<BaseProduct>) getIntent().getSerializableExtra(Consts.IMAGES);
		int pagerPosition = bundle.getInt(Consts.IMAGE_POSITION, 0);

		if (savedInstanceState != null) {
			pagerPosition = savedInstanceState.getInt(STATE_POSITION);
		}
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.image_bg)
		.showImageOnFail(R.drawable.image_bg)
		.showImageForEmptyUri(R.drawable.image_bg).cacheInMemory(true)
		.cacheOnDisc(true).build();

		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new ImagePagerAdapter(imageUrls));
		pager.setCurrentItem(pagerPosition);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		imageLoader.init(ImageLoaderConfiguration.createDefault(ImagePagerActivity.this));
		imageLoader.destroy();
		super.onDestroy();
	}
	
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_POSITION, pager.getCurrentItem());
	}

	private class ImagePagerAdapter extends PagerAdapter {

		private ArrayList<BaseProduct> images;
		private LayoutInflater inflater;

		ImagePagerAdapter(ArrayList<BaseProduct> images) {
			this.images = images;
			inflater = getLayoutInflater();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public void finishUpdate(View container) {
		}

		@Override
		public int getCount() {
			return images.size();
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
			ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
//			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
			imageLoader.init(ImageLoaderConfiguration.createDefault(ImagePagerActivity.this));
			imageLoader.displayImage(images.get(position).getImgUrl().replace("!200", ""), imageView, options, new SimpleImageLoadingListener() {
				
				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					String message = null;
					switch (failReason.getType()) {
						case IO_ERROR:
							message = "Input/Output error";
							break;
						case DECODING_ERROR:
							message = "Image can't be decoded";
							break;
						case NETWORK_DENIED:
							message = "Downloads are denied";
							break;
						case OUT_OF_MEMORY:
							message = "Out Of Memory error";
							break;
						case UNKNOWN:
							message = "Unknown error";
							break;
					}
//					Toast.makeText(ImagePagerActivity.this, message, Toast.LENGTH_SHORT).show();
					Log.e(ImagePagerActivity.class.getName(), message);
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				}
			});

			((ViewPager) view).addView(imageLayout, 0);
			return imageLayout;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View container) {
		}
	}
}