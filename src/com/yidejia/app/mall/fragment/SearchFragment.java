package com.yidejia.app.mall.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.SearchResultActivity;
import com.yidejia.app.mall.adapter.SearchListAdapter;
import com.yidejia.app.mall.datamanage.FunctionDataManage;
import com.yidejia.app.mall.model.Function;

public class SearchFragment extends SherlockFragment {
	
	public static SearchFragment newInstance(int title){
		
		SearchFragment fragment = new SearchFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("search", title);
		fragment.setArguments(bundle);
		
		return fragment;
	}
	
	private ArrayList<Function> functions;
	private int search;
	private int defaultInt = -1;
	private String TAG = "SearchFragment";
	
	private ListView searchListView;
//	private EditText searchEditText;//������
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		search = (bundle != null)? bundle.getInt("search"):defaultInt;
		
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_search_item_list, container, false);
		switch (search) {
		case 2:		
			
			break;

		default:
			break;
		}
//		getSherlockActivity().getSupportActionBar().setCustomView(R.layout.actionbar_search);
		searchListView = (ListView) view.findViewById(R.id.search_result_list);
//		searchEditText = (EditText) getSherlockActivity().findViewById(R.id.search_bar_edittext);
//		searchEditText.clearFocus();
//		FunctionDataManage manage = new FunctionDataManage(getSherlockActivity());
//		functions = manage.getFunArray();
		functions = new ArrayList<Function>();
		SearchListAdapter searchListAdapter = new SearchListAdapter(getActivity(), functions);
		searchListView.setAdapter(searchListAdapter);
		searchListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
//				getSherlockActivity().getSupportActionBar().setCustomView(R.layout.actionbar_common);
				Intent intent = new Intent(getActivity(), SearchResultActivity.class);
				Bundle bundle = new Bundle();
//				bundle.putString("fun", arg0.getItemAtPosition(arg2).toString());
				if (!functions.isEmpty()) {
					if(arg2 == 0) {
						bundle.putString("fun", "");
						bundle.putString("title", getResources().getString(R.string.filter_all));
					} else {
						bundle.putString("fun", functions.get(arg2 - 1).getFunId());
						bundle.putString("title", functions.get(arg2 - 1).getFunName());
					}
				} else {
					try {
						bundle.putString("title", listContent[arg2]);
						if(arg2 == 0){
							bundle.putString("fun", "");
						}else {
							bundle.putString("fun", listIds[arg2 - 1]);
						}
					} catch (Exception e) {
						// TODO: handle exception
						bundle.putString("title", "");
						bundle.putString("fun", "");
					}
				}
				bundle.putString("name", "");
				bundle.putString("price", "");
				bundle.putString("brand", "");
				intent.putExtras(bundle);
//				intent.putExtra("bundle", arg2);
				startActivity(intent);
				
//				searchEditText.clearFocus();
			}
		});
//		searchEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
//			
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				// TODO Auto-generated method stub
//				if (hasFocus) {
//					Intent intent = new Intent(getSherlockActivity(),
//							SearchActivity.class);
//					startActivity(intent);
////					getSherlockActivity().overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
//				}
//			}
//		});
		/* 在 mainfragmentActivity里了
		searchEditText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getSherlockActivity(),
						SearchActivity.class);
				startActivity(intent);
//				overridePendingTransition(null, null);
				searchEditText.clearFocus();
			}
		});
		*/
		return view;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "TestFragment-----onActivityCreated");
		
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d(TAG, "TestFragment-----onStart");
	}
	
	private String[] listContent = new String[] {"全部", "眼部护理", "活肌抗衰", "美白淡斑", "保湿锁水", "控油抗痘", "特别护理", "周期护理", "营养美容" };
	private String[] listIds = new String[]{"12","13","17","20","22","24","28","27"};
}
