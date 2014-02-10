package com.yidejia.app.mall.view;

import android.os.Bundle;

import com.yidejia.app.mall.HomeLoginActivity;

public class LoginActivity extends HomeLoginActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		hideBottomView();
	}
}