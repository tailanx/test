package com.yidejia.app.mall.fragment;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.VoucherDataManage;

public class IntegeralFragment extends Fragment {
	private String hello;
	private String defaultHello = "default hello";
	private TextView jiFen;
	private VoucherDataManage voucherDataManage;//积分
	private MyApplication myApplication;
	//通过单例模式，构建对象
	public static IntegeralFragment newInstance(String s){
		IntegeralFragment waitFragment = new IntegeralFragment();
		Bundle bundle = new Bundle();
		bundle.putString("hello", s);
		waitFragment.setArguments(bundle);
		return waitFragment;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//获取存储的参数
		Bundle args = getArguments();
		hello = args!=null?args.getString("hello"):defaultHello;
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		voucherDataManage = new VoucherDataManage(getActivity());
		myApplication = (MyApplication) getActivity().getApplication();
		View viewCoupons = inflater.inflate(R.layout.my_card_voucher1_item, null);//优惠券视图
		View viewIntegeral =inflater.inflate(R.layout.coupons, null);//积分视图
		jiFen = (TextView) viewIntegeral.findViewById(R.id.jiefen);
		Log.i("info", jiFen+"jifen");
		String ji = voucherDataManage.getUserVoucher(myApplication.getUserId(), myApplication.getToken());
		if(ji==null||"".equals(ji)){
			
			jiFen.setText(0+"");
		}else{
			jiFen.setText(ji);
		}
		if(hello.equals("jifenquan"))
			return viewCoupons;
		else 
			return viewIntegeral;
			
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}