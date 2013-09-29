package com.yidejia.app.mall.widget;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageButton;


@SuppressLint("DrawAllocation")
public class YLImageButton extends ImageButton {
	
	private String text = "";
	private int color = Color.BLACK;
	private Paint.Align align = Paint.Align.CENTER;
	private float _textsize = 32f;
	private float textX = 0f;
	private float textY = 0f;

//	public YLImageButton(Context context) {
//		super(context);
//		// TODO Auto-generated constructor stub
//	}
	
	public YLImageButton(Context context, AttributeSet attrs){
		super(context, attrs);
	}
	
	public YLImageButton(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
	}
	
	public void setTextColor(int color){
		this.color = color;
	}
	
	public void setTextColorResources(int resId){
		Resources r = this.getResources();
		this.color = r.getInteger(resId);
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public void setText(int resId){
		Resources r = this.getResources();
		this.text = r.getString(resId);
	}
	
	public void setTextX(float textX){
		this.textX = textX;
	}
	
	public void setTextY(float textY){
		this.textY = textY;
	}
	
	public void setText(CharSequence text){
		this.text = text.toString();
	}
	
	public void setPaintAlign(Paint.Align align){
		this.align = align;
	}
	
	public void setTextSize(float textsize){
        this._textsize = textsize;
    }
	
	@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setTextAlign(align);
        paint.setColor(color);
        paint.setTextSize(_textsize);
        if(textX == 0) textX = canvas.getWidth()/2;
        if(textY == 0) textY = (canvas.getHeight()/2);
        canvas.drawText(text, textX, textY, paint);  //»æÖÆÎÄ×Ö
    }

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		textX = widthMeasureSpec /2;
		textY = heightMeasureSpec /2;
	}
	
	
}
