package com.yidejia.app.mall.model;

/**
 * 退换货订单
 * @author long bin
 *
 */
public class RetOrderInfo {
	
	private String id;//退换货的id
	private String orderCode;//订单号
	private String theDate;//退换订单时间
	
	private String contact;//联系人
	private String contact_manner;//联系方式
	
	private String status;//订单状态
	private String cause;//退换货原因
	private String desc;//原因描述
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return id;
	}
	
	/**
	 * 订单号
	 * @param orderCode
	 */
	public void setOrderCode(String orderCode){
		this.orderCode = orderCode;
	}
	/**
	 * 
	 * @return 订单号
	 */
	public String getOrderCode(){
		return orderCode;
	}
	/**
	 * 退换订单时间
	 * @param theDate
	 */
	public void setTheDate(String theDate){
		this.theDate = theDate;
	}
	/**
	 * 
	 * @return 退换订单时间
	 */
	public String getTheDate(){
		return theDate;
	}
	/**
	 * 联系人
	 * @param orderCode
	 */
	public void setContact(String contact){
		this.contact = contact;
	}
	/**
	 * 
	 * @return 联系人
	 */
	public String getContact(){
		return contact;
	}
	/**
	 * 联系方式
	 * @param orderCode
	 */
	public void setContact_manner(String contact_manner){
		this.contact_manner = contact_manner;
	}
	/**
	 * 
	 * @return 联系方式
	 */
	public String getContact_manner(){
		return contact_manner;
	}
	/**
	 * 订单状态
	 * @param orderCode
	 */
	public void setStatus(String status){
		this.status = status;
	}
	/**
	 * 
	 * @return 订单状态
	 */
	public String getStatus(){
		return status;
	}
	/**
	 * 退换货原因
	 * @param orderCode
	 */
	public void setCause(String cause){
		this.cause = cause;
	}
	/**
	 * 
	 * @return 退换货原因
	 */
	public String getCause(){
		return cause;
	}
	/**
	 * 原因描述
	 * @param orderCode
	 */
	public void setDesc(String desc){
		this.desc = desc;
	}
	/**
	 * 
	 * @return 原因描述
	 */
	public String getDesc(){
		return desc;
	}
}
