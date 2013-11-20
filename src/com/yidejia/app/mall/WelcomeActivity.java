package com.yidejia.app.mall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ant.liao.GifView;
import com.ant.liao.GifView.GifImageType;

public class WelcomeActivity extends Activity {
	
	private GifView gifView;
//	private Handler mHandler;
	public static Activity ACTIVITY;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ACTIVITY = this;
		setContentView(R.layout.activity_welcome);
		gifView = (GifView) findViewById(R.id.gif1);
		gifView.setGifImageType(GifImageType.COVER);
		gifView.setGifImage(R.drawable.welcome);
		
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
