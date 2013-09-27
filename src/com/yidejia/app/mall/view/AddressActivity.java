package com.yidejia.app.mall.view;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.AddressDataManage;
import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.util.AddressUtil;
import com.yidejia.app.mall.util.DefinalDate;

public class AddressActivity extends SherlockActivity {

	private AddressDataManage mangerAddressDataManage;
	
	private TextView areaTextView;//������ַ
	private TextView addressTextView;//�����ַ
	private TextView nameTextView;//�ջ�������
	private TextView numberTextView;//�ջ��˵绰
	private ArrayList<Addresses> addressesArray;//�ջ��˵�ַ��
	private String TAG = "AddressDataManage";//log
	private ImageView deleteImageView;//ɾ��
	private ImageView editImageView;//�༭
	private  CheckBox checkBox;//��ѡ��
	private LinearLayout layout;
	
	private void setupShow(){
		
		try {
			layout = (LinearLayout) findViewById(R.id.address_management_relative2);

//			View person = getLayoutInflater().inflate(R.layout.address_management_item, null);
//			areaTextView = (TextView) person.findViewById(R.id.address_management_item_address1);
//			addressTextView = (TextView) person.findViewById(R.id.address_management_item_address2);
//			nameTextView = (TextView) person.findViewById(R.id.address_management_item_textview3);
//			numberTextView = (TextView) person.findViewById(R.id.address_management_item_textview4);
//			deleteImageView = (ImageView)person.findViewById(R.id.address_management_item_relative1_textview1);
//			editImageView = (ImageView)person.findViewById(R.id.address_management_item_relative1_textview2);
//			checkBox = (CheckBox)person.findViewById(R.id.address_management_item_relative1_checkBox1);
//			
//			
//			StringBuffer sb = new StringBuffer();
//			if(addressesArray!=null)
//			for(int i=0;i<addressesArray.size();i++){
//				Addresses addresses = addressesArray.get(i);
//				sb.append(addresses.getProvice());
//				sb.append(addresses.getCity());
//				sb.append(addresses.getArea());
//				areaTextView.setText(sb.toString());
//				addressTextView.setText(addresses.getAddress());
//				nameTextView.setText(addresses.getName());
//				numberTextView.setText(addresses.getPhone());
//				
//				deleteImageView.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View arg0) {
//						mangerAddressDataManage.deleteAddress(654321, 0);
//					}
//				});
//				
//				editImageView.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View arg0) {
//							Intent intent = new Intent(AddressActivity.this,EditNewAddressActivity.class);
//							Bundle  bundle = new Bundle();
//							String area = areaTextView.getText().toString();
//							String name = nameTextView.getText().toString();
//							String phone = numberTextView.getText().toString();
//							String address = addressTextView.getTag().toString();
//						    bundle.putString("area", area);
//						    bundle.putString("name", name);
//						    bundle.putString("phone", phone);
//						    bundle.putString("address", address);
//						    intent.putExtras(bundle);//����bundle����
//						    
//					}
//				});
//			}
//			
//			layout.addView(person);
			new AddressUtil(AddressActivity.this,layout,mangerAddressDataManage).AllAddresses();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "addAddress() ExecutionException");
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setActionbar();
		//���︸����Ҫ��scrollview
		setContentView(R.layout.address_management);
		mangerAddressDataManage = new AddressDataManage(this);
	    addressesArray = mangerAddressDataManage.getAddressesArray(654321, 0, 10);
		setupShow();
		
		
		
	}
	
	private void setActionbar(){
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
//		getSupportActionBar().setLogo(R.drawable.back);
		getSupportActionBar().setIcon(R.drawable.back);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_common);
//		startActionMode(new AnActionModeOfEpicProportions(ComposeActivity.this));
		ImageView leftButton = (ImageView) findViewById(R.id.actionbar_left);
		leftButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				AddressActivity.this.finish();
			}
		});
		Button rightButton = (Button) findViewById(R.id.actionbar_right);
		rightButton.setText("����");
		rightButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent(AddressActivity.this,NewAddressActivity.class);
				startActivityForResult(intent2,DefinalDate.requestcode);//����Intent,������������
//				//������ǰActivity��
//				AddressActivity.this.finish();
			}
		});
		
		TextView titleTextView = (TextView) findViewById(R.id.actionbar_title);
		titleTextView.setText("�ջ���ַ����");
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == DefinalDate.requestcode && resultCode == DefinalDate.responcode ){
			try {
//				LinearLayout layout = (LinearLayout) findViewById(R.id.address_management_relative2);

//				View person = getLayoutInflater().inflate(R.layout.address_management_item, null);
//				areaTextView = (TextView) person.findViewById(R.id.address_management_item_address1);
//				addressTextView = (TextView) person.findViewById(R.id.address_management_item_address2);
//				nameTextView = (TextView) person.findViewById(R.id.address_management_item_textview3);
//				numberTextView = (TextView) person.findViewById(R.id.address_management_item_textview4);
//				//��ȡ���ص�����
//				Bundle bundle = data.getExtras();
//				Addresses addresses = (Addresses) bundle.getSerializable("newaddress");
//				Log.i("info", addresses.getName().toString());
//				nameTextView.setText(addresses.getName());
//				addressTextView.setText(addresses.getAddress());
//				numberTextView.setText(addresses.getPhone());
//				areaTextView.setText(addresses.getProvice()+addresses.getCity());
//				layout.addView(person);
				new AddressUtil(AddressActivity.this, layout,mangerAddressDataManage).addAddresses(data);
				layout.invalidate();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(AddressActivity.this, "���粻������", Toast.LENGTH_SHORT).show();
			}
			   
			   
			}
	}
	
}
