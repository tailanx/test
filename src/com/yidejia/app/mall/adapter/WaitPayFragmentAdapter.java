package com.yidejia.app.mall.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class WaitPayFragmentAdapter extends FragmentPagerAdapter {
	private ArrayList<Fragment> fragmentlist;
	

	public WaitPayFragmentAdapter(FragmentManager fm) {
		super(fm);
	}
	public WaitPayFragmentAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
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
