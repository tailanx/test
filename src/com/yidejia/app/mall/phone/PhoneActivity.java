package com.yidejia.app.mall.phone;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.IsPhone;

/**
 * 用来展示充值界面的
 * 
 * @author LiuYong
 * 
 */
public class PhoneActivity extends BaseActivity implements OnClickListener {
	private EditText etPhoneNumber;
	private ImageView ivPhoneContact;
	private Button bt30, bt50, bt100, btCommito;
	private TextView tvRealPrice;
	private String numberContact;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setActionbarConfig();
		setTitle(R.string.main_message_center_text);

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

		bt100.setOnClickListener(this);// 30元点击事件
		bt30.setOnClickListener(this);// 50元点击事件
		bt50.setOnClickListener(this);// 100元点击事件
		bt30.setSelected(true);

		btCommito = (Button) findViewById(R.id.iv_commit_phone_contace);
		tvRealPrice = (TextView) findViewById(R.id.tv_price_contact);

	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.iv_bottom_contact:// 点击获取通讯录
			intent = new Intent(Intent.ACTION_PICK);
			intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
			startActivityForResult(intent, Consts.CONSTACT_REQUEST);
			break;

		case R.id.iv_commit_phone_contace:
			String phoneNumber = etPhoneNumber.getText().toString().trim().replace(" ", "");
			
			Log.e("info", phoneNumber);
			//判断是不是手机号码，和非空判断
			if (null == phoneNumber || "".equals(phoneNumber)||!IsPhone.isMobileNO(phoneNumber)) {
				Toast.makeText(this,
						getResources().getString(R.string.no_phone_number),
						Toast.LENGTH_SHORT).show();
				return;
			} else {
				intent = new Intent();
				intent.setClass(this, ContactSureActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.bt_price_30:
			bt30.setSelected(true);
			bt50.setSelected(false);
			bt100.setSelected(false);
			break;
		case R.id.bt_price_50:
			bt30.setSelected(false);
			bt50.setSelected(true);
			bt100.setSelected(false);
			break;
		case R.id.bt_price_100:
			bt30.setSelected(false);
			bt50.setSelected(false);
			bt100.setSelected(true);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Consts.CONSTACT_REQUEST
				&& resultCode == Activity.RESULT_OK) {
			Uri contactData = data.getData();
			getContactNum(contactData);
		}
	}

	/**
	 * 获取选中联系人的号码并显示到界面
	 * 
	 * @param contactData
	 */
	@SuppressWarnings("deprecation")
	private void getContactNum(Uri contactData) {
		ContentResolver reContentResolverol = getContentResolver();
		Cursor cursor = managedQuery(contactData, null, null, null, null);
		cursor.moveToFirst();
		String contactId = cursor.getString(cursor
				.getColumnIndex(ContactsContract.Contacts._ID));
		Cursor phone = reContentResolverol.query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
						+ contactId, null, null);
		while (phone.moveToNext()) {
			numberContact = phone
					.getString(phone
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			if (numberContact != null) {
				etPhoneNumber.setText(numberContact);
			}
		}
	}
}
