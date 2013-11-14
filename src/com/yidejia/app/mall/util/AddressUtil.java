package com.yidejia.app.mall.util;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.AddressDataManage;
import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.view.EditNewAddressActivity;

public class AddressUtil {
	private LinearLayout linearLayout;
	private Context context;
	private LayoutInflater inflater;
	private CheckBox checkBox;// ?????
	private TextView areaTextView;// ??????
	private TextView addressTextView;// ??????
	private TextView nameTextView;// ?????????
	private TextView numberTextView;// ?????��
	private ImageView deleteImageView;// ???
	private ImageView editImageView;// ??
	private AddressDataManage dataManage;
	private View view;
	ArrayList<Addresses> addressesArray;
	private ImageView delete;// ???
	private ImageView edit;// ??

	private MyApplication myApplication ;

	public AddressUtil() {

	}

	/**
	 * 
	 * @param context
	 * @param id
	 */
	public AddressUtil(Context context, LinearLayout linearLayout) {
		this.context = context;
		this.linearLayout = linearLayout;
		this.dataManage = dataManage;
		this.inflater = LayoutInflater.from(context);
		myApplication = (MyApplication) context.getApplicationContext();
	}

	private void setupShow() {
		edit = (ImageView) view
				.findViewById(R.id.address_management_item_relative1_textview2);
		delete = (ImageView) view
				.findViewById(R.id.address_management_item_relative1_textview1);

		areaTextView = (TextView) view
				.findViewById(R.id.address_management_item_address1);
		addressTextView = (TextView) view
				.findViewById(R.id.address_management_item_address2);
		nameTextView = (TextView) view
				.findViewById(R.id.address_management_item_textview3);
		numberTextView = (TextView) view
				.findViewById(R.id.address_management_item_textview6);
		deleteImageView = (ImageView) view
				.findViewById(R.id.address_management_item_relative1_textview1);
		editImageView = (ImageView) view
				.findViewById(R.id.address_management_item_relative1_textview2);
		view = LayoutInflater.from(context).inflate(
				R.layout.address_management_item, null);
		checkBox = (CheckBox) view
				.findViewById(R.id.address_management_item_relative1_checkBox1);

	}

	/**
	 * ???????????��???
	 * 
	 * @return
	 */

	public void addAddresses(Intent data) {
		try {
			dataManage = new AddressDataManage(context);
			Bundle bundle = data.getExtras();
			if (bundle != null) {
				final Addresses addresses = (Addresses) bundle
						.getSerializable("newaddress");
				 Log.i("info", addresses.getName().toString()+"   newaddress ");
				 view = LayoutInflater.from(context).inflate(
							R.layout.address_management_item, null);
				setupShow();

				nameTextView.setText(addresses.getName());
				addressTextView.setText(addresses.getAddress());
				numberTextView.setText(addresses.getHandset());

				areaTextView.setText(addresses.getProvice()
						+ addresses.getCity() + addresses.getArea());

				deleteImageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						dataManage.deleteAddress(((MyApplication)context.getApplicationContext()).getUserId(),
								addresses.getAddressId(), ((MyApplication)context.getApplicationContext()).getToken());

					}
				});
				checkBox.setChecked(addresses.getDefaultAddress());
				//
				editImageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(context,
								EditNewAddressActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("editaddress", addresses);
						intent.putExtras(bundle);
						((Activity) context).startActivityForResult(intent,
								DefinalDate.requestcode);// 

					}
				});
				checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						// TODO Auto-generated method stub
						if(isChecked) return;
						boolean isSucess = dataManage.setDefaultAddress(myApplication.getUserId(), addresses.getAddressId(), myApplication.getToken());
						Log.i("info", isSucess +"");
					}
				});
			}
			
			// ???view
			this.linearLayout.addView(view);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * ???��??
	 * 
	 * @param data
	 */

	public void updateAddresses(Intent data) {
		try {
			// dataManage = new AddressDataManage(context);
			Bundle bundle = data.getExtras();
			if (bundle != null) {
				final Addresses addresses = (Addresses) bundle
						.getSerializable("newaddress");
				Log.i("info", addresses.getName().toString());
				view = LayoutInflater.from(context).inflate(
						R.layout.address_management_item, null);
				CheckBox checkBox = (CheckBox) view
						.findViewById(R.id.address_management_item_relative1_checkBox1);

				nameTextView.setText(addresses.getName());
				addressTextView.setText(addresses.getAddress());
				numberTextView.setText(addresses.getHandset());

				areaTextView.setText(addresses.getProvice()
						+ addresses.getCity() + addresses.getArea());

				deleteImageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						boolean isDele = dataManage.deleteAddress(((MyApplication)context.getApplicationContext()).getUserId(),
								addresses.getAddressId(), ((MyApplication)context.getApplicationContext()).getToken());
//						Log.i("info", isDele + "");
					}
				});
				checkBox.setChecked(addresses.getDefaultAddress());
				//
				editImageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(context,
								EditNewAddressActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("editaddress", addresses);
						intent.putExtras(bundle);
						((Activity) context).startActivityForResult(intent,
								DefinalDate.requestcode);// ????Intent,????????????

					}
				});
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					if(isChecked) return;
					boolean isSucess = dataManage.setDefaultAddress(myApplication.getUserId(), addresses.getAddressId(), myApplication.getToken());
					Log.i("info", isSucess +"");
				}
			});
			}
			// ???view
			this.linearLayout.addView(view);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * 
	 */
	public void AllAddresses() {
		try {
			dataManage = new AddressDataManage(context);
			addressesArray = dataManage.getAddressesArray(((MyApplication)context.getApplicationContext()).getUserId(), 0, 20);
			for (int i = 0; i < addressesArray.size(); i++) {
				final Addresses addresses = addressesArray.get(i);
				StringBuffer sb = new StringBuffer();
				// Log.i("info", addressesArray.size() +
				// "+addressArray.size()");
				view = LayoutInflater.from(context).inflate(
						R.layout.address_management_item, null);
				CheckBox checkBox = (CheckBox) view
						.findViewById(R.id.address_management_item_relative1_checkBox1);

				// edit = (ImageView) view
				// .findViewById(R.id.address_management_item_relative1_textview2);

				// delete = (ImageView) view
				// .findViewById(R.id.address_management_item_relative1_textview1);
				final LinearLayout layout1 = (LinearLayout) view.findViewById(R.id.address_management_item_linearlayout);
				areaTextView = (TextView) view
						.findViewById(R.id.address_management_item_address1);
				addressTextView = (TextView) view
						.findViewById(R.id.address_management_item_address2);
				nameTextView = (TextView) view
						.findViewById(R.id.address_management_item_textview3);
				numberTextView = (TextView) view
						.findViewById(R.id.address_management_item_textview6);
				deleteImageView = (ImageView) view
						.findViewById(R.id.address_management_item_relative1_textview1);
				editImageView = (ImageView) view
						.findViewById(R.id.address_management_item_relative1_textview2);
				checkBox = (CheckBox) view
						.findViewById(R.id.address_management_item_relative1_checkBox1);
				boolean isDefault = addresses.getDefaultAddress();
//				if (isDefault) 
					checkBox.setChecked(isDefault);
				sb.append(addresses.getProvice());
				sb.append(addresses.getCity());
				sb.append(addresses.getArea());
				// Log.i("info", sb.toString()+"addresses.getArea()");
				// Log.i("info", addresses.getPhone()+"addresses.getPhone()");
				areaTextView.setText(sb.toString());
				addressTextView.setText(addresses.getAddress());
				nameTextView.setText(addresses.getName());
				numberTextView.setText(addresses.getHandset());
				//
				Handler  hanlder = new Handler(){
					public void handleMessage(android.os.Message msg) {
						
					};
				};
				deleteImageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						boolean isDele = dataManage.deleteAddress(((MyApplication)context.getApplicationContext()).getUserId(),
								addresses.getAddressId(), ((MyApplication)context.getApplicationContext()).getToken());
						Log.i("info", isDele + "   isDele");
						linearLayout.removeView(layout1);
					}
				});
				checkBox.setChecked(addresses.getDefaultAddress());
				//
				editImageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(context,
								EditNewAddressActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("editaddress", addresses);
						intent.putExtras(bundle);
						((Activity) context).startActivityForResult(intent,
								DefinalDate.requestcode);// ????Intent,????????????

					}
				});
				
				checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						// TODO Auto-generated method stub
						if(isChecked) return;
						boolean isSucess = dataManage.setDefaultAddress(myApplication.getUserId(), addresses.getAddressId(), myApplication.getToken());
						Log.i("info", isSucess +"");
						
					}
				});
				linearLayout.addView(view);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
