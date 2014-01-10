package com.yidejia.app.mall.goodinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.datamanage.FavoriteDataManage;
import com.yidejia.app.mall.initview.GoodsView;
import com.yidejia.app.mall.model.ProductBaseInfo;
import com.yidejia.app.mall.util.Consts;

public class BaseInfoFragment extends SherlockFragment {
	
	private InnerReceiver receiver;
	private CartsDataManage dataManage;
	private MyApplication myApplication;
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
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
//		goodsId = (bundle != null) ? bundle.getString("goodsId") : defaultInt;
		info = (ProductBaseInfo) bundle.getSerializable("info");
		Log.d(TAG, "TestFragment-----onCreate---" );
		
	}

	private View view;
	@Override
	public void onDestroy() {
		super.onDestroy();
		getSherlockActivity().unregisterReceiver(receiver);
	}
	
	private Button mButton;//购物车
	private ImageView add_favorites;//收藏
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		try {
			
			myApplication   = (MyApplication) getSherlockActivity().getApplication();
			
			receiver = new InnerReceiver();
			IntentFilter filter = new IntentFilter();
			filter.addAction(Consts.UPDATE_CHANGE);
			filter.addAction(Consts.BROAD_UPDATE_CHANGE);
			filter.addAction(Consts.DELETE_CART);
			filter.addAction(Consts.LONGIN_SUCESSES);
			getSherlockActivity().registerReceiver(receiver, filter);
			
			Log.d(TAG, "TestFragment-----onCreateView");
			dataManage = new CartsDataManage();
			view = inflater.inflate(R.layout.item_goods_base_info, container, false);
			mButton = (Button) view.findViewById(R.id.shopping_cart_button);
			add_favorites = (ImageView) view.findViewById(R.id.add_favorites);
//			view.invalidate();
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
		} catch (Exception e) {
			e.printStackTrace();
		Toast.makeText(getSherlockActivity(),getResources().getString(R.string.no_network),
		Toast.LENGTH_SHORT).show();
		}
//		addBaseImage(view);
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "TestFragment-----onActivityCreated");
//		ProductDataManage manage = new ProductDataManage(getSherlockActivity());
//		info = manage.getProductData(goodsId);
		GoodsView goodsView = new GoodsView(getSherlockActivity() , view , getDeviceWidth());
		goodsView.initGoodsView(info);
	}

	@Override
	public void onStart() {
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
	private int number1;
	public class InnerReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (Consts.UPDATE_CHANGE.equals(action)||Consts.BROAD_UPDATE_CHANGE.equals(action)) {
				number1 = dataManage.getCartAmount();
//			mButton.setText(number1+"");
				if(number1==0){
					mButton.setVisibility(View.GONE);
				}else{
					mButton.setVisibility(View.VISIBLE);
					mButton.setText(number1+"");
				}
			}
			if(Consts.DELETE_CART.equals(action)){
				number1 = dataManage.getCartAmount();
				if(number1==0){
					mButton.setVisibility(View.GONE);
				}else{
					mButton.setVisibility(View.VISIBLE);
					mButton.setText(number1+"");
				}
			}
			if(Consts.LONGIN_SUCESSES.equals(action)){
				// 检查是否收藏并且设置收藏按钮的图片
				FavoriteDataManage favoriteManage = new FavoriteDataManage(getSherlockActivity());
				if (myApplication.getIsLogin() && !"".equals(myApplication.getUserId())) {
					if (favoriteManage.checkExists(myApplication.getUserId(), info.getUId(), myApplication.getToken())) {
						add_favorites.setImageResource(R.drawable.add_favorites2);
//					Toast.makeText(activity, "yes", Toast.LENGTH_LONG).show();
					} else {
						add_favorites.setImageResource(R.drawable.add_favorites1);
//					Toast.makeText(activity, "no", Toast.LENGTH_LONG).show();
					}
				} else {
					add_favorites.setImageResource(R.drawable.add_favorites1);
				}
		
			}
		}

	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		StatService.onPause(this);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		StatService.onResume(this);
	}
}