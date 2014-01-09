package com.yidejia.app.mall.cache;

import com.nostra13.universalimageloader.core.ImageLoader;

public class CleanImageCache {
	
	protected ImageLoader imageLoader = ImageLoader.getInstance(); 
	
	public void clearMemoryCache(){
		imageLoader.clearMemoryCache(); // 清除内存缓存  
	}
	
	public void clearDiscCache(){
		imageLoader.clearDiscCache();// 清除SD卡中的缓存  
	}
	
	public void clearAllCache(){
		clearMemoryCache();
		clearDiscCache();
	}

}
