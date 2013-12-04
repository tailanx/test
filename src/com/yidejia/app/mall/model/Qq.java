package com.yidejia.app.mall.model;

import java.io.Serializable;

public class Qq implements Serializable {

	public String getQqName() {
		return qqName;
	}

	public void setQqName(String qqName) {
		this.qqName = qqName;
	}

	public String getQuenName() {
		return quenName;
	}

	public void setQuenName(String quenName) {
		this.quenName = quenName;
	}
	private String quenName;
	private String qqName;
}
