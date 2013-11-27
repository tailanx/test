package com.yidejia.app.mall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;


public class WelcomeActivity extends Activity {
	
//	private Handler mHandler;
	public static Activity ACTIVITY;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ACTIVITY = this;
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_welcome);
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WelcomeActivity.this, MainFragmentActivity.class);
				startActivity(intent);
				WelcomeActivity.this.finish();
			}
		}, 2*1000);
	}
	
	
	
}
