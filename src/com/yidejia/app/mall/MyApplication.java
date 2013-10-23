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
	
	private String token;//登录后返回的token
	
	public void setToken(String token){
		this.token = token;
	}
	
	public String getToken(){
		return token;
	}
	
	private String userHeadImg;//用户头像地址
	public void setUserHeadImg(String img){
		this.userHeadImg = img;
	}
	
	public String getUserHeadImg(){
		return userHeadImg;
	}
	private String password;//密码，应该是md5值
	private String nick;//昵称
	
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
	
	private String vip;//vip等级
	public void setVip(String vip){
		this.vip = vip;
	}
	public String getVip(){
		return vip;
	}

}
