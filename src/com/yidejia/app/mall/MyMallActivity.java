package com.yidejia.app.mall;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.yidejia.app.mall.datamanage.PersonCountDataManage;
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
	// private YLImageButton mButton1;
	private YLImageButton mButton2;
	// private YLImageButton mButton3;
	// private YLImageButton mButton4;
	// private YLImageButton mButton5;
	// private YLImageButton mButton6;
	// private YLImageButton mButton7;
	private YLImageButton mButton8;
	// private YLImageButton mButton9;
	// private YLImageButton mButton10;
	// private YLImageButton mButton11;
	private TextView favorites;// 收藏
	private TextView integration;// 积分
	private TextView message;// 消息
	private ImageView head;// 头像
	private TextView nick;// 昵称
	private TextView vip;
	private MyApplication myApplication;
	private PersonCountDataManage personCountDataManage;

	// private TextView mTextView;

	public void setupView(View view) {
		// // //实例化组件
		head = (ImageView) view.findViewById(R.id.person_shopping_image_person);
		head.setOnClickListener(this);
		nick = (TextView) view.findViewById(R.id.person_shopping_person_name);

		// nick.setOnClickListener(this);
		vip = (TextView) view.findViewById(R.id.person_shopping_person_vip);
		personMessage = (RelativeLayout) view
				.findViewById(R.id.main2_main2_linearlayout20);// 个人中心
		personMessage.setOnClickListener(this);
		mExchange = (RelativeLayout) view
				.findViewById(R.id.main2_main2_linearlayout10);// 退换货
		mExchange.setOnClickListener(this);
		mAllOrder = (RelativeLayout) view
				.findViewById(R.id.main2_main2_linearlayout1);// 全部订单
		mAllOrder.setOnClickListener(this);
		mWaitPay = (RelativeLayout) view
				.findViewById(R.id.main2_main2_linearlayout2);// 待付快订单
		mWaitPay.setOnClickListener(this);
		mwaitDeliver = (RelativeLayout) view
				.findViewById(R.id.main2_main2_linearlayout6);// 待发货订单
		mwaitDeliver.setOnClickListener(this);
		mAlreadyOrder = (RelativeLayout) view
				.findViewById(R.id.main2_main2_linearlayout4);// 已发货订单
		mAlreadyOrder.setOnClickListener(this);
		mAlreadyCom = (RelativeLayout) view
				.findViewById(R.id.main2_main2_linearlayout3);// "已完成订单"
		mAlreadyCom.setOnClickListener(this);
		mCardVoucher = (RelativeLayout) view
				.findViewById(R.id.main2_main2_linearlayout5);// 积分卡券
		mCardVoucher.setOnClickListener(this);
		mMyCollect = (RelativeLayout) view
				.findViewById(R.id.main2_main2_linearlayout7);// 我的收藏
		mMyCollect.setOnClickListener(this);
		mAddressManagement = (RelativeLayout) view
				.findViewById(R.id.main2_main2_linearlayout9);// 收货地址管理
		mAddressManagement.setOnClickListener(this);
		mLayout11 = (RelativeLayout) view
				.findViewById(R.id.main2_main2_linearlayout11);// 评价晒单
		mLayout11.setOnClickListener(this);

		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(
				false);
		getSherlockActivity().getSupportActionBar()
				.setDisplayShowCustomEnabled(true);
		getSherlockActivity().getSupportActionBar().setDisplayShowHomeEnabled(
				false);
		getSherlockActivity().getSupportActionBar().setDisplayShowTitleEnabled(
				false);
		getSherlockActivity().getSupportActionBar().setDisplayUseLogoEnabled(
				false);
		// 实例化组件
		getSherlockActivity().getSupportActionBar().setCustomView(
				R.layout.actionbar_mymall);
		imageView = (ImageView) getSherlockActivity().findViewById(
				R.id.person_shopping_button1);
		favorites = (TextView) view.findViewById(R.id.favorites);

		favorites.setOnClickListener(this);
		message = (TextView) view.findViewById(R.id.message);
		message.setOnClickListener(this);
		integration = (TextView) view.findViewById(R.id.integration);
		integration.setOnClickListener(this);

	}

	// @Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// setContentView(R.layout.person_shopping_mall1);
		// setupView();

		// mButton1 =
		// (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview1);
		// mButton1.setText("1");
		// mButton1.setTextColorResources(R.color.white);
		// mButton1.setTextSize(35f);

		// mButton2 =
		// (YLImageButton)findViewById(R.id.main2_main2_linearlayout2_textview2);
		// mButton2.setText("14");
		// mButton2.setTextColorResources(R.color.white);
		// mButton2.setTextSize(35f);

		// mButton3 =
		// (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview3);
		// mButton3.setText("6");
		// mButton3.setTextColorResources(R.color.white);
		// mButton3.setTextSize(35f);
		//
		// mButton4 =
		// (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview4);
		// mButton4.setText("17");
		// mButton4.setTextColorResources(R.color.white);
		// mButton4.setTextSize(35f);
		//
		// mButton5 =
		// (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview5);
		// mButton5.setText("1");
		// mButton5.setTextColorResources(R.color.white);
		// mButton5.setTextSize(35f);
		//
		// mButton6 =
		// (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview6);
		// mButton6.setText("16");
		// mButton6.setTextColorResources(R.color.white);
		// mButton6.setTextSize(35f);
		//
		// mButton7 =
		// (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview7);
		// mButton7.setText("5");
		// mButton7.setTextColorResources(R.color.white);
		// mButton7.setTextSize(35f);

		// mButton8 =
		// (YLImageButton)findViewById(R.id.main2_main2_linearlayout2_textview8);
		// mButton8.setText("5");
		// mButton8.setTextColorResources(R.color.white);
		// mButton8.setTextSize(35f);

		// mButton9 =
		// (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview9);
		// mButton9.setText("3");
		// mButton9.setTextColorResources(R.color.white);
		// mButton9.setTextSize(35f);
		//
		// mButton10 =
		// (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview10);
		// mButton10.setText("4");
		// mButton10.setTextColorResources(R.color.white);
		// mButton10.setTextSize(35f);
		//
		// mButton11 =
		// (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview11);
		// mButton11.setText("10");
		// mButton11.setTextColorResources(R.color.white);
		// mButton11.setTextSize(35f);
		//
		// setupView();

		// mTextView.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// Intent intent = new Intent(getSherlockActivity(),
		// LoginActivity.class);
		// startActivity(intent);
		//
		// // getSherlockActivity().finish();
		// }
		// });

	}

	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();// 加载图片

	private void initDisplayImageOption() {
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.hot_sell_right_top_image)
				.showImageOnFail(R.drawable.hot_sell_right_top_image)
				.showImageForEmptyUri(R.drawable.hot_sell_right_top_image)
				.cacheInMemory(true).cacheOnDisc(true).build();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		myApplication = (MyApplication) getSherlockActivity().getApplication();

		personCountDataManage = new PersonCountDataManage(getSherlockActivity());

		Log.i("info", personCountDataManage + "        personCountDataManage");
		View view = inflater.inflate(R.layout.person_shopping_mall1, container,
				false);
		setupView(view);
		initDisplayImageOption();
		// getSherlockActivity().getSupportActionBar().setCustomView(
		// R.layout.actionbar_mymall);

		String faString = personCountDataManage.getFavoliten();
		// Log.i("info", faString+"     faString");
		if (faString == null || "".equals(faString)) {
			favorites.setText(0 + "");
		} else {
			favorites.setText(faString);
		}

		String msString = personCountDataManage.getMsg();
		if (msString == null || "".equals(msString)) {
			message.setText(0 + "");
		} else {
			message.setText(msString);
		}

		String inString = personCountDataManage.getScores();
		if (inString == null || "".equals(inString)) {
			integration.setText(0 + "");
		} else {
			integration.setText(inString);
		}
		imageLoader.displayImage(myApplication.getUserHeadImg(), head, options,
				animateFirstListener);

		String name = myApplication.getNick();
		if (name == null || "".equals(name)) {
			nick.setText(myApplication.getUserId());
			Log.i("info", myApplication.getUserId() + "   name");
		} else {
			nick.setText(name);
		}

		String vip1 = myApplication.getVip();
		if (vip1 == null || "".equals(vip1)) {
			vip.setText("VIP0");
		} else {
			vip.setText(vip1);
		}
		//
		// // mButton1 =
		// (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview1);
		// // mButton1.setText("1");
		// // mButton1.setTextColorResources(R.color.white);
		// // mButton1.setTextSize(35f);
		//
		mButton2 = (YLImageButton) view
				.findViewById(R.id.main2_main2_linearlayout2_textview2);
		mButton2.setText("14");
		mButton2.setTextColorResources(R.color.white);
		mButton2.setTextSize(35f);

		// // mButton3 =
		// (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview3);
		// // mButton3.setText("6");
		// // mButton3.setTextColorResources(R.color.white);
		// // mButton3.setTextSize(35f);
		// //
		// // mButton4 =
		// (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview4);
		// // mButton4.setText("17");
		// // mButton4.setTextColorResources(R.color.white);
		// // mButton4.setTextSize(35f);
		// //
		// // mButton5 =
		// (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview5);
		// // mButton5.setText("1");
		// // mButton5.setTextColorResources(R.color.white);
		// // mButton5.setTextSize(35f);
		// //
		// // mButton6 =
		// (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview6);
		// // mButton6.setText("16");
		// // mButton6.setTextColorResources(R.color.white);
		// // mButton6.setTextSize(35f);
		// //
		// // mButton7 =
		// (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview7);
		// // mButton7.setText("5");
		// // mButton7.setTextColorResources(R.color.white);
		// // mButton7.setTextSize(35f);
		//
		mButton8 = (YLImageButton) view
				.findViewById(R.id.main2_main2_linearlayout2_textview8);
		mButton8.setText("5");
		mButton8.setTextColorResources(R.color.white);
		mButton8.setTextSize(35f);
		//
		// // mButton9 =
		// (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview9);
		// // mButton9.setText("3");
		// // mButton9.setTextColorResources(R.color.white);
		// // mButton9.setTextSize(35f);
		// //
		// // mButton10 =
		// (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview10);
		// // mButton10.setText("4");
		// // mButton10.setTextColorResources(R.color.white);
		// // mButton10.setTextSize(35f);
		// //
		// // mButton11 =
		// (YLImageButton)view.findViewById(R.id.main2_main2_linearlayout2_textview11);
		// // mButton11.setText("10");
		// // mButton11.setTextColorResources(R.color.white);
		// // mButton11.setTextSize(35f);
		// //
		//
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getSherlockActivity(),
						EditorActivity.class);
				getSherlockActivity().startActivity(intent);

				// getSherlockActivity().finish();

			}
		});
		// mTextView.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// Intent intent = new Intent(getSherlockActivity(),
		// LoginActivity.class);
		// startActivity(intent);
		//
		// // getSherlockActivity().finish();
		// }
		// });
		return view;
	}

	static final List<String> displayedImages = Collections
			.synchronizedList(new LinkedList<String>());

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
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
			// getSherlockActivity().finish();
			break;

		case R.id.main2_main2_linearlayout10:// 点击退换货
			Intent intent2 = new Intent(getSherlockActivity(),
					ExchangeActivity.class);
			startActivity(intent2);
			// getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout1:// 点击全部订单
			Intent intent3 = new Intent(getSherlockActivity(),
					AllOrderActivity.class);
			startActivity(intent3);
			// getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout2:// 待付款订单
			Intent intent4 = new Intent(getSherlockActivity(),
					WaitPayActivity.class);
			startActivity(intent4);
			// getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout6:// 待发货订单
			Intent intent5 = new Intent(getSherlockActivity(),
					WaitDeliverActivity.class);
			startActivity(intent5);
			// getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout4:// 已发货订单
			Intent intent6 = new Intent(getSherlockActivity(),
					AlreadyOrderActivity.class);
			startActivity(intent6);
			// getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout3:// 已完成订单
			Intent intent7 = new Intent(getSherlockActivity(),
					AlreadyComActivity.class);
			startActivity(intent7);
			// getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout5:// 积分卡券
			Intent intent8 = new Intent(getSherlockActivity(),
					IntegeralActivity.class);
			startActivity(intent8);
			// getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout7:// 我的收藏
			Intent intent9 = new Intent(getSherlockActivity(),
					MyCollectActivity.class);
			startActivity(intent9);
			// getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout9:// 收货地址管理
			Intent intent10 = new Intent(getSherlockActivity(),
					AddressActivity.class);
			startActivity(intent10);
			// getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout11:// 评价晒单
			Intent intent11 = new Intent(getSherlockActivity(),
					EvaluationActivity.class);
			startActivity(intent11);
			// getSherlockActivity().finish();
			break;
		case R.id.favorites:// 收藏
			Intent intent12 = new Intent(getSherlockActivity(),
					MyCollectActivity.class);
			startActivity(intent12);
			break;
		case R.id.message:// 收藏
			Intent intent13 = new Intent(getSherlockActivity(),
					PersonActivity.class);
			startActivity(intent13);
			break;
		case R.id.integration:// 收藏
			Intent intent14 = new Intent(getSherlockActivity(),
					IntegeralActivity.class);
			startActivity(intent14);
			break;
		case R.id.person_shopping_person_name:// 昵称
			// Toast.makeText(getSherlockActivity(), "",
			// Toast.LENGTH_LONG).show();
			break;
		case R.id.person_shopping_image_person:// 头像
			// Toast.makeText(getSherlockActivity(), "",
			// Toast.LENGTH_LONG).show();
			break;
		}
	}
}
