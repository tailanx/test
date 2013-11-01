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
import com.yidejia.app.mall.view.OrderDetailActivity;

public class AllOrderUtil {
	private Context context;
	private	LayoutInflater inflater;
//	private View view;
	private MyApplication myApplication;
	
	
//	private TextView titleTextView;//������״̬
//	private TextView numberTextView;//�����ı��
//	private TextView sumPrice;//�������ܼ۸�
//	private TextView countTextView;//����������Ŀ
	private LinearLayout mLinearLayoutLayout;//���Ĳ���
	private LinearLayout mLayout;//���Ĳ���

	private OrderDataManage orderDataManage ;//������ȡ�������
	
	public AllOrderUtil(Context context,LinearLayout mLayout){
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.mLinearLayoutLayout = mLayout;
		myApplication  = (MyApplication) context.getApplicationContext();
	}
//	/**
//	 * ʵ�����
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
//			Toast.makeText(context, "���粻������", Toast.LENGTH_SHORT).show();
//
//		}
//	}
/**
 * ������ͼ
 */
	public void loadView(int fromIndex, int amount) {
		try {
			orderDataManage = new OrderDataManage(context);
			final ArrayList<Order> mList = orderDataManage.getOrderArray(
					myApplication.getUserId(), "", "", "", fromIndex + "",
					amount + "", myApplication.getToken());
			// Log.i("info", mList.size()+"mList");
			for (int i = 0; i < mList.size(); i++) {
				
				final View view = inflater.inflate(R.layout.all_order_item_item, null);
				// Log.i("info", view+"");
				Button mCancal = (Button) view.findViewById(R.id.all_order_item_main_cancal);
				Button mButton = (Button) view
						.findViewById(R.id.all_order_item_main_pay);
				final LinearLayout layout1= (LinearLayout) view.findViewById(R.id.all_order_item_main_linerar);
				LinearLayout mLayout = (LinearLayout) view
						.findViewById(R.id.all_order_item_main_relative2);
				TextView titleTextView = (TextView) view
						.findViewById(R.id.all_order_item_main_item_detail);
				TextView numberTextView = (TextView) view
						.findViewById(R.id.all_order_item_main_item_number);
				TextView sumPrice = (TextView) view
						.findViewById(R.id.all_order_item_main_sum_money_deatil);
				TextView countTextView = (TextView) view
						.findViewById(R.id.all_order_item_main_item_textview7_detail);

				final Order mOrder = mList.get(i);
				titleTextView.setText(mOrder.getStatus());
				numberTextView.setText(mOrder.getOrderCode());

				final AllOrderDetail allOrderDetail = new AllOrderDetail(
						context, mOrder, mLayout);
				allOrderDetail.addView();// ������Ʒ
				for (int j = 0; j < allOrderDetail.map.size(); j++) {

					sumPrice.setText(allOrderDetail.map.get("price") + "");
					countTextView.setText(allOrderDetail.map.get("count")
							.intValue() + "");
				}
				mCancal.setOnClickListener( new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						boolean isSucess = orderDataManage.cancelOrder(myApplication.getUserId(),mOrder.getOrderCode(), myApplication.getToken());
						Log.i("info", isSucess + "      isSucess");
						mLinearLayoutLayout.removeView(layout1);
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
								allOrderDetail.map.get("price") + "");
						intent.putExtra("carts", mOrder.getCartsArray());
						mBundle.putString("cartActivity", "Y");
						intent.putExtras(mBundle);
						context.startActivity(intent);
					}
				});
				mLayout.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(context,
								OrderDetailActivity.class);

						Bundle bundle = new Bundle();
						bundle.putString("OrderCode", mOrder.getOrderCode());
						bundle.putString("OrderPrice", allOrderDetail.map.get("price") + "");
						intent.putExtras(bundle);
						intent.putExtra("carts", mOrder.getCartsArray());
						context.startActivity(intent);
					}
				});
//				Log.i("info", mLinearLayoutLayout + "+layout");
				mLinearLayoutLayout.addView(view);
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

