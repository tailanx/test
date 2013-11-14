package com.yidejia.app.mall.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.TaskSaveComm;
import com.yidejia.app.mall.datamanage.UserCommentDataManage;

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
	private UserCommentDataManage userDataManage;
	private MyApplication myApplication;
	private TextView commentContext;
	private RatingBar commRate;
	private String goodsId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		userDataManage = new UserCommentDataManage(this);
		myApplication = (MyApplication) getApplication();
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setActionbar();
		setContentView(R.layout.evaluation_person);
		goodsId = getIntent().getStringExtra("goodsId");
		findIds();
	}
	
	private void findIds(){
		commRate = (RatingBar) findViewById(R.id.ratingBar1);
		commentContext = (TextView) findViewById(R.id.comment_context);
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
//				Intent intent2 = new Intent(PersonEvaluationActivity.this, CstmPayActivity.class);
//				startActivity(intent2);
//				userDataManage.commitComment(11+"", myApplication.getUserId(), myApplication.getNick(), , value, date)
//				PersonEvaluationActivity.this.finish();
				String uid = myApplication.getUserId();
				String nick = myApplication.getNick();
				String commentText = commentContext.getText().toString();
				if("".equals(commentText.trim()) || null == commentText.trim()){
					Toast.makeText(PersonEvaluationActivity.this, "内容不能为空", Toast.LENGTH_LONG).show();
					return;
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String dateString = sdf.format(new Date());
				int rate = (int) commRate.getRating();
				TaskSaveComm taskSaveComm = new TaskSaveComm(PersonEvaluationActivity.this);
				taskSaveComm.saveComm(goodsId, uid, nick, getRateTitle(rate), commentText, dateString);
			}
		});
		
		TextView titleTextView = (TextView) findViewById(R.id.actionbar_title);
		titleTextView.setText(getResources().getString(R.string.person_evaluation));
	}
	
	private String getRateTitle(int rate){
		String[] titleStrings = {"不满意","一般般","还可以","满意","很满意"};
		Log.i("title", titleStrings[rate - 1]);
		return titleStrings[rate - 1];
	}
}