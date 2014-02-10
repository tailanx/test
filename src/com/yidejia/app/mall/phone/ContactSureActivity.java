package com.yidejia.app.mall.phone;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.yidejia.app.mall.R;

public class ContactSureActivity extends Activity implements OnClickListener {

	private RelativeLayout zhifubao;// 支付宝
	private RelativeLayout wangyezhifubao;// 支付宝网页
	private RelativeLayout caifutong;// 财付通
	private RelativeLayout yinlian;// 银联
	private CheckBox cb_zhifubao;// 支付宝的选择框
	private CheckBox cb_wangye;// 支付宝网页的选择框
	private CheckBox cb_caifutong;// 财付通的选择框
	private CheckBox cb_yinlian;// 银联的选择框

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_detail);
		initview();
		cb_zhifubao.setChecked(true);
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

		// 添加点击事件
		zhifubao.setOnClickListener(this);
		wangyezhifubao.setOnClickListener(this);
		caifutong.setOnClickListener(this);
		yinlian.setOnClickListener(this);
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
}
