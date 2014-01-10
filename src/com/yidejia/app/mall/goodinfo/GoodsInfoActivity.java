package com.yidejia.app.mall.goodinfo;

import java.io.IOException;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.model.ProductBaseInfo;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.goodsinfo.GetProductAddress;
import com.yidejia.app.mall.widget.YLProgressDialog;

/**
 * 商品基本信息类
 * 
 * @author long bin
 * 
 */
public class GoodsInfoActivity extends SherlockActivity implements
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
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		setActionbarConfig();
		setContentView(R.layout.activity_goods_info_layout);
		
		goods_framelayout = (FrameLayout) findViewById(R.id.goods_framelayout);
		refresh_view = (RelativeLayout) findViewById(R.id.good_item_refresh_view);
		refresh_data_btn = (ImageView) findViewById(R.id.refresh_data_btn);
		
		if (bundle != null) {
			goodsId = bundle.getString("goodsId");
			if (ConnectionDetector.isConnectingToInternet(this)) {
				closeTask();
				task = new Task();
				task.execute();
				
			} else {
				goods_framelayout.setVisibility(View.GONE);
				refresh_view.setVisibility(View.VISIBLE);
			}
		}
		
		refresh_data_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
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
			super.onPreExecute();
			bar = (ProgressDialog) new YLProgressDialog(GoodsInfoActivity.this)
			.createLoadingDialog(GoodsInfoActivity.this, null);
	bar.setOnCancelListener(new DialogInterface.OnCancelListener() {

		@Override
		public void onCancel(DialogInterface dialog) {
			cancel(true);
		}
	});
		}
		@Override
		protected void onPostExecute(Boolean result) {
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
			GetProductAddress product = new GetProductAddress();
			try {
				String httpresp;
				try {
					httpresp = product.getProductJsonString(goodsId);
					boolean issuccess = product.analysis(httpresp);
					info = product.getProductBaseInfo();
					return issuccess;
				} catch (TimeOutEx e) {
					e.printStackTrace();
					isTimeout = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
	}
	
	

	@Override
	protected void onDestroy() {
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

		/*try {
			fragmentsList = new ArrayList<Fragment>();
			Fragment emulate = CommentFragment.newInstance(goodsId);
			Fragment baseInfo = BaseInfoFragment.newInstance(info);
			Fragment last = GoodsDetailFragment.newInstance(info
					.getProductDetailUrl());

			fragmentsList.add(emulate);
			fragmentsList.add(baseInfo);
			fragmentsList.add(last);
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.add(R.id.goods_framelayout, fragmentsList.get(1)).commit();
			currIndex = 1;

		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	/**
	 * 切换fragment 
	 * @param index
	 */
	private void changeFragment(int index){
		// 用于切换时保存fragment
		/*try {
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
			e.printStackTrace();
		}*/
	}
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
		++number;
		cartButotn.setText(number + "");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onResume(this);
	}
}
