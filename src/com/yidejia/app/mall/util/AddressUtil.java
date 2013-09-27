package com.yidejia.app.mall.util;

import java.util.ArrayList;

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

public class AddressUtil implements OnClickListener{
	private LinearLayout linearLayout;
	private Context context;
	private LayoutInflater inflater;
	private  CheckBox checkBox;//��ѡ��
	private TextView areaTextView;//������ַ
	private TextView addressTextView;//�����ַ
	private TextView nameTextView;//�ջ�������
	private TextView numberTextView;//�ջ��˵绰
	private ImageView deleteImageView;//ɾ��
	private ImageView editImageView;//�༭
	private AddressDataManage dataManage;
	private View view;
	public AddressUtil(){
		
	}
	/**
	 * 
	 * @param context
	 * @param id
	 */
	public  AddressUtil(Context context,LinearLayout linearLayout,AddressDataManage dataManage){
		this.context = context;
		this.linearLayout = linearLayout;
		this.dataManage = dataManage;
		this.inflater = LayoutInflater.from(context);
	}
	private void setupShow(){
		 view = inflater.inflate(R.layout.address_management_item, null);
		areaTextView = (TextView) view.findViewById(R.id.address_management_item_address1);
		addressTextView = (TextView) view.findViewById(R.id.address_management_item_address2);
		nameTextView = (TextView) view.findViewById(R.id.address_management_item_textview3);
		numberTextView = (TextView) view.findViewById(R.id.address_management_item_textview4);
		deleteImageView = (ImageView)view.findViewById(R.id.address_management_item_relative1_textview1);
		editImageView = (ImageView)view.findViewById(R.id.address_management_item_relative1_textview2);
		checkBox = (CheckBox)view.findViewById(R.id.address_management_item_relative1_checkBox1);
	}
	/**
	 * �������һ���µĵ�ַ
	 * @return
	 */
	public  void addAddresses(Intent data){
		try {
			setupShow();
			Bundle bundle = data.getExtras();
			if(bundle != null){
				Addresses addresses = (Addresses) bundle.getSerializable("newaddress");
				Log.i("info", addresses.getName().toString());
				nameTextView.setText(addresses.getName());
				addressTextView.setText(addresses.getAddress());
				numberTextView.setText(addresses.getPhone());
				areaTextView.setText(addresses.getProvice()+addresses.getCity());
			}
			//���view
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
	public void AllAddresses(){
		try {
			setupShow();
			ArrayList<Addresses> addressesArray = dataManage .getAddressesArray(654321, 0, 1);
			StringBuffer sb = new StringBuffer();
			for(int i = 0;i<addressesArray.size();i++){
				Addresses addresses = addressesArray.get(i);
				sb.append(addresses.getProvice());
				sb.append(addresses.getCity());
				sb.append(addresses.getArea());
				areaTextView.setText(sb.toString());
				addressTextView.setText(addresses.getAddress());
				nameTextView.setText(addresses.getName());
				numberTextView.setText(addresses.getPhone());
				deleteImageView.setOnClickListener(this);
				checkBox.isChecked();
				linearLayout.addView(view);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "���粻������", Toast.LENGTH_SHORT).show();
		}

}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		dataManage.deleteAddress(654321,0);
	}

}