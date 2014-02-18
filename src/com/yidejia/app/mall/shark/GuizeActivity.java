package com.yidejia.app.mall.shark;

import android.os.Bundle;
import android.webkit.WebView;

import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.R;

public class GuizeActivity extends BaseActivity {
	private WebView webView;
	private String url = "http://m.yidejia.com/couponrules.html";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setActionbarConfig();
		setTitle(getResources().getString(R.string.shared_yguize));
		setContentView(R.layout.goods_webview);
		webView = (WebView) findViewById(R.id.webview);
		webView.loadUrl(url);
	}
	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "摇一摇规则");
	}
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "摇一摇规则");
	}
}
