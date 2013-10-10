package com.yidejia.app.mall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockFragment;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.util.WaitPayUtil;
import com.yidejia.app.mall.view.OrderDetailActivity;



public class WaitPayFragment extends SherlockFragment {
	private String hello;
	private String defaultHello = "default hello";
	//通过单例模式，构建对象
	public static WaitPayFragment newInstance(String s){
		WaitPayFragment waitFragment = new WaitPayFragment();
		Bundle bundle = new Bundle();
		bundle.putString("Hello", s);
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
		View view = inflater.inflate(R.layout.wait_pay_item_main, null);//获取视图对象
		LinearLayout relativeLayout = (LinearLayout)view.findViewById(R.id.wait_pay_order_scrollView_linearlayout1);//获取布局
		WaitPayUtil waitPayUtil = new WaitPayUtil(getSherlockActivity(), relativeLayout);
		waitPayUtil.loadView();
//		View produce = inflater.inflate(R.layout.all_order_item_produce, null);//产品详细
//		View produce1 = inflater.inflate(R.layout.all_order_item_produce, null);//产品详细
//		
//		relativeLayout.addView(produce);
//		relativeLayout.addView(produce1);
//		produce1.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				Intent intent = new Intent(getSherlockActivity(),OrderDetailActivity.class);
//				
//				startActivity(intent);
//			}
//		});
//		//添加监听
//		produce.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				Intent intent = new Intent(getSherlockActivity(),OrderDetailActivity.class);
//				
//				startActivity(intent);
//			}
//		});
		return view;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
