package com.yidejia.app.mall.skintest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.Qq;
import com.yidejia.app.mall.model.SkinAnswer;

public class SkinResultAcitivity extends Activity {
	private TextView first;
	private TextView second;
	private ImageView back;
	private String cps;
	private String skinName;
	private TextView skinLei;
	private TextView qqName;
	private TextView qqQuen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.skin_test_last);
		Bundle bundle = getIntent().getExtras();
		SkinAnswer sk = (SkinAnswer) bundle.getSerializable("SkinAnswer");
		Qq qq = (Qq) bundle.getSerializable("qq");
		skinName = bundle.getString("skinName");

		first = (TextView) findViewById(R.id.skin_test_last_first);
		second = (TextView) findViewById(R.id.skin_test_last_second);
		back = (ImageView) findViewById(R.id.skin_test_last_back);
		skinLei = (TextView) findViewById(R.id.skin_test_last_content);
		qqName = (TextView) findViewById(R.id.skin_test_last_quen);
		qqQuen = (TextView) findViewById(R.id.skin_test_last_qqname);
		
		Log.i("info",qq.getQqName()+"qq.getQqName()");
		qqName.setText(getResources().getString(R.string.qq_hao)+qq.getQqName());
		qqQuen.setText(getResources().getString(R.string.qq_quen)+qq.getQuenName());
		skinLei.setText(skinName);
		first.setText("      " + sk.getDesc());
		second.setText("      " + sk.getSuggest());

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SkinResultAcitivity.this.finish();
			}
		});
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		StatService.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		StatService.onResume(this);
	}
}
