package com.yidejia.app.mall.main;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.MainFragmentActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.MyMallActivity.InnerReceiver;
import com.yidejia.app.mall.datamanage.CartsDataManage;
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

public class HomeMyMaActivity extends SherlockActivity implements
		OnClickListener {
	private View view;
	private FrameLayout frameLayout;
	private LayoutInflater inflater;
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
	private YLImageButton mButton2;
	private YLImageButton mButton8;
	private TextView favorites;// 收藏
	private TextView integration;// 积分
	private TextView message;// 消息
	private ImageView head;// 头像
	private TextView nick;// 昵称
	private TextView vip;
	private MyApplication myApplication;
	private PersonCountDataManage personCountDataManage;
	private InnerReceiver receiver;
	private Resources res;

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
		personMessage.setVisibility(ViewGroup.GONE);// 这个版本隐藏这个功能
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

		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		// 实例化组件
		getSupportActionBar().setCustomView(R.layout.actionbar_mymall);
		imageView = (ImageView) findViewById(R.id.person_shopping_button1);
		imageView.clearFocus();
		imageView.setFocusable(true);
		imageView.setOnClickListener(edit);

		favorites = (TextView) view.findViewById(R.id.favorites);

		favorites.setOnClickListener(this);
		message = (TextView) view.findViewById(R.id.message);
		message.setOnClickListener(this);
		integration = (TextView) view.findViewById(R.id.integration);
		integration.setOnClickListener(this);

	}

	private OnClickListener edit = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(HomeMyMaActivity.this,
					EditorActivity.class);
			startActivity(intent);

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		cartsDataManage = new CartsDataManage();
		myApplication = (MyApplication) getApplication();
		setContentView(R.layout.activity_main_fragment_layout);
		frameLayout = (FrameLayout) findViewById(R.id.main_fragment);
		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.person_shopping_mall1, null);
		frameLayout.addView(view);

		setupView(view);
		res = getResources();
		initNavView();
		
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
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(HomeMyMaActivity.this,
						EditorActivity.class);
				HomeMyMaActivity.this.startActivity(intent);
			}
		});

	}

	private RelativeLayout downHomeLayout;
	private RelativeLayout downGuangLayout;
	private RelativeLayout downSearchLayout;
	private RelativeLayout downShoppingLayout;
	private RelativeLayout downMyLayout;
	private ImageView down_home_imageView;// 首页按钮图片
	private ImageView down_guang_imageView;// 逛按钮图片
	private ImageView down_search_imageView;// 搜索按钮图片
	private ImageView down_shopping_imageView; // 购物车按钮图片
	private ImageView down_my_imageView; // 我的商城按钮图片
	private CartsDataManage cartsDataManage;
	private TextView down_home_textview;
	private TextView down_guang_textview;
	private TextView down_search_textview;
	private TextView down_shopping_textview;
	private TextView down_my_textview;
	private int number;
	private Button cartImage;

	/**
	 * 底部事件
	 */
	/**
	 * 初始化底部导航栏
	 */
	private void initNavView() {
		// 改变底部首页背景，有按下去的效果的背景
		number = cartsDataManage.getCartAmount();
		cartImage = (Button) findViewById(R.id.down_shopping_cart);
		if (0 == number) {
			cartImage.setVisibility(View.GONE);
		} else {
			cartImage.setText(number + "");
		}
		downGuangLayout = (RelativeLayout) findViewById(R.id.down_guang_layout);
		downHomeLayout = (RelativeLayout) findViewById(R.id.down_home_layout);
		downSearchLayout = (RelativeLayout) findViewById(R.id.down_search_layout);
		downShoppingLayout = (RelativeLayout) findViewById(R.id.down_shopping_layout);
		downMyLayout = (RelativeLayout) findViewById(R.id.down_my_layout);

		down_home_imageView = (ImageView) findViewById(R.id.down_home_icon);
		down_home_textview = (TextView) findViewById(R.id.down_home_text);
		down_my_imageView = (ImageView) findViewById(R.id.down_my_icon);
		down_my_textview = (TextView) findViewById(R.id.down_my_text);

		downHomeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeMyMaActivity.this,
						HomeMallActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
			}
		});
		downSearchLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeMyMaActivity.this,
						HomeSearchActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
			}
		});
		downShoppingLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeMyMaActivity.this,
						HomeCarActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
			}
		});
		// downMyLayout.setOnClickListener(this);

		down_home_textview.setTextColor(this.getResources().getColor(
				R.color.white_white));
		downHomeLayout.setBackgroundResource(R.drawable.downbg);
		down_home_imageView.setImageResource(R.drawable.home_normal);

		downMyLayout.setBackgroundResource(R.drawable.down_hover1);
		down_my_imageView.setImageResource(R.drawable.down_my_hover);
		down_my_textview.setTextColor(res.getColor(R.color.white));
		downGuangLayout.setVisibility(ViewGroup.GONE);
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
			Intent intent1 = new Intent(this, PersonActivity.class);
			startActivity(intent1);
			// getSherlockActivity().finish();
			break;

		case R.id.main2_main2_linearlayout10:// 点击退换货
			Intent intent2 = new Intent(this, ExchangeActivity.class);
			startActivity(intent2);
			// getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout1:// 点击全部订单
			Intent intent3 = new Intent(this, AllOrderActivity.class);
			startActivity(intent3);
			// getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout2:// 待付款订单
			Intent intent4 = new Intent(this, WaitPayActivity.class);
			startActivity(intent4);
			// getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout6:// 待发货订单
			Intent intent5 = new Intent(this, WaitDeliverActivity.class);
			startActivity(intent5);
			// getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout4:// 已发货订单
			Intent intent6 = new Intent(this, AlreadyOrderActivity.class);
			startActivity(intent6);
			// getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout3:// 已完成订单
			Intent intent7 = new Intent(this, AlreadyComActivity.class);
			startActivity(intent7);
			// getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout5:// 积分卡券
			Intent intent8 = new Intent(this, IntegeralActivity.class);
			startActivity(intent8);
			// getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout7:// 我的收藏
			Intent intent9 = new Intent(this, MyCollectActivity.class);
			startActivity(intent9);
			// getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout9:// 收货地址管理
			Intent intent10 = new Intent(this, AddressActivity.class);
			startActivity(intent10);
			// getSherlockActivity().finish();
			break;
		case R.id.main2_main2_linearlayout11:// 评价晒单
			Intent intent11 = new Intent(this, EvaluationActivity.class);
			startActivity(intent11);
			// getSherlockActivity().finish();
			break;
		case R.id.favorites:// 收藏
			Intent intent12 = new Intent(this, MyCollectActivity.class);
			startActivity(intent12);
			break;
		case R.id.message:// 收藏
			Intent intent13 = new Intent(this, PersonActivity.class);
			startActivity(intent13);
			break;
		case R.id.integration:// 收藏
			Intent intent14 = new Intent(this, IntegeralActivity.class);
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

	// 双击返回键退出程序
	private long exitTime = 0;

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.exit),
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				// ((MyApplication)getApplication()).setUserId("");
				// ((MyApplication)getApplication()).setToken("");
				finish();
				// System.exit(0);
			}
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		personCountDataManage = new PersonCountDataManage(HomeMyMaActivity.this);
		personCountDataManage.getCountData(myApplication.getUserId(), myApplication.getToken());
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
			Log.e("info", inString);
			integration.setText(inString);
		}
	}
	
}
