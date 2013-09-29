package com.yidejia.app.mall.datamanage;

import android.content.Context;

import com.yidejia.app.mall.model.Preferential;

/**
 * 获取免费送，积分换购商品操作
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
	 * 根据客户已提交的购物车获取优惠产品
	 * @param userId 客户Id；
	 * @return  返回免费送，积分换购商品
	 */
	public Preferential getPreferential(int userId){
		return preferential;
	}
	/**
	 * 根据客户已提交的购物车获取优惠产品
	 * @param userId 客户Id；
	 * @param cards JSON数据（{[uId, amount], [uId, amount]...} )
	 * @return 返回免费送，积分换购商品
	 */
	public Preferential getPreferential(int userId, String cards){
		return preferential;
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
}
