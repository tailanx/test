package com.yidejia.app.mall.qiandao;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.R;

public class QiandaoActivity extends BaseActivity implements OnClickListener {
	private ImageView backImagView;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.qiandao);
		backImagView = (ImageView) findViewById(R.id.iv_qiandao_back);
		backImagView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_qiandao_back:
			this.finish();
			break;

		default:
			break;
		}
	}

}
