package com.yidejia.app.mall.adapter;

import java.util.ArrayList;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.FunctionDataManage;
import com.yidejia.app.mall.model.Function;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchListAdapter extends BaseAdapter {

	private Context mContext;
//	private LayoutInflater mLayoutInflater;
	private String[] listContent = new String[] { "眼部护理", "活肌抗衰", "美白淡斑", "保湿锁水", "控油抗痘", "特别护理", "周期护理", "营养美容" };
	private ArrayList<Function> functions;
	private int length = 0;
	
	public SearchListAdapter(Context mContext, ArrayList<Function> functions){
		this.mContext = mContext;
//		mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.functions = functions;
		length = functions.size();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(length == 0) length = listContent.length;
		return length;
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(length != 0) return functions.get(position).getFunName();
		return listContent[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		try {
			if(length != 0) return Long.parseLong(functions.get(position).getFunId());
		} catch (Exception e){
			
		}
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
//		convertView = (View) mLayoutInflater.inflate(R.layout.search_list_item, null);
		
		convertView = (View) LayoutInflater.from(mContext).inflate(R.layout.search_list_item, null);
		TextView search_list_text = (TextView) convertView.findViewById(R.id.search_list_item_text);
		if(length == 0)
			search_list_text.setText(listContent[position]);
		else {
			search_list_text.setText(functions.get(position).getFunName());
		}
		return convertView;
	}

}
