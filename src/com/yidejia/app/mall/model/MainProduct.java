package com.yidejia.app.mall.model;
/**
 * 首页接口，包括商品id，图片链接，商品标题，子标题，价格
 * @author long bin
 *
 */
public class MainProduct {
	private String uId;            //商品ID （可以通过这个ID索引到商品详情）
    private String imgUrl;      //图片链接
    private String title;       //商品标题 如：椒盐蛋白
    private String subTitle;    //子标题 如：美白 淡斑
    private String price;       //价格 如：¥220.00元
    
    public String getUId(){
    	return uId;
    }
    
    public void setUId(String uId){
    	this.uId = uId;
    }
    
    public String getImgUrl(){
    	return imgUrl;
    }
    
    public void setImgUrl(String imgUrl){
    	this.imgUrl = imgUrl;
    }
    
    public String getTitle(){
    	return title;
    }
    
    public void setTitle(String title){
    	this.title = title;
    }
    
    public String getSubTitle(){
    	return subTitle;
    }
    
    public void setSubTitle(String subTitle){
    	this.subTitle = subTitle;
    }
    public String getPrice(){
    	return price;
    }
    
    public void setPrice(String price){
    	this.price = price;
    }
    
}
