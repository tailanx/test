package com.yidejia.app.mall.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog.Builder;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
//import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
//import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
//import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.datamanage.PreferentialDataManage;
import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.Specials;
//import com.yidejia.app.mall.model.UserComment;
import com.yidejia.app.mall.util.CartUtil;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.view.CstmPayActivity;
import com.yidejia.app.mall.view.ExchangeFreeActivity;
import com.yidejia.app.mall.view.LoginActivity;

//import com.yidejia.app.mall.view.PayActivity;

public class CartActivity extends SherlockFragment implements OnClickListener {
	private AlertDialog dialog;
	private ImageView subtract;// 减
	private TextView number;
	private ImageView addImageView;

	private ImageView subtract2;// 减
	private TextView number2;
	private ImageView addImageView2;
	private int sumString;
	public static float sum ;
	private TextView sumTextView;// 总的钱数
	private TextView counTextView;// 总的数量
	// private TextView priceTextView1;// 价格
	// private TextView priceTextView2;// 价格
	// private View person;
	// private View person2;
	private CartsDataManage dataManage;
	// private Button shoppingCartTopay;
	// private ImageView mImageView;// 返回
	// private ArrayList<UserComment> mlist;
	private CartUtil cartUtil;
	private CheckBox mBox;
	// private final int MENU1 = 0x111;
	// private final int MENU2 = 0x112;
	// private final int MENU3 = 0x113;
	private LinearLayout layout;
	private View view;
	public static ArrayList<Cart> cartList;
	// private CartsDataManage dataManage2;
	// private AddressDataManage addressManage;// 地址管理
	// private PullToRefreshScrollView mPullToRefreshScrollView;// 刷新界面
	// private Fragment mFragment;
	private MyApplication myApplication;
	private PreferentialDataManage preferentialDataManage;
	// private List<HashMap<String, Float>> mlList = null;

	private InnerReceiver receiver;
	public static ArrayList<Specials> arrayListFree;
	public static ArrayList<Specials> arrayListExchange;

	private ArrayList<Cart> mList;
	private Button shoppingCartTopay;
	

	
	// private InnerReceiver receiver;

	// private void doClick(View v) {
	// switch (v.getId()) {
	// case R.id.shopping_cart_go_pay:// 去结算
	// Intent intent = new Intent(getSherlockActivity(),
	// GoodsInfoActivity.class);
	// getSherlockActivity().startActivity(intent);
	// break;
	//
	// }
	// }
	//
	// @Override
	// public void onClick(View v) {
	// if(v==person){
	// int sum = Integer.parseInt(number.getText().toString());
	// switch (v.getId()) {
	// case R.id.shopping_cart_item_subtract:// 点击减号时添加的事件
	// if (sum <= 0) {
	// Toast.makeText(getSherlockActivity(), "已经是最小的数值了",
	// Toast.LENGTH_LONG).show();
	// } else {
	// sum--;
	// number.setText(sum + "");
	//
	// sumTextView
	// .setText(Double.parseDouble(priceTextView1.getText()
	// .toString())
	// * Double.parseDouble(number.getText()
	// .toString())
	// + Double.parseDouble(priceTextView2.getText()
	// .toString())
	// * Double.parseDouble(number2.getText()
	// .toString()) + "");
	//
	// counTextView.setText(Integer.parseInt(number.getText()
	// .toString())
	// + Integer.parseInt(number2.getText().toString()) + "");
	// }
	// break;
	//
	// case R.id.shopping_cart_item_add:// 点击加号时添加的事件
	// if (sum >= 9999) {
	// Toast.makeText(getSherlockActivity(), "您想要购买更多的产品，请与客服联系",
	// Toast.LENGTH_LONG).show();
	// } else {
	// sum++;
	//
	// number.setText(sum + "");
	// sumTextView
	// .setText(Double.parseDouble(priceTextView1.getText()
	// .toString())
	// * Double.parseDouble(number.getText()
	// .toString())
	// + Double.parseDouble(priceTextView2.getText()
	// .toString())
	// * Double.parseDouble(number2.getText()
	// .toString()) + "");
	//
	// counTextView.setText(Integer.parseInt(number.getText()
	// .toString())
	// + Integer.parseInt(number2.getText().toString()) + "");
	// }
	// break;
	// }
	// }else if(v.equals(person2)) {
	// int sum = Integer.parseInt(number2.getText().toString());
	// switch (v.getId()) {
	// case R.id.shopping_cart_item_subtract:// 点击减号时添加的事件
	// if (sum <= 0) {
	// Toast.makeText(getSherlockActivity(), "已经是最小的数值了",
	// Toast.LENGTH_LONG).show();
	// } else {
	// sum--;
	// number2.setText(sum + "");
	//
	// sumTextView
	// .setText(Double.parseDouble(priceTextView1.getText()
	// .toString())
	// * Double.parseDouble(number.getText()
	// .toString())
	// + Double.parseDouble(priceTextView2.getText()
	// .toString())
	// * Double.parseDouble(number2.getText()
	// .toString()) + "");
	//
	// counTextView.setText(Integer.parseInt(number.getText()
	// .toString())
	// + Integer.parseInt(number2.getText().toString()) + "");
	// }
	// break;
	//
	// case R.id.shopping_cart_item_add:// 点击加号时添加的事件
	// if (sum >= 9999) {
	// Toast.makeText(getSherlockActivity(), "您想要购买更多的产品，请与客服联系",
	// Toast.LENGTH_LONG).show();
	// } else {
	// sum++;
	//
	// number2.setText(sum + "");
	// sumTextView
	// .setText(Double.parseDouble(priceTextView1.getText()
	// .toString())
	// * Double.parseDouble(number.getText()
	// .toString())
	// + Double.parseDouble(priceTextView2.getText()
	// .toString())
	// * Double.parseDouble(number2.getText()
	// .toString()) + "");
	//
	// counTextView.setText(Integer.parseInt(number.getText()
	// .toString())
	// + Integer.parseInt(number2.getText().toString()) + "");
	// }
	// break;
	// }
	// }
	// }

	// public ArrayList<Specials> getArrayListFree() {
	// return arrayListFree;
	// }
	//
	// public void setArrayListFree(ArrayList<Specials> arrayListFree) {
	// this.arrayListFree = arrayListFree;
	// }
	//
	// public ArrayList<Specials> getArrayListExchange() {
	// return arrayListExchange;
	// }
	//
	// public void setArrayListExchange(ArrayList<Specials> arrayListExchange) {
	// this.arrayListExchange = arrayListExchange;
	// }

//	public ArrayList<Cart> getCartList() {
//		return cartList;
//	}
//
//	public void setCartList(ArrayList<Cart> cartList) {
//		this.cartList = cartList;
//	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// addressManage = new AddressDataManage(getSherlockActivity());
		try {
			dataManage = new CartsDataManage();
			myApplication = (MyApplication) getSherlockActivity()
					.getApplicationContext();
			Log.i(CartActivity.class.getName(), "CartActivity__onCreateView");
			
			
			view = inflater.inflate(R.layout.shopping_cart,null);

			// TODO Auto-generated method stub

			sumTextView = (TextView) view
					.findViewById(R.id.shopping_cart_sum_money);// 总的钱数

			mBox = (CheckBox) view.findViewById(R.id.shopping_cart_checkbox);// 选择框

			counTextView = (TextView) view
					.findViewById(R.id.shopping_cart_sum_number);// 总的数量
			
			shoppingCartTopay = (Button) getSherlockActivity()
					.findViewById(R.id.shopping_cart_go_pay);

			receiver = new InnerReceiver();
			IntentFilter filter = new IntentFilter();
			filter.addAction(Consts.BROAD_UPDATE_CHANGE);
			filter.addAction(Consts.UPDATE_CHANGE);
			getSherlockActivity().registerReceiver(receiver, filter);
			// registerForContextMenu(layout);

			// layout = (LinearLayout) view
			// .findViewById(R.id.shopping_cart_relative2);

			// getSherlockActivity().getSupportActionBar().setCustomView(
			// R.layout.actionbar_cart);

			// layout = (LinearLayout)
			// view.findViewById(R.id.shopping_cart_relative2);
			layout = new LinearLayout(getSherlockActivity());
			layout.setOrientation(LinearLayout.VERTICAL);
			ScrollView scrollView = (ScrollView) view
					.findViewById(R.id.shopping_cart_item_goods_scrollView);
			scrollView.addView(layout);

			cartUtil = new CartUtil(getSherlockActivity(), layout,
					counTextView, sumTextView, mBox);

			cartUtil.AllComment();

			// mPullToRefreshScrollView = (PullToRefreshScrollView) view
			// .findViewById(R.id.shopping_cart_item_goods_scrollView);
			// String label = getResources().getString(R.string.update_time)
			// + DateUtils.formatDateTime(getSherlockActivity(),
			// System.currentTimeMillis(),
			// DateUtils.FORMAT_SHOW_TIME
			// | DateUtils.FORMAT_ABBREV_ALL
			// | DateUtils.FORMAT_SHOW_DATE);
			// mPullToRefreshScrollView.getLoadingLayoutProxy()
			// .setLastUpdatedLabel(label);
			// mPullToRefreshScrollView.onRefreshComplete();
			// mPullToRefreshScrollView.setOnRefreshListener(listener);

			// 结算
			// 弹出积分换购的对话框

			// mPullToRefreshScrollView = (PullToRefreshScrollView) view
			// .findViewById(R.id.shopping_cart_item_goods_scrollView);
			// String label = getResources().getString(R.string.update_time)
			// + DateUtils.formatDateTime(getSherlockActivity(),
			// System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
			// | DateUtils.FORMAT_ABBREV_ALL
			// | DateUtils.FORMAT_SHOW_DATE);
			// mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
			// label);
			// mPullToRefreshScrollView.onRefreshComplete();
			// mPullToRefreshScrollView.setOnRefreshListener(listener);
//			dialog = new Builder(getSherlockActivity())
//					.setTitle("换购商品")
//					.setIcon(android.R.drawable.dialog_frame)
//					.setMessage(
//							getResources().getString(R.string.exchange_produce))
//					.setPositiveButton(
//							"确定",
//							new android.content.DialogInterface.OnClickListener() {
//
//								@Override
//								public void onClick(DialogInterface arg0,
//										int arg1) {
//									Intent intent = new Intent(
//											getSherlockActivity(),
//											ExchangeFreeActivity.class);
//									Bundle bundle = new Bundle();
//									float sum = Float.parseFloat(sumTextView
//											.getText().toString());
//									bundle.putString("price", sum + "");
//									intent.putExtras(bundle);
//									getSherlockActivity().startActivity(intent);
//
//								}
//							})
//					.setNegativeButton(
//							"取消",
//							new android.content.DialogInterface.OnClickListener() {
//
//								@Override
//								public void onClick(DialogInterface dialog,
//										int which) {
//									// TODO Auto-generated method stub
//									Intent intent = new Intent(
//											getSherlockActivity(),
//											CstmPayActivity.class);
//									Bundle bundle = new Bundle();
//									float sum = Float.parseFloat(sumTextView
//											.getText().toString());
//									bundle.putString("price", sum + "");
//									intent.putExtras(bundle);
//									getSherlockActivity().startActivity(intent);
//
//								}
//							}).create();

//			if (shoppingCartTopay == null) {
//				Toast.makeText(
//						getSherlockActivity(),
//						getSherlockActivity().getResources().getString(
//								R.string.no_network), Toast.LENGTH_SHORT)
//						.show();
//			} else {
				shoppingCartTopay.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// getAddresses();
						if (!myApplication.getIsLogin()) {
							showLoginTips();
						} else {
							go2Pay();
						}
					}
				});
				// if(shoppingCartTopay==null){
				// Toast.makeText(
				// getSherlockActivity(),
				// getSherlockActivity().getResources().getString(
				// R.string.no_network), Toast.LENGTH_SHORT).show();
				// }else{
				// shoppingCartTopay.setOnClickListener(new OnClickListener() {
				//
				// @Override
				// public void onClick(View v) {
				// // TODO Auto-generated method stub
				// // getAddresses();
				// if (!myApplication.getIsLogin()) {
				// Toast.makeText(getSherlockActivity(), "你还未登陆，请先登陆",
				// Toast.LENGTH_LONG).show();
				// Intent intent = new Intent(getSherlockActivity(),
				// LoginActivity.class);
				// startActivity(intent);
				//
				// } else {
				// preferentialDataManage = new PreferentialDataManage(
				// getSherlockActivity());
				// StringBuffer sb = new StringBuffer();
				// mList = dataManage.getCartsArray();
				// for (int i = 0; i < mList.size(); i++) {
				// Cart cart = mList.get(i);
				// sb.append(cart.getUId());
				// sb.append(",");
				// sb.append(cart.getAmount());
				// sb.append("n");
				// sb.append(";");
				// }
				// // Log.i("info", sb.toString()+"    sb");
				// mList.clear();
				// preferentialDataManage.getPreferential(sb.toString(),
				// myApplication.getUserId());
				//
				// if (preferentialDataManage.getFreeGoods().size() != 0
				// || preferentialDataManage.getScoreGoods()
				// .size() != 0) {
				//
				// arrayListFree = preferentialDataManage
				// .getFreeGoods();
				// arrayListExchange = preferentialDataManage
				// .getScoreGoods();
				// dialog.show();
				// Intent intent = new Intent(getSherlockActivity(),
				// ExchangeFreeActivity.class);
				// Bundle bundle = new Bundle();
				// float sum =
				// Float.parseFloat(sumTextView.getText()
				// .toString());
				// bundle.putString("price", sum + "");
				// intent.putExtras(bundle);
				// startActivity(intent);

				// } else {
				// preferentialDataManage.getFreeGoods();

//				Log.i("info", arrayListExchange.size()
//						+ "CartActivity.arrayList");
//				Log.i("info", arrayListExchange.size()
//						+ "CartActivity.arrayList");
				// Intent intent1 = new
				// Intent(getSherlockActivity(),
				// CstmPayActivity.class);
				// Bundle bundle = new Bundle();
				// float sum =
				// Float.parseFloat(sumTextView.getText()
				// .toString());
				//
				// if (sum > 0) {
				// bundle.putString("price", sum + "");
				// intent.putExtras(bundle);
				// getSherlockActivity().startActivity(intent1);
				// } else {
				// Toast.makeText(getSherlockActivity(),
				// getResources().getString(R.string.buy_nothing),
				// Toast.LENGTH_LONG).show();
				// }
				// }
				// }
				// }
				// }
				// }

				// }
				// });
//			}
			//
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(
					getSherlockActivity(),
					getSherlockActivity().getResources().getString(
							R.string.no_network), Toast.LENGTH_SHORT).show();
		}
		return view;

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getSherlockActivity().unregisterReceiver(receiver);
	}
	
	private void showLoginTips(){
		new Builder(getSherlockActivity())
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
										getSherlockActivity(),
										LoginActivity.class);
								startActivity(intent);
							}
							//
						})
				.setNegativeButton(R.string.searchCancel,null)
				.create().show();
	}
	
	private void go2Pay(){
		cartList = new ArrayList<Cart>();
		List<HashMap<String, Object>> orderCarts = CartUtil.list1;
		for (int i = 0; i < orderCarts.size(); i++) {
			HashMap<String, Object> map = orderCarts.get(i);
			float ischeck = Float.parseFloat(map.get("check")
					.toString());
			Log.i("info", ischeck + "    ischeck");
			Cart cart1 = (Cart) map.get("cart");
			if (ischeck == 1.0) {
				cartList.add(cart1);
			}
		}
		float sum = Float.parseFloat(sumTextView.getText()
				.toString());
		if (sum > 0) {
			Intent intent1 = new Intent(getSherlockActivity(),
					CstmPayActivity.class);
			intent1.putExtra("carts", cartList);
			Bundle bundle = new Bundle();
			bundle.putString("cartActivity", "Y");
			bundle.putString("price", sum + "");
			intent1.putExtras(bundle);
			getSherlockActivity().startActivity(intent1);
		} else {
			Toast.makeText(
					getSherlockActivity(),
					getResources().getString(
							R.string.buy_nothing),
					Toast.LENGTH_LONG).show();
		}
	}

	/*
	 * private int fromIndex = 0; private int amontIndex = 10; // 刷新添加事件 private
	 * OnRefreshListener<ScrollView> listener = new
	 * OnRefreshListener<ScrollView>() {
	 * 
	 * @Override public void onRefresh(PullToRefreshBase<ScrollView>
	 * refreshView) { // TODO Auto-generated method stub
	 * 
	 * String label = getResources().getString(R.string.update_time) +
	 * DateUtils.formatDateTime(getSherlockActivity(),
	 * System.currentTimeMillis(), DateUtils.FORMAT_SHOW_DATE |
	 * DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_ABBREV_ALL);
	 * 
	 * refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label); //
	 * fromIndex += amontIndex; mPullToRefreshScrollView.onRefreshComplete(); }
	 * };
	 */
	// @Override
	// public void onCreateContextMenu(ContextMenu menu, View v,
	// ContextMenuInfo menuInfo) {
	// menu.add(0, MENU1, 0, "删除");
	// menu.add(0, MENU2, 0, "查看商品详情");
	// menu.add(0, MENU3, 0, "收藏");
	// menu.setGroupCheckable(0, true, true);
	// menu.setHeaderIcon(android.R.drawable.menu_frame);
	// menu.setHeaderTitle("购物车操作");
	// super.onCreateContextMenu(menu, v, menuInfo);
	// }

	// @Override
	// public void onDestroy() {
	// getActivity().unregisterReceiver(receiver);
	//
	// super.onDestroy();
	// }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	Addresses address = null;

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		// Log.i(CartActivity.class.getName(), "cart on start");
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	// /**
	// * 判断是否用户已经有保存好的地址
	// */
	// private void getAddresses() {
	//
	// String userId = ((MyApplication) getSherlockActivity().getApplication())
	// .getUserId();
	// ArrayList<Addresses> mAddresses = addressManage.getAddressesArray(
	// Integer.parseInt(userId), 0, 5);
	// if (mAddresses.size() == 0) {
	// Intent intent = new Intent(getSherlockActivity(),
	// NewAddressActivity.class);
	// getSherlockActivity().startActivity(intent);
	//
	// // Log.i("info", "nihao");
	//
	// } else {
	// address = mAddresses.remove(0);
	// // Log.i("info", address + "address");
	// }
	//
	// }
	public class InnerReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (Consts.BROAD_UPDATE_CHANGE.equals(action)) {
				// Log.i("info", action + "action");
				layout.removeAllViews();
				sumTextView.setText(""+0.00);
				counTextView.setText(""+0);
				CartUtil cartUtil = new CartUtil(getSherlockActivity(), layout,
						counTextView, sumTextView, mBox);
				cartUtil.AllComment();
			}else if(Consts.UPDATE_CHANGE.equals(action)){
				layout.removeAllViews();	
				CartUtil cartUtil = new CartUtil(getSherlockActivity(), layout,
						counTextView, sumTextView, mBox);
				cartUtil.AllComment();
			}
		}
	}

}