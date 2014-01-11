package com.yidejia.app.mall.goodinfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.R;

public class GoodsDetailActivity extends BaseActivity{
	
	private String TAG = GoodsDetailActivity.class.getName();
	private String url;
	private WebView webView;
	
	private ProgressDialog bar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		url = (bundle != null) ? bundle.getString("url") : "";
//		url = getIntent() != null ? getIntent().getStringExtra("url") : "";
		
		setActionbarConfig();
		setTitle(R.string.produce_imagecomment);
		
		setContentView(R.layout.goods_webview);
		
		bar = new ProgressDialog(this);
		bar.show();
		showView();
		
		Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
	}
	
	private void showView() {
		webView = (WebView) findViewById(R.id.webview);
		WebSettings settings = webView.getSettings(); 
		settings.setUseWideViewPort(false); 
        settings.setLoadWithOverviewMode(true); 
        settings.setJavaScriptEnabled(true);
        // 设置可以使用localStorage
        settings.setDomStorageEnabled(true);
        // 应用可以有数据库
        settings.setDatabaseEnabled(true);   
        String dbPath = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        settings.setDatabasePath(dbPath);
        // 应用可以有缓存
        settings.setAppCacheEnabled(true);            
        String appCaceDir = this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        settings.setAppCachePath(appCaceDir);
        
        loadWebView();
	}
	
	private void loadWebView() {
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				bar.dismiss();
			};

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

		});
		webView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onExceededDatabaseQuota(String url,
					String databaseIdentifier, long quota,
					long estimatedDatabaseSize, long totalQuota,
					QuotaUpdater quotaUpdater) {
				quotaUpdater.updateQuota(estimatedDatabaseSize * 2);
			}

			@Override
			public void onReachedMaxAppCacheSize(long requiredStorage,
					long quota, QuotaUpdater quotaUpdater) {
				quotaUpdater.updateQuota(requiredStorage * 2);
			}

		});
		webView.loadUrl(url);
	}

	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPause(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		StatService.onResume(this);
	}
}
