package com.yidejia.app.mall.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.UserPayActivity;
import com.yidejia.app.mall.datamanage.AddressDataManage;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.datamanage.ExpressDataManage;
import com.yidejia.app.mall.datamanage.OrderDataManage;
import com.yidejia.app.mall.datamanage.PreferentialDataManage;
import com.yidejia.app.mall.datamanage.VoucherDataManage;
import com.yidejia.app.mall.fragment.CartActivity.InnerReceiver;
import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.Express;
import com.yidejia.app.mall.model.FreePost;
import com.yidejia.app.mall.model.Specials;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.Http;
import com.yidejia.app.mall.util.MessageUtil;
import com.yidejia.app.mall.util.PayUtil;

public class CstmPayActivity extends SherlockActivity {
	private InnerReceiver receiver;
	private TextView userName;// 用户名
	private TextView phoneName;// 电话号码
	private TextView address;// 收货地址
	private TextView peiSong;// 配送地址
	private CheckBox general;// 普通配送
	private CheckBox emsBox;// ems配送
	private TextView generalPrice;// 普通配送价格
	private TextView emsPrice;// ems配送价格
	private AddressDataManage addressDataManage;
	// private ExpressDataManage expressDataManage;// 配送中心
	private CheckBox zhifubaoCheckBox;// 支付宝
	private CheckBox zhifubaowangyeCheckBox;// 支付宝网页支付
	private CheckBox yinlianCheckBox;// 银联支付
	private CheckBox caifutongCheckBox;// 财付通支付
	private TextView sumPrice;// 总的价格
	private Button saveOrderBtn;// 提交订单
	private Handler mHandler;// 创建handler对象
	private MyApplication myApplication;
	private EditText comment;// 评论
	private AlertDialog dialog;
	private RelativeLayout addressRelative;
	private PreferentialDataManage preferentialDataManage;//
	public static ArrayList<Specials> arrayListFree;
	public static ArrayList<Specials> arrayListExchange;

	// private EditText comment;//评论
	private ScrollView go_pay_scrollView;
	private VoucherDataManage voucherDataManage;

	// private Myreceiver receiver;
	// private Addresses addresses;
	private String peiSongCenter = "";// //配送中心
	private String recipientId = "";// 收集人id
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

	private static final String SERVER_URL = "http://202.104.148.76/splugin/interface";
	private static final String TRADE_COMMAND = "1001";
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

	/**
	 * 实例化控件
	 */
	public void setupShow() {
		try {
			// expressDataManage = new ExpressDataManage(this);

			sumPrice = (TextView) findViewById(R.id.go_pay_show_pay_money);
			saveOrderBtn = (Button) findViewById(R.id.save_order_btn);
			userName = (TextView) findViewById(R.id.go_pay_name);
			phoneName = (TextView) findViewById(R.id.go_pay_number);
			address = (TextView) findViewById(R.id.go_pay_address);
			peiSong = (TextView) findViewById(R.id.go_pay_peisong);
			general = (CheckBox) findViewById(R.id.go_pay_check);
			emsBox = (CheckBox) findViewById(R.id.go_pay_ems);
			generalPrice = (TextView) findViewById(R.id.go_pay_general_price);
			emsPrice = (TextView) findViewById(R.id.go_pay_ems_price);
			comment = (EditText) findViewById(R.id.go_pay_leave_message);

			RelativeLayout zhifubao = (RelativeLayout) findViewById(R.id.go_pay_zhifubao_relative);
			RelativeLayout zhifubaowangye = (RelativeLayout) findViewById(R.id.go_pay_zhifubao_wangyezhifu_relative);
			RelativeLayout yinlian = (RelativeLayout) findViewById(R.id.go_pay_zhifubao_yinlian_relative);
			RelativeLayout cafutong = (RelativeLayout) findViewById(R.id.go_pay_zhifubao_caifutong_relative);
			RelativeLayout generalRelative = (RelativeLayout) findViewById(R.id.go_pay_general_relative);
			RelativeLayout emsRelative = (RelativeLayout) findViewById(R.id.go_pay_payEMS_relative);

			zhifubaoCheckBox = (CheckBox) findViewById(R.id.zhifubao_checkbox);
			zhifubaowangyeCheckBox = (CheckBox) findViewById(R.id.zhufubaowangye_checkbox);
			yinlianCheckBox = (CheckBox) findViewById(R.id.yinlian_checkbox);
			caifutongCheckBox = (CheckBox) findViewById(R.id.caifutong_checkbox);

			// //支付宝和财付通暂时不可选
			// zhifubaoCheckBox.setClickable(false);
			// zhifubaowangyeCheckBox.setClickable(false);
			// caifutongCheckBox.setClickable(false);
			// 默认选择银联支付
			yinlianCheckBox.setChecked(true);

			// 支付宝和财付通暂时不可见
			// zhifubao.setVisibility(ViewGroup.GONE);
			// zhifubaowangye.setVisibility(ViewGroup.GONE);
			// cafutong.setVisibility(ViewGroup.GONE);

			generalRelative.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (!general.isChecked()) {
						general.setChecked(true);
						emsBox.setChecked(false);
						Message ms = new Message();
						mHandler.sendEmptyMessage(Consts.GENERAL);
					} else {
						general.setChecked(false);
						emsBox.setChecked(true);
						Message ms = new Message();
						mHandler.sendEmptyMessage(Consts.EMS);
					}
				}
			});
			emsRelative.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (!emsBox.isChecked()) {
						general.setChecked(false);
						emsBox.setChecked(true);
						Message ms = new Message();
						mHandler.sendEmptyMessage(Consts.EMS);
					} else {
						general.setChecked(true);
						emsBox.setChecked(false);
						Message ms = new Message();
						mHandler.sendEmptyMessage(Consts.GENERAL);
					}
				}
			});
			// // 快递
			// general.setOnCheckedChangeListener(new OnCheckedChangeListener()
			// {
			//
			// @Override
			// public void onCheckedChanged(CompoundButton buttonView,
			// boolean isChecked) {
			// if (!isChecked) {
			// emsBox.setChecked(true);
			// } else {
			// emsBox.setChecked(false);
			// Message ms = new Message();
			// mHandler.sendEmptyMessage(Consts.GENERAL);
			// }
			//
			// }
			// });
			// emsBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			//
			// @Override
			// public void onCheckedChanged(CompoundButton buttonView,
			// boolean isChecked) {
			// // TODO Auto-generated method stub
			// if (!isChecked) {
			// general.setChecked(true);
			// ;
			//
			// } else {
			// general.setChecked(false);
			// mHandler.sendEmptyMessage(Consts.EMS);
			// }
			// }
			// });

			/* 隐藏支付宝财付通的支付方式 */
			zhifubao.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (!zhifubaoCheckBox.isChecked()) {
						zhifubaoCheckBox.setChecked(true);
						zhifubaowangyeCheckBox.setChecked(false);
						yinlianCheckBox.setChecked(false);
						caifutongCheckBox.setChecked(false);
					} else {
						zhifubaoCheckBox.setChecked(false);
					}
				}
			});

			zhifubaowangye.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (!zhifubaowangyeCheckBox.isChecked()) {
						zhifubaowangyeCheckBox.setChecked(true);
						zhifubaoCheckBox.setChecked(false);
						yinlianCheckBox.setChecked(false);
						caifutongCheckBox.setChecked(false);
					} else {
						zhifubaowangyeCheckBox.setChecked(false);
					}
				}
			});
			yinlian.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (!yinlianCheckBox.isChecked()) {
						yinlianCheckBox.setChecked(true);
						zhifubaoCheckBox.setChecked(false);
						zhifubaowangyeCheckBox.setChecked(false);
						caifutongCheckBox.setChecked(false);
					} else {
						yinlianCheckBox.setChecked(false);
					}
				}
			});
			cafutong.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (!caifutongCheckBox.isChecked()) {
						caifutongCheckBox.setChecked(true);
						zhifubaoCheckBox.setChecked(false);
						zhifubaowangyeCheckBox.setChecked(false);
						yinlianCheckBox.setChecked(false);
					} else {
						caifutongCheckBox.setChecked(false);
					}
				}
			});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(CstmPayActivity.this,
					getResources().getString(R.string.bad_network),
					Toast.LENGTH_SHORT).show();
		}

	}

	private ArrayList<Cart> carts;// 购物车的数据，非换购的数据
	private RelativeLayout reLayout;
	private String isCartActivity;
	private String sum;

	private PayUtil pay;
	private float maxPay;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			Intent intent = getIntent();
			// final String sum = intent.getStringExtra("price");
			sum = intent.getStringExtra("price");
			maxPay = Float.parseFloat(sum);
			// carts = new ArrayList<Cart>();
			// cartList = new ArrayList<Cart>();
			Log.i(TAG, "sum:" + sum);
			carts = (ArrayList<Cart>) intent.getSerializableExtra("carts");
			isCartActivity = intent.getStringExtra("cartActivity");
			myApplication = (MyApplication) getApplication();
			voucherDataManage = new VoucherDataManage(CstmPayActivity.this);

			preferentialDataManage = new PreferentialDataManage(
					CstmPayActivity.this);
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
									// TODO Auto-generated method stub
									Intent intent = new Intent(
											CstmPayActivity.this,
											ExchangeFreeActivity.class);
									Bundle bundle = new Bundle();
									bundle.putString("cartActivity",
											isCartActivity);
									bundle.putString("price", sum + "");
									intent.putExtras(bundle);
									intent.putExtra("carts", carts);
									Log.i("info", "voucher:" + voucher);
									intent.putExtra("voucher", voucher);
									CstmPayActivity.this
											.startActivityForResult(
													intent,
													Consts.CstmPayActivity_Request);
								}
							})
					.setNegativeButton(
							getResources().getString(R.string.cancel),
							new android.content.DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									if (isCartActivity.equals("Y")
											|| isCartActivity.equals("N")) {
										show(carts, false);
										// voucherDataManage = new
										// VoucherDataManage(CstmPayActivity.this);
										// voucher
										// =Float.parseFloat(voucherDataManage.getUserVoucher(myApplication.getUserId(),
										// myApplication.getToken()));
									}
								}
							}).create();
			// new android.content.DialogInterface.OnClickListener() {
			//
			// @Override
			// public void onClick(DialogInterface dialog,
			// int which) {
			// // TODO Auto-generated method stub
			// Intent intent = new Intent(
			// CstmPayActivity.this,
			// CstmPayActivity.class);
			// Bundle bundle = new Bundle();
			// // float sum = Float.parseFloat(sumTextView
			// // .getText().toString());
			// // bundle.putString("price", sum + "");
			// // intent.putExtras(bundle);
			// CstmPayActivity.this.startActivity(intent);
			//
			// }
			// }).create();
			// receiver = new Myreceiver();
			// IntentFilter filter = new IntentFilter();
			// filter.addAction(Consts.BUY_NEW);
			// registerReceiver(receiver, filter);
			// 付款之前先判断用户是否登陆
			if (!myApplication.getIsLogin()) {
				Toast.makeText(this,
						getResources().getString(R.string.please_login),
						Toast.LENGTH_LONG).show();
				Intent intent1 = new Intent(this, LoginActivity.class);
				startActivity(intent1);
				this.finish();
			} else {

				// Cart cart = (Cart) intent.getSerializableExtra("Cart");
				// ArrayList<Cart> carts;
				//
				//
				voucherString1 = voucherDataManage.getUserVoucherForPay(
						myApplication.getUserId(), myApplication.getToken(),
						true);

				// voucher = Float.parseFloat(voucherString1);
				if ("".equals(voucherString1) || null == voucherString1)
					voucher = 0;
				else
					voucher = Float.parseFloat(voucherString1);
				Log.e(TAG, voucherString1 + ":voucher and is cartact"
						+ isCartActivity);
				if (carts.isEmpty())
					return;
				StringBuffer sb = new StringBuffer();
				if ("".equals(carts)) {
					Toast.makeText(CstmPayActivity.this,
							getResources().getString(R.string.no_network),
							Toast.LENGTH_SHORT).show();
				}
				for (int i = 0; i < carts.size(); i++) {
					Cart cart = carts.get(i);
					sb.append(cart.getUId());
					sb.append(",");
					sb.append(cart.getAmount());
					sb.append("n");
					sb.append(";");
				}
				preferentialDataManage.getPreferential(sb.toString(),
						myApplication.getUserId());
				if(preferentialDataManage.getFreeGoods().size() == 0 && preferentialDataManage.getScoreGoods().size() == 0){
					show(carts, false);
					return;
				}
				if(preferentialDataManage.getFreeGoods().size() == 0 && preferentialDataManage.getScoreGoods().size() != 0){
					if(voucher  == 0){
						show(carts, false);
						return;
					}else{
						dialog.show();
					}
				}
			
				if (preferentialDataManage.getFreeGoods().size() != 0
						|| preferentialDataManage.getScoreGoods().size() != 0) {
					Log.i("info", preferentialDataManage.getFreeGoods().size()
							+ "   preferentialDataManage.getFreeGoods()");
					arrayListFree = preferentialDataManage.getFreeGoods();
					arrayListExchange = preferentialDataManage.getScoreGoods();
				} 
				
					if (isCartActivity.equals("Y")
							|| isCartActivity.equals("N")) {//
						show(carts, false);// sum,
						if (voucher > 0 || maxPay > fP) {
							Log.i("info", voucher + "   voucher");
							Log.i("info", maxPay + "   maxPay");
							dialog.show();

							//
							// show(carts,false);//sum,

							// } else if (isCartActivity.equals("E")) {
							// voucher = intent.getIntExtra("voucher", -1);
							// Log.i("voucher", voucher + "  voucher");
							// show(carts,true);
						}
						// else {
						// // if (!carts.isEmpty()) {
						// // show(carts,);//sum,
						// Log.i("info", voucher+"   voucher");
						// Log.i("info", maxPay+"   maxPay");
						// show(carts, false);
//					}
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(CstmPayActivity.this,
					getResources().getString(R.string.bad_network),
					Toast.LENGTH_SHORT).show();
		}

	}

	private void show(final ArrayList<Cart> carts, boolean isHuanGou) {
		Log.i(TAG, "show sum:" + sum);
		setContentView(R.layout.go_pay);
		// 注册返回时的广播
		// receiver = new InnerReceiver();
		// IntentFilter filter = new IntentFilter();
		// filter.addAction(Consts.BACK_UPDATE_CHANGE);
		// registerReceiver(receiver, filter);

		addressDataManage = new AddressDataManage(this);
		go_pay_scrollView = (ScrollView) findViewById(R.id.go_pay_scrollView);
		setupShow();

		setActionbar();
		layout = (LinearLayout) findViewById(R.id.go_pay_relative2);
		pay = new PayUtil(CstmPayActivity.this, layout);

		// goods = pay.loadView(carts, false);

		// RelativeLayout relativeLayout = (RelativeLayout)
		// findViewById(R.id.go_shopping_use_evalution);
		// relativeLayout.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// Intent intent = new Intent(CstmPayActivity.this,
		// ExchangeFreeActivity.class);
		// cartList = carts;
		// intent.putExtra("voucher", voucher);
		// intent.putExtra("cartActivity", isCartActivity);
		// Log.i("info", voucher+"   voucher");
		// intent.putExtra("price", sum + "");
		// CstmPayActivity.this.startActivity(intent);
		// }
		// });
		reLayout = (RelativeLayout) findViewById(R.id.go_pay_relative);
		addressRelative = (RelativeLayout) findViewById(R.id.go_pay_relativelayout);
		// 地址增加监听事件
		addressRelative.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CstmPayActivity.this,
						AddressActivity.class);
				CstmPayActivity.this.startActivityForResult(intent,
						Consts.AddressRequestCode);
			}
		});
		// PayUtil pay = new PayUtil(CstmPayActivity.this, layout);
		goods = pay.loadView(carts, isHuanGou);
		// 添加地址、快递费用、配送中心
		addAddress();
		// // 获取免邮界限
		// ExpressDataManage expressDataManage = new ExpressDataManage(
		// CstmPayActivity.this);
		// ArrayList<FreePost> freePosts = expressDataManage
		// .getFreePostList("0", "20");
		// float fP = Float.MAX_VALUE;
		// if (freePosts.size() != 0)
		// fP = Float.parseFloat(freePosts.get(0).getMax());
		// Log.i("info", fP + "   fp");
		// if (Float.parseFloat(sum) > fP) {
		// sumPrice.setText(sum);
		// expressNum = "0";
		// } else {
		// sumPrice.setText(Double.parseDouble(sum)
		// + Double.parseDouble((general.isChecked() ? generalPrice
		// .getText().toString() : emsPrice
		// .getText().toString())) + "");
		// }
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Consts.GENERAL:
					try {
						expressNum = generalPrice.getText().toString();
						sumPrice.setText(Float.parseFloat(sum)
								+ Float.parseFloat(expressNum) + "");
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					postMethod = getResources().getString(R.string.ship_post);// 快递方式
					break;
				case Consts.EMS:
					try {
						expressNum = emsPrice.getText().toString();
						sumPrice.setText(Float.parseFloat(sum)
								+ Float.parseFloat(expressNum) + "");
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					postMethod = "EMS";
					break;
				}
				super.handleMessage(msg);
			}
		};
		// } else {
		// /*
		// * setActionbar(); setContentView(R.layout.go_pay);
		// * LinearLayout layout = (LinearLayout)
		// * findViewById(R.id.go_pay_relative2); PayUtil pay = new
		// * PayUtil(CstmPayActivity.this, layout, cart); PayUtil
		// pay
		// * = new PayUtil(CstmPayActivity.this, layout,
		// * carts.get(0));
		// *
		// * goods = pay.cartLoadView(); setupShow(); addAddress();
		// //
		// * 获取免邮界限 ExpressDataManage expressDataManage = new
		// * ExpressDataManage( CstmPayActivity.this);
		// * ArrayList<FreePost> freePosts = expressDataManage
		// * .getFreePostList("0", "20"); float fP =
		// Float.MAX_VALUE;
		// * if (freePosts.size() != 0) fP =
		// * Float.parseFloat(freePosts.get(0).getMax());
		// *
		// * if (Float.parseFloat(sum) > fP) {
		// sumPrice.setText(sum);
		// * } else { sumPrice.setText(Double.parseDouble(sum) +
		// * Double.parseDouble((general.isChecked() ? generalPrice
		// * .getText().toString() : emsPrice
		// .getText().toString()))
		// * + ""); } mHandler = new Handler() {
		// *
		// * @Override public void handleMessage(Message msg) {
		// switch
		// * (msg.what) { case Consts.GENERAL: expressNum =
		// * generalPrice.getText().toString();
		// * sumPrice.setText(Double.parseDouble(sum) +
		// * Double.parseDouble(expressNum) + ""); postMethod =
		// * getResources().getString( R.string.ship_post);// 快递方式
		// * break; case Consts.EMS: expressNum =
		// * emsPrice.getText().toString();
		// * sumPrice.setText(Double.parseDouble(sum) +
		// * Double.parseDouble(expressNum) + ""); postMethod =
		// "EMS";
		// * break; } super.handleMessage(msg); } };
		// */
		// }
		// 提交订单
		saveOrderBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!yinlianCheckBox.isChecked()
						&& !zhifubaoCheckBox.isChecked()
						&& !zhifubaowangyeCheckBox.isChecked()
						&& !caifutongCheckBox.isChecked()) {
					Toast.makeText(CstmPayActivity.this,
							getResources().getString(R.string.select_pay_type),
							Toast.LENGTH_LONG).show();
					go_pay_scrollView.smoothScrollTo(0, 0);
					return;
				}
				int mode = 1;
				String pay_type = "";
				if (yinlianCheckBox.isChecked()) {
					pay_type = "union";
					mode = 1;
				} else if (caifutongCheckBox.isChecked()) {
					mode = 0;
					pay_type = "tenpay";
					Toast.makeText(CstmPayActivity.this, "亲，暂时只支持银联的支付方式哦！",
							Toast.LENGTH_LONG).show();
					return;
				} else if (zhifubaoCheckBox.isChecked()) {
					mode = 2;
					pay_type = "alipay";
					Toast.makeText(CstmPayActivity.this, "亲，暂时只支持银联的支付方式哦！",
							Toast.LENGTH_LONG).show();
					return;
				} else {
					mode = 3;
					pay_type = "aliwappay";
					Toast.makeText(CstmPayActivity.this, "亲，暂时只支持银联的支付方式哦！",
							Toast.LENGTH_LONG).show();
					return;
				}
				OrderDataManage orderDataManage = new OrderDataManage(
						CstmPayActivity.this);
				orderDataManage.saveOrder(myApplication.getUserId(), "0",
						recipientId, "", jifen + "", expressNum, postMethod,
						peiSongCenter, goods, comment.getText().toString(),// 这里的comment.getText().toString()很重要
						pay_type, myApplication.getToken());
				// postMethod, peiSongCenter, goods,
				// comment.getText().toString(),
				// pay_type, myApplication.getToken());
				orderCode = orderDataManage.getOrderCode();
				resp_code = orderDataManage.getRespCode();
				tn = orderDataManage.getTN();
				// Log.e("OrderCode", orderCode);
				if (orderCode == null || "".equals(orderCode))
					return;
				if (isCartActivity.equals("Y")) {// ；来自购物车
					// 删除购物车的商品
					CartsDataManage cartsDataManage = new CartsDataManage();
					int length = carts.size();
					for (int i = 0; i < length; i++) {
						cartsDataManage.delCart(carts.get(i).getUId());
					}
					Intent intent = null;
					// if(cartsDataManage.getCartAmount() != 0){
					Log.e("NoProduceFragment", "DELETE_CART");
					intent = new Intent(Consts.DELETE_CART);
					CstmPayActivity.this.sendBroadcast(intent);
					// }
					// else {
					// Log.e("NoProduceFragment", "BROAD_UPDATE_CHANGE");
					// intent = new Intent(Consts.BROAD_UPDATE_CHANGE);
					// }
				}
				// 跳转到支付页面
				Intent userpayintent = new Intent(CstmPayActivity.this,
						UserPayActivity.class);
				Bundle bundle = new Bundle();

				bundle.putInt("mode", mode);
				// bundle.putString("name", "hello");
				// bundle.putString("amount", "1.00");
				bundle.putString("code", orderCode);
				bundle.putString("uid", myApplication.getUserId());
				bundle.putString("resp_code", resp_code);
				bundle.putString("tn", tn);

				userpayintent.putExtras(bundle);
				startActivity(userpayintent);
				CstmPayActivity.this.finish();

			}
		});
	}

	// @Override
	// protected void onDestroy() {
	// // TODO Auto-generated method stub
	// super.onDestroy();
	// // unregisterReceiver(receiver);
	// }
	/**
	 * UI控件的顶部
	 */
	private void setActionbar() {
		try {
			getSupportActionBar().setDisplayHomeAsUpEnabled(false);
			getSupportActionBar().setDisplayShowHomeEnabled(false);
			getSupportActionBar().setDisplayShowTitleEnabled(false);
			getSupportActionBar().setDisplayUseLogoEnabled(false);
			getSupportActionBar().setDisplayShowCustomEnabled(true);
			getSupportActionBar().setCustomView(R.layout.actionbar_compose);
			ImageView button = (ImageView) findViewById(R.id.compose_back);
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					CstmPayActivity.this.finish();
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(CstmPayActivity.this,
					getResources().getString(R.string.bad_network),
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 地址和快递费用，配送中心
	 */
	private void addAddress() {
		// Log.i(TAG, "show sum:"+sum);
		try {
			// ArrayList<Addresses> mList1 =
			// addressDataManage.getAddressesArray(
			//
			// myApplication.getUserId(), 0, 10);
			ArrayList<Addresses> mList = addressDataManage
					.getDefAddresses(myApplication.getUserId());
			Addresses addresses;
			// Log.i("info", mList.size() + " mlist");
			if (mList.size() == 0) {
				ArrayList<Addresses> addArray = addressDataManage
						.getAddressesArray(myApplication.getUserId(), 0, 10);
				if (addArray.isEmpty()) { // 无默认地址并且无地址
					Intent intent = new Intent(CstmPayActivity.this,
							NewAddressActivity.class);
					CstmPayActivity.this.startActivity(intent);
					return;
				} else {
					addresses = addArray.get(0);
				}
			} else {
				addresses = mList.get(0);
				// 获取默认地址
				int size = mList.size();
				for (int i = 0; size > 0 && i < size; i++) {
					boolean isDefault = mList.get(i).getDefaultAddress();
					if (isDefault) {
						addresses = mList.get(i);
						break;
					}
				}
			}
			setAdd(addresses);// 设置地址

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(CstmPayActivity.this,
					getResources().getString(R.string.bad_network),
					Toast.LENGTH_SHORT).show();
		}
		// userName.setText(addresses.getName());
		// phoneName.setText(addresses.getHandset());// �޸�Ϊ�ֻ�
		// StringBuffer sb = new StringBuffer();
		// sb.append(addresses.getProvice());
		// sb.append(addresses.getCity());
		// sb.append(addresses.getArea());
		// sb.append(addresses.getAddress());
		// address.setText(sb.toString());
		// recipientId = addresses.getAddressId();
		//
		// Express express = expressDataManage.getExpressesExpenses(
		// addresses.getProvice(), "y").get(0);
		// peiSongCenter = expressDataManage
		// .getDistributionsList(express.getPreId(), 0 + "",
		// 10 + "").get(0).getDisName(); // 获取配送中心
		// peiSong.setText(peiSongCenter);
		// generalPrice.setText(express.getExpress());
		// emsPrice.setText(express.getEms());
		// Log.i("info", addresses.getName());
		// postMethod = getResources().getString(R.string.ship_post);// 初始化快递方式;
		// expressNum = express.getExpress();// 初始化费用
		// }
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// Toast.makeText(CstmPayActivity.this,
		// getResources().getString(R.string.bad_network),
		// Toast.LENGTH_SHORT).show();
		// }
	}

	/**
	 * 设置地址，快递和配送中心
	 * 
	 * @param addresses
	 */
	private void setAdd(Addresses addresses) {
		// Log.i(TAG, "show sum:"+sum);
		// Log.i(TAG, "add address");
		userName.setText(addresses.getName());
		phoneName.setText(addresses.getHandset());// �޸�Ϊ�ֻ�
		StringBuffer sb = new StringBuffer();
		sb.append(addresses.getProvice());
		sb.append(addresses.getCity());
		sb.append(addresses.getArea());
		sb.append(addresses.getAddress());
		address.setText(sb.toString());
		recipientId = addresses.getAddressId();// 地址id
		// 判断是否免邮
		canFree(sum);

		// 设置快递费用和配送中心
		setKuaiDi(addresses);
	}

	/**
	 * 设置快递费用和配送中心
	 * 
	 * @param addresses
	 */
	private void setKuaiDi(Addresses addresses) {
		Express express;
		ArrayList<Express> expressArray = new ExpressDataManage(this)
				.getExpressesExpenses(addresses.getProvice(), "y");
		if (expressArray.isEmpty())
			return;
		express = expressArray.get(0);
		Log.i(TAG, "peisong id" + express.getPreId());
		// 设置配送中心
		setPeiSong(express.getPreId());
		Log.i("info", "setKuaidi:");
		postMethod = getResources().getString(R.string.ship_post);// 初始化快递方式;
		expressNum = express.getExpress();// 初始化费用
		if (isFree) {
			generalPrice.setText("0");
			emsPrice.setText("0");
			sumPrice.setText(sum);
			expressNum = "0";
		} else {
			generalPrice.setText(express.getExpress());
			emsPrice.setText(express.getEms());
			sumPrice.setText(Float.parseFloat(sum)
					+ Float.parseFloat((general.isChecked() ? generalPrice
							.getText().toString() : emsPrice.getText()
							.toString())) + "");
		}
		Log.i("info", "setKuaidi:");
		// postMethod = getResources().getString(R.string.ship_post);// 初始化快递方式;
		// expressNum = express.getExpress();// 初始化费用
	}

	/**
	 * 根据配送中心的id获取配送中心
	 * 
	 * @param preId
	 */
	private void setPeiSong(String preId) {
		try {
			peiSongCenter = new ExpressDataManage(this)
					.getDistributionsList(preId, 0 + "", 10 + "").get(0)
					.getDisName(); // 获取配送中心
			Log.i("info", "setpeisong:" + peiSongCenter);
			peiSong.setText(peiSongCenter);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private float fP;

	/**
	 * 判断是否免邮
	 * 
	 * @param sum
	 */
	private void canFree(String sum) {
		// 获取免邮界限
		ExpressDataManage expressDataManage = new ExpressDataManage(
				CstmPayActivity.this);
		ArrayList<FreePost> freePosts = expressDataManage.getFreePostList("0",
				"20");
		Log.i(TAG, "show sum:" + sum);
		fP = Float.MAX_VALUE;
		if (freePosts.size() != 0)
			fP = Float.parseFloat(freePosts.get(0).getMax());
		Log.i("info", fP + "   fp");
		try {
			if (Float.parseFloat(sum) >= fP) {// 大于等于免邮费用

				isFree = true;
			} else {

				isFree = false;
			}
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private Callable<Map<String, String>> call = new Callable<Map<String, String>>() {

		@Override
		public Map<String, String> call() throws Exception {

			Map<String, String> params = new HashMap<String, String>();
			params.put("orderDesc", "hello");
			params.put("orderAmt", "1000");
			String data = MessageUtil.assemblyParams(params);
			String content = "command=" + TRADE_COMMAND + "&data=" + data;
			String msg = Http.post(SERVER_URL, content);
			Log.d(TAG, "http return: " + msg);
			if (null != msg) {
				Map<String, String> resp = MessageUtil.resolveParams(msg);
				return resp;
			}

			return null;
		}
	};

	// @Override
	// protected void onDestroy() {
	// // TODO Auto-generated method stub
	// super.onDestroy();
	// unregisterReceiver(receiver);
	// }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// super.onActivityResult(requestCode, resultCode, data);
		/*************************************************
		 * 
		 * 步骤3：处理银联手机支付控件返回的支付结果
		 * 
		 ************************************************/

		Log.i("info", requestCode + "   requestCode");
		Log.i("info", resultCode + "   resultCode");
		if (data == null) {
			return;
		}
		if (requestCode == Consts.AddressRequestCode
				&& resultCode == Consts.AddressResponseCode) {
			Addresses addresses1 = (Addresses) data.getExtras()
					.getSerializable("addresses1");
			Log.i("info", addresses1.getAddress() + "str");
			if (addresses1 != null) {
				reLayout.removeView(addressRelative);
				userName.setText(addresses1.getName());
				phoneName.setText(addresses1.getHandset());// �޸�Ϊ�ֻ�
				StringBuffer sb = new StringBuffer();
				sb.append(addresses1.getProvice());
				sb.append(addresses1.getCity());
				sb.append(addresses1.getArea());
				sb.append(addresses1.getAddress());
				address.setText(sb.toString());
				setAdd(addresses1);
			}
		} else if (requestCode == Consts.CstmPayActivity_Request
				&& resultCode == Consts.CstmPayActivity_Response) {

			carts = (ArrayList<Cart>) data.getSerializableExtra("carts");
			Log.i("voucher", carts.size() + "    carts.size()11");

			voucher = data.getFloatExtra("voucher", -1);
			Log.i("voucher", jifen + "    jifen");
			jifen = data.getFloatExtra("jifen", -1);
			Log.i("voucher", jifen + "    jifen");
			// Log.i("voucher", voucher + "  voucher");
			// Log.i("voucher", voucher + "  voucher");
			// isCartActivity = data.getStringExtra("cartActivity");
			show(carts, false);
		} else {
			// String msg = "";
			// /*
			// * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
			// */
			// String str = data.getExtras().getString("pay_result");
			// Log.i("info", str + "str");
			// if (str.equalsIgnoreCase("success")) {
			// msg = "支付成功！";
			// CartsDataManage cartsDataManage = new CartsDataManage();
			// cartsDataManage.cleanCart();
			// if (!"".equals(orderCode) && orderCode != null) {
			// OrderDataManage orderDataManage = new OrderDataManage(
			// CstmPayActivity.this);
			// orderDataManage.changeOrder(myApplication.getUserId(),
			// orderCode);
			// }
			// } else if (str.equalsIgnoreCase("fail")) {
			// msg = "支付失败！";
			// } else if (str.equalsIgnoreCase("cancel")) {
			// msg = "用户取消了支付";
			// }
			//
			// AlertDialog.Builder builder = new AlertDialog.Builder(this);
			// builder.setTitle("支付结果通知");
			// builder.setMessage(msg);
			// builder.setInverseBackgroundForced(true);
			// // builder.setCustomTitle();
			// builder.setNegativeButton("确定",
			// new DialogInterface.OnClickListener() {
			// @Override
			// public void onClick(DialogInterface dialog, int which) {
			// dialog.dismiss();
			// }
			// });
			// builder.create().show();
		}
	}

	private float voucher;

	// public class InnerReceiver extends BroadcastReceiver {
	//
	// @Override
	// public void onReceive(Context context, Intent intent) {
	// // TODO Auto-generated method stub
	// String action = intent.getAction();
	// Log.i("voucher", "no" + "  nihao");
	// if (Consts.BACK_UPDATE_CHANGE.equals(action)) {
	// // Log.i("info", action + "action");
	// layout.removeAllViews();
	// voucher = intent.getIntExtra("voucher", -1);
	// Log.i("voucher", voucher + "  voucher");
	// cartList = (ArrayList<Cart>) intent
	// .getSerializableExtra("carts");
	// // ArrayList<Cart> carts = ExchangeFreeActivity.mArrayList;
	// PayUtil pay = new PayUtil(CstmPayActivity.this, layout);
	// goods = pay.loadView(cartList, true);
	// }
	// }
	// }
}