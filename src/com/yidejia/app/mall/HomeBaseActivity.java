package com.yidejia.app.mall;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yidejia.app.mall.datamanage.CartsDataManage;

public class HomeBaseActivity extends BaseActivity implements
		View.OnClickListener {

	private RelativeLayout downSearchLayout;
	private RelativeLayout downShoppingLayout;
	private RelativeLayout downMyLayout;
	private RelativeLayout downGuang;
	private int currentActivityId = 0;	//设置当前activity的id，0-5分别表示首页，逛，搜索，购物车，商城，登录

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
		Button cartImage = (Button) findViewById(R.id.down_shopping_cart); // 购物车上的按钮
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
		downGuang = (RelativeLayout) findViewById(R.id.re_down_guang_layout);
		downSearchLayout = (RelativeLayout) findViewById(R.id.re_down_search_layout);
		downShoppingLayout = (RelativeLayout) findViewById(R.id.re_down_shopping_layout);
		downMyLayout = (RelativeLayout) findViewById(R.id.re_down_my_layout);

		downShoppingLayout.setOnClickListener(this);
		downSearchLayout.setOnClickListener(this);
		downMyLayout.setOnClickListener(this);
		downGuang.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.re_down_search_layout:
			if(currentActivityId == 3) return;
			intent = new Intent();
			intent.setClass(this, HomeSearchActivity.class);
//			intent.putExtra("current", 0);
//			intent.putExtra("next", 1);
			
			break;
		case R.id.re_down_shopping_layout:
			if(currentActivityId == 1) return;
			intent = new Intent();
			intent.setClass(this, HomeCarActivity.class);
//			intent.putExtra("current", 0);
//			intent.putExtra("next", 2);
			break;
		case R.id.re_down_guang_layout:
			if(currentActivityId == 2)return;
			intent = new Intent();
			intent.setClass(this, HomeGuangActivity.class);
//			intent.putExtra("current", 0);
//			intent.putExtra("next", 5);
			break;
		case R.id.re_down_my_layout:
			if (MyApplication.getInstance().getIsLogin()) {
				if(currentActivityId == 4) return;
				intent = new Intent();
				intent.setClass(this, HomeMyMaActivity.class);
			} else {
				if(currentActivityId == 5) return;
				intent = new Intent();
				intent.setClass(this, HomeLogActivity.class);
			}
//			intent.putExtra("current", 0);
//			intent.putExtra("next", 3);
			break;
		case R.id.re_down_home_layout:
			if(currentActivityId == 0) return;
			intent = new Intent();
			intent.setClass(this, HomeMallActivity.class);
			break;
		}
		if(null == intent) return;
		this.startActivity(intent);
		overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
	}
}
