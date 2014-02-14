package com.yidejia.app.mall.address;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
//import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;
//import com.yidejia.app.mall.datamanage.AddressDataManage;
import com.yidejia.app.mall.wheelview.ArrayWheelAdapter;
import com.yidejia.app.mall.wheelview.OnWheelChangedListener;
import com.yidejia.app.mall.wheelview.WheelView;
//import com.yidejia.app.mall.util.DefinalDate;
import com.yidejia.app.mall.widget.YLProgressDialog;

public class EditNewAddressActivity extends BaseActivity {
	private static HashMap<String, Object> valueMap;
	private HashMap<Integer, String> valueMap3;
	private ArrayList<String> list;
	private ArrayList<String> list2;// ַ
	private ArrayList<ArrayList<String>> list1;// �������������еĵ�ַ
	private ArrayList<String> list3;// ��������ÿ������ĵ�ַ
	private ArrayList<ArrayList<String>> list4;// ����������������ĵ�ַ
	private ArrayList<HashMap<String, Object>> list5;
	private String conutryString = "";
	private String cityString = "";
	private String districtString = "";
	public static String addressId;

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
	private TextView sheng;
	private TextView shi;
	private TextView qu;
	private MyApplication myApplication;
	private boolean isUpdate;
	private boolean isDefauteUpdate;
	private String id;

	private View view;
	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		myApplication = (MyApplication) getApplication();

		setActionbar();
		setContentView(R.layout.new_address);

		nameTextView = (EditText) findViewById(R.id.new_address_item_edittext1);
		numberTextView = (EditText) findViewById(R.id.new_address_item_edittext2);
		areaTextView = (EditText) findViewById(R.id.new_address_item_edittext3);

		sheng = (TextView) findViewById(R.id.country1);
		shi = (TextView) findViewById(R.id.city1);
		qu = (TextView) findViewById(R.id.district1);

		// 获取bundle对象
		Bundle bundle = this.getIntent().getExtras();
		ModelAddresses addresses = null;
		if (null != bundle) {
			addresses = (ModelAddresses) bundle.get("editaddress");
		}
		String name = "";
		String phone = "";
		String dString = "";
		id = "";
		isDefauteUpdate = false;
		if (addresses != null) {
			name = addresses.getName();
			phone = addresses.getHandset();
			dString = addresses.getAddress();
			isDefauteUpdate = addresses.getDefaultAddress();
			id = addresses.getAddressId();
			conutryString = addresses.getProvice();// 省
			cityString = addresses.getCity();// 市
			districtString = addresses.getArea();// 区
		}
		setupShow();
		LinearLayout linear = (LinearLayout) findViewById(R.id.new_address_linear);

		sheng.setText(conutryString);
		shi.setText(cityString);
		qu.setText(districtString);

		nameTextView.setText(name);
		numberTextView.setText(phone);

		areaTextView.setText(dString);

		linear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.show();
				TaskNew editAddresss = new TaskNew();
				editAddresss.execute();
			}
		});

	}

	private void setupShow() {
		view = LayoutInflater.from(EditNewAddressActivity.this).inflate(
				R.layout.country_city, null);

		dialog = new Builder(EditNewAddressActivity.this)
				.setTitle(getResources().getString(R.string.country_city))
				.setIcon(R.drawable.ic_launcher)
				.setView(view)
				.setPositiveButton(getResources().getString(R.string.sure),
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								sheng.setText(conutryString);
								shi.setText(cityString);
								qu.setText(districtString);
							}
						})
				.setNegativeButton(getResources().getString(R.string.cancel),
						null).create();
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

					while (keyIterator2.hasNext()) {
						// ����ͬʡ���������������һ��
						key2 = keyIterator2.next(); // �� ��ͬһ��ʡ�ģ�
						// Log.i("info", key2 + "     key2");
						list2.add(key2);
						i++;
						value2 = dataJson1.get(key2).toString();// ��һ�����������������һ��

						value3 = value2.split(",");
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

							list3.add(value3[j]);// ��һ�����������������һ��
						}
						valueMap.put(key2, list3);
					}
					list1.add(list2);
					list5.add(valueMap);

				}
				Log.i("info", list3.size() + "    list3 size");
				WheelView country = (WheelView) view.findViewById(R.id.country);
				Object[] countries = list.toArray();

				country.setVisibleItems(5);
				country.setAdapter(new ArrayWheelAdapter<String>(countries));

				// Log.i("info", list1.size() + "    list1");
				final Object cities[][] = new Object[list.size()][];

				for (int i = 0; i < list1.size(); i++) {
					cities[i] = list1.get(i).toArray(
							new Object[list1.get(i).size()]);
				}

				country.setCurrentItem(0);
				final WheelView city = (WheelView) view.findViewById(R.id.city);
				city.setVisibleItems(5);
				city.setAdapter(new ArrayWheelAdapter<String>(cities[0]));
				city.setCurrentItem(0);

				conutryString = list.get(0);
				cityString = list1.get(0).get(0);

				final WheelView district = (WheelView) view
						.findViewById(R.id.district);
				district.setVisibleItems(5);
				HashMap<String, Object> mArrayList2 = list5.get(0);

				ArrayList<String> mArrayList3 = (ArrayList<String>) mArrayList2
						.get(cityString);
				Log.i("info", mArrayList3.size() + "mArrayList3");
				Object[] districts = mArrayList3.toArray();

				district.setAdapter(new ArrayWheelAdapter<String>(districts));
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
						Log.i("info", conutryString + "   conutryString");
						Log.i("info", cityString + "   cityString");
						Log.i("info", districtString + "   districtString");
					}
				});

				ArrayList<String> mArrayList = list1.get(1);

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
						Object[] districts = mArrayList3.toArray();

						district.setAdapter(new ArrayWheelAdapter<String>(
								districts));
						// district.setAdapter(new ArrayWheelAdapter<String>(
						// districts));
						district.setCurrentItem(0);
						cityString = cityName;
						districtString = mArrayList3.get(0);
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
				district.setCurrentItem(0);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		};
	};


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private void setActionbar() {
		setActionbarConfig();


		TextView rightButton = (TextView) findViewById(R.id.ab_common_tv_right);
		rightButton.setVisibility(View.VISIBLE);
		rightButton.setText("完成");
		rightButton.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String nameString = nameTextView.getText().toString()
						.trim();
				String handsetString = numberTextView.getText().toString()
						.trim();
				if (TextUtils.isEmpty(nameString) || TextUtils.isEmpty(handsetString)) {
					Toast.makeText(EditNewAddressActivity.this,
							"收货人姓名,电话不能为空 ", Toast.LENGTH_SHORT).show();
				} else {
					String shengString = sheng.getText().toString().trim();
					String shiString = shi.getText().toString().trim();
					String quString = qu.getText().toString().trim();
					String areaString = areaTextView.getText().toString()
							.trim();
					String userId = myApplication.getUserId();
					String token = myApplication.getToken();
					//联网提交数据
					saveAddr(userId, nameString, handsetString, shengString, shiString, quString, areaString, id, token);

				}
			}

		});

		TextView titleTextView = (TextView) findViewById(R.id.ab_common_title);
		titleTextView.setText("编辑收货地址");
	}
	
	private void saveAddr(final String customer_id, final String customer_name,
			final String handset, final String province, final String city, final String district,
			final String address, final String recipient_id, final String token){
		try {
			String nameTemp = URLEncoder.encode(customer_name, "UTF-8");
			String proTemp = URLEncoder.encode(province, "UTF-8");
			String cityTemp = URLEncoder.encode(city, "UTF-8");
			String addrTemp = URLEncoder.encode(address, "UTF-8");
			String disTemp = URLEncoder.encode(district, "UTF-8");
			String param = new JNICallBack().getHttp4SaveAddress(customer_id,
					nameTemp, handset, proTemp, cityTemp, disTemp, addrTemp,
					recipient_id, token);
			String url = new JNICallBack().HTTPURL;
			
			HttpClientUtil httpClientUtil = new HttpClientUtil();
			httpClientUtil.getHttpResp(url, param, new IHttpResp() {
				
				@Override
				public void success(String content) {
					ParseAddressJson parseAddressJson = new ParseAddressJson();
					boolean isSuccess = parseAddressJson.parseSaveJson(content);
					if(isSuccess){
						String id = parseAddressJson.getRecipient_id();
						ModelAddresses modelAddresses = newAddressModel(id, customer_name, handset, province, city, district, address);
						finishIntent(modelAddresses);
					} else {
						String message = parseAddressJson.getIsSuccessString();
						Toast.makeText(EditNewAddressActivity.this, message, Toast.LENGTH_SHORT).show();
					}
				}
			});
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private void finishIntent(ModelAddresses modelAddresses){
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putSerializable("addresses1", modelAddresses);
		intent.putExtras(bundle);
		this.setResult(Consts.AddressResponseCode, intent);
		finish();
	}
	
	/**构造一个收货地址**/
	private ModelAddresses newAddressModel(String recipient_id, String name,
			String handset, String province, String city, String area,
			String maddress) {
		ModelAddresses addresses = new ModelAddresses();
		addresses.setAddressId(recipient_id);
		addresses.setName(name);
		addresses.setHandset(handset);
		addresses.setPhone(handset);
		addresses.setProvince(province);
		addresses.setCity(city);
		addresses.setArea(area);
		addresses.setAddress(maddress);
		return addresses;
	}

	private class TaskNew extends AsyncTask<Void, Void, Boolean> {
		private ProgressDialog bar;

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result) {
				bar.dismiss();
			}

		}

		// 显示进度的提示
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			bar = (ProgressDialog) new YLProgressDialog(
					EditNewAddressActivity.this).createLoadingDialog(
					EditNewAddressActivity.this, null);
			bar.setOnCancelListener(new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
					cancel(true);
				}
			});
		}

		// 完成实际的下载
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
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
					int i = 0;
					while ((line = br.readLine()) != null) {
						if (i == 0) {
							Message msg = new Message();
							msg.what = 0x123;
							msg.obj = line;
							hanlder.sendMessage(msg);
							i++;
							return true;
						}
					}

				} else {
					Toast.makeText(EditNewAddressActivity.this,
							getResources().getString(R.string.no_network),
							Toast.LENGTH_SHORT).show();
					return false;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "新建或编辑地址页面");
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "新建或编辑地址页面");
	}
}