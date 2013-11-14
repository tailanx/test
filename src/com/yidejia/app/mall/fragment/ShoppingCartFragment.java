package com.yidejia.app.mall.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.yidejia.app.mall.R;

public class ShoppingCartFragment extends SherlockFragment {
	
	public static ShoppingCartFragment newInstance(int title){
		
		ShoppingCartFragment fragment = new ShoppingCartFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("cart", title);
		fragment.setArguments(bundle);
		
		return fragment;
	}
	
	private int cart;
	private int defaultInt = -1;
	private String TAG = "ShoppingCartFragment";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		cart = (bundle != null)? bundle.getInt("cart"):defaultInt;
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_search_image_layout, container, false);
		switch (cart) {
		case 3:		
			
			break;

		default:
			break;
		}
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
}
