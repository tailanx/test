/*
 * Copyright (C) 2011 Patrik �kerfeldt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yidejia.app.mall.adapter;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.R.drawable;
import com.yidejia.app.mall.R.id;
import com.yidejia.app.mall.R.layout;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context context;
//	private static final int[] ids = { R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};	
	
	public ImageAdapter(Context context) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
	}

	@Override
	public int getCount() {
//		return ids.length;
		return 3;
//		return Integer.MAX_VALUE;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.image_item, null);
		}
		final int p = position;
//		Log.e("MainActivity", "length:"+getCount());
//		((ImageView) convertView.findViewById(R.id.imgView)).setImageResource(ids[position%ids.length]);
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Toast.makeText(context, "position:"+p%ids.length, Toast.LENGTH_SHORT).show();
			}
		});
		return convertView;
	}

}
