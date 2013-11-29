package com.yidejia.app.mall.net.commments;

import java.io.IOException;

import org.apache.http.conn.ConnectTimeoutException;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpAddressParam;
import com.yidejia.app.mall.net.HttpGetConn;
import com.yidejia.app.mall.util.Md5;
/**
 * 获取用户待评论的商品列表
 * @author long bin
 *
 */
public class WaitingComment {
	private String TAG = WaitingComment.class.getName();
	
	private String result = "";
	public String getHttpResp(String userid)throws IOException, TimeOutEx{
//		HttpGetConn conn = new HttpGetConn(getHttpAddress(where, offset, limit, group, order, fields));
		HttpGetConn conn = new HttpGetConn(new JNICallBack().getHttp4GetNoEvaluate(userid), true);
		result = conn.getJsonResult();
		Log.i(TAG, result);
		return result;
	}
	
}
