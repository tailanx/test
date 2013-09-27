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

public class MyCollectActivity extends SherlockActivity {
//	public void doClick(View v){
//		switch (v.getId()) {
//		case R.id.my_collect_button1:
//			Intent intent = new Intent(MyCollectActivity.this,MyMallActivity.class);
//			startActivity(intent);
//			//结束当前Activity；
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
		setContentView(R.layout.my_collect);
		LinearLayout layout = (LinearLayout) findViewById(R.id.wmy_collect_scrollView_linearlayout1);

		View person = getLayoutInflater().inflate(R.layout.my_collect_item, null);
//		TextView textView = (TextView) person.findViewById(R.id.wait_pay_order_item_textview1);
//		TextView textView1 = (TextView) person.findViewById(R.id.wait_pay_order_item_textview3);
//		TextView textView2 = (TextView) person.findViewById(R.id.wait_pay_order_item_textview4);
//		TextView textView3 = (TextView) person.findViewById(R.id.wait_pay_order_item_textview5);
//		TextView textView4 = (TextView) person.findViewById(R.id.wait_pay_order_item_textview6);
//		TextView textView5 = (TextView) person.findViewById(R.id.wait_pay_order_item_textview7);
//
//		textView.setText("订单状态：已完成订单！");
//		textView1.setText("生生世世ss");
//		textView2.setText("￥160.00");
//		textView2.setText("数量：x1");
//		textView2.setText("订单金额：￥160.00");
//		textView2.setText("数量：x2");
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
		ImageView button = (ImageView) findViewById(R.id.compose_back);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Toast.makeText(ComposeActivity.this, "button", Toast.LENGTH_SHORT).show();
				MyCollectActivity.this.finish();
			}
		});
		
		TextView titleTextView = (TextView) findViewById(R.id.compose_title);
		titleTextView.setText("我的收藏");
	}
}
