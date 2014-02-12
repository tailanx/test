package com.yidejia.app.mall.msg;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.MsgCenter;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;

public class MsgDetailsActivity extends BaseActivity{

	private String id;	//消息的id
	
	private TextView tvTitle;
	private TextView tvTime;
	private TextView tvDetails;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setActionbar();
		id = getIntent().getStringExtra("msgid");
		
		//TODO setContentView
		setContentView(R.layout.activity_msg_details);
		init();
		getMsgDetails();
	}
	
	private void init(){
		tvDetails = (TextView) findViewById(R.id.tv_msg_details);
		tvTime = (TextView) findViewById(R.id.tv_msg_time);
		tvTitle = (TextView) findViewById(R.id.tv_msg_title);
		
		tvDetails.setMovementMethod(ScrollingMovementMethod.getInstance());
	}

	private void setActionbar(){
		setActionbarConfig();
		setTitle("消息详情");
	}
	
	private void getMsgDetails(){
		String url = new JNICallBack().getHttp4MsgDetails(id);
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		httpClientUtil.getHttpResp(url, new IHttpResp() {
			
			@Override
			public void success(String content) {
				//TODO
				ParseMsg parseMsg = new ParseMsg();
				boolean isSuccess = parseMsg.parseMsgDetails(content);
				MsgCenter msgCenter = parseMsg.getMsgCenter();
				if(isSuccess && null != msgCenter) {
					tvDetails.setText(msgCenter.getMsg());
					tvTime.setText(msgCenter.getTime());
					tvTitle.setText(msgCenter.getTitle());
				}
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "消息详情页面");
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "消息详情页面");
	}
}
