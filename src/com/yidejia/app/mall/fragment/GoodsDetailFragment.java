package com.yidejia.app.mall.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.actionbarsherlock.app.SherlockFragment;
import com.yidejia.app.mall.R;

public class GoodsDetailFragment extends SherlockFragment{
	
	private String TAG = GoodsDetailFragment.class.getName();
	private String url;
	private View view;
	private WebView webView;
	
	public static GoodsDetailFragment newInstance(String urlString){
		GoodsDetailFragment goodsDetailFragment = new GoodsDetailFragment();
		Bundle bundle = new Bundle();
		bundle.putString("url", urlString);
		Log.e("GoodsDetailFragment", urlString);
		goodsDetailFragment.setArguments(bundle);
		return goodsDetailFragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		url = (bundle != null) ? bundle.getString("url") : "";
//		url = bundle.getString("url");
		Log.d(TAG, "TestFragment-----onCreate---" );
		Log.d(TAG, url );
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d(TAG, "TestFragment-----onCreateView");
		view = inflater.inflate(R.layout.goods_webview, container, false);
		webView = (WebView) view.findViewById(R.id.webview);
		WebSettings settings = webView.getSettings(); 
		settings.setUseWideViewPort(false); 
        settings.setLoadWithOverviewMode(true); 
        settings.setJavaScriptEnabled(true);
        // 设置可以使用localStorage
        settings.setDomStorageEnabled(true);
        // 应用可以有数据库
        settings.setDatabaseEnabled(true);   
        String dbPath =getSherlockActivity().getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        settings.setDatabasePath(dbPath);
        // 应用可以有缓存
        settings.setAppCacheEnabled(true);            
        String appCaceDir =getSherlockActivity().getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        settings.setAppCachePath(appCaceDir);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		webView.setWebViewClient(new WebViewClient(){
        	@Override
            public void onPageFinished(WebView view, String url) {
            };
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				
				view.loadUrl(url);
				return true;
			}
			
        	
        });
		webView.setWebChromeClient(new WebChromeClient(){

			@Override
			public void onExceededDatabaseQuota(String url,
					String databaseIdentifier, long quota,
					long estimatedDatabaseSize, long totalQuota,
					QuotaUpdater quotaUpdater) {
				// TODO Auto-generated method stub
//				super.onExceededDatabaseQuota(url, databaseIdentifier, quota,
//						estimatedDatabaseSize, totalQuota, quotaUpdater);
				quotaUpdater.updateQuota(estimatedDatabaseSize * 2); 
			}

			@Override
			public void onReachedMaxAppCacheSize(long requiredStorage,
					long quota, QuotaUpdater quotaUpdater) {
				// TODO Auto-generated method stub
//				super.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
				quotaUpdater.updateQuota(requiredStorage * 2);
			}
			
		});
		webView.loadUrl(url);
	}
	
	
}
