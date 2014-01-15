package com.yidejia.app.mall;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.Application;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class MyApplication extends Application {

	static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
	
	private static MyApplication instance;
	
	
	private ImageLoadingListener animateFirstListener;
	
	public static MyApplication getInstance() {
		return instance;
	}
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		instance = this;
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
		animateFirstListener = new AnimateFirstDisplayListener();
	}

	private boolean isLogin;

	public void setIsLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public boolean getIsLogin() {
		return isLogin;
	}

	private String userId;

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}

	// 搜索历史记录个数
	private int schHstryAmount = 0;

	public int getSchHstryAmount() {
		return schHstryAmount;
	}

	public void setSchHstryAmount(int schHstryAmount) {
		this.schHstryAmount = schHstryAmount;
	}

	private String token;// 登录后返回的token

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	private String userHeadImg;// 用户头像地址

	public void setUserHeadImg(String img) {
		this.userHeadImg = img;
	}

	public String getUserHeadImg() {
		return userHeadImg;
	}

	private String password;// 密码，应该是md5值
	private String nick;// 昵称

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNick() {
		return nick;
	}

	private String vip;// vip等级

	public void setVip(String vip) {
		this.vip = vip;
	}

	public String getVip() {
		return vip;
	}
	
	/**
	 * 
	 * 返回图片加载的监听
	 */
	public ImageLoadingListener getImageLoadingListener() {
		return animateFirstListener;
	}
	
	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 100);
					displayedImages.add(imageUri);
				}
			}
		}
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		ImageLoader.getInstance().clearMemoryCache();
	}
	
	
	private DisplayImageOptions options;
	public DisplayImageOptions initGoodsImageOption(){
		options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.image_bg)
			.showImageOnFail(R.drawable.image_bg)
			.showImageForEmptyUri(R.drawable.image_bg)
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.build();
		
		return options;
	}
	
	public DisplayImageOptions initBannerImageOption(){
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.image_bg)
		.showImageOnFail(R.drawable.image_bg)
		.showImageForEmptyUri(R.drawable.image_bg)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.build();
		
		return options;
	}
	
}
