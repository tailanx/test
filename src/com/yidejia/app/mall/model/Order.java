package com.yidejia.app.mall.model;

import java.util.ArrayList;

/**
 * 订单模块，包括订单号，订单状态（已付款，待确认等），订单内商品（{@link Cart} 请参考购物车模块）,订单总额,订单商品总数量
 * @author long bin
 *
 */
public class Order {
	private String orderId ; //订单id
	private String orderCode;           //订单号
	private String status;              //订单状态（已付款，待确认等）
    private ArrayList<Cart> cartsArray;  //订单内商品（S_Cart 请参考购物车模块）
    private float orderSummary;         //订单总额
    private int orderAmount;            //订单商品总数量
    private String ship_fee;	//快递费
    private String core;//积分
    private String date;// 时间
    /**
     * 订单号
     * @param orderCode 订单号
     */
    public void setOrderCode(String orderCode){
    	this.orderCode = orderCode;
    }
    /**
     * 
     * @return orderCode 订单号
     */
    public String getOrderCode(){
    	return orderCode;
    }
    /**
     * 订单状态（已付款，待确认等）
     * @param starus 订单状态（已付款，待确认等）
     */
    public void setStatus(String starus){
    	this.status = starus;
    }
    /**
     * 
     * @return status 订单状态（已付款，待确认等）
     */
    public String getStatus(){
    	return status;
    }
    /**
     * 
     * @param cartsArray ArrayList<{@link Cart}> 订单内商品（Cart 请参考购物车模块）
     */
    public void setCartsArray(ArrayList<Cart> cartsArray){
    	this.cartsArray = cartsArray;
    }
    /**
     * 
     * @return cartsArray ArrayList<{@link Cart}> 订单内商品（Cart 请参考购物车模块）
     */
    public ArrayList<Cart> getCartsArray(){
    	return cartsArray;
    }
    /**
     * 
     * @param orderSummary 订单总额
     */
    public void setOrderSummary(float orderSummary){
    	this.orderSummary = orderSummary;
    }
    /**
     * 
     * @return orderSummary 订单总额
     */
    public float getOrderSummary(){
    	return orderSummary;
    }
    /**
     * 
     * @param orderAmount 订单数量
     */
    public void setOrderAmount(int orderAmount){
    	this.orderAmount = orderAmount;
    }
    /**
     * 
     * @return orderAmount 订单数量
     */
    public int getOrderAmount(){
    	return orderAmount;
    }
    
    public void setId(String orderId){
    	this.orderId = orderId;
    }
    
    public String getId(){
    	return orderId;
    }
    
    public void setDate(String date){
    	this.date = date;
    }
    
    public String getDate(){
    	return date;
    }
    
    public void setCore(String core){
    	this.core = core;
    }
    
    public String getCore(){
    	return core;
    }
    
    public void setShipFee(String ship_fee){
    	this.ship_fee = ship_fee;
    }
    
    public String getShipFee(){
    	return ship_fee;
    }
    
    
    /*获取订单列表

    userId: 客户Id
     the_day:  1.近一周；   2.近一月；  3.近一年
     status:  包括（录入（待付款），已付款（待发货），已发货和已完成，，等于空时，就是“全部订单“的意思）；
     
     return: 订单列表
     eg.:  http://u.atido.com/XXX/?the_date=1&status＝录入
     */
//    vector<S_Order> GetOrderArray(int userId，int the_day, const char* status);
}
