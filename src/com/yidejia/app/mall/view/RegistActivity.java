package com.yidejia.app.mall.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.ctrl.IpAddress;
import com.yidejia.app.mall.datamanage.UserDatamanage;

public class RegistActivity extends SherlockActivity  {
	public Button mback;//����
	private UserDatamanage userManage;
	private EditText mZhanghao;
	private EditText mPws;
	private EditText mEditText;
	private EditText mEditText2;
	private Button mButton;
	
	private String account;
	private String pwd;
	private String confirmPwd;
	private String obtain;
	
	private IpAddress ip;

//	public void doClick(View v){
//		Intent  intent = new Intent();
//		switch (v.getId()) {
//		case R.id.my_mall_regist_cancel:
//			intent.setClass(this, LoginActivity.class);
//			break;
//		}
//		startActivity(intent);
//		this.finish();
//	}
	private void setupShow(){
		mZhanghao = (EditText) findViewById(R.id.my_mall_regist_edittext_account);
		mPws = (EditText) findViewById(R.id.my_mall_regist_password);
		mEditText = (EditText) findViewById(R.id.my_mall_regist_confirm_password);
		mEditText2 = (EditText) findViewById(R.id.my_mall_regist_obtain);
		mButton = (Button) findViewById(R.id.find_password_confirm_button);
		
		account = mZhanghao.getText().toString();
		pwd= mPws.getText().toString();
		confirmPwd = mEditText.getText().toString();
		obtain = mEditText2.getText().toString();
	}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	try {
			super.onCreate(savedInstanceState);
//    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setActionbar();
			userManage = new UserDatamanage(RegistActivity.this);
			ip = new IpAddress();
			setContentView(R.layout.my_mall_regist);
			setupShow();
			mButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(obtain.isEmpty()){
						Toast.makeText(RegistActivity.this, "�������Ϣ��֤��",Toast.LENGTH_LONG).show();
					}else{
						boolean isSucess = userManage.register(account, pwd, confirmPwd, ip.getIpAddress());
					Log.i("info", isSucess + "");
					}
				}
			});
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(RegistActivity.this, "���粻����", Toast.LENGTH_LONG).show();
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
				RegistActivity.this.finish();
			}
		});
		
		TextView titleTextView = (TextView) findViewById(R.id.compose_title);
		titleTextView.setText("ע��");
	}
}
