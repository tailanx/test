package com.yidejia.app.mall;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.fragment.CartActivity;
import com.yidejia.app.mall.fragment.GuangFragment;
import com.yidejia.app.mall.fragment.MainPageFragment;
import com.yidejia.app.mall.fragment.MyMallFragment;
import com.yidejia.app.mall.fragment.SearchFragment;
import com.yidejia.app.mall.fragment.ShoppingCartFragment;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.ImageUrl;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.view.LoginActivity;
import com.yidejia.app.mall.widget.YLImageButton;


/**
 * ������󵼺�
 * @author ����
 *
 */
public class MainFragmentActivity extends SherlockFragmentActivity {

//	private ViewPager main_act_pager;
	private Fragment newFragment;
	private ArrayList<Fragment> fragmentsList;
	private int currentIndex = 0;//��ǰ��������ҳ��
	private RelativeLayout downHomeLayout;
	private RelativeLayout downGuangLayout;
	private RelativeLayout downSearchLayout;
	private RelativeLayout downShoppingLayout;
	private RelativeLayout downMyLayout;
	private ImageView down_home_imageView;//��ҳ��ťͼƬ
	private ImageView down_guang_imageView;//�䰴ťͼƬ
	private ImageView down_search_imageView;//������ťͼƬ
	private ImageView down_shopping_imageView; //���ﳵ��ťͼƬ
	private ImageView down_my_imageView; //�ҵ��̳ǰ�ťͼƬ
	
	private CartsDataManage cartsDataManage;
	private TextView down_home_textview;
	private TextView down_guang_textview;
	private TextView down_search_textview;
	private TextView down_shopping_textview;
	private TextView down_my_textview;
	private int number;
	private InnerReceiver receiver;
	
//	private Button down_shopping_cart;//���ﳵ������ť
	
//	private TextView down_home_TextView;
//	private TextView down_guang_TextView;
//	private TextView down_search_TextView;
//	private TextView down_shopping_TextView;
//	private TextView down_my_TextView;
	
	private Button cartImage;
	
	private Bitmap bmp;
	private Resources res;
	private Map<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();
	
	public static Activity MAINACTIVITY;
	
	@Override
	protected void onCreate(Bundle savedBundleState) {
		// TODO Auto-generated method stub
		super.onCreate(savedBundleState);
		setContentView(R.layout.activity_main_fragment_layout);
		setActionBarConfig();
		if(savedBundleState == null){
			initView();
		}
		MAINACTIVITY = this;
		initNavView();
		if (ConnectionDetector.isConnectingToInternet(this)) {
			ImageUrl imageUrl = new ImageUrl();
			imageUrl.getImageUrl();
		}
		receiver = new InnerReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Consts.UPDATE_CHANGE);
		registerReceiver(receiver, filter);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(receiver);
		super.onDestroy();
	}
	
/**
 * ��ʼ���ײ�������
 */
	private void initNavView() {
		//�ı�ײ���ҳ�������а���ȥ��Ч���ı���
		downHomeLayout = (RelativeLayout) findViewById(R.id.down_home_layout);
//		downHomeLayout.setBackgroundResource(R.drawable.down_hover);
		down_home_imageView = (ImageView) findViewById(R.id.down_home_icon);
		res = getResources();
//		bmp = BitmapFactory.decodeResource(res, R.drawable.home_hover);
//		down_home_imageView.setImageBitmap(bmp);
//		down_home_imageView.setImageResource(R.drawable.home_hover);//��������
		
		// cartsDataManage = new CartsDataManage();
		cartsDataManage = new CartsDataManage();
		number = cartsDataManage.getCartAmount();
		cartImage = (Button) findViewById(R.id.down_shopping_cart);

		cartImage.setText(number + "");
		
		downGuangLayout = (RelativeLayout) findViewById(R.id.down_guang_layout);
		downSearchLayout = (RelativeLayout) findViewById(R.id.down_search_layout);
		downShoppingLayout = (RelativeLayout) findViewById(R.id.down_shopping_layout);
		downMyLayout = (RelativeLayout) findViewById(R.id.down_my_layout);
		
		down_guang_imageView = (ImageView) findViewById(R.id.down_guang_icon);
		down_search_imageView = (ImageView) findViewById(R.id.down_search_icon);
		down_shopping_imageView = (ImageView) findViewById(R.id.down_shopping_icon);
		down_my_imageView = (ImageView) findViewById(R.id.down_my_icon);
		
		down_home_textview = (TextView) findViewById(R.id.down_home_text);
		down_guang_textview = (TextView) findViewById(R.id.down_guang_text);
		down_search_textview = (TextView) findViewById(R.id.down_search_text);
		down_shopping_textview = (TextView) findViewById(R.id.down_shopping_text);
		down_my_textview = (TextView) findViewById(R.id.down_my_text);
		
//		down_guang_TextView = (TextView) findViewById(R.id.down_guang_text);
//		down_search_TextView = (TextView) findViewById(R.id.down_search_text);
//		down_shopping_TextView = (TextView) findViewById(R.id.down_shopping_text);
//		down_my_TextView = (TextView) findViewById(R.id.down_my_text);
		
		downHomeLayout.setOnClickListener(new NavOnclick(0));
		downGuangLayout.setOnClickListener(new NavOnclick(1));
		downSearchLayout.setOnClickListener(new NavOnclick(2));
		downShoppingLayout.setOnClickListener(new NavOnclick(3));
		downMyLayout.setOnClickListener(new NavOnclick(4));
		
//		down_shopping_cart = (Button) findViewById(R.id.down_shopping_cart);
//		CartsDataManage cartsDataManage = new CartsDataManage();
//		int cartAcount = cartsDataManage.getCartAmount();
//		if(cartAcount == 0){
//			down_shopping_cart.setVisibility(View.GONE);
//		} else{
//			down_shopping_cart.setVisibility(View.VISIBLE);
//			down_shopping_cart.setText("" + cartAcount);
//		}
	}
	
	private void initView(){
		Fragment mainPageFragment = MainPageFragment.newInstance(0);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.main_fragment, mainPageFragment).commit();//.addToBackStack(fragmentTag[0])
	}
	/**
	 * ���ﲻ��fragment+ viewpager ʵ�֣�����add fragment����
	 */
	private void addView(){
//		main_act_pager = (ViewPager) findViewById(R.id.main_act_pager);
		fragmentsList = new ArrayList<Fragment>();
		
		Fragment mainPageFragment = MainPageFragment.newInstance(0);
		Fragment guangFragment = MainPageFragment.newInstance(1);
		Fragment searchFragment = SearchFragment.newInstance(2);
		Fragment shoppingCartFragment = ShoppingCartFragment.newInstance(3);
		Fragment personalFragment = ShoppingCartFragment.newInstance(4);
		
		fragmentsList.add(mainPageFragment);
		fragmentsList.add(guangFragment);
		fragmentsList.add(searchFragment);
		fragmentsList.add(shoppingCartFragment);
		fragmentsList.add(personalFragment);
		
//		main_act_pager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), fragmentsList));
//		main_act_pager.setCurrentItem(currentIndex);
	}
	
	private class NavOnclick implements View.OnClickListener {

		private int id;

		public NavOnclick(int id) {
			this.id = id;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
//			switch (id) {
//			case R.id.down_search_layout:
//				main_act_pager.setCurrentItem(2);
//				break;
//				
//			default:
//				break;
//			}
//			Fragment newFragment = null;
			if(currentIndex != id){
//				main_act_pager.setCurrentItem(id);
				switch (id) {
				case 0:
					newFragment = MainPageFragment.newInstance(0);
					setNavBackground();
//					downHomeLayout.setPressed(true);
					down_home_textview.setTextColor(res.getColor(R.color.white));
					downHomeLayout.setBackgroundResource(R.drawable.down_hover1);
//					down_home_imageView.setPressed(true);
					down_home_imageView.setImageResource(R.drawable.home_hover);
//					down_home_TextView.setTextColor(Color.WHITE);//getResources().getColor(R.color.white)
					break;
				case 1:
//					newFragment = MainPageFragment.newInstance(1);
					newFragment = new GuangFragment();
					setNavBackground();
//					downGuangLayout.setPressed(true);
					down_guang_textview.setTextColor(res.getColor(R.color.white));
					downGuangLayout.setBackgroundResource(R.drawable.down_hover1);
//					down_guang_imageView.setPressed(true);
					down_guang_imageView.setImageResource(R.drawable.down_guang_hover);
//					down_guang_TextView.setTextColor(Color.WHITE);
					break;
				case 2:
					newFragment = SearchFragment.newInstance(2);
					setNavBackground();
//					downSearchLayout.setPressed(true);
					down_search_textview.setTextColor(res.getColor(R.color.white));
					downSearchLayout.setBackgroundResource(R.drawable.down_hover1);
//					down_search_imageView.setPressed(true);
					down_search_imageView.setImageResource(R.drawable.down_search_hover);
//					down_search_TextView.setTextColor(Color.WHITE);
					break;
				case 3:
//					newFragment = ShoppingCartFragment.newInstance(3);
					newFragment = new CartActivity();
					setNavBackground();
//					downShoppingLayout.setPressed(true);
					down_shopping_textview.setTextColor(res.getColor(R.color.white));
					downShoppingLayout.setBackgroundResource(R.drawable.down_hover1);
//					down_shopping_imageView.setPressed(true);
					down_shopping_imageView.setImageResource(R.drawable.down_shopping_hover);
//					down_shopping_TextView.setTextColor(Color.WHITE);
					break;
				case 4:
					boolean isLogin = ((MyApplication) getApplication())
							.getIsLogin();

					Log.i("info", isLogin + "");
					down_my_textview.setTextColor(res.getColor(R.color.white));
					if (isLogin) {
						newFragment = new MyMallActivity();
						setNavBackground();
						// downMyLayout.setPressed(true);
						downMyLayout
								.setBackgroundResource(R.drawable.down_hover1);
						// down_my_imageView.setPressed(true);
						down_my_imageView
								.setImageResource(R.drawable.down_my_hover);
						break;
					} else {
						newFragment = new LoginActivity();
						setNavBackground();
						// downMyLayout.setPressed(true);
						downMyLayout
								.setBackgroundResource(R.drawable.down_hover1);
						// down_my_imageView.setPressed(true);
						down_my_imageView
								.setImageResource(R.drawable.down_my_hover);
						// down_my_TextView.setTextColor(Color.WHITE);
						break;
					}
				default:
					break;
				}
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		        ft.replace(R.id.main_fragment, newFragment).commit();//.addToBackStack(fragmentTag[id])
			}
			currentIndex = id;
		}

	}
	
	private String[] fragmentTag = {"main", "guang", "search", "cart", "my"};
	
	private void setNavBackground(){
		if (currentIndex == 0) {
			down_home_textview.setTextColor(this.getResources().getColor(
					R.color.white_white));
//			downHomeLayout.setPressed(false);
			downHomeLayout.setBackgroundResource(R.drawable.downbg);
//			down_home_imageView.setPressed(false);
//			down_home_TextView.setTextColor(Color.rgb(180, 180, 180));
			down_home_imageView.setImageResource(R.drawable.home_normal);

		} else if (currentIndex == 1) {
			down_guang_textview.setTextColor(this.getResources().getColor(
					R.color.white_white));
//			downGuangLayout.setPressed(false);
			downGuangLayout.setBackgroundResource(R.drawable.downbg);
//			down_guang_imageView.setPressed(false);
			down_guang_imageView
					.setImageResource(R.drawable.down_guang_normal);
//			down_guang_TextView.setTextColor(Color.rgb(180, 180, 180));

		} else if (currentIndex == 2) {
			down_search_textview.setTextColor(this.getResources().getColor(
					R.color.white_white));
//			downSearchLayout.setPressed(false);
			downSearchLayout.setBackgroundResource(R.drawable.downbg);
//			down_search_imageView.setPressed(false);
//			down_search_TextView.setTextColor(Color.rgb(180, 180, 180));
			down_search_imageView
					.setImageResource(R.drawable.down_search_normal);

		} else if (currentIndex == 3) {
			down_shopping_textview.setTextColor(this.getResources().getColor(
					R.color.white_white));
//			downShoppingLayout.setPressed(false);
			downShoppingLayout.setBackgroundResource(R.drawable.downbg);
//			down_shopping_imageView.setPressed(false);
//			down_shopping_TextView.setTextColor(Color.rgb(180, 180, 180));
			down_shopping_imageView
					.setImageResource(R.drawable.down_shopping_normal);

		} else if (currentIndex == 4) {
			down_my_textview.setTextColor(this.getResources().getColor(
					R.color.white_white));
//			downMyLayout.setPressed(false);
			downMyLayout.setBackgroundResource(R.drawable.downbg);
//			down_my_imageView.setPressed(false);
//			down_my_TextView.setTextColor(Color.rgb(180, 180, 180));
			down_my_imageView.setImageResource(R.drawable.down_my_normal);

		}
	}

	private void setActionBarConfig() {
		getSupportActionBar().setCustomView(R.layout.actionbar_main_home_title);
		getSupportActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.drawable.topbg));
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		invalidateOptionsMenu();
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		// getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_HOME,
		// getSupportActionBar().DISPLAY_SHOW_HOME |
		// getSupportActionBar().DISPLAY_USE_LOGO);
		getSupportActionBar().setIcon(R.drawable.left_menu);
		getSupportActionBar().setNavigationMode(
				ActionBar.NAVIGATION_MODE_STANDARD);
		// getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		getSupportActionBar().setHomeButtonEnabled(true);
		final EditText searchEditText = (EditText) findViewById(R.id.main_home_title_search);
		searchEditText.setSelected(false);
	}
	
	private class InnerReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (Consts.UPDATE_CHANGE.equals(action)) {
				cartsDataManage = new CartsDataManage();
				number = cartsDataManage.getCartAmount();
				cartImage.setText(number + "");
			}
		}

	}
}
