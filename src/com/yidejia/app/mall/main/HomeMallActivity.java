package com.yidejia.app.mall.main;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
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
	private MyApplication myApplication;
//	private boolean isFrist = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myApplication = (MyApplication) getApplication();
		MAINACTIVITY = this;
		cartsDataManage = new CartsDataManage();
		setContentView(R.layout.activity_main_fragment_layout);
		// 实例化组件
		frameLayout = (FrameLayout) findViewById(R.id.main_fragment);
		// 设置头部
		MallAction mallAction = new MallAction(HomeMallActivity.this,
				frameLayout);

		mallAction.setActionBarConfig();
		// 界面加载
		mallAction.onActivityCreated();
		// 注册一个广播
		receiver = new MallInnerReceiver(cartImage);
		IntentFilter filter = new IntentFilter();
		filter.addAction(Consts.UPDATE_CHANGE);
		filter.addAction(Consts.BROAD_UPDATE_CHANGE);
		filter.addAction(Consts.DELETE_CART);
		registerReceiver(receiver, filter);
		
		// 设置底部
		bottomLayout = (RelativeLayout) findViewById(R.id.down_parent_layout);
		bottomChange = new BottomChange(HomeMallActivity.this, bottomLayout);
//		if(!isFrist){
//			int currnet = getIntent().getIntExtra("current", -1);
//			int next = getIntent().getIntExtra("next", -1);
//			if(currnet !=-1||next !=-1){
//				bottomChange.initNavView(currnet,next);
//			}
//		}
		
		initNavView();
	}

	/**
	 * 初始化底部导航栏
	 */
	private void initNavView() {
		cartsDataManage = new CartsDataManage();
		number = cartsDataManage.getCartAmount();
		cartImage = (Button) findViewById(R.id.down_shopping_cart);
		if (number == 0) {
			cartImage.setVisibility(View.GONE);
		} else {
			cartImage.setText(number + "");
		}
		// downGuangLayout = (RelativeLayout)
		// findViewById(R.id.down_guang_layout);
		downSearchLayout = (RelativeLayout) findViewById(R.id.down_search_layout);
		downShoppingLayout = (RelativeLayout) findViewById(R.id.down_shopping_layout);
		downMyLayout = (RelativeLayout) findViewById(R.id.down_my_layout);

		downShoppingLayout.setOnClickListener(this);
		downSearchLayout.setOnClickListener(this);
		downMyLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.down_search_layout:
			intent.setClass(HomeMallActivity.this, HomeSearchActivity.class);
			intent.putExtra("current", 0);
			intent.putExtra("next", 1);
//			bottomChange.initNavView(0,1);
			break;
		case R.id.down_shopping_layout:
			intent.setClass(HomeMallActivity.this, HomeCarActivity.class);
			intent.putExtra("next", 2);
//			bottomChange.initNavView(0,2);
			break;
		case R.id.down_my_layout:
			if (myApplication.getIsLogin()){
				intent.setClass(HomeMallActivity.this, HomeMyMaActivity.class);
			}
			else
			{
				intent.setClass(HomeMallActivity.this, HomeLogActivity.class);
			}
			intent.putExtra("current", 0);
			intent.putExtra("next", 3);
//			bottomChange.initNavView(0,3);
			break;
		}
		HomeMallActivity.this.startActivity(intent);
		HomeMallActivity.this.finish();
		overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
	}

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