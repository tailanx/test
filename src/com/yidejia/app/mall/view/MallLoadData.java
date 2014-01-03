package com.yidejia.app.mall.view;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.MainProduct;

public class MallLoadData {

	private Context context;
	private ArrayList<MainProduct> acymerArray;
	private ArrayList<MainProduct> inerbtyArray;
	private ArrayList<MainProduct> hotsellArray;

	public MallLoadData(Context context, View view,
			ArrayList<MainProduct> acymerArray,
			ArrayList<MainProduct> inerbtyArray,
			ArrayList<MainProduct> hotsellArray) {
		this.context = context;
		this.acymerArray = acymerArray;
		this.inerbtyArray = inerbtyArray;
		this.hotsellArray = hotsellArray;
	}

	/**
	 * 获取首页商品hotsell,acymer,inerbty布局的控件和跳转
	 * 
	 * @param view
	 */
	public void intentToView(View view) {
		try {
			// hot sell 左边
			RelativeLayout hotsellLeft = (RelativeLayout) view
					.findViewById(R.id.main_hot_sell_left);
			hotsellLeft.setOnClickListener(new MainGoodsOnclick(hotsellArray
					.get(0).getUId()));

			hotsellLeft.setOnClickListener(new MainGoodsOnclick(hotsellArray
					.get(0).getUId()));
			// hot sell 右上
			RelativeLayout hotsellRightTop = (RelativeLayout) view
					.findViewById(R.id.main_hot_sell_right_top);
			hotsellRightTop.setOnClickListener(new MainGoodsOnclick(
					hotsellArray.get(1).getUId()));

			hotsellRightTop.setOnClickListener(new MainGoodsOnclick(
					hotsellArray.get(1).getUId()));
			// hot sell 右下
			RelativeLayout hotsellRightDown = (RelativeLayout) view
					.findViewById(R.id.main_hot_sell_right_down);
			hotsellRightDown.setOnClickListener(new MainGoodsOnclick(
					hotsellArray.get(2).getUId()));

			hotsellRightDown.setOnClickListener(new MainGoodsOnclick(
					hotsellArray.get(2).getUId()));

			// acymer 左边
			RelativeLayout acymeiLeft = (RelativeLayout) view
					.findViewById(R.id.main_acymei_left);
			acymeiLeft.setOnClickListener(new MainGoodsOnclick(acymerArray.get(
					0).getUId()));

			acymeiLeft.setOnClickListener(new MainGoodsOnclick(acymerArray.get(
					0).getUId()));
			// acymer 右上
			RelativeLayout acymeiRightTop = (RelativeLayout) view
					.findViewById(R.id.main_acymei_right_top);
			acymeiRightTop.setOnClickListener(new MainGoodsOnclick(acymerArray
					.get(1).getUId()));

			acymeiRightTop.setOnClickListener(new MainGoodsOnclick(acymerArray
					.get(1).getUId()));
			// acymer 右下
			RelativeLayout acymeiRightDown = (RelativeLayout) view
					.findViewById(R.id.main_acymei_right_down);
			acymeiRightDown.setOnClickListener(new MainGoodsOnclick(acymerArray
					.get(2).getUId()));

			acymeiRightDown.setOnClickListener(new MainGoodsOnclick(acymerArray
					.get(2).getUId()));

			// inerbty 左上
			RelativeLayout inerbtyTopLeft = (RelativeLayout) view
					.findViewById(R.id.main_inerbty_top_left);
			inerbtyTopLeft.setOnClickListener(new MainGoodsOnclick(inerbtyArray
					.get(0).getUId()));

			inerbtyTopLeft.setOnClickListener(new MainGoodsOnclick(inerbtyArray
					.get(0).getUId()));
			// inerbty 右上
			RelativeLayout inerbtyTopRight = (RelativeLayout) view
					.findViewById(R.id.main_inerbty_top_right);
			inerbtyTopRight.setOnClickListener(new MainGoodsOnclick(
					inerbtyArray.get(1).getUId()));

			inerbtyTopRight.setOnClickListener(new MainGoodsOnclick(
					inerbtyArray.get(1).getUId()));
			// inerbty 左中
			RelativeLayout nerbtyMidLeft = (RelativeLayout) view
					.findViewById(R.id.main_inerbty_mid_left);
			nerbtyMidLeft.setOnClickListener(new MainGoodsOnclick(inerbtyArray
					.get(2).getUId()));

			nerbtyMidLeft.setOnClickListener(new MainGoodsOnclick(inerbtyArray
					.get(2).getUId()));
			// inerbty 右中
			RelativeLayout inerbtyMidRight = (RelativeLayout) view
					.findViewById(R.id.main_inerbty_mid_right);
			inerbtyMidRight.setOnClickListener(new MainGoodsOnclick(
					inerbtyArray.get(3).getUId()));

			inerbtyMidRight.setOnClickListener(new MainGoodsOnclick(
					inerbtyArray.get(3).getUId()));
			// inerbty 左下
			RelativeLayout inerbtyDownLeft = (RelativeLayout) view
					.findViewById(R.id.main_inerbty_down_left);
			inerbtyDownLeft.setOnClickListener(new MainGoodsOnclick(
					inerbtyArray.get(4).getUId()));

			inerbtyDownLeft.setOnClickListener(new MainGoodsOnclick(
					inerbtyArray.get(4).getUId()));
			// inerbty 右下
			RelativeLayout inerbtyDownRight = (RelativeLayout) view
					.findViewById(R.id.main_inerbty_down_right);
			inerbtyDownRight.setOnClickListener(new MainGoodsOnclick(
					inerbtyArray.get(5).getUId()));

			inerbtyDownRight.setOnClickListener(new MainGoodsOnclick(
					inerbtyArray.get(5).getUId()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context,
					context.getResources().getString(R.string.bad_network),
					Toast.LENGTH_SHORT).show();
			Toast.makeText(context,
					context.getResources().getString(R.string.bad_network),
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 首页hotsell,acymer,inerbty商品点击事件
	 * 
	 * @author long bin
	 * 
	 */
	private class MainGoodsOnclick implements OnClickListener {

		private String goodsId;

		public MainGoodsOnclick(String goodsId) {
			this.goodsId = goodsId;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intentLeft = new Intent(context, GoodsInfoActivity.class);

			Bundle bundle = new Bundle();
			bundle.putString("goodsId", goodsId);
			intentLeft.putExtras(bundle);
			context.startActivity(intentLeft);
		}

	}
}
