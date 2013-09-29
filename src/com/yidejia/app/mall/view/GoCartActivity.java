package com.yidejia.app.mall.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.util.CartUtil;


public class GoCartActivity extends Activity implements OnClickListener {
	private TextView sumTextView;// �ܵ�Ǯ��
	private TextView counTextView;// �ܵ�����
	private CheckBox mBox;//ѡ���
	private Button mbutton;//ȥ����
	private CartUtil cartUtil;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.go_cart);
			mbutton  = (Button) findViewById(R.id.go_cart_go_pay);
			mbutton.setOnClickListener(this);
			
			mBox = (CheckBox) findViewById(R.id.go_cart_checkbox);// ѡ���

			sumTextView = (TextView)findViewById(R.id.shopping_cart_sum_money);// �ܵ�Ǯ��

			counTextView = (TextView)findViewById(R.id.shopping_cart_sum_number);// �ܵ�����

			LinearLayout layout = (LinearLayout) findViewById(R.id.go_cart_relative1);
			cartUtil = new CartUtil(this, layout, counTextView, sumTextView, mBox);
			cartUtil.AllComment();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(GoCartActivity.this, "���粻������", Toast.LENGTH_SHORT).show();

		}
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.go_cart_go_pay:
			Intent intent = new Intent(GoCartActivity.this,PayActivity.class);
			GoCartActivity.this.startActivity(intent);
			break;

		}
		
	}
	
	
}
