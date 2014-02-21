package com.yidejia.app.mall.yirihui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.R;

public class YirihuiActivity extends SherlockFragmentActivity implements
		OnClickListener {
	private TextView rightText;// 规则
	private int lastIndex;
	private TextView nowText;// 正在进行
	private LinearLayout laterLinear;
	private TextView laterText;// 即将开始
	private TextView completeText;// 已经结束
	private ImageView imagePrice;// 将一个图标隐藏
	private FragmentManager manager;
	private FragmentTransaction ft;
	private YirihuiFragment mFragment;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search_result_info_layout);
		manager = getSupportFragmentManager();
		initView();
	}

	private void initView() {
		((TextView) findViewById(R.id.actionbar_title)).setText(getResources()
				.getString(R.string.main_yirihui));

		rightText = (TextView) findViewById(R.id.actionbar_right);
		rightText.setVisibility(View.VISIBLE);
		rightText.setText(getResources().getString(R.string.yirihui_order));
		rightText.setOnClickListener(this);
		((TextView) findViewById(R.id.actionbar_left))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						YirihuiActivity.this.finish();
					}
				});

		((ImageView) findViewById(R.id.search_with_list))
				.setVisibility(View.GONE);

		nowText = (TextView) findViewById(R.id.tv_search_result_selled);
		nowText.setText(getResources().getString(R.string.yirihui_now));

		laterLinear = (LinearLayout) findViewById(R.id.ll_search_result_price_layout);
		laterText = (TextView) findViewById(R.id.tv_search_result_price);

		laterText.setText(getResources().getString(R.string.yirihui_later));

		completeText = (TextView) findViewById(R.id.tv_search_result_popularity);
		completeText.setText(getResources()
				.getString(R.string.yirihui_complete));
		imagePrice = (ImageView) findViewById(R.id.iv_search_result_price);
		imagePrice.setVisibility(View.GONE);
		nowText.setSelected(true);
		lastIndex = 0;
		mFragment = YirihuiFragment.newInstance(0);
		ft = manager.beginTransaction();
		ft.add(R.id.fl_search_result_fragment, mFragment);
		ft.commit();
		nowText.setOnClickListener(new TextClick(0));
		laterText.setOnClickListener(new TextClick(1));
		completeText.setOnClickListener(new TextClick(2));
	}

	private class TextClick implements View.OnClickListener {
		private int clickIndex;

		public TextClick(int clickIndex) {
			this.clickIndex = clickIndex;
		}

		@Override
		public void onClick(View v) {
			switch (clickIndex) {

			case 0:
				if (clickIndex == lastIndex) {
					return;
				} else if (lastIndex == 1 || lastIndex == 2) {
					mFragment = YirihuiFragment.newInstance(0);
					nowText.setSelected(true);
					laterText.setSelected(false);
					completeText.setSelected(false);
					laterLinear.setSelected(false);
					lastIndex = 0;
					ft = manager.beginTransaction();
					ft.add(R.id.fl_search_result_fragment, mFragment);
					ft.addToBackStack("zhengzaijingxin");
					ft.commit();
				}
				break;

			case 1:
				if (lastIndex == 1) {
					return;
				} else if (lastIndex == 0 || lastIndex == 2) {
					mFragment = YirihuiFragment.newInstance(1);
					laterText.setSelected(true);
					nowText.setSelected(false);
					completeText.setSelected(false);
					laterLinear.setSelected(true);
					lastIndex = 1;
					ft = manager.beginTransaction();
					ft.add(R.id.fl_search_result_fragment, mFragment);
					ft.commit();
				}
				break;
			case 2:
				if (lastIndex == 2) {
					return;
				} else if (lastIndex == 0 || lastIndex == 1) {
					mFragment = YirihuiFragment.newInstance(2);
					completeText.setSelected(true);
					nowText.setSelected(false);
					laterText.setSelected(false);
					laterLinear.setSelected(false);
					lastIndex = 2;
					ft = manager.beginTransaction();
					ft.add(R.id.fl_search_result_fragment, mFragment);
					ft.commit();
				}
				break;

			}

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "伊日惠活动页面");
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "伊日惠活动页面");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.actionbar_right:
			Intent intent = new Intent(this, YiGuiZeActivity.class);
			startActivity(intent);

			break;

		default:
			break;
		}
	}
}
