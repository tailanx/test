package com.yidejia.app.mall;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yidejia.app.mall.datamanage.CartsDataManage;

/**
 * 
 * 首页的父类，处理底部栏，退出程序等
 * @version 2 2014/2/6
 * @author LongBin
 *
 */
public class HomeBaseActivity extends BaseActivity {

	private RelativeLayout downSearchLayout;
	private RelativeLayout downCartLayout;
	private RelativeLayout downMyLayout;
	private RelativeLayout downGuangLayout;
	private RelativeLayout downHomeLayout;
	
	private ImageView iv_guang;// 逛按钮图片
	private ImageView iv_search;// 搜索按钮图片
	private ImageView iv_cart; // 购物车按钮图片
	private ImageView iv_mymall; // 我的商城按钮图片
	private ImageView iv_home;// 首页按钮图片
	
	private TextView tv_home;
	private TextView tv_guang;
	private TextView tv_search;
	private TextView tv_cart;
	private TextView tv_mymall;
	
	private int currentActivityId = 0;	//设置当前activity的id，0-5分别表示首页，逛，搜索，购物车，商城，登录
	private int intentId = 0;	//目的界面的id，0-5分别表示首页，逛，搜索，购物车，商城，登录

	@Override
	protected void onStart() {
		super.onStart();
		initNavView();
		setNum();
	}
	
	/**获取当前activity的id，0-5分别表示首页，逛，搜索，购物车，商城，登录**/
	public int getCurrentActivityId() {
		return currentActivityId;
	}
	/**设置当前activity的id，0-5分别表示首页，逛，搜索，购物车，商城，登录**/
	public void setCurrentActivityId(int currentActivityId) {
		this.currentActivityId = currentActivityId;
	}


	// 双击返回键退出程序
	private long exitTime = 0;

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
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

	/** 设置底部数字 **/
	private void setNum() {
		CartsDataManage cartsDataManage = new CartsDataManage();
		int number = cartsDataManage.getCartAmount();
		TextView cartImage = (TextView) findViewById(R.id.down_shopping_cart); // 购物车上的按钮
		if (number == 0) {
			cartImage.setVisibility(View.GONE);
		} else {
			cartImage.setText(number + "");
		}
	}

	/**
	 * 初始化底部导航栏
	 */
	private void initNavView() {
		downHomeLayout = (RelativeLayout) findViewById(R.id.re_down_home_layout);
		downGuangLayout = (RelativeLayout) findViewById(R.id.re_down_guang_layout);
		downSearchLayout = (RelativeLayout) findViewById(R.id.re_down_search_layout);
		downCartLayout = (RelativeLayout) findViewById(R.id.re_down_shopping_layout);
		downMyLayout = (RelativeLayout) findViewById(R.id.re_down_my_layout);

		downHomeLayout.setOnClickListener(homeListener);
		downGuangLayout.setOnClickListener(guangListener);
		downSearchLayout.setOnClickListener(searchListener);
		downCartLayout.setOnClickListener(cartListener);
		downMyLayout.setOnClickListener(myMallListener);
		
		iv_guang = (ImageView) findViewById(R.id.iv_down_guang_icon);
		iv_search = (ImageView) findViewById(R.id.iv_down_search_icon);
		iv_cart = (ImageView) findViewById(R.id.iv_down_shopping_icon);
		iv_mymall = (ImageView) findViewById(R.id.iv_down_my_icon);
		iv_home = (ImageView) findViewById(R.id.iv_down_home_icon);
		
		tv_home = (TextView) findViewById(R.id.tv_down_home_text);
		tv_guang = (TextView) findViewById(R.id.tv_down_guang_text);
		tv_search = (TextView) findViewById(R.id.tv_down_search_text);
		tv_cart = (TextView) findViewById(R.id.tv_down_shopping_text);
		tv_mymall = (TextView) findViewById(R.id.tv_down_my_text);
		
		setNavBackground();
	}
	
	private View.OnClickListener homeListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			intentId = 0;
			intentToActivity(intentId);
		}
	};
	
	private View.OnClickListener guangListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			intentId = 1;
			intentToActivity(intentId);
		}
	};
	
	private View.OnClickListener searchListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			intentId = 2;
			intentToActivity(intentId);
		}
	};
	
	private View.OnClickListener cartListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			intentId = 3;
			intentToActivity(intentId);
		}
	};
	
	private View.OnClickListener myMallListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (MyApplication.getInstance().getIsLogin()) {
				intentId = 4;
				intentToActivity(intentId);
				return;
			}
			intentId = 5;
			intentToActivity(intentId);
		}
	};
	
	/**
	 * 底部跳转到目的界面
	 * @param intentId 目的界面的id ，0-5分别表示首页，逛，搜索，购物车，商城，登录
	 */
	private void intentToActivity(int intentId){
		Intent intent = null;
		switch (intentId) {
		case 0:
			if(0 == currentActivityId) return;
			intent = new Intent(this, HomeMallActivity.class);
			break;
		case 1:
			if(1 == currentActivityId) return;
			intent = new Intent(this, HomeGuangActivity.class);
			break;
		case 2:
			if(2 == currentActivityId) return;
			intent = new Intent(this, HomeSearchActivity.class);
			break;
		case 3:
			if(3 == currentActivityId) return;
			intent = new Intent(this, HomeCartActivity.class);
			break;
		case 4:
			if(4 == currentActivityId) return;
			intent = new Intent(this, HomeMyMallActivity.class);
			break;
		case 5:
			if(5 == currentActivityId) return;
			intent = new Intent(this, HomeLoginActivity.class);
			break;
		default:
			break;
		}
		if(null == intent) return;
		startActivity(intent);
		overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
	}
	
	/**设置底部背景**/
	private void setNavBackground() {
		switch (currentActivityId) {
		case 0:
			tv_home.setSelected(true);
			iv_home.setSelected(true);
			break;
		case 1:
			tv_guang.setSelected(true);
			iv_guang.setSelected(true);
			break;
		case 2:
			tv_search.setSelected(true);
			iv_search.setSelected(true);
			break;
		case 3:
			tv_cart.setSelected(true);
			iv_cart.setSelected(true);
			break;
		case 4:
		case 5:
			tv_mymall.setSelected(true);
			iv_mymall.setSelected(true);
			break;
		default:
			break;
		}
	}

}
