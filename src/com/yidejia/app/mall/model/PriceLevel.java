package com.yidejia.app.mall.model;
/**
 * �۸�����
 * @author long bin
 *
 */
public class PriceLevel {
	
	private String priceId;        // �۸�����Id
	private String min;          // ������Сֵ
	private String max;          // �۸��������ֵ
	 
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
	// ��ȡ�۸������б�
//	 vector<S_PriceLevel> GetPriceLevelArray();
}
