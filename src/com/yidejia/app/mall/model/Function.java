package com.yidejia.app.mall.model;
/**
 * 商品功效，包括 id，名称
 * @author long bin
 *
 */
public class Function {
	private String funId;            //商品功效Id 
    private String funName;   //功效名称
    private String desc; 	//描述
    
    public String getFunName(){
    	return funName;
    }

    public void setFunName(String funName){
    	this.funName = funName;
    }
    
    public String getFunId(){
    	return funId;
    }
    
    public void setFunId(String funId){
    	this.funId = funId;
    }
    
    public String getDesc(){
    	return desc;
    }
    
    public void setDesc(String desc){
    	this.desc = desc;
    }
    
    // 获取功效列表
//    vector<S_Fun> GetFunArray();
}
