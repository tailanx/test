package com.yidejia.app.mall.view;

import java.io.IOException;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.R.drawable;
import com.yidejia.app.mall.R.id;
import com.yidejia.app.mall.R.layout;
import com.yidejia.app.mall.R.string;
import com.yidejia.app.mall.adapter.BaseFragmentPagerAdapter;
import com.yidejia.app.mall.datamanage.ProductDataManage;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.fragment.BaseInfoFragment;
import com.yidejia.app.mall.fragment.CommentFragment;
import com.yidejia.app.mall.fragment.GoodsDetailFragment;
import com.yidejia.app.mall.model.ProductBaseInfo;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.goodsinfo.GetProductAddress;
import com.yidejia.app.mall.task.TaskRegister;
import com.yidejia.app.mall.widget.YLProgressDialog;

/**
 * 商品基本信息类
 * 
 * @author long bin
 * 
 */
public class GoodsInfoActivity extends SherlockFragmentActivity implements
		OnClickListener {

	private TextView goodsBasicInfo;// 基本信息
	private TextView goodsDetails;// 详情
	private TextView goodsReviews;// 评论
//	private ViewPager goodsViewPager;
	private ArrayList<Fragment> fragmentsList;
	private int currIndex = 1;
	private int number = 0;
	private Button cartButotn;
	private ImageView refresh_data_btn;
	private RelativeLayout refresh_view;
	private FrameLayout goods_framelayout;
	
	private String goodsId;
	private ProductBaseInfo info;
	
	private Task task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		setActionbarConfig();
		setContentView(R.layout.activity_goods_info_layout);
//		bar = new ProgressDialog(GoodsInfoActivity.this);
//		bar.setCancelable(true);
//		bar.setMessage(getResources().getString(R.string.loading));
//		bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//		
//		bar.setOnCancelListener(new DialogInterface.OnCancelListener() {
//			
//			@Override
//			public void onCancel(DialogInterface dialog) {
//				// TODO Auto-generated method stub
//				closeTask();
//				goods_framelayout.setVisibility(View.GONE);
//				refresh_view.setVisibility(View.VISIBLE);
//			}
//		});
		
		goods_framelayout = (FrameLayout) findViewById(R.id.goods_framelayout);
		refresh_view = (RelativeLayout) findViewById(R.id.good_item_refresh_view);
		refresh_data_btn = (ImageView) findViewById(R.id.refresh_data_btn);
		
		if (bundle != null) {
			goodsId = bundle.getString("goodsId");
			if (ConnectionDetector.isConnectingToInternet(this)) {
				/*final ProductDataManage manage = new ProductDataManage(this);
				if (!"".equals(goodsId) && goodsId != null) {
					info = manage.getProductData(goodsId);
					// manage.getTask(goodsId);
					// info = manage.setCallBackListener(new CallBack() {
					//
					// @Override
					// public void callback() {
					// // TODO Auto-generated method stub
					// // manage.getTask(goodsId);
					// }
					// });
				}*/
				closeTask();
				task = new Task();
				task.execute();
				
			} else {
				goods_framelayout.setVisibility(View.GONE);
				refresh_view.setVisibility(View.VISIBLE);
			}
		}
//		initView();
//		initViewPager();
		
		refresh_data_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!ConnectionDetector.isConnectingToInternet(GoodsInfoActivity.this)) {
					Toast.makeText(GoodsInfoActivity.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
					return;
				}
				closeTask();
				task = new Task();
				task.execute();
			}
		});
	}
	
	private void closeTask(){
		if(task != null && task.getStatus().RUNNING == AsyncTask.Status.RUNNING){
			task.cancel(true);
		}
	}
	private ProgressDialog bar;
	
	private boolean isTimeout = false;
	private class Task extends AsyncTask<Void, Void, Boolean>{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			bar.show();
			bar = (ProgressDialog) new YLProgressDialog(GoodsInfoActivity.this)
			.createLoadingDialog(GoodsInfoActivity.this, null);
	bar.setOnCancelListener(new DialogInterface.OnCancelListener() {

		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			cancel(true);
		}
	});
		}
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(!result){
				if (isTimeout) {
					Toast.makeText(
							GoodsInfoActivity.this,
							GoodsInfoActivity.this.getResources()
							.getString(R.string.time_out),
							Toast.LENGTH_SHORT).show();
					isTimeout = false;
					bar.dismiss();
					return;
				}
				Toast.makeText(GoodsInfoActivity.this, getResources().getString(R.string.no_product), Toast.LENGTH_LONG).show();
			} else{
				goods_framelayout.setVisibility(View.VISIBLE);
				refresh_view.setVisibility(View.GONE);
				initView();
				initViewPager();
			}
			bar.dismiss();
		}
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			GetProductAddress product = new GetProductAddress();
			try {
				String httpresp;
				try {
					httpresp = product.getProductJsonString(goodsId);
					boolean issuccess = product.analysis(httpresp);
					info = product.getProductBaseInfo();
					return issuccess;
				} catch (TimeOutEx e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					isTimeout = true;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
	}
	
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		closeTask();
	}

	private TextView titleTextView;

	private void setActionbarConfig() {
		getSupportActionBar().setCustomView(R.layout.actionbar_compose);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		titleTextView = (TextView) findViewById(R.id.compose_title);
		// titleTextView.setText("商品展示") ;
		setTitle("商品展示");
		ImageView leftImageView = (ImageView) findViewById(R.id.compose_back);

		leftImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				GoodsInfoActivity.this.finish();
			}
		});
	}

	private void setTitle(String title) {
		titleTextView.setText(title);
	}

	/**
	 * 
	*/
	private void initView() {
		goodsBasicInfo = (TextView) findViewById(R.id.goods_basic_info);
		goodsDetails = (TextView) findViewById(R.id.goods_details);
		goodsReviews = (TextView) findViewById(R.id.goods_reviews);

		goodsBasicInfo.setTextColor(Color.rgb(112, 44, 145));

		goodsBasicInfo.setOnClickListener(new TabOnClickListener(1));
		goodsReviews.setOnClickListener(new TabOnClickListener(0));
		goodsDetails.setOnClickListener(new TabOnClickListener(2));
		
	}

	private void initViewPager() {
		// cartButotn = (Button)findViewById(R.id.shopping_cart_button);//购物车
		// number =
		// Integer.parseInt(cartButotn.getText().toString());//获取购物车上的数据

		try {
//			LayoutInflater mInflater = getLayoutInflater();
//			View activityView = mInflater.inflate(
//					R.layout.item_goods_base_info, null);
//			ImageView cartAdd = (ImageView) activityView
//					.findViewById(R.id.add_to_cart);// 加入购物车
//			cartAdd.setOnClickListener(this);

//			goodsViewPager = (Fragment) findViewById(R.id.goods_framelayout);
			fragmentsList = new ArrayList<Fragment>();
			// Fragment emulate = BaseInfoFragment.newInstance("0");
			Fragment emulate = CommentFragment.newInstance(goodsId);
			Fragment baseInfo = BaseInfoFragment.newInstance(info);
			// Fragment last = BaseInfoFragment.newInstance("2");
			Fragment last = GoodsDetailFragment.newInstance(info
					.getProductDetailUrl());

			fragmentsList.add(emulate);
			fragmentsList.add(baseInfo);
			fragmentsList.add(last);
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.add(R.id.goods_framelayout, fragmentsList.get(1)).commit();
			currIndex = 1;

//			goodsViewPager.setAdapter(new BaseFragmentPagerAdapter(
//					getSupportFragmentManager(), fragmentsList));
//			goodsViewPager.setCurrentItem(currIndex);
//			goodsViewPager
//					.setOnPageChangeListener(new GoodsPagerChangeListener());
//			goodsViewPager.setOffscreenPageLimit(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			Toast.makeText(
//					GoodsInfoActivity.this,
//					GoodsInfoActivity.this.getResources().getString(
//							R.string.bad_network), Toast.LENGTH_SHORT).show();
		}
		// goodsViewPager.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
	}
	
	/**
	 * 切换fragment 
	 * @param index
	 */
	private void changeFragment(int index){
		// 用于切换时保存fragment
		try {
			if(fragmentsList.get(index) == null || fragmentsList.get(currIndex) == null) return;
			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			if (fragmentsList.get(index).isAdded())
				ft.hide(fragmentsList.get(currIndex))
				.show(fragmentsList.get(index)).commit();
			else
				ft.hide(fragmentsList.get(currIndex))
				.add(R.id.goods_framelayout, fragmentsList.get(index))
				.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

//	public class GoodsPagerChangeListener implements OnPageChangeListener {
//
//		@Override
//		public void onPageScrollStateChanged(int arg0) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void onPageScrolled(int arg0, float arg1, int arg2) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void onPageSelected(int arg0) {
//			// TODO Auto-generated method stub
//			setBackgrounDefault(currIndex);
//			switch (arg0) {
//			case 0:
//				goodsReviews
//						.setBackgroundResource(R.drawable.product_details_selected);
//				goodsReviews.setTextColor(Color.rgb(112, 44, 145));
//				goodsBasicInfo
//						.setBackgroundResource(R.drawable.product_details_bg);
//				goodsDetails
//						.setBackgroundResource(R.drawable.product_details_bg);
//				break;
//			case 1:
//				goodsReviews
//						.setBackgroundResource(R.drawable.product_details_bg);
//				goodsBasicInfo
//						.setBackgroundResource(R.drawable.product_details_selected);
//				goodsBasicInfo.setTextColor(Color.rgb(112, 44, 145));
//				goodsDetails
//						.setBackgroundResource(R.drawable.product_details_bg);
//				break;
//			case 2:
//				goodsReviews
//						.setBackgroundResource(R.drawable.product_details_bg);
//				goodsBasicInfo
//						.setBackgroundResource(R.drawable.product_details_bg);
//				goodsDetails
//						.setBackgroundResource(R.drawable.product_details_selected);
//				goodsDetails.setTextColor(Color.rgb(112, 44, 145));
//				break;
//			default:
//				break;
//			}
////			fragmentsList.get(currIndex).onPause(); // 调用切换前Fargment的onPause()
//////        fragments.get(currentPageIndex).onStop(); // 调用切换前Fargment的onStop()
////			if(fragmentsList.get(arg0).isAdded()){
//////            fragments.get(i).onStart(); // 调用切换后Fargment的onStart()
////				fragmentsList.get(arg0).onResume(); // 调用切换后Fargment的onResume()
////			}
//			currIndex = arg0;
//		}
//	}
	/**
	 * 设置选中的项的字体颜色和背景
	 * @param index 选中项下标
	 */
	private void setNowBackground(int index) {
		switch (index) {
		case 0:
			goodsReviews.setBackgroundResource(R.drawable.product_details_selected);
			goodsReviews.setTextColor(Color.rgb(112, 44, 145));
			break;
		case 1:
			goodsBasicInfo.setBackgroundResource(R.drawable.product_details_selected);
			goodsBasicInfo.setTextColor(Color.rgb(112, 44, 145));
			break;
		case 2:
			goodsDetails.setBackgroundResource(R.drawable.product_details_selected);
			goodsDetails.setTextColor(Color.rgb(112, 44, 145));
			break;
		default:
			break;
		}
	}

	/**
	 * 设置上次选中项为默认字体颜色和背景
	 * @param lastIndex 上次选中下标
	 */
	private void setBackgrounDefault(int lastIndex) {
		switch (lastIndex) {
		case 0:
			goodsReviews.setTextColor(Color.BLACK);
			goodsReviews.setBackgroundResource(R.drawable.product_details_bg);
			break;
		case 1:
			goodsBasicInfo.setTextColor(Color.BLACK);
			goodsBasicInfo.setBackgroundResource(R.drawable.product_details_bg);
			break;
		case 2:
			goodsDetails.setTextColor(Color.BLACK);
			goodsDetails.setBackgroundResource(R.drawable.product_details_bg);
			break;

		default:
			break;
		}
	}

	private class TabOnClickListener implements View.OnClickListener {
		private int index = -1;

		public TabOnClickListener(int index) {
			this.index = index;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
//			goodsViewPager.setCurrentItem(index);
			if(index == currIndex) return;//选中项为当前项（上次选中项） 则跳过
			setBackgrounDefault(currIndex);//设置上次选中项的字体颜色和背景
			setNowBackground(index);//设置选中项的字体颜色和背景
			changeFragment(index);//跳转到不同的页面
			currIndex = index;//改变当前项下标
		}

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		++number;
		cartButotn.setText(number + "");
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		StatService.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		StatService.onResume(this);
	}
}
