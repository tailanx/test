package com.yidejia.app.mall;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.util.BottomChange;

public class HomeGuangActivity extends SherlockFragmentActivity {
	private BottomChange bottomChange;
	private RelativeLayout bottomLayout;
	private int number;// 商品的个数
	private CartsDataManage cartsDataManage;
	private LayoutInflater inflater;
	private FrameLayout frameLayout;
	private View view;
	private Button cartImage;//购物车
	private RelativeLayout downHomeLayout;
	private RelativeLayout downSearchLayout;
	private RelativeLayout downShoppingLayout;
	private RelativeLayout downMyLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		cartsDataManage = new CartsDataManage();
		setContentView(R.layout.activity_main_fragment_layout);
		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.guang, null);
		frameLayout = (FrameLayout) findViewById(R.id.main_fragment);
		frameLayout.addView(view);

		int current = getIntent().getIntExtra("current", -1);
		int next = getIntent().getIntExtra("next", -1);
		// 设置底部
		bottomLayout = (RelativeLayout) findViewById(R.id.down_parent_layout);
		bottomChange = new BottomChange(this, bottomLayout);
		if (current != -1 || next != -1) {
			bottomChange.initNavView(current, next);
		}
		initNavView();
		setActionbar();
	}

	/**
	 * 初始化底部导航栏
	 */
	private void initNavView() {
		// 改变底部首页背景，有按下去的效果的背景
		number = cartsDataManage.getCartAmount();
		cartImage = (Button) findViewById(R.id.down_shopping_cart);
		if (number == 0) {
			cartImage.setVisibility(View.GONE);
		} else {
			cartImage.setText(number + "");
		}
		downHomeLayout = (RelativeLayout) findViewById(R.id.re_down_home_layout);
		downSearchLayout = (RelativeLayout) findViewById(R.id.re_down_search_layout);
		downShoppingLayout = (RelativeLayout) findViewById(R.id.re_down_shopping_layout);
		downMyLayout = (RelativeLayout) findViewById(R.id.re_down_my_layout);


		downHomeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeGuangActivity.this,
						HomeMallActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_in,
						R.anim.activity_out);
			}
		});
		downSearchLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeGuangActivity.this,
						HomeSearchActivity.class);
				intent.putExtra("current", 5);
				intent.putExtra("next", 1);
				startActivity(intent);
				HomeGuangActivity.this.finish();
				overridePendingTransition(R.anim.activity_in,
						R.anim.activity_out);

			}
		});
		downShoppingLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeGuangActivity.this,
						HomeCarActivity.class);
				intent.putExtra("current", 5);
				intent.putExtra("next", 2);
				startActivity(intent);
				HomeGuangActivity.this.finish();
				overridePendingTransition(R.anim.activity_in,
						R.anim.activity_out);
			}
		});
		downMyLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeGuangActivity.this,
						HomeCarActivity.class);
				intent.putExtra("current", 5);
				intent.putExtra("next", 3);
				startActivity(intent);
				HomeGuangActivity.this.finish();
				overridePendingTransition(R.anim.activity_in,
						R.anim.activity_out);
			}
		});
	}
	private void setActionbar(){
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_common);
		TextView back = (TextView) findViewById(R.id.ab_common_back);
		back.setVisibility(View.GONE);
		TextView title = (TextView) findViewById(R.id.ab_common_title);
		title.setText(getResources().getString(R.string.guang));
	}
}
