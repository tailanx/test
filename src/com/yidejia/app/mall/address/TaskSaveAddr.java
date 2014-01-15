package com.yidejia.app.mall.address;

import java.io.IOException;



import com.yidejia.app.mall.R;
import com.yidejia.app.mall.exception.TimeOutEx;
//import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.net.address.SaveUserAddress;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.DefinalDate;
import com.yidejia.app.mall.widget.YLProgressDialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
//		bar = new ProgressDialog(activity);
//		bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//		bar.setMessage(activity.getResources().getString(R.string.loading));
//		bar.setCancelable(true);
		
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
	
	private boolean isTimeout = false;
	private Task task;
	
	private class Task extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				String httpresp;
				try {
					httpresp = saveUserAddress.saveAddress(userId, nameString, handsetString, shengString, shiString, quString, areaString, recipient_id, token);
				boolean issuccess = saveUserAddress.analysicSaveJson(httpresp);
				addressId = saveUserAddress.getRecipient_id();
				if(addressId == null || "".equals(addressId)) addressId = recipient_id;
				message = saveUserAddress.getIsSuccessString();
				return issuccess;
				} catch (TimeOutEx e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					isTimeout = true;
				}
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
//			bar.show();
			bar = (ProgressDialog) new YLProgressDialog(activity)
					.createLoadingDialog(activity, null);
			bar.setOnCancelListener(new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
					cancel(true);
		}
			});
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			bar.dismiss();
			if(result) {
				finishIntent();
			} else {
				if (isTimeout) {
					Toast.makeText(
							activity,
							activity.getResources()
									.getString(R.string.time_out),
							Toast.LENGTH_SHORT).show();
					isTimeout = false;
					return;
				}
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
		Intent intent = new Intent();
//		Bundle bundle = new Bundle();
//		bundle.putSerializable("newaddress", addresses);
//		intent.putExtras(bundle);
//		activity.setResult(Consts.NEW_ADDRESS_RESPONSE,intent);
		activity.setResult(DefinalDate.responcode, intent);
		intent.setAction(Consts.CST_NEWADDRESS);
		activity.sendBroadcast(intent);
		activity.finish();
	}

	
	public void closeTask(){
		if(null != task && AsyncTask.Status.RUNNING == task.getStatus().RUNNING){
			task.cancel(true);
		}
	}
}
