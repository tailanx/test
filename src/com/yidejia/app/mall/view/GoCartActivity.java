package com.yidejia.app.mall.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
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
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.datamanage.PreferentialDataManage;
import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.util.CartUtil;

public class GoCartActivity extends SherlockActivity {// implements
														// OnClickListener
	private AlertDialog dialog;
	private TextView sumTextView;// �ܵ�Ǯ��
	private TextView counTextView;// �ܵ�����
	private CheckBox mBox;// ѡ���
	private Button mbutton;// ȥ����
	private CartUtil cartUtil;
	private AddressDataManage addressManage;// ��ַ�������
	private PullToRefreshScrollView mPullToRefreshScrollView;// ����ˢ��
	private ImageView mImageView;// ����
	private TextView mTextView;// title
	private MyApplication myApplication;
	private CartsDataManage dataManage;
	private PreferentialDataManage preferentialDataManage ;
	public static ArrayList<Cart> cartList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping_cart);
		myApplication = (MyApplication) getApplication();
		addressManage = new AddressDataManage(GoCartActivity.this);
		preferentialDataManage = new PreferentialDataManage(GoCartActivity.this);
		dataManage = new CartsDataManage();
		mBox = (CheckBox) findViewById(R.id.shopping_cart_checkbox);// ѡ���

		sumTextView = (TextView) findViewById(R.id.shopping_cart_sum_money);// �ܵ�Ǯ��

		counTextView = (TextView) findViewById(R.id.shopping_cart_sum_number);// �ܵ�����

//		mPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.shopping_cart_item_goods_scrollView);
//		String label = getResources().getString(R.string.update_time)
//				+ DateUtils.formatDateTime(GoCartActivity.this,
//						System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_ALL
//								| DateUtils.FORMAT_SHOW_DATE
//								| DateUtils.FORMAT_SHOW_TIME);
//		mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
//				label);
//		mPullToRefreshScrollView.setOnRefreshListener(listener);

//		LinearLayout layout = (LinearLayout) findViewById(R.id.shopping_cart_relative2);
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		ScrollView scrollView = (ScrollView) findViewById(R.id.shopping_cart_item_goods_scrollView);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		GoCartActivity.this.getSupportActionBar().setCustomView(
				R.layout.actionbar_common);
		cartUtil = new CartUtil(GoCartActivity.this, layout, counTextView,
				sumTextView, mBox);
		cartUtil.AllComment();
		mbutton = (Button) findViewById(R.id.actionbar_right);
		mTextView = (TextView) findViewById(R.id.actionbar_title);
		mImageView = (ImageView) findViewById(R.id.actionbar_left);
		mTextView.setText(getResources().getString(R.string.gocart));

		try {
			mImageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					GoCartActivity.this.finish();
				}
			});
			dialog = new Builder(GoCartActivity.this)
			.setTitle("换购商品")
			.setIcon(android.R.drawable.dialog_frame)
			.setMessage(
					getResources().getString(R.string.exchange_produce))
			.setPositiveButton(
					"确定",
					new android.content.DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0,
								int arg1) {
							Intent intent = new Intent(
									GoCartActivity.this,
									ExchangeFreeActivity.class);
							Bundle bundle = new Bundle();
							float sum = Float.parseFloat(sumTextView
									.getText().toString());
							bundle.putString("price", sum + "");
							intent.putExtras(bundle);
							GoCartActivity.this.startActivity(intent);

						}
					})
			.setNegativeButton(
					"取消",
					new android.content.DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(
									GoCartActivity.this,
									CstmPayActivity.class);
							Bundle bundle = new Bundle();
							float sum = Float.parseFloat(sumTextView
									.getText().toString());
							bundle.putString("price", sum + "");
							intent.putExtras(bundle);
							GoCartActivity.this.startActivity(intent);

						}
					}).create();

	if (mbutton == null) {
		Toast.makeText(
				GoCartActivity.this,
				GoCartActivity.this.getResources().getString(
						R.string.no_network), Toast.LENGTH_SHORT)
				.show();
	} else {
		mbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// getAddresses();
				if (!myApplication.getIsLogin()) {
					new Builder(GoCartActivity.this)
							.setTitle(
									getResources().getString(
											R.string.tips))
							.setMessage(R.string.please_login)
							.setPositiveButton(
									R.string.sure,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated
											// method stub
											Intent intent = new Intent(
													GoCartActivity.this,
													LoginActivity.class);
											startActivity(intent);
										}
										//
									})
							.setNegativeButton(
									R.string.searchCancel,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated
											// method stub

										}
										//
									}).create().show();
					//
					return;
				} else {
					 cartList = new ArrayList<Cart>();
					List<HashMap<String, Object>> orderCarts = CartUtil.list1;
					for(int i=0;i<orderCarts.size();i++){
						HashMap<String, Object> map = orderCarts.get(i);
						float ischeck =  Float.parseFloat(map.get("check").toString());
						Log.i("info", ischeck + "    ischeck");
						Cart  cart1	= (Cart) map.get("cart");
						if(ischeck == 1.0){
							cartList.add(cart1);
					}
					}
					Intent intent1 = new Intent(GoCartActivity.this,
							CstmPayActivity.class);
					Bundle bundle = new Bundle();
					float sum = Float.parseFloat(sumTextView.getText()
							.toString());
					intent1.putExtra("carts", cartList);
			
//						for(int i = 0; i<mList.size();i++){
//							Cart cart = new Cart();
//							sb.append(cart.getUId());
//							sb.append(",");
//							sb.append(cart.getAmount());
//							sb.append("n");
//							sb.append(";");
//						}
//						preferentialDataManage.getPreferential(sb.toString(),myApplication.getUserId());
//						if (preferentialDataManage.getFreeGoods().size() != 0
//								|| preferentialDataManage.getScoreGoods().size() != 0) {
//							Intent intent = new Intent(GoCartActivity.this,
//									ExchangeFreeActivity.class);
//							
//						}else{
					
					if (sum > 0) {
						bundle.putString("cartActivity","N");
						bundle.putString("price", sum + "");
						intent1.putExtras(bundle);
						GoCartActivity.this.startActivity(intent1);
					} else {
						Toast.makeText(GoCartActivity.this, getResources().getString(R.string.buy_nothing),
								Toast.LENGTH_LONG).show();
					}
				}
					}
//				}
			});
	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(GoCartActivity.this, getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT)
					.show();
		}
		//
		//
		scrollView.addView(layout);
	}

	private OnRefreshListener<ScrollView> listener = new OnRefreshListener<ScrollView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
			// TODO Auto-generated method stub
			String label =getResources().getString(R.string.update_time)
					+ DateUtils.formatDateTime(GoCartActivity.this,
							System.currentTimeMillis(),
							DateUtils.FORMAT_ABBREV_ALL
									| DateUtils.FORMAT_SHOW_DATE
									| DateUtils.FORMAT_SHOW_TIME);
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
	// mBox = (CheckBox) findViewById(R.id.go_cart_checkbox);// ѡ���
	//
	// sumTextView = (TextView)findViewById(R.id.shopping_cart_sum_money);//
	// �ܵ�Ǯ��
	//
	// counTextView = (TextView)findViewById(R.id.shopping_cart_sum_number);//
	// �ܵ�����
	//
	// LinearLayout layout = (LinearLayout)
	// findViewById(R.id.go_cart_relative1);
	// cartUtil = new CartUtil(this, layout, counTextView, sumTextView, mBox);
	// cartUtil.AllComment();
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// Toast.makeText(GoCartActivity.this, "���粻������", Toast.LENGTH_SHORT).show();
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
	// Addresses address = null;

	// /**
	// *
	// * @return ����һ����ַ
	// */
	// private void getAddresses() {
	//
	// String userId = ((MyApplication) getApplication()).getUserId();
	// ArrayList<Addresses> mAddresses = addressManage.getAddressesArray(
	// userId, 0, 5);
	// if (mAddresses.size() == 0) {
	// Intent intent = new Intent(GoCartActivity.this,
	// NewAddressActivity.class);
	// GoCartActivity.this.startActivity(intent);
	// GoCartActivity.this.finish();
	// // Log.i("info", "nihao");
	//
	// } else {
	// address = mAddresses.remove(0);
	// // Log.i("info", address + "address");
	// }
	//
	// }

}
