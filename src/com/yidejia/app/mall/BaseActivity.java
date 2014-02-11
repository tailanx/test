package com.yidejia.app.mall;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;

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

}
