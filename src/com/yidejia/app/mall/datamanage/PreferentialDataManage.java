package com.yidejia.app.mall.datamanage;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.yidejia.app.mall.model.Preferential;
import com.yidejia.app.mall.model.Specials;
import com.yidejia.app.mall.net.voucher.Verify;

/**
 * 获取免费送，积分换购商品操作
 * @author long bin
 *
 */
public class PreferentialDataManage {
//	private Preferential preferential; 
	private ArrayList<Specials> freeProductArray;        //免费送商品
	private ArrayList<Specials> scoresProductArray;      //积分换购商品
	private Context context;
	
	public PreferentialDataManage(Context context){
		this.context = context;
//		preferential = new Preferential();
		freeProductArray = new ArrayList<Specials>();
		scoresProductArray = new ArrayList<Specials>();
	}
	/**
	 * 根据客户已提交的购物车获取优惠产品
	 * @param goods 购物车上的商品串， 必须， 格式"4,5n;"或"6,11n;"
	 * @param userId 客户Id；
	 * @return  返回免费送，积分换购商品
	 */
	public void getPreferential(String goods, String userId){
		return ;
	}
	/**
	 * 根据客户已提交的购物车获取优惠产品
	 * @param userId 客户Id；
	 * @param cards JSON数据（{[uId, amount], [uId, amount]...} )
	 * @return 返回免费送，积分换购商品
	 */
	public void getPreferential(int userId, String cards){
		return ;
	}
	/**
	 * 客户添加免费送商品
	 * @param userId 客户id
	 * @return true:成功，false:失败
	 */
	public boolean addFreePruduct(int userId){
		boolean result = true;
		return result;
	}
	/**
	 * 客户删除免费送商品
	 * @param userId 客户id
	 * @return true:成功，false:失败
	 */
	public boolean deleteFreePruduct(int userId){
		boolean result = true;
		return result;
	}
	/**
	 * 客户添加积分换购商品
	 * @param userId 客户id
	 * @return true:成功，false:失败
	 */
	public boolean addScoresPruduct(int userId){
		boolean result = true;
		return result;
	}
	/**
	 * 客户删除积分换购商品
	 * @param userId 客户id
	 * @return true:成功，false:失败
	 */
	public boolean deleteScoresPruduct(int userId){
		boolean result = true;
		return result;
	}
	/**
	 * 提交客户订单
	 * @param userId 客户id
	 * @return true:成功，false:失败
	 */
	public boolean commitOrder(int userId){
		boolean result = true;
		return result;
	}
	
	
	private String goods;
	private String userid;
	private class TaskVerify extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Verify verify = new Verify();
			try {
				String httpResponse = verify.getHttpResponse(goods, userid);
				try {
					JSONObject httpJsonObject = new JSONObject(httpResponse);
					int code = httpJsonObject.getInt("code");
					if(code == 1){
						String response = httpJsonObject.getString("response");
						analysis(response);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
	}
	
	private void analysis(String response) throws JSONException{
		JSONArray resArray = new JSONArray(response);
		for (int i = 0; i < resArray.length(); i++) {
			JSONObject itemObject = resArray.getJSONObject(i);
			String goods_list = itemObject.getString("goods_list");
			JSONArray goodsArray = new JSONArray(goods_list);
			Specials specials;
			JSONObject listObject;
			for (int j = 0; j < goodsArray.length(); j++) {
				specials = new Specials();
				listObject = goodsArray.getJSONObject(j);
				specials.setUId(listObject.getString("goods_id"));
				specials.setImgUrl(listObject.getString("imgname"));
				specials.setPrice(listObject.getString("price"));
				specials.setScores(listObject.getString("score_price"));
				specials.setBrief(listObject.getString("desc"));
				//还没有完成
			}
		}
	}
}
