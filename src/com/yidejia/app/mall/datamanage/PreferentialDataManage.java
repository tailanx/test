package com.yidejia.app.mall.datamanage;

import android.content.Context;

import com.yidejia.app.mall.model.Preferential;

/**
 * ��ȡ����ͣ����ֻ�����Ʒ����
 * @author long bin
 *
 */
public class PreferentialDataManage {
	private Preferential preferential; 
	private Context context;
	
	public PreferentialDataManage(Context context){
		this.context = context;
	}
	/**
	 * ���ݿͻ����ύ�Ĺ��ﳵ��ȡ�Żݲ�Ʒ
	 * @param userId �ͻ�Id��
	 * @return  ��������ͣ����ֻ�����Ʒ
	 */
	public Preferential getPreferential(int userId){
		return preferential;
	}
	/**
	 * ���ݿͻ����ύ�Ĺ��ﳵ��ȡ�Żݲ�Ʒ
	 * @param userId �ͻ�Id��
	 * @param cards JSON���ݣ�{[uId, amount], [uId, amount]...} )
	 * @return ��������ͣ����ֻ�����Ʒ
	 */
	public Preferential getPreferential(int userId, String cards){
		return preferential;
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
}
