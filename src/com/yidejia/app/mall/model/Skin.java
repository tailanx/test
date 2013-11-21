package com.yidejia.app.mall.model;

import java.util.ArrayList;

public class Skin {
	private String name;
	private String question;
	private ArrayList<SkinQOption> options;
	private boolean need;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public ArrayList<SkinQOption> getOptions() {
		return options;
	}
	public void setOptions(ArrayList<SkinQOption> options) {
		this.options = options;
	}
	public boolean isNeed() {
		return need;
	}
	public void setNeed(boolean need) {
		this.need = need;
	}
	
}
