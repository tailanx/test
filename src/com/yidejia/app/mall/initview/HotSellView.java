package com.yidejia.app.mall.initview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.MainProduct;

public class HotSellView {
	private View view;
//	private ArrayList<MainProduct> hotsellArray;
	public HotSellView(View view){
		this.view = view;
//		this.hotsellArray = hotsellArray;
		initDisplayImageOption();
	}
	
	public void initHotSellView(ArrayList<MainProduct> hotsellArray){
		MainProduct hotsellProduct;
		//hot sell 左边
		hotsellProduct = hotsellArray.get(0);
		TextView main_hot_sell_goods_title = (TextView) view.findViewById(R.id.main_hot_sell_goods_title);
		main_hot_sell_goods_title.setText(hotsellProduct.getTitle());
		TextView main_hot_sell_goods_details = (TextView) view.findViewById(R.id.main_hot_sell_goods_details);
		main_hot_sell_goods_details.setText(hotsellProduct.getPrice()+"元");
		ImageView main_hot_sell_left_image = (ImageView) view.findViewById(R.id.main_hot_sell_left_image);
		String imageUrl = hotsellProduct.getImgUrl();
		//添加图片
//		imageLoader.displayImage(imageUrl, main_hot_sell_left_image, options,
//				animateFirstListener);
		
		//hot sell 右上
		hotsellProduct = hotsellArray.get(1);
		TextView main_hot_sell_right_top_title = (TextView) view.findViewById(R.id.main_hot_sell_right_top_title);
		main_hot_sell_right_top_title.setText(hotsellProduct.getTitle());
		TextView main_hot_sell_right_top_price = (TextView) view.findViewById(R.id.main_hot_sell_right_top_price);
		main_hot_sell_right_top_price.setText(hotsellProduct.getPrice()+"元");
		ImageView main_hot_sell_right_top_image = (ImageView) view.findViewById(R.id.main_hot_sell_right_top_image);
		imageUrl = hotsellProduct.getImgUrl();
		//添加图片
//		imageLoader.displayImage(imageUrl, main_hot_sell_right_top_image, options,
//				animateFirstListener);
		
		//hot sell 右下
		hotsellProduct = hotsellArray.get(2);
		TextView main_hot_sell_right_down_title = (TextView) view.findViewById(R.id.main_hot_sell_right_down_title);
		main_hot_sell_right_down_title.setText(hotsellProduct.getTitle());
		TextView main_hot_sell_right_down_price = (TextView) view.findViewById(R.id.main_hot_sell_right_down_price);
		main_hot_sell_right_down_price.setText(hotsellProduct.getPrice()+"元");
		ImageView main_hot_sell_right_down_image = (ImageView) view.findViewById(R.id.main_hot_sell_right_down_image);
		imageUrl = hotsellProduct.getImgUrl();
		//添加图片
//		imageLoader.displayImage(imageUrl, main_hot_sell_right_down_image, options,
//				animateFirstListener);
	}
	
	public void initAcymerView(ArrayList<MainProduct> acymerArray){
		MainProduct acymerProduct;
		//acymer 左边
		acymerProduct = acymerArray.get(0);
		TextView main_acymei_goods_title = (TextView) view.findViewById(R.id.main_acymei_goods_title);
		main_acymei_goods_title.setText(acymerProduct.getTitle());
		Log.i("MainPage+acymer", acymerProduct.getTitle());
		TextView main_acymei_goods_details = (TextView) view.findViewById(R.id.main_acymei_goods_details);
		main_acymei_goods_details.setText(acymerProduct.getPrice()+"元");
		ImageView main_acymei_left_image = (ImageView) view.findViewById(R.id.main_acymei_left_image);
		String imageUrl = acymerProduct.getImgUrl();
		//添加图片
//		imageLoader.displayImage(imageUrl, main_acymei_left_image, options,
//				animateFirstListener);
		
		//acymer 右上
		acymerProduct = acymerArray.get(1);
		TextView main_acymei_right_top_title = (TextView) view.findViewById(R.id.main_acymei_right_top_title);
		main_acymei_right_top_title.setText(acymerProduct.getTitle());
		TextView main_acymei_right_top_price = (TextView) view.findViewById(R.id.main_acymei_right_top_price);
		main_acymei_right_top_price.setText(acymerProduct.getPrice()+"元");
		ImageView main_acymei_right_top_image = (ImageView) view.findViewById(R.id.main_acymei_right_top_image);
		imageUrl = acymerProduct.getImgUrl();
		//添加图片
//		imageLoader.displayImage(imageUrl, main_acymei_right_top_image, options,
//				animateFirstListener);
		
		//acymer 右下
		acymerProduct = acymerArray.get(2);
		TextView main_acymei_right_down_title = (TextView) view.findViewById(R.id.main_acymei_right_down_title);
		main_acymei_right_down_title.setText(acymerProduct.getTitle());
		TextView main_acymei_right_down_price = (TextView) view.findViewById(R.id.main_acymei_right_down_price);
		main_acymei_right_down_price.setText(acymerProduct.getPrice()+"元");
		ImageView main_acymei_right_down_image = (ImageView) view.findViewById(R.id.main_acymei_right_down_image);
		imageUrl = acymerProduct.getImgUrl();
		//添加图片
//		imageLoader.displayImage(imageUrl, main_acymei_right_down_image, options,
//				animateFirstListener);
	}
	
	public void initInerbtyView(ArrayList<MainProduct> inerbtyArray){
		MainProduct inerbtyProduct;
		//inerbty 左上
		inerbtyProduct = inerbtyArray.get(0);
		TextView main_inerbty_top_left_name = (TextView) view.findViewById(R.id.main_inerbty_top_left_name);
		main_inerbty_top_left_name.setText(inerbtyProduct.getTitle());
		Log.i("MainPage+inerbty", inerbtyProduct.getTitle());
		TextView main_inerbty_top_left_price = (TextView) view.findViewById(R.id.main_inerbty_top_left_price);
		main_inerbty_top_left_price.setText(inerbtyProduct.getPrice()+"元");
		ImageView main_inerbty_top_left_image = (ImageView) view.findViewById(R.id.main_inerbty_top_left_image);
		String imageUrl = inerbtyProduct.getImgUrl();
		//添加图片
//		imageLoader.displayImage(imageUrl, main_inerbty_top_left_image, options,
//				animateFirstListener);
		
		//inerbty 右上
		inerbtyProduct = inerbtyArray.get(1);
		TextView main_inerbty_top_right_name = (TextView) view.findViewById(R.id.main_inerbty_top_right_name);
		main_inerbty_top_right_name.setText(inerbtyProduct.getTitle());
		TextView main_inerbty_top_right_price = (TextView) view.findViewById(R.id.main_inerbty_top_right_price);
		main_inerbty_top_right_price.setText(inerbtyProduct.getPrice()+"元");
		ImageView main_inerbty_top_right_image = (ImageView) view.findViewById(R.id.main_inerbty_top_right_image);
		imageUrl = inerbtyProduct.getImgUrl();
		//添加图片
//		imageLoader.displayImage(imageUrl, main_inerbty_top_right_image, options,
//				animateFirstListener);
		
		//inerbty 左中
		inerbtyProduct = inerbtyArray.get(2);
		TextView main_inerbty_mid_left_name = (TextView) view.findViewById(R.id.main_inerbty_mid_left_name);
		main_inerbty_mid_left_name.setText(inerbtyProduct.getTitle());
		TextView main_inerbty_mid_left_price = (TextView) view.findViewById(R.id.main_inerbty_mid_left_price);
		main_inerbty_mid_left_price.setText(inerbtyProduct.getPrice()+"元");
		ImageView main_inerbty_mid_left_image = (ImageView) view.findViewById(R.id.main_inerbty_mid_left_image);
		imageUrl = inerbtyProduct.getImgUrl();
		//添加图片
//		imageLoader.displayImage(imageUrl, main_inerbty_mid_left_image, options,
//				animateFirstListener);
		
		//inerbty 右中
		inerbtyProduct = inerbtyArray.get(3);
		TextView main_inerbty_mid_right_name = (TextView) view.findViewById(R.id.main_inerbty_mid_right_name);
		main_inerbty_mid_right_name.setText(inerbtyProduct.getTitle());
		Log.i("MainPage+inerbty", inerbtyProduct.getTitle());
		TextView main_inerbty_mid_right_price = (TextView) view.findViewById(R.id.main_inerbty_mid_right_price);
		main_inerbty_mid_right_price.setText(inerbtyProduct.getPrice()+"元");
		ImageView main_inerbty_mid_right_image = (ImageView) view.findViewById(R.id.main_inerbty_mid_right_image);
		imageUrl = inerbtyProduct.getImgUrl();
		//添加图片
//		imageLoader.displayImage(imageUrl, main_inerbty_mid_right_image, options,
//				animateFirstListener);
		
		//inerbty 左下
		inerbtyProduct = inerbtyArray.get(4);
		TextView main_inerbty_down_left_name = (TextView) view.findViewById(R.id.main_inerbty_down_left_name);
		main_inerbty_down_left_name.setText(inerbtyProduct.getTitle());
		TextView main_inerbty_down_left_price = (TextView) view.findViewById(R.id.main_inerbty_down_left_price);
		main_inerbty_down_left_price.setText(inerbtyProduct.getPrice()+"元");
		ImageView main_inerbty_down_left_image = (ImageView) view.findViewById(R.id.main_inerbty_down_left_image);
		imageUrl = inerbtyProduct.getImgUrl();
		//添加图片
//		imageLoader.displayImage(imageUrl, main_inerbty_down_left_image, options,
//				animateFirstListener);
		
		//inerbty 右下
		inerbtyProduct = inerbtyArray.get(5);
		TextView main_inerbty_down_right_name = (TextView) view.findViewById(R.id.main_inerbty_down_right_name);
		main_inerbty_down_right_name.setText(inerbtyProduct.getTitle());
		TextView main_inerbty_down_right_price = (TextView) view.findViewById(R.id.main_inerbty_down_right_price);
		main_inerbty_down_right_price.setText(inerbtyProduct.getPrice()+"元");
		ImageView main_inerbty_down_right_image = (ImageView) view.findViewById(R.id.main_inerbty_down_right_image);
		imageUrl = inerbtyProduct.getImgUrl();
		//添加图片
//		imageLoader.displayImage(imageUrl, main_inerbty_down_right_image, options,
//				animateFirstListener);
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
	
	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private void initDisplayImageOption(){
		options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.hot_sell_right_top_image)
			.showImageOnFail(R.drawable.hot_sell_right_top_image)
			.showImageForEmptyUri(R.drawable.hot_sell_right_top_image)
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.build();
	}
	
}
