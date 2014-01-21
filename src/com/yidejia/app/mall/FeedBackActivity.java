package com.yidejia.app.mall;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class FeedBackActivity extends SherlockFragmentActivity implements View.OnClickListener{
	
	private EditText opinionEditText;
	private EditText contactEditText;
	private Button commitButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setActionBarConfig();
//		setContentView(R.layout.feedback_activity);
		initView();
	}
	/**
	 * 初始化控件
	 */
	private void initView(){
//		opinionEditText = (EditText) findViewById(R.id.feedback_opinion_text);
//		contactEditText = (EditText) findViewById(R.id.feedback_contact_text);
//		commitButton = (Button) findViewById(R.id.feedback_commit_btn);
		initListener();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
//		case R.id.feedback_commit_btn:
//			commitListener();
//			break;
		default:
			break;
		}
	}
	
	/**
	 * 初始化事件监听
	 */
	private void initListener(){
		commitButton.setOnClickListener(this);
	}
	
	private void commitListener(){
		String opinionString = opinionEditText.getText().toString();
		String contactString = contactEditText.getText().toString();
		if(TextUtils.isEmpty(opinionString)){
//			Toast.makeText(this, getResources().getString(R.string.feedback_opinion), Toast.LENGTH_SHORT).show();
			return;
		}
		commitOpinion(opinionString, contactString);
	}
	
	/**
	 * 提交意见反馈
	 * @param opinionString 意见
	 * @param contactString 联系方式
	 */
	private void commitOpinion(String opinionString, String contactString){
		
	}
	
	private void setActionBarConfig(){
		getSupportActionBar().setDisplayShowCustomEnabled(true);
//		getSupportActionBar().setCustomView(R.layout.actionbar_search_result);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		
		ImageView backImageView = (ImageView) findViewById(R.id.actionbar_left);
		backImageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FeedBackActivity.this.finish();
			}
		});
		
		TextView titleTextView = (TextView) findViewById(R.id.actionbar_title);
//		titleTextView.setText(R.string.feedback);
		
		Button actionbar_right = (Button) findViewById(R.id.actionbar_right);
		actionbar_right.setVisibility(View.GONE);
	}

}
