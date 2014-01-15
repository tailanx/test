package com.yidejia.app.mall.ctrl;

import java.util.ArrayList;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.fragment.AllOrderFragment;

public class OrderViewCtrl {
	
	private int currIndex = 0;
	private ArrayList<Fragment> fragmentsList;
	private TextView mWeek, mMonth, mYear;
	
	private FragmentActivity activity;
	
	public OrderViewCtrl(FragmentActivity activity){
		this.activity = activity;
	}
	
	public void viewCtrl(int orderType){
		initTextView();
		initViewPager(orderType);
	}
	
	private void initTextView() {
		mWeek = (TextView) activity.findViewById(R.id.all_order_week);
		mMonth = (TextView) activity.findViewById(R.id.all_order_moonth);
		mYear = (TextView) activity.findViewById(R.id.all_order_year);
		mWeek.setSelected(true);
		mWeek.setOnClickListener(new MyOnClickListener(0));
		mMonth.setOnClickListener(new MyOnClickListener(1));
		mYear.setOnClickListener(new MyOnClickListener(2));
	}

	private void initViewPager(int orderType) {
		// mPager = (ViewPager) findViewById(R.id.all_order_vPager);
		fragmentsList = new ArrayList<Fragment>();
//		LayoutInflater mInflater = getLayoutInflater();
		// View view = mInflater.inflate(R.layout.all_order_item_main, null);
		//
		// mPullToRefreshScrollView =
		// (PullToRefreshScrollView)view.findViewById(R.id.all_order_item_main_refresh_scrollview11);
		// mPullToRefreshScrollView.setScrollingWhileRefreshingEnabled(true);
		// //
		// mPullToRefreshScrollView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
		// mPullToRefreshScrollView.setVerticalScrollBarEnabled(false);
		// //���ô�ֱ����
		// mPullToRefreshScrollView.setHorizontalScrollBarEnabled(false);
		// //����ˮƽ����
		//

		Fragment weekfragment = AllOrderFragment.newInstance(orderType, 0);
		Fragment monthFragment = AllOrderFragment.newInstance(orderType, 1);
		Fragment yearFragment = AllOrderFragment.newInstance(orderType, 2);

		fragmentsList.add(weekfragment);
		fragmentsList.add(monthFragment);
		fragmentsList.add(yearFragment);

		/*
		 * mPager.setAdapter(new
		 * AllOrderFragmentAdapter(this.getSupportFragmentManager(),
		 * fragmentsList)); mPager.setCurrentItem(0);
		 * mPager.setOffscreenPageLimit(2); mPager.setOnPageChangeListener(new
		 * MyOnPageChangeListener());
		 */

		FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
		ft.add(R.id.order_framelayout, fragmentsList.get(0)).commit();
		currIndex = 0;
	}

	/*private void InitWidth() {
		ivBottomLine = (ImageView) findViewById(R.id.iv_bottom_line);// ����
		bottomLineWidth = ivBottomLine.getLayoutParams().width;
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);// ��ȡ��ǰ��Ļ������
		int screenW = dm.widthPixels;// ��Ļ�Ŀ�
		offset = (int) ((screenW / 3 - bottomLineWidth) / 2);// ��ʼλ��

		position_one = (int) (screenW / 3);
		position_two = position_one * 2;

	}*/

	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
//			mPager.setCurrentItem(index);
			changeBGAndFragment(index);
		}
	};

	private void changeBGAndFragment(int index) {
		switch (index) {
		case 0:// �����ǵ�һ����ѡ�У�����¼�
			if (currIndex == 1) {// ��ǰ�ǵڶ���
			// animation = new TranslateAnimation(position_one, 0, 0, 0);
				mMonth.setSelected(false);
//				mMonth.setBackgroundResource(R.drawable.product_details_bg);
				mMonth.setTextColor(Color.parseColor("#ec3587"));
			} else if (currIndex == 2) {
				// animation = new TranslateAnimation(position_two, 0, 0, 0);
				mYear.setSelected(false);
//				mYear.setBackgroundResource(R.drawable.product_details_bg);
				mYear.setTextColor(Color.parseColor("#ec3587"));
			}
			mWeek.setSelected(true);
			mWeek.setTextColor(Color.parseColor("#ffffff"));
//			mWeek.setBackgroundResource(R.drawable.product_details_selected);
			break;
		case 1:
			if (currIndex == 0) {
				// animation = new TranslateAnimation(offset, position_one, 0,
				// 0);
				mWeek.setSelected(false);
//				mWeek.setBackgroundResource(R.drawable.product_details_bg);
				mWeek.setTextColor(Color.parseColor("#ec3587"));
			} else if (currIndex == 2) {
				// animation = new TranslateAnimation(position_two,
				// position_one, 0, 0);
				mYear.setSelected(false);
//				mYear.setBackgroundResource(R.drawable.product_details_bg);
				mYear.setTextColor(Color.parseColor("#ec3587"));
			}
			mMonth.setSelected(true);
//			mMonth.setBackgroundResource(R.drawable.product_details_selected);
			mMonth.setTextColor(Color.parseColor("#ffffff"));
			break;
		case 2:
			if (currIndex == 0) {
				// animation = new TranslateAnimation(offset, position_two, 0,
				// 0);
				mWeek.setSelected(false);
//				mWeek.setBackgroundResource(R.drawable.product_details_bg);
				mWeek.setTextColor(Color.parseColor("#ec3587"));
			} else if (currIndex == 1) {
				mMonth.setSelected(false);
//				mMonth.setBackgroundResource(R.drawable.product_details_bg);
				// animation = new TranslateAnimation(position_one,
				// position_two, 0, 0);
				mMonth.setTextColor(Color.parseColor("#ec3587"));
			}
			mYear.setSelected(true);

			mYear.setTextColor(Color.parseColor("#ffffff"));
//			mYear.setBackgroundResource(R.drawable.product_details_selected);
			break;

		}
		changeFragment(index);
		currIndex = index;
	}
	
	/**
	 * 切换fragment 
	 * @param index
	 */
	private void changeFragment(int index){
		// 用于切换时保存fragment
		try {
			if(fragmentsList.get(index) == null || fragmentsList.get(currIndex) == null) return;
			FragmentTransaction ft = activity.getSupportFragmentManager()
					.beginTransaction();
//			if (fragmentsList.get(index).isAdded())
//				ft.hide(fragmentsList.get(currIndex))
//				.show(fragmentsList.get(index)).commit();
//			else
//				ft.hide(fragmentsList.get(currIndex))
//				.add(R.id.order_framelayout, fragmentsList.get(index))
//				.commit();
			ft.replace(R.id.order_framelayout, fragmentsList.get(index)).commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
