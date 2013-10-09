package com.yidejia.app.mall.util;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yidejia.app.mall.GoodsInfoActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.OrderDataManage;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.Order;
import com.yidejia.app.mall.view.OrderDetailActivity;

public class WaitPayOrderDetail {
	private Context context;
	private LayoutInflater inflater;
	private OrderDataManage orderDataManage;// ��ȡ�������������
	private Order order;// ����
	private TextView detail;//��Ʒ������
	private TextView price;//��Ʒ�ļ۸�
	private TextView count;//��Ʒ������
	private ImageView head;//ͷ��
	private LinearLayout layout;
	private View view;

	private float sumPrice = 0;//�ܵļ۸�
	private  int sumCount=0;//�ܵ�����
	
	public static HashMap<String, Float> map;//�����������
	public WaitPayOrderDetail(Context context, Order order,LinearLayout layout) {//,TextView sumView
		
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.order = order;
		this.layout = layout;
	}

	/**
	 * ʵ�����ؼ�
	 */
	public void setupShow() {
		 view = inflater.inflate(R.layout.wait_deliver_item_produce, null);
		head = (ImageView) view.findViewById(R.id.wait_deliver_item_image);
		detail = (TextView) view.findViewById(R.id.wait_deliver_item_text);
		detail.setOnClickListener(new OnClickListener() {//��ӵ���¼�
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(context,GoodsInfoActivity.class);
				context.startActivity(intent);
			}
		});
		price = (TextView) view.findViewById(R.id.wait_deliver_item_sum_price);
		count = (TextView) view.findViewById(R.id.wait_deliver_item_count_detail);
		
	}

	public void addView() {
		try {
			map  = new HashMap<String, Float>();
			ArrayList<Cart> mArrayList = order.getCartsArray();
//			Log.i("info", mArrayList.size()+"   mArrayList");
			for (int i = 0; i < mArrayList.size(); i++) {
				setupShow();
				Cart cart = mArrayList.get(i);
				String urlString = cart.getImgUrl();
				Bitmap bm = BitmapFactory.decodeFile(urlString);
				if(bm!=null){
					head.setImageBitmap(bm);
				}else{
					head.setImageResource(R.drawable.ic_launcher);
				}
				detail.setText(cart.getProductText());
				price.setText(cart.getPrice()+"");
				count.setText(cart.getAmount()+"");
				layout.addView(view);
				sumPrice +=cart.getPrice()*cart.getAmount();
				sumCount += cart.getAmount();
				
			}
			map.put("price", sumPrice);
			map.put("count", (float)sumCount);
//			Log.i("info", sumCount+"   sumprice");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "���粻������", Toast.LENGTH_SHORT).show();

		}
//		sumTextView.setText(sumPrice+"");
//		Log.i("info", sumPrice+"");
	}

	
}
