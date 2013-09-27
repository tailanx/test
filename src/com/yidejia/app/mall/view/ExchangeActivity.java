package com.yidejia.app.mall.view;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.yidejia.app.mall.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ExchangeActivity extends SherlockFragmentActivity implements OnClickListener {
//	private Button mButton; 
	public void setupShow(){
		LinearLayout layout = (LinearLayout) findViewById(R.id.return_exchange_scrollView_linearlayout1);
//		
//		
		View person = getLayoutInflater().inflate(R.layout.return_exchange1_item, null);
		layout.addView(person);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	 
		super.onCreate(savedInstanceState);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setActionbar();
		setContentView(R.layout.return_exchange1);
		LinearLayout person = (LinearLayout) findViewById(R.id.return_exchange_scrollView_linearlayout1);
		person.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ExchangeActivity.this,ReturnActivity.class);
				ExchangeActivity.this.startActivity(intent);
				
			}
		});
		setupShow();
		
//		mButton.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {// TODO Auto-generated method stub
//				Intent intent = new Intent(ExchangeActivity.this,MyMallActivity.class);
//				ExchangeActivity.this.startActivity(intent);
//				ExchangeActivity.this.finish();
//			}
//		});
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
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
				ExchangeActivity.this.finish();
			}
		});
		
		TextView titleTextView = (TextView) findViewById(R.id.compose_title);
		titleTextView.setText("ÍË»»»õ");
	}

}
