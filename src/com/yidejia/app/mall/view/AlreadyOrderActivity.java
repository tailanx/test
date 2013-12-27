package com.yidejia.app.mall.view;

import java.util.ArrayList;

import android.content.Intent;
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
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.adapter.AllOrderFragmentAdapter;
import com.yidejia.app.mall.adapter.AlreadyOrderFragmentAdapter;
import com.yidejia.app.mall.ctrl.OrderViewCtrl;
import com.yidejia.app.mall.fragment.AllOrderFragment;

public class AlreadyOrderActivity extends SherlockFragmentActivity {
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
	    public void doClick(View v){
	    	Intent intent = new Intent();
			switch (v.getId()){
			case R.id.already_oreder_item_main_exchange://�˻���
				intent.setClass(this, ReturnActivity.class);
				break;
			case R.id.already_oreder_item_main_search://�鿴����
				intent.setClass(this, CheckActivity.class);
				break;

			}
			startActivity(intent);
		}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBar();
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.already_oreder);
//		resources = getResources();
//		InitWidth();
//		InitTextView();
//		InitViewPager();
		setContentView(R.layout.all_order);
		OrderViewCtrl viewCtrl = new OrderViewCtrl(this);
		viewCtrl.viewCtrl(3);
		
	}
	private void setActionBar(){
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
//		getSupportActionBar().setLogo(R.drawable.back);
		getSupportActionBar().setIcon(R.drawable.back);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_compose);
//		startActionMode(new AnActionModeOfEpicProportions(ComposeActivity.this));
		ImageView button = (ImageView) findViewById(R.id.compose_back);//����
//		button.setText("����");
//		button.setTextSize(16);
//		button.setImageResource(R.drawable.filter);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Toast.makeText(ComposeActivity.this, "button", Toast.LENGTH_SHORT).show();
				AlreadyOrderActivity.this.finish();
			}
		});
		
		TextView titleTextView = (TextView) findViewById(R.id.compose_title);
		titleTextView.setText(getResources().getString(R.string.deliver_order));
	}


	private void InitTextView(){
		mWeek = (TextView)findViewById(R.id.already_oreder_week);
		mMonth = (TextView)findViewById(R.id.already_oreder_moonth);
		mYear = (TextView)findViewById(R.id.already_oreder_year);
		
		mWeek.setOnClickListener(new MyOnClickListener(0));
        mMonth.setOnClickListener(new MyOnClickListener(1));
        mYear.setOnClickListener(new MyOnClickListener(2));
	}
	private void InitViewPager(){
		 mPager = (ViewPager) findViewById(R.id.vPager);
		 fragmentsList = new ArrayList<Fragment>();
		 LayoutInflater mInflater = getLayoutInflater();
	  
	     
		 Fragment weekfragment = AllOrderFragment.newInstance(3, 0);
	     Fragment monthFragment = AllOrderFragment.newInstance(3, 1);
	     Fragment yearFragment = AllOrderFragment.newInstance(3, 2);
	     
	     fragmentsList.add(yearFragment);
	     fragmentsList.add(monthFragment);
	     fragmentsList.add(weekfragment);
	     
	     mPager.setAdapter(new AllOrderFragmentAdapter(this.getSupportFragmentManager(), fragmentsList));
	     mPager.setAdapter(new AlreadyOrderFragmentAdapter(this.getSupportFragmentManager(), fragmentsList));
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
					mMonth.setPressed(false);
					mMonth.setBackgroundResource(R.drawable.product_details_bg);
					animation = new TranslateAnimation(position_one, 0, 0, 0);
					mMonth.setTextColor(Color.parseColor("#000000"));
				} else if (currIndex == 2) {
					mYear.setPressed(false);
					mYear.setBackgroundResource(R.drawable.product_details_bg);
					animation = new TranslateAnimation(position_two, 0, 0, 0);
					mYear.setTextColor(Color.parseColor("#000000"));
				}
				mWeek.setPressed(true);
				mWeek.setBackgroundResource(R.drawable.product_details_selected);
				mWeek.setTextColor(Color.parseColor("#702c91"));
				break;
			case 1:
				if (currIndex == 0) {
					mWeek.setPressed(false);
					mWeek.setBackgroundResource(R.drawable.product_details_bg);
					animation = new TranslateAnimation(offset, position_one, 0,
							0);
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
					mMonth.setPressed(false);
					mMonth.setBackgroundResource(R.drawable.product_details_bg);
					animation = new TranslateAnimation(position_one,
							position_two, 0, 0);
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
