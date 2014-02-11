package com.yidejia.app.mall.msg;

import android.os.Bundle;

import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;

public class MsgDetailsActivity extends BaseActivity{

	private String id;	//消息的id
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setActionbar();
		id = getIntent().getStringExtra("msgid");
		
		//TODO setContentView
		
		getMsgDetails();
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
			}
		});
	}
}
