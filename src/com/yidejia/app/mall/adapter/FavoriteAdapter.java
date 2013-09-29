package com.yidejia.app.mall.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.SearchItem;

public class FavoriteAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	private ArrayList<SearchItem> mArrayList;
	/**
	 * 
	 * @param mArrayList
	 */	
	public void setmArrayList(ArrayList<SearchItem> mArrayList) {
 
		if(mArrayList != null)
			this.mArrayList = mArrayList;
		else
			this.mArrayList = new ArrayList<SearchItem>();
	}
	/**
	 * 构造方法
	 * @param context
	 * @param mlist
	 */
	public FavoriteAdapter(Context context,ArrayList<SearchItem> mlist){
		this.setmArrayList(mlist);
		inflater = LayoutInflater.from(context);
	}
	/**
	 * 更新视图
	 * @param mList
	 */
	public void changeDate(ArrayList<SearchItem> mList){
		this.mArrayList = mList;
		this.notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mArrayList.size();
	}

	@Override
	public SearchItem getItem(int arg0) {
		// TODO Auto-generated method stub
		return mArrayList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return Long.parseLong(mArrayList.get(arg0).getUId());
	}

	@Override
	public View getView(int id, View convertView, ViewGroup arg2) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.my_collect_item,null);
			holder = new ViewHolder();
			holder.head = (ImageView) convertView.findViewById(R.id.my_collect_item__imageview1);
			holder.content = (TextView)convertView.findViewById(R.id.my_collect_item_text);
			holder.price = (TextView)convertView.findViewById(R.id.my_collect_item_money);
			holder.sellCount = (TextView)convertView.findViewById(R.id.my_collect_item_sum1);
			holder.commentCount = (TextView)convertView.findViewById(R.id.my_collect_item_sum2);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		//获取数据
		SearchItem searchItem = mArrayList.get(id);
		holder.content.setText(searchItem.getName());
		holder.price.setText(searchItem.getPrice());
		holder.sellCount.setText(searchItem.getSelledAmount());
		holder.commentCount.setText(searchItem.getCommentAmount());
		String path = searchItem.getImgUrl();
		Bitmap bm = BitmapFactory.decodeFile("path");
		if(path != null){
			holder.head.setImageBitmap(bm);
		}else {
			holder.head.setImageResource(R.drawable.ic_launcher);
		}
		
		return convertView;
	}
	class ViewHolder{
		private ImageView head;
		private TextView content;
		private TextView price;
		private TextView sellCount;
		private TextView commentCount;
	}

}
