package com.yidejia.app.mall.model;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * 商品详情页的基本信息 , 参数 uid,bannerArray,name,salledAmount,price,commentAmount
 * ,showListAmount,brands,bannerArray,productSpecifications,productNumber,recommendArray
 * @author long bin
 *
 */
public class ProductBaseInfo implements Serializable{
	private String uId;                            //product id
	private String name;						//商品名称
	private String brief;                       //商品概要描述（<＝256字节）
	private String price;                        //价格
	private String salledAmount;                  //已销售数量
	private String showListAmount;                //晒单数量
	private String commentAmount;                 //评论数量
	private String productDetailUrl;            //商品详情页面链接
	private ArrayList<MainProduct> recommendArray;    //推荐搭配 （<＝10？）
	private String productSpecifications;       //规格
	private String productNumber;               //商品编号
	private String brands;                      //品牌
	private ArrayList<BaseProduct> bannerArray;  //轮播商品（个数不固定，<=5?)
	private String imgUrl; // 小图地址
	private boolean show_flag;	//是否能购买
	
	public boolean isShow_flag() {
		return show_flag;
	}
	
	public void setShow_flag(boolean show_flag) {
		this.show_flag = show_flag;
	}
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
	 * @param name 商品名称
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 
	 * @return 商品名称
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 
	 * @param bannerArray 设置 轮播商品（个数不固定，<=5?)
	 */
	public void setBannerArray(ArrayList<BaseProduct> bannerArray){
		this.bannerArray = bannerArray;
	}
	/**
	 * 
	 * @return bannerArray 轮播商品
	 */
	public ArrayList<BaseProduct> getBannerArray(){
		return bannerArray;
	}
	
	/**
	 * 
	 * @param brief 商品概要描述（《＝256字节）
	 */
	public void setBrief(String brief){
		this.brief = brief;
	}
	/**
	 * 
	 * @return brief 商品概要描述（《＝256字节）
	 */
	public String getBrief(){
		return brief;
	}
	/**
	 * 
	 * @param price 价格
	 */
	public void setPrice(String price){
		this.price = price;
	}
	/**
	 * 
	 * @return price 价格
	 */
	public String getPrice(){
		return price;
	}
	/**
	 * 
	 * @param salledAmount 已销售数量
	 */
	public void setSalledAmount(String salledAmount){
		this.salledAmount = salledAmount;
	}
	/**
	 * 
	 * @return  salledAmmount 已销售数量
	 */
	public String getSalledAmmount(){
		return salledAmount;
	}
	
	/**
	 * 
	 * @param commentAmount 评论数量
	 */
	public void setCommentAmount(String commentAmount){
		this.commentAmount = commentAmount;
	}
	/**
	 * @return commentAmount 评论数量
	 */
	public String getCommentAmount(){
		return commentAmount;
	}
	
	/**
	 * 
	 * @param showListAmount 晒单数量
	 */
	public void setShowListAmount(String showListAmount){
		this.showListAmount = showListAmount;
	}
	/**
	 * 
	 * @return showListAmmount 晒单数量
	 */
	public String getShowListAmount(){
		return showListAmount;
	}
	/**
	 * 
	 * @param brands 品牌
	 */
	public void setBrands(String brands){
		this.brands = brands;
	}
	/**
	 * 
	 * @return brands 品牌
	 */
	public String getBrands(){
		return brands;
	}
	/**
	 * 
	 * @param productNumber 商品编号
	 */
	public void setProductNumber(String productNumber){
		this.productNumber = productNumber;
	}
	/**
	 * 
	 * @return productNumber 商品编号
	 */
	public String getProductNumber(){
		return productNumber;
	}
	/**
	 * 
	 * @param productSpecifications 规格
	 */
	public void setProductSpecifications(String productSpecifications){
		this.productSpecifications = productSpecifications;
	}
	/**
	 * 
	 * @return productSpecifications 规格
	 */
	public String getProductSpecifications(){
		return productSpecifications;
	}
	/**
	 * 
	 * @param recommendArray 推荐搭配
	 */
	public void setRecommendArray(ArrayList<MainProduct> recommendArray){
		this.recommendArray = recommendArray;
	}
	/**
	 * 
	 * @return recommendArray 推荐搭配
	 */
	public ArrayList<MainProduct> getRecommendArray(){
		return recommendArray;
	}
	/**
	 * 
	 * @param productDetailUrl 商品详情页面链接
	 */
	public void setProductDetailUrl(String productDetailUrl){
		this.productDetailUrl = productDetailUrl;
	}
	/**
	 * 
	 * @return productDetailUrl 商品详情页面链接
	 */
	public String getProductDetailUrl(){
		return productDetailUrl;
	}
	/**
	 * 
	 * @param imgUrl 小图地址
	 */
	public void setImgUrl(String imgUrl){
		this.imgUrl = imgUrl;
	}
	/**
	 * 
	 * @return 小图地址
	 */
	public String getImgUrl(){
		return imgUrl;
	}
}
