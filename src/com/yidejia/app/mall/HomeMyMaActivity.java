package com.yidejia.app.mall;

import java.io.IOException;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
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
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.datamanage.PersonCountDataManage;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.net.user.GetCount;
import com.yidejia.app.mall.util.BottomChange;
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

public class HomeMyMaActivity extends SherlockFragmentActivity implements
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
	private RelativeLayout mWaitComent;
	private TextView favorites;// 收藏
	private TextView integration;// 积分
	private TextView message;// 消息
	private ImageView head;// 头像
	private TextView nick;// 昵称
	private TextView vip;
	private MyApplication myApplication;
	private PersonCountDataManage personCountDataManage;
	private Resources res;

	private BottomChange bottomChange;
	private RelativeLayout bottomLayout;

	public void setupView(View view) {
		// // //实例化组件
		head = (ImageView) view
				.findViewById(R.id.iv_person_shopping_image_person);
		head.setOnClickListener(this);// 图像
		nick = (TextView) view
				.findViewById(R.id.tv_person_shopping_person_name);// 昵称

		vip = (TextView) view.findViewById(R.id.tv_person_shopping_person_vip);
		personMessage = (RelativeLayout) view
				.findViewById(R.id.re_main2_main2_linearlayout4_all_message);// 消息中心
		personMessage.setOnClickListener(this);
		personMessage.setVisibility(ViewGroup.GONE);// 这个版本隐藏这个功能
		mExchange = (RelativeLayout) view
				.findViewById(R.id.re_main2_main2_linearlayout4_all_return);// 退换货
		mExchange.setOnClickListener(this);
		mAllOrder = (RelativeLayout) view
				.findViewById(R.id.re_main2_main2_linearlayout4_all_order);// 全部订单
		mAllOrder.setOnClickListener(this);
		mWaitPay = (RelativeLayout) view.findViewById(R.id.rv_wait_pay);// 待付快订单
		mWaitPay.setOnClickListener(this);
		mwaitDeliver = (RelativeLayout) view.findViewById(R.id.re_wait_deliver);// 待发货订单
		mwaitDeliver.setOnClickListener(this);
		mAlreadyOrder = (RelativeLayout) view
				.findViewById(R.id.re_alreay_deliver);// 已发货订单
		mAlreadyOrder.setOnClickListener(this);
		mAlreadyCom = (RelativeLayout) view
				.findViewById(R.id.re_compelte_oreder);// "已完成订单"
		mAlreadyCom.setOnClickListener(this);
		mCardVoucher = (RelativeLayout) view
				.findViewById(R.id.re_main2_main2_linearlayout4_integer);// 积分卡券
		mCardVoucher.setOnClickListener(this);
		mMyCollect = (RelativeLayout) view
				.findViewById(R.id.re_main2_main2_linearlayout4_collect);// 我的收藏
		mMyCollect.setOnClickListener(this);
		mAddressManagement = (RelativeLayout) view
				.findViewById(R.id.re_main2_main2_linearlayout4_address);// 收货地址管理
		mAddressManagement.setOnClickListener(this);
		mLayout11 = (RelativeLayout) view
				.findViewById(R.id.re_main2_main2_linearlayout4_all_show);// 评价晒单
		mLayout11.setOnClickListener(this);

		mWaitComent = (RelativeLayout) view.findViewById(R.id.re_wait_comment);// 待评价
		mWaitComent.setOnClickListener(this);
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

		favorites = (TextView) view.findViewById(R.id.tv_favorites);

		favorites.setOnClickListener(this);
		message = (TextView) view.findViewById(R.id.tv_message);
		message.setOnClickListener(this);
		integration = (TextView) view.findViewById(R.id.tv_integration);
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
		int current = getIntent().getIntExtra("current", -1);
		int next = getIntent().getIntExtra("next", -1);

		setupView(view);
		res = getResources();
		// 设置底部
		bottomLayout = (RelativeLayout) findViewById(R.id.down_parent_layout);
		bottomChange = new BottomChange(this, bottomLayout);
		if (current != -1 || next != -1) {
			bottomChange.initNavView(current, next);
		}
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

		downHomeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeMyMaActivity.this,
						HomeMallActivity.class);
				startActivity(intent);
				HomeMyMaActivity.this.finish();
				overridePendingTransition(R.anim.activity_in,
						R.anim.activity_out);
			}
		});
		downSearchLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeMyMaActivity.this,
						HomeSearchActivity.class);

				intent.putExtra("current", 3);
				intent.putExtra("next", 1);
				startActivity(intent);
				HomeMyMaActivity.this.finish();
				overridePendingTransition(R.anim.activity_in,
						R.anim.activity_out);
			}
		});
		downShoppingLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeMyMaActivity.this,
						HomeCarActivity.class);

				intent.putExtra("current", 3);
				intent.putExtra("next", 2);
				startActivity(intent);
				HomeMyMaActivity.this.finish();
				overridePendingTransition(R.anim.activity_in,
						R.anim.activity_out);
			}
		});
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.re_wait_comment:// 待评价
			break;
		case R.id.re_main2_main2_linearlayout4_all_message:// 点击消息中心
			intent.setClass(this, PersonActivity.class);
			break;

		case R.id.re_main2_main2_linearlayout4_all_return:// 点击退换货
			intent.setClass(this, ExchangeActivity.class);
			break;
		case R.id.re_main2_main2_linearlayout4_all_order:// 点击全部订单
			intent.setClass(this, AllOrderActivity.class);
			break;
		case R.id.rv_wait_pay:// 待付款订单
			intent.setClass(this, WaitPayActivity.class);
			break;
		case R.id.re_wait_deliver:// 待发货订单
			intent.setClass(this, WaitDeliverActivity.class);
			break;
		case R.id.re_alreay_deliver:// 已发货订单
			intent.setClass(this, AlreadyOrderActivity.class);
			break;
		case R.id.re_compelte_oreder:// 已完成订单
			intent.setClass(this, AlreadyComActivity.class);
			break;
		case R.id.re_main2_main2_linearlayout4_integer:// 积分卡券
			intent.setClass(this, IntegeralActivity.class);
			break;
		case R.id.re_main2_main2_linearlayout4_collect:// 我的收藏
			intent.setClass(this, MyCollectActivity.class);
			break;
		case R.id.re_main2_main2_linearlayout4_address:// 收货地址管理
			intent.setClass(this, AddressActivity.class);
			break;
		case R.id.re_main2_main2_linearlayout4_all_show:// 评价晒单
			intent.setClass(this, EvaluationActivity.class);
			break;
		case R.id.tv_favorites:// 收藏
			intent.setClass(this, MyCollectActivity.class);
			break;
		case R.id.tv_message:// 收藏
			intent.setClass(this, PersonActivity.class);
			break;
		case R.id.tv_integration:// 收藏
			intent.setClass(this, IntegeralActivity.class);
			break;
		case R.id.tv_person_shopping_person_name:// 昵称
			// Toast.makeText(getSherlockActivity(), "",
			// Toast.LENGTH_LONG).show();
			break;
		case R.id.iv_person_shopping_image_person:// 头像
			// Toast.makeText(getSherlockActivity(), "",
			// Toast.LENGTH_LONG).show();
			break;
		}
		startActivity(intent);
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
		closeTask();
		getNumTask = new GetNumTask();
		getNumTask.execute();
	}

	private GetNumTask getNumTask;
	private String scores;
	private String order;
	private String favoliten;
	private String msg;

	private class GetNumTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			GetCount getCount = new GetCount();
			try {
				boolean issuccess = getCount.analysis(getCount.getHttpResponse(
						myApplication.getUserId(), myApplication.getToken()));
				scores = getCount.getScores();
				favoliten = getCount.getFavoliten();
				return issuccess;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeOutEx e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			setCount(favoliten, null, scores);
		}

	}

	private void setCount(String faString, String msString, String inString) {
		// Log.i("info", faString+"     faString");
		if (faString == null || "".equals(faString)) {
			favorites.setText(0 + "");
		} else {
			favorites.setText(faString);
		}

		if (msString == null || "".equals(msString)) {
			message.setText(0 + "");
		} else {
			message.setText(msString);
		}

		if (inString == null || "".equals(inString)) {
			integration.setText(0 + "");
		} else {
			Log.e("info", inString);
			integration.setText(inString);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		closeTask();
	}

	private void closeTask() {
		if (null != getNumTask
				&& getNumTask.getStatus().RUNNING == AsyncTask.Status.RUNNING) {
			getNumTask.cancel(true);
		}
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
