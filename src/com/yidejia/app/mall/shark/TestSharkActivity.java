package com.yidejia.app.mall.shark;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.R.id;
import com.yidejia.app.mall.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TestSharkActivity extends Activity {
	
	private SharkUtil sharkUtil;
	private TextView tvTest;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userpay);
		tvTest = (TextView) findViewById(R.id.tv_test);
		sharkUtil = new SharkUtil(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		sharkUtil.registerListener(new IShark() {
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				tvTest.setText("ahahahaha");
			}
		});
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		sharkUtil.unregisterListener();
	}
	
}
