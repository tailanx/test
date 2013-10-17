package com.yidejia.app.mall.view;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.DBManager;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.adapter.MyAdapter;
import com.yidejia.app.mall.datamanage.AddressDataManage;
import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.model.MyListItem;
import com.yidejia.app.mall.util.DefinalDate;

public class NewAddressActivity extends SherlockActivity {
	private DBManager dbm;
	private SQLiteDatabase db;
	private Spinner spinner1 = null;
	private Spinner spinner2 = null;
	private Spinner spinner3 = null;

	private String province = null;
	private String city = null;
	private String district = null;
	
	private AddressDataManage dataManage;

	private TextView textView;
	public static int postion1;
	public static int postion2;
	public static int postion3;
	// private Spinner province;//收货人地址省
	// private Spinner city;//收货人地址市
	// private Spinner district;//收货人地址区
	private EditText nameTextView;// 收货人姓名
	private EditText numberTextView;// 收货人电话
	private EditText areaTextView;// 收货人详细地址
	public  static String addressId;
	// private List<MyListItem> list1;
	// private List<MyListItem> list2;
	// private List<MyListItem> list3;
	// private MyAdapter myAdapter1;
	// private MyAdapter myAdapter2;
	// private MyAdapter myAdapter3;
	// private AlertDialog builder1;
	// private AlertDialog builder2;
	// private AlertDialog builder3;

	// private void setupShow(){
	//
	// //添加条目被选中监听器
	// province.setOnItemSelectedListener(new
	// AdapterView.OnItemSelectedListener() {
	//
	// @Override
	// public void onItemSelected(AdapterView<?> parent, View view,int position,
	// long id) {
	// //parent既是province对象
	// Spinner spinner = (Spinner)parent;
	// String pro = (String)spinner.getItemAtPosition(position);
	//
	// //(处理省的市的显示)
	// //将默认值与ArrayAdapter连接(从资源数组文件中获取数据)
	// ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource
	// (NewAddressActivity.this, R.array.citydefault,
	// android.R.layout.simple_spinner_item);
	//
	// //new ArrayAdapter<CharSequence>
	// // (MainActivity.this,android.R.layout.simple_spinner_item, cities);
	// //获取所在省含有哪些市(从资源数组文件中获取数据)
	// if(pro.equals("河北省")){
	//
	// cityAdapter = ArrayAdapter.createFromResource
	// (NewAddressActivity.this, R.array.hb,
	// android.R.layout.simple_spinner_item);
	// }else if(pro.equals("北京市")){
	//
	// cityAdapter = ArrayAdapter.createFromResource
	// (NewAddressActivity.this, R.array.bj,
	// android.R.layout.simple_spinner_item);
	// }else if(pro.equals("山西省")){
	//
	// cityAdapter = ArrayAdapter.createFromResource
	// (NewAddressActivity.this, R.array.shx,
	// android.R.layout.simple_spinner_item);
	// }
	// //绑定数据到Spinner(City)上
	// city.setAdapter(cityAdapter);
	// }
	//
	// @Override
	// public void onNothingSelected(AdapterView<?> parent) {
	//
	// }
	//
	// });
	// }

	// private void setupShow() {
	//
	// builder1 = new Builder(this)
	// .setTitle("选择省份")
	// .setAdapter(myAdapter1, new OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// dialog.cancel();
	//
	//
	// spinner1.setText(list1.get(which).getName());
	// String pcode = ((MyListItem) myAdapter1.getItem(which))
	// .getPcode();
	// Log.i("info", pcode);
	// initSpinner2(pcode);
	// initSpinner3(pcode);
	// }
	// }).create();
	// // .setSingleChoiceItems(myAdapter1, 0, new OnClickListener() {
	// //
	// // @Override
	// // public void onClick(DialogInterface dialog, int which) {
	// // // TODO Auto-generated method stub
	// // dialog.dismiss();
	// // // Log.i("info", list1.size()+"spinner");
	// // spinner1.setText(list1.get(which).getName());
	// // String pcode = ((MyListItem) myAdapter1.getItem(which))
	// // .getPcode();
	// // Log.i("info", pcode);
	// // initSpinner2(pcode);
	// // initSpinner3(pcode);
	// // }
	// // }).create();
	//
	// builder2 = new Builder(this).setTitle("选择地区")
	// .setAdapter(myAdapter2, new OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// // TODO Auto-generated method stub
	// dialog.dismiss();
	// // Log.i("info", list1.size()+"spinner");
	// Log.i("info", list2.size() + "builder");
	// spinner2.setText(list2.get(which).getName());
	// String pcode = ((MyListItem) myAdapter2.getItem(which))
	// .getPcode();
	//
	// initSpinner3(pcode);
	// }
	// }).create();
	// builder3 = new Builder(this).setTitle("选择地区")
	//
	// .setSingleChoiceItems(myAdapter3, 0, new OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// // TODO Auto-generated method stub
	// dialog.dismiss();
	// // Log.i("info", list1.size()+"spinner");
	// spinner3.setText(list3.get(which).getName());
	// Toast.makeText(NewAddressActivity.this, "省市区",
	// Toast.LENGTH_LONG).show();
	// }
	// }).create();
	//
	// }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setActionbar();
		setContentView(R.layout.new_address);
		// setupShow();
		spinner1 = (Spinner) findViewById(R.id.province);
		spinner2 = (Spinner) findViewById(R.id.city);
		spinner3 = (Spinner) findViewById(R.id.district);

		spinner1.setPrompt("省");
		spinner2.setPrompt("城市");
		spinner3.setPrompt("地区");
		
		dataManage = new AddressDataManage(this);
		initSpinner1();
		// spinner2.setOnClickListener(new android.view.View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // Log.i("info", list2 + "builder");
		// builder2.show();
		// }
		// });

		// spinner3.setOnClickListener(new android.view.View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// builder3.show();
		// }
		// });
		//
		// spinner1.setOnClickListener(new android.view.View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// builder1.show();
		//
		//
		// }
		// });

		nameTextView = (EditText) findViewById(R.id.new_address_item_edittext1);
		numberTextView = (EditText) findViewById(R.id.new_address_item_edittext2);
		areaTextView = (EditText) findViewById(R.id.new_address_item_edittext3);

		// ArrayAdapter<CharSequence> adapter =
		// ArrayAdapter.createFromResource(this, R.array.province,
		// android.R.layout.simple_spinner_item);
		// //设置下拉列表的风格
		// adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// setupShow();
		// province.setAdapter(adapter);

	}

	public void initSpinner1() {
		dbm = new DBManager(this);
		dbm.openDatabase();
		db = dbm.getDatabase();
		List<MyListItem> list = new ArrayList<MyListItem>();

		try {
			String sql = "select * from province";
			Cursor cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
			while (!cursor.isLast()) {
				String code = cursor.getString(cursor.getColumnIndex("code"));
				byte bytes[] = cursor.getBlob(2);
				String name = new String(bytes, "gbk");
				MyListItem myListItem = new MyListItem();
				myListItem.setName(name);
				myListItem.setPcode(code);
				list.add(myListItem);
				cursor.moveToNext();
			}
			String code = cursor.getString(cursor.getColumnIndex("code"));
			byte bytes[] = cursor.getBlob(2);
			String name = new String(bytes, "gbk");
			MyListItem myListItem = new MyListItem();
			myListItem.setName(name);
			myListItem.setPcode(code);
			list.add(myListItem);

		} catch (Exception e) {
		}
		dbm.closeDatabase();
		db.close();
		MyAdapter myAdapter = new MyAdapter(this, list);
		spinner1.setAdapter(myAdapter);
		spinner1.setOnItemSelectedListener(new SpinnerOnSelectedListener1());
	}

	public void initSpinner2(String pcode) {
		dbm = new DBManager(this);
		dbm.openDatabase();
		db = dbm.getDatabase();
		List<MyListItem> list = new ArrayList<MyListItem>();

		try {
			String sql = "select * from city where pcode='" + pcode + "'";
			Cursor cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
			while (!cursor.isLast()) {
				String code = cursor.getString(cursor.getColumnIndex("code"));
				byte bytes[] = cursor.getBlob(2);
				String name = new String(bytes, "gbk");
				MyListItem myListItem = new MyListItem();
				myListItem.setName(name);
				myListItem.setPcode(code);
				list.add(myListItem);
				cursor.moveToNext();
			}
			String code = cursor.getString(cursor.getColumnIndex("code"));
			byte bytes[] = cursor.getBlob(2);
			String name = new String(bytes, "gbk");
			MyListItem myListItem = new MyListItem();
			myListItem.setName(name);
			myListItem.setPcode(code);
			list.add(myListItem);

		} catch (Exception e) {
		}
		dbm.closeDatabase();
		db.close();

		MyAdapter myAdapter = new MyAdapter(this, list);
		spinner2.setAdapter(myAdapter);
		spinner2.setOnItemSelectedListener(new SpinnerOnSelectedListener2());
	}

	public void initSpinner3(String pcode) {
		dbm = new DBManager(this);
		dbm.openDatabase();
		db = dbm.getDatabase();
		List<MyListItem> list = new ArrayList<MyListItem>();

		try {
			String sql = "select * from district where pcode='" + pcode + "'";
			Cursor cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
			while (!cursor.isLast()) {
				String code = cursor.getString(cursor.getColumnIndex("code"));
				byte bytes[] = cursor.getBlob(2);
				String name = new String(bytes, "gbk");
				MyListItem myListItem = new MyListItem();
				myListItem.setName(name);
				myListItem.setPcode(code);
				list.add(myListItem);
				cursor.moveToNext();
			}
			String code = cursor.getString(cursor.getColumnIndex("code"));
			byte bytes[] = cursor.getBlob(2);
			String name = new String(bytes, "gbk");
			MyListItem myListItem = new MyListItem();
			myListItem.setName(name);
			myListItem.setPcode(code);
			list.add(myListItem);

		} catch (Exception e) {
		}
		dbm.closeDatabase();
		db.close();

		MyAdapter myAdapter = new MyAdapter(this, list);
		spinner3.setAdapter(myAdapter);
		spinner3.setOnItemSelectedListener(new SpinnerOnSelectedListener3());
	}

	class SpinnerOnSelectedListener1 implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> adapterView, View view,
				int position, long id) {
			province = ((MyListItem) adapterView.getItemAtPosition(position))
					.getName();
//			if(province.endsWith("市")){
//				Log.i("info", false+);
//			}
			String pcode = ((MyListItem) adapterView
					.getItemAtPosition(position)).getPcode();
			postion1 = position;
			initSpinner2(pcode);
			initSpinner3(pcode);
		}

		public void onNothingSelected(AdapterView<?> adapterView) {
			// TODO Auto-generated method stub
		}
	}

	class SpinnerOnSelectedListener2 implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> adapterView, View view,
				int position, long id) {
			city = ((MyListItem) adapterView.getItemAtPosition(position))
					.getName();
			String pcode = ((MyListItem) adapterView
					.getItemAtPosition(position)).getPcode();
			postion2 = position;
			initSpinner3(pcode);
		}

		public void onNothingSelected(AdapterView<?> adapterView) {
			// TODO Auto-generated method stub
		}
	}

	class SpinnerOnSelectedListener3 implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> adapterView, View view,
				int position, long id) {
			district = ((MyListItem) adapterView.getItemAtPosition(position))
					.getName();
			postion3 = position;
//			Toast.makeText(NewAddressActivity.this,
//					province + " " + city + " " + district, Toast.LENGTH_LONG)
//					.show();
		}

		public void onNothingSelected(AdapterView<?> adapterView) {
			// TODO Auto-generated method stub
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private void setActionbar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		// getSupportActionBar().setLogo(R.drawable.back);
		getSupportActionBar().setIcon(R.drawable.back);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_common);
		// startActionMode(new
		// AnActionModeOfEpicProportions(ComposeActivity.this));
		ImageView leftButton = (ImageView) findViewById(R.id.actionbar_left);
		leftButton.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(ComposeActivity.this, "button",
				// Toast.LENGTH_SHORT).show();
				// Intent intent = new
				// Intent(AddressActivity.this,MyMallActivity.class);
				// startActivity(intent);
				// 结束当前Activity；
				NewAddressActivity.this.finish();
			}
		});
		try {
//			AddressDataManage dataManage = new AddressDataManage(NewAddressActivity.this);
			Button rightButton = (Button) findViewById(R.id.actionbar_right);
			rightButton.setText("完成");
			rightButton
					.setOnClickListener(new android.view.View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							if (nameTextView.getText().toString() == null
									|| "".equals(nameTextView.getText()
											.toString())||numberTextView.getText().toString()==null||"".equals(numberTextView.getText().toString())) {
								Toast.makeText(NewAddressActivity.this,
										"收货人姓名,电话不能为空 ", Toast.LENGTH_SHORT).show();
							} else {
								
								Addresses addresses = new Addresses();
								addresses.setProvince(province);
								addresses.setCity(city);
								addresses.setArea(district);
//								Log.i("info",city +"district");
//								Log.i("info",province +"district");
//								Log.i("info",district +"district");
								addresses.setName(nameTextView.getText()
										.toString());
								// addresses.setProvince(spinner1.getSelectedItem().toString());
								// addresses.setCity(spinner2.getSelectedItem().toString());
								addresses.setAddress(areaTextView.getText()
										.toString());
								addresses.setPhone(numberTextView.getText()
										.toString());
								
								addressId = dataManage
										.addAddress(
												((MyApplication) NewAddressActivity.this
														.getApplication())
														.getUserId(),
												nameTextView.getText()
														.toString(),
												province.trim(),
												city.trim(),
												district.trim(),
												areaTextView.getText()
														.toString().trim(),
												numberTextView.getText()
														.toString(),
												true,
												((MyApplication) NewAddressActivity.this
														.getApplication())
														.getToken());
								//	Log.i("info", addressId+"addressId");
							
								 if("".equals(addressId)){
										Toast.makeText(NewAddressActivity.this, "您的输入有问题", Toast.LENGTH_SHORT).show();

								 }else{
//								addresses.setAddressId(addressId+"");
								
								// 将数据绑定到Spinner视图上
								Intent intent = getIntent();
								// 获取bundle对象
								Bundle bundle = new Bundle();
								bundle.putSerializable("newaddress", addresses);
								intent.putExtras(bundle);// 放置bundle对象
								NewAddressActivity.this.setResult(
										DefinalDate.responcode, intent);
								NewAddressActivity.this.finish();
//								Toast.makeText(NewAddressActivity.this, "编辑成功",
//									}	Toast.LENGTH_LONG).show();
								 }
								 }
						}
					});

			TextView titleTextView = (TextView) findViewById(R.id.actionbar_title);
			titleTextView.setText("新增收货地址");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(NewAddressActivity.this, "网络不给力！",
					Toast.LENGTH_SHORT).show();
		}
	}
}
