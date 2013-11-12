package com.yidejia.app.mall.ctrl;

import com.yidejia.app.mall.R;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

public class ShipLogViewCtrl {
	
	private Activity activity;
	
	private TextView contextTextView;
	private TextView timeTextView;
	
	public ShipLogViewCtrl(Activity activity){
		this.activity = activity;
	}
	
	public View addView(){
		View view = activity.getLayoutInflater().inflate(R.layout.item_ship_detail, null);
		contextTextView = (TextView) view.findViewById(R.id.ship_log_context);
		timeTextView = (TextView) view.findViewById(R.id.ship_log_time);
		return view;
	}
	
	public void setText(String context, String time){
		contextTextView.setText(context);
		timeTextView.setText(time);
	}
	
	public void setTextColor(String colorString){
		contextTextView.setTextColor(Color.parseColor(colorString));
		timeTextView.setTextColor(Color.parseColor(colorString));
	}
}
