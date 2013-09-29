package com.yidejia.app.mall.model;

import java.util.ArrayList;

/**
 * ����ģ�飬���������ţ�����״̬���Ѹ����ȷ�ϵȣ�����������Ʒ��{@link Cart} ��ο����ﳵģ�飩,�����ܶ�,������Ʒ������
 * @author long bin
 *
 */
public class Order {
	private String orderId ; //����id
	private String orderCode;           //������
	private String status;              //����״̬���Ѹ����ȷ�ϵȣ�
    private ArrayList<Cart> cartsArray;  //��������Ʒ��S_Cart ��ο����ﳵģ�飩
    private float orderSummary;         //�����ܶ�
    private int orderAmount;            //������Ʒ������
    private String ship_fee;	//��ݷ�
    private String core;//����
    private String date;// ʱ��
    /**
     * ������
     * @param orderCode ������
     */
    public void setOrderCode(String orderCode){
    	this.orderCode = orderCode;
    }
    /**
     * 
     * @return orderCode ������
     */
    public String getOrderCode(){
    	return orderCode;
    }
    /**
     * ����״̬���Ѹ����ȷ�ϵȣ�
     * @param starus ����״̬���Ѹ����ȷ�ϵȣ�
     */
    public void setStatus(String starus){
    	this.status = starus;
    }
    /**
     * 
     * @return status ����״̬���Ѹ����ȷ�ϵȣ�
     */
    public String getStatus(){
    	return status;
    }
    /**
     * 
     * @param cartsArray ArrayList<{@link Cart}> ��������Ʒ��Cart ��ο����ﳵģ�飩
     */
    public void setCartsArray(ArrayList<Cart> cartsArray){
    	this.cartsArray = cartsArray;
    }
    /**
     * 
     * @return cartsArray ArrayList<{@link Cart}> ��������Ʒ��Cart ��ο����ﳵģ�飩
     */
    public ArrayList<Cart> getCartsArray(){
    	return cartsArray;
    }
    /**
     * 
     * @param orderSummary �����ܶ�
     */
    public void setOrderSummary(float orderSummary){
    	this.orderSummary = orderSummary;
    }
    /**
     * 
     * @return orderSummary �����ܶ�
     */
    public float getOrderSummary(){
    	return orderSummary;
    }
    /**
     * 
     * @param orderAmount ��������
     */
    public void setOrderAmount(int orderAmount){
    	this.orderAmount = orderAmount;
    }
    /**
     * 
     * @return orderAmount ��������
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
    
    
    /*��ȡ�����б�

    userId: �ͻ�Id
     the_day:  1.��һ�ܣ�   2.��һ�£�  3.��һ��
     status:  ������¼�루��������Ѹ�������������ѷ���������ɣ������ڿ�ʱ�����ǡ�ȫ������������˼����
     
     return: �����б�
     eg.:  http://u.atido.com/XXX/?the_date=1&status��¼��
     */
//    vector<S_Order> GetOrderArray(int userId��int the_day, const char* status);
}
