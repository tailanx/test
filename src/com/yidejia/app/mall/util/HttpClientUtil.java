package com.yidejia.app.mall.util;

import org.apache.http.HttpStatus;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.opens.asyncokhttpclient.AsyncHttpResponse;
import com.opens.asyncokhttpclient.AsyncOkHttpClient;
import com.opens.asyncokhttpclient.RequestParams;
import com.yidejia.app.mall.R;

public class HttpClientUtil {
	
	private IHttpResp iHttpResp;
	
	private Context context;
	private ProgressDialog progressDialog;  
	private PullToRefreshBase<?> mPullToRefreshBase;
	
	private boolean isShowLoading = false;
	private boolean isShowErrMessage = false;
	
	private AsyncHttpResponse asyncHttpResponse = new AsyncHttpResponse(){

		@Override
		public void onStart() {
			super.onStart();
			if (isShowLoading) {
				if (null != progressDialog) {
					showProgressDialog();
				} else {
					if(null == context) return;
					progressDialog = new ProgressDialog(context);
					setProgressStyle();
					showProgressDialog();
				}
			}
		}

		@Override
		public void onFinish() {
			super.onFinish();
			if(null != mPullToRefreshBase)mPullToRefreshBase.onRefreshComplete();
			if(null != progressDialog && progressDialog.isShowing()) progressDialog.dismiss();
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
			showErrMsg();
		}
		
	};
	
	public HttpClientUtil(){
		//默认构造函数
	}
	
	public HttpClientUtil(Context context){
		this.context = context;
	}
	
	/**设置是否显示等待加载的ProgressDialog**/
	public void setIsShowLoading(boolean isShowLoading){
		this.isShowLoading = isShowLoading;
	}
	
	public void setPullToRefreshView(PullToRefreshListView mPullToRefreshListView){
		this.mPullToRefreshBase = mPullToRefreshListView;
	}
	
	public void setPullToRefreshView(PullToRefreshScrollView mPullToRefreshScrollView){
		this.mPullToRefreshBase = mPullToRefreshScrollView;
	}
	/**设置加载等待的ProgressDialog**/
	public void setProgressDialog(ProgressDialog progressDialog){
		this.progressDialog = progressDialog;
	}
	
	/**获取加载等待的ProgressDialog**/
	public ProgressDialog getProgressDialog(){
		return this.progressDialog;
	}
	
	private void setProgressStyle(){
		if (null == progressDialog) return;
		this.progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.progressDialog.setProgressStyle(R.style.StyleProgressDialog);
	}
	
	private void showProgressDialog(){
		if (null == progressDialog) return;
		progressDialog.show();
		progressDialog.setContentView(R.layout.progress_dialog);
	}
	
	/**设置是否显示联网失败时显示的错误信息**/
	public void setShowErrMessage(boolean isShowErrMessage) {
		this.isShowErrMessage = isShowErrMessage;
	}
	
	/**显示联网失败时提示信息**/
	private void showErrMsg(){
		if(null != context && isShowErrMessage) {
			Toast.makeText(context, context.getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		}
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
