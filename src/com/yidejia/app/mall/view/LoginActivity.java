
package com.yidejia.app.mall.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.ctrl.IpAddress;
import com.yidejia.app.mall.datamanage.UserDatamanage;
import com.yidejia.app.mall.task.TaskLoginAct;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.DesUtils;

public class LoginActivity extends SherlockActivity implements OnClickListener{
	private RelativeLayout findPwd;//找回密码
	private RelativeLayout rapidRegist;//快速注册
	private Button mLogin;
	private EditText stringName;
	private EditText stringPassword;
	private CheckBox mBox;
	private SharedPreferences sp ;
	private Consts consts;
	
	private UserDatamanage userManage;//登陆的接口
	private IpAddress ip;
	
	private TaskLoginAct taskLoginAct;
	
//	private void doClick(View v){
//		switch (v.getId()) {
//		case R.id.login_edit://返回
//			Intent intent = new Intent(this,MainFragmentActivity.class);
//			startActivity(intent);
//			
//			break;
//		}
//	}
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		View view = inflater.inflate(R.layout.my_mall_login, container, false);
//		getSherlockActivity().getSupportActionBar().setCustomView(R.layout.login_top);
//		
//		findPwd = (RelativeLayout)view.findViewById(R.id.my_mall_login_retrieve_password);
//		//设置监听
//		findPwd.setOnClickListener(this);
//		rapidRegist = (RelativeLayout)view.findViewById(R.id.my_mall_login_retrieve_regist);
//		rapidRegist.setOnClickListener(this);
//		mLogin = (Button)view.findViewById(R.id.my_mall_login_button);
//		mLogin.setOnClickListener(this);
//		
//		stringName = (EditText)view.findViewById(R.id.my_mall_login__edittext_account);
//		stringPassword = (EditText)view.findViewById(R.id.my_mall_login__edittext_password);
//		
//		return view;
//	}

//	@Override
//	public void onClick(View arg0) {
//		
//	
//	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.login);
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		consts = new Consts();
		ip = new IpAddress();
		setActionbar();
//		userManage = new UserDatamanage(LoginActivity.this);
		//默认选中】
		mBox = (CheckBox)findViewById(R.id.my_login_checkbox);
		
		findPwd = (RelativeLayout)findViewById(R.id.my_mall_login_retrieve_password);
		//设置监听
		findPwd.setOnClickListener(this);
		rapidRegist = (RelativeLayout)findViewById(R.id.my_mall_login_retrieve_regist);
		rapidRegist.setOnClickListener(this);
		mLogin = (Button)findViewById(R.id.my_mall_login_button);
		stringName = (EditText)findViewById(R.id.my_mall_login__edittext_account);
		stringPassword = (EditText)findViewById(R.id.my_mall_login__edittext_password);
		mLogin.setOnClickListener(this);
		//优化登录账号密码焦点获取
		LinearLayout login_acount = (LinearLayout) findViewById(R.id.login_acount);
		LinearLayout login_psw = (LinearLayout) findViewById(R.id.login_psw);
		login_acount.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
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
		String usern = sp.getString("DESMI", null);
		String userkey = usern + consts.getMiStr();
		String userp = sp.getString("DESPWD", null);
		String userpass = DesUtils.decode(userkey, userp);
		if(usern!=null&&userpass!=null){
			mBox.setChecked(true);
			stringName.setText(usern);
			stringPassword.setText(userpass);
		}
		
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.my_mall_login_retrieve_password://找回密码
			Intent intent1 = new Intent(this, FindPwActivity.class);
			LoginActivity.this.startActivity(intent1);
			
			break;

		case R.id.my_mall_login_retrieve_regist://快速注册
			Intent registIntent = new Intent(this, RegistActivity.class);
			LoginActivity.this.startActivity(registIntent);
		
			break;
		case R.id.my_mall_login_button:// 登录
			String name = stringName.getText().toString().trim();
			String pwd = stringPassword.getText().toString().trim();
			
			if(name == null || "".equals(name)){
				Toast.makeText(LoginActivity.this, "账号不能为空!", Toast.LENGTH_LONG).show();
				return;
			}
			if(pwd == null || "".equals(pwd)){
				Toast.makeText(LoginActivity.this, "密码不能为空!", Toast.LENGTH_LONG).show();
				return;
			}
			
			taskLoginAct = new TaskLoginAct(LoginActivity.this);
			
			taskLoginAct.loginAct(name, pwd, ip.getIpAddress(),mBox,sp,consts);
			
			break;
//			boolean isSucess = userManage.userLogin(name, pwd,
//					ip.getIpAddress());
//			Log.i("info", isSucess + "   isSucess");
//			if (isSucess) {
//				myApplication.setIsLogin(true);
//
//				// Log.i("info",
//				// myApplication.getToken()+"      myApplication.getToken()");
//				this.finish();
//				// if(name==null||"".equals(name)){
//				// Toast.makeText(this, "请输入用户名或者密码",Toast.LENGTH_LONG).show();
//				// }
//				// if(pwd==null||"".equals(pwd)){
//				// Toast.makeText(this, "请输入用户名或者密码",Toast.LENGTH_LONG).show();
//				// }
//				// if(name.equals("aaa")&&pwd.equals("111")){ //登录成功
//				// newFragment = new MyMallActivity();
//				// this.finish();
//				// ((MyApplication) getApplication()).setIsLogin(true);
//				// Log.i("info", myApplication.getIsLogin()+"");
//				// myApplication.setIsLogin(true);
//				// Log.i("info", myApplication.getIsLogin()+"");
//				// FragmentTransaction ft =
//				// getFragmentManager().beginTransaction();
//				// ft.replace(R.id.main_fragment, newFragment).commit();
//				// Message ms = new Message();
//				// ms.what = 000;
//				// myApplication.getHandler().sendMessage(ms);
//
//			} else {
//				Toast.makeText(LoginActivity.this,
//						getResources().getString(R.string.no_network),
//						Toast.LENGTH_LONG).show();
//			}
		}

	}
	
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(taskLoginAct != null){
			taskLoginAct.closeTask();
		}
	}
	
	private void setActionbar(){
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
//		getSupportActionBar().setLogo(R.drawable.back);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_compose);
//		startActionMode(new AnActionModeOfEpicProportions(ComposeActivity.this));
		ImageView button = (ImageView) findViewById(R.id.compose_back);
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Toast.makeText(ComposeActivity.this, "button", Toast.LENGTH_SHORT).show();
				LoginActivity.this.finish();
			}
		});
		
		TextView titleTextView = (TextView) findViewById(R.id.compose_title);
		titleTextView.setText("登录");
	}
}