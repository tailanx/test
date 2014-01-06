package com.yidejia.app.mall.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.task.TaskFilter;

@SuppressLint("UseSparseArrays")
public class FilterFragment extends Fragment {
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	private View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		System.gc();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		try {
			view = inflater.inflate(R.layout.activity_filter, null);
//			((MyApplication)getActivity().getApplication()).setView(view);;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Log.e(FilterFragment.class.getName(), "===onStart");
	}
	private TaskFilter taskFilter;
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.e(FilterFragment.class.getName(), "===onActivityCreated");
		if(null == view){
//			view = ((MyApplication)getActivity().getApplication()).getView();
			return;
		}
		taskFilter = new TaskFilter(getActivity(), view);
		taskFilter.getFilter();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e(FilterFragment.class.getName(), "===onDestroy");
		if(taskFilter != null) taskFilter.closeTask();
		System.gc();
	}

	
}
