package com.yidejia.app.mall.view;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.AddressDataManage;
import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.util.CartUtil;

public class GoCartActivity extends SherlockActivity {// implements
														// OnClickListener

	private TextView sumTextView;// 总的钱数
	private TextView counTextView;// 总的数量
	private CheckBox mBox;// 选择框
	private Button mbutton;// 去结算
	private CartUtil cartUtil;
	private AddressDataManage addressManage;// 地址管理数据
	private PullToRefreshScrollView mPullToRefreshScrollView;// 界面刷新

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping_cart);
		addressManage = new AddressDataManage(GoCartActivity.this);

		mBox = (CheckBox) findViewById(R.id.shopping_cart_checkbox);// 选择框

		sumTextView = (TextView) findViewById(R.id.shopping_cart_sum_money);// 总的钱数

		counTextView = (TextView) findViewById(R.id.shopping_cart_sum_number);// 总的数量

		mPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.shopping_cart_item_goods_scrollView);
		String label = "上次更新于"
				+ DateUtils.formatDateTime(GoCartActivity.this,
						System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_ALL
								| DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_SHOW_TIME);
		mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		mPullToRefreshScrollView.setOnRefreshListener(listener);
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.shopping_cart_relative2);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		GoCartActivity.this.getSupportActionBar().setCustomView(
				R.layout.actionbar_cart);
		cartUtil = new CartUtil(GoCartActivity.this, layout, counTextView,
				sumTextView, mBox);
		cartUtil.AllComment();
		mbutton = (Button) findViewById(R.id.shopping_cart_go_pay);

		try {
			mbutton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					getAddresses();
					Intent intent = new Intent(GoCartActivity.this,
							PayActivity.class);
					float sum = Float.parseFloat(sumTextView.getText()
							.toString());
					if (address == null) {
						Toast.makeText(GoCartActivity.this, "您还未填写收货人地址",
								Toast.LENGTH_LONG).show();
					} else {
						if (sum > 0) {
							Bundle bundle = new Bundle();
							bundle.putSerializable("address", address);
							bundle.putString("price", sum + "");
							intent.putExtras(bundle);
							GoCartActivity.this.startActivity(intent);
						} else {
							Toast.makeText(GoCartActivity.this, "你还未购买任何商品",
									Toast.LENGTH_LONG).show();
						}
					}
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(GoCartActivity.this, "网络不给力！", Toast.LENGTH_SHORT)
					.show();
		}
		//
		//
	}
	private OnRefreshListener<ScrollView> listener = new OnRefreshListener<ScrollView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
			// TODO Auto-generated method stub
			String label = "上次更新于" + DateUtils.formatDateTime(GoCartActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_ALL|DateUtils.FORMAT_SHOW_DATE|DateUtils.FORMAT_SHOW_TIME);
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			mPullToRefreshScrollView.onRefreshComplete();
		}
	};
	// try {
	// TODO Auto-generated method stub
	// requestWindowFeature(Window.FEATURE_NO_TITLE);
	// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

	// mbutton = (Button) findViewById(R.id.go_cart_go_pay);
	// mbutton.setOnClickListener(this);
	//
	// mBox = (CheckBox) findViewById(R.id.go_cart_checkbox);// 选择框
	//
	// sumTextView = (TextView)findViewById(R.id.shopping_cart_sum_money);//
	// 总的钱数
	//
	// counTextView = (TextView)findViewById(R.id.shopping_cart_sum_number);//
	// 总的数量
	//
	// LinearLayout layout = (LinearLayout)
	// findViewById(R.id.go_cart_relative1);
	// cartUtil = new CartUtil(this, layout, counTextView, sumTextView, mBox);
	// cartUtil.AllComment();
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// Toast.makeText(GoCartActivity.this, "网络不给力！", Toast.LENGTH_SHORT).show();
	//
	// }
	//
	// }
	// @Override
	// public void onClick(View v) {
	// switch (v.getId()) {
	// case R.id.go_cart_go_pay:
	// Intent intent = new Intent(GoCartActivity.this,PayActivity.class);
	// GoCartActivity.this.startActivity(intent);
	// break;
	//
	// }
	//
	// }
	//
	/**
	 * 
	 * @return返回一个地址
	 */
	Addresses address = null;

	private void getAddresses() {

		String userId = ((MyApplication) getApplication()).getUserId();
		ArrayList<Addresses> mAddresses = addressManage.getAddressesArray(
				Integer.parseInt(userId), 0, 5);
		if (mAddresses.size() == 0) {
			Intent intent = new Intent(GoCartActivity.this,
					NewAddressActivity.class);
			GoCartActivity.this.startActivity(intent);
			GoCartActivity.this.finish();
			// Log.i("info", "nihao");

		} else {
			address = mAddresses.remove(0);
			// Log.i("info", address + "address");
		}

	}

}
//
// package com.yidejia.app.mall.view;
//
// import android.content.Intent;
// import android.os.Bundle;
// import android.view.View;
// import android.view.View.OnClickListener;
// import android.widget.Button;
// import android.widget.CheckBox;
// import android.widget.LinearLayout;
// import android.widget.TextView;
//
// import com.actionbarsherlock.app.SherlockActivity;
// import com.yidejia.app.mall.R;
// import com.yidejia.app.mall.util.CartUtil;

// public class GoCartActivity extends SherlockActivity {//implements
// OnClickListener
//
// private TextView sumTextView;// 总的钱数
// private TextView counTextView;// 总的数量
// private CheckBox mBox;//选择框
// private Button mbutton;//去结算
// private CartUtil cartUtil;
//
// @Override
// protected void onCreate(Bundle savedInstanceState) {
// super.onCreate(savedInstanceState);
// setContentView(R.layout.shopping_cart);
//
// mBox = (CheckBox) findViewById(R.id.shopping_cart_checkbox);// 选择框
//
// sumTextView = (TextView) findViewById(R.id.shopping_cart_sum_money);// 总的钱数
//
// counTextView = (TextView)findViewById(R.id.shopping_cart_sum_number);// 总的数量
//
// LinearLayout layout = (LinearLayout)
// findViewById(R.id.shopping_cart_relative2);
// getSupportActionBar().setDisplayShowCustomEnabled(true);
// getSupportActionBar().setDisplayHomeAsUpEnabled(false);
// getSupportActionBar().setDisplayShowHomeEnabled(false);
// GoCartActivity.this.getSupportActionBar().setCustomView(
// R.layout.actionbar_cart);
// cartUtil = new CartUtil(GoCartActivity.this, layout, counTextView,
// sumTextView,mBox);
// cartUtil.AllComment();
// mbutton = (Button)findViewById(
// R.id.shopping_cart_go_pay);
//
// mbutton.setOnClickListener(new OnClickListener() {
//
// @Override
// public void onClick(View v) {
// // TODO Auto-generated method stub
// Intent intent = new Intent(GoCartActivity.this,
// PayActivity.class);
// Bundle bundle = new Bundle();
// bundle.putString("price", sumTextView.getText().toString());
// intent.putExtras(bundle);
// GoCartActivity.this.startActivity(intent);
// }
// });
// //
// //
// }
// // try {
// // TODO Auto-generated method stub
// // requestWindowFeature(Window.FEATURE_NO_TITLE);
// //
// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
// // mbutton = (Button) findViewById(R.id.go_cart_go_pay);
// // mbutton.setOnClickListener(this);
// //
// // mBox = (CheckBox) findViewById(R.id.go_cart_checkbox);// 选择框
// //
// // sumTextView = (TextView)findViewById(R.id.shopping_cart_sum_money);// 总的钱数
// //
// // counTextView = (TextView)findViewById(R.id.shopping_cart_sum_number);//
// 总的数量
// //
// // LinearLayout layout = (LinearLayout) findViewById(R.id.go_cart_relative1);
// // cartUtil = new CartUtil(this, layout, counTextView, sumTextView, mBox);
// // cartUtil.AllComment();
// // } catch (Exception e) {
// // // TODO Auto-generated catch block
// // e.printStackTrace();
// // Toast.makeText(GoCartActivity.this, "网络不给力！", Toast.LENGTH_SHORT).show();
// //
// // }
// //
// // }
// // @Override
// // public void onClick(View v) {
// // switch (v.getId()) {
// // case R.id.go_cart_go_pay:
// // Intent intent = new Intent(GoCartActivity.this,PayActivity.class);
// // GoCartActivity.this.startActivity(intent);
// // break;
// //
// // }
// //
// // }
// //
//
// }

