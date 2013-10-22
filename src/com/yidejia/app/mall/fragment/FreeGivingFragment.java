package com.yidejia.app.mall.fragment;

import java.util.ArrayList;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.OrderDataManage;
import com.yidejia.app.mall.model.Order;
import com.yidejia.app.mall.util.AllOrderUtil;
import com.yidejia.app.mall.view.OrderDetailActivity;

public class FreeGivingFragment extends SherlockFragment {
//	private TextView titleTextView;//订单的状态
//	private TextView numberTextView;//订单的编号
//	private TextView sumPrice;//订单的总价格
//	private TextView countTextView;//订单的总数目
//	private LinearLayout mLayout;//外层的布局
//	private View view;
//	private OrderDataManage orderDataManage ;//用来获取订单数据
	
	private String hello;
	private String defaultHello = "default hello";
	/**
	 * 实例化对象
	 * @param view
	 */
	private void setupShow(View view){
//			 	mLayout = (LinearLayout) view.findViewById(R.id.all_order_item_main_scrollView_linearlayout1);
//				orderDataManage = new OrderDataManage(getSherlockActivity());
//				titleTextView = (TextView)view.findViewById(R.id.all_order_item_main_item_detail);
//				numberTextView = (TextView)view.findViewById(R.id.all_order_item_main_item_number);
//				sumPrice = (TextView)view.findViewById(R.id.all_order_item_main_sum_money_deatil);
//				countTextView = (TextView)view.findViewById(R.id.all_order_item_main_item_textview7);
	}


	//通过单例模式，构建对象
	public static FreeGivingFragment newInstance(String s){
		FreeGivingFragment waitFragment = new FreeGivingFragment();
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
		
		View view= inflater.inflate(R.layout.free_giving, null);//获取视图对象
		LinearLayout relativeLayout = (LinearLayout)view.findViewById(R.id.shopping_cart_relative2);//获取布局
//		AllOrderUtil allOrderUtil = new AllOrderUtil(getSherlockActivity(), relativeLayout);
//		Log.i("info", allOrderUtil+"");
//		allOrderUtil.loadView();
//		setupShow(view);
//		getData();
				
//		View produce = inflater.inflate(R.layout.all_order_item_produce, null);//产品详细
//		View produce1 = inflater.inflate(R.layout.all_order_item_produce, null);//产品详细
//		
//		relativeLayout.addView(produce);
//		relativeLayout.addView(produce1);
//		
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
//	/**
//	 * 用来展示数据的
//	 */
//	private void getData(){
//		ArrayList<Order> mList = orderDataManage.getOrderArray(514492+"", "", "", "", 0+"", 10+"");
//		for(int i=0;i<mList.size();i++){
//			Order mOrder = mList.get(i);
//			titleTextView.setText(mOrder.getStatus());
//			numberTextView.setText(mOrder.getOrderCode());
//			mLayout.addView(view);
//		}
//	}
}