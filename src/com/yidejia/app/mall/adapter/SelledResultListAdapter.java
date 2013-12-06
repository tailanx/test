package com.yidejia.app.mall.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.SearchItem;

public class SelledResultListAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<SearchItem> searchItems;
	private ImageView search_result_small_image;
	private TextView search_result_small_title;
	private TextView search_result_small_price;
	private TextView search_result_small_sell_num;
	private TextView search_result_small_em_num;
	private ImageLoader imageLoader;
	private ImageLoadingListener animateFirstListener;
	private DisplayImageOptions options;
	public SelledResultListAdapter(Context mContext, ArrayList<SearchItem> searchItems,ImageLoader imageloader,ImageLoadingListener listener,DisplayImageOptions options){
		this.mContext = mContext;
		this.searchItems = searchItems;
		this.imageLoader = imageloader;
		this.animateFirstListener = listener;
		this.options = options;
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
//		return listContent.length;
		return searchItems.size();
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
//		return listContent[position];
		return searchItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
//		return searchItems.get(position).getUId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
//		convertView = (View) mLayoutInflater.inflate(R.layout.search_list_item, null);
		convertView = (View) LayoutInflater.from(mContext).inflate(R.layout.item_search_result_list, null);
//		TextView search_list_text = (TextView) convertView.findViewById(R.id.search_list_item_text);
//		search_list_text.setText(listContent[position]);
		search_result_small_em_num = (TextView) convertView.findViewById(R.id.search_result_small_em_num);
		search_result_small_sell_num = (TextView) convertView.findViewById(R.id.search_result_small_sell_num);
		search_result_small_price = (TextView) convertView.findViewById(R.id.search_result_small_price);
		search_result_small_title = (TextView) convertView.findViewById(R.id.search_result_small_title);
		search_result_small_image = (ImageView) convertView.findViewById(R.id.search_result_small_image);
		
		SearchItem item = searchItems.get(position);
		search_result_small_title.setText(item.getName());
		search_result_small_price.setText(item.getPrice()+mContext.getResources().getString(R.string.unit));
		search_result_small_em_num.setText(item.getCommentAmount());
		search_result_small_sell_num.setText(item.getSelledAmount());
		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
		imageLoader.displayImage(item.getImgUrl(), search_result_small_image, options,
				animateFirstListener);
		return convertView;
	}
	
	
}
