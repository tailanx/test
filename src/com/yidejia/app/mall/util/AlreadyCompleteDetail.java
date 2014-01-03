package com.yidejia.app.mall.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.OrderDataManage;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.Order;
import com.yidejia.app.mall.view.GoodsInfoActivity;

public class AlreadyCompleteDetail {
	private Context context;
	private LayoutInflater inflater;
	private OrderDataManage orderDataManage;// ��ȡ������������
	private Order order;// ����
//	private TextView detail;//��Ʒ������
//	private TextView price;//��Ʒ�ļ۸�
//	private TextView count;//��Ʒ������
//	private ImageView head;//ͷ��
	private LinearLayout layout;
//	private View view;

	private float sumPrice = 0;//�ܵļ۸�
	private  int sumCount=0;//�ܵ�����
	
	public static HashMap<String, Float> map;//����������
	public AlreadyCompleteDetail(Context context, Order order,LinearLayout layout) {//,TextView sumView
		
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.order = order;
		this.layout = layout;
		initDisplayImageOption();
	}

	/**
	 * ʵ��ؼ�
	 */
//	public void setupShow() {
//		View view = inflater.inflate(R.layout.already_complete_item_produce, null);
//		RelativeLayout mLayout = (RelativeLayout) view.findViewById(R.id.already_complete_relative);
//		ImageView head = (ImageView) view.findViewById(R.id.already_complete_item_produce_image);
//		TextView detail = (TextView) view.findViewById(R.id.already_complete_item_produce_text);
//		mLayout.setOnClickListener(new OnClickListener() {//��ӵ���¼�
//			
//			@Override
//			public void onClick(View v) {
//				
//				Intent intent = new Intent(context,GoodsInfoActivity.class);
//				context.startActivity(intent);
//			}
//		});
//		TextView price = (TextView) view.findViewById(R.id.already_complete_item_produce_sum_detail);
//		TextView count = (TextView) view.findViewById(R.id.already_complete_item_produce_count_detail);
//		
//	}
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
	protected ImageLoader imageLoader = ImageLoader.getInstance();// ����ͼƬ

	private void initDisplayImageOption() {
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.image_bg)
				.showImageOnFail(R.drawable.image_bg)
				.showImageForEmptyUri(R.drawable.image_bg)
				.cacheInMemory(true).cacheOnDisc(true).build();
	}
	public void addView() {
		try {
			map  = new HashMap<String, Float>();
			ArrayList<Cart> mArrayList = order.getCartsArray();
			for (int i = 0; i < mArrayList.size(); i++) {
				View view = inflater.inflate(R.layout.already_complete_item_produce, null);
				RelativeLayout mLayout = (RelativeLayout) view.findViewById(R.id.already_complete_relative);
				ImageView head = (ImageView) view.findViewById(R.id.already_complete_item_produce_image);
				TextView detail = (TextView) view.findViewById(R.id.already_complete_item_produce_text);
				mLayout.setOnClickListener(new OnClickListener() {//��ӵ���¼�
					
					@Override
					public void onClick(View v) {
						
						Intent intent = new Intent(context,GoodsInfoActivity.class);
						context.startActivity(intent);
					}
				});
				TextView price = (TextView) view.findViewById(R.id.already_complete_item_produce_sum_detail);
				TextView count = (TextView) view.findViewById(R.id.already_complete_item_produce_count_detail);
				Cart cart = mArrayList.get(i);
				String urlString = cart.getImgUrl();
				imageLoader.displayImage(urlString, head, options,
						animateFirstListener);
//				Bitmap bm = BitmapFactory.decodeFile(urlString);
//				if(bm!=null){
//					head.setImageBitmap(bm);
//				}else{
//					head.setImageResource(R.drawable.ic_launcher);
//				}
				detail.setText(cart.getProductText());
				price.setText(cart.getPrice()+"");
				count.setText(cart.getAmount()+"");
				layout.addView(view);
				sumPrice +=cart.getPrice()*cart.getAmount();
				sumCount += cart.getAmount();
				
			}
			map.put("price", sumPrice);
			map.put("count", (float)sumCount);
			Log.i("info", sumCount+"   sumprice");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();

		}
//		sumTextView.setText(sumPrice+"");
//		Log.i("info", sumPrice+"");
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
//public class AlreadyCompleteDetail {
//	private Context context;
//	private LayoutInflater inflater;
//	private OrderDataManage orderDataManage;// ��ȡ������������
//	private Order order;// ����
//	private TextView detail;//��Ʒ������
//	private TextView price;//��Ʒ�ļ۸�
//	private TextView count;//��Ʒ������
//	private ImageView head;//ͷ��
//	private LinearLayout layout;
//	private View view;
//
//	private float sumPrice = 0;//�ܵļ۸�
//	private  int sumCount=0;//�ܵ�����
//	
//	public static HashMap<String, Float> map;//����������
//	public AlreadyCompleteDetail(Context context, Order order,LinearLayout layout) {//,TextView sumView
//		
//		this.context = context;
//		this.inflater = LayoutInflater.from(context);
//		this.order = order;
//		this.layout = layout;
//	}
//
//	/**
//	 * ʵ��ؼ�
//	 */
//	public void setupShow() {
//		 view = inflater.inflate(R.layout.already_complete_item_produce, null);
//		head = (ImageView) view.findViewById(R.id.already_complete_item_produce_image);
//		detail = (TextView) view.findViewById(R.id.already_complete_item_produce_text);
//		detail.setOnClickListener(new OnClickListener() {//��ӵ���¼�
//			
//			@Override
//			public void onClick(View v) {
//				
//				Intent intent = new Intent(context,GoodsInfoActivity.class);
//				context.startActivity(intent);
//			}
//		});
//		price = (TextView) view.findViewById(R.id.already_complete_item_produce_sum_detail);
//		count = (TextView) view.findViewById(R.id.already_complete_item_produce_count_detail);
//		
//	}
//
//	public void addView() {
//		try {
//			map  = new HashMap<String, Float>();
//			ArrayList<Cart> mArrayList = order.getCartsArray();
//			for (int i = 0; i < mArrayList.size(); i++) {
//				setupShow();
//				Cart cart = mArrayList.get(i);
//				String urlString = cart.getImgUrl();
//				Bitmap bm = BitmapFactory.decodeFile(urlString);
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
//			Log.i("info", sumCount+"   sumprice");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			Toast.makeText(context, "���粻������", Toast.LENGTH_SHORT).show();
//
//		}
////		sumTextView.setText(sumPrice+"");
////		Log.i("info", sumPrice+"");
//	}
//
//	
//}
//>>>>>>> 097921597f9f3c9d3c4be61352fe52a0864734f2
