package com.yidejia.app.mall.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.yidejia.app.mall.GoodsInfoActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.OrderDataManage;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.Order;
import com.yidejia.app.mall.view.GoCartActivity;
import com.yidejia.app.mall.view.OrderDetailActivity;

public class AllOrderDetail {
	private Context context;
	private LayoutInflater inflater;
	private OrderDataManage orderDataManage;// 获取订单详情的数据
	private Order order;// 订单
//	private TextView detail;// 商品的详情
//	private TextView price;// 商品的价格
//	private TextView count;// 商品的数量
//	private ImageView head;// 头像
	private LinearLayout layout;
//	private View view;

	private float sumPrice = 0;// 总的价格
	private int sumCount = 0;// 总的数量

	public static HashMap<String, Float> map;// 用来存放数据

	public AllOrderDetail(Context context, Order order, LinearLayout layout) {// ,TextView
																				// sumView

		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.order = order;
		this.layout = layout;
		initDisplayImageOption();
	}

	

	static final List<String> displayedImages = Collections
			.synchronizedList(new LinkedList<String>());

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();// 加载图片

	private void initDisplayImageOption() {
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.hot_sell_right_top_image)
				.showImageOnFail(R.drawable.hot_sell_right_top_image)
				.showImageForEmptyUri(R.drawable.hot_sell_right_top_image)
				.cacheInMemory(true).cacheOnDisc(true).build();
	}

	public void addView() {
		try {
			map = new HashMap<String, Float>();
			ArrayList<Cart> mArrayList = order.getCartsArray();
			// Log.i("info", mArrayList.size()+"");
			for (int i = 0; i < mArrayList.size(); i++) {
				final Cart cart = mArrayList.get(i);
			View	view = inflater.inflate(R.layout.all_order_item_produce, null);
				ImageView head = (ImageView) view
						.findViewById(R.id.all_order_item_image);
				TextView detail = (TextView) view
						.findViewById(R.id.all_order_item_text);

				TextView price = (TextView) view
						.findViewById(R.id.all_order_item_sum_detail);
				TextView count = (TextView) view
						.findViewById(R.id.all_order_item_count_detail);
				RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.all_relative);
				
				String urlString = cart.getImgUrl();
				imageLoader.displayImage(urlString, head, options,
						animateFirstListener);
				// Bitmap bm = BitmapFactory.decodeFile(urlString);

				relativeLayout.setOnClickListener(new OnClickListener() {// 添加点击事件

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(context,
								GoodsInfoActivity.class);

						Bundle bundle = new Bundle();
						bundle.putString("goodsId", cart.getUId());
						intent.putExtras(bundle);
						context.startActivity(intent);
					}
				});
				// if(bm!=null){
				// head.setImageBitmap(bm);
				// }else{
				// head.setImageResource(R.drawable.ic_launcher);
				// }
				detail.setText(cart.getProductText());
				price.setText(cart.getPrice() + "");
				count.setText(cart.getAmount() + "");
				layout.addView(view);
				sumPrice += cart.getPrice() * cart.getAmount();
				sumCount += cart.getAmount();

			}
			map.put("price", sumPrice);
			map.put("count", (float) sumCount);

			// Log.i("info", sumCount+"   sumprice");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "网络不给力！", Toast.LENGTH_SHORT).show();

		}
		// sumTextView.setText(sumPrice+"");
		// Log.i("info", sumPrice+"");
	}

}
//=======
//package com.yidejia.app.mall.util;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.yidejia.app.mall.GoodsInfoActivity;
//import com.yidejia.app.mall.R;
//import com.yidejia.app.mall.datamanage.OrderDataManage;
//import com.yidejia.app.mall.model.Cart;
//import com.yidejia.app.mall.model.Order;
//import com.yidejia.app.mall.view.OrderDetailActivity;
//
//public class AllOrderDetail {
//	private Context context;
//	private LayoutInflater inflater;
//	private OrderDataManage orderDataManage;// 获取订单详情的数据
//	private Order order;// 订单
//	private TextView detail;//商品的详情
//	private TextView price;//商品的价格
//	private TextView count;//商品的数量
//	private ImageView head;//头像
//	private LinearLayout layout;
//	private View view;
//
//	private float sumPrice = 0;//总的价格
//	private  int sumCount=0;//总的数量
//	
//	public static HashMap<String, Float> map;//用来存放数据
//	public AllOrderDetail(Context context, Order order,LinearLayout layout) {//,TextView sumView
//		
//		this.context = context;
//		this.inflater = LayoutInflater.from(context);
//		this.order = order;
//		this.layout = layout;
//	}
//
//	/**
//	 * 实例化控件
//	 */
//	public void setupShow() {
//		 view = inflater.inflate(R.layout.all_order_item_produce, null);
//		head = (ImageView) view.findViewById(R.id.all_order_item_image);
//		detail = (TextView) view.findViewById(R.id.all_order_item_text);
//		
//		price = (TextView) view.findViewById(R.id.all_order_item_sum_detail);
//		count = (TextView) view.findViewById(R.id.all_order_item_count_detail);
//		
//	}
//
//	public void addView() {
//		try {
//			map  = new HashMap<String, Float>();
//			ArrayList<Cart> mArrayList = order.getCartsArray();
////			Log.i("info", mArrayList.size()+"");
//			for (int i = 0; i < mArrayList.size(); i++) {
//				final Cart cart = mArrayList.get(i);
//				setupShow();
//				String urlString = cart.getImgUrl();
//				Bitmap bm = BitmapFactory.decodeFile(urlString);
//				detail.setOnClickListener(new OnClickListener() {//添加点击事件
//					
//					@Override
//					public void onClick(View v) {
//						Intent intent = new Intent(context,GoodsInfoActivity.class);
//						
//						Bundle bundle = new Bundle();
//						bundle.putString("goodsId", cart.getUId());
//						intent.putExtras(bundle);
//						context.startActivity(intent);
//					}
//				});
//				if(bm!=null){
//					head.setImageBitmap(bm);
//				}else{
//					head.setImageResource(R.drawable.ic_launcher);
//				}
//				detail.setText(cart.getProductText());
//				price.setText(cart.getPrice()+"");
//				count.setText(cart.getAmount()+"");
//				layout.addView(view);
//				sumPrice +=cart.getPrice()*cart.getAmount();
//				sumCount += cart.getAmount();
//				
//			}
//			map.put("price", sumPrice);
//			map.put("count", (float)sumCount);
//			
////			Log.i("info", sumCount+"   sumprice");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			Toast.makeText(context, "网络不给力！", Toast.LENGTH_SHORT).show();
//
//		}
////		sumTextView.setText(sumPrice+"");
////		Log.i("info", sumPrice+"");
//	}
//
//	
//}
//>>>>>>> 097921597f9f3c9d3c4be61352fe52a0864734f2
