package com.yidejia.app.mall.tickets;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
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
	private TextView noTextView;// 没有数据

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
			noTextView = (TextView) viewCoupons.findViewById(R.id.tv_no_data);
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

		Log.e("system.out", url);
		
		HttpClientUtil httpClientUtil = new HttpClientUtil(getActivity());
		httpClientUtil.setIsShowLoading(true);
		httpClientUtil.setShowErrMessage(true);
		httpClientUtil.getHttpResp(url, new IHttpResp() {

			@Override
			public void onSuccess(String content) {
				ParseTickets parseTickets = new ParseTickets();
				boolean isSuccess = parseTickets.parseTickets(content);
				if (isSuccess) {
					ArrayList<Ticket> tickets = parseTickets.getTickets();

					IntegerAdapter adapter = new IntegerAdapter(getActivity(),
							tickets, "Fragment");
					listview.setAdapter(adapter);

				} else {
					listview.setVisibility(View.GONE);
					noTextView.setVisibility(View.VISIBLE);
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

		HttpClientUtil client = new HttpClientUtil(getActivity());
		client.setIsShowLoading(true);
		client.getHttpResp(url, param, new IHttpResp() {
			
			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
				
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