package com.yidejia.app.mall.ctrl;


import com.yidejia.app.mall.goodinfo.GoodsInfoActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class ActViewCtrl {
	
	private Activity activity ;
	private TextView tvTitle;
	
	public ActViewCtrl(Activity activity){
		this.activity = activity;
	}
	
	public void setTitleTextView(TextView tvTitle){
		this.tvTitle = tvTitle;
	}
	
	public WebView getWebView(String url){
		WebView webView = new WebView(activity);
		
		WebChromeClient wvcc = new WebChromeClient() {  
            @Override  
            public void onReceivedTitle(WebView view, String title) {  
                super.onReceivedTitle(view, title);  
//                Log.d("ANDROID_LAB", "TITLE=" + title);  
                tvTitle.setText(title);  
            }  
  
        };  
		
        // 设置setWebChromeClient对象  
        webView.setWebChromeClient(wvcc);  
        
		WebSettings settings = webView.getSettings(); 
		settings.setUseWideViewPort(true); 
        settings.setLoadWithOverviewMode(true); 
        webView.setWebViewClient(new WebViewClient(){
        	@Override
            public void onPageFinished(WebView view, String url) {
            };
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (isNeed2Jump(url)) {
					view.loadUrl(url);
				} else {
					Intent intent = new Intent(activity,
							GoodsInfoActivity.class);
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
//		return url.endsWith(".html");
		// 商品的url http://m.yidejia.com/#item/id 
		return !url.contains("/#item/");
	}
	
	private String getId(String url){
		String[] temp = url.split("/");
		return temp[temp.length - 1];
	}
}
