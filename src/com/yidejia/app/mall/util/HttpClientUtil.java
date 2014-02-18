package com.yidejia.app.mall.util;

import org.apache.http.HttpStatus;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.opens.asyncokhttpclient.AsyncHttpResponse;
import com.opens.asyncokhttpclient.AsyncOkHttpClient;
import com.opens.asyncokhttpclient.RequestParams;

public class HttpClientUtil {
	
	private IHttpResp iHttpResp;
	
	private PullToRefreshBase<?> mPullToRefreshBase;
	
	private AsyncHttpResponse asyncHttpResponse = new AsyncHttpResponse(){

		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			super.onFinish();
			if(null != mPullToRefreshBase)mPullToRefreshBase.onRefreshComplete();
		}

		@Override
		public void onSuccess(int statusCode, String content) {
			super.onSuccess(statusCode, content);
			if(HttpStatus.SC_OK == statusCode){
				iHttpResp.success(content);
			}
		}

		@Override
		public void onError(Throwable error, String content) {
			super.onError(error, content);
//			Toast.makeText(activ, text, duration)
		}
		
	};
	
	public void setPullToRefreshView(PullToRefreshListView mPullToRefreshListView){
		this.mPullToRefreshBase = mPullToRefreshListView;
	}
	
	public void setPullToRefreshView(PullToRefreshScrollView mPullToRefreshScrollView){
		this.mPullToRefreshBase = mPullToRefreshScrollView;
	}
	
	/**
	 * 联网获取数据，get方法获取url上的数据，返回数据处理用ihttpresp回调；封装了转圈，获取数据失败等
	 * @param url 链接
	 * @param iHttpResp 回调
	 */
	public void getHttpResp(String url, IHttpResp iHttpResp){
		this.iHttpResp = iHttpResp;
		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.get(url, asyncHttpResponse);
	}
	
	/**
	 * 联网获取数据，post方法获取url上的数据，返回数据处理用ihttpresp回调；封装了转圈，获取数据失败等
	 * @param url 链接
	 * @param param 参数
	 * @param iHttpResp 回调
	 */
	public void getHttpResp(String url, String param, IHttpResp iHttpResp){
		this.iHttpResp = iHttpResp;
		RequestParams requestParams = new RequestParams();
		requestParams.put(param);
		
		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.post(url, requestParams, asyncHttpResponse);
	}
}
