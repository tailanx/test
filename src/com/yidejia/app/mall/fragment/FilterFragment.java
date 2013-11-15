package com.yidejia.app.mall.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.SearchResultActivity;
import com.yidejia.app.mall.adapter.FilterExListAdapter;
import com.yidejia.app.mall.datamanage.BrandDataManage;
import com.yidejia.app.mall.datamanage.FunctionDataManage;
import com.yidejia.app.mall.datamanage.PriceDataManage;
import com.yidejia.app.mall.model.Brand;
import com.yidejia.app.mall.model.Function;
import com.yidejia.app.mall.model.PriceLevel;

@SuppressLint("UseSparseArrays")
public class FilterFragment extends Fragment {
	
	private FilterExListAdapter filterAdapter;
	private ExpandableListView filterListView;
	private HashMap<Integer, ArrayList<Integer>> clickParentState = new HashMap<Integer, ArrayList<Integer>>();
	private ArrayList<Integer> clickChildState = new ArrayList<Integer>();
	private String TAG = "FilterFragment";
	
	private ArrayList<Brand> brandsArray;
	private ArrayList<Function> funsArray;
	private ArrayList<PriceLevel> pricesArray;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = getActivity().getLayoutInflater().inflate(R.layout.activity_filter, null);
		
		BrandDataManage brandDataManage = new BrandDataManage(getActivity());
		brandsArray = brandDataManage.getBrandArray();
		
		PriceDataManage priceDataManage = new PriceDataManage(getActivity());
		pricesArray = priceDataManage.getPriceArray();
		
		FunctionDataManage functionDataManage = new FunctionDataManage(getActivity());
		funsArray = functionDataManage.getFunArray();
		
		initView(view);
		initTopView(view);
		return view;
	}
	
	private void initView(View view){
		filterListView = (ExpandableListView) view.findViewById(R.id.filter_listview);
		
		filterAdapter = new FilterExListAdapter(getActivity(), clickParentState);
		filterAdapter.setBrands(brandsArray);
		filterAdapter.setFuns(funsArray);
		filterAdapter.setPrices(pricesArray);
		filterListView.setAdapter(filterAdapter);
		filterListView.setGroupIndicator(null);//��ȥ�Դ�ļ�ͷ
		filterListView.setChoiceMode(ExpandableListView.CHOICE_MODE_MULTIPLE);
		Resources res = getResources();
	    Drawable drawable = res.getDrawable(R.drawable.filter_line);
		filterListView.setChildDivider(drawable);
		filterListView.setDividerHeight(2);
		filterListView.setDivider(drawable);
		
		filterListView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				ImageView imageView = (ImageView) v.findViewById(R.id.filter_second_list_item_icon);
//				List<Integer> clickState = (List<Integer>) clickParentState.get(groupPosition);
				ArrayList<Integer> clickChildState = (ArrayList<Integer>) clickParentState.get(groupPosition);
				if(!clickParentState.isEmpty() && clickParentState.containsKey(groupPosition)){
					if(!clickChildState.contains(childPosition)){
						clickChildState.clear();
						clickChildState.add(childPosition);
//						clickParentState.remove(groupPosition);
//						clickParentState.put(groupPosition, clickChildState);
//						clickParentState.put(groupPosition, clickChildState);
						imageView.setImageResource(R.drawable.filter_selected);
						Log.d(TAG, "!!!!click at " + groupPosition +":"+childPosition);
					} else {
						clickChildState.remove((Integer)childPosition);
//						clickParentState.remove(groupPosition);
//						clickParentState.put(groupPosition, clickChildState);
						imageView.setImageResource(R.drawable.filter_unchecked);
						Log.d(TAG, "!!!click at " + groupPosition +":"+childPosition);
					}
					if(!clickChildState.isEmpty()){
						clickParentState.remove(groupPosition);
						clickParentState.put(groupPosition, clickChildState);
//						Log.d(TAG, "!!click at " + groupPosition +":"+clickParentState.get(groupPosition).get(childPosition));
					}
//					clickChildState.clear();
					Log.d(TAG, "click at " + groupPosition +":"+childPosition);
				} else {
					imageView.setImageResource(R.drawable.filter_selected);
					if(clickChildState == null){
						clickChildState = new ArrayList<Integer>();
					}
					clickChildState.add(childPosition);
					clickParentState.remove(groupPosition);
					clickParentState.put(groupPosition, clickChildState);
//					clickChildState.clear();
					Log.d(TAG, "!click at " + groupPosition +":"+childPosition);
				}
//				parent.invalidate();
//				filterAdapter.setClickParentState(clickParentState);
				filterAdapter.notifyDataSetChanged();
				return false;
			}
		});
		
		filterListView.setOnGroupClickListener(new OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// TODO Auto-generated method stub
				if(groudLastIndex != -1){
//					filterListView.collapseGroup(groudLastIndex);
					ArrayList<Integer> clickChildState = (ArrayList<Integer>) clickParentState.get(groudLastIndex);
					if(clickChildState != null && !clickChildState.isEmpty())
					for (int i = 0; i < clickChildState.size(); i++) {
						
						Log.i(TAG, "last at "+groudLastIndex+":in:"+clickChildState.get(i));
					}
					else {
						Log.i(TAG, "is empty"+groudLastIndex);
					}
//					clickChildState.clear();
					
				}
				groudLastIndex = groupPosition;
				
				ImageView imageView = (ImageView) v.findViewById(R.id.filter_list_item_icon);
				boolean isExpanded = parent.isGroupExpanded(groupPosition);
				if(!isExpanded){
					imageView.setImageResource(R.drawable.filter_arrow2);
//					parent.expandGroup(groupPosition);
				}
				else {
					imageView.setImageResource(R.drawable.filter_arrow);
//					parent.collapseGroup(groupPosition);
				}
//				v.invalidate();
//				parent.invalidate();
//				filterAdapter.getGroupView(groupPosition, !isExpanded, v, parent);
//				filterAdapter.notifyDataSetChanged();
				return false;
			}
		});
		
		filterListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			
			@Override
			public void onGroupExpand(int groupPosition) {
				// TODO Auto-generated method stub
				filterAdapter.setClickParentState(clickParentState);
				filterAdapter.notifyDataSetChanged();
			}
		});
		/**/
	}
	private int groudLastIndex = -1;
	
	private Button filter_complete;
	private Button filter_clear_conditions;
	private void initTopView(View view){
		filter_complete = (Button) view.findViewById(R.id.filter_complete);
		filter_clear_conditions = (Button) view.findViewById(R.id.filter_clear_conditions);
		filter_clear_conditions.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickChildState.clear();
				clickParentState.clear();
				filterAdapter.notifyDataSetChanged();
//				filterListView.invalidateViews();
			}
		});
		
		filter_complete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(getActivity() instanceof SearchResultActivity){
					SearchResultActivity activity = (SearchResultActivity) getActivity();
					Bundle bundle = new Bundle();
//					ArrayList<Integer> selectState;
//					selectState = clickParentState.get(0);
					bundle.putString("brand", getSelectState(clickParentState.get(0), 0));
					bundle.putString("fun", getSelectState(clickParentState.get(1), 1));
					bundle.putString("price", getSelectState(clickParentState.get(2), 2));
					activity.selectContent(bundle);
				}
			}
		});
	}
	
	private String getSelectState(ArrayList<Integer> selectState, int parent){
		StringBuffer temp = new StringBuffer();
		if(null != selectState){
			int length = selectState.size();
			for (int i = 0; i < length; i++) {
				int state = selectState.get(i);
				if(state == 0)
					temp.append("");
				else {
					switch (parent) {
					case 0:
						Brand brand = null;
						if (brandsArray.size() != 0) {
							brand = brandsArray.get(state - 1);
							temp.append(brand.getBrandName());
						} else {
							String[] brandStrings = {"acymer","inerbty"};
							temp.append(brandStrings[state - 1]);
						}
						break;
					case 1:
						Function fun = null;
						if (funsArray.size() != 0) {
							fun = funsArray.get(state - 1);
							temp.append(fun.getFunId());
						} else{
							String[] listIds = new String[]{"12","13","17","20","22","24","28","27"};
							temp.append(listIds[state - 1]);
						}
						break;
					case 2:
						PriceLevel price = null;
						if (pricesArray.size() != 0) {
							price = pricesArray.get(state - 1);
							temp.append(price.getPriceId());
						} else{
							temp.append(state);
						}
						break;

					default:
						break;
					}
				}
				if(i != length - 1){
					temp.append(",");
				}
			}
		} else {
			temp.append("");
		}
		return temp.toString();
	}
}
