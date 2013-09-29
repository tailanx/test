package com.yidejia.app.mall.util;

import java.util.ArrayList;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.OrderDataManage;
import com.yidejia.app.mall.model.Order;

import android.R.integer;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AllOrderUtil {
	private Context context;
	private	LayoutInflater inflater;
	private View view;
	
	private TextView titleTextView;//订单的状态
	private TextView numberTextView;//订单的编号
	private TextView sumPrice;//订单的总价格
	private TextView countTextView;//订单的总数目
	private LinearLayout mLinearLayoutLayout;//外层的布局
	private LinearLayout mLayout;//外层的布局

	private OrderDataManage orderDataManage ;//用来获取订单数据
	
	public AllOrderUtil(Context context,LinearLayout mLayout){
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.mLinearLayoutLayout = mLayout;
	}
	/**
	 * 实例化对象
	 */
	private void setupShow(){
		try {
			view = inflater.inflate(R.layout.all_order_item_item, null);
//			Log.i("info", view+"");
			mLayout = (LinearLayout) view.findViewById(R.id.all_order_item_main_relative2);
			titleTextView = (TextView)view.findViewById(R.id.all_order_item_main_item_detail);
			numberTextView = (TextView)view.findViewById(R.id.all_order_item_main_item_number);
			sumPrice = (TextView)view.findViewById(R.id.all_order_item_main_sum_money_deatil);
			countTextView = (TextView)view.findViewById(R.id.all_order_item_main_item_textview7_detail);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "网络不给力！", Toast.LENGTH_SHORT).show();

		}
	}
/**
 * 加载视图
 */
	public void loadView(){
		try {
			orderDataManage = new OrderDataManage(context);
			ArrayList<Order> mList = orderDataManage.getOrderArray(514492+"", "", "", "录入", 0+"", 5+"");
//			Log.i("info", mList.size()+"mList");
			for(int i=0;i<mList.size();i++){
				setupShow();
//				Log.i("info", view+"+view");
				Order mOrder = mList.get(i);
				titleTextView.setText(mOrder.getStatus());
				numberTextView.setText(mOrder.getOrderCode());
//				sumPrice.setText(100+"");
				AllOrderDetail allOrderDetail = new AllOrderDetail(context, mOrder, mLayout);
				allOrderDetail.addView();//加载商品
				for(int j=0;j<allOrderDetail.map.size();j++){
//				Log.i("info", mLinearLayoutLayout+"+mlayout");
				sumPrice.setText(allOrderDetail.map.get("price")+"");
				countTextView.setText(allOrderDetail.map.get("count").intValue()+"");
				}
				mLinearLayoutLayout.addView(view);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "网络不给力！", Toast.LENGTH_SHORT).show();

		}
	}
	
}
