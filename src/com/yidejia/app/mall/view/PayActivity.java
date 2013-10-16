package com.yidejia.app.mall.view;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.AddressDataManage;
import com.yidejia.app.mall.datamanage.ExpressDataManage;
import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.Express;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.PayUtil;

public class PayActivity extends SherlockActivity {
	private TextView userName;// 用户名
	private TextView phoneName;// 电话号码
	private TextView address;// 收货地址
	private TextView peiSong;// 配送地址
	private CheckBox general;// 普通配送
	private CheckBox emsBox;// ems配送
	private TextView generalPrice;// 普通配送价格
	private TextView emsPrice;// ems配送价格
	private AddressDataManage addressDataManage;
	private ExpressDataManage expressDataManage;// 配送中心
	private CheckBox zhifubaoCheckBox;// 支付宝
	private CheckBox zhifubaowangyeCheckBox;// 支付宝网页支付
	private CheckBox yinlianCheckBox;// 银联支付
	private CheckBox caifutongCheckBox;// 财付通支付
	private TextView sumPrice;// 总的价格
	private Handler mHandler;// 创建handler对象
	private MyApplication myApplication;

	// private Myreceiver receiver;
	// private Addresses addresses;

	/**
	 * 实例化控件
	 */
	public void setupShow() {
		try {
			expressDataManage = new ExpressDataManage(this);
			addressDataManage = new AddressDataManage(this);
			sumPrice = (TextView) findViewById(R.id.go_pay_show_pay_money);
			userName = (TextView) findViewById(R.id.go_pay_name);
			phoneName = (TextView) findViewById(R.id.go_pay_number);
			address = (TextView) findViewById(R.id.go_pay_address);
			peiSong = (TextView) findViewById(R.id.go_pay_peisong);
			general = (CheckBox) findViewById(R.id.go_pay_check);
			emsBox = (CheckBox) findViewById(R.id.go_pay_ems);
			generalPrice = (TextView) findViewById(R.id.go_pay_general_price);
			emsPrice = (TextView) findViewById(R.id.go_pay_ems_price);

			zhifubaoCheckBox = (CheckBox) findViewById(R.id.zhifubao_checkbox);
			zhifubaowangyeCheckBox = (CheckBox) findViewById(R.id.zhufubaowangye_checkbox);
			yinlianCheckBox = (CheckBox) findViewById(R.id.yinlian_checkbox);
			caifutongCheckBox = (CheckBox) findViewById(R.id.caifutong_checkbox);

			general.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if (!isChecked) {
						emsBox.setChecked(true);
						;
					} else {
						emsBox.setChecked(false);
						Message ms = new Message();
						mHandler.sendEmptyMessage(Consts.GENERAL);
					}

				}
			});
			emsBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					// TODO Auto-generated method stub
					if (!isChecked) {
						general.setChecked(true);
						;

					} else {
						general.setChecked(false);
						mHandler.sendEmptyMessage(Consts.EMS);
					}
				}
			});

			zhifubaoCheckBox
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton arg0,
								boolean isChecked) {
							// TODO Auto-generated method stub
							if (isChecked) {
								zhifubaowangyeCheckBox.setChecked(false);
								yinlianCheckBox.setChecked(false);
								caifutongCheckBox.setChecked(false);
							}
						}
					});
			zhifubaowangyeCheckBox
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton arg0,
								boolean isChecked) {
							// TODO Auto-generated method stub
							if (isChecked) {
								zhifubaoCheckBox.setChecked(false);
								yinlianCheckBox.setChecked(false);
								caifutongCheckBox.setChecked(false);
							}
						}
					});

			yinlianCheckBox
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton arg0,
								boolean isChecked) {
							// TODO Auto-generated method stub
							if (isChecked) {
								zhifubaoCheckBox.setChecked(false);
								zhifubaowangyeCheckBox.setChecked(false);
								caifutongCheckBox.setChecked(false);
							}
						}
					});
			caifutongCheckBox
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton arg0,
								boolean isChecked) {
							// TODO Auto-generated method stub
							if (isChecked) {
								zhifubaoCheckBox.setChecked(false);
								zhifubaowangyeCheckBox.setChecked(false);
								yinlianCheckBox.setChecked(false);
							}
						}
					});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(PayActivity.this, "网络不给力！", Toast.LENGTH_SHORT)
					.show();
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			// TODO Auto-generated method stub
			myApplication = (MyApplication) getApplication();
			// receiver = new Myreceiver();
			// IntentFilter filter = new IntentFilter();
			// filter.addAction(Consts.BUY_NEW);
			// registerReceiver(receiver, filter);
			// 付款之前先判断用户是否登陆
			if (!myApplication.getIsLogin()) {
				Toast.makeText(this, "你还未登陆，请先登陆", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
				this.finish();
			} else {
				Intent intent = getIntent();
				final String sum = intent.getStringExtra("price");
				Cart cart = (Cart) intent.getSerializableExtra("Cart");
				if (cart == null) {

					setActionbar();
					setContentView(R.layout.go_pay);
					LinearLayout layout = (LinearLayout) findViewById(R.id.go_pay_relative2);
					PayUtil pay = new PayUtil(PayActivity.this, layout);
					pay.loadView();
					setupShow();
					addAddress();

					if (Double.parseDouble(sum) > 299.0) {
						sumPrice.setText(sum);
					} else {
						sumPrice.setText(Double.parseDouble(sum)
								+ Double.parseDouble((general.isChecked() ? generalPrice
										.getText().toString() : emsPrice
										.getText().toString())) + "");
					}
					mHandler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
							case Consts.GENERAL:
								sumPrice.setText(Double.parseDouble(sum)
										+ Double.parseDouble(generalPrice
												.getText().toString()) + "");
								break;
							case Consts.EMS:
								sumPrice.setText(Double.parseDouble(sum)
										+ Double.parseDouble(emsPrice.getText()
												.toString()) + "");
								break;
							}
							super.handleMessage(msg);
						}
					};
				} else {
					setActionbar();
					setContentView(R.layout.go_pay);
					LinearLayout layout = (LinearLayout) findViewById(R.id.go_pay_relative2);
					PayUtil pay = new PayUtil(PayActivity.this, layout, cart);
					pay.cartLoadView();
					setupShow();
					addAddress();

					if (Double.parseDouble(sum) > 299.0) {
						sumPrice.setText(sum);
					} else {
						sumPrice.setText(Double.parseDouble(sum)
								+ Double.parseDouble((general.isChecked() ? generalPrice
										.getText().toString() : emsPrice
										.getText().toString())) + "");
					}
					mHandler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
							case Consts.GENERAL:
								sumPrice.setText(Double.parseDouble(sum)
										+ Double.parseDouble(generalPrice
												.getText().toString()) + "");
								break;
							case Consts.EMS:
								sumPrice.setText(Double.parseDouble(sum)
										+ Double.parseDouble(emsPrice.getText()
												.toString()) + "");
								break;
							}
							super.handleMessage(msg);
						}
					};
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(PayActivity.this, "网络不给力！", Toast.LENGTH_SHORT)
					.show();
		}

	}

	// @Override
	// protected void onDestroy() {
	// // TODO Auto-generated method stub
	// super.onDestroy();
	// // unregisterReceiver(receiver);
	// }
	/**
	 * UI控件的顶部
	 */
	private void setActionbar() {
		try {
			getSupportActionBar().setDisplayHomeAsUpEnabled(false);
			getSupportActionBar().setDisplayShowHomeEnabled(false);
			getSupportActionBar().setDisplayShowTitleEnabled(false);
			getSupportActionBar().setDisplayUseLogoEnabled(false);
			getSupportActionBar().setDisplayShowCustomEnabled(true);
			getSupportActionBar().setCustomView(R.layout.actionbar_compose);
			ImageView button = (ImageView) findViewById(R.id.compose_back);
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					PayActivity.this.finish();
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(PayActivity.this, "网络不给力！", Toast.LENGTH_SHORT)
					.show();
		}
	}

	/**
	 * 初始化控件
	 */
	private void addAddress() {
		try {
			ArrayList<Addresses> mList = addressDataManage.getAddressesArray(

					myApplication.getUserId(), 0, 10);
			Log.i("info", mList.size() + " mlist");
			if (mList.size() == 0) {
				Intent intent = new Intent(PayActivity.this,
						NewAddressActivity.class);
				PayActivity.this.startActivity(intent);
			} else {
				Addresses addresses = mList.remove(0);


				userName.setText(addresses.getName());
				phoneName.setText(addresses.getPhone());
				StringBuffer sb = new StringBuffer();
				sb.append(addresses.getProvice());
				sb.append(addresses.getCity());
				sb.append(addresses.getArea());
				sb.append(addresses.getAddress());
				address.setText(sb.toString());

				Express express = expressDataManage.getExpressesExpenses(
						addresses.getProvice(), "n").get(0);
				peiSong.setText(expressDataManage
						.getDistributionsList(0 + "", 10 + "").get(0)
						.getDisName());
				generalPrice.setText(express.getExpress());
				emsPrice.setText(express.getEms());
				Log.i("info", addresses.getName());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(PayActivity.this, "网络不给力！", Toast.LENGTH_SHORT)
					.show();
		}
	}

}
