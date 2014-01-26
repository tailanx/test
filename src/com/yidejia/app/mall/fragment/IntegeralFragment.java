package com.yidejia.app.mall.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.VoucherDataManage;
//import com.yidejia.app.mall.view.IntegeralActivity;

public class IntegeralFragment extends Fragment {
	private int hello;
	private String defaultHello = "default hello";
	private TextView jiFen;
	private VoucherDataManage voucherDataManage;// 积分
	private MyApplication myApplication;
	private WebView webView;
	private View viewIntegeral;// 积分的界面数据
	private View viewCoupons;// 优惠券的界面视图

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
//		voucherDataManage = new VoucherDataManage(getActivity());
		myApplication = (MyApplication) getActivity().getApplication();
		viewCoupons = inflater.inflate(R.layout.youhuiquan, null);// 优惠券视图
		viewIntegeral = inflater.inflate(R.layout.coupons, null);// 积分视图
		jiFen = (TextView) viewIntegeral.findViewById(R.id.jiefen);
		Log.i("info", jiFen + "jifen");
//		String ji = voucherDataManage.getUserVoucher(myApplication.getUserId(),
//				myApplication.getToken());
//		if (ji == null || "".equals(ji)) {
//
//			jiFen.setText(0 + "");
//		} else {
//			jiFen.setText(ji);
//		}
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
		voucherDataManage = new VoucherDataManage(getActivity());
		voucherDataManage.getUserVoucher(myApplication.getUserId(),
				myApplication.getToken());
		webView = (WebView) viewIntegeral.findViewById(R.id.wb_webView);
		webView.setBackgroundColor(0);
		webView.setBackgroundColor(getResources().getColor(R.color.white));
		webView.loadUrl("http://m.yidejia.com/regterms.html");
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}