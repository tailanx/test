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
	private EditText nameTextView;//收货人姓名
	private EditText numberTextView;//收货人电话
	private EditText areaTextView;//收货人详细地址
	private void setupShow(){
		
		//添加条目被选中监听器
		province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
			//parent既是province对象
			Spinner spinner = (Spinner)parent;
			String pro = (String)spinner.getItemAtPosition(position);

			//(处理省的市的显示)
			//将默认值与ArrayAdapter连接(从资源数组文件中获取数据)
			ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource
			(NewAddressActivity.this, R.array.citydefault, android.R.layout.simple_spinner_item);
			
			//new ArrayAdapter<CharSequence>
			// (MainActivity.this,android.R.layout.simple_spinner_item, cities);
			//获取所在省含有哪些市(从资源数组文件中获取数据)
			if(pro.equals("河北省")){

			cityAdapter = ArrayAdapter.createFromResource
			(NewAddressActivity.this, R.array.hb, android.R.layout.simple_spinner_item);
			}else if(pro.equals("北京市")){

			cityAdapter = ArrayAdapter.createFromResource
			(NewAddressActivity.this, R.array.bj, android.R.layout.simple_spinner_item);
			}else if(pro.equals("山西省")){

			cityAdapter = ArrayAdapter.createFromResource
			(NewAddressActivity.this, R.array.shx, android.R.layout.simple_spinner_item);
			}
			//绑定数据到Spinner(City)上
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
		//String[] provinces = new String[]{"-省份-","河北省","山西省","北京"};
		
		province = (Spinner)this.findViewById(R.id.province);
		city = (Spinner)this.findViewById(R.id.city);	
		nameTextView = (EditText) findViewById(R.id.new_address_item_edittext1);
		numberTextView = (EditText) findViewById(R.id.new_address_item_edittext2);
		areaTextView = (EditText) findViewById(R.id.new_address_item_edittext3);
		
		
		ArrayAdapter<CharSequence> adapter =
				ArrayAdapter.createFromResource(this, R.array.province, android.R.layout.simple_spinner_item);
		//设置下拉列表的风格
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
				//结束当前Activity；
				NewAddressActivity.this.finish();
			}
		});
		try {
			Button rightButton = (Button) findViewById(R.id.actionbar_right);
			rightButton.setText("完成");
			rightButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Addresses addresses = new Addresses();
					//nameTextView.getText().toString(), province.getSelectedItem().toString(),city.getSelectedItem().toString(), areaTextView.getText().toString(), numberTextView.getText().toString()
					//将数据绑定到Spinner视图上
					Intent intent = getIntent();
					//获取bundle对象
					Bundle bundle = new Bundle();
					bundle.putSerializable("newaddress", addresses);
					intent.putExtras(bundle);//放置bundle对象
					NewAddressActivity.this.setResult(DefinalDate.responcode, intent);
					NewAddressActivity.this.finish();
					Toast.makeText(NewAddressActivity.this, "编辑成功", Toast.LENGTH_LONG).show();
				}
			});
			
			TextView titleTextView = (TextView) findViewById(R.id.actionbar_title);
			titleTextView.setText("新增收货地址");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(NewAddressActivity.this, "网络不给力！", Toast.LENGTH_SHORT).show();
		}
	}
}
