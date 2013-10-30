package com.yidejia.app.mall.util;

import java.util.ArrayList;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.CartsDataManage;

import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.Order;
import com.yidejia.app.mall.view.CstmPayActivity;

public class PayUtil {
	private Context context;
	private LayoutInflater inflater;
	private View view;

	private LinearLayout mLinearLayoutLayout;// ���Ĳ���
	private LinearLayout mLayout;// ���Ĳ���

//	private CartsDataManage dataManage;// ������ȡ�������
	private Cart cart;
	public PayUtil(Context context, LinearLayout mLayout,Cart cart) {
		this.cart = cart;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.mLinearLayoutLayout = mLayout;
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.image_bg)
				.showImageOnFail(R.drawable.image_bg)
				.showImageForEmptyUri(R.drawable.image_bg)
				.cacheInMemory(true).cacheOnDisc(true).build();
	
	}

	public PayUtil(Context context, LinearLayout mLayout) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.mLinearLayoutLayout = mLayout;
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.image_bg)
				.showImageOnFail(R.drawable.image_bg)
				.showImageForEmptyUri(R.drawable.image_bg)
				.cacheInMemory(true).cacheOnDisc(true).build();
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

	/**
	 * ������ͼ
	 */
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();// ����ͼƬ

	public String loadView(ArrayList<Cart> mList) {
		StringBuffer goods = new StringBuffer();
		try {
//			dataManage = new CartsDataManage();
//			ArrayList<Cart> mList = dataManage.getCartsArray();
			// Log.i("info", mList.size()+"mList");
			for (int i = 0; i < mList.size(); i++) {
				view = inflater.inflate(R.layout.go_pay_item, null);
				// Log.i("info", view+"");
				Cart cart = mList.get(i);
				// Button mButton = (Button)
				// view.findViewById(R.id.all_order_item_main_pay);
				// LinearLayout mLayout = (LinearLayout)
				// view.findViewById(R.id.all_order_item_main_relative2);
				TextView titleTextView = (TextView) view
						.findViewById(R.id.go_pay_item_text);// ��������
				ImageView headImage = (ImageView) view
						.findViewById(R.id.go_pay_item_image);// ͷ��
				TextView sumPrice = (TextView) view
						.findViewById(R.id.go_pay_item_sum_detail);// �۸�
				// TextView sumPrice =
				// (TextView)view.findViewById(R.id.all_order_item_main_sum_money_deatil);
				TextView countTextView = (TextView) view
						.findViewById(R.id.go_pay_item_count_detail);// ��Ʒ��Ŀ

				// Order mOrder = mList.get(i);
				titleTextView.setText(cart.getProductText());
				String head = cart.getImgUrl();
				imageLoader.displayImage(head, headImage, options,
						animateFirstListener);
				// Log.i("info", head+"   head");
				// Bitmap bm = BitmapFactory.decodeFile(head);
				// if(bm != null){
				// headImage.setImageBitmap(bm);
				// }else{
				// headImage.setImageResource(R.drawable.ic_launcher);
				// }
				sumPrice.setText(cart.getPrice() + "");
				String amount = cart.getAmount() + "";
				countTextView.setText(amount);
				goods.append(cart.getUId()+","+amount+"n;");
				// numberTextView.setText(mOrder.getOrderCode());
				//
				// final AllOrderDetail allOrderDetail = new
				// AllOrderDetail(context, mOrder, mLayout);
				// allOrderDetail.addView();//������Ʒ
				// for(int j=0;j<allOrderDetail.map.size();j++){
				//
				// sumPrice.setText(allOrderDetail.map.get("price")+"");
				// countTextView.setText(allOrderDetail.map.get("count").intValue()+"");
				// }
				//
				//
				//
				// mButton.setOnClickListener(new OnClickListener() {
				//
				// @Override
				// public void onClick(View v) {
				// // TODO Auto-generated method stub
				// Intent intent = new Intent(context,PayActivity.class);
				// Bundle mBundle = new Bundle();
				// mBundle.putString("price",
				// allOrderDetail.map.get("price")+"");
				// intent.putExtras(mBundle);
				// context.startActivity(intent);
				// }
				// });
				mLinearLayoutLayout.addView(view);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();

		}
		return goods.toString();
	}
	public String cartLoadView() {
		StringBuffer goods = new StringBuffer();
		try {
		
				view = inflater.inflate(R.layout.go_pay_item, null);
				TextView titleTextView = (TextView) view
						.findViewById(R.id.go_pay_item_text);// ��������
				ImageView headImage = (ImageView) view
						.findViewById(R.id.go_pay_item_image);// ͷ��
				TextView sumPrice = (TextView) view
						.findViewById(R.id.go_pay_item_sum_detail);// �۸�
				TextView countTextView = (TextView) view
						.findViewById(R.id.go_pay_item_count_detail);// ��Ʒ��Ŀ
				sumPrice.setText(cart.getPrice()+"");
				countTextView.setText(cart.getAmount()+"");
				titleTextView.setText(cart.getProductText());
				String head = cart.getImgUrl();
				imageLoader.displayImage(head, headImage, options,
						animateFirstListener);
				
				mLinearLayoutLayout.addView(view);
				goods.append(cart.getUId()+","+cart.getAmount()+"n;");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();

		}
		return goods.toString();
	}
}

