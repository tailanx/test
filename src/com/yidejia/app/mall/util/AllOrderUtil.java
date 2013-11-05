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
import com.yidejia.app.mall.model.Cart;
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
	
	public void loadView(int orderTimeType, int orderType, int fromIndex, int amount) {
		try {
			orderDataManage = new OrderDataManage(context);
			final ArrayList<Order> mList = orderDataManage.getOrderArray(
					myApplication.getUserId(), "", String.valueOf(getOrderTime(orderTimeType)), getOrderTpye(orderType), fromIndex + "",
					amount + "", myApplication.getToken());
			// Log.i("info", mList.size()+"mList");
			for (int i = 0; i < mList.size(); i++) {
				
				final View view = inflater.inflate(R.layout.all_order_item_item, null);
				// Log.i("info", view+"");
				Button mCancalBtn = (Button) view.findViewById(R.id.all_order_item_main_cancal);
				Button mOkBtn = (Button) view
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
				
				mOrderType = getOrderTypeCode(mOrder.getStatus());
				mOkBtn.setText(setOkBtnText(mOrderType));
				if(mOrderType == 1 || mOrderType == 2) 
					mCancalBtn.setText(setCancelBtnText(mOrderType));
				else mCancalBtn.setVisibility(View.GONE);

				final AllOrderDetail allOrderDetail = new AllOrderDetail(
						context, mOrder, mLayout);
				allOrderDetail.addView();// ������Ʒ
				for (int j = 0; j < allOrderDetail.map.size(); j++) {

					sumPrice.setText(allOrderDetail.map.get("price") + "");
					countTextView.setText(allOrderDetail.map.get("count")
							.intValue() + "");
				}
				mCancalBtn.setOnClickListener( new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						boolean isSucess = orderDataManage.cancelOrder(myApplication.getUserId(),mOrder.getOrderCode(), myApplication.getToken());
						Log.i("info", isSucess + "      isSucess");
						mLinearLayoutLayout.removeView(layout1);
					}
				});
				mOkBtn.setOnClickListener(new OkClickListener(mOrder.getOrderCode(), allOrderDetail.map.get("price") + "", mOrder.getCartsArray()));
				
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
	
	/**
	 * 根据订单的状态（orderType）获取请求订单数据的字符串字段值
	 * @param orderType（0-4分别表示全部订单，待付款订单，待发货订单，已发货订单，已完成订单）
	 * @return
	 */
	private String getOrderTpye(int orderType){
		String orderTypeStr = "";
		switch (orderType) {
		case 0:
			orderTypeStr = "";
			break;
		case 1:
			orderTypeStr = "录入";
			break;
		case 2:
			orderTypeStr = "已付款";
			break;
		case 3:
			orderTypeStr = "已发货";
			break;
		case 4:
			orderTypeStr = "已签收";
			break;

		default:
			break;
		}
		return orderTypeStr;
	}
	
	/**
	 * 根据订单时间状态（orderTimeType）获取订单的时间戳
	 * @param orderTimeType 0-2 分别表示近一周，近一月，近一年
	 * @return 近一周，近一月，近一年数据查询的时间戳
	 */
	private long getOrderTime(int orderTimeType){
		long orderTime = System.currentTimeMillis()/1000;
		switch (orderTimeType) {
		case 0:
			orderTime -= 7L * 24L * 60L * 60L;
			break;
		case 1:
			orderTime -= 30L * 24L * 60L * 60L;
			break;
		case 2:
			orderTime -= 365L * 24L * 60L * 60L;
			break;

		default:
			break;
		}
		return orderTime;
	}
	
	/**
	 * 根据订单的状态（orderType）获取确定按钮的显示文字text
	 * @param orderType 0-4分别表示全部订单，待付款订单，待发货订单，已发货订单，已完成订单
	 * @return
	 */
	private String setOkBtnText(int orderType){
		String text = "";
		switch (orderType) {
		case 0:
			
			break;
		case 1:
			text = "付款";
			break;
		case 2:
			text = "催货";
			break;
		case 3:
			text = "查看物流";
			break;
		case 4:
			text = "评价";
			break;

		default:
			break;
		}
		return text;
	}
	
	/**
	 * 根据订单的状态（orderType）获取取消按钮的显示文字text
	 * @param orderType 0-4分别表示全部订单，待付款订单，待发货订单，已发货订单，已完成订单
	 * @return
	 */
	private String setCancelBtnText(int orderType){
		String text = "";
		switch (orderType) {
		case 0:
			
			break;
		case 1:
			text = "取消";
			break;
		case 2:
			text = "查看";
			break;
		case 3:
			break;
		case 4:
			break;
			
		default:
			break;
		}
		return text;
	}
	
	/**
	 * 根据字符串获取订单的种类
	 * @param str
	 * @return
	 */
	private int getOrderTypeCode(String str){
		int code = 0;
		if("已付款".equals(str)) code = 2;
		else if("录入".equals(str)) code = 1;
		else if("已发货".equals(str)) code = 3;
		else if("已签收".equals(str)) code = 4;
		else code = 0;
		return code;
	}

	
	private int mOrderType;
	private class OkClickListener implements View.OnClickListener{
		
		private String OrderPrice;
		private String OrderCode;
		private ArrayList<Cart> carts;
		
		public OkClickListener(String OrderCode, String OrderPrice, ArrayList<Cart> carts){
			this.OrderCode = OrderCode;
			this.OrderPrice = OrderPrice;
			this.carts = carts;
		}
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (mOrderType) {
			case 1:
				Intent intent = new Intent(context,
						OrderDetailActivity.class);

				Bundle bundle = new Bundle();
				bundle.putString("OrderCode", OrderCode);//mOrder.getOrderCode()
				bundle.putString("OrderPrice", OrderPrice);//allOrderDetail.map.get("price") + ""
				intent.putExtras(bundle);
				intent.putExtra("carts", carts);//mOrder.getCartsArray()
				context.startActivity(intent);
				break;

			default:
				break;
			}
		}
		
	};
}

