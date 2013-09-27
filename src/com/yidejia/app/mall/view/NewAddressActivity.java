package com.yidejia.app.mall.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.util.DefinalDate;

public class NewAddressActivity extends SherlockActivity {
	private TextView textView ;
	private Spinner province;
	private Spinner city;
	private EditText nameTextView;//�ջ�������
	private EditText numberTextView;//�ջ��˵绰
	private EditText areaTextView;//�ջ�����ϸ��ַ
	private void setupShow(){
		
		//�����Ŀ��ѡ�м�����
		province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
			//parent����province����
			Spinner spinner = (Spinner)parent;
			String pro = (String)spinner.getItemAtPosition(position);

			//(����ʡ���е���ʾ)
			//��Ĭ��ֵ��ArrayAdapter����(����Դ�����ļ��л�ȡ����)
			ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource
			(NewAddressActivity.this, R.array.citydefault, android.R.layout.simple_spinner_item);
			
			//new ArrayAdapter<CharSequence>
			// (MainActivity.this,android.R.layout.simple_spinner_item, cities);
			//��ȡ����ʡ������Щ��(����Դ�����ļ��л�ȡ����)
			if(pro.equals("�ӱ�ʡ")){

			cityAdapter = ArrayAdapter.createFromResource
			(NewAddressActivity.this, R.array.hb, android.R.layout.simple_spinner_item);
			}else if(pro.equals("������")){

			cityAdapter = ArrayAdapter.createFromResource
			(NewAddressActivity.this, R.array.bj, android.R.layout.simple_spinner_item);
			}else if(pro.equals("ɽ��ʡ")){

			cityAdapter = ArrayAdapter.createFromResource
			(NewAddressActivity.this, R.array.shx, android.R.layout.simple_spinner_item);
			}
			//�����ݵ�Spinner(City)��
			city.setAdapter(cityAdapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

			});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		
		setActionbar();
		setContentView(R.layout.new_address);
		//String[] provinces = new String[]{"-ʡ��-","�ӱ�ʡ","ɽ��ʡ","����"};
		
		province = (Spinner)this.findViewById(R.id.province);
		city = (Spinner)this.findViewById(R.id.city);	
		nameTextView = (EditText) findViewById(R.id.new_address_item_edittext1);
		numberTextView = (EditText) findViewById(R.id.new_address_item_edittext2);
		areaTextView = (EditText) findViewById(R.id.new_address_item_edittext3);
		
		
		ArrayAdapter<CharSequence> adapter =
				ArrayAdapter.createFromResource(this, R.array.province, android.R.layout.simple_spinner_item);
		//���������б�ķ��
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		setupShow();
		province.setAdapter(adapter);
		
		
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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
				// TODO Auto-generated method stub
//				Toast.makeText(ComposeActivity.this, "button", Toast.LENGTH_SHORT).show();
//				Intent intent = new Intent(AddressActivity.this,MyMallActivity.class);
//				startActivity(intent);
				//������ǰActivity��
				NewAddressActivity.this.finish();
			}
		});
		try {
			Button rightButton = (Button) findViewById(R.id.actionbar_right);
			rightButton.setText("���");
			rightButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Addresses addresses = new Addresses();
					//nameTextView.getText().toString(), province.getSelectedItem().toString(),city.getSelectedItem().toString(), areaTextView.getText().toString(), numberTextView.getText().toString()
					//�����ݰ󶨵�Spinner��ͼ��
					Intent intent = getIntent();
					//��ȡbundle����
					Bundle bundle = new Bundle();
					bundle.putSerializable("newaddress", addresses);
					intent.putExtras(bundle);//����bundle����
					NewAddressActivity.this.setResult(DefinalDate.responcode, intent);
					NewAddressActivity.this.finish();
					Toast.makeText(NewAddressActivity.this, "�༭�ɹ�", Toast.LENGTH_LONG).show();
				}
			});
			
			TextView titleTextView = (TextView) findViewById(R.id.actionbar_title);
			titleTextView.setText("�����ջ���ַ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(NewAddressActivity.this, "���粻������", Toast.LENGTH_SHORT).show();
		}
	}
}
