package com.yidejia.app.mall;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import java.util.ArrayList;

import com.yidejia.app.mall.R;


public class SlideImageLayout {
	private ArrayList<ImageView> mImageList = null;
	private Context mContext = null;
	private ImageView[] mImageViews = null; 
	private ImageView mImageView = null;
	private int pageIndex = 0;
//	private GetTopAdData getTopAdData;
	private Activity mActivity;
	private float height;
	private int width;
	private int bmpWidth;
	private int bmpHeight;
	private String TAG = "SlideImageLayout";
	
	public SlideImageLayout(Activity mActivity, Context context,int width) {
		this.mActivity = mActivity;
		this.mContext = context;
//		this.getTopAdData = getTopAdData;
		this.width = width;
		height = ((float)width / 320) *180;
		Log.i(TAG, "h:"+height+":w:"+width);
		
		mImageList = new ArrayList<ImageView>();
	}
	
	@SuppressWarnings("deprecation")
	public View getSlideImageLayout(Bitmap bitmap){
		LinearLayout imageLinerLayout = new LinearLayout(mContext);
		LinearLayout.LayoutParams imageLinerLayoutParames = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 
				LinearLayout.LayoutParams.MATCH_PARENT,
				1);
		
		ImageView iv = new ImageView(mContext);
//		int imagewidth = imageLinerLayoutParames.width;
//		int imageheight = imageLinerLayoutParames.height;
		
		
		bmpHeight = bitmap.getHeight();
		bmpWidth = bitmap.getWidth();
//		width = iv.getWidth();
//		height = iv.getHeight();
		float scaleWidth = ((float) width) / bmpWidth;
		
        float scaleHeight = ((float) height) / bmpHeight;
        Log.i(TAG, "sw:"+scaleWidth+":sh:"+scaleHeight);
        Log.i(TAG, "bw:"+bmpWidth+":bh:"+bmpHeight);
//        Log.i(TAG, "iw:"+imagewidth+":ih:"+imageheight);
        
     // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        matrix.reset();
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
     // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
        bmpWidth, bmpHeight, matrix, true);
        /* */
//		iv.setBackgroundResource(id);
//		iv.setBackgroundDrawable(drawable);
		Drawable drawable = new BitmapDrawable(resizedBitmap);
		iv.setBackgroundDrawable(drawable);
//		iv.setBackground(drawable);
//		iv.setImageBitmap(resizedBitmap);
//		iv.setImageDrawable(drawable);
		iv.setOnClickListener(new ImageOnClickListener());
		imageLinerLayout.addView(iv,imageLinerLayoutParames);
		mImageList.add(iv);
		
		return imageLinerLayout;
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
	
	public void setPageIndex(int index){
		pageIndex = index;
	}
	
    public class ImageOnClickListener implements OnClickListener{
    	@Override
    	public void onClick(View v) {
//    		Toast.makeText(mContext, "我点击了第"+"["+pageIndex+"]几个", Toast.LENGTH_SHORT).show();
//    		Intent intent = HufuActivity.newInstance(mActivity, 0, idString, urlString, titleString);
//    		mActivity.startActivity(intent);
    	}
    }
    
}
