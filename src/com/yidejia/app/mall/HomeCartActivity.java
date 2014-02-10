package com.yidejia.app.mall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.search.SearchResultActivity;
import com.yidejia.app.mall.util.CartUtil1;
import com.yidejia.app.mall.view.CstmPayActivity;
import com.yidejia.app.mall.view.LoginActivity;

//import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
//import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 购物车的界面
 * 
 * @author Administrator
 * 
 */
public class HomeCartActivity extends HomeBaseActivity {
	private TextView shoppingCartTopay;// 编辑
	private MyApplication myApplication;
	private FrameLayout frameLayout;
	private LayoutInflater inflater;
	private View view;
	private TextView sumTextView;// 总的钱数
	private TextView counTextView;// 总的数量
	private CheckBox mBox;
	private CartUtil1 cartUtil;
	private LinearLayout layout;
	public static ArrayList<Cart> cartList;
	public static float sum;
	private Button mButton;
	// private ImageLoader imageLoader;
	// private ImageLoadingListener listener;
	// private DisplayImageOptions options;

	// private BottomChange bottomChange;

	// private RelativeLayout downHomeLayout;
	// private RelativeLayout downGuangLayout;
	// private RelativeLayout downSearchLayout;
	// private RelativeLayout downShoppingLayout;
	// private RelativeLayout downMyLayout;
	private CartsDataManage cartsDataManage;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		myApplication = MyApplication.getInstance();
		setContentView(R.layout.activity_main_fragment_layout);
		frameLayout = (FrameLayout) findViewById(R.id.main_fragment);
		inflater = LayoutInflater.from(this);

		// 设置底部,获取当前的界面的id
		// int current = getIntent().getIntExtra("current", -1);
		// int next = getIntent().getIntExtra("next", -1);
		// bottomLayout = (RelativeLayout)
		// findViewById(R.id.down_parent_layout);
		// bottomChange = new BottomChange(this, bottomLayout);
		// if (current != -1 || next != -1) {
		// bottomChange.initNavView(current, next);
		// }

		setActionBarConfig();
		setCurrentActivityId(3);

		cartsDataManage = new CartsDataManage();
	}
	
	private void refreshView(){
		if (cartsDataManage.getCartAmount() != 0) {
			view = inflater.inflate(R.layout.shopping_cart, null);
			frameLayout.addView(view);
			setViewCtrl(view);
		} else {
			shoppingCartTopay.setVisibility(View.GONE);
			view = inflater.inflate(R.layout.no_produce, null);
			frameLayout.addView(view);
			setNoProduce(view);
		}

	}

	private void setNoProduce(View view) {
		mButton = (Button) view.findViewById(R.id.no_produce_button);
		mButton.setSelected(true);
		mButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(HomeCartActivity.this,
						SearchResultActivity.class);
				Bundle bundle = new Bundle();
				Log.e("info", "gouwuche");
				bundle.putString("title", "全部");
				bundle.putString("name", "");
				bundle.putString("price", "");
				bundle.putString("brand", "");
				bundle.putString("fun", "");
				intent.putExtras(bundle);
				HomeCartActivity.this.startActivity(intent);
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// imageLoader.init(ImageLoaderConfiguration
		// .createDefault(HomeCarActivity.this));
		// imageLoader.stop();
	}

	private void setViewCtrl(View view) {
		sumTextView = (TextView) view
				.findViewById(R.id.shopping_cart_sum_money);// 总的钱数

		mBox = (CheckBox) view.findViewById(R.id.shopping_cart_checkbox);// 选择框
		shoppingCartTopay = (TextView) findViewById(R.id.tv_shopping_cart_gopay);

		counTextView = (TextView) view
				.findViewById(R.id.shopping_cart_sum_number);// 总的数量
		try {
			shoppingCartTopay.setVisibility(View.VISIBLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		ScrollView scrollView = (ScrollView) view
				.findViewById(R.id.shopping_cart_item_goods_scrollView);

		cartUtil = new CartUtil1(this, layout, counTextView, sumTextView, mBox);

		cartUtil.AllComment();

		scrollView.addView(layout);

		if (shoppingCartTopay == null) {

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

	/**
	 * 头部
	 */
	private void setActionBarConfig() {
		setActionbarConfig();
		TextView leftButton = (TextView) findViewById(R.id.ab_common_back);
		leftButton.setVisibility(View.GONE);
		shoppingCartTopay = (TextView) findViewById(R.id.ab_common_tv_right);
		shoppingCartTopay.setVisibility(View.VISIBLE);
		shoppingCartTopay.setText(getResources().getString(
				R.string.addresss_edit));
		setTitle(getResources().getString(R.string.down_shopping_text));
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
										HomeCartActivity.this,
										LoginActivity.class);
								startActivity(intent);
							}
							//
						}).setNegativeButton(R.string.searchCancel, null)
				.create().show();
	}

	private void go2Pay() {
		cartList = new ArrayList<Cart>();
		List<HashMap<String, Object>> orderCarts = CartUtil1.list1;
		for (int i = 0; i < orderCarts.size(); i++) {
			HashMap<String, Object> map = orderCarts.get(i);
			float ischeck = Float.parseFloat(map.get("check").toString());
			Log.e("voucher", ischeck + "    ischeck");
			Cart cart1 = (Cart) map.get("cart");
			if (ischeck == 1.0) {
				cartList.add(cart1);
			}
		}
		Intent intent1 = new Intent(this, CstmPayActivity.class);
		Bundle bundle = new Bundle();
		sum = Float.parseFloat(sumTextView.getText().toString());
		intent1.putExtra("carts", cartList);

		Log.i("voucher", sum + "    sum");

		if (sum > 0) {
			bundle.putString("cartActivity", "Y");
			bundle.putFloat("price", sum);
			intent1.putExtras(bundle);
			HomeCartActivity.this.startActivity(intent1);
		} else {
			Toast.makeText(HomeCartActivity.this,
					getResources().getString(R.string.buy_nothing),
					Toast.LENGTH_LONG).show();
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPageEnd(this, getString(R.string.down_shopping_text));
	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, getString(R.string.down_shopping_text));
		refreshView();
	}
}
