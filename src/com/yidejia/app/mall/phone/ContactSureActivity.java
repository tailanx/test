package com.yidejia.app.mall.phone;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.pay.AlicPayUtil;
import com.yidejia.app.mall.pay.UnionActivity;
import com.yidejia.app.mall.pay.WebPayActivity;
import com.yidejia.app.mall.recharge.ParseRecharge;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;

public class ContactSureActivity extends BaseActivity implements
		OnClickListener {

	private RelativeLayout zhifubao;// 支付宝
	private RelativeLayout wangyezhifubao;// 支付宝网页
	private RelativeLayout caifutong;// 财付通
	private RelativeLayout yinlian;// 银联
	private CheckBox cb_zhifubao;// 支付宝的选择框
	private CheckBox cb_wangye;// 支付宝网页的选择框
	private CheckBox cb_caifutong;// 财付通的选择框
	private CheckBox cb_yinlian;// 银联的选择框
	private TextView tvDetails; // 产品信息view
	private TextView tvPhone; // 充值号码view
	private TextView tvPrice; // 应付金额view
	private Button btnSaveOrder; // 提交订单按钮

	private String details = ""; // 产品信息
	private String phone = ""; // 充值号码
	private double price = -1; // 应付金额
	private String goodsId = ""; // 手机充值商品id
	private String amount = ""; // 充值金额

	private boolean isCanPay = false; // 是否能支付

	private String userId = "";
	private String token = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setActionbarConfig();
		setTitle(R.string.comfirm_order);

		userId = MyApplication.getInstance().getUserId();
		token = MyApplication.getInstance().getToken();

		Intent intent = getIntent();
		details = intent.getStringExtra("details");
		phone = intent.getStringExtra("phone");
		price = intent.getDoubleExtra("price", -1);
		goodsId = intent.getStringExtra("goodsId");
		amount = intent.getStringExtra("amount");

		if (TextUtils.isEmpty(details) || TextUtils.isEmpty(phone)
				|| price < 0.1) {
			isCanPay = false;
		} else {
			isCanPay = true;
		}

		setContentView(R.layout.contact_detail);
		initview();
	}

	/**
	 * 初始化数据
	 */
	private void initview() {

		btnSaveOrder = (Button) findViewById(R.id.btn_commit_phone_contace);
		if (isCanPay)
			btnSaveOrder.setSelected(true);
		else
			btnSaveOrder.setClickable(false);
		btnSaveOrder.setOnClickListener(this);

		zhifubao = (RelativeLayout) findViewById(R.id.go_pay_zhifubao_relative);
		wangyezhifubao = (RelativeLayout) findViewById(R.id.go_pay_zhifubao_wangyezhifu_relative);
		caifutong = (RelativeLayout) findViewById(R.id.go_pay_zhifubao_caifutong_relative);
		yinlian = (RelativeLayout) findViewById(R.id.go_pay_zhifubao_yinlian_relative);
		cb_zhifubao = (CheckBox) findViewById(R.id.zhifubao_checkbox);
		cb_wangye = (CheckBox) findViewById(R.id.zhufubaowangye_checkbox);
		cb_caifutong = (CheckBox) findViewById(R.id.caifutong_checkbox);
		cb_yinlian = (CheckBox) findViewById(R.id.yinlian_checkbox);

		cb_caifutong.setChecked(true);

		// 添加点击事件
		zhifubao.setOnClickListener(this);
		wangyezhifubao.setOnClickListener(this);
		caifutong.setOnClickListener(this);
		yinlian.setOnClickListener(this);

		tvDetails = (TextView) findViewById(R.id.tv_contact_name);
		tvPhone = (TextView) findViewById(R.id.tv_contact_number);
		tvPrice = (TextView) findViewById(R.id.tv_contact_sum);

		tvDetails.setText(details);
		tvPhone.setText(phone);
		tvPrice.setText(price + "");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.go_pay_zhifubao_relative:
			if (cb_zhifubao.isChecked()) {
				return;
			} else {
				cb_zhifubao.setChecked(true);
				cb_wangye.setChecked(false);
				cb_caifutong.setChecked(false);
				cb_yinlian.setChecked(false);

			}
			break;
		case R.id.go_pay_zhifubao_wangyezhifu_relative:
			if (cb_wangye.isChecked()) {
				return;
			} else {
				cb_zhifubao.setChecked(false);
				cb_wangye.setChecked(true);
				cb_caifutong.setChecked(false);
				cb_yinlian.setChecked(false);

			}
			break;
		case R.id.go_pay_zhifubao_caifutong_relative:
			if (cb_caifutong.isChecked()) {
				return;
			} else {
				cb_zhifubao.setChecked(false);
				cb_wangye.setChecked(false);
				cb_caifutong.setChecked(true);
				cb_yinlian.setChecked(false);

			}
			break;
		case R.id.go_pay_zhifubao_yinlian_relative:
			if (cb_yinlian.isChecked()) {
				return;
			} else {
				cb_zhifubao.setChecked(false);
				cb_wangye.setChecked(false);
				cb_caifutong.setChecked(false);
				cb_yinlian.setChecked(true);

			}

			break;
		case R.id.btn_commit_phone_contace:
			if (!isCanPay)
				return;
			// 提交订单
			saveOrder();
			break;
		}
	}

	/** 提交订单 **/
	private void saveOrder() {
		String param = new JNICallBack().getHttp4SaveCZOrder(userId, phone,
				amount, price + "", details, goodsId, token);
		String url = new JNICallBack().HTTPURL;
		Log.e("system.out", url + "?" + param);

		HttpClientUtil httpClientUtil = new HttpClientUtil();
		httpClientUtil.getHttpResp(url, param, new IHttpResp() {

			@Override
			public void success(String content) {
				Log.e("system.out", content);
				ParseRecharge parseRecharge = new ParseRecharge();
				if (parseRecharge.parseCZOrder(content)) {
					// 获取订单号
					String orderCode = parseRecharge.getCzOrderCode();
					Log.e("system.out", orderCode);
					if (TextUtils.isEmpty(orderCode))
						return;
					/** 根据用户选择的支付方式支付 **/
					switchPay(orderCode);
				}
			}
		});

	}

	/** 根据用户选择的支付方式支付 **/
	private void switchPay(String orderCode) {
		if (cb_caifutong.isChecked()) {
			tenWebPay(orderCode);
		} else if (cb_wangye.isChecked()) {
			aliWapPay(orderCode);
		} else if (cb_zhifubao.isChecked()) {
			AlicPayUtil util = new AlicPayUtil(this);
			util.getAlicPay(userId, token, orderCode, true);
		} else if (cb_yinlian.isChecked()) {
			getUnionTn(orderCode);
		}
	}

	/** 财付通支付 **/
	private void tenWebPay(String orderCode) {
		String payurl = "http://u.yidejia.com/index.php?m=ucenter&c=order&a=onlineWap&code="
				+ orderCode + "&type=tenpay&is_mobile=y";
		go2WebPay(getString(R.string.caifutong_pay), payurl);
	}

	/** 支付宝网页支付 **/
	private void aliWapPay(String orderCode) {
		String payurl = "http://u.yidejia.com/index.php?m=ucenter&c=order&a=onlineWap&code="
				+ orderCode + "&type=alipay&is_mobile=y";
		go2WebPay(getString(R.string.zhifubao_wangye_pay_list), payurl);
	}

	/** 跳转到网页支付 **/
	private void go2WebPay(String title, String payurl) {
		Intent webIntent = new Intent(this, WebPayActivity.class);
		webIntent.putExtra("title", title);
		webIntent.putExtra("payurl", payurl);
		startActivity(webIntent);
		finish();
	}

	/** 银联支付获取流水号 **/
	private void getUnionTn(final String orderCode) {
		String param = new JNICallBack().getHttp4GetTn(userId, orderCode,
				token, "y");
		String url = new JNICallBack().HTTPURL;

		HttpClientUtil httpClientUtil = new HttpClientUtil();
		httpClientUtil.getHttpResp(url, param, new IHttpResp() {

			@Override
			public void success(String content) {
				ParseRecharge parseRecharge = new ParseRecharge();
				if (parseRecharge.parseCZUnion(content)) {
					String tn = parseRecharge.getTn();
					if (TextUtils.isEmpty(tn))
						return;
					go2UnionPay(orderCode, tn);
				}
			}
		});
	}

	/** 跳转到银联支付 **/
	private void go2UnionPay(String orderCode, String tn) {
		if (TextUtils.isEmpty(orderCode))
			return;

		// 跳转到支付页面
		Intent userpayintent = new Intent(ContactSureActivity.this,
				UnionActivity.class);
		Bundle bundle = new Bundle();

		bundle.putInt("mode", 1);
		bundle.putString("code", orderCode);
		bundle.putString("uid", userId);
		bundle.putString("resp_code", "00");
		bundle.putString("tn", tn);
		userpayintent.putExtras(bundle);
		startActivity(userpayintent);
		finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "手机充值确认订单页面");
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "手机充值确认订单页面");
	}
}
