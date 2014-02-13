package com.yidejia.app.mall.order;

import java.util.ArrayList;

import org.apache.http.HttpStatus;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.opens.asyncokhttpclient.AsyncHttpResponse;
import com.opens.asyncokhttpclient.AsyncOkHttpClient;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.address.ModelAddresses;
import com.yidejia.app.mall.address.ParseAddressJson;
import com.yidejia.app.mall.goodinfo.GoodsInfoActivity;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.view.ChangePayActivity;

public class OrderDetailActivity extends BaseActivity implements
		OnClickListener {
	private LinearLayout layout;// 订单内容
	private TextView emsTextView;// 快递费
	private TextView sumTextView;// 总共的价格
	private TextView orderTime;// 下单时间
	private TextView nameTextView;// 收件人姓名
	private TextView phoneTextView;// 收件人电话
	private TextView detailTextView;// 收件人地址
	private TextView priceTextView;// 订单物品价格
	private TextView payButton;// 立即付款
	private TextView orderNumber;// 订单的编号
	private TextView changePayTypeTextView;// 支付方式修改
	// private RelativeLayout orderAddressLayout;// 收件人地址的布局
	private String orderCode;// 传递过来的订单号
	private String orderPrice;// 传递过来的价格总数
	private RelativeLayout changePay;// 更改支付方式
	private TextView payDetail;// 具体的支付方式

	// private String TAG = getClass().getName();
	private int a = -1;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setActionBar();
		setActionbarConfig();
		setTitle(R.string.order_detail);
		setContentView(R.layout.order_detail);

		findIds();

		initDisplayImageOption();

		Intent intent = getIntent();
		orderCode = intent.getExtras().getString("OrderCode");
		orderPrice = intent.getExtras().getString("OrderPrice");
		ArrayList<Cart> carts = (ArrayList<Cart>) intent
				.getSerializableExtra("carts");

		Log.i("info", orderCode + "   orderCode");

		layout = (LinearLayout) findViewById(R.id.order_detail_relative2);

		if (null == carts) {
			Toast.makeText(OrderDetailActivity.this, "购物清单为空",
					Toast.LENGTH_SHORT).show();
			return;
		}

		int length = carts.size();
		for (int i = 0; i < length; i++) { // 加载购物清单界面
			View view = getLayoutInflater().inflate(R.layout.order_detail_item,
					null);
			final Cart cart = carts.get(i);
			setCartsInfo(view, cart);
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent goodsInfoIntent = new Intent(
							OrderDetailActivity.this, GoodsInfoActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("goodsId", cart.getUId());
					goodsInfoIntent.putExtras(bundle);
					startActivity(goodsInfoIntent);
				}
			});
			layout.addView(view);
		}
		getData();
	}

	private void findIds() {
		orderTime = (TextView) findViewById(R.id.order_detail_time_number);
		emsTextView = (TextView) findViewById(R.id.order_detail_yunhui_sum);
		nameTextView = (TextView) findViewById(R.id.order_detail_name);
		phoneTextView = (TextView) findViewById(R.id.order_detail_number);
		detailTextView = (TextView) findViewById(R.id.order_detail_position);
		priceTextView = (TextView) findViewById(R.id.order_detail_dingdan_sum);
		emsTextView = (TextView) findViewById(R.id.order_detail_yunhui_sum);
		sumTextView = (TextView) findViewById(R.id.order_detail_go_pay_show_pay_money);
		payButton = (TextView) findViewById(R.id.order_detail_pay);
		orderNumber = (TextView) findViewById(R.id.order_detail_biaohao_number);
		orderTime = (TextView) findViewById(R.id.order_detail_time_number);
		changePayTypeTextView = (TextView) findViewById(R.id.change_pay_type);
		changePay = (RelativeLayout) findViewById(R.id.re_order_detail_change_pay);
		changePay.setOnClickListener(this);
		payDetail = (TextView) findViewById(R.id.go_pay_address_way_detail);
		// orderAddressLayout = (RelativeLayout)
		// findViewById(R.id.order_address_layout);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			return;
		}
		if (requestCode == Consts.AddressRequestCode
				&& resultCode == Consts.AddressResponseCode) {
			ModelAddresses addresses1 = (ModelAddresses) data.getExtras()
					.getSerializable("addresses1");
			setAddress(addresses1);
		} else if (requestCode == Consts.CHANGE_REQUEST
				&& resultCode == Consts.CHANGE_RESPONSE) {
			a = data.getExtras().getInt(Consts.CHANGE_PAY);// 获取返回的参数
			if (a != 0) {
				switch (a) {
				case Consts.CHANGE_ZHIFUBAO:// 判断是不是支付宝支付
					payDetail
							.setText(getString(R.string.change_pay_for_zhifubao));
					break;
				case Consts.CHANGE_WANGYE:// 判断是不是支付宝网页支付
					payDetail
							.setText(getString(R.string.change_pay_for_wangye));

					break;
				case Consts.CHANGE_CAIFUTONG:// 判断是不是财付通支付
					payDetail
							.setText(getString(R.string.change_pay_for_caifutong));

					break;
				case Consts.CHANGE_YINLIAN:// 判断是不是银联支付
					payDetail
							.setText(getString(R.string.change_pay_for_yinlian));

					break;
				}
			}
		}
	}

	/** 根据订单号获取订单信息 **/
	private void getData() {
		if (!ConnectionDetector.isConnectingToInternet(this)) {
			Toast.makeText(this, getResources().getString(R.string.no_network),
					Toast.LENGTH_LONG).show();
			return;
		}

		String url = new JNICallBack().getHttp4GetOrderByCode(orderCode);

		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.get(url, new AsyncHttpResponse() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, content);
				if (HttpStatus.SC_OK == statusCode) {
					ParseOrder parseOrder = new ParseOrder(
							OrderDetailActivity.this);
					boolean isSuccess = parseOrder.parseOrderDetail(content);
					if (isSuccess) {
						Order order = parseOrder.getOrderDetail();
						showDetailView(order);

						String recipient_id = parseOrder.getRecipient_id();
						getAddressData(recipient_id);
					}
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onError(error, content);
			}

		});
	}

	private void showDetailView(Order order) {

		// addresses = new ArrayList<ModelAddresses>();

		priceTextView.setText(orderPrice);
		String expressNum = order.getShipFee();
		orderNumber.setText(orderCode);
		emsTextView.setText(getResources().getString(R.string.unit)
				+ expressNum);
		orderTime.setText(order.getDate());
		// 录入状态时才显示付款和修改支付方式的按钮
		if ("录入".equals(order.getStatus())) {
			payButton.setVisibility(View.VISIBLE);
			payButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO 提交订单
					// TaskGetTn task = new TaskGetTn(activity);
					// task.getOrderTn(orderCode);
				}
			});
			changePayTypeTextView.setVisibility(View.VISIBLE);
			// orderAddressLayout.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// Intent intent = new Intent(activity,
			// PayAddress.class);
			// activity.startActivityForResult(intent,
			// Consts.AddressRequestCode);
			// }
			// });
		} else {
			payButton.setVisibility(View.GONE);
			changePayTypeTextView.setVisibility(View.GONE);
		}
		try {

			float odprice = Float.parseFloat(orderPrice);
			float exprice = Float.parseFloat(expressNum);

			sumTextView.setText(getResources().getString(R.string.unit)
					+ (odprice + exprice));
		} catch (NumberFormatException e) {
			sumTextView.setText(R.string.price_error);
		}

	}

	/** 获取收件人地址信息 **/
	private void getAddressData(String recipient_id) {
		if (TextUtils.isEmpty(recipient_id)) {
			toastAddressError();
			return;
		}

		if (!ConnectionDetector
				.isConnectingToInternet(OrderDetailActivity.this)) {
			Toast.makeText(this, getResources().getString(R.string.no_network),
					Toast.LENGTH_SHORT).show();
			return;
		}
		String url = new JNICallBack().getHttp4GetAddress("recipient_id="
				+ recipient_id, "0", "1", "", "", "");

		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.get(url, new AsyncHttpResponse() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, content);
				if (HttpStatus.SC_OK == statusCode) {
					ParseAddressJson parseAddressJson = new ParseAddressJson();
					boolean isSuccess = parseAddressJson
							.parseAddressListJson(content);
					ArrayList<ModelAddresses> addressList = parseAddressJson
							.getAddresses();
					if (isSuccess && null != addressList
							&& !addressList.isEmpty()) {
						ModelAddresses addresses = addressList.get(0);
						setAddress(addresses);
					} else {
						toastAddressError();
					}
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onError(error, content);
			}

		});
	}

	/** 提示收件人地址出错 **/
	private void toastAddressError() {
		Toast.makeText(OrderDetailActivity.this, "获取收件人出错!", Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * 设置购物清单商品信息
	 * 
	 * @param view
	 * @param cart
	 */
	private void setCartsInfo(View view, Cart cart) {
		TextView order_detail_item_text = (TextView) view
				.findViewById(R.id.order_detail_item_text);
		order_detail_item_text.setText(cart.getProductText());
		ImageView order_detail_item_image = (ImageView) view
				.findViewById(R.id.order_detail_item_image);

		ImageLoader.getInstance()
				.init(MyApplication.getInstance().initConfig());
		imageLoader.displayImage(cart.getImgUrl(), order_detail_item_image,
				options, animateFirstListener);
		TextView order_detail_item_sum = (TextView) view
				.findViewById(R.id.order_detail_item_sum);
		order_detail_item_sum.setText(getResources().getString(R.string.unit)
				+ cart.getPrice());
		TextView order_detail_item_count = (TextView) view
				.findViewById(R.id.order_detail_item_count);
		order_detail_item_count.setText(getResources().getString(
				R.string.amount)
				+ cart.getAmount());
	}

	/** 设置收件人地址信息 **/
	private void setAddress(ModelAddresses address) {

		if (null == address)
			return;

		StringBuffer sb = new StringBuffer();
		// Addresses address = addresses.get(0);
		nameTextView.setText(address.getName());
		phoneTextView.setText(address.getHandset());
		sb.append(address.getProvice());
		sb.append(address.getCity());
		sb.append(address.getArea());
		sb.append(address.getAddress());
		detailTextView.setText(sb.toString());
	}

	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageLoadingListener animateFirstListener;

	private void initDisplayImageOption() {
		options = MyApplication.getInstance().initGoodsImageOption();
		animateFirstListener = MyApplication.getInstance()
				.getImageLoadingListener();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// StatService.onResume(this);
		StatService.onPageStart(this, "订单详情页面");
	}

	@Override
	protected void onPause() {
		super.onPause();
		// StatService.onPause(this);
		StatService.onPageEnd(this, "订单详情页面");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.re_order_detail_change_pay:
			Intent intent = new Intent(this, ChangePayActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt(Consts.CHANGE_PAY, a);
			intent.putExtras(bundle);
			OrderDetailActivity.this.startActivityForResult(intent,
					Consts.CHANGE_REQUEST);
			break;
		}
	}

}
