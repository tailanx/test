package com.yidejia.app.mall.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.yidejia.app.mall.datamanage.CartsDataManage;

public class MallInnerReceiver extends BroadcastReceiver {
	private CartsDataManage cartsDataManage ;
	private int number;
	private Button cartImage;
	public MallInnerReceiver(Button goBuy){
		this.cartImage = goBuy;
		cartsDataManage = new CartsDataManage();
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		if (Consts.UPDATE_CHANGE.equals(action)) {
			number = cartsDataManage.getCartAmount();
			cartImage.setVisibility(View.VISIBLE);
			cartImage.setText(number + "");
		}
		if (Consts.BROAD_UPDATE_CHANGE.equals(action)) {
			cartImage.setVisibility(View.GONE);
		}
		if (Consts.DELETE_CART.equals(action)) {
			number = cartsDataManage.getCartAmount();
			if (number == 0)
				cartImage.setVisibility(View.GONE);
			else {
				cartImage.setText(number + "");
			}
		}
	}

}
