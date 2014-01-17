package com.yidejia.app.mall.view;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.tenpay.android.service.TenpayServiceHelper;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.R.layout;
import com.yidejia.app.mall.R.string;
import com.yidejia.app.mall.order.AllOrderActivity;
import com.yidejia.app.mall.order.WaitPayActivity;
import com.yidejia.app.mall.util.Http;
import com.yidejia.app.mall.util.MessageUtil;

public class UserPayActivity extends Activity{
	private static final String SERVER_URL = "http://202.104.148.76/splugin/interface";
	private static final String TRADE_COMMAND = "1001";
	private final String TAG = getClass().getName();
	
	private String code;//我们服务器的订单号;
	private String uid;//客户id;
	//银联需要接口或数据
	private String goodsName;//商品描述
	private String goodsAmt;//价格
	private String respCode;
	private String upayTn;
	//财付通需要接口或数据
	private String mTokenId;//财付通支付订单号
	final static int MSG_PAY_RESULT = 1990;//返回接口
	
	private int payMode;
	private String payUrl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent getIntent = getIntent();
		Bundle getBundle = getIntent.getExtras();
		payMode = getBundle.getInt("mode");
		if(payMode == 1){//银联支付
//			goodsName = getBundle.getString("name");
//			goodsAmt = getBundle.getString("amount");
			respCode = getBundle.getString("resp_code");
			upayTn = getBundle.getString("tn");
			code = getBundle.getString("code");
			uid = getBundle.getString("uid");
			setContentView(R.layout.activity_userpay);
			UnionPayOrder();
//			UPPay();
//			new OrderCode().execute();
		} else if(payMode == 0){//财付通支付
			code = getBundle.getString("code");
			payUrl = getBundle.getString("payurl");
			TenPay();
		} else if(payMode == 3) {
			code = getBundle.getString("code");
			payUrl = getBundle.getString("payurl");
			aliwapPay();
		}
		
	}
	
//	private class OrderCode extends AsyncTask<Void, Void, Boolean>{
//
//		@Override
//		protected Boolean doInBackground(Void... params) {
//			// TODO Auto-generated method stub
//			GetOrderCode getOrderCode = new GetOrderCode(UserPayActivity.this);
//			try {
//				String httpresponse = getOrderCode.getTNCode(code, uid);
//				int httpcode;
//				try {
//					JSONObject httpObject = new JSONObject(httpresponse);
//					httpcode = httpObject.getInt("code");
//					if(httpcode == 1){
//						String response = httpObject.getString("response");
//						try {
//							JSONObject itemObject = new JSONObject(response);
//							respCode = itemObject.getString("respCode");
//							upayTn = itemObject.getString("tn");
//						} catch (Exception e) {
//							// TODO: handle exception
//						}
//						return true;
//					}
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return false;
//		}
//
//		@Override
//		protected void onPostExecute(Boolean result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//			if(result){
//				if (null != respCode && "00".equals(respCode) && null != upayTn) {
//					
//					UPPayAssistEx.startPayByJAR(UserPayActivity.this,
//							PayActivity.class, null, null, upayTn, "01");
//					
//				} else {
//					
//				}
//			}
//		}
//		
//	}

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
        
        boolean isSuccess = false;
        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel
         *      分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
        	StatService.onEventDuration(UserPayActivity.this, "pay success", "pay success", 100);
            msg = "支付成功！";
            isSuccess = true;
        } else if (str.equalsIgnoreCase("fail")) {
        	StatService.onEventDuration(UserPayActivity.this, "pay fail", "pay fail", 100);
            msg = "支付失败，是否重试？";
            isSuccess = false;
        } else if (str.equalsIgnoreCase("cancel")) {
        	StatService.onEventDuration(UserPayActivity.this, "pay cancel", "pay cancel", 100);
            msg = "支付失败，是否重试？";
            isSuccess = false;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage(msg);
        builder.setInverseBackgroundForced(true);
        //builder.setCustomTitle();
		if (isSuccess) {//支付成功
			builder.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						Intent intent = new Intent(UserPayActivity.this, AllOrderActivity.class);
	                    startActivity(intent);
						UserPayActivity.this.finish();
				}
			});
		} else {
			builder.setPositiveButton("是",
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					StatService.onEventDuration(UserPayActivity.this, "pay again", "pay again", 100);
//					UserPayActivity.this.finish();
					if(payMode == 1){
						UnionPayOrder();
					}
				}
			});
        	builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intent = new Intent(UserPayActivity.this, WaitPayActivity.class);
                    startActivity(intent);
                    UserPayActivity.this.finish();
                }
            });
        }
        builder.create().show();
	}
	
	private void UnionPayOrder(){
		if (null != respCode && "00".equals(respCode) && null != upayTn && !"".equals(upayTn)) {
			
			UPPayAssistEx.startPayByJAR(UserPayActivity.this,
					PayActivity.class, null, null, upayTn, "00");
			
		} else {
			Toast.makeText(UserPayActivity.this, "", Toast.LENGTH_LONG).show();
			UserPayActivity.this.finish();
		}
	}
	
	//银联支付的callable
	private Callable<Map<String, String>> call = new Callable<Map<String, String>>() {
		
		@Override
		public Map<String, String> call() throws Exception {
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("orderDesc", goodsName);
			params.put("orderAmt", goodsAmt);
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
	
	/**
	//银联支付
	private void UPPay(){

		FutureTask<Map<String, String>> task = new FutureTask<Map<String, String>>(call);
		Thread th = new Thread(task);
		th.start();
		Map<String, String> resp;
		try {
			resp = task.get();
			Log.d(TAG, "task status: " + task.isDone());
			
			if (null != resp && null != resp.get("code") && "0000".equals(resp.get("code"))) {
				String tn = resp.get("tn");
				UPPayAssistEx.startPayByJAR(this,
						PayActivity.class, null, null, tn, "00");
				
			} else {
				
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	*/
	
	// 接收财付通支付返回值的Handler
	protected Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_PAY_RESULT:
				String strRet = (String) msg.obj; // 支付返回值

				String statusCode = null;
				String info = null;
				String result = null;

				JSONObject jo;
				try {
					jo = new JSONObject(strRet);
					if (jo != null) {
						statusCode = jo.getString("statusCode");
						info = jo.getString("info");
						result = jo.getString("result");
					}
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

				String ret = "statusCode = " + statusCode + ", info = " + info
						+ ", result = " + result;

				// 按协议文档，解析并判断返回值，从而显示自定义的支付结果界面

				new AlertDialog.Builder(UserPayActivity.this)
						.setTitle("支付结果")
						.setMessage(ret)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {

									}
								}).setCancelable(false).create().show();

				break;
			}
		}
	};

	// 财付通支付
	private void TenPay(){
		//先得到财付通订单号
		WebView webView = new WebView(this);
		setContentView(webView);
		webView.loadUrl(payUrl);
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
			};

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

		});
	}
	
	// 财付通支付
	private void aliwapPay(){
		//先得到财付通订单号
		WebView webView = new WebView(this);
		setContentView(webView);
		webView.loadUrl(payUrl);
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
			};

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

		});
	}
	
	protected String getTokenID() {
		// 注意：按协议文档要求，商户应用可前端直接或通过自己后台与财付通后台支付初始化cgi接口通信得到财付通订单token_id
		// .....

		// 此固定串仅为演示示例,是一个假订单号，支付会出错，商户需要通过上述操作获得正确的订单号
		return "41aaa2651c9477544cbe6bfe41ddd1bd".toString();

	}
}
