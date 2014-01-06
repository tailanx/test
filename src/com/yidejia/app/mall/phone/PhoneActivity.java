package com.yidejia.app.mall.phone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.yidejia.app.mall.R;

/**
 * 用来展示充值界面的
 * 
 * @author Administrator
 * 
 */
public class PhoneActivity extends SherlockFragmentActivity implements
		OnClickListener {
	private EditText etPhoneNumber;
	private ImageView ivPhoneContact;
	private Button bt30, bt50, bt100, btCommito;
	private TextView tvRealPrice, tvAddress;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.phone_contact);
		initview();
		ivPhoneContact.setOnClickListener(this);
		btCommito.setOnClickListener(this);
	}

	/**
	 * 实例化组件
	 */
	private void initview() {
		etPhoneNumber = (EditText) findViewById(R.id.et_inputnumber);
		ivPhoneContact = (ImageView) findViewById(R.id.iv_bottom_contact);
		bt30 = (Button) findViewById(R.id.bt_price_30);
		bt50 = (Button) findViewById(R.id.bt_price_50);
		bt100 = (Button) findViewById(R.id.bt_price_100);
		btCommito = (Button) findViewById(R.id.iv_commit_phone_contace);
		tvRealPrice = (TextView) findViewById(R.id.tv_price_contact);
		tvAddress = (TextView) findViewById(R.id.tv_address_contact);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent  = new Intent(); 
		switch (v.getId()) {
		case R.id.iv_bottom_contact:// 点击获取通讯录
			intent.setClass(PhoneActivity.this, PhoneContactActivity.class);
			startActivity(intent);
			break;

		case R.id.iv_commit_phone_contace:
			intent.setClass(this, ContactSureActivity.class);
			startActivity(intent);
			break;
		}
	}
}
