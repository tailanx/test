package com.yidejia.app.mall;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.ctrl.ActViewCtrl;

public class ActiveGoActivity extends SherlockActivity {
	
	private String url = "http://m.yidejia.com/active.html";
	private WebView mWebView;
	
	private TextView titleTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		url = getIntent().getStringExtra("url");
		if(TextUtils.isEmpty(url)){
			url = "http://m.yidejia.com/active.html";
		}
		
		setActionbarConfig();
		
		ActViewCtrl viewCtrl = new ActViewCtrl(this);
		viewCtrl.setTitleTextView(titleTextView);
		mWebView = viewCtrl.getWebView(url);
		setContentView(mWebView);
	}
	
	private void setActionbarConfig() {
		getSupportActionBar().setCustomView(R.layout.actionbar_common);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		titleTextView = (TextView) findViewById(R.id.ab_common_title);
		// titleTextView.setText("商品展示") ;
		titleTextView.setText(getResources().getString(R.string.main_event_text));
		TextView leftImageView = (TextView) findViewById(R.id.ab_common_back);

		leftImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(mWebView.canGoBack()) {
					mWebView.goBack();
					return;
				}
				ActiveGoActivity.this.finish();
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
			mWebView.goBack();// 返回前一个页面
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	
	@Override
	protected void onResume() {
		super.onResume();
//		StatService.onResume(this);
		StatService.onPageStart(this, "活动馆页面");
	}

	@Override
	protected void onPause() {
		super.onPause();
//		StatService.onPause(this);
		StatService.onPageEnd(this, "活动馆页面");
	}
}
