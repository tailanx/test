package com.yidejia.app.mall.model;
/**
 * ��Ʒ��Ч������ id������
 * @author long bin
 *
 */
public class Function {
	private String funId;            //��Ʒ��ЧId 
    private String funName;   //��Ч����
    private String desc; 	//����
    
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
    
    // ��ȡ��Ч�б�
//    vector<S_Fun> GetFunArray();
}
