package com.yidejia.app.mall.address;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.AddressDataManage;
import com.yidejia.app.mall.util.DefinalDate;

public class PayAddressAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private ArrayList<ModelAddresses> mList;
	private MyApplication myApplication;
	private AddressDataManage dataManage;

	public PayAddressAdapter(Context context, ArrayList<ModelAddresses> mList) {
		myApplication = (MyApplication) context.getApplicationContext();
		dataManage = new AddressDataManage(context);
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.mList = mList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public ModelAddresses getItem(int arg0) {
		// TODO Auto-generated method stub
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stubpay_address_item_address_delete
		return Long.parseLong(mList.get(arg0).getAddressId());
	}

	@Override
	public View getView(int postion, View convertview, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertview == null) {
			convertview = inflater.inflate(R.layout.pay_address_item, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertview
					.findViewById(R.id.pay_address_item_name);
			holder.phone = (TextView) convertview
					.findViewById(R.id.pay_address_item_phone);
			holder.address = (TextView) convertview
					.findViewById(R.id.pay_address_item_address);
			holder.edit = (ImageView) convertview
					.findViewById(R.id.pay_address_item_address_edit);
			holder.delete = (ImageView) convertview
					.findViewById(R.id.pay_address_item_address_delete);
			convertview.setTag(holder);
		} else {
			holder = (ViewHolder) convertview.getTag();
		}
		final ModelAddresses addresses = mList.get(postion);
		holder.name.setText(addresses.getName());
		holder.phone.setText(addresses.getHandset());
		StringBuffer sb = new StringBuffer();
		sb.append(addresses.getProvice());
		sb.append(addresses.getCity());
		sb.append(addresses.getArea());
		sb.append(addresses.getAddress());
		holder.address.setText(sb.toString());
		holder.edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context,
						EditNewAddressActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("editaddress", addresses);
				intent.putExtras(bundle);
				((Activity) context).startActivityForResult(intent,
						DefinalDate.requestcode);
			}
		});
		holder.delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dataManage.deleteAddress(myApplication.getUserId(),
						addresses.getAddressId(), myApplication.getToken());
				mList.remove(addresses);
				notifyDataSetChanged();
			}
		});
		return convertview;
	}

	class ViewHolder {
		private TextView name;
		private TextView phone;
		private TextView address;
		private ImageView delete;
		private ImageView edit;
	}

}
