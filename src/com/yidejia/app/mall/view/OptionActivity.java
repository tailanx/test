package com.yidejia.app.mall.view;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.R;

/**
 * 没用的反馈界面
 * @author LiuYong
 *
 */
public class OptionActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionbar();

		setContentView(R.layout.opinion);

	}

	private void setActionbar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setIcon(R.drawable.back);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_common);
		ImageView leftButton = (ImageView) findViewById(R.id.actionbar_left);
		Button rightButton = (Button) findViewById(R.id.actionbar_right);
		rightButton.setText(getResources().getString(R.string.commit));
		rightButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Toast.makeText(OptionActivity.this,
						getResources().getString(R.string.commit_compelte),
						Toast.LENGTH_SHORT).show();
				OptionActivity.this.finish();
			}
		});
		leftButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				OptionActivity.this.finish();
			}
		});

		TextView titleTextView = (TextView) findViewById(R.id.actionbar_title);
		titleTextView.setText(getResources().getString(R.string.option));
	}
}
