package com.yidejia.app.mall.model;
/**
 * ���,��������
 * @author long bin
 *
 */
public class Express {
	private String ems; //ems���
	private String express; //���
	private String disName;//��������
	private String disDesc;//�������Ľ���
	/**
	 * 
	 * @param ems ems�۸�
	 */
	public void setEms(String ems){
		this.ems = ems;
	}
	/**
	 * 
	 * @return ems�۸�
	 */
	public String getEms(){
		return ems;
	}
	/**
	 * 
	 * @param express ��ݷ���
	 */
	public void setExpress(String express){
		this.express = express;
	}
	/**
	 * 
	 * @return ��ݷ���
	 */
	public String getExpress(){
		return express;
	}
	/**
	 * 
	 * @param disName ��������
	 */
	public void setDisName(String disName ){
		this.disName = disName;
	}
	/**
	 * 
	 * @return ��������
	 */
	public String getDisName(){
		return disName;
	}
	/**
	 *  
	 * @param disDesc �������Ľ���
	 */
	public void setDisDesc(String disDesc){
		this.disDesc = disDesc;
	}
	/**
	 * 
	 * @return �������Ľ���
	 */
	public String getDisDesc(){
		return disDesc;
	}
}
