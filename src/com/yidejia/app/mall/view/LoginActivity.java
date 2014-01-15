package com.yidejia.app.mall.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.FindPwActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.RegistActivity;
import com.yidejia.app.mall.ctrl.IpAddress;
import com.yidejia.app.mall.task.TaskLoginAct;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.DesUtils;

public class LoginActivity extends SherlockActivity implements OnClickListener {
	private RelativeLayout findPwd;// 找回密码
	private RelativeLayout rapidRegist;// 快速注册
	private Button mLogin;
	private EditText stringName;
	private EditText stringPassword;
	private CheckBox mBox;
	private SharedPreferences sp;
	private Consts consts;

	private IpAddress ip;

	private TaskLoginAct taskLoginAct;

	private InputMethodManager inputMethodManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_mall_login);
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		consts = new Consts();
		ip = new IpAddress();
		setActionbar();

		inputMethodManager = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		// 默认选中
		mBox = (CheckBox) findViewById(R.id.cb_my_login_checkbox);

		findPwd = (RelativeLayout) findViewById(R.id.re_my_mall_login_retrieve_password);
		// 设置监听
		findPwd.setOnClickListener(this);
		rapidRegist = (RelativeLayout) findViewById(R.id.re_my_mall_login_retrieve_regist);
		rapidRegist.setOnClickListener(this);
		mLogin = (Button) findViewById(R.id.bt_my_mall_login_button);
		stringName = (EditText) findViewById(R.id.et_my_mall_login__edittext_account);
		stringPassword = (EditText) findViewById(R.id.et_my_mall_login__edittext_password);
		mLogin.setOnClickListener(this);
		// 优化登录账号密码焦点获取
		LinearLayout login_acount = (LinearLayout) findViewById(R.id.ll_login_acount);
		LinearLayout login_psw = (LinearLayout) findViewById(R.id.ll_login_psw);
		login_acount
				.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
		login_psw.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
		login_acount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				stringName.requestFocus();
				stringName.setCursorVisible(true);
				stringPassword.setCursorVisible(false);
			}
		});

		login_psw.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				stringPassword.requestFocus();
				stringPassword.setCursorVisible(true);
				stringName.setCursorVisible(false);
			}
		});
		String usern = sp.getString("DESMI", null);
		String userkey = usern + consts.getMiStr();
		String userp = sp.getString("DESPWD", null);
		String userpass = DesUtils.decode(userkey, userp);
		if (usern != null && userpass != null) {
			mBox.setChecked(true);
			stringName.setText(usern);
			stringPassword.setText(userpass);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.re_my_mall_login_retrieve_password:// 找回密码
			Intent intent1 = new Intent(this, FindPwActivity.class);
			LoginActivity.this.startActivity(intent1);

			break;

		case R.id.re_my_mall_login_retrieve_regist:// 快速注册
			Intent registIntent = new Intent(this, RegistActivity.class);
			LoginActivity.this.startActivity(registIntent);

			break;
		case R.id.bt_my_mall_login_button:// 登录
			String name = stringName.getText().toString().trim();
			String pwd = stringPassword.getText().toString().trim();

			if (name == null || "".equals(name)) {
				Toast.makeText(LoginActivity.this, "账号不能为空!", Toast.LENGTH_LONG)
						.show();
				return;
			}
			if (pwd == null || "".equals(pwd)) {
				Toast.makeText(LoginActivity.this, "密码不能为空!", Toast.LENGTH_LONG)
						.show();
				return;
			}

			taskLoginAct = new TaskLoginAct(LoginActivity.this);

			taskLoginAct.loginAct(name, pwd, ip.getIpAddress(), mBox, sp,
					consts);
			// 隐藏键盘
			inputMethodManager.hideSoftInputFromWindow(
					stringName.getWindowToken(), 0);
			inputMethodManager.hideSoftInputFromWindow(
					stringPassword.getWindowToken(), 0);
			break;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (taskLoginAct != null) {
			taskLoginAct.closeTask();
		}
	}

	private void setActionbar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_common);
		TextView button = (TextView) findViewById(R.id.ab_common_title);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 隐藏键盘
				inputMethodManager.hideSoftInputFromWindow(
						stringName.getWindowToken(), 0);
				LoginActivity.this.finish();
			}
		});

		TextView titleTextView = (TextView) findViewById(R.id.ab_common_title);
		titleTextView.setText("登录");
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onResume(this);
	}
}