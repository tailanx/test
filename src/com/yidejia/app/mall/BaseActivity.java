package com.yidejia.app.mall;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.yidejia.app.mall.view.LoginActivity;

public class BaseActivity extends SherlockFragmentActivity {

	private TextView tvTitle;

	public void setActionbarConfig() {
		getSupportActionBar().setCustomView(R.layout.actionbar_common);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		tvTitle = ((TextView) findViewById(R.id.ab_common_title));

		TextView tvBack = (TextView) findViewById(R.id.ab_common_back);

		tvBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				BaseActivity.this.finish();
			}
		});
	}

	public void setTitle(String text) {
		tvTitle.setText(text);
	}

	public void setTitle(int resid) {
		tvTitle.setText(resid);
	}
	
	/** 检查是否已登录 **/
	public boolean isLogin() {
		if (!MyApplication.getInstance().getIsLogin()) {
			Toast.makeText(this,
					getResources().getString(R.string.please_login),
					Toast.LENGTH_LONG).show();
			Intent intent1 = new Intent(this,
					LoginActivity.class);
			startActivity(intent1);
			return false;
		}

		return true;
	}

}
