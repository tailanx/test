package com.yidejia.app.mall.model;
/**
 * 价格区间
 * @author long bin
 *
 */
public class PriceLevel {
	
	private String priceId;        // 价格区间Id
	private String min;          // 区间最小值
	private String max;          // 价格区间最大值
	 
	public void setPriceId(String priceId){
		this.priceId = priceId;
	}
	public String getPriceId(){
		return priceId;
	}
	
	public void setMinPrice(String minPrice){
		this.min = minPrice;
	}
	
	public String getMinPrice(){
		return min;
	}
	
	public void setMaxPrice(String maxPrice){
		this.max = maxPrice;
	}
	public String getMaxPrice(){
		return max;
	}
	// 获取价格区间列表
//	 vector<S_PriceLevel> GetPriceLevelArray();
}
