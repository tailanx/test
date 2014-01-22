package com.yidejia.app.mall;

import java.io.IOException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpanWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.ctrl.IpAddress;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.log.LoginTask;
import com.yidejia.app.mall.net.user.Login;
import com.yidejia.app.mall.util.BottomChange;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.DesUtils;
import com.yidejia.app.mall.widget.WiperSwitch;
import com.yidejia.app.mall.widget.WiperSwitch.OnChangedListener;
import com.yidejia.app.mall.widget.YLProgressDialog;

public class HomeLogActivity extends BaseActivity implements OnClickListener,
		OnChangedListener {
	private MyApplication myApplication;
	private View view;
	private LayoutInflater inflater;
	private FrameLayout frameLayout;
	private ImageView edit1;
	private InputMethodManager inputMethodManager;
	private RelativeLayout findPwd;// 找回密码
	private RelativeLayout rapidRegist;// 快速注册
	private Button mLogin;
	private EditText stringName;
	private EditText stringPassword;
	private IpAddress ipAddress;
	private WiperSwitch mBox;
	private SharedPreferences sp;
	private Consts consts;
	private LoginTask taskLoginAct;
	private BottomChange bottomChange;
	private RelativeLayout bottomLayout;
	public static boolean isCheck;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		cartsDataManage = new CartsDataManage();
		setContentView(R.layout.activity_main_fragment_layout);
		myApplication = (MyApplication) getApplication();
		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.my_mall_login, null);
		frameLayout = (FrameLayout) findViewById(R.id.main_fragment);
		frameLayout.addView(view);
		int current = getIntent().getIntExtra("current", -1);
		int next = getIntent().getIntExtra("next", -1);
		// 设置底部
		bottomLayout = (RelativeLayout) findViewById(R.id.down_parent_layout);
		bottomChange = new BottomChange(this, bottomLayout);
		if (current != -1 || next != -1) {
			bottomChange.initNavView(current, next);
		}
		initNavView();
		// 添加头部
		setActionBarConfigView();

		inputMethodManager = (InputMethodManager) HomeLogActivity.this
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		ipAddress = new IpAddress();
		consts = new Consts();
		sp = PreferenceManager
				.getDefaultSharedPreferences(HomeLogActivity.this);
		// 设置默认选中
		mBox = (WiperSwitch) view.findViewById(R.id.cb_my_login_checkbox);
		mBox.setOnChangedListener(this);
		findPwd = (RelativeLayout) view
				.findViewById(R.id.re_my_mall_login_retrieve_password);

		// 设置监听
		findPwd.setOnClickListener(this);
		rapidRegist = (RelativeLayout) view
				.findViewById(R.id.re_my_mall_login_retrieve_regist);
		rapidRegist.setOnClickListener(this);
		mLogin = (Button) view.findViewById(R.id.bt_my_mall_login_button);
		mLogin.setOnClickListener(this);
		stringName = (EditText) view
				.findViewById(R.id.et_my_mall_login__edittext_account);
		stringPassword = (EditText) view
				.findViewById(R.id.et_my_mall_login__edittext_password);
		// 优化登录账号密码焦点获取
		LinearLayout login_acount = (LinearLayout) view
				.findViewById(R.id.ll_login_acount);
		LinearLayout login_psw = (LinearLayout) view
				.findViewById(R.id.ll_login_psw);
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
//		String baseName = sp.getString("DESMI", null);
//		//
//		String basePwd = sp.getString("DESPWD", null);
//		Boolean isCheck = sp.getBoolean("CHECK", false);
//		String keyName = baseName + consts.getMiStr();
//		String basepasswrod = DesUtils.decode(keyName, basePwd);
//		if (isCheck) {
//			mBox.setChecked(true);
//			stringName.setText(baseName);
//			stringPassword.setText(basepasswrod);
//			taskLoginAct = new LoginTask(this, baseName, basepasswrod,
//					ipAddress.getIpAddress());
//		}
	}

	private RelativeLayout downHomeLayout;
	private RelativeLayout downGuangLayout;
	private RelativeLayout downSearchLayout;
	private RelativeLayout downShoppingLayout;
	private RelativeLayout downMyLayout;
	private CartsDataManage cartsDataManage;
	private int number;
	private Button cartImage;

	/**
	 * 底部事件
	 */
	/**
	 * 初始化底部导航栏
	 */
	private void initNavView() {
		// 改变底部首页背景，有按下去的效果的背景
		number = cartsDataManage.getCartAmount();
		cartImage = (Button) findViewById(R.id.down_shopping_cart);
		if (number == 0) {
			cartImage.setVisibility(View.GONE);
		} else {
			cartImage.setText(number + "");
		}
		downGuangLayout = (RelativeLayout) findViewById(R.id.re_down_guang_layout);
		downHomeLayout = (RelativeLayout) findViewById(R.id.re_down_home_layout);
		downSearchLayout = (RelativeLayout) findViewById(R.id.re_down_search_layout);
		downShoppingLayout = (RelativeLayout) findViewById(R.id.re_down_shopping_layout);
		downMyLayout = (RelativeLayout) findViewById(R.id.re_down_my_layout);

		downHomeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeLogActivity.this,
						HomeMallActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_in,
						R.anim.activity_out);
			}
		});
		downSearchLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeLogActivity.this,
						HomeSearchActivity.class);
				intent.putExtra("current", 3);
				intent.putExtra("next", 1);
				startActivity(intent);
				HomeLogActivity.this.finish();
				overridePendingTransition(R.anim.activity_in,
						R.anim.activity_out);

			}
		});
		downShoppingLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeLogActivity.this,
						HomeCarActivity.class);
				intent.putExtra("current", 3);
				intent.putExtra("next", 2);
				startActivity(intent);
				HomeLogActivity.this.finish();
				overridePendingTransition(R.anim.activity_in,
						R.anim.activity_out);
			}
		});
	}

	/**
	 * 头部
	 */
	private void setActionBarConfigView() {
		setActionbarConfig();
		setTitle(getResources().getString(R.string.login_title_add));
		TextView leftView = (TextView) findViewById(R.id.ab_common_back);
		leftView.setVisibility(View.GONE);
		edit1 = (ImageView) findViewById(R.id.ab_common_iv_share);
		edit1.setImageDrawable(getResources().getDrawable(R.drawable.setting));
		edit1.setVisibility(View.VISIBLE);
		edit1.setOnClickListener(edit);
	}

	/**
	 * 头部的点击事件
	 */
	private OnClickListener edit = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(HomeLogActivity.this,
					EditorActivity.class);
			startActivity(intent);

		}
	};
	private String name;
	private String pwd;
	private String ip;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.re_my_mall_login_retrieve_password:// 找回密码
			Intent intent1 = new Intent(HomeLogActivity.this,
					FindPwActivity.class);
			HomeLogActivity.this.startActivity(intent1);

			break;

		case R.id.re_my_mall_login_retrieve_regist:// 快速注册
			Intent registIntent = new Intent(HomeLogActivity.this,
					RegistActivity.class);
			HomeLogActivity.this.startActivity(registIntent);

			break;
		case R.id.bt_my_mall_login_button:// 登录
			name = stringName.getText().toString();
			pwd = stringPassword.getText().toString();
			ip = ipAddress.getIpAddress();
			if (name == null || "".equals(name)) {
				Toast.makeText(HomeLogActivity.this, "账号不能为空!",
						Toast.LENGTH_LONG).show();
				return;
			}
			if (pwd == null || "".equals(pwd)) {
				Toast.makeText(HomeLogActivity.this, "密码不能为空!",
						Toast.LENGTH_LONG).show();
				return;
			}
			taskLoginAct = new LoginTask(this, name, pwd,
					ipAddress.getIpAddress());
			break;
		default:
			break;
		}
	}

	// 双击返回键退出程序
	private long exitTime = 0;

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.exit),
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				// ((MyApplication)getApplication()).setUserId("");
				// ((MyApplication)getApplication()).setToken("");
				finish();
				// System.exit(0);
			}
			return true;
		}
		return super.onKeyUp(keyCode, event);
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

	@Override
	public void OnChanged(WiperSwitch wiperSwitch, boolean checkState) {
		// TODO Auto-generated method stub
		Log.e("info", checkState + "state");
		isCheck = checkState;
		// if (checkState) {
		// Log.e("info", checkState + "state");
		// sp.edit().putString("DESMI", name).commit();
		// sp.edit().putBoolean("CHECK", checkState);
		// String keyName = name + consts.getMiStr();
		//
		// try {
		// String demi = DesUtils.encode(keyName, pwd);
		// sp.edit().putString("DESPWD", demi).commit();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// } else {
		// sp.edit().remove("DESMI").commit();
		// sp.edit().remove("CHECK").commit();
		// sp.edit().remove("DESPWD").commit();
	}
	// }

}
