package com.yidejia.app.mall.model;
/**
 * ��Ʒ������Ϣ������ id��ͼƬ����
 * @author long bin
 *
 */
public class BaseProduct {
	private String uId;            //��ƷID ������ͨ�����ID��������Ʒ���飩
    private String imgUrl;      //ͼƬ����
    
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
