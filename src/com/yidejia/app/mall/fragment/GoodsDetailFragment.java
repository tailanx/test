package com.yidejia.app.mall.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.actionbarsherlock.app.SherlockFragment;
import com.yidejia.app.mall.R;

public class GoodsDetailFragment extends SherlockFragment{
	
	private String TAG = GoodsDetailFragment.class.getName();
	private String url;
	private View view;
	
	public static GoodsDetailFragment newInstance(String urlString){
		GoodsDetailFragment goodsDetailFragment = new GoodsDetailFragment();
		Bundle bundle = new Bundle();
		bundle.putString("url", urlString);
		goodsDetailFragment.setArguments(bundle);
		return goodsDetailFragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
//		goodsId = (bundle != null) ? bundle.getString("goodsId") : defaultInt;
		url = bundle.getString("url");
		Log.d(TAG, "TestFragment-----onCreate---" );
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d(TAG, "TestFragment-----onCreateView");
		view = inflater.inflate(R.layout.goods_webview, container, false);
		WebView webView = (WebView) view.findViewById(R.id.webview);
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
				
				view.loadUrl(url);
				return true;
			}
        	
        });
		webView.loadUrl(url);
		return view;
	}
}
