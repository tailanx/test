package com.yidejia.app.mall.order;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.opens.asyncokhttpclient.AsyncHttpResponse;
import com.opens.asyncokhttpclient.AsyncOkHttpClient;
import com.opens.asyncokhttpclient.RequestParams;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.util.IsPhone;

/**
 * 退换货详情或申请退换货
 * 
 * @author LongBin
 * 
 */
public class ReturnActivity extends BaseActivity {

	private String order_code;
	private String the_date;

	private TextView orderCodeTextView;
	private TextView orderDateTextView;

	private RetOrderInfo info;
	private EditText et_linkMan;
	private EditText et_phoneNumber;
	private Spinner sp_reason;
	private EditText et_describe;
	private TextView tv_exchange_title;
	
	private String cause;//退换原因
	private String contact;//联系人
	private String contact_manner;//联系方式
	private String desc;//原因描述
	private String userId;
	private String token;	
	private String theDate;	//申请退换货时间

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionbarConfig();
		TextView submit = (TextView) findViewById(R.id.ab_common_tv_right);
		
		setContentView(R.layout.exchange);

		Bundle bundle = getIntent().getExtras();

		info = (RetOrderInfo) bundle.get("info");

		order_code = bundle.getString("orderCode");

		the_date = bundle.getString("orderDate");
		
		findIds();
		
		if (info == null || "".equals(info)) {	//申请退换货
			setTitle(R.string.retrun_produce);
			submit.setText(getResources().getString(R.string.commit));
			submit.setVisibility(View.VISIBLE);
			submit.setOnClickListener(listener);
			orderCodeTextView.setText(order_code);
			orderDateTextView.setText(the_date);
			tv_exchange_title.setText(R.string.return_order_detail);
		} else {	//退换货详情

			setTitle(R.string.return_order_detail);
			submit.setText(getResources().getString(R.string.commit));
			submit.setVisibility(View.GONE);
			// submit.setVisibility(View.GONE);
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			orderCodeTextView.setText(info.getOrderCode());

			orderDateTextView.setText(info.getTheDate());

			et_linkMan.setText(info.getContact());
			et_linkMan.setEnabled(false);
			et_linkMan.requestFocus();

			et_phoneNumber.setText(info.getContact_manner());
			et_phoneNumber.setEnabled(false);
			et_phoneNumber.requestFocus();

			et_describe.setText(info.getDesc());
			et_describe.setEnabled(false);
			et_describe.requestFocus();

			sp_reason.setPrompt(info.getCause());
			sp_reason.setEnabled(false);

			tv_exchange_title.setText(info.getStatus());
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void findIds() {
		orderCodeTextView = (TextView) findViewById(R.id.exchange_biaohao_number);
		orderDateTextView = (TextView) findViewById(R.id.exchange_time_number);
		et_linkMan = (EditText) findViewById(R.id.exchange_edittext_lianxiren);
		et_phoneNumber = (EditText) findViewById(R.id.exchange_edittext_lianxifangshi);
		sp_reason = (Spinner) findViewById(R.id.exchange_spinner);
		et_describe = (EditText) findViewById(R.id.go_pay_leave_message);
		tv_exchange_title = (TextView) findViewById(R.id.exchange_title);
	}


	/**
	 * 提交按钮点击事件
	 */
	private OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			
			getParam();
		}
	};
	
	/**获取申请退换货的信息**/
	@SuppressLint("SimpleDateFormat")
	private void getParam(){
		cause = sp_reason.getSelectedItem().toString().trim();
		contact = et_linkMan.getText().toString().trim();
		contact_manner = et_phoneNumber.getText().toString().trim();
		desc = et_describe.getText().toString().trim();
		if(TextUtils.isEmpty(cause)){
			Toast.makeText(this, "请选择退换原因!", Toast.LENGTH_LONG).show();
			return;
		}
		if(TextUtils.isEmpty(contact)){
			Toast.makeText(this, "联系人不能为空!", Toast.LENGTH_LONG).show();
			return;
		}
		if(TextUtils.isEmpty(contact_manner)){
			Toast.makeText(this, "联系方式不能为空!", Toast.LENGTH_LONG).show();
			return;
		}
		if(TextUtils.isEmpty(desc)){
			Toast.makeText(this, "原因描述不能为空!", Toast.LENGTH_LONG).show();
			return;
		}
		if(!IsPhone.isMobileNO(contact_manner)){
			Toast.makeText(this,
					getResources().getString(R.string.phone),
					Toast.LENGTH_SHORT).show();
			return;
		}
		userId = MyApplication.getInstance().getUserId();
		token = MyApplication.getInstance().getToken();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		theDate = sdf.format(new Date()).trim();
		
		commitReturn();
	}
	
	/**提交申请**/
	private void commitReturn(){
		String param = new JNICallBack().getHttp4GetReturn(userId, order_code,
				theDate, contact, contact_manner, cause, desc, token);
		
		String url = new JNICallBack().HTTPURL;
		
		RequestParams requestParams = new RequestParams();
		requestParams.put(param);
		
		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.post(url, requestParams, new AsyncHttpResponse(){

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				if(HttpStatus.SC_OK == statusCode){
					JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(content);
						int code = jsonObject.optInt("code");
						if(code == 1){
							String msg = jsonObject.optString("msg");
							if("成功".equals(msg)) {
								Toast.makeText(ReturnActivity.this, "提交成功!", Toast.LENGTH_LONG).show();
								ReturnActivity.this.finish();
								return;
							} 
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				Toast.makeText(ReturnActivity.this, "提交失败!", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onError(Throwable error, String content) {
				super.onError(error, content);
				Toast.makeText(ReturnActivity.this, getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
//		StatService.onResume(this);
		if(info != null && !"".equals(info))
			StatService.onPageStart(this, "退换货详情页面");
		else StatService.onPageStart(this, getString(R.string.retrun_produce));
	}

	@Override
	protected void onPause() {
		super.onPause();
//		StatService.onPause(this);
		if(info != null && !"".equals(info))
			StatService.onPageEnd(this, "退换货详情页面");
		else StatService.onPageEnd(this, getString(R.string.retrun_produce));
	}

}
