package com.yidejia.app.mall.widget;

import com.yidejia.app.mall.R;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class YLProgressDialog {
	
	private static ProgressDialog loadingDialog;
	private Context context;
	private LinearLayout layout;
	private TextView tipTextView;
	private String msg;
	
	public YLProgressDialog(){
		
	}
	
	public YLProgressDialog(Context context) {
//		super(context);
		this.context = context;
		this.msg = context.getResources().getString(R.string.loading);
		setLayout();
	}
	
	public YLProgressDialog(Context context, int theme){
//		super(context, theme);
		this.context = context;
	}
	
	public void show(){
		loadingDialog.show();
//      loadingDialog.setCancelable(false);// 不可以用“返回键”取消  
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(  
              LinearLayout.LayoutParams.MATCH_PARENT,  
              LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局  
	}
	
//	private 

	/** 
     * 得到自定义的progressDialog 
     * @param context 
     * @param msg 
     * @return 
     */  
    public static ProgressDialog createLoadingDialog(Context context, String msg) {  
        
    	LayoutInflater inflater = LayoutInflater.from(context);  
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view  
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局  
        // main.xml中的ImageView  
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);  
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字  
        // 加载动画  
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(  
                context, R.anim.loading_animation);  
        // 使用ImageView显示动画  
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);  
        if(null != msg)tipTextView.setText(msg);// 设置加载信息  

        loadingDialog = new ProgressDialog(context, R.style.StyleProgressDialog);// 创建自定义样式dialog  
        loadingDialog.show();
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(  
                LinearLayout.LayoutParams.MATCH_PARENT,  
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局  
        return loadingDialog;  

    } 
    
    private void setLayout(){
    	LayoutInflater inflater = LayoutInflater.from(context);  
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view  
        layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局  
        // main.xml中的ImageView  
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);  
        tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字  
        // 加载动画  
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(  
                context, R.anim.loading_animation);  
        // 使用ImageView显示动画  
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);  
        tipTextView.setText(msg);// 设置加载信息  
    }
    
    public void setMessage(String msg){
    	this.msg = msg;
    	tipTextView.setText(msg);
    }
}
