package com.yidejia.app.mall.view;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.AddressDataManage;
import com.yidejia.app.mall.model.Addresses;

public class OrderDetailActivity extends SherlockFragmentActivity {
	private TextView nameTextView;//收件人姓名
	private TextView phoneTextView;//收件人电话
	private TextView detailTextView;//收件人地址
	private LinearLayout layout;//订单内容
	private TextView priceTextView;//订单物品价格
	private TextView emsTextView;//快递费
	private TextView sumTextView;//总共的价格
	private Button payButton;//立即付款
	private TextView orderNumber;//订单的编号
	private TextView orderTime;//下单时间
	private AddressDataManage addressDataManage ;//地址管理
	private MyApplication myApplication;
	private String orderCode;//传递过来的订单号
	private String orderPrice;//传递过来的价格总数
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setActionBar();
		setContentView(R.layout.order_detail);
		addressDataManage = new AddressDataManage(OrderDetailActivity.this);
		myApplication = (MyApplication) getApplication();
		Intent intent = getIntent();
		 orderCode = intent.getExtras().getString("OrderCode");
		 orderPrice =  intent.getExtras().getString("OrderPrice");
		Log.i("info", orderCode +"   orderCode");
		nameTextView = (TextView) findViewById(R.id.order_detail_name);
		phoneTextView = (TextView) findViewById(R.id.order_detail_number);
		detailTextView = (TextView) findViewById(R.id.order_detail_position);
		priceTextView = (TextView) findViewById(R.id.order_detail_dingdan_sum);
		emsTextView = (TextView) findViewById(R.id.order_detail_yunhui_sum);
		sumTextView = (TextView) findViewById(R.id.go_pay_show_pay_money);
		payButton = (Button) findViewById(R.id.order_detail_pay);
		orderNumber = (TextView) findViewById(R.id.order_detail_biaohao_number);
		orderTime =  (TextView) findViewById(R.id.order_detail_time_number);
		
		layout = (LinearLayout) findViewById(R.id.order_detail_relative2);
		View view = getLayoutInflater().inflate(R.layout.order_detail_item,
				null);
		layout.addView(view);
		setupShow();
	}

	private void setActionBar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setCustomView(R.layout.actionbar_compose);
		ImageView back = (ImageView) findViewById(R.id.compose_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				OrderDetailActivity.this.finish();
			}
		});
		getSupportActionBar().setIcon(R.drawable.back);
		TextView title = (TextView) findViewById(R.id.compose_title);
		title.setText(getResources().getString(R.string.order_detail));
	}
	int fromIndex = 0;
	int acount = 10;
	StringBuffer sb = new StringBuffer();
	private void setupShow(){
		ArrayList<Addresses> addresses = addressDataManage.getAddressesArray(myApplication.getUserId(), fromIndex, acount);
		Addresses address = addresses.get(0);
		nameTextView.setText(address.getName());
		phoneTextView.setText(address.getHandset());
		sb.append(address.getProvice());
		sb.append(address.getCity());
		sb.append(address.getArea());
		sb.append(address.getAddress());
		detailTextView.setText(sb.toString());
		priceTextView.setText(orderPrice);
		emsTextView.setText(10+"元");
		orderNumber.setText(orderCode);
		orderTime.setText("2013_12_01");
		sumTextView.setText(orderPrice+10);
	}
}
