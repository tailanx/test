package com.yidejia.app.mall.phone;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.recharge.ParseRecharge;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;
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
	private Button bt30, bt50, bt100, btCommit;
	private RelativeLayout rlPhonePrice;
	private TextView tvRealPrice;
	private String numberContact;

	private String cardid; // 卡类id
	private double inprice; // 所需面额
	private String game_area; // 运营商
	private String details; // 商品信息

	private boolean isCanClick = false;

	private ParseRecharge parseRecharge;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setActionbarConfig();
		setTitle(R.string.main_message_center_text);

		parseRecharge = new ParseRecharge();

		setContentView(R.layout.phone_contact);

		initview();
		ivPhoneContact.setOnClickListener(this);
		btCommit.setOnClickListener(this);

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

		rlPhonePrice = (RelativeLayout) findViewById(R.id.rl_phone_price);

		bt100.setOnClickListener(this);// 30元点击事件
		bt30.setOnClickListener(this);// 50元点击事件
		bt50.setOnClickListener(this);// 100元点击事件
		bt100.setSelected(true);

		btCommit = (Button) findViewById(R.id.iv_commit_phone_contace);
		tvRealPrice = (TextView) findViewById(R.id.tv_price_contact);

		TelephonyManager phoneMgr = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		etPhoneNumber.setText(phoneMgr.getLine1Number());
		
		etPhoneNumber.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				showPrice();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		
		showPrice();
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
			String phoneNumber = etPhoneNumber.getText().toString().trim()
					.replace(" ", "");
			if (phoneNumber.length() == 14) {
				phoneNumber = phoneNumber.substring(3, phoneNumber.length());
			}
			// Log.e("info", phoneNumber);
			// 判断是不是手机号码，和非空判断
			if (null == phoneNumber || "".equals(phoneNumber)
					|| !IsPhone.isMobileNO(phoneNumber)) {
				Toast.makeText(this,
						getResources().getString(R.string.no_phone_number),
						Toast.LENGTH_SHORT).show();
				return;
			} else {
				if(!isCanClick) return;
				
				intent = new Intent();
				intent.setClass(this, ContactSureActivity.class);
				intent.putExtra("details", details);
				intent.putExtra("price", inprice);
				intent.putExtra("goodsId", cardid);
				intent.putExtra("amount", getAmount());
				intent.putExtra("phone", phoneNumber);
				startActivity(intent);
			}
			break;
		case R.id.bt_price_30:
			bt30.setSelected(true);
			bt50.setSelected(false);
			bt100.setSelected(false);
			showPrice();
			break;
		case R.id.bt_price_50:
			bt30.setSelected(false);
			bt50.setSelected(true);
			bt100.setSelected(false);
			showPrice();
			break;
		case R.id.bt_price_100:
			bt30.setSelected(false);
			bt50.setSelected(false);
			bt100.setSelected(true);
			showPrice();
			break;
		}
	}

	private void showPrice() {
		isCanClick = false;
		rlPhonePrice.setVisibility(View.INVISIBLE);
		String handset = etPhoneNumber.getText().toString().trim();
		handset = handset.replace(" ", "");
		String amount = getAmount();
		if (IsPhone.isMobileNO(handset))
			getSimData(handset, amount);
	}

	private String getAmount() {
		return (bt30.isSelected()) ? "30" : (bt50.isSelected() ? "50" : "100");
	}

	/**
	 * 获取手机号码handset的sim情况和充值面值amount所需的价格
	 * 
	 * @param handset
	 *            手机号码
	 * @param amount
	 *            面值
	 */
	private void getSimData(String handset, String amount) {
		if (handset.length() == 14) {
			handset = handset.substring(3, handset.length());
		}
		// String url =
		// "http://u.yidejia.com/index.php?m=of&c=index&a=telquery&handset="+handset
		// + "&amount=" + amount;
		rlPhonePrice.setVisibility(View.INVISIBLE);
		isCanClick = false;
		
		String url = parseRecharge.getNeedPayUrl(handset, amount);
		HttpClientUtil httpClientUtil = new HttpClientUtil(this);
		httpClientUtil.setIsShowLoading(true);
		httpClientUtil.setShowErrMessage(true);
		httpClientUtil.getHttpResp(url, new IHttpResp() {

			@Override
			public void onSuccess(String content) {
				if (parseRecharge.parseNeedPay(content)) {
					inprice = parseRecharge.getInPrice();
					cardid = parseRecharge.getCardid();
					game_area = parseRecharge.getGame_area();
					details = parseRecharge.getCardname();
					rlPhonePrice.setVisibility(View.VISIBLE);
					isCanClick = true;
					tvRealPrice.setText(game_area + inprice);
				}
			}
		});
	}

	// /**
	// * 解析Sim信息数据
	// * @param content
	// */
	// private boolean parseSim(String content){
	// try {
	// JSONObject simObject = new JSONObject(content);
	// if(!"1".equals(simObject.opt("retcode"))){
	// Toast.makeText(this, simObject.optString("err_msg"),
	// Toast.LENGTH_SHORT).show();
	// } else {
	// cardid = simObject.optString("cardid");
	// inprice = simObject.optString("inprice");
	// game_area = simObject.optString("game_area");
	// details = simObject.optString("cardname");
	// return true;
	// }
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// return false;
	// }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Consts.CONSTACT_REQUEST
				&& resultCode == Activity.RESULT_OK) {
			Uri contactData = data.getData();
			getContactNum(contactData);
			showPrice();
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

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "手机充值页面");
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "手机充值页面");
	}
}
