
package com.yidejia.app.mall.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.yidejia.app.mall.MainActivity;
import com.yidejia.app.mall.MainFragmentActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.MyMallActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.ctrl.IpAddress;
import com.yidejia.app.mall.datamanage.UserDatamanage;
import com.yidejia.app.mall.view.FindPwActivity;
import com.yidejia.app.mall.view.RegistActivity;

public class LoginFragment extends SherlockFragment implements OnClickListener{
	private RelativeLayout findPwd;//�һ�����
	private RelativeLayout rapidRegist;//����ע��
	private Button mLogin;
	private EditText stringName;
	private EditText stringPassword;
	private UserDatamanage userManage;
	private IpAddress ipAddress;

	
	private MyMallActivity newFragment;
	
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
		userManage = new UserDatamanage(getSherlockActivity());
		ipAddress = new IpAddress();
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
			getSherlockActivity().startActivity(intent1);
			
			break;

		case R.id.my_mall_login_retrieve_regist://����ע��
			Intent registIntent = new Intent(getSherlockActivity(), RegistActivity.class);
			getSherlockActivity().startActivity(registIntent);
		
			break;
		case R.id.my_mall_login_button://��¼
		String name = stringName.getText().toString();
		String pwd = stringPassword.getText().toString();
		boolean isSucess = userManage.userLogin(name, pwd,ipAddress.getIpAddress());
		Log.i("info", isSucess +"isSucess");
		if(isSucess){
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.main_fragment, newFragment).commit();
//		if(name==null||"".equals(name)){
//			Toast.makeText(getSherlockActivity(), "�������û�����������",Toast.LENGTH_LONG).show();
//		}
//		if(pwd==null||"".equals(pwd)){
//			Toast.makeText(getSherlockActivity(), "�������û�����������",Toast.LENGTH_LONG).show();
//		}
//		if(name.equals("aaa")&&pwd.equals("111")){   //��¼�ɹ�
//			newFragment = new MyMallActivity();
//			((MyApplication)getSherlockActivity().getApplication()).setIsLogin(true);
////			Log.i("info", myApplication.getIsLogin()+"");
//			myApplication.setIsLogin(true);
//			Log.i("info", myApplication.getIsLogin()+"");
//			Message ms = new Message();
//			ms.what = 000;
//			myApplication.getHandler().sendMessage(ms);
		
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


