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
import com.yidejia.app.mall.view.PayActivity;

public class WaitPayUtil {
	private Context context;
	private LayoutInflater mInflater;
	private LinearLayout mLinearLayout;// �������õ�
	private LinearLayout mLayout;// ���������õ�
	private View view;

	private TextView titleTextView;// ������״̬
	private TextView numberTextView;// �����ı��
	private TextView sumPrice;// �������ܼ۸�
	private TextView countTextView;// ����Ŀ

	private OrderDataManage orderDataManage;// ������ȡ��������
	private Button mButton;

	public WaitPayUtil(Context context, LinearLayout layout) {
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.mLinearLayout = layout;
	}

	public void setupShow() {
		view = mInflater.inflate(R.layout.wair_pay_order_item_item, null);
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
	 * ������ͼ
	 */
	public void loadView() {
		try {
			orderDataManage = new OrderDataManage(context);
			ArrayList<Order> mList = orderDataManage.getOrderArray(((MyApplication)context.getApplicationContext()).getUserId(),
					"", "", "��ǩ��", 0 + "", 5 + "", ((MyApplication)context.getApplicationContext()).getToken());
			Log.i("info", mList.size() + "mList");
			for (int i = 0; i < mList.size(); i++) {
				setupShow();
				// Log.i("info", view+"+view");

				Order mOrder = mList.get(i);

				titleTextView.setText(mOrder.getStatus());

//				Log.i("info", mOrder.getStatus()+ "  ");

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
				mButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(context,PayActivity.class);
						Bundle mBundle = new Bundle();
						mBundle.putString("price", waitPayOrderDetail.map.get("price")+"");
						intent.putExtras(mBundle);
						context.startActivity(intent);
					}
				});	
				mLinearLayout.addView(view);
//				Log.i("info", mLinearLayout + "+mlayout");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "���粻������", Toast.LENGTH_SHORT).show();

		}
	}
}
