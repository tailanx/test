package com.yidejia.app.mall.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;
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
import com.yidejia.app.mall.util.DefinalDate;
import com.yidejia.app.mall.util.Http;
import com.yidejia.app.mall.util.MessageUtil;
import com.yidejia.app.mall.util.PayUtil;

public class CstmPayActivity extends SherlockActivity {
//	private InnerReceiver receiver;
	private TextView userName;// 鐢ㄦ埛鍚?
	private TextView phoneName;// 鐢佃瘽鍙风爜
	private TextView address;// 鏀惰揣鍦板潃
	private TextView peiSong;// 閰嶉€佸湴鍧€
	private CheckBox general;// 鏅€氶厤閫?
	private CheckBox emsBox;// ems閰嶉€?
	private TextView generalPrice;// 鏅€氶厤閫佷环鏍?
	private TextView emsPrice;// ems閰嶉€佷环鏍?
	private AddressDataManage addressDataManage;
	// private ExpressDataManage expressDataManage;// 閰嶉€佷腑蹇?
	private CheckBox zhifubaoCheckBox;// 鏀粯瀹?
	private CheckBox zhifubaowangyeCheckBox;// 鏀粯瀹濈綉椤垫敮浠?
	private CheckBox yinlianCheckBox;// 閾惰仈鏀粯
	private CheckBox caifutongCheckBox;// 璐粯閫氭敮浠?
	private TextView sumPrice;// 鎬荤殑浠锋牸
	private Button saveOrderBtn;// 鎻愪氦璁㈠崟
	private Handler mHandler;// 鍒涘缓handler瀵硅薄
	private MyApplication myApplication;
	private EditText comment;// 璇勮
	private AlertDialog dialog;
	private RelativeLayout addressRelative;
	private PreferentialDataManage preferentialDataManage;//
	public static ArrayList<Specials> arrayListFree;
	public static ArrayList<Specials> arrayListExchange;

	// private EditText comment;//璇勮
	private ScrollView go_pay_scrollView;
	private VoucherDataManage voucherDataManage;

	// private Myreceiver receiver;
	// private Addresses addresses;
	private String peiSongCenter = "";// //閰嶉€佷腑蹇?
	private String recipientId = "";// 鏀堕泦浜篿d
	private String expressNum = "";// 蹇€掕垂鐢?
	private String postMethod = "EMS";// 蹇€掓柟寮?
	private boolean isFree = false;// 鏄惁婊¤冻鍏嶉偖鏉′欢
	private String goods;// 鍟嗗搧涓?
	private String orderCode;// 璁㈠崟鍙?
	private String resp_code;// 杩斿洖鐘舵€佺爜
	private String tn;// 娴佹按鍙?
	private LinearLayout layout;
	private ArrayList<Cart> carts;// 获取传递过来的数据

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
	 * 瀹炰緥鍖栨帶浠?
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
			// // 蹇€?
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

	private RelativeLayout reLayout;
	private String isCartActivity;
	private String sum;
	private PayUtil pay;
	private double  maxPay;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			Intent intent = getIntent();
			// final String sum = intent.getStringExtra("price");
			sum = intent.getStringExtra("price");
			maxPay = Double.parseDouble(sum);
			carts = (ArrayList<Cart>) intent.getSerializableExtra("carts");
			Log.i(TAG, "sum:" + sum);
			isCartActivity = intent.getStringExtra("cartActivity");
			myApplication = (MyApplication) getApplication();
			voucherDataManage = new VoucherDataManage(CstmPayActivity.this);

			voucher = Double.parseDouble(voucherDataManage.getUserVoucher(
					myApplication.getUserId(), myApplication.getToken()));
			// TODO Auto-generated method stub
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
										voucherDataManage = new VoucherDataManage(
												CstmPayActivity.this);

										voucher = Double.parseDouble(voucherDataManage
												.getUserVoucher(myApplication
														.getUserId(),
														myApplication
																.getToken()));

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
			// 浠樻涔嬪墠鍏堝垽鏂敤鎴锋槸鍚︾櫥闄?
			if (!myApplication.getIsLogin()) {
				Toast.makeText(this,
						getResources().getString(R.string.please_login),
						Toast.LENGTH_LONG).show();
				Intent intent1 = new Intent(this, LoginActivity.class);
				startActivity(intent1);
				this.finish();
			} else {

				// Cart cart = (Cart) intent.getSerializableExtra("Cart");

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
					sb.append("y");
					sb.append(";");
				}
				preferentialDataManage.getPreferential(sb.toString(),
						myApplication.getUserId());
				if (preferentialDataManage.getFreeGoods().size() != 0
						|| preferentialDataManage.getScoreGoods().size() != 0) {

					arrayListFree = preferentialDataManage.getFreeGoods();
					arrayListExchange = preferentialDataManage.getScoreGoods();
				}
				if (isCartActivity.equals("Y") || isCartActivity.equals("N")) {// ||isCartActivity.equals("N")
					if(voucher > 0||maxPay>299){
						Log.i("info", voucher+"   voucher");
						Log.i("info", maxPay+"   maxPay");
					dialog.show();
					show(carts, false);
					}else{
						Log.i("info", voucher+"   voucher");
						Log.i("info", maxPay+"   maxPay");
						show(carts, false);
					}
				}
				// } else {
				// // if (!carts.isEmpty()) {
				// show( carts,false);
				// }
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
		// 娉ㄥ唽杩斿洖鏃剁殑骞挎挱
//		receiver = new InnerReceiver();
//		IntentFilter filter = new IntentFilter();
//		filter.addAction(Consts.BACK_UPDATE_CHANGE);
//		CstmPayActivity.this.registerReceiver(receiver, filter);

		addressDataManage = new AddressDataManage(this);
		go_pay_scrollView = (ScrollView) findViewById(R.id.go_pay_scrollView);
		setContentView(R.layout.go_pay);
		setupShow();

		setActionbar();

		layout = (LinearLayout) findViewById(R.id.go_pay_relative2);
		pay = new PayUtil(CstmPayActivity.this, layout);

		goods = pay.loadView(carts, false);

//		RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.go_shopping_use_evalution);
//		relativeLayout.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(CstmPayActivity.this,
//						ExchangeFreeActivity.class);
//				intent.putExtra("voucher", voucher);
//				intent.putExtra("cartActivity", isCartActivity);
//				Log.i("info", voucher + "   voucher");
//				intent.putExtra("price", sum + "");
//				intent.putExtra("carts", carts);
//				CstmPayActivity.this.startActivityForResult(intent, Consts.CstmPayActivity_Request);
//			}
//		});
		reLayout = (RelativeLayout) findViewById(R.id.go_pay_relative);
		addressRelative = (RelativeLayout) findViewById(R.id.go_pay_relativelayout);
		// 鍦板潃澧炲姞鐩戝惉浜嬩欢
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
		
		// 娣诲姞鍦板潃銆佸揩閫掕垂鐢ㄣ€侀厤閫佷腑蹇?
		addAddress();
		// // 鑾峰彇鍏嶉偖鐣岄檺
		// ExpressDataManage expressDataManage = new ExpressDataManage(
		// CstmPayActivity.this);
		// ArrayList<FreePost> freePosts = expressDataManage
		// .getFreePostList("0", "20");
//		 float fP = Float.MAX_VALUE;
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
					postMethod = getResources().getString(R.string.ship_post);// 蹇€掓柟寮?
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
		// * 鑾峰彇鍏嶉偖鐣岄檺 ExpressDataManage expressDataManage = new
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
		// * getResources().getString( R.string.ship_post);// 蹇€掓柟寮?
		// * break; case Consts.EMS: expressNum =
		// * emsPrice.getText().toString();
		// * sumPrice.setText(Double.parseDouble(sum) +
		// * Double.parseDouble(expressNum) + ""); postMethod =
		// "EMS";
		// * break; } super.handleMessage(msg); } };
		// */
		// }
		// 鎻愪氦璁㈠崟
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
				} else {
				}
				OrderDataManage orderDataManage = new OrderDataManage(
						CstmPayActivity.this);
				orderDataManage.saveOrder(myApplication.getUserId(), "0",
						recipientId, "", "0", expressNum, postMethod,
						peiSongCenter, goods, comment.getText().toString(),// 杩欓噷鐨刢omment.getText().toString()寰堥噸瑕?
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

				// //娴嬭瘯鎻愪氦璁㈠崟
				// FutureTask<Map<String, String>> task = new
				// FutureTask<Map<String, String>>(call);
				// Thread th = new Thread(task);
				// th.start();
				// Map<String, String> resp;
				// try {
				// resp = task.get();
				// Log.d(TAG, "task status: " + task.isDone());
				//
				// if (null != resp && null != resp.get("code") &&
				// "0000".equals(resp.get("code"))) {
				// String tn = resp.get("tn");
				// UPPayAssistEx.startPayByJAR(CstmPayActivity.this,
				// PayActivity.class, null, null, tn, "01");
				//
				// } else {
				// //
				// UPPayAssistEx.startPayByJAR(CstmPayActivity.this,
				// // PayActivity.class, null, null,
				// "121364646464646",
				// "01");
				// }
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// } catch (ExecutionException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
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
	 * UI鎺т欢鐨勯《閮?
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
	 * 鍦板潃鍜屽揩閫掕垂鐢紝閰嶉€佷腑蹇?
	 */
	private void addAddress() {
		Log.i(TAG, "show sum:" + sum);
		try {
			// ArrayList<Addresses> mList1 =
			// addressDataManage.getAddressesArray(
			//
			// myApplication.getUserId(), 0, 10);
			ArrayList<Addresses> mList = addressDataManage
					.getDefAddresses(myApplication.getUserId());
			Addresses addresses;
			Log.i("info", mList.size() + " mlist");
			if (mList.size() == 0) {
				ArrayList<Addresses> addArray = addressDataManage
						.getAddressesArray(myApplication.getUserId(), 0, 10);
				if (addArray.isEmpty()) { // 鏃犻粯璁ゅ湴鍧€骞朵笖鏃犲湴鍧€
					Intent intent = new Intent(CstmPayActivity.this,
							NewAddressActivity.class);
					CstmPayActivity.this.startActivity(intent);
					return;
				} else {
					addresses = addArray.get(0);
				}
			} else {
				addresses = mList.get(0);
				// 鑾峰彇榛樿鍦板潃
				int size = mList.size();
				for (int i = 0; size > 0 && i < size; i++) {
					boolean isDefault = mList.get(i).getDefaultAddress();
					if (isDefault) {
						addresses = mList.get(i);
						break;
					}
				}
			}
			setAdd(addresses);// 璁剧疆鍦板潃

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(CstmPayActivity.this,
					getResources().getString(R.string.bad_network),
					Toast.LENGTH_SHORT).show();
		}
		// userName.setText(addresses.getName());
		// phoneName.setText(addresses.getHandset());// 锟睫革拷为锟街伙拷
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
		// 10 + "").get(0).getDisName(); // 鑾峰彇閰嶉€佷腑蹇?
		// peiSong.setText(peiSongCenter);
		// generalPrice.setText(express.getExpress());
		// emsPrice.setText(express.getEms());
		// Log.i("info", addresses.getName());
		// postMethod = getResources().getString(R.string.ship_post);//
		// 鍒濆鍖栧揩閫掓柟寮?
		// expressNum = express.getExpress();// 鍒濆鍖栬垂鐢?
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
	 * 璁剧疆鍦板潃锛屽揩閫掑拰閰嶉€佷腑蹇?
	 * 
	 * @param addresses
	 */
	private void setAdd(Addresses addresses) {
		Log.i(TAG, "show sum:" + sum);
		Log.i(TAG, "add address");
		userName.setText(addresses.getName());
		phoneName.setText(addresses.getHandset());// 锟睫革拷为锟街伙拷
		StringBuffer sb = new StringBuffer();
		sb.append(addresses.getProvice());
		sb.append(addresses.getCity());
		sb.append(addresses.getArea());
		sb.append(addresses.getAddress());
		address.setText(sb.toString());
		recipientId = addresses.getAddressId();// 鍦板潃id
		// 鍒ゆ柇鏄惁鍏嶉偖
		canFree(sum);

		// 璁剧疆蹇€掕垂鐢ㄥ拰閰嶉€佷腑蹇?
		setKuaiDi(addresses);
	}

	/**
	 * 璁剧疆蹇€掕垂鐢ㄥ拰閰嶉€佷腑蹇?
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
		// 璁剧疆閰嶉€佷腑蹇?
		setPeiSong(express.getPreId());
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
		postMethod = getResources().getString(R.string.ship_post);// 鍒濆鍖栧揩閫掓柟寮?
		expressNum = express.getExpress();// 鍒濆鍖栬垂鐢?
	}

	/**
	 * 鏍规嵁閰嶉€佷腑蹇冪殑id鑾峰彇閰嶉€佷腑蹇?
	 * 
	 * @param preId
	 */
	private void setPeiSong(String preId) {
		try {
			peiSongCenter = new ExpressDataManage(this)
					.getDistributionsList(preId, 0 + "", 10 + "").get(0)
					.getDisName(); // 鑾峰彇閰嶉€佷腑蹇?
			Log.i("info", "setpeisong:" + peiSongCenter);
			peiSong.setText(peiSongCenter);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 鍒ゆ柇鏄惁鍏嶉偖
	 * 
	 * @param sum
	 */
	private float fP ;
	private void canFree(String sum) {
		// 鑾峰彇鍏嶉偖鐣岄檺
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
			if (Float.parseFloat(sum) >= fP) {// 澶т簬绛変簬鍏嶉偖璐圭敤

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

//	@Override
//	protected void onDestroy() {
//		// TODO Auto-generated method stub
//		super.onDestroy();
//		unregisterReceiver(receiver);
//	}

	private ArrayList<Cart> carts1;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// super.onActivityResult(requestCode, resultCode, data);
		/*************************************************
		 * 
		 * 姝ラ3锛氬鐞嗛摱鑱旀墜鏈烘敮浠樻帶浠惰繑鍥炵殑鏀粯缁撴灉
		 * 
		 ************************************************/

		Log.i("info", requestCode + "requestCode");

		Log.i("info", resultCode + "resultCode");
		if (data == null) {
			return;
		}
		if (requestCode == Consts.AddressRequestCode
				&& resultCode == Consts.AddressResponseCode) {
			Addresses addresses1 = (Addresses) data.getExtras()
					.getSerializable("addresses1");
			Log.i("info", addresses1.getAddress() + "str");
			if (addresses1 != null) {
				// reLayout.removeView(addressRelative);
				// userName.setText(addresses1.getName());
				// phoneName.setText(addresses1.getHandset());// 锟睫革拷为锟街伙拷
				// StringBuffer sb = new StringBuffer();
				// sb.append(addresses1.getProvice());
				// sb.append(addresses1.getCity());
				// sb.append(addresses1.getArea());
				// sb.append(addresses1.getAddress());
				// address.setText(sb.toString());
				setAdd(addresses1);
			}

			} else if (requestCode == Consts.CstmPayActivity_Request
					&& resultCode == Consts.CstmPayActivity_Response) {

				carts = (ArrayList<Cart>) data.getSerializableExtra("carts");
				voucher = data.getDoubleExtra("voucher", -1);
				Log.i("voucher", voucher + "  voucher");
				Log.i("voucher", voucher + "  voucher");
				isCartActivity = data.getStringExtra("cartActivity");
//				layout.removeAllViews();

//				 goods = pay.loadView(carts, false);
				// goods2 = pay.loadView(carts1, true);

				// goods += goods2;
				// carts.addAll(carts1);
				 show(carts,false);
		}
	}

	private double voucher;

//	public class InnerReceiver extends BroadcastReceiver {
//
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			// TODO Auto-generated method stub
//			String action = intent.getAction();
//			if (Consts.BACK_UPDATE_CHANGE.equals(action)) {
//				// Log.i("info", action + "action");
//				layout.removeAllViews();
//				voucher = intent.getIntExtra("voucher", -1);
//				ArrayList<Cart> carts = (ArrayList<Cart>) intent
//						.getSerializableExtra("carts");
//				// ArrayList<Cart> carts = ExchangeFreeActivity.mArrayList;
//				Log.i("voucher", carts + "  voucher");
//				PayUtil pay = new PayUtil(CstmPayActivity.this, layout);
//				goods = pay.loadView(carts, true);
//			}
//		}
//	}
}
