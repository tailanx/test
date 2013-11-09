package com.yidejia.app.mall.view;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.TaskReturn;


public class ReturnActivity extends SherlockActivity {
	
	private String order_code;
	private String the_date;
	
	private TextView orderCodeTextView;
	private TextView orderDateTextView;
	
	private TaskReturn taskReturn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setActionbar();
		setContentView(R.layout.exchange);
		Bundle bundle = getIntent().getExtras();
		order_code = bundle.getString("orderCode");
		the_date = bundle.getString("orderDate");
		findIds();
		orderCodeTextView.setText(order_code);
		orderDateTextView.setText(the_date);
	}
	
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(taskReturn != null) taskReturn.closeTask();
	}



	private void findIds(){
		orderCodeTextView = (TextView) findViewById(R.id.exchange_biaohao_number);
		orderDateTextView = (TextView) findViewById(R.id.exchange_time_number);
	}
	
	/**
	 *  设置头部
	 */
	private void setActionbar(){
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setIcon(R.drawable.back);
		getSupportActionBar().setCustomView(R.layout.actionbar_common);
		ImageView back = (ImageView) findViewById(R.id.actionbar_left);//����
		Button submit = (Button) findViewById(R.id.actionbar_right);//�ύ
		submit.setText(getResources().getString(R.string.commit));
		submit.setOnClickListener(listener);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ReturnActivity.this.finish();
			}
		});
		TextView title = (TextView) findViewById(R.id.actionbar_title);//����
		title.setText(getResources().getString(R.string.retrun_produce));
	}
	
	/**
	 * 提交按钮点击事件
	 */
	private OnClickListener listener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			taskReturn = new TaskReturn(ReturnActivity.this);
			taskReturn.returnOrder(order_code);
		}
	};
	
	
}
