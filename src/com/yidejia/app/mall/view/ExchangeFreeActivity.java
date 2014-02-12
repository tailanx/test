package com.yidejia.app.mall.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yidejia.app.mall.BaseActivity;
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

/**
 * 免费送和积分换购
 * 
 * @author LiuYong
 * 
 */
public class ExchangeFreeActivity extends BaseActivity {

	private ViewPager mPager;
	private ArrayList<Fragment> fragmentsList;

	private TextView mCoupons, mIntegeral;
	private ImageView ivBottomLine;
	private int currIndex = 0;
	private int bottomLineWidth;
	private int offset = 0;
	private int position_one;
	// private int position_two;
	// private int position_three;
	private String sumprice;
	private List<HashMap<String, Float>> exchange;// 换购商品
	private List<HashMap<String, Object>> cart;// 换购商品
	private VoucherDataManage dataManage;// 用户积分
	private float voucher;// 用户积分
	private MyApplication myApplication;
	private ArrayList<Cart> mArrayList;
	// private String isString;
	private AlertDialog dialog;
	private float jifen;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		sumprice = intent.getStringExtra("price");
		jifen = intent.getFloatExtra("voucher", -1);
		// isString = intent.getStringExtra("cartActivity");
		try {
			mArrayList = (ArrayList<Cart>) intent.getSerializableExtra("carts");
		} catch (Exception e) {
		}
		setContentView(R.layout.pay_free);
		dialog = new Builder(this)
				.setIcon(R.drawable.ic_launcher)
				.setTitle(getResources().getString(R.string.tips))
				.setMessage(getResources().getString(R.string.sure_commit))
				.setPositiveButton(getResources().getString(R.string.sure),
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								Intent intent = new Intent(
										ExchangeFreeActivity.this,
										CstmPayActivity.class);
								exchange = ExchangeAdapter.mlist1;
								cart = ExchangeAdapter.mlist2;

								float sum = 0;

								float sum1 = 0;
								for (int i = 0; i < exchange.size(); i++) {
									HashMap<String, Float> map = exchange
											.get(i);

									Float isSelelct = map.get("isCheck");
									Float price = map.get("price");
									Float count = map.get("count");
									if (isSelelct == 0.0) {
										sum = price * count;

										sum1 += sum;
									}

								}
								if (sum1 > voucher) {
									Toast.makeText(
											ExchangeFreeActivity.this,
											getResources().getString(
													R.string.my_voucher),
											Toast.LENGTH_SHORT).show();
									Intent intent1 = new Intent(
											Consts.EXCHANG_FREE);
									sendBroadcast(intent1);
									return;
									// }
								} else if (sum1 <= voucher) {// && isSelelct1 ==
																// 0.0
									for (int i = 0; i < cart.size(); i++) {
										HashMap<String, Object> map1 = cart
												.get(i);
										Float isSelelct1 = Float
												.parseFloat(map1
														.get("isCheck1")
														.toString());

										Float count1 = Float.parseFloat(map1
												.get("count1").toString());

										Specials specials = (Specials) map1
												.get("cart");
										Cart cart = new Cart();

										cart.setImgUrl(specials.getImgUrl());
										cart.setPrice(0);
										cart.setScort(specials.getScores());
										cart.setProductText(specials.getBrief());
										cart.setUId(specials.getUId());
										cart.setSalledAmmount(count1.intValue());
										if (isSelelct1 == 0.0 && count1 > 0) {
											mArrayList.add(cart);
											voucher = (int) (voucher - sum1);
										}
									}
								}
								Cart cart1 = FreeGivingAdapter.carts;
								Log.i("voucher", cart1 + "   cart1");
								if (null != cart1 && cart1.getUId() != null) {
									mArrayList.add(cart1);
								}
								intent.putExtra("price", sumprice);
								intent.putExtra("voucher", voucher);
								intent.putExtra("jifen", sum1);

								intent.putExtra("carts", mArrayList);
								intent.putExtra("cartActivity", "E");
								setResult(Consts.CstmPayActivity_Response,
										intent);
								ExchangeFreeActivity.this.finish();

							}
						})
				.setNegativeButton(getResources().getString(R.string.cancel),
						null).create();
		if (mArrayList == null) {
			Toast.makeText(ExchangeFreeActivity.this,
					getResources().getString(R.string.no_network),
					Toast.LENGTH_SHORT).show();
		}
		dataManage = new VoucherDataManage(ExchangeFreeActivity.this);
		myApplication = (MyApplication) getApplication();
		if (jifen == -1) {
			voucher = Float.parseFloat(dataManage.getUserVoucher(
					myApplication.getUserId(), myApplication.getToken()));
		} else {
			voucher = jifen;
		}

		InitWidth();
		InitTextView();
		InitViewPager();
		// setActionBar();
		setActionbarConfig();
		setTitle(getResources().getString(R.string.produce_exchange));

		TextView tvComplete = (TextView) findViewById(R.id.ab_common_tv_right);
		tvComplete.setVisibility(View.VISIBLE);
		tvComplete.setText(R.string.complete);
		tvComplete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.show();
			}
		});
	}

	private void setActionBar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setCustomView(R.layout.actionbar_search_result);
		ImageView back = (ImageView) findViewById(R.id.actionbar_left);
		Button confirm = (Button) findViewById(R.id.actionbar_right);
		confirm.setText(getResources().getString(R.string.complete));
		TextView titleTextView = (TextView) findViewById(R.id.actionbar_title);
		titleTextView.setText(getResources().getString(
				R.string.produce_exchange));
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ExchangeFreeActivity.this,
						CstmPayActivity.class);

				Bundle bundle = new Bundle();
				bundle.putString("price", sumprice);
				bundle.putFloat("jifen", jifen);
				intent.putExtra("cartActivity", "E");
				bundle.putSerializable("carts", mArrayList);
				intent.putExtras(bundle);
				ExchangeFreeActivity.this.setResult(
						Consts.CstmPayActivity_Response, intent);
				ExchangeFreeActivity.this.finish();
			}
		});

		confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.show();
			}
		});

	}

	private void InitTextView() {
		mIntegeral = (TextView) findViewById(R.id.pay_free_voucher_Coupons);// 赠送
		mCoupons = (TextView) findViewById(R.id.pay_free_voucher_mIntegeral);// 换购
		mIntegeral.setSelected(true);
		mCoupons.setOnClickListener(new MyOnClickListener(1));
		mIntegeral.setOnClickListener(new MyOnClickListener(0));
	}

	private void InitViewPager() {
		mPager = (ViewPager) findViewById(R.id.avPager);
		fragmentsList = new ArrayList<Fragment>();
		// LayoutInflater mInflater = getLayoutInflater();

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

		// position_one = (int) (screenW / 3);
		// position_two = position_one * 2;

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
					mCoupons.setSelected(false);
					animation = new TranslateAnimation(position_one, 0, 0, 0);
				}
				mIntegeral.setSelected(true);
				break;
			case 1:
				if (currIndex == 0) {
					mIntegeral.setSelected(false);
					animation = new TranslateAnimation(offset, position_one, 0,
							0);
				}
				mCoupons.setSelected(true);
				break;

			}
			currIndex = arg0;
			animation.setFillAfter(true);
			animation.setDuration(300);
			ivBottomLine.startAnimation(animation);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (!mArrayList.isEmpty())
			mArrayList.clear();
	}
}
