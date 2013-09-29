package com.yidejia.app.mall.fragment;

import java.util.ArrayList;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
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

public class AllOrderFragment extends SherlockFragment {
//	private TextView titleTextView;//������״̬
//	private TextView numberTextView;//�����ı��
//	private TextView sumPrice;//�������ܼ۸�
//	private TextView countTextView;//����������Ŀ
//	private LinearLayout mLayout;//���Ĳ���
//	private View view;
//	private OrderDataManage orderDataManage ;//������ȡ��������
	
	private String hello;
	private String defaultHello = "default hello";
	/**
	 * ʵ��������
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


	//ͨ������ģʽ����������
	public static AllOrderFragment newInstance(String s){
		AllOrderFragment waitFragment = new AllOrderFragment();
		Bundle bundle = new Bundle();
		bundle.putString("Hello", s);
		waitFragment.setArguments(bundle);
		return waitFragment;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//��ȡ�洢�Ĳ���
		Bundle args = getArguments();
		hello = args!=null?args.getString("hello"):defaultHello;
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view= inflater.inflate(R.layout.all_order_item_main, null);//��ȡ��ͼ����
		LinearLayout relativeLayout = (LinearLayout)view.findViewById(R.id.all_order_item_main_scrollView_linearlayout1);//��ȡ����
		AllOrderUtil allOrderUtil = new AllOrderUtil(getSherlockActivity(), relativeLayout);
		allOrderUtil.loadView();
//		setupShow(view);
//		getData();
				
//		View produce = inflater.inflate(R.layout.all_order_item_produce, null);//��Ʒ��ϸ
//		View produce1 = inflater.inflate(R.layout.all_order_item_produce, null);//��Ʒ��ϸ
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
//		//��Ӽ���
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
//	 * ����չʾ���ݵ�
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
