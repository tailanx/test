package com.yidejia.app.mall;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.opens.asyncokhttpclient.AsyncHttpResponse;
import com.opens.asyncokhttpclient.AsyncOkHttpClient;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.address.AddressActivity;
import com.yidejia.app.mall.evaluation.EvaluationActivity;
import com.yidejia.app.mall.favorite.MyCollectActivity;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.msg.MsgActivity;
import com.yidejia.app.mall.order.AllOrderActivity;
import com.yidejia.app.mall.order.AlreadyComActivity;
import com.yidejia.app.mall.order.AlreadyOrderActivity;
import com.yidejia.app.mall.order.ExchangeActivity;
import com.yidejia.app.mall.order.WaitDeliverActivity;
import com.yidejia.app.mall.order.WaitPayActivity;
import com.yidejia.app.mall.phone.PhoneOrderActivity;
import com.yidejia.app.mall.tickets.IntegeralActivity;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;

public class HomeMyMallActivity extends HomeBaseActivity implements
		OnClickListener {
	private View view;
	private FrameLayout frameLayout;
	private LayoutInflater inflater;
	private ImageView imageView;

	private RelativeLayout personMessage, mExchange, mAllOrder, mWaitPay,
			mwaitDeliver, mAlreadyOrder, mAlreadyCom, mCardVoucher, mMyCollect,
			mAddressManagement, mLayout11, mWaitComent;
	private TextView favorites, integration, message, nick, vip;
	private RelativeLayout aidouRelative;
	
	private TextView tvNumWaitPay;
	private TextView tvNumWaitDeliver;
	private TextView tvNumDeliverd;
	private TextView tvNumCompleted;
	private TextView tvNumComment;

	private ImageView head;// 头像
	private MyApplication myApplication;
	
	private String headUrl = "";	//头像url

	private ImageLoadingListener animateFirstListener;
	private DisplayImageOptions options;

	private String scoresNum; // 积分
	private String favolitenNum; // 收藏
	private String msgNum; // 消息中心
	private String numOrder; // 录入订单数
	private String numWaitDeliver;	//待发货订单数
	private String numDeliver;	//已发货订单数
	private String numCompleted;	//已完成订单数
	private String numComment;	//待评价订单数

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// cartsDataManage = new CartsDataManage();
		myApplication = MyApplication.getInstance();
		animateFirstListener = myApplication.getImageLoadingListener();
		options = myApplication.initGoodsImageOption();
		
		setContentView(R.layout.activity_main_fragment_layout);
		frameLayout = (FrameLayout) findViewById(R.id.main_fragment);
		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.person_shopping_mall1, null);
		frameLayout.addView(view);
		// int current = getIntent().getIntExtra("current", -1);
		// int next = getIntent().getIntExtra("next", -1);

		setupView(view);

		setCurrentActivityId(4);

	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.re_wait_comment:// 待评价
			intent = new Intent();
			intent.setClass(this, EvaluationActivity.class);
			break;
		case R.id.re_main2_main2_linearlayout4_all_message:// 点击消息中心
			intent = new Intent();
			intent.setClass(this, MsgActivity.class);
			break;

		case R.id.re_main2_main2_linearlayout4_all_return:// 点击退换货
			intent = new Intent();
			intent.setClass(this, ExchangeActivity.class);
			break;
		case R.id.re_main2_main2_linearlayout4_all_order:// 点击全部订单
			intent = new Intent();
			intent.setClass(this, AllOrderActivity.class);
			break;
		case R.id.rv_wait_pay:// 待付款订单
			intent = new Intent();
			intent.setClass(this, WaitPayActivity.class);
			break;
		case R.id.re_wait_deliver:// 待发货订单
			intent = new Intent();
			intent.setClass(this, WaitDeliverActivity.class);
			break;
		case R.id.re_alreay_deliver:// 已发货订单
			intent = new Intent();
			intent.setClass(this, AlreadyOrderActivity.class);
			break;
		case R.id.re_compelte_oreder:// 已完成订单
			intent = new Intent();
			intent.setClass(this, AlreadyComActivity.class);
			break;
		case R.id.re_main2_main2_linearlayout4_integer:// 积分卡券
			intent = new Intent();
			intent.setClass(this, IntegeralActivity.class);
			break;
		case R.id.re_main2_main2_linearlayout4_collect:// 我的收藏
			intent = new Intent();
			intent.setClass(this, MyCollectActivity.class);
			break;
		case R.id.re_main2_main2_linearlayout4_address:// 收货地址管理
			intent = new Intent();
			intent.setClass(this, AddressActivity.class);
			break;
		case R.id.re_main2_main2_linearlayout4_all_show://手机订单
			intent = new Intent();
			intent.setClass(this, PhoneOrderActivity.class);
			break;
		case R.id.tv_favorites:// 收藏
			intent = new Intent();
			intent.setClass(this, MyCollectActivity.class);
			break;
		case R.id.tv_message:// 收藏
			intent = new Intent();
			intent.setClass(this, MsgActivity.class);
			break;
		case R.id.tv_integration:// 收藏
			intent = new Intent();
			intent.setClass(this, IntegeralActivity.class);
			break;
		case R.id.tv_person_shopping_person_name:// 昵称
			break;
		case R.id.iv_person_shopping_image_person:// 头像
			break;
		case R.id.re_my_aidou:
			intent = new Intent(this, AidouActivity.class);
			break;

		}
		if (intent == null)
			return;
		startActivity(intent);
	}

	public void setupView(View view) {
		// // //实例化组件
		aidouRelative = (RelativeLayout) view.findViewById(R.id.re_my_aidou);
		aidouRelative.setOnClickListener(this);
		head = (ImageView) view
				.findViewById(R.id.iv_person_shopping_image_person);
		
		nick = (TextView) view
				.findViewById(R.id.tv_person_shopping_person_name);// 昵称

		vip = (TextView) view.findViewById(R.id.tv_person_shopping_person_vip);
		personMessage = (RelativeLayout) view
				.findViewById(R.id.re_main2_main2_linearlayout4_all_message);// 消息中心
		personMessage.setOnClickListener(this);
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

		favorites = (TextView) view.findViewById(R.id.tv_favorites);

		favorites.setOnClickListener(this);
		message = (TextView) view.findViewById(R.id.tv_message);
		message.setOnClickListener(this);
		integration = (TextView) view.findViewById(R.id.tv_integration);
		integration.setOnClickListener(this);
		
		tvNumWaitPay = (TextView) view.findViewById(R.id.tv_wait_pay_order_number);
		tvNumWaitDeliver = (TextView) view.findViewById(R.id.tv_wait_pay_deliver_number);
		tvNumDeliverd = (TextView) view.findViewById(R.id.tv_already_deliver_number);
		tvNumCompleted = (TextView) view.findViewById(R.id.tv_already_complete_number);
		tvNumComment = (TextView) view.findViewById(R.id.tv_wait_commen_number);
		
		// 头部实例化
		getActionbar();

	}

	// 头部实例化
	private void getActionbar() {
		setActionbarConfig();

		setTitle(R.string.my_mall);

		TextView tvBack = (TextView) findViewById(R.id.ab_common_back);
		tvBack.setVisibility(View.GONE);

		// 实例化组件
		// getSupportActionBar().setCustomView(R.layout.actionbar_mymall);
		imageView = (ImageView) findViewById(R.id.ab_common_iv_share);
		imageView.setVisibility(View.VISIBLE);
		imageView.setImageResource(R.drawable.setting);
		imageView.clearFocus();
		imageView.setFocusable(true);
		imageView.setOnClickListener(editListener);
	}

	private OnClickListener editListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(HomeMyMallActivity.this,
					EditorActivity.class);
			startActivity(intent);

		}
	};

	@Override
	public void onStart() {
		super.onStart();

		getNumData();

		String name = myApplication.getNick();
		if (name == null || "".equals(name)) {
			nick.setText(myApplication.getUserId());
		} else {
			nick.setText(name);
		}

		String vip1 = myApplication.getVip();
		if (vip1 == null || "".equals(vip1)) {
			vip.setText("VIP0");
		} else {
			vip.setText(vip1);
		}
		
		String imgUrl = myApplication.getUserHeadImg();
		if(!imgUrl.equals(headUrl)){
			ImageLoader.getInstance().init(MyApplication.getInstance().initConfig());
			
			ImageLoader.getInstance().displayImage(myApplication.getUserHeadImg(), head, options, animateFirstListener);
			head.setOnClickListener(this);// 图像
			headUrl = imgUrl;
		}
	}

	/** 获取收藏，积分，消息中心数字 **/
	private void getNumData() {
		String url = new JNICallBack().getHttp4GetCount(
				myApplication.getUserId(), myApplication.getToken());
		HttpClientUtil client = new HttpClientUtil(this);
		client.setShowErrMessage(false);
		client.getHttpResp(url, new IHttpResp() {

			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
				parseCountJson(content);
				setCount();
			}

		});
	}

	/** 解析收藏，积分，消息中心数字数据 **/
	private void parseCountJson(String content) {
		JSONObject httpObject;
		try {
			httpObject = new JSONObject(content);
			int code = httpObject.getInt("code");
			if (code == 1) {
				String response = httpObject.getString("response");
				JSONObject resObject = new JSONObject(response);
				scoresNum = resObject.optString("scores");
				numOrder = resObject.optString("order");
				numWaitDeliver = resObject.optString("pay_order");
				numDeliver = resObject.optString("ship_order");
				numCompleted = resObject.optString("sign_order");
				numComment = resObject.optString("pingjia");
				
				favolitenNum = resObject.optString("favoliten");
				msgNum = resObject.optString("msg");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/** 设置收藏，消息中心，积分的个数 **/
	private void setCount() {
		if (TextUtils.isEmpty(favolitenNum)) {
			favorites.setText(0 + "");
		} else {
			favorites.setText(favolitenNum);
		}

		if (TextUtils.isEmpty(msgNum)) {
			message.setText(0 + "");
		} else {
			message.setText(msgNum);
		}

		if (TextUtils.isEmpty(scoresNum)) {
			integration.setText(0 + "");
		} else {
			integration.setText(scoresNum);
		}
		
		if(TextUtils.isEmpty(numComment)){
			tvNumComment.setText("0");
		} else {
			tvNumComment.setText(numComment);
		}
		
		if(TextUtils.isEmpty(numCompleted)){
			tvNumCompleted.setText("0");
		} else {
			tvNumCompleted.setText(numCompleted);
		}
		
		if(TextUtils.isEmpty(numDeliver)){
			tvNumDeliverd.setText("0");
		} else {
			tvNumDeliverd.setText(numDeliver);
		}
		
		if(TextUtils.isEmpty(numOrder)){
			tvNumWaitPay.setText("0");
		} else {
			tvNumWaitPay.setText(numOrder);
		}
		
		if(TextUtils.isEmpty(numWaitDeliver)){
			tvNumWaitDeliver.setText("0");
		} else {
			tvNumWaitDeliver.setText(numWaitDeliver);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		// StatService.onResume(this);
		StatService.onPageStart(this, "我的商城页面");
	}

	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "我的商城页面");
	}

	
}
