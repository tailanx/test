package com.yidejia.app.mall.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.order.OrderDetailActivity;
import com.yidejia.app.mall.util.Consts;

/**
 * 更改支付方式
 * 
 * @author Administrator
 * 
 */
public class ChangePayActivity extends BaseActivity implements OnClickListener {
	private RelativeLayout zhifubao;// 支付宝
	private RelativeLayout wangye;// 支付宝网页支付
	private RelativeLayout caifutong;// 财付通
	private RelativeLayout yinlian;// 银联
	private CheckBox cb_zhifubao;
	private CheckBox cb_wangye;
	private CheckBox cb_caifutong;
	private CheckBox cb_yinlian;
	private int a;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setActionbarConfig();
		setTitle(getResources().getString(R.string.change_pay_for));
		setContentView(R.layout.change_pay);

		initview();
		a = (int) getIntent().getExtras().getInt(Consts.CHANGE_PAY);
		if (a == -1) {
			return;
		} else {
			switch (a) {
			case Consts.CHANGE_ZHIFUBAO://支付宝选中
				cb_zhifubao.setChecked(true);
				cb_caifutong.setChecked(false);
				break;
			case Consts.CHANGE_WANGYE://支付宝网页选中
				cb_caifutong.setChecked(false);
				cb_wangye.setChecked(true);
				break;
			case Consts.CHANGE_CAIFUTONG://财付通选中
				cb_caifutong.setChecked(true);
				break;
			case Consts.CHANGE_YINLIAN://银联选中
				cb_caifutong.setChecked(false);
				cb_yinlian.setChecked(true);
				break;

			}
		}

	}

	/**
	 * 初始化数据
	 */
	private void initview() {
		zhifubao = (RelativeLayout) findViewById(R.id.go_pay_zhifubao_relative);
		wangye = (RelativeLayout) findViewById(R.id.go_pay_zhifubao_wangyezhifu_relative);
		caifutong = (RelativeLayout) findViewById(R.id.go_pay_zhifubao_caifutong_relative);
		yinlian = (RelativeLayout) findViewById(R.id.go_pay_zhifubao_yinlian_relative);

		cb_zhifubao = (CheckBox) findViewById(R.id.zhifubao_checkbox);
		cb_wangye = (CheckBox) findViewById(R.id.zhufubaowangye_checkbox);
		cb_caifutong = (CheckBox) findViewById(R.id.caifutong_checkbox);
		cb_yinlian = (CheckBox) findViewById(R.id.yinlian_checkbox);

		zhifubao.setOnClickListener(this);
		wangye.setOnClickListener(this);
		caifutong.setOnClickListener(this);
		yinlian.setOnClickListener(this);

	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this,
				getResources().getString(R.string.change_pay_for));
	}

	@Override
	protected void onPause() {
		super.onPause();
		// StatService.onPause(this);
		StatService.onPageEnd(this,
				getResources().getString(R.string.change_pay_for));
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, OrderDetailActivity.class);
		Bundle bundle = new Bundle();
		switch (v.getId()) {
		case R.id.go_pay_zhifubao_relative:
			cb_zhifubao.setChecked(true);
			cb_caifutong.setChecked(false);
			bundle.putInt(Consts.CHANGE_PAY, Consts.CHANGE_ZHIFUBAO);
			break;

		case R.id.go_pay_zhifubao_wangyezhifu_relative:
			cb_wangye.setChecked(true);
			cb_caifutong.setChecked(false);
			bundle.putInt(Consts.CHANGE_PAY, Consts.CHANGE_WANGYE);
			break;
		case R.id.go_pay_zhifubao_caifutong_relative:
			bundle.putInt(Consts.CHANGE_PAY, Consts.CHANGE_CAIFUTONG);
			break;
		case R.id.go_pay_zhifubao_yinlian_relative:
			cb_yinlian.setChecked(true);
			cb_caifutong.setChecked(false);
			bundle.putInt(Consts.CHANGE_PAY, Consts.CHANGE_YINLIAN);
			break;
		}
		intent.putExtras(bundle);
		this.setResult(Consts.CHANGE_RESPONSE, intent);
		this.finish();
	}

}
