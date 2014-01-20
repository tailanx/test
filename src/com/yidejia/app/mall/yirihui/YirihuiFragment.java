package com.yidejia.app.mall.yirihui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yidejia.app.mall.R;

public class YirihuiFragment extends Fragment {
	private int index;
//	private PullToRefreshListView refreshListView;

	public static YirihuiFragment newInstance(int num) {
		YirihuiFragment now = new YirihuiFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("index", num);
		now.setArguments(bundle);
		return now;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		index = getArguments() != null ? getArguments().getInt("index") : -1;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.yirihui_item, null);
//		refreshListView = (PullToRefreshListView) view
//				.findViewById(R.id.pull_refresh_list);
		return view;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
}
