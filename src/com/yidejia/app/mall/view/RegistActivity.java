package com.yidejia.app.mall.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.ctrl.IpAddress;
import com.yidejia.app.mall.datamanage.UserDatamanage;

public class RegistActivity extends SherlockActivity {
	public Button mback;// ����
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
	private String ipAddress;
	private CheckBox mBox;

	// public void doClick(View v){
	// Intent intent = new Intent();
	// switch (v.getId()) {
	// case R.id.my_mall_regist_cancel:
	// intent.setClass(this, LoginActivity.class);
	// break;
	// }
	// startActivity(intent);
	// this.finish();
	// }
	private void setupShow() {
		mZhanghao = (EditText) findViewById(R.id.my_mall_regist_edittext_account);
		mPws = (EditText) findViewById(R.id.my_mall_regist_password);
		mEditText = (EditText) findViewById(R.id.my_mall_regist_confirm_password);
		mEditText2 = (EditText) findViewById(R.id.my_mall_regist_obtain);
		mButton = (Button) findViewById(R.id.find_password_confirm_button);
		mBox = (CheckBox) findViewById(R.id.my_mall_regist_checkbox);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		try {
			super.onCreate(savedInstanceState);
			// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
			// WindowManager.LayoutParams.FLAG_FULLSCREEN);
			userManage = new UserDatamanage(RegistActivity.this);
			ip = new IpAddress();
			ipAddress = ip.getIpAddress();
			setContentView(R.layout.my_mall_regist);
			
			setupShow();
			setActionbar();
			mButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					account = mZhanghao.getText().toString();
					pwd = mPws.getText().toString();
					confirmPwd = mEditText.getText().toString();
					obtain = mEditText2.getText().toString();
					// if(!mBox.isChecked()){
					// Toast.makeText(RegistActivity.this, "��ѡ���������",
					// Toast.LENGTH_LONG).show();els
					// }
					boolean phone = isMobileNO(account);
					if(phone){
					if (obtain == null || "".equals(obtain)) {
						Toast.makeText(RegistActivity.this,
								getResources().getString(R.string.obtain),
								Toast.LENGTH_LONG).show();
					} else {
						if (pwd.length() < 6) {
							Toast.makeText(
									RegistActivity.this,
									getResources().getString(
											R.string.pwd_length),
									Toast.LENGTH_SHORT).show();
						} else {
							if (pwd.equals(confirmPwd)) {
								boolean isSucess = userManage.register(account,
										pwd, "", ip.getIpAddress());
								RegistActivity.this.finish();
								if (isSucess) {
									Toast.makeText(
											RegistActivity.this,
											getResources().getString(
													R.string.equal),
											Toast.LENGTH_LONG).show();
								}
								Log.i("info", isSucess + "        isSucess");

							} else {
								Toast.makeText(
										RegistActivity.this,
										getResources().getString(
												R.string.regist_false),
										Toast.LENGTH_LONG).show();
							}
						}
					}
				}else{
					Toast.makeText(RegistActivity.this, getResources().getString(R.string.phone), Toast.LENGTH_SHORT).show();
				}
				}
				
			});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(RegistActivity.this,
					getResources().getString(R.string.no_network),
					Toast.LENGTH_LONG).show();
		}
	}

	private void setActionbar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		// getSupportActionBar().setLogo(R.drawable.back);
		getSupportActionBar().setIcon(R.drawable.back);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_compose);
		// startActionMode(new
		// AnActionModeOfEpicProportions(ComposeActivity.this));
		ImageView button = (ImageView) findViewById(R.id.compose_back);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(ComposeActivity.this, "button",
				// Toast.LENGTH_SHORT).show();
				RegistActivity.this.finish();
			}
		});

		TextView titleTextView = (TextView) findViewById(R.id.compose_title);
		titleTextView.setText(getResources().getString(R.string.regist));
	}
	 public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
	        Matcher m = p.matcher(mobiles);
	        Log.e("info", m.matches()+  "");
	        return m.matches();
	    }
	 
	 
}
