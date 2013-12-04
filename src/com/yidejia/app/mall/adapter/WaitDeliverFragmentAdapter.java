package com.yidejia.app.mall.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class WaitDeliverFragmentAdapter extends FragmentPagerAdapter {
	private ArrayList<Fragment> fragmentlist;
	

	public WaitDeliverFragmentAdapter(FragmentManager fm) {
		super(fm);
	}
	public WaitDeliverFragmentAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragmentlist = fragments;
    }


	
	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragmentlist.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragmentlist.size();
	}

}
