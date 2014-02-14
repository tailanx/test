package com.yidejia.app.mall.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebPayUtil {

	private Activity activity;
	
	public WebPayUtil(Activity activity){
		this.activity = activity;
	}
	
	// 网页支付
	@SuppressLint("SetJavaScriptEnabled")
	public void webPay(String payUrl) {
		// 先得到财付通订单号
		WebView webView = new WebView(activity);
		activity.setContentView(webView);
		webView.loadUrl(payUrl);
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
			};

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

		});
	}
}
