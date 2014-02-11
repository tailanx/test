package com.yidejia.app.mall.view;

import com.yidejia.app.mall.HomeLoginActivity;

public class LoginActivity extends HomeLoginActivity {

	@Override
	protected void onResume() {
		super.onResume();
		hideBottomView();
	}

}