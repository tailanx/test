package com.yidejia.app.mall.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog.Builder;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.adapter.IntegeralFragmentAdapter;
import com.yidejia.app.mall.datamanage.VoucherDataManage;
import com.yidejia.app.mall.fragment.ExchangeAdapter;
import com.yidejia.app.mall.fragment.ExchangeFragment;
import com.yidejia.app.mall.fragment.FreeGivingAdapter;
import com.yidejia.app.mall.fragment.FreeGivingFragment;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.Specials;
import com.yidejia.app.mall.util.Consts;

public class ExchangeFreeActivity extends SherlockFragmentActivity {

	private static final String TAG = "MainActivity";
	private ViewPager mPager;
	private ArrayList<Fragment> fragmentsList;

	private TextView mCoupons, mIntegeral;
	private ImageView ivBottomLine;
	private int currIndex = 0;
	private int bottomLineWidth;
	private int offset = 0;
	private int position_one;
	private int position_two;
	private int position_three;
	private Resources resources;
	private String sumprice;
	private List<HashMap<String, Float>> exchange;// 换购商品
	private List<HashMap<String, Object>> cart;// 换购商品
	private VoucherDataManage dataManage;// 用户积分
	private double voucher;// 用户积分
	private MyApplication myApplication;
	// private CartActivity cartActivity;
	private ArrayList<Cart> mArrayList;
	private String isString;
	private AlertDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// cartActivity = new CartActivity();
		setContentView(R.layout.pay_free);
		resources = getResources();
		dialog = new Builder(this).setIcon(R.drawable.ic_launcher).setTitle(getResources()
				.getString(R.string.tips)).setMessage(getResources().getString(R.string.sure_commit))
				.setPositiveButton(getResources().getString(R.string.sure),new android.content.DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub


						Intent intent = new Intent(ExchangeFreeActivity.this,
								CstmPayActivity.class);
//						intent.setClass(ExchangeFreeActivity.this,
//								CstmPayActivity.class);
//						Bundle bundle = new Bundle();

						// TODO Auto-generated method stub
//						ExchangeFreeActivity.this.finish();
						exchange = ExchangeAdapter.mlist1;
						cart = ExchangeAdapter.mlist2;

						// Log.i("info", exchange.toString() +
						// "    exchange.toString()");
						float sum1 = 0;
						float sum = 0;

						for (int i = 0; i < exchange.size(); i++) {
							HashMap<String, Float> map = exchange.get(i);
							// HashMap<String, Object> map1 = cart.get(i);

							Float isSelelct = map.get("isCheck");
							Float price = map.get("price");
							Float count = map.get("count");

							// Float isSelelct1 = Float.parseFloat(map1.get("isCheck1")
							// .toString());
							// Specials specials = (Specials) map1.get("cart");
							// Cart cart = new Cart();
							//
							// cart.setImgUrl(specials.getImgUrl());
							// cart.setPrice(0);
							// cart.setProductText(specials.getBrief());
							// cart.setUId(specials.getUId());
							// cart.setSalledAmmount(count.intValue());
							// Log.i("info", count+"    count");
							// Log.i("info", isSelelct1+"    isSelelct1");
							// Log.i("info", price+"    price");
							// Log.i("info", cart+"    cart");
							if (isSelelct == 0.0) {
								sum = price * count;
								// Log.i("info", sum + "   sum");

								sum1 += sum;
							}

							 Log.i("info", sum1+"    sum1");
							// sum1 =sum1/exchange.size()+1;
						}
						if (sum1 > voucher) {
							Toast.makeText(ExchangeFreeActivity.this,
									getResources().getString(R.string.my_voucher),
									Toast.LENGTH_SHORT).show();
							Toast.makeText(
									ExchangeFreeActivity.this,
									getResources().getString(R.string.show_voucher)
											+ voucher, Toast.LENGTH_SHORT).show();
							return;
						} else if (sum1 <= voucher) {// && isSelelct1 == 0.0
							for (int i = 0; i < cart.size(); i++) {
								HashMap<String, Object> map1 = cart.get(i);
								Float isSelelct1 = Float.parseFloat(map1
										.get("isCheck1").toString());
								Float count1 = Float.parseFloat(map1.get("count1")
										.toString());

								Specials specials = (Specials) map1.get("cart");
								Cart cart = new Cart();

								cart.setImgUrl(specials.getImgUrl());
								float a = (float) 0.1;
								cart.setPrice(Float.parseFloat(specials.getScores())+a);
								cart.setProductText(specials.getBrief());
								cart.setUId(specials.getUId());
								cart.setSalledAmmount(count1.intValue());
								// if(mArrayList==null){
								// Toast.makeText(ExchangeFreeActivity.this,
								// getResources().getString(R.string.no_network),
								// Toast.LENGTH_SHORT).show();
								// }
								boolean isExsist = false;
								if (isSelelct1 == 0.0) {
//									for(int j=0;i<mArrayList.size();j++){
//										Cart  cartList = mArrayList.get(j);
//										if(cartList.getUId().equals(cart.getUId())){
//											cartList.setSalledAmmount(cartList.getAmount()+cart.getAmount());
//											isExsist = true;
//											break;
//										}
		//
//									}
//									if(!isExsist)mArrayList.add(cart);
									mArrayList.add(cart);
									voucher = (int) (voucher - sum1);
								}
							}
							Log.i("info", voucher+"   vouche1r");
							// Cart cart1 = new
							// FreeGivingAdapter(ExchangeFreeActivity.this,
							// CstmPayActivity.arrayListFree).getCart();
							Cart cart1 = FreeGivingAdapter.carts;
							if (cart1.getUId() != null) {
								Log.i("info", mArrayList+"   mArrayList");
								mArrayList.add(cart1);
							}
							intent.putExtra("price", sumprice);
							Log.i("info", voucher+"   voucher");
							intent.putExtra("voucher", voucher);
							intent.putExtra("carts", mArrayList);
							intent.putExtra("cartActivity", "E");
						}
						setResult(Consts.CstmPayActivity_Response, intent);
						ExchangeFreeActivity.this.finish();
					
					}
				})
				.setNegativeButton(getResources().getString(R.string.cancel), null).create();				
		Intent intent = getIntent();
		sumprice = intent.getStringExtra("price");
		// Log.i("info", sumprice);
		Double jifen = intent.getDoubleExtra("voucher", -1);
		isString = intent.getStringExtra("cartActivity");
		 Log.i("info", jifen +"  jifen");
		mArrayList = (ArrayList<Cart>) intent.getSerializableExtra("carts");
		//		if (isString.equals("Y")) {
//		} else {
//			mArrayList = (ArrayList<Cart>) intent.getSerializableExtra("carts");
//		}
		if (mArrayList == null) {
			Toast.makeText(ExchangeFreeActivity.this,
					getResources().getString(R.string.no_network),
					Toast.LENGTH_SHORT).show();
		}
		// Log.i("info", jifen + "jifen");
		dataManage = new VoucherDataManage(ExchangeFreeActivity.this);
		myApplication = (MyApplication) getApplication();
		if (jifen == -1) {
			voucher = Double.parseDouble(dataManage.getUserVoucher(
					myApplication.getUserId(), myApplication.getToken()));
		} else {
			voucher = jifen;
		}

		InitWidth();
		InitTextView();
		InitViewPager();
		setActionBar();

	}

	private void setActionBar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setCustomView(R.layout.actionbar_common);
		ImageView back = (ImageView) findViewById(R.id.actionbar_left);
		Button confirm = (Button) findViewById(R.id.actionbar_right);
		confirm.setText(getResources().getString(R.string.complete));
		TextView titleTextView = (TextView) findViewById(R.id.actionbar_title);
		titleTextView.setText(getResources().getString(
				R.string.produce_exchange));
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ExchangeFreeActivity.this,
						CstmPayActivity.class);

				Bundle bundle = new Bundle();
				bundle.putString("price", sumprice);
				intent.putExtra("cartActivity", "E");
				bundle.putSerializable("carts", mArrayList);
				intent.putExtras(bundle);
				ExchangeFreeActivity.this.setResult(Consts.CstmPayActivity_Response, intent);
//				ExchangeFreeActivity.this.startActivity(intent);
				ExchangeFreeActivity.this.finish();
			}
		});

		confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.show();
				}
		});

		// confirm.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// //将免费赠送和换购商品加入购物车
		// float sum = 0;
		// for(int i=0;i<exchange.size();i++){
		// HashMap<String,Float> map = exchange.get(i);
		// Float isSelelct= map.get("isCheck");
		// Float price= map.get("price");
		// Float count =map.get("count");
		// // Log.i("info", count+"    count");
		// // Log.i("info", isSelelct+"    isCheck");
		// // Log.i("info", price+"    price");
		//
		// if(isSelelct == 0.0){
		// sum = price*count;
		//
		// }
		// sum1 += sum;
		// if(sum1<voucher){
		// Toast.makeText(ExchangeFreeActivity.this,
		// getResources().getString(R.string.my_voucher),
		// Toast.LENGTH_SHORT).show();
		// Toast.makeText(ExchangeFreeActivity.this,
		// getResources().getString(R.string.show_voucher)+voucher,
		// Toast.LENGTH_SHORT).show();
		//
		// }else{
		// // Log.i("info", sum1+"    sum1");
		//
		// Intent intent = new Intent(ExchangeFreeActivity.this,
		// CstmPayActivity.class);
		// Bundle bundle = new Bundle();
		// bundle.putString("price", price + "");
		// intent.putExtras(bundle);
		// ExchangeFreeActivity.this.startActivity(intent);
		// ExchangeFreeActivity.this.finish();
		// }
		// }
		//
		// }
		// });

	}

	private void InitTextView() {
		mIntegeral = (TextView) findViewById(R.id.pay_free_voucher_Coupons);
		mCoupons = (TextView) findViewById(R.id.pay_free_voucher_mIntegeral);
		mCoupons.setOnClickListener(new MyOnClickListener(1));
		mIntegeral.setOnClickListener(new MyOnClickListener(0));
	}

	private void InitViewPager() {
		mPager = (ViewPager) findViewById(R.id.avPager);
		fragmentsList = new ArrayList<Fragment>();
		LayoutInflater mInflater = getLayoutInflater();

		Fragment freeGivingFragment = FreeGivingFragment
				.newInstance("zengsong");
		Fragment exchangeFragment = ExchangeFragment.newInstance("huangou");

		fragmentsList.add(freeGivingFragment);
		fragmentsList.add(exchangeFragment);

		mPager.setAdapter(new IntegeralFragmentAdapter(this
				.getSupportFragmentManager(), fragmentsList));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());

	}

	private void InitWidth() {
		ivBottomLine = (ImageView) findViewById(R.id.iv_bottom_line);// 滑动
		bottomLineWidth = ivBottomLine.getLayoutParams().width;
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);// 获取当前屏幕的属性
		int screenW = dm.widthPixels;// 屏幕的宽
		offset = (int) ((screenW / 3 - bottomLineWidth) / 2);// 起始位置

		position_one = (int) (screenW / 3);
		position_two = position_one * 2;

	}

	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:// 假如是第一个被选中，添加事件
				if (currIndex == 1) {// 当前是第二个
					mIntegeral.setPressed(false);
					mIntegeral
							.setBackgroundResource(R.drawable.product_details_selected);
					animation = new TranslateAnimation(position_one, 0, 0, 0);
					mIntegeral.setTextColor(Color.parseColor("#702c91"));
				}
				mCoupons.setPressed(true);
				mCoupons.setBackgroundResource(R.drawable.product_details_bg);
				mCoupons.setTextColor(Color.parseColor("#ed217c"));
				break;
			case 1:
				if (currIndex == 0) {
					mCoupons.setPressed(false);
					mCoupons.setBackgroundResource(R.drawable.product_details_selected);
					animation = new TranslateAnimation(offset, position_one, 0,
							0);
					mCoupons.setTextColor(Color.parseColor("#702c91"));
				}
				mIntegeral.setPressed(true);
				mIntegeral.setBackgroundResource(R.drawable.product_details_bg);
				mIntegeral.setTextColor(Color.parseColor("#ed217c"));
				break;

			}
			currIndex = arg0;
			animation.setFillAfter(true);
			animation.setDuration(300);
			ivBottomLine.startAnimation(animation);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}
	}

}
