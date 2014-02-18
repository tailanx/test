package com.yidejia.app.mall;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.task.TaskGetCode;
import com.yidejia.app.mall.task.TaskReset;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;
import com.yidejia.app.mall.util.IsPhone;

public class FindPwActivity extends BaseActivity implements Callback {

	private EditText acount_textview;
	private EditText obtain_textView;
	private EditText psw_textview;
	private EditText psw_again_textview;
	private ImageView obtain_imageView;
	private Button okButton;

	private String account;
	private String pwd;
	private String confirmPwd;
	private String obtain;

	private TaskGetCode getCodeTask;

	private TaskReset taskReset;
	private String url;
	private HttpClientUtil client;
	private String reponse;// 返回码信息
	private int code;
	private TextView daojishi;// 倒计时
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionbarConfig();
		setTitle(getResources().getString(R.string.login_find_psw));

		setContentView(R.layout.find_password);

		findIds();

		obtain_imageView.setOnClickListener(obtainListener);

		okButton.setOnClickListener(okListener);
	}

	private void findIds() {
		handler = new Handler(this);
		daojishi = (TextView) findViewById(R.id.tv_dao_jisi);
		acount_textview = (EditText) findViewById(R.id.et_my_mall_find_edittext_account);
		obtain_textView = (EditText) findViewById(R.id.et_my_mall_find_obtain);
		obtain_imageView = (ImageView) findViewById(R.id.iv_my_mall_find_password_validation_button);
		psw_textview = (EditText) findViewById(R.id.et_my_mall_find_password);
		psw_again_textview = (EditText) findViewById(R.id.et_my_mall_find_confirm_password);
		okButton = (Button) findViewById(R.id.bt_find_find_confirm_button);
	}

	private OnClickListener okListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			resetListener();
		}
	};

	/**
	 * 验证码事件
	 */
	private OnClickListener obtainListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			getCodeListener();
		}
	};

	/**
	 * 获取验证码的事件监听
	 */
	private void getCodeListener() {
		account = acount_textview.getText().toString().trim();// 账号
		boolean isphone = IsPhone.isMobileNO(account);
		if (!isphone) {
			Toast.makeText(FindPwActivity.this,
					getResources().getString(R.string.phone),
					Toast.LENGTH_SHORT).show();
			return;
		}
		// getCodeTask = new TaskGetCode(FindPwActivity.this);
		// getCodeTask.getCode(account);
		String param = new JNICallBack().getHttp4GetCode(account);
		url = new JNICallBack().HTTPURL;
		client = new HttpClientUtil();
		client.getHttpResp(url, param, new IHttpResp() {

			@Override
			public void success(String content) {
				getCodeTask = new TaskGetCode(FindPwActivity.this);
				if (getCodeTask.parse(content)) {
					String respParam = new JNICallBack().getHttp4SendMsg(
							account, code + "");
					client.getHttpResp(url, respParam, new IHttpResp() {

						@Override
						public void success(String content) {
							if (getCodeTask.parseResp(content)) {
								daojishi.setVisibility(View.VISIBLE);
								obtain_imageView.setClickable(false);
								final Timer timer = new Timer();
								timer.scheduleAtFixedRate(new TimerTask() {
									long start = System.currentTimeMillis() + 1 * 60 * 1000;

									@Override
									public void run() {
										long end = System.currentTimeMillis();
										long time = (start - end) / 1000;
										Message msg = new Message();

										if (time > 0) {
											msg.arg1 = (int) time;
										} else {
											msg.what = Consts.GENERAL;
											timer.cancel();
										}
										handler.sendMessage(msg);
									}
								}, 0, 1000);
								Toast.makeText(FindPwActivity.this,
										"验证码已发送到您的手机，请注意查看!", Toast.LENGTH_LONG)
										.show();
							} else {
								Toast.makeText(
										FindPwActivity.this,
										FindPwActivity.this.getResources()
												.getString(R.string.time_out),
										Toast.LENGTH_SHORT).show();
							}
						}
					});

				}

			}
		});
	}

	/**
	 * 注册按钮点击事件监听
	 */
	private void resetListener() {
		account = acount_textview.getText().toString().trim();// 账号
		pwd = psw_textview.getText().toString().trim();// 密码
		confirmPwd = psw_again_textview.getText().toString().trim();// 重复密码
		obtain = obtain_textView.getText().toString().trim();// 验证码

		boolean phone = IsPhone.isMobileNO(account);
		if (phone) {
			if (obtain == null || "".equals(obtain)) {// 验证码为空
				Toast.makeText(FindPwActivity.this,
						getResources().getString(R.string.obtain),
						Toast.LENGTH_LONG).show();
			} else {
				if (pwd.length() < 6) {// 密码小于6位
					Toast.makeText(FindPwActivity.this,
							getResources().getString(R.string.pwd_length),
							Toast.LENGTH_SHORT).show();
				} else {
					if (pwd.equals(confirmPwd)) {

						taskReset = new TaskReset(FindPwActivity.this);
						taskReset.resetPsw(account, pwd, obtain);
						// taskCheckCode.checkCode(account, obtain, pwd,
						// ipAddress, "");
					} else {// 两次密码不一致
						Toast.makeText(
								FindPwActivity.this,
								getResources().getString(R.string.regist_false),
								Toast.LENGTH_LONG).show();
					}
				}
			}
		} else {
			Toast.makeText(FindPwActivity.this,
					getResources().getString(R.string.phone),
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// if (taskReset != null)
		// taskReset.closeTask();
		// if (getCodeTask != null)
		// getCodeTask.closeTask();
	}

	@Override
	public boolean handleMessage(Message msg) {
		if (msg.what == Consts.GENERAL) {
			daojishi.setVisibility(View.GONE);
			obtain_imageView.setClickable(true);
		} else {
			daojishi.setText(msg.arg1 + "");
		}
		return false;
	}

	/*
	 * @Override protected void onPause() { // TODO Auto-generated method stub
	 * super.onPause(); StatService.onPause(this); }
	 * 
	 * @Override protected void onResume() { // TODO Auto-generated method stub
	 * super.onResume(); StatService.onResume(this); }
	 */
}
