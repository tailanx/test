package com.yidejia.app.mall.util;

import java.util.ArrayList;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.OrderDataManage;
import com.yidejia.app.mall.model.Order;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WaitPayUtil {
	private Context context;
	private LayoutInflater mInflater;
	private LinearLayout mLinearLayout;// 外层加载用的
	private LinearLayout mLayout;// 用来传参用的
	private View view;

	private TextView titleTextView;// 订单的状态
	private TextView numberTextView;// 订单的编号
	private TextView sumPrice;// 订单的总价格
	private TextView countTextView;// 总数目

	private OrderDataManage orderDataManage;// 用来获取订单数据

	public WaitPayUtil(Context context, LinearLayout layout) {
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.mLinearLayout = layout;
	}

	public void setupShow() {
		view = mInflater.inflate(R.layout.wair_pay_order_item_item, null);
		mLayout = (LinearLayout) view
				.findViewById(R.id.wait_pay_order_relative2);
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
	public void loadView() {
		try {
			orderDataManage = new OrderDataManage(context);
			ArrayList<Order> mList = orderDataManage.getOrderArray(514492 + "",
					"", "", "已签收", 0 + "", 5 + "");
			Log.i("info", mList.size() + "mList");
			for (int i = 0; i < mList.size(); i++) {
				setupShow();
				// Log.i("info", view+"+view");

				Order mOrder = mList.get(i);

				titleTextView.setText(mOrder.getStatus());

//				Log.i("info", mOrder.getStatus());

				numberTextView.setText(mOrder.getOrderCode());

				WaitPayOrderDetail waitPayOrderDetail = new WaitPayOrderDetail(
						context, mOrder, mLayout);
				waitPayOrderDetail.addView();// 加载商品
				for (int j = 0; j < waitPayOrderDetail.map.size(); j++) {

					// Log.i("info", mLinearLayoutLayout+"+mlayout");
					sumPrice.setText(waitPayOrderDetail.map.get("price") + "");
					countTextView.setText(waitPayOrderDetail.map.get("count")
							.intValue() + "");
				}

				// sumPrice.setText(100+"");
				// countTextView.setText(1+"");
				mLinearLayout.addView(view);
//				Log.i("info", mLinearLayout + "+mlayout");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "网络不给力！", Toast.LENGTH_SHORT).show();

		}
	}
}
