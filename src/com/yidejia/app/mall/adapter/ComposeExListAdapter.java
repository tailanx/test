package com.yidejia.app.mall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.yidejia.app.mall.R;

public class ComposeExListAdapter extends BaseExpandableListAdapter {

	private Context mContext;
	
	private String[] composeGroup = new String[] { "妍诗美", "妍膳美" };
	private String[][] composeChild = new String[][] {
			{ "眼部护理", "活肌抗衰", "美白淡斑", "保湿锁水", "控油抗痘", "特别护理", "周期护理" },
			{ "sdf", "454", "yiu", "fga", "dsfa", "dsfa" } };
	
	public ComposeExListAdapter(Context context){
		this.mContext = context;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return composeChild[groupPosition][childPosition];
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = (View) LayoutInflater.from(mContext).inflate(R.layout.compose_second_list_item, null);
		TextView textView = (TextView) convertView.findViewById(R.id.compose_second_list_item_text);
		textView.setText(composeChild[groupPosition][childPosition]);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return composeChild[groupPosition].length;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return composeGroup[groupPosition];
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return composeGroup.length;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = (View) LayoutInflater.from(mContext).inflate(R.layout.compose_list_item, null);
		TextView textView = (TextView) convertView.findViewById(R.id.compose_list_item_text);
		textView.setText(composeGroup[groupPosition]);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
