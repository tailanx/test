package com.yidejia.app.mall.search;

import java.util.ArrayList;

import android.content.Context;
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
import com.yidejia.app.mall.MyApplication;
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
//	private ImageLoadingListener animateFirstListener;
	private DisplayImageOptions options;

	public SelledResultListAdapter(Context mContext, ArrayList<SearchItem> searchItems){
		this.mContext = mContext;
		this.searchItems = searchItems;
		initDisplayImageOption();
//		animateFirstListener = ((MyApplication) mContext.getApplicationContext()).getImageLoadingListener();
	}
	
	@Override
	public int getCount() {
		return searchItems.size();
	}
	
	@Override
	public Object getItem(int position) {
		return searchItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
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
		search_result_small_price.setText( mContext.getResources().getString(R.string.unit)+item.getPrice());
		search_result_small_em_num.setText(item.getCommentAmount());
		search_result_small_sell_num.setText(item.getSelledAmount());
		ImageLoader.getInstance().displayImage(item.getImgUrl(), search_result_small_image, options,
				MyApplication.getInstance().getImageLoadingListener());
		return convertView;
	}
	
	private void initDisplayImageOption(){
		options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.image_bg)
			.showImageOnFail(R.drawable.image_bg)
			.showImageForEmptyUri(R.drawable.image_bg)
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.build();
	}
}
