
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

	public class OptionActivity extends SherlockActivity {
		/*
		public void doClick(View v){
			switch (v.getId()) {
			case R.id.address_management_button1:
				Intent intent = new Intent(AddressActivity.this,MyMallActivity.class);
				startActivity(intent);
				//?????Activity??
				AddressActivity.this.finish();
				break;
			case R.id.address_management_button2:
				Intent intent2 = new Intent(AddressActivity.this,NewAddressActivity.class);
				startActivity(intent2);
				//?????Activity??
				AddressActivity.this.finish();
				break;
			}
		}
		*/
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setActionbar();
			//???��???????scrollview
			setContentView(R.layout.opinion);

		}
		
		private void setActionbar(){
			getSupportActionBar().setDisplayHomeAsUpEnabled(false);
			getSupportActionBar().setDisplayShowHomeEnabled(false);
			getSupportActionBar().setDisplayShowTitleEnabled(false);
			getSupportActionBar().setDisplayUseLogoEnabled(false);
//			getSupportActionBar().setLogo(R.drawable.back);
			getSupportActionBar().setIcon(R.drawable.back1);
			getSupportActionBar().setDisplayShowCustomEnabled(true);
			getSupportActionBar().setCustomView(R.layout.actionbar_common);
//			startActionMode(new AnActionModeOfEpicProportions(ComposeActivity.this));
			ImageView leftButton = (ImageView) findViewById(R.id.actionbar_left);
			Button rightButton = (Button) findViewById(R.id.actionbar_right);
			rightButton.setText(getResources().getString(R.string.commit));
			rightButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(OptionActivity.this, getResources().getString(R.string.commit_compelte), Toast.LENGTH_SHORT).show();
					OptionActivity.this.finish();
				}
			});
			leftButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
//					Toast.makeText(ComposeActivity.this, "button", Toast.LENGTH_SHORT).show();
//					Intent intent = new Intent(AddressActivity.this,MyMallActivity.class);
//					startActivity(intent);
					//?????Activity??
					OptionActivity.this.finish();
				}
			});
		
			
			TextView titleTextView = (TextView) findViewById(R.id.actionbar_title);
			titleTextView.setText(getResources().getString(R.string.option));
		}
	}


