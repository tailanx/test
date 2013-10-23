package com.yidejia.app.mall.view;

import java.util.ArrayList;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.adapter.IntegeralFragmentAdapter;
import com.yidejia.app.mall.fragment.ExchangeFragment;
import com.yidejia.app.mall.fragment.FreeGivingFragment;
import com.yidejia.app.mall.fragment.IntegeralFragment;

public class ExchangeFreeActivity extends SherlockFragmentActivity {
	    private static final String TAG = "MainActivity";
	    private ViewPager mPager;
	    private ArrayList<Fragment> fragmentsList;
	 
	    private TextView mCoupons,mIntegeral;
	    private ImageView ivBottomLine;
	    private int currIndex = 0;
	    private int bottomLineWidth;
	    private int offset = 0;
	    private int position_one;
	    private int position_two;
	    private int position_three;
	    private Resources resources;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBar();
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.pay_free);
		resources = getResources();
		InitWidth();
		InitTextView();
		InitViewPager();
		
	}
	private void setActionBar(){
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setCustomView(R.layout.actionbar_common);
		ImageView back = (ImageView) findViewById(R.id.actionbar_left);
		Button confirm = (Button)findViewById(R.id.actionbar_right);
		TextView  titleTextView = (TextView) findViewById(R.id.actionbar_title);
		titleTextView.setText("商品赠送换购");
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			ExchangeFreeActivity.this.finish();	
			}
		});
		confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ExchangeFreeActivity.this,CstmPayActivity.class);
				ExchangeFreeActivity.this.startActivity(intent);
				
			}
		});
		
	}

	private void InitTextView(){
		mCoupons = (TextView)findViewById(R.id.pay_free_voucher_Coupons);
		mIntegeral = (TextView)findViewById(R.id.pay_free_voucher_mIntegeral);
		mCoupons.setOnClickListener(new MyOnClickListener(1));
		mIntegeral.setOnClickListener(new MyOnClickListener(2));
	}
	private void InitViewPager(){
		 mPager = (ViewPager) findViewById(R.id.avPager);
		 fragmentsList = new ArrayList<Fragment>();
		 LayoutInflater mInflater = getLayoutInflater();
	    
	     
	     Fragment freeGivingFragment = FreeGivingFragment.newInstance("zengsong");
	     Fragment exchangeFragment = ExchangeFragment.newInstance("huangou");
	     
	    
	     fragmentsList.add(freeGivingFragment);
	     fragmentsList.add(exchangeFragment);
	     
	     mPager.setAdapter(new IntegeralFragmentAdapter(this.getSupportFragmentManager(), fragmentsList));
	     mPager.setCurrentItem(0);
	     mPager.setOnPageChangeListener(new MyOnPageChangeListener());
			
	     
	}

	
	private void InitWidth() {
		ivBottomLine = (ImageView) findViewById(R.id.iv_bottom_line);//滑动
        bottomLineWidth = ivBottomLine.getLayoutParams().width;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);//获取当前屏幕的属性
        int screenW = dm.widthPixels;//屏幕的宽
        offset = (int) ((screenW / 3 - bottomLineWidth)/2);//起始位置

        position_one = (int) (screenW / 3);
        position_two = position_one * 2;
        
    }
	  public class MyOnClickListener implements View.OnClickListener {
	        private int index = 0;

	        public MyOnClickListener(int i) {
	            index = i;
	        }

	        @Override
	        public void onClick(View v) {
	            mPager.setCurrentItem(index);
	        }
	    };
	    public class MyOnPageChangeListener implements OnPageChangeListener {

	        @Override
	        public void onPageSelected(int arg0) {
	            Animation animation = null;
	            switch (arg0) {
	            case 0://假如是第一个被选中，添加事件
	                if (currIndex == 1) {//当前是第二个
	                	mIntegeral.setPressed(false);
	                    animation = new TranslateAnimation(position_one, 0, 0, 0);
	                    mIntegeral.setTextColor(Color.parseColor("#ed217c"));
	                } 
	                mCoupons.setPressed(true);
	                mCoupons.setBackgroundResource(R.drawable.produce_textview_selector);
	                mCoupons.setTextColor(Color.parseColor("#702c91"));
	                break;
	            case 1:
	                if (currIndex == 0) {
	                	mCoupons.setPressed(false);
	                	mCoupons.setBackgroundResource(R.drawable.product_details_bg);
	                    animation = new TranslateAnimation(offset, position_one, 0, 0);
	                    mCoupons.setTextColor(Color.parseColor("#ed217c"));
	                } 
	                mIntegeral.setPressed(true);
	                mIntegeral.setBackgroundResource(R.drawable.produce_textview_selector);
	                mIntegeral.setTextColor(Color.parseColor("#702c91"));
	                break;
	           
	            }
	            currIndex = arg0;
	            animation.setFillAfter(true);
	            animation.setDuration(300);
	            ivBottomLine.startAnimation(animation);
	        }

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
	    }

}
