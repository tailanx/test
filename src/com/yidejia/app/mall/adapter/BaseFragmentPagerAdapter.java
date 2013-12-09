package com.yidejia.app.mall.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter{
	private ArrayList<Fragment> fragmentsList;
	private int count = 0;
	private FragmentManager fm;
	
	public BaseFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.fm = fm;
	}
	
	public BaseFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentsList){
		super(fm);
		this.fm = fm;
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
//		Fragment fragment = fragmentsList.get(position);
//		if(!fragment.isAdded()){
//			//如果还没有添加进去
//			FragmentTransaction ft = fm.beginTransaction();
//			ft.add(fragment, fragment.getClass().getSimpleName());
//			ft.commit();
//		} else {
////			FragmentTransaction ft = fm.beginTransaction();
////			ft.show(fragment).commit();
//		}
//		
//		if(fragment.getView().getParent() == null){
//             container.addView(fragment.getView()); // 为viewpager增加布局
//        }
////		return container;
//        return fragment.getView();
	}
	
}
