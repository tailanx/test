package com.yidejia.app.mall.view;

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
	private RelativeLayout reLayout;
	private String isCartActivity;
	private String sum;
	private String contentType;

	private float goodsPrice;
	private float voucher;
	private final String TAG = getClass().getName();

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

			// //支付宝和财付通暂时不可选
			// cb_zhifubao.setClickable(false);
			// cb_zhifubaowangye.setClickable(false);
			// cb_caifutong.setClickable(false);
			// 默认选择银联支付
			cb_yinlian.setChecked(true);

			// 支付宝和财付通暂时不可见
			// zhifubao.setVisibility(ViewGroup.GONE);
			// zhifubaowangye.setVisibility(ViewGroup.GONE);
			// cafutong.setVisibility(ViewGroup.GONE);

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
		
		reLayout = (RelativeLayout) findViewById(R.id.go_pay_relative);
		addressRelative = (RelativeLayout) findViewById(R.id.go_pay_relativelayout);
		
		// 地址增加监听事件
		addressRelative.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (null != showAddress) {
					Intent intent = new Intent(CstmPayActivity.this,
							AddressActivity.class);
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
						}
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
			url = new JNICallBack().getHttp4GetDistribute("id%3D" + preId, "0", "20", "", "", "%2A");
		} else {
			url = new JNICallBack().getHttp4GetDistribute("flag%3D%27y%27", "0", "20", "", "", "%2A");
		}
		return url;
	}
	
	/**
	 * 获取发货地点
	 */
	private void getDeliveryPoint(){
//		String url = new JNICallBack().getHttp4GetDistribute("flag%3D%27y%27", "0", "20", "", "", "%2A");
		String url = getDeliveryUrl(true);
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
							Toast.makeText(CstmPayActivity.this, "dis is null", Toast.LENGTH_LONG).show();
						}
					} else {
						//TODO 解析出错或服务器返回-1
						Toast.makeText(CstmPayActivity.this, "dis is not 1", Toast.LENGTH_LONG).show();
					}
				} else {
					//TODO 返回失败
					Toast.makeText(CstmPayActivity.this, "dis is ok", Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				super.onError(error, content);
				// TODO 
				Toast.makeText(CstmPayActivity.this, "dis is error", Toast.LENGTH_LONG).show();
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
		Log.e("system.out", url);
		return url;
	}
	
	/**
	 * 根据省份获取配送中心id和获取快递费用
	 * @param province 省份
	 */
	private void getExpressData(String province) {
		
		String url = getExpressUrl(province, true);
		
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
							getDeliveryPoint();
						}  else {
							// TODO 
							Toast.makeText(CstmPayActivity.this, "Express is null", Toast.LENGTH_LONG).show();
						}
					} else {
						// TODO 
						Log.e("system.out", content);
						Toast.makeText(CstmPayActivity.this, "Express is not success", Toast.LENGTH_LONG).show();
					}
				} else {
					//TODO 返回失败
					Toast.makeText(CstmPayActivity.this, "Express is ok", Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				super.onError(error, content);
				// TODO 
				Toast.makeText(CstmPayActivity.this, "Express is error", Toast.LENGTH_LONG).show();
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
							getExpressData(province);
						} else {
							// TODO 提示用户获取免邮信息出错？
							Toast.makeText(CstmPayActivity.this, "can free is null", Toast.LENGTH_LONG).show();
						}
					} else {
						// TODO 返回-1
						Toast.makeText(CstmPayActivity.this, "can free is not 1", Toast.LENGTH_LONG).show();
					}
				} else {
					// TODO 返回失败
					Toast.makeText(CstmPayActivity.this, "can free statusCode is not ok", Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				super.onError(error, content);
				// TODO 返回失败
				Toast.makeText(CstmPayActivity.this, "can free error", Toast.LENGTH_LONG).show();
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
							Toast.makeText(CstmPayActivity.this, "get credit null", Toast.LENGTH_LONG).show();
						}
					} else {
						//TODO
						Toast.makeText(CstmPayActivity.this, "get credit no success", Toast.LENGTH_LONG).show();
					}
				} else{
					//TODO
					Toast.makeText(CstmPayActivity.this, "get credit no ok", Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onError(error, content);
				Toast.makeText(CstmPayActivity.this, "get credit error", Toast.LENGTH_LONG).show();
			}
			
		});
	}
	
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
						if ((null != arrayListFree && !arrayListFree.isEmpty())
								|| (null != arrayListExchange && !arrayListExchange
										.isEmpty())) {
							dialog.show();
						} else {
							Toast.makeText(CstmPayActivity.this, "get free goods null", Toast.LENGTH_LONG).show();
						}
					} else {
						// TODO
						Toast.makeText(CstmPayActivity.this, "get free goods not success", Toast.LENGTH_LONG).show();
					}
				} else {
					//TODO
					Toast.makeText(CstmPayActivity.this, "get free goods not ok", Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onError(error, content);
				Toast.makeText(CstmPayActivity.this, "get free goods error", Toast.LENGTH_LONG).show();
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


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
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
				getDeliveryPoint();
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