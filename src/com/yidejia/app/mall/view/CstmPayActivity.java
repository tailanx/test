package com.yidejia.app.mall.view;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpStatus;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
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
import com.yidejia.app.mall.pay.AlixId;
import com.yidejia.app.mall.pay.BaseHelper;
import com.yidejia.app.mall.pay.DeliveryActivity;
import com.yidejia.app.mall.pay.MobileSecurePayer;
import com.yidejia.app.mall.pay.PartnerConfig;
import com.yidejia.app.mall.pay.ResultChecker;
import com.yidejia.app.mall.pay.Rsa;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.PayUtil;

public class CstmPayActivity extends BaseActivity {
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
	
	private RelativeLayout rl_peisong;	//选择配送中心的layout
	
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
	private float jifen = 0;
	public static String voucherString1;// 积分
	private ModelAddresses showAddress;
	private ArrayList<FreePost> freePosts;	//免邮条件列表
	private ArrayList<Express> distributions;	//配送中心列表
	private String preId;	//配送中心的id
	private String province;	//省份
	
	private String userId;
	private String token;
	private ArrayList<Cart> carts;// 购物车的数据，非换购的数据
//	private RelativeLayout reLayout;
	private String isCartActivity;
	private String sum;
	private String contentType;

	private float goodsPrice;
	private float voucher;
	private final String TAG = getClass().getName();
	
	private ProgressDialog mProgress = null;

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
			
			setActionbarConfig();
			setTitle(R.string.comfirm_order);
			
			Intent intent = getIntent();
			Bundle bundle = intent.getExtras();
			goodsPrice = bundle.getFloat("price");
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
				contentType = "application/x-www-form-urlencoded;charset=UTF-8";
				
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
									bundle.putString("price", sum + "");
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
							tv_sumPrice.setText(goodsPrice
									+ Float.parseFloat(expressNum) + "");
						} catch (Exception e) {
							e.printStackTrace();
						}
						postMethod = getResources().getString(R.string.ship_post);// 快递方式
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
							tv_sumPrice.setText(goodsPrice
									+ Float.parseFloat(expressNum) + "");
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
	protected void onDestroy() {
		super.onDestroy();
	}
	
	private void getVoucher() {
		if (null == carts || carts.isEmpty())
			return;
		try {
			voucher = Float.parseFloat(voucherString1);
			Log.e(TAG, voucherString1 + ":voucher and is cartact" + isCartActivity);
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
	
	private void show(final ArrayList<Cart> carts, boolean isHuanGou) {
		setContentView(R.layout.go_pay);
			
		scv_go_pay = (ScrollView) findViewById(R.id.go_pay_scrollView);
		setupShow();

		layout = (LinearLayout) findViewById(R.id.go_pay_relative2);
		
		rl_peisong = (RelativeLayout) findViewById(R.id.rl_peisong);
		rl_peisong.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				go2Delivery();
			}
		});
//		reLayout = (RelativeLayout) findViewById(R.id.go_pay_relative);
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
		
		// 提交订单
		tv_saveOrderBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!cb_yinlian.isChecked()
						&& !cb_zhifubao.isChecked()
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
//					Toast.makeText(CstmPayActivity.this, "亲，暂时只支持银联的支付方式哦！",
//							Toast.LENGTH_LONG).show();
//					return;
				} else if (cb_zhifubao.isChecked()) {
					mode = 2;
					pay_type = "alipay";
//					Toast.makeText(CstmPayActivity.this, "亲，暂时只支持银联的支付方式哦！",
//							Toast.LENGTH_LONG).show();
//					return;
					performPay();
					return;
				} else {
					mode = 3;
//					pay_type = "aliwappay";
					pay_type = "alipay";
//					Toast.makeText(CstmPayActivity.this, "亲，暂时只支持银联的支付方式哦！",
//							Toast.LENGTH_LONG).show();
//					return;
				}
				saveOrder();
			}
		});
	
		// 添加地址、快递费用、配送中心
		getDefaultAddress();
	}
	
	String pay_type = "";
	int mode = 1;
	
	/**
	 * 支付事件操作
	 * @param mode 0
	 */
	private void go2Pay(int mode){
		if (TextUtils.isEmpty(orderCode))
			return;
		if (isCartActivity.equals("Y")) {// ；来自购物车
			Log.e("NoProduceFragment", "DELETE_CART");
			// 删除购物车的商品
			CartsDataManage cartsDataManage = new CartsDataManage();
			int length = carts.size();
			for (int i = 0; i < length; i++) {
				cartsDataManage.delCart(carts.get(i).getUId());
			}
		}
		// 跳转到支付页面
		Intent userpayintent = new Intent(CstmPayActivity.this,
				UserPayActivity.class);
		Bundle bundle = new Bundle();
		
		bundle.putInt("mode", mode);
		bundle.putString("code", orderCode);
		if (mode == 1) {
			bundle.putString("uid", userId);
			bundle.putString("resp_code", resp_code);
			bundle.putString("tn", tn);
		} else if(mode == 0) {
			bundle.putString("payurl", "http://u.yidejia.com/index.php?m=ucenter&c=order&a=onlineWap&code="+orderCode+"&type=tenpay");
		} else if(mode == 3) {
			bundle.putString("payurl", "http://u.yidejia.com/index.php?m=ucenter&c=order&a=onlineWap&code="+orderCode+"&type=alipay");
		}
		userpayintent.putExtras(bundle);
		startActivity(userpayintent);
		CstmPayActivity.this.finish();
	}
	
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
		String params = jniCallBack.getHttp4SaveOrder(userId, "0", recipientId,
				"", jifen + "", expressNum, tmpPostMethod, tmpPeisong, goods,
				tmpComment, pay_type, token);
		
		Log.e("system.out", url+params);
		
		RequestParams requestParams = new RequestParams();
		requestParams.put(params);
		
		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.post(url, contentType, requestParams, new AsyncHttpResponse(){

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				if(statusCode == HttpStatus.SC_OK) {
					ParseOrder parseOrder = new ParseOrder(CstmPayActivity.this);
					Log.e("system.out", content);
					if(parseOrder.parseSaveOrder(content)){
						orderCode = parseOrder.getOrderCode();
						tn = parseOrder.getTn();
						resp_code = parseOrder.getResp_code();
						if(mode == 1) {
							go2Pay(mode);
						} else if(mode == 0){	//财付通网页
							//http://u.yidejia.com/index.php?m=ucenter&c=order&a=onlineWap&code=d0200114011626&type=tenpay
							go2Pay(mode);
						} else if(mode == 3) {	//支付宝网页
							//http://u.yidejia.com/index.php?m=ucenter&c=order&a=onlineWap&code=d0200114011626&type=alipay
							go2Pay(mode);
						} else if(mode == 2) {
							performPay();
						}
					}
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onError(error, content);
				Toast.makeText(CstmPayActivity.this, getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	/**alipay支付宝客户端支付**/
	private void performPay() {
		//
		// check to see if the MobileSecurePay is already installed.

		// check some info.
		// 检测配置信息
//		if (!checkInfo()) {
//			BaseHelper
//					.showDialog(
//							CstmPayActivity.this,
//							"提示",
//							"缺少partner或者seller，请在PartnerConfig.java中增加。",
//							R.drawable.infoicon);
//			return;
//		}

		// start pay for this order.
		// 根据订单信息开始进行支付
		try {
			// prepare the order info.
			// 准备订单信息
			/*String orderInfo = getOrderInfo();
			// 这里根据签名方式对订单信息进行签名
			String signType = getSignType();
			String strsign = sign(signType, orderInfo);
			Log.v("sign:", strsign);
			// 对签名进行编码
			strsign = URLEncoder.encode(strsign, "UTF-8");
			// 组装好参数
			String info = orderInfo + "&sign=" + "\"" + strsign + "\"" + "&"
					+ getSignType();*/
			String info = "partner=\"2088011831506812\"&seller_id=\"pay@yidejia.com\"&out_trade_no=\"d0200114011751\"&subject=\"title..\"&body=\"description..\"&total_fee=\"0.01\"&notify_url=\"http://www.taobao.com\"&service=\"mobile.securitypay.pay\"&_input_charset=\"utf-8\"&payment_type=\"1\"&sign=\"kmSIemgqtWgR4Z4YpKx8z%2FWcIsu8OUrDxqEGQOHxsve1wLHDr08N5RtzL9j60aUh0eQmd0kl6JjoNpVbAxwS%2Bm7iJw%2B4D%2BbdNrpmnkgghiI9Lqj2zUXURFbJx0sTY9tZ7mWVXr%2FBV0uR24%2FlNlm6fPb5vcB7bi4L3Ohp46AyJ5k%3D\"&sign_type=\"RSA\"";
			Log.v("orderInfo:", info);
			// start the pay.
			// 调用pay方法进行支付
			MobileSecurePayer msp = new MobileSecurePayer();
			boolean bRet = msp.pay(info, mHandler, AlixId.RQF_PAY, this);

			if (bRet) {
				// show the progress bar to indicate that we have started
				// paying.
				// 显示“正在支付”进度条
				closeProgress();
				mProgress = BaseHelper.showProgress(this, null, "正在支付", false,
						true);
			} else
				;
		} catch (Exception ex) {
			Toast.makeText(CstmPayActivity.this, R.string.remote_call_failed,
					Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * check some info.the partner,seller etc. 检测配置信息
	 * partnerid商户id，seller收款帐号不能为空
	 * 
	 * @return
	 */
	private boolean checkInfo() {
		String partner = PartnerConfig.PARTNER;
		String seller = PartnerConfig.SELLER;
		if (partner == null || partner.length() <= 0 || seller == null
				|| seller.length() <= 0)
			return false;

		return true;
	}
	/**
	 * get the selected order info for pay. 获取商品订单信息
	 * 
	 * @return
	 */
	String getOrderInfo() {
		String strOrderInfo = "partner=" + "\"" + PartnerConfig.PARTNER + "\"";
		strOrderInfo += "&";
		strOrderInfo += "seller=" + "\"" + PartnerConfig.SELLER + "\"";
		strOrderInfo += "&";
		strOrderInfo += "out_trade_no=" + "\"" + orderCode + "\"";
		strOrderInfo += "&";
		strOrderInfo += "subject=" + "\"" + "伊的家商城订单"
				+ "\"";
		strOrderInfo += "&";
		strOrderInfo += "body=" + "\"" + "测试支付宝支付订单" + "\"";
		strOrderInfo += "&";
		strOrderInfo += "total_fee=" + "\""
				+ "0.1" + "\"";
		strOrderInfo += "&";
		strOrderInfo += "notify_url=" + "\""
				+ "http://notify.java.jpxx.org/index.jsp" + "\"";

		return strOrderInfo;
	}
	
	//
	// close the progress bar
	// 关闭进度框
	void closeProgress() {
		try {
			if (mProgress != null) {
				mProgress.dismiss();
				mProgress = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 * @return
	 */
	String getSignType() {
		String getSignType = "sign_type=" + "\"" + "RSA" + "\"";
		return getSignType;
	}
	
	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param signType
	 *            签名方式
	 * @param content
	 *            待签名订单信息
	 * @return
	 */
	String sign(String signType, String content) {
		return Rsa.sign(content, PartnerConfig.RSA_PRIVATE);
	}
	
	// 这里接收支付结果，支付宝手机端同步通知
		private Handler mHandler = new Handler() {
			public void handleMessage(Message msg) {
				try {
					String ret = (String) msg.obj;

					Log.e(TAG, ret); // strRet范例：resultStatus={9000};memo={};result={partner="2088201564809153"&seller="2088201564809153"&out_trade_no="050917083121576"&subject="123456"&body="2010新款NIKE 耐克902第三代板鞋 耐克男女鞋 386201 白红"&total_fee="0.01"&notify_url="http://notify.java.jpxx.org/index.jsp"&success="true"&sign_type="RSA"&sign="d9pdkfy75G997NiPS1yZoYNCmtRbdOP0usZIMmKCCMVqbSG1P44ohvqMYRztrB6ErgEecIiPj9UldV5nSy9CrBVjV54rBGoT6VSUF/ufjJeCSuL510JwaRpHtRPeURS1LXnSrbwtdkDOktXubQKnIMg2W0PreT1mRXDSaeEECzc="}
					switch (msg.what) {
					case AlixId.RQF_PAY: {
						//
						closeProgress();

						BaseHelper.log(TAG, ret);

						// 处理交易结果
						try {
							// 获取交易状态码，具体状态代码请参看文档
							String tradeStatus = "resultStatus={";
							int imemoStart = ret.indexOf("resultStatus=");
							imemoStart += tradeStatus.length();
							int imemoEnd = ret.indexOf("};memo=");
							tradeStatus = ret.substring(imemoStart, imemoEnd);

							// 先验签通知
							ResultChecker resultChecker = new ResultChecker(ret);
							int retVal = resultChecker.checkSign();
							// 验签失败
							if (retVal == ResultChecker.RESULT_CHECK_SIGN_FAILED) {
								BaseHelper.showDialog(
										CstmPayActivity.this,
										"提示",
										getResources().getString(
												R.string.check_sign_failed),
										android.R.drawable.ic_dialog_alert);
							} else {// 验签成功。验签成功后再判断交易状态码
								if (tradeStatus.equals("9000"))// 判断交易状态码，只有9000表示交易成功
									BaseHelper.showDialog(CstmPayActivity.this, "提示",
											"支付成功。交易状态码：" + tradeStatus,
											R.drawable.ic_launcher);
								else
									BaseHelper.showDialog(CstmPayActivity.this, "提示",
											"支付失败。交易状态码:" + tradeStatus,
											R.drawable.ic_launcher);
							}

						} catch (Exception e) {
							e.printStackTrace();
//							BaseHelper.showDialog(CstmPayActivity.this, "提示", ret,
//									R.drawable.infoicon);
						}
					}
						break;
					}

					super.handleMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
	
	private String getAddressUrl(boolean isDefault) {
		String url = "";
		if (isDefault) {
			url = new JNICallBack().getHttp4GetAddress("customer_id%3D"
					+ userId
					+ "+and+is_default%3D%27y%27",
					0 + "", 1 + "", "", "", "");
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
		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.get(url, new AsyncHttpResponse(){

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				if(statusCode == HttpStatus.SC_OK){
					ParseAddressJson parseAddressJson = new ParseAddressJson();
					boolean isSuccess = parseAddressJson.parseAddressListJson(content);
					if (isSuccess) {
						ArrayList<ModelAddresses> addresses = parseAddressJson
								.getAddresses();
						if(null != addresses && 0 != addresses.size()) {
							showAddress = addresses.get(0);
							setAdd(showAddress);
							getCanFree();
						}
					} else {
						getFirstAddress();
					}
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				super.onError(error, content);
				// TODO Auto-generated method stub
				Toast.makeText(CstmPayActivity.this, getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	/**
	 * 获取收货地址的第一个地址
	 */
	private void getFirstAddress() {
		String url = getAddressUrl(false);
		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.get(url, new AsyncHttpResponse(){

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				if(statusCode == HttpStatus.SC_OK){
					ParseAddressJson parseAddressJson = new ParseAddressJson();
					boolean isSuccess = parseAddressJson.parseAddressListJson(content);
					if (isSuccess) {
						ArrayList<ModelAddresses> addresses = parseAddressJson
								.getAddresses();
						if(null != addresses && 0 != addresses.size()) {
							showAddress = addresses.get(0);
							setAdd(addresses.get(0));
							getCanFree();
						}
					} else {
						//TODO 收货地址为空，需要跳转到新增地址栏
					}
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				super.onError(error, content);
				// TODO Auto-generated method stub
				Toast.makeText(CstmPayActivity.this, getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	/**
	 * 默认方式下和非默认方式下获取配送中心的url
	 * @param isDefault 是否默认方式
	 * @return
	 */
	private String getDeliveryUrl(boolean isDefault) {
		String url = "";
		if(isDefault) {
			url = new JNICallBack().getHttp4GetDistribute("id%3D" + preId, "0", "100", "", "", "%2A");
		} else {
			url = new JNICallBack().getHttp4GetDistribute("flag%3D%27y%27", "0", "100", "", "", "%2A");
		}
		return url;
	}
	
	/**
	 * 获取发货地点
	 */
	private void getDeliveryPoint(boolean isDefault){
//		String url = new JNICallBack().getHttp4GetDistribute("flag%3D%27y%27", "0", "20", "", "", "%2A");
		String url = getDeliveryUrl(isDefault);
		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.get(url, new AsyncHttpResponse(){

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				if(statusCode == HttpStatus.SC_OK){
					ParseExpressJson parseExpressJson = new ParseExpressJson();
					boolean isSuccess = parseExpressJson.parseDist(content);
					if (isSuccess) {
						distributions = parseExpressJson.getDistributions();
						if (null != distributions && distributions.size() != 0) {
							peiSongCenter = distributions.get(0).getDisName();
							tv_peiSong.setText(peiSongCenter);
						} else {
							// TODO 
							Toast.makeText(CstmPayActivity.this, getString(R.string.bad_network), Toast.LENGTH_LONG).show();
						}
					} else {
						//TODO 解析出错或服务器返回-1
						Toast.makeText(CstmPayActivity.this, getString(R.string.bad_network), Toast.LENGTH_LONG).show();
					}
				} else {
					//TODO 返回失败
					Toast.makeText(CstmPayActivity.this, getString(R.string.bad_network), Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				super.onError(error, content);
				// TODO 
				Toast.makeText(CstmPayActivity.this, getString(R.string.bad_network), Toast.LENGTH_LONG).show();
			}
			
		});
		
	}
	
	/**
	 * 获取邮费和配送中心id的url
	 * @param province
	 * @param isDefault
	 * @return
	 */
	private String getExpressUrl(String province, boolean isDefault){
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
		
		if(isDefault) {
			 where.append("%27+and+is_default%3D%27y%27");
		} else {
			where.append("%27+and+pre_id%3D");
			where.append(preId);
		}
		
		url = new JNICallBack().getHttp4GetExpress(
				where.toString(), "0", "20", "", "", "%2A");
		return url;
	}
	
	/**
	 * 根据省份获取配送中心id和获取快递费用
	 * @param province 省份
	 */
	private void getExpressData(String province, final boolean isDefault) {
		
		String url = getExpressUrl(province, isDefault);
		
		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.get(url, new AsyncHttpResponse(){

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				if(statusCode == HttpStatus.SC_OK){
					ParseExpressJson parseExpressJson = new ParseExpressJson();
					boolean isSuccess = parseExpressJson.parseExpress(content);
					if(isSuccess) {
						expressArray = parseExpressJson.getExpresses();
						if(null != expressArray && 0 != expressArray.size()) {
							showExpressNum(expressArray.get(0).getExpress(), expressArray.get(0).getEms());
							preId = expressArray.get(0).getPreId();
							if(isDefault){
								getDeliveryPoint(isDefault);
							}
						}  else {
							// TODO 
							Toast.makeText(CstmPayActivity.this, getString(R.string.bad_network), Toast.LENGTH_LONG).show();
						}
					} else {
						// TODO 
						Log.e("system.out", content);
						Toast.makeText(CstmPayActivity.this, getString(R.string.bad_network), Toast.LENGTH_LONG).show();
					}
				} else {
					//TODO 返回失败
					Toast.makeText(CstmPayActivity.this, getString(R.string.bad_network), Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				super.onError(error, content);
				// TODO 
				Toast.makeText(CstmPayActivity.this, getString(R.string.bad_network), Toast.LENGTH_LONG).show();
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
		String where = "+startDate+%3C%3D+%27"+dateStr+"%27";
		String url = new JNICallBack().getHttp4GetFree(where, "0", "20", "", "", "%2A");
		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.get(url, new AsyncHttpResponse(){

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				if (statusCode == HttpStatus.SC_OK) {
					ParseExpressJson parseExpressJson = new ParseExpressJson();
					boolean isSuccess = parseExpressJson.parseFree(content);
					if (isSuccess) {
						freePosts = parseExpressJson.getFreePosts();
						if (null != freePosts && 0 != freePosts.size()) {
							canFreePost();
							getExpressData(province, true);
						} else {
							// TODO 提示用户获取免邮信息出错？
							Toast.makeText(CstmPayActivity.this, getString(R.string.bad_network), Toast.LENGTH_LONG).show();
						}
					} else {
						// TODO 返回-1
						Toast.makeText(CstmPayActivity.this, getString(R.string.bad_network), Toast.LENGTH_LONG).show();
					}
				} else {
					// TODO 返回失败
					Toast.makeText(CstmPayActivity.this, getString(R.string.bad_network), Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				super.onError(error, content);
				// TODO 返回失败
				Toast.makeText(CstmPayActivity.this, getString(R.string.bad_network), Toast.LENGTH_LONG).show();
			}
			
		});
	}
	
	/**
	 * 判断是否免邮
	 */
	private void canFreePost(){
		float fP = Float.MAX_VALUE;
		try {
			if (null != freePosts && freePosts.size() != 0)
				fP = Float.parseFloat(freePosts.get(0).getMax());
			if (goodsPrice >= (fP - 0.001)) {// 大于等于免邮费用
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
//		String url = jniCallBack.HTTPURL + jniCallBack.getHttp4GetVoucher(userId, token);
//		
//		Log.e("system.out", url);
//		Toast.makeText(this, url, Toast.LENGTH_LONG).show();
		AsyncOkHttpClient client = new AsyncOkHttpClient();
		RequestParams params = new RequestParams();
		params.put(jniCallBack.getHttp4GetVoucher(userId, token));

		client.post(jniCallBack.HTTPURL, contentType, params, new AsyncHttpResponse(){//);
		
//		client.get(url, new AsyncHttpResponse(){

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				if (statusCode == HttpStatus.SC_OK) {
					ParseCredit parseCredit = new ParseCredit();
					boolean isSuccess = parseCredit.parseCredit(content);
					if(isSuccess) {
						voucherString1 = parseCredit.getCreditNum();
						if(!TextUtils.isEmpty(voucherString1) && !"null".equals(voucherString1)) {
							getVoucher();
						} else {
							// TODO
							Toast.makeText(CstmPayActivity.this, getString(R.string.bad_network), Toast.LENGTH_LONG).show();
						}
					} else {
						//TODO
						Toast.makeText(CstmPayActivity.this, getString(R.string.bad_network), Toast.LENGTH_LONG).show();
					}
				} else{
					//TODO
					Toast.makeText(CstmPayActivity.this, getString(R.string.bad_network), Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onError(error, content);
				Toast.makeText(CstmPayActivity.this, getString(R.string.bad_network), Toast.LENGTH_LONG).show();
			}
			
		});
	}
	
	/**获取免费送和积分换购的商品**/
	private void getFreeGoods() {
		JNICallBack jniCallBack = new JNICallBack();
		String url = jniCallBack.HTTPURL;
		String param = jniCallBack.getHttp4GetVerify(goods, userId);
		
		AsyncOkHttpClient client = new AsyncOkHttpClient();
		
		RequestParams requestParams = new RequestParams();
		requestParams.put(param);
		
//		client.get(url, new AsyncHttpResponse(){
		client.post(url, contentType, requestParams, new AsyncHttpResponse(){
			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				if(statusCode == HttpStatus.SC_OK) {
					ParseCredit parseCredit = new ParseCredit();
					boolean isSuccess = parseCredit.parseVerify(content);
					if(isSuccess) {
						arrayListFree = parseCredit.getFreeGoods();
						arrayListExchange = parseCredit.getScoreGoods();
						boolean isFreeEmpty = ((null == arrayListFree) || arrayListFree.isEmpty());
						boolean isEchageEmpty = ((null == arrayListExchange) || arrayListExchange.isEmpty());
						if(isEchageEmpty & isFreeEmpty) return;
						
						if(!isFreeEmpty || (!isEchageEmpty && voucher > 0.001)) {
							dialog.show();
						}
					} else {
						// TODO
						Toast.makeText(CstmPayActivity.this, getString(R.string.bad_network), Toast.LENGTH_LONG).show();
					}
				} else {
					//TODO
					Toast.makeText(CstmPayActivity.this, getString(R.string.bad_network), Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onError(error, content);
				Toast.makeText(CstmPayActivity.this, getString(R.string.bad_network), Toast.LENGTH_LONG).show();
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
		if(!TextUtils.isEmpty(addresses.getHandset())){
			tv_phoneName.setText(addresses.getHandset());
		}else {
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
	
	private ArrayList<Express> expressArray ;

	
	private void showExpressNum(String generalNum, String emsNum){
		if (isFree) {
			tv_generalPrice.setText("0");
			tv_emsPrice.setText("0");
			tv_sumPrice.setText(goodsPrice + "");
			expressNum = "0";
		} else {
			tv_generalPrice.setText(generalNum);
			tv_emsPrice.setText(emsNum);
			expressNum = (cb_general.isChecked() ? tv_generalPrice
					.getText().toString() : tv_emsPrice.getText()
					.toString());
			tv_sumPrice.setText(goodsPrice
					+ Float.parseFloat(expressNum) + "");
		}
	}

	/**跳转到修改配送中心页面**/
	private void go2Delivery(){
		Intent intent = new Intent(CstmPayActivity.this,
				DeliveryActivity.class);
		intent.putExtra("preId", preId);
		CstmPayActivity.this.startActivityForResult(intent,
				Consts.DELIVERY_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// super.onActivityResult(requestCode, resultCode, data);
		/*************************************************
		 * 
		 * 步骤3：处理银联手机支付控件返回的支付结果
		 * 
		 ************************************************/

//		Log.i("info", requestCode + "   requestCode");
//		Log.i("info", resultCode + "   resultCode");

		
		if (data == null) {
			return;
		}
		if (requestCode == Consts.AddressRequestCode
				&& resultCode == Consts.AddressResponseCode) {
			ModelAddresses addresses1 = (ModelAddresses) data.getExtras()
					.getSerializable("addresses1");
			Log.i("info", addresses1.getAddress() + "str");
			if (addresses1 != null) {
				setAdd(addresses1);
				// 判断是否免邮
//				canFree(sum);
				
//				getDeliveryPoint(true);
				getExpressData(addresses1.getProvice(), true);
				// 设置快递费用和配送中心
//				setKuaiDi(addresses1);
			}
		} else if (requestCode == Consts.CstmPayActivity_Request
				&& resultCode == Consts.CstmPayActivity_Response) {
			carts.clear();
			carts = (ArrayList<Cart>) data.getSerializableExtra("carts");
			

			voucher = data.getFloatExtra("voucher", -1);
			jifen = data.getFloatExtra("jifen", -1);
			// Log.i("voucher", voucher + "  voucher");
			// Log.i("voucher", voucher + "  voucher");
			// isCartActivity = data.getStringExtra("cartActivity");
//			show(carts, false);
			layout.removeAllViews();
			PayUtil pay = new PayUtil(CstmPayActivity.this, layout);
			goods = pay.loadView(carts, false);
		} else if(Consts.DELIVERY_REQUEST == requestCode && Consts.DELIVERY_RESULT == resultCode){
			Express delivery = (Express) data.getExtras().getSerializable("delivery");
			tv_peiSong.setText(delivery.getDisName());
			preId = delivery.getPreId();
			if(isFree) return;
			getExpressData(province, false);
		}
	}
	

	@Override
	protected void onResume() {
		super.onResume();
//		StatService.onResume(this);
		StatService.onPageStart(this, "支付页面");
	}

	@Override
	protected void onPause() {
		super.onPause();
//		StatService.onPause(this);
		StatService.onPageEnd(this, "支付页面");
	}
	
}