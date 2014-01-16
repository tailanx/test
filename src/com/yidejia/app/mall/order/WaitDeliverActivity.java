package com.yidejia.app.mall.order;

import android.os.Bundle;

import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.R;

public class WaitDeliverActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionbarConfig();
		setTitle(getResources().getString(R.string.wait_deliver));
		setContentView(R.layout.all_order);
		OrderViewCtrl viewCtrl = new OrderViewCtrl(this);
		viewCtrl.viewCtrl(2);
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
