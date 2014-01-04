
package com.yidejia.app.mall.model;

import java.io.Serializable;

/**
 * 商品基本信息，包括 id，图片链接
 * @author long bin
 *
 */
public class BaseProduct implements Serializable {
	private String uId;            //商品ID （可以通过这个ID索引到商品详情）
    private String imgUrl;      //图片链接
    
    public String getImgUrl(){
    	return imgUrl;
    }

    public String getUId(){
    	return uId;
    }
    
    public void setImgUrl(String imgUrl){
    	this.imgUrl = imgUrl;
    }
    
    public void setUId(String uId){
    	this.uId = uId;
    }
    
    
}
