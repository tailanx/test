package com.yidejia.app.mall.goodinfo;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.R;

public class CommodityInfoActivity extends SherlockActivity{
	
	private String goodsId;	//商品id

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			goodsId = bundle.getString("goodsId");
		}
		
		setContentView(R.layout.item_goods_base_info);
		
	}

}
