package com.yidejia.app.mall.phone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.R;

public class ContactSureActivity extends BaseActivity implements OnClickListener {

	private RelativeLayout zhifubao;// 支付宝
	private RelativeLayout wangyezhifubao;// 支付宝网页
	private RelativeLayout caifutong;// 财付通
	private RelativeLayout yinlian;// 银联
	private CheckBox cb_zhifubao;// 支付宝的选择框
	private CheckBox cb_wangye;// 支付宝网页的选择框
	private CheckBox cb_caifutong;// 财付通的选择框
	private CheckBox cb_yinlian;// 银联的选择框
	private TextView tvDetails;	//产品信息view
	private TextView tvPhone;	//充值号码view
	private TextView tvPrice;	//应付金额view
	
	private String details;	//产品信息
	private String phone;	//充值号码
	private String price;	//应付金额

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setActionbarConfig();
		setTitle(R.string.comfirm_order);
		
		Intent intent = getIntent();
		details = intent.getStringExtra("details");
		phone = intent.getStringExtra("phone");
		price = intent.getStringExtra("price");
		
		setContentView(R.layout.contact_detail);
		initview();
	}

	/**
	 * 初始化数据
	 */
	private void initview() {
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
		tvPrice.setText(price);
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
		}
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
