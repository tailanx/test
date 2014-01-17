package com.yidejia.app.mall.order;

import android.os.Bundle;

import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.R;

public class AllOrderActivity extends BaseActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionbarConfig();
		setTitle(R.string.all_order);
		setContentView(R.layout.all_order);
		OrderViewCtrl viewCtrl = new OrderViewCtrl(AllOrderActivity.this);
		viewCtrl.viewCtrl(0);

	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onResume(this);
	}

}
