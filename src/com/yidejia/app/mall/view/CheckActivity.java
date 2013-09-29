package com.yidejia.app.mall.view;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.ComposeActivity;
import com.yidejia.app.mall.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CheckActivity extends SherlockActivity {
	/*
	public void doClick(View v){
		switch (v.getId()) {
		case R.id.address_management_button1:
			Intent intent = new Intent(AddressActivity.this,MyMallActivity.class);
			startActivity(intent);
			//结束当前Activity；
			AddressActivity.this.finish();
			break;
		case R.id.address_management_button2:
			Intent intent2 = new Intent(AddressActivity.this,NewAddressActivity.class);
			startActivity(intent2);
			//结束当前Activity；
			AddressActivity.this.finish();
			break;
		}
	}
	*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionbar();
		//这里父布局要用scrollview
		setContentView(R.layout.check_logistics);
		

		
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
		ImageView leftButton = (ImageView) findViewById(R.id.compose_back);
		leftButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Toast.makeText(ComposeActivity.this, "button", Toast.LENGTH_SHORT).show();
//				Intent intent = new Intent(AddressActivity.this,MyMallActivity.class);
//				startActivity(intent);
				//结束当前Activity；
				CheckActivity.this.finish();
			}
		});
		
		TextView titleTextView = (TextView) findViewById(R.id.compose_title);
		titleTextView.setText("查看物流");
	}
}
