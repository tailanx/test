package com.yidejia.app.mall.model;
/**
 * 免邮信息
 * @author long bin 
 *
 */
public class FreePost {
	private String id;
	private String name;//名称
	private String startDate;//开始时间
	private String endDate;//结束时间
	private String max;//免邮界限
	private String desc;//描述
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return id;
	}
	/**
	 * 
	 * @param name 名称
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 
	 * @return 名称
	 */
	public String getName(){
		return name;
	}
	/**
	 * 
	 * @param startDate 开始时间
	 */
	public void setStartDate(String startDate){
		this.startDate = startDate;
	}
	/**
	 * 
	 * @return 开始时间
	 */
	public String getStartDate(){
		return startDate;
	}
	/**
	 * 
	 * @param endDate 结束时间
	 */
	public void setEndDate(String endDate){
		this.endDate = endDate;
	}
	/**
	 * 
	 * @return 结束时间
	 */
	public String getEndDate(){
		return endDate;
	}
	/**
	 * 
	 * @param max 免邮界限
	 */
	public void setMax(String max){
		this.max = max;
	}
	/**
	 * 
	 * @return 免邮界限
	 */
	public String getMax(){
		return max;
	}
	/**
	 * 
	 * @param desc 描述
	 */
	public void setDesc(String desc){
		this.desc = desc;
	}
	/**
	 * 
	 * @return 描述
	 */
	public String getDesc(){
		return desc;
	}
}