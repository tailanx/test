package com.yidejia.app.mall.view;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.AddressDataManage;
import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.util.DefinalDate;

public class AddressAdapter extends BaseAdapter {
	public ArrayList<Addresses> mAddresses;
	private AddressDataManage dataManage;
	private Activity activity;
	private LayoutInflater inflater;
	private MyApplication myApplication;
	private int temp = -1;
	private boolean isSelect;
	public AddressAdapter(Activity context, ArrayList<Addresses> mAddresses) {
		this.mAddresses = mAddresses;
		myApplication = (MyApplication) context.getApplication();
		dataManage = new AddressDataManage(context);
		this.activity = context;
		this.inflater = LayoutInflater.from(context);

	}

	// public ArrayList<Addresses> getmAddresses() {
	// return mAddresses;
	// }
	//
	// public void setmAddresses(ArrayList<Addresses> mAddresses) {
	// if(mAddresses!=null)
	// this.mAddresses = mAddresses;
	// else
	// this.mAddresses = new ArrayList<Addresses>();
	// }
	//
	// public void changeDate(ArrayList<Addresses> mAddresses ){
	// this.setmAddresses(mAddresses);
	// this.notifyDataSetChanged();
	// }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mAddresses.size();
	}

	@Override
	public Addresses getItem(int arg0) {
		// TODO Auto-generated method stub
		return mAddresses.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.address_management_item,
					null);
			holder = new ViewHolder();
			holder.ctTextView = (TextView) convertView
					.findViewById(R.id.address_management_item_address1);
			holder.detailTextView = (TextView) convertView
					.findViewById(R.id.address_management_item_address2);
			holder.nameTextView = (TextView) convertView
					.findViewById(R.id.address_management_item_textview3);
			holder.phoneTextView = (TextView) convertView
					.findViewById(R.id.address_management_item_textview6);
			holder.cb = (CheckBox) convertView
					.findViewById(R.id.address_management_item_relative1_checkBox1);
			holder.deteleImageView = (ImageView) convertView
					.findViewById(R.id.address_management_item_relative1_textview1);
			holder.editImageView = (ImageView) convertView
					.findViewById(R.id.address_management_item_relative1_textview2);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Addresses addresses = mAddresses.get(position);

	final AlertDialog dialog = new Builder(activity)
				.setTitle(activity.getResources().getString(R.string.tips))
				.setIcon(R.drawable.ic_launcher)
				.setMessage(
						activity.getResources().getString(
								R.string.delete_address))
				.setPositiveButton(
						activity.getResources().getString(R.string.sure),
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dataManage.deleteAddress(
										myApplication.getUserId(),
										addresses.getAddressId(),
										myApplication.getToken());
								mAddresses.remove(addresses);
								notifyDataSetChanged();
							}
						})
				.setNegativeButton(
						activity.getResources().getString(R.string.cancel),
						null).create();
		// if(position == 0){
		// temp = Integer.parseInt(addresses.getAddressId());
		// Log.i("info", position+"onResume");
		// holder.cb.setChecked(true);
		// }

		StringBuffer sb = new StringBuffer();
		sb.append(addresses.getProvice());
		sb.append(addresses.getCity());
		sb.append(addresses.getArea());
		holder.ctTextView.setText(sb.toString());
		holder.detailTextView.setText(addresses.getAddress());
		holder.nameTextView.setText(addresses.getName());
		holder.phoneTextView.setText(addresses.getHandset());
		holder.editImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(activity,
						EditNewAddressActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("editaddress", addresses);
				intent.putExtras(bundle);
				dataManage.deleteAddress(
						myApplication.getUserId(),
						addresses.getAddressId(),
						myApplication.getToken());
				mAddresses.remove(addresses);
				notifyDataSetChanged();
				((Activity) activity).startActivityForResult(intent,
						DefinalDate.requestcode);
			}
		});
		holder.deteleImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				Log.i("info", addresses.getAddressId());
//				dataManage.deleteAddress(myApplication.getUserId(),
//				addresses.getAddressId(), myApplication.getToken());
//				mAddresses.remove(addresses);
//				notifyDataSetChanged();
				 dialog.show();
			}
		});

		holder.cb.setId(position);// 对checkbox的id进行重新设置为当前的position
		if (addresses.getDefaultAddress()) {
			position = temp;
		}
//		isSelect = holder.cb.isChecked();
//		Log.i("info", isSelect+" isSelect");
//		// TODO Auto-generated method stub
//		if(isSelect){
//			Log.i("info", true+" isSelect");
//
//			return convertView;
//		}else{
//			
//		holder.cb.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				
////				}else{
////					Log.i("info", false+" isSelect");
////					if(temp != v.getId()){
//					holder.cb.setChecked(true);
//					notifyDataSetChanged();
//					boolean isSuceess = dataManage.setDefaultAddress(
//							myApplication.getUserId(),
//							addresses.getAddressId(), myApplication.getToken());
//					if(temp !=-1){
//						//找到上次点击的checkbox，并把它设置为false，
//						CheckBox tempCheckBox = (CheckBox)activity.findViewById(temp);
//						if(tempCheckBox!=null){
//							tempCheckBox.setChecked(false);
//						}
//						//保存当前选中的id
//						temp = v.getId();
//					}
////					}else if(temp == v.getId()){
////						return;
////					}
////				}
//				
//			}
//		});
		holder.cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
  
			// 把上次被选中的checkbox设为false
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				Log.i("info", temp + "    temp");
				if (isChecked) {// 实现checkbox的单选功能,同样适用于radiobutton
					boolean isSuceess = dataManage.setDefaultAddress(
							myApplication.getUserId(),
							addresses.getAddressId(), myApplication.getToken());
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
		private TextView ctTextView;// 省市区
		private TextView detailTextView;// 详细地址
		private TextView nameTextView;// 收货人姓名
		private TextView phoneTextView;// 收货人电话
		private ImageView deteleImageView;// 删除
		private ImageView editImageView;// 编辑
		public CheckBox cb;

	}

}
