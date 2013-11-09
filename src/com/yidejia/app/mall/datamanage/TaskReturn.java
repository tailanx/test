package com.yidejia.app.mall.datamanage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;

import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.net.order.ReturnOrder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class TaskReturn {
	
	private Activity activity;
	
	private EditText contactEditText;
	private EditText msgEditText;
	private EditText phoneEditText;
	private Spinner spinner;
	
	private String cause;//退换原因
	private String contact;//联系人
	private String contact_manner;//联系方式
	private String desc;//原因描述
	private String userId;
	private String token;
	private String theDate;
	private String orderCode;
	
	private Task task;
	
	public TaskReturn(Activity activity){
		this.activity = activity;
	}
	
	public void returnOrder(String orderCode){
		this.orderCode = orderCode;
		findIds();
		getParam();
		
		if (task != null
				&& task.getStatus() == AsyncTask.Status.RUNNING) {
			task.cancel(true); // 如果Task还在运行，则先取消它
		}
		task = new Task();
		task.execute();
	}
	
	private void findIds(){
		spinner = (Spinner) activity.findViewById(R.id.exchange_spinner);
		contactEditText = (EditText) activity.findViewById(R.id.exchange_edittext_lianxiren);
		phoneEditText = (EditText) activity.findViewById(R.id.exchange_edittext_lianxifangshi);
		msgEditText = (EditText) activity.findViewById(R.id.go_pay_leave_message);
	}
	
	private void getParam(){
		cause = spinner.getSelectedItem().toString();
		contact = contactEditText.getText().toString();
		contact_manner = phoneEditText.getText().toString();
		desc = msgEditText.getText().toString();
		userId = ((MyApplication)activity.getApplication()).getUserId();
		token = ((MyApplication)activity.getApplication()).getToken();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		theDate = sdf.format(new Date(0));
	}
	
	
	private class Task extends AsyncTask<Void, Void, Boolean>{

		private ProgressDialog bar;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			bar = new ProgressDialog(activity);
			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			bar.show();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result){
				Toast.makeText(activity, "提交成功!", Toast.LENGTH_LONG).show();
			}else {
				Toast.makeText(activity, "提交失败!", Toast.LENGTH_LONG).show();
			}
			bar.dismiss();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ReturnOrder order = new ReturnOrder();
			try {
				String httpResp = order.getHttpResp(userId, orderCode, theDate, contact, contact_manner, cause, desc, token);
				try {
					return order.analysisReturn(httpResp);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		
	}
	
	public void closeTask(){
		if (task != null
				&& task.getStatus() == AsyncTask.Status.RUNNING) {
			task.cancel(true); // 如果Task还在运行，则先取消它
		}
	}
}
