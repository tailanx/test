package com.yidejia.app.mall.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.main.HomeCarActivity;
import com.yidejia.app.mall.main.HomeLogActivity;
import com.yidejia.app.mall.main.HomeMyMaActivity;
import com.yidejia.app.mall.main.HomeSearchActivity;
import com.yidejia.app.mall.net.user.GetMessage;

public class BottomChange {
	private SherlockFragmentActivity activity;
	private int currentIndex;// 当前的id
	private RelativeLayout downHomeLayout;
	private RelativeLayout downGuangLayout;
	private RelativeLayout downSearchLayout;
	private RelativeLayout downShoppingLayout;
	private RelativeLayout downMyLayout;

	private ImageView down_guang_imageView;// 逛按钮图片
	private ImageView down_search_imageView;// 搜索按钮图片
	private ImageView down_shopping_imageView; // 购物车按钮图片
	private ImageView down_my_imageView; // 我的商城按钮图片
	private ImageView down_home_imageView;// 首页按钮图片

	private TextView down_home_textview;
	private TextView down_guang_textview;
	private TextView down_search_textview;
	private TextView down_shopping_textview;
	private TextView down_my_textview;
	private MyApplication myApplication;
	private Resources res;
	public BottomChange(SherlockFragmentActivity context,RelativeLayout view) {
		this.activity = context;
		res = activity.getResources();
		myApplication = (MyApplication) context.getApplication();
		downHomeLayout = (RelativeLayout) view
				.findViewById(R.id.down_home_layout);
		downGuangLayout = (RelativeLayout) view
				.findViewById(R.id.down_guang_layout);
		downSearchLayout = (RelativeLayout) view
				.findViewById(R.id.down_search_layout);
		downShoppingLayout = (RelativeLayout) view
				.findViewById(R.id.down_shopping_layout);
		downMyLayout = (RelativeLayout) view
				.findViewById(R.id.down_my_layout);
		down_guang_imageView = (ImageView) view
				.findViewById(R.id.down_guang_icon);
		down_search_imageView = (ImageView) view
				.findViewById(R.id.down_search_icon);
		down_shopping_imageView = (ImageView) view
				.findViewById(R.id.down_shopping_icon);
		down_my_imageView = (ImageView) view
				.findViewById(R.id.down_my_icon);
		down_home_textview = (TextView) view
				.findViewById(R.id.down_home_text);
		down_guang_textview = (TextView) view
				.findViewById(R.id.down_guang_text);
		down_search_textview = (TextView) view
				.findViewById(R.id.down_search_text);
		down_shopping_textview = (TextView) view
				.findViewById(R.id.down_shopping_text);
		down_my_textview = (TextView) view.findViewById(R.id.down_my_text);

	}

	public void initNavView(int index) {
		this.currentIndex = index;
		Intent intent = new Intent();
		switch (index) {
		case 0:
			intent.setClass(activity, HomeMyMaActivity.class);
			setNavBackground();
			down_my_imageView.setBackgroundResource(R.drawable.down_my_hover);
			downHomeLayout.setBackgroundResource(R.drawable.down_hover1);
			down_my_textview.setTextColor(res.getColor(R.color.white));
		case 1:
			intent.setClass(activity, HomeSearchActivity.class);
			setNavBackground();
			down_search_textview.setTextColor(res.getColor(R.color.white));
			downSearchLayout.setBackgroundResource(R.drawable.down_hover1);
			down_search_imageView
					.setImageResource(R.drawable.down_search_hover);
			break;
		case 2:
			intent.setClass(activity, HomeCarActivity.class);
			setNavBackground();
			down_shopping_textview.setTextColor(res.getColor(R.color.white));
			downShoppingLayout.setBackgroundResource(R.drawable.down_hover1);
			down_shopping_imageView
					.setImageResource(R.drawable.down_shopping_hover);
			break;
		case 3:
			setNavBackground();
			downMyLayout.setBackgroundResource(R.drawable.down_hover1);
			down_my_imageView.setImageResource(R.drawable.down_my_hover);
			down_my_textview.setTextColor(res.getColor(R.color.white));
			if (myApplication.getIsLogin())
				intent.setClass(activity, HomeMyMaActivity.class);
			else
				intent.setClass(activity, HomeLogActivity.class);
			break;
		}
		activity.startActivity(intent);
	}

	private void setNavBackground() {
		if (currentIndex == 0) {
			down_home_textview.setTextColor(res.getColor(R.color.white_white));
			downHomeLayout.setBackgroundResource(R.drawable.downbg);
			down_home_imageView.setImageResource(R.drawable.home_normal);

		} else if (currentIndex == 5) {
			down_guang_textview.setTextColor(res.getColor(R.color.white_white));
			downGuangLayout.setBackgroundResource(R.drawable.downbg);
			down_guang_imageView.setImageResource(R.drawable.down_guang_normal);

		} else if (currentIndex == 1) {
			down_search_textview
					.setTextColor(res.getColor(R.color.white_white));
			downSearchLayout.setBackgroundResource(R.drawable.downbg);
			down_search_imageView
					.setImageResource(R.drawable.down_search_normal);

		} else if (currentIndex == 2) {
			down_shopping_textview.setTextColor(res
					.getColor(R.color.white_white));
			downShoppingLayout.setBackgroundResource(R.drawable.downbg);
			down_shopping_imageView
					.setImageResource(R.drawable.down_shopping_normal);

		} else if (currentIndex == 3) {
			down_my_textview.setTextColor(res.getColor(R.color.white_white));
			downMyLayout.setBackgroundResource(R.drawable.downbg);
			down_my_imageView.setImageResource(R.drawable.down_my_normal);

		}
	}
}
