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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RecommendActivity extends SherlockActivity {
//	public void doClick(View v){
//		switch (v.getId()) {
//		case R.id.my_collect_button1:
//			Intent intent = new Intent(MyCollectActivity.this,MyMallActivity.class);
//			startActivity(intent);
//			//����ǰActivity��
//			MyCollectActivity.this.finish();
//			break;
//		
//		}
//	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setActionbar();
		setContentView(R.layout.recommend);
		LinearLayout layout = (LinearLayout) findViewById(R.id.recommend_scrollView_linearlayout1);
		

		View person1 = getLayoutInflater().inflate(R.layout.recommend_item, null);
		View person2 = getLayoutInflater().inflate(R.layout.recommend_item, null);
//		TextView textView = (TextView) person.findViewById(R.id.wait_pay_order_item_textview1);
//		TextView textView1 = (TextView) person.findViewById(R.id.wait_pay_order_item_textview3);
//		TextView textView2 = (TextView) person.findViewById(R.id.wait_pay_order_item_textview4);
//		TextView textView3 = (TextView) person.findViewById(R.id.wait_pay_order_item_textview5);
//		TextView textView4 = (TextView) person.findViewById(R.id.wait_pay_order_item_textview6);
//		TextView textView5 = (TextView) person.findViewById(R.id.wait_pay_order_item_textview7);
//
//		textView.setText("����״̬������ɶ�����");
//		textView1.setText("��������ss");
//		textView2.setText("��160.00");
//		textView2.setText("������x1");
//		textView2.setText("��������160.00");
//		textView2.setText("������x2");
		layout.addView(person1);
		layout.addView(person2);
		
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
				RecommendActivity.this.finish();
			}
		});
		
		TextView titleTextView = (TextView) findViewById(R.id.compose_title);
		titleTextView.setText(getResources().getString(R.string.recomend));
	}
}

