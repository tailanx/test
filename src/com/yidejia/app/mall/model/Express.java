package com.yidejia.app.mall.model;

/**
 * 快递,配送中心
 * 
 * @author long bin
 * 
 */
public class Express {
	private String ems; // ems快递
	private String express; // 快递
	private String disName;// 配送中心
	private String disDesc;// 配送中心介绍

	/**
	 * 
	 * @param ems
	 *            ems价格
	 */
	public void setEms(String ems) {
		this.ems = ems;
	}

	/**
	 * 
	 * @return ems价格
	 */
	public String getEms() {
		return ems;
	}

	/**
	 * 
	 * @param express
	 *            快递费用
	 */
	public void setExpress(String express) {
		this.express = express;
	}

	/**
	 * 
	 * @return 快递费用
	 */
	public String getExpress() {
		return express;
	}

	/**
	 * 
	 * @param disName
	 *            配送中心
	 */
	public void setDisName(String disName) {
		this.disName = disName;
	}

	/**
	 * 
	 * @return 配送中心
	 */
	public String getDisName() {
		return disName;
	}

	/**
	 * 
	 * @param disDesc
	 *            配送中心介绍
	 */
	public void setDisDesc(String disDesc) {
		this.disDesc = disDesc;
	}

	/**
	 * 
	 * @return 配送中心介绍
	 */
	public String getDisDesc() {
		return disDesc;
	}

	private boolean isDefault;// 是否为默认
	private String pre_id;// 配送中心id

	public String getPreId() {
		return pre_id;
	}

	public void setPreId(String pre_id) {
		this.pre_id = pre_id;
	}

	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public boolean getIsDefault() {
		return isDefault;
	}
}
