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
	
	private TextView areaTextView;//地区地址
	private TextView addressTextView;//具体地址
	private TextView nameTextView;//收货人姓名
	private TextView numberTextView;//收货人电话
	private ArrayList<Addresses> addressesArray;//收货人地址栏
	private String TAG = "AddressDataManage";//log
	private ImageView deleteImageView;//删除
	private ImageView editImageView;//编辑
	private CheckBox checkBox;//单选框
	private LinearLayout layout;
	private View view;
	
	private void setupShow(){
		
		try {
			layout = (LinearLayout) findViewById(R.id.address_management_relative2);


//			deleteImageView = (ImageView)view.findViewById(R.id.address_management_item_relative1_textview1);
//			editImageView = (ImageView)view.findViewById(R.id.address_management_item_relative1_textview2);
//			checkBox = (CheckBox)view.findViewById(R.id.address_management_item_relative1_checkBox1);
			
//			deleteImageView.setOnClickListener(this);
				
			

//			new AddressUtil(AddressActivity.this,layout).AllAddresses();
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
		//这里父布局要用scrollview
		setContentView(R.layout.address_management);
//		mangerAddressDataManage = new AddressDataManage(this);
//	    addressesArray = mangerAddressDataManage.getAddressesArray(654321, 0, 10);
//	    addView = getLayoutInflater().inflate(R.layout.address_management_item, null);
		setupShow();
		new AddressUtil(AddressActivity.this,layout).AllAddresses();
		
		
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
		rightButton.setText("新增");
		rightButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent(AddressActivity.this,NewAddressActivity.class);
				startActivityForResult(intent2,DefinalDate.requestcode);//发送Intent,并设置请求码

			}
		});
		
		TextView titleTextView = (TextView) findViewById(R.id.actionbar_title);
		titleTextView.setText("收货地址管理");
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			if(requestCode == DefinalDate.requestcode && resultCode == DefinalDate.responcode){
				try {
					new AddressUtil(AddressActivity.this, layout).addAddresses(data);
					layout.invalidate();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(AddressActivity.this, "您的输入有问题", Toast.LENGTH_SHORT).show();
				}
				   
				   
				}else if(requestCode == DefinalDate.requestcode && resultCode == DefinalDate.responcode1){
					new AddressUtil(AddressActivity.this, layout).updateAddresses(data);
					layout.invalidate();
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(AddressActivity.this, "网络不给力！", Toast.LENGTH_SHORT).show();

		}
	}


}
