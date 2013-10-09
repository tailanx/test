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
	private OrderDataManage orderDataManage;// 获取订单详情的数据
	private Order order;// 订单
	private TextView detail;//商品的详情
	private TextView price;//商品的价格
	private TextView count;//商品的数量
	private ImageView head;//头像
	private LinearLayout layout;
	private View view;

	private float sumPrice = 0;//总的价格
	private  int sumCount=0;//总的数量
	
	public static HashMap<String, Float> map;//用来存放数据
	public WaitPayOrderDetail(Context context, Order order,LinearLayout layout) {//,TextView sumView
		
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.order = order;
		this.layout = layout;
	}

	/**
	 * 实例化控件
	 */
	public void setupShow() {
		 view = inflater.inflate(R.layout.wait_deliver_item_produce, null);
		head = (ImageView) view.findViewById(R.id.wait_deliver_item_image);
		detail = (TextView) view.findViewById(R.id.wait_deliver_item_text);
		detail.setOnClickListener(new OnClickListener() {//添加点击事件
			
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
			Toast.makeText(context, "网络不给力！", Toast.LENGTH_SHORT).show();

		}
//		sumTextView.setText(sumPrice+"");
//		Log.i("info", sumPrice+"");
	}

	
}
