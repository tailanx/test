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
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EvaluationActivity extends SherlockActivity {
		public void doClick(View v){
			Intent intent = new Intent();
			switch (v.getId()) {
//			case R.id.evaluation_button://返回
//				intent.setClass(this, MyMallActivity.class);
//				break;
			case R.id.evaluation_item_evaluation://评价
				intent.setClass(this, PersonEvaluationActivity.class);
			}
			startActivity(intent);
			//结束当前Activity；
			EvaluationActivity.this.finish();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setActionbar();
		setContentView(R.layout.evaluation);
		
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.evaluation_relative2);
		View person = getLayoutInflater().inflate(R.layout.evaluation_item, null);
		
//		TextView textView1 = (TextView) person.findViewById(R.id.ecaluation_item_textview3);
//		TextView textView2 = (TextView) person.findViewById(R.id.ecaluation_item_textview4);
//		TextView textView3 = (TextView) person.findViewById(R.id.ecaluation_item_textview5);
//		TextView textView4 = (TextView) person.findViewById(R.id.ecaluation_item_textview6);
//		TextView textView5 = (TextView) person.findViewById(R.id.ecaluation_item_textview7);
//		
//		textView1.setText("生生世世ssada啊大叔 啊飒飒撒大多数都是撒都是淡淡的淡淡的淡淡的淡淡adasdadasdsadsadasdsadsadsssssssssssssssss");
//		textView2.setText("￥160.00");
//		textView3.setText("数   量：x1");
//		textView3.setText("订单金额：￥160.00");
//		textView3.setText("数    量：x1");
		layout.addView(person);
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
		ImageView button = (ImageView) findViewById(R.id.compose_back);//返回
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Toast.makeText(ComposeActivity.this, "button", Toast.LENGTH_SHORT).show();
				EvaluationActivity.this.finish();
			}
		});
		
		TextView titleTextView = (TextView) findViewById(R.id.compose_title);
		titleTextView.setText(getResources().getString(R.string.evaluation));
	}
}
