package com.yidejia.app.mall;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.R.id;
import com.yidejia.app.mall.R.layout;
import com.yidejia.app.mall.R.string;
import com.yidejia.app.mall.ctrl.ActViewCtrl;

public class ActiveGoActivity extends SherlockActivity {
	
	private String url = "http://m.yidejia.com/active.html";
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActViewCtrl viewCtrl = new ActViewCtrl(this);
		mWebView = viewCtrl.getWebView(url);
		setContentView(mWebView);
		setActionbarConfig();
	}
	
	private void setActionbarConfig() {
		getSupportActionBar().setCustomView(R.layout.actionbar_compose);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		TextView titleTextView = (TextView) findViewById(R.id.compose_title);
		// titleTextView.setText("商品展示") ;
		titleTextView.setText(getResources().getString(R.string.main_event_text));
		ImageView leftImageView = (ImageView) findViewById(R.id.compose_back);

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
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		StatService.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		StatService.onResume(this);
	}
}
