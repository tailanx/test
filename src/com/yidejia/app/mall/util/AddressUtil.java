package com.yidejia.app.mall.util;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
	private CheckBox checkBox;// ��ѡ��
	private TextView areaTextView;// ������ַ
	private TextView addressTextView;// �����ַ
	private TextView nameTextView;// �ջ�������
	private TextView numberTextView;// �ջ��˵绰
	private ImageView deleteImageView;// ɾ��
	private ImageView editImageView;// �༭
	private AddressDataManage dataManage;
	private View view;
	ArrayList<Addresses> addressesArray;
	private ImageView delete;// ɾ��
	private ImageView edit;// �༭

	public AddressUtil() {

	}

	/**
	 * 
	 * @param context
	 * @param id
	 */
	public AddressUtil(Context context, LinearLayout linearLayout) {
		this.context = context;
		this.view = view;
		this.linearLayout = linearLayout;
		this.dataManage = dataManage;
		this.inflater = LayoutInflater.from(context);
	}

	private void setupShow() {
//		view = LayoutInflater.from(context).inflate(
//				R.layout.address_management_item, null);
//		edit = (ImageView) view
//				.findViewById(R.id.address_management_item_relative1_textview2);
//
//		delete = (ImageView) view
//				.findViewById(R.id.address_management_item_relative1_textview1);
//
//		areaTextView = (TextView) view
//				.findViewById(R.id.address_management_item_address1);
//		addressTextView = (TextView) view
//				.findViewById(R.id.address_management_item_address2);
//		nameTextView = (TextView) view
//				.findViewById(R.id.address_management_item_textview3);
//		numberTextView = (TextView) view
//				.findViewById(R.id.address_management_item_textview4);
//		deleteImageView = (ImageView) view
//				.findViewById(R.id.address_management_item_relative1_textview1);
//		editImageView = (ImageView) view
//				.findViewById(R.id.address_management_item_relative1_textview2);
//		checkBox = (CheckBox) view
//				.findViewById(R.id.address_management_item_relative1_checkBox1);

	}

	/**
	 * �������һ���µĵ�ַ
	 * 
	 * @return
	 */
	
	public void addAddresses(Intent data) {
		try {
			dataManage = new AddressDataManage(context);
			Bundle bundle = data.getExtras();
			if (bundle != null) {
				Addresses addresses = (Addresses) bundle
						.getSerializable("newaddress");
				// Log.i("info", addresses.getName().toString());
				setupShow();
				nameTextView.setText(addresses.getName());
				addressTextView.setText(addresses.getAddress());
				numberTextView.setText(addresses.getPhone());
				areaTextView.setText(addresses.getProvice()
						+     addresses.getCity()+    addresses.getArea());
			}
			// ���view
			this.linearLayout.addView(view);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "���粻������", Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * 
	 */
	public void AllAddresses() {
		try {
			dataManage = new AddressDataManage(context);
			addressesArray = dataManage.getAddressesArray(68298 + "", 0, 15);
			for (int i = 0; i < addressesArray.size(); i++) {
				StringBuffer sb = new StringBuffer();
//				Log.i("info", addressesArray.size() + "+addressArray.size()");
				final Addresses addresses = addressesArray.get(i);
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
						.findViewById(R.id.address_management_item_textview4);
				deleteImageView = (ImageView) view
						.findViewById(R.id.address_management_item_relative1_textview1);
				editImageView = (ImageView) view
						.findViewById(R.id.address_management_item_relative1_textview2);
				checkBox = (CheckBox) view
						.findViewById(R.id.address_management_item_relative1_checkBox1);
				sb.append(addresses.getProvice());
				sb.append(addresses.getCity());
				sb.append(addresses.getArea());
//				Log.i("info", sb.toString()+"addresses.getArea()");

				areaTextView.setText(sb.toString());
				addressTextView.setText(addresses.getAddress());
				nameTextView.setText(addresses.getName());
				numberTextView.setText(addresses.getPhone());
				
				deleteImageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						dataManage.deleteAddress(68298,
								Integer.parseInt(addresses.getAddressId()));

					}
				});
				checkBox.setChecked(false);
				
				editImageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(context,
								EditNewAddressActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("editaddress", addresses);
						intent.putExtras(bundle);
						((Activity) context).startActivityForResult(intent,
								DefinalDate.requestcode);// ����Intent,������������

					}
				});

				linearLayout.addView(view);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "���粻������", Toast.LENGTH_SHORT).show();
		}

	}

}