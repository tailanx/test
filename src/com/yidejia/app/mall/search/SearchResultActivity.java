package com.yidejia.app.mall.search;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.jeremyfeinstein.slidingmenu.SlidingFragmentActivity;
import com.jeremyfeinstein.slidingmenu.SlidingMenu;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.R;

public class SearchResultActivity extends SlidingFragmentActivity {

	private TextView selledTextView;
	private LinearLayout priceLayout;
	private TextView priceTextView;
	private TextView popularityTextView;
	private ImageView showWithList;
	private ImageView ivPriceUpDown;
//	private ImageView showWithImage;
//	private SelledResultFragment newFragment;
	
	private int lastIndex = 0;
	private boolean isShowWithList = true;
	private Bundle bundle;
	private String title = "";
	
//	private SelledResultFragment sellFragment;
//	private SelledResultFragment priceFragment;
//	private SelledResultFragment needsFragment;
//	private int currindex = 0;
//	private SelledResultFragment currFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarConfig();
//		setActionbarConfig();
		bundle = getIntent().getExtras();
		if(bundle != null){
			title = bundle.getString("title");
			isShowWithList = bundle.getBoolean("isShowWithList", true);
			setTitle(title);
		}
		try {
			setContentView(R.layout.activity_search_result_info_layout);
			setSlidingMenuConfig();
			setRightFilterMenu(savedInstanceState);
			initView();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void initView(){
		selledTextView = (TextView) findViewById(R.id.tv_search_result_selled);
		priceLayout = (LinearLayout) findViewById(R.id.ll_search_result_price_layout);
		priceTextView = (TextView) findViewById(R.id.tv_search_result_price);
		popularityTextView = (TextView) findViewById(R.id.tv_search_result_popularity);
		ivPriceUpDown = (ImageView) findViewById(R.id.iv_search_result_price);
		ivPriceUpDown.setEnabled(false);
//		showWithImage = (ImageView) findViewById(R.id.search_with_image);
		selledTextView.setSelected(true);
		setFragment(0, isShowWithList);
//		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//		Bundle newBundle = (Bundle) bundle.clone();
//		sellFragment = SelledResultFragment.newInstance(newBundle, isShowWithList);
//		ft.replace(R.id.search_result_fragment, sellFragment).commit();
//		currFragment = sellFragment;
		
		selledTextView.setOnClickListener(new AddFragmentListener(0));
		priceLayout.setOnClickListener(new AddFragmentListener(1));
		popularityTextView.setOnClickListener(new AddFragmentListener(2));
//		showWithImage.setOnClickListener(new AddFragmentListener(4));
	}
	
	
	private void setFragment(int index, boolean isShowWithList){
		Bundle newBundle = (Bundle) bundle.clone();
		SelledResultFragment newFragment = null;
		switch (index) {
		case 0:
			newBundle.putString("order", "sells+desc");
			break;
		case 1:
			String orderString = "";
			if(isDesc) orderString = "price+desc";
			else orderString = "price+asc";
			newBundle.putString("order", orderString);
			break;
		case 2:
			newBundle.putString("order", "needs+desc");
			break;

		default:
			break;
		}
		newFragment = SelledResultFragment.newInstance(newBundle, isShowWithList);
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//		if(newFragment.isAdded()) ft.hide(currFragment).show(newFragment).commit();
//		else ft.hide(currFragment).replace(R.id.search_result_fragment, newFragment).commit();
		ft.replace(R.id.fl_search_result_fragment, newFragment).commit();
        newBundle = null;
//        currindex = index;
//        currFragment = newFragment;
	}
	
	private TextView titleTextView;
	private void setActionBarConfig(){
		getSupportActionBar().setCustomView(R.layout.actionbar_search_result);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		
		TextView rightBtn = (TextView) findViewById(R.id.actionbar_right);
		rightBtn.setText(getResources().getString(R.string.filter));
		rightBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showMenu();
			}
		});
		
		TextView actionbar_left = (TextView) findViewById(R.id.actionbar_left);
		actionbar_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SearchResultActivity.this.finish();
			}
		});
		
		titleTextView = (TextView) findViewById(R.id.actionbar_title);
		
		showWithList = (ImageView) findViewById(R.id.search_with_list);
		showWithList.setOnClickListener(new AddFragmentListener(3));
	}
	
	private void setTitle(String title){
		titleTextView.setText(title);
	}
	
	private Fragment mFrag;
	private void setRightFilterMenu(Bundle savedInstanceState){
		setBehindContentView(R.layout.activity_frame_left_menu);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager()
					.beginTransaction();
				mFrag = new FilterFragment();
			t.add(R.id.menu_frame, mFrag);
			t.commit();
		} else {
			mFrag = (Fragment) this.getSupportFragmentManager()
					.findFragmentById(R.id.menu_frame);
		}
	}
	
	private void setSlidingMenuConfig(){
		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);//设置侧滑的阴影宽度
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setAboveOffset(R.dimen.above_offset);
		sm.setBehindScrollScale(0);
		sm.setFadeDegree(0f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		sm.setMode(SlidingMenu.RIGHT);
		setSlidingActionBarEnabled(true);
	}
	
	private boolean isDesc = false;
	
	private class AddFragmentListener implements View.OnClickListener{
		
		private int clickIndex;
		public AddFragmentListener(int clickIndex){
			this.clickIndex = clickIndex;
		}
		
		@Override
		public void onClick(View v) {
			switch (clickIndex) {
			case 0: //考虑settag
				if(clickIndex == lastIndex) return;
//					setTabBackground(lastIndex);
				lastIndex = 0;
//				selledTextView.setTextColor(Color.rgb(112, 44, 145));
//				selledTextView.setBackgroundResource(R.drawable.product_details_selected);
				selledTextView.setSelected(true);
				priceTextView.setSelected(false);
				popularityTextView.setSelected(false);
				priceLayout.setSelected(false);
				ivPriceUpDown.setEnabled(false);
				
				setFragment(0, isShowWithList);
//				priceClickCount = 0;
				isDesc = false;
				break;
			case 1: //考虑settag
				if(clickIndex != lastIndex){
					isDesc = true;
//					setTabBackground(lastIndex);
//					priceTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.price_sort_down, 0);
				} else {
//					priceClickCount++;
					isDesc = !isDesc;
				}
				if(isDesc){
//					priceTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.price_sort_down, 0);
//					priceTextView.setText(getResources().getString(R.string.price) + "↓");
				} else {
//					priceTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.price_sort_up, 0);
//					priceTextView.setText(getResources().getString(R.string.price) + "↑");
				}
				ivPriceUpDown.setEnabled(true);
				ivPriceUpDown.setSelected(isDesc);
				lastIndex = 1;
				selledTextView.setSelected(false);
				priceTextView.setSelected(true);
				popularityTextView.setSelected(false);
				priceLayout.setSelected(true);
//				priceLayout.setBackgroundResource(R.drawable.product_details_selected);
				setFragment(1, isShowWithList);
				break;
			case 2: //考虑settag
				if(clickIndex == lastIndex) return;
//					setTabBackground(lastIndex);
				lastIndex = 2;
				selledTextView.setSelected(false);
				priceTextView.setSelected(false);
				popularityTextView.setSelected(true);
				priceLayout.setSelected(false);
				ivPriceUpDown.setEnabled(false);
//				popularityTextView.setBackgroundResource(R.drawable.product_details_selected);
				setFragment(2, isShowWithList);
//				priceClickCount = 0;
				isDesc = false;
				break;
			case 3:
//				if(isShowWithList) break;
				showWithList.setSelected(isShowWithList);
				isShowWithList = !isShowWithList;
//				showWithList.setImageResource(R.drawable.list_hover);
//				showWithImage.setImageResource(R.drawable.thumbnails_normal);
				setFragment(lastIndex, isShowWithList);
				break;
			case 4:
				if(!isShowWithList) break;
				isShowWithList = false;
//				showWithImage.setImageResource(R.drawable.thumbnails_hover);
				showWithList.setImageResource(R.drawable.list_normal);
				setFragment(lastIndex, isShowWithList);
				break;
			default:
				break;
			}
		}
	}
	
	/*private void setTabBackground(int lastIndex){
		switch (lastIndex) {
		case 0:
			selledTextView.setBackgroundResource(R.drawable.product_details_bg);
			selledTextView.setTextColor(Color.BLACK);
			break;
		case 1:
			priceLayout.setBackgroundResource(R.drawable.product_details_bg);
			priceTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.price_normal, 0);
			priceTextView.setTextColor(Color.BLACK);
			break;
		case 2:
			popularityTextView.setBackgroundResource(R.drawable.product_details_bg);
			popularityTextView.setTextColor(Color.BLACK);
			break;
		default:
			break;
		}
	}*/
	
	public void selectContent(Bundle bundle){
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			public void run() {
				getSlidingMenu().showContent();
			}
		}, 20);
		String name = this.bundle.get("name").toString();
		this.bundle = bundle;
		this.bundle.putString("name", name);
		setFragment(0, isShowWithList);
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