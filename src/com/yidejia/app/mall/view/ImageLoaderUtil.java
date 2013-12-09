package com.yidejia.app.mall.view;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class ImageLoaderUtil {
	public DisplayImageOptions getOptions() {
		return options;
	}
	public void setOptions(DisplayImageOptions options) {
		this.options = options;
	}
	public ImageLoader getImageLoader() {
		return imageLoader;
	}
	public void setImageLoader(ImageLoader imageLoader) {
		this.imageLoader = imageLoader;
	}
	public ImageLoadingListener getAnimateFirstListener() {
		return animateFirstListener;
	}
	public void setAnimateFirstListener(ImageLoadingListener animateFirstListener) {
		this.animateFirstListener = animateFirstListener;
	}
	public static List<String> getDisplayedimages() {
		return displayedImages;
	}
	public ImageLoaderUtil(){
		initDisplayImageOption();
	}
	
	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private void initDisplayImageOption(){
		options = new DisplayImageOptions.Builder()
//			.showStubImage(R.drawable.hot_sell_right_top_image)
//			.showImageOnFail(R.drawable.hot_sell_right_top_image)
//			.showImageForEmptyUri(R.drawable.hot_sell_right_top_image)
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.build();
	}
	static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {


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
