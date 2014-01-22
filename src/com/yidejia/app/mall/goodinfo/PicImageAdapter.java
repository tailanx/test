package com.yidejia.app.mall.goodinfo;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.BaseProduct;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.DPIUtil;
import com.yidejia.app.mall.view.ImagePagerActivity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class PicImageAdapter extends PagerAdapter {

	private ArrayList<BaseProduct> productPics;
	private Activity activity;
	private int width;
	private DisplayImageOptions options;

	public PicImageAdapter(Activity activity, ArrayList<BaseProduct> productPics) {
		this.activity = activity;
		this.productPics = productPics;
		width = DPIUtil.getWidth();
		initDisplayImageOption();
	}

	@Override
	public int getCount() {
		if (null == productPics)
			return 0;
		return productPics.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
//		super.destroyItem(container, position, object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView imageView = new ImageView(activity);
		ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width,
				width);
		imageView.setLayoutParams(layoutParams);
		
		ImageLoader.getInstance().init(MyApplication.getInstance().initConfig());
		ImageLoader.getInstance().displayImage(
				productPics.get(position).getImgUrl(), imageView, options,
				MyApplication.getInstance().getImageLoadingListener());
		container.addView(imageView);
		imageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(activity,ImagePagerActivity.class);
				intent.putExtra(Consts.IMAGES, productPics);
				intent.putExtra(Consts.IMAGE_POSITION, v.getId());
				activity.startActivity(intent);
			}
		});
		return imageView;
	}

	private void initDisplayImageOption() {
		options = MyApplication.getInstance().initGoodsImageOption();
	}

}
