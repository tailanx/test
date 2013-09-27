package com.yidejia.app.mall.model;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * ��Ʒ����ҳ�Ļ�����Ϣ , ���� uid,bannerArray,name,salledAmount,price,commentAmount
 * ,showListAmount,brands,bannerArray,productSpecifications,productNumber,recommendArray
 * @author long bin
 *
 */
public class ProductBaseInfo implements Serializable{
	private String uId;                            //product id
	private String name;						//��Ʒ����
	private String brief;                       //��Ʒ��Ҫ������<��256�ֽڣ�
	private String price;                        //�۸�
	private String salledAmount;                  //����������
	private String showListAmount;                //ɹ������
	private String commentAmount;                 //��������
	private String productDetailUrl;            //��Ʒ����ҳ������
	private ArrayList<MainProduct> recommendArray;    //�Ƽ����� ��<��10����
	private String productSpecifications;       //���
	private String productNumber;               //��Ʒ���
	private String brands;                      //Ʒ��
	private ArrayList<BaseProduct> bannerArray;  //�ֲ���Ʒ���������̶���<=5?)
	private String imgUrl; // Сͼ��ַ
	
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
	 * @param name ��Ʒ����
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 
	 * @return ��Ʒ����
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 
	 * @param bannerArray ���� �ֲ���Ʒ���������̶���<=5?)
	 */
	public void setBannerArray(ArrayList<BaseProduct> bannerArray){
		this.bannerArray = bannerArray;
	}
	/**
	 * 
	 * @return bannerArray �ֲ���Ʒ
	 */
	public ArrayList<BaseProduct> getBannerArray(){
		return bannerArray;
	}
	
	/**
	 * 
	 * @param brief ��Ʒ��Ҫ����������256�ֽڣ�
	 */
	public void setBrief(String brief){
		this.brief = brief;
	}
	/**
	 * 
	 * @return brief ��Ʒ��Ҫ����������256�ֽڣ�
	 */
	public String getBrief(){
		return brief;
	}
	/**
	 * 
	 * @param price �۸�
	 */
	public void setPrice(String price){
		this.price = price;
	}
	/**
	 * 
	 * @return price �۸�
	 */
	public String getPrice(){
		return price;
	}
	/**
	 * 
	 * @param salledAmount ����������
	 */
	public void setSalledAmount(String salledAmount){
		this.salledAmount = salledAmount;
	}
	/**
	 * 
	 * @return  salledAmmount ����������
	 */
	public String getSalledAmmount(){
		return salledAmount;
	}
	
	/**
	 * 
	 * @param commentAmount ��������
	 */
	public void setCommentAmount(String commentAmount){
		this.commentAmount = commentAmount;
	}
	/**
	 * @return commentAmount ��������
	 */
	public String getCommentAmount(){
		return commentAmount;
	}
	
	/**
	 * 
	 * @param showListAmount ɹ������
	 */
	public void setShowListAmount(String showListAmount){
		this.showListAmount = showListAmount;
	}
	/**
	 * 
	 * @return showListAmmount ɹ������
	 */
	public String getShowListAmount(){
		return showListAmount;
	}
	/**
	 * 
	 * @param brands Ʒ��
	 */
	public void setBrands(String brands){
		this.brands = brands;
	}
	/**
	 * 
	 * @return brands Ʒ��
	 */
	public String getBrands(){
		return brands;
	}
	/**
	 * 
	 * @param productNumber ��Ʒ���
	 */
	public void setProductNumber(String productNumber){
		this.productNumber = productNumber;
	}
	/**
	 * 
	 * @return productNumber ��Ʒ���
	 */
	public String getProductNumber(){
		return productNumber;
	}
	/**
	 * 
	 * @param productSpecifications ���
	 */
	public void setProductSpecifications(String productSpecifications){
		this.productSpecifications = productSpecifications;
	}
	/**
	 * 
	 * @return productSpecifications ���
	 */
	public String getProductSpecifications(){
		return productSpecifications;
	}
	/**
	 * 
	 * @param recommendArray �Ƽ�����
	 */
	public void setRecommendArray(ArrayList<MainProduct> recommendArray){
		this.recommendArray = recommendArray;
	}
	/**
	 * 
	 * @return recommendArray �Ƽ�����
	 */
	public ArrayList<MainProduct> getRecommendArray(){
		return recommendArray;
	}
	/**
	 * 
	 * @param productDetailUrl ��Ʒ����ҳ������
	 */
	public void setProductDetailUrl(String productDetailUrl){
		this.productDetailUrl = productDetailUrl;
	}
	/**
	 * 
	 * @return productDetailUrl ��Ʒ����ҳ������
	 */
	public String getProductDetailUrl(){
		return productDetailUrl;
	}
	/**
	 * 
	 * @param imgUrl Сͼ��ַ
	 */
	public void setImgUrl(String imgUrl){
		this.imgUrl = imgUrl;
	}
	/**
	 * 
	 * @return Сͼ��ַ
	 */
	public String getImgUrl(){
		return imgUrl;
	}
}
