package com.yidejia.app.mall.net.homepage;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.BaseProduct;
import com.yidejia.app.mall.model.MainProduct;
import com.yidejia.app.mall.net.HttpGetConn;
import com.yidejia.app.mall.util.UnicodeToString;
/**
 * ��ȡ��ҳ
 * @author long bin
 *
 */
public class GetHomePage {
	
	private ArrayList<BaseProduct> bannerArray; //轮播商品 （个数不固定，<=6）
	private ArrayList<MainProduct> hotSellArray; //热卖商品 （个数暂定3个）
	private ArrayList<MainProduct> acymerArray;    //美容护肤  （个数暂定3个）
	private ArrayList<MainProduct> inerbtyArray;    //内调养护  （个数暂定6个）
	private Context context;
	private String TAG = GetHomePage.class.getName();
	private UnicodeToString unicode;
	
	private boolean isHasPTR = false;
	
	private String result = "";
	public String getHomePageJsonString()throws IOException{
//		HttpGetConn conn = new HttpGetConn(getHttpAddress());
		HttpGetConn conn = new HttpGetConn(JNICallBack.getHttp4GetHome(), true);
		result = conn.getJsonResult();
		return result;
	}
	
}
