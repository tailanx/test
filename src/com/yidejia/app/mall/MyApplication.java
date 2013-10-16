//package com.yidejia.app.mall;
//
//import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
//import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
//
//import android.app.Application;
//
//public class MyApplication extends Application{
//	
//	@Override
//	public void onCreate() {
//		// TODO Auto-generated method stub
//		super.onCreate();
//		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
//				getApplicationContext())
//				.threadPriority(Thread.NORM_PRIORITY - 2)
//				.denyCacheImageMultipleSizesInMemory()
//				.discCacheFileNameGenerator(new Md5FileNameGenerator())
//				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
//
//		// Initialize ImageLoader with configuration.
//		ImageLoader.getInstance().init(config);
//	}
//	
//	private boolean isLogin;
//	public void setIsLogin(boolean isLogin){
//		this.isLogin = isLogin;
//	}
//	
//	public boolean getIsLogin(){
//		return isLogin;
//	}
//	
//	private String userId = 235669 +"";
//	public void setUserId(String userId){
//		this.userId = userId;
//	}
//	
//	public String getUserId(){
//		return this.userId;
//	}
//}
//=======
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
	
	// ������ʷ��¼����
	private int schHstryAmount = 0;

	public int getSchHstryAmount() {
		return schHstryAmount;
	}

	public void setSchHstryAmount(int schHstryAmount) {
		this.schHstryAmount = schHstryAmount;
	}
	
	private String token;//��¼�󷵻ص�token
	
	public void setToken(String token){
		this.token = token;
	}
	
	public String getToken(){
		return token;
	}
	
	private String userHeadImg;//�û�ͷ���ַ
	public void setUserHeadImg(String img){
		this.userHeadImg = img;
	}
	
	public String getUserHeadImg(){
		return userHeadImg;
	}
	private String password;//���룬Ӧ����md5ֵ
	private String nick;//�ǳ�
	
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
	
	private String vip;//vip�ȼ�
	public void setVip(String vip){
		this.vip = vip;
	}
	public String getVip(){
		return vip;
	}

}
