package com.yidejia.app.mall.util;

import com.yidejia.app.mall.R;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;


public class SlideImageLayout {
	private Context mContext = null;
	private ImageView[] mImageViews = null; 
	private ImageView mImageView = null;
	private float height;
	private String TAG = "SlideImageLayout";
	
	public SlideImageLayout(Context context,int width) {
		this.mContext = context;
		height = ((float)width / 320) *180;
		Log.i(TAG, "h:"+height+":w:"+width);
		
	}
	
	public View getLinearLayout(View view,int width,int height){
		LinearLayout linerLayout = new LinearLayout(mContext);
		LinearLayout.LayoutParams linerLayoutParames = new LinearLayout.LayoutParams(
				width, 
				height,
				1);
		linerLayout.setPadding(2, 0, 2, 0);
		linerLayout.addView(view, linerLayoutParames);
//		this.width = width;
//		this.height = height;
		return linerLayout;
	}
	
	public void setCircleImageLayout(int size){
		mImageViews = new ImageView[size];
	}
	
	public ImageView getCircleImageLayout(int index){
		mImageView = new ImageView(mContext);  
		mImageView.setLayoutParams(new LayoutParams(9,9));
        mImageView.setScaleType(ScaleType.FIT_XY);
        
        mImageViews[index] = mImageView;
         
        if (index == 0) {  
            mImageViews[index].setBackgroundResource(R.drawable.dot1);  
        } else {  
            mImageViews[index].setBackgroundResource(R.drawable.dot2);  
        }  
         
        return mImageViews[index];
	}
	
    
}
