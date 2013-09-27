package com.yidejia.app.mall.view;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.R;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PayActivity extends SherlockActivity {
	private ImageView addImageView;
	private EditText mEditText;
	int countNumber;

	public void setupShow() {
		addImageView = (ImageView) findViewById(R.id.shopping_cart_item_add);
		mEditText = (EditText) findViewById(R.id.shopping_cart_item_edit_number);
		countNumber = Integer.parseInt(mEditText.getText() + "");
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setActionbar();
		setContentView(R.layout.go_pay);
		LinearLayout layout = (LinearLayout) findViewById(R.id.go_pay_relative2);

		View person = getLayoutInflater().inflate(R.layout.go_pay_item, null);

		layout.addView(person);
	}

	public void add(View v) {
		addImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				++countNumber;
				mEditText.setText(countNumber);

			}
		});
	}
	
	private void setActionbar(){
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
//		getSupportActionBar().setLogo(R.drawable.back);
		getSupportActionBar().setIcon(R.drawable.back);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_compose);
//		startActionMode(new AnActionModeOfEpicProportions(ComposeActivity.this));
		ImageView button = (ImageView) findViewById(R.id.compose_back);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Toast.makeText(ComposeActivity.this, "button", Toast.LENGTH_SHORT).show();
				PayActivity.this.finish();
			}
		});
		
//		TextView textView = (TextView) findViewById(R.id.compose_title);
//		textView.setText(text);
	}
}
