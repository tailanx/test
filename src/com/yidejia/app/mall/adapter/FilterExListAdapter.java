package com.yidejia.app.mall.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.Brand;
import com.yidejia.app.mall.model.Function;
import com.yidejia.app.mall.model.PriceLevel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("UseSparseArrays")
public class FilterExListAdapter extends BaseExpandableListAdapter {

	private Context mContext;
	private HashMap<Integer, ArrayList<Integer>> clickParentState = new HashMap<Integer, ArrayList<Integer>>();
	private ArrayList<Integer> clickChildState = new ArrayList<Integer>();
	private String TAG = "FilterExListAdapter";
	
	private String[] filterGroup = new String[] { "品牌：全部", "功效：全部功效", "价格：0-∞" };
	private String[][] filterChild = new String[][] {
			{ "全部", "妍诗美", "妍膳美" },
			{ "全部", "眼部护理", "活肌抗衰", "美白淡斑", "保湿锁水", "控油抗痘", "特别护理", "周期护理" },
			{ "全部", "0-100元", "100-200元", "200-400元", "400-800元", "800-1000元",
					"1000以上" } };
	
	private HashMap<Integer, ArrayList<String>> filterHashMap = new HashMap<Integer, ArrayList<String>>();
	
	@SuppressWarnings("unchecked")
	public FilterExListAdapter(Context context, HashMap<Integer, ArrayList<Integer>> clickParentState ){
		this.mContext = context;
		this.clickParentState = (HashMap<Integer, ArrayList<Integer>>) clickParentState.clone();
	}
	public void setClickParentState(HashMap<Integer, ArrayList<Integer>> clickParentState ){
		this.clickParentState = clickParentState;
	}
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		if(isDefault) return filterChild[groupPosition][childPosition];
		return filterHashMap.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = (View) LayoutInflater.from(mContext).inflate(R.layout.filter_second_list_item, null);
		TextView textView = (TextView) convertView.findViewById(R.id.filter_second_list_item_text);
		if(isDefault) textView.setText(filterChild[groupPosition][childPosition]);
		else textView.setText(filterHashMap.get(groupPosition).get(childPosition));
		ImageView imageView = (ImageView) convertView.findViewById(R.id.filter_second_list_item_icon);
		if(!clickParentState.isEmpty() && clickParentState.containsKey(groupPosition)){
			clickChildState = (ArrayList<Integer>) clickParentState.get(groupPosition).clone();
			if(clickChildState.contains(childPosition)){
				Log.d(TAG, "p:"+groupPosition+":c:"+childPosition);
				imageView.setImageResource(R.drawable.filter_selected);
			} else {
				imageView.setImageResource(R.drawable.filter_unchecked);
			}
			clickChildState.clear();
		}
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		if(isDefault) return filterChild[groupPosition].length;
		return filterHashMap.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		if(isDefault) return filterGroup[groupPosition];
		return filterHashMap.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		if(isDefault)return filterGroup.length;
		return filterHashMap.size();
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
		convertView = (View) LayoutInflater.from(mContext).inflate(R.layout.filter_list_item, null);
		TextView textView = (TextView) convertView.findViewById(R.id.filter_list_item_text);
		if(isDefault) textView.setText(filterGroup[groupPosition]);
		else textView.setText(filterGroup[groupPosition]);
		ImageView imageView = (ImageView) convertView.findViewById(R.id.filter_list_item_icon);
		
		if(isExpanded){
			imageView.setImageResource(R.drawable.filter_arrow2);
		} else {
			imageView.setImageResource(R.drawable.filter_arrow);
		}
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
	
	private boolean isDefault = false;
	
//	private ArrayList<Brand> brandsArray;
//	private ArrayList<Function> funsArray;
//	private ArrayList<PriceLevel> pricesArray;
	
	public void setBrands(ArrayList<Brand> brandsArray){
//		this.brandsArray = brandsArray;
		if(brandsArray.size() == 0){
			isDefault = true;
			return;
		}
		ArrayList<String> brandStrings = new ArrayList<String>();
		brandStrings.add(mContext.getResources().getString(R.string.filter_all));
		for (int i = 0; i < brandsArray.size(); i++) {
			brandStrings.add(brandsArray.get(i).getBrandName());
		}
		filterHashMap.put(0, brandStrings);
	}
	public void setFuns(ArrayList<Function> funsArray){
//		this.funsArray = funsArray;
		if(funsArray.size() == 0){
			isDefault = true;
			return;
		}
		ArrayList<String> funStrings = new ArrayList<String>();
		funStrings.add(mContext.getResources().getString(R.string.filter_all));
		for (int i = 0; i < funsArray.size(); i++) {
			funStrings.add(funsArray.get(i).getFunName());
		}
		filterHashMap.put(1, funStrings);
	}
	public void setPrices(ArrayList<PriceLevel> pricesArray){
//		this.pricesArray = pricesArray;
		if(pricesArray.size() == 0){
			isDefault = true;
			return;
		}
		ArrayList<String> priceStrings = new ArrayList<String>();
		priceStrings.add(mContext.getResources().getString(R.string.filter_all));
		for (int i = 0; i < pricesArray.size(); i++) {
			StringBuffer temp = new StringBuffer();
			temp.append(pricesArray.get(i).getMinPrice() + "-");
			temp.append(pricesArray.get(i).getMaxPrice() + mContext.getResources().getString(R.string.unit));
			priceStrings.add(temp.toString());
		}
		filterHashMap.put(2, priceStrings);
	}

}
