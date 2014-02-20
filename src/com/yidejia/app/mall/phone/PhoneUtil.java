package com.yidejia.app.mall.phone;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.order.Order;

/**
 * 用来加载电话订单
 * 
 * @author Administrator
 * 
 */
public class PhoneUtil {

	private Context context;
	private ArrayList<Order> mlist;
	private LinearLayout linearLayout;

	public PhoneUtil(Context context, LinearLayout linearLayout,
			ArrayList<Order> list) {
		this.context = context;
		this.mlist = list;
		this.linearLayout = linearLayout;
	}

	/**
	 * 加载界面
	 */
	public void addView() {

		if (mlist.isEmpty()) {
			mlist = new ArrayList<Order>();
		} else {
			int leng = mlist.size();
			for (int i = 0; i < leng; i++) {
				final View view = LayoutInflater.from(context).inflate(
						R.layout.phone_item, null);
				TextView order_state = (TextView) view
						.findViewById(R.id.all_order_item_main_item_detail);// 订单详情
				TextView order_number = (TextView) view
						.findViewById(R.id.all_order_item_main_item_number);// 订单号码
				ImageView order_image = (ImageView) view
						.findViewById(R.id.all_order_item_image);// 订单图片
				TextView order_detail = (TextView) view
						.findViewById(R.id.all_order_item_text);// 订单详情
				TextView order_handset = (TextView) view
						.findViewById(R.id.all_order_item_sum_detail);// 手机号码
				TextView order_sum = (TextView) view
						.findViewById(R.id.all_order_item_sum);
				TextView order_time = (TextView) view
						.findViewById(R.id.all_order_item_main_sum_add);
				Order order = mlist.get(i);
				order_state.setText(order.getStatus());
				order_number.setText(order.getOrderCode());
				order_detail.setText(order.getDetail());
				order_handset.setText(order.getNumber());
				order_sum.setText("￥" + order.getPay_money());
				order_time.setText(order.getDate());
				ImageLoader.getInstance().init(
						MyApplication.getInstance().initConfig());
				ImageLoader.getInstance().displayImage(order.getImage(),
						order_image,
						MyApplication.getInstance().initBannerImageOption(),
						MyApplication.getInstance().getImageLoadingListener());
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				lp.setMargins(0, 20, 0, 0);
				view.setLayoutParams(lp);
				linearLayout.addView(view);
			}
		}
	}
}
