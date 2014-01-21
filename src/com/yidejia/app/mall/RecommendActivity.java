package com.yidejia.app.mall;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.R;

/**
 * 应用推荐activity
 * @author LiuYong
 *
 */

public class RecommendActivity extends BaseActivity {
	
	private Button xiazai ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setActionbar();
		setActionbarConfig();
		setTitle(getResources().getString(R.string.recomend));
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
	
	@Override
	protected void onResume() {
		super.onResume();
//		StatService.onResume(this);
		StatService.onPageStart(this, "应用推荐页面");
	}

	@Override
	protected void onPause() {
		super.onPause();
//		StatService.onPause(this);
		StatService.onPageEnd(this, "应用推荐页面");
	}
	
}

