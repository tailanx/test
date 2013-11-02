package com.yidejia.app.mall.util;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.OrderDataManage;
import com.yidejia.app.mall.model.Order;
import com.yidejia.app.mall.view.CstmPayActivity;

public class WaitPayUtil {
	private Context context;
	private LayoutInflater mInflater;
	private LinearLayout mLinearLayout;// 外层加载用的
	private LinearLayout mLayout;// 用来传参用的
	private View view;
	private MyApplication myApplication;

	private TextView titleTextView;// 订单的状态
	private TextView numberTextView;// 订单的编号
	private TextView sumPrice;// 订单的总价格
	private TextView countTextView;// 总数目
  	private Button cancel;

	private OrderDataManage orderDataManage;// 用来获取订单数据
	private Button mButton;
	private LinearLayout linear1;

	public WaitPayUtil(Context context, LinearLayout layout) {
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.mLinearLayout = layout;
		myApplication = (MyApplication) context.getApplicationContext();
	}

	public void setupShow() {
		view = mInflater.inflate(R.layout.wair_pay_order_item_item, null);
		linear1 = (LinearLayout) view
				.findViewById(R.id.wait_pay_order_linear);
		cancel = (Button) view.findViewById(R.id.wait_pay_order_item_cancal);
		mLayout = (LinearLayout) view
				.findViewById(R.id.wait_pay_order_relative2);
		mButton = (Button) view.findViewById(R.id.wait_pay_order_item_payment);
		titleTextView = (TextView) view
				.findViewById(R.id.wait_pay_order_item_main_item_detail);
		numberTextView = (TextView) view
				.findViewById(R.id.wait_pay_order_item_textview2_number);
		sumPrice = (TextView) view
				.findViewById(R.id.wait_pay_order_sum_money_detail);
		countTextView = (TextView) view
				.findViewById(R.id.wait_pay_order_item_textview7_count);

	}

	/**
	 * 加载视图
	 */
	public void loadView(int fromIndex, int amount) {
		try {
			orderDataManage = new OrderDataManage(context);
			ArrayList<Order> mList = orderDataManage.getOrderArray(
					myApplication.getUserId(), "", "", "录入", fromIndex + "",
					amount + "", myApplication.getToken());
			Log.i("info", mList.size() + "mList");
			for (int i = 0; i < mList.size(); i++) {
				setupShow();
				// Log.i("info", view+"+view");

				final Order mOrder = mList.get(i);

				titleTextView.setText(mOrder.getStatus());

				// Log.i("info", mOrder.getStatus()+ "  ");

				numberTextView.setText(mOrder.getOrderCode());

				final WaitPayOrderDetail waitPayOrderDetail = new WaitPayOrderDetail(
						context, mOrder, mLayout);
				waitPayOrderDetail.addView();// ������Ʒ
				for (int j = 0; j < waitPayOrderDetail.map.size(); j++) {

					// Log.i("info", mLinearLayoutLayout+"+mlayout");
					sumPrice.setText(waitPayOrderDetail.map.get("price") + "");
					countTextView.setText(waitPayOrderDetail.map.get("count")
							.intValue() + "");
				}

				// sumPrice.setText(100+"");
				// countTextView.setText(1+"");
				cancel.setOnClickListener( new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						orderDataManage.cancelOrder(myApplication.getUserId(), mOrder.getOrderCode(), myApplication.getToken());
						mLinearLayout.removeView(linear1);
					}
				});
				mButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(context,
								CstmPayActivity.class);
						Bundle mBundle = new Bundle();
						mBundle.putString("price",
								waitPayOrderDetail.map.get("price") + "");
						mBundle.putString("cartActivity", "Y");
						intent.putExtra("carts", mOrder.getCartsArray());
						intent.putExtras(mBundle);
						context.startActivity(intent);
					}
				});
				mLinearLayout.addView(view);
				// Log.i("info", mLinearLayout + "+mlayout");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context,
					context.getResources().getString(R.string.bad_network),
					Toast.LENGTH_SHORT).show();

		}
	}
}
