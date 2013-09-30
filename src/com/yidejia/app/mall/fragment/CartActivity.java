package com.yidejia.app.mall.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.os.DropBoxManager.Entry;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.datamanage.UserCommentDataManage;
import com.yidejia.app.mall.model.UserComment;
import com.yidejia.app.mall.util.CartUtil;
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
	
			dataManage = new CartsDataManage();
			View view = inflater.inflate(R.layout.shopping_cart, container, false);
			// TODO Auto-generated method stub

			mBox = (CheckBox) view.findViewById(R.id.shopping_cart_checkbox);// 选择框

			sumTextView = (TextView) view
					.findViewById(R.id.shopping_cart_sum_money);// 总的钱数

			counTextView = (TextView) view
					.findViewById(R.id.shopping_cart_sum_number);// 总的数量

			LinearLayout layout = (LinearLayout) view
					.findViewById(R.id.shopping_cart_relative2);

			getSherlockActivity().getSupportActionBar().setCustomView(
					R.layout.actionbar_cart);
			cartUtil = new CartUtil(getSherlockActivity(), layout, counTextView,
					sumTextView,mBox);
			cartUtil.AllComment();
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
					Intent intent = new Intent(getSherlockActivity(),
							PayActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("price", sumTextView.getText().toString());
					intent.putExtras(bundle);
					getSherlockActivity().startActivity(intent);
				}
			});
			//
			return view;
	
		

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

}
