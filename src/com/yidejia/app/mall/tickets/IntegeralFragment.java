package com.yidejia.app.mall.tickets;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.opens.asyncokhttpclient.AsyncHttpResponse;
import com.opens.asyncokhttpclient.AsyncOkHttpClient;
import com.opens.asyncokhttpclient.RequestParams;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.adapter.IntegerAdapter;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;
import com.yidejia.app.mall.util.TimeUtil;
import com.yidejia.app.mall.widget.YLProgressDialog;

public class IntegeralFragment extends Fragment {
	private int hello;
	private int defaultHello = -1;
	private TextView jiFen;
	private MyApplication myApplication;
	private WebView webView;
	private View viewIntegeral;// 积分的界面数据
	private View viewCoupons;// 优惠券的界面视图
	private String voucherNum = "";
	private ProgressDialog bar;// 加载框
	private ListView listview;

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
		super.onCreate(savedInstanceState);
		// 获取存储的参数
		Bundle args = getArguments();
		hello = (Integer) (args != null ? args.getInt("hello") : defaultHello);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		myApplication = (MyApplication) getActivity().getApplication();
		if (hello == 0) {
			viewIntegeral = inflater.inflate(R.layout.coupons, null);// 积分视图
			initviewJifen();// 加载界面
			return viewIntegeral;
		} else {
			viewCoupons = inflater.inflate(R.layout.youhuiquan, null);// 优惠券视图
			listview = (ListView) viewCoupons
					.findViewById(R.id.youhuiquan_listview);
			getTicket();

			return viewCoupons;
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	/**
	 * 加载积分的界面
	 */
	private void initviewJifen() {
		getInteger();
		webView = (WebView) viewIntegeral.findViewById(R.id.wb_webView);
		webView.setBackgroundColor(0);
		webView.setBackgroundColor(getResources().getColor(R.color.white));
		webView.loadUrl("http://m.yidejia.com/integral.html");
	}

	/** 获取优惠券 **/
	private void getTicket() {
		String url = new JNICallBack().getHttp4GetTicket(
				myApplication.getUserId(), myApplication.getToken());

		HttpClientUtil httpClientUtil = new HttpClientUtil();
		httpClientUtil.getHttpResp(url, new IHttpResp() {

			@Override
			public void success(String content) {
				ParseTickets parseTickets = new ParseTickets();
				boolean isSuccess = parseTickets.parseTickets(content);
				if (isSuccess) {
					ArrayList<Ticket> tickets = parseTickets.getTickets();
					if (null != tickets) {
						IntegerAdapter adapter = new IntegerAdapter(
								getActivity(), tickets);
						listview.setAdapter(adapter);
					}
				}
			}
		});
	}

	/**
	 * 获取用户的积分
	 * 
	 */
	private void getInteger() {
		String param = new JNICallBack().getHttp4GetVoucher(
				myApplication.getUserId(), myApplication.getToken());
		String url = new JNICallBack().HTTPURL;

		RequestParams requestParams = new RequestParams();
		requestParams.put(param);

		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.post(url, requestParams, new AsyncHttpResponse() {
			@Override
			public void onError(Throwable error, String content) {
				super.onError(error, content);
				Toast.makeText(getActivity(),
						getResources().getString(R.string.bad_network),
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFinish() {
				super.onFinish();
			}

			@SuppressWarnings("static-access")
			@Override
			public void onStart() {
				super.onStart();
				bar = new YLProgressDialog(getActivity()).createLoadingDialog(
						getActivity(), null);
				bar.setOnCancelListener(new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						bar.cancel();
					}
				});
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				if (200 == statusCode) {
					bar.cancel();
				}
				JSONObject httpObject;
				try {
					httpObject = new JSONObject(content);
					if ("1".equals(httpObject.getString("code"))) {
						String response = httpObject.getString("response");
						JSONObject resObject = new JSONObject(response);
						voucherNum = resObject.getString("can_use_score");
						jiFen = (TextView) viewIntegeral
								.findViewById(R.id.jiefen);
						if (null != voucherNum || "".equals(voucherNum)) {
							jiFen.setText(voucherNum);
						} else {
							jiFen.setText(0 + "");
						}
					} else {
						Toast.makeText(getActivity(),
								getResources().getString(R.string.bad_network),
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		});

	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}