package com.yidejia.app.mall;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.yidejia.app.mall.adapter.BaseFragmentPagerAdapter;
import com.yidejia.app.mall.datamanage.ProductDataManage;
import com.yidejia.app.mall.fragment.BaseInfoFragment;
import com.yidejia.app.mall.fragment.CommentFragment;
import com.yidejia.app.mall.fragment.GoodsDetailFragment;
import com.yidejia.app.mall.model.ProductBaseInfo;
import com.yidejia.app.mall.net.ConnectionDetector;
/**
 * 商品基本信息类
 * @author long bin
 *
 */
public class GoodsInfoActivity extends SherlockFragmentActivity implements OnClickListener {

	private TextView goodsBasicInfo;//基本信息
	private TextView goodsDetails;// 详情
	private TextView goodsReviews;// 评论
	private ViewPager goodsViewPager;
	private ArrayList<Fragment> fragmentsList;
	private int currIndex = 1;
	private int number=0;
	private Button cartButotn;
	private String goodsId;
	private ProductBaseInfo info;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			goodsId = bundle.getString("goodsId");
			if (ConnectionDetector.isConnectingToInternet(this)) {
				final ProductDataManage manage = new ProductDataManage(this);
				if(!"".equals(goodsId) && goodsId != null){
					info = manage.getProductData(goodsId);
//					manage.getTask(goodsId);
//					info = manage.setCallBackListener(new CallBack() {
//						
//						@Override
//						public void callback() {
//							// TODO Auto-generated method stub
////							manage.getTask(goodsId);
//						}
//					});
				}
			}
		}
		setActionbarConfig();
		setContentView(R.layout.activity_goods_info_layout);
		initView();
		initViewPager();
	}
	
	private TextView titleTextView;
	private void setActionbarConfig(){
		getSupportActionBar().setCustomView(R.layout.actionbar_compose);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		titleTextView = (TextView)findViewById(R.id.compose_title);
//		titleTextView.setText("商品展示") ;
		setTitle("商品展示");
		ImageView leftImageView = (ImageView) findViewById(R.id.compose_back);
		
		leftImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				GoodsInfoActivity.this.finish();
			}
		});
	}
	
	private void setTitle(String title){
		titleTextView.setText(title) ;
	}
	/**
	 * 
	*/
	private void initView(){
		goodsBasicInfo = (TextView) findViewById(R.id.goods_basic_info);
		goodsDetails = (TextView) findViewById(R.id.goods_details);
		goodsReviews = (TextView) findViewById(R.id.goods_reviews);
		
		goodsBasicInfo.setTextColor(Color.rgb(112, 44, 145));
		
		goodsBasicInfo.setOnClickListener(new TabOnClickListener(1));
		goodsReviews.setOnClickListener(new TabOnClickListener(0));
		goodsDetails.setOnClickListener(new TabOnClickListener(2));
	}
	
	private void initViewPager(){
//		 cartButotn =  (Button)findViewById(R.id.shopping_cart_button);//购物车
//		 number = Integer.parseInt(cartButotn.getText().toString());//获取购物车上的数据
		 
		 try {
			LayoutInflater mInflater = getLayoutInflater();
			 View activityView = mInflater.inflate(R.layout.item_goods_base_info, null);
			 ImageView cartAdd = (ImageView)activityView.findViewById(R.id.add_to_cart);//加入购物车
			 cartAdd.setOnClickListener(this);
				
			 
			 
			 
			goodsViewPager = (ViewPager) findViewById(R.id.goods_viewpager);
			fragmentsList = new ArrayList<Fragment>();
//		Fragment emulate = BaseInfoFragment.newInstance("0");
			Fragment emulate = CommentFragment.newInstance(goodsId);
			Fragment baseInfo = BaseInfoFragment.newInstance(info);
//		Fragment last = BaseInfoFragment.newInstance("2");
			Fragment last = GoodsDetailFragment.newInstance(info.getProductDetailUrl());
			
			
			fragmentsList.add(emulate);
			fragmentsList.add(baseInfo);
			fragmentsList.add(last);
			
			goodsViewPager.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragmentsList));
			goodsViewPager.setCurrentItem(currIndex);
			goodsViewPager.setOnPageChangeListener(new GoodsPagerChangeListener());
			goodsViewPager.setOffscreenPageLimit(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(GoodsInfoActivity.this, GoodsInfoActivity.this.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT)
			.show();
		}
//		goodsViewPager.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
	}
	
	public class GoodsPagerChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			setBackgrounDefault(currIndex);
			switch (arg0) {
			case 0:
				goodsReviews.setBackgroundResource(R.drawable.product_details_selected);
				goodsReviews.setTextColor(Color.rgb(112, 44, 145));
				goodsBasicInfo.setBackgroundResource(R.drawable.product_details_bg);
				goodsDetails.setBackgroundResource(R.drawable.product_details_bg);
				break;
			case 1:
				goodsReviews.setBackgroundResource(R.drawable.product_details_bg);
				goodsBasicInfo.setBackgroundResource(R.drawable.product_details_selected);
				goodsBasicInfo.setTextColor(Color.rgb(112, 44, 145));
				goodsDetails.setBackgroundResource(R.drawable.product_details_bg);
				break;
			case 2:
				goodsReviews.setBackgroundResource(R.drawable.product_details_bg);
				goodsBasicInfo.setBackgroundResource(R.drawable.product_details_bg);
				goodsDetails.setBackgroundResource(R.drawable.product_details_selected);
				goodsDetails.setTextColor(Color.rgb(112, 44, 145));
				break;
			default:
				break;
			}
			
			currIndex = arg0;
		}
	}
	
	private void setBackgrounDefault(int lastIndex){
		switch (lastIndex) {
		case 0:
			goodsReviews.setTextColor(Color.BLACK);
			break;
		case 1:
			goodsBasicInfo.setTextColor(Color.BLACK);
			break;
		case 2:
			goodsDetails.setTextColor(Color.BLACK);
			break;

		default:
			break;
		}
	}
	
	private class TabOnClickListener implements View.OnClickListener{
		private int index = -1;
		
		public TabOnClickListener(int index){
			this.index = index;
		}
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			goodsViewPager.setCurrentItem(index);
		}
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		++number;
		cartButotn.setText(number+"");
	}
}
