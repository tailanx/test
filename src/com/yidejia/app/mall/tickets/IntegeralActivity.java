package com.yidejia.app.mall.tickets;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
//import android.webkit.WebView;
//import android.widget.ImageView;
import android.widget.TextView;




import com.baidu.mobstat.StatService;
//import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.yidejia.app.mall.BaseActivity;
//import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;

public class IntegeralActivity extends BaseActivity implements OnClickListener {
	private FragmentManager manager;
	private FragmentTransaction ft;
	private int currentId;// 设置当前id；
	private TextView integeralTextView;// 积分
	private TextView youhuiquanTexView;// 优惠券
	private Fragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		manager = getSupportFragmentManager();

		// 已取消优惠券只显示积分
		setContentView(R.layout.integeraltop);
		setActionBar();
		fragment = IntegeralFragment.newInstance(0);
		ft = manager.beginTransaction();
		ft.add(R.id.fl_search_result_fragment, fragment);
//		ft.addToBackStack("jifen");
		ft.commit();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
	}

	 /**
	 * 设置头部
	 */
	private void setActionBar() {
		TextView back = (TextView) findViewById(R.id.integeral_back);
		integeralTextView = (TextView) findViewById(R.id.tv_search_result_selled);
		// titleTextView.setText("积分卡券");
		integeralTextView.setSelected(true);
		currentId = 0;
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				IntegeralActivity.this.finish();
			}
		});
		youhuiquanTexView = (TextView) findViewById(R.id.tv_search_result_popularity);// 积分劵
		youhuiquanTexView.setOnClickListener(this);
		integeralTextView.setOnClickListener(this);// 积分
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_search_result_popularity:// 优惠券
			fragment = IntegeralFragment.newInstance(1);
			if (currentId == 1) {
				return;
			} else {
				integeralTextView.setSelected(false);
				youhuiquanTexView.setSelected(true);
				ft = manager.beginTransaction();
				ft.add(R.id.fl_search_result_fragment, fragment);
				ft.commit();
				currentId = 1;
				break;
			}
		case R.id.tv_search_result_selled:// 积分
			fragment = IntegeralFragment.newInstance(0);//
			if (currentId == 0) {
				return;
			} else {
				integeralTextView.setSelected(true);
				youhuiquanTexView.setSelected(false);
				ft = manager.beginTransaction();
				ft.add(R.id.fl_search_result_fragment, fragment);
				ft.commit();
				currentId = 0;
			}
			break;
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "积分卡券页面");
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "积分卡券页面");
	}
}
