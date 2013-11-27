package com.yidejia.app.mall.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.SkinAnswer;

public class SkinResultAcitivity extends Activity {
	private TextView first;
	private TextView second;
	private ImageView back;
	private String cps;
	private String skinName;
	private TextView skinLei;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.skin_test_last);
		Bundle bundle = getIntent().getExtras();
		SkinAnswer sk = (SkinAnswer) bundle.getSerializable("SkinAnswer");
		skinName = bundle.getString("skinName");
		
		first = (TextView) findViewById(R.id.skin_test_last_first);
		second = (TextView) findViewById(R.id.skin_test_last_second);
		back = (ImageView) findViewById(R.id.skin_test_last_back);
		skinLei = (TextView) findViewById(R.id.skin_test_last_content);
		
		skinLei.setText(skinName);
		first.setText("      "+sk.getDesc());
		second.setText("      "+sk.getSuggest());
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			SkinResultAcitivity.this.finish();
			}
		});
	}
}
