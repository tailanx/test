package com.yidejia.app.mall.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.actionbarsherlock.app.SherlockFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.yidejia.app.mall.GoodsInfoActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.SearchActivity;
import com.yidejia.app.mall.SlideImageLayout;
import com.yidejia.app.mall.datamanage.MainPageDataManage;
import com.yidejia.app.mall.initview.HotSellView;
import com.yidejia.app.mall.model.BaseProduct;
import com.yidejia.app.mall.model.MainProduct;
import com.yidejia.app.mall.view.AllOrderActivity;
import com.yidejia.app.mall.view.IntegeralActivity;
import com.yidejia.app.mall.view.MyCollectActivity;
import com.yidejia.app.mall.view.PersonActivity;
import com.yidejia.app.mall.widget.YLViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainPageFragment extends SherlockFragment {
	
	public static MainPageFragment newInstance(int title){
		
		MainPageFragment fragment = new MainPageFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("main", title);
		fragment.setArguments(bundle);
		
		return fragment;
	}
	
	private int main;
	private int defaultInt = -1;
	private String TAG = "MainPageFragment";
	private PullToRefreshScrollView mPullToRefreshScrollView;
	private TextView main_mall_notice_content;
//	private RelativeLayout myorderLayout;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		main = (bundle != null)? bundle.getInt("main"):defaultInt;
		getSherlockActivity().getSupportActionBar().setCustomView(R.layout.actionbar_main_home_title);
	}

	private View view;
	private LayoutInflater inflater;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.activity_main_layout, container, false);
		this.inflater = inflater;
		switch (main) {
		case 0:		
			
			break;
			
		default:
			break;
		}
		MainPageDataManage manage = new MainPageDataManage(getSherlockActivity(), null);
		manage.getMainPageData();
		bannerArray = manage.getBannerArray();
		acymerArray = manage.getAcymerArray();
		inerbtyArray = manage.getInerbtyArray();
		hotsellArray = manage.getHotSellArray();
		ggTitleArray = manage.getGGTitle();
		return view;
	}
	
	
	/**
	 * activity����ʱ�������������ʾ����
	 * @param view
	 * @param inflater
	 */
	private void createView(View view, LayoutInflater inflater){
		getMainListFirstItem();
		FrameLayout layout = (FrameLayout) view.findViewById(R.id.layout);
		layout.addView(mMainView);
		RelativeLayout shorcutLayout = (RelativeLayout) view.findViewById(R.id.function_parent_layout);
		View child = inflater.inflate(R.layout.main_function, null);
		shorcutLayout.addView(child);
		
		mPullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.main_pull_refresh_scrollview);
		mPullToRefreshScrollView.setScrollingWhileRefreshingEnabled(true);
		mPullToRefreshScrollView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
		mPullToRefreshScrollView.setVerticalScrollBarEnabled(false); //���ô�ֱ����
		mPullToRefreshScrollView.setHorizontalScrollBarEnabled(false); //����ˮƽ����
		String label = "�ϴθ�����"	+ DateUtils.formatDateTime(
				getSherlockActivity().getApplicationContext(),
				System.currentTimeMillis(),
				DateUtils.FORMAT_SHOW_TIME
					| DateUtils.FORMAT_SHOW_DATE
					| DateUtils.FORMAT_ABBREV_ALL);
		mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		mPullToRefreshScrollView.setOnRefreshListener(listener);
		
		functionIntent(child);
		
		main_mall_notice_content = (TextView) view.findViewById(R.id.main_mall_notice_content);
	}
	
	private OnRefreshListener<ScrollView> listener = new OnRefreshListener<ScrollView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
			// TODO Auto-generated method stub
			String label = "�ϴθ�����"	+ DateUtils.formatDateTime(
					getSherlockActivity().getApplicationContext(),
					System.currentTimeMillis(),
					DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
//			mPullToRefreshScrollView.setRefreshing();
			MainPageDataManage manage = new MainPageDataManage(getSherlockActivity(), mPullToRefreshScrollView);
			
			manage.getMainPageData();
			bannerArray = manage.getBannerArray();
			acymerArray = manage.getAcymerArray();
			inerbtyArray = manage.getInerbtyArray();
			hotsellArray = manage.getHotSellArray();
			ggTitleArray = manage.getGGTitle();
			HotSellView hotSellView = new HotSellView(view);
			hotSellView.initHotSellView(hotsellArray);
			hotSellView.initAcymerView(acymerArray);
			hotSellView.initInerbtyView(inerbtyArray);
			main_mall_notice_content.setText(ggTitleArray.get(0));
//			mPullToRefreshScrollView.onRefreshComplete();
		}
		
		
	};
	
	/**
	 * ��ȡ��ҳ��Ʒhotsell,acymer,inerbty���ֵĿؼ�����ת
	 * @param view
	 */
	private void intentToView(View view){
		//hot sell ���
		RelativeLayout hotsellLeft = (RelativeLayout)view.findViewById(R.id.main_hot_sell_left);
		hotsellLeft.setOnClickListener(new MainGoodsOnclick(hotsellArray.get(0).getUId()));
		//hot sell ����
		RelativeLayout hotsellRightTop = (RelativeLayout)view.findViewById(R.id.main_hot_sell_right_top);
		hotsellRightTop.setOnClickListener(new MainGoodsOnclick(hotsellArray.get(1).getUId()));
		//hot sell ����
		RelativeLayout hotsellRightDown = (RelativeLayout)view.findViewById(R.id.main_hot_sell_right_down);
		hotsellRightDown.setOnClickListener(new MainGoodsOnclick(hotsellArray.get(2).getUId()));
		
		// acymer ���
		RelativeLayout acymeiLeft = (RelativeLayout)view.findViewById(R.id.main_acymei_left);
		acymeiLeft.setOnClickListener(new MainGoodsOnclick(acymerArray.get(0).getUId()));
		// acymer ����
		RelativeLayout acymeiRightTop = (RelativeLayout)view.findViewById(R.id.main_acymei_right_top);
		acymeiRightTop.setOnClickListener(new MainGoodsOnclick(acymerArray.get(1).getUId()));
		// acymer ����
		RelativeLayout acymeiRightDown = (RelativeLayout)view.findViewById(R.id.main_acymei_right_down);
		acymeiRightDown.setOnClickListener(new MainGoodsOnclick(acymerArray.get(2).getUId()));
		
		// inerbty ����
		RelativeLayout inerbtyTopLeft = (RelativeLayout)view.findViewById(R.id.main_inerbty_top_left);
		inerbtyTopLeft.setOnClickListener(new MainGoodsOnclick(inerbtyArray.get(0).getUId()));
		// inerbty ����
		RelativeLayout inerbtyTopRight = (RelativeLayout)view.findViewById(R.id.main_inerbty_top_right);
		inerbtyTopRight.setOnClickListener(new MainGoodsOnclick(inerbtyArray.get(1).getUId()));
		// inerbty ����
		RelativeLayout nerbtyMidLeft = (RelativeLayout)view.findViewById(R.id.main_inerbty_mid_left);
		nerbtyMidLeft.setOnClickListener(new MainGoodsOnclick(inerbtyArray.get(2).getUId()));
		// inerbty ����
		RelativeLayout inerbtyMidRight = (RelativeLayout)view.findViewById(R.id.main_inerbty_mid_right);
		inerbtyMidRight.setOnClickListener(new MainGoodsOnclick(inerbtyArray.get(3).getUId()));
		// inerbty ����
		RelativeLayout inerbtyDownLeft = (RelativeLayout)view.findViewById(R.id.main_inerbty_down_left);
		inerbtyDownLeft.setOnClickListener(new MainGoodsOnclick(inerbtyArray.get(4).getUId()));
		// inerbty ����
		RelativeLayout inerbtyDownRight = (RelativeLayout)view.findViewById(R.id.main_inerbty_down_right);
		inerbtyDownRight.setOnClickListener(new MainGoodsOnclick(inerbtyArray.get(5).getUId()));
	}
	/**
	 * ��ҳhotsell,acymer,inerbty��Ʒ����¼�
	 * @author long bin
	 *
	 */
	private class MainGoodsOnclick implements OnClickListener{
		
		private String goodsId;
		public MainGoodsOnclick(String goodsId){
			this.goodsId = goodsId;
		}
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intentLeft = new Intent(getSherlockActivity(), GoodsInfoActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("goodsId", goodsId);
			intentLeft.putExtras(bundle);
			getSherlockActivity().startActivity(intentLeft);
		}
		
	}

	/**
	 * ��ݹ����ǿ�������Ӧ����
	 * @param child
	 */
	private void functionIntent(View child){
		RelativeLayout myOrder = (RelativeLayout) child.findViewById(R.id.function_my_order);//����
		myOrder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intentOrder = new Intent(getSherlockActivity(),AllOrderActivity.class);
				getSherlockActivity().startActivity(intentOrder);
			}
		});
		
		RelativeLayout myCollect = (RelativeLayout) child.findViewById(R.id.function_my_favorite);//�ղ�
		myCollect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intentOrder = new Intent(getSherlockActivity(),MyCollectActivity.class);
				getSherlockActivity().startActivity(intentOrder);
			}
		});
		RelativeLayout myEvent = (RelativeLayout) child.findViewById(R.id.function_event);//���
		myEvent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intentOrder = new Intent(getSherlockActivity(),MyCollectActivity.class);
				getSherlockActivity().startActivity(intentOrder);
			}
		});
		RelativeLayout myMember = (RelativeLayout) child.findViewById(R.id.function_member);//��Ա��
		myMember.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intentOrder = new Intent(getSherlockActivity(),MyCollectActivity.class);
				getSherlockActivity().startActivity(intentOrder);
			}
		});
		RelativeLayout myHistory = (RelativeLayout) child.findViewById(R.id.function_history);//�����ʷ
		myHistory.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intentOrder = new Intent(getSherlockActivity(), SearchActivity.class);
				getSherlockActivity().startActivity(intentOrder);
			}
		});
		RelativeLayout myCoupon = (RelativeLayout) child.findViewById(R.id.function_coupon);//���ֿ�ȯ
		myCoupon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intentOrder = new Intent(getSherlockActivity(),IntegeralActivity.class);
				getSherlockActivity().startActivity(intentOrder);
			}
		});
		RelativeLayout mySkin = (RelativeLayout) child.findViewById(R.id.function_skin);//��������
		mySkin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intentOrder = new Intent(getSherlockActivity(),IntegeralActivity.class);
				getSherlockActivity().startActivity(intentOrder);
			}
		});
		RelativeLayout myMessage = (RelativeLayout) child.findViewById(R.id.function_message);//��Ϣ����
		myMessage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intentOrder = new Intent(getSherlockActivity(),PersonActivity.class);
				getSherlockActivity().startActivity(intentOrder);
			}
		});
	}

	private ArrayList<BaseProduct> bannerArray;
	private ArrayList<MainProduct> acymerArray;
	private ArrayList<MainProduct> inerbtyArray;
	private ArrayList<MainProduct> hotsellArray;
	private ArrayList<String> ggTitleArray;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "TestFragment-----onActivityCreated");
//		intentToView(view);
		
		createView(view, inflater);
		HotSellView hotSellView = new HotSellView(view);
		hotSellView.initHotSellView(hotsellArray);
		hotSellView.initAcymerView(acymerArray);
		hotSellView.initInerbtyView(inerbtyArray);
		intentToView(view);
		main_mall_notice_content.setText(ggTitleArray.get(0));
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d(TAG, "TestFragment-----onStart");
	}
	
	private ViewGroup mMainView = null;
	private YLViewPager mViewPager;
	private ViewGroup mImageCircleView = null;
	private SlideImageLayout mSlideLayout;
	private ImageView[] mImageCircleViews;
//	private static final int[] ids = { R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};
	private int length = 0;
	/**
	 * 
	 * @return ��ҳ�ֲ��Ŀؼ�
	 */
	private ViewGroup getMainListFirstItem() {
		length = bannerArray.size();
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = (ViewGroup) inflater.inflate(
				R.layout.layout_first_item_in_main_listview, null);
		if(length == 0) return mMainView;
		mViewPager = (YLViewPager) mMainView.findViewById(R.id.image_slide_page);
		mSlideLayout = new SlideImageLayout(getActivity(),
				mMainView.getContext(),width);
		mSlideLayout.setCircleImageLayout(length);
		mImageCircleViews = new ImageView[length];
		mImageCircleView = (ViewGroup) mMainView
				.findViewById(R.id.layout_circle_images);
//		mSlideTitle = (TextView) mMainView.findViewById(R.id.tvSlideTitle);
		
		Log.i(TAG, "length--------------------------------"+bannerArray.size());
		for (int i = 0; i < length; i++) {
			
			//mainImageData.getBitmaps().get(i)
//			mImagePageViewList.add(mSlideLayout
//					.getSlideImageLayout((Bitmap)topAdImage.get(i)));//mainImageData.getBitmaps().get(i)
			mImageCircleViews[i] = mSlideLayout.getCircleImageLayout(i);
			mImageCircleView.addView(mSlideLayout.getLinearLayout(
					mImageCircleViews[i], 9, 9));
		}

		// ����ViewPager
		mViewPager.setAdapter(new SlideImageAdapter(bannerArray));
		mViewPager.setOnPageChangeListener(new ImagePageChangeListener());
		mViewPager.setCurrentItem(0);
		mViewPager.setOffscreenPageLimit(2);
		return mMainView;
	}
	/** 
	 * ����ͼƬ����������
	 */
    private class SlideImageAdapter extends PagerAdapter {
    	private LayoutInflater inflater;
    	
    	private ArrayList<BaseProduct> bannerArray;
    	public SlideImageAdapter(ArrayList<BaseProduct> bannerArray){
    		this.bannerArray = bannerArray;
    		inflater = getActivity().getLayoutInflater();
    		initDisplayImageOption();
    	}
//    	private int height = (int)((float)width / 320) * 160;

//    	public SlideImageAdapter(){
//    	}
        public int getCount() {
            return length;//1000;
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
//			Toast.makeText(getSherlockActivity(), bannerArray.get(position).getImgUrl(), Toast.LENGTH_SHORT).show();
			imageLoader.displayImage(bannerArray.get(position).getImgUrl(), imageView, options,
					animateFirstListener);
//			imageView.setBackgroundResource(ids[position%length]);
	        imageView.setOnClickListener(new ImageOnClickListener(position));
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
        	private int index;
        	public ImageOnClickListener(int index){
        		this.index = index;
        	}
        	@Override
        	public void onClick(View v) {
//        		Toast.makeText(getActivity(), "�ҵ���˵�"+"["+pageIndex%length+"]����", Toast.LENGTH_SHORT).show();
        		Intent intent = new Intent(getSherlockActivity(), GoodsInfoActivity.class);
        		Bundle bundle = new Bundle();
        		bundle.putString("goodsId", bannerArray.get(index).getUId());
        		intent.putExtras(bundle);
        		getSherlockActivity().startActivity(intent);
        	}
        }
    }
//    private int pageIndex = 0;

    /** 
     * ����ҳ������¼�������
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
//            pageIndex = index;
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
    
    
    
	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private void initDisplayImageOption(){
		options = new DisplayImageOptions.Builder()
//			.showStubImage(R.drawable.hot_sell_right_top_image)
//			.showImageOnFail(R.drawable.hot_sell_right_top_image)
//			.showImageForEmptyUri(R.drawable.hot_sell_right_top_image)
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.build();
	}
	
	static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {


		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
