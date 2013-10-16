
package com.yidejia.app.mall.model;

import java.io.Serializable;

/**
 * ���ﳵ��Ϣ������ ��ƷId��������������Ʒ�������۸���ƷСͼ���ӵ�ַ
 * @author long bin
 *
 */
public class Cart implements Serializable{
	private String uId;                //��ƷId
	private int amount;             //��������
	private String productText;     //��Ʒ����������256�ֽڣ�
	private float price;            //�۸�
	private String imgUrl;          //��ƷСͼ���ӵ�ַ
	/**
	 * 
	 * @param uId ����product id
	 */
	public void setUId(String uId){
		this.uId = uId;
	}
	/**
	 * 
	 * @return uId ����product id
	 */
	public String getUId(){
		return uId;
	}
	/**
	 * 
	 * @param price �۸�
	 */
	public void setPrice(float price){
		this.price = price;
	}
	/**
	 * 
	 * @return price �۸�
	 */
	public float getPrice(){
		return price;
	}
	/**
	 * 
	 * @param amount ��������
	 */
	public void setSalledAmmount(int amount){
		this.amount = amount;
	}
	/**
	 * 
	 * @return  Amount ��������
	 */
	public int getAmount(){
		return amount;
	}
	/**
	 * 
	 * @return imgUrl СͼƬ���ӵ�ַ
	 */
	public String getImgUrl(){
    	return imgUrl;
    }
	/**
	 * 
	 * @param imgUrl СͼƬ���ӵ�ַ
	 */
	public void setImgUrl(String imgUrl){
    	this.imgUrl = imgUrl;
    }
	/**
	 * 
	 * @param productText ��Ʒ����
	 */
	public void setProductText(String productText){
		this.productText = productText;
	}
	/**
	 * 
	 * @return productText ��Ʒ����
	 */
	public String getProductText(){
		return productText;
	}
	
	
	/*��ӵ����ﳵ
	 userId:    �ͻ�Id
	 return:    ���ﳵ��Ʒ�б�
	 */
//	vector<S_Cart> GetCartsArray(int userId);
	
}
