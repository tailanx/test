package com.yidejia.app.mall.model;
/**
 * �û�������Ϣ,����id,�ǳƣ�vip�ȼ����ͻ�Сͷ�����ӵ�ַ���ͻ����ۣ�
 * ����ͻ������Ʒ�ľ��壬�ͻ����֣�����ʱ��
 * @author long bin
 *
 */
public class UserComment {
	private String userId;                     //�ͻ�ID ��δ�����Ե���û�ͷ����ת���鿴�ͻ������Ϣ��
	public void setUserId(String usetId){
		this.userId = usetId;
	}
	public String getUserId(){
		return userId;
	}
	
	private String userName;                //
	public void setUserName(String userName){
		this.userName = userName;
	}
	public String getUserName(){
		return userName;
	}
	private String vipLevel;                //
	public void setVipLevel(String vipLevel){
		this.vipLevel = vipLevel;
	}
	/**
	 * 
	 * @return vipLevel vip�ȼ�
	 */
	public String getVipLevel(){
		return vipLevel;
	}
	private String userPictureUrl;          //�ͻ�Сͷ�����ӵ�ַ
	/**
	 * 
	 * @param userPictureUrl �ͻ�Сͷ�����ӵ�ַ
	 */
	public void setUserPictureUrl(String userPictureUrl){
		this.userPictureUrl = userPictureUrl;
	}
	/**
	 * 
	 * @return userPictureUrl �ͻ�Сͷ�����ӵ�ַ
	 */
	public String getUserPictureUrl(){
		return userPictureUrl;
	}
	private String userCommentText;         //�ͻ����� ������1024�ֽڣ���
	/**
	 * 
	 * @param userCommentText �ͻ�����
	 */
	public void setUserCommentText(String userCommentText){
		this.userCommentText = userCommentText;
	}
	/**
	 * 
	 * @return userCommentText �ͻ�����
	 */
	public String getUserCommentText(){
		return userCommentText;
	}
	
	private String productBaseDetail;       //����ͻ������Ʒ�ľ��壨�������࣬�Ƿ�����ɫ����С֮�֣���
	/**
	 * 
	 * @param productBaseDetail ����ͻ������Ʒ�ľ���
	 */
	public void setProductBaseDetail(String productBaseDetail){
		this.productBaseDetail = productBaseDetail;
	}
	/**
	 * 
	 * @return productBaseDetail ����ͻ������Ʒ�ľ���
	 */
	public String getProductBaseDetail(){
		return productBaseDetail;
	}
	private int rate;                       //�ͻ�����
	/**
	 * 
	 * @param rate �ͻ�����
	 */
	public void setRate(int rate){
		this.rate = rate;
	}
	/**
	 * 
	 * @return rate �ͻ�����
	 */
	public int getRate(){
		return rate;
	}
	private String commentTime;             //����ʱ��
	/**
	 * 
	 * @param commentTime ����ʱ��
	 */
	public void setCommentTime(String commentTime){
		this.commentTime = commentTime;
	}
	/**
	 * 
	 * @return commentTime ����ʱ��
	 */
	public String getCommentTime(){
		return commentTime;
	}
	
	private String goodsId;//��Ʒid
	public void setGoodsId(String goodsId){
		this.goodsId = goodsId;
	}
	/**
	 * 
	 * @return goods id ��Ʒid
	 */
	public String getGoodsId(){
		return goodsId;
	}
	
	private String title;//���۱���
	public void setTitle(String title){
		this.title = title;
	}
	/**
	 * 
	 * @return title ���۱���
	 */
	public String getTitle(){
		return title;
	}
	
	private String id;//����id
	public void setId(String id){
		this.id = id;
	}
	/**
	 * 
	 * @return id ���۵�����id
	 */
	public String getId(){
		return id;
	}
}
