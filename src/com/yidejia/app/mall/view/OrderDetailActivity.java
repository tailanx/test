package com.yidejia.app.mall.view;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.yidejia.app.mall.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class OrderDetailActivity extends SherlockFragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setActionBar();
		setContentView(R.layout.order_detail);
		RelativeLayout person = (RelativeLayout) findViewById(R.id.order_detail_relative2);
		View view = getLayoutInflater().inflate(R.layout.order_detail_item,
				null);
		person.addView(view);
	}

	private void setActionBar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setCustomView(R.layout.actionbar_compose);
		ImageView back = (ImageView) findViewById(R.id.compose_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				OrderDetailActivity.this.finish();
			}
		});
		getSupportActionBar().setIcon(R.drawable.back);
		TextView title = (TextView) findViewById(R.id.compose_title);
		title.setText("∂©µ•œÍ«È");
	}

//	public void doClick(View v) {
//		switch (v.getId()) {
//		case R.id.order_detail_pay:
//			Intent intent = new Intent(this, PayActivity.class);
//			startActivity(intent);
//			break;
//
//		}
//	}
}
