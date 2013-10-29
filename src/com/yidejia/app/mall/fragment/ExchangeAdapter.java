package com.yidejia.app.mall.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.Specials;

public class ExchangeAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Specials> mlist;
	private static HashMap<Integer, Boolean> isSelected;// ��������ѡ�е�״̬
	private LayoutInflater inflater;
//	private Handler handler;

	public ExchangeAdapter(ArrayList<Specials> mList, Context context) {
		this.context = context;
		this.mlist = mList;
		this.inflater = LayoutInflater.from(context);
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.image_bg)
		.showImageOnFail(R.drawable.image_bg)
		.showImageForEmptyUri(R.drawable.image_bg)
		.cacheInMemory(true).cacheOnDisc(true).build();
		initData();
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		ExchangeAdapter.isSelected = isSelected;
	}

	/**
	 * ��ʼ��checkbox��ѡ��״̬
	 */
	private void initData() {
		for (int i = 0; i < mlist.size(); i++) {
			getIsSelected().put(i, false);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.size();
	}

	@Override
	public Specials getItem(int arg0) {
		// TODO Auto-generated method stub
		return mlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return Long.parseLong(mlist.get(arg0).getUId());
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
	 * ������ͼ
	 */
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();// ����ͼƬ

	ViewHolder holder = null;
	@Override
	public View getView(int postion, View covertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (covertView == null) {
			covertView = inflater.inflate(R.layout.exchange_produce_item, null);
			holder = new ViewHolder();
			holder.iv = (ImageView) covertView
					.findViewById(R.id.exchange_produce_item__imageview1);
			holder.tvContent = (TextView) covertView
					.findViewById(R.id.exchange_produce_item_text);
			holder.tvPrice = (TextView) covertView
					.findViewById(R.id.exchange_produce_item_money);
			holder.subtract = (ImageView) covertView
					.findViewById(R.id.exchange_produce_item_subtract);
			holder.add = (ImageView) covertView
					.findViewById(R.id.exchange_produce_item_add);
			holder.count = (TextView) covertView
					.findViewById(R.id.exchange_produce_item_edit_number);
			holder.cb = (CheckBox) covertView
					.findViewById(R.id.exchange_produce_item_checkbox);
			covertView.setTag(holder);
		} else {
			holder = (ViewHolder) covertView.getTag();
		}
		Specials s = mlist.get(postion);
		imageLoader.displayImage(s.getImgUrl(), holder.iv, options, animateFirstListener);
		holder.tvContent.setText(s.getBrief());
		holder.tvPrice.setText(s.getPrice());
		holder.cb.setChecked(getIsSelected().get(postion));
		
		holder.add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int sum = Integer.parseInt(holder.count.getText().toString());
				
				if (sum >= 9999) {
					Toast.makeText(context, "����Ҫ������Ĳ�Ʒ������ͷ���ϵ",
							Toast.LENGTH_LONG).show();
				} else {
					sum++;
					holder.count.setText(sum + "");
					// mTextView.setText(Integer.parseInt(number.getText()
					// .toString())+"");
				}
//				Message ms = new Message();
//				ms.what = 123;
//				handler.sendMessage(ms);
				
			}
		});
		holder.subtract.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int sum = Integer.parseInt(holder.count.getText().toString());
				if (sum <= 1) {
					Toast.makeText(context, "�Ѿ�����С����ֵ��",
							Toast.LENGTH_LONG).show();
				} else {
					sum--;
					holder.count.setText(sum + "");
					
				}
//			
//				Message ms = new Message();
//				ms.what = 123;
//				handler.sendMessage(ms);
			}
		});
		return covertView;
	}

	static class ViewHolder {
		CheckBox cb;
		private ImageView iv;
		private TextView tvContent;
		private TextView tvPrice;
		private ImageView subtract;
		private ImageView add;
		private TextView count;
	}

}
