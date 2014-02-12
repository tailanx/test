package com.yidejia.app.mall;

import android.os.Bundle;

import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.R;

public class AboutActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		setActionbarConfig();
		setTitle(getResources().getString(R.string.edit_about));
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "关于页面");
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "关于页面");
	}	
	
}
