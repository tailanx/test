package com.yidejia.app.mall.model;
/**
 * 免费送，积分换购商品.包括商品Id，商品描述，所需价格，所需积分，图片地址
 * @author long bin
 *
 */
public class Specials {
	private String uId;            //商品Id
	private String brief;       //商品描述
	private String price;        //所需价格
	private String scores;       //所需积分
	private String imgUrl;      //图片地址
	
	/**
	 * 
	 * @param uId 设置商品Id
	 */
	public void setUId(String uId){
		this.uId = uId;
	}
	/**
	 * 
	 * @return uId 返回商品Id
	 */
	public String getUId(){
		return uId;
	}
	/**
	 * 
	 * @param price 所需价格
	 */
	public void setPrice(String price){
		this.price = price;
	}
	/**
	 * 
	 * @return price 所需价格
	 */
	public String getPrice(){
		return price;
	}
	/**
	 * 
	 * @return imgUrl 图片地址
	 */
	public String getImgUrl(){
    	return imgUrl;
    }
	/**
	 * 
	 * @param imgUrl 图片地址
	 */
	public void setImgUrl(String imgUrl){
    	this.imgUrl = imgUrl;
    }
	/**
	 * 
	 * @param brief 商品概要描述
	 */
	public void setBrief(String brief){
		this.brief = brief;
	}
	/**
	 * 
	 * @return brief 商品概要描述
	 */
	public String getBrief(){
		return brief;
	}
	/**
	 * 
	 * @param scores 所需积分
	 */
	public void setScores(String scores){
		this.scores = scores;
	}
	/**
	 * 
	 * @return scores 所需积分
	 */
	public String getScores(){
		return scores;
	}
}
