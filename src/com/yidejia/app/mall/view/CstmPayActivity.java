package com.yidejia.app.mall.view;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
//import com.yidejia.app.mall.UserPayActivity;
import com.yidejia.app.mall.datamanage.AddressDataManage;
import com.yidejia.app.mall.datamanage.ExpressDataManage;
import com.yidejia.app.mall.datamanage.OrderDataManage;
import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.Express;
import com.yidejia.app.mall.model.FreePost;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.Http;
import com.yidejia.app.mall.util.MessageUtil;
import com.yidejia.app.mall.util.PayUtil;

public class CstmPayActivity extends SherlockActivity {
	private TextView userName;// 用户名
	private TextView phoneName;// 电话号码
	private TextView address;// 收货地址
	private TextView peiSong;// 配送地址
	private CheckBox general;// 普通配送
	private CheckBox emsBox;// ems配送
	private TextView generalPrice;// 普通配送价格
	private TextView emsPrice;// ems配送价格
	private AddressDataManage addressDataManage;
	private ExpressDataManage expressDataManage;// 配送中心
	private CheckBox zhifubaoCheckBox;// 支付宝
	private CheckBox zhifubaowangyeCheckBox;// 支付宝网页支付
	private CheckBox yinlianCheckBox;// 银联支付
	private CheckBox caifutongCheckBox;// 财付通支付
	private TextView sumPrice;// 总的价格
	private Button saveOrderBtn;//提交订单
	private Handler mHandler;// 创建handler对象
	private MyApplication myApplication;
	private EditText comment;//评论
	
	
	// private Myreceiver receiver;
	// private Addresses addresses;
	private String peiSongCenter = "";////配送中心
	private String recipientId = "";//收集人id
	private String expressNum = "";//快递费用
	private String postMethod = "EMS";//快递方式
	private String goods;//商品串
	
	
	private static final String SERVER_URL = "http://202.104.148.76/splugin/interface";
	private static final String TRADE_COMMAND = "1001";
	private final String TAG = getClass().getName();

	/**
	 * 实例化控件
	 */
	public void setupShow() {
		try {
			expressDataManage = new ExpressDataManage(this);
			
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

			zhifubaoCheckBox = (CheckBox) findViewById(R.id.zhifubao_checkbox);
			zhifubaowangyeCheckBox = (CheckBox) findViewById(R.id.zhufubaowangye_checkbox);
			yinlianCheckBox = (CheckBox) findViewById(R.id.yinlian_checkbox);
			caifutongCheckBox = (CheckBox) findViewById(R.id.caifutong_checkbox);
			
			//快递
			general.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if (!isChecked) {
						emsBox.setChecked(true);
						;
					} else {
						emsBox.setChecked(false);
						Message ms = new Message();
						mHandler.sendEmptyMessage(Consts.GENERAL);
					}

				}
			});
			//ems
			emsBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					// TODO Auto-generated method stub
					if (!isChecked) {
						general.setChecked(true);
						;

					} else {
						general.setChecked(false);
						mHandler.sendEmptyMessage(Consts.EMS);
					}
				}
			});

			zhifubaoCheckBox
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton arg0,
								boolean isChecked) {
							// TODO Auto-generated method stub
							if (isChecked) {
								zhifubaowangyeCheckBox.setChecked(false);
								yinlianCheckBox.setChecked(false);
								caifutongCheckBox.setChecked(false);
							}
						}
					});
			zhifubaowangyeCheckBox
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton arg0,
								boolean isChecked) {
							// TODO Auto-generated method stub
							if (isChecked) {
								zhifubaoCheckBox.setChecked(false);
								yinlianCheckBox.setChecked(false);
								caifutongCheckBox.setChecked(false);
							}
						}
					});

			yinlianCheckBox
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton arg0,
								boolean isChecked) {
							// TODO Auto-generated method stub
							if (isChecked) {
								zhifubaoCheckBox.setChecked(false);
								zhifubaowangyeCheckBox.setChecked(false);
								caifutongCheckBox.setChecked(false);
							}
						}
					});
			caifutongCheckBox
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton arg0,
								boolean isChecked) {
							// TODO Auto-generated method stub
							if (isChecked) {
								zhifubaoCheckBox.setChecked(false);
								zhifubaowangyeCheckBox.setChecked(false);
								yinlianCheckBox.setChecked(false);
							}
						}
					});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(CstmPayActivity.this, getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT)
					.show();
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			// TODO Auto-generated method stub
			myApplication = (MyApplication) getApplication();
			// receiver = new Myreceiver();
			// IntentFilter filter = new IntentFilter();
			// filter.addAction(Consts.BUY_NEW);
			// registerReceiver(receiver, filter);
			// 付款之前先判断用户是否登陆
			if (!myApplication.getIsLogin()) {
				Toast.makeText(this, getResources().getString(R.string.please_login), Toast.LENGTH_LONG).show();
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
				this.finish();
			} else {
				addressDataManage = new AddressDataManage(this);
				Intent intent = getIntent();
				final String sum = intent.getStringExtra("price");
				Cart cart = (Cart) intent.getSerializableExtra("Cart");
				if (cart == null) {

					setContentView(R.layout.go_pay);
					setupShow();
					addAddress();
					setActionbar();
					LinearLayout layout = (LinearLayout) findViewById(R.id.go_pay_relative2);
					PayUtil pay = new PayUtil(CstmPayActivity.this, layout);
					goods = pay.loadView();
					//获取免邮界限
					ExpressDataManage expressDataManage = new ExpressDataManage(CstmPayActivity.this);
					ArrayList<FreePost> freePosts = expressDataManage.getFreePostList("0", "20");
					float fP = Float.MAX_VALUE;
					if(freePosts.size() != 0) fP = Float.parseFloat(freePosts.get(0).getMax());
					if (Float.parseFloat(sum) > fP) {
						sumPrice.setText(sum);
						expressNum = "0";
					} else {
						sumPrice.setText(Double.parseDouble(sum)
								+ Double.parseDouble((general.isChecked() ? generalPrice
										.getText().toString() : emsPrice
										.getText().toString())) + "");
					}
					mHandler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
							case Consts.GENERAL:
								expressNum = generalPrice.getText().toString();
								sumPrice.setText(Double.parseDouble(sum)
										+ Double.parseDouble(expressNum) + "");
								postMethod = getResources().getString(R.string.ship_post);//快递方式
								break;
							case Consts.EMS:
								expressNum = emsPrice.getText().toString();
								sumPrice.setText(Double.parseDouble(sum)
										+ Double.parseDouble(expressNum) + "");
								postMethod = "EMS";
								break;
							}
							super.handleMessage(msg);
						}
					};
				} else {
					setActionbar();
					setContentView(R.layout.go_pay);
					LinearLayout layout = (LinearLayout) findViewById(R.id.go_pay_relative2);
					PayUtil pay = new PayUtil(CstmPayActivity.this, layout, cart);
					goods = pay.cartLoadView();
					setupShow();
					addAddress();
					//获取免邮界限
					ExpressDataManage expressDataManage = new ExpressDataManage(CstmPayActivity.this);
					ArrayList<FreePost> freePosts = expressDataManage.getFreePostList("0", "20");
					float fP = Float.MAX_VALUE;
					if(freePosts.size() != 0) fP = Float.parseFloat(freePosts.get(0).getMax());
					
					if (Float.parseFloat(sum) > fP) {
						sumPrice.setText(sum);
					} else {
						sumPrice.setText(Double.parseDouble(sum)
								+ Double.parseDouble((general.isChecked() ? generalPrice
										.getText().toString() : emsPrice
										.getText().toString())) + "");
					}
					mHandler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
							case Consts.GENERAL:
								expressNum = generalPrice.getText().toString();
								sumPrice.setText(Double.parseDouble(sum)
										+ Double.parseDouble(expressNum) + "");
								postMethod = getResources().getString(R.string.ship_post);//快递方式
								break;
							case Consts.EMS:
								expressNum = emsPrice.getText()
										.toString();
								sumPrice.setText(Double.parseDouble(sum)
										+ Double.parseDouble(expressNum) + "");
								postMethod = "EMS";
								break;
							}
							super.handleMessage(msg);
						}
					};
				}
				
				saveOrderBtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						OrderDataManage orderDataManage = new OrderDataManage(CstmPayActivity.this);
						orderDataManage.saveOrder(myApplication.getUserId(),
								"0", recipientId, "", "0", expressNum,
								"EMS", peiSongCenter, goods, "",
								myApplication.getToken());
						String orderCode = orderDataManage.getOrderCode();
						Log.e("OrderCode", orderCode);
//						Intent userpayintent = new Intent(CstmPayActivity.this, UserPayActivity.class);
//						Bundle bundle = new Bundle();
//						bundle.putString("name", "hello");
//						bundle.putString("amount", "100");
//						userpayintent.putExtras(bundle);
//						startActivity(userpayintent);
//						CstmPayActivity.this.finish();
						
						//测试提交订单
//						FutureTask<Map<String, String>> task = new FutureTask<Map<String, String>>(call);
//						Thread th = new Thread(task);
//						th.start();
//						Map<String, String> resp;
//						try {
//							resp = task.get();
//							Log.d(TAG, "task status: " + task.isDone());
//							
//							if (null != resp && null != resp.get("code") && "0000".equals(resp.get("code"))) {
//								String tn = resp.get("tn");
//								UPPayAssistEx.startPayByJAR(CstmPayActivity.this,
//										PayActivity.class, null, null, tn, "01");
//								
//							} else {
////								UPPayAssistEx.startPayByJAR(CstmPayActivity.this,
////										PayActivity.class, null, null, "121364646464646", "01");
//							}
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (ExecutionException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
					}
				});
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(CstmPayActivity.this, getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT)
					.show();
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
			Toast.makeText(CstmPayActivity.this, getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT)
					.show();
		}
	}

	/**
	 * 初始化控件
	 */
	private void addAddress() {
		try {
			ArrayList<Addresses> mList = addressDataManage.getAddressesArray(

					myApplication.getUserId(), 0, 10);
			Log.i("info", mList.size() + " mlist");
			if (mList.size() == 0) {
				Intent intent = new Intent(CstmPayActivity.this,
						NewAddressActivity.class);
				CstmPayActivity.this.startActivity(intent);
				
			} else {
				Addresses addresses = mList.get(0);
				//获取默认地址
				int size = mList.size();
				for (int i = 0; i < size; i++) {
					boolean isDefault = mList.get(i).getDefaultAddress();
					if(isDefault){
						addresses = mList.get(i);
						break;
					}
				}
				userName.setText(addresses.getName());
				phoneName.setText(addresses.getHandset());//�޸�Ϊ�ֻ�
				StringBuffer sb = new StringBuffer();
				sb.append(addresses.getProvice());
				sb.append(addresses.getCity());
				sb.append(addresses.getArea());
				sb.append(addresses.getAddress());
				address.setText(sb.toString());
				recipientId = addresses.getAddressId();

				Express express = expressDataManage.getExpressesExpenses(
						addresses.getProvice(), "y").get(0);
				peiSongCenter = expressDataManage
						.getDistributionsList(express.getPreId(), 0 + "", 10 + "").get(0)
						.getDisName(); //获取配送中心
				peiSong.setText(peiSongCenter);
				generalPrice.setText(express.getExpress());
				emsPrice.setText(express.getEms());
				Log.i("info", addresses.getName());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(CstmPayActivity.this, getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT)
					.show();
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


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
		/************************************************* 
         * 
         *  步骤3：处理银联手机支付控件返回的支付结果 
         *  
         ************************************************/
        if (data == null) {
            return;
        }

        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel
         *      分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            msg = "支付成功！";
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("支付结果通知");
        builder.setMessage(msg);
        builder.setInverseBackgroundForced(true);
        //builder.setCustomTitle();
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
	}
	
}
