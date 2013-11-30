package com.yidejia.app.mall.model;

import java.util.ArrayList;
import java.util.List;

public class Skin {
	
	private String name;
	private String question;
	private ArrayList<SkinQOption> options;
	private boolean need;
	private boolean is_multiple;
	
////	
//	/**�û�ѡ��Ĵ�*/
//	private List<Integer> userAnswers=new ArrayList<Integer>();
//	
//	public List<Integer> getUserAnswers() {
//		return userAnswers;
//	}
//	
//	
//	public void setUserAnswers(List<Integer> userAnswers) {
//		this.userAnswers = userAnswers;
//	}
//	
//	
//	public void saveUserAnswers(List<Integer> userAns){
//		this.userAnswers.clear();
//		this.userAnswers.addAll(userAns);
//	}

	
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
	public boolean isIs_multiple() {
		return is_multiple;
	}
	public void setIs_multiple(boolean is_multiple) {
		this.is_multiple = is_multiple;
	}
	
}
