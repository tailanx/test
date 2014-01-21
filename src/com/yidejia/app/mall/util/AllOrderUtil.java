package com.yidejia.app.mall.util;

import java.util.ArrayList;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
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
import com.yidejia.app.mall.datamanage.AnlsGetOrderData;
import com.yidejia.app.mall.datamanage.OrderDataManage;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.order.Order;
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
	
	public AllOrderUtil(Context context, LinearLayout mLayout){
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.mLinearLayoutLayout = mLayout;
		myApplication  = (MyApplication) context.getApplicationContext();
	}
	
	public AllOrderUtil(){
		
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
	
	private int orderType;//0-4分别表示全部订单，待付款订单，待发货订单，已发货订单，已完成订单
//	private int mOrderType;//订单的状态
//	private Handler mHandler;
	
	/**
	 * 获取订单和加载订单到视图层
	 * @param orderTimeType 0-2 分别表示近一周，近一月，近一年
	 * @param orderType 0-4分别表示全部订单，待付款订单，待发货订单，已发货订单，已完成订单
	 * @param fromIndex 获取订单的开始下标
	 * @param amount 获取订单的个数
	 */
	
	public void loadView(int orderTimeType, int orderType, int fromIndex, int amount) {
		this.orderType = orderType;
		try {
//			orderDataManage = new OrderDataManage(context);
//			final ArrayList<Order> mList = orderDataManage.getOrderArray(
//					myApplication.getUserId(), "", String.valueOf(getOrderTime(orderTimeType)), getOrderTpye(orderType), fromIndex + "",
//					amount + "", myApplication.getToken());
			// Log.i("info", mList.size()+"mList");
//			viewCtrl(mList);
			AnlsGetOrderData taskGetOrder = new AnlsGetOrderData(context, mLinearLayoutLayout, orderType);
			taskGetOrder.getOrderArray(myApplication.getUserId(), "",
					String.valueOf(getOrderTime(orderTimeType)),
					getOrderTpye(orderType), fromIndex + "", amount + "",
					myApplication.getToken());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context,
					context.getResources().getString(R.string.bad_network),
					Toast.LENGTH_SHORT).show();

		}
	}
	/**/
	
	public void viewCtrl(ArrayList<Order> mList){
		for (int i = 0; i < mList.size(); i++) {
			
			final View view = inflater.inflate(R.layout.all_order_item_item, null);
			// Log.i("info", view+"");
			final Button mCancelBtn = (Button) view.findViewById(R.id.all_order_item_main_cancal);
			final Button mOkBtn = (Button) view
					.findViewById(R.id.all_order_item_main_pay);
			final LinearLayout layout1= (LinearLayout) view.findViewById(R.id.all_order_item_main_linerar);
			LinearLayout mLayout = (LinearLayout) view
					.findViewById(R.id.all_order_item_main_relative2);
			final TextView titleTextView = (TextView) view
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
			
			int mOrderType = getOrderTypeCode(mOrder.getStatus());
			mOkBtn.setText(setOkBtnText(mOrderType));
			if(mOrderType == 1 || mOrderType == 2) 
				mCancelBtn.setText(setCancelBtnText(mOrderType));
			else {
				mCancelBtn.setVisibility(View.GONE);
				if(mOrderType == -1){
					mOkBtn.setVisibility(View.GONE);
				}
			}

			final AllOrderDetail allOrderDetail = new AllOrderDetail(
					context, mOrder, mLayout);
			allOrderDetail.addView();// ������Ʒ
			for (int j = 0; j < allOrderDetail.map.size(); j++) {

				sumPrice.setText(allOrderDetail.map.get("price") + "");
				countTextView.setText(allOrderDetail.map.get("count")
						.intValue() + "");
			}
			if (mOrderType == 1) {//
				mCancelBtn.setOnClickListener(new CancelClickListener(
						mOrderType, mOrder.getOrderCode(),
						allOrderDetail.map.get("price") + "", mOrder
								.getCartsArray(), layout1, titleTextView,
						mOkBtn));
			} else if (mOrderType == 2) {
				mCancelBtn.setOnClickListener(new CancelClickListener(
						mOrderType, mOrder.getOrderCode(),
						allOrderDetail.map.get("price") + "", mOrder
								.getCartsArray(), null, null, null));
			}
			mOkBtn.setOnClickListener(new OkClickListener(mOrderType, mOrder.getOrderCode(), allOrderDetail.map.get("price") + "", mOrder.getCartsArray()));
			
			mLayout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					go2OrderDetail(mOrder.getOrderCode(),
							allOrderDetail.map.get("price") + "",
							mOrder.getCartsArray());
				}
			});
//			Log.i("info", mLinearLayoutLayout + "+layout");
			mLinearLayoutLayout.addView(view);
			/*
			mHandler = new Handler(){  
		        
		        public void handleMessage(Message msg) {  
		            switch (msg.what) {  
		            case 1:  
//		                updateTitle();  
//		            	mOrderType = 5;
		            	mOkBtn.setOnClickListener(new OkClickListener(5, mOrder.getOrderCode(), allOrderDetail.map.get("price") + "", mOrder.getCartsArray()));
		            	mCancelBtn.setVisibility(View.GONE);
						mOkBtn.setText(context.getResources().getString(R.string.delete_produce));
						titleTextView.setText("已取消");
		                break;  
		            }  
		        }
		    }; 
		    */
		}
	}
	
	/**
	 * 根据订单的状态（orderType）获取请求订单数据的字符串字段值
	 * @param orderType（0-4分别表示全部订单，待付款订单，待发货订单，已发货订单，已完成订单）
	 * @return
	 */
	public String getOrderTpye(int orderType){
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
	public long getOrderTime(int orderTimeType){
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
	 * @param orderType 0-5分别表示全部订单，待付款订单，待发货订单，已发货订单，已完成订单,已取消订单
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
		case 5:
			text = "删除";
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
		else if("已取消".equals(str)) code = 5;
		else code = -1;
		return code;
	}

	/**
	 * 
	 * 查看物流，删除，评价， 付款按钮
	 *
	 */
	private class OkClickListener implements View.OnClickListener{
		
		private int mOrderType;
		private String OrderPrice;
		private String OrderCode;
		private ArrayList<Cart> carts;
		
		public OkClickListener(int mOrderType, String OrderCode, String OrderPrice, ArrayList<Cart> carts){
			this.OrderCode = OrderCode;
			this.OrderPrice = OrderPrice;
			this.carts = carts;
			this.mOrderType = mOrderType;
		}
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.i(AllOrderUtil.class.getName(), "orderType:" + mOrderType);
			switch (mOrderType) {
			case 1:
				Intent detailIntent = new Intent(context,
						OrderDetailActivity.class);

				Bundle detailBundle = new Bundle();
				detailBundle.putString("OrderCode", OrderCode);//mOrder.getOrderCode()
				detailBundle.putString("OrderPrice", OrderPrice);//allOrderDetail.map.get("price") + ""
				detailIntent.putExtras(detailBundle);
				detailIntent.putExtra("carts", carts);//mOrder.getCartsArray()
				context.startActivity(detailIntent);
				break;
			case 5:
//				Intent delIntent = new Intent(context,
//						OrderDetailActivity.class);
//				
//				Bundle bundle = new Bundle();
//				bundle.putString("OrderCode", OrderCode);//mOrder.getOrderCode()
//				bundle.putString("OrderPrice", OrderPrice);//allOrderDetail.map.get("price") + ""
//				intent.putExtras(bundle);
//				intent.putExtra("carts", carts);//mOrder.getCartsArray()
//				context.startActivity(intent);
//				if(null == layout1) break;
//				new Builder(context).setTitle(R.string.tips)
//				.setMessage(R.string.del_order)
//				.setPositiveButton(context.getResources().getString(R.string.sure), new DialogInterface.OnClickListener() {
//					
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						// TODO Auto-generated method stub
//						
//						boolean isSucess = orderDataManage.cancelOrder(myApplication.getUserId(), orderCode, myApplication.getToken());
//						Log.i("info", isSucess + "      isSucess");
//						mLinearLayoutLayout.removeView(layout1);
//					}
//				}).setNegativeButton(context.getResources().getString(R.string.cancel), null).create().show();
				Toast.makeText(context, "del", Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		}
		
	};
	
	/**
	 * 
	 * 取消按钮
	 *
	 */
	private class CancelClickListener implements View.OnClickListener{
		
		private String orderCode;
		private int mOrderType;
		private LinearLayout layout1;
		private TextView titleTextView;
		private Button mOkBtn;
		private String orderPrice;
		private ArrayList<Cart> carts;
		
//		public CancelClickListener(int mOrderType, String orderCode, LinearLayout layout1){
//			this.orderCode = orderCode;
//			this.mOrderType = mOrderType;
//			this.layout1 = layout1;
//		}
		
		public CancelClickListener(int mOrderType, String orderCode,
				String orderPrice, ArrayList<Cart> carts,
				LinearLayout layout1, TextView titleTextView, Button okButton) {
			this.orderCode = orderCode;
			this.mOrderType = mOrderType;
			this.layout1 = layout1;
			this.titleTextView = titleTextView;
			this.mOkBtn = okButton;
			this.orderPrice = orderPrice;
			this.carts = carts;
		}
		
//		public CancelClickListener(int mOrderType, String orderCode, String orderPrice, ArrayList<Cart> carts){
//			this.orderCode = orderCode;
//			this.orderPrice = orderPrice;
//			this.mOrderType = mOrderType;
//			this.carts = carts;
//		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			final View mCancelBtn = v;
			switch (mOrderType) {
			case 1:
				if(null == layout1) break;
				new Builder(context).setTitle(R.string.tips)
				.setMessage(R.string.cancel_order)
				.setPositiveButton(context.getResources().getString(R.string.sure), new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						boolean isSucess = orderDataManage.cancelOrder(myApplication.getUserId(), orderCode, myApplication.getToken());
						Log.i("info", isSucess + "      isSucess");
						if (isSucess) {
							if (orderType == 1)//待付款订单页remove掉该订单
								mLinearLayoutLayout.removeView(layout1);
							else if (orderType == 0) {//全部订单页修改支付状态和隐藏取消按钮
//								Message message = new Message();  
//					            message.what = 1;  
//					            mHandler.sendMessage(message); 
								if(mOkBtn == null) return;
								mOkBtn.setOnClickListener(new OkClickListener(5, orderCode, orderPrice, carts));
				            	mCancelBtn.setVisibility(View.GONE);
								mOkBtn.setText(context.getResources().getString(R.string.delete));
								titleTextView.setText("已取消");
							}
						}
					}
				}).setNegativeButton(context.getResources().getString(R.string.cancel), null).create().show();
				break;
			case 2:
				if(carts.isEmpty() || "".equals(orderPrice) || null == orderPrice)break;
				go2OrderDetail(orderCode, orderPrice, carts);
				break;
			default:
				break;
			}
		}
		
	}
	
	/**
	 * 跳转到订单详情
	 * @param orderCode 订单号
	 * @param orderPrice 订单价格
	 * @param carts 购物清单
	 */
	private void go2OrderDetail(String orderCode, String orderPrice, ArrayList<Cart> carts){
		Intent intent = new Intent(context,
				OrderDetailActivity.class);

		Bundle bundle = new Bundle();
		bundle.putString("OrderCode", orderCode);
		bundle.putString("OrderPrice", orderPrice);
		intent.putExtras(bundle);
		intent.putExtra("carts", carts);
		context.startActivity(intent);
	}
	
	/*
	private Handler mHandler = new Handler(){  
        
        public void handleMessage(Message msg) {  
            switch (msg.what) {  
            case 1:  
//                updateTitle();  
            	mOrderType = 5;
				cancleView.setVisibility(View.GONE);
				okButton.setText(context.getResources().getString(R.string.delete_produce));
				titleTextView.setText("已取消");
                break;  
            }  
        };  
    };
    */  
}

