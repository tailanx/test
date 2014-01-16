package com.yidejia.app.mall.order;

import android.os.Bundle;

import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.R;

public class AlreadyComActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionbarConfig();
		setTitle(R.string.complete_order);
		setContentView(R.layout.all_order);
		OrderViewCtrl viewCtrl = new OrderViewCtrl(this);
		viewCtrl.viewCtrl(4);
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
