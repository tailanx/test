package com.yidejia.app.mall.util;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.PersonEvaluationActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.OrderDataManage;
import com.yidejia.app.mall.model.Order;

public class AlreadyCompleteUtil {
	private Context context;
	private LayoutInflater mInflater;
	private LinearLayout mLinearLayout;//�������õ�
	private LinearLayout mLayout;//���������õ�
	private View view;
	private MyApplication myApplication;
	
	private TextView titleTextView;//������״̬
	private TextView numberTextView;//�����ı��
	private TextView sumPrice;//�������ܼ۸�
	private TextView countTextView;//����Ŀ
	private Button comment;//评价 
	
	private OrderDataManage orderDataManage ;//������ȡ�������
	
	public AlreadyCompleteUtil(Context context,LinearLayout layout){
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.mLinearLayout = layout;
		myApplication = (MyApplication) context.getApplicationContext();
	}
	public void setupShow(){
		view = mInflater.inflate(R.layout.already_complete_item_main_item,null);
		mLayout = (LinearLayout) view.findViewById(R.id.already_complete_item_main_relative2);
		titleTextView = (TextView)view.findViewById(R.id.already_complete_item_main_item_detail);
		numberTextView = (TextView)view.findViewById(R.id.already_complete_item_main_item_textview2_number);
		sumPrice = (TextView)view.findViewById(R.id.already_complete_item_main_sum_money_detail);
		countTextView = (TextView)view.findViewById(R.id.already_complete_item_main_item_textview7_detail);
		comment = (Button) view.findViewById(R.id.already_complete_item_main_item_comment);
	}
	/**
	 * ������ͼ
	 */
		public void loadView(int fromIndex, int amount){
			try {
				orderDataManage = new OrderDataManage(context);
				ArrayList<Order> mList = orderDataManage.getOrderArray(myApplication.getUserId(), "", "", "已发货", fromIndex+"", amount+"",myApplication.getToken());///已签收
				Log.i("info", mList.size()+"mList");
				for(int i=0;i<mList.size();i++){
					setupShow();
					Log.i("info", view+"+view");
					Order mOrder = mList.get(i);
					titleTextView.setText(mOrder.getStatus());
					numberTextView.setText(mOrder.getOrderCode());
					
					AlreadyCompleteDetail alreadyCompleteDetail = new AlreadyCompleteDetail(context, mOrder, mLayout);
					alreadyCompleteDetail.addView();//������Ʒ
					for(int j=0;j<alreadyCompleteDetail.map.size();j++){
//					Log.i("info", mLinearLayoutLayout+"+mlayout");
					sumPrice.setText(alreadyCompleteDetail.map.get("price")+"");
					countTextView.setText(alreadyCompleteDetail.map.get("count").intValue()+"");
					}
//					sumPrice.setText(100+"");
//					countTextView.setText(1+"");
					comment.setOnClickListener( new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(context,PersonEvaluationActivity.class);
							context.startActivity(intent);
							
						}
					});
					mLinearLayout.addView(view);
					Log.i("info", mLinearLayout+"+mlayout");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();

			}
		}
}

