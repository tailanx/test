package com.yidejia.app.mall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.R.id;
import com.yidejia.app.mall.R.layout;
import com.yidejia.app.mall.view.AddressActivity;
import com.yidejia.app.mall.view.AllOrderActivity;
import com.yidejia.app.mall.view.AlreadyComActivity;
import com.yidejia.app.mall.view.AlreadyOrderActivity;
import com.yidejia.app.mall.view.EditorActivity;
import com.yidejia.app.mall.view.EvaluationActivity;
import com.yidejia.app.mall.view.ExchangeActivity;
import com.yidejia.app.mall.view.IntegeralActivity;
import com.yidejia.app.mall.view.LoginActivity;
import com.yidejia.app.mall.view.MyCollectActivity;
import com.yidejia.app.mall.view.PersonActivity;
import com.yidejia.app.mall.view.WaitDeliverActivity;
import com.yidejia.app.mall.view.WaitPayActivity;
import com.yidejia.app.mall.widget.YLImageButton;

public class MyMallActivity extends SherlockFragment implements OnClickListener {

	private ImageView imageView;

	private RelativeLayout personMessage;
	private RelativeLayout mExchange;
	private RelativeLayout mAllOrder;
	private RelativeLayout mWaitPay;
	private RelativeLayout mwaitDeliver;
	private RelativeLayout mAlreadyOrder;
	private RelativeLayout mAlreadyCom;
	private RelativeLayout mCardVoucher;
	private RelativeLayout mMyCollect;
	private RelativeLayout mAddressManagement;
	private RelativeLayout mLayout11;
//	private YLImageButton mButton1;
	private YLImageButton mButton2;
//	private YLImageButton mButton3;
//	private YLImageButton mButton4;
//	private YLImageButton mButton5;
//	private YLImageButton mButton6;
//	private YLImageButton mButton7;
	private YLImageButton mButton8;
//	private YLImageButton mButton9;
//	private YLImageButton mButton10;
//	private YLImageButton mButton11;
	private TextView favorites;
	private TextView integration;
	private TextView message;
	
//	private TextView mTextView;

	public void setupView(View view) {
		// // //ʵ�������
		personMessage = (RelativeLayout) view.findViewById(R.id.main2_main2_linearlayout20);// ��������
		personMessage.setOnClickListener(this);
		mExchange = (RelativeLayout) view.findViewById(R.id.main2_main2_linearlayout10);// �˻���
		mExchange.setOnClickListener(this);
		mAllOrder = (RelativeLayout) view.findViewById(R.id.main2_main2_linearlayout1);// ȫ������
		mAllOrder.setOnClickListener(this);
		mWaitPay = (RelativeLayout) view.findViewById(R.id.main2_main2_linearlayout2);// �����충��
		mWaitPay.setOnClickListener(this);
		mwaitDeliver = (RelativeLayout) view.findViewById(R.id.main2_main2_linearlayout6);// ����������
		mwaitDeliver.setOnClickListener(this);
		mAlreadyOrder = (RelativeLayout) view.findViewById(R.id.main2_main2_linearlayout4);// �ѷ�������
		mAlreadyOrder.setOnClickListener(this);
		mAlreadyCom = (RelativeLayout) view.findViewById(R.id.main2_main2_linearlayout3);// "����ɶ���"
		mAlreadyCom.setOnClickListener(this);
		mCardVoucher = (RelativeLayout) view.findViewById(R.id.main2_main2_linearlayout5);// ���ֿ�ȯ
		mCardVoucher.setOnClickListener(this);
		mMyCollect = (RelativeLayout) view.findViewById(R.id.main2_main2_linearlayout7);// �ҵ��ղ�
		mMyCollect.setOnClickListener(this);
		mAddressManagement = (RelativeLayout) view.findViewById(R.id.main2_main2_linearlayout9);// �ջ���ַ����
		mAddressManagement.setOnClickListener(this);
		mLayout11 = (RelativeLayout) view.findViewById(R.id.main2_main2_linearlayout11);// ����ɹ��
		mLayout11.setOnClickListener(this);
		
		
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSherlockActivity().getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSherlockActivity().getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSherlockActivity().getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSherlockActivity().getSupportActionBar().setDisplayUseLogoEnabled(false);
		// ʵ�������
		getSherlockActivity().getSupportActionBar().setCustomView(R.layout.actionbar_mymall);
		imageView = (ImageView)getSherlockActivity().findViewById(R.id.person_shopping_button1);
		favorites = (TextView) view.findViewById(R.id.favorites);
		favorites.setOnClickListener(this);
		message = (TextView)view.findViewById(R.id.message);
		message.setOnClickListener(this);
		integration = (TextView)view.findViewById(R.id.integration);
		integration.setOnClickListener(this);
	
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getSherlockActivity(),
						EditorActivity.class);
				startActivity(intent);

//				getSherlockActivity().finish();

			}
		});
	}

//	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		setContentView(R.layout.person_shopping_mall1);
//		setupView();
		
		
		
//		mButton1 = (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview1);
//		mButton1.setText("1");
//		mButton1.setTextColorResources(R.color.white);
//		mButton1.setTextSize(35f);
		
//		mButton2 = (YLImageButton)findViewById(R.id.main2_main2_linearlayout2_textview2);
//		mButton2.setText("14");
//		mButton2.setTextColorResources(R.color.white);
//		mButton2.setTextSize(35f);
		
//		mButton3 = (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview3);
//		mButton3.setText("6");
//		mButton3.setTextColorResources(R.color.white);
//		mButton3.setTextSize(35f);
//		
//		mButton4 = (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview4);
//		mButton4.setText("17");
//		mButton4.setTextColorResources(R.color.white);
//		mButton4.setTextSize(35f);
//		
//		mButton5 = (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview5);
//		mButton5.setText("1");
//		mButton5.setTextColorResources(R.color.white);
//		mButton5.setTextSize(35f);
//		
//		mButton6 = (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview6);
//		mButton6.setText("16");
//		mButton6.setTextColorResources(R.color.white);
//		mButton6.setTextSize(35f);
//		
//		mButton7 = (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview7);
//		mButton7.setText("5");
//		mButton7.setTextColorResources(R.color.white);
//		mButton7.setTextSize(35f);
		
//		mButton8 = (YLImageButton)findViewById(R.id.main2_main2_linearlayout2_textview8);
//		mButton8.setText("5");
//		mButton8.setTextColorResources(R.color.white);
//		mButton8.setTextSize(35f);
		
//		mButton9 = (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview9);
//		mButton9.setText("3");
//		mButton9.setTextColorResources(R.color.white);
//		mButton9.setTextSize(35f);
//		
//		mButton10 = (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview10);
//		mButton10.setText("4");
//		mButton10.setTextColorResources(R.color.white);
//		mButton10.setTextSize(35f);
//		
//		mButton11 = (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview11);
//		mButton11.setText("10");
//		mButton11.setTextColorResources(R.color.white);
//		mButton11.setTextSize(35f);
//		
//		setupView();
		
		
//		mTextView.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				Intent intent = new Intent(getSherlockActivity(),
//						LoginActivity.class);
//				startActivity(intent);
//
////				getSherlockActivity().finish();
//			}
//		});
		

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.person_shopping_mall1, container, false);
		getSherlockActivity().getSupportActionBar().setCustomView(R.layout.actionbar_mymall);
		
		favorites = (TextView) view.findViewById(R.id.favorites);
		favorites.setOnClickListener(this);
		message = (TextView) view.findViewById(R.id.message);
		message.setOnClickListener(this);
		integration = (TextView) view.findViewById(R.id.integration);
		integration.setOnClickListener(this);
//		
////		mButton1 = (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview1);
////		mButton1.setText("1");
////		mButton1.setTextColorResources(R.color.white);
////		mButton1.setTextSize(35f);
//		
		mButton2 = (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview2);
		mButton2.setText("14");
		mButton2.setTextColorResources(R.color.white);
		mButton2.setTextSize(35f);
		
////		mButton3 = (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview3);
////		mButton3.setText("6");
////		mButton3.setTextColorResources(R.color.white);
////		mButton3.setTextSize(35f);
////		
////		mButton4 = (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview4);
////		mButton4.setText("17");
////		mButton4.setTextColorResources(R.color.white);
////		mButton4.setTextSize(35f);
////		
////		mButton5 = (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview5);
////		mButton5.setText("1");
////		mButton5.setTextColorResources(R.color.white);
////		mButton5.setTextSize(35f);
////		
////		mButton6 = (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview6);
////		mButton6.setText("16");
////		mButton6.setTextColorResources(R.color.white);
////		mButton6.setTextSize(35f);
////		
////		mButton7 = (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview7);
////		mButton7.setText("5");
////		mButton7.setTextColorResources(R.color.white);
////		mButton7.setTextSize(35f);
//		
		mButton8 = (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview8);
		mButton8.setText("5");
		mButton8.setTextColorResources(R.color.white);
		mButton8.setTextSize(35f);
//		
////		mButton9 = (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview9);
////		mButton9.setText("3");
////		mButton9.setTextColorResources(R.color.white);
////		mButton9.setTextSize(35f);
////		
////		mButton10 = (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview10);
////		mButton10.setText("4");
////		mButton10.setTextColorResources(R.color.white);
////		mButton10.setTextSize(35f);
////		
////		mButton11 = (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview11);
////		mButton11.setText("10");
////		mButton11.setTextColorResources(R.color.white);
////		mButton11.setTextSize(35f);
////		
		setupView(view);
//		
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getSherlockActivity(),
						EditorActivity.class);
				startActivity(intent);

//				getSherlockActivity().finish();

			}
		});
//		mTextView.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				Intent intent = new Intent(getSherlockActivity(),
//						LoginActivity.class);
//				startActivity(intent);
//
////				getSherlockActivity().finish();
//			}
//		});
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// case R.id.person_shopping_button1:
		// Intent intent = new Intent(MainActivity.this,EditorActivity.class);
		// startActivity(intent);
		// MainActivity.this.finish();
		// break;
		case R.id.main2_main2_linearlayout20:// �����������
			Intent intent1 = new Intent(getSherlockActivity(),
					PersonActivity.class);
			startActivity(intent1);
//			getSherlockActivity().finish();
			break;

		case R.id.main2_main2_linearlayout10:// ����˻���
			Intent intent2 = new Intent(getSherlockActivity(),
					ExchangeActivity.class);
			startActivity(intent2);
//			getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout1:// ���ȫ������
			Intent intent3 = new Intent(getSherlockActivity(),
					AllOrderActivity.class);
			startActivity(intent3);
//			getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout2:// �������
			Intent intent4 = new Intent(getSherlockActivity(),
					WaitPayActivity.class);
			startActivity(intent4);
//			getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout6:// ����������
			Intent intent5 = new Intent(getSherlockActivity(),
					WaitDeliverActivity.class);
			startActivity(intent5);
//			getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout4:// �ѷ�������
			Intent intent6 = new Intent(getSherlockActivity(),
					AlreadyOrderActivity.class);
			startActivity(intent6);
//			getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout3:// ����ɶ���
			Intent intent7 = new Intent(getSherlockActivity(),
					AlreadyComActivity.class);
			startActivity(intent7);
//			getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout5:// ���ֿ�ȯ
			Intent intent8 = new Intent(getSherlockActivity(),
					IntegeralActivity.class);
			startActivity(intent8);
//			getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout7:// �ҵ��ղ�
			Intent intent9 = new Intent(getSherlockActivity(),
					MyCollectActivity.class);
			startActivity(intent9);
//			getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout9:// �ջ���ַ����
			Intent intent10 = new Intent(getSherlockActivity(),
					AddressActivity.class);
			startActivity(intent10);
//			getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout11:// ����ɹ��
			Intent intent11 = new Intent(getSherlockActivity(),
					EvaluationActivity.class);
			startActivity(intent11);
//			getSherlockActivity().finish();
			break;
		case R.id.favorites://�ղ�
			Intent intent12 = new Intent(getSherlockActivity(),MyCollectActivity.class);
			startActivity(intent12);
			break;
		case R.id.message://�ղ�
			Intent intent13 = new Intent(getSherlockActivity(),PersonActivity.class);
			startActivity(intent13);
			break;
		case R.id.integration://�ղ�
			Intent intent14 = new Intent(getSherlockActivity(),IntegeralActivity.class);
			startActivity(intent14);
			break;
		}
	}
}