package com.yidejia.app.mall.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.AddressDataManage;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.model.UserComment;
import com.yidejia.app.mall.util.CartUtil;
import com.yidejia.app.mall.view.NewAddressActivity;
import com.yidejia.app.mall.view.PayActivity;

public class CartActivity extends SherlockFragment implements OnClickListener {
	private ImageView subtract;// 减
	private TextView number;
	private ImageView addImageView;

	private ImageView subtract2;// 减
	private TextView number2;
	private ImageView addImageView2;
	private int sumString;
	private TextView sumTextView;// 总的钱数
	private TextView counTextView;// 总的数量
	private TextView priceTextView1;// 价格
	private TextView priceTextView2;// 价格
	private View person;
	private View person2;
	private CartsDataManage dataManage;
	private Button shoppingCartTopay;
	private ArrayList<UserComment> mlist;
	private CartUtil cartUtil;
	private CheckBox mBox;
	private final int MENU1 = 0x111;
	private final int MENU2 = 0x112;
	private final int MENU3 = 0x113;
	private LinearLayout layout;
	private View view;
	private CartsDataManage dataManage2;
//	private AddressDataManage addressManage;// 地址管理
	private PullToRefreshScrollView mPullToRefreshScrollView;// 刷新界面

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		addressManage = new AddressDataManage(getSherlockActivity());
		view = inflater.inflate(R.layout.shopping_cart, container, false);
		// dataManage = new CartsDataManage();
		// TODO Auto-generated method stub

		mBox = (CheckBox) view.findViewById(R.id.shopping_cart_checkbox);// 选择框

		sumTextView = (TextView) view
				.findViewById(R.id.shopping_cart_sum_money);// 总的钱数

		counTextView = (TextView) view
				.findViewById(R.id.shopping_cart_sum_number);// 总的数量

		layout = (LinearLayout) view.findViewById(R.id.shopping_cart_relative2);

		// registerForContextMenu(layout);

		getSherlockActivity().getSupportActionBar().setCustomView(
				R.layout.actionbar_cart);
		cartUtil = new CartUtil(getSherlockActivity(), layout, counTextView,
				sumTextView, mBox);

		cartUtil.AllComment();
		mPullToRefreshScrollView = (PullToRefreshScrollView) view
				.findViewById(R.id.shopping_cart_item_goods_scrollView);
		String label = "上次更新于"
				+ DateUtils.formatDateTime(getSherlockActivity(),
						System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
								| DateUtils.FORMAT_ABBREV_ALL
								| DateUtils.FORMAT_SHOW_DATE);
		mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
				label);
		mPullToRefreshScrollView.onRefreshComplete();
		mPullToRefreshScrollView.setOnRefreshListener(listener);
		// receiver = new InnerReceiver();
		// IntentFilter filter = new IntentFilter();
		// filter.addAction(Consts.UPDATE_CHANGE);
		// getActivity().registerReceiver(receiver, filter);

		// List<HashMap<String, Integer>> mlist = cartUtil.list;
		// for(int i = 0;i<mlist.size();i++){
		// HashMap<String, Integer> map = mlist.get(i);
		// int count = map.get("count");
		// Log.i("info", count+"");
		//
		// Set<java.util.Map.Entry<String, Integer>> set = map.entrySet();
		// for(java.util.Map.Entry<String, Integer> en:set){

		// }
		// }

		// ListView listView = (ListView)
		// view.findViewById(R.id.shopping_cart_listview);
		// dataManage = new UserCommentDataManage(getSherlockActivity());
		//
		// mlist = dataManage.getUserCommentsArray(26 + "", 0, 10, false);
		// CartAdapter adapter = new CartAdapter(getSherlockActivity(),mlist);
		// listView.setAdapter(adapter);
		// listView.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		// // TODO Auto-generated method stub
		//
		// }
		// });

		// person = inflater.inflate(R.layout.shopping_cart_item, null);
		// person2 = inflater.inflate(R.layout.shopping_cart_item, null);
		//
		//
		// TextView detail = (TextView) person
		// .findViewById(R.id.shopping_cart_item_text);
		//
		// detail.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// Intent intent = new Intent(getSherlockActivity(),
		// OrderDetailActivity.class);
		// startActivity(intent);
		// }
		// });
		// subtract = (ImageView) person
		// .findViewById(R.id.shopping_cart_item_subtract);// 减
		// number = (TextView) person
		// .findViewById(R.id.shopping_cart_item_edit_number);// 输入的个数
		// number.setText(0 + "");
		// addImageView = (ImageView) person
		// .findViewById(R.id.shopping_cart_item_add);// 加
		// priceTextView1 = (TextView) person
		// .findViewById(R.id.shopping_cart_item_money);
		//
		// addImageView.setOnClickListener(this);
		// subtract.setOnClickListener(this);
		//
		// TextView detail1 = (TextView) person2
		// .findViewById(R.id.shopping_cart_item_text);
		//
		// detail1.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// Intent intent = new Intent(getSherlockActivity(),
		// OrderDetailActivity.class);
		// startActivity(intent);
		// }
		// });
		//
		//
		//
		//
		//
		// subtract2 = (ImageView) person2
		// .findViewById(R.id.shopping_cart_item_subtract);// 减
		// number2 = (TextView) person2
		// .findViewById(R.id.shopping_cart_item_edit_number);// 输入的个数
		// number2.setText(0 + "");
		// addImageView2 = (ImageView) person2
		// .findViewById(R.id.shopping_cart_item_add);// 加
		// priceTextView2 = (TextView) person2
		// .findViewById(R.id.shopping_cart_item_money);
		// addImageView2.setOnClickListener(this);
		// subtract2.setOnClickListener(this);
		//
		// layout.addView(person);
		// layout.addView(person2);

		// 结算
		shoppingCartTopay = (Button) getSherlockActivity().findViewById(
				R.id.shopping_cart_go_pay);
		shoppingCartTopay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				getAddresses();
				Intent intent = new Intent(getSherlockActivity(),
						PayActivity.class);
				Bundle bundle = new Bundle();
				float sum = Float.parseFloat(sumTextView.getText().toString());
				
					if (sum > 0) {
						bundle.putString("price", sum + "");
//						bundle.putSerializable("address", address);
						intent.putExtras(bundle);
						getSherlockActivity().startActivity(intent);
					} else {
						Toast.makeText(getSherlockActivity(), "你还未购买任何商品",
								Toast.LENGTH_LONG).show();

					
				}
			}
		});
		//
		return view;

	}
private  int fromIndex = 0;
private int amontIndex = 10 ;
	// 刷新添加事件
	private OnRefreshListener<ScrollView> listener = new OnRefreshListener<ScrollView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
			// TODO Auto-generated method stub

			String label = "上次更新于"
					+ DateUtils.formatDateTime(getSherlockActivity(),
							System.currentTimeMillis(),
							DateUtils.FORMAT_SHOW_DATE
									| DateUtils.FORMAT_SHOW_TIME
									| DateUtils.FORMAT_ABBREV_ALL);

			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			fromIndex += amontIndex;
			mPullToRefreshScrollView.onRefreshComplete();
		}
	};

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

//	/**
//	 * 判断是否用户已经有保存好的地址
//	 */
//	private void getAddresses() {
//
//		String userId = ((MyApplication) getSherlockActivity().getApplication())
//				.getUserId();
//		ArrayList<Addresses> mAddresses = addressManage.getAddressesArray(
//				Integer.parseInt(userId), 0, 5);
//		if (mAddresses.size() == 0) {
//			Intent intent = new Intent(getSherlockActivity(),
//					NewAddressActivity.class);
//			getSherlockActivity().startActivity(intent);
//
//			// Log.i("info", "nihao");
//
//		} else {
//			address = mAddresses.remove(0);
//			// Log.i("info", address + "address");
//		}
//
//	}
}
