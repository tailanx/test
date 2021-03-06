package com.yidejia.app.mall.pay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpStatus;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
//import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.opens.asyncokhttpclient.AsyncHttpResponse;
import com.opens.asyncokhttpclient.AsyncOkHttpClient;
import com.opens.asyncokhttpclient.RequestParams;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.address.AddressActivity;
import com.yidejia.app.mall.address.EditNewAddressActivity;
import com.yidejia.app.mall.address.ModelAddresses;
import com.yidejia.app.mall.address.ParseAddressJson;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.express.ParseCredit;
import com.yidejia.app.mall.express.ParseExpressJson;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.Express;
import com.yidejia.app.mall.model.FreePost;
import com.yidejia.app.mall.model.Specials;
import com.yidejia.app.mall.order.ParseOrder;
import com.yidejia.app.mall.util.ActivityIntentUtil;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;
//import com.yidejia.app.mall.util.HttpClientUtil;
//import com.yidejia.app.mall.util.IHttpResp;
import com.yidejia.app.mall.util.PayUtil;
import com.yidejia.app.mall.util.VersonNameUtil;
import com.yidejia.app.mall.view.ExchangeFreeActivity;
import com.yidejia.app.mall.view.LoginActivity;
import com.yidejia.app.mall.youhui.YouhuiActivity;

public class CstmPayActivity extends BaseActivity implements OnClickListener {
	private TextView tv_userName;// 用户名
	private TextView tv_phoneName;// 电话号码
	private TextView tv_address;// 收货地址
	private TextView tv_peiSong;// 配送地址
	private CheckBox cb_general;// 普通配送
	private CheckBox cb_emsBox;// ems配送
	private TextView tv_generalPrice;// 普通配送价格
	private TextView tv_emsPrice;// ems配送价格
	private CheckBox cb_zhifubao;// 支付宝
	private CheckBox cb_zhifubaowangye;// 支付宝网页支付
	private CheckBox cb_yinlian;// 银联支付
	private CheckBox cb_caifutong;// 财付通支付
	private TextView tv_sumPrice;// 总的价格
	private TextView tv_saveOrderBtn;// 提交订单
	private EditText et_comment;// 评论
	private AlertDialog dialog;
	private RelativeLayout addressRelative;
	private RelativeLayout rlYouhuiquan;// 使用优惠折扣
	private TextView tvTicketName;	//使用优惠券的名称

	private RelativeLayout rl_peisong; // 选择配送中心的layout

	public static ArrayList<Specials> arrayListFree;
	public static ArrayList<Specials> arrayListExchange;

	private ScrollView scv_go_pay;

	private String peiSongCenter = "";// //配送中心
	private String recipientId = "";// 收件人id
	private String expressNum = "";// 快递费用
	private String postMethod = "EMS";// 快递方式
	private boolean isFree = false;// 是否满足免邮条件
	private String goods;// 商品串
	private String orderCode;// 订单号
	private String resp_code;// 返回状态码
	private String tn;// 流水号
	private LinearLayout layout;
	public static ArrayList<Cart> cartList;
	private float needJifen = 0;	//用户本次购买消耗积分
	public static String voucherString1;// 积分
	private ModelAddresses showAddress;
	private ArrayList<FreePost> freePosts; // 免邮条件列表
	private ArrayList<Express> distributions; // 配送中心列表
	private String preId; // 配送中心的id
	private String province; // 省份

	private String userId;
	private String token;
	private ArrayList<Cart> carts;// 购物车的数据，非换购的数据
	// private RelativeLayout reLayout;
	private String isCartActivity;
//	private String sum;
//	private String contentType;

	private float goodsPrice;
	private float voucher;	//用户总积分
//	private final String TAG = getClass().getName();

	private boolean isCanPay = false;	//所有数据都获取成功，可以点击支付按钮
	private String tickId = "0";	//优惠券id
	private String tickMoney = "0.0";	//优惠券面值
	private boolean isCanHuanGou = true;		//是否可以免费换购
	private boolean isCanTicket = true;	//是否可以使用优惠券
	private String ruleId = "";		//伊日惠id
	
	private String generalNum = "0";		//快递费用
	private String emsNum = "0";	//ems邮费

	public static ArrayList<Specials> getArrayListFree() {
		return arrayListFree;
	}

	public static void setArrayListFree(ArrayList<Specials> arrayListFree) {
		CstmPayActivity.arrayListFree = arrayListFree;
	}

	public static ArrayList<Specials> getArrayListExchange() {
		return arrayListExchange;
	}

	public static void setArrayListExchange(
			ArrayList<Specials> arrayListExchange) {
		CstmPayActivity.arrayListExchange = arrayListExchange;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);

			arrayListFree = new ArrayList<Specials>();
			
			setActionbarConfig();
			setTitle(R.string.comfirm_order);

			Intent intent = getIntent();
			Bundle bundle = intent.getExtras();
			goodsPrice = bundle.getFloat("price");
			isCanHuanGou = bundle.getBoolean("canHuanGou", true);
			isCanTicket = bundle.getBoolean("canTicket", true);
			ruleId = bundle.getString("ruleId");
			if(null == ruleId) ruleId = "";
			carts = (ArrayList<Cart>) intent.getSerializableExtra("carts");
			isCartActivity = intent.getStringExtra("cartActivity");

			// 付款之前先判断用户是否登陆
			if (!MyApplication.getInstance().getIsLogin()) {
				Toast.makeText(this,
						getResources().getString(R.string.please_login),
						Toast.LENGTH_LONG).show();
				Intent intent1 = new Intent(this, LoginActivity.class);
				startActivity(intent1);
				this.finish();
				return;
			} else {

				userId = MyApplication.getInstance().getUserId();
				token = MyApplication.getInstance().getToken();
//				contentType = "application/x-www-form-urlencoded;charset=UTF-8";

				show(carts, false);

			}

			dialog = new Builder(CstmPayActivity.this)
					.setTitle(
							getResources().getString(R.string.produce_exchange))
					.setIcon(android.R.drawable.dialog_frame)
					.setMessage(
							getResources().getString(R.string.exchange_produce))
					.setPositiveButton(
							getResources().getString(R.string.sure),
							new android.content.DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									Intent intent = new Intent(
											CstmPayActivity.this,
											ExchangeFreeActivity.class);
									Bundle bundle = new Bundle();
									bundle.putString("cartActivity",
											isCartActivity);
//									bundle.putString("price", sum + "");
									bundle.putFloat("price", goodsPrice);
									intent.putExtras(bundle);
									intent.putExtra("carts", carts);
									intent.putExtra("voucher", voucher);
									CstmPayActivity.this
											.startActivityForResult(
													intent,
													Consts.CstmPayActivity_Request);
								}
							})
					.setNegativeButton(
							getResources().getString(R.string.cancel), null)
					.create();
			
			// 添加地址、快递费用、配送中心
			getDefaultAddress();
			
			if(isCanHuanGou)
				getCredit();

			postMethod = getResources().getString(R.string.ship_post);

		} catch (NumberFormatException e) {
			e.printStackTrace();
			Toast.makeText(CstmPayActivity.this,
					getResources().getString(R.string.price_error),
					Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * 实例化控件
	 */
	public void setupShow() {
		try {
			rlYouhuiquan = (RelativeLayout) findViewById(R.id.go_shopping_use_evalution);
			tvTicketName = (TextView) findViewById(R.id.tv_ticket_name);
			tvTicketName.setText("");
			tv_sumPrice = (TextView) findViewById(R.id.go_pay_show_pay_money);
			tv_saveOrderBtn = (TextView) findViewById(R.id.save_order_btn);
			tv_userName = (TextView) findViewById(R.id.go_pay_name);
			tv_phoneName = (TextView) findViewById(R.id.go_pay_number);
			tv_address = (TextView) findViewById(R.id.tv_address_details);
			tv_peiSong = (TextView) findViewById(R.id.go_pay_peisong);
			cb_general = (CheckBox) findViewById(R.id.go_pay_check);
			cb_emsBox = (CheckBox) findViewById(R.id.go_pay_ems);
			tv_generalPrice = (TextView) findViewById(R.id.go_pay_general_price);
			tv_emsPrice = (TextView) findViewById(R.id.go_pay_ems_price);
			et_comment = (EditText) findViewById(R.id.go_pay_leave_message);

			RelativeLayout zhifubao = (RelativeLayout) findViewById(R.id.go_pay_zhifubao_relative);
			RelativeLayout zhifubaowangye = (RelativeLayout) findViewById(R.id.go_pay_zhifubao_wangyezhifu_relative);
			RelativeLayout yinlian = (RelativeLayout) findViewById(R.id.go_pay_zhifubao_yinlian_relative);
			RelativeLayout cafutong = (RelativeLayout) findViewById(R.id.go_pay_zhifubao_caifutong_relative);
			RelativeLayout generalRelative = (RelativeLayout) findViewById(R.id.go_pay_general_relative);
			RelativeLayout emsRelative = (RelativeLayout) findViewById(R.id.go_pay_payEMS_relative);

			cb_zhifubao = (CheckBox) findViewById(R.id.zhifubao_checkbox);
			cb_zhifubaowangye = (CheckBox) findViewById(R.id.zhufubaowangye_checkbox);
			cb_yinlian = (CheckBox) findViewById(R.id.yinlian_checkbox);
			cb_caifutong = (CheckBox) findViewById(R.id.caifutong_checkbox);

			// 默认选择银联支付
			cb_caifutong.setChecked(true);

			generalRelative.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!cb_general.isChecked()) {
						cb_general.setChecked(true);
						cb_emsBox.setChecked(false);
						try {
							expressNum = tv_generalPrice.getText().toString();
							tv_sumPrice.setText((goodsPrice
									+ Float.parseFloat(expressNum))  - Float
									.parseFloat(tickMoney) + "");
						} catch (Exception e) {
							e.printStackTrace();
						}
						postMethod = getResources().getString(
								R.string.ship_post);// 快递方式
					}
				}
			});
			emsRelative.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!cb_emsBox.isChecked()) {
						cb_general.setChecked(false);
						cb_emsBox.setChecked(true);
						try {
							expressNum = tv_emsPrice.getText().toString();
							tv_sumPrice.setText((goodsPrice
									+ Float.parseFloat(expressNum)) - Float
									.parseFloat(tickMoney) + "");
						} catch (Exception e) {
							e.printStackTrace();
						}
						postMethod = "EMS";
					}
				}
			});

			/* 隐藏支付宝财付通的支付方式 */
			zhifubao.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					cb_zhifubao.setChecked(true);
					cb_zhifubaowangye.setChecked(false);
					cb_yinlian.setChecked(false);
					cb_caifutong.setChecked(false);
				}
			});

			zhifubaowangye.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					cb_zhifubaowangye.setChecked(true);
					cb_zhifubao.setChecked(false);
					cb_yinlian.setChecked(false);
					cb_caifutong.setChecked(false);
				}
			});
			yinlian.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					cb_yinlian.setChecked(true);
					cb_zhifubao.setChecked(false);
					cb_zhifubaowangye.setChecked(false);
					cb_caifutong.setChecked(false);
				}
			});
			cafutong.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					cb_caifutong.setChecked(true);
					cb_zhifubao.setChecked(false);
					cb_zhifubaowangye.setChecked(false);
					cb_yinlian.setChecked(false);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(CstmPayActivity.this,
					getResources().getString(R.string.bad_network),
					Toast.LENGTH_SHORT).show();
		}

	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (!carts.isEmpty())
			carts.clear();
	}

	private void getVoucher() {
		if (null == carts || carts.isEmpty())
			return;
		try {
			voucher = Float.parseFloat(voucherString1);
//			Log.e(TAG, voucherString1 + ":voucher and is cartact"
//					+ isCartActivity);
		} catch (Exception e) {
			voucherString1 = "";
			voucher = 0.0f;
		}

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < carts.size(); i++) {
			Cart cart = carts.get(i);
			sb.append(cart.getUId());
			sb.append(",");
			sb.append(cart.getAmount());
			sb.append("n");
			sb.append(";");
		}

		goods = sb.toString();

		getFreeGoods();

	}

	/** 显示界面 **/
	private void show(final ArrayList<Cart> carts, boolean isHuanGou) {
		setContentView(R.layout.go_pay);

		scv_go_pay = (ScrollView) findViewById(R.id.go_pay_scrollView);
		setupShow();
		rlYouhuiquan.setOnClickListener(this);
		if(!isCanTicket) rlYouhuiquan.setVisibility(View.GONE);
		
		layout = (LinearLayout) findViewById(R.id.go_pay_relative2);

		rl_peisong = (RelativeLayout) findViewById(R.id.rl_peisong);
		rl_peisong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				go2Delivery();
			}
		});
		// reLayout = (RelativeLayout) findViewById(R.id.go_pay_relative);
		addressRelative = (RelativeLayout) findViewById(R.id.go_pay_relativelayout);

		// 地址增加监听事件
		addressRelative.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (null != showAddress) {
					Intent intent = new Intent(CstmPayActivity.this,
							AddressActivity.class);
					intent.putExtra("requestCode", Consts.AddressRequestCode);
					CstmPayActivity.this.startActivityForResult(intent,
							Consts.AddressRequestCode);
				}
			}
		});
		PayUtil pay = new PayUtil(CstmPayActivity.this, layout);
		goods = pay.loadView(carts, isHuanGou);

		tv_saveOrderBtn.setSelected(false);
		// 提交订单点击事件
		tv_saveOrderBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!isCanPay) return;
				
				if (!cb_yinlian.isChecked() && !cb_zhifubao.isChecked()
						&& !cb_zhifubaowangye.isChecked()
						&& !cb_caifutong.isChecked()) {
					Toast.makeText(CstmPayActivity.this,
							getResources().getString(R.string.select_pay_type),
							Toast.LENGTH_LONG).show();
					scv_go_pay.smoothScrollTo(0, 0);
					return;
				}

				if (cb_yinlian.isChecked()) {
					pay_type = "union";
					mode = 1;
				} else if (cb_caifutong.isChecked()) {
					mode = 0;
					pay_type = "tenpay";
				} else if (cb_zhifubao.isChecked()) {
					mode = 2;
					pay_type = "alipay";
				} else {
					mode = 3;
					pay_type = "alipay";
				}
				saveOrder();
			}
		});

	}

	String pay_type = "";
	int mode = 1;

	/** 清除购物车数据 **/
	private void cleanCart() {
		if ("Y".equals(isCartActivity)) {// ；来自购物车
			// 删除购物车的商品
			CartsDataManage cartsDataManage = new CartsDataManage();
			int length = carts.size();
			for (int i = 0; i < length; i++) {
				cartsDataManage.delCart(carts.get(i).getUId());
			}
		}
	}

	private void go2WebPay(String title, String payurl) {
		Intent webIntent = new Intent(this, WebPayActivity.class);
		webIntent.putExtra("title", title);
		webIntent.putExtra("payurl", payurl);
		startActivity(webIntent);
		finish();
	}

	/**
	 * 支付事件操作
	 * 
	 * @param mode
	 *            0
	 */
	private void go2Pay(int mode) {
		if (TextUtils.isEmpty(orderCode))
			return;

		// 跳转到支付页面
		Intent userpayintent = new Intent(CstmPayActivity.this,
				UnionActivity.class);
		Bundle bundle = new Bundle();

		bundle.putInt("mode", mode);
		bundle.putString("code", orderCode);
		bundle.putString("uid", userId);
		bundle.putString("resp_code", resp_code);
		bundle.putString("tn", tn);
		userpayintent.putExtras(bundle);
		startActivity(userpayintent);
		CstmPayActivity.this.finish();
	}

	/** 提交订单 **/
	private void saveOrder() {
		String encodeMethod = "UTF-8";

		String tmpPeisong = peiSongCenter;
		String tmpPostMethod = postMethod;
		String tmpComment = et_comment.getText().toString();
		try {
			tmpPeisong = URLEncoder.encode(peiSongCenter, encodeMethod);
			tmpPostMethod = URLEncoder.encode(postMethod, encodeMethod);
			tmpComment = URLEncoder.encode(tmpComment, encodeMethod);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		JNICallBack jniCallBack = new JNICallBack();
		String url = jniCallBack.HTTPURL;
		String params = jniCallBack.getHttp4SaveOrder(userId, tickId, recipientId,
				"", needJifen + "", expressNum, tmpPostMethod, tmpPeisong, goods,
				tmpComment, pay_type, token, "android" + VersonNameUtil.getVersionName(), ruleId);

		// Log.e("system.out", url+params);

//		RequestParams requestParams = new RequestParams();
//		requestParams.put(params);

		HttpClientUtil client = new HttpClientUtil(this);
		client.setIsShowLoading(true);
		client.getHttpResp(url, params, new IHttpResp() {

			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
					// 清除购物车数据
					cleanCart();

					ParseOrder parseOrder = new ParseOrder(CstmPayActivity.this);
//					Log.e("system.out", content);
					if (parseOrder.parseSaveOrder(content)) {
						orderCode = parseOrder.getOrderCode();
						tn = parseOrder.getTn();
						resp_code = parseOrder.getResp_code();

						if (TextUtils.isEmpty(orderCode))
							return;

						if (mode == 1) {
							go2Pay(mode);
						} else if (mode == 0) { // 财付通网页
							String payurl = "http://u.yidejia.com/index.php?m=ucenter&c=order&a=onlineWap&code="
									+ orderCode + "&type=tenpay";
							// go2Pay(mode);
							go2WebPay(getString(R.string.caifutong_pay), payurl);
						} else if (mode == 3) { // 支付宝网页
							String payurl = "http://u.yidejia.com/index.php?m=ucenter&c=order&a=onlineWap&code="
									+ orderCode + "&type=alipay";
							// go2Pay(mode);
							go2WebPay(
									getString(R.string.zhifubao_wangye_pay_list),
									payurl);
						} else if (mode == 2) {
							AlicPayUtil util = new AlicPayUtil(
									CstmPayActivity.this);
							util.getAlicPay(userId, token, orderCode, false);
						}
						
//						if(!TextUtils.isEmpty(ruleId)){
//							updateYRH(ruleId);
//						}
//						
					} else {
						String errResult = parseOrder.getErrResult();
						if (TextUtils.isEmpty(errResult))
							return;
						Toast.makeText(CstmPayActivity.this, errResult,
								Toast.LENGTH_SHORT).show();
					}
			}

//			@Override
//			public void onError() {
//				super.onError();
//				Toast.makeText(CstmPayActivity.this,
//						getString(R.string.bad_network), Toast.LENGTH_SHORT)
//						.show();
//			}

		});
	}
	/*
	private void updateYRH(String rule_id){
		String url = new JNICallBack().HTTPURL;
		String param = new JNICallBack().getHttp4UpdateYiRiHui(rule_id);
//		Log.e("system.out", url + "?" + param);
		
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		httpClientUtil.getHttpResp(url, param, new IHttpResp() {
			
			@Override
			public void success(String content) {
//				Log.e("system.out", content);
				
			}
		});
	}*/

	private String getAddressUrl(boolean isDefault) {
		String url = "";
		if (isDefault) {
			url = new JNICallBack().getHttp4GetAddress("customer_id%3D"
					+ userId + "+and+is_default%3D%27y%27", 0 + "", 1 + "", "",
					"", "");
		} else {
			url = new JNICallBack().getHttp4GetAddress("customer_id%3D"
					+ userId + "+and+valid_flag%3D%27y%27", 0 + "", 1 + "", "",
					"", "");
		}
		return url;
	}

	/**
	 * 获取默认地址
	 */
	private void getDefaultAddress() {
		String url = getAddressUrl(true);
		HttpClientUtil client = new HttpClientUtil(this);
		client.setIsShowLoading(true);
		client.getHttpResp(url, new IHttpResp() {

			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
//				if (statusCode == HttpStatus.SC_OK) {
					ParseAddressJson parseAddressJson = new ParseAddressJson();
					boolean isSuccess = parseAddressJson
							.parseAddressListJson(content);
					if (isSuccess) {
						ArrayList<ModelAddresses> addresses = parseAddressJson
								.getAddresses();
						if (null != addresses && 0 != addresses.size()) {
							showAddress = addresses.get(0);
							setAdd(showAddress);
							getCanFree();
						}
					} else {
						getFirstAddress();
					}
//				}
			}
//
//			@Override
//			public void onError(Throwable error, String content) {
//				super.onError(error, content);
//				// TODO Auto-generated method stub
//				Toast.makeText(CstmPayActivity.this,
//						getString(R.string.bad_network), Toast.LENGTH_SHORT)
//						.show();
//			}

		});
	}

	/**
	 * 获取收货地址的第一个地址
	 */
	private void getFirstAddress() {
		String url = getAddressUrl(false);
		HttpClientUtil client = new HttpClientUtil(this);
		client.getHttpResp(url, new IHttpResp() {

			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
//				if (statusCode == HttpStatus.SC_OK) {
					ParseAddressJson parseAddressJson = new ParseAddressJson();
					boolean isSuccess = parseAddressJson
							.parseAddressListJson(content);
					if (isSuccess) {
						ArrayList<ModelAddresses> addresses = parseAddressJson
								.getAddresses();
						if (null != addresses && 0 != addresses.size()) {
							showAddress = addresses.get(0);
							setAdd(addresses.get(0));
							getCanFree();
						}
					} else {
						// TODO 收货地址为空，需要跳转到新增地址栏
						ActivityIntentUtil.intentActivityForResult(
								CstmPayActivity.this,
								EditNewAddressActivity.class,
								Consts.AddressRequestCode);
					}
//				}
			}
//
//			@Override
//			public void onError(Throwable error, String content) {
//				super.onError(error, content);
//				// TODO Auto-generated method stub
//				Toast.makeText(CstmPayActivity.this,
//						getString(R.string.bad_network), Toast.LENGTH_SHORT)
//						.show();
//			}

		});
	}

	/**
	 * 默认方式下和非默认方式下获取配送中心的url
	 * 
	 * @param isDefault
	 *            是否默认方式
	 * @return
	 */
	private String getDeliveryUrl(boolean isDefault) {
		String url = "";
		if (isDefault) {
			url = new JNICallBack().getHttp4GetDistribute("id%3D" + preId, "0",
					"100", "", "", "%2A");
		} else {
			url = new JNICallBack().getHttp4GetDistribute("flag%3D%27y%27",
					"0", "100", "", "", "%2A");
		}
		return url;
	}

	/**
	 * 获取发货地点
	 */
	private void getDeliveryPoint(boolean isDefault) {
		// String url = new
		// JNICallBack().getHttp4GetDistribute("flag%3D%27y%27", "0", "20", "",
		// "", "%2A");
		String url = getDeliveryUrl(isDefault);
		HttpClientUtil client = new HttpClientUtil();
		client.getHttpResp(url, new IHttpResp() {

			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
//				if (statusCode == HttpStatus.SC_OK) {
					ParseExpressJson parseExpressJson = new ParseExpressJson();
					boolean isSuccess = parseExpressJson.parseDist(content);
					if (isSuccess) {
						distributions = parseExpressJson.getDistributions();
						if (null != distributions && distributions.size() != 0) {
							peiSongCenter = distributions.get(0).getDisName();
							tv_peiSong.setText(peiSongCenter);
							isCanPay = true;
							tv_saveOrderBtn.setSelected(true);
						} else {
							// TODO
							Toast.makeText(CstmPayActivity.this,
									getString(R.string.bad_network),
									Toast.LENGTH_LONG).show();
						}
					} else {
						// TODO 解析出错或服务器返回-1
						Toast.makeText(CstmPayActivity.this,
								getString(R.string.bad_network),
								Toast.LENGTH_LONG).show();
					}
//				} else {
//					// TODO 返回失败
//					Toast.makeText(CstmPayActivity.this,
//							getString(R.string.bad_network), Toast.LENGTH_LONG)
//							.show();
//				}
			}

			@Override
			public void onError() {
				super.onError();
				// TODO
				Toast.makeText(CstmPayActivity.this,
						getString(R.string.bad_network), Toast.LENGTH_LONG)
						.show();
			}

		});

	}

	/**
	 * 获取邮费和配送中心id的url
	 * 
	 * @param province
	 * @param isDefault
	 * @return
	 */
	private String getExpressUrl(String province, boolean isDefault) {
		String url = "";
		StringBuffer where = new StringBuffer();
		where.append("province%3D%27");

		String whereStr = province;
		try {
			whereStr = URLEncoder.encode(whereStr, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		where.append(whereStr);

		if (isDefault) {
			where.append("%27+and+is_default%3D%27y%27");
		} else {
			where.append("%27+and+pre_id%3D");
			where.append(preId);
		}

		url = new JNICallBack().getHttp4GetExpress(where.toString(), "0", "20",
				"", "", "%2A");
		return url;
	}

	/**
	 * 根据省份获取配送中心id和获取快递费用
	 * 
	 * @param province
	 *            省份
	 */
	private void getExpressData(String province, final boolean isDefault) {

		String url = getExpressUrl(province, isDefault);

		HttpClientUtil client = new HttpClientUtil();
		client.getHttpResp(url, new IHttpResp() {

			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
//				if (statusCode == HttpStatus.SC_OK) {
					ParseExpressJson parseExpressJson = new ParseExpressJson();
					boolean isSuccess = parseExpressJson.parseExpress(content);
					if (isSuccess) {
						expressArray = parseExpressJson.getExpresses();
						if (null != expressArray && 0 != expressArray.size()) {
							generalNum = expressArray.get(0).getExpress();
							emsNum = expressArray.get(0).getEms();
							showExpressNum();
							preId = expressArray.get(0).getPreId();
							if (isDefault) {
								getDeliveryPoint(isDefault);
							} else {
								if (!TextUtils.isEmpty(generalNum)
										&& !TextUtils.isEmpty(emsNum)) {
									//邮费不为空时，可购买
									isCanPay = true;
									tv_saveOrderBtn.setSelected(true);
								}
							}
						} else {
							// TODO
							Toast.makeText(CstmPayActivity.this,
									getString(R.string.bad_network),
									Toast.LENGTH_LONG).show();
						}
					} else {
						// TODO
//						Log.e("system.out", content);
						Toast.makeText(CstmPayActivity.this,
								getString(R.string.bad_network),
								Toast.LENGTH_LONG).show();
					}
//				} else {
//					// TODO 返回失败
//					Toast.makeText(CstmPayActivity.this,
//							getString(R.string.bad_network), Toast.LENGTH_LONG)
//							.show();
//				}
			}

			@Override
			public void onError() {
				super.onError();
				// TODO
				Toast.makeText(CstmPayActivity.this,
						getString(R.string.bad_network), Toast.LENGTH_LONG)
						.show();
			}

		});
	}

	/**
	 * 获取是否免邮
	 */
	@SuppressLint("SimpleDateFormat")
	private void getCanFree() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());
		String dateStr = format.format(curDate);
		String where = "+startDate+%3C%3D+%27" + dateStr + "%27";
		String url = new JNICallBack().getHttp4GetFree(where, "0", "20", "",
				"", "%2A");
		HttpClientUtil client = new HttpClientUtil();
		client.getHttpResp(url, new IHttpResp() {

			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
//				if (statusCode == HttpStatus.SC_OK) {
					ParseExpressJson parseExpressJson = new ParseExpressJson();
					boolean isSuccess = parseExpressJson.parseFree(content);
					if (isSuccess) {
						freePosts = parseExpressJson.getFreePosts();
						if (null != freePosts && 0 != freePosts.size()) {
							canFreePost();
							getExpressData(province, true);
						} else {
							// TODO 提示用户获取免邮信息出错？
							Toast.makeText(CstmPayActivity.this,
									getString(R.string.bad_network),
									Toast.LENGTH_LONG).show();
						}
					} else {
						// TODO 返回-1
						Toast.makeText(CstmPayActivity.this,
								getString(R.string.bad_network),
								Toast.LENGTH_LONG).show();
					}
//				} else {
//					// TODO 返回失败
//					Toast.makeText(CstmPayActivity.this,
//							getString(R.string.bad_network), Toast.LENGTH_LONG)
//							.show();
//				}
			}

			@Override
			public void onError() {
				super.onError();
				// TODO 返回失败
				Toast.makeText(CstmPayActivity.this,
						getString(R.string.bad_network), Toast.LENGTH_LONG)
						.show();
			}

		});
	}

	/**
	 * 判断是否免邮
	 */
	private void canFreePost() {
		float fP = Float.MAX_VALUE;
		try {
			if (null != freePosts && freePosts.size() != 0)
				fP = Float.parseFloat(freePosts.get(0).getMax());
			if ((goodsPrice - Float
					.parseFloat(tickMoney)) >= (fP - 0.001)) {// 大于等于免邮费用
				isFree = true;
				expressNum = "0";
			} else {
				isFree = false;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取积分
	 */
	private void getCredit() {
		JNICallBack jniCallBack = new JNICallBack();
		// String url = jniCallBack.HTTPURL +
		// jniCallBack.getHttp4GetVoucher(userId, token);
		//
		// Log.e("system.out", url);
		// Toast.makeText(this, url, Toast.LENGTH_LONG).show();
		HttpClientUtil client = new HttpClientUtil();
		RequestParams params = new RequestParams();
		params.put(jniCallBack.getHttp4GetVoucher(userId, token));

		client.getHttpResp(jniCallBack.HTTPURL, jniCallBack.getHttp4GetVoucher(userId, token),
				new IHttpResp() {// );

					// client.get(url, new AsyncHttpResponse(){

					@Override
					public void onSuccess(String content) {
						super.onSuccess(content);
//						if (statusCode == HttpStatus.SC_OK) {
							ParseCredit parseCredit = new ParseCredit();
							boolean isSuccess = parseCredit
									.parseCredit(content);
							if (isSuccess) {
								voucherString1 = parseCredit.getCreditNum();
								if (!TextUtils.isEmpty(voucherString1)
										&& !"null".equals(voucherString1)) {
									getVoucher();
								} else {
									// TODO
									Toast.makeText(CstmPayActivity.this,
											getString(R.string.bad_network),
											Toast.LENGTH_LONG).show();
								}
							} else {
								// TODO
								Toast.makeText(CstmPayActivity.this,
										getString(R.string.bad_network),
										Toast.LENGTH_LONG).show();
							}
//						} else {
//							// TODO
//							Toast.makeText(CstmPayActivity.this,
//									getString(R.string.bad_network),
//									Toast.LENGTH_LONG).show();
//						}
					}

					@Override
					public void onError() {
						// TODO Auto-generated method stub
						super.onError();
						Toast.makeText(CstmPayActivity.this,
								getString(R.string.bad_network),
								Toast.LENGTH_LONG).show();
					}

				});
	}

	/** 获取免费送和积分换购的商品 **/
	private void getFreeGoods() {
		JNICallBack jniCallBack = new JNICallBack();
		String url = jniCallBack.HTTPURL;
		String param = jniCallBack.getHttp4GetVerify(goods, userId);

		HttpClientUtil client = new HttpClientUtil();

//		RequestParams requestParams = new RequestParams();
//		requestParams.put(param);

//		Log.e("system.out", url + "?" + param);
		// client.get(url, new AsyncHttpResponse(){
		client.getHttpResp(url, param, new IHttpResp() {
			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
				
				
//				if (statusCode == HttpStatus.SC_OK) {
					ParseCredit parseCredit = new ParseCredit();
					boolean isSuccess = parseCredit.parseVerify(content);
					if (isSuccess) {
						arrayListFree = parseCredit.getFreeGoods();
						arrayListExchange = parseCredit.getScoreGoods();
						boolean isFreeEmpty = ((null == arrayListFree) || arrayListFree
								.isEmpty());
						boolean isEchageEmpty = ((null == arrayListExchange) || arrayListExchange
								.isEmpty());
						if (isEchageEmpty & isFreeEmpty)
							return;

						if (!isFreeEmpty || (!isEchageEmpty && voucher > 0.001)) {
							dialog.show();
						}
					} else {
						// TODO
						Toast.makeText(CstmPayActivity.this,
								getString(R.string.bad_network),
								Toast.LENGTH_LONG).show();
					}
//				} else {
//					// TODO
//					Toast.makeText(CstmPayActivity.this,
//							getString(R.string.bad_network), Toast.LENGTH_LONG)
//							.show();
//				}
			}

			@Override
			public void onError() {
				// TODO Auto-generated method stub
				super.onError();
				Toast.makeText(CstmPayActivity.this,
						getString(R.string.bad_network), Toast.LENGTH_LONG)
						.show();
			}

		});
	}

	/**
	 * 设置地址，快递和配送中心
	 * 
	 * @param addresses
	 */
	private void setAdd(ModelAddresses addresses) {
		// Log.i(TAG, "show sum:"+sum);
		tv_userName.setText(addresses.getName());
		if (!TextUtils.isEmpty(addresses.getHandset())) {
			tv_phoneName.setText(addresses.getHandset());
		} else {
			tv_phoneName.setText(addresses.getPhone());
		}

		province = addresses.getProvice();

		StringBuffer sb = new StringBuffer();
		sb.append(province);
		sb.append(addresses.getCity());
		sb.append(addresses.getArea());
		sb.append(addresses.getAddress());
		tv_address.setText(sb.toString());
		recipientId = addresses.getAddressId();// 地址id
	}

	private ArrayList<Express> expressArray;

	private void showExpressNum() {
		if (isFree) {
			tv_generalPrice.setText("0");
			tv_emsPrice.setText("0");
			expressNum = "0";
			tv_sumPrice.setText((goodsPrice + Float.parseFloat(expressNum)) - Float
					.parseFloat(tickMoney) + "");
		} else {
			tv_generalPrice.setText(generalNum);
			tv_emsPrice.setText(emsNum);
			expressNum = (cb_general.isChecked() ? tv_generalPrice.getText()
					.toString() : tv_emsPrice.getText().toString());
			tv_sumPrice.setText((goodsPrice + Float.parseFloat(expressNum)) - Float
					.parseFloat(tickMoney) + "");
		}
	}

	/** 跳转到修改配送中心页面 **/
	private void go2Delivery() {
		Intent intent = new Intent(CstmPayActivity.this, DeliveryActivity.class);
		intent.putExtra("preId", preId);
		CstmPayActivity.this.startActivityForResult(intent,
				Consts.DELIVERY_REQUEST);
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
//			Log.i("info", addresses1.getAddress() + "str");
			if (addresses1 != null) {
				setAdd(addresses1);
				if(null != tv_saveOrderBtn) {
					isCanPay = false;
					tv_saveOrderBtn.setSelected(false);
				}
				// 获取邮费
				getExpressData(addresses1.getProvice(), true);
			}
		} else if (requestCode == Consts.CstmPayActivity_Request
				&& resultCode == Consts.CstmPayActivity_Response) {
			carts.clear();
			carts = (ArrayList<Cart>) data.getSerializableExtra("carts");

			voucher = data.getFloatExtra("voucher", -1);
			needJifen = data.getFloatExtra("needJifen", -1);

			layout.removeAllViews();
			PayUtil pay = new PayUtil(CstmPayActivity.this, layout);
			goods = pay.loadView(carts, false);
		} else if (Consts.DELIVERY_REQUEST == requestCode
				&& Consts.DELIVERY_RESULT == resultCode) {
			Express delivery = (Express) data.getExtras().getSerializable(
					"delivery");
			tv_peiSong.setText(delivery.getDisName());
			preId = delivery.getPreId();
			if (isFree)
				return;
			if(!TextUtils.isEmpty(province)) {
				if(null != tv_saveOrderBtn) {
					isCanPay = false;
					tv_saveOrderBtn.setSelected(false);
				}
				getExpressData(province, false);
			}
		} else if (Consts.YOUHUIQUAN_REQUEST == requestCode
				&& Consts.YOUHUIQUAN_RESPONSE == resultCode) {//使用优惠权的返回值
			tickMoney = data.getExtras().getString("youhuiquan");
			tickId = data.getExtras().getString("tickId");
			String tickName = data.getExtras().getString("tickName");
//			Log.e("system.out", "price:" + youhuiData + ":id:" + tickId + ":name:" + tickName);
			if(TextUtils.isEmpty(tickId)) tickId = "0";
			if (null != tickMoney && null != tvTicketName) {
				tvTicketName.setText("(" + tickName + ")");
				try {
					float priceF = (goodsPrice + Float.parseFloat(expressNum) - Float
							.parseFloat(tickMoney));
					if (priceF < 0.001){
						isCanPay = false;
						return;
					}
					
					float fP = Float.MAX_VALUE;
					try {
						if (null != freePosts && freePosts.size() != 0)
							fP = Float.parseFloat(freePosts.get(0).getMax());
						if (priceF >= (fP - 0.001)) {// 大于等于免邮费用
							isFree = true;
							expressNum = "0";
							tv_sumPrice.setText(priceF + "");
						} else {
							isFree = false;
//							getExpressData(province, true);
							showExpressNum();
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
					
				} catch (NumberFormatException e) {
					e.printStackTrace();
					isCanPay = false;
				}
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// StatService.onResume(this);
		StatService.onPageStart(this, "确认订单页面");
	}

	@Override
	protected void onPause() {
		super.onPause();
		// StatService.onPause(this);
		StatService.onPageEnd(this, "确认订单页面");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.go_shopping_use_evalution:
			Intent intent = new Intent(CstmPayActivity.this,
					YouhuiActivity.class);
			intent.putExtra("totalPrice", goodsPrice);
			startActivityForResult(intent, Consts.YOUHUIQUAN_REQUEST);
			break;
		}
	}

}