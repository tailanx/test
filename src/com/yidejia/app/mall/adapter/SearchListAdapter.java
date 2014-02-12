package com.yidejia.app.mall.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.Function;

public class SearchListAdapter extends BaseAdapter {

	private Context mContext;
//	private LayoutInflater mLayoutInflater;
//	private String[] listContent = new String[] { "眼部护理", "活肌抗衰", "美白淡斑", "保湿锁水" };//, "控油抗痘", "特别护理", "周期护理", "营养美容"
	private ArrayList<Function> functions;
	private int length = 0;
	private boolean isEmpty = false;
	public void setIsEmpty(boolean isEmpty){
		this.isEmpty = isEmpty;
	}
	
	public SearchListAdapter(Context mContext, ArrayList<Function> functions){
		this.mContext = mContext;
//		mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.functions = functions;
		if(functions.isEmpty()) {
			isEmpty = true;
//			length = listContent.length;
			length++;
			return;
		}
		length = functions.size();
		length++;
	}
	
	@Override
	public int getCount() {
//		if(!isEmpty) length = listContent.length + 1;
//		else length = functions.size() + 1;
		return length;
	}
	
	@Override
	public Object getItem(int position) {
		if(position == 0) return mContext.getResources().getString(R.string.filter_all);
		if(!isEmpty && position != 0) return functions.get(position - 1).getFunName();
//		return listContent[position - 1];
		return position;
	}

	@Override
	public long getItemId(int position) {
		try {
			if(!isEmpty && position != 0) return Long.parseLong(functions.get(position - 1).getFunId());
		} catch (Exception e){
			
		}
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		convertView = (View) mLayoutInflater.inflate(R.layout.search_list_item, null);
		final Holder holder;
		if(null == convertView){
			convertView = (View) LayoutInflater.from(mContext).inflate(R.layout.search_list_item, null);
			holder = new Holder();
			holder.tv_title = (TextView) convertView.findViewById(R.id.tv_search_title);
			holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_search_desc);
			holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_search_icon);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if(position == 0) {
			holder.tv_title.setText(mContext.getResources().getString(R.string.filter_all));
			holder.tv_desc.setText("全类目/妍诗美/妍膳美");
			holder.iv_icon.setImageResource(R.drawable.all_category);
			return convertView;
		}
		if(isEmpty);
//			search_list_text.setText(listContent[position - 1]);
		else {
			holder.tv_title.setText(functions.get(position - 1).getFunName());
			holder.tv_desc.setText(functions.get(position - 1).getDesc());
			ImageLoader.getInstance().displayImage(functions.get(position - 1).getImgUrl(), holder.iv_icon, MyApplication.getInstance().initGoodsImageOption(), MyApplication.getInstance().getImageLoadingListener());
		}
		return convertView;
	}
	
	private class Holder{
		private TextView tv_title;
		private TextView tv_desc;
		private ImageView iv_icon;
	}

}
