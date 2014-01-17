package com.yidejia.app.mall.phone;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts.Photo;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.util.Consts;

/**
 * 用来获取手机的联系人和sd卡上的联系ren
 * 
 * @author Administrator
 * 
 */
@SuppressLint("InlinedApi")
public class PhoneContactActivity extends Activity {
	private Context context;
	/** 联系人显示名称 **/
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;
	/** 电话号码 **/
	private static final int PHONES_NUMBER_INDEX = 1;
	/** 头像ID **/
	private static final int PHONES_PHOTO_ID_INDEX = 2;

	/** 联系人的ID **/
	private static final int PHONES_CONTACT_ID_INDEX = 3;

	/** 获取库Phon表字段 **/
	private static final String[] PHONES_PROJECTION = new String[] {
			Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID };
	// 联系人名字
	private List<String> mContactsName = new ArrayList<String>();
	// 联系人号码
	private List<String> mContactsNumber = new ArrayList<String>();
	// 联系人头像
	private List<Bitmap> mContactsPic = new ArrayList<Bitmap>();
	private ListView mlistView;
	private ContactAdapter contactAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.context = this;
		setContentView(R.layout.contacts);
		getPhoneContacts();
		// getSIMContacts();
		mlistView = (ListView) findViewById(R.id.lv_contacts);
		contactAdapter = new ContactAdapter(this, mContactsName,
				mContactsNumber, mContactsPic);
		mlistView.setAdapter(contactAdapter);
		mlistView.setOnItemClickListener(new MySelect());
	}

	/**
	 * 得到手机联系人信息
	 */
	private void getPhoneContacts() {
		ContentResolver resolver = context.getContentResolver();
		// 获取手机联系人
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,
				PHONES_PROJECTION, null, null, null);
		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {
				// 获取到联系人手机号码
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				if (TextUtils.isEmpty(phoneNumber)) {
					continue;
				}
				// 得到联系人名称
				String phoneName = phoneCursor
						.getString(PHONES_DISPLAY_NAME_INDEX);
				// 得到联系人的Id
				Long phoneId = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);
				// 得到联系人图片
				Long phonePic = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);
				Bitmap bm = null;
				if (phonePic > 0) {
					Uri uri = ContentUris.withAppendedId(
							ContactsContract.Contacts.CONTENT_URI, phoneId);
					InputStream input = ContactsContract.Contacts
							.openContactPhotoInputStream(resolver, uri);
					bm = BitmapFactory.decodeStream(input);
				} else {
					bm = BitmapFactory.decodeResource(context.getResources(),
							R.drawable.ic_launcher);
				}
				mContactsName.add(phoneName);
				mContactsNumber.add(phoneNumber);
				mContactsPic.add(bm);
			}
			phoneCursor.close();
		}
	}

	/**
	 * 获取手机SIM卡的联系人的信息
	 */
	private void getSIMContacts() {
		ContentResolver resolver = context.getContentResolver();
		Uri uri = Uri.parse("content://icc//adn");
		Cursor cursorContacts = resolver.query(uri, PHONES_PROJECTION, null,
				null, null);
		if (cursorContacts != null) {
			while (cursorContacts.moveToNext()) {
				// 获取手机号码
				String phoneNumber = cursorContacts
						.getString(PHONES_NUMBER_INDEX);
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				// 获取联系人姓名
				String phoneContacts = cursorContacts
						.getString(PHONES_CONTACT_ID_INDEX);
				mContactsName.add(phoneContacts);
				mContactsNumber.add(phoneNumber);

			}
			cursorContacts.close();
		}
	}

	private class MySelect implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			String number = (String) contactAdapter.getItem(position);
			Intent intent = new Intent(PhoneContactActivity.this,
					PhoneActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("number", number);
			intent.putExtras(bundle);
			PhoneContactActivity.this.setResult(Consts.CONSTACT_RESPONSE,
					intent);
			PhoneContactActivity.this.finish();
		}

	}
}
