package com.yidejia.app.mall;

import android.os.Bundle;

import com.yidejia.app.mall.R;

public class AboutActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		setActionbarConfig();
		setTitle(getResources().getString(R.string.edit_about));
	}
	
}
