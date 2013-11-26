package com.yidejia.app.mall.model;

import java.io.Serializable;

public class SkinAnswer implements Serializable{

	@Override
	public String toString() {
		return "SkinAnswer [desc=" + desc + ", suggest=" + suggest + "]";
	}

	private String desc;
	private String suggest;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}

}
