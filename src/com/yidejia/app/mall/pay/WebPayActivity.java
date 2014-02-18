package com.yidejia.app.mall.pay;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.HomeMyMallActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.order.AllOrderActivity;
import com.yidejia.app.mall.order.WaitPayActivity;
import com.yidejia.app.mall.util.ActivityIntentUtil;

public class WebPayActivity extends BaseActivity {

	private String title;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		Intent intent = getIntent();
		String payUrl = intent.getStringExtra("payurl");
		title = intent.getStringExtra("title");
		
		setActionbarConfig();
		setTitle(title);
		TextView tvBack = (TextView) findViewById(R.id.ab_common_back);
		tvBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ActivityIntentUtil.intentActivityAndFinish(WebPayActivity.this, AllOrderActivity.class);
			}
		});
		
//		payUrl = "http://m.yidejia.com/paysuccess.html";
		WebPayUtil util = new WebPayUtil(this);
		util.webPay(payUrl);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			ActivityIntentUtil.intentActivityAndFinish(WebPayActivity.this, AllOrderActivity.class);
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
//		StatService.onResume(this);
		StatService.onPageStart(this, title);
	}

	@Override
	protected void onPause() {
		super.onPause();
//		StatService.onPause(this);
		StatService.onPageEnd(this, title);
	}
}
