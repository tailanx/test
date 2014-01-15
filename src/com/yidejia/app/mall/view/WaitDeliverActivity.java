package com.yidejia.app.mall.view;

import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.adapter.AllOrderFragmentAdapter;
import com.yidejia.app.mall.ctrl.OrderViewCtrl;
import com.yidejia.app.mall.fragment.AllOrderFragment;

public class WaitDeliverActivity extends BaseActivity {
	    private static final String TAG = "MainActivity";
	    private ViewPager mPager;
	    private ArrayList<Fragment> fragmentsList;
	 
	    private TextView mWeek,mMonth,mYear;
	    private ImageView ivBottomLine;
	    private int currIndex = 0;
	    private int bottomLineWidth;
	    private int offset = 0;
	    private int position_one;
	    private int position_two;
	    private int position_three;
	    private Resources resources;

//	    public void doClick(View v){
//	    	Intent intent = new Intent();
//			switch (v.getId()) {
////			case R.id.wait_deliver_button1:
////				Intent intent = new Intent(WaitDeliverActivity.this,MyMallActivity.class);
////				startActivity(intent);
////				//����ǰActivity��
////				WaitDeliverActivity.this.finish();
////				break;
//			case R.id.wait_deliver_item_exchange://�˻���
//				intent.setClass(this, ReturnActivity.class);
//				break;
//			}
//			startActivity(intent);
//		}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionbarConfig();
		setTitle(getResources().getString(R.string.wait_deliver));
		setContentView(R.layout.all_order);
		OrderViewCtrl viewCtrl = new OrderViewCtrl(this);
		viewCtrl.viewCtrl(2);
	}

	private void InitTextView(){
		mWeek = (TextView)findViewById(R.id.wait_deliver_week);
		mMonth = (TextView)findViewById(R.id.wait_deliver_moonth);
		mYear = (TextView)findViewById(R.id.wait_deliver_year);
		
		mWeek.setOnClickListener(new MyOnClickListener(0));
        mMonth.setOnClickListener(new MyOnClickListener(1));
        mYear.setOnClickListener(new MyOnClickListener(2));
	}
	private void InitViewPager(){
		 mPager = (ViewPager) findViewById(R.id.vPager);
		 fragmentsList = new ArrayList<Fragment>();
		 LayoutInflater mInflater = getLayoutInflater();
	     View activityView = mInflater.inflate(R.layout.wait_deliver_item_main, null);
	     
	     Fragment weekfragment = AllOrderFragment.newInstance(2, 0);
	     Fragment monthFragment = AllOrderFragment.newInstance(2, 1);
	     Fragment yearFragment = AllOrderFragment.newInstance(2, 2);
	     
	     fragmentsList.add(weekfragment);
	     fragmentsList.add(monthFragment);
	     fragmentsList.add(yearFragment);
	     
	     mPager.setAdapter(new AllOrderFragmentAdapter(this.getSupportFragmentManager(), fragmentsList));
	     mPager.setCurrentItem(0);
	     mPager.setOffscreenPageLimit(2);
	     mPager.setOnPageChangeListener(new MyOnPageChangeListener());
			
	     
	}

	
	private void InitWidth() {
		ivBottomLine = (ImageView) findViewById(R.id.iv_bottom_line);// ����
		bottomLineWidth = ivBottomLine.getLayoutParams().width;
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);// ��ȡ��ǰ��Ļ������
		int screenW = dm.widthPixels;// ��Ļ�Ŀ�
		offset = (int) ((screenW / 3 - bottomLineWidth) / 2);// ��ʼλ��

		position_one = (int) (screenW / 3);
		position_two = position_one * 2;

	}

	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:// �����ǵ�һ����ѡ�У�����¼�
				if (currIndex == 1) {// ��ǰ�ǵڶ���
					animation = new TranslateAnimation(position_one, 0, 0, 0);
					mMonth.setPressed(false);
					mMonth.setBackgroundResource(R.drawable.product_details_bg);
					mMonth.setTextColor(Color.parseColor("#000000"));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(position_two, 0, 0, 0);
					mYear.setPressed(false);
					mYear.setBackgroundResource(R.drawable.product_details_bg);
					mYear.setTextColor(Color.parseColor("#000000"));
				}
				mWeek.setPressed(true);
				mWeek.setBackgroundResource(R.drawable.product_details_selected);
				mWeek.setTextColor(Color.parseColor("#702c91"));
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, position_one, 0,
							0);
					mWeek.setPressed(false);
					mWeek.setBackgroundResource(R.drawable.product_details_bg);
					mWeek.setTextColor(Color.parseColor("#000000"));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(position_two,
							position_one, 0, 0);
					mYear.setPressed(false);
					mYear.setBackgroundResource(R.drawable.product_details_bg);
					mYear.setTextColor(Color.parseColor("#000000"));
				}
				mMonth.setPressed(true);
				mMonth.setBackgroundResource(R.drawable.product_details_selected);
				mMonth.setTextColor(Color.parseColor("#702c91"));
				break;
			case 2:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, position_two, 0,
							0);
					mWeek.setPressed(false);
					mWeek.setBackgroundResource(R.drawable.product_details_bg);
					mWeek.setTextColor(Color.parseColor("#000000"));
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(position_one,
							position_two, 0, 0);
					mMonth.setPressed(false);
					mMonth.setBackgroundResource(R.drawable.product_details_bg);
					mMonth.setTextColor(Color.parseColor("#000000"));
				}
				mYear.setPressed(true);
				mYear.setBackgroundResource(R.drawable.product_details_selected);
				mYear.setTextColor(Color.parseColor("#702c91"));
				break;

			}
			currIndex = arg0;
			animation.setFillAfter(true);
			animation.setDuration(300);
			ivBottomLine.startAnimation(animation);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		StatService.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		StatService.onResume(this);
	}
}
