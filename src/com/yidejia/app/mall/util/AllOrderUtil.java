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

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.OrderDataManage;
import com.yidejia.app.mall.model.Order;
import com.yidejia.app.mall.view.PayActivity;

public class AllOrderUtil {
	private Context context;
	private	LayoutInflater inflater;
	private View view;
	
//	private TextView titleTextView;//订单的状态
//	private TextView numberTextView;//订单的编号
//	private TextView sumPrice;//订单的总价格
//	private TextView countTextView;//订单的总数目
	private LinearLayout mLinearLayoutLayout;//外层的布局
	private LinearLayout mLayout;//外层的布局

	private OrderDataManage orderDataManage ;//用来获取订单数据
	
	public AllOrderUtil(Context context,LinearLayout mLayout){
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.mLinearLayoutLayout = mLayout;
	}
//	/**
//	 * 实例化对象
//	 */
//	private void setupShow(){
//		try {
//			view = inflater.inflate(R.layout.all_order_item_item, null);
////			Log.i("info", view+"");
//			mLayout = (LinearLayout) view.findViewById(R.id.all_order_item_main_relative2);
//			titleTextView = (TextView)view.findViewById(R.id.all_order_item_main_item_detail);
//			numberTextView = (TextView)view.findViewById(R.id.all_order_item_main_item_number);
//			sumPrice = (TextView)view.findViewById(R.id.all_order_item_main_sum_money_deatil);
//			countTextView = (TextView)view.findViewById(R.id.all_order_item_main_item_textview7_detail);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			Toast.makeText(context, "网络不给力！", Toast.LENGTH_SHORT).show();
//
//		}
//	}
/**
 * 加载视图
 */
	public void loadView(){
		try {
			orderDataManage = new OrderDataManage(context);
			ArrayList<Order> mList = orderDataManage.getOrderArray(514492+"", "", "", "录入", 0+"","");
			Log.i("info", mList.size()+"mList");
			for(int i=0;i<mList.size();i++){
				view = inflater.inflate(R.layout.all_order_item_item, null);
//				Log.i("info", view+"");
				Button mButton = (Button) view.findViewById(R.id.all_order_item_main_pay);
				LinearLayout mLayout = (LinearLayout) view.findViewById(R.id.all_order_item_main_relative2);
				TextView titleTextView = (TextView)view.findViewById(R.id.all_order_item_main_item_detail);
				TextView numberTextView = (TextView)view.findViewById(R.id.all_order_item_main_item_number);
				TextView sumPrice = (TextView)view.findViewById(R.id.all_order_item_main_sum_money_deatil);
				TextView countTextView = (TextView)view.findViewById(R.id.all_order_item_main_item_textview7_detail);

				Order mOrder = mList.get(i);
				titleTextView.setText(mOrder.getStatus());
				numberTextView.setText(mOrder.getOrderCode());

				final AllOrderDetail allOrderDetail = new AllOrderDetail(context, mOrder, mLayout);
				allOrderDetail.addView();//加载商品
				for(int j=0;j<allOrderDetail.map.size();j++){

				sumPrice.setText(allOrderDetail.map.get("price")+"");
				countTextView.setText(allOrderDetail.map.get("count").intValue()+"");
				}
				
				
				
				mButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(context,PayActivity.class);
						Bundle mBundle = new Bundle();
						mBundle.putString("price", allOrderDetail.map.get("price")+"");
						intent.putExtras(mBundle);
						context.startActivity(intent);
					}
				});
				mLinearLayoutLayout.addView(view);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "网络不给力！", Toast.LENGTH_SHORT).show();

		}
	}
	
}
