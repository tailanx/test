package com.yidejia.app.mall.model;
/**
 * 搜索模块需求和接口 
 * <p>
 * 参数：uId, price, selledAmount, commentAmount, brief, imgUrl, name , favoriteId
 * <p>
 * @author long bin
 *
 */
public class SearchItem {
	private String uId;                //商品Id
	private String name; 			//商品名称
	private String price;            //价格
	private String selledAmount;       //已售数量
	private String commentAmount;      //评论数量
	private String brief;           //商品概要描述 （《＝256字节）
	private String imgUrl;          //小图片链接地址
	private String favoriteId;		//收藏id ，用于收藏，商品那块可忽略这个属性
	private String the_code;   		//商品编号
	private String module;			//商品功效
	private String brand;			//品牌
	private String spec;			//规格
	private String desc;			//描述
	
	
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
	public void setSelledAmount(String selledAmount){
		this.selledAmount = selledAmount;
	}
	/**
	 * 
	 * @return  salledAmount 已销售数量
	 */
	public String getSelledAmount(){
		return selledAmount;
	}
	/**
	 * 
	 * @param commentAmmount 评论数量
	 */
	public void setCommentAmount(String commentAmount){
		this.commentAmount = commentAmount;
	}
	/**
	 * @return commentAmmount 评论数量
	 */
	public String getCommentAmount(){
		return commentAmount;
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
	 * 商品名称
	 * @param name
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 商品名称
	 * @return
	 */
	public String getName(){
		return name;
	}
	/**
	 * 收藏id ，用于收藏，商品那块可忽略这个属性
	 * @param favoriteId
	 */
	public void setFavoriteId(String favoriteId){
		this.favoriteId = favoriteId;
	}
	/**
	 * 收藏id ，用于收藏，商品那块可忽略这个属性
	 * @return favoriteId
	 */
	public String getFavoriteId(){
		return favoriteId;
	}
	
//	vector<S_SearchItem> GetSearchArray(const char* order, const char* name, const char* brand, const char* fun, const char* price);
	
	/**
	 * 
	 * @param the_code 商品编号
	 */
	public void setTheCode(String the_code){
		this.the_code = the_code;
	}
	/**
	 * 
	 * @return 商品编号
	 */
	public String getTheCode(){
		return the_code;
	}
	/**
	 * 
	 * @param module 商品功效
	 */
	public void setModule(String module){
		this.module = module;
	}
	/**
	 * 
	 * @return 商品功效
	 */
	public String getModule(){
		return module;
	}
	/**
	 * 
	 * @param brand 品牌
	 */
	public void setBrand(String brand){
		this.brand = brand;
	}
	/**
	 * 
	 * @return 品牌
	 */
	public String getBrand(){
		return brand;
	}
	/**
	 * 
	 * @param spec 规格
	 */
	public void setSpec(String spec){
		this.spec = spec;
	}
	/**
	 * 
	 * @return 规格
	 */
	public String getSpec(){
		return spec;
	}
	/**
	 * 
	 * @param desc 描述
	 */
	public void setDesc(String desc){
		this.desc = desc;
	}
	/**
	 * 
	 * @return 描述
	 */
	public String getDesc(){
		return desc;
	}
}

