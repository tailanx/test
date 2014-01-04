package com.yidejia.app.mall.model;

import java.util.ArrayList;

/**
 * 订单优惠，包括免费送商品，积分换购商品
 * @author long bin
 * 
 */
public class Preferential {
	private ArrayList<Specials> freeProductArray;        //免费送商品
	private ArrayList<Specials> scoresProductArray;      //积分换购商品
	/**
	 * 
	 * @param freeProductArray ArrayList<{@link Specials}>免费送商品
	 */
	public void setFreeProductArray(ArrayList<Specials> freeProductArray){
		this.freeProductArray = freeProductArray;
	}
	/**
	 * 
	 * @return freeProductArray ArrayList<{@link Specials}>免费送商品
	 */
	public ArrayList<Specials> getFreeProductArray(){
		return freeProductArray;
	}
	/**
	 * 
	 * @param scoresProductArray ArrayList<{@link Specials}>积分换购商品
	 */
	public void setScoresProductArray(ArrayList<Specials> scoresProductArray){
		this.scoresProductArray = scoresProductArray;
	}
	/**
	 * 
	 * @return scoresProductArray ArrayList<{@link Specials}>积分换购商品
	 */
	public ArrayList<Specials> getScoresProductArray(){
		return scoresProductArray;
	}
	
}
