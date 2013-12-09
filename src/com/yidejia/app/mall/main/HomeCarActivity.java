package com.yidejia.app.mall.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.SearchResultActivity;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.fragment.CartActivity;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.util.CartUtil;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.view.CstmPayActivity;
import com.yidejia.app.mall.view.ImageLoaderUtil;
import com.yidejia.app.mall.view.LoginActivity;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeCarActivity extends SherlockFragmentActivity implements OnClickListener{
	private MyApplication myApplication;
	private FrameLayout frameLayout;
	private LayoutInflater inflater;
	private int number;
	private View view;
	private TextView sumTextView;// 总的钱数
	private TextView counTextView;// 总的数量
	private CheckBox mBox;
	private CartUtil cartUtil;
	private LinearLayout layout;
	public static ArrayList<Cart> cartList;
	public static float sum;
	private Button mButton;
	private InnerReceiver receiver;
	private ImageLoader imageLoader;
	private ImageLoadingListener listener;
	private DisplayImageOptions options;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		receiver = new InnerReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Consts.UPDATE_CHANGE);
		filter.addAction(Consts.BROAD_UPDATE_CHANGE);
		filter.addAction(Consts.DELETE_CART);
		registerReceiver(receiver, filter);
		
		cartsDataManage =  new CartsDataManage();
		myApplication = (MyApplication) getApplication();
		setContentView(R.layout.activity_main_fragment_layout);
		frameLayout = (FrameLayout) findViewById(R.id.main_fragment);
		inflater = LayoutInflater.from(this);
		initNavView();
		setActionBarConfig();
		
		ImageLoaderUtil imageLoaderUtil = new ImageLoaderUtil();
		imageLoader = imageLoaderUtil.getImageLoader();
		listener = imageLoaderUtil.getAnimateFirstListener();
		options = imageLoaderUtil.getOptions();
		
		if(cartsDataManage.getCartAmount() != 0){
		view = inflater.inflate(R.layout.shopping_cart, null);
		frameLayout.addView(view);
		setViewCtrl(view);
		}else{
			view = inflater.inflate(R.layout.no_produce,null);
			frameLayout.addView(view);
			setNoProduce(view);
		}
		
	}
	private void setNoProduce(View view){
		mButton = (Button) view.findViewById(R.id.no_produce_button);
		mButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent (HomeCarActivity.this,SearchResultActivity.class);
				Bundle bundle = new Bundle();
				Log.e("info", "gouwuche");
				bundle.putString("title", "全部");
				bundle.putString("name", "");
				bundle.putString("price", "");
				bundle.putString("brand", "");
				bundle.putString("fun", "");
				intent.putExtras(bundle);
				HomeCarActivity.this.startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		imageLoader.init(ImageLoaderConfiguration.createDefault(HomeCarActivity.this));
		imageLoader.stop();
		unregisterReceiver(receiver);
	}
	private void setViewCtrl(View view){
		sumTextView = (TextView) view
				.findViewById(R.id.shopping_cart_sum_money);// 总的钱数

		mBox = (CheckBox) view.findViewById(R.id.shopping_cart_checkbox);// 选择框

		counTextView = (TextView) view
				.findViewById(R.id.shopping_cart_sum_number);// 总的数量

		shoppingCartTopay = (Button)findViewById(
				R.id.shopping_cart_go_pay);
		try{
			shoppingCartTopay.setVisibility(View.VISIBLE);
		} catch(Exception e){
			Log.e(CartActivity.class.getName(), "set visibility err");
		}
		layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		ScrollView scrollView = (ScrollView) view
				.findViewById(R.id.shopping_cart_item_goods_scrollView);
		
		cartUtil = new CartUtil(this, layout,
				counTextView, sumTextView, mBox,imageLoader,listener,options);

		cartUtil.AllComment();

		scrollView.addView(layout);
		
		
		if (shoppingCartTopay == null) {
			Log.e(CartActivity.class.getName(), "cart act button is null");

		} else {
			shoppingCartTopay.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// getAddresses();
					Log.e("NoProduceFragment", "cart act button is null");
					if (!myApplication.getIsLogin()) {
						showLoginTips();
						return;
					} else {
						Log.e("NoProduceFragment", "cart act button is null");
						go2Pay();
					}
				}
			});
		}
	}

	private Button shoppingCartTopay;// 去结算
	/**
	 * 头部
	 */
	private void setActionBarConfig() {
		getSupportActionBar().setCustomView(R.layout.actionbar_cart);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		shoppingCartTopay = (Button) findViewById(R.id.shopping_cart_go_pay);
		number = cartsDataManage.getCartsArray().size();
		if(number == 0){
			shoppingCartTopay.setVisibility(View.GONE);
		}else{
			shoppingCartTopay.setVisibility(View.VISIBLE); 
		}
		

	}
	private RelativeLayout downHomeLayout;
	private RelativeLayout downGuangLayout;
	private RelativeLayout downSearchLayout;
	private RelativeLayout downShoppingLayout;
	private RelativeLayout downMyLayout;
	private ImageView down_home_imageView;// 首页按钮图片
	private ImageView down_guang_imageView;// 逛按钮图片
	private ImageView down_search_imageView;// 搜索按钮图片
	private ImageView down_shopping_imageView; // 购物车按钮图片
	private ImageView down_my_imageView; // 我的商城按钮图片
	private CartsDataManage cartsDataManage;
	private TextView down_home_textview;
	private TextView down_guang_textview;
	private TextView down_search_textview;
	private TextView down_shopping_textview;
	private TextView down_my_textview;
	private Resources res;
	private Button cartImage;

	/**
	 * 初始化底部导航栏
	 */
	private void initNavView() {
		// 改变底部首页背景，有按下去的效果的背景
		number = cartsDataManage.getCartAmount();
		cartImage = (Button) findViewById(R.id.down_shopping_cart);
		if (number == 0) {
			cartImage.setVisibility(View.GONE);
		} else {
			cartImage.setText(number + "");
		}
		res = getResources();
		downGuangLayout = (RelativeLayout) findViewById(R.id.down_guang_layout);
		downHomeLayout = (RelativeLayout) findViewById(R.id.down_home_layout);
		down_home_imageView = (ImageView) findViewById(R.id.down_home_icon);
		down_shopping_imageView = (ImageView) findViewById(R.id.down_shopping_icon);
		down_home_textview = (TextView) findViewById(R.id.down_home_text);
		down_shopping_textview = (TextView) findViewById(R.id.down_shopping_text);

		downGuangLayout = (RelativeLayout) findViewById(R.id.down_guang_layout);
		downSearchLayout = (RelativeLayout) findViewById(R.id.down_search_layout);
		downShoppingLayout = (RelativeLayout) findViewById(R.id.down_shopping_layout);
		downMyLayout = (RelativeLayout) findViewById(R.id.down_my_layout);

		downHomeLayout.setOnClickListener(this);
		downSearchLayout.setOnClickListener(this);
		downMyLayout.setOnClickListener(this);

		down_home_textview.setTextColor(this.getResources().getColor(
				R.color.white_white));
		downHomeLayout.setBackgroundResource(R.drawable.downbg);
		down_home_imageView.setImageResource(R.drawable.home_normal);

		down_shopping_textview.setTextColor(res.getColor(R.color.white));
		downShoppingLayout.setBackgroundResource(R.drawable.down_hover1);
		down_shopping_imageView
		.setImageResource(R.drawable.down_shopping_hover);
		downGuangLayout.setVisibility(ViewGroup.GONE);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.down_home_layout:
			intent.setClass(HomeCarActivity.this, HomeMallActivity.class);
			break;
		case R.id.down_search_layout:
			intent.setClass(HomeCarActivity.this, HomeSearchActivity.class);
			break;
		case R.id.down_my_layout:
			if (myApplication.getIsLogin())
				intent.setClass(HomeCarActivity.this, HomeMyMaActivity.class);
			else
				intent.setClass(HomeCarActivity.this, HomeLogActivity.class);
			break;
		}
		HomeCarActivity.this.startActivity(intent);

	}
	private void showLoginTips() {
		new Builder(this)
				.setTitle(getResources().getString(R.string.tips))
				.setMessage(R.string.please_login)
				.setPositiveButton(R.string.sure,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated
								// method stub
								Intent intent = new Intent(
										HomeCarActivity.this,
										LoginActivity.class);
								startActivity(intent);
							}
							//
						}).setNegativeButton(R.string.searchCancel, null)
				.create().show();
	}
	
	private void go2Pay() {
		 cartList = new ArrayList<Cart>();
			List<HashMap<String, Object>> orderCarts = CartUtil.list1;
			for(int i=0;i<orderCarts.size();i++){
				HashMap<String, Object> map = orderCarts.get(i);
				float ischeck =  Float.parseFloat(map.get("check").toString());
				Log.e("voucher", ischeck + "    ischeck");
				Cart  cart1	= (Cart) map.get("cart");
				if(ischeck == 1.0){
					cartList.add(cart1);
			}
			}
			Intent intent1 = new Intent(this,
					CstmPayActivity.class);
			Bundle bundle = new Bundle();
			sum = Float.parseFloat(sumTextView.getText()
					.toString());
			intent1.putExtra("carts", cartList);
	
			Log.i("voucher", sum + "    sum");

			
			if (sum > 0) {
				bundle.putString("cartActivity","Y");
				bundle.putString("price", sum + "");
				intent1.putExtras(bundle);
				HomeCarActivity.this.startActivity(intent1);
			} else {
				Toast.makeText(HomeCarActivity.this, getResources().getString(R.string.buy_nothing),
						Toast.LENGTH_LONG).show();
			}
		
	}
	// 双击返回键退出程序
		private long exitTime = 0;

		@Override
		public boolean onKeyUp(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				if ((System.currentTimeMillis() - exitTime) > 2000) {
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.exit),
							Toast.LENGTH_SHORT).show();
					exitTime = System.currentTimeMillis();
				} else {
					// ((MyApplication)getApplication()).setUserId("");
					// ((MyApplication)getApplication()).setToken("");
					finish();
					// System.exit(0);
				}
				return true;
			}
			return super.onKeyUp(keyCode, event);
		}
		private class InnerReceiver extends BroadcastReceiver {
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				String action = intent.getAction();
				if (Consts.UPDATE_CHANGE.equals(action)) {
					number = cartsDataManage.getCartAmount();
					frameLayout.removeAllViews();
					View view = LayoutInflater.from(HomeCarActivity.this).inflate(R.layout.shopping_cart, null);
					frameLayout.addView(view);
					setViewCtrl(view);
					
					sum = Float.parseFloat(sumTextView.getText().toString());
					cartImage.setVisibility(View.VISIBLE);
					cartImage.setText(number + "");
				}
				if(Consts.BROAD_UPDATE_CHANGE.equals(action)){
					number = cartsDataManage.getCartAmount();
					if(number == 0){
						frameLayout.removeAllViews();
						View view = LayoutInflater.from(HomeCarActivity.this).inflate(R.layout.no_produce, null);
						frameLayout.addView(view);
						shoppingCartTopay.setVisibility(View.GONE);
						setNoProduce(view);
						cartImage.setVisibility(View.GONE);
					}else{
						cartImage.setText(number+"");
					}
				}
				if(Consts.DELETE_CART.equals(action)){
					number = cartsDataManage.getCartAmount();
					{
					if(number == 0){
						frameLayout.removeAllViews();
						View view = LayoutInflater.from(HomeCarActivity.this).inflate(R.layout.no_produce, null);
						frameLayout.addView(view);
						shoppingCartTopay.setVisibility(View.GONE);
						setNoProduce(view);
						cartImage.setVisibility(View.GONE);
					}else{
						layout.removeAllViews();
						CartUtil cartUtil = new CartUtil(HomeCarActivity.this, layout,
								counTextView, sumTextView, mBox,imageLoader,listener,options);
						cartUtil.AllComment();
						cartImage.setText(number+"");
					}
					}
				}
			}
		}
}
