package com.yidejia.app.mall.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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
import com.yidejia.app.mall.HomeMallActivity;
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
import com.yidejia.app.mall.view.GoCartActivity;
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
	public static float sum;
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
	private int sumCart;
	private NoProduceFragment fragment;
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

	// public ArrayList<Cart> getCartList() {
	// return cartList;
	// }
	//
	// public void setCartList(ArrayList<Cart> cartList) {
	// this.cartList = cartList;
	// }
	
	public static boolean isCartNull = true;
	
	private LayoutInflater inflater;
	private ViewGroup container;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// addressManage = new AddressDataManage(getSherlockActivity());
//		Log.e("NoProduceFragment", "CarActivity");
		receiver = new InnerReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Consts.BROAD_UPDATE_CHANGE);
		filter.addAction(Consts.UPDATE_CHANGE);
		filter.addAction(Consts.DELETE_CART);
		activity =  HomeMallActivity.MAINACTIVITY;
		activity.registerReceiver(receiver, filter);
		
//		getSherlockActivity().getSupportActionBar().setCustomView(
//				R.layout.actionbar_cart);
		
		Log.e(TAG, "on createView");
		this.inflater = inflater;
		this.container = container;
		fragment = new NoProduceFragment();
	
		try {
			dataManage = new CartsDataManage();
			myApplication = (MyApplication) activity
					.getApplicationContext();
			Log.i(CartActivity.class.getName(), "CartActivity__onCreateView");
			if(dataManage.getCartAmount() != 0)isCartNull = false;
			else isCartNull = true;
			if(!isCartNull) {
				view = inflater.inflate(R.layout.shopping_cart, container, false);
				setViewCtrl();
			}
			else {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				if(fragment.isAdded()) ft.hide(CartActivity.this).show(fragment).commit();
				ft.hide(CartActivity.this).replace(R.id.main_fragment, fragment).commit();
//				view = inflater.inflate(R.layout.no_produce, container, false);
//				setViewCtrl();
//				shoppingCartTopay.setVisibility(View.GONE);
//				return view;
			}

		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			Toast.makeText(
//					getSherlockActivity(),
//					getSherlockActivity().getResources().getString(
//							R.string.no_network), Toast.LENGTH_SHORT).show();
		}
		return view;

	}
	
	private static Activity activity;
	
	private void setViewCtrl(){
		sumTextView = (TextView) view
				.findViewById(R.id.shopping_cart_sum_money);// 总的钱数

		mBox = (CheckBox) view.findViewById(R.id.shopping_cart_checkbox);// 选择框

		counTextView = (TextView) view
				.findViewById(R.id.shopping_cart_sum_number);// 总的数量

		shoppingCartTopay = (Button) activity.findViewById(
				R.id.shopping_cart_go_pay);
		try{
			shoppingCartTopay.setVisibility(View.VISIBLE);
		} catch(Exception e){
			Log.e(CartActivity.class.getName(), "set visibility err");
		}
		layout = new LinearLayout(activity);
		layout.setOrientation(LinearLayout.VERTICAL);
		ScrollView scrollView = (ScrollView) view
				.findViewById(R.id.shopping_cart_item_goods_scrollView);
		
//		cartUtil = new CartUtil(activity, layout,
//				counTextView, sumTextView, mBox);

		cartUtil.AllComment();

		scrollView.addView(layout);
		Log.i("info", sum + "sum");
		
		if (shoppingCartTopay == null) {
			Log.e(CartActivity.class.getName(), "cart act button is null");
//			Toast.makeText(
//					getSherlockActivity(),
//					getSherlockActivity().getResources().getString(
//							R.string.no_network), Toast.LENGTH_SHORT)
//					.show();
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

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		activity.unregisterReceiver(receiver);
	}

	private void showLoginTips() {
		new Builder(activity)
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
										activity,
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
			Intent intent1 = new Intent(getSherlockActivity(),
					CstmPayActivity.class);
			Bundle bundle = new Bundle();
			sum = Float.parseFloat(sumTextView.getText()
					.toString());
			intent1.putExtra("carts", cartList);
	
			Log.i("voucher", sum + "    sum");
//				for(int i = 0; i<mList.size();i++){
//					Cart cart = new Cart();
//					sb.append(cart.getUId());
//					sb.append(",");
//					sb.append(cart.getAmount());
//					sb.append("n");
//					sb.append(";");
//				}
//				preferentialDataManage.getPreferential(sb.toString(),myApplication.getUserId());
//				if (preferentialDataManage.getFreeGoods().size() != 0
//						|| preferentialDataManage.getScoreGoods().size() != 0) {
//					Intent intent = new Intent(GoCartActivity.this,
//							ExchangeFreeActivity.class);
//					
//				}else{
			
			if (sum > 0) {
				bundle.putString("cartActivity","Y");
				bundle.putString("price", sum + "");
				intent1.putExtras(bundle);
				getSherlockActivity().startActivity(intent1);
			} else {
				Toast.makeText(getSherlockActivity(), getResources().getString(R.string.buy_nothing),
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

//	@Override
//	public void onStart() {
//		// TODO Auto-generated method stub
//		super.onStart();
////		setViewCtrl();
////		container.requestLayout();
//		Log.i(CartActivity.class.getName(), "cart on start");
//	}

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
	
	String TAG = CartActivity.class.getName();

	public class InnerReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
//			// TODO Auto-generated method stub
//			String action = intent.getAction();
////			FragmentTransaction ft = getFragmentManager().beginTransaction();
//
//			if (Consts.BROAD_UPDATE_CHANGE.equals(action)) {
//				sumCart = dataManage.getCartAmount();
//				Log.i("voucher",sumCart+"    sumCart");
//				
////				activity = CartActivity.this.getSherlockActivity();
//				if (sumCart == 0) {
//					Log.e("NoProduceFragment", "DELETE_CART");
//					FragmentTransaction ft = getFragmentManager().beginTransaction();
//					if(fragment.isAdded()) ft.hide(CartActivity.this).show(fragment).commitAllowingStateLoss();//.show(fragment).
//					else ft.hide(CartActivity.this).replace(R.id.main_fragment, fragment).commitAllowingStateLoss();
//					shoppingCartTopay.setVisibility(View.GONE);		
//					
//				} else {
//					layout.removeAllViews();
//					Log.e("NoProduceFragment", "DELETE_CART2");
//					CartUtil cartUtil = new CartUtil(getSherlockActivity(),
//							layout, counTextView, sumTextView, mBox);
//					cartUtil.AllComment();
//					sum = Float.parseFloat(sumTextView.getText().toString());
////					
//				}
//			} 
//				else if (Consts.UPDATE_CHANGE.equals(action)) {
//					
//				Log.e("NoProduceFragment", "DELETE_CART1");
////				CartUtil.list.clear();
////				sumCart = dataManage.getCartAmount();
//				
//				layout.removeAllViews();
//				CartUtil cartUtil = new CartUtil(activity, layout,
//						counTextView, sumTextView, mBox);
//				cartUtil.AllComment();
//				
//				sum = Float.parseFloat(sumTextView.getText().toString());
//			}
//				else if (Consts.DELETE_CART.equals(action)) {
//				sumCart = dataManage.getCartAmount();
//				if (sumCart == 0) {
////					Log.e("NoProduceFragment", "CarActivity");
//					shoppingCartTopay.setVisibility(View.GONE);
//					FragmentTransaction ft = getFragmentManager().beginTransaction();
//					
//					if(fragment.isAdded()) ft.hide(CartActivity.this).show(fragment).commitAllowingStateLoss();//.show(fragment).
//					else ft.replace(R.id.main_fragment, fragment).commitAllowingStateLoss();
//					
////					if(fragment.isAdded()) ft.hide(CartActivity.this).commitAllowingStateLoss();
////					else ft.hide(CartActivity.this).replace(R.id.main_fragment, fragment).commitAllowingStateLoss();
//				} else {
//					layout.removeAllViews();
////					Log.e("NoProduceFragment", "CarActivity!=0");
//					CartUtil cartUtil = new CartUtil(activity,
//							layout, counTextView, sumTextView, mBox);
//					cartUtil.AllComment();
//					sum = Float.parseFloat(sumTextView.getText().toString());
//				}
//				// sumTextView.setText(""+0.00);
//				// counTextView.setText(""+0);
//			}
		}
	}
//	
//	

}