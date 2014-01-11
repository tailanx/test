package com.yidejia.app.mall;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.R;

/**
 * 应用推荐activity
 * @author LiuYong
 *
 */

public class RecommendActivity extends SherlockActivity {
	
	private Button xiazai ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionbar();
		setContentView(R.layout.recommend_item);
		
		xiazai = (Button) findViewById(R.id.xiazai);
		xiazai.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 事件id（"registered id"）的事件pass，其时长持续100毫秒
				StatService.onEventDuration(RecommendActivity.this,
						"download skincarer", "download skincarer", 100);
				Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://static.yidejia.com/apk/skincare.apk"));
				startActivity(intent);
			}
		});

	}
	
	private void setActionbar(){
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setIcon(R.drawable.back);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_common);
		TextView button = (TextView) findViewById(R.id.ab_common_back);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RecommendActivity.this.finish();
			}
		});
		
		TextView titleTextView = (TextView) findViewById(R.id.ab_common_title);
		titleTextView.setText(getResources().getString(R.string.recomend));
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onResume(this);
	}
	
}

