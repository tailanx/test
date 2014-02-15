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
import com.yidejia.app.mall.widget.YLProgressDialog;

public class AidouActivity extends BaseActivity {
	private MyApplication myApplication;
	private WebView webView;
	private ProgressDialog bar;
	private TextView sum;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.coupons);
		myApplication = (MyApplication) getApplication();
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
		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.get(url, new AsyncHttpResponse() {
			@SuppressWarnings("static-access")
			@Override
			public void onStart() {
				super.onStart();
				bar = new YLProgressDialog(AidouActivity.this)
						.createLoadingDialog(AidouActivity.this, null);
				bar.setOnCancelListener(new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						bar.dismiss();
					}
				});

			}

			@Override
			public void onError(Throwable error, String content) {
				super.onError(error, content);
				Toast.makeText(
						AidouActivity.this,
						AidouActivity.this.getResources().getString(
								R.string.no_network), Toast.LENGTH_SHORT)
						.show();
			}

			@Override
			public void onFinish() {
				super.onFinish();
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				if (200 == statusCode) {
					bar.cancel();
				}
				JSONObject object;
				try {
					object = new JSONObject(content);
					if ("1".equals(object.getString("code"))) {
						String sumAidou = object.getString("response");
						sum.setText(sumAidou);
					} else {
						Toast.makeText(
								AidouActivity.this,
								AidouActivity.this.getResources().getString(
										R.string.no_network),
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
