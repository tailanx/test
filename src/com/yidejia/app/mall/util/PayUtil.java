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
import com.yidejia.app.mall.view.PayActivity;

public class PayUtil {
	private Context context;
	private LayoutInflater inflater;
	private View view;

	private LinearLayout mLinearLayoutLayout;// 外层的布局
	private LinearLayout mLayout;// 外层的布局

	private CartsDataManage dataManage;// 用来获取订单数据

	public PayUtil(Context context, LinearLayout mLayout) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.mLinearLayoutLayout = mLayout;
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.hot_sell_right_top_image)
				.showImageOnFail(R.drawable.hot_sell_right_top_image)
				.showImageForEmptyUri(R.drawable.hot_sell_right_top_image)
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
	 * 加载视图
	 */
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();// 加载图片

	public void loadView() {
		try {
			dataManage = new CartsDataManage();
			ArrayList<Cart> mList = dataManage.getCartsArray();
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
						.findViewById(R.id.go_pay_item_text);// 文字描述
				ImageView headImage = (ImageView) view
						.findViewById(R.id.go_pay_item_image);// 头像
				TextView sumPrice = (TextView) view
						.findViewById(R.id.go_pay_item_sum_detail);// 价格
				// TextView sumPrice =
				// (TextView)view.findViewById(R.id.all_order_item_main_sum_money_deatil);
				TextView countTextView = (TextView) view
						.findViewById(R.id.go_pay_item_count_detail);// 商品数目

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
				countTextView.setText(cart.getAmount() + "");
				// numberTextView.setText(mOrder.getOrderCode());
				//
				// final AllOrderDetail allOrderDetail = new
				// AllOrderDetail(context, mOrder, mLayout);
				// allOrderDetail.addView();//加载商品
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
			Toast.makeText(context, "网络不给力！", Toast.LENGTH_SHORT).show();

		}

	}

}
