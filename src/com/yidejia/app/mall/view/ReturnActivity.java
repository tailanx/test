package com.yidejia.app.mall.view;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.R;


public class ReturnActivity extends SherlockActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setActionbar();
		setContentView(R.layout.exchange);
	}
	private void setActionbar(){
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setIcon(R.drawable.back);
		getSupportActionBar().setCustomView(R.layout.actionbar_common);
		ImageView back = (ImageView) findViewById(R.id.actionbar_left);//返回
		Button submit = (Button) findViewById(R.id.actionbar_right);//提交
		submit.setText("提交");
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ReturnActivity.this.finish();
			}
		});
		TextView title = (TextView) findViewById(R.id.actionbar_title);//标题
		title.setText("申请退换货");
	}
}
