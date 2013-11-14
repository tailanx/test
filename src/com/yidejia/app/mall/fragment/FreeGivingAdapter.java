package com.yidejia.app.mall.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
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
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.Specials;

public class FreeGivingAdapter extends BaseAdapter {
	private int temp = -1;
	private Activity activity;
	private LayoutInflater inflater;
	private ArrayList<Specials> mList;
	private static List<HashMap<String, Object>> list;
	public static Cart carts;
	private int length = 0;

	public FreeGivingAdapter(Activity context, ArrayList<Specials> mList) {
		this.activity = context;
		this.inflater = LayoutInflater.from(context);
		this.mList = mList;
		if(mList.isEmpty()) length = 0;
		else length = mList.size();
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.image_bg)
				.showImageOnFail(R.drawable.image_bg)
				.showImageForEmptyUri(R.drawable.image_bg)
				.cacheInMemory(true).cacheOnDisc(true).build();
		list  = new ArrayList<HashMap<String,Object>>();
		// init();
		// sp = context.getSharedPreferences("CHECK",0);
		// SharedPreferences.Editor editor = sp.edit();
		// editor.putString("statePosition", );
		carts = new Cart();
	}
	
	
	/**
	 * �޸����ʱ,���õķ���
	 * 
	 * @param mList
	 */
	public void changeDate(ArrayList<Specials> mList) {
		this.mList = mList;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return length;
	}

	@Override
	public Specials getItem(int position) {
		// TODO Auto-generated method stub
		if(length == 0) return null;
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		if(length == 0) return position;
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
	 * ������ͼ
	 */
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();// ����ͼƬ

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
//		Log.i("info", position+"           postion");
		ViewHolder holder = null;
		HashMap< String , Object> map = new HashMap<String, Object>();
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
		// ��ȡ���
		final Handler handler = new Handler(){
			public void handleMessage(Message msg) {
				
			};
		};
		final Specials specials = mList.get(position);
		// �������ʾ��item��
		holder.title.setText(specials.getBrief());
		holder.price.setText(specials.getPrice());
		imageLoader.displayImage(specials.getImgUrl(), holder.mImageView,
				options, animateFirstListener);

		holder.cb.setId(position);// ��checkbox��id������������Ϊ��ǰ��position
		holder.cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			// ���ϴα�ѡ�е�checkbox��Ϊfalse
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {// ʵ��checkbox�ĵ�ѡ����,ͬ��������radiobutton
					if (temp != -1) {
						// �ҵ��ϴε����checkbox,����������Ϊfalse,������ѡ��ʱ���Խ���ǰ�Ĺص�
						CheckBox tempCheckBox = (CheckBox) activity
								.findViewById(temp);
						if (tempCheckBox != null)
							tempCheckBox.setChecked(false);
					}
					try {
						carts.setUId(specials.getUId());
						carts.setImgUrl(specials.getImgUrl());
						carts.setProductText(specials.getBrief());
						carts.setSalledAmmount(1);
						carts.setPrice(0);
						carts.setScort(0+"");
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
					temp = buttonView.getId();// ���浱ǰѡ�е�checkbox��idֵ
					Message ms = new Message();
					handler.sendEmptyMessage(114);
				}
			}

		});
		// System.out.println("temp:"+temp);
		// System.out.println("position:"+position);
		if (position == temp)// �ȶ�position�͵�ǰ��temp�Ƿ�һ��
			holder.cb.setChecked(true);
		else
			holder.cb.setChecked(false);
		for(int i=0;i<mList.size();i++){
			map.put("check", holder.cb.isChecked());
		}
		list.add(map);
//		Log.i("info", list.toString()+"  list.toString()");
		return convertView;
	}

	static class ViewHolder {
		TextView title;// ����
		ImageView mImageView;// ͼƬ
		TextView price;// �۸�
		CheckBox cb;
	}
	
	public Cart getCart(){
		return this.carts;
	}

}
