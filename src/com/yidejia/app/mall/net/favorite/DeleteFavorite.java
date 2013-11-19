package com.yidejia.app.mall.net.favorite;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;




import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpAddressParam;
import com.yidejia.app.mall.net.HttpPostConn;
import com.yidejia.app.mall.util.Md5;
/**
 * ɾ���ղ�
 * @author long bin
 *
 */
public class DeleteFavorite {
	
	private String TAG = DeleteFavorite.class.getName();
	
	public String deleteFavorite(String userid, String goodsid, String token) throws IOException{
		HttpPostConn conn = new HttpPostConn(new JNICallBack().getHttp4DelFav(userid, goodsid, token));
		return conn.getHttpResponse();
	}
	
	/**
	 * 解析添加收藏数据
	 * @param resultJson http返回的数据
	 * @return
	 */
	public boolean analysicDeleteJson(String resultJson) {
		if ("".equals(resultJson))
			return false;

		JSONObject httpResultObject;
		try {
			httpResultObject = new JSONObject(resultJson);
			int code = httpResultObject.getInt("code");
			Log.i(TAG, "analysis delete json code"+code);
			if (code == 1){
				return true;
			}
			else {
				return false;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "delete favorite analysis json jsonexception error");
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "delete favorite analysis json exception error");
			e.printStackTrace();
			return false;
		}
	}
	
}
