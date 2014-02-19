package com.yidejia.app.mall.view;

import com.yidejia.app.mall.HomeCartActivity;

public class GoCartActivity extends HomeCartActivity {

	@Override
	protected void onResume() {
		super.onResume();
		hideBottomView();
		//当前的购物车页不是根节点时值应该设置为false
		setIsNeedExit(false);
	}

}
