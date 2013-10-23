package com.yidejia.app.mall.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.Specials;

public class FreeGivingAdapter extends BaseAdapter {
	private int temp = -1;
	private Activity activity;
	private LayoutInflater inflater;
	private ArrayList<Specials> mList;


	public FreeGivingAdapter(Activity context, ArrayList<Specials> mList) {
		this.activity = context;
		this.inflater = LayoutInflater.from(context);
		this.mList = mList;
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.hot_sell_right_top_image)
				.showImageOnFail(R.drawable.hot_sell_right_top_image)
				.showImageForEmptyUri(R.drawable.hot_sell_right_top_image)
				.cacheInMemory(true).cacheOnDisc(true).build();
		// init();
		// sp = context.getSharedPreferences("CHECK",0);
		// SharedPreferences.Editor editor = sp.edit();
		// editor.putString("statePosition", );
	}

	/**
	 * 修改数据时,调用的方法
	 * 
	 * @param mList
	 */
	public void changeDate(ArrayList<Specials> mList) {
		this.mList = mList;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Specials getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return Long.parseLong(mList.get(position).getUId());
	}

	static final List<String> displayedImages = Collections
			.synchronizedList(new LinkedList<String>());

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

	/**
	 * 加载视图
	 */
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();// 加载图片

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.free_giving_item, null);
			holder = new ViewHolder();
			holder.title = (TextView) convertView
					.findViewById(R.id.free_giving_item_text);
			holder.mImageView = (ImageView) convertView
					.findViewById(R.id.free_giving_item__imageview1);
			holder.price = (TextView) convertView
					.findViewById(R.id.free_giving_item_money);
			holder.cb = (CheckBox) convertView
					.findViewById(R.id.free_giving_item_checkbox);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 获取数据
		Specials specials = mList.get(position);
		// 将数据显示在item上
		holder.title.setText(specials.getBrief());
		holder.price.setText(specials.getPrice());
		imageLoader.displayImage(specials.getImgUrl(), holder.mImageView,
				options, animateFirstListener);

		holder.cb.setId(position);// 对checkbox的id进行重新设置为当前的position
		holder.cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			// 把上次被选中的checkbox设为false
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {// 实现checkbox的单选功能,同样适用于radiobutton
					if (temp != -1) {
						// 找到上次点击的checkbox,并把它设置为false,对重新选择时可以将以前的关掉
						CheckBox tempCheckBox = (CheckBox) activity
								.findViewById(temp);
						if (tempCheckBox != null)
							tempCheckBox.setChecked(false);
					}
					temp = buttonView.getId();// 保存当前选中的checkbox的id值
				}
			}

		});
		// System.out.println("temp:"+temp);
		// System.out.println("position:"+position);
		if (position == temp)// 比对position和当前的temp是否一致
			holder.cb.setChecked(true);
		else
			holder.cb.setChecked(false);
		return convertView;
	}

	static class ViewHolder {
		TextView title;// 内容
		ImageView mImageView;// 图片
		TextView price;// 价格
		CheckBox cb;
	}

}
