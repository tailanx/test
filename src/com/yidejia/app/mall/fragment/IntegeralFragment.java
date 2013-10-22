package com.yidejia.app.mall.fragment;



import com.yidejia.app.mall.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class IntegeralFragment extends Fragment {
	private String hello;
	private String defaultHello = "default hello";
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
		View viewCoupons = inflater.inflate(R.layout.my_card_voucher1_item, null);//优惠券视图
		View viewIntegeral =inflater.inflate(R.layout.coupons, null);//积分视图
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