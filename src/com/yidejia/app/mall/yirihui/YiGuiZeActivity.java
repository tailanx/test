package com.yidejia.app.mall.yirihui;

import android.os.Bundle;
import android.webkit.WebView;

import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.R;

public class YiGuiZeActivity extends BaseActivity {
	String url = "http://m.yidejia.com/yrhrules.html";
	private WebView webView;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.goods_webview);
		setActionbarConfig();
		setTitle(getString(R.string.yirihui_guize));
		webView = (WebView) findViewById(R.id.webview);
		webView.loadUrl(url);
	}
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "伊日惠规则");
	}
@Override
protected void onPause() {
	super.onPause();
	StatService.onPageEnd(this, "伊日惠规则");
}
}
