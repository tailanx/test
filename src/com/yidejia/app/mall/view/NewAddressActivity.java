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

import android.R.integer;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.actionbarsherlock.app.SherlockActivity;

import android.app.AlertDialog.Builder;

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

public class NewAddressActivity extends SherlockActivity {
	// private DBManager dbm;
	// private SQLiteDatabase db;
	// private Spinner spinner1 = null;
	// private Spinner spinner2 = null;
	// private Spinner spinner3 = null;
	//
	// private String province = null;
	// private String city = null;
	// private String district = null;
	//
	//
	// private TextView textView;
	// public static int postion1;
	// public static int postion2;
	// public static int postion3;
	// // private Spinner province;//�ջ��˵�ַʡ
	// // private Spinner city;//�ջ��˵�ַ��
	// // private Spinner district;//�ջ��˵�ַ��
	private AddressDataManage dataManage;
	private static HashMap<String, Object> valueMap;
	private HashMap<Integer, String> valueMap3;
	private ArrayList<String> list;// 放置整个省的集合
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
	private EditText nameTextView;// �ջ�������
	private EditText numberTextView;// �ջ��˵绰
	private EditText areaTextView;// �ջ�����ϸ��ַ
	public static String addressId;

	private AlertDialog dialog;
	private TextView sheng;
	private TextView shi;
	private TextView qu;
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
	// //�����Ŀ��ѡ�м�����
	// province.setOnItemSelectedListener(new
	// AdapterView.OnItemSelectedListener() {
	//
	// @Override
	// public void onItemSelected(AdapterView<?> parent, View view,int position,
	// long id) {
	// //parent����province����
	// Spinner spinner = (Spinner)parent;
	// String pro = (String)spinner.getItemAtPosition(position);
	//
	// //(����ʡ���е���ʾ)
	// //��Ĭ��ֵ��ArrayAdapter����(����Դ�����ļ��л�ȡ���)
	// ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource
	// (NewAddressActivity.this, R.array.citydefault,
	// android.R.layout.simple_spinner_item);
	//
	// //new ArrayAdapter<CharSequence>
	// // (MainActivity.this,android.R.layout.simple_spinner_item, cities);
	// //��ȡ����ʡ������Щ��(����Դ�����ļ��л�ȡ���)
	// if(pro.equals("�ӱ�ʡ")){
	//
	// cityAdapter = ArrayAdapter.createFromResource
	// (NewAddressActivity.this, R.array.hb,
	// android.R.layout.simple_spinner_item);
	// }else if(pro.equals("������")){
	//
	// cityAdapter = ArrayAdapter.createFromResource
	// (NewAddressActivity.this, R.array.bj,
	// android.R.layout.simple_spinner_item);
	// }else if(pro.equals("ɽ��ʡ")){
	//
	// cityAdapter = ArrayAdapter.createFromResource
	// (NewAddressActivity.this, R.array.shx,
	// android.R.layout.simple_spinner_item);
	// }
	// //����ݵ�Spinner(City)��
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
	// .setTitle("ѡ��ʡ��")
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
	// builder2 = new Builder(this).setTitle("ѡ�����")
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
	// builder3 = new Builder(this).setTitle("ѡ�����")
	//
	// .setSingleChoiceItems(myAdapter3, 0, new OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// // TODO Auto-generated method stub
	// dialog.dismiss();
	// // Log.i("info", list1.size()+"spinner");
	// spinner3.setText(list3.get(which).getName());
	// Toast.makeText(NewAddressActivity.this, "ʡ����",
	// Toast.LENGTH_LONG).show();
	// }
	// }).create();
	//
	// }
	private View view;

	private void setupShow() {
		view = LayoutInflater.from(NewAddressActivity.this).inflate(
				R.layout.country_city, null);

		dialog = new Builder(NewAddressActivity.this)
				.setTitle(getResources().getString(R.string.country_city))
				.setIcon(R.drawable.ic_launcher)
				.setView(view)
				.setPositiveButton(getResources().getString(R.string.sure),
						new android.content.DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								sheng.setText(conutryString);
								shi.setText(cityString);
								qu.setText(districtString);
							}
						})
				.setNegativeButton(getResources().getString(R.string.cancel),
						null).create();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setActionbar();
		setContentView(R.layout.new_address);
		// spinner1 = (Spinner) findViewById(R.id.province);
		// spinner2 = (Spinner) findViewById(R.id.city);
		// spinner3 = (Spinner) findViewById(R.id.district);
		//
		// spinner1.setPrompt("省");
		// spinner2.setPrompt("城市");
		// spinner3.setPrompt("地区");
		//
		dataManage = new AddressDataManage(this);
		// initSpinner1();
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
		//
		nameTextView = (EditText) findViewById(R.id.new_address_item_edittext1);
		numberTextView = (EditText) findViewById(R.id.new_address_item_edittext2);
		areaTextView = (EditText) findViewById(R.id.new_address_item_edittext3);
		

		sheng = (TextView) findViewById(R.id.country1);
		shi = (TextView) findViewById(R.id.city1);
		qu = (TextView) findViewById(R.id.district1);
		
		sheng.setText(getResources().getString(R.string.sheng));
		shi.setText(getResources().getString(R.string.shi));
		qu.setText(getResources().getString(R.string.xianzhen));
	

		setupShow();
		LinearLayout linear = (LinearLayout) findViewById(R.id.new_address_linear);
		
		linear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.show();
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
		});
		
	}

	StringBuffer sb = new StringBuffer();
	Handler hanlder = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0x123) {
				sb.append(msg.obj.toString() + "\t");

			}
			String s = sb.substring(13, sb.length() - 1);
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
					key = keyIterator.next();//获取省市区
					list.add(key);

					value = dataJson.get(key);
//					 Log.i("info", value + "     value");
					// valueMap.put(key, valueMap);
					JSONObject dataJson1 = new JSONObject(value + "");
					Iterator<String> keyIterator2 = dataJson1.keys();// ��+��

					int i = 0;
					list2 = new ArrayList<String>();//市
					// valueMap2 = new HashMap<String, Object>();

					while (keyIterator2.hasNext()) {
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
//				Log.i("info", list3.size() + "    list3 size");
				// for(int j=0;j<list1.size();j++){
				// Log.i("info", list1.get(j) + "          list1");
				// }
				// Log.i("info", countries.length + "countries");
				// Log.i("info", valueMap3 + "    valueMap3");

				// Log.i("info", list + "  valueMap4 ");

				WheelView country = (WheelView) view.findViewById(R.id.country);
				
				
				Object[] countries = list.toArray();

				country.setVisibleItems(5);
				country.setAdapter(new ArrayWheelAdapter<String>(countries));
				country.setCurrentItem(0);

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
				
				final WheelView city = (WheelView) view.findViewById(R.id.city);
				city.setVisibleItems(5);
				city.setAdapter(new ArrayWheelAdapter<String>(cities[0]));
				city.setCurrentItem(0);
//				city.setCurrentItem(0);
				
				conutryString = list.get(0);
				cityString = list1.get(0).get(0);
				
				final WheelView district = (WheelView) view
						.findViewById(R.id.district);
				district.setVisibleItems(5);
				
				HashMap<String, Object> mArrayList2 = list5
						.get(0);
				
				ArrayList<String> mArrayList3 = (ArrayList<String>) mArrayList2
						.get(cityString);
				Log.i("info", mArrayList3.size() +"mArrayList3");
				Object[] districts = mArrayList3.toArray();

				district.setAdapter(new ArrayWheelAdapter<String>(
						districts));
				district.setCurrentItem(0);
				
				
				districtString = mArrayList3.get(0);
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
						Object[] districts = mArrayList3.toArray();

						district.setAdapter(new ArrayWheelAdapter<String>(
								districts));
						district.setCurrentItem(0);
						cityString = cityName;
						districtString = mArrayList3.get(0);
//						Log.i("info", conutryString + "   conutryString");
//						Log.i("info", cityString + "   cityString");
//						Log.i("info", districtString + "   districtString");
					}
				});

				ArrayList<String> mArrayList = list1.get(1);
				// String cityName = mArrayList.get(0);
				// HashMap<String, Object> mArrayList2 = list5.get(0);
				// ArrayList<String> mArrayList3 = (ArrayList<String>)
				// mArrayList2
				// .get(cityName);
				// districts = mArrayList3.toArray();
				// district.setAdapter(new
				// ArrayWheelAdapter<String>(districts));
				city.addChangingListener(new OnWheelChangedListener() {
					public void onChanged(WheelView wheel, int oldValue,
							int newValue) {
						// Log.i("info", "nihao");
						ArrayList<String> mArrayList = list1.get(a);
						Log.i("info", mArrayList.size()+"   mArrayList");
						b = newValue;
						
						String cityName = mArrayList.get(newValue);
						HashMap<String, Object> mArrayList2 = list5
								.get(newValue);
						
						ArrayList<String> mArrayList3 = (ArrayList<String>) mArrayList2
								.get(cityName);
						Log.i("info", mArrayList3.size() +"mArrayList3");
						Object[] districts = mArrayList3.toArray();

						district.setAdapter(new ArrayWheelAdapter<String>(
								districts));
						// district.setAdapter(new ArrayWheelAdapter<String>(
						// districts));
						district.setCurrentItem(0);
						cityString = cityName;
						districtString = mArrayList3.get(0);
//						Log.i("info", conutryString + "   conutryString");
//						Log.i("info", cityString + "   cityString");
//						Log.i("info", districtString + "   districtString");
					}
				});
				district.addChangingListener(new OnWheelChangedListener() {

					@Override
					public void onChanged(WheelView wheel, int oldValue,
							int newValue) {
						// TODO Auto-generated method stub
						ArrayList<String> mArrayList1 = list1.get(a);
						
						String cityName = mArrayList1.get(b);
						HashMap<String, Object> mArrayList2 = list5
								.get(newValue);
						ArrayList<String> mArrayList3 = (ArrayList<String>) mArrayList2
								.get(cityName);
						district.setCurrentItem(newValue);

						districtString = mArrayList3.get(newValue);
//						Log.i("info", conutryString + "   conutryString");
//						Log.i("info", cityString + "   cityString");
//						Log.i("info", districtString + "   districtString");

					}
				});

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

	// ArrayAdapter<CharSequence> adapter =
	// ArrayAdapter.createFromResource(this, R.array.province,
	// android.R.layout.simple_spinner_item);
	// //���������б�ķ��
	// adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	// setupShow();
	// province.setAdapter(adapter);

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
	// spinner3.setOnItemSelectedListener(new SpinnerOnSelectedListener3());
	// }
	//
	// class SpinnerOnSelectedListener1 implements OnItemSelectedListener {
	//
	// public void onItemSelected(AdapterView<?> adapterView, View view,
	// int position, long id) {
	// province = ((MyListItem) adapterView.getItemAtPosition(position))
	// .getName();
	// // if(province.endsWith("��")){
	// // Log.i("info", false+);
	// // }
	// String pcode = ((MyListItem) adapterView
	// .getItemAtPosition(position)).getPcode();
	// postion1 = position;
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
	// postion2 = position;
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
	// postion3 = position;
	// // Toast.makeText(NewAddressActivity.this,
	// // province + " " + city + " " + district, Toast.LENGTH_LONG)
	// // .show();
	// }
	//
	// public void onNothingSelected(AdapterView<?> adapterView) {
	// // TODO Auto-generated method stub
	// }
	// }
	//
	// @Override
	// protected void onDestroy() {
	// // TODO Auto-generated method stub
	// super.onDestroy();
	// }
	//
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
				// ����ǰActivity��
				NewAddressActivity.this.finish();
			}
		});
		try {
			// AddressDataManage dataManage = new
			// AddressDataManage(NewAddressActivity.this);
			Button rightButton = (Button) findViewById(R.id.actionbar_right);
			rightButton.setText("完成");
			rightButton
					.setOnClickListener(new android.view.View.OnClickListener() {

						@Override
						public void onClick(View v) {
							if (nameTextView.getText().toString() == null
									|| "".equals(nameTextView.getText()
											.toString())
									|| numberTextView.getText().toString() == null
									|| "".equals(numberTextView.getText()
											.toString())) {
								Toast.makeText(NewAddressActivity.this,
										"收货人姓名,电话不能为空 ", Toast.LENGTH_SHORT)
										.show();
							} else {

								Addresses addresses = new Addresses();

								addresses.setProvince(conutryString);
								addresses.setCity(cityString);
								addresses.setArea(districtString);
								// Log.i("info",city +"district");
								// Log.i("info",province +"district");
								// Log.i("info",district +"district");
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
												conutryString,
												cityString,
												districtString,
												areaTextView.getText()
														.toString().trim(),
												numberTextView.getText()
														.toString(),
												true,
												((MyApplication) NewAddressActivity.this
														.getApplication())
														.getToken());
								// Log.i("info", addressId+"addressId");

								if ("".equals(addressId)) {
									Toast.makeText(NewAddressActivity.this,
											"您的输入有问题", Toast.LENGTH_SHORT)
											.show();

								} else {
									// addresses.setAddressId(addressId+"");
									// ����ݰ󶨵�Spinner��ͼ��
									Intent intent = getIntent();
									// ��ȡbundle����
									Bundle bundle = new Bundle();
									bundle.putSerializable("newaddress",
											addresses);
									intent.putExtras(bundle);// ����bundle����
									NewAddressActivity.this.setResult(
											DefinalDate.responcode, intent);
									NewAddressActivity.this.finish();
									// Toast.makeText(NewAddressActivity.this,
									// "�༭�ɹ�",
									// } Toast.LENGTH_LONG).show();
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
