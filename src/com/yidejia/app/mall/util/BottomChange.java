package com.yidejia.app.mall.util;

import android.content.Intent;
import android.content.res.Resources;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.yidejia.app.mall.HomeCarActivity;
import com.yidejia.app.mall.HomeLogActivity;
import com.yidejia.app.mall.HomeMyMaActivity;
import com.yidejia.app.mall.HomeSearchActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;

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

	public BottomChange(SherlockFragmentActivity context, RelativeLayout view) {
		this.activity = context;
		res = activity.getResources();
		myApplication = (MyApplication) context.getApplication();
		downHomeLayout = (RelativeLayout) view
				.findViewById(R.id.re_down_home_layout);
		downGuangLayout = (RelativeLayout) view
				.findViewById(R.id.re_down_guang_layout);
		downSearchLayout = (RelativeLayout) view
				.findViewById(R.id.re_down_search_layout);
		downShoppingLayout = (RelativeLayout) view
				.findViewById(R.id.re_down_shopping_layout);
		downMyLayout = (RelativeLayout) view.findViewById(R.id.re_down_my_layout);
		down_guang_imageView = (ImageView) view
				.findViewById(R.id.iv_down_guang_icon);
		down_search_imageView = (ImageView) view
				.findViewById(R.id.iv_down_search_icon);
		down_shopping_imageView = (ImageView) view
				.findViewById(R.id.iv_down_shopping_icon);
		down_my_imageView = (ImageView) view.findViewById(R.id.iv_down_my_icon);
		down_home_imageView = (ImageView) view
				.findViewById(R.id.iv_down_home_icon);
		down_home_textview = (TextView) view.findViewById(R.id.tv_down_home_text);
		down_guang_textview = (TextView) view
				.findViewById(R.id.tv_down_guang_text);
		down_search_textview = (TextView) view
				.findViewById(R.id.tv_down_search_text);
		down_shopping_textview = (TextView) view
				.findViewById(R.id.tv_down_shopping_text);
		down_my_textview = (TextView) view.findViewById(R.id.tv_down_my_text);

	}

	public void initNavView(int current, int next) {
		this.currentIndex = current;
		switch (current) {
		case 0:
			setNavBackground();
			break;
		case 1:
			setNavBackground();
			break;
		case 2:
			setNavBackground();
			break;
		case 3:
			setNavBackground();
			break;
		case 5:
			setNavBackground();
			break;

		}
		switch (next) {
		case 0:
			down_my_imageView.setBackgroundResource(R.drawable.home_hover);
			downHomeLayout.setBackgroundResource(R.drawable.down_hover1);
			down_my_textview.setTextColor(res.getColor(R.color.white));
			break;
		case 1:

			down_search_textview.setTextColor(res.getColor(R.color.white));
			downSearchLayout.setBackgroundResource(R.drawable.down_hover1);
			down_search_imageView
					.setImageResource(R.drawable.down_search_hover);
			break;
		case 2:
			// intent.setClass(activity, HomeCarActivity.class);
			down_shopping_textview.setTextColor(res.getColor(R.color.white));
			downShoppingLayout.setBackgroundResource(R.drawable.down_hover1);
			down_shopping_imageView
					.setImageResource(R.drawable.down_shopping_hover);
			break;
		case 3:
			downMyLayout.setBackgroundResource(R.drawable.down_hover1);
			down_my_imageView.setImageResource(R.drawable.down_my_hover);
			down_my_textview.setTextColor(res.getColor(R.color.white));

			break;
		}

	}

	private void setNavBackground() {
		down_home_textview.setTextColor(res.getColor(R.color.white_white));
		downHomeLayout.setBackgroundResource(R.drawable.downbg);
		down_home_imageView.setImageResource(R.drawable.home_normal);
		if (currentIndex == 5) {
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
