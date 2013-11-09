package com.yidejia.app.mall.util;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import com.unionpay.uppay.task.s;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.OrderDataManage;
import com.yidejia.app.mall.datamanage.TaskDelOrder;
import com.yidejia.app.mall.model.Brand;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.Order;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.order.CancelOrder;
import com.yidejia.app.mall.view.CstmPayActivity;
import com.yidejia.app.mall.view.OrderDetailActivity;
import com.yidejia.app.mall.view.ReturnActivity;

public class OrdersUtil {
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

//	private OrderDataManage orderDataManage ;//������ȡ�������
	
	public OrdersUtil(Context context, LinearLayout mLayout){
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
	/*
	public void loadView(int orderTimeType, int orderType, int fromIndex, int amount) {
		this.orderType = orderType;
		try {
			orderDataManage = new OrderDataManage(context);
			final ArrayList<Order> mList = orderDataManage.getOrderArray(
					myApplication.getUserId(), "", String.valueOf(getOrderTime(orderTimeType)), getOrderTpye(orderType), fromIndex + "",
					amount + "", myApplication.getToken());
			// Log.i("info", mList.size()+"mList");
			viewCtrl(mList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context,
					context.getResources().getString(R.string.bad_network),
					Toast.LENGTH_SHORT).show();

		}
	}
	*/
	
	public void viewCtrl(ArrayList<Order> mList, int orderType){
		this.orderType = orderType;
		if(mList.isEmpty()) return;
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
			titleTextView.setText(mOrder.getStatus());//设置状态
			numberTextView.setText(mOrder.getOrderCode());//设置订单号
			
			int mOrderType = getOrderTypeCode(mOrder.getStatus());//根据订单状态获取订单类型
			
//			if(mOrderType == 1 || mOrderType == 2 || mOrderType == 3) 
//				mCancelBtn.setText(setCancelBtnText(mOrderType));
//			else {
//				mCancelBtn.setVisibility(View.GONE);
//				if(mOrderType == -1){
//					mOkBtn.setVisibility(View.GONE);
//				}
//			}

			final AllOrderDetail allOrderDetail = new AllOrderDetail(
					context, mOrder, mLayout);
			allOrderDetail.addView();// ������Ʒ
			for (int j = 0; j < allOrderDetail.map.size(); j++) {

				sumPrice.setText(allOrderDetail.map.get("price") + "");//设置订单总价格
				//设置订单商品总数
				countTextView.setText(allOrderDetail.map.get("count")
						.intValue() + "");
			}
			
			mOkBtn.setText(setOkBtnText(mOrderType));//设置右边按钮的显示
			mCancelBtn.setText(setCancelBtnText(mOrderType));//设置左边按钮的显示
			if (mOrderType == 1) {//左边取消
				
				mCancelBtn.setOnClickListener(new CancelClickListener(
						mOrderType, mOrder.getOrderCode(),
						allOrderDetail.map.get("price") + "", mOrder
								.getCartsArray(), layout1, titleTextView,
						mOkBtn));
			} else if (mOrderType == 2) {//左边查看
//				mCancelBtn.setText(setCancelBtnText(mOrderType));
				mCancelBtn.setOnClickListener(new CancelClickListener(
						mOrderType, mOrder.getOrderCode(),
						allOrderDetail.map.get("price") + "", mOrder
								.getCartsArray(), layout1, titleTextView, mOkBtn));
				
//				mOkBtn.setOnClickListener(new OkClickListener(mOrderType, mOrder.getOrderCode(), mOrder.getDate()));
			} else if(mOrderType == 3){//左边退换货
//				mCancelBtn.setText(setCancelBtnText(mOrderType));
				mCancelBtn.setOnClickListener(new CancelClickListener(mOrderType, mOrder.getOrderCode(), mOrder.getDate()));
			} else {
				mCancelBtn.setVisibility(View.GONE);
				if(mOrderType == -1){
					mOkBtn.setVisibility(View.GONE);
				}
			}
			
			
			if (mOrderType == 5) {// 右边删除按钮
				mOkBtn.setOnClickListener(new OkClickListener(mOrderType,
						mOrder.getOrderCode(), myApplication.getUserId(),
						myApplication.getToken(), layout1));
			} else if (mOrderType == 2) {//右边退换货按钮
				mOkBtn.setOnClickListener(new OkClickListener(mOrderType,
						mOrder.getOrderCode(), mOrder.getDate()));
			} else if(mOrderType == 4){//右边评价按钮
				mOkBtn.setVisibility(View.GONE);
			}else {
				mOkBtn.setOnClickListener(new OkClickListener(mOrderType,
						mOrder.getOrderCode(), allOrderDetail.map.get("price")
								+ "", mOrder.getCartsArray(), mOrder.getTn()));
			}
			
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
	/*
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
	*/
	/**
	 * 根据订单时间状态（orderTimeType）获取订单的时间戳
	 * @param orderTimeType 0-2 分别表示近一周，近一月，近一年
	 * @return 近一周，近一月，近一年数据查询的时间戳
	 */
	/*
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
	*/
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
			text = "退换货";
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
			text = "退换货";
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
	
	private TaskDelOrder taskDelOrder;

	/**
	 * 
	 * 查看物流，删除，评价， 付款按钮
	 *	
	 */
	private class OkClickListener implements View.OnClickListener{
		
		private int mOrderType;
		private String orderPrice;
		private String orderCode;
		private ArrayList<Cart> carts;
		private String orderTn;
		
		private String userId;
		private String token;
		private LinearLayout layout1;
		
		/**
		 * 待付款订单的ok按钮点击事件
		 * @param mOrderType 订单类型，0-5分别表示全部订单，待付款订单，待发货订单，已发货订单，已完成订单，已取消订单
		 * @param OrderCode 订单号
		 * @param OrderPrice 订单价格
		 * @param carts 订单商品数据
		 * @param orderTn 流水号
		 */
		public OkClickListener(int mOrderType, String OrderCode, String OrderPrice, ArrayList<Cart> carts, String orderTn){
			this.orderCode = OrderCode;
			this.orderPrice = OrderPrice;
			this.carts = carts;
			this.mOrderType = mOrderType;
			this.orderTn = orderTn;
		}
		
		/**
		 * 全部订单已取消订单的删除按钮的点击事件
		 * @param mOrderType 订单类型，0-5分别表示全部订单，待付款订单，待发货订单，已发货订单，已完成订单，已取消订单
		 * @param orderCode 订单号
		 * @param userId 用户id
		 * @param token 
		 * @param layout1 删除的一项界面
		 */
		public OkClickListener(int mOrderType, String orderCode, String userId, String token, LinearLayout layout1){
			this.mOrderType = mOrderType;
			this.orderCode = orderCode;
			this.userId = userId;
			this.token = token;
			this.layout1 = layout1;
		}
		
		private String orderDate;
		
		/**
		 * 右边按钮退换货点击事件
		 * @param mOrderType 只能是2
		 * @param orderCode 订单号
		 * @param orderDate 订单时间
		 */
		public OkClickListener(int mOrderType, String orderCode, String orderDate){
			this.mOrderType = mOrderType;
			this.orderCode = orderCode;
			this.orderDate = orderDate;
		}
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.i(OrdersUtil.class.getName(), "orderType:" + mOrderType);
			switch (mOrderType) {
			case 1://支付
				if(carts.isEmpty()) break;
				Intent detailIntent = new Intent(context,
						OrderDetailActivity.class);

				Bundle detailBundle = new Bundle();
				detailBundle.putString("OrderCode", orderCode);//mOrder.getOrderCode()
				detailBundle.putString("OrderPrice", orderPrice);//allOrderDetail.map.get("price") + ""
				detailBundle.putString("OrderTn", orderTn);
				detailIntent.putExtras(detailBundle);
				detailIntent.putExtra("carts", carts);//mOrder.getCartsArray()
				context.startActivity(detailIntent);
				break;
			case 2://退货
				go2Return(orderCode, orderDate);
				break;
			case 3://查看物流
				break;
			case 4://评价
				
				break;
			case 5://删除
//				Intent delIntent = new Intent(context,
//						OrderDetailActivity.class);
//				
//				Bundle bundle = new Bundle();
//				bundle.putString("OrderCode", OrderCode);//mOrder.getOrderCode()
//				bundle.putString("OrderPrice", OrderPrice);//allOrderDetail.map.get("price") + ""
//				intent.putExtras(bundle);
//				intent.putExtra("carts", carts);//mOrder.getCartsArray()
//				context.startActivity(intent);
				if(null == layout1) break;
				new Builder(context).setTitle(R.string.tips)
				.setMessage(R.string.del_order)
				.setPositiveButton(context.getResources().getString(R.string.sure), new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
//						boolean isSucess = orderDataManage.cancelOrder(myApplication.getUserId(), orderCode, myApplication.getToken());
//						Log.i("info", isSucess + "      isSucess");
//						mLinearLayoutLayout.removeView(layout1);
						taskDelOrder = new TaskDelOrder(context, mLinearLayoutLayout, layout1);
						taskDelOrder.delOrder(userId, orderCode, token);
					}
				}).setNegativeButton(context.getResources().getString(R.string.cancel), null).create().show();
//				Toast.makeText(context, "del", Toast.LENGTH_SHORT).show();
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
		/**
		 * 
		 * @param mOrderType 订单类型，0-5分别表示全部订单，待付款订单，待发货订单，已发货订单，已完成订单，已取消订单
		 * @param orderCode 订单号
		 * @param orderPrice 订单总价
		 * @param carts 订单商品
		 * @param layout1 订单项的布局
		 * @param titleTextView 订单状态的显示的textview
		 * @param okButton 订单项的ok按钮
		 */
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
		
		
		private String orderDate;//下订单时间
		/**
		 * 退换货用
		 * @param mOrderType 只能是3，代表退换货
		 * @param orderCode 订单号
		 * @param orderDate 下订单时间
		 */
		public CancelClickListener(int mOrderType, String orderCode, String orderDate){
			this.mOrderType = mOrderType;
			this.orderCode = orderCode;
			this.orderDate = orderDate;
		}

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
						
						/*
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
								mOkBtn.setText(context.getResources().getString(R.string.delete_produce));
								titleTextView.setText("已取消");
							}
						}
						*/
						if(!ConnectionDetector.isConnectingToInternet(context)) {
							Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
							return;
						}
						if(taskCancelOrder != null && taskCancelOrder.getStatus() == AsyncTask.Status.RUNNING){
							taskCancelOrder.cancel(true);
						}
						taskCancelOrder = new TaskCancelOrder(myApplication.getUserId(), orderCode, myApplication.getToken(),
								orderPrice, carts, titleTextView, mOkBtn, mCancelBtn, layout1);
						taskCancelOrder.execute();
					}
				}).setNegativeButton(context.getResources().getString(R.string.cancel), null).create().show();
				break;
			case 2:
				if(carts.isEmpty() || "".equals(orderPrice) || null == orderPrice)break;
				go2OrderDetail(orderCode, orderPrice, carts);
				break;
				
			case 3:
				go2Return(orderCode, orderDate);
				break;
			default:
				break;
			}
		}
		
	}
	
	/**
	 * 跳转到退换货
	 * @param orderCode 订单号
	 */
	private void go2Return(String orderCode, String orderDate){
		Intent retIintent = new Intent(context, ReturnActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("orderCode", orderCode);
		bundle.putString("orderDate", orderDate);
		retIintent.putExtras(bundle);
		context.startActivity(retIintent);
		
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
	
	private String TAG = OrdersUtil.class.getName();
	
	private TaskCancelOrder taskCancelOrder;
	
	
	/**
	 * 
	 * @param customer_id 用户id
	 * @param code 订单号
	 * @param token
	 * @param orderPrice 订单价格
	 * @param carts 订单商品
	 * @param titleTextView 标题状态的textview
	 * @param mOkBtn 支付按钮
	 * @param mCancelBtn 取消按钮
	 */
	private class TaskCancelOrder extends AsyncTask<Void, Void, Boolean>{
		private String customer_id;
		private String code;
		private String token;
		private String orderPrice;
		private ArrayList<Cart> carts;
		private TextView titleTextView;
		private Button mOkBtn;
		private View mCancelBtn;
		private LinearLayout layout1;
		
		private ProgressDialog bar ;
		
		private UnicodeToString unicode;
		
		/**
		 * 
		 * @param customer_id 用户id
		 * @param code 订单号
		 * @param token
		 * @param orderPrice 订单价格
		 * @param carts 订单商品
		 * @param titleTextView 标题状态的textview
		 * @param mOkBtn 支付按钮
		 * @param mCancelBtn 取消按钮
		 */
		public TaskCancelOrder(String customer_id, String code, String token, String orderPrice, ArrayList<Cart> carts,
				TextView titleTextView, Button mOkBtn, View mCancelBtn, LinearLayout layout1){
			this.customer_id = customer_id;
			this.code = code;
			this.token = token;
			this.orderPrice = orderPrice;
			this.carts = carts;
			this.titleTextView = titleTextView;
			this.mCancelBtn = mCancelBtn;
			this.mOkBtn = mOkBtn;
			this.layout1 = layout1;
			bar = new ProgressDialog(context);
			unicode = new UnicodeToString();
		}
		
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			bar.setMessage(context.getResources().getString(R.string.searching));
			bar.show();
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				CancelOrder cancelOrder = new CancelOrder();
				String httpResponse = cancelOrder.getHttpResponseString(customer_id, code, token);
				JSONObject jsonObject = new JSONObject(httpResponse);
				int responseCode = jsonObject.getInt("code");
				if(responseCode == 1){
					String response = jsonObject.getString("response");
					JSONObject responseObject = new JSONObject(response);
					String result = responseObject.getString("@p_result");
					if(unicode.revert(result).equals("success取消成功")){
						return true;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "cancel order task io ex");
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "cancel order task other ex");
				e.printStackTrace();
			}
			return false;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result){
				if(orderType == 0){//取消订单后支付按钮变成删除按钮，标题变成已取消状态， 取消按钮不可见
					setAfterCancelView(code, titleTextView, mOkBtn, mCancelBtn, layout1);
//					setAfterCancelView(code, orderPrice, carts, titleTextView, mOkBtn, mCancelBtn);
				} else if (orderType == 1){//待付款订单页remove掉该订单
					mLinearLayoutLayout.removeView(layout1);
				}
				Toast.makeText(context, context.getResources().getString(R.string.success_cancel_order), Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context, context.getResources().getString(R.string.fail_cancel_order), Toast.LENGTH_SHORT).show();
			}
			bar.dismiss();
		}
	}
	
	/**
	 * 取消订单后支付按钮变成删除按钮，标题变成已取消状态， 取消按钮不可见
	 * @param orderCode 订单号
	 * @param orderPrice 订单价格
	 * @param carts 订单商品
	 * @param titleTextView 标题状态的textview
	 * @param mOkBtn 支付按钮
	 * @param mCancelBtn 取消按钮
	 */
	private void setAfterCancelView(String orderCode,
//			String orderPrice, ArrayList<Cart> carts,
			TextView titleTextView, Button mOkBtn, View mCancelBtn,
			LinearLayout layout1){
		if(mOkBtn == null) return;
		mOkBtn.setOnClickListener(new OkClickListener(5, orderCode, myApplication.getUserId(), myApplication.getToken(), layout1));
    	mCancelBtn.setVisibility(View.GONE);
		mOkBtn.setText(context.getResources().getString(R.string.delete_produce));
		titleTextView.setText("已取消");
	}
	
	/**
	 * 取消运行的task
	 */
	public void cancelTask(){
		if(taskCancelOrder != null && taskCancelOrder.getStatus() == AsyncTask.Status.RUNNING){
			taskCancelOrder.cancel(true);
		}
		if(taskDelOrder != null)
			taskDelOrder.cancelTask();
	}
}

