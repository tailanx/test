package com.yidejia.app.mall.net.commments;

import java.io.IOException;

import android.util.Log;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpGetConn;
/**
 * 获取用户待评论的商品列表
 * @author long bin
 *
 */
public class WaitingComment {
	private String TAG = WaitingComment.class.getName();
	
	private String result = "";
	public String getHttpResp(String userid)throws IOException{
//		HttpGetConn conn = new HttpGetConn(getHttpAddress(where, offset, limit, group, order, fields));
		HttpGetConn conn = new HttpGetConn(JNICallBack.getHttp4GetNoEvaluate(userid), true);
		result = conn.getJsonResult();
		Log.i(TAG, result);
		return result;
	}
	
}
