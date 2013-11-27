package com.yidejia.app.mall.task;

import java.io.IOException;


import com.yidejia.app.mall.R;
//import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.net.address.SaveUserAddress;
import com.yidejia.app.mall.util.DefinalDate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
//import android.os.Bundle;
import android.widget.Toast;

public class TaskSaveAddr {
	
	private Activity activity;
	private ProgressDialog bar;
	
	private SaveUserAddress saveUserAddress;
	
	private String shengString;
	private String shiString;
	private String quString;
	private String nameString;
	private String areaString;
	private String handsetString;
	private String recipient_id;
	private String userId;
	private String token;
	
	private String addressId;
	private String message;
	
	public TaskSaveAddr(Activity activity){
		this.activity = activity;
		bar = new ProgressDialog(activity);
		bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		bar.setMessage(activity.getResources().getString(R.string.loading));
		bar.setCancelable(true);
		
		saveUserAddress = new SaveUserAddress(activity);
	}
	
	public void saveAddr(String userId, String nameString, String handsetString, 
			String shengString, String shiString, String quString, String areaString,
			String recipient_id, String token) {
		this.userId = userId;
		this.shengString = shengString;
		this.shiString = shiString;
		this.quString = quString;
		this.nameString = nameString;
		this.areaString = areaString;
		this.handsetString = handsetString;
		this.recipient_id = recipient_id;
		this.token = token;
		
		closeTask();
		task = new Task();
		task.execute();
	}
	
	
	private Task task;
	
	private class Task extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				String httpresp = saveUserAddress.saveAddress(userId, nameString, handsetString, shengString, shiString, quString, areaString, recipient_id, token);
				boolean issuccess = saveUserAddress.analysicSaveJson(httpresp);
				addressId = saveUserAddress.getRecipient_id();
				if(addressId == null || "".equals(addressId)) addressId = recipient_id;
				message = saveUserAddress.getIsSuccessString();
				return issuccess;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			bar.show();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			bar.dismiss();
			if(result) {
				finishIntent();
			} else {
				Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
			}
		}
		
	}
	
	
	private void finishIntent(){
//		Addresses addresses = new Addresses();
//		addresses.setProvince(shengString);
//		addresses.setCity(shiString);
//		addresses.setArea(quString);
//		addresses.setName(nameString);
//		addresses.setAddress(areaString);
//		addresses.setHandset(handsetString);
//		addresses.setAddressId(addressId);
		Intent intent = activity.getIntent();
//		Bundle bundle = new Bundle();
//		bundle.putSerializable("newaddress", addresses);
//		intent.putExtras(bundle);
		activity.setResult(DefinalDate.responcode, intent);
		activity.finish();
	}

	
	public void closeTask(){
		if(null != task && AsyncTask.Status.RUNNING == task.getStatus().RUNNING){
			task.cancel(true);
		}
	}
}
