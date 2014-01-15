package com.yidejia.app.mall.yirihui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.R;

public class YirihuiActivity extends BaseActivity {
	private TextView rightText;// 规则
	private int lastIndex;
	private TextView nowText;
	private TextView laterText;
	private TextView completeText;
	private ImageView imagePrice;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setActionbarConfig();
		setContentView(R.layout.activity_search_result_info_layout);
		initView();
	}

	private void initView() {
		setTitle(getResources().getString(R.string.main_yirihui));
		rightText = (TextView) findViewById(R.id.ab_common_tv_right);
		rightText.setVisibility(View.VISIBLE);
		rightText.setText(getResources().getString(R.string.yirihui_order));
		nowText = (TextView) findViewById(R.id.tv_search_result_selled);
		nowText.setText(getResources().getString(R.string.yirihui_now));
		laterText = (TextView) findViewById(R.id.ll_search_result_price_layout);
		laterText.setText(getResources().getString(R.string.yirihui_later));
		completeText = (TextView) findViewById(R.id.tv_search_result_price);
		completeText.setText(getResources()
				.getString(R.string.yirihui_complete));
		imagePrice = (ImageView) findViewById(R.id.iv_search_result_price);
		imagePrice.setVisibility(View.GONE);
		nowText.setSelected(true);
		lastIndex = 0;
	}

	private class TextClick implements View.OnClickListener {
		private int clickIndex;

		public TextClick(int clickIndex) {
			this.clickIndex = clickIndex;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (clickIndex) {
			case 0:
				if (clickIndex == lastIndex) {
					return;
				}
				break;

			case 1:
				
				break;
			}

		}

	}

}
