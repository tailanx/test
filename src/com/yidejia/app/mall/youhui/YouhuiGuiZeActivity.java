package com.yidejia.app.mall.youhui;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.R;

public class YouhuiGuiZeActivity extends BaseActivity {

	private WebView webView;
	private TextView back;
	private String url = "http://m.yidejia.com/couponrules.html";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setActionbarConfig();
		setTitle(getString(R.string.youhui_guize));
		setContentView(R.layout.goods_webview);
		webView = (WebView) findViewById(R.id.webview);
		webView.loadUrl(url);
	}
}
