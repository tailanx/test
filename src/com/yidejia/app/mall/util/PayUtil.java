package com.yidejia.app.mall.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.unionpay.mpay.views.an;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.Cart;

public class PayUtil {
	private Context context;
	private LayoutInflater inflater;
	private View view;

	private LinearLayout mLinearLayoutLayout;// ���Ĳ���
	private LinearLayout mLayout;// ���Ĳ���
	private ImageLoader imageLoader;
	private ImageLoadingListener animateFirstListener;
	private DisplayImageOptions options;

//	private CartsDataManage dataManage;// ������ȡ�������
	private Cart cart;
	public PayUtil(Context context, LinearLayout mLayout) {
//		this.cart = cart;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.mLinearLayoutLayout = mLayout;
//		this.options = options;
//		this.imageLoader = imageLoader;
//		this.animateFirstListener = listener;
		imageLoader = ImageLoader.getInstance();
		animateFirstListener = MyApplication.getInstance().getImageLoadingListener();
		options = MyApplication.getInstance().initGoodsImageOption();
		ImageLoader.getInstance().init(MyApplication.getInstance().initConfig());
	}

	public PayUtil(Context context, LinearLayout mLayout,ImageLoader imageLoader,ImageLoadingListener listener,DisplayImageOptions option) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.mLinearLayoutLayout = mLayout;
		this.options = option;
		this.animateFirstListener = listener;
		this.imageLoader = imageLoader;
	}

	public String loadView(ArrayList<Cart> mList, boolean isHuanGou) {//, boolean isHuanGou
		StringBuffer goods = new StringBuffer();
		try {
//			dataManage = new CartsDataManage();
//			ArrayList<Cart> mList = dataManage.getCartsArray();
			// Log.i("info", mList.size()+"mList");
			Log.i("voucher", mList.size()+"    carts.size()");
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
				Log.i("info", cart+" cart");
				titleTextView.setText(cart.getProductText());
				String head = cart.getImgUrl();
				imageLoader.init(ImageLoaderConfiguration.createDefault(context));
				imageLoader.displayImage(head, headImage, options,
						animateFirstListener);
//				// Log.i("info", head+"   head");
//				// Bitmap bm = BitmapFactory.decodeFile(head);
//				// if(bm != null){
//				// headImage.setImageBitmap(bm);
//				// }else{
//				// headImage.setImageResource(R.drawable.ic_launcher);
//				// }
				String  a = cart.getPrice()+"";
				String b= cart.getScort()+"";
				Log.i("info", a+" a");
//				int b= a.indexOf(".");
//				
//				Log.e("info", a.charAt(b+1)+"");
				
				if(("0.0".equals(a))&&!"0".equals(b)){

					sumPrice.setText(cart.getScort()+"  积分");
				}else{
					sumPrice.setText("￥ "+cart.getPrice());
					}
				String amount = cart.getAmount() + "";
				countTextView.setText(amount);
				if(isHuanGou)
					goods.append(cart.getUId()+","+amount+"y;");
				else
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
				if(cart.getPrice()>0.0){
				sumPrice.setText(cart.getPrice()+"积分");
				}else{
					sumPrice.setText("￥"+cart.getPrice());
				}
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

