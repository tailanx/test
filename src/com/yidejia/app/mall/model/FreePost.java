package com.yidejia.app.mall.model;
/**
 * ������Ϣ
 * @author long bin 
 *
 */
public class FreePost {
	private String id;
	private String name;//����
	private String startDate;//��ʼʱ��
	private String endDate;//����ʱ��
	private String max;//���ʽ���
	private String desc;//����
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return id;
	}
	/**
	 * 
	 * @param name ����
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 
	 * @return ����
	 */
	public String getName(){
		return name;
	}
	/**
	 * 
	 * @param startDate ��ʼʱ��
	 */
	public void setStartDate(String startDate){
		this.startDate = startDate;
	}
	/**
	 * 
	 * @return ��ʼʱ��
	 */
	public String getStartDate(){
		return startDate;
	}
	/**
	 * 
	 * @param endDate ����ʱ��
	 */
	public void setEndDate(String endDate){
		this.endDate = endDate;
	}
	/**
	 * 
	 * @return ����ʱ��
	 */
	public String getEndDate(){
		return endDate;
	}
	/**
	 * 
	 * @param max ���ʽ���
	 */
	public void setMax(String max){
		this.max = max;
	}
	/**
	 * 
	 * @return ���ʽ���
	 */
	public String getMax(){
		return max;
	}
	/**
	 * 
	 * @param desc ����
	 */
	public void setDesc(String desc){
		this.desc = desc;
	}
	/**
	 * 
	 * @return ����
	 */
	public String getDesc(){
		return desc;
	}
}
