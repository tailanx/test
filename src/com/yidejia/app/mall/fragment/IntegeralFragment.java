package com.yidejia.app.mall.fragment;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.VoucherDataManage;
//import com.yidejia.app.mall.view.IntegeralActivity;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.net.voucher.Voucher;
import com.yidejia.app.mall.widget.YLProgressDialog;

public class IntegeralFragment extends Fragment {
	private int hello;
	private String defaultHello = "default hello";
	private TextView jiFen;
	private VoucherDataManage voucherDataManage;// 积分
	private MyApplication myApplication;
	private WebView webView;
	private View viewIntegeral;// 积分的界面数据
	private View viewCoupons;// 优惠券的界面视图
	private String voucherNum = "";
	private boolean isTimeout = false;
	private boolean isFromPay = false;
	private TaskVoucher taskVoucher;

	// 通过单例模式，构建对象
	public static IntegeralFragment newInstance(int s) {
		IntegeralFragment waitFragment = new IntegeralFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("hello", s);
		waitFragment.setArguments(bundle);
		return waitFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 获取存储的参数
		Bundle args = getArguments();
		hello = (Integer) (args != null ? args.getInt("hello") : defaultHello);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// voucherDataManage = new VoucherDataManage(getActivity());
		myApplication = (MyApplication) getActivity().getApplication();
		viewCoupons = inflater.inflate(R.layout.youhuiquan, null);// 优惠券视图
		viewIntegeral = inflater.inflate(R.layout.coupons, null);// 积分视图
		jiFen = (TextView) viewIntegeral.findViewById(R.id.jiefen);
		Log.i("info", jiFen + "jifen");
		// String ji =
		// voucherDataManage.getUserVoucher(myApplication.getUserId(),
		// myApplication.getToken());
		// if (ji == null || "".equals(ji)) {
		//
		// jiFen.setText(0 + "");
		// } else {
		// jiFen.setText(ji);
		// }
		if (hello == 0) {
			initviewJifen();// 加载界面
			return viewIntegeral;
		} else {
			return viewCoupons;
		}
	}

	/**
	 * 加载积分的界面
	 */
	private void initviewJifen() {
		// voucherDataManage = new VoucherDataManage(getActivity());
		// voucherDataManage.getUserVoucher(myApplication.getUserId(),
		// myApplication.getToken());
		taskVoucher = new TaskVoucher();
		taskVoucher.execute();
		webView = (WebView) viewIntegeral.findViewById(R.id.wb_webView);
		webView.setBackgroundColor(0);
		webView.setBackgroundColor(getResources().getColor(R.color.white));
		webView.loadUrl("http://m.yidejia.com/integral.html");
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (taskVoucher != null
				&& AsyncTask.Status.RUNNING == taskVoucher.getStatus()) {
			taskVoucher.cancel(true);
		}

	}

	private class TaskVoucher extends AsyncTask<Void, Void, Boolean> {

		private ProgressDialog bar;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// bar = new ProgressDialog(activity);
			// bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			// bar.setMessage(activity.getResources().getString(R.string.loading));
			// bar.show();
			bar = (ProgressDialog) new YLProgressDialog(getActivity())
					.createLoadingDialog(getActivity(), null);
			bar.setOnCancelListener(new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
					cancel(true);
				}
			});
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Voucher voucher = new Voucher();
			try {
				String httpResponse;
				try {
					httpResponse = voucher
							.getHttpResponse(myApplication.getUserId(),
									myApplication.getToken());
					try {
						JSONObject httpObject = new JSONObject(httpResponse);
						int code = httpObject.getInt("code");
						if (code == 1) {
							String response = httpObject.getString("response");
							JSONObject resObject = new JSONObject(response);
							voucherNum = resObject.getString("can_use_score");
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
			bar.dismiss();
			if (result && !isFromPay) {
				jiFen = (TextView) viewIntegeral.findViewById(R.id.jiefen);
				if (voucherNum == null || "".equals(voucherNum)) {
					jiFen.setText("0");
				} else {
					jiFen.setText(voucherNum + "");
				}
			} else {
				if (isTimeout) {
					Toast.makeText(
							getActivity(),
							getActivity().getResources().getString(
									R.string.time_out), Toast.LENGTH_SHORT)
							.show();
					isTimeout = false;
				}
			}

		}

	}
}