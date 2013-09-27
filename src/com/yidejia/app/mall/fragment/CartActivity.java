package com.yidejia.app.mall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.yidejia.app.mall.GoodsInfoActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.view.OrderDetailActivity;
import com.yidejia.app.mall.view.PayActivity;

public class CartActivity extends SherlockFragment implements OnClickListener {
	private ImageView subtract;
	private TextView number;
	private ImageView addImageView;
	private int sumString;
	
	private Button shoppingCartTopay;

	private void doClick(View v) {
		switch (v.getId()) {
		case R.id.shopping_cart_go_pay:// ȥ����
			Intent intent = new Intent(getSherlockActivity(),GoodsInfoActivity.class);
			getSherlockActivity().startActivity(intent);
//			gets.finish();

			break;

		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.shopping_cart);
		
	}


	@Override
	public void onClick(View v) {

		int sum = Integer.parseInt(number.getText().toString());
		switch (v.getId()) {
		case R.id.shopping_cart_item_subtract:// �������ʱ��ӵ��¼�
			if (sum <= 0) {
				Toast.makeText(getSherlockActivity(), "�Ѿ�����С����ֵ��", Toast.LENGTH_LONG).show();
			} else {
				sum--;
				number.setText(sum + "");
			}
			break;

		case R.id.shopping_cart_item_add:// ����Ӻ�ʱ��ӵ��¼�
			if (sum >= 9999) {
				Toast.makeText(getSherlockActivity(), "����Ҫ�������Ĳ�Ʒ������ͷ���ϵ", Toast.LENGTH_LONG)
						.show();
			} else {
				sum++;
				number.setText(sum + "");
			}
			break;
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.shopping_cart, container, false);
		
		RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.shopping_cart_relative2);
		
		getSherlockActivity().getSupportActionBar().setCustomView(R.layout.actionbar_cart);
		
		View person = inflater.inflate(R.layout.shopping_cart_item, null);
		TextView detail = (TextView)person.findViewById(R.id.shopping_cart_item_text);
		detail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getSherlockActivity(),OrderDetailActivity.class);
				startActivity(intent);
			}
		});
		
		subtract = (ImageView) person
				.findViewById(R.id.shopping_cart_item_subtract);// ��
		number = (TextView) person
				.findViewById(R.id.shopping_cart_item_edit_number);// ����ĸ���
		number.setText(1 + "");
		addImageView = (ImageView) person
				.findViewById(R.id.shopping_cart_item_add);// ��
		addImageView.setOnClickListener(this);
		subtract.setOnClickListener(this);
		layout.addView(person);
		
		shoppingCartTopay = (Button) getSherlockActivity().findViewById(R.id.shopping_cart_go_pay);
		shoppingCartTopay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getSherlockActivity(), PayActivity.class);
				getSherlockActivity().startActivity(intent);
			}
		});
		return view;
	}

}
