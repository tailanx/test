package com.yidejia.app.mall.ctrl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.RetOrderInfo;
import com.yidejia.app.mall.view.ReturnActivity;

public class RetViewCtrl {

	private Activity activity;
	private TextView statusTextView;
	private TextView codeTextView;
	private TextView dateTextView;
	private RetOrderInfo info;
	private LinearLayout linearLayout;

	public RetViewCtrl(Activity activity, RetOrderInfo info) {
		this.activity = activity;
		this.info = info;
	}

	/**
	 * 返回一个退换货页面
	 * 
	 * @return
	 */
	public View getRetView() {
		View view = activity.getLayoutInflater().inflate(
				R.layout.return_exchange1_item, null);

		findIds(view);

		return view;
	}

	private void findIds(View view) {
		statusTextView = (TextView) view.findViewById(R.id.ret_order_status);
		codeTextView = (TextView) view.findViewById(R.id.ret_order_code);
		dateTextView = (TextView) view.findViewById(R.id.ret_the_date);

	}

	/**
	 * 设置退换货页面详细信息
	 * 
	 * @param status
	 * @param code
	 * @param date
	 */
	public void setText(String status, String code, String date) {
		statusTextView.setText(status);
		codeTextView.setText(code);
		dateTextView.setText(date);
	}

}
