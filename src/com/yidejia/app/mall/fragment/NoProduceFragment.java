package com.yidejia.app.mall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragment;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.SearchActivity;

public class NoProduceFragment extends SherlockFragment {
	private View view;
	private Button mButton;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.no_produce,container,false);
		mButton = (Button) view.findViewById(R.id.no_produce_button);
		mButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent (getSherlockActivity(),SearchActivity.class);
				getSherlockActivity().startActivity(intent);
			}
		});
		return view;
	}
}
