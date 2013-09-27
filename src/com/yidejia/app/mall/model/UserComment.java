package com.yidejia.app.mall.model;
/**
 * 用户评论信息,包括id,昵称，vip等级，客户小头像链接地址，客户评论，
 * 这个客户购买产品的具体，客户评分，评论时间
 * @author long bin
 *
 */
public class UserComment {
	private String userId;                     //客户ID （未来可以点击用户头像，跳转到查看客户相关信息）
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
	 * @return vipLevel vip等级
	 */
	public String getVipLevel(){
		return vipLevel;
	}
	private String userPictureUrl;          //客户小头像链接地址
	/**
	 * 
	 * @param userPictureUrl 客户小头像链接地址
	 */
	public void setUserPictureUrl(String userPictureUrl){
		this.userPictureUrl = userPictureUrl;
	}
	/**
	 * 
	 * @return userPictureUrl 客户小头像链接地址
	 */
	public String getUserPictureUrl(){
		return userPictureUrl;
	}
	private String userCommentText;         //客户评论 （《＝1024字节？）
	/**
	 * 
	 * @param userCommentText 客户评论
	 */
	public void setUserCommentText(String userCommentText){
		this.userCommentText = userCommentText;
	}
	/**
	 * 
	 * @return userCommentText 客户评论
	 */
	public String getUserCommentText(){
		return userCommentText;
	}
	
	private String productBaseDetail;       //这个客户购买产品的具体（非衣物类，是否有颜色，大小之分？）
	/**
	 * 
	 * @param productBaseDetail 这个客户购买产品的具体
	 */
	public void setProductBaseDetail(String productBaseDetail){
		this.productBaseDetail = productBaseDetail;
	}
	/**
	 * 
	 * @return productBaseDetail 这个客户购买产品的具体
	 */
	public String getProductBaseDetail(){
		return productBaseDetail;
	}
	private int rate;                       //客户评分
	/**
	 * 
	 * @param rate 客户评分
	 */
	public void setRate(int rate){
		this.rate = rate;
	}
	/**
	 * 
	 * @return rate 客户评分
	 */
	public int getRate(){
		return rate;
	}
	private String commentTime;             //评论时间
	/**
	 * 
	 * @param commentTime 评论时间
	 */
	public void setCommentTime(String commentTime){
		this.commentTime = commentTime;
	}
	/**
	 * 
	 * @return commentTime 评论时间
	 */
	public String getCommentTime(){
		return commentTime;
	}
	
	private String goodsId;//商品id
	public void setGoodsId(String goodsId){
		this.goodsId = goodsId;
	}
	/**
	 * 
	 * @return goods id 商品id
	 */
	public String getGoodsId(){
		return goodsId;
	}
	
	private String title;//评论标题
	public void setTitle(String title){
		this.title = title;
	}
	/**
	 * 
	 * @return title 评论标题
	 */
	public String getTitle(){
		return title;
	}
	
	private String id;//评论id
	public void setId(String id){
		this.id = id;
	}
	/**
	 * 
	 * @return id 评论的主键id
	 */
	public String getId(){
		return id;
	}
}
