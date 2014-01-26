package com.yidejia.app.mall.order;

import android.os.Bundle;

import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.R;

public class AlreadyOrderActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_order);
		setActionbarConfig();
		setTitle(getResources().getString(R.string.deliver_order));
		OrderViewCtrl viewCtrl = new OrderViewCtrl(this);
		viewCtrl.viewCtrl(3);
	}


	@Override
	protected void onResume() {
		super.onResume();
//		StatService.onResume(this);
		StatService.onPageStart(this, "已发货订单容器页面");
	}

	@Override
	protected void onPause() {
		super.onPause();
//		StatService.onPause(this);
		StatService.onPageEnd(this, "已发货订单容器页面");
	}
}
