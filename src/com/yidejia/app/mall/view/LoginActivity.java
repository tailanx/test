package com.yidejia.app.mall.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.yidejia.app.mall.MainFragmentActivity;
import com.yidejia.app.mall.R;

public class LoginActivity extends SherlockFragment implements OnClickListener{
	private RelativeLayout findPwd;//�һ�����
	private RelativeLayout rapidRegist;//����ע��
	private Button mLogin;
	private EditText stringName;
	private EditText stringPassword;

	private void doClick(View v){
		switch (v.getId()) {
		case R.id.login_edit://����
			Intent intent = new Intent(getSherlockActivity(),MainFragmentActivity.class);
			startActivity(intent);
			
			break;
		}
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.my_mall_login, container, false);
		getSherlockActivity().getSupportActionBar().setCustomView(R.layout.login_top);
		
		findPwd = (RelativeLayout)view.findViewById(R.id.my_mall_login_retrieve_password);
		//���ü���
		findPwd.setOnClickListener(this);
		rapidRegist = (RelativeLayout)view.findViewById(R.id.my_mall_login_retrieve_regist);
		rapidRegist.setOnClickListener(this);
		mLogin = (Button)view.findViewById(R.id.my_mall_login_button);
		mLogin.setOnClickListener(this);
		
		stringName = (EditText)view.findViewById(R.id.my_mall_login__edittext_account);
		stringPassword = (EditText)view.findViewById(R.id.my_mall_login__edittext_password);
		
		return view;
	}

//	@Override
//	public void onClick(View arg0) {
//		
//	
//	}

	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
////		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
////		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		
//		setActionbar();
//		
//		setContentView(R.layout.my_mall_login);
//		setupShow();
//	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_mall_login_retrieve_password://�һ�����
			Intent intent1 = new Intent(getSherlockActivity(), FindPwActivity.class);
			LoginActivity.this.startActivity(intent1);
			
			break;

		case R.id.my_mall_login_retrieve_regist://����ע��
			Intent registIntent = new Intent(getSherlockActivity(), RegistActivity.class);
			LoginActivity.this.startActivity(registIntent);
		
			break;
		case R.id.my_mall_login_button://��¼
		String name = stringName.getText().toString();
		String pwd = stringPassword.getText().toString();
		if(name==null||"".equals(name)){
			Toast.makeText(getSherlockActivity(), "�������û�����������",Toast.LENGTH_LONG).show();
		}
		if(pwd==null||"".equals(pwd)){
			Toast.makeText(getSherlockActivity(), "�������û�����������",Toast.LENGTH_LONG).show();
		}
		if(name.equals("aaa")&&pwd.equals("111")){
//			Intent intent = new Intent(getSherlockActivity(),MyMallFragment.class);
//			LoginActivity.this.startActivity(intent);
		}
 		}
		
	}
	
}
//	private void setActionbar(){
//		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//		getSupportActionBar().setDisplayShowHomeEnabled(false);
//		getSupportActionBar().setDisplayShowTitleEnabled(false);
//		getSupportActionBar().setDisplayUseLogoEnabled(false);
////		getSupportActionBar().setLogo(R.drawable.back);
//		getSupportActionBar().setIcon(R.drawable.back);
//		getSupportActionBar().setDisplayShowCustomEnabled(true);
//		getSupportActionBar().setCustomView(R.layout.actionbar_compose);
////		startActionMode(new AnActionModeOfEpicProportions(ComposeActivity.this));
//		ImageView button = (ImageView) findViewById(R.id.compose_back);
//		button.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
////				Toast.makeText(ComposeActivity.this, "button", Toast.LENGTH_SHORT).show();
//				LoginActivity.this.finish();
//			}
//		});
//		
//		TextView titleTextView = (TextView) findViewById(R.id.compose_title);
//		titleTextView.setText("��¼");
//	}
//}
