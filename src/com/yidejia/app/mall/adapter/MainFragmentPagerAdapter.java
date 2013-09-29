package com.yidejia.app.mall.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter{
	private ArrayList<Fragment> fragmentsList;
	private int count = 0;
	
	public MainFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
	
	public MainFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentsList){
		super(fm);
		this.fragmentsList = fragmentsList;
		count = fragmentsList.size();
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragmentsList.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
//		Log.d("BaseInfoFragment", "size"+fragmentsList.size());
		return count;
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return super.getItemPosition(object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		return super.instantiateItem(container, position);
	}
	
}
