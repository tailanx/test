package com.yidejia.app.mall.view;

import com.yidejia.app.mall.HomeCartActivity;

public class GoCartActivity extends HomeCartActivity {

	@Override
	protected void onResume() {
		super.onResume();
		hideBottomView();
	}

}
