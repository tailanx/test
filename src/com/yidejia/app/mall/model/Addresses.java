package com.yidejia.app.mall.model;

import java.io.Serializable;

/**
 * 
 * @author long bin
 *
 */
public class Addresses  implements Serializable{
	
	private String addressId;          //��ַid
	private String name;            //�ջ�������
	private String province;        //ʡ��
	private String city;	//����
	private String area;	//��
	private String address; //��ϸ��ַ
	private String phone;	//�绰
	private String handset;	//�ֻ�
	private boolean defaultAddress;  //Ĭ�ϵ�ַ
	
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
}
