package com.yidejia.app.mall.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.yidejia.app.mall.GoodsInfoActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.TaskGetOrderByCode;
import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.util.Consts;

public class OrderDetailActivity extends SherlockFragmentActivity {
//	private TextView nameTextView;//收件人姓名
//	private TextView phoneTextView;//收件人电话
//	private TextView detailTextView;//收件人地址
	private LinearLayout layout;//订单内容
//	private TextView priceTextView;//订单物品价格
//	private TextView emsTextView;//快递费
//	private TextView sumTextView;//总共的价格
//	private Button payButton;//立即付款
//	private TextView orderNumber;//订单的编号
//	private TextView orderTime;//下单时间
//	private AddressDataManage addressDataManage ;//地址管理
//	private MyApplication myApplication;
	private String orderCode;//传递过来的订单号
	private String orderPrice;//传递过来的价格总数
	private String orderTn;//传递过来的流水号
	
	private String TAG = OrderDetailActivity.class.getName();
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setActionBar();
		setContentView(R.layout.order_detail);
		initDisplayImageOption();
//		addressDataManage = new AddressDataManage(OrderDetailActivity.this);
//		myApplication = (MyApplication) getApplication();
		Intent intent = getIntent();
		orderCode = intent.getExtras().getString("OrderCode");
		orderPrice =  intent.getExtras().getString("OrderPrice");
		ArrayList<Cart> carts = (ArrayList<Cart>)intent.getSerializableExtra("carts");
//		try {
//			orderTn = intent.getExtras().getString("OrderTn");
//		} catch (Exception e) {
//			// TODO: handle exception
//			Log.e(TAG, "tn is null");
//			e.printStackTrace();
//		}
		Log.i("info", orderCode +"   orderCode");
//		nameTextView = (TextView) findViewById(R.id.order_detail_name);
//		phoneTextView = (TextView) findViewById(R.id.order_detail_number);
//		detailTextView = (TextView) findViewById(R.id.order_detail_position);
//		priceTextView = (TextView) findViewById(R.id.order_detail_dingdan_sum);
//		emsTextView = (TextView) findViewById(R.id.order_detail_yunhui_sum);
//		sumTextView = (TextView) findViewById(R.id.go_pay_show_pay_money);
//		Button payButton = (Button) findViewById(R.id.order_detail_pay);
//		payButton.setClickable(true);
//		payButton.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if("".equals(orderTn) || null == orderTn) {
//					Log.e(TAG, "tn is null");
//					return;
//				}
//				Log.e(TAG, "tn is not null");
//				Intent intent = new Intent(OrderDetailActivity.this, UserPayActivity.class);
//				Bundle bundle = new Bundle();
//				bundle.putInt("mode", 1);
//				bundle.putString("tn", orderTn);
//				bundle.putString("resp_code", "00");
//				bundle.putString("code", orderCode);
//				bundle.putString("uid", ((MyApplication)getApplication()).getUserId());
//				intent.putExtras(bundle);
//				startActivity(intent);
//				OrderDetailActivity.this.finish();
//			}
//		});
//		orderNumber = (TextView) findViewById(R.id.order_detail_biaohao_number);
//		orderTime =  (TextView) findViewById(R.id.order_detail_time_number);
		
		layout = (LinearLayout) findViewById(R.id.order_detail_relative2);
		
//		RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.order_detail_data_layout);
		
		int length = carts.size();
		for (int i = 0; i < length; i++) {
			View view = getLayoutInflater().inflate(R.layout.order_detail_item,
					null);
			final Cart cart = carts.get(i);
			setCartsInfo(view, cart);
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent goodsInfoIntent = new Intent(OrderDetailActivity.this, GoodsInfoActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("goodsId", cart.getUId());
					goodsInfoIntent.putExtras(bundle);
					startActivity(goodsInfoIntent);
				}
			});
			layout.addView(view);
		}
		setupShow();
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
//		super.onActivityResult(arg0, arg1, arg2);
		if (data == null) {
			return;
		}
		if (requestCode == Consts.AddressRequestCode
				&& resultCode == Consts.AddressResponseCode) {
			Addresses addresses1 = (Addresses) data.getExtras()
					.getSerializable("addresses1");
			taskOrderByCode.setAddress(addresses1);
		}
	}



	private void setActionBar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setCustomView(R.layout.actionbar_compose);
		ImageView back = (ImageView) findViewById(R.id.compose_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				OrderDetailActivity.this.finish();
			}
		});
		getSupportActionBar().setIcon(R.drawable.back1);
		TextView title = (TextView) findViewById(R.id.compose_title);
		title.setText(getResources().getString(R.string.order_detail));
	}
	int fromIndex = 0;
	int acount = 10;
	StringBuffer sb = new StringBuffer();
	TaskGetOrderByCode taskOrderByCode;
	
	private void setupShow(){
//		ArrayList<Addresses> addresses = addressDataManage.getAddressesArray(myApplication.getUserId(), fromIndex, acount);
//		if(addresses.isEmpty())return;
//		Addresses address = addresses.get(0);
//		nameTextView.setText(address.getName());
//		phoneTextView.setText(address.getHandset());
//		sb.append(address.getProvice());
//		sb.append(address.getCity());
//		sb.append(address.getArea());
//		sb.append(address.getAddress());
//		detailTextView.setText(sb.toString());
//		priceTextView.setText(orderPrice);
//		String expressNum = "10";
//		emsTextView.setText(expressNum+getResources().getString(R.string.unit));
//		orderNumber.setText(orderCode);
////		orderTime.setText("2013_12_01");
//		try {
//			
//			float odprice = Float.parseFloat(orderPrice);
//			float exprice = Float.parseFloat(expressNum);
//			
//			sumTextView.setText((odprice + exprice) + getResources().getString(R.string.unit));
//		} catch (NumberFormatException e) {
//			// TODO: handle exception
//			sumTextView.setText("");
//		}
		
		
		taskOrderByCode = new TaskGetOrderByCode(this);
		taskOrderByCode.getOderBC(orderCode, orderPrice);//TN , orderTn
	}
	/**
	 * 设置购物清单商品信息
	 * @param view
	 * @param cart
	 */
	private void setCartsInfo(View view, Cart cart){
		TextView order_detail_item_text = (TextView) view.findViewById(R.id.order_detail_item_text);
		order_detail_item_text.setText(cart.getProductText());
		ImageView order_detail_item_image = (ImageView) view.findViewById(R.id.order_detail_item_image);
		imageLoader.displayImage(cart.getImgUrl(),
				order_detail_item_image, options, animateFirstListener);
		TextView order_detail_item_sum = (TextView) view.findViewById(R.id.order_detail_item_sum);
		order_detail_item_sum.setText(cart.getPrice() + getResources().getString(R.string.unit));
		TextView order_detail_item_count = (TextView) view.findViewById(R.id.order_detail_item_count);
		order_detail_item_count.setText(getResources().getString(R.string.amount) + cart.getAmount());
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

	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	private void initDisplayImageOption() {
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.image_bg)
				.showImageOnFail(R.drawable.image_bg)
				.showImageForEmptyUri(R.drawable.image_bg)
				.cacheInMemory(true).cacheOnDisc(true).build();
	}
}
