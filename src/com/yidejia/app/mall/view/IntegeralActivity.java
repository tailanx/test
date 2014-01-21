package com.yidejia.app.mall.view;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.VoucherDataManage;

public class IntegeralActivity extends BaseActivity {
	private static final String TAG = "MainActivity";
	// private ViewPager mPager;
	// private ArrayList<Fragment> fragmentsList;
	//
	// private TextView mCoupons,mIntegeral;
	// private ImageView ivBottomLine;
	// private int currIndex = 0;
	// private int bottomLineWidth;
	// private int offset = 0;
	// private int position_one;
	// private int position_two;
	// private int position_three;
	// private Resources resources;
	private VoucherDataManage voucherDataManage;// 积分
	private MyApplication myApplication;
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionbarConfig();
		setTitle(getResources().getString(R.string.main_voucher_text));
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// setContentView(R.layout.my_card_voucher);
		// resources = getResources();
		// InitWidth();
		// InitTextView();
		// InitViewPager();

		// 已取消优惠券只显示积分
		setContentView(R.layout.coupons);

		voucherDataManage = new VoucherDataManage(IntegeralActivity.this);
		myApplication = (MyApplication) getApplication();
		voucherDataManage.getUserVoucher(myApplication.getUserId(),
				myApplication.getToken());
		webView = (WebView) findViewById(R.id.wb_webView);
		webView.setBackgroundColor(0);
		webView.setBackgroundColor(getResources().getColor(R.color.white));
		webView.loadUrl("http://m.yidejia.com/regterms.html");
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
		// TODO Auto-generated method stub
		super.onDestroy();
		if (voucherDataManage != null) {
			voucherDataManage.cancelTask();
		}
	}

	// /**
	// * 设置头部
	// */
	// private void setActionBar(){
	// getSupportActionBar().setDisplayHomeAsUpEnabled(false);
	// getSupportActionBar().setDisplayShowCustomEnabled(true);
	// getSupportActionBar().setDisplayShowHomeEnabled(false);
	// getSupportActionBar().setDisplayShowTitleEnabled(false);
	// getSupportActionBar().setDisplayUseLogoEnabled(false);
	// getSupportActionBar().setCustomView(R.layout.actionbar_common);
	// TextView back = (TextView) findViewById(R.id.ab_common_back);
	// TextView titleTextView = (TextView) findViewById(R.id.ab_common_title);
	// titleTextView.setText("积分卡券");
	//
	// back.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View arg0) {
	// // TODO Auto-generated method stub
	// IntegeralActivity.this.finish();
	// }
	// });
	// }

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
