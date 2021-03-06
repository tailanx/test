package com.yidejia.app.mall.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.search.SearchResultActivity;

public class NOProduceActivity extends SherlockActivity {
	private Button mButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setActionbar();
		setContentView(R.layout.no_produce);
		mButton = (Button) findViewById(R.id.no_produce_button);
		mButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentOrder = new Intent(NOProduceActivity.this, SearchResultActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("title", "全部");
				bundle.putString("name", "");
				bundle.putString("price", "");
				bundle.putString("brand", "");
				bundle.putString("fun", "");
				intentOrder.putExtras(bundle);
				NOProduceActivity.this.startActivity(intentOrder);
			}
		});
	}
	
	private void setActionbar(){
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_common);

		TextView button = (TextView) findViewById(R.id.ab_common_back);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				NOProduceActivity.this.finish();
			}
		});
		
		TextView titleTextView = (TextView) findViewById(R.id.ab_common_title);
		titleTextView.setText(getResources().getString(R.string.cart));
	}
	
}
