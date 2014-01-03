package com.yidejia.app.mall.main;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.broadcast.MallAction;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.util.BottomChange;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.MallInnerReceiver;

public class HomeMallActivity extends SherlockFragmentActivity implements
		OnClickListener {

	private FrameLayout frameLayout;// 填充activity的界面用的
	public static Activity MAINACTIVITY;
	private MallInnerReceiver receiver;
	private RelativeLayout downSearchLayout;
	private RelativeLayout downShoppingLayout;
	private RelativeLayout downMyLayout;
	private CartsDataManage cartsDataManage;
	private int number;
	private Button cartImage;// 购物车上的按钮
	private BottomChange bottomChange;
	private RelativeLayout bottomLayout;
	private RelativeLayout downHomeLayout;
	private RelativeLayout downGuangLayout;
	private ImageView down_home_imageView;// 首页按钮图片
	private ImageView down_guang_imageView;// 逛按钮图片
	private ImageView down_search_imageView;// 搜索按钮图片
	private ImageView down_shopping_imageView; // 购物车按钮图片
	private ImageView down_my_imageView; // 我的商城按钮图片
	private TextView down_home_textview;
	private TextView down_guang_textview;
	private TextView down_search_textview;
	private TextView down_shopping_textview;
	private TextView down_my_textview;
	private Resources res;
	private MyApplication myApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MAINACTIVITY = this;
		cartsDataManage = new CartsDataManage();
		myApplication = (MyApplication) getApplication();	
		setContentView(R.layout.activity_main_fragment_layout);
		// 实例化组件
		frameLayout = (FrameLayout) findViewById(R.id.main_fragment);
		// 设置头部
		MallAction mallAction = new MallAction(HomeMallActivity.this,
				frameLayout);
		
		mallAction.setActionBarConfig();
		// 界面加载
		mallAction.onActivityCreated();
		// 设置底部
		// 注册一个广播
		receiver = new MallInnerReceiver(cartImage);
		IntentFilter filter = new IntentFilter();
		filter.addAction(Consts.UPDATE_CHANGE);
		filter.addAction(Consts.BROAD_UPDATE_CHANGE);
		filter.addAction(Consts.DELETE_CART);
		registerReceiver(receiver, filter);
//		//底部加载
//		bottomLayout = (RelativeLayout) findViewById(R.id.down_parent_layout);
//		bottomChange = new BottomChange(HomeMallActivity.this,bottomLayout);
		initNavView();
	}

//	/**
//	 * 初始化底部导航栏
//	 */
//	private void initNavView() {
//		cartsDataManage = new CartsDataManage();
//		number = cartsDataManage.getCartAmount();
//		cartImage = (Button) findViewById(R.id.down_shopping_cart);
//		if (number == 0) {
//			cartImage.setVisibility(View.GONE);
//		} else {
//			cartImage.setText(number + "");
//		}
////		downGuangLayout = (RelativeLayout) findViewById(R.id.down_guang_layout);
//		downSearchLayout = (RelativeLayout) findViewById(R.id.down_search_layout);
//		downShoppingLayout = (RelativeLayout) findViewById(R.id.down_shopping_layout);
//		downMyLayout = (RelativeLayout) findViewById(R.id.down_my_layout);
//
//		downShoppingLayout.setOnClickListener(this);
//		downSearchLayout.setOnClickListener(this);
//		downMyLayout.setOnClickListener(this);
//	}

	
	/**
	 * 初始化底部导航栏
	 */
	private void initNavView() {
		// 改变底部首页背景，有按下去的效果的背景
		downHomeLayout = (RelativeLayout) findViewById(R.id.down_home_layout);
		down_home_imageView = (ImageView) findViewById(R.id.down_home_icon);

		res = getResources();
		cartsDataManage = new CartsDataManage();
		number = cartsDataManage.getCartAmount();
		cartImage = (Button) findViewById(R.id.down_shopping_cart);
		if (number == 0) {
			cartImage.setVisibility(View.GONE);
		} else {
			cartImage.setText(number + "");
		}
		downGuangLayout = (RelativeLayout) findViewById(R.id.down_guang_layout);
		downSearchLayout = (RelativeLayout) findViewById(R.id.down_search_layout);
		downShoppingLayout = (RelativeLayout) findViewById(R.id.down_shopping_layout);
		downMyLayout = (RelativeLayout) findViewById(R.id.down_my_layout);

		downGuangLayout.setOnClickListener(this);
		downSearchLayout.setOnClickListener(this);
		downShoppingLayout.setOnClickListener(this);
		downMyLayout.setOnClickListener(this);

		downGuangLayout.setVisibility(ViewGroup.GONE);
	}
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//	
//		switch (v.getId()) {
//		case R.id.down_search_layout:
//			bottomChange.initNavView(1);
//			break;
//		case R.id.down_shopping_layout:
//			bottomChange.initNavView(2);
//			break;
//		case R.id.down_my_layout:
//			bottomChange.initNavView(3);
//			break;
//		}
//		overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
//	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	// 双击返回键退出程序
	private long exitTime = 0;

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.exit),
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.down_search_layout:
			intent.setClass(HomeMallActivity.this, HomeSearchActivity.class);
			break;
		case R.id.down_shopping_layout:
			intent.setClass(HomeMallActivity.this, HomeCarActivity.class);
			break;
		case R.id.down_my_layout:
			if (myApplication.getIsLogin())
				intent.setClass(HomeMallActivity.this, HomeMyMaActivity.class);
			else
				intent.setClass(HomeMallActivity.this, HomeLogActivity.class);
			break;
		}
		HomeMallActivity.this.startActivity(intent);
		overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		StatService.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		StatService.onResume(this);
	}

}