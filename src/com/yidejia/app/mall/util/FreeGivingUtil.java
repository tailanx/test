package com.yidejia.app.mall.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.PreferentialDataManage;
import com.yidejia.app.mall.model.Specials;

public class FreeGivingUtil {
	private Context context;
	private LinearLayout layout;
	private LayoutInflater inflater;
	private PreferentialDataManage dataManage;
	private ArrayList<Specials> mlist;
	private HashMap<Object, Integer> map;
	public FreeGivingUtil(Context context,LinearLayout layout){
		this.context = context;
		this.layout = layout;
		inflater =LayoutInflater.from(context);
		dataManage = new PreferentialDataManage(context);
	}
	
	public void loadView(){
		map = new HashMap<Object, Integer>();
		initDisplayImageOption();
		mlist = dataManage.getFreeGoods();
		View view = inflater.inflate(R.layout.free_giving_item, null);
		
		CheckBox checkBox = (CheckBox) view.findViewById(R.id.free_giving_item_checkbox);
		ImageView imageView = (ImageView) view.findViewById(R.id.free_giving_item__imageview1);
		TextView contentView = (TextView) view.findViewById(R.id.free_giving_item_text);
		for(int i =1;i<mlist.size();i++){
			map.put(checkBox, i);
			Specials specials = mlist.get(i);
			imageLoader.displayImage(specials.getImgUrl(), imageView, options, animateFirstListener);
			contentView.setText(specials.getBrief());
			
			
			layout.addView(view);
		}
		Specials specials = mlist.get(0);
		checkBox.setChecked(true);
		imageLoader.displayImage(specials.getImgUrl(), imageView, options, animateFirstListener);
		contentView.setText(specials.getBrief());
		layout.addView(view);
		
	}
	
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();// ¼ÓÔØÍ¼Æ¬
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
	private void initDisplayImageOption() {
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.hot_sell_right_top_image)
				.showImageOnFail(R.drawable.hot_sell_right_top_image)
				.showImageForEmptyUri(R.drawable.hot_sell_right_top_image)
				.cacheInMemory(true).cacheOnDisc(true).build();
	}
	
	
	
}
