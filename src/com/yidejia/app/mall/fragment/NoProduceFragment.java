package com.yidejia.app.mall.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragment;
import com.yidejia.app.mall.MainFragmentActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.SearchResultActivity;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.fragment.CartActivity.InnerReceiver;
import com.yidejia.app.mall.util.CartUtil;
import com.yidejia.app.mall.util.Consts;

public class NoProduceFragment extends SherlockFragment {
	private View view;
	private Button mButton;
	private int sumCart;//购物车中数目
	private CartsDataManage dataManage;
	private FragmentTransaction ft;
	private InnerReceiver receiver;
	private Activity activity;
	private Fragment fragment;
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		activity.unregisterReceiver(receiver);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		dataManage = new CartsDataManage();
		fragment = new CartActivity();
		
		receiver = new InnerReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Consts.BROAD_UPDATE_CHANGE);
		filter.addAction(Consts.UPDATE_CHANGE);
		filter.addAction(Consts.DELETE_CART);
		activity =  MainFragmentActivity.MAINACTIVITY;
		activity.registerReceiver(receiver, filter);
		
		ft = getFragmentManager().beginTransaction();
		view = inflater.inflate(R.layout.no_produce,container,false);
		mButton = (Button) view.findViewById(R.id.no_produce_button);
		Log.e("NoProduceFragment", "NoProduceFragment");
		mButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent (getSherlockActivity(),SearchResultActivity.class);
				Bundle bundle = new Bundle();
				Log.e("info", "gouwuche");
				bundle.putString("title", "全部");
				intent.putExtras(bundle);
				getSherlockActivity().startActivity(intent);
			}
		});
		return view;
	}
	
	public class InnerReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
//			FragmentTransaction 
//			if (Consts.BROAD_UPDATE_CHANGE.equals(action)) {
//				
//			sumCart = dataManage.getCartAmount();
//				Log.i("voucher",sumCart+"    sumCart");
//				
////				activity = CartActivity.this.getSherlockActivity();
//				if (sumCart == 0) {
//					if(fragment.isAdded()) ft.hide(CartActivity.this).show(fragment).commit();
//					ft.hide(CartActivity.this).replace(R.id.main_fragment, fragment).commit();
//					shoppingCartTopay.setVisibility(View.GONE);
////					view = inflater.inflate(R.layout.no_produce, container, false);
//					 
//				} else {
//					layout.removeAllViews();
////					isCartNull = false;
////					container.removeView(view);
//					if(!isCartNull){ 
//						view = inflater.inflate(R.layout.shopping_cart, container, false);
////						container.addView(view);
//						setViewCtrl();
////						shoppingCartTopay.setVisibility(View.VISIBLE);
//					} else {
//						view = inflater.inflate(R.layout.no_produce, null);
////						container.addView(view);
//						return;
//					}
////					ft.replace(R.id.main_fragment, CartActivity.this).commit();
//					
////					if(fragment.isAdded()){
////						ft.hide(fragment).show(CartActivity.this).commit();
////					}else{
////						ft.hide(fragment).replace(R.id.main_fragment, CartActivity.this).commit();
////					}
////					shoppingCartTopay.setVisibility(View.VISIBLE);
////					CartUtil cartUtil = new CartUtil(getSherlockActivity(),
////							layout, counTextView, sumTextView, mBox);
////					cartUtil.AllComment();
////					sum = Float.parseFloat(sumTextView.getText().toString());
//				}
//			} 
//		else 
			if (Consts.UPDATE_CHANGE.equals(action)) {
				sumCart = dataManage.getCartAmount();
//				layout.removeAllViews();
				if(sumCart!=0){
					if(activity instanceof MainFragmentActivity){
						
					}
					if(fragment.isAdded()) ft.hide(NoProduceFragment.this).show(fragment). commitAllowingStateLoss();
					else ft.hide(NoProduceFragment.this).replace(R.id.main_fragment, fragment). commitAllowingStateLoss();
//					view = inflater.inflate(R.layout.no_produce, container, false);
				}
				
			} 
//			else if (Consts.DELETE_CART.equals(action)) {
//				sumCart = dataManage.getCartAmount();
//				if (sumCart == 0) {
////					layout.removeAllViews();
////					activity.startActivity(intent1);
//					if(fragment.isAdded()){
//						ft.hide(CartActivity.this).show(fragment).commit();
//					}
//					else {
//						ft.hide(CartActivity.this).replace(R.id.main_fragment, fragment).commit();
//					}
//
//				} else {
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

}
