package com.yidejia.app.mall.model;
/**
 * Ʒ�ƻ�����Ϣ������ id������
 * @author long bin
 *
 */
public class Brand {
	private int brandId;            //Ʒ��ID ������ͨ�����ID��������Ʒ���飩ʵ���Ϻ���û�����id
    private String brandName;   //Ʒ������
    
    private String desc; 	// ����
    
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
