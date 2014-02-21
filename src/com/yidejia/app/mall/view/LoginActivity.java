package com.yidejia.app.mall.view;

import com.yidejia.app.mall.HomeLoginActivity;

public class LoginActivity extends HomeLoginActivity {

	@Override
	protected void onResume() {
		super.onResume();
		hideBottomView();
		
		//当前的登录页不是根节点时值应该设置为false
		setIsNeedExit(false);
	}

}