package com.yidejia.app.mall;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.ctrl.IpAddress;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.task.TaskCheckCode;
import com.yidejia.app.mall.task.TaskGetCode;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;
import com.yidejia.app.mall.util.IsPhone;

public class RegistActivity extends BaseActivity implements Callback {
	public Button mback;// ����
	// private UserDatamanage userManage;
	private EditText mZhanghao;
	private EditText mPws;
	private EditText mEditText;
	private EditText mEditText2;
	private Button mButton;
	private ImageView getCodeImgView;
	// private TextView regist_agreement;
	private TextView protocol;// 协议

	private String account;
	private String pwd;
	private String confirmPwd;
	private String obtain;

	private IpAddress ip;
	private String ipAddress;
	private CheckBox mBox;
	private String url1 = "http://m.yidejia.com/regterms.html";

	private TaskGetCode getCodeTask;
	private String url;
	private HttpClientUtil client;
//	private String reponse;// 返回码信息
	private int code;
	private TextView daojishi;// 倒计时

	private TaskCheckCode taskCheckCode;
	private Handler handler;

	private void setupShow() {
		mZhanghao = (EditText) findViewById(R.id.et_my_mall_regist_edittext_account);
		mPws = (EditText) findViewById(R.id.et_my_mall_regist_password);
		mEditText = (EditText) findViewById(R.id.et_my_mall_regist_confirm_password);
		mEditText2 = (EditText) findViewById(R.id.et_my_mall_regist_obtain);
		mButton = (Button) findViewById(R.id.bt_find_password_confirm_button);
		mBox = (CheckBox) findViewById(R.id.ck_my_mall_regist_checkbox);
		getCodeImgView = (ImageView) findViewById(R.id.iv_my_mall_regist_password_validation_button);
		// regist_agreement = (TextView) findViewById(R.id.tv_regist_agreement);
		protocol = (TextView) findViewById(R.id.tv_regist_agreement);
		daojishi = (TextView) findViewById(R.id.tv_dao_jisi);
		handler = new Handler(this);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);

			setActionbarConfig();
			setTitle(getResources().getString(R.string.login_find_psw_add));
			// userManage = new UserDatamanage(RegistActivity.this);
			ip = new IpAddress();
			ipAddress = ip.getIpAddress();
			setContentView(R.layout.my_mall_regist);

			setupShow();

			mBox.setChecked(true);

			mButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					registerListener();
				}

			});

			getCodeImgView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					getCodeListener();
				}
			});
			//
			// regist_agreement.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// // TODO Auto-generated method stub
			// goToAgreementAct();
			// }
			// });

			protocol.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Uri uri = Uri.parse(url1);
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(intent);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 注册按钮点击事件监听
	 */
	private void registerListener() {
		account = mZhanghao.getText().toString().trim();// 账号
		pwd = mPws.getText().toString().trim();// 密码
		confirmPwd = mEditText.getText().toString().trim();// 重复密码
		obtain = mEditText2.getText().toString().trim();// 验证码
		if (!mBox.isChecked()) {
			Toast.makeText(RegistActivity.this, "请先勾选同意协议!", Toast.LENGTH_LONG)
					.show();
			return;
		}
		boolean phone = IsPhone.isMobileNO(account);
		if (phone) {
			if (obtain == null || "".equals(obtain)) {
				Toast.makeText(RegistActivity.this,
						getResources().getString(R.string.obtain),
						Toast.LENGTH_LONG).show();
			} else {
				if (pwd.length() < 6) {
					Toast.makeText(RegistActivity.this,
							getResources().getString(R.string.pwd_length),
							Toast.LENGTH_SHORT).show();
				} else {
					if (pwd.equals(confirmPwd)) {
						// 事件id（"registered id"）的事件pass，其时长持续100毫秒
						StatService.onEventDuration(RegistActivity.this,
								"register", "register", 100);
						taskCheckCode = new TaskCheckCode(RegistActivity.this);
						taskCheckCode.checkCode(account, obtain, pwd,
								ipAddress, "");
					} else {
						Toast.makeText(
								RegistActivity.this,
								getResources().getString(R.string.regist_false),
								Toast.LENGTH_LONG).show();
					}
				}
			}
		} else {
			Toast.makeText(RegistActivity.this,
					getResources().getString(R.string.phone),
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 获取验证码的事件监听
	 */
	private void getCodeListener() {
		account = mZhanghao.getText().toString().trim();// 账号
		boolean isphone = IsPhone.isMobileNO(account);
		if (!isphone) {
			Toast.makeText(RegistActivity.this,
					getResources().getString(R.string.phone),
					Toast.LENGTH_SHORT).show();
			return;
		}
		String param = new JNICallBack().getHttp4GetCode(account);
		url = new JNICallBack().HTTPURL;
		client = new HttpClientUtil(this);
		client.setIsShowLoading(true);
		client.setShowErrMessage(true);
		
		client.getHttpResp(url, param, new IHttpResp() {

			@Override
			public void onSuccess(String content) {
				getCodeTask = new TaskGetCode(RegistActivity.this);
				if (getCodeTask.parse(content)) {
					code = getCodeTask.getCode();
					String respParam = new JNICallBack().getHttp4SendMsg(
							account, code + "");
					HttpClientUtil client = new HttpClientUtil(RegistActivity.this);
					client.setShowErrMessage(true);
					client.getHttpResp(url, respParam, new IHttpResp() {

						@Override
						public void onSuccess(String content) {
							if (getCodeTask.parseResp(content)) {
								getCodeImgView.setClickable(false);
								daojishi.setVisibility(View.VISIBLE);
								final Timer timer = new Timer();
								timer.scheduleAtFixedRate(new TimerTask() {
									long start = System.currentTimeMillis() + 1 * 60 * 1000;

									@Override
									public void run() {
										long end = System.currentTimeMillis();
										long time = (int) ((start - end) / 1000);
										Message msg = new Message();
										if (time > 0) {

											msg.arg1 = (int) time;

										} else {
											msg.what = Consts.EMS;
											timer.cancel();
										}
										handler.sendMessage(msg);

									}
								}, 0, 1000);
								Toast.makeText(RegistActivity.this,
										"验证码已发送到您的手机，请注意查看!", Toast.LENGTH_LONG)
										.show();
							} else {
								Toast.makeText(
										RegistActivity.this,
										RegistActivity.this.getResources()
												.getString(R.string.time_out),
										Toast.LENGTH_SHORT).show();
							}
						}
					});

				}

			}
		});
	}


	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "注册页面");
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "注册页面");
	}

	@Override
	public boolean handleMessage(Message msg) {

		if (msg.what == Consts.EMS) {
			daojishi.setVisibility(View.GONE);
			getCodeImgView.setClickable(true);

		} else {
			daojishi.setText(msg.arg1 + "");
		}
		return false;
	}
}
