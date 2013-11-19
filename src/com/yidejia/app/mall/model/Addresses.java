package com.yidejia.app.mall.model;

import java.io.Serializable;

/**
 * 
 * @author long bin
 *
 */
public class Addresses  implements Serializable{
	
	private String addressId;          //地址id
	private String name;            //收货人姓名
	private String province;        //省份
	private String city;	//城市
	private String area;	//区
	private String address; //详细地址
	private String phone;	//电话
	private String handset;	//手机
	private boolean defaultAddress;  //默认地址
	
	private String recipient_id;//收件人id
	
	public void setAddressId(String addressId){
		this.addressId = addressId;
	}
	
	public String getAddressId(){
		return addressId;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setProvince(String province){
		this.province = province;
	}
	
	public String getProvice(){
		return province;
	}
	
	public void setCity(String city){
		this.city = city;
	}
	
	public String getCity(){
		return city;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public String getArea(){
		return area;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public String getAddress(){
		return address;
	}
	
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	public String getPhone(){
		return phone;
	}
	
	public void setDefaultAddress(boolean defaultAddress){
		this.defaultAddress = defaultAddress;
	}
	
	public boolean getDefaultAddress(){
		return defaultAddress;
	}
	
	public String getHandset(){
		return handset;
	}
	
	public void setHandset(String handset){
		this.handset = handset;
	}
	
	public void setRecipientId(String recipient_id){
		this.recipient_id = recipient_id;
	}
	public String getRecipientId(){
		return recipient_id;
	}
}
