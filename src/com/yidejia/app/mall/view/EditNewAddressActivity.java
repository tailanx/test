package com.yidejia.app.mall.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import com.yidejia.app.mall.datamanage.AddressDataManage;
import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.model.MyListItem;
import com.yidejia.app.mall.util.DefinalDate;
import com.yidejia.app.mall.view.NewAddressActivity.SpinnerOnSelectedListener1;
import com.yidejia.app.mall.view.NewAddressActivity.SpinnerOnSelectedListener2;
import com.yidejia.app.mall.view.NewAddressActivity.SpinnerOnSelectedListener3;

public class EditNewAddressActivity extends SherlockActivity {
	private DBManager dbm;
	private SQLiteDatabase db;
	private TextView textView;
	// private Spinner province;
	// private Spinner city;
	private EditText nameTextView;// �ջ�������
	private EditText numberTextView;// �ջ��˵绰
	private EditText areaTextView;// �ջ��˵�ַ
	private Spinner spinner1 = null;
	private Spinner spinner2 = null;
	private Spinner spinner3 = null;
	private String province = null;
	private String city = null;
	private String district = null;
	private AddressDataManage dataManage;
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
		// String[] provinces = new String[]{"-ʡ��-","�ӱ�ʡ","ɽ��ʡ","����"};
		spinner1 = (Spinner) findViewById(R.id.province);
		spinner2 = (Spinner) findViewById(R.id.city);
		spinner3 = (Spinner) findViewById(R.id.district);
		initSpinner1();

		nameTextView = (EditText) findViewById(R.id.new_address_item_edittext1);
		numberTextView = (EditText) findViewById(R.id.new_address_item_edittext2);
		areaTextView = (EditText) findViewById(R.id.new_address_item_edittext3);
		
		spinner1.setPrompt("ʡ");
		spinner2.setPrompt("����");
		spinner3.setPrompt("����");

		// ��ȡbundle����
		Bundle bundle = this.getIntent().getExtras();
		Addresses addresses = (Addresses) bundle.get("editaddress");
		String name = addresses.getName();
		String phone = addresses.getHandset();
		isDefauteUpdate = addresses.getDefaultAddress();
		id = addresses.getAddressId();
		// String provice = addresses.getProvice();
		// String city = addresses.getCity();
		// String area = addresses.getArea();
		String dString = addresses.getAddress();// ��ϸ��ַ

		nameTextView.setText(name);
		numberTextView.setText(phone);

		areaTextView.setText(dString);

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
		spinner1.setSelection(new NewAddressActivity().postion1);
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
		spinner2.setSelection(new NewAddressActivity().postion2);
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
		spinner3.setSelection(new NewAddressActivity().postion3);
		spinner3.setOnItemSelectedListener(new SpinnerOnSelectedListener3());
	}

	class SpinnerOnSelectedListener1 implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> adapterView, View view,
				int position, long id) {
			province = ((MyListItem) adapterView.getItemAtPosition(position))
					.getName();
			String pcode = ((MyListItem) adapterView
					.getItemAtPosition(position)).getPcode();

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
			// Toast.makeText(NewAddressActivity.this,
			// province + " " + city + " " + district, Toast.LENGTH_LONG)
			// .show();
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
		leftButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(ComposeActivity.this, "button",
				// Toast.LENGTH_SHORT).show();
				// Intent intent = new
				// Intent(AddressActivity.this,MyMallActivity.class);
				// startActivity(intent);
				// ������ǰActivity��
				EditNewAddressActivity.this.finish();
			}
		});
		Button rightButton = (Button) findViewById(R.id.actionbar_right);
		rightButton.setText("���");
		rightButton.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (nameTextView.getText().toString() == null
						|| "".equals(nameTextView.getText().toString())
						|| numberTextView.getText().toString() == null
						|| "".equals(numberTextView.getText().toString())) {
					Toast.makeText(EditNewAddressActivity.this,
							"�ջ�������,�绰����Ϊ�� ", Toast.LENGTH_SHORT).show();
				} else {
					
					Addresses addresses = new Addresses();
					addresses.setProvince(province);
					addresses.setCity(city);
					addresses.setArea(district);
					// Log.i("info",city +"district");
					// Log.i("info",province +"district");
					// Log.i("info",district +"district");
					addresses.setName(nameTextView.getText().toString());
					// addresses.setProvince(spinner1.getSelectedItem().toString());
					// addresses.setCity(spinner2.getSelectedItem().toString());
					addresses.setAddress(areaTextView.getText().toString());
					addresses.setHandset(numberTextView.getText().toString());

					isUpdate = dataManage.updateAddress(new MyApplication().getUserId(),
							nameTextView.getText().toString(), province.trim(),
							city.trim(), district.trim(), areaTextView
									.getText().toString().trim(),
							numberTextView.getText().toString(),
							isDefauteUpdate,
							id);
						
					addresses.setAddressId(new AddressDataManage(EditNewAddressActivity.this).getAddressId()+"");
					
//					 Log.i("info", isUpdate+"addressId");

					if (!isUpdate) {
						Toast.makeText(EditNewAddressActivity.this, "��������������",
								Toast.LENGTH_SHORT).show();

					} else {
						// addresses.setAddressId(addressId+"");

						// �����ݰ󶨵�Spinner��ͼ��
						Intent intent = getIntent();
						// ��ȡbundle����
						Bundle bundle = new Bundle();
						bundle.putSerializable("newaddress", addresses);
						intent.putExtras(bundle);// ����bundle����
						EditNewAddressActivity.this.setResult(
								DefinalDate.responcode1, intent);
						EditNewAddressActivity.this.finish();
						// Toast.makeText(NewAddressActivity.this, "�༭�ɹ�",
						// } Toast.LENGTH_LONG).show();
					}
				}
			}
		});

		TextView titleTextView = (TextView) findViewById(R.id.actionbar_title);
		titleTextView.setText("�༭�ջ���ַ");
	}
}
