package com.yidejia.app.mall.phone;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yidejia.app.mall.R;

public class ContactAdapter extends BaseAdapter {

	private List<String> mContactName;
	private List<String> mContactNumber;
	private List<Bitmap> mContactPic;
	private LayoutInflater inflater;


	public ContactAdapter(Context context,List<String> mContactName,
			List<String> mContactNumber, List<Bitmap> mContactPic) {
		inflater = LayoutInflater.from(context);
		this.mContactName = mContactName;
		this.mContactNumber = mContactNumber;
		this.mContactPic = mContactPic;

	}

	public void setArrayList(ArrayList<String> mContactName,
			ArrayList<String> mContactNumber, ArrayList<Bitmap> mContactPic) {
		if (mContactName != null) {
			this.mContactName = mContactName;
		} else {
			mContactName = new ArrayList<String>();
		}
		if (mContactNumber != null) {
			this.mContactNumber = mContactNumber;
		} else {
			mContactNumber = new ArrayList<String>();
		}
		if (mContactPic != null) {
			this.mContactPic = mContactPic;
		} else {
			mContactPic = new ArrayList<Bitmap>();

		}

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mContactName.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mContactName.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View contentview, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (contentview == null) {
			contentview = inflater.inflate(R.layout.contact_item, null);
			holder = new ViewHolder();
			holder.mImageView = (ImageView) contentview
					.findViewById(R.id.iv_contact_item_imageview);
			holder.mName = (TextView) contentview
					.findViewById(R.id.tv_contact_item_name);
			holder.mNumber = (TextView) contentview
					.findViewById(R.id.tv_contact_item_number);
			contentview.setTag(holder);
		} else {
			holder = (ViewHolder) contentview.getTag();
		}
		
		holder.mImageView.setImageBitmap(mContactPic.get(arg0));
		holder.mName.setText(mContactName.get(arg0));
		holder.mNumber.setText(mContactNumber.get(arg0));
		return contentview;
	}

	class ViewHolder {
		private ImageView mImageView;
		private TextView mName;
		private TextView mNumber;
	}

}
