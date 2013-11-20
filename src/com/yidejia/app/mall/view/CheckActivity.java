package com.yidejia.app.mall.view;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.ComposeActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.TaskGetShipLog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CheckActivity extends SherlockActivity {

	private String shipCode;//快递单号
	private String shipCompany;//快递公司
	
	private TextView shipCompanyTextView;//
	private TextView shipCodeTextView;
	private LinearLayout logistics_details_layout;
	
	private TaskGetShipLog task;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionbar();
		//这里父布局要用scrollview
		setContentView(R.layout.check_logistics);
		
		Bundle bundle = getIntent().getExtras();
		shipCode = bundle.getString("shipCode");
		shipCompany = bundle.getString("shipCompany");
		
		findIds();
		shipCompanyTextView.setText(shipCompany);
//		shipCodeTextView.setText("6350485541");
		shipCodeTextView.setText(shipCode);
		
		task = new TaskGetShipLog(CheckActivity.this, logistics_details_layout);
//		task.getShipLogs("6350485541");
		task.getShipLogs(shipCode);
	}
	
	
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(task != null) task.closeTask();
	}




	private void findIds(){
		shipCompanyTextView = (TextView) findViewById(R.id.check_logistics_name);
		shipCodeTextView = (TextView) findViewById(R.id.check_logistics_number);
		logistics_details_layout = (LinearLayout) findViewById(R.id.logistics_details_layout);
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
		ImageView leftButton = (ImageView) findViewById(R.id.compose_back);
		leftButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Toast.makeText(ComposeActivity.this, "button", Toast.LENGTH_SHORT).show();
//				Intent intent = new Intent(AddressActivity.this,MyMallActivity.class);
//				startActivity(intent);
				//结束当前Activity；
				CheckActivity.this.finish();
			}
		});
		
		TextView titleTextView = (TextView) findViewById(R.id.compose_title);
		titleTextView.setText(getResources().getString(R.string.check_logistics));
	}
}
