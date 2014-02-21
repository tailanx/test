package com.yidejia.app.mall.pay;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.order.AllOrderActivity;
import com.yidejia.app.mall.phone.PhoneOrderActivity;
import com.yidejia.app.mall.util.ActivityIntentUtil;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;

public class AlicPayUtil {

	private ProgressDialog mProgress = null;
	private String alicInfo; // 支付宝客户端支付需要的字符串
	private String TAG = getClass().getName();
	private boolean isMobile = false;	//手机充值订单

	private Activity activity; // 上下文

	public AlicPayUtil(Activity activity) {
		this.activity = activity;
	}

	public void getAlicPay(String userId, String token, String orderCode, boolean isMobile) {
		this.isMobile = isMobile;
		// 检测安全支付服务是否被安装
		MobileSecurePayHelper mspHelper = new MobileSecurePayHelper(activity);
		boolean isAlicExsist = mspHelper.detectMobile_sp();
		if(!isAlicExsist) return;
		String isMobileStr = "";
		if(isMobile) isMobileStr = "y";
		
		String url = new JNICallBack().HTTPURL;
		String param = new JNICallBack().getHttp4AlicSign(userId, token,
				orderCode , isMobileStr);

		HttpClientUtil httpClientUtil = new HttpClientUtil(activity);
//		httpClientUtil.setIsShowLoading(true);
//		httpClientUtil.setShowErrMessage(true);
		httpClientUtil.getHttpResp(url, param, new IHttpResp() {

			@Override
			public void onSuccess(String content) {
				parseAlicPay(content);
			}
		});
	}

	private void parseAlicPay(String content) {
		try {
			JSONObject httpObject = new JSONObject(content);
			int code = httpObject.optInt("code");
			if (1 == code) {
				alicInfo = httpObject.optString("response");
				performPay(alicInfo);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/** alipay支付宝客户端支付 **/
	private void performPay(String info) {

		// 根据订单信息开始进行支付
		try {

			// 调用pay方法进行支付
			MobileSecurePayer msp = new MobileSecurePayer();
			boolean bRet = msp.pay(info, mHandler, AlixId.RQF_PAY, activity);

			if (bRet) {
				// show the progress bar to indicate that we have started
				// paying.
				// 显示“正在支付”进度条
				closeProgress();
				mProgress = BaseHelper.showProgress(activity, null, "正在支付",
						false, true);
			} else
				;
		} catch (Exception ex) {
			Toast.makeText(activity, R.string.remote_call_failed,
					Toast.LENGTH_SHORT).show();
		}
	}

	// 关闭进度框
	private void closeProgress() {
		try {
			if (mProgress != null) {
				mProgress.dismiss();
				mProgress = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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

					// 处理交易结果
					try {
						// 获取交易状态码，具体状态代码请参看文档
						String tradeStatus = "resultStatus={";
						int imemoStart = ret.indexOf("resultStatus=");
						imemoStart += tradeStatus.length();
						int imemoEnd = ret.indexOf("};memo=");
						tradeStatus = ret.substring(imemoStart, imemoEnd);

						if (tradeStatus.equals("9000")) {// 判断交易状态码，只有9000表示交易成功
							BaseHelper.showDialog(activity, "提示", "支付成功!",
									R.drawable.ic_launcher,
									android.R.string.ok, paySuccessListener,
									-1, null);
//							if(!isMobile)
//							ActivityIntentUtil.intentActivityAndFinish(
//									activity, AllOrderActivity.class);
//							else ActivityIntentUtil.intentActivityAndFinish(
//									activity, PhoneOrderActivity.class);
							StatService.onEventDuration(activity, "alicpaysuccess", "alicpaysuccess", 100);
						} else if (tradeStatus.equals("6001")) {
							if(!isMobile)
							ActivityIntentUtil.intentActivityAndFinish(
									activity, AllOrderActivity.class);
							else ActivityIntentUtil.intentActivityAndFinish(
									activity, PhoneOrderActivity.class);
							StatService.onEventDuration(activity, "alicpaycancel", "alicpaycancel", 100);
							return;
						} else if (tradeStatus.equals("6002")) {
							StatService.onEventDuration(activity, "alicpayneterr", "alicpayneterr", 100);
							BaseHelper.showDialog(activity, "提示",
									"网络连接出错!是否重试?", R.drawable.ic_launcher,
									"是", payFailAndOkListener, "否",
									payFailAndCancelListener);
						} else if (tradeStatus.equals("4000")) {
							StatService.onEventDuration(activity, "alicpayerr", "alicpayerr", 100);
							BaseHelper.showDialog(activity, "提示", "支付失败!"
									+ "\r\n是否重试？", R.drawable.ic_launcher, "是",
									payFailAndOkListener, "否",
									payFailAndCancelListener);
						} else {
							StatService.onEventDuration(activity, "alicpayerr", "alicpayerr", 100);
							BaseHelper.showDialog(activity, "提示", "支付失败。交易状态码:"
									+ tradeStatus
									+ "\r\n请截图联系我们！\r\n为了不影响您的使用,建议使用其他支付方式.",
									R.drawable.ic_launcher, "是", null, null,
									null);
						}

					} catch (Exception e) {
						e.printStackTrace();
						// BaseHelper.showDialog(activity, "提示",
						// ret,
						// R.drawable.infoicon);
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

	/** 支付宝支付失败并且继续支付 **/
	private DialogInterface.OnClickListener payFailAndOkListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			performPay(alicInfo);
		}
	};
	/** 支付成功 **/
	private DialogInterface.OnClickListener paySuccessListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// go2AllOrderActivity();
			if(!isMobile)
				ActivityIntentUtil.intentActivityAndFinish(activity,
						AllOrderActivity.class);
				else ActivityIntentUtil.intentActivityAndFinish(
						activity, PhoneOrderActivity.class); 
		}
	};

	/** 支付失败并且取消继续支付 **/
	private DialogInterface.OnClickListener payFailAndCancelListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// go2WaitPayOrder();
			if(!isMobile)
			ActivityIntentUtil.intentActivityAndFinish(activity,
					AllOrderActivity.class);
			else ActivityIntentUtil.intentActivityAndFinish(
					activity, PhoneOrderActivity.class); 
		}
	};
}
