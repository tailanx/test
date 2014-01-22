package com.yidejia.app.mall.initview;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.goodinfo.GoodsInfoActivity;
import com.yidejia.app.mall.model.SearchItem;

public class SRViewWithImage {
	private Activity activity;
	private View view;
	
	private ImageView search_result_lg_image_left;
	private TextView search_result_lg_title_left;
	private TextView search_result_lg_sell_num_left;
	private TextView search_result_lg_price_left;
	
	private ImageView search_result_lg_image_right;
	private TextView search_result_lg_price_right;
	private TextView search_result_lg_title_right;
	private TextView search_result_lg_sell_num_right;
	
	private RelativeLayout search_result_lg_layout_left;
	private RelativeLayout search_result_lg_layout_right;
	
	public SRViewWithImage(Activity activity, View view){
		this.activity = activity;
		this.view = view;
		initDisplayImageOption();
	}
	
	/**
	 * Å¼Êý¸öÉÌÆ·
	 * @param leftItem
	 * @param rightItem
	 */
	public void initView(final SearchItem leftItem, final SearchItem rightItem){
		findEvenIds();
		
		loadImage(leftItem.getImgUrl(), search_result_lg_image_left);
		loadImage(rightItem.getImgUrl(), search_result_lg_image_right);
		
		search_result_lg_title_left.setText(leftItem.getName());
		search_result_lg_sell_num_left.setText(leftItem.getCommentAmount());
		search_result_lg_price_left.setText(leftItem.getPrice());
		
		search_result_lg_title_right.setText(rightItem.getName());
		search_result_lg_sell_num_right.setText(rightItem.getCommentAmount());
		search_result_lg_price_right.setText(rightItem.getPrice());
		
		search_result_lg_layout_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intentToGoodsInfo(leftItem.getUId());
			}
		});
		
		search_result_lg_layout_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intentToGoodsInfo(rightItem.getUId());
			}
		});
	}
	
	/**
	 * ÆæÊý¸öÉÌÆ·
	 * @param item
	 */
	public void initView(final SearchItem item){
		findOddIds();
		
		loadImage(item.getImgUrl(), search_result_lg_image_left);
		search_result_lg_title_left.setText(item.getName());
		search_result_lg_sell_num_left.setText(item.getCommentAmount());
		search_result_lg_price_left.setText(item.getPrice());
		
		
		
		search_result_lg_layout_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intentToGoodsInfo(item.getUId());
			}
		});
	}

	private void findEvenIds(){
		search_result_lg_image_left = (ImageView) view.findViewById(R.id.search_result_lg_image_left);
		search_result_lg_title_left = (TextView) view.findViewById(R.id.search_result_lg_title_left);
		search_result_lg_sell_num_left = (TextView) view.findViewById(R.id.search_result_lg_sell_num_left);
		search_result_lg_price_left = (TextView) view.findViewById(R.id.search_result_lg_price_left);
		
		search_result_lg_image_right = (ImageView) view.findViewById(R.id.search_result_lg_image_right);
		search_result_lg_title_right = (TextView) view.findViewById(R.id.search_result_lg_title_right);
		search_result_lg_sell_num_right = (TextView) view.findViewById(R.id.search_result_lg_sell_num_right);
		search_result_lg_price_right = (TextView) view.findViewById(R.id.search_result_lg_price_right);
		
		search_result_lg_layout_left = (RelativeLayout) view.findViewById(R.id.search_result_lg_layout_left);
		search_result_lg_layout_right = (RelativeLayout) view.findViewById(R.id.search_result_lg_layout_right);
		
	}
	
	private void findOddIds(){
		search_result_lg_image_left = (ImageView) view.findViewById(R.id.search_result_lg_image);
		search_result_lg_title_left = (TextView) view.findViewById(R.id.search_result_lg_title);
		search_result_lg_sell_num_left = (TextView) view.findViewById(R.id.search_result_lg_sell_num);
		search_result_lg_price_left = (TextView) view.findViewById(R.id.search_result_lg_price);
		
		search_result_lg_layout_left = (RelativeLayout) view.findViewById(R.id.search_result_lg_layout);
	}
	
	private void intentToGoodsInfo(String gooodsId){
		Intent intent = new Intent(activity, GoodsInfoActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("goodsId", gooodsId);
		intent.putExtras(bundle);
		activity.startActivity(intent);
	}
	
	private void loadImage(String imageUrl, ImageView imageView){
		ImageLoader.getInstance().init(MyApplication.getInstance().initConfig());
		imageLoader.displayImage(imageUrl, imageView, options,
				animateFirstListener);
	}
	
	
	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageLoadingListener animateFirstListener;
	private void initDisplayImageOption(){
		options = MyApplication.getInstance().initGoodsImageOption();
		animateFirstListener = MyApplication.getInstance().getImageLoadingListener();
	}
}