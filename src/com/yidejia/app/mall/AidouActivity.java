package com.yidejia.app.mall;

import com.baidu.mobstat.StatService;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class AidouActivity extends BaseActivity {
	private MyApplication myApplication;
	private WebView webView;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.coupons);
		myApplication = (MyApplication) getApplication();
		setActionbarConfig();
		setTitle(getResources().getString(R.string.my_aidu));
		TextView mtextview = (TextView) findViewById(R.id.coupons_text);
		mtextview.setText(getResources().getString(R.string.my_aidu_sum));
		TextView sum = (TextView) findViewById(R.id.jiefen);
		if (null == myApplication.getAidou()
				|| "".equals(myApplication.getAidou())) {
			sum.setText("0");
		} else {
			sum.setText(myApplication.getAidou());
		}
		webView = (WebView) findViewById(R.id.wb_webView);
		webView.setBackgroundColor(0);
		webView.setBackgroundColor(getResources().getColor(R.color.white));
		webView.loadUrl("http://m.yidejia.com/regterms.html");
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
}
