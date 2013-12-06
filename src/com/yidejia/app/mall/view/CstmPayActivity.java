package com.yidejia.app.mall.view;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.UserPayActivity;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.datamanage.ExpressDataManage;
import com.yidejia.app.mall.datamanage.PreferentialDataManage;
import com.yidejia.app.mall.datamanage.VoucherDataManage;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.Express;
import com.yidejia.app.mall.model.FreePost;
import com.yidejia.app.mall.model.Specials;
import com.yidejia.app.mall.net.address.GetUserAddressList;
import com.yidejia.app.mall.net.order.SaveOrder;
import com.yidejia.app.mall.net.voucher.Voucher;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.PayUtil;
import com.yidejia.app.mall.widget.YLProgressDialog;

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
//	private AddressDataManage addressDataManage;
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
//
//	private static final String SERVER_URL = "http://202.104.148.76/splugin/interface";
//	private static final String TRADE_COMMAND = "1001";
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
			receiver = new InnerReceiver();
			IntentFilter filter = new IntentFilter();
			filter.addAction(Consts.CST_NEWADDRESS);
			registerReceiver(receiver, filter);
			
			ImageLoaderUtil imageLoaderUtil = new ImageLoaderUtil();
			imageLoader  = imageLoaderUtil.getImageLoader();
			listener = imageLoaderUtil.getAnimateFirstListener();
			options = imageLoaderUtil.getOptions();
			
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
									// Log.i("info", "voucher:" + voucher);
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
			
			// 付款之前先判断用户是否登陆
			if (!myApplication.getIsLogin()) {
				Toast.makeText(this,
						getResources().getString(R.string.please_login),
						Toast.LENGTH_LONG).show();
				Intent intent1 = new Intent(this, LoginActivity.class);
				startActivity(intent1);
				this.finish();
			} else {
				show(carts, false);
				
//				getVoucher();
				getPreferential();
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(CstmPayActivity.this,
					getResources().getString(R.string.bad_network),
					Toast.LENGTH_SHORT).show();
		}

	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		imageLoader.init(ImageLoaderConfiguration.createDefault(CstmPayActivity.this));
		imageLoader.stop();
		unregisterReceiver(receiver);
		closeTask();
		closeVoucherTask();
	}

	/**
	 * 积分换购
	 */
	private void getPreferential(){
		closeVoucherTask();
		taskVoucher = new TaskVoucher();
		taskVoucher.execute();
	}
	
	private void closeVoucherTask(){
		if(taskVoucher != null && Status.RUNNING == taskVoucher.getStatus().RUNNING){
			taskVoucher.cancel(true);
		}
	}
	
	private TaskVoucher taskVoucher;
	private boolean isTimeout = false;
	
	private class TaskVoucher extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Voucher voucher = new Voucher();
			try {
				String httpResponse;
				try {
					httpResponse = voucher.getHttpResponse(myApplication.getUserId(), myApplication.getToken());
					try {
					JSONObject httpObject = new JSONObject(httpResponse);
					int code = httpObject.getInt("code");
					if(code == 1){
						String response = httpObject.getString("response");
						JSONObject resObject = new JSONObject(response);
						voucherString1 = resObject.getString("can_use_score");
						return true;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				} catch (TimeOutEx e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					isTimeout = true;
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(!result){
				if (isTimeout) {
					Toast.makeText(
							CstmPayActivity.this,
							CstmPayActivity.this.getResources()
									.getString(R.string.time_out),
							Toast.LENGTH_SHORT).show();
					isTimeout = false;
					return;
				}
			}
			getVoucher();
		}
		
		
	}
	
	private void getVoucher() {
//		voucherString1 = voucherDataManage.getUserVoucherForPay(
//				myApplication.getUserId(), myApplication.getToken(), true);
		try {
			
			voucher = Float.parseFloat(voucherString1);
			Log.e(TAG, voucherString1 + ":voucher and is cartact" + isCartActivity);
		} catch (Exception e) {
			// TODO: handle exception
			voucherString1 = "";
		}
		if ("".equals(voucherString1) || null == voucherString1)
			voucher = 0;
		else
			voucher = Float.parseFloat(voucherString1);
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
		if (preferentialDataManage.getFreeGoods().size() == 0
				&& preferentialDataManage.getScoreGoods().size() == 0) {
			return;
		}
		if (preferentialDataManage.getFreeGoods().size() == 0
				&& preferentialDataManage.getScoreGoods().size() != 0) {
			if (voucher == 0) {
				// show(carts, false);
				return;
			} else {
				dialog.show();
			}
		}

		if (preferentialDataManage.getFreeGoods().size() != 0
				|| preferentialDataManage.getScoreGoods().size() != 0) {
			// Log.i("info", preferentialDataManage.getFreeGoods().size()
			// + "   preferentialDataManage.getFreeGoods()");
			arrayListFree = preferentialDataManage.getFreeGoods();
			arrayListExchange = preferentialDataManage.getScoreGoods();
		}

		if (isCartActivity.equals("Y") || isCartActivity.equals("N")) {//
		// show(carts, false);// sum,
			if (voucher > 0 || !arrayListFree.isEmpty()) {
				dialog.show();
			}
		}
	}
	private ImageLoader imageLoader;
	private ImageLoadingListener listener;
	private DisplayImageOptions options;
	private void show(final ArrayList<Cart> carts, boolean isHuanGou) {
		Log.i(TAG, "show sum:" + sum);
		setContentView(R.layout.go_pay);
		// 注册返回时的广播
		// receiver = new InnerReceiver();
		// IntentFilter filter = new IntentFilter();
		// filter.addAction(Consts.BACK_UPDATE_CHANGE);
		// registerReceiver(receiver, filter);
		
//		bar = new ProgressDialog(this);
//		bar.setCancelable(true);
//		bar.setMessage(getResources().getString(R.string.loading));
//		bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//		bar.setOnCancelListener(new DialogInterface.OnCancelListener() {
//			
//			@Override
//			public void onCancel(DialogInterface dialog) {
//				// TODO Auto-generated method stub
//				closeTask();
//			}
//		});
			
//		addressDataManage = new AddressDataManage(this);
		go_pay_scrollView = (ScrollView) findViewById(R.id.go_pay_scrollView);
		setupShow();

		setActionbar();
		layout = (LinearLayout) findViewById(R.id.go_pay_relative2);
		
	
		pay = new PayUtil(CstmPayActivity.this, layout,imageLoader,listener,options);

		reLayout = (RelativeLayout) findViewById(R.id.go_pay_relative);
		addressRelative = (RelativeLayout) findViewById(R.id.go_pay_relativelayout);
		
		// 地址增加监听事件
		addressRelative.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(!getUserAddressList.getAddresses().isEmpty()){
				Intent intent = new Intent(CstmPayActivity.this,
						AddressActivity.class);
				CstmPayActivity.this.startActivityForResult(intent,
						Consts.AddressRequestCode);
				}
			}
		});
		// PayUtil pay = new PayUtil(CstmPayActivity.this, layout);
		goods = pay.loadView(carts, isHuanGou);
		
		
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
				/*OrderDataManage orderDataManage = new OrderDataManage(
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
				*/
				closeTask();
				taskSaveOrder = new TaskSaveOrder();
				taskSaveOrder.execute();
			}
		});
	
		// 添加地址、快递费用、配送中心
//		addAddress();
		closeTask();
		addressTask = new AddressTask();
		addressTask.execute();
	}
	
	String pay_type = "";
	int mode = 1;
	
	private void go2Pay(int mode){
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
	
	private TaskSaveOrder taskSaveOrder;
	
	private class TaskSaveOrder extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			SaveOrder saveOrder = new SaveOrder(CstmPayActivity.this);
			try {
				String httpresp;
				try {
					httpresp = saveOrder.getHttpResponse(myApplication.getUserId(), "0",
							recipientId, "", jifen + "", expressNum, postMethod,
							peiSongCenter, goods, comment.getText().toString(),// 这里的comment.getText().toString()很重要
							pay_type, myApplication.getToken());
				boolean issuccess = saveOrder.analysisHttpResp(httpresp);
				orderCode = saveOrder.getOrderCode();
				resp_code = saveOrder.getResp_code();
				tn = saveOrder.getTn();
				return issuccess;
				} catch (TimeOutEx e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					isTimeout = true;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			bar.show();
			bar = (ProgressDialog) new YLProgressDialog(CstmPayActivity.this)
			.createLoadingDialog(CstmPayActivity.this, null);
	bar.setOnCancelListener(new DialogInterface.OnCancelListener() {

		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			cancel(true);
		}
	});
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			bar.dismiss();
			if(result){
				go2Pay(mode);
			} else{
				if (isTimeout) {
					Toast.makeText(
							CstmPayActivity.this,
							CstmPayActivity.this.getResources()
									.getString(R.string.time_out),
							Toast.LENGTH_SHORT).show();
					isTimeout = false;
					return;
				}
				Toast.makeText(CstmPayActivity.this, "提交订单失败", Toast.LENGTH_LONG).show();
			}
		}
		
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

	private void closeTask(){
		if(addressTask != null && addressTask.getStatus().RUNNING == Status.RUNNING){
			addressTask.cancel(true);
		}
		if(taskSaveOrder != null && taskSaveOrder.getStatus().RUNNING == Status.RUNNING) {
			taskSaveOrder.cancel(true);
		}
	}
	
	private GetUserAddressList getUserAddressList;
	private AddressTask addressTask;
	private ProgressDialog bar;
	
	private class AddressTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Boolean issuccess = false;
			getUserAddressList = new GetUserAddressList();
			String httpresp;
			try {
				
				try {
				httpresp = getUserAddressList.getAddressHttpresp("customer_id%3D"+myApplication.getUserId()+"+and+is_default%3D%27y%27+and+valid_flag%3D%27y%27", 0 + "", 1 + "");
				issuccess = getUserAddressList.analysis(httpresp);
				mList = getUserAddressList.getAddresses();
				Addresses addresses;
				if(issuccess && mList != null && !mList.isEmpty()) {
//					return issuccess;
//					if()
						addresses = mList.get(0);
//					else issuccess = false;
				} else {
					httpresp = getUserAddressList.getAddressHttpresp(
							"customer_id%3D" + myApplication.getUserId()
									+ "+and+valid_flag%3D%27y%27", 0 + "",
							1 + "");
					issuccess = getUserAddressList.analysis(httpresp);
					addArray = getUserAddressList.getAddresses();
					if(issuccess && addArray != null && !addArray.isEmpty())addresses = addArray.get(0);
					else return issuccess;
				}
				} catch (TimeOutEx e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					isTimeout = true;
				}
				/*// 获取免邮界限
				ExpressDataManage expressDataManage = new ExpressDataManage(
						CstmPayActivity.this);
				freePosts = expressDataManage.getFreePostList("0", "20");
				expressArray = new ExpressDataManage(CstmPayActivity.this)
				.getExpressesExpenses(addresses.getProvice(), "y");
				peiSongCenter = new ExpressDataManage(CstmPayActivity.this)
				.getDistributionsList(expressArray.get(0).getPreId(), 0 + "", 10 + "").get(0)
				.getDisName(); */// 获取配送中心
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return issuccess;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			bar.show();
			bar = (ProgressDialog) new YLProgressDialog(CstmPayActivity.this)
			.createLoadingDialog(CstmPayActivity.this, null);
	bar.setOnCancelListener(new DialogInterface.OnCancelListener() {

		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			cancel(true);
		}
	});
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			bar.dismiss();
			if(result){
				addAddress();
			} else {
				if (isTimeout) {
					Toast.makeText(
							CstmPayActivity.this,
							CstmPayActivity.this.getResources()
									.getString(R.string.time_out),
							Toast.LENGTH_SHORT).show();
					isTimeout = false;
			} 
		}
		}
		
	}
	
	private ArrayList<Addresses> mList;
	private ArrayList<Addresses> addArray;
	private AlertDialog addresssDialog;

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
//			ArrayList<Addresses> mList = new AddressDataManage()
//					.getDefAddresses(myApplication.getUserId());
			Addresses addresses;
//			addresssDialog = new Builder(CstmPayActivity.this).setTitle(getResources().getString(R.string.no_address_title)).setMessage(getResources().getString(R.string.no_address_content))
//					.setPositiveButton(getResources().getString(R.string.sure), new android.content.DialogInterface.OnClickListener() {
//						
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							// TODO Auto-generated method stub
//							Intent intent = new Intent(CstmPayActivity.this,
//									EditNewAddressActivity.class);
//							Bundle bundle = new Bundle();
//							bundle.putSerializable("editaddress", null);
//							intent.putExtras(bundle);
//							CstmPayActivity.this.startActivity(intent);
//						}
//					}).setNegativeButton(getResources().getString(R.string.cancel), null).create();
		
			// Log.i("info", mList.size() + " mlist");
			if (mList != null && !mList.isEmpty()) {
//				ArrayList<Addresses> addArray = addressDataManage
//						.getAddressesArray(myApplication.getUserId(), 0, 1);
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
				
			} else {
				if (addArray != null && !addArray.isEmpty()) { // 无默认地址并且无地址
					addresses = addArray.get(0);
				} else {
//					addresssDialog.show();
					Intent intent = new Intent(CstmPayActivity.this,
							EditNewAddressActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("editaddress", null);
					intent.putExtras(bundle);
					CstmPayActivity.this.startActivityForResult(intent, Consts.NEW_ADDRESS_REQUEST);
					return;
				}
			}
			setAdd(addresses);// 设置地址
			// 判断是否免邮
			canFree(sum);

			// 设置快递费用和配送中心
			setKuaiDi(addresses);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(CstmPayActivity.this,
					getResources().getString(R.string.bad_network),
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 设置地址，快递和配送中心
	 * 
	 * @param addresses
	 */
	private void setAdd(Addresses addresses) {
		// Log.i(TAG, "show sum:"+sum);
		 Log.e(TAG, "add address");
		userName.setText(addresses.getName());
		phoneName.setText(addresses.getHandset());// �޸�Ϊ�ֻ�
		StringBuffer sb = new StringBuffer();
		sb.append(addresses.getProvice());
		sb.append(addresses.getCity());
		sb.append(addresses.getArea());
		sb.append(addresses.getAddress());
		address.setText(sb.toString());
		recipientId = addresses.getAddressId();// 地址id
	}
	
	private ArrayList<Express> expressArray ;

	/**
	 * 设置快递费用和配送中心
	 * 
	 * @param addresses
	 */
	private void setKuaiDi(Addresses addresses) {
		Express express;
		expressArray = new ExpressDataManage(CstmPayActivity.this)
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
//		Log.i("info", "setKuaidi:");
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
			peiSongCenter = new ExpressDataManage(CstmPayActivity.this)
			.getDistributionsList(expressArray.get(0).getPreId(), 0 + "", 10 + "").get(0)
			.getDisName(); // 获取配送中心
//			Log.i("info", "setpeisong:" + peiSongCenter);
			peiSong.setText(peiSongCenter);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private float fP;
	private ArrayList<FreePost> freePosts;

	/**
	 * 判断是否免邮
	 * 
	 * @param sum
	 */
	private void canFree(String sum) {
		ExpressDataManage expressDataManage = new ExpressDataManage(
				CstmPayActivity.this);
		freePosts = expressDataManage.getFreePostList("0", "20");
		Log.i(TAG, "show sum:" + sum);
		fP = Float.MAX_VALUE;
		if (freePosts.size() != 0)
			fP = Float.parseFloat(freePosts.get(0).getMax());
//		Log.i("info", fP + "   fp");
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

	/*private Callable<Map<String, String>> call = new Callable<Map<String, String>>() {

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
	};*/

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

//		Log.i("info", requestCode + "   requestCode");
//		Log.i("info", resultCode + "   resultCode");

		
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
				// 判断是否免邮
				canFree(sum);

				// 设置快递费用和配送中心
				setKuaiDi(addresses1);
			}
		} else if (requestCode == Consts.CstmPayActivity_Request
				&& resultCode == Consts.CstmPayActivity_Response) {

			carts = (ArrayList<Cart>) data.getSerializableExtra("carts");
			

			voucher = data.getFloatExtra("voucher", -1);
			Log.i("voucher", jifen + "    jifen");
			jifen = data.getFloatExtra("jifen", -1);
			Log.i("voucher", jifen + "    jifen");
			// Log.i("voucher", voucher + "  voucher");
			// Log.i("voucher", voucher + "  voucher");
			// isCartActivity = data.getStringExtra("cartActivity");
			show(carts, false);
		} else if(requestCode == Consts.NEW_ADDRESS_REQUEST && resultCode==Consts.NEW_ADDRESS_RESPONSE){
			
		
//			show(carts, false);
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
	public class InnerReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Log.i("voucher", action + "    action");
			if (Consts.CST_NEWADDRESS.equals(action)) {
				show(carts, false);
			}
		}
	}

}