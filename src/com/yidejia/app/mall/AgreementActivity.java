package com.yidejia.app.mall;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.yidejia.app.mall.R;

/**
 * 服务条款页面
 * @author LiuYong
 *
 */
public class AgreementActivity extends SherlockFragmentActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agreement_layout);
		setActionbar();
	}
	
	private void setActionbar(){
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_compose);
		TextView title = (TextView) findViewById(R.id.compose_title);
		title.setText("伊的家服务条款");
		ImageView back = (ImageView) findViewById(R.id.compose_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				AgreementActivity.this.finish();
			}
		});
	}
	
}