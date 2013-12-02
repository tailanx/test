package com.yidejia.app.mall.fragment;

import java.io.IOException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
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
import android.widget.TableRow;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.yidejia.app.mall.MainFragmentActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.MyMallActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.ctrl.IpAddress;
import com.yidejia.app.mall.datamanage.UserDatamanage;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.net.user.Login;
import com.yidejia.app.mall.task.TaskLoginAct;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.DESUtil;
import com.yidejia.app.mall.util.DesUtils;
import com.yidejia.app.mall.view.EditorActivity;
import com.yidejia.app.mall.view.FindPwActivity;
import com.yidejia.app.mall.view.RegistActivity;
import com.yidejia.app.mall.widget.YLProgressDialog;

public class LoginFragment extends SherlockFragment implements OnClickListener {
	private RelativeLayout findPwd;// 找回密码
	private RelativeLayout rapidRegist;// 快速注册
	private Button mLogin;
	private EditText stringName;
	private EditText stringPassword;
	private ImageView configImageView;
//	private UserDatamanage userManage;
	private IpAddress ipAddress;
	private MyMallActivity fragment;
	private MyApplication myApplication;
	private CheckBox mBox;
	private SharedPreferences sp;
	private Consts consts;
	private Task taskLoginAct;
	
	private InputMethodManager inputMethodManager;

	private MyMallActivity newFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.login, container, false);
		// getSherlockActivity().getSupportActionBar().setCustomView(R.layout.login_top);
//		userManage = new UserDatamanage(getSherlockActivity());
		myApplication = (MyApplication) getSherlockActivity().getApplication();

		configImageView = (ImageView) getSherlockActivity().findViewById(
				R.id.config_btn);//设置
		
		configImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getSherlockActivity(),
						EditorActivity.class);
				getSherlockActivity().startActivity(intent);
			}
		});
		
		inputMethodManager =(InputMethodManager)this.getSherlockActivity().
				getSystemService(Context.INPUT_METHOD_SERVICE); 
		
		ipAddress = new IpAddress();
		consts = new Consts();
		sp = PreferenceManager
				.getDefaultSharedPreferences(getSherlockActivity());
		// 设置默认选中
		mBox = (CheckBox) view.findViewById(R.id.my_login_checkbox);
		findPwd = (RelativeLayout) view
				.findViewById(R.id.my_mall_login_retrieve_password);
		// 设置监听
		findPwd.setOnClickListener(this);
		rapidRegist = (RelativeLayout) view
				.findViewById(R.id.my_mall_login_retrieve_regist);
		rapidRegist.setOnClickListener(this);
		mLogin = (Button) view.findViewById(R.id.my_mall_login_button);
		mLogin.setOnClickListener(this);
		stringName = (EditText) view
				.findViewById(R.id.my_mall_login__edittext_account);
		stringPassword = (EditText) view
				.findViewById(R.id.my_mall_login__edittext_password);
		// 优化登录账号密码焦点获取
		LinearLayout login_acount = (LinearLayout) view.findViewById(R.id.login_acount);
		LinearLayout login_psw = (LinearLayout) view.findViewById(R.id.login_psw);
		login_acount
				.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
		login_psw.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
		login_acount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stringName.requestFocus();
				stringName.setCursorVisible(true);
				stringPassword.setCursorVisible(false);
			}
		});

		login_psw.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stringPassword.requestFocus();
				stringPassword.setCursorVisible(true);
				stringName.setCursorVisible(false);
			}
		});
		String baseName = sp.getString("DESMI", null);
		//
		String basePwd = sp.getString("DESPWD", null);
		 String keyName = baseName+consts.getMiStr();
		 String basepasswrod = DesUtils.decode(keyName, basePwd);
	
		if (baseName != null && basepasswrod != null) {
			mBox.setChecked(true);
			stringName.setText(baseName);
			stringPassword.setText(basepasswrod);
		}
		return view;
	}

	// @Override
	// public void onClick(View arg0) {
	//
	//
	// }

	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// // TODO Auto-generated method stub
	// super.onCreate(savedInstanceState);
	// // this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	// // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	// WindowManager.LayoutParams.FLAG_FULLSCREEN);
	//
	// setActionbar();
	//
	// setContentView(R.layout.my_mall_login);
	// setupShow();
	// }
	private String name;
	private String pwd;
	private String ip;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_mall_login_retrieve_password:// 找回密码
			Intent intent1 = new Intent(getSherlockActivity(),
					FindPwActivity.class);
			getSherlockActivity().startActivity(intent1);

			break;

		case R.id.my_mall_login_retrieve_regist:// 快速注册
			Intent registIntent = new Intent(getSherlockActivity(),
					RegistActivity.class);
			getSherlockActivity().startActivity(registIntent);

			break;
		case R.id.my_mall_login_button:// 登录
			name = stringName.getText().toString();
			pwd = stringPassword.getText().toString();
			ip = ipAddress.getIpAddress();
			if (name == null || "".equals(name)) {
				Toast.makeText(getSherlockActivity(), "账号不能为空!",
						Toast.LENGTH_LONG).show();
				return;
			}
			if (pwd == null || "".equals(pwd)) {
				Toast.makeText(getSherlockActivity(), "密码不能为空!",
						Toast.LENGTH_LONG).show();
				return;
			}
//			if(taskLoginAct != null && taskLoginAct.getStatus().RUNNING == AsyncTask.Status.RUNNING){
//				taskLoginAct.cancel(true);
//			}
			taskLoginAct = new Task();
			  taskLoginAct.execute();
			//			taskLoginAct.loginAct(name, pwd, ipAddress.getIpAddress());
			break;
		// boolean isSucess = userManage.userLogin(name, pwd,
		// ipAddress.getIpAddress());
		//
		// // Log.i("info", isSucess +"isSucess");
		// if (isSucess) {
		// myApplication.setIsLogin(true);
		//
		// fragment = new MyMallActivity();
		// FragmentTransaction ft = getFragmentManager()
		// .beginTransaction();
		// if (fragment.isAdded())
		// ft.hide(LoginFragment.this).show(fragment).commit();
		// else
		// ft.hide(LoginFragment.this)
		// .replace(R.id.main_fragment, fragment).commit();
		//
		// if (mBox.isChecked()) {
		// sp.edit().putString("DESMI", name).commit();
		//
		// String keyName = name+consts.getMiStr();
		//
		//
		// desUtil.setKey(keyName);
		// String demi = desUtil.encryptStr(pwd);
		// Log.i("info", demi+"     demi.getBytes()");
		// // String basmi = Base64.encodeToString(demi.getBytes(),
		// Base64.DEFAULT);
		// // Log.i("info", basmi+"     basmi");
		// sp.edit().putString("DESPWD", demi).commit();
		//
		//
		// } else {
		// sp.edit().remove("DESMI").commit();
		// sp.edit().remove("DESPWD").commit();
		// }
		// if(name==null||"".equals(name)){
		// Toast.makeText(getSherlockActivity(),
		// "请输入用户名或者密码",Toast.LENGTH_LONG).show();
		// }
		// if(pwd==null||"".equals(pwd)){
		// Toast.makeText(getSherlockActivity(),
		// "请输入用户名或者密码",Toast.LENGTH_LONG).show();
		// }
		// if(name.equals("aaa")&&pwd.equals("111")){ //登录成功
		// newFragment = new MyMallActivity();
		// ((MyApplication)getSherlockActivity().getApplication()).setIsLogin(true);
		// // Log.i("info", myApplication.getIsLogin()+"");
		// myApplication.setIsLogin(true);
		// Log.i("info", myApplication.getIsLogin()+"");
		// Message ms = new Message();
		// ms.what = 000;
		// myApplication.getHandler().sendMessage(ms);

		// }else{
		// Toast.makeText(getSherlockActivity(),
		// getSherlockActivity().getResources().getString(R.string.login_error),
		// Toast.LENGTH_SHORT).show();
		// }
		}

	}

	private String message;
	private boolean isTimeout = false;

	private class Task extends AsyncTask<Void, Void, Boolean> {
		private ProgressDialog bar;

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Login login = new Login();
			try {
				String httpresp;
				try {
					httpresp = login.getHttpResponse(name, pwd, ip);
					boolean issuccess = login.analysisHttpResp(
							getSherlockActivity(), httpresp);
					message = login.getMsg();
					return issuccess;
				} catch (TimeOutEx e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					isTimeout = true;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			bar = new ProgressDialog(getSherlockActivity());
//			bar.setCancelable(true);
//			bar.setMessage(getSherlockActivity().getResources().getString(R.string.loading));
//			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			bar.show();
			bar = (ProgressDialog) new YLProgressDialog(getSherlockActivity())
					.createLoadingDialog(getSherlockActivity(), null);
			bar.setOnCancelListener(new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
					cancel(true);
				}
			});
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			bar.dismiss();
			if (result) {
				Toast.makeText(getSherlockActivity(), "登陆成功！",
						Toast.LENGTH_LONG).show();
				//隐藏键盘
				inputMethodManager.hideSoftInputFromWindow(stringName.getWindowToken(), 0);
				inputMethodManager.hideSoftInputFromWindow(stringPassword.getWindowToken(), 0);
				
				myApplication.setIsLogin(true);
				fragment = new MyMallActivity();
				FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				if (fragment.isAdded())
					ft.hide(LoginFragment.this).show(fragment).commit();
				else
					ft.hide(LoginFragment.this)
							.replace(R.id.main_fragment, fragment).commit();
				
				if (mBox.isChecked()) {
					 sp.edit().putString("DESMI", name).commit();
					
					 String keyName = name+consts.getMiStr();
					
					try {
						String demi = DesUtils.encode(keyName,pwd);
						sp.edit().putString("DESPWD", demi).commit();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
				
					
					 } else {
					 sp.edit().remove("DESMI").commit();
					 sp.edit().remove("DESPWD").commit();
					 }
			} else {
				if (isTimeout) {
					Toast.makeText(
							getSherlockActivity(),
							getSherlockActivity().getResources()
									.getString(R.string.time_out),
							Toast.LENGTH_SHORT).show();
					isTimeout = false;
					return;
				}
				Toast.makeText(getSherlockActivity(), message,
						Toast.LENGTH_LONG).show();
			}
		}

	}

}
// private void setActionbar(){
// getSupportActionBar().setDisplayHomeAsUpEnabled(false);
// getSupportActionBar().setDisplayShowHomeEnabled(false);
// getSupportActionBar().setDisplayShowTitleEnabled(false);
// getSupportActionBar().setDisplayUseLogoEnabled(false);
// // getSupportActionBar().setLogo(R.drawable.back);
// getSupportActionBar().setIcon(R.drawable.back);
// getSupportActionBar().setDisplayShowCustomEnabled(true);
// getSupportActionBar().setCustomView(R.layout.actionbar_compose);
// // startActionMode(new AnActionModeOfEpicProportions(ComposeActivity.this));
// ImageView button = (ImageView) findViewById(R.id.compose_back);
// button.setOnClickListener(new OnClickListener() {
//
// @Override
// public void onClick(View v) {
// // TODO Auto-generated method stub
// // Toast.makeText(ComposeActivity.this, "button", Toast.LENGTH_SHORT).show();
// LoginActivity.this.finish();
// }
// });
//
// TextView titleTextView = (TextView) findViewById(R.id.compose_title);
// titleTextView.setText("登录1");
// }
// }