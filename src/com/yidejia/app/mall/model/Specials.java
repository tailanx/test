package com.yidejia.app.mall.model;
/**
 * ����ͣ����ֻ�����Ʒ.������ƷId����Ʒ����������۸�������֣�ͼƬ��ַ
 * @author long bin
 *
 */
public class Specials {
	private int uId;            //��ƷId
	private String brief;       //��Ʒ����
	private float price;        //����۸�
	private float scores;       //�������
	private String imgUrl;      //ͼƬ��ַ
	
	/**
	 * 
	 * @param uId ������ƷId
	 */
	public void setUId(int uId){
		this.uId = uId;
	}
	/**
	 * 
	 * @return uId ������ƷId
	 */
	public int getUId(){
		return uId;
	}
	/**
	 * 
	 * @param price ����۸�
	 */
	public void setPrice(float price){
		this.price = price;
	}
	/**
	 * 
	 * @return price ����۸�
	 */
	public float getPrice(){
		return price;
	}
	/**
	 * 
	 * @return imgUrl ͼƬ��ַ
	 */
	public String getImgUrl(){
    	return imgUrl;
    }
	/**
	 * 
	 * @param imgUrl ͼƬ��ַ
	 */
	public void setImgUrl(String imgUrl){
    	this.imgUrl = imgUrl;
    }
	/**
	 * 
	 * @param brief ��Ʒ��Ҫ����
	 */
	public void setBrief(String brief){
		this.brief = brief;
	}
	/**
	 * 
	 * @return brief ��Ʒ��Ҫ����
	 */
	public String getBrief(){
		return brief;
	}
	/**
	 * 
	 * @param scores �������
	 */
	public void setScores(float scores){
		this.scores = scores;
	}
	/**
	 * 
	 * @return scores �������
	 */
	public float getScores(){
		return scores;
	}
}
