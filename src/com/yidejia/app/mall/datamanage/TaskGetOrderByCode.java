package com.yidejia.app.mall.datamanage;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
//import android.content.activity;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.Order;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.ImageUrl;
import com.yidejia.app.mall.net.address.GetUserAddressList;
import com.yidejia.app.mall.net.commments.WaitingComment;
import com.yidejia.app.mall.net.goodsinfo.GetProductAddress;
import com.yidejia.app.mall.net.order.GetOrderByCode;
import com.yidejia.app.mall.view.PersonEvaluationActivity;

public class TaskGetOrderByCode {
	
//	private int fromIndex = 0;
//	private int amount = 10;
	
	private Activity activity;
//	private View view;
//	private LinearLayout layout;
//	private LayoutInflater inflater;
	private TaskGOBC taskGobc;
	private Order order;//
	private String recipient_id;
	
	
	public TaskGetOrderByCode(Activity activity){//, LinearLayout layout
		this.activity = activity;
		order = new Order();
//		this.view = view;
//		this.layout = layout;
//		this.inflater = LayoutInflater.from(activity);
		orderTime = (TextView) activity.findViewById(R.id.order_detail_time_number);
		emsTextView = (TextView) activity.findViewById(R.id.order_detail_yunhui_sum);
		nameTextView = (TextView) activity.findViewById(R.id.order_detail_name);
		phoneTextView = (TextView) activity.findViewById(R.id.order_detail_number);
		detailTextView = (TextView) activity.findViewById(R.id.order_detail_position);
		priceTextView = (TextView) activity.findViewById(R.id.order_detail_dingdan_sum);
		emsTextView = (TextView) activity.findViewById(R.id.order_detail_yunhui_sum);
		sumTextView = (TextView) activity.findViewById(R.id.go_pay_show_pay_money);
		payButton = (Button) activity.findViewById(R.id.order_detail_pay);
		orderNumber = (TextView) activity.findViewById(R.id.order_detail_biaohao_number);
		orderTime =  (TextView) activity.findViewById(R.id.order_detail_time_number);
	}
	
	public void getOderBC(String code, String price){//, boolean isFirstIn
		if(!ConnectionDetector.isConnectingToInternet(activity)) {
			Toast.makeText(activity, activity.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return;
		}
		this.orderCode = code;
		this.orderPrice = price;
		
		if (taskGobc != null
				&& taskGobc.getStatus() == AsyncTask.Status.RUNNING) {
			taskGobc.cancel(true); // 如果Task还在运行，则先取消它
		}
		
		taskGobc = new TaskGOBC(code);
		taskGobc.execute();
	}
	

	private class TaskGOBC extends AsyncTask<Void, Void, Boolean> {

		private String code;
//		private boolean isFirstIn;
		private ProgressDialog bar;

		public TaskGOBC(String code) {//, boolean isFirstIn
			this.code = code;
//			this.isFirstIn = isFirstIn;
//			if(isFirstIn)
				bar = new ProgressDialog(activity);
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			if (isFirstIn) {
				bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				bar.setMessage(activity.getResources().getString(
						R.string.searching));
				bar.show();
//			}
		}



		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if(isCancelled()) return null;
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			GetOrderByCode gobc = new GetOrderByCode();
			try {
				String httpResp = gobc.getHttpResponse(code);
				try {
					JSONObject httpJsonObject = new JSONObject(httpResp);
					int respCode = httpJsonObject.getInt("code");
					if(respCode == 1){
						String respString = httpJsonObject.getString("response");
						JSONObject respObject = new JSONObject(respString);
						order.setId(respObject.getString("order_id"));
						order.setOrderCode(respObject.getString("order_code"));
						order.setDate(respObject.getString("the_date"));
						order.setStatus(respObject.getString("status"));
						order.setCore(respObject.getString("goods_ascore"));
						order.setShipFee(respObject.getString("ship_fee"));
						recipient_id = (respObject.getString("recipient_id"));
//						goods_acash
						return true;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//			if(isFirstIn)
				
			if(result){
				
				setupShow();
			}
			bar.dismiss();
		}

		
		
	}
	
	private int fromIndex = 0;
	private int acount = 10;
//	private StringBuffer sb = new StringBuffer();
	private MyApplication myApplication;
	private TextView emsTextView;//快递费
	private TextView sumTextView;//总共的价格
	private TextView orderTime;//下单时间
	private TextView nameTextView;//收件人姓名
	private TextView phoneTextView;//收件人电话
	private TextView detailTextView;//收件人地址
	private TextView priceTextView;//订单物品价格
	private Button payButton;//立即付款
	private TextView orderNumber;//订单的编号
	private String orderCode;//传递过来的订单号
	private String orderPrice;//传递过来的价格总数
	private ArrayList<Addresses> addresses ;
	private TaskGetUserAddress taskGetUserAddress;
	
	private void setupShow(){
		
		myApplication = (MyApplication) activity.getApplication();
		addresses  = new ArrayList<Addresses>();
		
		priceTextView.setText(orderPrice);
		String expressNum = order.getShipFee();
		orderNumber.setText(orderCode);
		emsTextView.setText(expressNum+activity.getResources().getString(R.string.unit));
		orderTime.setText(order.getDate());
		try {
			
			float odprice = Float.parseFloat(orderPrice);
			float exprice = Float.parseFloat(expressNum);
			
			sumTextView.setText((odprice + exprice) + activity.getResources().getString(R.string.unit));
		} catch (NumberFormatException e) {
			// TODO: handle exception
			sumTextView.setText(R.string.price_error);
		}
		
		if(!"".equals(recipient_id )&& null != recipient_id){
			
		}
		
		if (taskGetUserAddress != null
				&& taskGetUserAddress.getStatus() == AsyncTask.Status.RUNNING) {
			taskGetUserAddress.cancel(true); // 如果Task还在运行，则先取消它
		}
		taskGetUserAddress = new TaskGetUserAddress();
		
		taskGetUserAddress.execute();
		
//		if(addresses.isEmpty())return;
//		Addresses address = addresses.get(0);
//		nameTextView.setText(address.getName());
//		phoneTextView.setText(address.getHandset());
//		sb.append(address.getProvice());
//		sb.append(address.getCity());
//		sb.append(address.getArea());
//		sb.append(address.getAddress());
//		detailTextView.setText(sb.toString());
	}
	
	private class TaskGetUserAddress extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if(isCancelled()) return null;
			GetUserAddressList addressList = new GetUserAddressList(activity);
			try {
				String httpresp = addressList.getAddressListJsonString("recipient_id=" + recipient_id, "0", "10", "", "", "");
				AddressDataManage manage = new AddressDataManage(activity);
				addresses = manage.analysisGetListJson(httpresp);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result){
				if(addresses.isEmpty()){
					Log.i(TaskGetOrderByCode.class.getName(), "address is null");
					return;
				}
				StringBuffer sb = new StringBuffer();
				Addresses address = addresses.get(0);
				nameTextView.setText(address.getName());
				phoneTextView.setText(address.getHandset());
				sb.append(address.getProvice());
				sb.append(address.getCity());
				sb.append(address.getArea());
				sb.append(address.getAddress());
				detailTextView.setText(sb.toString());
			}
		}
		
	}
}
