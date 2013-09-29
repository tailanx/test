package com.yidejia.app.mall.adapter;

import java.util.ArrayList;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AlreadyOrderFragmentAdapter extends FragmentPagerAdapter {
	private ArrayList<Fragment> fragmentlist;
	

	public AlreadyOrderFragmentAdapter(FragmentManager fm) {
		super(fm);
	}
	public AlreadyOrderFragmentAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
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
