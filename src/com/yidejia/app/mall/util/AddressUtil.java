package com.yidejia.app.mall.util;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.AddressDataManage;
import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.view.AddressActivity;
import com.yidejia.app.mall.view.EditNewAddressActivity;
import com.yidejia.app.mall.view.NewAddressActivity;

public class AddressUtil {
	private LinearLayout linearLayout;
	private Context context;
	private LayoutInflater inflater;
	private CheckBox checkBox;// 单选框
	private TextView areaTextView;// 地区地址
	private TextView addressTextView;// 具体地址
	private TextView nameTextView;// 收货人姓名
	private TextView numberTextView;// 收货人电话
	private ImageView deleteImageView;// 删除
	private ImageView editImageView;// 编辑
	private AddressDataManage dataManage;
	private View view;
	ArrayList<Addresses> addressesArray;
	private ImageView delete;// 删除
	private ImageView edit;// 编辑

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
	}

	private void setupShow() {
		view = LayoutInflater.from(context).inflate(
				R.layout.address_management_item, null);
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
		checkBox = (CheckBox) view
				.findViewById(R.id.address_management_item_relative1_checkBox1);

	}

	/**
	 * 用于添加一个新的地址
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
				// Log.i("info", addresses.getName().toString());
				setupShow();
				nameTextView.setText(addresses.getName());
				addressTextView.setText(addresses.getAddress());
				numberTextView.setText(addresses.getHandset());

				areaTextView.setText(addresses.getProvice()
						+ addresses.getCity() + addresses.getArea());

				deleteImageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						dataManage.deleteAddress(new MyApplication().getUserId(),
								addresses.getAddressId());

					}
				});
				checkBox.setChecked(false);
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
								DefinalDate.requestcode);// 发送Intent,并设置请求码

					}
				});
			}
			// 添加view
			this.linearLayout.addView(view);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "网络不给力！", Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * 更新地址
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
				setupShow();
				nameTextView.setText(addresses.getName());
				addressTextView.setText(addresses.getAddress());
				numberTextView.setText(addresses.getHandset());

				areaTextView.setText(addresses.getProvice()
						+ addresses.getCity() + addresses.getArea());

				deleteImageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						boolean isDele = dataManage.deleteAddress(new MyApplication().getUserId(),
								addresses.getAddressId());
//						Log.i("info", isDele + "");
					}
				});
				checkBox.setChecked(false);
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
								DefinalDate.requestcode);// 发送Intent,并设置请求码

					}
				});
			}
			// 添加view
			this.linearLayout.addView(view);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "网络不给力！", Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * 
	 */
	public void AllAddresses() {
		try {
			dataManage = new AddressDataManage(context);
			addressesArray = dataManage.getAddressesArray(new MyApplication().getUserId(), 0, 20);
			for (int i = 0; i < addressesArray.size(); i++) {
				final Addresses addresses = addressesArray.get(i);
				StringBuffer sb = new StringBuffer();
				// Log.i("info", addressesArray.size() +
				// "+addressArray.size()");
				view = LayoutInflater.from(context).inflate(
						R.layout.address_management_item, null);
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
						boolean isDele = dataManage.deleteAddress(new MyApplication().getUserId(),
								addresses.getAddressId());
						Log.i("info", isDele + "   isDele");
						linearLayout.removeView(layout1);
					}
				});
				checkBox.setChecked(false);
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
								DefinalDate.requestcode);// 发送Intent,并设置请求码

					}
				});

				linearLayout.addView(view);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "网络不给力！", Toast.LENGTH_SHORT).show();
		}

	}
}
