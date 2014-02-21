package com.yidejia.app.mall;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.opens.asyncokhttpclient.AsyncHttpResponse;
import com.opens.asyncokhttpclient.AsyncOkHttpClient;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;
import com.yidejia.app.mall.widget.YLProgressDialog;

public class AidouActivity extends BaseActivity {
//	private MyApplication myApplication;
	private WebView webView;
//	private ProgressDialog bar;
	private TextView sum;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.coupons);
//		myApplication = (MyApplication) getApplication();
		setActionbarConfig();
		setTitle(getResources().getString(R.string.my_aidu));
		TextView mtextview = (TextView) findViewById(R.id.coupons_text);
		mtextview.setText(getResources().getString(R.string.my_aidu_sum));
		sum = (TextView) findViewById(R.id.jiefen);

		webView = (WebView) findViewById(R.id.wb_webView);
		webView.setBackgroundColor(0);
		webView.setBackgroundColor(getResources().getColor(R.color.white));
		webView.loadUrl("http://m.yidejia.com/aidourules.html");

		getAidou();
	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "我的爱豆页面");
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "我的爱豆页面");
	}

	/**
	 * 获取爱豆数目
	 */
	private void getAidou() {
		String url = new JNICallBack().getHttp4GetGold(MyApplication
				.getInstance().getUserId(), MyApplication.getInstance()
				.getToken());
		HttpClientUtil client = new HttpClientUtil(this);
		client.setIsShowLoading(true);
		client.getHttpResp(url, new IHttpResp() {

			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
				JSONObject object;
				try {
					object = new JSONObject(content);
					if ("1".equals(object.optString("code"))) {
						String sumAidou = object.optString("response");
						sum.setText(sumAidou);
					} else {
						Toast.makeText(
								AidouActivity.this,
								AidouActivity.this.getResources().getString(
										R.string.bad_network),
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
