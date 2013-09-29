package com.yidejia.app.mall.view;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.AddressDataManage;
import com.yidejia.app.mall.datamanage.ExpressDataManage;
import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.model.Express;

public class PayActivity extends SherlockActivity {
	private TextView userName;// �û���
	private TextView phoneName;// �绰����
	private TextView address;// �ջ���ַ
	private TextView peiSong;// ���͵�ַ
	private CheckBox general;// ��ͨ����
	private CheckBox emsBox;// ems����
	private TextView generalPrice;//��ͨ���ͼ۸�
	private TextView emsPrice;//ems���ͼ۸�
	private AddressDataManage addressDataManage;
	private ExpressDataManage expressDataManage;//��������
	private CheckBox zhifubaoCheckBox;// ֧����
	private CheckBox zhifubaowangyeCheckBox;// ֧������ҳ֧��
	private CheckBox yinlianCheckBox;// ����֧��
	private CheckBox caifutongCheckBox;// �Ƹ�֧ͨ��
	private TextView sumPrice;//�ܵļ۸�
/**
 * ʵ�����ؼ�
 */
	public void setupShow() {
		expressDataManage = new ExpressDataManage(this);
		addressDataManage = new AddressDataManage(this);
		sumPrice = (TextView) findViewById(R.id.go_pay_show_pay_money);
		userName = (TextView) findViewById(R.id.go_pay_name);
		phoneName = (TextView) findViewById(R.id.go_pay_number);
		address = (TextView) findViewById(R.id.go_pay_address);
		peiSong = (TextView) findViewById(R.id.go_pay_peisong);
		general = (CheckBox) findViewById(R.id.go_pay_check);
		emsBox = (CheckBox) findViewById(R.id.go_pay_ems);
		generalPrice = (TextView)findViewById(R.id.go_pay_general_price);
		emsPrice = (TextView)findViewById(R.id.go_pay_ems_price);
		
		zhifubaoCheckBox = (CheckBox) findViewById(R.id.zhifubao_checkbox);
		zhifubaowangyeCheckBox = (CheckBox) findViewById(R.id.zhufubaowangye_checkbox);
		yinlianCheckBox = (CheckBox) findViewById(R.id.yinlian_checkbox);
		caifutongCheckBox = (CheckBox) findViewById(R.id.caifutong_checkbox);
		
		general.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(!isChecked){
					emsBox.isChecked();
				}else{
					emsBox.setChecked(false);
				}
				
			}
		});
		emsBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
			if (!isChecked) {
				general.isChecked();
			
			}else {
				general.setChecked(false);
			}
			}
		});
		
		zhifubaoCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					zhifubaowangyeCheckBox.setChecked(false);
					yinlianCheckBox.setChecked(false);
					caifutongCheckBox.setChecked(false);
				}
			}
		});
		zhifubaowangyeCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					zhifubaoCheckBox.setChecked(false);
					yinlianCheckBox.setChecked(false);
					caifutongCheckBox.setChecked(false);
				}
			}
		});
		
		yinlianCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					zhifubaoCheckBox.setChecked(false);
					zhifubaowangyeCheckBox.setChecked(false);
					caifutongCheckBox.setChecked(false);
				}
			}
		});
		caifutongCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					zhifubaoCheckBox.setChecked(false);
					zhifubaowangyeCheckBox.setChecked(false);
					yinlianCheckBox.setChecked(false);
				}
			}
		});

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setActionbar();
		setContentView(R.layout.go_pay);
		LinearLayout layout = (LinearLayout) findViewById(R.id.go_pay_relative2);
		View person = getLayoutInflater().inflate(R.layout.go_pay_item, null);
		layout.addView(person);
		setupShow();
		addAddress();
		Intent  intent = getIntent();
		String sum = intent.getStringExtra("price");
		if(Double.parseDouble(sum)>2990.0){			
			sumPrice.setText(sum);
		}else{
			sumPrice.setText(Double.parseDouble(sum)+Double.parseDouble((general.isChecked()?generalPrice.getText().toString():emsPrice.getText().toString()))+"");
		}
		Log.i("info", sum);
	}
/**
 * UI�ؼ��Ķ���
 */
	private void setActionbar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_compose);
		ImageView button = (ImageView) findViewById(R.id.compose_back);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PayActivity.this.finish();
			}
		});
	}
	/**
	 * ��ʼ���ؼ�
	 */
	private void addAddress(){
		ArrayList<Addresses> mList = addressDataManage.getAddressesArray(68298+"", 0, 10);
		Addresses addresses = mList.remove(0);
		userName.setText(addresses.getName());
		phoneName.setText(addresses.getPhone());
		StringBuffer sb = new StringBuffer();
		sb.append(addresses.getProvice());
		sb.append(addresses.getCity());
		sb.append(addresses.getArea());
		address.setText(sb.toString());
		
		Express express = expressDataManage.getExpressesExpenses(addresses.getProvice(), "n").get(0);
		peiSong.setText(expressDataManage.getDistributionsList(0+"", 10+"").get(0).getDisName());
		generalPrice.setText(express.getExpress());
		emsPrice.setText(express.getEms());
		Log.i("info", addresses.getName());
	}
	
}
