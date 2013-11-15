package com.yidejia.app.mall.net.commments;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.UserComment;
import com.yidejia.app.mall.net.HttpGetConn;
import com.yidejia.app.mall.util.UnicodeToString;
/**
 * 获取商品评论列表
 * @author long bin
 *
 */
public class GetProductCommentList {

	private String TAG = "GetProductCommentList";

	private UnicodeToString unicode;
	private ArrayList<UserComment> userComments;
	private boolean isNoMore = false;//判断是否还有更多数据,true为没有更多了
	private boolean isHasRst = false;//判断是否有结果,true为有结果
	
	public GetProductCommentList(){
		unicode = new UnicodeToString();
		userComments = new ArrayList<UserComment>();
	}
	
	
	
	private String result = "";
	public String getCommentsListJsonString(String where, String offset, String limit, String group, String order, String fields)throws IOException{
//		HttpGetConn conn = new HttpGetConn(getHttpAddress(where, offset, limit, group, order, fields));
		HttpGetConn conn = new HttpGetConn(new JNICallBack().getHttp4GetComment(where, offset, limit, group, order, fields), true);
		result = conn.getJsonResult();
		return result;
	}
	
	
	/**
	 * 解析所有评论数据
	 * @param httpResultString
	 * @return
	 */
	public boolean analysisGetListJson(String httpResultString){
		JSONObject httpResultObject;
		try {
			httpResultObject = new JSONObject(httpResultString);
			int code = httpResultObject.getInt("code");
			Log.i(TAG, "code"+code);
			if(code == 1){
				isHasRst = true;
				String responseString = httpResultObject.getString("response");
				JSONArray responseArray = new JSONArray(responseString);
				int length = responseArray.length();
				JSONObject commentItem ;
				UserComment comments;
				for (int i = 0; i < length; i++) {
					comments = new UserComment();
					commentItem = responseArray.getJSONObject(i);
					String id = commentItem.getString("id");
					comments.setId(id);
					String goodsId = commentItem.getString("goods_id");
					comments.setGoodsId(goodsId);
					String user_id = commentItem.getString("user_id");
					comments.setUserId(user_id);
					String name = commentItem.getString("user_name");
					if(name == null || "".equals(name)) comments.setUserName(user_id);
					else comments.setUserName(unicode.revert(name));
					String title = commentItem.getString("title");
					comments.setTitle(unicode.revert(title));
					String experience = commentItem.getString("experience");
					comments.setUserCommentText(unicode.revert(experience));
					String commentDate = commentItem.getString("commentDate");
					comments.setCommentTime(commentDate);
					String level = commentItem.getString("customer_grade");
					comments.setVipLevel(unicode.revert(level));
//					String maddress = commentItem.getString("address");
//					comments.setAddress(unicode.revert(maddress));
//					String isDefault = commentItem.getString("is_default");
//					boolean isDef = isDefault.equals("y") ? true : false;
//					comments.setDefaultAddress(isDef);
					int rate = 5;
					comments.setRate(rate);
					userComments.add(comments);
				}
				return true;
			} else if(code == -1){
				isNoMore = true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取评论列表
	 * @return
	 */
	public ArrayList<UserComment> getComments(){
		return userComments;
	}
	
	/**
	 * 检查是否没更多数据
	 * @return
	 */
	public boolean getIsNoMore(){
		return isNoMore;
	}
	
	public boolean getIsHasRet(){
		return isHasRst;
	}
	
}
