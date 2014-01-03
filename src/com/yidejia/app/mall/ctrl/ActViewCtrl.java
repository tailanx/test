package com.yidejia.app.mall.ctrl;

import com.yidejia.app.mall.view.GoodsInfoActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ActViewCtrl {
	
	private Activity activity ;
	
	public ActViewCtrl(Activity activity){
		this.activity = activity;
	}
	
	public WebView getWebView(String url){
		WebView webView = new WebView(activity);
		WebSettings settings = webView.getSettings(); 
		settings.setUseWideViewPort(true); 
        settings.setLoadWithOverviewMode(true); 
        webView.setWebViewClient(new WebViewClient(){
        	@Override
            public void onPageFinished(WebView view, String url) {
            };
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				if(isNeed2Jump(url))
					view.loadUrl(url);
				else {
					
					Intent intent = new Intent(activity, GoodsInfoActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("goodsId", getId(url));
					intent.putExtras(bundle);
					activity.startActivity(intent);
				}
				return true;
			}
        	
        });
		webView.loadUrl(url);
		return webView;
	}
	
	private boolean isNeed2Jump(String url){
		return url.endsWith(".html");
	}
	
	private String getId(String url){
		String[] temp = url.split("/");
		return temp[temp.length - 1];
	}
}
