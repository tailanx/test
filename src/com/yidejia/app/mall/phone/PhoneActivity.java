package com.yidejia.app.mall.phone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.util.Consts;

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
	private TextView tvRealPrice;
	private String numberContact;

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

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.iv_bottom_contact:// 点击获取通讯录
			intent.setClass(PhoneActivity.this, PhoneContactActivity.class);
			PhoneActivity.this.startActivityForResult(intent,
					Consts.CONSTACT_REQUEST);
			break;

		case R.id.iv_commit_phone_contace:
			intent.setClass(this, ContactSureActivity.class);
			startActivity(intent);
			break;
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		Log.e("info", arg0 + "number");
		Log.e("info", arg1 + "number");
		if (arg0 == Consts.CONSTACT_REQUEST && arg1 == Consts.CONSTACT_RESPONSE) {
			Bundle bundle = arg2.getExtras();
			numberContact = bundle.getString("number");
			Log.e("info", numberContact + "number");
			if (numberContact != null) {
				etPhoneNumber.setText(numberContact);
			}
		}
	}
}
