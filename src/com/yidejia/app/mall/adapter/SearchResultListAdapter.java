package com.yidejia.app.mall.adapter;

import com.yidejia.app.mall.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchResultListAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private String[] listContent = new String[] { "眼部护理", "活肌抗衰", "美白淡斑", "保湿锁水", "控油抗痘", "特别护理", "周期护理", "营养美容" };
	
	public SearchResultListAdapter(Context mContext){
		this.mContext = mContext;
		mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listContent.length;
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listContent[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
//		convertView = (View) mLayoutInflater.inflate(R.layout.search_list_item, null);
		convertView = (View) LayoutInflater.from(mContext).inflate(R.layout.item_search_result_list, null);
//		TextView search_list_text = (TextView) convertView.findViewById(R.id.search_list_item_text);
//		search_list_text.setText(listContent[position]);
		return convertView;
	}

}
