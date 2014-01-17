package com.yidejia.app.mall.util;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.Order;

public class AllOrderDetail {
	private Context context;
	private LayoutInflater inflater;
	private Order order;// 锟斤拷锟斤拷
	private LinearLayout layout;

	private float sumPrice = 0;// 锟杰的价革拷
	private int sumCount = 0;// 锟杰碉拷锟斤拷锟斤拷

	public HashMap<String, Float> map;// 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
	
	private ImageLoadingListener animateFirstListener;
	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();

	public AllOrderDetail(Context context, Order order, LinearLayout layout) {// ,TextView
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.order = order;
		this.layout = layout;
		options = MyApplication.getInstance().initGoodsImageOption();
		animateFirstListener = MyApplication.getInstance().getImageLoadingListener();
	}

	
	public void addView() {
		try {
			map = new HashMap<String, Float>();
			ArrayList<Cart> mArrayList = order.getCartsArray();
			if(mArrayList == null) return;
			for (int i = 0; i < mArrayList.size(); i++) {
				final Cart cart = mArrayList.get(i);
				View view = inflater.inflate(R.layout.all_order_item_produce, null);
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
				detail.setText(cart.getProductText());
				price.setText(cart.getPrice() + "");
				count.setText(cart.getAmount() + "");
				layout.addView(view);
				sumPrice += cart.getPrice() * cart.getAmount();
				sumCount += cart.getAmount();

			}
			map.put("price", sumPrice);
			map.put("count", (float) sumCount);

		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();

		}
	}

}
