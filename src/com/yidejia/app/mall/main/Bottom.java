package com.yidejia.app.mall.main;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.CartsDataManage;

public class Bottom {
	private RelativeLayout downHomeLayout;
	private RelativeLayout downGuangLayout;
	private RelativeLayout downSearchLayout;
	private RelativeLayout downShoppingLayout;
	private RelativeLayout downMyLayout;
	private ImageView down_home_imageView;// 首页按钮图片
	private ImageView down_guang_imageView;// 逛按钮图片
	private ImageView down_search_imageView;// 搜索按钮图片
	private ImageView down_shopping_imageView; // 购物车按钮图片
	private ImageView down_my_imageView; // 我的商城按钮图片
	private CartsDataManage cartsDataManage;
	private TextView down_home_textview;
	private TextView down_guang_textview;
	private TextView down_search_textview;
	private TextView down_shopping_textview;
	private TextView down_my_textview;
	private Resources res;
	private int number;
	private Button cartImage;// 购物车上的按钮
	
	private LayoutInflater inflater;
	private RelativeLayout view;
	private Activity activity;
	
	
	public  Bottom(){
//		this.activity = activity;
//		this.view = view;
//		this.number = number;
//		Log.e("info", activity.getLocalClassName());
	
	}
	public void getBottm(Activity activity,RelativeLayout view,int number){
		// 改变底部首页背景，有按下去的效果的背景
				downHomeLayout = (RelativeLayout) view.findViewById(R.id.down_home_layout);
				down_home_imageView = (ImageView) view.findViewById(R.id.down_home_icon);

				res = activity.getResources();
//				cartsDataManage = new CartsDataManage();
//				number = cartsDataManage.getCartAmount();
				cartImage = (Button) view.findViewById(R.id.down_shopping_cart);
				if (number == 0) {
					cartImage.setVisibility(View.GONE);
				} else {
					cartImage.setText(number + "");
				}
				downGuangLayout = (RelativeLayout) view.findViewById(R.id.down_guang_layout);
				downSearchLayout = (RelativeLayout) view.findViewById(R.id.down_search_layout);
				downShoppingLayout = (RelativeLayout) view.findViewById(R.id.down_shopping_layout);
				downMyLayout = (RelativeLayout) view.findViewById(R.id.down_my_layout);

//				downGuangLayout.setOnClickListener(this);
//				downSearchLayout.setOnClickListener(this);
//				downShoppingLayout.setOnClickListener(this);
//				downMyLayout.setOnClickListener(this);

				downGuangLayout.setVisibility(ViewGroup.GONE);
				setonClickListener(activity);
	}
	private void setonClickListener(Activity mActivity){
				if(mActivity.getLocalClassName().endsWith("main.HomeMallActivity")){
					
				}
	}
}
