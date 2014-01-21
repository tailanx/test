package com.yidejia.app.mall.evaluation;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.opens.asyncokhttpclient.AsyncHttpResponse;
import com.opens.asyncokhttpclient.AsyncOkHttpClient;
import com.opens.asyncokhttpclient.RequestParams;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
//import com.yidejia.app.mall.datamanage.TaskSaveComm;
import com.yidejia.app.mall.jni.JNICallBack;

/**用户评论商品**/
public class PersonEvaluationActivity extends BaseActivity {
	private MyApplication myApplication;
	private TextView commentContext;
	private RatingBar commRate;
	private String goodsId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApplication = (MyApplication) getApplication();
//		setActionbar();
		setActionbarConfig();
		setTitle(R.string.person_evaluation);
		setRightMenu();
		setContentView(R.layout.evaluation_person);
		goodsId = getIntent().getStringExtra("goodsId");
		findIds();
	}
	
	private void findIds(){
		commRate = (RatingBar) findViewById(R.id.ratingBar1);
		commentContext = (TextView) findViewById(R.id.comment_context);
	}
	
	private void setRightMenu(){
		TextView rightButton = (TextView)findViewById(R.id.ab_common_tv_right);
		rightButton.setVisibility(View.VISIBLE);
		rightButton.setText(getResources().getString(R.string.commit));
		rightButton.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("SimpleDateFormat")
			@Override
			public void onClick(View v) {
				String uid = myApplication.getUserId();
				String nick = myApplication.getNick();
				String comment = commentContext.getText().toString().trim();
				if(TextUtils.isEmpty(comment)){
					Toast.makeText(PersonEvaluationActivity.this, "内容不能为空", Toast.LENGTH_LONG).show();
					return;
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String dateString = sdf.format(new Date());
				int rate = (int) commRate.getRating();
//				TaskSaveComm taskSaveComm = new TaskSaveComm(PersonEvaluationActivity.this);
//				taskSaveComm.saveComm(goodsId, uid, nick, getRateTitle(rate), commentText, dateString);
				saveComm(uid, nick, getRateTitle(rate), comment, dateString);
			}
		});
	}
	
	private void saveComm(String uid, String nick, String title, String comment, String date){
		if(TextUtils.isEmpty(nick)) nick = uid;
		String param = new JNICallBack().getHttp4SaveComment(goodsId, uid, nick, title, comment, date);
		
		String url = new JNICallBack().HTTPURL;
		
		RequestParams requestParams = new RequestParams();
		requestParams.put(param);
		
		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.post(url, requestParams, new AsyncHttpResponse(){

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, content);
				if(HttpStatus.SC_OK == statusCode){
					try {
						JSONObject httpObject = new JSONObject(content);
						int code = httpObject.getInt("code");
						if (code == 1) {
							Toast.makeText(PersonEvaluationActivity.this, "提交评论成功！", Toast.LENGTH_LONG).show();
							int resultCode = 4002;
							PersonEvaluationActivity.this.setResult(resultCode);//通知更新界面
							PersonEvaluationActivity.this.finish();
						} else {
							Toast.makeText(PersonEvaluationActivity.this, "提交评论失败！", Toast.LENGTH_LONG).show();	
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onError(error, content);
				Toast.makeText(PersonEvaluationActivity.this, getResources().getString(R.string.bad_network), Toast.LENGTH_LONG).show();	
			}
			
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
//		StatService.onResume(this);
		StatService.onPageStart(this, "评价商品页面");
	}

	@Override
	protected void onPause() {
		super.onPause();
//		StatService.onPause(this);
		StatService.onPageEnd(this, "评价商品页面");
	}
	
	private String getRateTitle(int rate){
		String[] titleStrings = {"不满意","一般般","还可以","满意","很满意"};
		Log.i("title", titleStrings[rate - 1]);
		return titleStrings[rate - 1];
	}
}