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
	private TextView userName;// �û���
	private TextView phoneName;// �绰����
	private TextView address;// �ջ���ַ
	private TextView peiSong;// ���͵�ַ
	private CheckBox general;// ��ͨ����
	private CheckBox emsBox;// ems����
	private TextView generalPrice;// ��ͨ���ͼ۸�
	private TextView emsPrice;// ems���ͼ۸�
	private AddressDataManage addressDataManage;
	private ExpressDataManage expressDataManage;// ��������
	private CheckBox zhifubaoCheckBox;// ֧����
	private CheckBox zhifubaowangyeCheckBox;// ֧������ҳ֧��
	private CheckBox yinlianCheckBox;// ����֧��
	private CheckBox caifutongCheckBox;// �Ƹ�֧ͨ��
	private TextView sumPrice;// �ܵļ۸�
	private Handler mHandler;// ����handler����
	private MyApplication myApplication;

	// private Myreceiver receiver;
	// private Addresses addresses;

	/**
	 * ʵ�����ؼ�
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
			Toast.makeText(PayActivity.this, "���粻������", Toast.LENGTH_SHORT)
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
			// ����֮ǰ���ж��û��Ƿ��½
			if (!myApplication.getIsLogin()) {
				Toast.makeText(this, "�㻹δ��½�����ȵ�½", Toast.LENGTH_LONG).show();
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
			Toast.makeText(PayActivity.this, "���粻������", Toast.LENGTH_SHORT)
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
	 * UI�ؼ��Ķ���
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
			Toast.makeText(PayActivity.this, "���粻������", Toast.LENGTH_SHORT)
					.show();
		}
	}

	/**
	 * ��ʼ���ؼ�
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
			Toast.makeText(PayActivity.this, "���粻������", Toast.LENGTH_SHORT)
					.show();
		}
	}

}
