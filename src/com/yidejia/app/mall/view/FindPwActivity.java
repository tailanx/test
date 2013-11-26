package com.yidejia.app.mall.view;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.task.TaskGetCode;
import com.yidejia.app.mall.task.TaskReset;
import com.yidejia.app.mall.util.IsPhone;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FindPwActivity extends SherlockActivity {

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setActionbar();
		
		setContentView(R.layout.find_password);
		
		findIds();
		
		obtain_imageView.setOnClickListener(obtainListener);
		
		okButton.setOnClickListener(okListener);
	}
	
	private void findIds(){
		acount_textview = (EditText) findViewById(R.id.my_mall_find_edittext_account);
		obtain_textView = (EditText) findViewById(R.id.my_mall_find_obtain);
		obtain_imageView = (ImageView) findViewById(R.id.my_mall_find_password_validation_button);
		psw_textview = (EditText) findViewById(R.id.my_mall_find_password);
		psw_again_textview = (EditText) findViewById(R.id.my_mall_find_confirm_password);
		okButton = (Button) findViewById(R.id.find_find_confirm_button);
	}
	
	private OnClickListener okListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			resetListener();
		}
	};
	
	/**
	 * 验证码事件
	 */
	private OnClickListener obtainListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			getCodeListener();
		}
	};
	
	
	/**
	 * 获取验证码的事件监听
	 */
	private void getCodeListener(){
		account = acount_textview.getText().toString().trim();//账号
		boolean isphone = IsPhone.isMobileNO(account);
		if(!isphone){
			Toast.makeText(FindPwActivity.this,
					getResources().getString(R.string.phone),
					Toast.LENGTH_SHORT).show();
			return;
		}
		getCodeTask = new TaskGetCode(FindPwActivity.this);
		getCodeTask.getCode(account);
	}
	
	
	/**
	 * 注册按钮点击事件监听
	 */
	private void resetListener(){
		account = acount_textview.getText().toString().trim();//账号
		pwd = psw_textview.getText().toString().trim();//密码
		confirmPwd = psw_again_textview.getText().toString().trim();//重复密码
		obtain = obtain_textView.getText().toString().trim();//验证码
		
		boolean phone = IsPhone.isMobileNO(account);
		if (phone) {
			if (obtain == null || "".equals(obtain)) {//验证码为空
				Toast.makeText(FindPwActivity.this,
						getResources().getString(R.string.obtain),
						Toast.LENGTH_LONG).show();
			} else {
				if (pwd.length() < 6) {//密码小于6位
					Toast.makeText(FindPwActivity.this,
							getResources().getString(R.string.pwd_length),
							Toast.LENGTH_SHORT).show();
				} else {
					if (pwd.equals(confirmPwd)) {
						
						taskReset = new TaskReset(FindPwActivity.this);
						taskReset.resetPsw(account, pwd, obtain);
//						taskCheckCode.checkCode(account, obtain, pwd, ipAddress, "");
					} else {//两次密码不一致
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
		// TODO Auto-generated method stub
		super.onDestroy();
		if(taskReset != null) taskReset.closeTask();
		if(getCodeTask != null) getCodeTask.closeTask();
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
				FindPwActivity.this.finish();
			}
		});
		
		TextView titleTextView = (TextView) findViewById(R.id.compose_title);
		titleTextView.setText("找回密码");
	}
}
