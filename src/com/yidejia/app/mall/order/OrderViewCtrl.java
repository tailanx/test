package com.yidejia.app.mall.order;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.yidejia.app.mall.R;

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
		fragmentsList = new ArrayList<Fragment>();

		Fragment weekfragment = AllOrderFragment.newInstance(orderType, 0);
		Fragment monthFragment = AllOrderFragment.newInstance(orderType, 1);
		Fragment yearFragment = AllOrderFragment.newInstance(orderType, 2);

		fragmentsList.add(weekfragment);
		fragmentsList.add(monthFragment);
		fragmentsList.add(yearFragment);

		FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
		ft.add(R.id.order_framelayout, fragmentsList.get(0)).commit();
		currIndex = 0;
	}

	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			changeBGAndFragment(index);
		}
	};

	/**
	 * 改变选卡的背景和切换页面
	 * @param index
	 */
	private void changeBGAndFragment(int index) {
		switch (index) {
		case 0:// �����ǵ�һ����ѡ�У�����¼�
			if (currIndex == 1) {// ��ǰ�ǵڶ���
				mMonth.setSelected(false);
			} else if (currIndex == 2) {
				mYear.setSelected(false);
			}
			mWeek.setSelected(true);
			break;
		case 1:
			if (currIndex == 0) {
				mWeek.setSelected(false);
			} else if (currIndex == 2) {
				mYear.setSelected(false);
			}
			mMonth.setSelected(true);
			break;
		case 2:
			if (currIndex == 0) {
				mWeek.setSelected(false);
			} else if (currIndex == 1) {
				mMonth.setSelected(false);
			}
			mYear.setSelected(true);
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
			ft.replace(R.id.order_framelayout, fragmentsList.get(index)).commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
