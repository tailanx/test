package com.yidejia.app.mall;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Application;

public class MyApplication extends Application{
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
	
	private boolean isLogin;
	public void setIsLogin(boolean isLogin){
		this.isLogin = isLogin;
	}
	
	public boolean getIsLogin(){
		return isLogin;
	}
	
	private String userId;
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getUserId(){
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
}
