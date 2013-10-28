package com.yidejia.app.mall.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class YLViewPager extends ViewPager {

	public YLViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public YLViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
	
	@Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean ret = super.onInterceptTouchEvent(ev);
        if (ret)
            getParent().requestDisallowInterceptTouchEvent(true);
        return ret;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean ret = super.onTouchEvent(ev);
        if (ret)
            getParent().requestDisallowInterceptTouchEvent(true);
        return ret;
    }
}
