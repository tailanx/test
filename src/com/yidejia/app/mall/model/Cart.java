package com.yidejia.app.mall.model;

import java.io.Serializable;

/**
 * 购物车信息，包括 商品Id，购买数量，商品描述，价格，商品小图链接地址
 * @author long bin
 *
 */
public class Cart implements Serializable{
	private String uId;                //商品Id
	private int amount;             //购买数量
	private String productText;     //商品描述（《＝256字节）
	private float price;            //价格
	private String imgUrl;          //商品小图链接地址
	/**
	 * 
	 * @param uId 设置product id
	 */
	public void setUId(String uId){
		this.uId = uId;
	}
	/**
	 * 
	 * @return uId 返回product id
	 */
	public String getUId(){
		return uId;
	}
	/**
	 * 
	 * @param price 价格
	 */
	public void setPrice(float price){
		this.price = price;
	}
	/**
	 * 
	 * @return price 价格
	 */
	public float getPrice(){
		return price;
	}
	/**
	 * 
	 * @param amount 购买数量
	 */
	public void setSalledAmmount(int amount){
		this.amount = amount;
	}
	/**
	 * 
	 * @return  Amount 购买数量
	 */
	public int getAmount(){
		return amount;
	}
	/**
	 * 
	 * @return imgUrl 小图片链接地址
	 */
	public String getImgUrl(){
    	return imgUrl;
    }
	/**
	 * 
	 * @param imgUrl 小图片链接地址
	 */
	public void setImgUrl(String imgUrl){
    	this.imgUrl = imgUrl;
    }
	/**
	 * 
	 * @param productText 商品描述
	 */
	public void setProductText(String productText){
		this.productText = productText;
	}
	/**
	 * 
	 * @return productText 商品描述
	 */
	public String getProductText(){
		return productText;
	}
	
	
	/*添加到购物车
	 userId:    客户Id
	 return:    购物车产品列表
	 */
//	vector<S_Cart> GetCartsArray(int userId);
	
}