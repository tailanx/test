
package com.yidejia.app.mall.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.MainFragmentActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.MyMallActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.UserDatamanage;

public class LoginActivity extends SherlockActivity implements OnClickListener{
	private RelativeLayout findPwd;//找回密码
	private RelativeLayout rapidRegist;//快速注册
	private Button mLogin;
	private EditText stringName;
	private EditText stringPassword;

	
	private MyMallActivity newFragment;
	private UserDatamanage userManage;//登陆的接口
	
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
		
		setActionbar();
		userManage = new UserDatamanage(LoginActivity.this);
		setContentView(R.layout.my_mall_login);
		findPwd = (RelativeLayout)findViewById(R.id.my_mall_login_retrieve_password);
		//设置监听
		findPwd.setOnClickListener(this);
		rapidRegist = (RelativeLayout)findViewById(R.id.my_mall_login_retrieve_regist);
		rapidRegist.setOnClickListener(this);
		mLogin = (Button)findViewById(R.id.my_mall_login_button);
		stringName = (EditText)findViewById(R.id.my_mall_login__edittext_account);
		stringPassword = (EditText)findViewById(R.id.my_mall_login__edittext_password);
		mLogin.setOnClickListener(this);

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
		case R.id.my_mall_login_button://登录
		String name = stringName.getText().toString();
		String pwd = stringPassword.getText().toString();
		boolean isSucess = userManage.userLogin(name, pwd, null);
		Log.i("info",isSucess +"   isSucess");
		if(isSucess){
//		if(name==null||"".equals(name)){
//			Toast.makeText(this, "请输入用户名或者密码",Toast.LENGTH_LONG).show();
//		}
//		if(pwd==null||"".equals(pwd)){
//			Toast.makeText(this, "请输入用户名或者密码",Toast.LENGTH_LONG).show();
//		}
//		if(name.equals("aaa")&&pwd.equals("111")){   //登录成功
//			newFragment = new MyMallActivity();
//			this.finish();
			((MyApplication) getApplication()).setIsLogin(true);
//			Log.i("info", myApplication.getIsLogin()+"");
//			myApplication.setIsLogin(true);
//			Log.i("info", myApplication.getIsLogin()+"");
//		FragmentTransaction ft = getFragmentManager().beginTransaction();
//	    ft.replace(R.id.main_fragment, newFragment).commit();
//			Message ms = new Message();
//			ms.what = 000;
//			myApplication.getHandler().sendMessage(ms);
		
		}else{
			Toast.makeText(LoginActivity.this, "网络部给力", Toast.LENGTH_LONG).show();
		}
 		}
		
	}
	

	private void setActionbar(){
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
//		getSupportActionBar().setLogo(R.drawable.back);
		getSupportActionBar().setIcon(R.drawable.back);
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