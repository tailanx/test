package com.yidejia.app.mall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.TaskNoEva;

public class EvaluationActivity extends SherlockActivity {
	private LinearLayout layout;
	private MyApplication myApplication;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evaluation);
		myApplication = (MyApplication) getApplication();
		setActionbar();
		layout = (LinearLayout) findViewById(R.id.evaluation_scrollView_linearlayout1);
		TaskNoEva taskNoEva = new TaskNoEva(EvaluationActivity.this, layout);
		taskNoEva.getWaitingComment(myApplication.getUserId(), true);
	}


	private void setActionbar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_common);
		TextView button = (TextView) findViewById(R.id.ab_common_back);// 返回
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EvaluationActivity.this.finish();
			}
		});

		TextView titleTextView = (TextView) findViewById(R.id.ab_common_title);
		titleTextView.setText(getResources().getString(R.string.evaluation));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 4001 && resultCode == 4002){
			layout.removeAllViews();
			TaskNoEva taskNoEva = new TaskNoEva(EvaluationActivity.this, layout);
			taskNoEva.getWaitingComment(myApplication.getUserId(), true);
		}
	}
	
	
}