package com.yidejia.app.mall;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.qiandao.QiandaoActivity;
import com.yidejia.app.mall.shark.TestSharkActivity;
import com.yidejia.app.mall.util.BottomChange;

public class HomeGuangActivity extends HomeBaseActivity implements OnClickListener {
//	private BottomChange bottomChange;
	private RelativeLayout bottomLayout;
//	private CartsDataManage cartsDataManage;
	private LayoutInflater inflater;
	private FrameLayout frameLayout;
	private View view;
	private RelativeLayout qiandao;
	private RelativeLayout yaoyiyao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		cartsDataManage = new CartsDataManage();
		setContentView(R.layout.activity_main_fragment_layout);
		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.guang, null);
		frameLayout = (FrameLayout) findViewById(R.id.main_fragment);
		frameLayout.addView(view);

//		int current = getIntent().getIntExtra("current", -1);
//		int next = getIntent().getIntExtra("next", -1);
		// 设置底部
//		bottomLayout = (RelativeLayout) findViewById(R.id.down_parent_layout);
//		bottomChange = new BottomChange(this, bottomLayout);
//		if (current != -1 || next != -1) {
//			bottomChange.initNavView(current, next);
//		}
		setActionbar();
		
		setCurrentActivityId(1);
	}


	private void setActionbar() {
		setActionbarConfig();
		TextView back = (TextView) findViewById(R.id.ab_common_back);
		back.setVisibility(View.GONE);
		TextView title = (TextView) findViewById(R.id.ab_common_title);
		title.setText(getResources().getString(R.string.guang));
		qiandao = (RelativeLayout) findViewById(R.id.iv_guang_qiandao);
		yaoyiyao = (RelativeLayout) findViewById(R.id.iv_guang_yaoyiyao);
		qiandao.setOnClickListener(this);
		yaoyiyao.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.iv_guang_qiandao:
			intent = new Intent();
			intent.setClass(this, QiandaoActivity.class);
			break;

		case R.id.iv_guang_yaoyiyao:
			intent = new Intent();
			intent.setClass(this, TestSharkActivity.class);
			break;
		}
		if(null == intent)return;
		startActivity(intent);
	}
}
