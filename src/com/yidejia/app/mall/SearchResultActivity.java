package com.yidejia.app.mall;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.SlidingFragmentActivity;
import com.jeremyfeinstein.slidingmenu.SlidingMenu;
import com.yidejia.app.mall.fragment.FilterFragment;
import com.yidejia.app.mall.fragment.SelledResultFragment;

public class SearchResultActivity extends SlidingFragmentActivity {

	private TextView selledTextView;
	private LinearLayout priceLayout;
	private TextView priceTextView;
	private TextView popularityTextView;
	private ImageView showWithList;
	private ImageView showWithImage;
	private SelledResultFragment newFragment;
	
	private int lastIndex = 0;
	private boolean isShowWithList = true;
//	private ArrayList<SearchItem> searchResults;
	private Bundle bundle;
	private String title = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setActionBarConfig();
		bundle = getIntent().getExtras();
		if(bundle != null){
//			String fun = bundle.getString("fun");
//			SearchDataManage manage = new SearchDataManage(this);
//			searchResults = manage.getSearchArray("", fun, "", "", "", "0", "1");
//			if(searchResults.size() != 0){
//			}
			title = bundle.getString("title");
			setTitle(title);
//			bundle.putString("order", "sells");
		}
		try {
			setContentView(R.layout.activity_search_result_info_layout);
			setSlidingMenuConfig();
			setRightFilterMenu(savedInstanceState);
			Log.e("SearchResultActivity", "begin initView");
			initView();
			Log.e("SearchResultActivity", "finish initView");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		ListView resultList = (ListView) findViewById(R.id.search_listview);
//		resultList.setAdapter(new SearchResultListAdapter(SearchResultActivity.this));
		
	}
	
	private void initView(){
		selledTextView = (TextView) findViewById(R.id.search_result_selled);
		priceLayout = (LinearLayout) findViewById(R.id.search_result_price_layout);
		priceTextView = (TextView) findViewById(R.id.search_result_price);
		popularityTextView = (TextView) findViewById(R.id.search_result_popularity);
		showWithImage = (ImageView) findViewById(R.id.search_with_image);
		showWithList = (ImageView) findViewById(R.id.search_with_list);
		
		Log.e("SearchResultActivity", "begin setFragment");
//		setFragment(0, isShowWithList);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Bundle newBundle = (Bundle) bundle.clone();
		newBundle.putString("order", "sells+desc");
		sellFragment = SelledResultFragment.newInstance(newBundle, isShowWithList);
		ft.replace(R.id.search_result_fragment, sellFragment).commit();
		currFragment = sellFragment;
		Log.e("SearchResultActivity", "finish setFragment");
		
		selledTextView.setOnClickListener(new AddFragmentListener(0));
		priceLayout.setOnClickListener(new AddFragmentListener(1));
		popularityTextView.setOnClickListener(new AddFragmentListener(2));
		showWithList.setOnClickListener(new AddFragmentListener(3));
		showWithImage.setOnClickListener(new AddFragmentListener(4));
	}
	
	
	private SelledResultFragment sellFragment;
	private SelledResultFragment priceFragment;
	private SelledResultFragment needsFragment;
	private int currindex = 0;
	private SelledResultFragment currFragment;
	
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
		ft.replace(R.id.search_result_fragment, newFragment).commit();
        newBundle = null;
        currindex = index;
        currFragment = newFragment;
	}
	
	private TextView titleTextView;
	private void setActionBarConfig(){
		getSupportActionBar().setCustomView(R.layout.actionbar_common);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		
		Button rightBtn = (Button) findViewById(R.id.actionbar_right);
//		rightBtn.setBackgroundResource(resid);
		rightBtn.setText(getResources().getString(R.string.filter));
		rightBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showMenu();
			}
		});
		
		ImageView actionbar_left = (ImageView) findViewById(R.id.actionbar_left);
		actionbar_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SearchResultActivity.this.finish();
			}
		});
		
		titleTextView = (TextView) findViewById(R.id.actionbar_title);
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
//		sm.setSelectorEnabled(true);
//		sm.setSelectedView(getSlidingMenu().getMenu());
		sm.setFadeDegree(0f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
//		sm.setTouchmodeMarginThreshold(80);
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
			// TODO Auto-generated method stub
			switch (clickIndex) {
			case 0: //考虑settag
				if(clickIndex != lastIndex)
					setTabBackground(lastIndex);
				lastIndex = 0;
				selledTextView.setTextColor(Color.rgb(112, 44, 145));
				selledTextView.setBackgroundResource(R.drawable.product_details_selected);
				setFragment(0, isShowWithList);
//				priceClickCount = 0;
				isDesc = false;
				break;
			case 1: //考虑settag
				if(clickIndex != lastIndex){
					isDesc = true;
					setTabBackground(lastIndex);
					priceTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.price_sort_down, 0);
				} else {
//					priceClickCount++;
					isDesc = !isDesc;
					if(isDesc){
						priceTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.price_sort_down, 0);
					} else {
						priceTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.price_sort_up, 0);
					}
				}
				lastIndex = 1;
				priceTextView.setTextColor(Color.rgb(112, 44, 145));
				priceLayout.setBackgroundResource(R.drawable.product_details_selected);
				setFragment(1, isShowWithList);
				break;
			case 2: //考虑settag
				if(clickIndex != lastIndex)
					setTabBackground(lastIndex);
				lastIndex = 2;
				popularityTextView.setTextColor(Color.rgb(112, 44, 145));
				popularityTextView.setBackgroundResource(R.drawable.product_details_selected);
				setFragment(2, isShowWithList);
//				priceClickCount = 0;
				isDesc = false;
				break;
			case 3:
				if(isShowWithList) break;
				isShowWithList = true;
				showWithList.setImageResource(R.drawable.list_hover);
				showWithImage.setImageResource(R.drawable.thumbnails_normal);
				setFragment(lastIndex, isShowWithList);
				break;
			case 4:
				if(!isShowWithList) break;
				isShowWithList = false;
				showWithImage.setImageResource(R.drawable.thumbnails_hover);
				showWithList.setImageResource(R.drawable.list_normal);
				setFragment(lastIndex, isShowWithList);
				break;
			default:
				break;
			}
		}
	}
	
	private void setTabBackground(int lastIndex){
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
	}
	
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
}