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
	private LinearLayout mLinearLayout;//�������õ�
	private LinearLayout mLayout;//���������õ�
	private View view;
	private MyApplication myApplication;
	
	private TextView titleTextView;//������״̬
	private TextView numberTextView;//�����ı��
	private TextView sumPrice;//�������ܼ۸�
	private TextView countTextView;//����Ŀ
	private Button mButton;//�߻�
	private Button mButton2;//�˻���
	
	private OrderDataManage orderDataManage ;//������ȡ�������
	
	public WaitDeliverUtil(Context context,LinearLayout layout){
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.mLinearLayout = layout;
		this.myApplication = (MyApplication) context.getApplicationContext();
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
	 * ������ͼ
	 */
		public void loadView(int fromIndex,int amount){
			try {
				orderDataManage = new OrderDataManage(context);
				ArrayList<Order> mList = orderDataManage.getOrderArray(myApplication.getUserId(), "", "", "已付款", fromIndex+"", amount+"",myApplication.getToken());
				Log.i("info", mList.size()+"mList");
				for(int i=0;i<mList.size();i++){
					setupShow();
					Log.i("info", view+"+view");
					Order mOrder = mList.get(i);
					titleTextView.setText(mOrder.getStatus());
					numberTextView.setText(mOrder.getOrderCode());
//					sumPrice.setText(100+"");
//					countTextView.setText(1+"");
					WaitDeliverDetail waitDeliverDetail = new WaitDeliverDetail(context, mOrder, mLayout);
					waitDeliverDetail.addView();//������Ʒ
					for(int j=0;j<waitDeliverDetail.map.size();j++){
//					Log.i("info", mLinearLayoutLayout+"+mlayout");
					sumPrice.setText(waitDeliverDetail.map.get("price")+"");
					countTextView.setText(waitDeliverDetail.map.get("count").intValue()+"");
					}
					
					mLinearLayout.addView(view);
					Log.i("info", mLinearLayout+"+mlayout");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();

			}
		}
}

