package com.yidejia.app.mall.model;
/**
 * ����ģ������ͽӿ� 
 * <p>
 * ������uId, price, selledAmount, commentAmount, brief, imgUrl, name , favoriteId
 * <p>
 * @author long bin
 *
 */
public class SearchItem {
	private String uId;                //��ƷId
	private String name; 			//��Ʒ����
	private String price;            //�۸�
	private String selledAmount;       //��������
	private String commentAmount;      //��������
	private String brief;           //��Ʒ��Ҫ���� ������256�ֽڣ�
	private String imgUrl;          //СͼƬ���ӵ�ַ
	private String favoriteId;		//�ղ�id �������ղأ���Ʒ�ǿ�ɺ����������
	private String the_code;   		//��Ʒ���
	private String module;			//��Ʒ��Ч
	private String brand;			//Ʒ��
	private String spec;			//���
	private String desc;			//����
	
	
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
	public void setSelledAmount(String selledAmount){
		this.selledAmount = selledAmount;
	}
	/**
	 * 
	 * @return  salledAmount ����������
	 */
	public String getSelledAmount(){
		return selledAmount;
	}
	/**
	 * 
	 * @param commentAmmount ��������
	 */
	public void setCommentAmount(String commentAmount){
		this.commentAmount = commentAmount;
	}
	/**
	 * @return commentAmmount ��������
	 */
	public String getCommentAmount(){
		return commentAmount;
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
	 * ��Ʒ����
	 * @param name
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 * ��Ʒ����
	 * @return
	 */
	public String getName(){
		return name;
	}
	/**
	 * �ղ�id �������ղأ���Ʒ�ǿ�ɺ����������
	 * @param favoriteId
	 */
	public void setFavoriteId(String favoriteId){
		this.favoriteId = favoriteId;
	}
	/**
	 * �ղ�id �������ղأ���Ʒ�ǿ�ɺ����������
	 * @return favoriteId
	 */
	public String getFavoriteId(){
		return favoriteId;
	}
	
//	vector<S_SearchItem> GetSearchArray(const char* order, const char* name, const char* brand, const char* fun, const char* price);
	
	/**
	 * 
	 * @param the_code ��Ʒ���
	 */
	public void setTheCode(String the_code){
		this.the_code = the_code;
	}
	/**
	 * 
	 * @return ��Ʒ���
	 */
	public String getTheCode(){
		return the_code;
	}
	/**
	 * 
	 * @param module ��Ʒ��Ч
	 */
	public void setModule(String module){
		this.module = module;
	}
	/**
	 * 
	 * @return ��Ʒ��Ч
	 */
	public String getModule(){
		return module;
	}
	/**
	 * 
	 * @param brand Ʒ��
	 */
	public void setBrand(String brand){
		this.brand = brand;
	}
	/**
	 * 
	 * @return Ʒ��
	 */
	public String getBrand(){
		return brand;
	}
	/**
	 * 
	 * @param spec ���
	 */
	public void setSpec(String spec){
		this.spec = spec;
	}
	/**
	 * 
	 * @return ���
	 */
	public String getSpec(){
		return spec;
	}
	/**
	 * 
	 * @param desc ����
	 */
	public void setDesc(String desc){
		this.desc = desc;
	}
	/**
	 * 
	 * @return ����
	 */
	public String getDesc(){
		return desc;
	}
}

