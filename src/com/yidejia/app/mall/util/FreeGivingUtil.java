package com.yidejia.app.mall.util;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.yidejia.app.mall.MyApplication;
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
		inflater = LayoutInflater.from(context);
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
		
		ImageLoader.getInstance().init(MyApplication.getInstance().initConfig());
		
		for (int i = 1; i < mlist.size(); i++) {
			map.put(checkBox, i);
			Specials specials = mlist.get(i);
			imageLoader.displayImage(specials.getImgUrl(), imageView, options,
					animateFirstListener);
			contentView.setText(specials.getBrief());

			layout.addView(view);
		}
		Specials specials = mlist.get(0);
		checkBox.setChecked(true);
		
		imageLoader.displayImage(specials.getImgUrl(), imageView, options, animateFirstListener);
		contentView.setText(specials.getBrief());
		layout.addView(view);
		
	}
	
	private ImageLoadingListener animateFirstListener;
	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();// ����ͼƬ

	
	private void initDisplayImageOption() {
		options = MyApplication.getInstance().initGoodsImageOption();
		animateFirstListener = MyApplication.getInstance().getImageLoadingListener();
	}
	
	
	
}
