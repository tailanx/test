package com.yidejia.app.mall.view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
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
import com.yidejia.app.mall.address.ArrayWheelAdapter;
import com.yidejia.app.mall.address.OnWheelChangedListener;
import com.yidejia.app.mall.address.WheelView;
import com.yidejia.app.mall.datamanage.AddressDataManage;
import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.model.MyListItem;
import com.yidejia.app.mall.util.DefinalDate;

public class EditNewAddressActivity extends SherlockActivity {
	// private DBManager dbm;
	// private SQLiteDatabase db;
	// private TextView textView;
	// // private Spinner province;
	// // private Spinner city;
	private AddressDataManage dataManage;
	private static HashMap<String, Object> valueMap;
	private HashMap<Integer, String> valueMap3;
	private ArrayList<String> list;
	private ArrayList<String> list2;// ַ
	private ArrayList<ArrayList<String>> list1;// �������������еĵ�ַ
	private ArrayList<String> list3;// ��������ÿ������ĵ�ַ
	private ArrayList<ArrayList<String>> list4;// ����������������ĵ�ַ
	private ArrayList<HashMap<String, Object>> list5;
	private String conutryString;
	private String cityString;
	private String districtString;

	private Map<String, Object> valueMap2;
	private String key2;
	// private Object value2;
	private String value2;
	private String[] value3;
	private String key;
	private Object value;
	private int a;
	private int b;
	private String keyString[] = null;

	Object districts[] = null;
	private EditText nameTextView;// 收货人姓名
	private EditText numberTextView;// 收货人电话
	private EditText areaTextView;// 收货人地址
	// private Spinner spinner1 = null;
	// private Spinner spinner2 = null;
	// private Spinner spinner3 = null;
	// private String province = null;
	// private String city = null;
	// private String district = null;
	// private AddressDataManage dataManage;
	 private boolean isUpdate;
	 private boolean isDefauteUpdate;
	 private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dataManage = new AddressDataManage(this);

		setActionbar();
		setContentView(R.layout.new_address);
		
		// String[] provinces = new String[]{"-省份-","河北省","山西省","北京"};
		/*
		 * spinner1 = (Spinner) findViewById(R.id.province); spinner2 =
		 * (Spinner) findViewById(R.id.city); spinner3 = (Spinner)
		 * findViewById(R.id.district); initSpinner1();
		 */

		nameTextView = (EditText) findViewById(R.id.new_address_item_edittext1);
		numberTextView = (EditText) findViewById(R.id.new_address_item_edittext2);
		areaTextView = (EditText) findViewById(R.id.new_address_item_edittext3);
		

		// spinner1.setPrompt("省");
		// spinner2.setPrompt("城市");
		// spinner3.setPrompt("地区");

		// 获取bundle对象
		Bundle bundle = this.getIntent().getExtras();
		Addresses addresses = (Addresses) bundle.get("editaddress");
		String name = addresses.getName();
		String phone = addresses.getHandset();
		isDefauteUpdate = addresses.getDefaultAddress();
		id = addresses.getAddressId();
		// String provice = addresses.getProvice();
		// String city = addresses.getCity();
		// String area = addresses.getArea();
		String dString = addresses.getAddress();// 详细地址

		nameTextView.setText(name);
		numberTextView.setText(phone);

		areaTextView.setText(dString);
		new Thread() {
			public void run() {
				try {
					String uri = "http://static.atido.com/min/b=js&f=helper/address.js";
					HttpGet httpRequst = new HttpGet(uri);
					HttpClient httpClient = new DefaultHttpClient();
					HttpResponse httpResponse = httpClient.execute(httpRequst);
					HttpEntity entity = httpResponse.getEntity();

					if (entity != null) {
						BufferedReader br = new BufferedReader(
								new InputStreamReader(entity.getContent()));
						String line = null;
						// Log.i(MainActivity.class.getName(),line.toString());
						int i = 0;
						while ((line = br.readLine()) != null) {
							if (i == 0) {
								Message msg = new Message();
								msg.what = 0x123;
								msg.obj = line;
								hanlder.sendMessage(msg);
								i++;
							}
						}

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();

	}

	StringBuffer sb = new StringBuffer();
	Handler hanlder = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0x123) {
				sb.append(msg.obj.toString() + "\t");
				// sb.substring(10, 20);
				// textView.append(msg.obj.toString()+"\ttt");
				// String id = msg.obj.toString()+"\t";
				// Log.i("info", sb.toString() + "");
			}
			String s = sb.substring(13, sb.length() - 1);
			// Log.i("info", s.length() + "");
			try {
				JSONObject dataJson = new JSONObject(s);
				Iterator<String> keyIterator = dataJson.keys();
				// int j = 0;
				valueMap = new HashMap<String, Object>();
				valueMap3 = new HashMap<Integer, String>();
				list = new ArrayList<String>();// province
				list1 = new ArrayList<ArrayList<String>>();

				list4 = new ArrayList<ArrayList<String>>();
				list5 = new ArrayList<HashMap<String, Object>>();
				while (keyIterator.hasNext()) {
					key = keyIterator.next();// ʡ
					list.add(key);

					value = dataJson.get(key);
					// Log.i("info", value + "     value");
					// valueMap.put(key, valueMap);
					JSONObject dataJson1 = new JSONObject(value + "");
					Iterator<String> keyIterator2 = dataJson1.keys();// ��+��

					int i = 0;
					list2 = new ArrayList<String>();
					// valueMap2 = new HashMap<String, Object>();

					while (keyIterator2.hasNext()) {
						// ����ͬʡ���������������һ��
						key2 = keyIterator2.next(); // �� ��ͬһ��ʡ�ģ�
						// Log.i("info", key2 + "     key2");
						list2.add(key2);
						i++;
						// value2 = dataJson1.get(key2);
						value2 = dataJson1.get(key2).toString();// ��һ�����������������һ��
						// value2.replaceAll("[", "");
						// value2.replaceAll("]", "");
						// value2.replaceAll("\"", "");
						// value2 = value2.substring(0, value2.length()-1);
						// if(value2 == null || "".equals(value2))continue;
						value3 = value2.split(",");
						// Log.i("info", value2 + "    value2");

						// for(int j = 0;j<value2.length();j++){
						// value3 = value2.valueOf(j);
						// }
						// cities[i] = ;
						// if(value2==null||"".equals(value2)){
						// list3.add("bbb");
						// }
						// value3 = value2.toString();
						list3 = new ArrayList<String>();
						for (int j = 0; j < value3.length; j++) {
							if (value3[j].contains("[")) {
								value3[j] = value3[j].substring(1);
							}
							if (value3[j].endsWith("\"]")) {
								value3[j] = value3[j].substring(0,
										value3[j].length() - 1);
							}
							if (value3[j].endsWith("]")) {
								value3[j] = "";
							}
							if (value3[j].contains("\"")) {
								value3[j] = value3[j].substring(1,
										value3[j].length() - 1);
							}
							// if(value3[j].contains("[")&&value3[j].contains("\"")){
							// value3[j] = value3[j].substring(2,
							// value3[j].length()-1);
							// }i

							// Log.i("info", value3[j].charAt(0) +"aadsa");
							// if(value3[j].charAt(0)=='['||value3[j].charAt(value3[j].length())==']')
							// value3[j].re
							// value3[j].replace('[', '');
							// value3[j].
							// Log.i("info", value3[j] + "    value3");
							list3.add(value3[j]);// ��һ�����������������һ��
						}
						// list4.add(list3);
						valueMap.put(key2, list3);
						// value2 = dataJson1.get(key2);

						// list3.clear();
					}
					// Log.i("info", list2.size() + "    list3");
					// Log.i("info", list3.size() + "    list3");
					//
					// Log.i("info", list4.size() + "list4 size");
					list1.add(list2);
					list5.add(valueMap);

				}
				Log.i("info", list3.size() + "    list3 size");
				// for(int j=0;j<list1.size();j++){
				// Log.i("info", list1.get(j) + "          list1");
				// }
				// Log.i("info", countries.length + "countries");
				// Log.i("info", valueMap3 + "    valueMap3");

				// Log.i("info", list + "  valueMap4 ");
				WheelView country = (WheelView) findViewById(R.id.country);
				Object[] countries = list.toArray();

				country.setVisibleItems(5);
				country.setAdapter(new ArrayWheelAdapter<String>(countries));

				// Log.i("info", list1.size() + "    list1");
				final Object cities[][] = new Object[list.size()][];

				for (int i = 0; i < list1.size(); i++) {
					cities[i] = list1.get(i).toArray(
							new Object[list1.get(i).size()]);
				}
				// final String districts[][] = new String[][] {
				// new String[] { "New York2", "Washing2", "Chicago2",
				// "Atlanta2",
				// "Orlando2" },
				// new String[] { "Ottawa2", "Vancouv2", "Toronto2", "Windsor2",
				// "Montreal2" },
				// new String[] { "Kiev2", "Dnipro2", "Lviv2", "Kharkiv2" },
				// new String[] { "Paris2", "Bordeaux2" }, };
				//
				// final Object districts[][] = new Object[list4.size()][] ;
				// for(int i = 0;i<list4.size();i++){
				// districts[i] = list4.get(i).toArray(new
				// Object[list4.get(i).size()]);
				// }
				//
				final WheelView city = (WheelView) findViewById(R.id.city);
				city.setVisibleItems(5);
				city.setAdapter(new ArrayWheelAdapter<String>(cities[3]));
				city.setCurrentItem(0);
				final WheelView district = (WheelView) findViewById(R.id.district);
				district.setVisibleItems(5);

				conutryString = list.get(1);
				country.addChangingListener(new OnWheelChangedListener() {
					public void onChanged(WheelView wheel, int oldValue,
							int newValue) {
						a = newValue;
						city.setAdapter(new ArrayWheelAdapter<String>(
								cities[newValue]));
						city.setCurrentItem(0);
						conutryString = list.get(newValue);

						ArrayList<String> mArrayList = list1.get(newValue);
						String cityName = mArrayList.get(0);

						HashMap<String, Object> mArrayList2 = list5
								.get(newValue);
						ArrayList<String> mArrayList3 = (ArrayList<String>) mArrayList2
								.get(cityName);
						districts = mArrayList3.toArray();

						district.setAdapter(new ArrayWheelAdapter<String>(
								districts));
						district.setCurrentItem(0);
						cityString = cityName;
						districtString = mArrayList3.get(0);
						Log.i("info", conutryString + "   conutryString");
						Log.i("info", cityString + "   cityString");
						Log.i("info", districtString + "   districtString");
					}
				});

				country.setCurrentItem(1);
				city.setCurrentItem(1);
				ArrayList<String> mArrayList = list1.get(1);
				// String cityName = mArrayList.get(0);
				// HashMap<String, Object> mArrayList2 = list5.get(0);
				// ArrayList<String> mArrayList3 = (ArrayList<String>)
				// mArrayList2
				// .get(cityName);
				// districts = mArrayList3.toArray();
				// district.setAdapter(new
				// ArrayWheelAdapter<String>(districts));
				cityString = list1.get(1).get(0);
				city.addChangingListener(new OnWheelChangedListener() {
					public void onChanged(WheelView wheel, int oldValue,
							int newValue) {
						// Log.i("info", "nihao");
						ArrayList<String> mArrayList = list1.get(a);
						b = newValue;
						String cityName = mArrayList.get(newValue);
						HashMap<String, Object> mArrayList2 = list5
								.get(newValue);
						ArrayList<String> mArrayList3 = (ArrayList<String>) mArrayList2
								.get(cityName);
						districts = mArrayList3.toArray();

						district.setAdapter(new ArrayWheelAdapter<String>(
								districts));
						// district.setAdapter(new ArrayWheelAdapter<String>(
						// districts));
						district.setCurrentItem(1);
						cityString = cityName;
						districtString = mArrayList3.get(1);
						Log.i("info", conutryString + "   conutryString");
						Log.i("info", cityString + "   cityString");
						Log.i("info", districtString + "   districtString");
					}
				});
				Log.i("info", cityString + "     cityString");
				district.addChangingListener(new OnWheelChangedListener() {

					@Override
					public void onChanged(WheelView wheel, int oldValue,
							int newValue) {
						// TODO Auto-generated method stub
						ArrayList<String> mArrayList = list1.get(a);
						String cityName = mArrayList.get(b);
						HashMap<String, Object> mArrayList2 = list5
								.get(newValue);
						ArrayList<String> mArrayList3 = (ArrayList<String>) mArrayList2
								.get(cityName);
						district.setCurrentItem(newValue);

						districtString = mArrayList3.get(newValue);
						Log.i("info", conutryString + "   conutryString");
						Log.i("info", cityString + "   cityString");
						Log.i("info", districtString + "   districtString");

					}
				});

				Log.i("info", districtString + "   districtString");
				district.setCurrentItem(1);
				// Message ms = new Message();
				// ms.what = 000;
				// hanlder.sendMessage(ms);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// JSONObject response = dataJson.getJSONObject("�Ϻ�");
			//
			// JSONArray dataArray= response.getJSONArray();

		};
	};

	

	// public void initSpinner1() {
	// dbm = new DBManager(this);
	// dbm.openDatabase();
	// db = dbm.getDatabase();
	// List<MyListItem> list = new ArrayList<MyListItem>();
	//
	// try {
	// String sql = "select * from province";
	// Cursor cursor = db.rawQuery(sql, null);
	// cursor.moveToFirst();
	// while (!cursor.isLast()) {
	// String code = cursor.getString(cursor.getColumnIndex("code"));
	// byte bytes[] = cursor.getBlob(2);
	// String name = new String(bytes, "gbk");
	// MyListItem myListItem = new MyListItem();
	// myListItem.setName(name);
	// myListItem.setPcode(code);
	// list.add(myListItem);
	// cursor.moveToNext();
	// }
	// String code = cursor.getString(cursor.getColumnIndex("code"));
	// byte bytes[] = cursor.getBlob(2);
	// String name = new String(bytes, "gbk");
	// MyListItem myListItem = new MyListItem();
	// myListItem.setName(name);
	// myListItem.setPcode(code);
	// list.add(myListItem);
	//
	// } catch (Exception e) {
	// }
	// dbm.closeDatabase();
	// db.close();
	// MyAdapter myAdapter = new MyAdapter(this, list);
	// spinner1.setAdapter(myAdapter);
	// spinner1.setSelection(new NewAddressActivity().postion1);
	// spinner1.setOnItemSelectedListener(new SpinnerOnSelectedListener1());
	// }
	//
	// public void initSpinner2(String pcode) {
	// dbm = new DBManager(this);
	// dbm.openDatabase();
	// db = dbm.getDatabase();
	// List<MyListItem> list = new ArrayList<MyListItem>();
	//
	// try {
	// String sql = "select * from city where pcode='" + pcode + "'";
	// Cursor cursor = db.rawQuery(sql, null);
	// cursor.moveToFirst();
	// while (!cursor.isLast()) {
	// String code = cursor.getString(cursor.getColumnIndex("code"));
	// byte bytes[] = cursor.getBlob(2);
	// String name = new String(bytes, "gbk");
	// MyListItem myListItem = new MyListItem();
	// myListItem.setName(name);
	// myListItem.setPcode(code);
	// list.add(myListItem);
	// cursor.moveToNext();
	// }
	// String code = cursor.getString(cursor.getColumnIndex("code"));
	// byte bytes[] = cursor.getBlob(2);
	// String name = new String(bytes, "gbk");
	// MyListItem myListItem = new MyListItem();
	// myListItem.setName(name);
	// myListItem.setPcode(code);
	// list.add(myListItem);
	//
	// } catch (Exception e) {
	// }
	// dbm.closeDatabase();
	// db.close();
	//
	// MyAdapter myAdapter = new MyAdapter(this, list);
	// spinner2.setAdapter(myAdapter);
	// spinner2.setSelection(new NewAddressActivity().postion2);
	// spinner2.setOnItemSelectedListener(new SpinnerOnSelectedListener2());
	// }
	//
	// public void initSpinner3(String pcode) {
	// dbm = new DBManager(this);
	// dbm.openDatabase();
	// db = dbm.getDatabase();
	// List<MyListItem> list = new ArrayList<MyListItem>();
	//
	// try {
	// String sql = "select * from district where pcode='" + pcode + "'";
	// Cursor cursor = db.rawQuery(sql, null);
	// cursor.moveToFirst();
	// while (!cursor.isLast()) {
	// String code = cursor.getString(cursor.getColumnIndex("code"));
	// byte bytes[] = cursor.getBlob(2);
	// String name = new String(bytes, "gbk");
	// MyListItem myListItem = new MyListItem();
	// myListItem.setName(name);
	// myListItem.setPcode(code);
	// list.add(myListItem);
	// cursor.moveToNext();
	// }
	// String code = cursor.getString(cursor.getColumnIndex("code"));
	// byte bytes[] = cursor.getBlob(2);
	// String name = new String(bytes, "gbk");
	// MyListItem myListItem = new MyListItem();
	// myListItem.setName(name);
	// myListItem.setPcode(code);
	// list.add(myListItem);
	//
	// } catch (Exception e) {
	// }
	// dbm.closeDatabase();
	// db.close();
	//
	// MyAdapter myAdapter = new MyAdapter(this, list);
	// spinner3.setAdapter(myAdapter);
	// spinner3.setSelection(new NewAddressActivity().postion3);
	// spinner3.setOnItemSelectedListener(new SpinnerOnSelectedListener3());
	// }

	// class SpinnerOnSelectedListener1 implements OnItemSelectedListener {
	//
	// public void onItemSelected(AdapterView<?> adapterView, View view,
	// int position, long id) {
	// province = ((MyListItem) adapterView.getItemAtPosition(position))
	// .getName();
	// String pcode = ((MyListItem) adapterView
	// .getItemAtPosition(position)).getPcode();
	//
	// initSpinner2(pcode);
	// initSpinner3(pcode);
	// }
	//
	// public void onNothingSelected(AdapterView<?> adapterView) {
	// // TODO Auto-generated method stub
	// }
	// }
	//
	// class SpinnerOnSelectedListener2 implements OnItemSelectedListener {
	//
	// public void onItemSelected(AdapterView<?> adapterView, View view,
	// int position, long id) {
	// city = ((MyListItem) adapterView.getItemAtPosition(position))
	// .getName();
	// String pcode = ((MyListItem) adapterView
	// .getItemAtPosition(position)).getPcode();
	//
	// initSpinner3(pcode);
	// }
	//
	// public void onNothingSelected(AdapterView<?> adapterView) {
	// // TODO Auto-generated method stub
	// }
	// }
	//
	// class SpinnerOnSelectedListener3 implements OnItemSelectedListener {
	//
	// public void onItemSelected(AdapterView<?> adapterView, View view,
	// int position, long id) {
	// district = ((MyListItem) adapterView.getItemAtPosition(position))
	// .getName();
	// // Toast.makeText(NewAddressActivity.this,
	// // province + " " + city + " " + district, Toast.LENGTH_LONG)
	// // .show();
	// }
	//
	// public void onNothingSelected(AdapterView<?> adapterView) {
	// // TODO Auto-generated method stub
	// }
	// }

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
		leftButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(ComposeActivity.this, "button",
				// Toast.LENGTH_SHORT).show();
				// Intent intent = new
				// Intent(AddressActivity.this,MyMallActivity.class);
				// startActivity(intent);
				// 结束当前Activity；
				EditNewAddressActivity.this.finish();
			}
		});
		Button rightButton = (Button) findViewById(R.id.actionbar_right);
		rightButton.setText("完成");
		rightButton.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (nameTextView.getText().toString() == null
						|| "".equals(nameTextView.getText().toString())
						|| numberTextView.getText().toString() == null
						|| "".equals(numberTextView.getText().toString())) {
					Toast.makeText(EditNewAddressActivity.this,
							"收货人姓名,电话不能为空 ", Toast.LENGTH_SHORT).show();
				} else {

					Addresses addresses = new Addresses();
					addresses.setProvince(conutryString);
					addresses.setCity(cityString);
					addresses.setArea(districtString);
					// Log.i("info",city +"district");
					// Log.i("info",province +"district");
					// Log.i("info",district +"district");
					addresses.setName(nameTextView.getText().toString());
					// addresses.setProvince(spinner1.getSelectedItem().toString());
					// addresses.setCity(spinner2.getSelectedItem().toString());
					addresses.setAddress(areaTextView.getText().toString());
					addresses.setHandset(numberTextView.getText().toString());

					isUpdate = dataManage.updateAddress(
							((MyApplication) EditNewAddressActivity.this
									.getApplicationContext()).getUserId(),
							nameTextView.getText().toString(), conutryString,
							cityString, districtString, areaTextView
									.getText().toString().trim(),
							numberTextView.getText().toString(),
							isDefauteUpdate, id,
							((MyApplication) EditNewAddressActivity.this
									.getApplicationContext()).getToken());

					addresses.setAddressId(new AddressDataManage(
							EditNewAddressActivity.this).getAddressId() + "");

					 Log.i("info", isUpdate+"addressId");

					if (!isUpdate) {
						Toast.makeText(EditNewAddressActivity.this, "您的输入有问题",
								Toast.LENGTH_SHORT).show();

					} else {
						// addresses.setAddressId(addressId+"");

						// 将数据绑定到Spinner视图上
						Intent intent = getIntent();
						// 获取bundle对象
						Bundle bundle = new Bundle();
						bundle.putSerializable("newaddress", addresses);
						intent.putExtras(bundle);// 放置bundle对象
						EditNewAddressActivity.this.setResult(
								DefinalDate.responcode1, intent);
						EditNewAddressActivity.this.finish();
						
					}
				}
			}
		});

		TextView titleTextView = (TextView) findViewById(R.id.actionbar_title);
		titleTextView.setText("编辑收货地址");
	}
}
