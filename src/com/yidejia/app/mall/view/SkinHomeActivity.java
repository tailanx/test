package com.yidejia.app.mall.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.yidejia.app.mall.R;

public class SkinHomeActivity extends Activity implements OnClickListener{

	public String getCpsNumber() {
		return cpsNumber;
	}
	public void setCpsNumber(String cpsNumber) {
		this.cpsNumber = cpsNumber;
	}
	
	private ImageView back;//返回
	private EditText number;
	private ImageView startSkin;
	private String cpsNumber;
	private void setupShow(){
		back = (ImageView) findViewById(R.id.skin_test_home_back);
		number = (EditText) findViewById(R.id.skin_test_home_number);
		startSkin =  (ImageView) findViewById(R.id.skin_test_home_startSkin);
		}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	setContentView(R.layout.skin_test_home_pag);
	setupShow();
	back.setOnClickListener(this);
	startSkin.setOnClickListener(this);
	cpsNumber = number.getText().toString();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.skin_test_home_back:
			this.finish();
			break;

		case R.id.skin_test_home_startSkin:
			Intent intent = new Intent(SkinHomeActivity.this, SkinQuesActivity.class);
			startActivity(intent);
			this.finish();
			break;
		}
	}
	
	
	
}
