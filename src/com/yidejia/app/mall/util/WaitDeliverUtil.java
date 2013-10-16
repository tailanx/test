package com.yidejia.app.mall.util;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
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
import com.yidejia.app.mall.view.ExchangeActivity;

public class WaitDeliverUtil {
	private Context context;
	private LayoutInflater mInflater;
	private LinearLayout mLinearLayout;//外层加载用的
	private LinearLayout mLayout;//用来传参用的
	private View view;
	
	private TextView titleTextView;//订单的状态
	private TextView numberTextView;//订单的编号
	private TextView sumPrice;//订单的总价格
	private TextView countTextView;//总数目
	private Button mButton;//催货
	private Button mButton2;//退换货
	
	private OrderDataManage orderDataManage ;//用来获取订单数据
	
	public WaitDeliverUtil(Context context,LinearLayout layout){
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.mLinearLayout = layout;
	}
	public void setupShow(){
		
		view = mInflater.inflate(R.layout.wait_deliver_item_main_item,null);
		
		mButton = (Button) view.findViewById(R.id.wait_deliver_item_payment);
		
		mButton2 = (Button) view.findViewById(R.id.wait_deliver_item_exchange);
		
		mButton2.setOnClickListener(new OnClickListener() {
			   
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, ExchangeActivity.class);
				context.startActivity(intent);
				
			}
		});
		mLayout = (LinearLayout) view.findViewById(R.id.wait_deliver_relative2);
		titleTextView = (TextView)view.findViewById(R.id.wait_deliver_item_detail);
		numberTextView = (TextView)view.findViewById(R.id.wait_deliver_item_textview2_nubmer);
		sumPrice = (TextView)view.findViewById(R.id.wait_deliver_sum_money_detail);
		countTextView = (TextView)view.findViewById(R.id.wait_deliver_item_textview7_count);
		
	}
	
	/**
	 * 加载视图
	 */
	public void loadView() {
		try {
			orderDataManage = new OrderDataManage(context);
			ArrayList<Order> mList = orderDataManage.getOrderArray(
					((MyApplication) context.getApplicationContext())
							.getUserId(), "", "", "已签收", 0 + "", 5 + "",
					((MyApplication) context.getApplicationContext())
							.getToken());
			Log.i("info", mList.size() + "mList");
			for (int i = 0; i < mList.size(); i++) {
				setupShow();
				Log.i("info", view + "+view");
				Order mOrder = mList.get(i);
				titleTextView.setText(mOrder.getStatus());
				numberTextView.setText(mOrder.getOrderCode());
				// sumPrice.setText(100+"");
				// countTextView.setText(1+"");
				WaitDeliverDetail waitDeliverDetail = new WaitDeliverDetail(
						context, mOrder, mLayout);
				waitDeliverDetail.addView();// 加载商品
				for (int j = 0; j < waitDeliverDetail.map.size(); j++) {
					// Log.i("info", mLinearLayoutLayout+"+mlayout");
					sumPrice.setText(waitDeliverDetail.map.get("price") + "");
					countTextView.setText(waitDeliverDetail.map.get("count")
							.intValue() + "");
				}

				mLinearLayout.addView(view);
				Log.i("info", mLinearLayout + "+mlayout");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "网络不给力！", Toast.LENGTH_SHORT).show();

		}
	}
}
