package com.yidejia.app.mall.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.ctrl.IpAddress;
import com.yidejia.app.mall.model.SkinAnswer;
import com.yidejia.app.mall.net.skin.Answer;
import com.yidejia.app.mall.task.TaskCheckCode;
import com.yidejia.app.mall.util.IsPhone;
import com.yidejia.app.mall.widget.DateTimePickerDialog;

public class SkinAnswerActivity {
	private Activity activity;
	private RelativeLayout view;
	private Answer answer;
	private ImageView mButton;
	private IpAddress ip;
	private EditText nameEditText;
	private EditText qqEditText;
	private EditText phoneEditText;
	private RadioGroup sexGroup;
	private TextView birTextView;
	private ImageView commitImageView;

	private String name;
	private String qqNumber;
	private String phoneNumber;
	private String birth;
	private String sex;

	private void setupShow() {
		// mButton = (ImageView) view.findViewById(R.id.skin_test_answer_back);
		nameEditText = (EditText) view.findViewById(R.id.skin_test_name1);
		qqEditText = (EditText) view
				.findViewById(R.id.skin_test_answer_qqnumber1);
		phoneEditText = (EditText) view
				.findViewById(R.id.skin_test_answer_phonenumber1);
		sexGroup = (RadioGroup) view.findViewById(R.id.skin_test_answer_group1);
		birTextView = (TextView) view.findViewById(R.id.skin_test_birthday1);
		commitImageView = (ImageView) view
				.findViewById(R.id.skin_test_answer_commit1);

	}

	public SkinAnswerActivity(RelativeLayout view, final Activity activity) {
		// TODO Auto-generated method stub
		// super.onCreate(savedInstanceState);
		ip = new IpAddress();
		this.activity = activity;
		this.view = view;
		// setContentView(R.layout.skin_test_answer);
		setupShow();
		sex = "女";
		sexGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == R.id.skin_test_answer_female) {
					sex = "女";
				} else {
					sex = "男";
				}

			}
		});

		// mButton.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// activity.finish();
		// }
		// });
		//

		commitImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				judgeNull(activity);
				
				
				
				Task task = new Task();
				task.execute();
			}

			private void judgeNull(final Activity activity) {
				name = nameEditText.getText().toString();
				qqNumber = qqEditText.getText().toString();
				phoneNumber = phoneEditText.getText().toString();
				birth = birTextView.getText().toString().trim();
//				Log.i("info", birth + "birth");
//				Log.i("info", sex + "sex");
				if (name == null || "".equals(name)) {
					Toast.makeText(
							activity,
							activity.getResources().getString(
									R.string.skin_name), Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (birth == null || "".equals(birth)) {
//					Log.i("info", birth + "birth");
					Toast.makeText(
							activity,
							activity.getResources()
									.getString(R.string.skin_bir),
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (qqNumber == null || "".equals(qqNumber)) {
					Toast.makeText(
							activity,
							activity.getResources().getString(R.string.skin_qq),
							Toast.LENGTH_SHORT).show();
					return;
				} else if (qqNumber.length() <= 6 || qqNumber.length() >= 10) {
					Toast.makeText(
							activity,
							activity.getResources().getString(
									R.string.skin_qq_error), Toast.LENGTH_SHORT)
							.show();
					return;
				}
				boolean phone = IsPhone.isMobileNO(phoneNumber);
				if (phoneNumber == null || "".equals(phoneNumber)) {
					Toast.makeText(activity,
							activity.getResources().getString(R.string.skin_phone),
							Toast.LENGTH_LONG).show();
					return;
				}
				if (!phone) {
					Toast.makeText(activity,
							activity.getResources().getString(R.string.phone),
							Toast.LENGTH_SHORT).show();
					return;
				}
			}
		});

		birTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DateTimePickerDialog dateTimePicKDialog = new DateTimePickerDialog(
						activity);
				dateTimePicKDialog.dateTimePicKDialog(birTextView, 1);
			}
		});
	}

	// public void onClick(View v) {
	// switch (v.getId()) {
	// case R.id.skin_test_answer_back:
	// this.finish();
	// // Intent intent = new Intent(SkinAnswerActivity.this,)
	// break;

	// case R.id.skin_test_answer_commit:
	// Task task = new Task();
	// task.execute();
	// break;
	// case R.id.skin_test_birthday:
	//
	// DateTimePickerDialog dateTimePicKDialog = new DateTimePickerDialog(
	// SkinAnswerActivity.this);
	// dateTimePicKDialog.dateTimePicKDialog(birTextView, 1);
	// break;
	// }
	// // TODO Auto-generated method stub
	// }

	private ProgressDialog bar;
	private SkinAnswer skinAnswer;

	public class Task extends AsyncTask<Void, Void, Boolean> {
		boolean isSucess = false;

		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				// TODO Auto-generated method stub

					List<String> keys = SkinQuesActivity.keys;
					Log.i("info", keys.toString()+"  keys");
					String a[] = new String[keys.size()];
					for(int i=0;i<keys.size();i++){
						a[i] = keys.get(i);
					}
				
				answer = new Answer();
				String sb = null;
				sb = a[2].substring(1, a[2].length()-1);
				
				Log.i(SkinAnswer.class.getName(), sb+"    http");
				 String httpresp = answer.getHttpResp(phoneNumber,
						 phoneNumber ,name , sex, birth, a[0], a[1],
				 sb.toString(), a[3], a[4], 122 + "", ip.getIpAddress());
				 Log.i(SkinAnswer.class.getName(), httpresp+"    http");
				 isSucess = answer.analysis(httpresp);
				 skinAnswer = answer.getAnswer();
				 if(isSucess){
					 Intent  intent = new Intent(activity, SkinResultAcitivity.class);
					 Bundle bunlder = new Bundle();
					 bunlder.putSerializable("SkinAnswer", skinAnswer);
					 intent.putExtras(bunlder);
					 activity.startActivity(intent);
					 activity.finish();
				 }
				// Log.i(SkinAnswer.class.getName(), skinAnswer.toString());
				 return isSucess;
			} catch (Exception e) {
				// TODO: handle exception
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			bar.dismiss();
			if (result) {

			}

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			bar = new ProgressDialog(activity);
			bar.setCancelable(true);
			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			bar.show();

		}

	}
}
