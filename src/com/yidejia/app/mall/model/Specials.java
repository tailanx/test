package com.yidejia.app.mall.model;
/**
 * ����ͣ����ֻ�����Ʒ.������ƷId����Ʒ����������۸�������֣�ͼƬ��ַ
 * @author long bin
 *
 */
public class Specials {
	private String uId;            //��ƷId
	private String brief;       //��Ʒ����
	private String price;        //����۸�
	private String scores;       //�������
	private String imgUrl;      //ͼƬ��ַ
	
	/**
	 * 
	 * @param uId ������ƷId
	 */
	public void setUId(String uId){
		this.uId = uId;
	}
	/**
	 * 
	 * @return uId ������ƷId
	 */
	public String getUId(){
		return uId;
	}
	/**
	 * 
	 * @param price ����۸�
	 */
	public void setPrice(String price){
		this.price = price;
	}
	/**
	 * 
	 * @return price ����۸�
	 */
	public String getPrice(){
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
	public void setScores(String scores){
		this.scores = scores;
	}
	/**
	 * 
	 * @return scores �������
	 */
	public String getScores(){
		return scores;
	}
}
