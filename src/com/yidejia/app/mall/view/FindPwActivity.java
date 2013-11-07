package com.yidejia.app.mall.view;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class FindPwActivity extends SherlockActivity {
//	public void doClick(View v){
//		Intent intent = new Intent();
//		switch (v.getId()) {
//		case R.id.my_mall_find_cancel:
//			intent.setClass(this,LoginActivity.class);
//			break;
//
//		case R.id.find_find_confirm_button:
//			break;
//		}
//		startActivity(intent);
//		this.finish();
//	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setActionbar();
		
		setContentView(R.layout.find_password);
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
				FindPwActivity.this.finish();
			}
		});
		
		TextView titleTextView = (TextView) findViewById(R.id.compose_title);
		titleTextView.setText("找回密码");
	}
}
