package com.yidejia.app.mall.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
//import android.webkit.WebView;
//import android.widget.ImageView;
import android.widget.TextView;


//import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.yidejia.app.mall.BaseActivity;
//import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.fragment.IntegeralFragment;

public class IntegeralActivity extends BaseActivity implements OnClickListener {
	// private static final String TAG = "MainActivity";
	private FragmentManager manager;
	private FragmentTransaction ft;
	private int currentId;// 设置当前id；
	private TextView integeralTextView;// 积分
	private TextView youhuiquanTexView;// 优惠券
//	private VoucherDataManage voucherDataManage;// 积分接口
	// private MyApplication myApplication;
	// private WebView webView;
	private Fragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setActionbarConfig();
		// setTitle(getResources().getString(R.string.main_voucher_text));
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		manager = getSupportFragmentManager();
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// setContentView(R.layout.my_card_voucher);
		// resources = getResources();
		// InitWidth();
		// InitTextView();
		// InitViewPager();

		// 已取消优惠券只显示积分
		setContentView(R.layout.integeraltop);
		setActionBar();
		fragment = IntegeralFragment.newInstance(0);
		ft = manager.beginTransaction();
		ft.add(R.id.fl_search_result_fragment, fragment);
//		ft.addToBackStack("jifen");
		ft.commit();

		// myApplication = (MyApplication) getApplication();

		// String ji =
		// voucherDataManage.getUserVoucher(myApplication.getUserId(),
		// myApplication.getToken());
		// TextView jiFen = (TextView) findViewById(R.id.jiefen);
		// if(ji==null||"".equals(ji)){
		// jiFen.setText("0");
		// }else{
		// jiFen.setText(ji);
		// }
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
	}

	// /**
	// * 设置头部
	// */
	private void setActionBar() {
		// getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		// getSupportActionBar().setDisplayShowCustomEnabled(true);
		// getSupportActionBar().setDisplayShowHomeEnabled(false);
		// getSupportActionBar().setDisplayShowTitleEnabled(false);
		// getSupportActionBar().setDisplayUseLogoEnabled(false);
		// getSupportActionBar().setCustomView(R.layout.integeraltop);
		TextView back = (TextView) findViewById(R.id.integeral_back);
		integeralTextView = (TextView) findViewById(R.id.tv_search_result_selled);
		// titleTextView.setText("积分卡券");
		integeralTextView.setSelected(true);
		currentId = 0;
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				IntegeralActivity.this.finish();
			}
		});
		youhuiquanTexView = (TextView) findViewById(R.id.tv_search_result_popularity);// 积分劵
		youhuiquanTexView.setOnClickListener(this);
		integeralTextView.setOnClickListener(this);// 积分
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_search_result_popularity:// 优惠券
			fragment = IntegeralFragment.newInstance(1);
			if (currentId == 1) {
				return;
			} else {
				integeralTextView.setSelected(false);
				youhuiquanTexView.setSelected(true);
				ft = manager.beginTransaction();
				ft.add(R.id.fl_search_result_fragment, fragment);
//				ft.addToBackStack("youhuiquan");
				ft.commit();
				currentId = 1;
				break;
			}
		case R.id.tv_search_result_selled:// 积分
			fragment = IntegeralFragment.newInstance(0);//
			if (currentId == 0) {
				return;
			} else {
				integeralTextView.setSelected(true);
				youhuiquanTexView.setSelected(false);
				ft = manager.beginTransaction();
				ft.add(R.id.fl_search_result_fragment, fragment);
//				ft.addToBackStack("jiefen");
				ft.commit();
				currentId = 0;
			}
			break;
		}
	}
	/*
	 * private void InitTextView(){ mCoupons =
	 * (TextView)findViewById(R.id.my_card_voucher_Coupons); mIntegeral =
	 * (TextView)findViewById(R.id.my_card_voucher_mIntegeral);
	 * 
	 * mCoupons.setOnClickListener(new MyOnClickListener(0));
	 * mIntegeral.setOnClickListener(new MyOnClickListener(1)); } private void
	 * InitViewPager(){ mPager = (ViewPager) findViewById(R.id.vPager);
	 * fragmentsList = new ArrayList<Fragment>(); LayoutInflater mInflater =
	 * getLayoutInflater();
	 * 
	 * 
	 * Fragment couponsFragment = IntegeralFragment.newInstance("jifenquan");
	 * Fragment integeralFragment = IntegeralFragment.newInstance("jifen");
	 * 
	 * 
	 * fragmentsList.add(couponsFragment); fragmentsList.add(integeralFragment);
	 * 
	 * mPager.setAdapter(new
	 * IntegeralFragmentAdapter(this.getSupportFragmentManager(),
	 * fragmentsList)); mPager.setCurrentItem(0);
	 * mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	 * 
	 * 
	 * }
	 * 
	 * 
	 * private void InitWidth() { ivBottomLine = (ImageView)
	 * findViewById(R.id.iv_bottom_line);//滑动 bottomLineWidth =
	 * ivBottomLine.getLayoutParams().width; DisplayMetrics dm = new
	 * DisplayMetrics();
	 * getWindowManager().getDefaultDisplay().getMetrics(dm);//获取当前屏幕的属性 int
	 * screenW = dm.widthPixels;//屏幕的宽 offset = (int) ((screenW / 3 -
	 * bottomLineWidth)/2);//起始位置
	 * 
	 * position_one = (int) (screenW / 3); position_two = position_one * 2;
	 * 
	 * } public class MyOnClickListener implements View.OnClickListener {
	 * private int index = 0;
	 * 
	 * public MyOnClickListener(int i) { index = i; }
	 * 
	 * @Override public void onClick(View v) { mPager.setCurrentItem(index); }
	 * }; public class MyOnPageChangeListener implements OnPageChangeListener {
	 * 
	 * @Override public void onPageSelected(int arg0) { Animation animation =
	 * null; switch (arg0) { case 0://假如是第一个被选中，添加事件 if (currIndex == 1)
	 * {//当前是第二个 mIntegeral.setPressed(false);
	 * mIntegeral.setBackgroundResource(R.drawable.product_details_bg);
	 * animation = new TranslateAnimation(position_one, 0, 0, 0);
	 * mIntegeral.setTextColor(Color.parseColor("#ed217c")); }
	 * mCoupons.setPressed(true);
	 * mCoupons.setBackgroundResource(R.drawable.product_details_selected);
	 * mCoupons.setTextColor(Color.parseColor("#702c91")); break; case 1: if
	 * (currIndex == 0) { mCoupons.setPressed(false);
	 * mCoupons.setBackgroundResource(R.drawable.product_details_bg); animation
	 * = new TranslateAnimation(offset, position_one, 0, 0);
	 * mCoupons.setTextColor(Color.parseColor("#ed217c")); }
	 * mIntegeral.setPressed(true);
	 * mIntegeral.setBackgroundResource(R.drawable.product_details_selected);
	 * mIntegeral.setTextColor(Color.parseColor("#702c91")); break;
	 * 
	 * } currIndex = arg0; animation.setFillAfter(true);
	 * animation.setDuration(300); ivBottomLine.startAnimation(animation); }
	 * 
	 * @Override public void onPageScrollStateChanged(int arg0) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public void onPageScrolled(int arg0, float arg1, int arg2) { //
	 * TODO Auto-generated method stub
	 * 
	 * } }
	 */
}
