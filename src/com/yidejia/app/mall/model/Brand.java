package com.yidejia.app.mall.model;
/**
 * 品牌基本信息，包括 id，名称
 * @author long bin
 *
 */
public class Brand {
	private int brandId;            //品牌ID （可以通过这个ID索引到商品详情）实际上好像没有这个id
    private String brandName;   //品牌名称
    
    private String desc; 	// 描述
    
    public String getBrandName(){
    	return brandName;
    }

    public void setBrandName(String brandName){
    	this.brandName = brandName;
    }
    
    public int getBrandId(){
    	return brandId;
    }
    
    public void setBrandId(int brandId){
    	this.brandId = brandId;
    }
    
    public void setDesc(String desc){
    	this.desc =  desc;
    }
    
    public String getDesc(){
    	return desc;
    }
    
    
    // ��ȡƷ���б�
//    vector<S_Brand> GetBrandArray();
}
