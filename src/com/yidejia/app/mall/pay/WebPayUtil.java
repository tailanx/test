package com.yidejia.app.mall.pay;

import com.yidejia.app.mall.order.AllOrderActivity;
import com.yidejia.app.mall.order.WaitPayActivity;
import com.yidejia.app.mall.util.ActivityIntentUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
//				view.loadUrl(url);
				if (isNeed2Jump(url)) {
					view.loadUrl(url);
				} 
				
				return true;
			}

		});
	}
	
	private boolean isNeed2Jump(String url) {
		String[] urlArray = url.split("/");
		if(urlArray.length <= 1) return true;
		url = urlArray[urlArray.length - 1];
		if(url.contains("success")) {
//			Intent intent = new Intent(activity, AllOrderActivity.class);
			ActivityIntentUtil.intentActivityAndFinish(activity, AllOrderActivity.class);
			return false;
		} else if(url.contains("failure")){
			ActivityIntentUtil.intentActivityAndFinish(activity, AllOrderActivity.class);
			return false;
		}
		return true;
	}
}
