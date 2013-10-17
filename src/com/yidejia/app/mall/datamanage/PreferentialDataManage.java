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
 * ��ȡ����ͣ����ֻ�����Ʒ����
 * @author long bin
 *
 */
public class PreferentialDataManage {
//	private Preferential preferential; 
	private ArrayList<Specials> freeProductArray;        //�������Ʒ
	private ArrayList<Specials> scoresProductArray;      //���ֻ�����Ʒ
	private Context context;
	
	public PreferentialDataManage(Context context){
		this.context = context;
//		preferential = new Preferential();
		freeProductArray = new ArrayList<Specials>();
		scoresProductArray = new ArrayList<Specials>();
	}
	/**
	 * ���ݿͻ����ύ�Ĺ��ﳵ��ȡ�Żݲ�Ʒ
	 * @param goods ���ﳵ�ϵ���Ʒ���� ���룬 ��ʽ"4,5n;"��"6,11n;"
	 * @param userId �ͻ�Id��
	 * @return  ��������ͣ����ֻ�����Ʒ
	 */
	public void getPreferential(String goods, String userId){
		return ;
	}
	/**
	 * ���ݿͻ����ύ�Ĺ��ﳵ��ȡ�Żݲ�Ʒ
	 * @param userId �ͻ�Id��
	 * @param cards JSON���ݣ�{[uId, amount], [uId, amount]...} )
	 * @return ��������ͣ����ֻ�����Ʒ
	 */
	public void getPreferential(int userId, String cards){
		return ;
	}
	/**
	 * �ͻ�����������Ʒ
	 * @param userId �ͻ�id
	 * @return true:�ɹ���false:ʧ��
	 */
	public boolean addFreePruduct(int userId){
		boolean result = true;
		return result;
	}
	/**
	 * �ͻ�ɾ���������Ʒ
	 * @param userId �ͻ�id
	 * @return true:�ɹ���false:ʧ��
	 */
	public boolean deleteFreePruduct(int userId){
		boolean result = true;
		return result;
	}
	/**
	 * �ͻ���ӻ��ֻ�����Ʒ
	 * @param userId �ͻ�id
	 * @return true:�ɹ���false:ʧ��
	 */
	public boolean addScoresPruduct(int userId){
		boolean result = true;
		return result;
	}
	/**
	 * �ͻ�ɾ�����ֻ�����Ʒ
	 * @param userId �ͻ�id
	 * @return true:�ɹ���false:ʧ��
	 */
	public boolean deleteScoresPruduct(int userId){
		boolean result = true;
		return result;
	}
	/**
	 * �ύ�ͻ�����
	 * @param userId �ͻ�id
	 * @return true:�ɹ���false:ʧ��
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
				//��û�����
			}
		}
	}
}
