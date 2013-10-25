package com.yidejia.app.mall.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.R;

public class PersonEvaluationActivity extends SherlockActivity {
//	public void doClick(View v){
//		Intent intent = new Intent();
//		switch (v.getId()) {
//		case R.id.evaluation_person_edit://����
//			intent.setClass(this, AlreadyComActivity.class);
//			break;
//
//		}
//		startActivity(intent);
//		finish();
//	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setActionbar();
		setContentView(R.layout.evaluation_person);
		
	}
	
	private void setActionbar(){
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
//		getSupportActionBar().setLogo(R.drawable.back);
		getSupportActionBar().setIcon(R.drawable.back);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_common);
//		startActionMode(new AnActionModeOfEpicProportions(ComposeActivity.this));
		ImageView leftButton = (ImageView) findViewById(R.id.actionbar_left);
		leftButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Toast.makeText(ComposeActivity.this, "button", Toast.LENGTH_SHORT).show();
//				Intent intent = new Intent(AddressActivity.this,MyMallActivity.class);
//				startActivity(intent);
				//����ǰActivity��
				PersonEvaluationActivity.this.finish();
			}
		});
		Button rightButton = (Button) findViewById(R.id.actionbar_right);
		rightButton.setText(getResources().getString(R.string.commit));
		rightButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent(PersonEvaluationActivity.this, CstmPayActivity.class);
				startActivity(intent2);
				//����ǰActivity��
				PersonEvaluationActivity.this.finish();
			}
		});
		
		TextView titleTextView = (TextView) findViewById(R.id.actionbar_title);
		titleTextView.setText(getResources().getString(R.string.person_evaluation));
	}
}