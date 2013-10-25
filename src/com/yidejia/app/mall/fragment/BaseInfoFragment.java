package com.yidejia.app.mall.fragment;

import com.actionbarsherlock.app.SherlockFragment;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.ProductDataManage;
import com.yidejia.app.mall.initview.GoodsView;
import com.yidejia.app.mall.model.ProductBaseInfo;
import com.yidejia.app.mall.util.DPIUtil;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class BaseInfoFragment extends SherlockFragment {
	

	public static BaseInfoFragment newInstance(ProductBaseInfo info) {
		BaseInfoFragment baseInfoFragment = new BaseInfoFragment();
		Bundle bundle = new Bundle();
//		bundle.putString("goodsId", goodsId);
		bundle.putSerializable("info", info);
		baseInfoFragment.setArguments(bundle);
		return baseInfoFragment;
	}

	private String goodsId;
	private String defaultInt = "";
	private String TAG = BaseInfoFragment.class.getName();
	private int number;
	
	private ProductBaseInfo info;
//	public BaseInfoFragment(int base){
//		super();
//		this.base = base;
//	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
//		goodsId = (bundle != null) ? bundle.getString("goodsId") : defaultInt;
		info = (ProductBaseInfo) bundle.getSerializable("info");
		Log.d(TAG, "TestFragment-----onCreate---" );
		
	}

	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d(TAG, "TestFragment-----onCreateView");
		view = inflater.inflate(R.layout.item_goods_base_info, container, false);
		/*
		switch (goodsId) {
		case 0:
			view = inflater.inflate(R.layout.item_goods_emulate, container, false);
			break;
		case 1:
			view = inflater.inflate(R.layout.item_goods_base_info, container, false);
//			View parentView =  inflater.inflate(R.layout.activity_goods_info_layout, null);//获取购物车
//			final Button cartButotn =(Button) parentView.findViewById(R.id.shopping_cart_button);
//			number = Integer.parseInt(cartButotn.getText().toString());
			ImageView buyNow = (ImageView)view.findViewById(R.id.buy_now);//立即购买
			buyNow.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					
				}
			});
//			ImageView addCart = (ImageView)view.findViewById(R.id.add_to_cart);//加入购物车
//			addCart.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View arg0) {
//					++number;
//					cartButotn.setText(number+"");
//					
//					
//				}
//			});
			
			addBaseImage(view);
			break;
		case 2:
			view = inflater.inflate(R.layout.item_goods_base_info, container, false);
			break;
		default:
			view = inflater.inflate(R.layout.item_goods_base_info, container, false);
			addBaseImage(view);
			break;
		}
		*/
//		addBaseImage(view);
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "TestFragment-----onActivityCreated");
//		ProductDataManage manage = new ProductDataManage(getSherlockActivity());
//		info = manage.getProductData(goodsId);
		GoodsView goodsView = new GoodsView(getSherlockActivity() , view , getDeviceWidth());
		goodsView.initGoodsView(info);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d(TAG, "TestFragment-----onStart");
	}
	
	
	private int getPixels(int dipValue) {
		Resources r = getResources();
		int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dipValue, r.getDisplayMetrics());
		return px;
	}

	private int getDeviceWidth() {
		DisplayMetrics dm = new DisplayMetrics();// 获得屏幕分辨率
		getSherlockActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	} 
	
}