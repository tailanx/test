package com.yidejia.app.mall.order;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.R.id;
import com.yidejia.app.mall.R.layout;
import com.yidejia.app.mall.R.string;
import com.yidejia.app.mall.datamanage.TaskReturn;

/**
 * 退换货详情
 * 
 * @author LongBin
 * 
 */
public class ReturnActivity extends BaseActivity {

	private String order_code;
	private String the_date;

	private TextView orderCodeTextView;
	private TextView orderDateTextView;

	private TaskReturn taskReturn;
	private RetOrderInfo info;
	private Button submit;
	private EditText linkMan;
	private EditText phoneNumber;
	private Spinner reason;
	private EditText describe;
	private TextView exchange_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionbarConfig();
		setTitle(getResources().getString(R.string.return_order_detail));
		setContentView(R.layout.exchange);
		TextView submit = (TextView) findViewById(R.id.ab_common_tv_right);
		submit.setText(getResources().getString(R.string.commit));
		submit.setVisibility(View.GONE);
		submit.setOnClickListener(listener);

		Bundle bundle = getIntent().getExtras();

		info = (RetOrderInfo) bundle.get("info");

		order_code = bundle.getString("orderCode");

		the_date = bundle.getString("orderDate");
		if (info == null || "".equals(info)) {
			findIds();
			orderCodeTextView.setText(order_code);
			orderDateTextView.setText(the_date);
			exchange_title.setText("退换货详情");
		} else {

			// submit.setVisibility(View.GONE);
			findIds();
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			orderCodeTextView.setText(info.getOrderCode());

			orderDateTextView.setText(info.getTheDate());

			linkMan.setText(info.getContact());
			linkMan.setEnabled(false);
			linkMan.requestFocus();

			phoneNumber.setText(info.getContact_manner());
			phoneNumber.setEnabled(false);
			phoneNumber.requestFocus();

			describe.setText(info.getDesc());
			describe.setEnabled(false);
			describe.requestFocus();

			reason.setPrompt(info.getCause());
			reason.setEnabled(false);

			exchange_title.setText(info.getStatus());
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (taskReturn != null)
			taskReturn.closeTask();
	}

	private void findIds() {
		orderCodeTextView = (TextView) findViewById(R.id.exchange_biaohao_number);
		orderDateTextView = (TextView) findViewById(R.id.exchange_time_number);
		linkMan = (EditText) findViewById(R.id.exchange_edittext_lianxiren);
		phoneNumber = (EditText) findViewById(R.id.exchange_edittext_lianxifangshi);
		reason = (Spinner) findViewById(R.id.exchange_spinner);
		describe = (EditText) findViewById(R.id.go_pay_leave_message);
		exchange_title = (TextView) findViewById(R.id.exchange_title);
	}


	/**
	 * 提交按钮点击事件
	 */
	private OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			taskReturn = new TaskReturn(ReturnActivity.this);
			taskReturn.returnOrder(order_code);
		}
	};
	
	@Override
	protected void onResume() {
		super.onResume();
//		StatService.onResume(this);
		StatService.onPageStart(this, "退换货详情页面");
	}

	@Override
	protected void onPause() {
		super.onPause();
//		StatService.onPause(this);
		StatService.onPageEnd(this, "退换货详情页面");
	}

}
