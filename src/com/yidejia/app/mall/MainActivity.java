package com.yidejia.app.mall;


import java.lang.reflect.Field;

//import org.taptwo.android.widget.CircleFlowIndicator;
//import org.taptwo.android.widget.ViewFlow;







import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.yidejia.app.mall.R;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
/**
 * �Ѹ�Ϊ fragmentʵ�� ������Ӧ��Ҳ�ò�����
 * @author ���
 *
 */
public class MainActivity extends SherlockFragmentActivity {//implements ActionBar.TabListener , implements OnGestureListener

	private PullToRefreshScrollView mPullToRefreshScrollView;
	protected ListFragment mFrag;
//	private ViewFlow viewFlow;
//	private static final int[] ids = { R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};
	private ViewFlipper viewFlipper;
	private GestureDetector detector; //���Ƽ��
	
	private static final String TAG = "MainActivity";
	private ImageSwitcher imageSwitcher;

	Animation leftInAnimation;
	Animation leftOutAnimation;
	Animation rightInAnimation;
	Animation rightOutAnimation;
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
//		menu.add(Menu.NONE, 0, Menu.NONE, "��ҳ")
//		.setIcon(R.drawable.home_normal)
//		
//		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//		menu.add(Menu.NONE, 1, Menu.NONE, "���֡���")
//		.setIcon(R.drawable.down_guang_normal)
//		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//		menu.add(Menu.NONE, 2, Menu.NONE, "����")
//		.setIcon(R.drawable.down_search_normal)
//		.setOnMenuItemClickListener(new OnMenuItemClickListener() {
//			
//			@Override
//			public boolean onMenuItemClick(MenuItem item) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(MainActivity.this, ComposeActivity.class);
//				startActivity(intent);
//				MainActivity.this.finish();
//				return true;
//			}
//		})
//		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//		menu.add(Menu.NONE, 3, Menu.NONE, "���ﳵ")
//		.setIcon(R.drawable.down_shopping_normal)
//		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//		menu.add(Menu.NONE, 4, Menu.NONE, "�ҵ��̳�")
//		.setIcon(R.drawable.down_my_normal)
//		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }
	
	private void initNavView(){
		RelativeLayout downHomeLayout = (RelativeLayout) findViewById(R.id.down_home_layout);
		downHomeLayout.setBackgroundResource(R.drawable.down_hover);
		ImageView down_home_imageView = (ImageView) findViewById(R.id.down_home_icon);
		Resources res = getResources();
		Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.home_hover);
		down_home_imageView.setImageBitmap(bmp);
		
		RelativeLayout downGuangLayout = (RelativeLayout) findViewById(R.id.down_guang_layout);
		RelativeLayout downSearchLayotu = (RelativeLayout) findViewById(R.id.down_search_layout);
		RelativeLayout downShoppingLayout = (RelativeLayout) findViewById(R.id.down_shopping_layout);
		RelativeLayout downMyLayout = (RelativeLayout) findViewById(R.id.down_my_layout);
		downSearchLayotu.setOnClickListener(new NavOnclick(R.id.down_search_layout));
		
		
	}
	
	private class NavOnclick implements View.OnClickListener{
		
		private int id;
		
		public NavOnclick(int id){
			this.id = id;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (id) {
			case R.id.down_search_layout:
				Intent intent = new Intent(MainActivity.this, SearchActivity.class);
				startActivity(intent);
				MainActivity.this.finish();
				break;

			default:
				break;
			}
		}
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light); 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		setContentView(R.layout.activity_main_fragment_layout);
		
		setActionBarConfig();
		
//		ActionBar.Tab newTab = getSupportActionBar().newTab();
//		for (int i = 0; i < 4; i++) {
//			newTab.setIcon(R.drawable.ic_title_share_default);
//			newTab.setText("Text!");
//			
//			newTab.setTabListener(this);
//			getSupportActionBar().addTab(newTab);
//		}

//		Button leftButton = (Button) findViewById(R.id.left_menu);
//		leftButton.setVisibility(View.GONE);
//		leftButton.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
////				Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
//				showMenu();
//			}
//		});
		
//		Button rightButton = (Button) findViewById(R.id.right_menu);
////		rightButton.setVisibility(View.GONE);
//		rightButton.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
////				Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
//				showSecondaryMenu();
//			}
//		});
		
		
		// �������һ����˵�activity
//		setLeftAndRightMenu(savedInstanceState);
		// ����sliding menu
//		setSlidingMenuConfig();
		width = getWindowManager().getDefaultDisplay().getWidth();
		getMainListFirstItem();
//		mPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
		FrameLayout layout = (FrameLayout) findViewById(R.id.layout);
		layout.addView(mMainView);
//		mPullToRefreshScrollView.setScrollingWhileRefreshingEnabled(true);
//		mPullToRefreshScrollView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
		((ScrollView)findViewById(R.id.pull_refresh_scrollview)).setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);;
		/*
		viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);
        detector = new GestureDetector(this);
        
      //����Ч��
    	leftInAnimation = AnimationUtils.loadAnimation(this, R.anim.left_in);
		leftOutAnimation = AnimationUtils.loadAnimation(this, R.anim.left_out);
		rightInAnimation = AnimationUtils.loadAnimation(this, R.anim.right_in);
		rightOutAnimation = AnimationUtils.loadAnimation(this, R.anim.right_out);
		//��viewFlipper���View
		for (int i = 0; i < ids.length; i++) {
			viewFlipper.addView(getImageView(ids[i]));
		}
         */
//		mPullToRefreshScrollView.addView(mMainView);
		/*
		viewFlow = (ViewFlow) findViewById(R.id.viewflow);
		viewFlow.setAdapter(new ImageAdapter(this), 0);
		viewFlow.setmSideBuffer(7);
		CircleFlowIndicator indic = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
		viewFlow.setFlowIndicator(indic);
		viewFlow.setTimeSpan(3 * 1000);  
        viewFlow.setSelection(10 * 7); // ���ó�ʼλ��  
//        viewFlow.startAutoFlowTimer(); // �����Զ�����  
//        viewFlow.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				Toast.makeText(MainActivity.this, "position:"+arg2, Toast.LENGTH_SHORT).show();
//			}
//		});
//        viewFlow.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//				Toast.makeText(MainActivity.this, "position:", Toast.LENGTH_SHORT).show();
//				return false;
//			}
//		});
        
         */
		
		initNavView();
	}
	 private ImageView getImageView(int id){
	    	ImageView imageView = new ImageView(this);
	    	imageView.setImageResource(id);
	    	Log.e(TAG, "id"+id);
	    	return imageView;
	    }
	 /*
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	*/
	
	private void setActionBarConfig(){
		getSupportActionBar().setCustomView(R.layout.actionbar_main_home_title);
		getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.topbg));
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		invalidateOptionsMenu();
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
//		getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_HOME, getSupportActionBar().DISPLAY_SHOW_HOME | getSupportActionBar().DISPLAY_USE_LOGO);
		getSupportActionBar().setIcon(R.drawable.left_menu);
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
//		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		getSupportActionBar().setHomeButtonEnabled(true);
		final EditText searchEditText = (EditText) findViewById(R.id.main_home_title_search);
		searchEditText.setSelected(false);
//		searchEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
//			
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				// TODO Auto-generated method stub
//				if(!hasFocus){
//					searchEditText.clearFocus();
//				} else {
//					Intent intent = new Intent(MainActivity.this, SearchActivity.class);
//					startActivity(intent);
//				}
//			}
//		});
//		searchEditText.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(MainActivity.this, SearchActivity.class);
//				startActivity(intent);
//				return true;
//			}
//		});
	}
	/*
	private void setLeftAndRightMenu(Bundle savedInstanceState) {
		// set the Behind View Fragment
		setBehindContentView(R.layout.activity_frame_left_menu);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager()
					.beginTransaction();
			mFrag = new LeftMenuListFragment();
			t.replace(R.id.menu_frame, mFrag);
			t.commit();
		} else {
			mFrag = (ListFragment) this.getSupportFragmentManager()
					.findFragmentById(R.id.menu_frame);
		}
		// set secondary menu Fragment
		
		getSlidingMenu().setSecondaryMenu(R.layout.activity_about_us);
		getSlidingMenu().setSecondaryShadowDrawable(R.drawable.shadowright);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.info_frame, new RightInfoFragment())
		.commit();
	}
    
    private void setSlidingMenuConfig(){
		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setAboveOffset(R.dimen.above_offset);
		sm.setBehindScrollScale(0);
//		sm.setSelectorEnabled(true);
//		sm.setSelectedView(getSlidingMenu().getMenu());
		sm.setFadeDegree(0f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
//		sm.setTouchmodeMarginThreshold(80);
		sm.setMode(SlidingMenu.LEFT_RIGHT);
		setSlidingActionBarEnabled(true);
	}
    */
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
//			showMenu();
			return true;
		case R.id.right_menu:
//			showSecondaryMenu();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
    
    /* If your min SDK version is < 8 you need to trigger the onConfigurationChanged in ViewFlow manually, like this */	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
//		viewFlow.onConfigurationChanged(newConfig);
	}
	
	//View pager item
		private ViewGroup mMainView = null;
		private ViewPager mViewPager;
		private ViewGroup mImageCircleView = null;
		private SlideImageLayout mSlideLayout;
		private ImageView[] mImageCircleViews;
//	    private TextView mSlideTitle;
	/**
	 * 
	 * @return ��ҳ�ֲ��Ŀؼ�
	 */
	private ViewGroup getMainListFirstItem() {
//		((PullToRefreshListView) mPullRefreshListView)
//				.setFilterTouchEvents(true);
//		mPullRefreshListView.setFirstLoading(true);
		LayoutInflater inflater = getLayoutInflater();
		mMainView = (ViewGroup) inflater.inflate(
				R.layout.layout_first_item_in_main_listview, null);
		mViewPager = (ViewPager) mMainView.findViewById(R.id.image_slide_page);
		mSlideLayout = new SlideImageLayout(MainActivity.this,
				mMainView.getContext(),width);
		mSlideLayout.setCircleImageLayout(3);
		mImageCircleViews = new ImageView[3];
		mImageCircleView = (ViewGroup) mMainView
				.findViewById(R.id.layout_circle_images);
//		mSlideTitle = (TextView) mMainView.findViewById(R.id.tvSlideTitle);
		
		
//		for (int i = 0; i < ids.length; i++) {
			
			//mainImageData.getBitmaps().get(i)
//			mImagePageViewList.add(mSlideLayout
//					.getSlideImageLayout((Bitmap)topAdImage.get(i)));//mainImageData.getBitmaps().get(i)
//			mImageCircleViews[i] = mSlideLayout.getCircleImageLayout(i);
//			mImageCircleView.addView(mSlideLayout.getLinearLayout(
//					mImageCircleViews[i], 9, 9));
//		}

		// ����ViewPager
		mViewPager.setAdapter(new SlideImageAdapter());
		mViewPager.setOnPageChangeListener(new ImagePageChangeListener());
		mViewPager.setCurrentItem(0);
		mViewPager.setOffscreenPageLimit(2);
//		mViewPager.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//				Toast.makeText(MainActivity.this, "45454", Toast.LENGTH_SHORT).show();
//				return false;
//			}
//		});
//		try {
//		    Field mScroller;
//		    mScroller = ViewPager.class.getDeclaredField("mScroller");
//		    mScroller.setAccessible(true); 
//		    FixedSpeedScroller scroller = new FixedSpeedScroller(mViewPager.getContext(), new AccelerateInterpolator());
//		    scroller.setFixedDuration(300);
//		    mScroller.set(mViewPager, scroller);
//		} catch (NoSuchFieldException e) {
//		} catch (IllegalArgumentException e) {
//		} catch (IllegalAccessException e) {
//		}
		return mMainView;
	}
	
	/** 
	 * ����ͼƬ���������
	 */
    private class SlideImageAdapter extends PagerAdapter {
    	private LayoutInflater inflater;
    	
    	
    	private int height = (int)((float)width / 320) * 160;

    	public SlideImageAdapter(){
    		inflater = getLayoutInflater();
    	}
        public int getCount() {
//            return ids.length;//1000;
        	return 3;
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
//            ((ViewPager) view).removeView(mImagePageViewList.get(arg1));
        	((ViewPager) container).removeView((View) object);
        }

        public Object instantiateItem(ViewGroup view, int position) {
//            ((ViewPager) view).addView(mImagePageViewList.get(position));
            View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
			final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
//			imageView.setBackgroundResource(ids[position%ids.length]);
	        imageView.setOnClickListener(new ImageOnClickListener());
	        ((ViewPager) view).addView(imageLayout, 0);
            return imageLayout;
        }

        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        	
        }

        public Parcelable saveState() {
            return null;
        }

        public void startUpdate(View arg0) {
        }

        public void finishUpdate(View arg0) {
        }
//        public void setPageIndex(int index){
//    		pageIndex = index;
//    	}
        
        public class ImageOnClickListener implements OnClickListener{
        	@Override
        	public void onClick(View v) {
//        		Toast.makeText(MainActivity.this, "�ҵ���˵�"+"["+pageIndex%ids.length+"]����", Toast.LENGTH_SHORT).show();
        	}
        }
    }
    private int pageIndex = 0;

    /** 
     * ����ҳ�����¼�������
     */
    private class ImagePageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        	
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        	if(positionOffset >= 50){
//        		mViewPager.setCurrentItem(pageIndex);
//        	}
        }
        
        @Override
        public void onPageSelected(int index) {
//            mSlideLayout.setPageIndex(index);
//            SlideImageAdapter slideImageAdapter = new SlideImageAdapter();
//            slideImageAdapter.setPageIndex(index);
            pageIndex = index;
            int length = mImageCircleViews.length;
            mImageCircleViews[index%length].setBackgroundResource(R.drawable.dot1);
            for (int i = 0; i < length; i++) {
//            	mSlideTitle.setText(""+i);
                
                if (index%length != i) {
                    mImageCircleViews[i%length].setBackgroundResource(R.drawable.dot2);
                }
            }
        }

    }
    
    private int width;
   
    /* 
    @Override
    public boolean onTouchEvent(MotionEvent event) {
     
    	return this.detector.onTouchEvent(event); //touch�¼��������ƴ��?
    }

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		if(!viewFlipper.isFocused())
			return false;
		
		Log.i(TAG, "e1="+e1.getX()+" e2="+e2.getX()+" e1-e2="+(e1.getX()-e2.getX()));
		if(e1.getX()-e2.getX()>50){
			viewFlipper.setInAnimation(leftInAnimation);
			viewFlipper.setOutAnimation(leftOutAnimation);
		    viewFlipper.showNext();//���һ���
		    return true;
		}else if(e1.getX()-e2.getX()<-50){
			viewFlipper.setInAnimation(rightInAnimation);
			viewFlipper.setOutAnimation(rightOutAnimation);
			viewFlipper.showPrevious();//���󻬶�
			return true;
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		Toast.makeText(MainActivity.this, "ni", Toast.LENGTH_SHORT).show();
		return false;
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
//		if(viewFlipper.hasFocus())
			this.detector.onTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}
	/* */
}
