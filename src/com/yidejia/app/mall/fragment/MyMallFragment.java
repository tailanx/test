package com.yidejia.app.mall.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.SherlockFragment;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.view.AddressActivity;
import com.yidejia.app.mall.view.AllOrderActivity;
import com.yidejia.app.mall.view.AlreadyComActivity;
import com.yidejia.app.mall.view.AlreadyOrderActivity;
import com.yidejia.app.mall.view.EditorActivity;
import com.yidejia.app.mall.view.EvaluationActivity;
import com.yidejia.app.mall.view.ExchangeActivity;
import com.yidejia.app.mall.view.IntegeralActivity;
import com.yidejia.app.mall.view.MyCollectActivity;
import com.yidejia.app.mall.view.PersonActivity;
import com.yidejia.app.mall.view.WaitDeliverActivity;
import com.yidejia.app.mall.view.WaitPayActivity;
import com.yidejia.app.mall.widget.YLImageButton;

public class MyMallFragment extends SherlockFragment implements OnClickListener {

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
	
//	private TextView mTextView;
	
	private YLImageButton ylImageButton;

	public void setupView(View view) {
		// // //实例化组件
	
		personMessage = (RelativeLayout) view.findViewById(R.id.main2_main2_linearlayout20);// 个人中心
		personMessage.setOnClickListener(this);
		mExchange = (RelativeLayout) view.findViewById(R.id.main2_main2_linearlayout10);// 退换货
		mExchange.setOnClickListener(this);
		mAllOrder = (RelativeLayout) view.findViewById(R.id.main2_main2_linearlayout1);// 全部订单
		mAllOrder.setOnClickListener(this);
		mWaitPay = (RelativeLayout) view.findViewById(R.id.main2_main2_linearlayout2);// 待付快订单
		mWaitPay.setOnClickListener(this);
		mwaitDeliver = (RelativeLayout) view.findViewById(R.id.main2_main2_linearlayout6);// 待发货订单
		mwaitDeliver.setOnClickListener(this);
		mAlreadyOrder = (RelativeLayout) view.findViewById(R.id.main2_main2_linearlayout4);// 已发货订单
		mAlreadyOrder.setOnClickListener(this);
		mAlreadyCom = (RelativeLayout) view.findViewById(R.id.main2_main2_linearlayout3);// "已完成订单"
		mAlreadyCom.setOnClickListener(this);
		mCardVoucher = (RelativeLayout) view.findViewById(R.id.main2_main2_linearlayout5);// 积分卡券
		mCardVoucher.setOnClickListener(this);
		mMyCollect = (RelativeLayout) view.findViewById(R.id.main2_main2_linearlayout7);// 我的收藏
		mMyCollect.setOnClickListener(this);
		mAddressManagement = (RelativeLayout) view.findViewById(R.id.main2_main2_linearlayout9);// 收货地址管理
		mAddressManagement.setOnClickListener(this);
		mLayout11 = (RelativeLayout) view.findViewById(R.id.main2_main2_linearlayout11);// 评价晒单
		mLayout11.setOnClickListener(this);
		// 实例化组件
		imageView = (ImageView) getSherlockActivity().findViewById(R.id.person_shopping_button1);
//		mTextView = (TextView) getSherlockActivity().findViewById(R.id.my_shopping);// 我的商城

		
		ylImageButton = (YLImageButton) view.findViewById(R.id.main2_main2_linearlayout2_textview2);
		ylImageButton.setText("1");
		ylImageButton.setTextSize(24f);
		ylImageButton.setTextColor(Color.WHITE);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Log.i("info", "你好");
		super.onCreate(savedInstanceState);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		setContentView(R.layout.person_shopping_mall1);
//		setupView();
		// Log.i("info", "你好");
		// //点击跳转到个人中心
//		imageView.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				Intent intent = new Intent(getSherlockActivity(),
//						EditorActivity.class);
//				startActivity(intent);
//
//				getSherlockActivity().finish();
//
//			}
//		});
//		mTextView.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				Intent intent = new Intent(getSherlockActivity(),
//						LoginActivity.class);
//				startActivity(intent);
//
//				getSherlockActivity().finish();
//			}
//		});
		// //添加响应事件
		// mImageView1.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// Intent intent = new Intent(MainActivity.this,PersonActivity.class);
		// startActivity(intent);
		// //// Log.i("info", "你好");
		// MainActivity.this.finish();
		// }
		// });
		// mExchange.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		//
		// Intent intent = new Intent(MainActivity.this,ExchangeActivity.class);
		// startActivity(intent);
		// //// Log.i("info", "你好");
		// MainActivity.this.finish();
		// }
		// });
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.person_shopping_mall1, container, false);
		getSherlockActivity().getSupportActionBar().setCustomView(R.layout.actionbar_mymall);
		setupView(view);
		
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
		case R.id.main2_main2_linearlayout20:// 点击个人中心
			Intent intent1 = new Intent(getSherlockActivity(),
					PersonActivity.class);
			startActivity(intent1);
//			getSherlockActivity().finish();
			break;

		case R.id.main2_main2_linearlayout10:// 点击退换货
			Intent intent2 = new Intent(getSherlockActivity(),
					ExchangeActivity.class);
			startActivity(intent2);
//			getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout1:// 点击全部订单
			Intent intent3 = new Intent(getSherlockActivity(),
					AllOrderActivity.class);
			startActivity(intent3);
//			getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout2:// 待付款订单
			Intent intent4 = new Intent(getSherlockActivity(),
					WaitPayActivity.class);
			startActivity(intent4);
//			getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout6:// 待发货订单
			Intent intent5 = new Intent(getSherlockActivity(),
					WaitDeliverActivity.class);
			startActivity(intent5);
//			getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout4:// 已发货订单
			Intent intent6 = new Intent(getSherlockActivity(),
					AlreadyOrderActivity.class);
			startActivity(intent6);
//			getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout3:// 已完成订单
			Intent intent7 = new Intent(getSherlockActivity(),
					AlreadyComActivity.class);
			startActivity(intent7);
//			getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout5:// 积分卡券
			Intent intent8 = new Intent(getSherlockActivity(),
					IntegeralActivity.class);
			startActivity(intent8);
//			getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout7:// 我的收藏
			Intent intent9 = new Intent(getSherlockActivity(),
					MyCollectActivity.class);
			startActivity(intent9);
//			getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout9:// 收货地址管理
			Intent intent10 = new Intent(getSherlockActivity(),
					AddressActivity.class);
			startActivity(intent10);
//			getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout11:// 评价晒单
			Intent intent11 = new Intent(getSherlockActivity(),
					EvaluationActivity.class);
			startActivity(intent11);
//			getSherlockActivity().finish();
			break;
		}
	}
}