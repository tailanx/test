package com.yidejia.app.mall.model;

import java.util.ArrayList;

/**
 * �����Żݣ������������Ʒ�����ֻ�����Ʒ
 * @author long bin
 * 
 */
public class Preferential {
	private ArrayList<Specials> freeProductArray;        //�������Ʒ
	private ArrayList<Specials> scoresProductArray;      //���ֻ�����Ʒ
	/**
	 * 
	 * @param freeProductArray ArrayList<{@link Specials}>�������Ʒ
	 */
	public void setFreeProductArray(ArrayList<Specials> freeProductArray){
		this.freeProductArray = freeProductArray;
	}
	/**
	 * 
	 * @return freeProductArray ArrayList<{@link Specials}>�������Ʒ
	 */
	public ArrayList<Specials> getFreeProductArray(){
		return freeProductArray;
	}
	/**
	 * 
	 * @param scoresProductArray ArrayList<{@link Specials}>���ֻ�����Ʒ
	 */
	public void setScoresProductArray(ArrayList<Specials> scoresProductArray){
		this.scoresProductArray = scoresProductArray;
	}
	/**
	 * 
	 * @return scoresProductArray ArrayList<{@link Specials}>���ֻ�����Ʒ
	 */
	public ArrayList<Specials> getScoresProductArray(){
		return scoresProductArray;
	}
	
}
